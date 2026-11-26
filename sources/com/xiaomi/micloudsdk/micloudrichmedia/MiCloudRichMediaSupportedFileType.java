package com.xiaomi.micloudsdk.micloudrichmedia;

import java.util.HashSet;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudRichMediaSupportedFileType {
    public static final String ICO = "ico";
    public static final String MIXIN = "mixin";
    public static final String MIXIN2 = "mixin2";
    public static final String MMS = "mms";
    public static final String NOTE_ENT = "note_ent";
    public static final String NOTE_IMAGE = "note_img";
    public static final String NOTE_REC = "note_rec";
    public static final String REC = "rec";
    private static final HashSet<String> sSupportedFileTypeSet;

    static {
        HashSet<String> hashSet = new HashSet<>();
        sSupportedFileTypeSet = hashSet;
        hashSet.add("mms");
        hashSet.add("ico");
        hashSet.add("rec");
        hashSet.add("mixin");
        hashSet.add("note_img");
        hashSet.add("note_rec");
        hashSet.add("note_ent");
        hashSet.add("mixin2");
    }

    public static boolean isSupported(String str) {
        return sSupportedFileTypeSet.contains(str);
    }

    public static boolean isMiXin2(String str) {
        return "mixin2".equals(str);
    }
}
