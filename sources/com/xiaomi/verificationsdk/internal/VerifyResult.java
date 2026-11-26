package com.xiaomi.verificationsdk.internal;

/* JADX INFO: loaded from: classes4.dex */
public class VerifyResult {
    private String scoreData;
    private String token;

    public String getToken() {
        return this.token;
    }

    public String getScoreData() {
        return this.scoreData;
    }

    private VerifyResult(Builder builder) {
        this.token = builder.token;
        this.scoreData = builder.scoreData;
    }

    public static class Builder {
        private String scoreData;
        private String token;

        public Builder token(String str) {
            this.token = str;
            return this;
        }

        public Builder scoreData(String str) {
            this.scoreData = str;
            return this;
        }

        public VerifyResult build() {
            return new VerifyResult(this);
        }
    }
}
