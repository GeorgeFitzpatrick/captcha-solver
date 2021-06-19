package com.georgefitzpatrick.captchasolver.twocaptcha.translators;

import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import com.georgefitzpatrick.captchasolver.twocaptcha.TwoCaptchaRequest;
import lombok.NonNull;

import java.util.Map;

import static com.georgefitzpatrick.captchasolver.CaptchaType.NORMAL;

public class TwoCaptchaNormalCaptchaTranslator implements CaptchaRequest.Translator<TwoCaptchaRequest> {

    public static TwoCaptchaNormalCaptchaTranslator INSTANCE = new TwoCaptchaNormalCaptchaTranslator();

    private TwoCaptchaNormalCaptchaTranslator() {
    }

    public static TwoCaptchaNormalCaptchaTranslator getInstance() {
        return INSTANCE;
    }

    @Override
    public @NonNull TwoCaptchaRequest translate(@NonNull CaptchaRequest request) {
        CaptchaType type = request.getCaptchaType();
        if (type != NORMAL) {
            throw new IllegalStateException(String.format("Untranslatable Captcha Type (%s)", type));
        }

        Map<String, String> params = request.getParams();

        TwoCaptchaRequest captcha = new TwoCaptchaRequest();
        captcha.getFiles().putAll(request.getFiles());
        captcha.setProxy(request.getProxy());

        if (params.containsKey("caseSensitive")) {
            boolean phrase = Boolean.parseBoolean(params.get("caseSensitive"));
            captcha.getParams().put("regsense", phrase ? "1" : "0");
        }

        if (params.containsKey("calculate")) {
            boolean phrase = Boolean.parseBoolean(params.get("calculate"));
            captcha.getParams().put("calc", phrase ? "1" : "0");
        }

        if (params.containsKey("phrase")) {
            boolean phrase = Boolean.parseBoolean(params.get("phrase"));
            captcha.getParams().put("phrase", phrase ? "1" : "0");
        }

        if (params.containsKey("minLength")) {
            captcha.getParams().put("min_len", params.get("minLength"));
        }

        if (params.containsKey("maxLength")) {
            captcha.getParams().put("max_len", params.get("maxLength"));
        }

        if (params.containsKey("language")) {
            captcha.getParams().put("lang", params.get("language"));
        }

        if (params.containsKey("hint")) {
            captcha.getParams().put("textinstructions", params.get("hint"));
        }

        return captcha;
    }

}
