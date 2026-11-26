package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.reflect.Constructor;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReflectJavaClass.kt */
/* JADX INFO: loaded from: classes5.dex */
final /* synthetic */ class ReflectJavaClass$constructors$2 extends FunctionReferenceImpl implements Function1<Constructor<?>, ReflectJavaConstructor> {
    public static final ReflectJavaClass$constructors$2 INSTANCE = new ReflectJavaClass$constructors$2();

    ReflectJavaClass$constructors$2() {
        super(1, ReflectJavaConstructor.class, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "<init>(Ljava/lang/reflect/Constructor;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final ReflectJavaConstructor invoke(Constructor<?> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new ReflectJavaConstructor(p0);
    }
}
