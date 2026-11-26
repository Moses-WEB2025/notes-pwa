package com.xiaomi.phonenum.obtain;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.phonenum.Constant;
import com.xiaomi.phonenum.bean.HttpError;
import com.xiaomi.phonenum.http.HttpClient;
import com.xiaomi.phonenum.http.HttpClientConfig;
import com.xiaomi.phonenum.http.Request;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.http.UrlConnHttpFactory;
import com.xiaomi.phonenum.utils.MapUtil;
import com.xiaomi.phonenum.utils.RSAEncryptUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class EncryptHttpClient implements HttpClient {
    private static final String TAG = "EncryptHttpClient";
    private RSAEncryptUtil RSAEncryptUtil;
    private HttpClient client;

    EncryptHttpClient(HttpClient httpClient) {
        this.client = httpClient;
        try {
            this.RSAEncryptUtil = new RSAEncryptUtil();
        } catch (RSAEncryptUtil.EncryptException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.phonenum.http.HttpClient
    public Response excute(Request request) throws IOException {
        HashMap map;
        if (!request.url.startsWith(Constant.BASE_URL)) {
            return this.client.excute(request);
        }
        if (this.RSAEncryptUtil == null) {
            return HttpError.ENCRYPT.result();
        }
        Request requestBuild = null;
        try {
            URI uri = request.uri;
            ArrayList arrayList = new ArrayList();
            arrayList.add(MapUtil.joinToQuery(request.formBody));
            arrayList.add(uri.getQuery());
            String strJoin = MapUtil.join(arrayList, "&");
            if (TextUtils.isEmpty(strJoin)) {
                map = null;
            } else {
                RSAEncryptUtil.EncryptResult encryptResultEncrypt = this.RSAEncryptUtil.encrypt(strJoin);
                map = new HashMap();
                map.put("params", encryptResultEncrypt.content);
                map.put("secretKey", encryptResultEncrypt.encryptedKey);
            }
            requestBuild = new Request.Builder().url(new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString()).headers(request.headers).formBody(map).build();
        } catch (RSAEncryptUtil.EncryptException e) {
            AccountLogger.log(TAG, "encryptedRequest Exception" + request, e);
        } catch (URISyntaxException unused) {
            throw new IllegalArgumentException("unexpected newQuery: " + request.url);
        }
        if (requestBuild == null) {
            return HttpError.ENCRYPT.result();
        }
        Response responseExcute = this.client.excute(requestBuild);
        if (responseExcute == null) {
            return HttpError.DECRYPT.result();
        }
        if (responseExcute.body == null) {
            return responseExcute;
        }
        try {
            return new Response.Builder(responseExcute).body(this.RSAEncryptUtil.aesDecrypt(responseExcute.body)).build();
        } catch (RSAEncryptUtil.EncryptException e2) {
            AccountLogger.log(TAG, "decryptedResponse Exception" + responseExcute, e2);
            return HttpError.DECRYPT.result();
        }
    }

    public static class HttpFactory extends UrlConnHttpFactory {
        public HttpFactory(Context context) {
            super(context);
        }

        @Override // com.xiaomi.phonenum.http.UrlConnHttpFactory, com.xiaomi.phonenum.http.HttpFactory
        public HttpClient createHttpClient(HttpClientConfig httpClientConfig) {
            return new EncryptHttpClient(super.createHttpClient(httpClientConfig));
        }
    }
}
