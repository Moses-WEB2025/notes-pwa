package com.xiaomi.ai.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

/* JADX INFO: loaded from: classes4.dex */
public class b {
    private static final String a = "LocationUtils";

    public static Location a(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager != null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(2);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                criteria.setPowerRequirement(1);
                String bestProvider = locationManager.getBestProvider(criteria, true);
                if (bestProvider == null) {
                    return null;
                }
                Log.w(a, "best strLocationProvider " + bestProvider);
                Location lastKnownLocation = locationManager.getLastKnownLocation(bestProvider);
                if (lastKnownLocation == null) {
                    c cVar = new c();
                    locationManager.requestSingleUpdate(criteria, cVar, Looper.getMainLooper());
                    locationManager.removeUpdates(cVar);
                }
                return lastKnownLocation;
            }
        } catch (Exception e) {
            Log.e(a, "e", e);
        }
        return null;
    }
}
