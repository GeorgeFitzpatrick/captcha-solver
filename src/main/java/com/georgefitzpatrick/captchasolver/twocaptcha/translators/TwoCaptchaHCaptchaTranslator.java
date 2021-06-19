package com.georgefitzpatrick.captchasolver.twocaptcha.translators;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import com.georgefitzpatrick.captchasolver.twocaptcha.TwoCaptchaRequest;
import lombok.NonNull;

import static com.georgefitzpatrick.captchasolver.CaptchaType.HCAPTCHA_CHECKBOX;
import static com.georgefitzpatrick.captchasolver.CaptchaType.HCAPTCHA_INVISIBLE;

public class TwoCaptchaHCaptchaTranslator implements CaptchaRequest.Translator<TwoCaptchaRequest> {

    public static TwoCaptchaHCaptchaTranslator INSTANCE = new TwoCaptchaHCaptchaTranslator();

    private TwoCaptchaHCaptchaTranslator() {
    }

    public static TwoCaptchaHCaptchaTranslator getInstance() {
        return INSTANCE;
    }

    @Override
    public @NonNull TwoCaptchaRequest translate(@NonNull CaptchaRequest request) {
        CaptchaType type = request.getCaptchaType();
        if (type != HCAPTCHA_CHECKBOX && type != HCAPTCHA_INVISIBLE) {
            throw new IllegalStateException(String.format("Untranslatable Captcha Type (%s)", type));
        }

        TwoCaptchaRequest captcha = new TwoCaptchaRequest();
        captcha.getParams().put("method", "hcaptcha");
        captcha.getParams().put("sitekey", request.getParams().get("siteKey"));
        captcha.getParams().put("pageurl", request.getUrl());
        captcha.setProxy(request.getProxy());

        return captcha;
    }

}
