package xc.engine.math.iink.util;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.page.PhoneNoteListPageFragment$$ExternalSyntheticBackport0;
import java.lang.AutoCloseable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: autoCloseable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\u0010\b\u0000\u0010\u0001*\n\u0018\u00010\u0002j\u0004\u0018\u0001`\u00032\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u0004B:\u0012\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000\u0012'\b\u0002\u0010\u0007\u001a!\u0012\u0015\u0012\u0013\u0018\u00018\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\b¢\u0006\u0004\b\r\u0010\u000eJ$\u0010\u0011\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0012\u001a\u00020\u00052\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0096\u0002¢\u0006\u0002\u0010\u0015J,\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00052\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\b\u0010\u000f\u001a\u0004\u0018\u00018\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0017R-\u0010\u0007\u001a!\u0012\u0015\u0012\u0013\u0018\u00018\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0010¨\u0006\u0018"}, d2 = {"Lxc/engine/math/iink/util/AutoCloseableDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/lang/AutoCloseable;", "Lkotlin/AutoCloseable;", "Lkotlin/properties/ReadWriteProperty;", "", "initialValue", "onUpdate", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "oldValue", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)V", "value", "Ljava/lang/AutoCloseable;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/AutoCloseable;", "setValue", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/AutoCloseable;)V", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AutoCloseableDelegate<T extends AutoCloseable> implements ReadWriteProperty<Object, T> {
    private final Function1<T, Unit> onUpdate;
    private T value;

    /* JADX WARN: Multi-variable type inference failed */
    public AutoCloseableDelegate(T t, Function1<? super T, Unit> function1) {
        this.onUpdate = function1;
        this.value = t;
    }

    public /* synthetic */ AutoCloseableDelegate(AutoCloseable autoCloseable, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(autoCloseable, (i & 2) != 0 ? null : function1);
    }

    @Override // kotlin.properties.ReadWriteProperty, kotlin.properties.ReadOnlyProperty
    public /* bridge */ /* synthetic */ Object getValue(Object obj, KProperty kProperty) {
        return getValue(obj, (KProperty<?>) kProperty);
    }

    @Override // kotlin.properties.ReadWriteProperty
    public /* bridge */ /* synthetic */ void setValue(Object obj, KProperty kProperty, Object obj2) throws Exception {
        setValue(obj, (KProperty<?>) kProperty, (AutoCloseable) obj2);
    }

    @Override // kotlin.properties.ReadWriteProperty, kotlin.properties.ReadOnlyProperty
    public T getValue(Object thisRef, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(property, "property");
        return this.value;
    }

    public void setValue(Object thisRef, KProperty<?> property, T value) throws Exception {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(property, "property");
        T t = this.value;
        if (t == value) {
            return;
        }
        try {
            this.value = value;
            Function1<T, Unit> function1 = this.onUpdate;
            if (function1 != null) {
                function1.invoke(t);
            }
        } finally {
            if (t != null) {
                PhoneNoteListPageFragment$$ExternalSyntheticBackport0.m(t);
            }
        }
    }
}
