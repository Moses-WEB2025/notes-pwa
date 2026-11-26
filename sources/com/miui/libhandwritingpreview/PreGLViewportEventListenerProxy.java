package com.miui.libhandwritingpreview;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.system.GLViewportEventListener;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PreGLViewportEventListenerProxy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\tH\u0016R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/miui/libhandwritingpreview/PreGLViewportEventListenerProxy;", "Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;", "listener", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/system/GLViewportEventListener;)V", "ref", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "onScrollBack", "", "onTopPageChanged", "pageIndex", "", "onTitleChanged", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreGLViewportEventListenerProxy implements GLViewportEventListener {
    private final WeakReference<GLViewportEventListener> ref;

    public PreGLViewportEventListenerProxy(GLViewportEventListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.ref = new WeakReference<>(listener);
    }

    @Override // com.miui.libhandwritingpreview.system.GLViewportEventListener
    public void onScrollBack() {
        GLViewportEventListener gLViewportEventListener = this.ref.get();
        if (gLViewportEventListener != null) {
            gLViewportEventListener.onScrollBack();
        }
    }

    @Override // com.miui.libhandwritingpreview.system.GLViewportEventListener
    public void onTopPageChanged(int pageIndex) {
        GLViewportEventListener gLViewportEventListener = this.ref.get();
        if (gLViewportEventListener != null) {
            gLViewportEventListener.onTopPageChanged(pageIndex);
        }
    }

    @Override // com.miui.libhandwritingpreview.system.GLViewportEventListener
    public void onTitleChanged() {
        GLViewportEventListener gLViewportEventListener = this.ref.get();
        if (gLViewportEventListener != null) {
            gLViewportEventListener.onTitleChanged();
        }
    }
}
