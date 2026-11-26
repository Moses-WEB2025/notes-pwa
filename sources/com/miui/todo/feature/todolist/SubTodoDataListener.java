package com.miui.todo.feature.todolist;

import android.view.View;
import com.miui.todo.data.bean.SubTodoEntity;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface SubTodoDataListener {
    void onDataClick(View view, int i);

    void onDataDelete(int i);

    void onDataMerge(int i);

    void onDataSave(SubTodoEntity subTodoEntity, int i, boolean z, String str);

    void onFinishStatusChanged(boolean z, int i);

    void onMultiDataAdd(List<SubTodoEntity> list, int i, boolean z, String str);
}
