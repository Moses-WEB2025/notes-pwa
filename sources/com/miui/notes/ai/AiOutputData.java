package com.miui.notes.ai;

/* JADX INFO: loaded from: classes4.dex */
public class AiOutputData {
    public int correctNums;
    public String resultContent;
    public String resultContentMode;
    public String resultContentStream;
    public String theme;

    public AiOutputData(String str, String str2) {
        this.resultContent = str;
        this.resultContentStream = str2;
    }

    public AiOutputData(String str, String str2, String str3, String str4) {
        this.resultContent = str;
        this.resultContentStream = str2;
        this.theme = str3;
        this.resultContentMode = str4;
    }

    public AiOutputData(String str, String str2, int i) {
        this.resultContent = str;
        this.resultContentStream = str2;
        this.correctNums = i;
    }

    public String toString() {
        return "AiOutputData resultContent " + this.resultContent + " resultContentStream " + this.resultContentStream + " correctNums " + this.correctNums + " theme " + this.theme + " resultContentMode " + this.resultContentMode;
    }
}
