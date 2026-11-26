package com.miui.notes.assist;

import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import com.miui.notes.NoteApp;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CtaActivityResult implements ActivityResultCallback<ActivityResult> {
    public abstract void onCtaActivityResult(ActivityResult activityResult);

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(ActivityResult activityResult) {
        int resultCode = activityResult.getResultCode();
        if (resultCode == 1) {
            PreferenceUtils.setFirstHandle(false, NoteApp.getInstance());
            PreferenceUtils.setCTAAccepted(true, NoteApp.getInstance());
            PreferenceUtils.setAllowNetwork(NoteApp.getInstance(), true);
            Bundle bundle = new Bundle();
            bundle.putInt("push_type", 0);
            SyncUtils.requestSync(NoteApp.getInstance(), bundle, true);
        } else if (resultCode == 666) {
            PreferenceUtils.setFirstHandle(false, NoteApp.getInstance());
        }
        onCtaActivityResult(activityResult);
    }
}
