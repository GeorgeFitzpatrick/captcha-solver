package com.georgefitzpatrick.captchasolver.builder;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import lombok.NonNull;

import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V2_CHECKBOX;
import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V2_INVISIBLE;

public class RecaptchaV2RequestBuilder extends CaptchaRequest.Builder {

    public RecaptchaV2RequestBuilder(@NonNull boolean checkbox) {
        super(checkbox ? RECAPTCHA_V2_CHECKBOX : RECAPTCHA_V2_INVISIBLE);
        params.put("action", "verify");
    }

    @NonNull
    public RecaptchaV2RequestBuilder siteKey(@NonNull String siteKey) {
        params.put("siteKey", siteKey);
        return this;
    }

    @Override
    public @NonNull CaptchaRequest build() {
        String siteKey = params.get("siteKey");
        require(siteKey != null, String.format("Invalid Site Key (%s)", siteKey));

        return super.build();
    }

}
