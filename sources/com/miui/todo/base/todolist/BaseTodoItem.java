package com.miui.todo.base.todolist;

import java.util.ArrayList;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseTodoItem {
    public static int e;
    protected long id;
    public int position;
    public boolean isExpand = true;
    public String parentUniqueId = null;
    public final String uniqueId = UUID.randomUUID().toString();
    protected ArrayList<BaseTodoItem> subItems = null;

    public abstract int getType();

    public void setId(long j) {
        this.id = j;
    }

    public long getId() {
        return this.id;
    }

    public void addSubItem(BaseTodoItem baseTodoItem) {
        baseTodoItem.parentUniqueId = this.uniqueId;
        this.subItems.add(baseTodoItem);
    }

    public void addSubItemInHead(BaseTodoItem baseTodoItem) {
        baseTodoItem.parentUniqueId = this.uniqueId;
        this.subItems.add(0, baseTodoItem);
    }

    public void removeSubItem(BaseTodoItem baseTodoItem) {
        this.subItems.remove(baseTodoItem);
    }

    public void clearSubItems() {
        this.subItems.clear();
    }

    public ArrayList<BaseTodoItem> getSubItems() {
        return this.subItems;
    }
}
