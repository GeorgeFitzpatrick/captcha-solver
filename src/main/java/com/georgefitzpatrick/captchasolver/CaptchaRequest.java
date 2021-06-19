package com.georgefitzpatrick.captchasolver;

import com.georgefitzpatrick.http.HttpProxy;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class CaptchaRequest {

    @NonNull
    @SerializedName("id")
    private final String id = UUID.randomUUID().toString();

    @NonNull
    @SerializedName("captchaType")
    private final CaptchaType captchaType;

    @Nullable
    @SerializedName("url")
    private final String url;

    @Nullable
    @SerializedName("cookies")
    private final String cookies;

    @Nullable
    @SerializedName("proxy")
    private final HttpProxy proxy;

    @NonNull
    @SerializedName("params")
    private final Map<String, String> params;

    @NonNull
    @SerializedName("files")
    protected final Map<String, byte[]> files;

    private CaptchaRequest(CaptchaType captchaType, @Nullable String url, @Nullable String cookies, @Nullable HttpProxy proxy, @NonNull Map<String, String> params, Map<String, byte[]> files) {
        this.captchaType = captchaType;
        this.url = url;
        this.cookies = cookies;
        this.proxy = proxy;
        this.params = params;
        this.files = files;
    }

    public boolean isProxyRequired() {
        return proxy != null;
    }

    public Builder newBuilder() {
        return new Builder(captchaType, params, files).url(url).proxy(proxy).cookies(cookies);
    }

    public interface Translator<T> {

        @NonNull
        T translate(@NonNull CaptchaRequest request);

    }

    public static class Builder {

        protected final Map<String, String> params;
        protected final Map<String, byte[]> files;
        private final CaptchaType captchaType;
        private String url;
        private String cookies;
        private HttpProxy proxy;

        protected Builder(CaptchaType captchaType) {
            this(captchaType, new HashMap<>(), new HashMap<>());
        }

        protected Builder(@NonNull CaptchaType captchaType, @NonNull Map<String, String> params, Map<String, byte[]> files) {
            this.captchaType = captchaType;
            this.params = params;
            this.files = files;
        }

        protected static void require(boolean condition, String message) {
            if (!condition) {
                throw new IllegalStateException(message);
            }
        }

        @NonNull
        public final Builder url(@NonNull String url) {
            this.url = url;
            return this;
        }

        @NonNull
        public final Builder cookies(@Nullable String cookies) {
            this.cookies = cookies;
            return this;
        }

        @NonNull
        public final Builder proxy(@Nullable HttpProxy proxy) {
            this.proxy = proxy;
            return this;
        }

        @NonNull
        public CaptchaRequest build() {
            return new CaptchaRequest(captchaType, url, cookies, proxy, params, files);
        }

    }

}