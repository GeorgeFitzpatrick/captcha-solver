package com.georgefitzpatrick.captchasolver.builder;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import lombok.NonNull;

import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V3;

public class RecaptchaV3RequestBuilder extends CaptchaRequest.Builder {

    public RecaptchaV3RequestBuilder() {
        super(RECAPTCHA_V3);
        params.put("action", "verify");
    }

    @NonNull
    public RecaptchaV3RequestBuilder siteKey(@NonNull String siteKey) {
        params.put("siteKey", siteKey);
        return this;
    }

    @NonNull
    public RecaptchaV3RequestBuilder minScore(@NonNull double minScore) {
        params.put("minScore", String.valueOf(minScore));
        return this;
    }

    @Override
    public @NonNull CaptchaRequest build() {
        String siteKey = params.get("siteKey");
        require(siteKey != null, String.format("Invalid Site Key (%s)", siteKey));

        String minScore = params.get("minScore");
        require(minScore != null, String.format("Invalid Min Score (%s)", minScore));

        return super.build();
    }

}