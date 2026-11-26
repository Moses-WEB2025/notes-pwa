package com.miui.libhandwritingpage.view.listenerProxy;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpage.model.PageInkModel;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.callback.MathSelectCallback;

/* JADX INFO: compiled from: MathSelectCallbackProxy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\"\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00030\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/miui/libhandwritingpage/view/listenerProxy/MathSelectCallbackProxy;", "Lxc/engine/math/iink/callback/MathSelectCallback;", "pageInkModel", "Lcom/miui/libhandwritingpage/model/PageInkModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpage/model/PageInkModel;)V", "ref", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "onMathSelectRequired", "", "selectRect", "Landroid/graphics/RectF;", "curves", "", "Lxc/engine/data/pagedata/DataCurve;", "onExitSelect", "libHandWritingPage_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MathSelectCallbackProxy implements MathSelectCallback {
    private final WeakReference<PageInkModel> ref;

    public MathSelectCallbackProxy(PageInkModel pageInkModel) {
        Intrinsics.checkNotNullParameter(pageInkModel, "pageInkModel");
        this.ref = new WeakReference<>(pageInkModel);
    }

    @Override // xc.engine.math.iink.callback.MathSelectCallback
    public void onMathSelectRequired(RectF selectRect, List<DataCurve> curves) {
        PageInkModel pageInkModel = this.ref.get();
        if (pageInkModel != null) {
            pageInkModel.onMathSelectRequired(selectRect, curves);
        }
    }

    @Override // xc.engine.math.iink.callback.MathSelectCallback
    public void onExitSelect() {
        PageInkModel pageInkModel = this.ref.get();
        if (pageInkModel != null) {
            pageInkModel.onExitSelect();
        }
    }
}
