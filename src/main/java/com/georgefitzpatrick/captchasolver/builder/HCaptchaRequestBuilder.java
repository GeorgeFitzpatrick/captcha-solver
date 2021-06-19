package com.georgefitzpatrick.captchasolver.builder;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import lombok.NonNull;

import static com.georgefitzpatrick.captchasolver.CaptchaType.HCAPTCHA_CHECKBOX;
import static com.georgefitzpatrick.captchasolver.CaptchaType.HCAPTCHA_INVISIBLE;

public class HCaptchaRequestBuilder extends CaptchaRequest.Builder {

    public HCaptchaRequestBuilder(boolean checkbox) {
        super(checkbox ? HCAPTCHA_CHECKBOX : HCAPTCHA_INVISIBLE);
    }

    @NonNull
    public final HCaptchaRequestBuilder siteKey(@NonNull String siteKey) {
        params.put("siteKey", siteKey);
        return this;
    }

    @Override
    public final @NonNull CaptchaRequest build() {
        String siteKey = params.get("siteKey");
        require(siteKey != null, String.format("Invalid Site Key (%s)", siteKey));

        return super.build();
    }

}
