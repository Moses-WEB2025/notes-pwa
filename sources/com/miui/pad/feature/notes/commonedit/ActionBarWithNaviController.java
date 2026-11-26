package com.miui.pad.feature.notes.commonedit;

import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.R;
import com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.ui.BaseActionBarController;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.theme.Theme;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ActionBarWithNaviController extends BaseActionBarController {
    private static final String TAG = "PadHeaderController";
    protected Fragment mFragment;
    protected ImageView mHeaderHome;
    private boolean mHomeButtonUseForHome;
    private boolean mIsNaviShow;
    protected Theme mTheme;

    protected abstract Drawable getBackIcon();

    protected abstract Drawable getCollapseIcon();

    protected abstract Drawable getExpandIcon();

    public ActionBarWithNaviController(Fragment fragment, BaseActionBarController.EditCallback editCallback) {
        super(fragment.getActivity(), editCallback);
        this.mHomeButtonUseForHome = false;
        this.mIsNaviShow = false;
        this.mFragment = fragment;
    }

    public void setHomeButtonState(boolean z) {
        this.mHomeButtonUseForHome = z;
        if (this.mHeaderHome == null) {
            return;
        }
        updateHomeButton();
    }

    public boolean getNaviState() {
        return this.mHomeButtonUseForHome || !this.mIsNaviShow;
    }

    public void showOrHideNaviState(boolean z) {
        if (this.mHomeButtonUseForHome) {
            this.mEditCallback.onPerformAction(25, new Object[0]);
        } else if (this.mIsNaviShow && !z) {
            this.mEditCallback.onPerformAction(31, new Object[0]);
        } else {
            this.mEditCallback.onPerformAction(30, new Object[0]);
        }
    }

    public void setNaviState(boolean z) {
        this.mIsNaviShow = z;
        ImageView imageView = this.mHeaderHome;
        if (imageView == null || this.mHomeButtonUseForHome || this.mTheme == null) {
            return;
        }
        if (z) {
            imageView.setImageDrawable(getExpandIcon());
            ImageView imageView2 = this.mHeaderHome;
            imageView2.setContentDescription(imageView2.getContext().getString(R.string.pad_expand_navigation));
        } else {
            imageView.setImageDrawable(getCollapseIcon());
            ImageView imageView3 = this.mHeaderHome;
            imageView3.setContentDescription(imageView3.getContext().getString(R.string.pad_collpase_navigation));
        }
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    protected void initializeView(View view) {
        this.mHeaderPanel = (ViewGroup) view;
        this.mEditGroup = view.findViewById(R.id.edit_group);
        this.mEditGroupXoffset = view.getResources().getDimensionPixelOffset(R.dimen.v12_notes_edit_header_ic_size) + view.getResources().getDimensionPixelOffset(R.dimen.v12_notes_edit_header_ic_margin);
        if (Utils.isRTL()) {
            this.mEditGroupXoffset = -this.mEditGroupXoffset;
        }
        this.mUndoView = (ImageView) view.findViewById(R.id.undo);
        this.mUndoView.setOnClickListener(this);
        this.mRedoView = (ImageView) view.findViewById(R.id.redo);
        this.mRedoView.setOnClickListener(this);
        this.mDoneView = (ImageView) view.findViewById(R.id.done);
        this.mDoneView.setOnClickListener(this);
        this.mShareView = (ImageView) view.findViewById(R.id.share);
        this.mShareView.setOnClickListener(this);
        this.mViewThemeView = (ImageView) view.findViewById(R.id.view_theme);
        this.mViewThemeView.setOnClickListener(this);
        this.mMoreView = (ImageView) view.findViewById(R.id.more);
        this.mMoreView.setOnClickListener(this);
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    public void initializeView(View view, INoteInfoHeader iNoteInfoHeader) {
        super.initializeView(view, iNoteInfoHeader);
        this.mViewThemeView.setEnabled(false);
        ImageView imageView = (ImageView) view.findViewById(R.id.home);
        this.mHeaderHome = imageView;
        imageView.setOnClickListener(this);
        UIUtils.setFolmeHoverEffect(this.mHeaderHome);
        this.mTheme = ResourceManager.getTheme(0);
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    public void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
        updateHomeButton();
        this.mUndoView.setImageDrawable(noteThemeResCache.actionBarUndoIcon);
        this.mRedoView.setImageDrawable(noteThemeResCache.actionBarRedoIcon);
        this.mDoneView.setImageDrawable(noteThemeResCache.actionBarDoneIcon);
        this.mShareView.setImageDrawable(noteThemeResCache.actionBarShareIcon);
        this.mViewThemeView.setImageDrawable(noteThemeResCache.actionBarThemeIcon);
        this.mMoreView.setImageDrawable(noteThemeResCache.actionBarMoreIcon);
        this.mNoteInfoHeader.updateStyle(theme, noteThemeResCache);
    }

    @Override // com.miui.notes.ui.BaseActionBarController, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.home) {
            showOrHideNaviState(false);
            BaseHybridNoteEditFragment.removeAiFloatingWindow();
        } else {
            super.onClick(view);
        }
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    public void updateMenu(Menu menu) {
        if (this.mEditCallback.isNoteHidden()) {
            menu.findItem(R.id.hide).setVisible(false);
            menu.findItem(R.id.unhide).setVisible(true);
            menu.findItem(R.id.move_to).setVisible(false);
            menu.findItem(R.id.send_to_desktop).setVisible(false);
            return;
        }
        menu.findItem(R.id.hide).setVisible(true);
        menu.findItem(R.id.unhide).setVisible(false);
        menu.findItem(R.id.move_to).setVisible(true);
        Logger.INSTANCE.d(TAG, "updateMenu RomUtils.isPadDevice(): " + RomUtils.isPadDevice() + " UIUtils.isMiuiWidgetSupported(mFragment.getContext()): " + UIUtils.isMiuiWidgetSupported(this.mFragment.getContext()));
        if (UIUtils.isMiuiWidgetSupported(this.mFragment.getContext())) {
            if (RomUtils.isPadDevice()) {
                menu.findItem(R.id.send_to_desktop).setVisible(false);
                return;
            } else {
                menu.findItem(R.id.send_to_desktop).setVisible(true);
                return;
            }
        }
        menu.findItem(R.id.send_to_desktop).setVisible(false);
    }

    private void updateHomeButton() {
        if (this.mHomeButtonUseForHome) {
            this.mHeaderHome.setImageDrawable(getBackIcon());
            ImageView imageView = this.mHeaderHome;
            imageView.setContentDescription(imageView.getContext().getString(R.string.actionbar_home_description));
        } else if (this.mIsNaviShow) {
            this.mHeaderHome.setImageDrawable(getExpandIcon());
            ImageView imageView2 = this.mHeaderHome;
            imageView2.setContentDescription(imageView2.getContext().getString(R.string.pad_expand_navigation));
        } else {
            this.mHeaderHome.setImageDrawable(getCollapseIcon());
            ImageView imageView3 = this.mHeaderHome;
            imageView3.setContentDescription(imageView3.getContext().getString(R.string.pad_collpase_navigation));
        }
    }
}
