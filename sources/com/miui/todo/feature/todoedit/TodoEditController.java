package com.miui.todo.feature.todoedit;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.softinputhelper.KeyboardMarginProcessor;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.pad.feature.todo.edit.PadSubTodoEditAdapter;
import com.miui.todo.feature.todolist.SubTodoEditAdapter;
import com.miui.todo.feature.todolist.TodoEditListener;
import com.miui.todo.utils.TodoUtils;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class TodoEditController extends BaseTodoEditController {
    private static final String TAG = "TodoEditController";

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    protected int getLayoutId() {
        return R.layout.new_todo_edit_layout;
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    public void updateContentMargin(int i) {
    }

    public TodoEditController(Activity activity, View.OnClickListener onClickListener, TodoEditListener todoEditListener, boolean z) {
        super(activity, onClickListener, todoEditListener, z);
        this.mContentMarginToSoftInput = activity.getResources().getDimensionPixelSize(R.dimen.todo_edit_container_margin_bottom_to_soft_input);
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    protected SubTodoEditAdapter createSubTodoEditAdapter() {
        return new PadSubTodoEditAdapter(this.mContext, false);
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    protected void setRemindCustom(int i) {
        if (i > 0) {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind_repeat));
        } else {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind));
        }
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    protected void updateRemindCustomTagPaddingEnd(boolean z) {
        if (z) {
            this.mTagRemindCustom.setPaddingRelative(this.mTagRemindCustom.getPaddingStart(), this.mTagRemindCustom.getPaddingTop(), this.mTagRemindCustom.getContext().getResources().getDimensionPixelSize(R.dimen.pad_todo_edit_remind_text_padding_end), this.mTagRemindCustom.getPaddingBottom());
        } else {
            this.mTagRemindCustom.setPaddingRelative(this.mTagRemindCustom.getPaddingStart(), this.mTagRemindCustom.getPaddingTop(), this.mTagRemindCustom.getContext().getResources().getDimensionPixelSize(R.dimen.pad_todo_edit_remind_text_padding_end_default), this.mTagRemindCustom.getPaddingBottom());
        }
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    public void onConfigurationChanged() {
        super.onConfigurationChanged();
        resetSoftMode();
    }

    private void resetSoftMode() {
        if (UIUtils.isAboveAndroidR()) {
            if (isSplitScreenAndLandscape()) {
                this.mActivity.getWindow().clearFlags(52);
                this.mActivity.getWindow().setSoftInputMode(34);
            } else {
                this.mActivity.getWindow().clearFlags(34);
                this.mActivity.getWindow().setSoftInputMode(52);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSplitScreenAndLandscape() {
        if (this.mActivity != null) {
            return ((RomUtils.isFoldDevice() && !UIUtils.isFoldInternalScreen(this.mActivity)) || !RomUtils.isPadMode()) && DisplayUtils.getRealScreenOrientation(this.mActivity) == 2;
        }
        return false;
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    public void show(View view) {
        this.mIsShow = true;
        updateWindowState();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mEditRootLayout.getLayoutParams();
        marginLayoutParams.bottomMargin = this.mEditPanelBaseMarginBottom;
        this.mEditRootLayout.setLayoutParams(marginLayoutParams);
        resetSoftMode();
        initContent(this.mTodoData);
        ViewGroup viewGroup = (ViewGroup) this.mActivity.getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) UIUtils.getActionBarOverlayLayout(view);
        this.mRootView.setAlpha(0.0f);
        viewGroup2.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        if (this.mTodoData == null) {
            this.mTodoTextEdit.setCursorVisible(true);
            this.mTodoTextEdit.requestFocus();
        }
        this.mRootView.post(new Runnable() { // from class: com.miui.todo.feature.todoedit.TodoEditController.1
            @Override // java.lang.Runnable
            public void run() {
                if (TodoEditController.this.mRootView != null) {
                    Folme.useAt(TodoEditController.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().setMinDuration(100L).addListeners(TodoEditController.this.mShowListener));
                }
            }
        });
        this.mRootView.postDelayed(new Runnable() { // from class: com.miui.todo.feature.todoedit.TodoEditController.2
            @Override // java.lang.Runnable
            public void run() {
                if (TodoEditController.this.mRootView == null || TodoEditController.this.mTodoTextEdit == null) {
                    return;
                }
                TodoEditController.this.mTodoTextEdit.requestFocus();
                TodoEditController.this.mTodoTextEdit.sendAccessibilityEvent(8);
                TodoUtils.showSoftInput(TodoEditController.this.mTodoTextEdit);
            }
        }, 100L);
        this.mEditKeyboardHelper = new KeyboardHelper(this.mEditRootLayout, viewGroup, new KeyboardMarginProcessor() { // from class: com.miui.todo.feature.todoedit.TodoEditController.3
            final Rect rect = new Rect();
            final Rect rootRect = new Rect();
            boolean isKeyboardVisible = false;
            int lastHeight = 0;
            float currentVerticalBias = 1.0f;
            int lastOrientation = 0;

            @Override // com.miui.common.tool.softinputhelper.KeyboardMarginProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedEnd(View view2, WindowInsets windowInsets, int i) {
                super.onKeyboardHeightChangedEnd(view2, windowInsets, i);
                TodoEditController.this.mRvTodoSubList.scrollToPosition(TodoEditController.this.mSubTodoEditAdapter.getBindContext().getDoEditIndex());
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardMarginProcessor
            protected void updateViewMarginBottom(View view2, WindowInsets windowInsets, int i) {
                boolean zIsKeyboardVisibleInMultiWindowMode;
                int i2;
                if (RomUtils.isPadDevice() && !UIUtils.isFreeFormScreen(TodoEditController.this.mContext.getResources().getConfiguration())) {
                    int i3 = TodoEditController.this.mContext.getResources().getConfiguration().orientation;
                    if (Utils.isExternalKeyboardEnable(TodoEditController.this.mContext) && this.lastOrientation == i3 && i < 1800) {
                        if (i == 0 || i < (i2 = this.lastHeight)) {
                            this.isKeyboardVisible = false;
                        } else if (i > i2 && i > TodoEditController.this.mEditPanelBaseMarginBottom) {
                            this.isKeyboardVisible = true;
                        }
                        float f = this.isKeyboardVisible ? 1.0f : 0.5f;
                        if (this.currentVerticalBias != f) {
                            Logger.INSTANCE.d(TodoEditController.TAG, "updateViewMarginBottom: keyboardHeight = " + i + " vertical bias = " + f);
                            TodoEditController.this.setVerticalBias(f);
                            this.currentVerticalBias = f;
                        }
                    } else if (!Utils.isExternalKeyboardEnable(TodoEditController.this.mContext) && this.currentVerticalBias == 0.5f) {
                        TodoEditController.this.setVerticalBias(1.0f);
                        this.currentVerticalBias = 1.0f;
                    }
                    this.lastOrientation = i3;
                    this.lastHeight = i < 1800 ? i : this.lastHeight;
                }
                TodoEditController.this.mRootView.getGlobalVisibleRect(this.rootRect);
                if (TodoEditController.this.mActivity.isInMultiWindowMode()) {
                    if (UIUtils.isAboveAndroidR() && TodoEditController.this.mIsKeyboardVisible != (zIsKeyboardVisibleInMultiWindowMode = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(TodoEditController.this.mActivity))) {
                        TodoEditController.this.onKeyboardVisibleChanged(zIsKeyboardVisibleInMultiWindowMode);
                    }
                } else if (UIUtils.isAboveAndroidR()) {
                    boolean zIsKeyboardVisibleInMultiWindowMode2 = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(TodoEditController.this.mActivity);
                    if (TodoEditController.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode2) {
                        TodoEditController.this.onKeyboardVisibleChanged(zIsKeyboardVisibleInMultiWindowMode2);
                    }
                } else if (i <= 0) {
                    if (TodoEditController.this.mIsKeyboardVisible) {
                        TodoEditController.this.onKeyboardVisibleChanged(false);
                    }
                } else if (!TodoEditController.this.mIsKeyboardVisible) {
                    TodoEditController.this.onKeyboardVisibleChanged(true);
                }
                int i4 = TodoEditController.this.mActivity.getWindow().getAttributes().softInputMode;
                if (UIUtils.isAboveAndroidR()) {
                    if (!TodoEditController.this.mIsInFullScreenMode && !UIUtils.isAboveAndroidV()) {
                        TodoEditController.this.mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(new Rect());
                        if ((!RomUtils.isFoldDevice() && !UIUtils.isInMultiWindowMode(TodoEditController.this.mActivity)) || !UIUtils.isInNarrowScreen(TodoEditController.this.mContext) || !UIUtils.isInMultiWindowMode(TodoEditController.this.mActivity)) {
                            i -= TodoEditController.this.mNavigationBarHeight;
                        }
                    }
                    TodoEditController.this.mEditRootLayout.getGlobalVisibleRect(this.rect);
                    if (TodoEditController.this.mActivity.isInMultiWindowMode() && i > ((double) this.rootRect.bottom) * 0.8d) {
                        Logger.INSTANCE.d(TodoEditController.TAG, "get imeHeight error");
                        return;
                    }
                    int i5 = this.rootRect.bottom - i;
                    Logger.INSTANCE.d(TodoEditController.TAG, "rootVisibleBottom =" + i5 + " rootRect.bottom =" + this.rootRect.bottom + " imeHeight = " + i);
                    int i6 = i5 - this.rect.bottom;
                    int i7 = TodoEditController.this.mContentMarginToSoftInput - i6;
                    int i8 = ((ViewGroup.MarginLayoutParams) TodoEditController.this.mEditRootLayout.getLayoutParams()).bottomMargin;
                    int i9 = i7 + i8;
                    if (i6 < TodoEditController.this.mContentMarginToSoftInput) {
                        int i10 = i5 - TodoEditController.this.mEditPanelBaseMarginBottom;
                        this.rect.height();
                    }
                    if (i8 == i9 || TodoEditController.this.isSplitScreenAndLandscape()) {
                        return;
                    }
                    Logger.INSTANCE.d(TodoEditController.TAG, "bottomMarginAfter =" + i9 + " mEditPanelBaseMarginBottom=  " + TodoEditController.this.mEditPanelBaseMarginBottom);
                    super.updateViewMarginBottom(view2, windowInsets, Math.max(TodoEditController.this.mEditPanelBaseMarginBottom, i9));
                    return;
                }
                super.updateViewMarginBottom(view2, windowInsets, Math.max(TodoEditController.this.mEditPanelBaseMarginBottom, i - (TodoEditController.this.mNavigationBarHeight - TodoEditController.this.mContentMarginToSoftInput)));
            }
        });
        this.mEditKeyboardHelper.setForceMode(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVerticalBias(float f) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mEditRootLayout.getLayoutParams();
        layoutParams.verticalBias = f;
        this.mEditRootLayout.setLayoutParams(layoutParams);
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    public void dismiss() {
        this.mIsShow = false;
        if (this.mEditKeyboardHelper != null) {
            this.mEditKeyboardHelper.release();
            this.mEditKeyboardHelper = null;
        }
        this.mActivity.getWindow().setSoftInputMode(this.mLastSoftInputMode);
        Utils.hideSoftInput(this.mActivity);
        this.mTodoTextEdit.clearFocus();
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
        Folme.useAt(this.mEditRootLayout).state().to(ViewProperty.TRANSLATION_Y, Integer.valueOf(this.mEditRootLayout.getHeight()));
        if (this.mWindowListener != null) {
            this.mWindowListener.onDismiss();
        }
    }

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    public void dismissNoAnim() {
        this.mIsShow = false;
        this.mActivity.getWindow().setSoftInputMode(this.mLastSoftInputMode);
        this.mTodoTextEdit.clearFocus();
        if (this.mEditKeyboardHelper != null) {
            this.mEditKeyboardHelper.release();
            this.mEditKeyboardHelper = null;
        }
        if (this.mRootView != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        Utils.hideSoftInput(this.mActivity);
        this.mActivity = null;
        this.mRootView = null;
        if (this.mWindowListener != null) {
            this.mWindowListener.onDismiss();
        }
    }
}
