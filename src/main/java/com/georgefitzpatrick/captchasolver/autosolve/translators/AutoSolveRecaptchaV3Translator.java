package com.georgefitzpatrick.captchasolver.autosolve.translators;

import com.fuzzy.aycd.autosolve.model.AutoSolveProxyConfig;
import com.fuzzy.aycd.autosolve.model.task.impl.CaptchaTokenRequest;
import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import lombok.NonNull;

import java.util.Map;
import java.util.UUID;

import static com.georgefitzpatrick.captchasolver.CaptchaType.RECAPTCHA_V3;

public class AutoSolveRecaptchaV3Translator implements CaptchaRequest.Translator<CaptchaTokenRequest> {

    public static AutoSolveRecaptchaV3Translator INSTANCE = new AutoSolveRecaptchaV3Translator();

    private AutoSolveRecaptchaV3Translator() { }

    public static AutoSolveRecaptchaV3Translator getInstance() {
        return INSTANCE;
    }

    @Override
    public @NonNull CaptchaTokenRequest translate(@NonNull CaptchaRequest request) {
        CaptchaType type = request.getCaptchaType();
        if (type != RECAPTCHA_V3) {
            throw new IllegalStateException(String.format("Untranslatable Captcha Type (%s)", type));
        }

        Map<String, String> params = request.getParams();

        String id = UUID.randomUUID().toString();
        String url = request.getUrl();
        String siteKey = params.get("siteKey");
        String action = params.get("action");
        double minScore = Double.parseDouble(params.get("minScore"));

        AutoSolveProxyConfig proxyConfig;
        boolean proxyRequired;
        if (request.isProxyRequired()) {
            String proxy = request.getProxy().toString();
            proxyConfig = AutoSolveProxyConfig.parseOneOrNone(proxy);
            proxyRequired = true;
        } else {
            proxyConfig = null;
            proxyRequired = false;
        }

        return CaptchaTokenRequest.ofReCaptchaV3(id, url, siteKey, action, minScore, proxyConfig, proxyRequired);
    }

}
