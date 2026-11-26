package com.miui.pad.feature.notes.handwrite;

import com.google.common.base.Ascii;
import com.miui.common.stat.BaseStat;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.stat.StatHelper;
import com.miui.handwriting.common.tools.constants.Constants;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class NoteStylusTrackListener extends BaseStat {
    public static final String TIP_DOODLE_COLOR_CLICK = "729.34.7.1.21854";
    public static final String TIP_DOODLE_MORE_TOOL_CLICK = "729.34.8.1.21855";
    public static final String TIP_DOODLE_TEXT_SIZE_CLICK = "729.34.6.1.21853";
    public static final String TIP_DOODLE_TOOL_CLICK = "729.34.6.1.21852";
    public static final String TIP_DOODLE_TOP_MORE_CLICK = "729.34.5.1.21851";
    HashMap<String, Object> params = new HashMap<>();

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void sendData(String str) {
        if (StatHelper.isEnable()) {
            str.hashCode();
            byte b = -1;
            switch (str.hashCode()) {
                case -2100929608:
                    if (str.equals("text_scale")) {
                        b = 0;
                    }
                    break;
                case -1911950265:
                    if (str.equals("select_scale")) {
                        b = 1;
                    }
                    break;
                case -1762374059:
                    if (str.equals("pic_scale")) {
                        b = 2;
                    }
                    break;
                case -1412146812:
                    if (str.equals("switch_finger_off")) {
                        b = 3;
                    }
                    break;
                case -1391258033:
                    if (str.equals("bg_dot")) {
                        b = 4;
                    }
                    break;
                case -1266092155:
                    if (str.equals("bg_black")) {
                        b = 5;
                    }
                    break;
                case -1246809169:
                    if (str.equals("bg_white")) {
                        b = 6;
                    }
                    break;
                case -1131577091:
                    if (str.equals("text_delete")) {
                        b = 7;
                    }
                    break;
                case -1003319665:
                    if (str.equals("text_add")) {
                        b = 8;
                    }
                    break;
                case -721299283:
                    if (str.equals("text_rotate")) {
                        b = 9;
                    }
                    break;
                case -693199062:
                    if (str.equals("pencil_tips")) {
                        b = 10;
                    }
                    break;
                case 3089282:
                    if (str.equals("done")) {
                        b = 11;
                    }
                    break;
                case 3357525:
                    if (str.equals("more")) {
                        b = 12;
                    }
                    break;
                case 3496446:
                    if (str.equals(NotesTextStat.REDO)) {
                        b = 13;
                    }
                    break;
                case 3594468:
                    if (str.equals(NotesTextStat.UNDO)) {
                        b = 14;
                    }
                    break;
                case 132084529:
                    if (str.equals("brush_eraser")) {
                        b = 15;
                    }
                    break;
                case 231541546:
                    if (str.equals("switch_finger_on")) {
                        b = 16;
                    }
                    break;
                case 387476876:
                    if (str.equals("brush_normal")) {
                        b = 17;
                    }
                    break;
                case 435371442:
                    if (str.equals(Constants.TOOL_BRUSH_PENCIL)) {
                        b = 18;
                    }
                    break;
                case 516660786:
                    if (str.equals("brush_rect_select")) {
                        b = 19;
                    }
                    break;
                case 773710336:
                    if (str.equals("pic_delete")) {
                        b = Ascii.DC4;
                    }
                    break;
                case 842093054:
                    if (str.equals("select_rotate")) {
                        b = Ascii.NAK;
                    }
                    break;
                case 925376270:
                    if (str.equals("pic_insert")) {
                        b = Ascii.SYN;
                    }
                    break;
                case 966500100:
                    if (str.equals("insert_pic")) {
                        b = Ascii.ETB;
                    }
                    break;
                case 1183988144:
                    if (str.equals("pic_rotate")) {
                        b = Ascii.CAN;
                    }
                    break;
                case 1540386737:
                    if (str.equals("brush_ruler")) {
                        b = Ascii.EM;
                    }
                    break;
                case 2133491495:
                    if (str.equals(Constants.TOOL_BRUSH_MARKPEN)) {
                        b = Ascii.SUB;
                    }
                    break;
            }
            switch (b) {
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 16:
                case 20:
                case 22:
                case 23:
                case 24:
                    reportToolClick("729.34.8.1.21855", str);
                    break;
                case 1:
                case 11:
                case 13:
                case 14:
                case 15:
                case 17:
                case 18:
                case 19:
                case 21:
                case 25:
                case 26:
                    reportToolClick("729.34.6.1.21852", str);
                    break;
                case 12:
                    reportClickEvent("729.34.5.1.21851", null);
                    break;
            }
        }
    }

    private void reportToolClick(String str, String str2) {
        this.params.clear();
        this.params.put("click_element_type", str2);
        reportClickEvent(str, this.params);
    }

    public void sendCollectData(String str, HashMap<String, Object> map) {
        if (StatHelper.isEnable()) {
            str.hashCode();
            switch (str) {
                case "eraser_size_change":
                case "pen_size_change":
                    reportClickEvent("729.34.6.1.21853", map);
                    break;
                case "color_change":
                    reportClickEvent("729.34.7.1.21854", map);
                    break;
            }
        }
    }
}
