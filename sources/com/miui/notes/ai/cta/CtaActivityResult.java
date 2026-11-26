package com.miui.notes.ai.cta;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import com.miui.notes.ai.utils.ReflectUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CtaActivityResult implements ActivityResultCallback<ActivityResult> {
    public abstract void onCtaActivityResult(ActivityResult activityResult);

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(ActivityResult activityResult) {
        int resultCode = activityResult.getResultCode();
        if (resultCode == 1) {
            try {
                ReflectUtils.callStaticMethod(Class.forName("com.miui.notes.cta.CTAHelper"), "agree", (Class<?>[]) null, new Object[0]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 666) {
            try {
                ReflectUtils.callStaticMethod(Class.forName("com.miui.notes.cta.CTAHelper"), "reject", (Class<?>[]) null, new Object[0]);
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        onCtaActivityResult(activityResult);
    }
}
