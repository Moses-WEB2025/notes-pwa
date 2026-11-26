package androidx.graphics.opengl.egl;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EGLConfigAttributes.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"EGLConfigAttributes", "Landroidx/graphics/opengl/egl/EGLConfigAttributes;", "block", "Lkotlin/Function1;", "Landroidx/graphics/opengl/egl/EGLConfigAttributes$Builder;", "", "Lkotlin/ExtensionFunctionType;", "libXCEngine_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class EGLConfigAttributesKt {
    public static final /* synthetic */ EGLConfigAttributes EGLConfigAttributes(Function1<? super EGLConfigAttributes.Builder, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        EGLConfigAttributes.Builder builder = new EGLConfigAttributes.Builder();
        block.invoke(builder);
        return builder.build();
    }
}
