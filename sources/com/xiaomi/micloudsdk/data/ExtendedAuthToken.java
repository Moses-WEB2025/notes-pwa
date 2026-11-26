package com.xiaomi.micloudsdk.data;

import android.text.TextUtils;
import com.xiaomi.micloudsdk.request.utils.CloudRequestHelper;
import com.xiaomi.micloudsdk.request.utils.HttpUtils;
import com.xiaomi.micloudsdk.utils.CryptCoder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/* JADX INFO: loaded from: classes4.dex */
public final class ExtendedAuthToken implements IAuthToken {
    private static final String SP = ",";
    public final String authToken;
    public final String security;

    private ExtendedAuthToken(String str, String str2) {
        this.authToken = str;
        this.security = str2;
    }

    public static ExtendedAuthToken build(String str, String str2) {
        return new ExtendedAuthToken(str, str2);
    }

    public static ExtendedAuthToken parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(",");
        if (strArrSplit.length != 2 || TextUtils.isEmpty(strArrSplit[0]) || TextUtils.isEmpty(strArrSplit[1])) {
            return null;
        }
        return new ExtendedAuthToken(strArrSplit[0], strArrSplit[1]);
    }

    public String toPlain() {
        return this.authToken + "," + this.security;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExtendedAuthToken extendedAuthToken = (ExtendedAuthToken) obj;
        return Objects.equals(this.authToken, extendedAuthToken.authToken) && Objects.equals(this.security, extendedAuthToken.security);
    }

    public int hashCode() {
        String str = this.authToken;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.security;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    @Override // com.xiaomi.micloudsdk.data.IAuthToken
    public IAuthToken.HttpRequestBuilder getBuilderForCloudRequest() {
        return new IAuthToken.HttpRequestBuilder() { // from class: com.xiaomi.micloudsdk.data.ExtendedAuthToken.1
            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public void addAdditionalHeaders(List<Header> list) {
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public void addParams(ArrayList<NameValuePair> arrayList) {
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public CryptCoder getCryptCoder(String str) {
                return CloudRequestHelper.getCryptCoder(str, ExtendedAuthToken.this.security);
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public void signParams(HttpUtils.HttpMethod httpMethod, String str, ArrayList<NameValuePair> arrayList) throws UnsupportedEncodingException {
                arrayList.add(new BasicNameValuePair("signature", HttpUtils.getSignature(httpMethod, str, arrayList, ExtendedAuthToken.this.security)));
            }

            @Override // com.xiaomi.micloudsdk.data.IAuthToken.HttpRequestBuilder
            public String getServiceToken() {
                return ExtendedAuthToken.this.authToken;
            }
        };
    }
}
