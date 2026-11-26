package com.xiaomi.passport.ui;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TrackEventManager {
    private static List<String> sEventNameList;
    private static TrackEventCallback sTrackEventCallback;

    public interface TrackEventCallback {
        void call(String str);
    }

    public static void setTrackEventCallback(List<String> list, TrackEventCallback trackEventCallback) {
        sTrackEventCallback = trackEventCallback;
        sEventNameList = list;
    }

    public static void addEvent(String str) {
        TrackEventCallback trackEventCallback;
        if (!isContainsEvent(str) || (trackEventCallback = sTrackEventCallback) == null) {
            return;
        }
        trackEventCallback.call(str);
    }

    private static boolean isContainsEvent(String str) {
        List<String> list = sEventNameList;
        if (list != null) {
            return list.contains(str);
        }
        return false;
    }
}
