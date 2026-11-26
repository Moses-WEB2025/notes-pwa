package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes6.dex */
public class BasicMarkerFactory implements IMarkerFactory {
    private final ConcurrentMap<String, Marker> markerMap = new ConcurrentHashMap();

    @Override // org.slf4j.IMarkerFactory
    public Marker getMarker(String str) {
        Marker markerPutIfAbsent;
        if (str == null) {
            throw new IllegalArgumentException("Marker name cannot be null");
        }
        Marker basicMarker = this.markerMap.get(str);
        return (basicMarker != null || (markerPutIfAbsent = this.markerMap.putIfAbsent(str, (basicMarker = new BasicMarker(str)))) == null) ? basicMarker : markerPutIfAbsent;
    }

    @Override // org.slf4j.IMarkerFactory
    public boolean exists(String str) {
        if (str == null) {
            return false;
        }
        return this.markerMap.containsKey(str);
    }

    @Override // org.slf4j.IMarkerFactory
    public boolean detachMarker(String str) {
        return (str == null || this.markerMap.remove(str) == null) ? false : true;
    }

    @Override // org.slf4j.IMarkerFactory
    public Marker getDetachedMarker(String str) {
        return new BasicMarker(str);
    }
}
