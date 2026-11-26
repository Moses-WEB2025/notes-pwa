package com.miui.notes.tool;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.PopupWindow;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.ui.view.AppGestureGuideDialog;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class AppGuideUtils {
    @Deprecated
    public static AlertDialog showGestureGuideDialog(Activity activity, final Runnable runnable) {
        if (LiteUtils.isLiteOrMiddle()) {
            return null;
        }
        if (PreferenceUtils.getPrefShowGestureGuide()) {
            if (runnable != null) {
                runnable.run();
            }
            return null;
        }
        AppGestureGuideDialog appGestureGuideDialog = new AppGestureGuideDialog(activity);
        appGestureGuideDialog.show();
        PreferenceUtils.setPrefShowGestureGuide(true);
        appGestureGuideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.tool.AppGuideUtils.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        return appGestureGuideDialog;
    }

    public static PopupWindow showQuickNoteTipsWindowOnce(Context context, View view) {
        if (PreferenceUtils.getBooleanPreference(context, PreferenceUtils.PREF_KEY_SHOW_QUICK_NOTE_NEW_USER_GUIDE_BUBBLE, false)) {
            return null;
        }
        QuickNoteTipPopWindow quickNoteTipPopWindow = new QuickNoteTipPopWindow(context);
        quickNoteTipPopWindow.setGuideText(R.string.quick_note_new_user_guide);
        if (RomUtils.isPadMode()) {
            quickNoteTipPopWindow.setArrowMode(8);
            quickNoteTipPopWindow.show(view, false);
        } else {
            quickNoteTipPopWindow.setArrowMode(10);
            if (!Utils.isRTL()) {
                quickNoteTipPopWindow.show(view, (-view.getWidth()) / 2, -20, false);
            } else {
                quickNoteTipPopWindow.show(view, view.getWidth() / 2, -20, false);
            }
        }
        PreferenceUtils.setBooleanPreference(context, PreferenceUtils.PREF_KEY_SHOW_QUICK_NOTE_NEW_USER_GUIDE_BUBBLE, true);
        return quickNoteTipPopWindow;
    }
}
