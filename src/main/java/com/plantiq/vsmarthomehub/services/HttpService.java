package com.plantiq.vsmarthomehub.services;

import com.plantiq.vsmarthomehub.vManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpService {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    public static String postRequest(String url, String data) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .uri(URI.create(url))
                .setHeader("User-Agent","Java 11 HttpClient")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = null;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("[HttpService] Performed POST request to '"+url+"'");

        return response.body();
    }

    public static String patchRequest(String url, String data) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .method("PATCH",HttpRequest.BodyPublishers.ofString(data))
                .uri(URI.create(url))
                .setHeader("User-Agent","Java 11 HttpClient")
                .setHeader("Authorization", "Bearer "+ vManager.getInstance().getToken())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = null;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("[HttpService] Performed PATCH request to '"+url+"'");

        return response.body();
    }

    public static String getRequest(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent","Java 11 HttpClient")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .setHeader("Authorization", "Bearer "+ vManager.getInstance().getToken())
                .build();

        HttpResponse<String> response = null;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("[HttpService] Performed GET request to '"+url+"'");

        return response.body();
    }

    public static String deleteRequest(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(url))
                .setHeader("User-Agent","Java 11 HttpClient")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = null;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("[HttpService] Performed DELETE request to '"+url+"'");

        return response.body();
    }


}
