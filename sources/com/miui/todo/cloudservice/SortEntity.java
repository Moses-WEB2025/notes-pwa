package com.miui.todo.cloudservice;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SortEntity {
    private long eTag;
    private List<Long> orders;

    SortEntity(long j, List<Long> list) {
        this.eTag = j;
        this.orders = list;
    }

    public long geteTag() {
        return this.eTag;
    }

    public void seteTag(long j) {
        this.eTag = j;
    }

    public List<Long> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Long> list) {
        this.orders = list;
    }
}
