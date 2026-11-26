package com.miui.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.component.noteedit.IBaseNoteEditToolbarController;
import com.miui.notes.component.noteedit.LiteSmallScreenNoteEditToolBarController;
import com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneNoteLiteEditFragment extends PhoneNativeNoteEditFragment {
    private static final String TAG = "PhoneNoteLiteEditFragment";
    public static View liteEditView;
    private ProgressBar frameProgressBar;

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected int getLayoutResId() {
        return R.layout.phone_lite_note_edit;
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected BaseActionBarController createHeaderBarController(View view) {
        return new PhoneLiteActionBarController(this, this);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected IBaseNoteEditToolbarController createToolBarController(View view) {
        return new LiteSmallScreenNoteEditToolBarController(getActivity(), view, this.mEditorView);
    }

    @Override // com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Logger.INSTANCE.d(TAG, "onResume");
        Intent intent = getActivity().getIntent();
        if (intent == null || !intent.getBooleanExtra(EditActivity.KEYGUARD_SHORTCUT, false)) {
            return;
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ui.PhoneNoteLiteEditFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onResume$0();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$0() {
        Logger.INSTANCE.d(TAG, "onResume isRecording = " + this.mIsRecording);
        if (this.mEditorController == null || this.mIsRecording) {
            return;
        }
        this.mEditorController.showInputMethod();
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate;
        Logger.INSTANCE.d(TAG, "onInflateView: " + liteEditView);
        View view = liteEditView;
        if (view == null || view.getParent() != null) {
            Logger.INSTANCE.d(TAG, "liteEditView: " + liteEditView);
            viewInflate = layoutInflater.inflate(getLayoutResId(), viewGroup, false);
        } else {
            viewInflate = liteEditView;
        }
        liteEditView = null;
        this.frameProgressBar = (ProgressBar) viewInflate.findViewById(R.id.frame_progress_bar);
        this.mHandler.postDelayed(new Runnable() { // from class: com.miui.notes.ui.PhoneNoteLiteEditFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onInflateView$1();
            }
        }, 1500L);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInflateView$1() {
        ProgressBar progressBar = this.frameProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void updateNoteTheme(Theme theme, boolean z) {
        if (this.mCurrentTheme != null) {
            return;
        }
        Theme theme2 = ResourceManager.getTheme(0);
        if (this.mIsChangingTheme) {
            return;
        }
        this.mEditToolbarController.lock();
        super.updateNoteTheme(theme2, z);
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment
    protected NoteThemeResCache loadNoteThemeResToCache(Theme theme) {
        return super.loadNoteThemeResToCache(ResourceManager.getTheme(0));
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void onPostUpdateNoteTheme(Theme theme, NoteThemeResCache noteThemeResCache, boolean z) {
        this.mEditorView.onThemeChanged(theme);
        this.mIsForceDark = theme.getStatusBarStyle(getActivity()).mStatusBar == 2;
        getActionBarController().updateStyle(theme, noteThemeResCache);
        updateWindowStatus(this.mIsForceDark);
        updateEditContentBgStyle(theme, z);
        updateEditorTheme(theme, Boolean.valueOf(z));
        if (this.mCurrentTheme == null || !this.mCurrentTheme.equals(theme)) {
            this.mCurrentTheme = theme;
        }
        this.mEditToolbarController.updateTheme(theme, noteThemeResCache);
        if (this.mAnnotationController != null) {
            this.mAnnotationController.updateStyle(theme);
        }
        if (this.mSharePopupWindow != null) {
            this.mSharePopupWindow.updateStyle(theme);
        }
    }

    @Override // com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment
    protected void updateEditorTheme(Theme theme, Boolean bool) {
        if (theme.getId() != 0) {
            theme = ResourceManager.getTheme(0);
        }
        super.updateEditorTheme(theme, bool);
    }

    @Override // com.miui.notes.feature.noteedit.PhoneNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNativeNoteEditFragment, com.miui.notes.basefeature.noteedit.BaseNormalNoteEditFragment, com.miui.notes.detail.BaseNoteDetailFragment, miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        liteEditView = null;
        super.onDestroy();
    }
}
