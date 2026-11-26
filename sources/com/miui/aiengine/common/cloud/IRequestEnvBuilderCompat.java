package com.miui.aiengine.common.cloud;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import kotlin.Metadata;

/* JADX INFO: compiled from: IRequestEnvBuilderCompat.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0004J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&¨\u0006\u0005À\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/cloud/IRequestEnvBuilderCompat;", "", "build", "Lcom/miui/aiengine/common/cloud/IRequestEnvBuilderCompat$RequestEnv;", "RequestEnv", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IRequestEnvBuilderCompat {

    /* JADX INFO: compiled from: IRequestEnvBuilderCompat.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0014\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J(\u0010\n\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\tH&J\b\u0010\u0011\u001a\u00020\u0012H&R\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001bÀ\u0006\u0003"}, d2 = {"Lcom/miui/aiengine/common/cloud/IRequestEnvBuilderCompat$RequestEnv;", "", "queryAuthToken", "", "var1", "Landroid/content/Context;", "invalidateAuthToken", "", "getSystemAccount", "Landroid/accounts/Account;", "getEncryptedUserId", "var2", "Landroid/os/IBinder;", "var3", "userAgent", "getUserAgent", "()Ljava/lang/String;", "shouldUpdateHost", "", "maxRetryCount", "", "getMaxRetryCount", "()I", "autoRetryInterval", "", "getAutoRetryInterval", "()J", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface RequestEnv {
        long getAutoRetryInterval();

        String getEncryptedUserId(Context var1, IBinder var2, Account var3) throws RemoteException;

        int getMaxRetryCount();

        Account getSystemAccount(Context var1);

        String getUserAgent();

        void invalidateAuthToken(Context var1);

        String queryAuthToken(Context var1) throws Exception;

        boolean shouldUpdateHost();
    }

    RequestEnv build();
}
