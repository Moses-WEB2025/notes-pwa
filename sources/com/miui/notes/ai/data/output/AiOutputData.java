package com.miui.notes.ai.data.output;

/* JADX INFO: loaded from: classes4.dex */
public class AiOutputData {
    public int correctNums;
    public String reasoning;
    public String resultContent;
    public String resultContentMode;
    public String resultContentStream;
    public String theme;
    public String traceId;

    public AiOutputData(String str, String str2, String str3) {
        this.resultContent = str;
        this.resultContentStream = str2;
        this.traceId = str3;
    }

    public AiOutputData(String str, String str2, String str3, String str4) {
        this.resultContent = str;
        this.resultContentStream = str2;
        this.theme = str3;
        this.traceId = str4;
    }

    public AiOutputData(String str, String str2, String str3, String str4, String str5, String str6) {
        this.resultContent = str;
        this.resultContentStream = str2;
        this.theme = str3;
        this.resultContentMode = str4;
        this.traceId = str5;
        this.reasoning = str6;
    }

    public AiOutputData(String str, String str2, int i, String str3) {
        this.resultContent = str;
        this.resultContentStream = str2;
        this.correctNums = i;
        this.traceId = str3;
    }

    public String toString() {
        return "AiOutputData resultContent " + this.resultContent + " resultContentStream " + this.resultContentStream + " correctNums " + this.correctNums + " theme " + this.theme + " resultContentMode " + this.resultContentMode + " traceId " + this.traceId;
    }
}
