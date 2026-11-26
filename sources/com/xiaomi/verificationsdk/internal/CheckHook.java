package com.xiaomi.verificationsdk.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CheckHook {
    public static boolean isHook(Context context) {
        return isHookByPackageName(context) || isHookByStack(context) || isHookByJar();
    }

    public static boolean isHookByPackageName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("de.robv.android.xposed.installer", 0);
            Log.wtf("CheckHook", "Xposed found on the system.");
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.wtf("CheckHook", "Xposed NOT found on the system.");
            try {
                packageManager.getPackageInfo("com.saurik.substrate", 0);
                Log.wtf("CheckHook", "Substrate found on the system.");
                return true;
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.wtf("CheckHook", "Substrate NOT found on the system.");
                return false;
            }
        }
    }

    public static boolean isHookByStack(Context context) {
        try {
            throw new Exception("blah");
        } catch (Exception e) {
            boolean z = false;
            int i = 0;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit") && (i = i + 1) == 2) {
                    Log.wtf("HookDetection", "Substrate is active on the device.");
                    z = true;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    Log.wtf("HookDetection", "A method on the stack trace has been hooked using Substrate.");
                    z = true;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    Log.wtf("HookDetection", "Xposed is active on the device.");
                    z = true;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    Log.wtf("HookDetection", "A method on the stack trace has been hooked using Xposed.");
                    z = true;
                }
            }
            return z;
        }
    }

    public static boolean isHookByJar() {
        boolean z = false;
        try {
            HashSet<String> hashSet = new HashSet();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/maps"));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    hashSet.add(line.substring(line.lastIndexOf(StringUtils.SPACE) + 1));
                }
            }
            for (String str : hashSet) {
                if (str.contains("com.saurik.substrate")) {
                    Log.wtf("HookDetection", "Substrate shared object found: " + str);
                    z = true;
                }
                if (str.contains("XposedBridge.jar")) {
                    Log.wtf("HookDetection", "Xposed JAR found: " + str);
                    z = true;
                }
            }
            bufferedReader.close();
            return z;
        } catch (Exception e) {
            Log.wtf("HookDetection", e.toString());
            return z;
        }
    }
}
