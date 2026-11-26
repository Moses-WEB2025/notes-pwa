package com.xiaomi.miai.api;

import com.xiaomi.miai.api.common.Required;

/* JADX INFO: loaded from: classes4.dex */
public class AuthHardwareAuth {

    public static class HardwareTokenResponse {

        @Required
        private long expiredInMillis;

        @Required
        private String fid;

        @Required
        private String token;

        public HardwareTokenResponse() {
        }

        public HardwareTokenResponse(String str, String str2, long j) {
            this.fid = str;
            this.token = str2;
            this.expiredInMillis = j;
        }

        @Required
        public HardwareTokenResponse setFid(String str) {
            this.fid = str;
            return this;
        }

        @Required
        public String getFid() {
            return this.fid;
        }

        @Required
        public HardwareTokenResponse setToken(String str) {
            this.token = str;
            return this;
        }

        @Required
        public String getToken() {
            return this.token;
        }

        @Required
        public HardwareTokenResponse setExpiredInMillis(long j) {
            this.expiredInMillis = j;
            return this;
        }

        @Required
        public long getExpiredInMillis() {
            return this.expiredInMillis;
        }
    }

    public static class NonceResponse {

        @Required
        private String fid;

        @Required
        private String nonce;

        @Required
        private String nonceCert;

        public NonceResponse() {
        }

        public NonceResponse(String str, String str2, String str3) {
            this.fid = str;
            this.nonce = str2;
            this.nonceCert = str3;
        }

        @Required
        public NonceResponse setFid(String str) {
            this.fid = str;
            return this;
        }

        @Required
        public String getFid() {
            return this.fid;
        }

        @Required
        public NonceResponse setNonce(String str) {
            this.nonce = str;
            return this;
        }

        @Required
        public String getNonce() {
            return this.nonce;
        }

        @Required
        public NonceResponse setNonceCert(String str) {
            this.nonceCert = str;
            return this;
        }

        @Required
        public String getNonceCert() {
            return this.nonceCert;
        }
    }
}
