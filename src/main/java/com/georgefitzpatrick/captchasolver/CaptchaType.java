package com.georgefitzpatrick.captchasolver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaptchaType {

    NORMAL("Normal"),

    // hCaptcha
    HCAPTCHA_CHECKBOX("hCaptcha checkbox"),
    HCAPTCHA_INVISIBLE("hCaptcha invisible"),

    // reCAPTCHA
    RECAPTCHA_V2_CHECKBOX("reCAPTCHA v2 checkbox"),
    RECAPTCHA_V2_INVISIBLE("reCAPTCHA v2 invisible"),
    RECAPTCHA_V3("reCAPTCHA v3");

    private final String name;

}