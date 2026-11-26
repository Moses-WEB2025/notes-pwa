package com.miui.notes.ai.utils;

import com.miui.notes.base.utils.Logger;
import miui.process.IForegroundInfoListener;
import miui.process.IForegroundWindowListener;

/* JADX INFO: loaded from: classes4.dex */
public class ForegroundInfoUtils {
    public static final String TAG = "ForegroundInfoUtils";

    public static void registerForegroundWindowListener(IForegroundWindowListener iForegroundWindowListener) {
        try {
            ReflectUtils.callStaticMethod(Class.forName("miui.process.ProcessManager"), "registerForegroundWindowListener", (Class<?>[]) new Class[]{IForegroundWindowListener.class}, iForegroundWindowListener);
        } catch (ClassNotFoundException e) {
            Logger.INSTANCE.e(TAG, "registerForegroundWindowListener: " + e);
        }
    }

    public static void unregisterForegroundWindowListener(IForegroundWindowListener.Stub stub) {
        try {
            ReflectUtils.callStaticMethod(Class.forName("miui.process.ProcessManager"), "unregisterForegroundWindowListener", (Class<?>[]) new Class[]{IForegroundWindowListener.class}, stub);
        } catch (ClassNotFoundException e) {
            Logger.INSTANCE.e(TAG, "unregisterForegroundWindowListener: " + e);
        }
    }

    public static void registerForegroundInfoListener(IForegroundInfoListener iForegroundInfoListener) {
        try {
            ReflectUtils.callStaticMethod(Class.forName("miui.process.ProcessManager"), "registerForegroundInfoListener", (Class<?>[]) new Class[]{IForegroundInfoListener.class}, iForegroundInfoListener);
        } catch (ClassNotFoundException e) {
            Logger.INSTANCE.e(TAG, "registerForegroundInfoListener: " + e);
        }
    }

    public static void unregisterForegroundInfoListener(IForegroundInfoListener iForegroundInfoListener) {
        try {
            ReflectUtils.callStaticMethod(Class.forName("miui.process.ProcessManager"), "unregisterForegroundInfoListener", (Class<?>[]) new Class[]{IForegroundInfoListener.class}, iForegroundInfoListener);
        } catch (ClassNotFoundException e) {
            Logger.INSTANCE.e(TAG, "registerForegroundInfoListener: " + e);
        }
    }
}
