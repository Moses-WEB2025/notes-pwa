package com.xiaomi.miai.auth;

import com.xiaomi.miai.json.JsObject;
import com.xiaomi.miai.json.JsonFacade;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

/* JADX INFO: loaded from: classes4.dex */
public class GeneralToken {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String REFRESH_TOKEN = "refresh_token";
    private final String accessToken;
    private final long expiresIn;
    private final String refreshToken;

    public static GeneralToken parse(String str) {
        return parse(JsonFacade.parse(str).asObject());
    }

    public static GeneralToken parse(JsObject jsObject) {
        return new GeneralToken(jsObject.getString(ACCESS_TOKEN), jsObject.getString("refresh_token"), jsObject.getLong(EXPIRES_IN));
    }

    public static TokenRecord parseAndConvert(JsObject jsObject, String str, String str2, TemporalUnit temporalUnit) {
        return new TokenRecord(str, str2, JsonFacade.stringify(jsObject), Duration.of(parse(jsObject).expiresIn, temporalUnit));
    }

    public GeneralToken(String str, String str2, long j) {
        this.accessToken = str;
        this.refreshToken = str2;
        this.expiresIn = j;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }
}
