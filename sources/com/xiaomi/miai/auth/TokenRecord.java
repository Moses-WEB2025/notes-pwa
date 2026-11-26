package com.xiaomi.miai.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

/* JADX INFO: loaded from: classes4.dex */
public class TokenRecord {
    private final String authType;
    private final LocalDateTime expiresAt;
    private final Duration expiresIn;
    private final String identity;
    private final String tokenValue;

    public TokenRecord(String str, String str2, String str3, Duration duration) {
        this(str, str2, str3, LocalDateTime.now().plus((TemporalAmount) duration), duration);
    }

    public TokenRecord(String str, String str2, String str3, LocalDateTime localDateTime, Duration duration) {
        this.authType = str;
        this.identity = str2;
        this.tokenValue = str3;
        this.expiresAt = localDateTime;
        this.expiresIn = duration;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public Duration getExpiresIn() {
        return this.expiresIn;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public String getIdentity() {
        return this.identity;
    }

    public String getAuthType() {
        return this.authType;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}
