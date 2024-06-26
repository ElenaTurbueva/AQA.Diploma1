package com.example.utils;

import okhttp3.*;

import java.io.IOException;

public class HttpClient {
    private static final OkHttpClient client = new OkHttpClient();

    public static String get(String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(Config.BASE_URL + endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String post(String endpoint, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(Config.BASE_URL + endpoint)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}


