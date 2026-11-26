package com.miui.handwriting.common.ai.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface CreationType {
    public static final String CREATION_FIGURE = "3";
    public static final String CREATION_FULL = "1";
    public static final String CREATION_PART_ELIMINATE = "5";
    public static final String CREATION_PART_REPAINT = "4";
    public static final String CREATION_POSTURE = "2";
    public static final String CREATION_TEXT = "6";
    public static final int CREATION_TEXT_DIFF = 5;
    public static final String CREATION_TEXT_FIGURE = "8";
    public static final String CREATION_TEXT_POSTURE = "7";
}
