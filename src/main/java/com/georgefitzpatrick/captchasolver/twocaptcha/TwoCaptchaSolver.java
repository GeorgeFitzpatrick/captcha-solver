package com.georgefitzpatrick.captchasolver.twocaptcha;

import com.georgefitzpatrick.captchasolver.AbstractCaptchaSolver;
import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaResponse;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import com.georgefitzpatrick.captchasolver.twocaptcha.translators.TwoCaptchaHCaptchaTranslator;
import com.georgefitzpatrick.captchasolver.twocaptcha.translators.TwoCaptchaNormalCaptchaTranslator;
import com.georgefitzpatrick.captchasolver.twocaptcha.translators.TwoCaptchaRecaptchaV2Translator;
import com.georgefitzpatrick.captchasolver.twocaptcha.translators.TwoCaptchaRecaptchaV3Translator;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.georgefitzpatrick.captchasolver.CaptchaType.*;
import static com.georgefitzpatrick.captchasolver.util.ValidationUtil.require;

@Setter
public class TwoCaptchaSolver extends AbstractCaptchaSolver {

    private static final String DEFAULT_API_DOMAIN = "2captcha.com";
    private static final int POLL_INTERVAL = 5_000;
    private static final int TIMEOUT = 600_000;
    private static final Map<CaptchaType, CaptchaRequest.Translator<TwoCaptchaRequest>> TRANSLATORS = new HashMap<>();

    static {
        TRANSLATORS.put(NORMAL, TwoCaptchaNormalCaptchaTranslator.getInstance());
        TRANSLATORS.put(RECAPTCHA_V2_CHECKBOX, TwoCaptchaRecaptchaV2Translator.getInstance());
        TRANSLATORS.put(RECAPTCHA_V2_INVISIBLE, TwoCaptchaRecaptchaV2Translator.getInstance());
        TRANSLATORS.put(RECAPTCHA_V3, TwoCaptchaRecaptchaV3Translator.getInstance());
        TRANSLATORS.put(HCAPTCHA_CHECKBOX, TwoCaptchaHCaptchaTranslator.getInstance());
        TRANSLATORS.put(HCAPTCHA_INVISIBLE, TwoCaptchaHCaptchaTranslator.getInstance());
    }

    private final String apiDomain;
    private String apiKey;

    public TwoCaptchaSolver(String apiKey) {
        this(DEFAULT_API_DOMAIN, apiKey);
    }

    public TwoCaptchaSolver(@NonNull String apiDomain, @NonNull String apiKey) {
        this.apiDomain = apiDomain;
        this.apiKey = apiKey;
    }

    @Override
    public CaptchaResponse fulfil(@NonNull CaptchaRequest request) throws Exception {
        CaptchaRequest.Translator<TwoCaptchaRequest> translator = TRANSLATORS.get(request.getCaptchaType());
        TwoCaptchaRequest captcha = translator.translate(request);

        String solveId = send(captcha);

        String result;
        long start = System.currentTimeMillis();
        while (true) {
            result = getResult(solveId);

            if (result != null) {
                return new CaptchaResponse.Builder().result(result).request(request).build();
            }

            if (System.currentTimeMillis() - start >= TIMEOUT) {
                throw new Exception(String.format("Timeout Exceeded (%s)", TIMEOUT));
            }

            Thread.sleep(POLL_INTERVAL);
        }
    }

    private String send(TwoCaptchaRequest captcha) throws Exception {
        Map<String, String> params = captcha.getParams();
        Map<String, byte[]> files = captcha.getFiles();

        params.put("key", apiKey);

        String response = TwoCaptchaClient.getInstance().submitSolveRequest(apiDomain, params, files);

        require(response.startsWith("OK|"),
                String.format("Unrecognised Response (%s)", response));

        return response.substring(3);
    }

    private String getResult(String id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("action", "get");
        params.put("id", id);
        params.put("key", apiKey);

        String response = TwoCaptchaClient.getInstance().getResult(apiDomain, params);

        if (response.equals("CAPCHA_NOT_READY")) {
            return null;
        }

        require(response.startsWith("OK|"),
                String.format("Unrecognised Response (%s)", response));

        return response.substring(3);
    }

}
