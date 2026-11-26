package com.miui.aiengine.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.NetConfig;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemoteMthodInvoker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\b&\u0018\u0000 \u001b*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0002\u001a\u001bB\u0011\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\nH\u0016J\r\u0010\u0011\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u0002H$J\u0017\u0010\u0017\u001a\u00028\u00002\b\u0010\u0018\u001a\u0004\u0018\u00010\nH$¢\u0006\u0002\u0010\u0019R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/aiengine/common/utils/RemoteMethodInvoker;", "R", "Landroid/content/ServiceConnection;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "mContext", "future", "Lcom/miui/aiengine/common/utils/RemoteMethodInvoker$AsyncFuture;", "Landroid/os/IBinder;", "onServiceDisconnected", "", "name", "Landroid/content/ComponentName;", "onServiceConnected", NotificationCompat.CATEGORY_SERVICE, "invoke", "()Ljava/lang/Object;", "bindService", "", "ctx", RemoteDeviceInfo.KEY_SN, "invokeRemoteMethod", "var1", "(Landroid/os/IBinder;)Ljava/lang/Object;", "AsyncFuture", "Companion", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class RemoteMethodInvoker<R> implements ServiceConnection {
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_TIMEOUT_SECONDS = 5;
    private static final String TAG = "RemoteMethodInvoker";
    private final AsyncFuture<IBinder> future = new AsyncFuture<>();
    private Context mContext;

    protected abstract boolean bindService(Context ctx, ServiceConnection sn);

    protected abstract R invokeRemoteMethod(IBinder var1) throws RemoteException;

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName name) {
        Intrinsics.checkNotNullParameter(name, "name");
    }

    public RemoteMethodInvoker(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null".toString());
        }
        this.mContext = context.getApplicationContext();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName name, IBinder service) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(service, "service");
        Log.i(TAG, "RemoteMethodInvoker connects remote service " + name.getShortClassName());
        this.future.setResult(service);
    }

    public final R invoke() {
        ThreadUtils.INSTANCE.ensureWorkerThread();
        R rInvokeRemoteMethod = null;
        if (!bindService(this.mContext, this)) {
            Log.e(TAG, "Cannot bind remote service.");
            return null;
        }
        for (int i = 3; i > 0; i--) {
            try {
                rInvokeRemoteMethod = invokeRemoteMethod(this.future.get(5L, TimeUnit.SECONDS));
                break;
            } catch (RemoteException e) {
                Log.e(TAG, "invoke service method error - remaining retry count : " + i + ", exception : " + e);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                break;
            } catch (ExecutionException unused2) {
                Log.e(TAG, "invoke service method occurs execution error - remaining retry count : " + i);
            } catch (TimeoutException unused3) {
                Log.e(TAG, "invoke service method time out - remaining retry count : " + i);
            }
        }
        try {
            Context context = this.mContext;
            Intrinsics.checkNotNull(context);
            context.unbindService(this);
        } catch (NoSuchElementException e2) {
            LogUtils.INSTANCE.logError(NetConfig.TAG, TAG, String.valueOf(e2.getMessage()));
        }
        return rInvokeRemoteMethod;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: RemoteMthodInvoker.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/aiengine/common/utils/RemoteMethodInvoker$AsyncFuture;", "V", "Ljava/util/concurrent/FutureTask;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "setResult", "", "v", "(Ljava/lang/Object;)V", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final class AsyncFuture<V> extends FutureTask<V> {
        public AsyncFuture() {
            super(new Callable() { // from class: com.miui.aiengine.common.utils.RemoteMethodInvoker$AsyncFuture$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return RemoteMethodInvoker.AsyncFuture._init_$lambda$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Object _init_$lambda$0() {
            throw new IllegalStateException("this should never be called");
        }

        public final void setResult(V v) {
            set(v);
        }
    }
}
