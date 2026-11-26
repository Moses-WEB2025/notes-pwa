package com.miui.todo.feature.todolist;

import android.view.View;
import com.miui.todo.data.bean.TodoEntity;

/* JADX INFO: loaded from: classes4.dex */
public interface ITodoItemFakeVh {
    void bind(TodoEntity todoEntity);

    View getItemView();

    void startCheckAnim();
}
