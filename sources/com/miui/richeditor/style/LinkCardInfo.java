package com.miui.richeditor.style;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardInfo {
    public String content;
    public String link;
    public String title;
    public String webIconId;

    public LinkCardInfo(String str, String str2, String str3, String str4) {
        this(str, str2);
        this.content = str3;
        this.webIconId = str4;
    }

    public LinkCardInfo(String str, String str2) {
        this.link = str;
        this.title = str2;
    }
}
