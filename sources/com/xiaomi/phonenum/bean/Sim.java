package com.xiaomi.phonenum.bean;

import android.os.Bundle;
import com.xiaomi.passport.sim.SIMInfo;

/* JADX INFO: loaded from: classes4.dex */
public class Sim {
    public final String iccid;
    public final String imsi;
    public final String line1Number;
    public final String mccmnc;

    public Sim(String str, String str2, String str3, String str4) {
        this.iccid = str;
        this.imsi = str2;
        this.mccmnc = str3;
        this.line1Number = str4;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("iccid", this.iccid);
        bundle.putString(SIMInfo.SIM_INFO_TYPE_IMSI, this.imsi);
        bundle.putString(SIMInfo.SIM_INFO_TYPE_MCCMNC, this.mccmnc);
        bundle.putString(SIMInfo.SIM_INFO_TYPE_LINE_1_NUMBER, this.line1Number);
        return bundle;
    }

    public String toString() {
        return toBundle().toString();
    }
}
