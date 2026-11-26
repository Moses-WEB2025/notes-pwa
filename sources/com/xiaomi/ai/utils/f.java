package com.xiaomi.ai.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.ai.HTTPCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes4.dex */
public class f {
    public static String a() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
        return simpleDateFormat.format(calendar.getTime()) + " GMT";
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004b A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, LOOP:0: B:12:0x0045->B:14:0x004b, LOOP_END, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x008b A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, TRY_ENTER, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x009f A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, LOOP:1: B:19:0x0099->B:21:0x009f, LOOP_END, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:23:0x00b8 A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x00cc A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, LOOP:2: B:24:0x00c6->B:26:0x00cc, LOOP_END, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x00e6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:30:0x00e8 A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x00f0 A[Catch: IOException -> 0x00f8, MalformedURLException -> 0x0106, TRY_LEAVE, TryCatch #2 {MalformedURLException -> 0x0106, IOException -> 0x00f8, blocks: (B:10:0x0017, B:11:0x001a, B:12:0x0045, B:14:0x004b, B:15:0x005b, B:18:0x008b, B:19:0x0099, B:21:0x009f, B:22:0x00b4, B:27:0x00e1, B:30:0x00e8, B:31:0x00f0, B:23:0x00b8, B:24:0x00c6, B:26:0x00cc), top: B:44:0x0017 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x00b4 A[EDGE_INSN: B:46:0x00b4->B:22:0x00b4 BREAK  A[LOOP:1: B:19:0x0099->B:21:0x009f], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x00b4 A[EDGE_INSN: B:47:0x00b4->B:22:0x00b4 BREAK  A[LOOP:2: B:24:0x00c6->B:26:0x00cc], SYNTHETIC] */
    public static String a(HTTPCallback hTTPCallback, String str, Map<String, String> map, String str2) {
        String string;
        HttpURLConnection httpURLConnection;
        int responseCode;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        BufferedReader bufferedReader2;
        String line2;
        if (TextUtils.isEmpty(str2)) {
            if (hTTPCallback == null) {
                return null;
            }
            hTTPCallback.onHTTPRequestError(-1, "args error");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (hTTPCallback != null) {
            try {
                hTTPCallback.onHTTPRequestStart();
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                for (String str3 : map.keySet()) {
                    httpURLConnection.setRequestProperty(str3, map.get(str3));
                }
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(str2.getBytes());
                httpURLConnection.connect();
                responseCode = httpURLConnection.getResponseCode();
                Log.d("Track", "code " + responseCode);
                if (responseCode == 200) {
                    inputStreamReader = new InputStreamReader(httpURLConnection.getErrorStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    while (true) {
                        line = bufferedReader.readLine();
                        if (line != null) {
                            break;
                        }
                        stringBuffer.append(line + StringUtils.LF);
                    }
                } else {
                    inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                    bufferedReader2 = new BufferedReader(inputStreamReader);
                    while (true) {
                        line2 = bufferedReader2.readLine();
                        if (line2 != null) {
                            break;
                        }
                        stringBuffer.append(line2 + StringUtils.LF);
                    }
                }
                inputStreamReader.close();
                outputStream.close();
                if (hTTPCallback != null) {
                    if (responseCode != 200) {
                        hTTPCallback.onHTTPRequestError(responseCode, stringBuffer.toString());
                    } else {
                        hTTPCallback.onHTTPRequestResult(stringBuffer.toString());
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                if (hTTPCallback != null) {
                    string = e.toString();
                    hTTPCallback.onHTTPRequestError(-1, string);
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (hTTPCallback != null) {
                    string = e2.toString();
                    hTTPCallback.onHTTPRequestError(-1, string);
                }
            }
        } else {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod(PassportSimpleRequest.HTTP_METHOD_POST);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            while (r2.hasNext()) {
                httpURLConnection.setRequestProperty(str3, map.get(str3));
            }
            OutputStream outputStream2 = httpURLConnection.getOutputStream();
            outputStream2.write(str2.getBytes());
            httpURLConnection.connect();
            responseCode = httpURLConnection.getResponseCode();
            Log.d("Track", "code " + responseCode);
            if (responseCode == 200) {
                inputStreamReader = new InputStreamReader(httpURLConnection.getErrorStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    line = bufferedReader.readLine();
                    if (line != null) {
                        break;
                        break;
                    }
                    stringBuffer.append(line + StringUtils.LF);
                }
            } else {
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader2 = new BufferedReader(inputStreamReader);
                while (true) {
                    line2 = bufferedReader2.readLine();
                    if (line2 != null) {
                        break;
                        break;
                    }
                    stringBuffer.append(line2 + StringUtils.LF);
                }
            }
            inputStreamReader.close();
            outputStream2.close();
            if (hTTPCallback != null) {
                if (responseCode != 200) {
                    hTTPCallback.onHTTPRequestError(responseCode, stringBuffer.toString());
                } else {
                    hTTPCallback.onHTTPRequestResult(stringBuffer.toString());
                }
            }
        }
        return stringBuffer.toString();
    }

    public static boolean a(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) ? false : true;
    }
}
