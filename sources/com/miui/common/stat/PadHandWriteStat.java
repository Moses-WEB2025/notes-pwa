package com.miui.common.stat;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class PadHandWriteStat extends BaseStat {
    public static final String HAND_WEITE_BROWSER_DELET = "729.34.4.1.21850";
    public static final String HAND_WEITE_BROWSER_MORE = "729.34.3.1.21848";
    public static final String HAND_WEITE_BROWSER_MORE_CLICK = "729.34.3.1.21849";
    public static final String HAND_WEITE_CREATE = "729.34.1.1.21843";
    public static final String HAND_WEITE_EDIT = "729.34.0.1.21845";
    public static final String HAND_WEITE_EDIT_MORE = "729.34.8.1.21855";
    public static final String HAND_WEITE_EFFECT_CREATE = "729.34.0.1.21844";
    public static final String HAND_WEITE_SHARE_CLICK = "729.34.2.1.21846";
    public static final String HAND_WEITE_SHARE_PICTURE = "729.34.2.1.21847";
    public static final String HAND_WEITE_TOP_ELEMENT_CLICK = "729.34.6.1.21852";
    public static final String HAND_WEITE_TOP_ELEMENT_COLOR = "729.34.7.1.21854";
    public static final String HAND_WEITE_TOP_ELEMENT_FONT = "729.34.6.1.21853";
    public static final String HAND_WEITE_TOP_ELEMENT_MORE = "729.34.5.1.21851";
    public static String TIP_NOTE = "tip_note";
    public static boolean isCreateHandWriteNote = false;

    public static void reportClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("create_type", str);
        reportClickEvent(HAND_WEITE_CREATE, map);
    }

    public static void reportShareClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(HAND_WEITE_SHARE_PICTURE, map);
    }

    public static void reportMoreClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(HAND_WEITE_BROWSER_MORE_CLICK, map);
    }

    public static void reportDeleteClickEvent(boolean z) {
        HashMap map = new HashMap();
        map.put("click_element_type", z ? ToDoStat.DELETE : ToDoStat.CANCEL);
        reportClickEvent(HAND_WEITE_BROWSER_DELET, map);
    }
}
