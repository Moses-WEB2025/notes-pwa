package com.android.soundrecorder.ai.base.constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class RecordSource {
    public static final int AUDIO_MONITOR = 10;
    public static final int INVALID = -1;
    public static final int IN_CALL = 1;
    public static final int MESSAGE = 3;
    public static final int NOTE = 2;
    public static final int REOCRDER = 0;
    public static final int THIRD_PARTY = 50;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    public static String getSourceName(int i) {
        if (i == 0) {
            return "recorder";
        }
        if (i == 1) {
            return "in call";
        }
        if (i == 2) {
            return "note";
        }
        if (i == 3) {
            return "message";
        }
        if (i == 10) {
            return "audio monitor";
        }
        if (i == 50) {
            return "third party";
        }
        return "invalid";
    }
}
