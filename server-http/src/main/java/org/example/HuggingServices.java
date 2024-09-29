package org.example;

import lombok.Data;

import okhttp3.*;

import java.io.IOException;

@Data

public class HuggingServices {
    private final String apiUri = "https://api-inference.huggingface.co/models/bigscience/bloom";
    private final String token = "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";

    public String getData (String prop) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                "{\"inputs\": \"" + prop + "\", \"parameters\": {\"temperature\": 0.2, \"max_length\": 60}}"
        );

        Request request = new Request.Builder()
                .url(this.apiUri)
                .post(body)
                .addHeader("Authorization", "Bearer " + this.token)
                .build();

        try {
            Response response = client.newCall(request).execute();
                prop = response.body().string();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prop;
    }
}
