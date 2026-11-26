package com.miui.pad.feature.notes.mindnote;

import android.view.View;
import android.webkit.WebView;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.ui.BaseEditorActionBarController;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.navigator.Navigator;

/* JADX INFO: compiled from: PadWebMindNoteFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J-\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0016\u0010\u0006\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\b0\u0007\"\u0004\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u0003H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0016¨\u0006\u0010"}, d2 = {"com/miui/pad/feature/notes/mindnote/PadWebMindNoteFragment$mEditCallback$1", "Lcom/miui/notes/ui/BaseEditorActionBarController$EditCallback;", "onPerformAction", "", "action", "", "args", "", "", "(I[Ljava/lang/Object;)V", "onNavigationUp", "getOptionResources", "", "getOptionMenuText", "", "resourceId", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadWebMindNoteFragment$mEditCallback$1 implements BaseEditorActionBarController.EditCallback {
    final /* synthetic */ PadWebMindNoteFragment this$0;

    @Override // com.miui.notes.ui.BaseEditorActionBarController.EditCallback
    public void onNavigationUp() {
    }

    PadWebMindNoteFragment$mEditCallback$1(PadWebMindNoteFragment padWebMindNoteFragment) {
        this.this$0 = padWebMindNoteFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onPerformAction$lambda$0(PadWebMindNoteFragment padWebMindNoteFragment, View view) {
        if (padWebMindNoteFragment.isAdded()) {
            padWebMindNoteFragment.showImmersionMenu(view, null);
        }
    }

    @Override // com.miui.common.base.ActionCallBack
    public void onPerformAction(int action, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (action == 17) {
            this.this$0.undo();
            return;
        }
        if (action == 18) {
            this.this$0.redo();
            return;
        }
        if (action == 23) {
            long j = this.this$0.mIsKeyboardVisible ? 300 : 200;
            Object obj = args[0];
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.View");
            final View view = (View) obj;
            if (this.this$0.mIsKeyboardVisible) {
                FragmentActivity fragmentActivityRequireActivity = this.this$0.requireActivity();
                Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
                Utils.hideSoftInput(fragmentActivityRequireActivity);
            }
            WebView webView = this.this$0.mWebView;
            final PadWebMindNoteFragment padWebMindNoteFragment = this.this$0;
            webView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.notes.mindnote.PadWebMindNoteFragment$mEditCallback$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PadWebMindNoteFragment$mEditCallback$1.onPerformAction$lambda$0(padWebMindNoteFragment, view);
                }
            }, j);
            return;
        }
        if (action != 25) {
            switch (action) {
                case 29:
                    this.this$0.mWebView.clearFocus();
                    Utils.hideSoftInput(this.this$0.mWebView);
                    break;
                case 30:
                    this.this$0.mWebView.clearFocus();
                    Utils.hideSoftInput(this.this$0.mWebView);
                    if (!Navigator.get(this.this$0).isContentOpen()) {
                        this.this$0.setNaviState(true);
                        Navigator.get(this.this$0).openContent();
                    }
                    break;
                case 31:
                    this.this$0.mWebView.clearFocus();
                    Utils.hideSoftInput(this.this$0.mWebView);
                    if (Navigator.get(this.this$0).isContentOpen()) {
                        this.this$0.setNaviState(false);
                        Navigator.get(this.this$0).closeContent();
                    }
                    break;
                default:
                    switch (action) {
                        case 50:
                            this.this$0.addChildNode();
                            break;
                        case 51:
                            this.this$0.addBrotherNode();
                            break;
                        case 52:
                            this.this$0.deleteNode();
                            break;
                        case 53:
                            this.this$0.changeViewMode(0);
                            this.this$0.onViewModeChanged(0);
                            this.this$0.mMindContentType = 0;
                            this.this$0.changeContentMargin();
                            break;
                        case 54:
                            this.this$0.changeViewMode(1);
                            this.this$0.onViewModeChanged(1);
                            this.this$0.mMindContentType = 1;
                            this.this$0.changeContentMargin();
                            break;
                        case 55:
                            this.this$0.levelIncrease();
                            break;
                        case 56:
                            this.this$0.levelReduce();
                            break;
                    }
                    break;
            }
            return;
        }
        if (this.this$0.isAdded()) {
            Logger.INSTANCE.d(this.this$0.getTAG(), "onPerformAction: ACTION_HOME");
            this.this$0.handleBackPress();
        }
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController.EditCallback
    public int[] getOptionResources() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.string.undo));
        arrayList.add(Integer.valueOf(R.string.redo));
        arrayList.add(Integer.valueOf(R.string.option_menu_save_as_pic));
        WorkingNote workingNote = this.this$0.mWorkingNote;
        Intrinsics.checkNotNull(workingNote);
        if (!workingNote.isHidden()) {
            arrayList.add(Integer.valueOf(R.string.menu_move_to));
        }
        arrayList.add(Integer.valueOf(R.string.option_menu_share));
        arrayList.add(Integer.valueOf(R.string.menu_delete));
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            Object obj = arrayList.get(i);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            iArr[i] = ((Number) obj).intValue();
        }
        return iArr;
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController.EditCallback
    public CharSequence getOptionMenuText(int resourceId) {
        if (this.this$0.getActivity() == null || !this.this$0.isAdded()) {
            return null;
        }
        return this.this$0.getString(resourceId);
    }
}
