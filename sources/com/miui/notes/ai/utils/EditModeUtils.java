package com.miui.notes.ai.utils;

import android.content.Context;
import android.os.Build;
import android.view.ActionMode;
import android.view.View;
import com.miui.notes.ai.ui.CustomAiTextWidgetEditText;
import com.miui.notes.base.utils.Logger;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes4.dex */
public class EditModeUtils {
    private static final int ANDROID_S = 31;
    private static final String TAG = "EditModeUtils";
    private Method mActionModeMethod;
    private Object mFloatingActionModeHelper;
    private Class mToolbarHelperClass;

    public static boolean supportHook(ActionMode.Callback callback, int i) {
        return Build.VERSION.SDK_INT >= 31 && (callback instanceof ActionMode.Callback2) && i == 1;
    }

    public ActionMode createActionMode(int i, ActionMode.Callback callback, View view) {
        if (Build.VERSION.SDK_INT < 31 || i != 1 || !(view instanceof CustomAiTextWidgetEditText) || !(callback instanceof ActionMode.Callback2)) {
            return null;
        }
        try {
            Context contextCreatePackageContext = view.getContext().createPackageContext("com.miuix.editor", 3);
            if (this.mToolbarHelperClass == null) {
                this.mToolbarHelperClass = contextCreatePackageContext.getClassLoader().loadClass("miuix.toolbar.FloatingActionModeHelper");
            }
            if (this.mFloatingActionModeHelper == null) {
                this.mFloatingActionModeHelper = this.mToolbarHelperClass.getConstructor(Context.class).newInstance(contextCreatePackageContext);
            }
            if (this.mActionModeMethod == null) {
                this.mActionModeMethod = this.mToolbarHelperClass.getMethod("startActionMode", View.class, ActionMode.Callback2.class);
            }
            Object objInvoke = this.mActionModeMethod.invoke(this.mFloatingActionModeHelper, view, (ActionMode.Callback2) callback);
            if (objInvoke != null) {
                return (ActionMode) objInvoke;
            }
            return null;
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "try catch editor reflect exception" + e.getMessage());
            return null;
        }
    }
}
