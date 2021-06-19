package com.georgefitzpatrick.captchasolver.capmonster;

import com.georgefitzpatrick.captchasolver.twocaptcha.TwoCaptchaSolver;

public class CapMonsterSolver extends TwoCaptchaSolver {

    private static final String API_DOMAIN = "api.capmonster.cloud";

    public CapMonsterSolver(String apiKey) {
        super(API_DOMAIN, apiKey);
    }

}
