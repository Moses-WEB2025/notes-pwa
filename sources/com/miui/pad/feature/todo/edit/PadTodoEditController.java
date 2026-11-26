package com.miui.pad.feature.todo.edit;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.tool.softinputhelper.KeyboardMarginProcessor;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.todo.feature.todoedit.BaseTodoEditController;
import com.miui.todo.feature.todolist.SubTodoEditAdapter;
import com.miui.todo.feature.todolist.TodoEditListener;
import com.miui.todo.utils.TodoUtils;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PadTodoEditController extends BaseTodoEditController {
    public static final String TAG = "PadTodoEditController";

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    protected int getLayoutId() {
        return R.layout.pad_todo_edit_layout;
    }

    public PadTodoEditController(Activity activity, View.OnClickListener onClickListener, TodoEditListener todoEditListener, boolean z, int i) {
        super(activity, onClickListener, todoEditListener, z);
        this.mContentMarginToSoftInput = activity.getResources().getDimensionPixelSize(R.dimen.pad_todo_edit_container_margin_bottom_to_soft_input);
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
    public void show(View view) {
        this.mIsShow = true;
        updateWindowState();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mEditRootLayout.getLayoutParams();
        marginLayoutParams.bottomMargin = this.mEditPanelBaseMarginBottom;
        this.mEditRootLayout.setLayoutParams(marginLayoutParams);
        this.mActivity.getWindow().setSoftInputMode(52);
        initContent(this.mTodoData);
        ViewGroup viewGroup = (ViewGroup) this.mActivity.getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) UIUtils.getActionBarOverlayLayout(view);
        this.mRootView.setAlpha(0.0f);
        viewGroup2.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        if (this.mTodoData == null) {
            this.mTodoTextEdit.setCursorVisible(true);
            this.mTodoTextEdit.requestFocus();
        }
        this.mRootView.postDelayed(new Runnable() { // from class: com.miui.pad.feature.todo.edit.PadTodoEditController.1
            @Override // java.lang.Runnable
            public void run() {
                if (PadTodoEditController.this.mRootView == null || PadTodoEditController.this.mTodoTextEdit == null) {
                    return;
                }
                PadTodoEditController.this.mTodoTextEdit.requestFocus();
                PadTodoEditController.this.mTodoTextEdit.sendAccessibilityEvent(8);
                TodoUtils.showSoftInput(PadTodoEditController.this.mTodoTextEdit);
            }
        }, 100L);
        this.mRootView.post(new Runnable() { // from class: com.miui.pad.feature.todo.edit.PadTodoEditController.2
            @Override // java.lang.Runnable
            public void run() {
                if (PadTodoEditController.this.mRootView != null) {
                    PadTodoEditController.this.mEditRootLayout.setScaleX(0.8f);
                    PadTodoEditController.this.mEditRootLayout.setScaleY(0.8f);
                    Folme.useAt(PadTodoEditController.this.mEditRootLayout).state().to(ViewProperty.SCALE_X, Float.valueOf(1.0f)).to(ViewProperty.SCALE_Y, Float.valueOf(1.0f));
                    Folme.useAt(PadTodoEditController.this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().setMinDuration(100L).addListeners(PadTodoEditController.this.mShowListener));
                }
            }
        });
        this.mEditKeyboardHelper = new KeyboardHelper(this.mEditRootLayout, viewGroup, new KeyboardMarginProcessor() { // from class: com.miui.pad.feature.todo.edit.PadTodoEditController.3
            final Rect rect = new Rect();
            final Rect rootRect = new Rect();
            boolean isKeyboardVisible = false;
            int lastHeight = 0;
            float currentVerticalBias = 1.0f;
            int lastOrientation = 0;

            @Override // com.miui.common.tool.softinputhelper.KeyboardMarginProcessor, com.miui.common.tool.softinputhelper.KeyboardHeightListener
            public void onKeyboardHeightChangedEnd(View view2, WindowInsets windowInsets, int i) {
                super.onKeyboardHeightChangedEnd(view2, windowInsets, i);
                PadTodoEditController.this.mRvTodoSubList.scrollToPosition(PadTodoEditController.this.mSubTodoEditAdapter.getBindContext().getDoEditIndex());
            }

            @Override // com.miui.common.tool.softinputhelper.KeyboardMarginProcessor
            protected void updateViewMarginBottom(View view2, WindowInsets windowInsets, int i) {
                boolean zIsKeyboardVisibleInMultiWindowMode;
                int i2;
                int i3 = PadTodoEditController.this.mContext.getResources().getConfiguration().orientation;
                if (!Utils.isExternalKeyboardEnable(PadTodoEditController.this.mContext) || this.lastOrientation != i3 || i >= 1800) {
                    if (!Utils.isExternalKeyboardEnable(PadTodoEditController.this.mContext) && this.currentVerticalBias == 0.5f) {
                        PadTodoEditController.this.setVerticalBias(1.0f);
                        this.currentVerticalBias = 1.0f;
                    }
                } else {
                    if (i != 0 && i >= (i2 = this.lastHeight)) {
                        if (i > i2 && i > PadTodoEditController.this.mEditPanelBaseMarginBottom) {
                            this.isKeyboardVisible = true;
                        }
                    } else {
                        this.isKeyboardVisible = false;
                    }
                    float f = this.isKeyboardVisible ? 1.0f : 0.5f;
                    if (this.currentVerticalBias != f) {
                        Logger.INSTANCE.d(PadTodoEditController.TAG, "updateViewMarginBottom: keyboardHeight = " + i + " vertical bias = " + f);
                        PadTodoEditController.this.setVerticalBias(f);
                        this.currentVerticalBias = f;
                    }
                }
                this.lastOrientation = i3;
                this.lastHeight = i < 1800 ? i : this.lastHeight;
                PadTodoEditController.this.mRootView.getGlobalVisibleRect(this.rootRect);
                if (PadTodoEditController.this.mActivity.isInMultiWindowMode()) {
                    if (UIUtils.isAboveAndroidR() && PadTodoEditController.this.mIsKeyboardVisible != (zIsKeyboardVisibleInMultiWindowMode = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(PadTodoEditController.this.mActivity))) {
                        PadTodoEditController.this.onKeyboardVisibleChanged(zIsKeyboardVisibleInMultiWindowMode);
                    }
                } else if (UIUtils.isAboveAndroidR()) {
                    boolean zIsKeyboardVisibleInMultiWindowMode2 = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(PadTodoEditController.this.mActivity);
                    if (PadTodoEditController.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode2) {
                        PadTodoEditController.this.onKeyboardVisibleChanged(zIsKeyboardVisibleInMultiWindowMode2);
                    }
                } else if (i <= 0) {
                    if (PadTodoEditController.this.mIsKeyboardVisible) {
                        PadTodoEditController.this.onKeyboardVisibleChanged(false);
                    }
                } else if (!PadTodoEditController.this.mIsKeyboardVisible) {
                    PadTodoEditController.this.onKeyboardVisibleChanged(true);
                }
                int i4 = PadTodoEditController.this.mActivity.getWindow().getAttributes().softInputMode;
                if (UIUtils.isAboveAndroidR()) {
                    if (!PadTodoEditController.this.mIsInFullScreenMode && !UIUtils.isAboveAndroidV()) {
                        i -= PadTodoEditController.this.mNavigationBarHeight;
                    }
                    PadTodoEditController.this.mEditRootLayout.getGlobalVisibleRect(this.rect);
                    int i5 = PadTodoEditController.this.mContentMarginToSoftInput + (this.rect.bottom - (this.rootRect.bottom - i));
                    int i6 = ((ViewGroup.MarginLayoutParams) PadTodoEditController.this.mEditRootLayout.getLayoutParams()).bottomMargin;
                    int i7 = i5 + i6;
                    if (i7 < (this.rootRect.bottom - PadTodoEditController.this.mEditPanelBaseMarginBottom) - 30 && i6 != i7) {
                        super.updateViewMarginBottom(view2, windowInsets, Math.max(PadTodoEditController.this.mEditPanelBaseMarginBottom, i7));
                        return;
                    }
                    return;
                }
                super.updateViewMarginBottom(view2, windowInsets, Math.max(PadTodoEditController.this.mEditPanelBaseMarginBottom, i - (PadTodoEditController.this.mNavigationBarHeight - PadTodoEditController.this.mContentMarginToSoftInput)));
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
        this.mTodoTextEdit.setInputType(0);
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
        this.mTodoTextEdit.setInputType(0);
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

    @Override // com.miui.todo.feature.todoedit.BaseTodoEditController
    public void updateContentMargin(int i) {
        Resources resources = this.mActivity.getResources();
        int i2 = R.dimen.pad_todo_edit_container_margin_horizontal_common;
        this.horizontalMargin = resources.getDimensionPixelSize(R.dimen.pad_todo_edit_container_margin_horizontal_common);
        boolean z = this.mActivity.getResources().getConfiguration().orientation == 2;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mEditRootLayout.getLayoutParams();
        if (i == 2) {
            Resources resources2 = this.mActivity.getResources();
            if (!z) {
                i2 = R.dimen.pad_todo_edit_container_margin_horizontal_port;
            }
            this.horizontalMargin = resources2.getDimensionPixelSize(i2);
        } else if (i == 3) {
            this.horizontalMargin = this.mActivity.getResources().getDimensionPixelSize(R.dimen.pad_todo_edit_container_margin_horizontal_land_big);
        } else {
            this.horizontalMargin = this.mActivity.getResources().getDimensionPixelSize(R.dimen.todo_edit_container_margin_horizontal);
        }
        marginLayoutParams.setMarginStart(this.horizontalMargin);
        marginLayoutParams.setMarginEnd(this.horizontalMargin);
        this.mEditRootLayout.setLayoutParams(marginLayoutParams);
    }
}
