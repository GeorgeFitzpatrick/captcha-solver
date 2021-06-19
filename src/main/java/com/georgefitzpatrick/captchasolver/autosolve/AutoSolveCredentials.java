package com.georgefitzpatrick.captchasolver.autosolve;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
public class AutoSolveCredentials {

    @SerializedName("apiKey")
    private final String apiKey;

    @SerializedName("accessToken")
    private final String accessToken;

    private AutoSolveCredentials(String apiKey, String accessToken) {
        this.apiKey = apiKey;
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AutoSolveCredentials)) {
            return false;
        }

        AutoSolveCredentials credentials = (AutoSolveCredentials) obj;
        return credentials.getApiKey().equals(apiKey);
    }

    @NoArgsConstructor
    public static class Builder {

        private static final String API_KEY_PATTERN = "[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}";
        private static final String ACCESS_TOKEN_PATTERN = "[a-zA-Z0-9]{5}-[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}";

        private String apiKey;
        private String accessToken;

        @NonNull
        public Builder apiKey(@NonNull String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        @NonNull
        public Builder accessToken(@NonNull String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        @NonNull
        public AutoSolveCredentials build() {
            require(apiKey != null && apiKey.matches(API_KEY_PATTERN),
                    String.format("Invalid API Key (%s)", apiKey));
            require(accessToken != null && accessToken.matches(ACCESS_TOKEN_PATTERN),
                    String.format("Invalid Access Token (%s)", accessToken));

            return new AutoSolveCredentials(apiKey, accessToken);
        }

        private static void require(boolean condition, String message) {
            if (!condition) {
                throw new IllegalStateException(message);
            }
        }

    }

}
