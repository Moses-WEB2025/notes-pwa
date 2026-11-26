package com.miui.todo.feature.todolist;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.common.tool.CompatUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.utils.CharUtils;
import com.miui.todo.utils.TodoUtils;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoEditItemVh extends RecyclerView.ViewHolder {
    private SubTodoEditListBindContext mBindContext;
    private CheckBox mCbSubTodo;
    private final CompoundButton.OnCheckedChangeListener mCheckChangeListener;
    private SubTodoDataListener mDataListener;
    private final View.OnFocusChangeListener mFocusChangeListener;
    protected SubTodoRichEditor mSubTodoEditor;
    private final TextWatcher mTextWatcher;
    private SubTodoEntity mWorkingSubTodo;

    public static SubTodoEditItemVh newInstance(ViewGroup viewGroup) {
        return new SubTodoEditItemVh(UIUtils.inflateView(viewGroup, R.layout.subtodo_edit_item));
    }

    public SubTodoEditItemVh(View view) {
        super(view);
        this.mCheckChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SubTodoEditItemVh.this.changeDataFinishStatus(z);
                if (SubTodoEditItemVh.this.mBindContext != null) {
                    SubTodoEditItemVh.this.mBindContext.getAdapter().notifyDataSetChanged();
                }
            }
        };
        this.mFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh.2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view2, boolean z) {
                if (z) {
                    SubTodoEditItemVh.this.mSubTodoEditor.setCursorVisible(true);
                    if (SubTodoEditItemVh.this.mDataListener != null) {
                        SubTodoEditItemVh.this.mDataListener.onDataClick(view2, SubTodoEditItemVh.this.getAdapterPosition());
                        return;
                    }
                    return;
                }
                SubTodoEditItemVh.this.mSubTodoEditor.destroyActionMode();
            }
        };
        this.mTextWatcher = new TextWatcher() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh.3
            private boolean isInputEnterInMiddle = false;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int i4 = i3 + i;
                if (!StringUtils.LF.contentEquals(charSequence.subSequence(i, i4)) || i4 >= charSequence.length()) {
                    return;
                }
                this.isInputEnterInMiddle = true;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                LinearLayoutManager linearLayoutManager;
                if (editable.length() == 0) {
                    SubTodoEditItemVh.this.deleteData();
                } else {
                    boolean z = false;
                    for (int i = 0; i < editable.length(); i++) {
                        if (editable.charAt(i) == '\n') {
                            z = true;
                        }
                    }
                    if (z) {
                        if (SubTodoEditItemVh.this.mBindContext != null && SubTodoEditItemVh.this.mBindContext.getAdapter().isMaxLimit()) {
                            Toast.makeText(NoteApp.getInstance(), NoteApp.getInstance().getString(R.string.todo_list_overflow_tip), 0).show();
                            String string = editable.toString();
                            if (string.contains(StringUtils.LF)) {
                                SubTodoEditItemVh.this.mSubTodoEditor.setText(string.replace(StringUtils.LF, ""));
                                return;
                            }
                            return;
                        }
                        if (editable.length() == 2 && (editable.charAt(1) == '\n' || editable.charAt(0) == '\n')) {
                            SubTodoEditItemVh.this.mSubTodoEditor.setText(CharUtils.ZERO_WIDTH_SPACE);
                            return;
                        }
                        if (SubTodoEditItemVh.this.mWorkingSubTodo.getIsFinish()) {
                            SubTodoEditItemVh.this.mSubTodoEditor.removeTextChangedListener(this);
                            SubTodoEditItemVh.this.mSubTodoEditor.setText(CharUtils.removeCharFromString(editable.toString(), '\n'));
                            SubTodoEditItemVh.this.mSubTodoEditor.addTextChangedListener(this);
                            return;
                        }
                        SubTodoEditItemVh.this.mSubTodoEditor.removeTextChangedListener(this);
                        String[] stringArrayByChar = CharUtils.getStringArrayByChar(editable.toString(), '\n');
                        if (stringArrayByChar.length == 1) {
                            SubTodoEditItemVh.this.mSubTodoEditor.setText(stringArrayByChar[0]);
                            if (SubTodoEditItemVh.this.mBindContext != null) {
                                SubTodoEditItemVh.this.mBindContext.setEditIndex(SubTodoEditItemVh.this.getLayoutPosition());
                                SubTodoEditItemVh.this.mBindContext.setDoEditIndex(SubTodoEditItemVh.this.getLayoutPosition());
                                SubTodoEditItemVh.this.mBindContext.getAdapter().editNext();
                            }
                            SubTodoEditItemVh.this.saveData(true);
                        } else if (stringArrayByChar.length > 1) {
                            SubTodoEditItemVh.this.mSubTodoEditor.setText(stringArrayByChar[0]);
                            if (SubTodoEditItemVh.this.mBindContext != null) {
                                if (this.isInputEnterInMiddle) {
                                    SubTodoEditItemVh.this.mBindContext.setSplitDataIndex(SubTodoEditItemVh.this.getLayoutPosition() + 1);
                                    if (SubTodoEditItemVh.this.itemView.getParent() != null && (linearLayoutManager = (LinearLayoutManager) ((RecyclerView) SubTodoEditItemVh.this.itemView.getParent()).getLayoutManager()) != null && SubTodoEditItemVh.this.getLayoutPosition() == linearLayoutManager.findLastVisibleItemPosition()) {
                                        linearLayoutManager.scrollToPosition(SubTodoEditItemVh.this.getLayoutPosition() + 1);
                                    }
                                } else {
                                    SubTodoEditItemVh.this.mBindContext.setEditIndex((SubTodoEditItemVh.this.getLayoutPosition() + stringArrayByChar.length) - 1);
                                    SubTodoEditItemVh.this.mBindContext.setDoEditIndex((SubTodoEditItemVh.this.getLayoutPosition() + stringArrayByChar.length) - 1);
                                }
                            }
                            SubTodoEditItemVh.this.addMultiData(stringArrayByChar, true);
                        }
                        SubTodoEditItemVh.this.mSubTodoEditor.addTextChangedListener(this);
                    } else {
                        SubTodoEditItemVh.this.saveData(false);
                    }
                }
                this.isInputEnterInMiddle = false;
            }
        };
        this.mDataListener = null;
        initView(view);
    }

    private void initView(View view) {
        SubTodoRichEditor subTodoRichEditor = (SubTodoRichEditor) view.findViewById(R.id.editor);
        this.mSubTodoEditor = subTodoRichEditor;
        DisplayUtils.setMiuiRegularTypeFace(subTodoRichEditor);
        this.mSubTodoEditor.setCursorVisible(false);
        this.mSubTodoEditor.setHighlightColor(this.itemView.getContext().getResources().getColor(R.color.theme_default_high_light_color));
        CompatUtils.invokeCursorColor(this.mSubTodoEditor, this.itemView.getContext().getResources().getColor(R.color.theme_default_cursor_color), this.itemView.getContext().getResources().getColor(R.color.theme_default_cursor_handle_color));
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_subtodo);
        this.mCbSubTodo = checkBox;
        checkBox.setChecked(false);
        this.mCbSubTodo.setOnCheckedChangeListener(this.mCheckChangeListener);
        AccessibilityDelegateUtil.setDoneAccessibilityDelegate(this.mCbSubTodo);
        this.mSubTodoEditor.setOnFocusChangeListener(this.mFocusChangeListener);
        this.mSubTodoEditor.addTextChangedListener(this.mTextWatcher);
        this.mSubTodoEditor.setOnKeyListener(new View.OnKeyListener() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                return this.f$0.lambda$initView$0(view2, i, keyEvent);
            }
        });
        this.mSubTodoEditor.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh.4
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                SubTodoEditItemVh.this.mSubTodoEditor.setCursorVisible(false);
                SubTodoEditItemVh.this.mSubTodoEditor.setCursorVisible(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0(View view, int i, KeyEvent keyEvent) {
        if (i != 67 || keyEvent.getAction() != 0 || keyEvent.getRepeatCount() != 0 || this.mSubTodoEditor.getSelectionStart() != this.mSubTodoEditor.getSelectionEnd() || this.mSubTodoEditor.getSelectionStart() != 0) {
            return false;
        }
        mergeData();
        return true;
    }

    public void bind(SubTodoEntity subTodoEntity, SubTodoEditListBindContext subTodoEditListBindContext, int i, boolean z) {
        this.mBindContext = subTodoEditListBindContext;
        this.mSubTodoEditor.removeTextChangedListener(this.mTextWatcher);
        this.mCbSubTodo.setOnCheckedChangeListener(null);
        this.mCbSubTodo.setChecked(subTodoEntity.getIsFinish());
        this.mCbSubTodo.setActivated(false);
        this.mCbSubTodo.setOnCheckedChangeListener(this.mCheckChangeListener);
        String content = subTodoEntity.getContent();
        final int length = this.mSubTodoEditor.getText().length();
        if (content == null || content.length() == 0) {
            this.mSubTodoEditor.setText(CharUtils.ZERO_WIDTH_SPACE);
        } else {
            this.mSubTodoEditor.setText(CharUtils.ZERO_WIDTH_SPACE + CharUtils.getStringBuilder(CharUtils.getStringBuilder(content, CharUtils.ZERO_WIDTH_SPACE_CHAR), '\n'));
        }
        setContentCheckState(this.mCbSubTodo.isChecked());
        if (i == this.mBindContext.getSplitDataEditIndex()) {
            this.mBindContext.setSplitDataIndex(-1);
            this.mSubTodoEditor.setCursorVisible(true);
            this.mSubTodoEditor.setOnFocusChangeListener(null);
            if (z) {
                this.mSubTodoEditor.requestFocus();
            }
            this.mSubTodoEditor.post(new Runnable() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh.5
                @Override // java.lang.Runnable
                public void run() {
                    SubTodoEditItemVh.this.mSubTodoEditor.requestFocus();
                    SubTodoEditItemVh.this.mSubTodoEditor.setSelection(0);
                    SubTodoEditItemVh.this.mSubTodoEditor.setOnFocusChangeListener(SubTodoEditItemVh.this.mFocusChangeListener);
                }
            });
        } else if (i == this.mBindContext.getMergeIndex()) {
            this.mBindContext.setMergeIndex(-1);
            this.mSubTodoEditor.setCursorVisible(true);
            this.mSubTodoEditor.setOnFocusChangeListener(null);
            if (z) {
                this.mSubTodoEditor.requestFocus();
            }
            this.mSubTodoEditor.post(new Runnable() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bind$1(length);
                }
            });
        } else if (i == this.mBindContext.getEditIndex()) {
            this.mBindContext.setEditIndex(-1);
            this.mSubTodoEditor.setCursorVisible(true);
            this.mSubTodoEditor.setOnFocusChangeListener(null);
            if (z) {
                this.mSubTodoEditor.requestFocus();
            }
            this.mSubTodoEditor.post(new Runnable() { // from class: com.miui.todo.feature.todolist.SubTodoEditItemVh$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bind$2();
                }
            });
        }
        this.mWorkingSubTodo = createNewData(subTodoEntity.getId());
        this.mSubTodoEditor.addTextChangedListener(this.mTextWatcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$1(int i) {
        this.mSubTodoEditor.requestFocus();
        if (i < this.mSubTodoEditor.length()) {
            this.mSubTodoEditor.setSelection(i);
        }
        this.mSubTodoEditor.setOnFocusChangeListener(this.mFocusChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bind$2() {
        this.mSubTodoEditor.requestFocus();
        SubTodoRichEditor subTodoRichEditor = this.mSubTodoEditor;
        subTodoRichEditor.setSelection(subTodoRichEditor.getText().toString().length());
        this.mSubTodoEditor.setOnFocusChangeListener(this.mFocusChangeListener);
    }

    public void setDataListener(SubTodoDataListener subTodoDataListener) {
        this.mDataListener = subTodoDataListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveData(boolean z) {
        int adapterPosition;
        SubTodoEntity subTodoEntity = this.mWorkingSubTodo;
        if (subTodoEntity != null) {
            String content = subTodoEntity.getContent();
            this.mWorkingSubTodo.setContent(this.mSubTodoEditor.getText().toString());
            this.mWorkingSubTodo.setIsFinish(this.mCbSubTodo.isChecked());
            if (this.mDataListener == null || (adapterPosition = getAdapterPosition()) < 0) {
                return;
            }
            this.mDataListener.onDataSave(this.mWorkingSubTodo, adapterPosition, z, content);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteData() {
        int adapterPosition;
        if (this.mDataListener == null || (adapterPosition = getAdapterPosition()) < 0) {
            return;
        }
        this.mDataListener.onDataDelete(adapterPosition);
    }

    private void mergeData() {
        int adapterPosition;
        if (this.mDataListener == null || (adapterPosition = getAdapterPosition()) < 0) {
            return;
        }
        this.mBindContext.setMergeIndex(adapterPosition - 1);
        this.mDataListener.onDataMerge(adapterPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addMultiData(String[] strArr, boolean z) {
        String content;
        int adapterPosition;
        ArrayList arrayList = new ArrayList();
        SubTodoEntity subTodoEntity = this.mWorkingSubTodo;
        if (subTodoEntity != null) {
            content = subTodoEntity.getContent();
            this.mWorkingSubTodo.setContent(this.mSubTodoEditor.getText().toString());
            this.mWorkingSubTodo.setIsFinish(this.mCbSubTodo.isChecked());
            arrayList.add(this.mWorkingSubTodo);
        } else {
            content = null;
        }
        for (int i = 1; i < strArr.length; i++) {
            arrayList.add(new SubTodoEntity(i, strArr[i], false));
        }
        if (this.mDataListener == null || (adapterPosition = getAdapterPosition()) < 0) {
            return;
        }
        this.mDataListener.onMultiDataAdd(arrayList, adapterPosition, z, content);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDataFinishStatus(boolean z) {
        SubTodoEntity subTodoEntity = this.mWorkingSubTodo;
        if (subTodoEntity != null) {
            subTodoEntity.setIsFinish(this.mCbSubTodo.isChecked());
            setContentCheckState(z);
            SubTodoDataListener subTodoDataListener = this.mDataListener;
            if (subTodoDataListener != null) {
                subTodoDataListener.onFinishStatusChanged(z, getAdapterPosition());
            }
        }
    }

    protected void setContentCheckState(boolean z) {
        if (z) {
            this.mSubTodoEditor.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_disable_color));
        } else {
            this.mSubTodoEditor.setTextColor(TodoUtils.getColor(R.color.todo_item_content_text_common_color));
        }
    }

    private SubTodoEntity createNewData(int i) {
        return new SubTodoEntity(i, this.mSubTodoEditor.getText().toString(), this.mCbSubTodo.isChecked());
    }
}
