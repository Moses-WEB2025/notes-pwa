package com.miui.pad.feature.notes.meeting;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.security.devicecredential.SecurityDeviceCredentialAbility;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiFidSigner {
    private static final String KEY_STRING_FID = "fid";
    private static final String SP_NAME = "note_fid_signer";
    private static final String TAG = "MiuiFidSigner";
    private static String sCachedFid;
    private static ExecutorService sExecutors = Executors.newSingleThreadExecutor();

    public byte[] sign(final byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return (byte[]) safeCall(new Callable<byte[]>() { // from class: com.miui.pad.feature.notes.meeting.MiuiFidSigner.1
            @Override // java.util.concurrent.Callable
            public byte[] call() throws Exception {
                SecurityDeviceCredentialAbility securityDeviceCredentialAbility = new SecurityDeviceCredentialAbility(NoteApp.getInstance());
                try {
                    return securityDeviceCredentialAbility.signWithDeviceCredential(bArr);
                } finally {
                    securityDeviceCredentialAbility.release();
                }
            }
        });
    }

    public String getFid() {
        synchronized (MiuiFidSigner.class) {
            if (sCachedFid != null) {
                Logger.INSTANCE.d(TAG, "getFid get from memory " + sCachedFid);
                return sCachedFid;
            }
            String string = getSharedPrefs().getString(KEY_STRING_FID, null);
            sCachedFid = string;
            if (string != null) {
                Logger.INSTANCE.d(TAG, "getFid get from sp " + sCachedFid);
                return sCachedFid;
            }
            return (String) safeCall(new Callable<String>() { // from class: com.miui.pad.feature.notes.meeting.MiuiFidSigner.2
                @Override // java.util.concurrent.Callable
                public String call() throws Exception {
                    SecurityDeviceCredentialAbility securityDeviceCredentialAbility = new SecurityDeviceCredentialAbility(NoteApp.getInstance());
                    try {
                        String securityDeviceId = securityDeviceCredentialAbility.getSecurityDeviceId();
                        securityDeviceCredentialAbility.release();
                        if (!TextUtils.isEmpty(securityDeviceId)) {
                            synchronized (MiuiFidSigner.class) {
                                MiuiFidSigner.sCachedFid = securityDeviceId;
                                MiuiFidSigner.getSharedPrefs().edit().putString(MiuiFidSigner.KEY_STRING_FID, securityDeviceId).commit();
                            }
                        }
                        Logger.INSTANCE.d(MiuiFidSigner.TAG, "getFid get from binder " + securityDeviceId);
                        return securityDeviceId;
                    } catch (Throwable th) {
                        securityDeviceCredentialAbility.release();
                        throw th;
                    }
                }
            });
        }
    }

    private <T> T safeCall(Callable<T> callable) {
        Future<T> futureSubmit = sExecutors.submit(callable);
        try {
            return futureSubmit.get(5L, TimeUnit.SECONDS);
        } catch (TimeoutException unused) {
            futureSubmit.cancel(true);
            return null;
        } catch (Exception unused2) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SharedPreferences getSharedPrefs() {
        return NoteApp.getInstance().getSharedPreferences(SP_NAME, 0);
    }
}
