package com.market.sdk.homeguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import com.market.sdk.ServiceProxy2;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Log;
import com.market.sdk.utils.PkgUtils;

/* JADX INFO: loaded from: classes2.dex */
class AppstoreUserGuideService extends ServiceProxy2 implements IAppstoreHomeGuideService {
    private static final String TAG = "AppStoreUserGuide";
    private static final String TARGET_PKG = "com.xiaomi.mipicks";
    private static final String USER_GUIDE_ACTION = "com.xiaomi.market.HOME_USER_GUIDE";

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.market.sdk.homeguide.IAppstoreHomeGuideService
    public HomeUserGuideResult ready(HomeUserGuideData homeUserGuideData) throws RemoteException {
        return null;
    }

    @Override // com.market.sdk.homeguide.IAppstoreHomeGuideService
    public void show(ResultReceiver resultReceiver) throws RemoteException {
    }

    public static AppstoreUserGuideService openService() {
        return new AppstoreUserGuideService(AppGlobal.getContext(), getUserGuideIntent());
    }

    public static Intent getUserGuideIntent() {
        Intent intent = new Intent(USER_GUIDE_ACTION);
        intent.setPackage("com.xiaomi.mipicks");
        if (PkgUtils.queryIntentServices(intent, 0).isEmpty()) {
            return null;
        }
        return intent;
    }

    public AppstoreUserGuideService(Context context, Intent intent) {
        super(context, intent);
    }

    public void tryShow(final HomeUserGuideData homeUserGuideData, final Callback callback) {
        setTask(new ServiceProxy2.ProxyTask<Void>(false) { // from class: com.market.sdk.homeguide.AppstoreUserGuideService.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.market.sdk.ServiceProxy2.ProxyTask
            public Void run(IBinder iBinder) throws RemoteException {
                Handler handler = null;
                if (iBinder == null) {
                    return null;
                }
                boolean z = true;
                try {
                    IAppstoreHomeGuideService iAppstoreHomeGuideServiceAsInterface = IAppstoreHomeGuideService.Stub.asInterface(iBinder);
                    AppGlobal.getContext().grantUriPermission("com.xiaomi.mipicks", homeUserGuideData.getHomeScreenUri(), 1);
                    int errorCode = iAppstoreHomeGuideServiceAsInterface.ready(homeUserGuideData).getErrorCode();
                    if (errorCode == 1) {
                        throw new IllegalArgumentException();
                    }
                    if (errorCode == 2) {
                        try {
                            throw new IllegalArgumentException();
                        } catch (Throwable th) {
                            th = th;
                            z = false;
                            notifyResult(z);
                            unbind();
                            Log.e(AppstoreUserGuideService.TAG, th.toString(), th);
                            return null;
                        }
                    }
                    Callback callback2 = callback;
                    if (callback2 == null || !callback2.onIntercept()) {
                        iAppstoreHomeGuideServiceAsInterface.show(new ResultReceiver(handler) { // from class: com.market.sdk.homeguide.AppstoreUserGuideService.1.1
                            @Override // android.os.ResultReceiver
                            protected void onReceiveResult(int i, Bundle bundle) {
                                notifyResult(i != 0);
                                unbind();
                            }
                        });
                        iBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.market.sdk.homeguide.AppstoreUserGuideService.1.2
                            @Override // android.os.IBinder.DeathRecipient
                            public void binderDied() {
                                notifyResult(true);
                            }
                        }, 0);
                    } else {
                        unbind();
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void notifyResult(boolean z) {
                Callback callback2 = callback;
                if (callback2 != null) {
                    callback2.onFinish(z);
                }
            }
        });
    }
}
