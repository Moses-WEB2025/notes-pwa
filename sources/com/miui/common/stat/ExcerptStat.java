package com.miui.common.stat;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class ExcerptStat extends BaseStat {
    public static final String FAIL = "fail";
    public static final String IMAGE_SAVE = "image_save_time";
    public static final String SUCCESS = "success";
    public static final String TIP_EXCERPT_PAGE_ENTER = "729.4.0.1.21808";
    public static final String TIP_START_EXCERPT_CLICK = "729.4.1.1.21810";
    public static final String TIP_STOP_EXCERPTING_CLICK = "729.4.1.1.21812";
    public static final String TIP_STOP_EXCERPT_CLICK = "729.4.1.1.21811";

    public static void trackExcerptingClick(String str, String str2) {
        HashMap map = new HashMap();
        map.put("jump_result", str);
        map.put("click_element_type", str2);
        reportClickEvent(TIP_STOP_EXCERPTING_CLICK, map);
    }

    public static void trackStopExcerpt(int i) {
        HashMap map = new HashMap();
        map.put(IMAGE_SAVE, Integer.valueOf(i));
        reportClickEvent(TIP_STOP_EXCERPT_CLICK, map);
    }
}
