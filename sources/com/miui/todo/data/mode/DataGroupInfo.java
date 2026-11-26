package com.miui.todo.data.mode;

/* JADX INFO: loaded from: classes4.dex */
public class DataGroupInfo {
    public int mGroupType = -1;
    public int mSubPosition = -1;

    public boolean isGroup() {
        return this.mSubPosition == -1;
    }
}
