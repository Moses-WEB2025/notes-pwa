package com.aios.apptoolsdk;

/* JADX INFO: loaded from: classes.dex */
public class AppResult {
    private final boolean error;
    private final String text;

    private AppResult(String str, boolean z) {
        this.text = str;
        this.error = z;
    }

    public String getText() {
        return this.text;
    }

    public boolean isError() {
        return this.error;
    }

    public static AppResult success(String str) {
        return new AppResult(str, false);
    }

    public static AppResult error(String str) {
        return new AppResult(str, true);
    }
}
