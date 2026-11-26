package com.xiaomi.security.devicecredential;

import android.os.RemoteException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ErrorCodeRemoteException {
    private static final String KEY_INT_ERROR_CODE = "errorCode";
    public final Integer errorCode;
    public final RemoteException remoteException;

    private ErrorCodeRemoteException(Integer num, RemoteException remoteException) {
        this.errorCode = num;
        this.remoteException = remoteException;
    }

    public static RemoteException create(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("errorCode", i);
            return new RemoteException(jSONObject.toString());
        } catch (JSONException unused) {
            throw new IllegalStateException("should never happen");
        }
    }

    public static ErrorCodeRemoteException parse(RemoteException remoteException) {
        try {
            return new ErrorCodeRemoteException(Integer.valueOf(new JSONObject(remoteException.getMessage()).getInt("errorCode")), remoteException);
        } catch (JSONException unused) {
            return new ErrorCodeRemoteException(null, remoteException);
        }
    }
}
