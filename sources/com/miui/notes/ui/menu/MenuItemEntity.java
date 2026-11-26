package com.miui.notes.ui.menu;

/* JADX INFO: loaded from: classes4.dex */
public class MenuItemEntity {
    private String mFolderName;
    private long mId;

    public MenuItemEntity(long j, String str) {
        this.mId = j;
        this.mFolderName = str;
    }

    public long getId() {
        return this.mId;
    }

    public void setId(long j) {
        this.mId = j;
    }

    public String getFolderName() {
        return this.mFolderName;
    }

    public void setFolderName(String str) {
        this.mFolderName = str;
    }
}
