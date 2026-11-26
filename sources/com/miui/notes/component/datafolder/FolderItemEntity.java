package com.miui.notes.component.datafolder;

/* JADX INFO: loaded from: classes4.dex */
public class FolderItemEntity {
    private long folderId;
    private String folderName;
    private boolean isStick;

    public FolderItemEntity(long j, String str, boolean z) {
        this.folderName = str;
        this.folderId = j;
        this.isStick = z;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String str) {
        this.folderName = str;
    }

    public boolean isStick() {
        return this.isStick;
    }

    public void setStick(boolean z) {
        this.isStick = z;
    }

    public long getFolderId() {
        return this.folderId;
    }

    public void setFolderId(long j) {
        this.folderId = j;
    }
}
