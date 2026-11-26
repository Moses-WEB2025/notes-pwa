package com.miui.todo.cloudservice;

/* JADX INFO: loaded from: classes4.dex */
public class LocalSortEntity {
    private long customSortId;
    private long id;

    public LocalSortEntity(long j, long j2) {
        this.id = j;
        this.customSortId = j2;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getCustomSortId() {
        return this.customSortId;
    }

    public void setCustomSortId(long j) {
        this.customSortId = j;
    }
}
