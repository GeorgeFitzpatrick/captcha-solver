package com.georgefitzpatrick.captchasolver.twocaptcha.translators;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import com.georgefitzpatrick.captchasolver.twocaptcha.TwoCaptchaRequest;
import lombok.NonNull;

import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V2_CHECKBOX;
import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V2_INVISIBLE;

public class TwoCaptchaRecaptchaV2Translator implements CaptchaRequest.Translator<TwoCaptchaRequest> {

    public static TwoCaptchaRecaptchaV2Translator INSTANCE = new TwoCaptchaRecaptchaV2Translator();

    private TwoCaptchaRecaptchaV2Translator() {
    }

    public static TwoCaptchaRecaptchaV2Translator getInstance() {
        return INSTANCE;
    }

    @Override
    public @NonNull TwoCaptchaRequest translate(@NonNull CaptchaRequest request) {
        CaptchaType type = request.getCaptchaType();
        if (type != RECAPTCHA_V2_CHECKBOX && type != RECAPTCHA_V2_INVISIBLE) {
            throw new IllegalStateException(String.format("Untranslatable Captcha Type (%s)", type));
        }

        boolean checkbox = request.getCaptchaType() == RECAPTCHA_V2_CHECKBOX;

        TwoCaptchaRequest captcha = new TwoCaptchaRequest();
        captcha.getParams().put("method", "userrecaptcha");
        captcha.getParams().put("googlekey", request.getParams().get("siteKey"));
        captcha.getParams().put("pageurl", request.getUrl());
        captcha.getParams().put("invisible", checkbox ? "0" : "1");
        captcha.getParams().put("action", request.getParams().get("action"));
        captcha.setProxy(request.getProxy());

        return captcha;
    }

}
