package com.georgefitzpatrick.captchasolver.twocaptcha.translators;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import com.georgefitzpatrick.captchasolver.twocaptcha.TwoCaptchaRequest;
import lombok.NonNull;

import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V3;

public class TwoCaptchaRecaptchaV3Translator implements CaptchaRequest.Translator<TwoCaptchaRequest> {

    public static TwoCaptchaRecaptchaV3Translator INSTANCE = new TwoCaptchaRecaptchaV3Translator();

    private TwoCaptchaRecaptchaV3Translator() {
    }

    public static TwoCaptchaRecaptchaV3Translator getInstance() {
        return INSTANCE;
    }

    @Override
    public @NonNull TwoCaptchaRequest translate(@NonNull CaptchaRequest request) {
        CaptchaType type = request.getCaptchaType();
        if (type != RECAPTCHA_V3) {
            throw new IllegalStateException(String.format("Untranslatable Captcha Type (%s)", type));
        }

        TwoCaptchaRequest captcha = new TwoCaptchaRequest();
        captcha.getParams().put("method", "userrecaptcha");
        captcha.getParams().put("googlekey", request.getParams().get("siteKey"));
        captcha.getParams().put("pageurl", request.getUrl());
        captcha.getParams().put("version", "v3");
        captcha.getParams().put("action", request.getParams().get("action"));
        captcha.getParams().put("min_score", request.getParams().get("minScore"));
        captcha.setProxy(request.getProxy());

        return captcha;
    }

}
