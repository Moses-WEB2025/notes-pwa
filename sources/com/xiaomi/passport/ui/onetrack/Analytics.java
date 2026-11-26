package com.xiaomi.passport.ui.onetrack;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class Analytics {
    private static TrackCallBack mCallBack;
    private static volatile Tracker sTracker;

    public interface TrackCallBack {
        void clickTrack(String str, Map<String, Object> map);

        void moduleViewTrack(String str, Map<String, Object> map);

        void pauseTrack(String str, Map<String, Object> map);

        void resultTrack(String str, Map<String, Object> map);

        void resumeTrack(String str, Map<String, Object> map);

        void viewTrack(String str, Map<String, Object> map);
    }

    public interface Tracker {
        void track(String str, Map<String, Object> map);
    }

    public static void setCallBack(TrackCallBack trackCallBack) {
        mCallBack = trackCallBack;
    }

    public static void setTracker(Tracker tracker) {
        sTracker = tracker;
    }

    public static void track(String str, Map<String, Object> map) {
        if (sTracker != null) {
            sTracker.track(str, map);
        }
    }

    public static void moduleViewEvent(String str, String str2, Map<String, Object> map) {
        String str3 = TrackConstants.VIEW + str + str2;
        TrackCallBack trackCallBack = mCallBack;
        if (trackCallBack != null) {
            trackCallBack.moduleViewTrack(str2, map);
        }
        track(str3, map);
    }

    public static void moduleViewEvent(String str, String str2) {
        moduleViewEvent(str, str2, new HashMap());
    }

    public static void viewEvent(String str, Map<String, Object> map) {
        String str2 = TrackConstants.VIEW + str;
        TrackCallBack trackCallBack = mCallBack;
        if (trackCallBack != null) {
            trackCallBack.viewTrack(str, map);
        }
        track(str2, map);
    }

    public static void viewEvent(String str) {
        viewEvent(str, new HashMap());
    }

    public static void resumeEvent(String str, Map<String, Object> map) {
        String str2 = TrackConstants.RESUME + str;
        TrackCallBack trackCallBack = mCallBack;
        if (trackCallBack != null) {
            trackCallBack.resumeTrack(str, map);
        }
        track(str2, map);
    }

    public static void resumeEvent(String str) {
        resumeEvent(str, new HashMap());
    }

    public static void pauseEvent(String str, Map<String, Object> map) {
        String str2 = TrackConstants.PAUSE + str;
        TrackCallBack trackCallBack = mCallBack;
        if (trackCallBack != null) {
            trackCallBack.pauseTrack(str, map);
        }
        track(str2, map);
    }

    public static void pauseEvent(String str) {
        pauseEvent(str, new HashMap());
    }

    public static void clickEvent(String str, Map<String, Object> map) {
        String str2 = TrackConstants.CLICK + str;
        TrackCallBack trackCallBack = mCallBack;
        if (trackCallBack != null) {
            trackCallBack.clickTrack(str, map);
        }
        track(str2, map);
    }

    public static void clickEvent(String str) {
        clickEvent(str, new HashMap());
    }

    public static void resultEvent(String str, Map<String, Object> map) {
        TrackCallBack trackCallBack = mCallBack;
        if (trackCallBack != null) {
            trackCallBack.resultTrack(str, map);
        }
        track(str, map);
    }

    public static void resultEvent(String str) {
        resultEvent(str, new HashMap());
    }
}
