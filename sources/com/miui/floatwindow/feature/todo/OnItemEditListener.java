package com.miui.floatwindow.feature.todo;

import com.miui.todo.data.bean.TodoEntity;

/* JADX INFO: loaded from: classes2.dex */
public interface OnItemEditListener {
    void onAudioFileCreated(String str);

    void onAudioTextChanged(String str);

    void onEnterEdit(TodoEntity todoEntity, int i, FwTodoChildItemVh fwTodoChildItemVh, boolean z);

    void onExitEdit(boolean z);

    void onSetRemindTime(boolean z);

    void onSubTodoChanged(String str);

    void onTextChanged(FwTodoChildItemVh fwTodoChildItemVh, CharSequence charSequence);

    void onTodoTypeChanged(int i);
}
