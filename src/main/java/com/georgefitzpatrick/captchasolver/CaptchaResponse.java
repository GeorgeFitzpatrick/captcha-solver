package com.georgefitzpatrick.captchasolver;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
public class CaptchaResponse {

    @NonNull
    @SerializedName("result")
    private final String result;

    @NonNull
    @SerializedName("createdAt")
    private final long createdAt = System.currentTimeMillis();

    @NonNull
    private final CaptchaRequest request;

    private CaptchaResponse(String result, @NonNull CaptchaRequest request) {
        this.result = result;
        this.request = request;
    }

    @NoArgsConstructor
    public static class Builder {

        private String result;
        private CaptchaRequest request;

        private static void require(boolean condition, String message) {
            if (!condition) {
                throw new IllegalStateException(message);
            }
        }

        @NonNull
        public Builder result(@NonNull String result) {
            this.result = result;
            return this;
        }

        @NonNull
        public Builder request(@NonNull CaptchaRequest request) {
            this.request = request;
            return this;
        }

        @NonNull
        public CaptchaResponse build() {
            require(result != null,
                    String.format("Invalid Result (%s)", result));
            require(request != null,
                    "Invalid Captcha Request");

            return new CaptchaResponse(result, request);
        }

    }

}