package com.miui.notes.excerpt;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlResultLine {
    private String lineContent;
    private int type;

    public HtmlResultLine(int i, String str) {
        this.type = i;
        this.lineContent = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getLineContent() {
        return this.lineContent;
    }

    public void setLineContent(String str) {
        this.lineContent = str;
    }

    public class LineType {
        public static final int TYPE_PIC = 1;
        public static final int TYPE_TEXT = 0;

        public LineType() {
        }
    }
}
