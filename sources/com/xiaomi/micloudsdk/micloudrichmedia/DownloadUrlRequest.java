package com.xiaomi.micloudsdk.micloudrichmedia;

import android.content.Context;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class DownloadUrlRequest extends Request {
    protected String mFileId;
    protected String mType;

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public /* bridge */ /* synthetic */ Request addParam(String str, String str2) {
        return super.addParam(str, str2);
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public /* bridge */ /* synthetic */ List getParamsWithSignature(String str, String str2) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return super.getParamsWithSignature(str, str2);
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public /* bridge */ /* synthetic */ JSONObject request(String str, ExtendedAuthToken extendedAuthToken, String str2) throws CloudServerException {
        return super.request(str, extendedAuthToken, str2);
    }

    public DownloadUrlRequest(String str, String str2, Context context) {
        this(str, str2, null, context);
    }

    public DownloadUrlRequest(String str, String str2, String str3, Context context) {
        this.mFileId = str;
        this.mType = str2;
        this.mUrl = str3;
        this.mContext = context;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected List<NameValuePair> getParams(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("type", CloudCoder.encodeString(str, this.mType, "UTF-8")));
        if (MiCloudRichMediaSupportedFileType.isMiXin2(this.mType)) {
            arrayList.add(new BasicNameValuePair("shareId", CloudCoder.encodeString(str, this.mFileId, "UTF-8")));
            return arrayList;
        }
        arrayList.add(new BasicNameValuePair("fileId", CloudCoder.encodeString(str, this.mFileId, "UTF-8")));
        return arrayList;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getHttpMethod() {
        return PassportSimpleRequest.HTTP_METHOD_GET;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected HttpURLConnection getConn(String str, String str2, int i) throws CloudServerException {
        HttpURLConnection conn = super.getConn(str, str2, i);
        if (conn != null) {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }
        return conn;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getBaseUrl() {
        if (MiCloudRichMediaSupportedFileType.isMiXin2(this.mType)) {
            return super.getBaseUrl() + "/dlrequest";
        }
        return super.getBaseUrl();
    }
}
