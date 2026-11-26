package com.xiaomi.phonenum.procedure.cert;

import android.content.Context;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.obtain.ObtainHandler;
import com.xiaomi.phonenum.obtain.PhoneException;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class OperatorAccountCertificationFetcher implements IAccountCertificationFetcher {
    private final String packageName;
    private final String sid;

    public OperatorAccountCertificationFetcher(String str, String str2) {
        this.sid = str;
        this.packageName = str2;
    }

    @Override // com.xiaomi.phonenum.procedure.cert.IAccountCertificationFetcher
    public AccountCertification fetch(Context context, int i) throws AccountCertificationFetchException {
        try {
            return ObtainHandler.getInstance(context).obtainAccountCertification(i, this.sid, this.packageName);
        } catch (PhoneException e) {
            throw new AccountCertificationFetchException(e);
        } catch (IOException e2) {
            throw new AccountCertificationFetchException(e2);
        }
    }
}
