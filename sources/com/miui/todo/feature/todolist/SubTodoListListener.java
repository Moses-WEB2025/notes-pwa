package com.miui.todo.feature.todolist;

import android.view.View;
import com.miui.todo.data.bean.TodoEntity;

/* JADX INFO: loaded from: classes4.dex */
public interface SubTodoListListener {
    void onDataChanged(TodoEntity todoEntity, int i, View view);

    void onDataFinishStateChanged(TodoEntity todoEntity, int i, View view, boolean z);

    void scroll(int i, int i2, int i3);
}
