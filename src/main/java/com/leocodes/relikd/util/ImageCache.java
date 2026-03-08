package com.leocodes.relikd.util;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCache {

    private static final Map<String, Image> cache = new ConcurrentHashMap<>();
    
    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
    
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public static void loadAsync(String urlString, ImageView imageView, Runnable onComplete) {
        if (urlString == null || urlString.isEmpty()) {
            if (onComplete != null) onComplete.run();
            return;
        }

        if (cache.containsKey(urlString)) {
            imageView.setImage(cache.get(urlString));
            if (onComplete != null) onComplete.run();
            return;
        }

        EXECUTOR.submit(() -> {
            try {
                var req = HttpRequest.newBuilder()
                        .uri(URI.create(urlString))
                        .header("User-Agent", "Relikd/1.0")
                        .GET()
                        .build();

                var res = HTTP_CLIENT.send(req, HttpResponse.BodyHandlers.ofInputStream());
                if (res.statusCode() == 200) {
                    Image img = new Image(res.body());
                    if (!img.isError()) {
                        cache.put(urlString, img);
                        javafx.application.Platform.runLater(() -> {
                            imageView.setImage(img);
                            if (onComplete != null) onComplete.run();
                        });
                        return;
                    }
                }
            } catch (Exception e) {
            }
            
            javafx.application.Platform.runLater(() -> {
                if (onComplete != null) onComplete.run();
            });
        });
    }
}
