package com.miui.notes.ai.utils;

import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class NoteSchemaUtil {
    public static final int AUDIO_LABEL_LENGTH = 63;
    private static final String SHA1_STR_REG = "[0-9a-fA-F]{40}";
    public static final String TAG = "NoteSchemaUtil";
    private static final Pattern PATTERN_MODERN_TAG = Pattern.compile("<[^>]+>");
    private static final Pattern PATTERN_MODERN_TAG_WITHOUT_LINEBREAK = Pattern.compile("<[^>]+>");
    private static final Pattern PATTERN_MODERN_IMAGE = Pattern.compile("<img fileid=\"([^\"]+?)\" imgshow=\"([^\"]+?)\" imgdes=\"([^\"]*?)\" />", 8);
    public static final Pattern PATTERN_MODERN_AUDIO = Pattern.compile("<sound fileid=\"[0-9a-fA-F]{40}\\.mp3\" />", 8);
    public static final Pattern NEW_PATTERN_MODERN_AUDIO = Pattern.compile("<sound fileid=\"[0-9a-fA-F]{40}\\.mp3\" voicedes=\".*\" />", 8);

    public static String extractPlainText(String str) {
        if (str == null) {
            return "";
        }
        return PATTERN_MODERN_TAG.matcher(PATTERN_MODERN_TAG_WITHOUT_LINEBREAK.matcher(str).replaceAll("")).replaceAll("");
    }

    public static int getPlainTextCount(String str) {
        return extractPlainText(str).length();
    }

    public static boolean hasImage(String str) {
        return PATTERN_MODERN_IMAGE.matcher(str).find();
    }

    public static boolean hasAudioLabel(String str) {
        if (str == null || str.length() < 63) {
            return false;
        }
        boolean zFind = PATTERN_MODERN_AUDIO.matcher(str).find();
        return !zFind ? NEW_PATTERN_MODERN_AUDIO.matcher(str).find() : zFind;
    }

    public static String getContentType(String str) {
        if (hasImage(str)) {
            return AITextWidgetStat.TYPE_IMAGE;
        }
        if (hasAudioLabel(str)) {
            return AITextWidgetStat.TYPE_AUDIO;
        }
        return "text";
    }

    public static String hasDeleteLabel(String str) {
        Matcher matcher = Pattern.compile("<delete>(.*?)</delete>").matcher(str);
        String strGroup = "";
        while (matcher.find()) {
            strGroup = matcher.group(1);
            Logger.INSTANCE.d(TAG, "hasDeleteLabel: " + strGroup);
        }
        return strGroup;
    }

    public static String hasOneTrackLabel(String str) {
        Matcher matcher = Pattern.compile("<onetrack>(.*?)</onetrack>").matcher(str);
        String strGroup = "";
        while (matcher.find()) {
            strGroup = matcher.group(1);
            Logger.INSTANCE.d(TAG, "oneTrackLabel: " + strGroup);
        }
        return strGroup;
    }

    public static int[] matchOneTrackLabel(String str) {
        Logger.INSTANCE.d(TAG, "matchOneTrackLabel: " + str);
        Matcher matcher = Pattern.compile("lastTime:(\\d+).*count:(\\d+)").matcher(str);
        int[] iArr = new int[2];
        if (matcher.find()) {
            iArr[0] = Integer.parseInt((String) Objects.requireNonNull(matcher.group(1)));
            iArr[1] = Integer.parseInt((String) Objects.requireNonNull(matcher.group(2)));
            Logger.INSTANCE.d(TAG, "lastTime: " + iArr[0]);
            Logger.INSTANCE.d(TAG, "count: " + iArr[1]);
        }
        return iArr;
    }

    public static String hasThemeLabel(String str) {
        Matcher matcher = Pattern.compile("<theme>(.*?)</theme>").matcher(str);
        String strGroup = "";
        while (matcher.find()) {
            strGroup = matcher.group(1);
            Logger.INSTANCE.d(TAG, "hasThemeLabel: " + strGroup);
        }
        return strGroup;
    }
}
