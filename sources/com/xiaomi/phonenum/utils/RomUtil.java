package com.xiaomi.phonenum.utils;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes4.dex */
public class RomUtil {
    public static boolean isMIUIRom() {
        return (TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name")) && TextUtils.isEmpty(getSystemProperty("ro.mi.os.version.name"))) ? false : true;
    }

    private static String getSystemProperty(String str) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop " + str).getInputStream()), 1024);
            try {
                String line = bufferedReader.readLine();
                bufferedReader.close();
                try {
                    bufferedReader.close();
                } catch (IOException unused) {
                }
                return line;
            } catch (IOException unused2) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException unused3) {
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (IOException unused5) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }
}
