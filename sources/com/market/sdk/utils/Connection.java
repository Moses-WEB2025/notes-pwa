package com.market.sdk.utils;

import android.text.TextUtils;
import ch.qos.logback.classic.spi.CallerData;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.TreeMap;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class Connection {
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int GPRS_READ_TIMEOUT = 30000;
    protected static final String PROTOCOL_HTTP = "http";
    protected static final String PROTOCOL_HTTPS = "https";
    private static final String TAG = "MarketConnection";
    private static final int WIFI_READ_TIMEOUT = 10000;
    protected boolean mIsBackground;
    protected boolean mNeedBaseParameter;
    protected boolean mNeedHosted;
    protected boolean mNeedId;
    protected boolean mNeedSessionID;
    protected Parameter mParameter;
    protected JSONObject mResponse;
    protected String mString;
    protected URL mUrl;
    protected boolean mUseGet;

    public enum NetworkError {
        OK,
        URL_ERROR,
        NETWORK_ERROR,
        AUTH_ERROR,
        CLIENT_ERROR,
        SERVER_ERROR,
        RESULT_ERROR,
        UNKNOWN_ERROR
    }

    protected HttpURLConnection onConnectionCreated(HttpURLConnection httpURLConnection) throws ConnectionException {
        return httpURLConnection;
    }

    protected Parameter onQueryCreated(Parameter parameter) throws ConnectionException {
        return parameter;
    }

    protected String onURLCreated(String str, Parameter parameter) throws ConnectionException {
        return str;
    }

    public Connection(String str) {
        this(str, false);
    }

    public Connection(String str, String str2) {
        this(connect(str, str2), false);
    }

    public Connection(String str, boolean z) {
        URL url;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            Log.e(TAG, "URL error: " + e);
            url = null;
        }
        init(url);
        this.mIsBackground = z;
    }

    public static String connect(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        if (str.charAt(str.length() - 1) == '/') {
            str = str.substring(0, str.length() - 1);
        }
        if (str2.charAt(0) == '/') {
            str2 = str2.substring(1);
        }
        return str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2;
    }

    private void init(URL url) {
        this.mNeedBaseParameter = true;
        this.mUseGet = false;
        this.mNeedHosted = true;
        this.mNeedId = true;
        this.mNeedSessionID = true;
        if (checkURL(url)) {
            this.mUrl = url;
        }
    }

    public JSONObject getResponse() {
        return this.mResponse;
    }

    public String getStringResponse() {
        return this.mString;
    }

    public Parameter getParameter() {
        return this.mParameter;
    }

    public void setUseGet(boolean z) {
        this.mUseGet = z;
    }

    public void setNeedBaseParameter(boolean z) {
        this.mNeedBaseParameter = z;
    }

    public void setNeedHosted(boolean z) {
        this.mNeedHosted = z;
    }

    public void setNeedId(boolean z) {
        this.mNeedId = z;
    }

    public void setNeedSessionId(boolean z) {
        this.mNeedSessionID = z;
    }

    public NetworkError requestJSON() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        NetworkError networkErrorRequest = request(new MemoryResetableOutputStream(byteArrayOutputStream));
        try {
            if (networkErrorRequest == NetworkError.OK) {
                this.mResponse = new JSONObject(byteArrayOutputStream.toString());
            } else {
                Log.e(TAG, "Connection failed : " + networkErrorRequest);
            }
            return networkErrorRequest;
        } catch (JSONException e) {
            Log.e(TAG, "JSON error: " + e);
            return NetworkError.RESULT_ERROR;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public NetworkError requestString() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        NetworkError networkErrorRequest = request(new MemoryResetableOutputStream(byteArrayOutputStream));
        if (networkErrorRequest == NetworkError.OK) {
            this.mString = byteArrayOutputStream.toString();
        } else {
            Log.e(TAG, "Connection failed : " + networkErrorRequest);
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused) {
        }
        return networkErrorRequest;
    }

    public NetworkError requestFile(File file) throws Throwable {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        try {
            FileResetableOutputStream fileResetableOutputStream = new FileResetableOutputStream(file);
            NetworkError networkErrorRequest = request(fileResetableOutputStream);
            try {
                fileResetableOutputStream.close();
                if (networkErrorRequest != NetworkError.OK) {
                    Log.e(TAG, "Connection failed : " + networkErrorRequest);
                    file.delete();
                }
            } catch (IOException unused) {
            }
            return networkErrorRequest;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e);
            throw e;
        }
    }

    protected NetworkError request(ResetableOutputStream resetableOutputStream) throws Throwable {
        String string;
        if (this.mUrl == null) {
            return NetworkError.URL_ERROR;
        }
        if (!Utils.isConnected(AppGlobal.getContext())) {
            return NetworkError.NETWORK_ERROR;
        }
        if (this.mParameter == null) {
            this.mParameter = new Parameter(this);
        }
        try {
            Parameter parameterOnQueryCreated = onQueryCreated(this.mParameter);
            String string2 = this.mUrl.toString();
            if (this.mUseGet && !parameterOnQueryCreated.isEmpty()) {
                String query = this.mUrl.getQuery();
                String string3 = this.mUrl.toString();
                string2 = TextUtils.isEmpty(query) ? string3 + CallerData.NA + parameterOnQueryCreated.toString() : string3 + "&" + parameterOnQueryCreated.toString();
            }
            try {
                String strOnURLCreated = onURLCreated(string2, parameterOnQueryCreated);
                if (Utils.DEBUG) {
                    Log.d(TAG, "connection url: " + strOnURLCreated);
                }
                if (this.mUseGet) {
                    string = "";
                } else {
                    string = parameterOnQueryCreated.toString();
                }
                String str = string;
                long jCurrentTimeMillis = System.currentTimeMillis();
                NetworkError networkErrorInnerRequest = innerRequest(strOnURLCreated, str, this.mUseGet, false, resetableOutputStream);
                if (Utils.DEBUG) {
                    Log.d(TAG, "Time(ms) spent in request: " + (System.currentTimeMillis() - jCurrentTimeMillis) + ", " + strOnURLCreated);
                }
                return networkErrorInnerRequest;
            } catch (ConnectionException e) {
                return e.mError;
            }
        } catch (ConnectionException e2) {
            return e2.mError;
        }
    }

    private NetworkError innerRequest(String str, String str2, boolean z, boolean z2, ResetableOutputStream resetableOutputStream) throws Throwable {
        HttpURLConnection httpURLConnectionOnConnectionCreated;
        Exception e;
        BufferedInputStream bufferedInputStream;
        Throwable th;
        Exception e2;
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add(str);
        for (String str3 : arrayList) {
            if (Utils.DEBUG) {
                Log.d(TAG, "hosted connection url: " + str3);
            }
            try {
                URL url = new URL(str3);
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnectionOnConnectionCreated = (HttpURLConnection) url.openConnection();
                    try {
                        try {
                            httpURLConnectionOnConnectionCreated.setConnectTimeout(10000);
                            if (Utils.isWifiConnected(AppGlobal.getContext())) {
                                httpURLConnectionOnConnectionCreated.setReadTimeout(10000);
                            } else {
                                httpURLConnectionOnConnectionCreated.setReadTimeout(30000);
                            }
                            if (z) {
                                httpURLConnectionOnConnectionCreated.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_GET);
                                httpURLConnectionOnConnectionCreated.setDoOutput(false);
                            } else {
                                httpURLConnectionOnConnectionCreated.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
                                httpURLConnectionOnConnectionCreated.setDoOutput(true);
                            }
                            try {
                                httpURLConnectionOnConnectionCreated = onConnectionCreated(httpURLConnectionOnConnectionCreated);
                                httpURLConnectionOnConnectionCreated.connect();
                                if (!z && !TextUtils.isEmpty(str2)) {
                                    OutputStream outputStream = httpURLConnectionOnConnectionCreated.getOutputStream();
                                    outputStream.write(str2.getBytes());
                                    if (Utils.DEBUG) {
                                        Log.d(TAG, "[post]" + str2);
                                    }
                                    outputStream.close();
                                }
                                NetworkError networkErrorHandleResponseCode = handleResponseCode(httpURLConnectionOnConnectionCreated.getResponseCode());
                                if (networkErrorHandleResponseCode == NetworkError.OK && resetableOutputStream != null) {
                                    try {
                                        bufferedInputStream = new BufferedInputStream(httpURLConnectionOnConnectionCreated.getInputStream(), 8192);
                                        try {
                                            try {
                                                byte[] bArr = new byte[1024];
                                                while (true) {
                                                    int i = bufferedInputStream.read(bArr, 0, 1024);
                                                    if (i <= 0) {
                                                        break;
                                                    }
                                                    resetableOutputStream.write(bArr, 0, i);
                                                }
                                                resetableOutputStream.flush();
                                                bufferedInputStream.close();
                                            } catch (Throwable th2) {
                                                th = th2;
                                                if (bufferedInputStream != null) {
                                                    bufferedInputStream.close();
                                                }
                                                throw th;
                                            }
                                        } catch (Exception e3) {
                                            e2 = e3;
                                            Log.e(TAG, "Connection Exception for " + url.getHost() + " : read file stream error " + e2);
                                            resetableOutputStream.reset();
                                            if (bufferedInputStream != null) {
                                                bufferedInputStream.close();
                                            }
                                            if (httpURLConnectionOnConnectionCreated != null) {
                                                httpURLConnectionOnConnectionCreated.disconnect();
                                            }
                                        }
                                    } catch (Exception e4) {
                                        bufferedInputStream = null;
                                        e2 = e4;
                                    } catch (Throwable th3) {
                                        bufferedInputStream = null;
                                        th = th3;
                                    }
                                }
                                if (httpURLConnectionOnConnectionCreated != null) {
                                    httpURLConnectionOnConnectionCreated.disconnect();
                                }
                                return networkErrorHandleResponseCode;
                            } catch (ConnectionException e5) {
                                NetworkError networkError = e5.mError;
                                if (httpURLConnectionOnConnectionCreated != null) {
                                    httpURLConnectionOnConnectionCreated.disconnect();
                                }
                                return networkError;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            Log.e(TAG, "Connection Exception for " + url.getHost() + " :" + e);
                            if (httpURLConnectionOnConnectionCreated != null) {
                                httpURLConnectionOnConnectionCreated.disconnect();
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        httpURLConnection = httpURLConnectionOnConnectionCreated;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th;
                    }
                } catch (Exception e7) {
                    httpURLConnectionOnConnectionCreated = null;
                    e = e7;
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (MalformedURLException e8) {
                Log.e(TAG, " URL error :" + e8);
            }
        }
        return NetworkError.NETWORK_ERROR;
    }

    protected boolean checkURL(URL url) {
        if (url == null) {
            return false;
        }
        String protocol = url.getProtocol();
        return TextUtils.equals(protocol, PROTOCOL_HTTP) || TextUtils.equals(protocol, PROTOCOL_HTTPS);
    }

    private NetworkError handleResponseCode(int i) {
        if (i == 200) {
            return NetworkError.OK;
        }
        Log.e(TAG, "Network Error : " + i);
        return NetworkError.SERVER_ERROR;
    }

    public class Parameter {
        private TreeMap<String, String> params;

        public Parameter(Connection connection) {
            this(true);
        }

        public Parameter(boolean z) {
            this.params = new TreeMap<>();
            if (z) {
                Connection.this.mParameter = this;
            }
        }

        public Parameter add(String str, String str2) {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                this.params.put(str, str2);
            }
            return this;
        }

        public Parameter add(String str, boolean z) {
            if (z) {
                this.params.put(str, "true");
                return this;
            }
            this.params.put(str, "false");
            return this;
        }

        public String get(String str) {
            return this.params.get(str);
        }

        public boolean isEmpty() {
            return this.params.isEmpty();
        }

        public String toString() {
            if (this.params.isEmpty()) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (String str : this.params.keySet()) {
                sb.append(str);
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(this.params.get(str), "UTF-8"));
                } catch (UnsupportedEncodingException unused) {
                }
                sb.append("&");
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        }

        public TreeMap<String, String> getParams() {
            return this.params;
        }
    }

    protected class ConnectionException extends Exception {
        private static final long serialVersionUID = 1;
        protected NetworkError mError;

        public ConnectionException(NetworkError networkError) {
            this.mError = networkError;
        }
    }

    protected abstract class ResetableOutputStream extends OutputStream {
        protected OutputStream mOutputStream;

        public abstract void reset();

        public ResetableOutputStream(OutputStream outputStream) {
            if (outputStream == null) {
                throw new IllegalArgumentException("outputstream is null");
            }
            this.mOutputStream = outputStream;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.mOutputStream.close();
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.mOutputStream.flush();
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.mOutputStream.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.mOutputStream.write(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.mOutputStream.write(i);
        }
    }

    protected class MemoryResetableOutputStream extends ResetableOutputStream {
        public MemoryResetableOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
            super(byteArrayOutputStream);
        }

        @Override // com.market.sdk.utils.Connection.ResetableOutputStream
        public void reset() {
            ((ByteArrayOutputStream) this.mOutputStream).reset();
        }
    }

    protected class FileResetableOutputStream extends ResetableOutputStream {
        private File mFile;

        public FileResetableOutputStream(File file) throws FileNotFoundException {
            super(new FileOutputStream(file));
            this.mFile = file;
        }

        @Override // com.market.sdk.utils.Connection.ResetableOutputStream
        public void reset() {
            try {
                this.mOutputStream.close();
            } catch (IOException unused) {
            }
            this.mFile.delete();
            try {
                this.mOutputStream = new FileOutputStream(this.mFile);
            } catch (FileNotFoundException unused2) {
            }
        }
    }
}
