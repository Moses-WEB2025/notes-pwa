package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import com.xiaomi.accountsdk.account.URLs;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes4.dex */
public class AccountServerTimeComputer implements ServerTimeUtil.IServerTimeComputer {
    private static final String SP_KEY_SYSTEM_TIME_DIFF = "system_time_diff";
    private static final String SP_NAME = "accountsdk_servertime";
    private static final String TAG = "AccountServerTimeCompu";
    private static final Set<String> acceptHosts;
    private final Context context;
    private volatile long elapsedRealTimeDiff = 0;
    private final AtomicBoolean alignFlag = new AtomicBoolean(false);

    static {
        HashSet hashSet = new HashSet();
        acceptHosts = hashSet;
        String[] strArr = new String[1];
        try {
            hashSet.add(new URL(URLs.ACCOUNT_DOMAIN).getHost().toLowerCase());
        } catch (MalformedURLException unused) {
        }
    }

    public AccountServerTimeComputer(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        this.context = context.getApplicationContext();
    }

    @Override // com.xiaomi.accountsdk.utils.ServerTimeUtil.IServerTimeComputer
    public long computeServerTime() {
        if (this.alignFlag.get()) {
            return SystemClock.elapsedRealtime() + this.elapsedRealTimeDiff;
        }
        return System.currentTimeMillis() + getSystemTimeDiff();
    }

    @Override // com.xiaomi.accountsdk.utils.ServerTimeUtil.IServerTimeComputer
    public void alignWithServerTime(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("serverDate == null");
        }
        if (this.alignFlag.get()) {
            return;
        }
        alignServer(date);
        this.alignFlag.set(true);
    }

    @Override // com.xiaomi.accountsdk.utils.ServerTimeUtil.IServerTimeComputer
    public void alignWithServerDateHeader(String str, String str2) {
        if (!this.alignFlag.get() && isAccountUrl(str)) {
            try {
                alignWithServerTime(DateUtils.parseDate(str2));
            } catch (ParseException e) {
                AccountLogger.log(TAG, e);
            }
        }
    }

    static boolean isAccountUrl(String str) {
        try {
            return acceptHosts.contains(new URL(str).getHost().toLowerCase());
        } catch (MalformedURLException e) {
            AccountLogger.log(TAG, e);
            return false;
        }
    }

    void alignServer(Date date) {
        long time = date.getTime();
        this.elapsedRealTimeDiff = time - SystemClock.elapsedRealtime();
        setSystemTimeDiff(time - System.currentTimeMillis());
        ServerTimeUtil.notifyServerTimeAligned();
        SyncServerTimeExecutor.getInstance().syncServerTime(date);
    }

    long getSystemTimeDiff() {
        return getSP().getLong(SP_KEY_SYSTEM_TIME_DIFF, 0L);
    }

    void setSystemTimeDiff(long j) {
        getSP().edit().putLong(SP_KEY_SYSTEM_TIME_DIFF, j).apply();
    }

    SharedPreferences getSP() {
        return this.context.getSharedPreferences(SP_NAME, 0);
    }

    void updateElapsedRealTimeDiffForTest(long j) {
        this.elapsedRealTimeDiff = j;
    }

    long getElapsedRealTimeDiffForTest() {
        return this.elapsedRealTimeDiff;
    }

    void setAlignedForTest(boolean z) {
        this.alignFlag.set(z);
    }
}
