package com.miui.notes.excerpt;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlResult {
    private List<HtmlResultLine> list;
    private String title;

    public HtmlResult(String str, List<HtmlResultLine> list) {
        this.title = str;
        this.list = list;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public List<HtmlResultLine> getList() {
        return this.list;
    }

    public void setList(List<HtmlResultLine> list) {
        this.list = list;
    }
}
