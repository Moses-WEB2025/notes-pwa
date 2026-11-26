package com.miui.todo.base;

import android.view.View;
import com.miui.todo.data.bean.RemindTimeConfig;
import com.miui.todo.data.bean.TodoBaseEntity;

/* JADX INFO: loaded from: classes4.dex */
public interface IEditTodoView {
    void cancelRecord();

    void cancelSetRemindTime();

    void completeRecord();

    void enterEditMode(TodoBaseEntity todoBaseEntity, boolean z, boolean z2);

    void exitEditMode(boolean z, boolean z2);

    View getEditTextView();

    void prepareRecord();

    void updateRemindCustomInfo(boolean z, RemindTimeConfig remindTimeConfig);

    void willCancelRecord();
}
