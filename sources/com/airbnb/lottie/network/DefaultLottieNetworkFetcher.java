package com.airbnb.lottie.network;

import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class DefaultLottieNetworkFetcher implements LottieNetworkFetcher {
    @Override // com.airbnb.lottie.network.LottieNetworkFetcher
    public LottieFetchResult fetchSync(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
        httpURLConnection.connect();
        return new DefaultLottieFetchResult(httpURLConnection);
    }
}
