package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class d {
    private static final String a = "DataConvertUtil";

    public static String a(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            try {
                return new JSONObject(map).toString();
            } catch (Exception e) {
                r.b(a, "map to json error:" + e.getMessage());
            }
        }
        return "";
    }

    public static Map<String, String> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap();
        }
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.optString(next));
            }
            return map;
        } catch (Exception e) {
            r.b(a, "json to map error:" + e.getMessage());
            return map;
        }
    }

    public static byte[] a(Object obj) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    try {
                        objectOutputStream.writeObject(obj);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        o.a((OutputStream) byteArrayOutputStream);
                        o.a((OutputStream) objectOutputStream);
                        return byteArray;
                    } catch (IOException e) {
                        e = e;
                        r.b(a, "serialize error:" + e.getMessage());
                        o.a((OutputStream) byteArrayOutputStream);
                        o.a((OutputStream) objectOutputStream);
                        return null;
                    }
                } catch (IOException e2) {
                    e = e2;
                    objectOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    o.a((OutputStream) byteArrayOutputStream2);
                    o.a((OutputStream) 0);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                byteArrayOutputStream = null;
                objectOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                o.a((OutputStream) byteArrayOutputStream2);
                o.a((OutputStream) 0);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v9 */
    public static Object a(byte[] bArr) throws Throwable {
        ?? r6;
        ObjectInputStream objectInputStream;
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    try {
                        Object object = objectInputStream.readObject();
                        o.a((InputStream) byteArrayInputStream);
                        o.a((InputStream) objectInputStream);
                        return object;
                    } catch (Exception e) {
                        e = e;
                        r.b(a, "deserialize error:" + e.getMessage());
                        o.a((InputStream) byteArrayInputStream);
                        o.a((InputStream) objectInputStream);
                        return null;
                    }
                } catch (Exception e2) {
                    e = e2;
                    objectInputStream = null;
                } catch (Throwable th) {
                    th = th;
                    bArr = null;
                    byteArrayInputStream2 = byteArrayInputStream;
                    r6 = bArr;
                    o.a((InputStream) byteArrayInputStream2);
                    o.a((InputStream) r6);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                objectInputStream = null;
                byteArrayInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                r6 = 0;
                o.a((InputStream) byteArrayInputStream2);
                o.a((InputStream) r6);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
