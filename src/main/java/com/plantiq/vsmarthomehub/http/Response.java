package com.plantiq.vsmarthomehub.http;

import com.plantiq.vsmarthomehub.vManager;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Response {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    public static Response fromRequest(String uri, HttpMethods method, String data,boolean authed) {

        HttpRequest.Builder request = HttpRequest.newBuilder();

        request.uri(URI.create(vManager.getInstance().baseURI+uri));

        switch (method){
            case GET -> request.GET();
            case POST -> {
                request.POST(HttpRequest.BodyPublishers.ofString(data));
                request.header("Content-Type", "application/x-www-form-urlencoded");
            }
            case PATCH -> {
                request.method("PATCH",HttpRequest.BodyPublishers.ofString(data));
                request.header("Content-Type", "application/x-www-form-urlencoded");
            }
            case DELETE -> request.DELETE();
        }

        request.setHeader("User-Agent","PlantIQ vManager client");

        if(authed){
            request.setHeader("Authorization", "Bearer "+ vManager.getInstance().getToken());
        }

        HttpResponse<String> response = null;

        try {
            response = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[Response] performed "+method+" request to "+vManager.getInstance().baseURI+uri);

        return new Response(response);
    }

    public int status;
    public boolean outcome;
    public String message;
    public JSONObject data;

    public Response(HttpResponse<String> rawResponse){
        this.status = rawResponse.statusCode();
        this.data = new JSONObject(rawResponse.body());
    }

    public int getStatus(){
        return this.status;
    }

    public boolean getOutcome(){
        return this.data.getBoolean("outcome");
    }

    public String getContentByKey(String key){
        return this.data.getString(key);
    }

    public JSONObject getData(){
        return this.data;
    }

}
