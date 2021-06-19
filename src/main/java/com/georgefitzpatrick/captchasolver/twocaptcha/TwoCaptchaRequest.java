package com.georgefitzpatrick.captchasolver.twocaptcha;

import com.georgefitzpatrick.http.HttpProxy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class TwoCaptchaRequest {

    private final Map<String, String> params = new HashMap<>();
    private final Map<String, byte[]> files = new HashMap<>();

    @Setter
    private String id;

    @Setter
    private String code;

    public void setProxy(@Nullable HttpProxy proxy) {
        if (proxy == null) {
            params.remove("proxy");
            params.remove("proxytype");
        } else {
            params.put("proxy", proxy.toUrl());
            params.put("proxytype", "HTTPS");
        }
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>(this.params);

        if (!params.containsKey("method")) {
            if (params.containsKey("body")) {
                params.put("method", "base64");
            } else {
                params.put("method", "post");
            }
        }

        return params;
    }

    public Map<String, byte[]> getFiles() {
        return new HashMap<>(files);
    }

}