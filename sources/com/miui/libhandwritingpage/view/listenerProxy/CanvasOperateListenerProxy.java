package com.miui.libhandwritingpage.view.listenerProxy;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.operate.canvas.ICanvasOperateListener;

/* JADX INFO: compiled from: CanvasOperateListenerProxy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\"\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/libhandwritingpage/view/listenerProxy/CanvasOperateListenerProxy;", "Lxc/engine/operate/canvas/ICanvasOperateListener;", "listener", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/operate/canvas/ICanvasOperateListener;)V", "ref", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "onCanvasDataChanged", "", "rectF", "Landroid/graphics/RectF;", "canvasChangedType", "", "pageIndex", "libHandWritingPage_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CanvasOperateListenerProxy implements ICanvasOperateListener {
    private final WeakReference<ICanvasOperateListener> ref;

    public CanvasOperateListenerProxy(ICanvasOperateListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.ref = new WeakReference<>(listener);
    }

    @Override // xc.engine.operate.canvas.ICanvasOperateListener
    public void onCanvasDataChanged(RectF rectF, int canvasChangedType) {
        ICanvasOperateListener iCanvasOperateListener = this.ref.get();
        if (iCanvasOperateListener != null) {
            iCanvasOperateListener.onCanvasDataChanged(rectF, canvasChangedType);
        }
    }

    @Override // xc.engine.operate.canvas.ICanvasOperateListener
    public void onCanvasDataChanged(RectF rectF, int canvasChangedType, int pageIndex) {
        ICanvasOperateListener iCanvasOperateListener = this.ref.get();
        if (iCanvasOperateListener != null) {
            iCanvasOperateListener.onCanvasDataChanged(rectF, canvasChangedType, pageIndex);
        }
    }
}
