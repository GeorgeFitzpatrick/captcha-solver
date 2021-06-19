package com.georgefitzpatrick.captchasolver.twocaptcha;

import lombok.NonNull;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.georgefitzpatrick.captchasolver.util.ValidationUtil.require;

public class TwoCaptchaClient {

    private static final TwoCaptchaClient INSTANCE = new TwoCaptchaClient();

    private final OkHttpClient client;

    private TwoCaptchaClient() {
        this.client = new OkHttpClient.Builder()
                .followRedirects(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static TwoCaptchaClient getInstance() {
        return INSTANCE;
    }

    public String submitSolveRequest(String apiDomain, Map<String, String> params, Map<String, byte[]> files) throws Exception {
        HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiDomain)
                .addPathSegment("in.php");

        RequestBody body;
        if (files.size() == 0) {
            FormBody.Builder form = new FormBody.Builder();
            params.forEach(form::add);
            body = form.build();
        } else {
            MultipartBody.Builder form = new MultipartBody.Builder();
            form.setType(MultipartBody.FORM);
            params.forEach(form::addFormDataPart);
            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                form.addFormDataPart(entry.getKey(), entry.getKey(), RequestBody.create(entry.getValue()));
            }
            body = form.build();
        }

        Request request = new Request.Builder()
                .url(url.build())
                .post(body)
                .build();

        return send(request);
    }

    public String getResult(String apiDomain, Map<String, String> params) throws Exception {
        HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host(apiDomain)
                .addPathSegment("res.php");

        params.forEach(url::addQueryParameter);

        Request request = new Request.Builder()
                .url(url.build())
                .build();

        return send(request);
    }

    private String send(@NonNull Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            require(response.isSuccessful(),
                    String.format("Unexpected HTTP Code (%s)", response.code()));

            String body = response.body().string();

            require(!body.startsWith("ERROR_"), body);

            return body;
        }
    }

}
