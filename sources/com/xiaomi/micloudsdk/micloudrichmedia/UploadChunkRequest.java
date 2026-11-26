package com.xiaomi.micloudsdk.micloudrichmedia;

import android.content.Context;
import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.miui.maml.elements.MusicLyricParser;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.micloudsdk.utils.MiCloudLog;
import com.xiaomi.micloudsdk.utils.RegexUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class UploadChunkRequest extends UploadRequest {
    private static final int BUFFER_SIZE = 5120;
    private static final String TAG = "UploadChunkRequest";
    private int mChunkLength;
    private String mCkeyHint;
    private boolean mIsLastChunk;
    private int mOffset;
    private final UploadEncryptInfo uploadEncryptInfo;

    public UploadChunkRequest(UploadEntity uploadEntity, boolean z, int i, int i2, Context context) {
        this(uploadEntity, z, i, i2, null, context);
    }

    public UploadChunkRequest(UploadEntity uploadEntity, boolean z, int i, int i2, String str, Context context) {
        this(uploadEntity, z, i, i2, str, null, context);
    }

    public UploadChunkRequest(UploadEntity uploadEntity, boolean z, int i, int i2, String str, UploadEncryptInfo uploadEncryptInfo, Context context) {
        super(uploadEntity);
        this.mIsLastChunk = z;
        this.mChunkLength = i;
        this.mCkeyHint = UUID.randomUUID().toString();
        this.mOffset = i2;
        this.mUrl = str;
        this.uploadEncryptInfo = uploadEncryptInfo;
        this.mContext = context;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected List<NameValuePair> getParams(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("type", CloudCoder.encodeString(str, this.mFile.getType(), "UTF-8")));
        if (this.uploadEncryptInfo == null) {
            arrayList.add(new BasicNameValuePair(RequestParameters.CKEYHINT, CloudCoder.encodeString(str, this.mCkeyHint, "ASCII")));
        }
        if (!this.mFile.isFirstChunk() || MiCloudRichMediaSupportedFileType.isMiXin2(this.mFile.getType())) {
            if (TextUtils.isEmpty(this.mFile.getTempId()) || TextUtils.isEmpty(this.mFile.getHostingServer())) {
                throw new IllegalArgumentException("The tempid or hosting server should not be null for the non first chunk or mixin2");
            }
            arrayList.add(new BasicNameValuePair("tmpid", CloudCoder.encodeString(str, this.mFile.getTempId(), "ASCII")));
            arrayList.add(new BasicNameValuePair("_hostingserver", this.mFile.getHostingServer()));
            arrayList.add(new BasicNameValuePair("offset", CloudCoder.encodeString(str, String.valueOf(this.mOffset), "UTF-8")));
        }
        if (this.mIsLastChunk) {
            if (this.uploadEncryptInfo != null) {
                arrayList.add(new BasicNameValuePair("encryptInfo", CloudCoder.encodeString(str, this.uploadEncryptInfo.toJsonString(), "UTF-8")));
                arrayList.add(new BasicNameValuePair(RequestParameters.BLOCKS_ORIGIN_SHA1, CloudCoder.encodeString(str, TextUtils.join(",", this.mFile.mBlockSha1Hex), "UTF-8")));
            }
            arrayList.add(new BasicNameValuePair(RequestParameters.ST, CloudCoder.encodeString(str, "2", "UTF-8")));
            arrayList.add(new BasicNameValuePair(RequestParameters.EXT, CloudCoder.encodeString(str, this.mFile.getExt(), "UTF-8")));
            arrayList.add(new BasicNameValuePair("digest", CloudCoder.encodeString(str, this.mFile.getHexDigest(), "UTF-8")));
            return arrayList;
        }
        arrayList.add(new BasicNameValuePair(RequestParameters.ST, CloudCoder.encodeString(str, "1", "UTF-8")));
        return arrayList;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected String getBaseUrl() {
        String baseUrl = super.getBaseUrl();
        if (MiCloudRichMediaSupportedFileType.isMiXin2(this.mFile.getType())) {
            return baseUrl + "/upload";
        }
        return !this.mFile.isFirstChunk() ? baseUrl + "/more" : baseUrl;
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.UploadRequest, com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected HttpURLConnection getConn(String str, String str2, int i) throws CloudServerException {
        HttpURLConnection conn = super.getConn(str, str2, i);
        if (conn != null) {
            conn.setRequestProperty("Connection", HttpHeaders.KEEP_ALIVE);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
        }
        return conn;
    }

    /* JADX WARN: Code duplicated, block: B:97:0x0128 A[PHI: r6
  0x0128: PHI (r6v13 ??) = (r6v8 ??), (r6v9 ??), (r6v10 ??), (r6v11 ??), (r6v12 ??), (r6v17 ??) binds: [B:96:0x0126, B:86:0x0113, B:76:0x0100, B:106:0x0146, B:66:0x00ed, B:30:0x00b0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x014b: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:110:0x014b */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.xiaomi.micloudsdk.micloudrichmedia.UploadChunkRequest] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v17, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    public JSONObject request(String str, ExtendedAuthToken extendedAuthToken, String str2) throws Throwable {
        DataOutputStream dataOutputStream;
        DataOutputStream dataOutputStream2;
        DataOutputStream dataOutputStream3 = null;
        try {
            try {
                str = getConn(getTargetUrl(String.format(getBaseUrl(), str), URLEncodedUtils.format(getParamsWithSignature(extendedAuthToken.security, str), "UTF-8")), str2, 0);
                if (str == 0) {
                    if (str != 0) {
                        str.disconnect();
                    }
                    return null;
                }
                try {
                    str.connect();
                    dataOutputStream = new DataOutputStream(str.getOutputStream());
                    try {
                        dataOutputStream.writeBytes("--*****\r\n");
                        dataOutputStream.writeBytes("Content-Disposition:form-data;name=\"uploadfile0\";filename=\"uploadfile0\"\r\n");
                        dataOutputStream.writeBytes(MusicLyricParser.CRLF);
                        byte[] data = this.mFile.getData(this.mChunkLength);
                        UploadEncryptInfo uploadEncryptInfo = this.uploadEncryptInfo;
                        if (uploadEncryptInfo != null) {
                            dataOutputStream.write(uploadEncryptInfo.getCipher().update(data));
                        } else {
                            dataOutputStream.write(CloudCoder.encodeStream(this.mCkeyHint, data));
                        }
                        dataOutputStream.writeBytes("\r\n--*****--\r\n");
                        dataOutputStream.flush();
                        int responseCode = str.getResponseCode();
                        if (responseCode == 200) {
                            JSONObject jSONObject = new JSONObject(connect(str, extendedAuthToken, str2, 0));
                            try {
                                dataOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (str != 0) {
                                str.disconnect();
                            }
                            return jSONObject;
                        }
                        if (CloudServerException.isMiCloudServerException(responseCode)) {
                            throw new CloudServerException(responseCode, (HttpURLConnection) str);
                        }
                        MiCloudRichMediaManager2.log(String.format("The responsed message is %s, code is %d", str.getResponseMessage(), Integer.valueOf(responseCode)));
                        try {
                            dataOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        if (str != 0) {
                            str.disconnect();
                        }
                        return null;
                    } catch (E2EEException e3) {
                        e = e3;
                        e.printStackTrace();
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (str != 0) {
                            str.disconnect();
                        }
                    } catch (IOException e5) {
                        e = e5;
                        MiCloudLog.w(TAG, RegexUtils.hideIP(e.toString()));
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        if (str != 0) {
                            str.disconnect();
                        }
                    } catch (BadPaddingException e7) {
                        e = e7;
                        e.printStackTrace();
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                        }
                        if (str != 0) {
                            str.disconnect();
                        }
                    } catch (IllegalBlockSizeException e9) {
                        e = e9;
                        e.printStackTrace();
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (IOException e10) {
                                e10.printStackTrace();
                            }
                        }
                        if (str != 0) {
                            str.disconnect();
                        }
                    } catch (JSONException e11) {
                        e = e11;
                        e.printStackTrace();
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (IOException e12) {
                                e12.printStackTrace();
                            }
                        }
                        if (str != 0) {
                            str.disconnect();
                        }
                    }
                } catch (E2EEException e13) {
                    e = e13;
                    dataOutputStream = null;
                } catch (IOException e14) {
                    e = e14;
                    dataOutputStream = null;
                } catch (BadPaddingException e15) {
                    e = e15;
                    dataOutputStream = null;
                } catch (IllegalBlockSizeException e16) {
                    e = e16;
                    dataOutputStream = null;
                } catch (JSONException e17) {
                    e = e17;
                    dataOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    if (dataOutputStream3 != null) {
                        try {
                            dataOutputStream3.close();
                        } catch (IOException e18) {
                            e18.printStackTrace();
                        }
                    }
                    if (str == 0) {
                        throw th;
                    }
                    str.disconnect();
                    throw th;
                }
            } catch (E2EEException e19) {
                e = e19;
                str = 0;
                dataOutputStream = null;
            } catch (IOException e20) {
                e = e20;
                str = 0;
                dataOutputStream = null;
            } catch (BadPaddingException e21) {
                e = e21;
                str = 0;
                dataOutputStream = null;
            } catch (IllegalBlockSizeException e22) {
                e = e22;
                str = 0;
                dataOutputStream = null;
            } catch (JSONException e23) {
                e = e23;
                str = 0;
                dataOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                str = 0;
            }
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream3 = dataOutputStream2;
        }
    }

    @Override // com.xiaomi.micloudsdk.micloudrichmedia.Request
    protected void sendDataInRedirect(HttpURLConnection httpURLConnection) throws BadPaddingException, IllegalBlockSizeException, E2EEException, IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.writeBytes("--*****\r\n");
        dataOutputStream.writeBytes("Content-Disposition:form-data;name=\"uploadfile0\";filename=\"uploadfile0\"\r\n");
        dataOutputStream.writeBytes(MusicLyricParser.CRLF);
        if (this.mFile.mOffset == this.mFile.getLength()) {
            this.mFile.mOffset -= this.mFile.getLength() % this.mChunkLength;
        } else {
            this.mFile.mOffset -= this.mChunkLength;
        }
        byte[] data = this.mFile.getData(this.mChunkLength);
        UploadEncryptInfo uploadEncryptInfo = this.uploadEncryptInfo;
        if (uploadEncryptInfo != null) {
            uploadEncryptInfo.regenerateCipher(this.mFile.mOffset);
            dataOutputStream.write(this.uploadEncryptInfo.getCipher().update(data));
        } else {
            dataOutputStream.write(CloudCoder.encodeStream(this.mCkeyHint, data));
        }
        dataOutputStream.writeBytes("\r\n--*****--\r\n");
        dataOutputStream.flush();
    }
}
