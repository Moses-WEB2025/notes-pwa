package com.miui.creation.logger.inner;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/* JADX INFO: loaded from: classes2.dex */
public class Markers {
    private static Marker fileMarker;
    private static Marker fileOnlyMarker;

    public static Marker getFileMarker() {
        if (fileMarker == null) {
            fileMarker = MarkerFactory.getMarker("XCFileMarker");
        }
        return fileMarker;
    }

    public static Marker getFileOnlyMarker() {
        if (fileOnlyMarker == null) {
            fileOnlyMarker = MarkerFactory.getMarker("XCFileOnlyMarker");
        }
        return fileOnlyMarker;
    }
}
