package com.xiaomi.micloudsdk.micloudrichmedia;

import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import java.net.HttpURLConnection;

/* JADX INFO: loaded from: classes4.dex */
abstract class UploadRequest extends Request {
    protected static final String BOUNDARY = "*****";
    protected static final String LINE_END = "\r\n";
    protected static final String TWO_HYPHENS = "--";
    protected UploadEntity mFile;

    UploadRequest(UploadEntity uploadEntity) {
        if (uploadEntity == null) {
            throw new IllegalArgumentException("The file should not be null");
        }
        this.mFile = uploadEntity;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getHttpMethod() {
        return PassportSimpleRequest.HTTP_METHOD_POST;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected HttpURLConnection getConn(String str, String str2, int i) throws CloudServerException {
        HttpURLConnection conn = super.getConn(str, str2, i);
        if (conn != null) {
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
        }
        return conn;
    }
}
