package com.miui.todo.feature.todoedit;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.KeyboardHelper;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.ExternalTextWatcher;
import com.miui.richeditor.RichEditScrollView;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.feature.todolist.SubTodoEditAdapter;
import com.miui.todo.feature.todolist.SubTodoEditAdapterListener;
import com.miui.todo.feature.todolist.SubTodoEditListener;
import com.miui.todo.feature.todolist.SubTodoEditorDivider;
import com.miui.todo.feature.todolist.SubTodoRichEditor;
import com.miui.todo.feature.todolist.TodoEditListener;
import com.miui.todo.utils.CalenderUtils;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.EditRootLayout;
import miuix.animation.listener.TransitionListener;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseTodoEditController {
    private static final String TAG = "BaseTodoEditController";
    protected Activity mActivity;
    protected TextView mBtnTodoEditCreate;
    protected Context mContext;
    protected TextView mCountDownWarnView;
    protected View mEditGroup;
    protected EditRootLayout mEditRootLayout;
    protected View mEditRootView;
    protected LinearLayout mEditorMaskView;
    protected boolean mIsNew;
    protected boolean mIsShow;
    protected boolean mIsSubMode;
    protected ImageView mIvRemindCustom;
    protected View mLastFocusedViewForSoftInput;
    protected int mLastSoftInputMode;
    protected View mPlaceHolder;
    protected View mRootView;
    protected RecyclerView mRvTodoSubList;
    protected SubModeImpl mSubTodoData;
    protected SubTodoEditAdapter mSubTodoEditAdapter;
    private ValueAnimator mSubTodoEditorLayoutAnimator;
    protected TextView mTagRemindCustom;
    protected ImageView mTagRemindCustomCancel;
    protected View mTagRemindCustomDivider;
    protected View mTagRemindCustomGroup;
    protected EditText mTitleEditText;
    protected TodoBaseEntity mTodoData;
    protected TodoEditTitleContainerView mTodoEditContainer;
    private final TodoEditListener mTodoEditListener;
    protected View mTodoEditorGroup;
    protected View mTodoTextCheckGroup;
    protected TodoRichEditor mTodoTextEdit;
    protected View.OnClickListener mViewClickListener;
    protected OnWindowListener mWindowListener;
    protected SubModeImpl oldSubTodoData;
    protected TransitionListener mShowListener = new TransitionListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (BaseTodoEditController.this.mWindowListener != null) {
                BaseTodoEditController.this.mWindowListener.onShow();
            }
        }
    };
    protected TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            if (BaseTodoEditController.this.mRootView != null) {
                ((ViewGroup) BaseTodoEditController.this.mRootView.getParent()).removeView(BaseTodoEditController.this.mRootView);
            }
            if (BaseTodoEditController.this.isAboveAndroidR() && BaseTodoEditController.this.mEditKeyboardHelper != null) {
                BaseTodoEditController.this.mEditKeyboardHelper.release();
            }
            if (BaseTodoEditController.this.mLastFocusedViewForSoftInput != null) {
                Utils.hideSoftInput(BaseTodoEditController.this.mLastFocusedViewForSoftInput);
            } else if (BaseTodoEditController.this.mActivity != null) {
                Utils.hideSoftInput(BaseTodoEditController.this.mActivity);
            } else {
                Logger.INSTANCE.w(BaseTodoEditController.TAG, " fail to hideSoftInput ");
            }
            BaseTodoEditController.this.mContext = null;
            BaseTodoEditController.this.mActivity = null;
            BaseTodoEditController.this.mRootView = null;
            if (BaseTodoEditController.this.mWindowListener != null) {
                BaseTodoEditController.this.mWindowListener.onDismiss();
            }
        }
    };
    protected boolean mIsKeyboardVisible = false;
    protected boolean mIsInFullScreenMode = false;
    protected int mNavigationBarHeight = 0;
    protected int mEditPanelBaseMarginBottom = 0;
    protected KeyboardHelper mEditKeyboardHelper = null;
    protected int mContentMarginToSoftInput = 0;
    protected final TextWatcher mTextEditorWatcher = new TextWatcher() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.3
        private static final long MX_STATUS_CHECK_DURATION = 10000;
        private long lastCheckTime = System.currentTimeMillis();

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String string = charSequence.toString();
            if (BaseTodoEditController.this.getIsEnter(charSequence.toString())) {
                BaseTodoEditController baseTodoEditController = BaseTodoEditController.this;
                if (baseTodoEditController.getIsFinish(baseTodoEditController.mTodoData)) {
                    return;
                }
            }
            if (!TextUtils.isEmpty(string) && string.length() > 2000) {
                Toast.makeText(BaseTodoEditController.this.mContext, TodoUtils.getString(R.string.exceed_todo_text_limitation), 1).show();
                String strSubstring = string.substring(0, 2000);
                int iMin = Math.min(2000, BaseTodoEditController.this.mTodoTextEdit.getSelectionEnd());
                BaseTodoEditController.this.mTodoTextEdit.setText(strSubstring);
                BaseTodoEditController.this.mTodoTextEdit.setSelection(iMin, iMin);
                return;
            }
            BaseTodoEditController.this.notifyTodoContentChanged();
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            boolean isEnter = BaseTodoEditController.this.getIsEnter(editable.toString());
            if (isEnter) {
                BaseTodoEditController baseTodoEditController = BaseTodoEditController.this;
                if (baseTodoEditController.getIsFinish(baseTodoEditController.mTodoData)) {
                    BaseTodoEditController baseTodoEditController2 = BaseTodoEditController.this;
                    if (!baseTodoEditController2.getIsInputAudio(baseTodoEditController2.mTodoData) && !LiteUtils.isNewLiteOrMiddle()) {
                        BaseTodoEditController.this.mTodoTextEdit.removeTextChangedListener(BaseTodoEditController.this.mTextEditorWatcher);
                        BaseTodoEditController.this.mTodoTextEdit.setText(CharUtils.removeCharFromString(editable.toString(), '\n'));
                        BaseTodoEditController.this.mTodoTextEdit.addTextChangedListener(BaseTodoEditController.this.mTextEditorWatcher);
                        return;
                    }
                }
            }
            if (isEnter && !LiteUtils.isNewLiteOrMiddle()) {
                BaseTodoEditController baseTodoEditController3 = BaseTodoEditController.this;
                if (baseTodoEditController3.getIsInputAudio(baseTodoEditController3.mTodoData)) {
                    if (System.currentTimeMillis() - this.lastCheckTime > 10000) {
                        this.lastCheckTime = System.currentTimeMillis();
                    }
                    BaseTodoEditController.this.notifyTodoContentChanged();
                    return;
                } else {
                    if (editable.length() == 1) {
                        BaseTodoEditController.this.mTodoTextEdit.setText("");
                        return;
                    }
                    BaseTodoEditController baseTodoEditController4 = BaseTodoEditController.this;
                    if (!baseTodoEditController4.getIsInputAudio(baseTodoEditController4.mTodoData)) {
                        BaseTodoEditController.this.changeEditMode(true);
                        return;
                    }
                }
            } else {
                BaseTodoEditController baseTodoEditController5 = BaseTodoEditController.this;
                if (baseTodoEditController5.getIsInputAudio(baseTodoEditController5.mTodoData) && !AudioUtils.hasAudioLabel(editable.toString())) {
                    BaseTodoEditController.this.mTodoData.setAudioFileName(null);
                    BaseTodoEditController.this.mTodoData.setInputType(0);
                    BaseTodoEditController.this.mTodoTextCheckGroup.setVisibility(0);
                    BaseTodoEditController.this.mTodoTextEdit.clearRichTextData();
                }
                if (System.currentTimeMillis() - this.lastCheckTime > 10000) {
                    this.lastCheckTime = System.currentTimeMillis();
                }
                BaseTodoEditController.this.notifyTodoContentChanged();
            }
            if (!TextUtils.isEmpty(editable) || LiteUtils.isNewLiteOrMiddle()) {
                BaseTodoEditController.this.mTodoTextEdit.setHint("");
            } else {
                BaseTodoEditController.this.mTodoTextEdit.setHint(BaseTodoEditController.this.mIsSubMode ? NoteApp.getInstance().getString(R.string.todo_list) : NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
            }
        }
    };
    protected final TextWatcher mSubTodoTitleWatcher = new TextWatcher() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.4
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String string = editable.toString();
            if (TextUtils.isEmpty(string)) {
                string = "";
            }
            if (TextUtils.equals(string, BaseTodoEditController.this.mSubTodoData.getTitle())) {
                return;
            }
            BaseTodoEditController.this.mSubTodoData.setTitle(string);
            BaseTodoEditController baseTodoEditController = BaseTodoEditController.this;
            baseTodoEditController.notifySubTodoContentChanged(baseTodoEditController.mSubTodoData.getContent());
        }
    };
    protected final ExternalTextWatcher mRichEditorWatcher = new ExternalTextWatcher() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.5
        @Override // com.miui.richeditor.ExternalTextWatcher
        public void afterCheckBoxChanged() {
        }

        @Override // com.miui.richeditor.ExternalTextWatcher, android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // com.miui.richeditor.ExternalTextWatcher
        public void afterTextActuallyChanged(Editable editable) {
            if (BaseTodoEditController.this.isInTodoEditMode()) {
                if (TextUtils.isEmpty(editable)) {
                    BaseTodoEditController.this.mBtnTodoEditCreate.setEnabled(false);
                } else if (!BaseTodoEditController.this.mBtnTodoEditCreate.isEnabled()) {
                    BaseTodoEditController.this.mBtnTodoEditCreate.setEnabled(true);
                }
                if (editable.length() == 1 && editable.charAt(0) == '\n') {
                    BaseTodoEditController.this.mTodoTextEdit.setText("");
                    BaseTodoEditController.this.mBtnTodoEditCreate.setEnabled(false);
                }
            }
        }
    };
    private final SubTodoEditListener mSubTodoEditListener = new SubTodoEditListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.6
        @Override // com.miui.todo.feature.todolist.SubTodoEditListener
        public void onDataChanged(String str, int i, int i2) {
            BaseTodoEditController baseTodoEditController = BaseTodoEditController.this;
            baseTodoEditController.oldSubTodoData = baseTodoEditController.mSubTodoEditAdapter != null ? BaseTodoEditController.this.mSubTodoEditAdapter.getOldSubTodoList() : null;
            String content = BaseTodoEditController.this.oldSubTodoData != null ? BaseTodoEditController.this.oldSubTodoData.getContent() : null;
            Logger.INSTANCE.d(BaseTodoEditController.TAG, "onDataChanged: oldData size = " + (content != null ? Integer.valueOf(content.length()) : "null"));
            Logger.INSTANCE.d(BaseTodoEditController.TAG, "onDataChanged: newData size = " + (str != null ? Integer.valueOf(str.length()) : "null"));
            BaseTodoEditController.this.notifySubTodoContentChanged(str);
            if (BaseTodoEditController.this.mSubTodoData.getSubTodoSize() == 0) {
                BaseTodoEditController.this.changeEditMode(false);
            } else {
                BaseTodoEditController.this.updateSubTodoEditorOnDataChanged(i, i2);
            }
        }

        @Override // com.miui.todo.feature.todolist.SubTodoEditListener
        public void onSubItemClicked(View view) {
            BaseTodoEditController.this.mLastFocusedViewForSoftInput = view;
        }
    };
    private final SubTodoEditAdapterListener mSubTodoEditAdapterListener = new SubTodoEditAdapterListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.7
        @Override // com.miui.todo.feature.todolist.SubTodoEditAdapterListener
        public void showToast(String str, long j) {
        }

        @Override // com.miui.todo.feature.todolist.SubTodoEditAdapterListener
        public void onCreateNewData(int i) {
            BaseTodoEditController.this.mRvTodoSubList.smoothScrollToPosition(i);
        }
    };
    protected int horizontalMargin = 0;
    protected int subTodoHeight = 0;
    protected int subTodoMarginHeight = 0;
    private long mLastClickRemindTime = 0;

    public interface OnWindowListener {
        void onDismiss();

        void onShow();
    }

    static /* synthetic */ void lambda$initEditView$2() {
    }

    static /* synthetic */ void lambda$initEditView$3(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
    }

    public void dismiss() {
    }

    public void dismissNoAnim() {
    }

    protected abstract int getLayoutId();

    public boolean onBackPressed() {
        return false;
    }

    public void onMultiWindowModeChanged() {
    }

    public void updateContentMargin(int i) {
    }

    protected void updateRemindCustomTagPaddingEnd(boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getIsEnter(String str) {
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n') {
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getIsFinish(TodoBaseEntity todoBaseEntity) {
        return todoBaseEntity != null && ((TodoEntity) todoBaseEntity).getIsFinish() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getIsInputAudio(TodoBaseEntity todoBaseEntity) {
        return todoBaseEntity != null && ((TodoEntity) todoBaseEntity).getInputType() == 1;
    }

    private static class DataChangeType {
        static final int ADD_MULTI_DATA = 3;
        static final int DELETE = 1;
        static final int MERGE = 2;
        static final int SAVE = 0;

        private DataChangeType() {
        }
    }

    public BaseTodoEditController(Activity activity, View.OnClickListener onClickListener, TodoEditListener todoEditListener, boolean z) {
        this.mContext = activity;
        this.mActivity = activity;
        this.mLastSoftInputMode = activity.getWindow().getAttributes().softInputMode;
        View viewInflate = LayoutInflater.from(activity).inflate(getLayoutId(), (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setAlpha(0.0f);
        this.mViewClickListener = onClickListener;
        this.mTodoEditListener = todoEditListener;
        this.mIsNew = z;
        initEditView(this.mRootView);
        smallWindowAdapter();
    }

    public void setWindowListener(OnWindowListener onWindowListener) {
        this.mWindowListener = onWindowListener;
    }

    public void onPause() {
        this.mTodoTextEdit.stopMediaPlayerIfNeed();
    }

    public void onResume() {
        updateWindowState();
        smallWindowAdapter();
    }

    public void updateWindowState() {
        this.mIsInFullScreenMode = UIUtils.isInFullWindowGestureMode(this.mContext);
        this.mNavigationBarHeight = UIUtils.getNavigationBarHeight(this.mContext);
        if (this.mIsInFullScreenMode || UIUtils.isAboveAndroidV()) {
            this.mEditPanelBaseMarginBottom = this.mContentMarginToSoftInput + this.mNavigationBarHeight;
        } else {
            this.mEditPanelBaseMarginBottom = this.mContentMarginToSoftInput;
        }
    }

    public void updateScreenSpec(int i) {
        updateContentMargin(i);
    }

    public void onConfigurationChanged() {
        smallWindowAdapter();
    }

    public void show(View view) {
        this.mIsShow = true;
        initContent(this.mTodoData);
        this.mRootView.animate().alpha(1.0f).start();
        if (isInTodoEditMode()) {
            this.mTodoTextEdit.setFocusable(true);
            this.mTodoTextEdit.setFocusableInTouchMode(true);
            showSoftInputOnTodoEditMode(200L);
            return;
        }
        showSoftInputOnSubTodoEditMode(200L);
    }

    public void hide() {
        this.mEditRootLayout.animate().alpha(0.0f);
    }

    public void reshow() {
        this.mEditRootLayout.animate().alpha(1.0f);
    }

    public void close() {
        TodoUtils.hideSoftInput(getKeyboardRequestView());
        this.mLastFocusedViewForSoftInput = null;
        this.mTodoTextEdit.clearFocus();
        this.mTodoTextEdit.stopMediaPlayerIfNeed();
    }

    public boolean isShow() {
        return this.mIsShow;
    }

    protected View getKeyboardRequestView() {
        return this.mLastFocusedViewForSoftInput;
    }

    public void showSoftInput() {
        if (isInTodoEditMode()) {
            this.mTodoTextEdit.setFocusable(true);
            this.mTodoTextEdit.setFocusableInTouchMode(true);
            showSoftInputOnTodoEditMode(200L);
            return;
        }
        showSoftInputOnSubTodoEditMode(200L);
    }

    public void hideSoftInput() {
        this.mTodoTextEdit.clearFocus();
        TodoUtils.hideSoftInput(this.mTodoTextEdit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInTodoEditMode() {
        return !this.mIsSubMode;
    }

    private void showSoftInputOnTodoEditMode(long j) {
        Logger.INSTANCE.i(TAG, "showSoftInputOnTodoEditMode delay = " + j);
        this.mTodoTextEdit.requestFocus();
        final int selectionStart = this.mTodoTextEdit.getSelectionStart();
        this.mTodoTextEdit.postDelayed(new Runnable() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSoftInputOnTodoEditMode$0(selectionStart);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftInputOnTodoEditMode$0(int i) {
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        this.mLastFocusedViewForSoftInput = todoRichEditor;
        TodoUtils.showSoftInput(todoRichEditor);
        Editable text = this.mTodoTextEdit.getText();
        this.mTodoTextEdit.setText(text);
        TodoRichEditor todoRichEditor2 = this.mTodoTextEdit;
        if (text == null) {
            i = 0;
        }
        todoRichEditor2.setSelection(i);
        this.mTodoTextEdit.setCursorVisible(true);
    }

    private void showSoftInputOnSubTodoEditMode(long j) {
        Logger.INSTANCE.i(TAG, "showSoftInputOnSubTodoEditMode delay = " + j);
        this.mRvTodoSubList.postDelayed(new Runnable() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSoftInputOnSubTodoEditMode$1();
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftInputOnSubTodoEditMode$1() {
        if (this.mLastFocusedViewForSoftInput != null) {
            TodoUtils.showSoftInput(getKeyboardRequestView());
            return;
        }
        if (this.mRvTodoSubList.getChildCount() > 0) {
            RecyclerView recyclerView = this.mRvTodoSubList;
            View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            if (childAt != null) {
                EditText editText = (EditText) childAt.findViewById(R.id.editor);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                this.mLastFocusedViewForSoftInput = editText;
                TodoUtils.showSoftInput(getKeyboardRequestView());
            }
        }
    }

    private void initEditView(View view) {
        view.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                BaseTodoEditController.lambda$initEditView$2();
            }
        });
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                BaseTodoEditController.lambda$initEditView$3(view2, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        view.setOnClickListener(this.mViewClickListener);
        initEditRootLayout(view);
        this.mEditRootView = view.findViewById(R.id.edit_root);
        this.mEditGroup = view.findViewById(R.id.bottom_editor_panel);
        TextView textView = (TextView) view.findViewById(R.id.send_button);
        this.mBtnTodoEditCreate = textView;
        DisplayUtils.setMiuiMediumTypeFace(textView);
        this.mBtnTodoEditCreate.setOnClickListener(this.mViewClickListener);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_tag_custom);
        this.mTagRemindCustom = textView2;
        DisplayUtils.setMiuiMediumTypeFace(textView2);
        this.mTagRemindCustomGroup = view.findViewById(R.id.group_tag_custom);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tag_custom);
        this.mIvRemindCustom = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.remindCustomOnClick(view2);
            }
        });
        this.mTagRemindCustom.setSelected(false);
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setSelected(false);
        this.mIvRemindCustom.setActivated(false);
        this.mTagRemindCustomGroup.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.remindCustomOnClick(view2);
            }
        });
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(this.mTagRemindCustomGroup);
        this.mTagRemindCustomDivider = view.findViewById(R.id.v_tag_divider);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.iv_tag_cancel);
        this.mTagRemindCustomCancel = imageView2;
        imageView2.setOnClickListener(this.mViewClickListener);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.todo_edit_mask);
        this.mEditorMaskView = linearLayout;
        linearLayout.setVisibility(0);
        this.mEditorMaskView.setOnClickListener(this.mViewClickListener);
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initEditView$4(view2);
            }
        });
        this.mPlaceHolder = view.findViewById(R.id.v_placeholder);
        initTodoEditorGroup(view);
        initSubTodoEditorGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditView$4(View view) {
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this.mEditorMaskView);
            this.mViewClickListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remindCustomOnClick(View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - this.mLastClickRemindTime) < 500) {
            this.mLastClickRemindTime = jCurrentTimeMillis;
            return;
        }
        this.mLastClickRemindTime = jCurrentTimeMillis;
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    protected void initEditRootLayout(View view) {
        EditRootLayout editRootLayout = (EditRootLayout) view.findViewById(R.id.edit_panel);
        this.mEditRootLayout = editRootLayout;
        editRootLayout.setKeyEventPreImeListener(new EditRootLayout.OnKeyEventPreImeListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.8
            @Override // com.miui.todo.view.EditRootLayout.OnKeyEventPreImeListener
            public boolean onKeyEventPreIme(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() != 4) {
                    return false;
                }
                if (BaseTodoEditController.this.mViewClickListener == null) {
                    return true;
                }
                BaseTodoEditController.this.mViewClickListener.onClick(BaseTodoEditController.this.mEditorMaskView);
                BaseTodoEditController.this.mViewClickListener = null;
                return true;
            }
        });
        this.mEditRootLayout.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initEditRootLayout$5(view2);
            }
        });
        this.mEditRootLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.lambda$initEditRootLayout$6(view2, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditRootLayout$5(View view) {
        if (this.mIsSubMode) {
            return;
        }
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        todoRichEditor.setSelection(todoRichEditor.getText() == null ? 0 : this.mTodoTextEdit.getText().length());
        this.mTodoTextEdit.setCursorVisible(true);
        this.mTodoTextEdit.requestFocus();
        this.mLastFocusedViewForSoftInput = this.mTodoTextEdit;
        TodoUtils.showSoftInput(getKeyboardRequestView());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditRootLayout$6(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        KeyboardHelper keyboardHelper;
        if (i2 == 0 && i4 == 0) {
            return;
        }
        if ((i2 == i6 && i4 == i8) || (keyboardHelper = this.mEditKeyboardHelper) == null) {
            return;
        }
        keyboardHelper.update();
    }

    private void initTodoEditorGroup(View view) {
        this.mTodoEditorGroup = view.findViewById(R.id.todo_editor);
        TodoRichEditor todoRichEditor = (TodoRichEditor) view.findViewById(R.id.rich_editor);
        this.mTodoTextEdit = todoRichEditor;
        todoRichEditor.setScrollView((RichEditScrollView) view.findViewById(R.id.editor_scroll_view));
        this.mTodoEditContainer = (TodoEditTitleContainerView) view.findViewById(R.id.todo_editor_container);
        this.mTodoTextCheckGroup = view.findViewById(R.id.normal_todo_check_group);
        TodoRichEditor todoRichEditor2 = this.mTodoTextEdit;
        CompatUtils.invokeCursorColor(todoRichEditor2, todoRichEditor2.getContext().getResources().getColor(R.color.theme_light_cursor_color), this.mTodoTextEdit.getContext().getResources().getColor(R.color.theme_light_cursor_handle_color));
        DisplayUtils.setMiuiRegularTypeFace(this.mTodoTextEdit);
        this.mTodoTextEdit.setHighlightColor(this.mContext.getResources().getColor(R.color.theme_light_high_light_color));
        this.mTodoTextEdit.setExternalTextWatcher(this.mRichEditorWatcher);
        this.mTodoTextEdit.setCursorVisible(false);
        this.mTodoTextEdit.setOnClickListener(new View.OnClickListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BaseTodoEditController.lambda$initTodoEditorGroup$7(view2);
            }
        });
        this.mTodoTextEdit.clearFocus();
        CompatUtils.invokeCursorColor(this.mTodoTextEdit, this.mContext.getResources().getColor(R.color.theme_default_cursor_color), this.mContext.getResources().getColor(R.color.theme_default_cursor_handle_color));
    }

    static /* synthetic */ void lambda$initTodoEditorGroup$7(View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            if (editText.isCursorVisible()) {
                return;
            }
            editText.setCursorVisible(true);
        }
    }

    private void initSubTodoEditorGroup() {
        RecyclerView recyclerView = (RecyclerView) this.mTodoEditorGroup.findViewById(R.id.rv_subtodo);
        this.mRvTodoSubList = recyclerView;
        recyclerView.setLayoutManager(new TodoLayoutManager(this.mContext));
        this.mTitleEditText = this.mTodoTextEdit;
        SubTodoEditAdapter subTodoEditAdapterCreateSubTodoEditAdapter = createSubTodoEditAdapter();
        this.mSubTodoEditAdapter = subTodoEditAdapterCreateSubTodoEditAdapter;
        subTodoEditAdapterCreateSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
        this.mSubTodoEditAdapter.setHasStableIds(true);
        this.mSubTodoEditAdapter.setSubTodoEditListener(this.mSubTodoEditListener);
        this.mRvTodoSubList.addItemDecoration(new SubTodoEditorDivider(this.mContext.getResources().getDimensionPixelSize(R.dimen.todo_edit_subEditor_spacing), this.mContext.getResources().getDimensionPixelSize(R.dimen.todo_edit_subEditor_spacing)));
    }

    protected void updateEditPanelBaseMargin() {
        EditRootLayout editRootLayout = this.mEditRootLayout;
        if (editRootLayout != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) editRootLayout.getLayoutParams();
            marginLayoutParams.bottomMargin = this.mContentMarginToSoftInput + this.mNavigationBarHeight;
            this.mEditRootLayout.setLayoutParams(marginLayoutParams);
        }
    }

    protected SubTodoEditAdapter createSubTodoEditAdapter() {
        return new SubTodoEditAdapter(this.mContext, false);
    }

    protected void initContent(TodoBaseEntity todoBaseEntity) {
        if (todoBaseEntity != null) {
            if (todoBaseEntity.getListType() == 0) {
                initTodoContent(todoBaseEntity);
            } else if (todoBaseEntity.getListType() == 1) {
                initSubTodoContent(todoBaseEntity);
            }
            ((CheckBox) this.mTodoTextCheckGroup.findViewById(R.id.cb_subtodo)).setChecked(getIsFinish(todoBaseEntity));
            return;
        }
        this.mTodoTextCheckGroup.setVisibility(0);
        if (!LiteUtils.isNewLiteOrMiddle()) {
            this.mTodoTextEdit.setHint(this.mContext.getString(R.string.todo_list_edit_text_hint));
        }
        this.mTodoTextEdit.setCursorVisible(true);
        this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
    }

    private void initTodoContent(TodoBaseEntity todoBaseEntity) {
        this.mIsSubMode = false;
        this.mTodoTextEdit.updateContent(todoBaseEntity.getContent(), null);
        this.mBtnTodoEditCreate.setEnabled(true);
        this.mTodoTextEdit.setMinLines(1);
        this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
        if (!LiteUtils.isNewLiteOrMiddle() && TextUtils.isEmpty(todoBaseEntity.getContent())) {
            this.mTodoTextEdit.setHint(NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
        }
        DisplayUtils.setMiuiRegularTypeFace(this.mTodoTextEdit);
        this.mTodoTextEdit.postDelayed(new Runnable() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initTodoContent$8();
            }
        }, 50L);
        if (todoBaseEntity.getInputType() != 1) {
            this.mTodoTextCheckGroup.setVisibility(0);
        } else {
            this.mTodoTextCheckGroup.setVisibility(8);
        }
        initRemindTime(todoBaseEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTodoContent$8() {
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        todoRichEditor.setSelection(todoRichEditor.getText().toString().length(), this.mTodoTextEdit.getText().toString().length());
        this.mTodoTextEdit.requestFocus();
        this.mTodoTextEdit.setCursorVisible(true);
    }

    private void initSubTodoContent(TodoBaseEntity todoBaseEntity) {
        this.mIsSubMode = true;
        this.mBtnTodoEditCreate.setEnabled(true);
        this.mSubTodoData = new SubModeImpl(todoBaseEntity.getContent());
        ViewGroup.LayoutParams layoutParams = this.mRvTodoSubList.getLayoutParams();
        layoutParams.height = calSubTodoHeight(-1, -1) + this.mRvTodoSubList.getPaddingTop();
        this.mRvTodoSubList.setLayoutParams(layoutParams);
        this.mSubTodoEditAdapter.setEditIndex(this.mSubTodoData.getSubTodoSize() - 1);
        this.mSubTodoEditAdapter.bindDataSource(this.mSubTodoData);
        this.mSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
        this.mRvTodoSubList.setAdapter(this.mSubTodoEditAdapter);
        DisplayUtils.setMiuiMediumTypeFace(this.mTitleEditText);
        this.mTitleEditText.setText(this.mSubTodoData.getTitle());
        this.mTitleEditText.setHint(NoteApp.getInstance().getString(R.string.todo_list));
        changeEditorContainerHeight(true);
        addTitleMonitor();
        initRemindTime(todoBaseEntity);
    }

    private int calSubTodoHeight(int i, int i2) {
        TextPaint textPaint;
        int windowWidth;
        View view;
        SubTodoRichEditor subTodoRichEditor;
        Logger.INSTANCE.d(TAG, "calSubTodoHeight: type = " + i2 + " index = " + i + " height = " + this.subTodoHeight);
        RecyclerView recyclerView = this.mRvTodoSubList;
        if (recyclerView == null || recyclerView.findViewHolderForLayoutPosition(0) == null || (view = this.mRvTodoSubList.findViewHolderForLayoutPosition(0).itemView) == null || (subTodoRichEditor = (SubTodoRichEditor) view.findViewById(R.id.editor)) == null || subTodoRichEditor.getLayout() == null) {
            textPaint = null;
            windowWidth = 0;
        } else {
            windowWidth = subTodoRichEditor.getLayout().getWidth();
            textPaint = subTodoRichEditor.getLayout().getPaint();
        }
        if (windowWidth == 0) {
            windowWidth = (UIUtils.getWindowWidth(NoteApp.getInstance()) - (this.horizontalMargin * 2)) - ((int) this.mContext.getResources().getDimension(this.horizontalMargin == 0 ? R.dimen.todo_margin : R.dimen.todo_margin_with_horizontalMargin));
        }
        if (textPaint == null) {
            textPaint = new TextPaint();
            textPaint.setTextSize(NoteApp.getInstance().getResources().getDimension(R.dimen.pad_todo_edit_subtodo_text_size));
        }
        this.subTodoMarginHeight = ((int) this.mContext.getResources().getDimension(R.dimen.todo_edit_subEditor_spacing)) * this.mSubTodoData.getSubTodoSize() * 2;
        if (i2 == 0) {
            if (this.mSubTodoEditAdapter.getIsFirstSave()) {
                if (this.oldSubTodoData.getSubTodoSize() < this.mSubTodoData.getSubTodoSize()) {
                    int i3 = i + 1;
                    this.subTodoHeight += StaticLayout.Builder.obtain(this.mSubTodoData.getEntity(i3).getContent(), 0, this.mSubTodoData.getEntity(i3).getContent().length(), textPaint, windowWidth).build().getHeight();
                } else {
                    this.subTodoHeight -= StaticLayout.Builder.obtain(this.oldSubTodoData.getEntity(i).getContent(), 0, this.oldSubTodoData.getEntity(i).getContent().length(), textPaint, windowWidth).build().getHeight();
                    this.subTodoHeight += StaticLayout.Builder.obtain(this.mSubTodoData.getEntity(i).getContent(), 0, this.mSubTodoData.getEntity(i).getContent().length(), textPaint, windowWidth).build().getHeight();
                }
                this.mSubTodoEditAdapter.setIsFirstSave(false);
            }
        } else if (i2 == 1) {
            this.subTodoHeight -= StaticLayout.Builder.obtain(this.oldSubTodoData.getEntity(i).getContent(), 0, this.oldSubTodoData.getEntity(i).getContent().length(), textPaint, windowWidth).build().getHeight();
        } else if (i2 == 2) {
            if (i > 0) {
                this.subTodoHeight -= StaticLayout.Builder.obtain(this.oldSubTodoData.getEntity(i).getContent(), 0, this.oldSubTodoData.getEntity(i).getContent().length(), textPaint, windowWidth).build().getHeight();
                int i4 = i - 1;
                this.subTodoHeight -= StaticLayout.Builder.obtain(this.oldSubTodoData.getEntity(i4).getContent(), 0, this.oldSubTodoData.getEntity(i4).getContent().length(), textPaint, windowWidth).build().getHeight();
                this.subTodoHeight += StaticLayout.Builder.obtain(this.mSubTodoData.getEntity(i4).getContent(), 0, this.mSubTodoData.getEntity(i4).getContent().length(), textPaint, windowWidth).build().getHeight();
            }
        } else if (i2 == 3) {
            this.subTodoHeight -= StaticLayout.Builder.obtain(this.oldSubTodoData.getEntity(i).getContent(), 0, this.oldSubTodoData.getEntity(i).getContent().length(), textPaint, windowWidth).build().getHeight();
            int subTodoSize = this.mSubTodoData.getSubTodoSize() - this.oldSubTodoData.getSubTodoSize();
            for (int i5 = i; i5 <= i + subTodoSize; i5++) {
                this.subTodoHeight += StaticLayout.Builder.obtain(this.mSubTodoData.getEntity(i5).getContent(), 0, this.mSubTodoData.getEntity(i5).getContent().length(), textPaint, windowWidth).build().getHeight();
            }
        } else {
            this.subTodoHeight = 0;
            for (int i6 = 0; i6 < this.mSubTodoData.getSubTodoSize(); i6++) {
                this.subTodoHeight += StaticLayout.Builder.obtain(this.mSubTodoData.getEntity(i6).getContent(), 0, this.mSubTodoData.getEntity(i6).getContent().length(), textPaint, windowWidth).build().getHeight();
            }
        }
        Logger.INSTANCE.d(TAG, "calSubTodoHeight: newHeight = " + this.subTodoHeight);
        return this.subTodoHeight + this.subTodoMarginHeight;
    }

    protected int getSubTodoHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.sub_todo_item_height);
    }

    private void changeEditorContainerHeight(boolean z) {
        if (z) {
            this.mTodoEditContainer.setMaxHeight(this.mTodoTextEdit.getLineHeight() * 3);
        } else {
            this.mTodoEditContainer.setMaxHeight(-1);
        }
    }

    private void initRemindTime(TodoBaseEntity todoBaseEntity) {
        long remindTime = todoBaseEntity.getRemindTime();
        int remindType = todoBaseEntity.getRemindType();
        int remindRepeatType = todoBaseEntity.getRemindRepeatType();
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setActivated(false);
        if (remindType != 0) {
            if (todoBaseEntity.getRemindType() == 2 || todoBaseEntity.getRemindType() == 1) {
                this.mIvRemindCustom.setSelected(true);
            }
            this.mTagRemindCustom.setSelected(true);
            this.mTagRemindCustom.setText(CalenderUtils.getScheduleTimeString(remindType, remindTime));
            this.mTagRemindCustomGroup.setContentDescription(CalenderUtils.getScheduleTimeString(remindType, remindTime));
            updateRemindCustomTagPaddingEnd(true);
            this.mTagRemindCustomDivider.setVisibility(0);
            this.mTagRemindCustomCancel.setVisibility(0);
            if (CalenderUtils.isTimeExpired(todoBaseEntity.getRemindTime())) {
                this.mTagRemindCustom.setSelected(false);
                this.mIvRemindCustom.setSelected(false);
                this.mTagRemindCustom.setActivated(true);
                this.mIvRemindCustom.setActivated(true);
            }
        }
        setRemindCustom(remindRepeatType);
    }

    protected void setRemindCustom(int i) {
        if (i > 0) {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind_repeat));
        } else {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind));
        }
    }

    private void createSubTodoFromTodo(String str) {
        String[] stringArrayByChar = CharUtils.getStringArrayByChar(str, '\n');
        this.mSubTodoData = new SubModeImpl("");
        for (int i = 0; i < stringArrayByChar.length; i++) {
            this.mSubTodoData.add(new SubTodoEntity(i, stringArrayByChar[i], false));
        }
        if (stringArrayByChar.length == 1) {
            this.mSubTodoData.add(new SubTodoEntity(stringArrayByChar.length, "", false));
        }
        this.mRvTodoSubList.setAdapter(this.mSubTodoEditAdapter);
        if (stringArrayByChar.length > 1) {
            this.mSubTodoEditAdapter.setSplitDataIndex(stringArrayByChar.length - 1);
        } else {
            this.mSubTodoEditAdapter.setEditIndex(this.mSubTodoData.getSubTodoSize() - 1);
        }
        this.mSubTodoEditAdapter.bindDataSource(this.mSubTodoData);
        this.mSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeEditMode(boolean z) {
        if (z) {
            if (this.mIsSubMode) {
                return;
            }
            this.mIsSubMode = true;
            this.mTodoTextCheckGroup.setVisibility(8);
            this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
            DisplayUtils.setMiuiMediumTypeFace(this.mTodoTextEdit);
            String string = this.mTodoTextEdit.getText().toString();
            this.mTitleEditText.setText((CharSequence) null);
            this.mTitleEditText.setHint(NoteApp.getInstance().getString(R.string.todo_list));
            createSubTodoFromTodo(string);
            addTitleMonitor();
            this.mBtnTodoEditCreate.setEnabled(true);
        } else {
            this.mIsSubMode = false;
            String title = this.mSubTodoData.getTitle();
            if (title == null) {
                title = "";
            }
            this.mTodoTextCheckGroup.setVisibility(0);
            DisplayUtils.setMiuiRegularTypeFace(this.mTodoTextEdit);
            this.mTodoTextEdit.setMaxLines(Integer.MAX_VALUE);
            this.mTodoTextEdit.removeTextChangedListener(this.mSubTodoTitleWatcher);
            this.mTodoTextEdit.setCursorVisible(true);
            if (!LiteUtils.isNewLiteOrMiddle()) {
                this.mTodoTextEdit.setText(title);
            }
            this.mTodoTextEdit.setHint(this.mContext.getString(R.string.todo_list_edit_text_hint));
            if (this.mSubTodoEditAdapter.getBindContext().getCursorIndexOnMergeToNormalTodo() == -1) {
                this.mTodoTextEdit.setSelection(title.length());
            } else {
                this.mTodoTextEdit.setSelection(this.mSubTodoEditAdapter.getBindContext().getCursorIndexOnMergeToNormalTodo());
                this.mSubTodoEditAdapter.getBindContext().setCursorIndexOnMergeToNormalTodo(-1);
            }
            this.mTodoTextEdit.requestFocus();
            this.mLastFocusedViewForSoftInput = this.mTodoTextEdit;
            TodoUtils.showSoftInput(getKeyboardRequestView());
            this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
        }
        changeEditorContainerHeight(z);
        notifyTodoListTypeChanged(z ? 1 : 0);
        notifySubTodoContentChanged(z ? this.mSubTodoData.getContent() : this.mTodoTextEdit.getText().toString());
        createSubTodoEditorAnim(z).start();
    }

    private ValueAnimator createSubTodoEditorAnim(boolean z) {
        return createSubTodoEditorChanged(z ? calSubTodoHeight(-1, -1) + this.mRvTodoSubList.getPaddingTop() : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSubTodoEditorOnDataChanged(int i, int i2) {
        if (this.mSubTodoData == null || this.mContext == null) {
            return;
        }
        int iCalSubTodoHeight = calSubTodoHeight(i, i2) + this.mRvTodoSubList.getPaddingTop();
        ValueAnimator valueAnimator = this.mSubTodoEditorLayoutAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mSubTodoEditorLayoutAnimator.cancel();
        }
        ValueAnimator valueAnimatorCreateSubTodoEditorChanged = createSubTodoEditorChanged(iCalSubTodoHeight);
        this.mSubTodoEditorLayoutAnimator = valueAnimatorCreateSubTodoEditorChanged;
        valueAnimatorCreateSubTodoEditorChanged.start();
    }

    private ValueAnimator createSubTodoEditorChanged(int i) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvTodoSubList.getHeight(), i);
        valueAnimatorOfInt.setDuration(100L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createSubTodoEditorChanged$9(valueAnimator);
            }
        });
        valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.9
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        return valueAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSubTodoEditorChanged$9(ValueAnimator valueAnimator) {
        int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        ViewGroup.LayoutParams layoutParams = this.mRvTodoSubList.getLayoutParams();
        layoutParams.height = iMax;
        this.mRvTodoSubList.setLayoutParams(layoutParams);
    }

    public void setContentData(TodoBaseEntity todoBaseEntity) {
        this.mTodoData = todoBaseEntity;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x001f  */
    /* JADX WARN: Code duplicated, block: B:14:0x002d  */
    /* JADX WARN: Code duplicated, block: B:17:0x0052  */
    /* JADX WARN: Code duplicated, block: B:19:0x0068  */
    /* JADX WARN: Code duplicated, block: B:20:0x007d  */
    /* JADX WARN: Code duplicated, block: B:21:0x0092  */
    /* JADX WARN: Code duplicated, block: B:23:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:26:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:28:0x00ca  */
    public void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig) {
        String scheduleTimeString;
        boolean z2;
        if (remindTimeConfig != null) {
            scheduleTimeString = CalenderUtils.getScheduleTimeString(2, remindTimeConfig.remindTime);
            if (z && CalenderUtils.isTimeExpired(remindTimeConfig.remindTime)) {
                z2 = true;
            }
            if (!TextUtils.isEmpty(scheduleTimeString)) {
                this.mTagRemindCustom.setText(scheduleTimeString);
                this.mTagRemindCustomGroup.setContentDescription(scheduleTimeString);
                updateRemindCustomTagPaddingEnd(true);
            } else {
                this.mTagRemindCustom.setText(TodoUtils.getString(R.string.todo_edit_btn_custom));
                this.mTagRemindCustomGroup.setContentDescription(scheduleTimeString);
                updateRemindCustomTagPaddingEnd(false);
            }
            this.mTagRemindCustom.setSelected(z);
            this.mTagRemindCustom.setActivated(false);
            this.mIvRemindCustom.setActivated(false);
            if (z) {
                this.mTagRemindCustom.setSelected(true);
                this.mIvRemindCustom.setSelected(true);
                this.mTagRemindCustomDivider.setVisibility(0);
                this.mTagRemindCustomCancel.setVisibility(0);
                if (z2) {
                    this.mTagRemindCustom.setSelected(false);
                    this.mIvRemindCustom.setSelected(false);
                    this.mTagRemindCustom.setActivated(true);
                    this.mIvRemindCustom.setActivated(true);
                } else {
                    this.mTagRemindCustom.setSelected(z);
                    this.mIvRemindCustom.setSelected(z);
                    this.mTagRemindCustom.setActivated(false);
                    this.mIvRemindCustom.setActivated(false);
                }
            } else {
                this.mTagRemindCustom.setSelected(false);
                this.mIvRemindCustom.setSelected(false);
                this.mTagRemindCustomDivider.setVisibility(8);
                this.mTagRemindCustomCancel.setVisibility(8);
            }
            setRemindCustom(remindTimeConfig != null ? remindTimeConfig.repeatType : 0);
            this.mTodoTextEdit.setFocusable(true);
            this.mTodoTextEdit.setFocusableInTouchMode(true);
            this.mTagRemindCustom.requestLayout();
            if (isInTodoEditMode()) {
                showSoftInputOnTodoEditMode(200L);
            } else {
                showSoftInputOnSubTodoEditMode(200L);
            }
        }
        scheduleTimeString = null;
        z2 = false;
        if (!TextUtils.isEmpty(scheduleTimeString)) {
            this.mTagRemindCustom.setText(scheduleTimeString);
            this.mTagRemindCustomGroup.setContentDescription(scheduleTimeString);
            updateRemindCustomTagPaddingEnd(true);
        } else {
            this.mTagRemindCustom.setText(TodoUtils.getString(R.string.todo_edit_btn_custom));
            this.mTagRemindCustomGroup.setContentDescription(scheduleTimeString);
            updateRemindCustomTagPaddingEnd(false);
        }
        this.mTagRemindCustom.setSelected(z);
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setActivated(false);
        if (z) {
            this.mTagRemindCustom.setSelected(true);
            this.mIvRemindCustom.setSelected(true);
            this.mTagRemindCustomDivider.setVisibility(0);
            this.mTagRemindCustomCancel.setVisibility(0);
            if (z2) {
                this.mTagRemindCustom.setSelected(false);
                this.mIvRemindCustom.setSelected(false);
                this.mTagRemindCustom.setActivated(true);
                this.mIvRemindCustom.setActivated(true);
            } else {
                this.mTagRemindCustom.setSelected(z);
                this.mIvRemindCustom.setSelected(z);
                this.mTagRemindCustom.setActivated(false);
                this.mIvRemindCustom.setActivated(false);
            }
        } else {
            this.mTagRemindCustom.setSelected(false);
            this.mIvRemindCustom.setSelected(false);
            this.mTagRemindCustomDivider.setVisibility(8);
            this.mTagRemindCustomCancel.setVisibility(8);
        }
        setRemindCustom(remindTimeConfig != null ? remindTimeConfig.repeatType : 0);
        this.mTodoTextEdit.setFocusable(true);
        this.mTodoTextEdit.setFocusableInTouchMode(true);
        this.mTagRemindCustom.requestLayout();
        if (isInTodoEditMode()) {
            showSoftInputOnTodoEditMode(200L);
        } else {
            showSoftInputOnSubTodoEditMode(200L);
        }
    }

    protected void notifyTodoContentChanged() {
        TodoEditListener todoEditListener = this.mTodoEditListener;
        if (todoEditListener != null) {
            todoEditListener.onTextChanged(this.mTodoTextEdit.getRichTextData());
        }
    }

    protected void notifyTodoListTypeChanged(int i) {
        TodoEditListener todoEditListener = this.mTodoEditListener;
        if (todoEditListener != null) {
            todoEditListener.onTodoTypeChanged(i);
        }
    }

    protected void notifySubTodoContentChanged(String str) {
        TodoEditListener todoEditListener = this.mTodoEditListener;
        if (todoEditListener == null || str == null) {
            return;
        }
        todoEditListener.onSubTodoChanged(str);
    }

    private void addTitleMonitor() {
        this.mTitleEditText.removeTextChangedListener(this.mTextEditorWatcher);
        this.mTitleEditText.addTextChangedListener(this.mSubTodoTitleWatcher);
        this.mTitleEditText.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController$$ExternalSyntheticLambda9
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return this.f$0.lambda$addTitleMonitor$10(view, i, keyEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$addTitleMonitor$10(View view, int i, KeyEvent keyEvent) {
        SubTodoEditAdapter subTodoEditAdapter;
        if (i != 66 || keyEvent.getAction() != 1 || (subTodoEditAdapter = this.mSubTodoEditAdapter) == null) {
            return false;
        }
        subTodoEditAdapter.editNew();
        return false;
    }

    protected boolean isAboveAndroidR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    public void onKeyboardVisibleChanged(boolean z) {
        this.mIsKeyboardVisible = z;
    }

    public void saveTodoBeforeDestroy() {
        View.OnClickListener onClickListener;
        SubModeImpl subModeImpl;
        TextView textView = this.mBtnTodoEditCreate;
        if (textView == null || !textView.isEnabled()) {
            return;
        }
        if ((!this.mIsSubMode || (subModeImpl = this.mSubTodoData) == null || subModeImpl.getUnFinishSubTodoSize() != 0 || this.mSubTodoData.getFinishSubTodoSize() <= 0) && (onClickListener = this.mViewClickListener) != null) {
            onClickListener.onClick(this.mBtnTodoEditCreate);
        }
    }

    private void smallWindowAdapter() {
        View view = this.mRootView;
        if (view == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.miui.todo.feature.todoedit.BaseTodoEditController.10
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                Activity activity = UIUtils.getActivity(BaseTodoEditController.this.mRootView.getContext());
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view2);
                if (rootWindowInsets != null && activity != null) {
                    if (UIUtils.isInMultiWindowMode(activity)) {
                        BaseTodoEditController.this.mRootView.setPadding(0, 0, rootWindowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars()).right, 0);
                        return windowInsetsCompat;
                    }
                    BaseTodoEditController.this.mRootView.setPadding(0, 0, 0, 0);
                }
                return windowInsetsCompat;
            }
        });
    }
}
