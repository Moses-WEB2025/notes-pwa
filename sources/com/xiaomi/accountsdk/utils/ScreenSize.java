package com.xiaomi.accountsdk.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/* JADX INFO: loaded from: classes4.dex */
public class ScreenSize {
    public static Point getContentSizeUnderCurrentRotation(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point;
    }

    public static Point getFullSizeUnderCurrentRotation(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        return point;
    }

    public static Point getContentSizeIgnoreCurrentRotation(Activity activity) {
        Point point = new Point();
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        defaultDisplay.getSize(point);
        int rotation = defaultDisplay.getRotation();
        return (rotation == 1 || rotation == 3) ? new Point(point.y, point.x) : point;
    }

    public static Point getFullSizeIgnoreCurrentRotation(Activity activity) {
        Point point = new Point();
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        defaultDisplay.getRealSize(point);
        int rotation = defaultDisplay.getRotation();
        return (rotation == 1 || rotation == 3) ? new Point(point.y, point.x) : point;
    }
}
