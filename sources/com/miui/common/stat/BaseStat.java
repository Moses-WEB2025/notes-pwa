package com.miui.common.stat;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class BaseStat {
    protected static final String CLICK_ELEMENT_TYPE = "click_element_type";
    protected static final String CREATE_TYPE = "create_type";
    protected static final String DRAG_ELEMENT_TYPE = "element_type";
    private static final String EVENT_CLICK = "click";
    private static final String EVENT_CREATE = "create";
    private static final String EVENT_DELETE = "delete";
    private static final String EVENT_DRAG = "drag";
    private static final String EVENT_EDIT = "edit";
    private static final String EVENT_ENTER = "enter";
    private static final String EVENT_EXPOSE = "expose";
    private static final String EVENT_OPERATE = "operate";
    private static final String EVENT_PULL = "pull";
    private static final String EVENT_QUIT = "quit";
    private static final String EVENT_SEARCH = "search";
    private static final String EVENT_SEARCH_RESULT = "search_result";
    private static final String EVENT_SWITCH = "switch";
    protected static final String JUMP_RESULT = "jump_result";
    protected static final String SELECTED_QUANTITY = "selected_quantity";
    protected static final String STAY_DURATION = "stay_duration";
    protected static final String SWITCH_MODE = "switch_mode";
    protected static final String THEME_STYLE_TYPE = "theme_style_type";

    public static void reportEnterEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_ENTER, map);
    }

    public static void reportQuitEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_QUIT, map);
    }

    public static void reportOpenEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_ENTER, map);
    }

    public static void reportSwitchEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, "switch", map);
    }

    public static void reportPullEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_PULL, map);
    }

    public static void reportClickEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, "click", map);
    }

    public static void reportExposeEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_EXPOSE, map);
    }

    public static void reportCreateEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_CREATE, map);
    }

    public static void reportEditEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_EDIT, map);
    }

    public static void reportDragEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_DRAG, map);
    }

    public static void reportOperateEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_OPERATE, map);
    }

    public static void reportDeleteEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, "delete", map);
    }

    public static void reportSearchEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, "search", map);
    }

    public static void reportSearchResultEvent(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        StatHelper.track(str, EVENT_SEARCH_RESULT, map);
    }
}
