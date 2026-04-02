package com.leocodes.relikd.util;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCache {

    private static final int MAX_CACHE_SIZE = 120;

    private static final Map<String, Image> cache = new LinkedHashMap<>(64, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Image> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    private static final ConcurrentHashMap<String, Boolean> failedUrls = new ConcurrentHashMap<>();

    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    private static final String USER_AGENT = "Relikd/1.0 (Vintage Computer Catalog; JavaFX Desktop App)";

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public static void loadAsync(String url, ImageView target, Runnable onComplete) {
        if (url == null || url.isBlank()) {
            runOnFx(onComplete);
            return;
        }

        Image cached;
        synchronized (cache) {
            cached = cache.get(url);
        }
        if (cached != null) {
            target.setImage(cached);
            runOnFx(onComplete);
            return;
        }

        if (failedUrls.containsKey(url)) {
            runOnFx(onComplete);
            return;
        }

        EXECUTOR.submit(() -> {
            try {

                URI uri = URI.create(url);

                var request = HttpRequest.newBuilder(uri)
                        .header("User-Agent", USER_AGENT)
                        .header("Accept", "image/*")
                        .GET()
                        .timeout(Duration.ofSeconds(12))
                        .build();

                var response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());

                if (response.statusCode() != 200) {
                    System.err.println("Image fetch HTTP " + response.statusCode() + ": " + url);
                    failedUrls.put(url, true);
                    runOnFx(onComplete);
                    return;
                }

                String contentType = response.headers()
                        .firstValue("Content-Type")
                        .orElse("");
                if (!contentType.startsWith("image/")) {
                    System.err.println("Image fetch got non-image content (" + contentType + "): " + url);
                    failedUrls.put(url, true);
                    runOnFx(onComplete);
                    return;
                }

                byte[] bytes = response.body();
                Image img = new Image(new ByteArrayInputStream(bytes));

                if (img.isError()) {
                    System.err.println("Image decode failed: " + url);
                    failedUrls.put(url, true);
                    runOnFx(onComplete);
                    return;
                }

                synchronized (cache) {
                    cache.put(url, img);
                }

                Platform.runLater(() -> {
                    target.setImage(img);
                    if (onComplete != null)
                        onComplete.run();
                });
                return;

            } catch (IllegalArgumentException e) {
                System.err.println("Malformed image URL: " + url);
            } catch (Exception e) {
                System.err.println("Image fetch error for " + url + ": " + e.getMessage());
            }

            failedUrls.put(url, true);
            runOnFx(onComplete);
        });
    }

    private static void runOnFx(Runnable action) {
        if (action == null)
            return;
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }
}
