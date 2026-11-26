package com.xiaomi.micloudsdk.data;

import android.text.TextUtils;
import com.xiaomi.micloudsdk.exception.CipherException;
import com.xiaomi.micloudsdk.request.utils.HttpUtils;
import com.xiaomi.micloudsdk.utils.CryptCoder;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: classes4.dex */
public class AccessToken implements IAuthToken {
    private static final String SP = ",";
    private final String accessToken;
    private final String clientId;

    private AccessToken(String str, String str2) {
        this.accessToken = str;
        this.clientId = str2;
    }

    public static AccessToken build(String str, String str2) {
        return new AccessToken(str, str2);
    }

    public static AccessToken parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        if (strArrSplit.length != 2 || TextUtils.isEmpty(strArrSplit[0]) || TextUtils.isEmpty(strArrSplit[1])) {
            return null;
        }
        return new AccessToken(strArrSplit[0], strArrSplit[1]);
    }

    public String toPlain() {
        return this.accessToken + "," + this.clientId;
    }

    @Override // com.xiaomi.micloudsdk.data.IAuthToken
    public IAuthToken.HttpRequestBuilder getBuilderForCloudRequest() {
        return new IAuthToken.HttpRequestBuilder() { // from class: com.xiaomi.micloudsdk.data.AccessToken.1
            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public String getServiceToken() {
                return null;
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public void signParams(HttpUtils.HttpMethod httpMethod, String str, ArrayList<NameValuePair> arrayList) {
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public CryptCoder getCryptCoder(String str) {
                return new CryptCoder() { // from class: com.xiaomi.micloudsdk.data.AccessToken.1.1
                    @Override // com.xiaomi.micloudsdk.utils.CryptCoder
                    public String decrypt(String str2) throws CipherException {
                        return str2;
                    }

                    @Override // com.xiaomi.micloudsdk.utils.CryptCoder
                    public String encrypt(String str2) throws CipherException {
                        return str2;
                    }
                };
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public void addParams(ArrayList<NameValuePair> arrayList) {
                arrayList.add(new BasicNameValuePair("clientId", AccessToken.this.clientId));
                arrayList.add(new BasicNameValuePair(Constants.VERIFY_SUCESS_TOKEN, AccessToken.this.accessToken));
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public void addAdditionalHeaders(List<Header> list) {
                list.add(new BasicHeader("Origin", "www.mi.com"));
            }
        };
    }
}
