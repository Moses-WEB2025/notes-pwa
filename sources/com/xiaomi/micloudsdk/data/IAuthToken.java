package com.xiaomi.micloudsdk.data;

import com.xiaomi.micloudsdk.request.utils.HttpUtils;
import com.xiaomi.micloudsdk.utils.CryptCoder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

/* JADX INFO: loaded from: classes4.dex */
public interface IAuthToken {

    public interface HttpRequestBuilder {
        void addAdditionalHeaders(List<Header> list);

        void addParams(ArrayList<NameValuePair> arrayList);

        CryptCoder getCryptCoder(String str);

        String getServiceToken();

        void signParams(HttpUtils.HttpMethod httpMethod, String str, ArrayList<NameValuePair> arrayList) throws UnsupportedEncodingException;
    }

    HttpRequestBuilder getBuilderForCloudRequest();
}
