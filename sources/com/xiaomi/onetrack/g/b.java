package com.xiaomi.onetrack.g;

import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.d.f;
import com.xiaomi.onetrack.util.o;
import com.xiaomi.onetrack.util.r;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class b {
    public static final int a = 30000;
    public static final int b = 30000;
    public static final String c = "OT_SID";
    public static final String d = "OT_ts";
    public static final String e = "OT_net";
    public static final String f = "OT_sender";
    public static final String g = "OT_protocol";
    public static final String h = "Connection";
    public static final int i = 60000;
    private static String j = "HttpUtil";
    private static final String k = "GET";
    private static final String l = "POST";
    private static final String m = "&";
    private static final String n = "=";
    private static final String o = "UTF-8";
    private static final String p = "miui_sdkconfig_jafej!@#)(*e@!#";
    private static final int q = 3;

    private b() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 3, insn: 0x00f2: MOVE (r0 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:29:0x00f2 */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v17, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v2, types: [int] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v17, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.InputStream] */
    public static String a(String str, byte[] bArr) throws IOException {
        InputStream inputStream;
        ?? inputStream2;
        String str2 = j;
        ?? Append = new StringBuilder("doPost url=").append(str).append(", len=");
        ?? length = bArr.length;
        ?? string = Append.append(length).toString();
        r.a(str2, string);
        InputStream inputStream3 = null;
        try {
            try {
                string = (HttpURLConnection) new URL(str).openConnection();
                try {
                    string.setConnectTimeout(30000);
                    string.setReadTimeout(30000);
                    string.setDoOutput(true);
                    string.setRequestMethod("POST");
                    a((HttpURLConnection) string);
                    string.setRequestProperty("Content-Type", "application/octet-stream");
                    string.setRequestProperty(c, f.a().b()[1]);
                    string.setRequestProperty(d, Long.toString(System.currentTimeMillis()));
                    string.setRequestProperty(e, c.a(com.xiaomi.onetrack.f.a.b()).toString());
                    string.setRequestProperty(f, com.xiaomi.onetrack.f.a.e());
                    string.setRequestProperty(g, "3.0");
                    length = string.getOutputStream();
                    try {
                        length.write(bArr, 0, bArr.length);
                        length.flush();
                        int responseCode = string.getResponseCode();
                        inputStream2 = string.getInputStream();
                        try {
                            byte[] bArrB = o.b((InputStream) inputStream2);
                            r.a(j, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str, Integer.valueOf(responseCode)));
                            String str3 = new String(bArrB, "UTF-8");
                            o.a((InputStream) inputStream2);
                            o.a((OutputStream) length);
                            o.a((HttpURLConnection) string);
                            return str3;
                        } catch (IOException e2) {
                            e = e2;
                            r.b(j, String.format("HttpUtils POST 上传失败, url: %s, error: %s", str, e.getMessage()));
                            o.a((InputStream) inputStream2);
                            o.a((OutputStream) length);
                            o.a((HttpURLConnection) string);
                            return null;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        inputStream2 = 0;
                    } catch (Throwable th) {
                        th = th;
                        o.a(inputStream3);
                        o.a((OutputStream) length);
                        o.a((HttpURLConnection) string);
                        throw th;
                    }
                } catch (IOException e4) {
                    e = e4;
                    length = 0;
                    string = string;
                    inputStream2 = length;
                    r.b(j, String.format("HttpUtils POST 上传失败, url: %s, error: %s", str, e.getMessage()));
                    o.a((InputStream) inputStream2);
                    o.a((OutputStream) length);
                    o.a((HttpURLConnection) string);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    length = 0;
                }
            } catch (IOException e5) {
                e = e5;
                string = 0;
                length = 0;
            } catch (Throwable th3) {
                th = th3;
                string = 0;
                length = 0;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream3 = inputStream;
        }
    }

    private static void a(HttpURLConnection httpURLConnection) {
        OneTrack.HttpRequestProperty httpReqPropConnection;
        if (httpURLConnection == null || (httpReqPropConnection = OneTrack.getHttpReqPropConnection()) == null) {
            return;
        }
        try {
            r.a(j, String.format("setCustomRequestProperty->connection: %s", httpReqPropConnection.getType()));
            httpURLConnection.setRequestProperty("Connection", httpReqPropConnection.getType());
        } catch (Exception e2) {
            r.b(j, "setCustomRequestProperty error: " + e2.getMessage());
        }
    }

    public static String a(String str) throws IOException {
        return a(str, (Map<String, String>) null, false);
    }

    public static String a(String str, Map<String, String> map) throws IOException {
        return a(str, map, true);
    }

    public static String a(String str, Map<String, String> map, boolean z) throws IOException {
        return a("GET", str, map, z);
    }

    public static String b(String str, Map<String, String> map) throws IOException {
        return b(str, map, true);
    }

    public static String b(String str, Map<String, String> map, boolean z) throws IOException {
        return a("POST", str, map, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r10v9, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.util.Map, java.util.Map<java.lang.String, java.lang.String>] */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9, types: [java.io.OutputStream] */
    private static String a(String str, String str2, Map<String, String> map, boolean z) throws Throwable {
        String strA;
        InputStream inputStream;
        ?? r3 = 0;
        r3 = 0;
        r3 = 0;
        try {
            if (map == 0) {
                strA = null;
            } else {
                try {
                    strA = a((Map<String, String>) map, (boolean) z);
                } catch (Exception e2) {
                    e = e2;
                    map = 0;
                    z = 0;
                    inputStream = null;
                    r.b(j, "HttpUtils url:" + str2 + " ," + e.getMessage());
                    o.a(inputStream);
                    o.a((OutputStream) map);
                    o.a((HttpURLConnection) z);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    map = 0;
                    z = 0;
                    o.a((InputStream) r3);
                    o.a((OutputStream) map);
                    o.a((HttpURLConnection) z);
                    throw th;
                }
            }
            z = (HttpURLConnection) new URL((!"GET".equals(str) || strA == null) ? str2 : str2 + "? " + strA).openConnection();
            try {
                z.setConnectTimeout(30000);
                z.setReadTimeout(30000);
                a((HttpURLConnection) z);
                try {
                    if (!"GET".equals(str)) {
                        if ("POST".equals(str) && strA != null) {
                            z.setRequestMethod("POST");
                            z.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            z.setDoOutput(true);
                            byte[] bytes = strA.getBytes("UTF-8");
                            map = z.getOutputStream();
                            try {
                                map.write(bytes, 0, bytes.length);
                                map.flush();
                                map = map;
                            } catch (Exception e3) {
                                e = e3;
                                inputStream = null;
                                r.b(j, "HttpUtils url:" + str2 + " ," + e.getMessage());
                                o.a(inputStream);
                                o.a((OutputStream) map);
                                o.a((HttpURLConnection) z);
                                return null;
                            } catch (Throwable th2) {
                                th = th2;
                                o.a((InputStream) r3);
                                o.a((OutputStream) map);
                                o.a((HttpURLConnection) z);
                                throw th;
                            }
                        }
                        int responseCode = z.getResponseCode();
                        inputStream = z.getInputStream();
                        byte[] bArrB = o.b(inputStream);
                        r.a(j, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str2, Integer.valueOf(responseCode)));
                        String str3 = new String(bArrB, "UTF-8");
                        o.a(inputStream);
                        o.a((OutputStream) map);
                        o.a((HttpURLConnection) z);
                        return str3;
                    }
                    z.setRequestMethod("GET");
                    byte[] bArrB2 = o.b(inputStream);
                    r.a(j, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str2, Integer.valueOf(responseCode)));
                    String str4 = new String(bArrB2, "UTF-8");
                    o.a(inputStream);
                    o.a((OutputStream) map);
                    o.a((HttpURLConnection) z);
                    return str4;
                } catch (Exception e4) {
                    e = e4;
                    r.b(j, "HttpUtils url:" + str2 + " ," + e.getMessage());
                    o.a(inputStream);
                    o.a((OutputStream) map);
                    o.a((HttpURLConnection) z);
                    return null;
                }
                map = 0;
                int responseCode2 = z.getResponseCode();
                inputStream = z.getInputStream();
            } catch (Exception e5) {
                e = e5;
                map = 0;
                inputStream = null;
            } catch (Throwable th3) {
                th = th3;
                map = 0;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = "POST";
        }
    }

    private static String a(Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                if (!TextUtils.isEmpty(entry.getKey())) {
                    if (sb.length() > 0) {
                        sb.append(m);
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    sb.append(n);
                    sb.append(URLEncoder.encode(entry.getValue() == null ? "null" : entry.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException unused) {
                r.b(j, "format params failed");
            }
        }
        if (z) {
            String strA = a(map);
            if (sb.length() > 0) {
                sb.append(m);
            }
            sb.append(URLEncoder.encode("sign", "UTF-8"));
            sb.append(n);
            sb.append(URLEncoder.encode(strA, "UTF-8"));
        }
        return sb.toString();
    }

    public static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            ArrayList<String> arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    sb.append(map.get(str));
                }
            }
        }
        sb.append(p);
        return com.xiaomi.onetrack.d.d.c(sb.toString());
    }

    public static boolean b(String str) throws Throwable {
        if (TextUtils.isEmpty(str)) {
            r.a(j, "doGetAdMonitor dbUrl is null");
            return true;
        }
        HttpURLConnection httpURLConnection = null;
        int i2 = 0;
        int i3 = 0;
        while (i2 < 3) {
            try {
                try {
                    try {
                        if (i3 / 100 == 3) {
                            i2++;
                            str = httpURLConnection.getHeaderField("Location");
                            r.a(j, "redirect url is:" + str);
                        }
                        HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                        try {
                            httpURLConnection2.setInstanceFollowRedirects(false);
                            httpURLConnection2.setRequestMethod("GET");
                            httpURLConnection2.setConnectTimeout(30000);
                            httpURLConnection2.setReadTimeout(30000);
                            int responseCode = httpURLConnection2.getResponseCode();
                            r.a(j, "AdMonitor get 请求url:" + str + "_ResponseCode：" + responseCode);
                            if (responseCode / 100 != 5 && responseCode / 100 != 3) {
                                try {
                                    o.a(httpURLConnection2);
                                } catch (Exception unused) {
                                }
                                return true;
                            }
                            if (responseCode / 100 != 3) {
                                o.a(httpURLConnection2);
                                return false;
                            }
                            i3 = responseCode;
                            httpURLConnection = httpURLConnection2;
                        } catch (ProtocolException e2) {
                            e = e2;
                            httpURLConnection = httpURLConnection2;
                            if (TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("200 OK")) {
                                r.a(j, "response code is 200, bug status line is invalid.");
                                try {
                                    o.a(httpURLConnection);
                                } catch (Exception unused2) {
                                }
                                return true;
                            }
                            o.a(httpURLConnection);
                        } catch (Exception e3) {
                            e = e3;
                            httpURLConnection = httpURLConnection2;
                            r.b(j, "HttpUtils doGetAdMonitor 上传异常:" + e.getMessage());
                            o.a(httpURLConnection);
                        } catch (Throwable th) {
                            th = th;
                            httpURLConnection = httpURLConnection2;
                            try {
                                o.a(httpURLConnection);
                            } catch (Exception unused3) {
                            }
                            throw th;
                        }
                    } catch (ProtocolException e4) {
                        e = e4;
                    } catch (Exception e5) {
                        e = e5;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused4) {
            }
        }
        r.a(j, "redirectCount >= 3, return true");
        try {
            o.a(httpURLConnection);
        } catch (Exception unused5) {
        }
        return true;
    }

    public static d a(String str, String str2, String str3) throws Throwable {
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        OutputStream outputStream2;
        InputStream inputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(60000);
                httpURLConnection.setReadTimeout(60000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("accept", "*/*");
                httpURLConnection.setRequestProperty("connection", HttpHeaders.KEEP_ALIVE);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", str3);
                httpURLConnection.setDoOutput(true);
                byte[] bytes = str2.getBytes("UTF-8");
                outputStream = httpURLConnection.getOutputStream();
                try {
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    int responseCode = httpURLConnection.getResponseCode();
                    d dVar = new d();
                    dVar.d = responseCode;
                    InputStream errorStream = responseCode != 200 ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
                    try {
                        dVar.a(new String(o.b(errorStream), "UTF-8"));
                        if (dVar.d == 200) {
                            r.a(j, "POST 成功 publishResponse:" + dVar.toString());
                        } else {
                            r.b(j, "POST 失败 publishResponse:" + dVar.toString());
                        }
                        o.a(errorStream);
                        o.a(outputStream);
                        o.a(httpURLConnection);
                        return dVar;
                    } catch (Exception e2) {
                        inputStream = errorStream;
                        e = e2;
                        outputStream2 = outputStream;
                        try {
                            r.b(j, "HttpUtils post url:" + str + " ," + e.getMessage());
                            o.a(inputStream);
                            o.a(outputStream2);
                            o.a(httpURLConnection);
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            inputStream2 = inputStream;
                            outputStream = outputStream2;
                            o.a(inputStream2);
                            o.a(outputStream);
                            o.a(httpURLConnection);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream2 = errorStream;
                        o.a(inputStream2);
                        o.a(outputStream);
                        o.a(httpURLConnection);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    outputStream2 = outputStream;
                    inputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e4) {
                e = e4;
                inputStream = null;
                outputStream2 = null;
            } catch (Throwable th4) {
                th = th4;
                outputStream = null;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
            outputStream2 = null;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th = th5;
            outputStream = null;
            httpURLConnection = null;
        }
    }

    public static String a(String str, String str2, Map<String, String> map) throws Throwable {
        String strA;
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        InputStream inputStream2 = null;
        if (map == null) {
            strA = null;
        } else {
            try {
                strA = a(map, true);
            } catch (Exception e2) {
                e = e2;
                inputStream = null;
                outputStream = null;
                httpURLConnection = null;
                r.b(j, "HttpUtils post token:" + str2 + " ," + e.getMessage());
                o.a(inputStream);
                o.a(outputStream);
                o.a(httpURLConnection);
                return null;
            } catch (Throwable th) {
                th = th;
                outputStream = null;
                httpURLConnection = null;
                o.a(inputStream2);
                o.a(outputStream);
                o.a(httpURLConnection);
                throw th;
            }
        }
        httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
        try {
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + str);
            httpURLConnection.setDoOutput(true);
            byte[] bytes = strA.getBytes("UTF-8");
            outputStream = httpURLConnection.getOutputStream();
            try {
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                r.a(j, "postGetToken responseData statusCode:" + httpURLConnection.getResponseCode());
                inputStream = httpURLConnection.getInputStream();
                try {
                    try {
                        String str3 = new String(o.b(inputStream), "UTF-8");
                        o.a(inputStream);
                        o.a(outputStream);
                        o.a(httpURLConnection);
                        return str3;
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream2 = inputStream;
                        o.a(inputStream2);
                        o.a(outputStream);
                        o.a(httpURLConnection);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    r.b(j, "HttpUtils post token:" + str2 + " ," + e.getMessage());
                    o.a(inputStream);
                    o.a(outputStream);
                    o.a(httpURLConnection);
                    return null;
                }
            } catch (Exception e4) {
                e = e4;
                inputStream = null;
            } catch (Throwable th3) {
                th = th3;
                o.a(inputStream2);
                o.a(outputStream);
                o.a(httpURLConnection);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
            outputStream = null;
        } catch (Throwable th4) {
            th = th4;
            outputStream = null;
        }
    }
}
