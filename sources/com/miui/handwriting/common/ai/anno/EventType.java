package com.miui.handwriting.common.ai.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface EventType {
    public static final String EVENT_CHANGE_STATE = "EVENT_CHANGE_STATE";
    public static final String EVENT_DOING_CHANGE = "EVENT_DOING_CHANGE";
    public static final String EVENT_DONE_CHANGE = "EVENT_DONE_CHANGE";
    public static final String EVENT_PROMOTE_CHANGE = "EVENT_PROMOTE_CHANGE";
    public static final String EVENT_SHARE_CLOSE_AI_PAGE = "EVENT_CLOSE_AI_PAGE";
    public static final String EVENT_SHARE_TRANSITION_FINISH = "EVENT_SHARE_TRANSITION_CHANGE";
    public static final String EVENT_TAG_ANIME_STYLE = "EVENT_TAG_ANIME_STYLE";
    public static final String EVENT_THEME_CHANGE = "EVENT_THEME_CHANGE";
    public static final String EVENT_TYPE_HIDE_ADD_DESCRIPTION = "EVENT_TYPE_HIDE_ADD_DESCRIPTION";
    public static final String EVENT_TYPE_IMAGE_GENERATE = "EVENT_TYPE_IMAGE_GENERATE";
    public static final String EVENT_TYPE_STATUS_RECOVER = "EVENT_TYPE_STATUS_RECOVER";
}
