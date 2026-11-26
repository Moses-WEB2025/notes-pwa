package com.miui.pad.feature.notes;

import android.view.View;
import android.widget.ImageView;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.ui.BaseEditorActionBarController;
import com.miui.richeditor.theme.Theme;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public abstract class PadNoteActionBarController extends BaseEditorActionBarController {
    protected Fragment mFragment;
    protected ImageView mHeaderHome;
    private boolean mHomeButtonUseForHome;
    protected boolean mIsNaviShow;

    public PadNoteActionBarController(Fragment fragment, BaseEditorActionBarController.EditCallback editCallback) {
        super(fragment.getActivity(), editCallback);
        this.mHomeButtonUseForHome = false;
        this.mIsNaviShow = false;
        this.mFragment = fragment;
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController
    public void initializeView(View view) {
        super.initializeView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.home);
        this.mHeaderHome = imageView;
        imageView.setOnClickListener(this);
        Folme.useAt(this.mHeaderHome).touch().handleTouchOf(this.mHeaderHome, new AnimConfig[0]);
        UIUtils.setFolmeHoverEffect(this.mHeaderHome);
        updateHomeButton();
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController
    public void updateStyle(Theme theme) {
        super.updateStyle(theme);
        if (this.mHeaderHome != null) {
            updateHomeButton();
        }
    }

    @Override // com.miui.notes.ui.BaseEditorActionBarController, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.home) {
            showOrHideNaviState(false);
        } else {
            super.onClick(view);
        }
    }

    public boolean getNaviState() {
        return this.mHomeButtonUseForHome || !this.mIsNaviShow;
    }

    public void showOrHideNaviState(boolean z) {
        if (this.mHomeButtonUseForHome) {
            this.mActionCallback.onPerformAction(25, new Object[0]);
        } else if (this.mIsNaviShow && !z) {
            this.mActionCallback.onPerformAction(31, new Object[0]);
        } else {
            this.mActionCallback.onPerformAction(30, new Object[0]);
        }
    }

    public void setHomeButtonState(boolean z) {
        this.mHomeButtonUseForHome = z;
        if (this.mHeaderHome == null) {
            return;
        }
        updateHomeButton();
    }

    public void setNaviState(boolean z) {
        this.mIsNaviShow = z;
        ImageView imageView = this.mHeaderHome;
        if (imageView == null || this.mHomeButtonUseForHome) {
            return;
        }
        if (z) {
            imageView.setImageDrawable(this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_layout_expand));
            ImageView imageView2 = this.mHeaderHome;
            imageView2.setContentDescription(imageView2.getContext().getString(R.string.pad_expand_navigation));
        } else {
            imageView.setImageDrawable(this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_layout_collapse));
            ImageView imageView3 = this.mHeaderHome;
            imageView3.setContentDescription(imageView3.getContext().getString(R.string.pad_collpase_navigation));
        }
    }

    private void updateHomeButton() {
        if (this.mHomeButtonUseForHome) {
            this.mHeaderHome.setImageDrawable(this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_action_back));
            ImageView imageView = this.mHeaderHome;
            imageView.setContentDescription(imageView.getContext().getString(R.string.actionbar_home_description));
        } else if (this.mIsNaviShow) {
            this.mHeaderHome.setImageDrawable(this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_layout_expand));
            ImageView imageView2 = this.mHeaderHome;
            imageView2.setContentDescription(imageView2.getContext().getString(R.string.pad_expand_navigation));
        } else {
            this.mHeaderHome.setImageDrawable(this.mTheme.getResTheme(this.mFragment.getContext()).getDrawable(R.drawable.ic_layout_collapse));
            ImageView imageView3 = this.mHeaderHome;
            imageView3.setContentDescription(imageView3.getContext().getString(R.string.pad_collpase_navigation));
        }
    }
}
