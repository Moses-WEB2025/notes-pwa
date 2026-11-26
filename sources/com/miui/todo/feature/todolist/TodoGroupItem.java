package com.miui.todo.feature.todolist;

import com.miui.todo.base.todolist.BaseTodoItem;
import com.miui.todo.utils.TodoUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class TodoGroupItem extends BaseTodoItem {
    private int mGroupType;
    private String mTitle;

    @Override // com.miui.todo.base.todolist.BaseTodoItem
    public int getType() {
        return 1;
    }

    public TodoGroupItem(int i) {
        this.mGroupType = i;
        this.mTitle = TodoUtils.getGroupName(i);
        this.subItems = new ArrayList<>();
        this.isExpand = true;
    }

    public TodoGroupItem(int i, long j) {
        this.mGroupType = i;
        this.mTitle = TodoUtils.getGroupName(i);
        this.subItems = new ArrayList<>();
        this.isExpand = true;
        this.id = j;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public int getGroupType() {
        return this.mGroupType;
    }

    public void toggleExpand() {
        this.isExpand = !this.isExpand;
    }
}
