package com.miui.libhandwritingpage.view.listenerProxy;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.CsBufferBean;
import xc.engine.data.OnExtraDataListener;

/* JADX INFO: compiled from: OnExtraDataListenerProxy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0017\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J2\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000b2\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000e\u001a\u00020\u000bH\u0016R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/miui/libhandwritingpage/view/listenerProxy/OnExtraDataListenerProxy;", "Lxc/engine/data/OnExtraDataListener;", "listenerProxy", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/data/OnExtraDataListener;)V", "ref", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "onLoadListener", "", IntegerTokenConverter.CONVERTER_KEY, "", "dataTypeArr", "", "pageIndex", "list", "", "", "onSaveExtraData", "Lxc/engine/data/CsBufferBean;", "libHandWritingPage_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class OnExtraDataListenerProxy implements OnExtraDataListener {
    private final WeakReference<OnExtraDataListener> ref;

    public OnExtraDataListenerProxy(OnExtraDataListener listenerProxy) {
        Intrinsics.checkNotNullParameter(listenerProxy, "listenerProxy");
        this.ref = new WeakReference<>(listenerProxy);
    }

    @Override // xc.engine.data.OnExtraDataListener
    public void onLoadListener(int i, short[] dataTypeArr, int pageIndex, List<byte[]> list) {
        OnExtraDataListener onExtraDataListener = this.ref.get();
        if (onExtraDataListener != null) {
            onExtraDataListener.onLoadListener(i, dataTypeArr, pageIndex, list);
        }
    }

    @Override // xc.engine.data.OnExtraDataListener
    public CsBufferBean onSaveExtraData(int pageIndex) {
        OnExtraDataListener onExtraDataListener = this.ref.get();
        if (onExtraDataListener != null) {
            return onExtraDataListener.onSaveExtraData(pageIndex);
        }
        return null;
    }
}
