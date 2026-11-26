package com.xiaomi.security.devicecredential;

import android.os.IInterface;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes4.dex */
public interface IOnRemoteCallFinishedListener extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.security.devicecredential.ionremotecallfinishedlistener.v0";
    public static final int TRANSACTION_ON_FORCE_RELOAD = 3;
    public static final int TRANSACTION_ON_GET_SECURITY_DEIVCE_ID_FINISHED = 1;
    public static final int TRANSACTION_ON_SIGN_FINISHED = 2;

    void onForceReloadFinished(int i) throws RemoteException;

    void onGetSecurityDeviceIdFinished(int i, String str) throws RemoteException;

    void onSignFinished(int i, byte[] bArr) throws RemoteException;
}
