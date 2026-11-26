package com.miui.notes.ui.fragment;

import android.view.View;
import com.miui.notes.ui.ActionStateListener;
import miuix.appcompat.app.ActionBar;
import miuix.miuixbasewidget.widget.EmptyStateView;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ModuleFragment extends DialogManagedFragment {
    public ActionStateListener mActionStateListener;
    protected View mBtnContentAdd;
    private ActionBar mParentActionBar;
    protected EmptyStateView mListBlankView = null;
    protected boolean mIsSelected = false;

    public void enterSearchMode() {
    }

    public void exitSearchMode() {
    }

    public abstract String getSearchContent();

    public abstract boolean onBackPressed();

    public void onFragmentResume() {
    }

    public void setActionStateListener(ActionStateListener actionStateListener) {
        this.mActionStateListener = actionStateListener;
    }

    public void bindCreateContentBtn(View view) {
        this.mBtnContentAdd = view;
    }

    public void bindParentActionBar(ActionBar actionBar) {
        this.mParentActionBar = actionBar;
    }

    public ActionBar getParentActionBar() {
        return this.mParentActionBar;
    }

    public void onSelected(boolean z) {
        this.mIsSelected = z;
    }
}
