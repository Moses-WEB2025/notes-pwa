package com.miui.notes.home;

import android.os.Bundle;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import com.miui.notes.ui.NotesListActivity;

/* JADX INFO: loaded from: classes4.dex */
public class PrivatePadActivityController extends PadActivityController {
    public PrivatePadActivityController(NotesListActivity notesListActivity) {
        super(notesListActivity);
    }

    @Override // com.miui.notes.home.PadActivityController, com.miui.notes.basefeature.IActivityController
    public void onCreate() {
        getHomeViewModel().getToHomeFromOutMode().observe(getActivity(), new Observer() { // from class: com.miui.notes.home.PrivatePadActivityController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$onCreate$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(Boolean bool) {
        if (bool.booleanValue()) {
            if (this.toolLayout == null) {
                getActivity().onBackPressed();
                return;
            }
            unregisterNoteEditKeyboardDetector();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.toolLayout.getLayoutParams();
            marginLayoutParams.bottomMargin = 0;
            this.toolLayout.setLayoutParams(marginLayoutParams);
            this.mHandler.post(new Runnable() { // from class: com.miui.notes.home.PrivatePadActivityController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreate$0();
                }
            });
            this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.home.PrivatePadActivityController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.registerNoteEditKeyboardDetector();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        getActivity().onBackPressed();
        getHomeViewModel().setKeyBoardShowStatus(false);
    }

    @Override // com.miui.notes.home.PadActivityController, com.miui.notes.basefeature.IActivityController
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        registerNoteEditKeyboardDetector();
    }
}
