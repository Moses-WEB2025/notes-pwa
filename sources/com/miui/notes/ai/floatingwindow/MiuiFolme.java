package com.miui.notes.ai.floatingwindow;

import android.view.View;
import com.miui.notes.base.utils.Logger;
import miuix.animation.Folme;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiFolme {
    private static final String TAG = "MiuiFolme";

    public static void cleanFolme(View... viewArr) {
        try {
            Folme.clean(viewArr);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, e.getMessage());
        }
    }

    public static void goneView(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    public static void visibleView(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setVisibility(0);
            }
        }
    }

    public static void alphaShowView(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setAlpha(1.0f);
            }
        }
    }
}
