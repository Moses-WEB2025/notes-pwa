package com.xiaomi.accountsdk.guestaccount;

import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public interface GuestAccountHttpRequester {
    public static final String HEADER_KEY_EXTENSION_PRAGMA = "extension-pragma";

    Response get(String str) throws IOException;

    Response post(String str, Map<String, String> map, Map<String, String> map2) throws IOException;

    public static class Response {
        public final String body;
        public final int code;
        public final Map<String, String> cookies;
        public final String headerExtensionPragma;

        public Response(int i, String str, String str2, Map<String, String> map) {
            this.code = i;
            this.body = str;
            this.headerExtensionPragma = str2;
            this.cookies = map;
        }
    }
}
