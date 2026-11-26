package com.xiaomi.micloudsdk.micloudrichmedia;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.RichmediaServerFailureException;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class DownloadRequest extends Request {
    private static final String CONN_RANGE_PROPERTY = "RANGE";
    private static final String CONN_RANGE_VALUE_FORMAT = "bytes=%d-";
    private String mBaseUrl;
    private String mCkey;
    private String mFileSha1;
    private boolean mNeedCheckSha1;
    private int mRetryTimes;

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected List<NameValuePair> getParams(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return null;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public List<NameValuePair> getParamsWithSignature(String str, String str2) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        return null;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public /* bridge */ /* synthetic */ Request addParam(String str, String str2) {
        return super.addParam(str, str2);
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public /* bridge */ /* synthetic */ JSONObject request(String str, ExtendedAuthToken extendedAuthToken, String str2) throws CloudServerException {
        return super.request(str, extendedAuthToken, str2);
    }

    public DownloadRequest(String str, String str2, String str3, Context context) {
        this(str, str2, str3, true, context);
    }

    public DownloadRequest(String str, String str2, String str3, boolean z, Context context) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("The download requset parameters should not be null");
        }
        this.mBaseUrl = str;
        this.mCkey = str2;
        this.mFileSha1 = str3;
        this.mRetryTimes = 0;
        this.mNeedCheckSha1 = z;
        this.mContext = context;
    }

    public byte[] download(Context context, String str, ExtendedAuthToken extendedAuthToken, String str2) throws BadPaddingException, CloudServerException, DecoderException, IllegalBlockSizeException, IOException, RichmediaServerFailureException {
        return download(context, str, extendedAuthToken, str2, true);
    }

    /* JADX WARN: Code duplicated, block: B:107:0x0143 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x0139 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:114:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:88:0x0149 A[Catch: IOException -> 0x0152, TryCatch #6 {IOException -> 0x0152, blocks: (B:86:0x0143, B:88:0x0149, B:89:0x014e), top: B:107:0x0143 }] */
    /* JADX WARN: Code duplicated, block: B:94:0x0158  */
    public byte[] download(Context context, String str, ExtendedAuthToken extendedAuthToken, String str2, boolean z) throws Throwable {
        ByteArrayOutputStream temporaryDownloadData;
        HttpURLConnection conn;
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            conn = getConn(getBaseUrl(), str2, 0);
            if (z) {
                try {
                    temporaryDownloadData = getTemporaryDownloadData(context, this.mFileSha1);
                } catch (Throwable th) {
                    th = th;
                    temporaryDownloadData = null;
                    if (0 != 0) {
                        try {
                            inputStream2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (temporaryDownloadData != null) {
                        try {
                            if (temporaryDownloadData.size() > 0) {
                                saveTemporaryDownloadedData(context, temporaryDownloadData, this.mFileSha1);
                            }
                            temporaryDownloadData.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                        throw th;
                    }
                    throw th;
                }
            } else {
                temporaryDownloadData = null;
            }
            try {
                if (temporaryDownloadData != null) {
                    conn.addRequestProperty(CONN_RANGE_PROPERTY, String.format(CONN_RANGE_VALUE_FORMAT, Integer.valueOf(temporaryDownloadData.size())));
                } else {
                    temporaryDownloadData = new ByteArrayOutputStream();
                }
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode != 200 && (temporaryDownloadData.size() <= 0 || responseCode != 206)) {
                    if (CloudServerException.isMiCloudServerException(responseCode)) {
                        throw new CloudServerException(responseCode, conn);
                    }
                    MiCloudRichMediaManager2.log(String.format("download:The responsed message is %s, code is %d", conn.getResponseMessage(), Integer.valueOf(responseCode)));
                    throw new RichmediaServerFailureException(conn.getResponseMessage());
                }
                if (this.mCkey != null) {
                    inputStream = new CipherInputStream(conn.getInputStream(), CloudCoder.newRC4Cipher(Hex.decodeHex(this.mCkey.toCharArray()), 2));
                } else {
                    inputStream = conn.getInputStream();
                }
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    temporaryDownloadData.write(bArr, 0, i);
                }
                byte[] byteArray = temporaryDownloadData.toByteArray();
                temporaryDownloadData.reset();
                if (this.mNeedCheckSha1 && !TextUtils.equals(CloudCoder.getDataSha1Digest(byteArray), this.mFileSha1)) {
                    int i2 = this.mRetryTimes;
                    if (i2 < 3) {
                        int i3 = i2 + 1;
                        this.mRetryTimes = i3;
                        MiCloudRichMediaManager2.log(String.format("download:Retry %s time to download file because of mismatch sha1", Integer.valueOf(i3)));
                        byte[] bArrDownload = download(context, str, extendedAuthToken, str2);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (temporaryDownloadData != null) {
                            try {
                                if (temporaryDownloadData.size() > 0) {
                                    saveTemporaryDownloadedData(context, temporaryDownloadData, this.mFileSha1);
                                }
                                temporaryDownloadData.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                        return bArrDownload;
                    }
                    throw new RichmediaServerFailureException("The download data sha1 is not consistant with server sha1");
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (temporaryDownloadData != null) {
                    try {
                        if (temporaryDownloadData.size() > 0) {
                            saveTemporaryDownloadedData(context, temporaryDownloadData, this.mFileSha1);
                        }
                        temporaryDownloadData.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
                return byteArray;
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                    inputStream2.close();
                }
                if (temporaryDownloadData != null) {
                    if (temporaryDownloadData.size() > 0) {
                        saveTemporaryDownloadedData(context, temporaryDownloadData, this.mFileSha1);
                    }
                    temporaryDownloadData.close();
                }
                if (conn != null) {
                    conn.disconnect();
                    throw th;
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            temporaryDownloadData = null;
            conn = null;
        }
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getHttpMethod() {
        return PassportSimpleRequest.HTTP_METHOD_GET;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getBaseUrl() {
        return this.mBaseUrl;
    }
}
