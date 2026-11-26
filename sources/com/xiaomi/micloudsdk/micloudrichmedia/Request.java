package com.xiaomi.micloudsdk.micloudrichmedia;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.e2ee.E2EEException;
import com.xiaomi.e2ee.E2EEManager;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.CloudRelocationUtils;
import com.xiaomi.micloudsdk.utils.CloudCoder;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.micloudsdk.utils.MiCloudLog;
import com.xiaomi.micloudsdk.utils.RegexUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
abstract class Request {
    public static final int HTTP_REQUEST_DELAY_MS = 5000;
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30000;
    private static final String TAG = "MiCloudSDKRequest";
    public static final int UPLOAD_CHUNK_SIZE = 4194304;
    protected static final String USER_FULL_URL = MiCloudConstants.URL.URL_RICH_MEDIA_BASE + "/mic/file/v2/user/full";
    protected String mUrl = null;
    protected Context mContext = null;
    private final Map<String, String> mExtParams = new HashMap();

    protected abstract String getHttpMethod();

    protected abstract List<NameValuePair> getParams(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException;

    protected void sendDataInRedirect(HttpURLConnection httpURLConnection) throws BadPaddingException, IllegalBlockSizeException, E2EEException, IOException {
    }

    Request() {
    }

    protected HttpURLConnection getConn(String str, String str2, int i) throws CloudServerException {
        HttpURLConnection httpURLConnection = null;
        if (TextUtils.isEmpty(str) || this.mContext == null) {
            return null;
        }
        if (CloudRelocationUtils.shouldUpdateHost()) {
            str = CloudRelocationUtils.updateRequestHost(str, false);
        }
        MiCloudRichMediaManager2.log("The connection url is:" + str);
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setReadTimeout(30000);
                httpURLConnection2.setConnectTimeout(30000);
                httpURLConnection2.setRequestMethod(getHttpMethod());
                if (!TextUtils.isEmpty(str2)) {
                    httpURLConnection2.setRequestProperty("Cookie", str2);
                }
                httpURLConnection2.setRequestProperty("User-Agent", com.xiaomi.micloudsdk.request.utils.Request.getUserAgent());
                if (i > 0) {
                    httpURLConnection2.setRequestProperty(MiCloudConstants.REQUEST.X_XIAOMI_REDIRECT_COUNT, i + "");
                }
                httpURLConnection2.setRequestProperty(MiCloudConstants.REQUEST.X_XIAOMI_SUPPORT_REDIRECT, "true");
                httpURLConnection2.setRequestProperty("X-SUPPORT-HTTPS", "true");
                addE2EEHeader(httpURLConnection2);
                return httpURLConnection2;
            } catch (MalformedURLException e) {
                e = e;
                httpURLConnection = httpURLConnection2;
                e.printStackTrace();
                return httpURLConnection;
            } catch (IOException e2) {
                e = e2;
                httpURLConnection = httpURLConnection2;
                MiCloudLog.w(TAG, RegexUtils.hideIP(e.toString()));
                return httpURLConnection;
            }
        } catch (MalformedURLException e3) {
            e = e3;
        } catch (IOException e4) {
            e = e4;
        }
    }

    private void addE2EEHeader(HttpURLConnection httpURLConnection) {
        if (!E2EEManager.getInstance().hasInitE2EE()) {
            MiCloudLog.i(TAG, "not add e2ee header");
        } else {
            httpURLConnection.setRequestProperty("X-E2EE", "1.0");
            httpURLConnection.setRequestProperty("X-E2EE-STATUS", String.valueOf(E2EEManager.getInstance().isE2EEOpen()));
        }
    }

    protected String getTargetUrl(String str, String str2) {
        return TextUtils.isEmpty(str2) ? str : String.format("%s?%s", str, str2);
    }

    protected String getBaseUrl() {
        return !TextUtils.isEmpty(this.mUrl) ? this.mUrl : USER_FULL_URL;
    }

    public Request addParam(String str, String str2) {
        this.mExtParams.put(str, str2);
        return this;
    }

    public List<NameValuePair> getParamsWithSignature(String str, String str2) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        List<NameValuePair> params = getParams(str);
        if (params == null) {
            params = new ArrayList<>();
        }
        if (!this.mExtParams.isEmpty()) {
            for (Map.Entry<String, String> entry : this.mExtParams.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), CloudCoder.encodeString(str, entry.getValue(), "UTF-8")));
            }
        }
        TreeMap treeMap = new TreeMap();
        for (NameValuePair nameValuePair : params) {
            treeMap.put(nameValuePair.getName(), nameValuePair.getValue());
        }
        params.add(new BasicNameValuePair("signature", CloudCoder.generateSignature(getHttpMethod(), String.format(getBaseUrl(), str2), treeMap, str)));
        return params;
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00b2 A[PHI: r8
  0x00b2: PHI (r8v11 java.net.HttpURLConnection) = 
  (r8v6 java.net.HttpURLConnection)
  (r8v7 java.net.HttpURLConnection)
  (r8v8 java.net.HttpURLConnection)
  (r8v10 java.net.HttpURLConnection)
  (r8v12 java.net.HttpURLConnection)
 binds: [B:53:0x00b0, B:49:0x00aa, B:44:0x00a2, B:58:0x00d2, B:35:0x0076] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.net.HttpURLConnection] */
    public JSONObject request(String str, ExtendedAuthToken extendedAuthToken, String str2) throws Throwable {
        HttpURLConnection conn;
        ?? r3 = 0;
        try {
            try {
                try {
                    conn = getConn(getTargetUrl(getBaseUrl(), URLEncodedUtils.format(getParamsWithSignature(extendedAuthToken.security, str), "UTF-8")), str2, 0);
                    try {
                        conn.connect();
                        int responseCode = conn.getResponseCode();
                        if (responseCode == 200) {
                            JSONObject jSONObject = new JSONObject(connect(conn, extendedAuthToken, str2, 0));
                            if (conn != null) {
                                conn.disconnect();
                            }
                            return jSONObject;
                        }
                        if (CloudServerException.isMiCloudServerException(responseCode)) {
                            throw new CloudServerException(responseCode, conn);
                        }
                        MiCloudRichMediaManager2.log(String.format("The responsed message is %s, code is %d", conn.getResponseMessage(), Integer.valueOf(responseCode)));
                        if (conn != null) {
                            conn.disconnect();
                            return null;
                        }
                        return null;
                    } catch (E2EEException e) {
                        e = e;
                        e.printStackTrace();
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (IOException e2) {
                        e = e2;
                        MiCloudLog.w(TAG, "failed to request http for " + RegexUtils.hideIP(e.toString()));
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (IllegalArgumentException e3) {
                        e = e3;
                        Log.d("Request", "Bad base 64 exception: " + getBaseUrl() + " response:");
                        throw e;
                    } catch (BadPaddingException e4) {
                        e = e4;
                        e.printStackTrace();
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (IllegalBlockSizeException e5) {
                        e = e5;
                        e.printStackTrace();
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (JSONException e6) {
                        e = e6;
                        e.printStackTrace();
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }
                } catch (E2EEException e7) {
                    e = e7;
                    conn = null;
                } catch (IOException e8) {
                    e = e8;
                    conn = null;
                } catch (IllegalArgumentException e9) {
                    e = e9;
                } catch (BadPaddingException e10) {
                    e = e10;
                    conn = null;
                } catch (IllegalBlockSizeException e11) {
                    e = e11;
                    conn = null;
                } catch (JSONException e12) {
                    e = e12;
                    conn = null;
                }
            } catch (Throwable th) {
                th = th;
                if (r3 != 0) {
                    r3.disconnect();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r3 = str;
        }
    }

    protected String connect(HttpURLConnection httpURLConnection, ExtendedAuthToken extendedAuthToken, String str, int i) throws BadPaddingException, CloudServerException, IllegalBlockSizeException, E2EEException, IOException {
        if (i > 0) {
            httpURLConnection.connect();
            sendDataInRedirect(httpURLConnection);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                if (CloudServerException.isMiCloudServerException(responseCode)) {
                    throw new CloudServerException(responseCode, httpURLConnection);
                }
                MiCloudRichMediaManager2.log(String.format("The responsed message is %s, code is %d", httpURLConnection.getResponseMessage(), Integer.valueOf(responseCode)));
                return null;
            }
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        String string = sb.toString();
        if (TextUtils.isEmpty(string)) {
            throw new IOException("empty response is invalid.");
        }
        try {
            String strDecodeString = CloudCoder.decodeString(extendedAuthToken.security, string, "UTF-8");
            if (!TextUtils.isEmpty(strDecodeString)) {
                String strCheckRedirect = CloudRelocationUtils.checkRedirect(strDecodeString, i);
                if (!TextUtils.isEmpty(strCheckRedirect)) {
                    int i2 = i + 1;
                    return connect(getConn(strCheckRedirect, str, i2), extendedAuthToken, str, i2);
                }
            }
            return strDecodeString;
        } catch (IllegalArgumentException e) {
            Log.d("Request", "IllegalArgumentException Response:" + string + " url:" + httpURLConnection.getURL());
            throw e;
        }
    }

    /* JADX WARN: Code duplicated, block: B:49:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v16, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v9, types: [java.lang.String] */
    protected ByteArrayOutputStream getTemporaryDownloadData(Context context, String str) throws Throwable {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        IOException e;
        FileInputStream fileInputStream;
        FileNotFoundException e2;
        File file = new File(context.getCacheDir() + File.separator + str);
        if (!file.exists()) {
            return null;
        }
        ?? r5 = String.format("getTemporaryDownloadData:The temporary downloaded file %s exist", file.getName());
        MiCloudRichMediaManager2.log(r5);
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, i);
                        }
                        byteArrayOutputStream.flush();
                    } catch (FileNotFoundException e3) {
                        e2 = e3;
                        e2.printStackTrace();
                        if (fileInputStream != null) {
                        }
                        file.delete();
                        return byteArrayOutputStream;
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                        }
                        file.delete();
                        return byteArrayOutputStream;
                    }
                } catch (FileNotFoundException e5) {
                    byteArrayOutputStream = null;
                    e2 = e5;
                } catch (IOException e6) {
                    byteArrayOutputStream = null;
                    e = e6;
                }
            } catch (FileNotFoundException e7) {
                byteArrayOutputStream = null;
                e2 = e7;
                fileInputStream = null;
            } catch (IOException e8) {
                byteArrayOutputStream = null;
                e = e8;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                r5 = 0;
                if (r5 != 0) {
                    try {
                        r5.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                file.delete();
                throw th;
            }
            try {
                fileInputStream.close();
            } catch (IOException e10) {
                e10.printStackTrace();
            }
            file.delete();
            return byteArrayOutputStream;
        } catch (Throwable th3) {
            th = th3;
            if (r5 != 0) {
                r5.close();
            }
            file.delete();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x004c A[Catch: IOException -> 0x0059, PHI: r0
  0x004c: PHI (r0v7 java.io.FileOutputStream) = (r0v5 java.io.FileOutputStream), (r0v8 java.io.FileOutputStream) binds: [B:16:0x004a, B:22:0x0056] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #0 {IOException -> 0x0059, blocks: (B:5:0x0038, B:17:0x004c), top: B:34:0x0001 }] */
    protected void saveTemporaryDownloadedData(Context context, ByteArrayOutputStream byteArrayOutputStream, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        IOException e;
        FileNotFoundException e2;
        try {
            try {
                try {
                    MiCloudRichMediaManager2.log(String.format("Save temporary downloaded data to file %s", str));
                    fileOutputStream = new FileOutputStream(context.getCacheDir() + File.separator + str);
                    try {
                        fileOutputStream.write(byteArrayOutputStream.toByteArray());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (FileNotFoundException e3) {
                        e2 = e3;
                        e2.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    }
                } catch (FileNotFoundException e5) {
                    fileOutputStream = null;
                    e2 = e5;
                } catch (IOException e6) {
                    fileOutputStream = null;
                    e = e6;
                } catch (Throwable th) {
                    fileOutputStream = null;
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
