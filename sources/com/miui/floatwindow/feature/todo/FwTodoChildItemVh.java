package com.miui.floatwindow.feature.todo;

import android.animation.Animator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.floatwindow.view.FwFullScrollView;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.ExternalTextWatcher;
import com.miui.richeditor.RichEditScrollView;
import com.miui.todo.base.BaseListAdapterBindContext;
import com.miui.todo.base.IEditTodoView;
import com.miui.todo.base.TodoLayoutManager;
import com.miui.todo.base.todolist.BaseTodoChildItemVh;
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
import com.miui.todo.utils.CalenderUtils;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import com.miui.todo.view.swipemenulayout.SimpleSwipeListener;
import com.miui.todo.view.swipemenulayout.SwipeMenuLayout;

/* JADX INFO: loaded from: classes2.dex */
public class FwTodoChildItemVh extends BaseTodoChildItemVh implements IEditTodoView {
    private static final String TAG = "FwTodoChildItemVh";
    private final Runnable mActionShowSoftInput;
    protected FwTodoListAdapterBindContext mBindContext;
    protected TextView mBtnTodoEditCreate;
    protected OnItemEditListener mEditListener;
    private final View.OnClickListener mEditModeClickListener;
    protected View mEditRootView;
    View.OnAttachStateChangeListener mEditTextAttachStateChangeListener;
    protected boolean mIsInEdit;
    private boolean mIsNew;
    protected boolean mIsRecording;
    private boolean mIsRootViewAniming;
    private boolean mIsSubMode;
    protected ImageView mIvRemindCustom;
    protected View mLastFocusedViewForSoftInput;
    private RecyclerView mRvTodoSubList;
    protected boolean mStarted;
    private SubModeImpl mSubTodoData;
    private SubTodoEditAdapter mSubTodoEditAdapter;
    private final SubTodoEditAdapterListener mSubTodoEditAdapterListener;
    private final SubTodoEditListener mSubTodoEditListener;
    protected final TextWatcher mSubTodoTextWatcher;
    protected TextView mTagRemindCustom;
    protected ImageView mTagRemindCustomCancel;
    protected View mTagRemindCustomDivider;
    protected View mTagRemindCustomGroup;
    protected View mTextCreateGroup;
    protected final TextWatcher mTextEditorWatcher;
    private EditText mTitleEditText;
    protected View mToastGroup;
    protected TextView mToastMes;
    protected TodoEditTitleContainerView mTodoEditContainer;
    private View mTodoEditorGroup;
    protected TodoEntity mTodoEntity;
    protected View mTodoTextCheckGroup;
    protected TodoRichEditor mTodoTextEdit;
    protected boolean mUseAudio;

    @Override // com.miui.todo.base.IEditTodoView
    public void cancelRecord() {
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void completeRecord() {
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected int getSelectedFg() {
        return R.drawable.phone_todo_item_press_fg;
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void prepareRecord() {
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void willCancelRecord() {
    }

    public static FwTodoChildItemVh newInstance(ViewGroup viewGroup) {
        if (RomUtils.isPadMode()) {
            return new FwTodoChildItemVh(UIUtils.inflateView(viewGroup, R.layout.pad_todo_fw_task_normal_item));
        }
        return new FwTodoChildItemVh(UIUtils.inflateView(viewGroup, R.layout.todo_fw_task_normal_item));
    }

    public FwTodoChildItemVh(final View view) {
        super(view);
        this.mIsInEdit = false;
        this.mEditListener = null;
        this.mIsRecording = false;
        this.mActionShowSoftInput = new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.1
            @Override // java.lang.Runnable
            public void run() {
                TodoUtils.showSoftInput(FwTodoChildItemVh.this.mLastFocusedViewForSoftInput);
            }
        };
        this.mSubTodoEditListener = new SubTodoEditListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.2
            @Override // com.miui.todo.feature.todolist.SubTodoEditListener
            public void onDataChanged(String str, int i, int i2) {
                FwTodoChildItemVh.this.notifySubTodoContentChanged(str);
                if (FwTodoChildItemVh.this.mSubTodoData.getSubTodoSize() == 0) {
                    FwTodoChildItemVh.this.changeEditMode(false);
                }
            }

            @Override // com.miui.todo.feature.todolist.SubTodoEditListener
            public void onSubItemClicked(View view2) {
                FwTodoChildItemVh.this.mLastFocusedViewForSoftInput = view2;
            }
        };
        this.mIsRootViewAniming = false;
        this.mTextEditorWatcher = new TextWatcher() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.3
            private static final long MX_STATUS_CHECK_DURATION = 10000;
            private long lastCheckTime = System.currentTimeMillis();

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String string = charSequence.toString();
                if (!TextUtils.isEmpty(string) && string.length() > 2000) {
                    FwTodoChildItemVh.this.showFwToast(TodoUtils.getString(R.string.exceed_todo_text_limitation), EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
                    String strSubstring = string.substring(0, 2000);
                    int iMin = Math.min(FwTodoChildItemVh.this.mTodoTextEdit.getSelectionEnd(), 2000);
                    FwTodoChildItemVh.this.mTodoTextEdit.setText(strSubstring);
                    FwTodoChildItemVh.this.mTodoTextEdit.setSelection(iMin, iMin);
                    return;
                }
                FwTodoChildItemVh.this.notifyTodoContentChanged();
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                TodoEntity todoData = (FwTodoChildItemVh.this.mBindContext.getWorkingTodo() == null || FwTodoChildItemVh.this.mBindContext.getWorkingTodo().getTodoData() == null) ? null : FwTodoChildItemVh.this.mBindContext.getWorkingTodo().getTodoData();
                if (todoData == null) {
                    todoData = FwTodoChildItemVh.this.mTodoEntity;
                }
                boolean z = false;
                for (int i = 0; i < editable.length(); i++) {
                    if (editable.charAt(i) == '\n') {
                        z = true;
                    }
                }
                if (z && !LiteUtils.isNewLiteOrMiddle()) {
                    if (todoData != null && todoData.getInputType() == 1) {
                        if (System.currentTimeMillis() - this.lastCheckTime > 10000) {
                            this.lastCheckTime = System.currentTimeMillis();
                        }
                        FwTodoChildItemVh.this.notifyTodoContentChanged();
                        return;
                    } else if (editable.length() == 1) {
                        FwTodoChildItemVh.this.mTodoTextEdit.setText("");
                        FwTodoChildItemVh.this.notifyTodoContentChanged();
                        return;
                    } else if (todoData == null || todoData.getInputType() != 1) {
                        FwTodoChildItemVh.this.changeEditMode(true);
                        return;
                    }
                } else {
                    if (todoData != null && todoData.getInputType() == 1 && !AudioUtils.hasAudioLabel(editable.toString())) {
                        todoData.setAudioFileName(null);
                        todoData.setInputType(0);
                        FwTodoChildItemVh.this.mTodoTextEdit.clearRichTextData();
                        FwTodoChildItemVh.this.mTodoTextCheckGroup.setVisibility(0);
                    }
                    if (System.currentTimeMillis() - this.lastCheckTime > 10000) {
                        this.lastCheckTime = System.currentTimeMillis();
                    }
                    FwTodoChildItemVh.this.notifyTodoContentChanged();
                }
                if (!TextUtils.isEmpty(editable) || LiteUtils.isNewLiteOrMiddle()) {
                    FwTodoChildItemVh.this.mTodoTextEdit.setHint("");
                } else {
                    FwTodoChildItemVh.this.mTodoTextEdit.setHint(FwTodoChildItemVh.this.mIsSubMode ? NoteApp.getInstance().getString(R.string.todo_list) : NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
                }
            }
        };
        this.mEditModeClickListener = new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (Utils.isFastClick(view2.getId(), 500L)) {
                    return;
                }
                int id = view2.getId();
                if (id == R.id.group_tag_custom) {
                    Logger.INSTANCE.d(FwTodoChildItemVh.TAG, "click group tag custom");
                    if (FwTodoChildItemVh.this.mEditListener != null) {
                        FwTodoChildItemVh.this.mEditListener.onSetRemindTime(false);
                        return;
                    }
                    return;
                }
                if (id == R.id.iv_tag_cancel) {
                    Logger.INSTANCE.d(FwTodoChildItemVh.TAG, "click iv tag cancel");
                    if (FwTodoChildItemVh.this.mEditListener != null) {
                        FwTodoChildItemVh.this.mEditListener.onSetRemindTime(true);
                        return;
                    }
                    return;
                }
                if (id == R.id.send_button) {
                    Logger.INSTANCE.d(FwTodoChildItemVh.TAG, "click send button");
                    if (FwTodoChildItemVh.this.mEditListener != null) {
                        FwTodoChildItemVh.this.mEditListener.onExitEdit(true);
                        return;
                    }
                    return;
                }
                Logger.INSTANCE.d(FwTodoChildItemVh.TAG, "click elsewhere");
            }
        };
        this.mSubTodoTextWatcher = new TextWatcher() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.5
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
                FwTodoChildItemVh.this.mSubTodoData.setTitle(string);
                FwTodoChildItemVh fwTodoChildItemVh = FwTodoChildItemVh.this;
                fwTodoChildItemVh.notifySubTodoContentChanged(fwTodoChildItemVh.mSubTodoData.getContent());
            }
        };
        this.mEditTextAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.8
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                FwTodoChildItemVh.this.mTodoTextEdit.setCursorVisible(false);
                FwTodoChildItemVh.this.mTodoTextEdit.setCursorVisible(true);
            }
        };
        this.mSubTodoEditAdapterListener = new SubTodoEditAdapterListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.14
            @Override // com.miui.todo.feature.todolist.SubTodoEditAdapterListener
            public void onCreateNewData(int i) {
            }

            @Override // com.miui.todo.feature.todolist.SubTodoEditAdapterListener
            public void showToast(String str, long j) {
                FwTodoChildItemVh.this.showFwToast(str, j);
            }
        };
        view.setLongClickable(true);
        DisplayUtils.setMiuiMediumTypeFace(this.mContent);
        this.mRootView.setVisibility(0);
        this.mRootView.setAlpha(1.0f);
        this.mRootView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$new$0(view, view2);
            }
        });
        this.mRootView.setSwipeListener(new SimpleSwipeListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.6
            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onUpdate(SwipeMenuLayout swipeMenuLayout, float f, boolean z) {
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onStart(boolean z) {
                if (FwTodoChildItemVh.this.mSwipeListener != null) {
                    FwTodoChildItemVh.this.mSwipeListener.onSwipeStart(FwTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onEnd(boolean z) {
                if (z) {
                    FwTodoChildItemVh.this.mCheckBox.setChecked(!FwTodoChildItemVh.this.mCheckBox.isChecked());
                }
                if (FwTodoChildItemVh.this.mSwipeListener != null) {
                    FwTodoChildItemVh.this.mSwipeListener.onSwipeFinish(FwTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuOpen(boolean z) {
                FwTodoChildItemVh.this.updateViewStateByMenuState();
                if (FwTodoChildItemVh.this.mSwipeListener != null) {
                    FwTodoChildItemVh.this.mSwipeListener.onSwipeStop(FwTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onMenuClose(boolean z) {
                FwTodoChildItemVh.this.updateViewStateByMenuState();
                if (FwTodoChildItemVh.this.mSwipeListener != null) {
                    FwTodoChildItemVh.this.mSwipeListener.onSwipeStop(FwTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }

            @Override // com.miui.todo.view.swipemenulayout.SimpleSwipeListener
            public void onReset(boolean z) {
                if (FwTodoChildItemVh.this.mSwipeListener != null) {
                    FwTodoChildItemVh.this.mSwipeListener.onSwipeStop(FwTodoChildItemVh.this.getAdapterPosition(), z);
                }
            }
        });
        this.mToastGroup = view.findViewById(R.id.fw_toast_group);
        this.mToastMes = (TextView) view.findViewById(android.R.id.message);
        initEditGroup(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, View view2) {
        OnItemEditListener onItemEditListener;
        int id = view2.getId();
        if (id == R.id.group_item_group) {
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onToggleGroup(view);
                return;
            }
            return;
        }
        if (id == R.id.multi_checkbox) {
            if (this.mItemOperationListener != null) {
                this.mItemOperationListener.onSelected(view);
            }
        } else {
            if (id == R.id.sl_item_group) {
                if (!view2.isClickable() || (onItemEditListener = this.mEditListener) == null) {
                    return;
                }
                onItemEditListener.onEnterEdit(this.mTodoEntity, getAdapterPosition(), this, false);
                if (this.mTodoEntity == null) {
                    this.mIsNew = true;
                    return;
                }
                return;
            }
            if (this.mOtherViewClickListener != null) {
                this.mOtherViewClickListener.onClick(view2);
            }
        }
    }

    private void initEditGroup(View view) {
        View viewFindViewById = view.findViewById(R.id.todo_edit);
        this.mEditRootView = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mEditModeClickListener);
        this.mTodoEditorGroup = this.mEditRootView.findViewById(R.id.editor_container);
        this.mTextCreateGroup = view.findViewById(R.id.bottom_editor_panel);
        view.findViewById(R.id.bottom_editor).setOnClickListener(new View.OnClickListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$initEditGroup$1(view2);
            }
        });
        this.mTodoTextEdit = (TodoRichEditor) view.findViewById(R.id.rich_editor);
        RichEditScrollView richEditScrollView = (RichEditScrollView) view.findViewById(R.id.editor_scroll_view);
        richEditScrollView.setNestedScrollingEnabled(false);
        this.mTodoTextEdit.setScrollView(richEditScrollView);
        this.mTodoEditContainer = (TodoEditTitleContainerView) view.findViewById(R.id.todo_editor_container);
        this.mTodoTextCheckGroup = view.findViewById(R.id.normal_todo_check_group);
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        CompatUtils.invokeCursorColor(todoRichEditor, todoRichEditor.getContext().getResources().getColor(R.color.theme_light_cursor_color), this.mTodoTextEdit.getContext().getResources().getColor(R.color.theme_light_cursor_handle_color));
        DisplayUtils.setMiuiRegularTypeFace(this.mTodoTextEdit);
        TextView textView = (TextView) view.findViewById(R.id.send_button);
        this.mBtnTodoEditCreate = textView;
        textView.setOnClickListener(this.mEditModeClickListener);
        DisplayUtils.setMiuiMediumTypeFace(this.mBtnTodoEditCreate);
        TextView textView2 = (TextView) view.findViewById(R.id.tv_tag_custom);
        this.mTagRemindCustom = textView2;
        DisplayUtils.setMiuiMediumTypeFace(textView2);
        View viewFindViewById2 = view.findViewById(R.id.group_tag_custom);
        this.mTagRemindCustomGroup = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mEditModeClickListener);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tag_cancel);
        this.mTagRemindCustomCancel = imageView;
        imageView.setOnClickListener(this.mEditModeClickListener);
        this.mTagRemindCustomDivider = view.findViewById(R.id.v_tag_divider);
        this.mIvRemindCustom = (ImageView) view.findViewById(R.id.iv_tag_custom);
        this.mTagRemindCustom.setSelected(false);
        this.mIvRemindCustom.setSelected(false);
        this.mTodoTextEdit.setExternalTextWatcher(new ExternalTextWatcher() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.7
            @Override // com.miui.richeditor.ExternalTextWatcher
            public void afterCheckBoxChanged() {
            }

            @Override // com.miui.richeditor.ExternalTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // com.miui.richeditor.ExternalTextWatcher
            public void afterTextActuallyChanged(Editable editable) {
                if (FwTodoChildItemVh.this.mIsSubMode) {
                    return;
                }
                if (TextUtils.isEmpty(editable)) {
                    FwTodoChildItemVh.this.mBtnTodoEditCreate.setEnabled(false);
                } else {
                    if (FwTodoChildItemVh.this.mBtnTodoEditCreate.isEnabled()) {
                        return;
                    }
                    FwTodoChildItemVh.this.mBtnTodoEditCreate.setEnabled(true);
                }
            }
        });
        this.mTodoTextEdit.setCursorVisible(false);
        initSubTodoEditorGroup(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initEditGroup$1(View view) {
        Editable text = this.mTodoTextEdit.getText();
        this.mTodoTextEdit.setSelection(text == null ? 0 : text.length());
        this.mTodoTextEdit.setCursorVisible(true);
        this.mTodoTextEdit.requestFocus();
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        this.mLastFocusedViewForSoftInput = todoRichEditor;
        TodoUtils.showSoftInput(todoRichEditor);
    }

    private void initSubTodoEditorGroup(View view) {
        RecyclerView recyclerView = (RecyclerView) this.mTodoEditorGroup.findViewById(R.id.rv_subtodo);
        this.mRvTodoSubList = recyclerView;
        recyclerView.setLayoutManager(new TodoLayoutManager(view.getContext()));
        this.mRvTodoSubList.setNestedScrollingEnabled(false);
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        this.mTitleEditText = todoRichEditor;
        todoRichEditor.setBackgroundColor(NoteApp.getInstance().getColor(R.color.todo_item_bg_color));
        SubTodoEditAdapter subTodoEditAdapter = new SubTodoEditAdapter(view.getContext(), true);
        this.mSubTodoEditAdapter = subTodoEditAdapter;
        subTodoEditAdapter.setSubTodoEditListener(this.mSubTodoEditListener);
        this.mSubTodoEditAdapter.setHasStableIds(true);
        this.mSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    public void bind(TodoEntity todoEntity, BaseListAdapterBindContext baseListAdapterBindContext, int i) {
        this.mBindContext = (FwTodoListAdapterBindContext) baseListAdapterBindContext;
        this.mIsSubMode = false;
        this.mCheckBox.setClickable(false);
        this.mCheckBox.setOnCheckedChangeListener(this.mCheckBoxListener);
        super.bind(todoEntity, baseListAdapterBindContext, i);
        this.mTodoEntity = todoEntity.copy();
        this.mRootView.setVisibility(0);
        this.mRootView.setAlpha(1.0f);
        this.mRootView.closeMenuByTouchDown(0);
        Drawable drawable = AppCompatResources.getDrawable(this.mRootView.getContext(), R.drawable.todo_fw_child_item_bg);
        if (drawable != null) {
            this.mRootView.findViewById(R.id.item_content).setBackground(drawable.mutate());
        }
        this.mContent.requestLayout();
        updateContentLayout(todoEntity);
        bindEditGroup();
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected int getNormalTimeTextColor() {
        return TodoUtils.getColor(R.color.todo_fw_item_time_text_common_color);
    }

    private void bindEditGroup() {
        this.mTagRemindCustom.setSelected(false);
        this.mIvRemindCustom.setSelected(false);
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setActivated(false);
        this.mTagRemindCustom.setText(TodoUtils.getString(R.string.todo_edit_btn_custom));
        this.mTagRemindCustomDivider.setVisibility(8);
        this.mTagRemindCustomCancel.setVisibility(8);
        if (this.mTodoEntity.getId() == this.mBindContext.getEditId()) {
            this.mIsInEdit = false;
            this.mBindContext.refreshEditView(this);
            if (this.mBindContext.getWorkingTodo() != null) {
                enterEditMode(this.mBindContext.getWorkingTodo().getTodoData(), false, false);
                return;
            } else {
                enterEditMode(this.mTodoEntity, false, false);
                return;
            }
        }
        resetLayout(false);
        this.mIsInEdit = false;
    }

    protected void initEditContent(TodoBaseEntity todoBaseEntity) {
        if (todoBaseEntity != null && todoBaseEntity.getListType() == 1) {
            initSubTodoEditContent(todoBaseEntity);
        } else {
            initTodoEditContent(todoBaseEntity);
        }
        if (todoBaseEntity != null) {
            ((CheckBox) this.mTodoTextCheckGroup.findViewById(R.id.cb_subtodo)).setChecked(((TodoEntity) todoBaseEntity).getIsFinish() == 1);
        }
    }

    private void initTodoEditContent(TodoBaseEntity todoBaseEntity) {
        SubModeImpl subModeImpl = new SubModeImpl("");
        this.mSubTodoData = subModeImpl;
        this.mSubTodoEditAdapter.bindDataSource(subModeImpl);
        this.mSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
        this.mTodoEditorGroup.setVisibility(0);
        this.mTodoEditorGroup.setAlpha(1.0f);
        if (!LiteUtils.isNewLiteOrMiddle()) {
            this.mTodoTextEdit.setHint(NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
        }
        this.mTodoTextEdit.setMaxLines(Integer.MAX_VALUE);
        this.mTodoTextEdit.setSelection(0);
        this.mTodoTextEdit.removeTextChangedListener(this.mSubTodoTextWatcher);
        this.mBtnTodoEditCreate.setEnabled(true);
        if (todoBaseEntity != null) {
            if (!TextUtils.isEmpty(todoBaseEntity.getContent())) {
                this.mTodoTextEdit.updateContent(todoBaseEntity.getContent(), null);
                Editable text = this.mTodoTextEdit.getText();
                this.mTodoTextEdit.setSelection(text == null ? 0 : text.length());
                this.mTodoTextEdit.setCursorVisible(false);
                this.mTodoTextEdit.setHint("");
            }
            if (((TodoEntity) todoBaseEntity).getId() == 0) {
                this.mTodoTextEdit.setSelection(0);
            }
            this.mTodoTextEdit.setCursorVisible(true);
            this.mTodoTextEdit.requestFocus();
            FwFullScrollView.sForbidScrollY = true;
            this.mTodoTextEdit.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$initTodoEditContent$2();
                }
            }, 200L);
            initRemindTime(todoBaseEntity);
            if (todoBaseEntity.getInputType() != 1) {
                this.mTodoTextCheckGroup.setVisibility(0);
            } else {
                this.mTodoTextCheckGroup.setVisibility(8);
            }
        } else {
            this.mTodoTextCheckGroup.setVisibility(0);
            this.mTodoTextEdit.setText((CharSequence) null);
            this.mTodoTextEdit.setCursorVisible(true);
            this.mTodoTextEdit.setSelection(0);
            this.mBtnTodoEditCreate.setEnabled(false);
        }
        this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTodoEditContent$2() {
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        this.mLastFocusedViewForSoftInput = todoRichEditor;
        TodoUtils.showSoftInput(todoRichEditor);
    }

    protected void initSubTodoEditContent(TodoBaseEntity todoBaseEntity) {
        this.mIsSubMode = true;
        this.mBtnTodoEditCreate.setEnabled(true);
        SubModeImpl subModeImpl = new SubModeImpl(todoBaseEntity.getContent());
        this.mSubTodoData = subModeImpl;
        this.mSubTodoEditAdapter.setEditIndex(subModeImpl.getSubTodoSize() - 1);
        this.mSubTodoEditAdapter.bindDataSource(this.mSubTodoData);
        this.mSubTodoEditAdapter.setNew(this.mIsNew);
        this.mSubTodoEditAdapter.setSubTodoEditAdapterListener(this.mSubTodoEditAdapterListener);
        this.mRvTodoSubList.setAdapter(this.mSubTodoEditAdapter);
        this.mTitleEditText.setText(this.mSubTodoData.getTitle());
        this.mTitleEditText.setHint(NoteApp.getInstance().getString(R.string.todo_list));
        showSoftInputOnSubTodoEditMode(200L);
        addTitleMonitor();
        initRemindTime(todoBaseEntity);
    }

    private void initRemindTime(TodoBaseEntity todoBaseEntity) {
        long remindTime = todoBaseEntity.getRemindTime();
        int remindType = todoBaseEntity.getRemindType();
        int remindRepeatType = todoBaseEntity.getRemindRepeatType();
        this.mTagRemindCustom.setActivated(false);
        this.mIvRemindCustom.setActivated(false);
        if (remindType != 0) {
            if (todoBaseEntity.getRemindType() == 2 || todoBaseEntity.getRemindType() == 1) {
                this.mTagRemindCustom.setSelected(true);
                this.mIvRemindCustom.setSelected(true);
                this.mTagRemindCustom.setText(CalenderUtils.getScheduleTimeString(remindType, remindTime));
                this.mTagRemindCustomDivider.setVisibility(0);
                this.mTagRemindCustomCancel.setVisibility(0);
            } else if (!CalenderUtils.isToday(remindTime) && !CalenderUtils.isTomorrow(remindTime)) {
                this.mTagRemindCustom.setSelected(true);
                this.mTagRemindCustom.setText(CalenderUtils.getScheduleTimeString(remindType, remindTime));
            }
            if (CalenderUtils.isTimeExpired(todoBaseEntity.getRemindTime())) {
                this.mTagRemindCustom.setSelected(false);
                this.mIvRemindCustom.setSelected(false);
                this.mTagRemindCustom.setActivated(true);
                this.mIvRemindCustom.setActivated(true);
            }
        }
        if (remindRepeatType > 0) {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind_repeat));
        } else {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind));
        }
    }

    private void showSoftInputOnSubTodoEditMode(long j) {
        Logger.INSTANCE.i(TAG, "showSoftInputOnSubTodoEditMode delay = " + j);
        this.mRvTodoSubList.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showSoftInputOnSubTodoEditMode$3();
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftInputOnSubTodoEditMode$3() {
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
                TodoUtils.showSoftInput(editText);
                FwFullScrollView.sForbidScrollY = true;
            }
        }
    }

    private void notifyTodoListTypeChanged(int i) {
        OnItemEditListener onItemEditListener = this.mEditListener;
        if (onItemEditListener != null) {
            onItemEditListener.onTodoTypeChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySubTodoContentChanged(String str) {
        OnItemEditListener onItemEditListener = this.mEditListener;
        if (onItemEditListener != null) {
            onItemEditListener.onSubTodoChanged(str);
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
            this.mTodoTextEdit.setCursorVisible(false);
            this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
            Editable text = this.mTodoTextEdit.getText();
            if (text == null) {
                string = "";
            } else {
                string = text.toString();
            }
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
            this.mTodoTextEdit.setMaxLines(Integer.MAX_VALUE);
            this.mTodoTextEdit.removeTextChangedListener(this.mSubTodoTextWatcher);
            this.mTodoTextEdit.setText(title);
            if (!LiteUtils.isNewLiteOrMiddle()) {
                this.mTodoTextEdit.setHint(NoteApp.getInstance().getString(R.string.todo_list_edit_text_hint));
            }
            if (this.mSubTodoEditAdapter.getBindContext().getCursorIndexOnMergeToNormalTodo() == -1) {
                this.mTodoTextEdit.setSelection(title.length());
            } else {
                this.mTodoTextEdit.setSelection(this.mSubTodoEditAdapter.getBindContext().getCursorIndexOnMergeToNormalTodo());
                this.mSubTodoEditAdapter.getBindContext().setCursorIndexOnMergeToNormalTodo(-1);
            }
            this.mTodoTextEdit.requestFocus();
            TodoRichEditor todoRichEditor = this.mTodoTextEdit;
            this.mLastFocusedViewForSoftInput = todoRichEditor;
            TodoUtils.showSoftInput(todoRichEditor);
            this.mTodoTextEdit.addTextChangedListener(this.mTextEditorWatcher);
        }
        notifyTodoListTypeChanged(z ? 1 : 0);
        Editable text2 = this.mTodoTextEdit.getText();
        if (text2 != null) {
            content = text2.toString();
        }
        if (z) {
            content = this.mSubTodoData.getContent();
        }
        notifySubTodoContentChanged(content);
    }

    private void addTitleMonitor() {
        this.mTitleEditText.removeTextChangedListener(this.mTextEditorWatcher);
        this.mTitleEditText.addTextChangedListener(this.mSubTodoTextWatcher);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderRecycledCallback
    public void onRecycled() {
        if (this.mStarted) {
            onAnimationStop(false);
        }
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        if (todoRichEditor != null) {
            todoRichEditor.removeCallbacks(this.mActionShowSoftInput);
            this.mLastFocusedViewForSoftInput = null;
        }
        this.mRootView.closeMenuByTouchDown(0);
        if (this.mTodoTextEdit == null || this.mTodoEntity.getId() != this.mBindContext.getEditId()) {
            return;
        }
        this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoItemVh
    public void setOtherViewClickListener(View.OnClickListener onClickListener) {
        super.setOtherViewClickListener(onClickListener);
        this.mTodoTextEdit.setOnClickListener(onClickListener);
    }

    public void setOnItemEditListener(OnItemEditListener onItemEditListener) {
        this.mEditListener = onItemEditListener;
        if (this.mTodoEntity.getId() == 0) {
            boolean z = this.mTodoEntity.getInputType() == 1;
            if (this.mEditListener == null || this.mBindContext.getEditId() == 0) {
                return;
            }
            this.mEditListener.onEnterEdit(null, 0, this, z);
            this.mIsNew = true;
        }
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    /* JADX INFO: renamed from: hasAnimationStarted */
    public boolean getMStarted() {
        return this.mStarted;
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStart(boolean z) {
        if (z) {
            this.mCheckBox.setVisibility(4);
            this.mCheckboxMask.setVisibility(4);
            this.mRootView.setSwipeEnable(false);
        }
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationUpdate(boolean z, float f) {
        if (!z) {
            f = 1.0f - f;
        }
        this.mCheckBox.setAlpha(1.0f - f);
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onAnimationStop(boolean z) {
        onClearAnimation();
        if (z) {
            return;
        }
        this.mCheckBox.setVisibility(0);
        this.mCheckboxMask.setVisibility(0);
    }

    @Override // com.miui.notes.ui.widget.ViewHolderEditableCallback
    public void onClearAnimation() {
        this.mCheckBox.setAlpha(1.0f);
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh, com.miui.notes.ui.widget.ViewHolderEditableCallback
    public boolean onUpdateEditable(boolean z, boolean z2) {
        super.onUpdateEditable(z, z2);
        this.itemView.setHapticFeedbackEnabled(true);
        if (!z) {
            return false;
        }
        this.mCheckBox.setVisibility(4);
        this.mCheckboxMask.setVisibility(4);
        this.mRootView.setSwipeEnable(false);
        return true;
    }

    @Override // com.miui.todo.base.IEditTodoView
    public View getEditTextView() {
        return this.mTodoTextEdit;
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void enterEditMode(TodoBaseEntity todoBaseEntity, boolean z, boolean z2) {
        this.mUseAudio = z;
        if (this.mIsInEdit) {
            return;
        }
        this.mIsInEdit = true;
        if (!z) {
            this.mTextCreateGroup.setVisibility(0);
            initEditContent(todoBaseEntity);
            showEditView(true, z2);
            this.mTodoTextEdit.addOnAttachStateChangeListener(this.mEditTextAttachStateChangeListener);
            if (todoBaseEntity == null) {
                TodoRichEditor todoRichEditor = this.mTodoTextEdit;
                this.mLastFocusedViewForSoftInput = todoRichEditor;
                todoRichEditor.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$enterEditMode$4();
                    }
                }, 200L);
            }
            showContentView(false, z2);
            return;
        }
        this.mTextCreateGroup.setVisibility(8);
        this.mRootView.setVisibility(8);
        this.mEditRootView.setAlpha(1.0f);
        this.mEditRootView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterEditMode$4() {
        if (!this.mTodoTextEdit.hasFocus()) {
            this.mTodoTextEdit.requestFocus();
            FwFullScrollView.sForbidScrollY = true;
        }
        TodoUtils.showSoftInput(this.mLastFocusedViewForSoftInput);
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void exitEditMode(boolean z, boolean z2) {
        TodoRichEditor todoRichEditor = this.mTodoTextEdit;
        if (todoRichEditor != null) {
            todoRichEditor.stopMediaPlayerIfNeed();
        }
        if (this.mIsInEdit) {
            this.mIsInEdit = false;
            TodoRichEditor todoRichEditor2 = this.mTodoTextEdit;
            if (todoRichEditor2 != null) {
                todoRichEditor2.clearFocus();
                this.mTodoTextEdit.destroyActionMode();
                this.mTodoTextEdit.removeOnAttachStateChangeListener(this.mEditTextAttachStateChangeListener);
            }
            if (z2) {
                resetLayout(true);
            }
            if (this.mIsRecording) {
                this.mIsRecording = false;
            }
            View view = this.mLastFocusedViewForSoftInput;
            if (view != null) {
                view.clearFocus();
                TodoUtils.hideSoftInput(this.mLastFocusedViewForSoftInput);
            }
            this.mLastFocusedViewForSoftInput = null;
        }
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void updateRemindCustomInfo(boolean z, RemindTimeConfig remindTimeConfig) {
        String scheduleTimeString = remindTimeConfig != null ? CalenderUtils.getScheduleTimeString(2, remindTimeConfig.remindTime) : null;
        if (!TextUtils.isEmpty(scheduleTimeString)) {
            this.mTagRemindCustom.setText(scheduleTimeString);
        } else {
            this.mTagRemindCustom.setText(TodoUtils.getString(R.string.todo_edit_btn_custom));
        }
        this.mTagRemindCustom.setSelected(z);
        if (z) {
            this.mTagRemindCustom.setSelected(true);
            this.mIvRemindCustom.setSelected(true);
            this.mTagRemindCustomDivider.setVisibility(0);
            this.mTagRemindCustomCancel.setVisibility(0);
        } else {
            this.mTagRemindCustom.setSelected(false);
            this.mIvRemindCustom.setSelected(false);
            this.mTagRemindCustomDivider.setVisibility(8);
            this.mTagRemindCustomCancel.setVisibility(8);
        }
        if (remindTimeConfig != null && remindTimeConfig.repeatType > 0) {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind_repeat));
        } else {
            this.mIvRemindCustom.setImageDrawable(TodoUtils.getDrawable(R.drawable.ic_todo_edit_remind));
        }
        if (remindTimeConfig != null && !TextUtils.isEmpty(scheduleTimeString) && CalenderUtils.isTimeExpired(remindTimeConfig.remindTime)) {
            this.mTagRemindCustom.setSelected(false);
            this.mIvRemindCustom.setSelected(false);
            this.mTagRemindCustom.setActivated(true);
            this.mIvRemindCustom.setActivated(true);
        } else {
            this.mTagRemindCustom.setSelected(false);
            this.mIvRemindCustom.setSelected(false);
            this.mTagRemindCustom.setActivated(false);
            this.mIvRemindCustom.setActivated(false);
        }
        View view = this.mLastFocusedViewForSoftInput;
        if (view != null) {
            view.requestFocus();
            View view2 = this.mLastFocusedViewForSoftInput;
            TodoRichEditor todoRichEditor = this.mTodoTextEdit;
            if (view2 == todoRichEditor) {
                todoRichEditor.setCursorVisible(true);
            }
        }
        this.itemView.postDelayed(this.mActionShowSoftInput, 300L);
    }

    @Override // com.miui.todo.base.IEditTodoView
    public void cancelSetRemindTime() {
        View view = this.mLastFocusedViewForSoftInput;
        if (view != null) {
            view.requestFocus();
            View view2 = this.mLastFocusedViewForSoftInput;
            TodoRichEditor todoRichEditor = this.mTodoTextEdit;
            if (view2 == todoRichEditor) {
                todoRichEditor.setCursorVisible(true);
            }
        }
        this.itemView.postDelayed(this.mActionShowSoftInput, 300L);
    }

    private void resetLayout(boolean z) {
        this.mTodoTextEdit.removeTextChangedListener(this.mTextEditorWatcher);
        if (!TextUtils.isEmpty(this.mTodoTextEdit.getRichTextData())) {
            showContentView(true, z);
            this.mRootView.setVisibility(0);
            this.mRootView.setAlpha(1.0f);
            this.mContent.requestLayout();
        }
        this.mTodoTextEdit.setCursorVisible(false);
        showEditView(false, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTodoContentChanged() {
        OnItemEditListener onItemEditListener = this.mEditListener;
        if (onItemEditListener != null) {
            onItemEditListener.onTextChanged(this, this.mTodoTextEdit.getRichTextData());
        }
    }

    private void showContentView(boolean z, boolean z2) {
        if (z) {
            if (this.mRootView.getVisibility() != 0 || this.mIsRootViewAniming) {
                this.mRootView.setVisibility(0);
                if (z2) {
                    this.mRootView.animate().alpha(1.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.9
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                            FwTodoChildItemVh.this.mIsRootViewAniming = true;
                            Logger.INSTANCE.i(FwTodoChildItemVh.TAG, " mRootView show anim start");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            FwTodoChildItemVh.this.mIsRootViewAniming = false;
                            FwTodoChildItemVh.this.mRootView.setAlpha(1.0f);
                            FwTodoChildItemVh.this.mRootView.setVisibility(0);
                            Logger.INSTANCE.i(FwTodoChildItemVh.TAG, " mRootView show anim end");
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                            FwTodoChildItemVh.this.mIsRootViewAniming = false;
                        }
                    }).start();
                    return;
                } else {
                    this.mRootView.setAlpha(1.0f);
                    return;
                }
            }
            return;
        }
        if (this.mRootView.getVisibility() != 8 || this.mIsRootViewAniming) {
            if (z2) {
                this.mRootView.animate().alpha(0.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.10
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        FwTodoChildItemVh.this.mIsRootViewAniming = true;
                        Logger.INSTANCE.i(FwTodoChildItemVh.TAG, " mRootView dismiss anim start");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FwTodoChildItemVh.this.mIsRootViewAniming = false;
                        FwTodoChildItemVh.this.mRootView.setAlpha(0.5f);
                        FwTodoChildItemVh.this.mRootView.setVisibility(8);
                        Logger.INSTANCE.i(FwTodoChildItemVh.TAG, " mRootView dismiss anim end");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        FwTodoChildItemVh.this.mIsRootViewAniming = false;
                    }
                }).start();
            } else {
                this.mRootView.setVisibility(8);
            }
        }
    }

    private void showEditView(boolean z, boolean z2) {
        if (z) {
            if (this.mEditRootView.getVisibility() == 0) {
                return;
            }
            if (z2) {
                this.mEditRootView.setAlpha(0.0f);
                this.mEditRootView.setVisibility(0);
                this.mEditRootView.animate().alpha(1.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.11
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FwTodoChildItemVh.this.mEditRootView.setAlpha(1.0f);
                        FwTodoChildItemVh.this.mEditRootView.setVisibility(0);
                    }
                }).start();
                return;
            } else {
                this.mEditRootView.setAlpha(1.0f);
                this.mEditRootView.setVisibility(0);
                return;
            }
        }
        if (this.mEditRootView.getVisibility() == 8) {
            return;
        }
        if (z2) {
            this.mEditRootView.animate().alpha(0.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.12
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    FwTodoChildItemVh.this.mEditRootView.setVisibility(8);
                    FwTodoChildItemVh.this.mTextCreateGroup.setVisibility(0);
                }
            }).start();
        } else {
            this.mEditRootView.setVisibility(8);
            this.mTextCreateGroup.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFwToast(String str, long j) {
        if (this.mToastGroup.getVisibility() != 0) {
            Rect rect = new Rect();
            this.mTodoTextEdit.getFocusedRect(rect);
            this.mToastMes.setText(str);
            this.mToastGroup.setAlpha(0.0f);
            if (rect.top == 0) {
                this.mToastGroup.setY(60.0f);
            } else {
                this.mToastGroup.setY(rect.top);
            }
            this.mToastGroup.setVisibility(0);
            this.mToastGroup.requestLayout();
            this.mToastGroup.animate().alpha(1.0f).setDuration(200L).setListener(new AnonymousClass13(j)).start();
        }
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$13, reason: invalid class name */
    class AnonymousClass13 implements Animator.AnimatorListener {
        final /* synthetic */ long val$showDuration;

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        AnonymousClass13(long j) {
            this.val$showDuration = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            FwTodoChildItemVh.this.mToastGroup.animate().alpha(0.0f).setDuration(200L).setListener(new Animator.AnimatorListener() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh.13.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    FwTodoChildItemVh.this.mToastGroup.setVisibility(8);
                }
            });
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            FwTodoChildItemVh.this.mToastGroup.postDelayed(new Runnable() { // from class: com.miui.floatwindow.feature.todo.FwTodoChildItemVh$13$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAnimationEnd$0();
                }
            }, this.val$showDuration);
        }
    }

    protected void updateContentLayout(TodoEntity todoEntity) {
        if (todoEntity.getRemindType() == 0 && todoEntity.getInputType() != 1) {
            this.mLabelGroup.setVisibility(8);
        } else {
            this.mLabelGroup.setVisibility(0);
        }
    }

    @Override // com.miui.todo.base.todolist.BaseTodoChildItemVh
    protected void updateViewStateByMenuState() {
        super.updateViewStateByMenuState();
        if (this.mRootView != null) {
            if (this.mRootView.isMenuOpen()) {
                Drawable drawable = AppCompatResources.getDrawable(this.mRootView.getContext(), R.drawable.todo_fw_child_item_bg_nopress);
                if (drawable != null) {
                    this.mRootView.findViewById(R.id.item_content).setBackground(drawable.mutate());
                    return;
                }
                return;
            }
            Drawable drawable2 = AppCompatResources.getDrawable(this.mRootView.getContext(), R.drawable.todo_fw_child_item_bg);
            if (drawable2 != null) {
                this.mRootView.findViewById(R.id.item_content).setBackground(drawable2.mutate());
            }
        }
    }
}
