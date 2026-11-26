package com.miui.todo.data.bean;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoList {
    private boolean isExpand;
    private List<SubTodoEntity> subTodoEntities;
    private String title;

    public SubTodoList(String str, List<SubTodoEntity> list, boolean z) {
        this.title = str;
        this.subTodoEntities = list;
        this.isExpand = z;
    }

    public String getTitle() {
        return this.title;
    }

    public List<SubTodoEntity> getList() {
        return this.subTodoEntities;
    }

    public boolean getIsExpand() {
        return this.isExpand;
    }
}
