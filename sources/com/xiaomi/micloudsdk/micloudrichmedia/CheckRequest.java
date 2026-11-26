package com.xiaomi.micloudsdk.micloudrichmedia;

import android.content.Context;
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

/* JADX INFO: loaded from: classes4.dex */
class CheckRequest extends UploadRequest {
    public CheckRequest(UploadEntity uploadEntity, Context context) {
        this(uploadEntity, null, context);
    }

    public CheckRequest(UploadEntity uploadEntity, String str, Context context) {
        super(uploadEntity);
        this.mUrl = str;
        this.mContext = context;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadRequest, com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected HttpURLConnection getConn(String str, String str2, int i) throws CloudServerException {
        HttpURLConnection conn = super.getConn(str, str2, i);
        if (conn != null) {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }
        return conn;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected List<NameValuePair> getParams(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("type", CloudCoder.encodeString(str, this.mFile.getType(), "UTF-8")));
        arrayList.add(new BasicNameValuePair("digest", CloudCoder.encodeString(str, this.mFile.getHexDigest(), "UTF-8")));
        if (MiCloudRichMediaSupportedFileType.isMiXin2(this.mFile.getType())) {
            arrayList.add(new BasicNameValuePair(RequestParameters.EXT, CloudCoder.encodeString(str, this.mFile.getExt(), "UTF-8")));
        }
        return arrayList;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getBaseUrl() {
        if (MiCloudRichMediaSupportedFileType.isMiXin2(this.mFile.getType())) {
            return super.getBaseUrl() + "/ulrequest";
        }
        return super.getBaseUrl() + "/digest";
    }
}
