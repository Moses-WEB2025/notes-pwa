package com.miui.notes.editor;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.miui.common.tool.Utils;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.EditorActionListener;
import com.miui.richeditor.IEditorPopMenu;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseNoteRichEditorController {
    private static final String TAG = "BaseNoteRichEditorController";
    protected Activity mActivity;
    protected NoteRichEditor mNoteRichEditor;
    protected EditText mNoteTitleEditor;
    protected IEditorPopMenu.PopMenuListener mPopMenuListener = null;
    protected IEditorPopMenu mImagePopMenu = null;
    protected IEditorPopMenu mShowingPopMenu = null;
    protected Runnable mDataLoadedCallback = null;
    protected Runnable mPendingTaskOnDataLoaded = null;

    protected abstract EditorActionListener createActionListener();

    public BaseNoteRichEditorController(Activity activity, NoteRichEditor noteRichEditor, EditText editText) {
        this.mActivity = activity;
        this.mNoteRichEditor = noteRichEditor;
        noteRichEditor.setEditorActionListener(createActionListener());
        this.mNoteTitleEditor = editText;
    }

    protected Context getContext() {
        return this.mNoteRichEditor.getContext();
    }

    public void setDataLoadedCallback(Runnable runnable) {
        this.mDataLoadedCallback = runnable;
    }

    public void setPendingTaskOnDataLoaded(Runnable runnable) {
        this.mPendingTaskOnDataLoaded = runnable;
    }

    public void setMenuListener(IEditorPopMenu.PopMenuListener popMenuListener) {
        this.mPopMenuListener = popMenuListener;
    }

    public void showInputMethod() {
        this.mNoteRichEditor.requestFocus();
        Logger.INSTANCE.i(TAG, "showSoftInput");
        Utils.showSoftInput(this.mNoteRichEditor, true);
    }

    @Deprecated
    public void showSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager.isActive(this.mNoteRichEditor) && this.mNoteRichEditor.isFocusable()) {
            return;
        }
        this.mNoteRichEditor.clearFocus();
        this.mNoteRichEditor.requestFocus();
        inputMethodManager.showSoftInput(this.mNoteRichEditor, 0);
    }

    public void forceShowInputMethod() {
        this.mNoteRichEditor.requestFocus();
        Logger.INSTANCE.i(TAG, "forceShowSoftInput");
        Utils.showSoftInput(this.mNoteRichEditor);
    }

    public void hideInputMethod() {
        Logger.INSTANCE.i(TAG, "hideInputMethod");
        Utils.hideSoftInput(this.mNoteRichEditor);
    }

    public void showLoadingView(boolean z) {
        if (z) {
            this.mNoteTitleEditor.setVisibility(4);
            this.mNoteRichEditor.showLoadingView();
            return;
        }
        this.mNoteTitleEditor.setVisibility(0);
        this.mNoteRichEditor.hideLoadingView();
        Runnable runnable = this.mDataLoadedCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void dismissShowingPopupWindow() {
        IEditorPopMenu iEditorPopMenu = this.mShowingPopMenu;
        if (iEditorPopMenu == null || !iEditorPopMenu.isShowing()) {
            return;
        }
        this.mShowingPopMenu.dismiss();
        this.mShowingPopMenu = null;
    }
}
