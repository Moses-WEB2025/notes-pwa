package com.xiaomi.phonenum.http;

import java.util.List;
import java.util.Map;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public class Response {
    public final String body;
    public final int code;
    public final Map<String, List<String>> headers;
    public final String location;
    public final String setCookie;
    public long time;

    public Response(Builder builder) {
        this.code = builder.code;
        this.body = builder.body;
        this.headers = builder.headers;
        this.setCookie = builder.setCookie;
        this.location = builder.location;
        this.time = builder.time;
    }

    public String toString() {
        return "{code:" + this.code + ", body:" + this.body + StringSubstitutor.DEFAULT_VAR_END;
    }

    public static class Builder {
        String body;
        int code;
        Map<String, List<String>> headers;
        String location;
        String setCookie;
        long time;

        public Builder() {
            this.time = 0L;
        }

        public Builder(Response response) {
            this.time = 0L;
            this.code = response.code;
            this.body = response.body;
            this.headers = response.headers;
            this.setCookie = response.setCookie;
            this.location = response.location;
            this.time = response.time;
        }

        public Builder headers(Map<String, List<String>> map) {
            this.headers = map;
            return this;
        }

        public Builder body(String str) {
            this.body = str;
            return this;
        }

        public Builder code(int i) {
            this.code = i;
            return this;
        }

        public Builder setCookie(String str) {
            this.setCookie = str;
            return this;
        }

        public Builder location(String str) {
            this.location = str;
            return this;
        }

        public Builder time(long j) {
            this.time = j;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
