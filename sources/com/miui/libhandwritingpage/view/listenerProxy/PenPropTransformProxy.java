package com.miui.libhandwritingpage.view.listenerProxy;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.IPenPropTransform;

/* JADX INFO: compiled from: PenPropTransformProxy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/miui/libhandwritingpage/view/listenerProxy/PenPropTransformProxy;", "Lxc/engine/data/IPenPropTransform;", "listener", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/data/IPenPropTransform;)V", "ref", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "transformColor", "", IntegerTokenConverter.CONVERTER_KEY, "libHandWritingPage_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PenPropTransformProxy implements IPenPropTransform {
    private final WeakReference<IPenPropTransform> ref;

    public PenPropTransformProxy(IPenPropTransform listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.ref = new WeakReference<>(listener);
    }

    @Override // xc.engine.data.IPenPropTransform
    public int transformColor(int i) {
        IPenPropTransform iPenPropTransform = this.ref.get();
        return iPenPropTransform != null ? iPenPropTransform.transformColor(i) : i;
    }
}
