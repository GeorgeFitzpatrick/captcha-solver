package com.georgefitzpatrick.captchasolver;

import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractCaptchaSolver {

    public final CaptchaResponse solve(@NonNull CaptchaRequest request) throws Exception {
        CaptchaType type = request.getCaptchaType();

        boolean typeSupported = getSupportedCaptchaTypes().contains(type);
        if (!typeSupported) {
            String message = String.format("Captcha Type (%s) Not Supported", type.getName());
            throw new IllegalStateException(message);
        }

        return fulfil(request);
    }

    protected abstract CaptchaResponse fulfil(@NonNull CaptchaRequest request) throws Exception;

    protected List<CaptchaType> getSupportedCaptchaTypes() {
        return Arrays.asList(CaptchaType.values());
    }

}