package com.xiaomi.mirror.synergy;

import android.content.Context;
import android.os.Bundle;
import com.xiaomi.mirror.IMirrorTakePhotoCallback;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class TakingPhotoManager {
    public CallbackWrapper mCallbackWrapper;

    public class CallbackWrapper extends IMirrorTakePhotoCallback.Stub {
        public WeakReference<MirrorTakePhotoCallback> mCallBack;

        public CallbackWrapper(MirrorTakePhotoCallback mirrorTakePhotoCallback) {
            this.mCallBack = new WeakReference<>(mirrorTakePhotoCallback);
        }

        @Override // com.xiaomi.mirror.IMirrorTakePhotoCallback
        public void onTakePhotoChanged(String str, boolean z) {
            if (this.mCallBack.get() != null) {
                this.mCallBack.get().onTakePhotoChanged(str, z);
            }
        }
    }

    public int registerTakePhotoCallback(Context context, MirrorTakePhotoCallback mirrorTakePhotoCallback) {
        if (this.mCallbackWrapper != null) {
            return -1;
        }
        this.mCallbackWrapper = new CallbackWrapper(mirrorTakePhotoCallback);
        Bundle bundle = new Bundle();
        bundle.putBinder(CallMethod.ARG_CALLBACK, this.mCallbackWrapper.asBinder());
        try {
            return CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_REGISTER_TAKING_PHOTO, null, bundle) != null ? 0 : -1;
        } catch (Exception unused) {
        }
    }

    public int unregisterTakePhotoCallback(Context context, MirrorTakePhotoCallback mirrorTakePhotoCallback) {
        CallbackWrapper callbackWrapper = this.mCallbackWrapper;
        if (callbackWrapper == null || callbackWrapper.mCallBack.get() != mirrorTakePhotoCallback) {
            return -1;
        }
        Bundle bundle = new Bundle();
        bundle.putBinder(CallMethod.ARG_CALLBACK, this.mCallbackWrapper.asBinder());
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_UNREGISTER_TAKING_PHOTO, null, bundle);
        } catch (Exception unused) {
        }
        this.mCallbackWrapper = null;
        return 0;
    }
}
