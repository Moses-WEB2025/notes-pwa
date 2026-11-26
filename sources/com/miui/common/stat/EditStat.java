package com.miui.common.stat;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class EditStat extends BaseStat {
    public static final String ELEMENT_BOLD = "bold";
    public static final String ELEMENT_BULLET = "bullet";
    public static final String ELEMENT_CENTER = "center";
    public static final String ELEMENT_CHECKBOX = "checkbox";
    public static final String ELEMENT_CHOOSE_GALLERY = "gallery";
    public static final String ELEMENT_CHOOSE_LOCAL_CAMERA = "local_camera";
    public static final String ELEMENT_CHOOSE_REMOTE_CAMERA = "remote_camera";
    public static final String ELEMENT_DOODLE = "doodle";
    public static final String ELEMENT_FONT_INCREASE = "increase_font";
    public static final String ELEMENT_FONT_REDUCE = "reduce_font";
    public static final String ELEMENT_HIGHLIGHT = "highlight";
    public static final String ELEMENT_ITALIC = "italic";
    public static final String ELEMENT_LEFT = "left";
    public static final String ELEMENT_RIGHT = "right";
    public static final String ELEMENT_SWITCH = "switch";
    public static final String ELEMENT_UNDERLINE = "underline ";
    public static final String TIP_AUDIO_CREATE = "729.5.2.1.20068";
    public static final String TIP_GALLERY_CHOOSE_CLICK = "729.5.3.1.20070";
    public static final String TIP_RICH_EDIT_CONTROLLER_CLICK = "729.5.6.1.20074";
    public static final String VOICE_DURATION = "voice_duration";

    public static void trackRichEditorControllerClick(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(TIP_RICH_EDIT_CONTROLLER_CLICK, map);
    }

    public static void trackGalleryChooserClick(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(TIP_GALLERY_CHOOSE_CLICK, map);
    }

    public static void trackVoiceCreate(int i) {
        HashMap map = new HashMap();
        map.put(VOICE_DURATION, Integer.valueOf(i));
        reportCreateEvent(TIP_AUDIO_CREATE, map);
    }
}
