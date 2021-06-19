package com.georgefitzpatrick.captchasolver.builder;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static com.georgefitzpatrick.captchasolver.CaptchaType.NORMAL;

public class NormalCaptchaRequestBuilder extends CaptchaRequest.Builder {

    public NormalCaptchaRequestBuilder() {
        super(NORMAL);
    }

    @NonNull
    public NormalCaptchaRequestBuilder image(@NonNull String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);
        return image(bytes);
    }

    @NonNull
    public NormalCaptchaRequestBuilder image(@NonNull File file) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }

        return image(bytes);
    }

    @NonNull
    public NormalCaptchaRequestBuilder image(@NonNull byte[] bytes) {
        files.put("image", bytes);
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder caseSensitive(@NonNull boolean caseSensitive) {
        params.put("caseSensitive", String.valueOf(caseSensitive));
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder calculate(@NonNull boolean calculate) {
        params.put("calculate", String.valueOf(calculate));
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder phrase(@NonNull boolean phrase) {
        params.put("phrase", String.valueOf(phrase));
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder minLength(@NonNull int minLength) {
        params.put("minLength", String.valueOf(minLength));
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder maxLength(@NonNull int maxLength) {
        params.put("maxLength", String.valueOf(maxLength));
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder language(@NonNull String language) {
        params.put("language", String.valueOf(language));
        return this;
    }

    @NonNull
    public NormalCaptchaRequestBuilder hint(@NonNull String hint) {
        params.put("hint", String.valueOf(hint));
        return this;
    }

    @Override
    public @NonNull CaptchaRequest build() {
        require(files.get("image") != null, "Invalid Image");

        return super.build();
    }

}
