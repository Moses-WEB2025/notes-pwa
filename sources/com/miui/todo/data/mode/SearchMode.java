package com.miui.todo.data.mode;

import android.content.Context;
import com.miui.todo.data.bean.TodoEntity;
import io.reactivex.Observable;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface SearchMode {
    void deleteItem(TodoEntity todoEntity);

    void finishItem(TodoEntity todoEntity);

    Observable<List<TodoEntity>> queryItem(Context context, String str);

    Observable<List<TodoEntity>> queryItem(Context context, String str, int i);

    void unFinishItem(TodoEntity todoEntity);

    void updateItem(TodoEntity todoEntity);
}
