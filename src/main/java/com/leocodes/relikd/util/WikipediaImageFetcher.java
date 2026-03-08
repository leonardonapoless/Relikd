package com.leocodes.relikd.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WikipediaImageFetcher {

    private static final String API_URL = "https://en.wikipedia.org/w/api.php";
    private static final String USER_AGENT = "Relikd/1.0";
    private static final int THUMB_SIZE = 400;

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public static String fetchImageUrl(String brand, String model) {
        if ("Mac mini G4".equals(model)) {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Mac-mini-1st-gen.jpg/400px-Mac-mini-1st-gen.jpg";
        }
        try {
            String q = brand + " " + model + " computer";
            String enc = URLEncoder.encode(q, StandardCharsets.UTF_8);

            String url = API_URL
                    + "?action=query&generator=search&gsrsearch=" + enc
                    + "&gsrlimit=1&prop=pageimages&piprop=thumbnail&pithumbsize=" + THUMB_SIZE
                    + "&format=json";

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", USER_AGENT)
                    .GET()
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> res = HTTP_CLIENT.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200) {
                return null;
            }

            return extractThumbnailUrl(res.body());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String extractThumbnailUrl(String json) {
        try {
            Gson gson = new Gson();
            JsonObject root = gson.fromJson(json, JsonObject.class);
            
            JsonObject query = root.getAsJsonObject("query");
            if (query == null) {
                return null;
            }
            
            JsonObject pages = query.getAsJsonObject("pages");
            if (pages == null) {
                return null;
            }
            
            String firstKey = pages.keySet().iterator().next();
            JsonObject page = pages.getAsJsonObject(firstKey);
            
            JsonObject thumb = page.getAsJsonObject("thumbnail");
            if (thumb == null) {
                return null;
            }
            
            return thumb.get("source").getAsString();
        } catch (Exception e) {
            System.err.println("Failed to parse the Wikipedia JSON response.");
            return null;
        }
    }
}
