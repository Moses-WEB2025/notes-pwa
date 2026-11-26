package com.miui.todo.data.bean;

/* JADX INFO: loaded from: classes4.dex */
public class SubTodoEntity {
    private String content;
    private transient int id;
    private boolean isFinish;

    public SubTodoEntity(int i, String str, boolean z) {
        this.content = str;
        this.isFinish = z;
        this.id = i;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public boolean getIsFinish() {
        return this.isFinish;
    }

    public void setIsFinish(boolean z) {
        this.isFinish = z;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }

    public static SubTodoEntity getDefault() {
        return new SubTodoEntity(0, "", false);
    }
}
