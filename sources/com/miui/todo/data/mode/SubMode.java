package com.miui.todo.data.mode;

import com.miui.todo.data.bean.SubTodoEntity;
import com.miui.todo.data.bean.SubTodoList;

/* JADX INFO: loaded from: classes4.dex */
public interface SubMode {
    boolean add(SubTodoEntity subTodoEntity);

    boolean add(SubTodoEntity subTodoEntity, int i);

    int changeFinishStatus(int i, boolean z);

    void changeFinishStatusAll(boolean z);

    SubMode copy();

    void delete(int i);

    void deleteBatch(int[] iArr);

    boolean dragItem(int i, int i2, int i3);

    String getCompleteSchedule();

    String getContent();

    SubTodoEntity getEntity(int i);

    int getFinishOperation();

    int getFinishSubTodoSize();

    String getPlainTextForAlert();

    String getPlainTextForSearch();

    SubTodoList getSubTodoList();

    int getSubTodoSize();

    String getTitle();

    int getUnFinishSubTodoSize();

    boolean isExpand();

    void setExpand(boolean z);

    void setTitle(String str);

    void update(SubTodoEntity subTodoEntity, int i);
}
