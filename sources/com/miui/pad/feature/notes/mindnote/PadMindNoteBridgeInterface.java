package com.miui.pad.feature.notes.mindnote;

import android.webkit.JavascriptInterface;
import androidx.fragment.app.FragmentActivity;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PadMindNoteBridgeInterface.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0011\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0007J\u0018\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0007J\u0018\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\rH\u0007R\u0014\u0010\u0006\u001a\u00020\u00078WX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/miui/pad/feature/notes/mindnote/PadMindNoteBridgeInterface;", "Lcom/miui/notes/basefeature/mindnote/BaseMindNoteBridgeInterface;", "Lcom/miui/pad/feature/notes/mindnote/PadWebMindNoteFragment;", "fragment", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/pad/feature/notes/mindnote/PadWebMindNoteFragment;)V", "uiMode", "", "getUiMode", "()I", "updateMapBtnState", "", "addChildEnable", "", "addBrotherEnable", "deleteEnable", "updateOverviewBtnState", "levelReduceEnable", "levelIncreaseEnable", "updateUndoRedoEnable", "undoEnable", "redoEnable", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadMindNoteBridgeInterface extends BaseMindNoteBridgeInterface<PadWebMindNoteFragment> {
    public PadMindNoteBridgeInterface(PadWebMindNoteFragment padWebMindNoteFragment) {
        super(padWebMindNoteFragment);
    }

    @Override // com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface
    @JavascriptInterface
    public int getUiMode() {
        if (!hasReference()) {
            return 1;
        }
        PadWebMindNoteFragment baseFragment = getBaseFragment();
        Intrinsics.checkNotNull(baseFragment);
        return baseFragment.getUiMode();
    }

    @JavascriptInterface
    public final void updateMapBtnState(final String addChildEnable, final String addBrotherEnable, final String deleteEnable) {
        PadWebMindNoteFragment baseFragment;
        FragmentActivity activity;
        Intrinsics.checkNotNullParameter(addChildEnable, "addChildEnable");
        Intrinsics.checkNotNullParameter(addBrotherEnable, "addBrotherEnable");
        Intrinsics.checkNotNullParameter(deleteEnable, "deleteEnable");
        if (!hasReference() || (baseFragment = getBaseFragment()) == null || (activity = baseFragment.getActivity()) == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadMindNoteBridgeInterface$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PadMindNoteBridgeInterface.updateMapBtnState$lambda$0(this.f$0, addChildEnable, addBrotherEnable, deleteEnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateMapBtnState$lambda$0(PadMindNoteBridgeInterface padMindNoteBridgeInterface, String str, String str2, String str3) {
        PadMindNoteToolBarController toolBarController;
        PadWebMindNoteFragment baseFragment = padMindNoteBridgeInterface.getBaseFragment();
        if (baseFragment == null || (toolBarController = baseFragment.getToolBarController()) == null) {
            return;
        }
        toolBarController.updateMapBtnState(Boolean.parseBoolean(str), Boolean.parseBoolean(str2), Boolean.parseBoolean(str3));
    }

    @JavascriptInterface
    public final void updateOverviewBtnState(final String levelReduceEnable, final String levelIncreaseEnable) {
        PadWebMindNoteFragment baseFragment;
        FragmentActivity activity;
        Intrinsics.checkNotNullParameter(levelReduceEnable, "levelReduceEnable");
        Intrinsics.checkNotNullParameter(levelIncreaseEnable, "levelIncreaseEnable");
        if (!hasReference() || (baseFragment = getBaseFragment()) == null || (activity = baseFragment.getActivity()) == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadMindNoteBridgeInterface$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PadMindNoteBridgeInterface.updateOverviewBtnState$lambda$1(this.f$0, levelReduceEnable, levelIncreaseEnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateOverviewBtnState$lambda$1(PadMindNoteBridgeInterface padMindNoteBridgeInterface, String str, String str2) {
        PadMindNoteToolBarController toolBarController;
        PadWebMindNoteFragment baseFragment = padMindNoteBridgeInterface.getBaseFragment();
        if (baseFragment == null || (toolBarController = baseFragment.getToolBarController()) == null) {
            return;
        }
        toolBarController.updateOverviewBtnState(Boolean.parseBoolean(str), Boolean.parseBoolean(str2));
    }

    @JavascriptInterface
    public final void updateUndoRedoEnable(final String undoEnable, final String redoEnable) {
        FragmentActivity fragmentActivityRequireActivity;
        Intrinsics.checkNotNullParameter(undoEnable, "undoEnable");
        Intrinsics.checkNotNullParameter(redoEnable, "redoEnable");
        if (hasReference()) {
            Logger.INSTANCE.i(BaseMindNoteBridgeInterface.TAG, "undoEnable = " + undoEnable + " redoEnable = " + redoEnable);
            PadWebMindNoteFragment baseFragment = getBaseFragment();
            if (baseFragment == null || (fragmentActivityRequireActivity = baseFragment.requireActivity()) == null) {
                return;
            }
            fragmentActivityRequireActivity.runOnUiThread(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadMindNoteBridgeInterface$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PadMindNoteBridgeInterface.updateUndoRedoEnable$lambda$2(this.f$0, undoEnable, redoEnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateUndoRedoEnable$lambda$2(PadMindNoteBridgeInterface padMindNoteBridgeInterface, String str, String str2) {
        PadMindNoteActionBarController actionBarController;
        PadMindNoteActionBarController actionBarController2;
        PadWebMindNoteFragment baseFragment = padMindNoteBridgeInterface.getBaseFragment();
        if (baseFragment != null && (actionBarController2 = baseFragment.getActionBarController()) != null) {
            actionBarController2.updateUndoEnable(Boolean.parseBoolean(str));
        }
        PadWebMindNoteFragment baseFragment2 = padMindNoteBridgeInterface.getBaseFragment();
        if (baseFragment2 == null || (actionBarController = baseFragment2.getActionBarController()) == null) {
            return;
        }
        actionBarController.updateRedoEnable(Boolean.parseBoolean(str2));
    }
}
