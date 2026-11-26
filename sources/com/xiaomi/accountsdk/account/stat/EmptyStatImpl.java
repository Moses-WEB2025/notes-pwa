package com.xiaomi.accountsdk.account.stat;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class EmptyStatImpl extends AccountStatInterface {
    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void enableNetworkAccess(Context context) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void setCommonProperty(Map<String, Object> map) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void statHttpEvent(String str, long j) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void statHttpEvent(String str, Exception exc) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void track(String str, Map<String, Object> map) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void trackNetAvailable(NetStatParam netStatParam) {
    }
}
