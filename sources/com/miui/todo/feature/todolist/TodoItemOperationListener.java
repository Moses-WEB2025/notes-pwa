package com.miui.todo.feature.todolist;

import android.view.View;
import com.miui.todo.data.bean.TodoEntity;
import io.reactivex.Observable;

/* JADX INFO: loaded from: classes4.dex */
public interface TodoItemOperationListener {
    void onClick(View view);

    void onDelete(View view);

    void onFinished(View view);

    void onLongClick(View view);

    Observable<Boolean> onMoved(TodoEntity todoEntity, int i, int i2);

    void onSelected(View view);

    boolean onSimpleMoved(int i, int i2);

    void onToggleGroup(View view);

    void onUnFinished(View view);
}
