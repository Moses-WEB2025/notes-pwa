package com.xiaomi.accountsdk.request;

import android.util.Base64;
import com.google.common.net.HttpHeaders;
import com.xiaomi.accountsdk.utils.IOUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/* JADX INFO: loaded from: classes4.dex */
public class UploadFileRequest {
    private static final String CRLF = "\r\n";

    public static String execute(String str, InputStream inputStream, String str2, String str3) throws Throwable {
        HttpURLConnection httpURLConnection;
        DataOutputStream dataOutputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                byte[] bArr = new byte[16];
                new Random().nextBytes(bArr);
                String strEncodeToString = Base64.encodeToString(bArr, 2);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
                httpURLConnection.setRequestProperty("Connection", HttpHeaders.KEEP_ALIVE);
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + strEncodeToString);
                httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                DataOutputStream dataOutputStream2 = new DataOutputStream(httpURLConnection.getOutputStream());
                try {
                    dataOutputStream2.writeBytes("--" + strEncodeToString + "\r\n");
                    dataOutputStream2.writeBytes("Content-Disposition: form-data; name=\"" + str2 + "\"; filename=\"" + str3 + "\"\r\n");
                    dataOutputStream2.writeBytes("Content-Type: " + getMIMEType(str3) + "\r\n");
                    dataOutputStream2.writeBytes("\r\n");
                    byte[] bArr2 = new byte[8192];
                    while (true) {
                        int i = inputStream.read(bArr2);
                        if (i == -1) {
                            break;
                        }
                        dataOutputStream2.write(bArr2, 0, i);
                    }
                    dataOutputStream2.flush();
                    dataOutputStream2.writeBytes("\r\n");
                    dataOutputStream2.flush();
                    dataOutputStream2.writeBytes("--" + strEncodeToString + "--\r\n");
                    dataOutputStream2.flush();
                    inputStream.close();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode != 200) {
                        throw new InvalidResponseException(responseCode, "failed to upload file");
                    }
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        } catch (Throwable th) {
                            IOUtils.closeQuietly(bufferedReader);
                            throw th;
                        }
                    }
                    IOUtils.closeQuietly(bufferedReader);
                    String string = sb.toString();
                    try {
                        dataOutputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return string;
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = dataOutputStream2;
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (httpURLConnection == null) {
                        throw th;
                    }
                    httpURLConnection.disconnect();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            httpURLConnection = null;
        }
    }

    private static String getMIMEType(String str) {
        if (str.endsWith("png") || str.endsWith("PNG")) {
            return "image/png";
        }
        return "image/jpg";
    }
}
