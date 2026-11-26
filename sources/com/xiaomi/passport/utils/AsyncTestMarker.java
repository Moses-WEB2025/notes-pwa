package com.xiaomi.passport.utils;

/* JADX INFO: loaded from: classes4.dex */
public final class AsyncTestMarker {
    private static final Marker DEFAULT_MARKER;
    private static volatile Marker marker;

    public interface Marker {
        void decrement();

        void increment();
    }

    public static void setMarker(Marker marker2) {
        marker = marker2;
    }

    public static void resetMarker() {
        marker = DEFAULT_MARKER;
    }

    static {
        MarkerDefaultImpl markerDefaultImpl = new MarkerDefaultImpl();
        DEFAULT_MARKER = markerDefaultImpl;
        marker = markerDefaultImpl;
    }

    public static void increment() {
        marker.increment();
    }

    public static void decrement() {
        marker.decrement();
    }

    private static class MarkerDefaultImpl implements Marker {
        @Override // com.xiaomi.passport.utils.AsyncTestMarker.Marker
        public void decrement() {
        }

        @Override // com.xiaomi.passport.utils.AsyncTestMarker.Marker
        public void increment() {
        }

        private MarkerDefaultImpl() {
        }
    }
}
