package com.miui.common.stat;

import com.miui.notes.base.utils.Logger;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class NotesTextStat extends BaseStat {
    public static final String ADD_IMAGE = "添加图片";
    public static final String ADD_IMAGE_PHOTO = "添加图片_相册";
    public static final String ADD_IMAGE_PICTURE = "添加图片_照片";
    public static final String ADD_TEXT = "添加文字";
    public static final String ADD_TEXT_CANCEL = "添加文字_取消";
    public static final String ADD_TEXT_SURE = "添加文字_确定";
    public static final String ANITQUE = "anitque";
    private static final String APK_NAME = "apk";
    public static final String ARTY = "arty";
    public static final String AUTO_FILL = "自动填充";
    public static final String BLACK = "black";
    public static final String BLUE = "blue";
    public static final String BRUSH_PEN_TOOL = "毛笔工具";
    public static final String CANDY = "candy";
    public static final String CONTENT_CREATIME = "content_creatime";
    public static final String CONTENT_LOCATION = "content_location";
    public static final String CONTENT_STATE = "content_status";
    public static final String CONTENT_STYLE = "content_style";
    public static final String CONTENT_TEXT_COUNT = "content_text_count";
    public static final String CONTENT_TYPE = "content_type";
    public static final String COPY = "复制";
    public static final String CUT = "剪切";
    public static final String DEFAULT = "default";
    public static final String DONE_BACK_SAVE = "完成_返回保存";
    public static final String DONE_CLICK_RIGHT = "完成_点击右上角";
    public static final String DURATION = "duration";
    private static final String ELEMENT_NAME = "element_name";
    public static final String ENTER_WAY = "enter_way";
    public static final String ERASER_TOOL = "橡皮工具";
    public static final String ERASER_TOOL_PX = "橡皮工具_像素";
    public static final String ERASER_TOOL_STROKE = "橡皮工具_笔划";
    public static final String FOLDER = "folder";
    public static final String FULL_SCREEN = "全屏";
    public static final String GEOMETRY = "geometry";
    public static final String GREEN = "green";
    public static final String IS_AI_USED = "is_ai_used";
    public static final String IS_PRIVACY = "is_privacy";
    public static final String IS_REMIND = "is_remind";
    public static final String IS_TOPUP = "is_topup";
    public static final String IS_TYPE_CHECK = "is_type_check";
    public static final String IS_TYPE_COMMON = "is_type_common";
    public static final String IS_TYPE_EASY = "is_type_easy";
    public static final String IS_TYPE_EXTENSION = "is_type_extension";
    public static final String IS_TYPE_FRIENDLY = "is_type_friendly";
    public static final String IS_TYPE_INPUT = "is_type_input";
    public static final String IS_TYPE_PROFESSIONAL = "is_type_professional";
    public static final String IS_TYPE_RECOMMEND = "is_type_recommend";
    public static final String IS_TYPE_SUMMARY = "is_type_summary";
    public static final String LEAF = "leaf";
    public static final String MARK_TOOL = "马克笔工具";
    public static final String NOTE_CLIP_BOARD = "729.5.1.1.39603";
    public static final String NOTE_EDIT_DRAG = "729.5.1.1.20361";
    public static final String NOTE_EDIT_MODE_DOODLE_CLICK = "729.5.4.1.20071";
    public static final String NOTE_EDIT_MODE_DOODLE_CREATE = "729.5.4.1.20073";
    public static final String NOTE_EDIT_MODE_ENTER = "729.5.1.1.39606";
    public static final String NOTE_EDIT_MODE_QUIT = "729.5.1.1.39607";
    public static final String NOTE_EDIT_OPEN = "729.5.0.1.20065";
    public static final String NOTE_EDIT_QUIT = "729.5.0.1.20066";
    public static final String NOTE_EFFECTIVE_CREATE = "729.5.0.1.20350";
    public static final String NOTE_SET_THEME = "729.5.0.1.25591";
    public static final String NOTE_TOP_TOOLBAR_CLICK = "729.5.7.1.20076";
    public static final String NOTE_TOP_TOOLBAR_SHARE_CLICK = "729.5.8.1.20077";
    public static final String NOTE_TOP_TOOLBAR_SHARE_IMAGE_CLICK = "729.5.8.1.20079";
    public static final String NOTE_TOP_TOOLBAR_SHARE_TEXT_CLICK = "729.5.8.1.20078";
    public static final String OUT_FULL_SCREEN = "退出全屏";
    public static final String PASTE = "粘贴";
    public static final String PENCIL_TOOL = "铅笔工具";
    public static final String PEN_BAG_EXPAND = "笔袋展开";
    public static final String PEN_BAG_PACK_UP = "笔袋收起";
    public static final String PEN_TOOL = "钢笔工具";
    public static final String QUIT_WAY = "quit_way";
    public static final String READ_ALOUD = "朗读";
    public static final String RED = "red";
    public static final String REDO = "redo";
    public static final String SELECT_ALL = "全选";
    public static final String SHADOW = "shadow";
    public static final String SHAPE_RECOGNITION_ARC = "触发形状识别_弧线";
    public static final String SHAPE_RECOGNITION_BROKEN_LINE = "触发形状识别_折线";
    public static final String SHAPE_RECOGNITION_CIRCULAR = "触发形状识别_圆形";
    public static final String SHAPE_RECOGNITION_ELLIPSE = "触发形状识别_椭圆";
    public static final String SHAPE_RECOGNITION_FIVE_POINT_STAR = "触发形状识别_五角星";
    public static final String SHAPE_RECOGNITION_LINE = "触发形状识别_直线";
    public static final String SHAPE_RECOGNITION_PENTAGON = "触发形状识别_五边形";
    public static final String SHAPE_RECOGNITION_RECTANGLE = "触发形状识别_矩形";
    public static final String SHAPE_RECOGNITION_TRIANGLE = "触发形状识别_三角形";
    public static final String SHARE = "分享";
    public static final String SKY = "sky";
    public static final String SOURCE = "source";
    public static final String STARS = "stars";
    public static final String SUB_SOURCE = "sub_source";
    public static final String SUMMARY_TEXT_NUM = "summary_text_num";
    public static final String TEXTURE_TOOL = "纹理工具";
    public static final String TEXTURE_TOOL_POINT_PAPER = "纹理工具_点阵纸";
    public static final String TEXTURE_TOOL_WITHE_PAPER = "纹理工具_白纸";
    public static final String TRANSLATE = "翻译";
    public static final String UNDO = "undo";
    public static final String USE_COLOR = "使用颜色";
    public static final String YELLOW = "yellow";
    public static boolean isCreateCommonNote = false;

    public static void reportTopClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(NOTE_TOP_TOOLBAR_CLICK, map);
    }

    public static void reportThemeClickEvent(int i) {
        HashMap map = new HashMap();
        map.put("theme_style_type", getTheme(i));
        reportClickEvent(NOTE_SET_THEME, map);
    }

    public static void reportShareClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(NOTE_TOP_TOOLBAR_SHARE_CLICK, map);
    }

    public static void reportShareAsTextClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(NOTE_TOP_TOOLBAR_SHARE_TEXT_CLICK, map);
    }

    public static void reportShareAsPictureClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(NOTE_TOP_TOOLBAR_SHARE_IMAGE_CLICK, map);
    }

    public static void reportQuitEvent(int i) {
        HashMap map = new HashMap();
        map.put("theme_style_type", getTheme(i));
        reportQuitEvent(NOTE_EDIT_QUIT, map);
    }

    public static void reportClipBoardEvent(String str, String str2, int i, long j, int i2, String str3, String str4) {
        HashMap map = new HashMap();
        map.put(CONTENT_TYPE, str);
        map.put(CONTENT_STATE, str2);
        map.put(CONTENT_TEXT_COUNT, Integer.valueOf(i));
        map.put(CONTENT_CREATIME, Long.valueOf(j));
        map.put(CONTENT_STYLE, Integer.valueOf(i2));
        map.put(ELEMENT_NAME, str3);
        map.put("apk", str4);
        reportClickEvent(NOTE_CLIP_BOARD, map);
        Logger.INSTANCE.d("NotesTextStat", "reportClipBoardEvent: map === " + map);
    }

    public static void reportEnterEditModeEvent(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, String str9, int i2, String str10) {
        HashMap map = new HashMap();
        map.put("source", str);
        map.put("sub_source", str2);
        map.put("folder", str3);
        map.put(IS_REMIND, str4);
        map.put(IS_TOPUP, str5);
        map.put(IS_PRIVACY, str6);
        map.put(CONTENT_TYPE, str7);
        map.put(CONTENT_STATE, str8);
        map.put(CONTENT_TEXT_COUNT, Integer.valueOf(i));
        map.put(CONTENT_CREATIME, str9);
        map.put(CONTENT_STYLE, Integer.valueOf(i2));
        map.put(ENTER_WAY, str10);
        reportEnterEvent(NOTE_EDIT_MODE_ENTER, map);
        Logger.INSTANCE.d("NotesTextStat", "reportEnterEditModeEvent: " + map);
    }

    public static void reportQuitEditModeEvent(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, String str9, int i2, String str10, String str11, int i3) {
        HashMap map = new HashMap();
        map.put("source", str);
        map.put("sub_source", str2);
        map.put("folder", str3);
        map.put(IS_REMIND, str4);
        map.put(IS_TOPUP, str5);
        map.put(IS_PRIVACY, str6);
        map.put(CONTENT_TYPE, str7);
        map.put(CONTENT_STATE, str8);
        map.put(CONTENT_TEXT_COUNT, Integer.valueOf(i));
        map.put(CONTENT_CREATIME, str9);
        map.put(CONTENT_STYLE, Integer.valueOf(i2));
        map.put(ENTER_WAY, str10);
        map.put("quit_way", str11);
        map.put("duration", Integer.valueOf(i3));
        reportQuitEvent(NOTE_EDIT_MODE_QUIT, map);
        Logger.INSTANCE.d("NotesTextStat", "reportQuitEditModeEvent: " + map);
    }

    public static void reportQuitEditEvent(String str, String str2, String str3, String str4, String str5, int i, long j, String str6, long j2, String str7, String str8, String str9, String str10) {
        HashMap map = new HashMap();
        map.put("source", str);
        map.put("sub_source", str2);
        map.put("folder", str3);
        map.put(CONTENT_TYPE, str4);
        map.put(CONTENT_STATE, str5);
        map.put(CONTENT_TEXT_COUNT, Integer.valueOf(i));
        map.put(CONTENT_CREATIME, Long.valueOf(j));
        map.put(CONTENT_STYLE, str6);
        map.put("duration", Long.valueOf(j2));
        map.put("quit_way", str7);
        map.put(IS_REMIND, str8);
        map.put(IS_TOPUP, str9);
        map.put(IS_PRIVACY, str10);
        reportQuitEvent(NOTE_EDIT_QUIT, map);
        Logger.INSTANCE.d("NotesTextStat", "reportQuitEditEvent: " + map);
    }

    public static void reportOpenEditEvent(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, long j, int i2) {
        HashMap map = new HashMap();
        map.put("source", str);
        map.put("sub_source", str2);
        map.put("folder", str3);
        map.put(IS_REMIND, str4);
        map.put(IS_TOPUP, str5);
        map.put(IS_PRIVACY, str6);
        map.put(CONTENT_TYPE, str7);
        map.put(CONTENT_STATE, str8);
        map.put(CONTENT_TEXT_COUNT, Integer.valueOf(i));
        map.put(CONTENT_CREATIME, Long.valueOf(j));
        map.put(CONTENT_STYLE, Integer.valueOf(i2));
        reportOpenEvent(NOTE_EDIT_OPEN, map);
        Logger.INSTANCE.d("NotesTextStat", "reportOpenEditEvent:  " + map);
    }

    public static void reportDoodleClickEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(NOTE_EDIT_MODE_DOODLE_CLICK, map);
    }

    public static void reportNoteDragEvent(String str) {
        HashMap map = new HashMap();
        map.put("element_type", str);
        reportDragEvent(NOTE_EDIT_DRAG, map);
    }

    public static void markDoodleOperation(int i) {
        String str;
        switch (i) {
            case 0:
                str = SHAPE_RECOGNITION_CIRCULAR;
                break;
            case 1:
                str = SHAPE_RECOGNITION_ELLIPSE;
                break;
            case 2:
                str = SHAPE_RECOGNITION_LINE;
                break;
            case 3:
                str = SHAPE_RECOGNITION_TRIANGLE;
                break;
            case 4:
                str = SHAPE_RECOGNITION_PENTAGON;
                break;
            case 5:
                str = SHAPE_RECOGNITION_FIVE_POINT_STAR;
                break;
            case 6:
                str = SHAPE_RECOGNITION_RECTANGLE;
                break;
            case 7:
                str = SHAPE_RECOGNITION_BROKEN_LINE;
                break;
            case 8:
                str = SHAPE_RECOGNITION_ARC;
                break;
            case 9:
                str = ADD_TEXT_SURE;
                break;
            case 10:
                str = ADD_TEXT_CANCEL;
                break;
            default:
                str = "";
                break;
        }
        reportDoodleClickEvent(str);
    }

    public static String getTheme(int i) {
        switch (i) {
            case 0:
                return "default";
            case 1:
                return BLUE;
            case 2:
                return YELLOW;
            case 3:
                return GREEN;
            case 4:
                return RED;
            case 5:
                return CANDY;
            case 6:
                return "black";
            case 7:
                return ANITQUE;
            case 8:
                return ARTY;
            case 9:
                return STARS;
            case 10:
            default:
                return "";
            case 11:
                return SHADOW;
            case 12:
                return LEAF;
            case 13:
                return SKY;
            case 14:
                return GEOMETRY;
        }
    }
}
