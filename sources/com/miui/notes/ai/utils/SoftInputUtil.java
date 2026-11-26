package com.miui.notes.ai.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class SoftInputUtil {
    private static final String TAG = "SoftInputUtil";

    public static void hideSoftInput(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftInput(final View view) {
        final InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            Logger.INSTANCE.i(TAG, "showSoftInput");
            view.requestFocus();
            if (!inputMethodManager.showSoftInput(view, 2)) {
                view.postDelayed(new Runnable() { // from class: com.miui.notes.ai.utils.SoftInputUtil$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SoftInputUtil.lambda$showSoftInput$0(view, inputMethodManager);
                    }
                }, 50L);
            } else {
                Logger.INSTANCE.i(TAG, "showSoftInput success");
            }
        }
    }

    static /* synthetic */ void lambda$showSoftInput$0(View view, InputMethodManager inputMethodManager) {
        Logger.INSTANCE.i(TAG, "showSoftInput retry again");
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 2);
    }
}
