package com.miui.pc.feature.todo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.pc.frame.MirrorToastManager;
import com.miui.pc.view.datepopupview.view.DatePopupWindow;
import com.miui.richeditor.ExternalTextWatcher;
import com.miui.richeditor.RichEditScrollView;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.TodoBaseEntity;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.mode.SubModeImpl;
import com.miui.todo.feature.todoedit.TodoEditTitleContainerView;
import com.miui.todo.feature.todoedit.TodoRichEditor;
import com.miui.todo.feature.todolist.SubTodoEditAdapter;
import com.miui.todo.feature.todolist.SubTodoEditAdapterListener;
import com.miui.todo.feature.todolist.SubTodoEditListener;
import com.miui.todo.feature.todolist.TodoEditListener;
import com.miui.todo.utils.CalenderUtils;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.EditRootLayout;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class PcTodoEditController {
    private static final String TAG = "PadTodoEditController";
    private TextView mBtnTodoEditCancel;
    private TextView mBtnTodoEditCreate;
    private final Context mContext;
    private EditRootLayout mEditRootLayout;
    private boolean mIsNew;
    private boolean mIsShow;
    private boolean mIsSubMode;
    private ImageView mIvRemindCustom;
    private final View mRootView;
    private RecyclerView mRvTodoSubList;
    private SubModeImpl mSubTodoData;
    private SubTodoEditAdapter mSubTodoEditAdapter;
    private ValueAnimator mSubTodoEditorLayoutAnimator;
    private final int mSubTodoItemHeight;
    private TextView mTagRemindCustom;
    private ImageView mTagRemindCustomCancel;
    private EditText mTitleEditText;
    private TodoBaseEntity mTodoData;
    private TodoEditTitleContainerView mTodoEditContainer;
    private final TodoEditListener mTodoEditListener;
    private View mTodoEditorGroup;
    protected View mTodoTextCheckGroup;
    private TodoRichEditor mTodoTextEdit;
    private View.OnClickListener mViewClickListener;
    private final TransitionListener mDismissListener = new TransitionListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController.1
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            ViewGroup viewGroup;
            super.onComplete(obj);
            if (PcTodoEditController.this.mRootView == null || (viewGroup = (ViewGroup) PcTodoEditController.this.mRootView.getParent()) == null) {
                return;
            }
            viewGroup.removeView(PcTodoEditController.this.mRootView);
        }
    };
    protected final TextWatcher mTextEditorWatcher = new TextWatcher() { // from class: com.miui.pc.feature.todo.PcTodoEditController.2
        private static final long MX_STATUS_CHECK_DURATION = 10000;
        private long lastCheckTime = System.currentTimeMillis();

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String string = charSequence.toString();
            if (!TextUtils.isEmpty(string) && string.length() > 2000) {
                PcTodoEditController pcTodoEditController = PcTodoEditController.this;
                pcTodoEditController.makeTextToToast(pcTodoEditController.getContext(), R.string.exceed_todo_text_limitation);
                String strSubstring = string.substring(0, 2000);
                int iMin = Math.min(2000, PcTodoEditController.this.mTodoTextEdit.getSelectionEnd());
                PcTodoEditController.this.mTodoTextEdit.setText(strSubstring);
                PcTodoEditController.this.mTodoTextEdit.setSelection(iMin, iMin);
                return;
            }
            PcTodoEditController.this.notifyTodoContentChanged();
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            boolean z = false;
            for (int i = 0; i < editable.length(); i++) {
                if (editable.charAt(i) == '\n') {
                    z = true;
                }
            }
            if (z && !LiteUtils.isNewLiteOrMiddle()) {
                if (PcTodoEditController.this.mTodoData != null && PcTodoEditController.this.mTodoData.getInputType() == 1) {
                    if (System.currentTimeMillis() - this.lastCheckTime > 10000) {
                        this.lastCheckTime = System.currentTimeMillis();
                    }
                    PcTodoEditController.this.notifyTodoContentChanged();
                    return;
                } else if (editable.length() == 1) {
                    PcTodoEditController.this.mTodoTextEdit.setText("");
                    return;
                } else if (PcTodoEditController.this.mTodoData == null || PcTodoEditController.this.mTodoData.getInputType() != 1) {
                    PcTodoEditController.this.changeEditMode(true);
                    return;
                }
            } else {
                if (PcTodoEditController.this.mTodoData != null && PcTodoEditController.this.mTodoData.getInputType() == 1 && !AudioUtils.hasAudioLabel(editable.toString())) {
                    PcTodoEditController.this.mTodoData.setAudioFileName(null);
                    PcTodoEditController.this.mTodoData.setInputType(0);
                    PcTodoEditController.this.mTodoTextCheckGroup.setVisibility(0);
                }
                if (System.currentTimeMillis() - this.lastCheckTime > 10000) {
                    this.lastCheckTime = System.currentTimeMillis();
                }
                PcTodoEditController.this.notifyTodoContentChanged();
            }
            if (!TextUtils.isEmpty(editable)) {
                PcTodoEditController.this.mTodoTextEdit.setHint("");
            } else {
                if (LiteUtils.isNewLiteOrMiddle()) {
                    return;
                }
                PcTodoEditController.this.mTodoTextEdit.setHint(PcTodoEditController.this.mIsSubMode ? NoteApp.getInstance().getString(R.string.todo_list) : NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
            }
        }
    };
    protected final TextWatcher mSubTodoTitleWatcher = new TextWatcher() { // from class: com.miui.pc.feature.todo.PcTodoEditController.3
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
            PcTodoEditController.this.mSubTodoData.setTitle(string);
            PcTodoEditController pcTodoEditController = PcTodoEditController.this;
            pcTodoEditController.notifySubTodoContentChanged(pcTodoEditController.mSubTodoData.getContent());
        }
    };
    protected final ExternalTextWatcher mRichEditorWatcher = new ExternalTextWatcher() { // from class: com.miui.pc.feature.todo.PcTodoEditController.4
        @Override // com.miui.richeditor.ExternalTextWatcher
        public void afterCheckBoxChanged() {
        }

        @Override // com.miui.richeditor.ExternalTextWatcher, android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // com.miui.richeditor.ExternalTextWatcher
        public void afterTextActuallyChanged(Editable editable) {
            if (PcTodoEditController.this.isInTodoEditMode()) {
                if (TextUtils.isEmpty(editable)) {
                    PcTodoEditController.this.mBtnTodoEditCreate.setEnabled(false);
                } else {
                    if (PcTodoEditController.this.mBtnTodoEditCreate.isEnabled()) {
                        return;
                    }
                    PcTodoEditController.this.mBtnTodoEditCreate.setEnabled(true);
                }
            }
        }
    };
    private final SubTodoEditListener mSubTodoEditListener = new SubTodoEditListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController.5
        @Override // com.miui.todo.feature.todolist.SubTodoEditListener
        public void onSubItemClicked(View view) {
        }

        @Override // com.miui.todo.feature.todolist.SubTodoEditListener
        public void onDataChanged(String str, int i, int i2) {
            PcTodoEditController.this.notifySubTodoContentChanged(str);
            if (PcTodoEditController.this.mSubTodoData.getSubTodoSize() == 0) {
                PcTodoEditController.this.changeEditMode(false);
            } else {
                PcTodoEditController.this.updateSubTodoEditorOnDataChanged();
            }
        }
    };
    private final SubTodoEditAdapterListener mSubTodoEditAdapterListener = new SubTodoEditAdapterListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController.6
        @Override // com.miui.todo.feature.todolist.SubTodoEditAdapterListener
        public void showToast(String str, long j) {
        }

        @Override // com.miui.todo.feature.todolist.SubTodoEditAdapterListener
        public void onCreateNewData(int i) {
            PcTodoEditController.this.mRvTodoSubList.smoothScrollToPosition(PcTodoEditController.this.mSubTodoEditAdapter.getItemCount());
        }
    };
    private TextView mTvSubTodoProgress = null;

    static /* synthetic */ void lambda$initView$2(View view) {
    }

    public PcTodoEditController(Context context, View.OnClickListener onClickListener, TodoEditListener todoEditListener, boolean z) {
        this.mContext = context;
        this.mRootView = LayoutInflater.from(context).inflate(R.layout.pc_todo_edit_layout, (ViewGroup) null, false);
        this.mViewClickListener = onClickListener;
        this.mTodoEditListener = todoEditListener;
        this.mIsNew = z;
        this.mSubTodoItemHeight = context.getResources().getDimensionPixelSize(R.dimen.pc_sub_todo_item_height);
        initView();
    }

    private void initView() {
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$0(view);
            }
        });
        EditRootLayout editRootLayout = (EditRootLayout) findViewById(R.id.content);
        this.mEditRootLayout = editRootLayout;
        editRootLayout.setKeyEventPreImeListener(new EditRootLayout.OnKeyEventPreImeListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda10
            @Override // com.miui.todo.view.EditRootLayout.OnKeyEventPreImeListener
            public final boolean onKeyEventPreIme(KeyEvent keyEvent) {
                return this.f$0.lambda$initView$1(keyEvent);
            }
        });
        findViewById(R.id.editor_scroll_view).setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PcTodoEditController.lambda$initView$2(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.cancel_button);
        this.mBtnTodoEditCancel = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$3(view);
            }
        });
        TextView textView2 = (TextView) findViewById(R.id.send_button);
        this.mBtnTodoEditCreate = textView2;
        DisplayUtils.setMiuiMediumTypeFace(textView2);
        this.mBtnTodoEditCreate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$4(view);
            }
        });
        TextView textView3 = (TextView) findViewById(R.id.tv_tag_custom);
        this.mTagRemindCustom = textView3;
        DisplayUtils.setMiuiMediumTypeFace(textView3);
        View viewFindViewById = findViewById(R.id.group_tag_custom);
        this.mIvRemindCustom = (ImageView) findViewById(R.id.iv_tag_custom);
        this.mTagRemindCustom.setSelected(false);
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setSelected(false);
        this.mIvRemindCustom.setActivated(false);
        viewFindViewById.setOnClickListener(this.mViewClickListener);
        ImageView imageView = (ImageView) findViewById(R.id.iv_tag_cancel);
        this.mTagRemindCustomCancel = imageView;
        imageView.setOnClickListener(this.mViewClickListener);
        initTodoEditorGroup();
        initSubTodoEditorGroup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4) {
            return false;
        }
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener == null) {
            return true;
        }
        onClickListener.onClick(this.mRootView);
        this.mViewClickListener = null;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(View view) {
        if (this.mIsNew && this.mIsSubMode && this.mSubTodoData.getUnFinishSubTodoSize() == 0 && this.mSubTodoData.getFinishSubTodoSize() > 0) {
            makeTextToToast(getContext(), R.string.todo_list_new_finish_all_tip);
            return;
        }
        View.OnClickListener onClickListener = this.mViewClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        dismiss();
    }

    public void show(View view) {
        this.mIsShow = true;
        this.mRootView.setAlpha(0.0f);
        ((ViewGroup) view).addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$5() {
        EditRootLayout editRootLayout = this.mEditRootLayout;
        editRootLayout.setTranslationX(editRootLayout.getWidth());
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f));
        Folme.useAt(this.mEditRootLayout).state().to(ViewProperty.TRANSLATION_X, 0);
    }

    public void dismiss() {
        this.mIsShow = false;
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
        Folme.useAt(this.mEditRootLayout).state().to(ViewProperty.TRANSLATION_X, Integer.valueOf(this.mEditRootLayout.getWidth()));
    }

    public boolean isShow() {
        return this.mIsShow;
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
        TodoUtils.hideSoftInput(getKeyboardRequestView());
    }

    private <T extends View> T findViewById(int i) {
        return (T) this.mRootView.findViewById(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Context getContext() {
        return this.mRootView.getContext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInTodoEditMode() {
        return !this.mIsSubMode;
    }

    private void showSoftInputOnTodoEditMode(long j) {
        this.mTodoTextEdit.requestFocus();
        this.mTodoTextEdit.postDelayed(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSoftInputOnTodoEditMode$6();
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftInputOnTodoEditMode$6() {
        TodoUtils.showSoftInput(getKeyboardRequestView());
    }

    private void showSoftInputOnSubTodoEditMode(long j) {
        this.mRvTodoSubList.postDelayed(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSoftInputOnSubTodoEditMode$7();
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftInputOnSubTodoEditMode$7() {
        if (this.mRvTodoSubList.getChildCount() > 0) {
            RecyclerView recyclerView = this.mRvTodoSubList;
            View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            if (childAt != null) {
                EditText editText = (EditText) childAt.findViewById(R.id.editor);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                TodoUtils.showSoftInput(getKeyboardRequestView());
            }
        }
    }

    private View getKeyboardRequestView() {
        return this.mRootView;
    }

    private void initTodoEditorGroup() {
        this.mTodoEditorGroup = findViewById(R.id.todo_editor);
        this.mTodoTextEdit = (TodoRichEditor) findViewById(R.id.rich_editor);
        this.mTodoEditContainer = (TodoEditTitleContainerView) findViewById(R.id.todo_editor_container);
        this.mTodoTextCheckGroup = findViewById(R.id.normal_todo_check_group);
        this.mTodoTextEdit.updateFontSizeId(0);
        this.mTodoTextEdit.setHighlightColor(getContext().getResources().getColor(R.color.theme_light_high_light_color));
        this.mTodoTextEdit.setScrollView((RichEditScrollView) findViewById(R.id.rich_editor_scrollview));
        this.mTodoTextEdit.setExternalTextWatcher(this.mRichEditorWatcher);
        this.mTodoTextEdit.setCursorVisible(false);
        this.mTodoTextEdit.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PcTodoEditController.lambda$initTodoEditorGroup$8(view);
            }
        });
        this.mTodoTextEdit.clearFocus();
        DisplayUtils.setMiuiRegularTypeFace(this.mTodoTextEdit);
        CompatUtils.invokeCursorColor(this.mTodoTextEdit, getContext().getResources().getColor(R.color.theme_light_cursor_color), getContext().getResources().getColor(R.color.theme_light_cursor_handle_color));
    }

    static /* synthetic */ void lambda$initTodoEditorGroup$8(View view) {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mTitleEditText = this.mTodoTextEdit;
        this.mTvSubTodoProgress = (TextView) this.mTodoEditorGroup.findViewById(R.id.tv_subtodo_progress);
        PcSubTodoEditAdapter pcSubTodoEditAdapter = new PcSubTodoEditAdapter(getContext());
        this.mSubTodoEditAdapter = pcSubTodoEditAdapter;
        pcSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
        this.mSubTodoEditAdapter.setHasStableIds(true);
        this.mSubTodoEditAdapter.setSubTodoEditListener(this.mSubTodoEditListener);
    }

    private void initContent(TodoBaseEntity todoBaseEntity) {
        if (todoBaseEntity != null) {
            if (todoBaseEntity.getListType() == 0) {
                initTodoContent(todoBaseEntity);
            } else if (todoBaseEntity.getListType() == 1) {
                initSubTodoContent(todoBaseEntity);
            }
            ((CheckBox) this.mTodoTextCheckGroup.findViewById(R.id.cb_subtodo)).setChecked(((TodoEntity) todoBaseEntity).getIsFinish() == 1);
            return;
        }
        this.mTodoTextCheckGroup.setVisibility(0);
        this.mTodoTextEdit.setCursorVisible(true);
        this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
    }

    private void initTodoContent(TodoBaseEntity todoBaseEntity) {
        this.mIsSubMode = false;
        this.mTodoTextEdit.updateContent(todoBaseEntity.getContent(), null);
        this.mBtnTodoEditCreate.setEnabled(true);
        this.mTodoTextEdit.setMaxLines(Integer.MAX_VALUE);
        this.mTodoTextEdit.setTextSize(0, getContext().getResources().getDimensionPixelSize(R.dimen.pc_todo_edit_text_size));
        this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
        this.mTodoTextEdit.setHint(NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
        this.mTodoTextEdit.postDelayed(new Runnable() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initTodoContent$9();
            }
        }, 50L);
        if (todoBaseEntity.getInputType() != 1) {
            this.mTodoTextCheckGroup.setVisibility(0);
        } else {
            this.mTodoTextCheckGroup.setVisibility(8);
        }
        initRemindTime(todoBaseEntity);
        this.mTvSubTodoProgress.setVisibility(8);
        this.mTvSubTodoProgress.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTodoContent$9() {
        Editable text = this.mTodoTextEdit.getText();
        String string = text == null ? "" : text.toString();
        this.mTodoTextEdit.setSelection(string.length(), string.length());
        this.mTodoTextEdit.setCursorVisible(true);
    }

    private void initSubTodoContent(TodoBaseEntity todoBaseEntity) {
        this.mIsSubMode = true;
        this.mBtnTodoEditCreate.setEnabled(true);
        this.mSubTodoData = new SubModeImpl(todoBaseEntity.getContent());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRvTodoSubList.getLayoutParams();
        layoutParams.height = this.mSubTodoItemHeight * this.mSubTodoData.getSubTodoSize();
        this.mRvTodoSubList.setLayoutParams(layoutParams);
        this.mSubTodoEditAdapter.setEditIndex(this.mSubTodoData.getSubTodoSize());
        this.mSubTodoEditAdapter.bindDataSource(this.mSubTodoData);
        this.mSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
        this.mRvTodoSubList.setAdapter(this.mSubTodoEditAdapter);
        this.mTitleEditText.setTextSize(0, getContext().getResources().getDimensionPixelSize(R.dimen.pc_todo_edit_text_title_size));
        this.mTitleEditText.setText(this.mSubTodoData.getTitle());
        this.mTitleEditText.setHint(NoteApp.getInstance().getString(R.string.todo_list));
        this.mTvSubTodoProgress.setVisibility(0);
        this.mTvSubTodoProgress.setText(this.mSubTodoData.getCompleteSchedule());
        changeEditorContainerHeight(true);
        addTitleMonitor();
        initRemindTime(todoBaseEntity);
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
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setActivated(false);
        this.mTagRemindCustom.setActivated(CalenderUtils.isTimeExpired(remindTime));
        if (remindType != 0) {
            if (todoBaseEntity.getRemindType() == 2 || todoBaseEntity.getRemindType() == 1) {
                this.mTagRemindCustom.setSelected(true);
                this.mIvRemindCustom.setSelected(true);
                this.mTagRemindCustom.setText(CalenderUtils.getScheduleTimeString(remindType, remindTime));
                this.mTagRemindCustomCancel.setVisibility(0);
                return;
            }
            this.mTagRemindCustom.setSelected(true);
            this.mTagRemindCustom.setText(CalenderUtils.getScheduleTimeString(remindType, remindTime));
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
        String string;
        String content = "";
        if (z) {
            if (this.mIsSubMode) {
                return;
            }
            this.mIsSubMode = true;
            this.mTodoTextCheckGroup.setVisibility(8);
            this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
            Editable text = this.mTodoTextEdit.getText();
            if (text == null) {
                string = "";
            } else {
                string = text.toString();
            }
            this.mTitleEditText.setText((CharSequence) null);
            this.mTitleEditText.setTextSize(0, getContext().getResources().getDimensionPixelSize(R.dimen.pc_todo_edit_text_title_size));
            this.mTitleEditText.setHint(getContext().getString(R.string.todo_list));
            createSubTodoFromTodo(string);
            addTitleMonitor();
            this.mBtnTodoEditCreate.setEnabled(true);
            this.mTvSubTodoProgress.setVisibility(0);
            this.mTvSubTodoProgress.setText(this.mSubTodoData.getCompleteSchedule());
        } else {
            this.mIsSubMode = false;
            String title = this.mSubTodoData.getTitle();
            if (title == null) {
                title = "";
            }
            this.mTodoTextCheckGroup.setVisibility(0);
            this.mTodoTextEdit.setMaxLines(Integer.MAX_VALUE);
            this.mTodoTextEdit.removeTextChangedListener(this.mSubTodoTitleWatcher);
            this.mTodoTextEdit.setCursorVisible(true);
            this.mTodoTextEdit.setText(title);
            this.mTodoTextEdit.setTextSize(0, getContext().getResources().getDimensionPixelSize(R.dimen.pc_todo_edit_text_size));
            if (!LiteUtils.isNewLiteOrMiddle()) {
                this.mTodoTextEdit.setHint(getContext().getString(R.string.todo_list_edit_text_hint));
            }
            if (this.mSubTodoEditAdapter.getBindContext().getCursorIndexOnMergeToNormalTodo() == -1) {
                this.mTodoTextEdit.setSelection(title.length());
            } else {
                this.mTodoTextEdit.setSelection(this.mSubTodoEditAdapter.getBindContext().getCursorIndexOnMergeToNormalTodo());
                this.mSubTodoEditAdapter.getBindContext().setCursorIndexOnMergeToNormalTodo(-1);
            }
            this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
            this.mTvSubTodoProgress.setVisibility(8);
            this.mTvSubTodoProgress.setText("");
        }
        changeEditorContainerHeight(z);
        notifyTodoListTypeChanged(z ? 1 : 0);
        Editable text2 = this.mTodoTextEdit.getText();
        if (text2 != null) {
            content = text2.toString();
        }
        if (z) {
            content = this.mSubTodoData.getContent();
        }
        notifySubTodoContentChanged(content);
        createSubTodoEditorAnim(z).start();
    }

    private ValueAnimator createSubTodoEditorAnim(boolean z) {
        return createSubTodoEditorChanged(z ? this.mSubTodoItemHeight * this.mSubTodoData.getSubTodoSize() : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSubTodoEditorOnDataChanged() {
        SubModeImpl subModeImpl = this.mSubTodoData;
        if (subModeImpl != null) {
            int subTodoSize = this.mSubTodoItemHeight * subModeImpl.getSubTodoSize();
            ValueAnimator valueAnimator = this.mSubTodoEditorLayoutAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.mSubTodoEditorLayoutAnimator.cancel();
            }
            ValueAnimator valueAnimatorCreateSubTodoEditorChanged = createSubTodoEditorChanged(subTodoSize);
            this.mSubTodoEditorLayoutAnimator = valueAnimatorCreateSubTodoEditorChanged;
            valueAnimatorCreateSubTodoEditorChanged.start();
            this.mTvSubTodoProgress.setText(this.mSubTodoData.getCompleteSchedule());
        }
    }

    private ValueAnimator createSubTodoEditorChanged(int i) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mRvTodoSubList.getHeight(), i);
        valueAnimatorOfInt.setDuration(100L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createSubTodoEditorChanged$10(valueAnimator);
            }
        });
        return valueAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSubTodoEditorChanged$10(ValueAnimator valueAnimator) {
        int iMax = Math.max(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRvTodoSubList.getLayoutParams();
        layoutParams.height = iMax;
        this.mRvTodoSubList.setLayoutParams(layoutParams);
    }

    public void setContentData(TodoBaseEntity todoBaseEntity) {
        this.mTodoData = todoBaseEntity;
        initContent(todoBaseEntity);
    }

    public void updateRemindCustomTag(boolean z, RemindTimeConfig remindTimeConfig) {
        String scheduleTimeString;
        boolean zIsTimeExpired;
        if (remindTimeConfig != null) {
            scheduleTimeString = CalenderUtils.getScheduleTimeString(2, remindTimeConfig.remindTime);
            zIsTimeExpired = CalenderUtils.isTimeExpired(remindTimeConfig.remindTime);
        } else {
            scheduleTimeString = null;
            zIsTimeExpired = false;
        }
        if (!TextUtils.isEmpty(scheduleTimeString)) {
            this.mTagRemindCustom.setText(scheduleTimeString);
            this.mTagRemindCustom.setTextColor(getContext().getColor(R.color.pc_todo_edit_remind_color));
        } else {
            this.mTagRemindCustom.setText("点击添加时间");
            this.mTagRemindCustom.setTextColor(getContext().getColor(R.color.pc_todo_edit_remind_tint_color));
        }
        this.mTagRemindCustom.setSelected(z);
        this.mIvRemindCustom.setActivated(false);
        this.mTagRemindCustom.setActivated(zIsTimeExpired);
        if (z) {
            this.mTagRemindCustom.setSelected(true);
            this.mIvRemindCustom.setSelected(true);
            this.mTagRemindCustomCancel.setVisibility(0);
            this.mTagRemindCustom.setSelected(z);
            this.mIvRemindCustom.setSelected(z);
            this.mTagRemindCustom.setActivated(false);
            this.mIvRemindCustom.setActivated(false);
        } else {
            this.mTagRemindCustom.setSelected(false);
            this.mIvRemindCustom.setSelected(false);
            this.mTagRemindCustomCancel.setVisibility(8);
        }
        this.mTodoTextEdit.setFocusable(true);
        this.mTodoTextEdit.setFocusableInTouchMode(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTodoContentChanged() {
        TodoEditListener todoEditListener = this.mTodoEditListener;
        if (todoEditListener != null) {
            todoEditListener.onTextChanged(this.mTodoTextEdit.getRichTextData());
        }
    }

    private void notifyTodoListTypeChanged(int i) {
        TodoEditListener todoEditListener = this.mTodoEditListener;
        if (todoEditListener != null) {
            todoEditListener.onTodoTypeChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySubTodoContentChanged(String str) {
        TodoEditListener todoEditListener = this.mTodoEditListener;
        if (todoEditListener != null) {
            todoEditListener.onSubTodoChanged(str);
        }
    }

    private void addTitleMonitor() {
        this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
        this.mTitleEditText.addTextChangedListener(this.mSubTodoTitleWatcher);
        this.mTitleEditText.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.pc.feature.todo.PcTodoEditController$$ExternalSyntheticLambda7
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return this.f$0.lambda$addTitleMonitor$11(view, i, keyEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$addTitleMonitor$11(View view, int i, KeyEvent keyEvent) {
        SubTodoEditAdapter subTodoEditAdapter;
        if (i != 66 || keyEvent.getAction() != 1 || (subTodoEditAdapter = this.mSubTodoEditAdapter) == null) {
            return false;
        }
        subTodoEditAdapter.editNew();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeTextToToast(Context context, int i) {
        MirrorToastManager.show(context, i, false);
    }

    public void showRemindTimePickerPopupWin(Long l, int i, PcTodoListFragment.OnTimeSetListener onTimeSetListener) {
        DatePopupWindow datePopupWindow = new DatePopupWindow(this.mContext, l.longValue(), i, onTimeSetListener);
        datePopupWindow.getContentView().measure(makeDropDownMeasureSpec(datePopupWindow.getWidth()), makeDropDownMeasureSpec(datePopupWindow.getHeight()));
        datePopupWindow.showAsDropDown(this.mEditRootLayout, 0, -(datePopupWindow.getContentView().getMeasuredHeight() + 10));
    }

    private static int makeDropDownMeasureSpec(int i) {
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), i == -2 ? 0 : 1073741824);
    }
}
