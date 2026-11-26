package com.miui.notes.excerpt;

import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class ParseTitleInfo {
    private String linkUrl;
    private UUID uuid;

    public ParseTitleInfo(UUID uuid, String str) {
        this.uuid = uuid;
        this.linkUrl = str;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public void setLinkUrl(String str) {
        this.linkUrl = str;
    }
}
