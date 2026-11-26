package com.xiaomi.miai.network;

import com.xiaomi.miai.config.ConfigKeys;
import com.xiaomi.miai.config.MiaiSdkConfig;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
class CommonRequestInterceptor implements Interceptor {
    private final MiaiSdkConfig config;

    CommonRequestInterceptor(MiaiSdkConfig miaiSdkConfig) {
        this.config = miaiSdkConfig;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builderNewBuilder = chain.request().newBuilder();
        builderNewBuilder.addHeader("Date", OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME));
        UserAgent userAgent = (UserAgent) this.config.getOrNull(ConfigKeys.USER_AGENT);
        if (userAgent != null) {
            builderNewBuilder.addHeader("User-Agent", userAgent.toString());
        }
        return chain.proceed(builderNewBuilder.build());
    }
}
