package com.ibm.icu.text;

/* JADX INFO: loaded from: classes2.dex */
abstract class CharsetRecognizer {
    public String getLanguage() {
        return null;
    }

    abstract String getName();

    abstract CharsetMatch match(CharsetDetector charsetDetector);

    CharsetRecognizer() {
    }
}
