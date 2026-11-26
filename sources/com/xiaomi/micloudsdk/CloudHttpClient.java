package com.xiaomi.micloudsdk;

import org.apache.http.client.HttpClient;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class CloudHttpClient extends com.xiaomi.micloudsdk.request.CloudHttpClient {
    private CloudHttpClient(HttpClient httpClient) {
        super(httpClient);
    }

    public static CloudHttpClient newInstance() {
        return new CloudHttpClient(initClient());
    }
}
