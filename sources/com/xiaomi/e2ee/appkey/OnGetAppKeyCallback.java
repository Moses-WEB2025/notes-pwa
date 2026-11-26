package com.xiaomi.e2ee.appkey;

import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.e2ee.E2EEException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
public class OnGetAppKeyCallback extends IGetAppKeyCallback.Stub {
    private static final int REMOTE_CALL_TIMEOUT_DELAY = 60000;
    private static final String TAG = "OnGetAppKeyCallback";
    private String mAppKeyInfoJsonResult;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private String mRemoteMessage;
    private int mResultCode;

    public AppKeyInfo getResult() throws InterruptedException, E2EEException, RemoteException {
        try {
            waitForResult();
            checkResultCode();
            if (this.mAppKeyInfoJsonResult == null) {
                Log.i(TAG, "E2EE is close, return null");
                return null;
            }
            return AppKeyInfo.jsonStrToBean(AppKeyServiceManager.getInstance().decryptJsonStr(this.mAppKeyInfoJsonResult));
        } catch (JSONException e) {
            throw new E2EEException(-300, e);
        }
    }

    public void resetCountDown() {
        this.mCountDownLatch = new CountDownLatch(1);
    }

    private void setResult(String str, int i, String str2) {
        Log.i(TAG, "remote call success, result code is " + i + ", message is " + str2);
        this.mResultCode = i;
        this.mAppKeyInfoJsonResult = str;
        this.mRemoteMessage = str2;
        this.mCountDownLatch.countDown();
    }

    private void waitForResult() throws InterruptedException, E2EEException {
        if (!this.mCountDownLatch.await(60000L, TimeUnit.MILLISECONDS)) {
            throw new E2EEException(-200, "remote call timeout.");
        }
    }

    private void checkResultCode() throws E2EEException {
        if (this.mResultCode != 0) {
            throw new E2EEException(this.mResultCode, this.mRemoteMessage);
        }
    }

    @Override // com.xiaomi.e2ee.appkey.IGetAppKeyCallback
    public void onGetAppKeyResult(String str, int i, String str2) throws RemoteException {
        setResult(str, i, str2);
    }
}
