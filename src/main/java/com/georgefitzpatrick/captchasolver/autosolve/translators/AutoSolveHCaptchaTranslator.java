package com.georgefitzpatrick.captchasolver.autosolve.translators;

import com.fuzzy.aycd.autosolve.model.AutoSolveProxyConfig;
import com.fuzzy.aycd.autosolve.model.task.impl.CaptchaTokenRequest;
import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import lombok.NonNull;

import java.util.Map;
import java.util.UUID;

import static com.georgefitzpatrick.captchasolver.CaptchaType.HCAPTCHA_CHECKBOX;
import static com.georgefitzpatrick.captchasolver.CaptchaType.HCAPTCHA_INVISIBLE;

public class AutoSolveHCaptchaTranslator implements CaptchaRequest.Translator<CaptchaTokenRequest> {

    public static AutoSolveHCaptchaTranslator INSTANCE = new AutoSolveHCaptchaTranslator();

    private AutoSolveHCaptchaTranslator() { }

    public static AutoSolveHCaptchaTranslator getInstance() {
        return INSTANCE;
    }

    @Override
    public @NonNull CaptchaTokenRequest translate(@NonNull CaptchaRequest request) {
        CaptchaType type = request.getCaptchaType();
        if (type != HCAPTCHA_CHECKBOX && type != HCAPTCHA_INVISIBLE) {
            throw new IllegalStateException(String.format("Untranslatable Captcha Type (%s)", type));
        }

        Map<String, String> params = request.getParams();

        String id = UUID.randomUUID().toString();
        String url = request.getUrl();
        String siteKey = params.get("siteKey");
        boolean checkbox = request.getCaptchaType() == HCAPTCHA_CHECKBOX;

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

        return CaptchaTokenRequest.ofHCaptcha(id, url, siteKey, checkbox, proxyConfig, proxyRequired);
    }

}
