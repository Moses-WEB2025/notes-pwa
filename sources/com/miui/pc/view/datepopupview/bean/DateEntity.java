package com.miui.pc.view.datepopupview.bean;

/* JADX INFO: loaded from: classes4.dex */
public class DateEntity {
    public String mDate;
    public String mDay;
    public boolean mIsToday;
    public boolean mIsUnAvaible;
    public String mMonth;
    public int mType;
    public int mWeekNum;

    public DateEntity() {
    }

    public DateEntity(int i, String str) {
        this.mType = i;
        this.mDay = str;
    }
}
