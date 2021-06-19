package com.georgefitzpatrick.captchasolver.autosolve;

import com.fuzzy.aycd.autosolve.AbstractAutoSolveManager;
import com.fuzzy.aycd.autosolve.exception.AutoSolveException;
import com.fuzzy.aycd.autosolve.model.AutoSolveAccount;
import com.fuzzy.aycd.autosolve.model.AutoSolveStatus;
import com.fuzzy.aycd.autosolve.model.task.impl.CaptchaToken;
import com.fuzzy.aycd.autosolve.model.task.impl.CaptchaTokenRequest;
import com.georgefitzpatrick.captchasolver.AbstractCaptchaSolver;
import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.CaptchaResponse;
import com.georgefitzpatrick.captchasolver.CaptchaType;
import com.georgefitzpatrick.captchasolver.autosolve.translators.AutoSolveHCaptchaTranslator;
import com.georgefitzpatrick.captchasolver.autosolve.translators.AutoSolveRecaptchaV2Translator;
import com.georgefitzpatrick.captchasolver.autosolve.translators.AutoSolveRecaptchaV3Translator;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.georgefitzpatrick.captchasolver.CaptchaType.*;

public class AutoSolveSolver extends AbstractCaptchaSolver {

    private static final int TIMEOUT = 600_000;
    private static final Map<CaptchaType, CaptchaRequest.Translator<CaptchaTokenRequest>> TRANSLATORS = new HashMap<>();

    static {
        TRANSLATORS.put(RECAPTCHA_V2_CHECKBOX, AutoSolveRecaptchaV2Translator.getInstance());
        TRANSLATORS.put(RECAPTCHA_V2_INVISIBLE, AutoSolveRecaptchaV2Translator.getInstance());
        TRANSLATORS.put(RECAPTCHA_V3, AutoSolveRecaptchaV3Translator.getInstance());
        TRANSLATORS.put(HCAPTCHA_CHECKBOX, AutoSolveHCaptchaTranslator.getInstance());
        TRANSLATORS.put(HCAPTCHA_INVISIBLE, AutoSolveHCaptchaTranslator.getInstance());
    }

    private final AbstractAutoSolveManager manager;

    public AutoSolveSolver(String clientId, AutoSolveCredentials credentials) {
        this(new Manager(clientId), credentials);
    }

    public AutoSolveSolver(@NonNull AbstractAutoSolveManager manager, AutoSolveCredentials credentials) {
        this.manager = manager;
        loadAccount(credentials);
    }

    public void loadAccount(@Nullable AutoSolveCredentials credentials) {
        AutoSolveAccount account = null;
        if (credentials != null) {
            account = new AutoSolveAccount(credentials.getAccessToken(), credentials.getApiKey());
        }

        boolean success = true;
        try {
            manager.load(account);
        } catch (Exception e) {
            success = false;
        }

        if (!success && account != null) {
            try {
                manager.load(null);
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public CaptchaResponse fulfil(@NonNull CaptchaRequest request) throws AutoSolveException {
        CaptchaRequest.Translator<CaptchaTokenRequest> translator = TRANSLATORS.get(request.getCaptchaType());
        CaptchaTokenRequest tokenRequest = translator.translate(request);

        CaptchaToken captchaToken = manager.requestAndWaitForCaptchaToken(tokenRequest, TIMEOUT, TimeUnit.MILLISECONDS);

        return new CaptchaResponse.Builder().result(captchaToken.getToken()).request(request).build();
    }

    @Override
    public List<CaptchaType> getSupportedCaptchaTypes() {
        return Arrays.asList(
                CaptchaType.HCAPTCHA_CHECKBOX,
                CaptchaType.HCAPTCHA_INVISIBLE,
                RECAPTCHA_V2_CHECKBOX,
                CaptchaType.RECAPTCHA_V2_INVISIBLE,
                CaptchaType.RECAPTCHA_V3);
    }

    public static class Manager extends AbstractAutoSolveManager {

        public Manager(String clientId) {
            super(clientId);
        }

        @Override
        protected void onStatusChanged(AutoSolveStatus autoSolveStatus) {
        }

        @Override
        protected void onCaptchaTokenRequestCancelled(CaptchaTokenRequest captchaTokenRequest) {
        }

        @Override
        protected void onCaptchaTokenReceived(CaptchaToken captchaToken) {
        }

    }

}
