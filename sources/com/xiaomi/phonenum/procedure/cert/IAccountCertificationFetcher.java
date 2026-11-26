package com.xiaomi.phonenum.procedure.cert;

import android.content.Context;
import com.xiaomi.phonenum.data.AccountCertification;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountCertificationFetcher {
    AccountCertification fetch(Context context, int i) throws AccountCertificationFetchException;
}
