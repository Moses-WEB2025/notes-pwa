package miui.cloud.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes5.dex */
public class XMainThreadCallback<T> extends XDirectCallback<T> {
    private Handler mHandler;

    private static class CallRequest {
        public Object[] args;
        public Method method;

        public CallRequest(Method method, Object[] objArr) {
            this.method = method;
            this.args = objArr;
        }
    }

    public XMainThreadCallback(Class<T> cls, T t) {
        super(cls, t);
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: miui.cloud.common.XMainThreadCallback.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                CallRequest callRequest = (CallRequest) message.obj;
                XMainThreadCallback.this.handleCallbackInMainThread(callRequest.method, callRequest.args);
            }
        };
    }

    @Override // miui.cloud.common.XDirectCallback, miui.cloud.common.XCallback
    protected Object handleCallback(Method method, Object[] objArr) throws Throwable {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.obj = new CallRequest(method, objArr);
        messageObtainMessage.sendToTarget();
        return null;
    }

    protected void handleCallbackInMainThread(Method method, Object[] objArr) {
        try {
            super.handleCallback(method, objArr);
        } catch (Throwable unused) {
            XLogger.loge("Exception in callback, but unable to propagate to the original thread. ");
        }
    }
}
