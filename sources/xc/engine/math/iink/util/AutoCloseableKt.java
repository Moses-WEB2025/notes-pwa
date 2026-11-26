package xc.engine.math.iink.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: autoCloseable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001aX\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0010\b\u0000\u0010\u0002*\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u0001H\u00022'\b\u0002\u0010\u0006\u001a!\u0012\u0015\u0012\u0013\u0018\u0001H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0007¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"autoCloseable", "Lxc/engine/math/iink/util/AutoCloseableDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "initialValue", "onUpdate", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "oldValue", "", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Lxc/engine/math/iink/util/AutoCloseableDelegate;", "libXCEngine_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class AutoCloseableKt {
    public static /* synthetic */ AutoCloseableDelegate autoCloseable$default(AutoCloseable autoCloseable, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            autoCloseable = null;
        }
        if ((i & 2) != 0) {
            function1 = null;
        }
        return autoCloseable(autoCloseable, function1);
    }

    public static final <T extends AutoCloseable> AutoCloseableDelegate<T> autoCloseable(T t, Function1<? super T, Unit> function1) {
        return new AutoCloseableDelegate<>(t, function1);
    }
}
