package com.miui.libhandwritingpreview;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpreview.adapter.PagePreAdapter;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.IDataListener;
import xc.engine.utils.FileUtil;

/* JADX INFO: compiled from: DataBindPreHandler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 !2\u00020\u0001:\u0002 !B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\r2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0010\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001f\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/miui/libhandwritingpreview/DataBindPreHandler;", "Landroid/os/Handler$Callback;", "adapter", "Lcom/miui/libhandwritingpreview/adapter/PagePreAdapter;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/adapter/PagePreAdapter;)V", "thread", "Landroid/os/HandlerThread;", "handler", "Landroid/os/Handler;", "isDestroyed", "", "quitLooper", "", "addLoadTask", "itemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "itemData", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "addWaitIdleTask", "runnable", "Ljava/lang/Runnable;", "handleMessage", "msg", "Landroid/os/Message;", "doUnBindEngineData", "bindMessage", "Lcom/miui/libhandwritingpreview/DataBindPreHandler$BindMessage;", "doLoad", "doBind", "doUnBind", "doWaitIdleTask", "BindMessage", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DataBindPreHandler implements Handler.Callback {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private static final int WHAT_BIND = 2;
    private static final int WHAT_LOAD = 1;
    private static final int WHAT_REDO = 5;
    private static final int WHAT_UNBIND = 3;
    private static final int WHAT_UNBIND_ENGINE = 6;
    private static final int WHAT_UNDO = 4;
    private static final int WHAT_WAIT_IDLE = 7;
    private final PagePreAdapter adapter;
    private final Handler handler;
    private volatile boolean isDestroyed;
    private final HandlerThread thread;

    public DataBindPreHandler(PagePreAdapter adapter) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        this.adapter = adapter;
        HandlerThread handlerThread = new HandlerThread("DataBindPre");
        this.thread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper(), this);
    }

    public final void quitLooper() {
        this.isDestroyed = true;
        try {
            this.handler.removeCallbacksAndMessages(null);
            this.thread.interrupt();
            this.handler.getLooper().quitSafely();
            this.thread.quitSafely();
        } catch (Exception unused) {
        }
    }

    public final void addLoadTask(PagePreItemView itemView, PageItemData itemData) {
        Intrinsics.checkNotNullParameter(itemData, "itemData");
        Message messageObtainMessage = this.handler.obtainMessage(itemData.getPageIndex(), 1, -1, new BindMessage(itemView, itemData));
        Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "obtainMessage(...)");
        messageObtainMessage.sendToTarget();
    }

    public final void addWaitIdleTask(Runnable runnable) {
        Message messageObtainMessage = this.handler.obtainMessage(7, 7, -1, runnable);
        Intrinsics.checkNotNullExpressionValue(messageObtainMessage, "obtainMessage(...)");
        messageObtainMessage.sendToTarget();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        int i = msg.arg1;
        if (i == 1) {
            Object obj = msg.obj;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.libhandwritingpreview.DataBindPreHandler.BindMessage");
            doLoad((BindMessage) obj);
        } else if (i == 2) {
            Object obj2 = msg.obj;
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type com.miui.libhandwritingpreview.DataBindPreHandler.BindMessage");
            doBind((BindMessage) obj2);
        } else if (i == 3) {
            Object obj3 = msg.obj;
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type com.miui.libhandwritingpreview.DataBindPreHandler.BindMessage");
            doUnBind((BindMessage) obj3);
        } else if (i == 6) {
            Object obj4 = msg.obj;
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type com.miui.libhandwritingpreview.DataBindPreHandler.BindMessage");
            doUnBindEngineData((BindMessage) obj4);
        } else {
            if (i != 7) {
                return false;
            }
            Object obj5 = msg.obj;
            Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type java.lang.Runnable");
            doWaitIdleTask((Runnable) obj5);
        }
        return true;
    }

    private final void doUnBindEngineData(BindMessage bindMessage) {
        if (this.isDestroyed) {
            return;
        }
        Object lock = bindMessage.getItemData().lock;
        Intrinsics.checkNotNullExpressionValue(lock, "lock");
        synchronized (lock) {
            if (bindMessage.getItemData().hadLoad && !bindMessage.getItemData().serialized) {
                PageItemData itemData = bindMessage.getItemData();
                PagePreItemView itemView = bindMessage.getItemView();
                Intrinsics.checkNotNull(itemView);
                itemData.engineData = itemView.unBindEngineData();
                bindMessage.getItemData().serialized = false;
                doUnBind(bindMessage);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void doLoad(BindMessage bindMessage) {
        if (this.isDestroyed) {
            return;
        }
        if (!bindMessage.getItemData().hadLoad) {
            bindMessage.getItemData().hadLoad = false;
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            PagePreItemView itemView = bindMessage.getItemView();
            Intrinsics.checkNotNull(itemView);
            itemView.load(bindMessage.getItemData().filePath, bindMessage.getItemData(), bindMessage.getItemView(), new IDataListener() { // from class: com.miui.libhandwritingpreview.DataBindPreHandler$$ExternalSyntheticLambda0
                @Override // xc.engine.data.IDataListener
                public final void onDataLoaded(boolean z) {
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
                if (this.isDestroyed) {
                    return;
                }
                bindMessage.getItemData().hadLoad = true;
                PageItemData itemData = bindMessage.getItemData();
                PagePreItemView itemView2 = bindMessage.getItemView();
                Intrinsics.checkNotNull(itemView2);
                itemData.engineData = itemView2.unBindEngineData();
                bindMessage.getItemData().serialized = false;
                PagePreAdapter pagePreAdapter = this.adapter;
                PagePreItemView itemView3 = bindMessage.getItemView();
                Intrinsics.checkNotNull(itemView3);
                pagePreAdapter.onLoadFinish(itemView3, bindMessage.getItemData());
                return;
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        doBind(bindMessage);
    }

    private final void doBind(BindMessage bindMessage) {
        if (this.isDestroyed) {
            return;
        }
        Object lock = bindMessage.getItemData().lock;
        Intrinsics.checkNotNullExpressionValue(lock, "lock");
        synchronized (lock) {
            INSTANCE.bind(bindMessage.getItemData());
            Unit unit = Unit.INSTANCE;
        }
        PagePreItemView itemView = bindMessage.getItemView();
        Intrinsics.checkNotNull(itemView);
        Object tag = itemView.getTag();
        PagePreAdapter pagePreAdapter = this.adapter;
        PagePreItemView itemView2 = bindMessage.getItemView();
        Intrinsics.checkNotNull(itemView2);
        pagePreAdapter.onTaskFinish(itemView2, bindMessage.getItemData(), tag == bindMessage.getItemData() && bindMessage.getItemData().hadLoad);
    }

    private final void doUnBind(BindMessage bindMessage) {
        if (this.isDestroyed) {
            return;
        }
        Object lock = bindMessage.getItemData().lock;
        Intrinsics.checkNotNullExpressionValue(lock, "lock");
        synchronized (lock) {
            INSTANCE.unBind(bindMessage.getItemData());
            Unit unit = Unit.INSTANCE;
        }
        if ((bindMessage != null ? bindMessage.getItemView() : null) != null) {
            PagePreItemView itemView = bindMessage.getItemView();
            Intrinsics.checkNotNull(itemView);
            itemView.unBind();
        }
    }

    private final void doWaitIdleTask(Runnable runnable) {
        runnable.run();
        Log.i(TAG, "doWaitIdleTask: finish !");
    }

    /* JADX INFO: compiled from: DataBindPreHandler.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00038F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lcom/miui/libhandwritingpreview/DataBindPreHandler$BindMessage;", "", "itemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "itemData", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/view/PagePreItemView;Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;)V", "getItemData", "()Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "setItemData", "(Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;)V", "itemViewRef", "Ljava/lang/ref/WeakReference;", "getItemViewRef", "()Ljava/lang/ref/WeakReference;", "getItemView", "()Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class BindMessage {
        private PageItemData itemData;
        private final WeakReference<PagePreItemView> itemViewRef;

        public BindMessage(PagePreItemView pagePreItemView, PageItemData itemData) {
            Intrinsics.checkNotNullParameter(itemData, "itemData");
            this.itemData = itemData;
            this.itemViewRef = new WeakReference<>(pagePreItemView);
        }

        public final PageItemData getItemData() {
            return this.itemData;
        }

        public final void setItemData(PageItemData pageItemData) {
            Intrinsics.checkNotNullParameter(pageItemData, "<set-?>");
            this.itemData = pageItemData;
        }

        public final WeakReference<PagePreItemView> getItemViewRef() {
            return this.itemViewRef;
        }

        public final PagePreItemView getItemView() {
            return this.itemViewRef.get();
        }
    }

    /* JADX INFO: compiled from: DataBindPreHandler.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0012\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/miui/libhandwritingpreview/DataBindPreHandler$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "WHAT_LOAD", "", "WHAT_BIND", "WHAT_UNBIND", "WHAT_UNDO", "WHAT_REDO", "WHAT_UNBIND_ENGINE", "WHAT_WAIT_IDLE", "bind", "", "data", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "closeInputStream", "inputStream", "Ljava/io/FileInputStream;", "unBind", "closeOutputStream", "outputStream", "Ljava/io/FileOutputStream;", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void bind(PageItemData data) throws Throwable {
            Intrinsics.checkNotNullParameter(data, "data");
            if (!data.serialized) {
                return;
            }
            FileInputStream fileInputStream = null;
            try {
                try {
                    if (data.unbindDataPath == null) {
                        data.unbindDataPath = FileUtil.getUnbindDataPath();
                    }
                    FileInputStream fileInputStream2 = new FileInputStream(data.unbindDataPath);
                    try {
                        data.unSerialize(data.engineData.unSerialize(new DataInputStream(fileInputStream2), data.getPageIndex()));
                        data.serialized = false;
                        closeInputStream(fileInputStream2);
                    } catch (Exception e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        closeInputStream(fileInputStream);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        closeInputStream(fileInputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }

        private final void closeInputStream(FileInputStream inputStream) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public final void unBind(PageItemData data) throws Throwable {
            Intrinsics.checkNotNullParameter(data, "data");
            if (data.serialized) {
                return;
            }
            FileOutputStream fileOutputStream = null;
            try {
                if (data.unbindDataPath == null) {
                    data.unbindDataPath = FileUtil.getUnbindDataPath();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(data.unbindDataPath);
                try {
                    data.engineData.serialize(new DataOutputStream(fileOutputStream2), data.getPageIndex());
                    data.serialize();
                    data.serialized = true;
                    closeOutputStream(fileOutputStream2);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    try {
                        closeOutputStream(fileOutputStream);
                        throw th;
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            closeOutputStream(fileOutputStream);
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        private final void closeOutputStream(FileOutputStream outputStream) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("DataBindPreHandler", "getSimpleName(...)");
        TAG = "DataBindPreHandler";
    }
}
