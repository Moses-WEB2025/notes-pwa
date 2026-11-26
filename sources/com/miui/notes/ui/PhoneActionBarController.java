package com.miui.notes.ui;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import com.miui.notes.R;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.theme.Theme;
import miuix.appcompat.app.Fragment;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneActionBarController extends BaseActionBarController {
    private static final String TAG = "PhoneActionBarController";
    private ImageView mHeaderHome;

    @Override // com.miui.notes.ui.BaseActionBarController
    public int getLayoutResource() {
        return R.layout.v12_phone_edit_header;
    }

    public PhoneActionBarController(Fragment fragment, BaseActionBarController.EditCallback editCallback) {
        super(fragment.getActivity(), editCallback);
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    public void initializeView(View view, INoteInfoHeader iNoteInfoHeader) {
        super.initializeView(view, iNoteInfoHeader);
        ImageView imageView = (ImageView) view.findViewById(R.id.home);
        this.mHeaderHome = imageView;
        imageView.setOnClickListener(this);
    }

    private int getActionButtonColorFromTheme(Theme theme) {
        Resources.Theme resTheme = theme.getResTheme(this.mActivity);
        TypedValue typedValue = new TypedValue();
        resTheme.resolveAttribute(R.attr.actionButtonColor, typedValue, true);
        return typedValue.data;
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    public void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache) {
        int actionButtonColorFromTheme = getActionButtonColorFromTheme(theme);
        this.mHeaderHome.setImageDrawable(noteThemeResCache.actionBarHomeIcon);
        this.mHeaderHome.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mUndoView.setImageDrawable(noteThemeResCache.actionBarUndoIcon);
        this.mUndoView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mRedoView.setImageDrawable(noteThemeResCache.actionBarRedoIcon);
        this.mRedoView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mDoneView.setImageDrawable(noteThemeResCache.actionBarDoneIcon);
        this.mDoneView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mShareView.setImageDrawable(noteThemeResCache.actionBarShareIcon);
        this.mShareView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mViewThemeView.setImageDrawable(noteThemeResCache.actionBarThemeIcon);
        this.mViewThemeView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mMoreView.setImageDrawable(noteThemeResCache.actionBarMoreIcon);
        this.mMoreView.setImageTintList(ColorStateList.valueOf(actionButtonColorFromTheme));
        this.mNoteInfoHeader.updateStyle(theme, noteThemeResCache);
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
        menu.findItem(R.id.send_to_desktop).setVisible(true);
    }

    @Override // com.miui.notes.ui.BaseActionBarController
    public void cancelAiMode() {
        super.cancelAiMode();
        resetViewState(this.mViewThemeView);
        this.mViewThemeView.setVisibility(0);
    }
}
