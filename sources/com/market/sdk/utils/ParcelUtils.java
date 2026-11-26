package com.market.sdk.utils;

import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public class ParcelUtils {
    public static boolean isMagicExist(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        if (parcel.readInt() == i) {
            return true;
        }
        parcel.setDataPosition(iDataPosition);
        return false;
    }

    public static void writeMagic(Parcel parcel, int i) {
        parcel.writeInt(i);
    }
}
