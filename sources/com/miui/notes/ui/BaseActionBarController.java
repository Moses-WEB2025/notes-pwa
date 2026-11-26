package com.miui.notes.ui;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.event.LiveDataEventBus;
import com.miui.notes.R;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.notes.ui.view.INoteInfoHeader;
import com.miui.richeditor.EditorActionMode;
import com.miui.richeditor.theme.Theme;
import java.lang.ref.WeakReference;
import miuix.animation.Folme;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseActionBarController implements View.OnClickListener {
    public static final int ACTION_ADD_BROTHER_NODE = 51;
    public static final int ACTION_ADD_CHILD_NODE = 50;
    public static final int ACTION_CLICK_BLANK = 29;
    public static final int ACTION_CLOSE_NAVI = 31;
    public static final int ACTION_DELETE = 24;
    public static final int ACTION_DELETE_NODE = 52;
    public static final int ACTION_DONE = 20;
    public static final int ACTION_EDIT_THEME = 19;
    public static final int ACTION_HIDE = 13;
    public static final int ACTION_HOME = 25;

    @Deprecated
    public static final int ACTION_LAUNCH_SOURCE = 10;
    public static final int ACTION_LEVEL_INCREASE = 55;
    public static final int ACTION_LEVEL_REDUCE = 56;
    public static final int ACTION_MAP_MODE = 53;
    public static final int ACTION_MORE = 23;
    public static final int ACTION_MOVE_TO = 16;
    public static final int ACTION_OPEN_NAVI = 30;
    public static final int ACTION_OVERVIEW_MODE = 54;
    public static final int ACTION_REDO = 18;
    public static final int ACTION_SEND = 5;
    public static final int ACTION_SEND_AS_DUPLICATE = 58;
    public static final int ACTION_SEND_AS_IMAGE = 15;
    public static final int ACTION_SEND_AS_MARKDOWN = 57;
    public static final int ACTION_SEND_TO_LAUNCHER = 6;
    public static final int ACTION_SET_ALARM = 8;

    @Deprecated
    public static final int ACTION_SET_COLOR = 9;
    public static final int ACTION_SHARE = 21;
    public static final int ACTION_SHOW_SET_ALARM = 7;
    public static final int ACTION_UNDO = 17;
    public static final int ACTION_UNHIDE = 14;
    public static final int ACTION_VIEW_THEME = 22;
    public static final int ATTACHMENT_TYPE_CAMERA = 2;
    public static final int ATTACHMENT_TYPE_GALLERY = 1;
    public static final boolean IS_SUPER_LITE = LiteUtils.isSuperLite();
    protected static final SparseIntArray sBgSelectorBtnsMap;
    protected Activity mActivity;
    protected ImageView mDoneView;
    protected EditCallback mEditCallback;
    protected View mEditGroup;
    private EditorActionMode mEditorActionMode;
    protected ViewGroup mHeaderPanel;
    private boolean mInAiMode;
    private boolean mIsEditorEmpty;
    protected ImageView mMoreView;
    protected INoteInfoHeader mNoteInfoHeader;
    protected ImageView mRedoView;
    protected ImageView mShareView;
    protected ImageView mUndoView;
    protected ImageView mViewThemeView;
    boolean setQuick;
    private final String TAG = getClass().getSimpleName();
    protected int mEditGroupXoffset = 0;
    private boolean mDoneViewVisible = false;
    private boolean mMoreViewVisible = false;

    public interface EditCallback {
        boolean isInvalid();

        boolean isNoteHidden();

        boolean isOnSaveInstanceCalled();

        void onNavigationUp();

        void onPerformAction(int i, Object... objArr);
    }

    protected abstract int getLayoutResource();

    public abstract void updateMenu(Menu menu);

    public abstract void updateStyle(Theme theme, NoteThemeResCache noteThemeResCache);

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sBgSelectorBtnsMap = sparseIntArray;
        sparseIntArray.append(R.id.pick_white, 0);
        sparseIntArray.append(R.id.pick_blue, 1);
        sparseIntArray.append(R.id.pick_yellow, 2);
        sparseIntArray.append(R.id.pick_green, 3);
        sparseIntArray.append(R.id.pick_red, 4);
    }

    public BaseActionBarController(Activity activity, EditCallback editCallback) {
        this.mActivity = activity;
        this.mEditCallback = editCallback;
    }

    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(getLayoutResource(), viewGroup, false);
    }

    public void initializeView(View view, INoteInfoHeader iNoteInfoHeader) {
        initializeView(view);
        initHeaderView(iNoteInfoHeader);
    }

    protected void initializeView(View view) {
        this.mHeaderPanel = (ViewGroup) view;
        this.mEditGroup = view.findViewById(R.id.edit_group);
        this.mEditGroupXoffset = view.getResources().getDimensionPixelOffset(R.dimen.ic_100px_size) + view.getResources().getDimensionPixelOffset(R.dimen.v12_notes_edit_header_ic_margin);
        if (Utils.isRTL()) {
            this.mEditGroupXoffset = -this.mEditGroupXoffset;
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.undo);
        this.mUndoView = imageView;
        imageView.setOnClickListener(this);
        this.mUndoView.setEnabled(false);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.redo);
        this.mRedoView = imageView2;
        imageView2.setOnClickListener(this);
        this.mRedoView.setEnabled(false);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.done);
        this.mDoneView = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.share);
        this.mShareView = imageView4;
        imageView4.setOnClickListener(this);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.view_theme);
        this.mViewThemeView = imageView5;
        imageView5.setOnClickListener(this);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.more);
        this.mMoreView = imageView6;
        imageView6.setOnClickListener(this);
        if (IS_SUPER_LITE) {
            this.mViewThemeView.setVisibility(8);
        }
    }

    protected void initHeaderView(INoteInfoHeader iNoteInfoHeader) {
        this.mNoteInfoHeader = iNoteInfoHeader;
    }

    public void update(WorkingNote workingNote, int i) {
        this.mNoteInfoHeader.update(workingNote, i);
    }

    public void updateTime(WorkingNote workingNote) {
        this.mNoteInfoHeader.updateDataTime(workingNote);
    }

    public void updateTextCount(int i) {
        this.mNoteInfoHeader.updateTextCount(i);
    }

    public void updateAlarm(WorkingNote workingNote) {
        this.mNoteInfoHeader.updateAlarm(workingNote);
        this.mNoteInfoHeader.getView().requestLayout();
        this.mNoteInfoHeader.getView().invalidate();
    }

    public View getHeadInfo() {
        return this.mNoteInfoHeader.getView();
    }

    public void updateHeadInfoMargin(Rect rect) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mNoteInfoHeader.getView().getLayoutParams();
        marginLayoutParams.leftMargin = rect.left;
        marginLayoutParams.rightMargin = rect.right;
        this.mNoteInfoHeader.getView().requestLayout();
        this.mNoteInfoHeader.getView().invalidate();
    }

    public void updateHeadInfoMarginInAnim(int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mNoteInfoHeader.getView().getLayoutParams();
        marginLayoutParams.leftMargin = i;
        marginLayoutParams.rightMargin = i2;
        this.mNoteInfoHeader.getView().requestLayout();
        this.mNoteInfoHeader.getView().invalidate();
    }

    public void updateNoteHiddenState(WorkingNote workingNote) {
        this.mNoteInfoHeader.updateNoteHiddenState(workingNote);
        this.mNoteInfoHeader.getView().requestLayout();
        this.mNoteInfoHeader.getView().invalidate();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done /* 2131362431 */:
                Logger.INSTANCE.d("BaseActionBarController", "onClick-done");
                this.mEditCallback.onPerformAction(20, new Object[0]);
                break;
            case R.id.home /* 2131362709 */:
                Logger.INSTANCE.d("BaseActionBarController", "onClick-home");
                this.mEditCallback.onPerformAction(25, new Object[0]);
                onNavigationUp();
                break;
            case R.id.more /* 2131362987 */:
                this.mEditCallback.onPerformAction(23, view);
                NotesTextStat.reportTopClickEvent("menu");
                break;
            case R.id.redo /* 2131363278 */:
                Logger.INSTANCE.d("BaseActionBarController", "onClick-redo");
                this.mEditCallback.onPerformAction(18, new Object[0]);
                break;
            case R.id.share /* 2131363452 */:
                LiveDataEventBus.with(LiveDataEventBus.SEARCH_CLEAR_FOCUS).setValue(true);
                this.mEditCallback.onPerformAction(21, new Object[0]);
                NotesTextStat.reportTopClickEvent("share");
                break;
            case R.id.undo /* 2131363735 */:
                Logger.INSTANCE.d("BaseActionBarController", "onClick-undo");
                this.mEditCallback.onPerformAction(17, new Object[0]);
                break;
            case R.id.view_theme /* 2131363775 */:
                this.mEditCallback.onPerformAction(22, new Object[0]);
                NotesTextStat.reportTopClickEvent("view_theme");
                break;
        }
    }

    public void updateActionMode(EditorActionMode editorActionMode, boolean z) {
        updateActionMode(editorActionMode, z, true);
    }

    public void setEditableQuick() {
        this.setQuick = true;
        this.mUndoView.setVisibility(0);
        this.mRedoView.setVisibility(0);
        this.mDoneView.setVisibility(0);
        this.mDoneViewVisible = true;
    }

    public void startAiMode() {
        this.mInAiMode = true;
        updateActionMode(EditorActionMode.VIEW, true, false);
        hideViewWithCleanAnim(this.mUndoView);
        hideViewWithCleanAnim(this.mRedoView);
        hideViewWithCleanAnim(this.mDoneView);
        hideViewWithCleanAnim(this.mViewThemeView);
        hideViewWithCleanAnim(this.mShareView);
        hideViewWithCleanAnim(this.mMoreView);
    }

    private void hideViewWithCleanAnim(View view) {
        if (view != null) {
            Folme.clean(view);
            view.setVisibility(8);
        }
    }

    public void cancelAiMode() {
        this.mInAiMode = false;
        resetViewState(this.mShareView);
        this.mShareView.setVisibility(0);
        resetViewState(this.mMoreView);
        this.mMoreView.setVisibility(0);
        updateActionMode(EditorActionMode.VIEW, false, false);
    }

    public void tempChangeUndoRedoView(boolean z) {
        if (z) {
            Folme.clean(this.mShareView);
            this.mShareView.setVisibility(8);
            if (operateViewTheme()) {
                Folme.clean(this.mViewThemeView);
                this.mViewThemeView.setVisibility(8);
            }
            Folme.clean(this.mMoreView);
            this.mMoreView.setVisibility(8);
            this.mMoreViewVisible = false;
            this.mDoneView.setVisibility(0);
            this.mEditGroup.setTranslationX(0.0f);
            resetViewState(this.mDoneView);
        }
        ImageView imageView = this.mRedoView;
        if (imageView != null) {
            Folme.clean(imageView);
            this.mRedoView.setVisibility(4);
        }
        ImageView imageView2 = this.mUndoView;
        if (imageView2 != null) {
            Folme.clean(imageView2);
            this.mUndoView.setVisibility(4);
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x00af  */
    public void updateActionMode(EditorActionMode editorActionMode, boolean z, boolean z2) {
        int i;
        Logger.INSTANCE.d(this.TAG, "updateActionMode actionMode：" + editorActionMode + " ,isEditorEmpty:" + z + " ,withAnim: " + z2);
        if (this.mInAiMode) {
            Logger.INSTANCE.i(this.TAG, "updateActionMode,AI Mode return");
            return;
        }
        if (this.mEditorActionMode == editorActionMode && this.mIsEditorEmpty == z) {
            Logger.INSTANCE.i(this.TAG, "updateActionMode,same mode return or EditorEmpty return,mEditorActionMode=" + this.mEditorActionMode + ",mIsEditorEmpty=" + this.mIsEditorEmpty);
            return;
        }
        this.mEditorActionMode = editorActionMode;
        this.mIsEditorEmpty = z;
        if (RomUtils.isPadMode() || this.setQuick || LiteUtils.isLiteOrMiddle() || LiteUtils.isNewLiteOrMiddle()) {
            z2 = false;
        }
        boolean zIsEditTextEnable = editorActionMode.isEditTextEnable();
        if ((zIsEditTextEnable && !z && !this.mDoneViewVisible) || (!zIsEditTextEnable && !this.mMoreViewVisible)) {
            int i2 = zIsEditTextEnable ? 0 : 8;
            int i3 = !zIsEditTextEnable ? 0 : 8;
            boolean z3 = IS_SUPER_LITE;
            if (z3) {
                if (zIsEditTextEnable && editorActionMode == EditorActionMode.EDIT) {
                    i = 0;
                } else {
                    i = 8;
                }
            } else if (zIsEditTextEnable && editorActionMode == EditorActionMode.EDIT) {
                i = 0;
            } else {
                i = 4;
            }
            if (z2) {
                showToolBarIconWithFolme(this.mUndoView, i);
                showToolBarIconWithFolme(this.mRedoView, i);
                if (z) {
                    this.mDoneView.setVisibility(z3 ? 8 : 4);
                    this.mEditGroup.animate().translationX(this.mEditGroupXoffset).start();
                    this.mDoneViewVisible = false;
                } else {
                    if (z3) {
                        this.mDoneView.setVisibility(i2);
                    } else {
                        showToolBarIconWithFolme(this.mDoneView, i2);
                    }
                    boolean z4 = i2 == 0;
                    this.mDoneViewVisible = z4;
                    this.mEditGroup.setTranslationX(z4 ? 0.0f : this.mEditGroupXoffset);
                }
                showToolBarIconWithFolme(this.mShareView, i3);
                if (operateViewTheme()) {
                    showToolBarIconWithFolme(this.mViewThemeView, i3);
                }
                showToolBarIconWithFolme(this.mMoreView, i3);
                this.mMoreViewVisible = i3 == 0;
            } else {
                resetViewState(this.mUndoView);
                this.mUndoView.setVisibility(i);
                resetViewState(this.mRedoView);
                this.mRedoView.setVisibility(i);
                resetViewState(this.mDoneView);
                this.mDoneView.setAlpha(1.0f);
                if (z) {
                    this.mDoneView.setVisibility(4);
                    this.mEditGroup.setTranslationX(this.mEditGroupXoffset);
                    this.mDoneViewVisible = false;
                } else {
                    this.mDoneView.setVisibility(i2);
                    boolean z5 = i2 == 0;
                    this.mDoneViewVisible = z5;
                    this.mEditGroup.setTranslationX(z5 ? 0.0f : this.mEditGroupXoffset);
                }
                resetViewState(this.mShareView);
                this.mShareView.setVisibility(i3);
                if (operateViewTheme()) {
                    resetViewState(this.mViewThemeView);
                    this.mViewThemeView.setVisibility(i3);
                }
                resetViewState(this.mMoreView);
                this.mMoreView.setVisibility(i3);
                this.mMoreViewVisible = i3 == 0;
            }
        } else if (editorActionMode == EditorActionMode.EDIT_RECODE) {
            Logger.INSTANCE.i(this.TAG, "updateActionMode 2");
            if (z2) {
                showToolBarIconWithFolme(this.mUndoView, 4);
                showToolBarIconWithFolme(this.mRedoView, 4);
            } else {
                this.mUndoView.setVisibility(4);
                this.mRedoView.setVisibility(4);
            }
        } else if (editorActionMode == EditorActionMode.EDIT) {
            Logger.INSTANCE.i("HeaderController", "updateActionMode 3");
            if (z2) {
                if (this.mShareView.getVisibility() == 0) {
                    showToolBarIconWithFolme(this.mShareView, 8);
                }
                if (operateViewTheme() && this.mViewThemeView.getVisibility() == 0) {
                    showToolBarIconWithFolme(this.mViewThemeView, 8);
                }
                if (this.mMoreView.getVisibility() == 0) {
                    showToolBarIconWithFolme(this.mMoreView, 8);
                }
                showToolBarIconWithFolme(this.mRedoView, 0);
                showToolBarIconWithFolme(this.mUndoView, 0);
                this.mMoreViewVisible = false;
                if (z) {
                    this.mDoneView.setVisibility(4);
                    this.mEditGroup.animate().translationX(this.mEditGroupXoffset).start();
                    this.mDoneViewVisible = false;
                } else if (this.mDoneView.getVisibility() != 0) {
                    showToolBarIconWithFolme(this.mDoneView, 0);
                    this.mEditGroup.animate().translationX(0.0f).start();
                    this.mDoneViewVisible = true;
                }
            } else {
                Folme.clean(this.mShareView);
                this.mShareView.setVisibility(8);
                if (operateViewTheme()) {
                    Folme.clean(this.mViewThemeView);
                    this.mViewThemeView.setVisibility(8);
                }
                Folme.clean(this.mMoreView);
                this.mMoreView.setVisibility(8);
                this.mMoreViewVisible = false;
                this.mDoneView.setVisibility(z ? 4 : 0);
                this.mDoneViewVisible = !z;
                this.mEditGroup.setTranslationX(z ? this.mEditGroupXoffset : 0.0f);
                if (this.mDoneViewVisible) {
                    resetViewState(this.mDoneView);
                }
                this.mRedoView.setVisibility(0);
                resetViewState(this.mRedoView);
                this.mUndoView.setVisibility(0);
                resetViewState(this.mUndoView);
            }
        } else if (editorActionMode == EditorActionMode.EDIT_TITLE) {
            Logger.INSTANCE.i("HeaderController", "updateActionMode 4");
            if (z2) {
                if (this.mShareView.getVisibility() == 0) {
                    showToolBarIconWithFolme(this.mShareView, 8);
                }
                if (operateViewTheme() && this.mViewThemeView.getVisibility() == 0) {
                    showToolBarIconWithFolme(this.mViewThemeView, 8);
                }
                if (this.mMoreView.getVisibility() == 0) {
                    showToolBarIconWithFolme(this.mMoreView, 8);
                }
                showToolBarIconWithFolme(this.mRedoView, 8);
                showToolBarIconWithFolme(this.mUndoView, 8);
                this.mMoreViewVisible = false;
                if (z) {
                    this.mDoneView.setVisibility(4);
                    this.mEditGroup.animate().translationX(this.mEditGroupXoffset).start();
                    this.mDoneViewVisible = false;
                } else if (this.mDoneView.getVisibility() != 0) {
                    showToolBarIconWithFolme(this.mDoneView, 0);
                    this.mEditGroup.animate().translationX(0.0f).start();
                    this.mDoneViewVisible = true;
                }
            } else {
                this.mShareView.setVisibility(8);
                if (operateViewTheme()) {
                    this.mViewThemeView.setVisibility(8);
                }
                this.mMoreView.setVisibility(8);
                this.mMoreViewVisible = false;
                this.mDoneView.setVisibility(z ? 4 : 0);
                this.mDoneViewVisible = !z;
                this.mEditGroup.setTranslationX(z ? this.mEditGroupXoffset : 0.0f);
                this.mRedoView.setVisibility(8);
                this.mUndoView.setVisibility(8);
            }
        }
        this.setQuick = false;
        if (RomUtils.isPadDevice()) {
            resumeHoverEffect();
        }
    }

    public void updateUndoState(boolean z, boolean z2) {
        this.mUndoView.setEnabled(z);
        this.mRedoView.setEnabled(z2);
    }

    @Deprecated
    public void onNavigationUp() {
        if (this.mEditCallback.isOnSaveInstanceCalled()) {
            return;
        }
        try {
            Logger.INSTANCE.i(this.TAG, "onNavigationUp");
            this.mActivity.onBackPressed();
        } catch (IllegalStateException unused) {
        }
    }

    public static void resetViewState(View view) {
        Folme.clean(view);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setAlpha(1.0f);
    }

    public static void showToolBarIconWithFolme(View view, int i) {
        Logger.INSTANCE.d("BaseActionBarController", "showToolBarIconWithFolme " + i + " for view:" + view);
        if (i == 0) {
            Folme.clean(view);
            view.setVisibility(0);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            Folme.useAt(view).visible().setScale(1.0f, IVisibleStyle.VisibleType.SHOW).setHide().show(new AnimConfig[0]);
            return;
        }
        Folme.clean(view);
        if (view.getVisibility() == 0) {
            Folme.useAt(view).visible().setScale(0.6f, IVisibleStyle.VisibleType.HIDE).hide(new AnimConfig().addListeners(new WeakViewTransitionListener(view, i)));
        }
    }

    private boolean operateViewTheme() {
        return !IS_SUPER_LITE && this.mViewThemeView.isEnabled();
    }

    public void resetButtonAnimState() {
        Folme.clean(this.mUndoView);
        Folme.clean(this.mRedoView);
        Folme.clean(this.mDoneView);
        Folme.clean(this.mShareView);
        Folme.clean(this.mMoreView);
    }

    public void resumeHoverEffect() {
        UIUtils.setFolmeHoverEffect(this.mUndoView);
        UIUtils.setFolmeHoverEffect(this.mRedoView);
        UIUtils.setFolmeHoverEffect(this.mDoneView);
        UIUtils.setFolmeHoverEffect(this.mShareView);
        UIUtils.setFolmeHoverEffect(this.mMoreView);
    }

    private static class WeakViewTransitionListener extends TransitionListener {
        private int endVisibility;
        private WeakReference<View> mWkView;

        public WeakViewTransitionListener(View view, int i) {
            this.mWkView = new WeakReference<>(view);
            this.endVisibility = i;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            View view;
            super.onComplete(obj);
            WeakReference<View> weakReference = this.mWkView;
            if (weakReference == null || (view = weakReference.get()) == null) {
                return;
            }
            view.setVisibility(this.endVisibility);
        }
    }

    public void onDestroy() {
        this.mUndoView.setOnClickListener(null);
        this.mRedoView.setOnClickListener(null);
        this.mDoneView.setOnClickListener(null);
        this.mShareView.setOnClickListener(null);
        this.mViewThemeView.setOnClickListener(null);
        this.mMoreView.setOnClickListener(null);
    }
}
