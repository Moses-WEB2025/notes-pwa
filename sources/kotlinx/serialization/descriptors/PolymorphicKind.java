package kotlinx.serialization.descriptors;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.ExperimentalSerializationApi;

/* JADX INFO: compiled from: SerialKinds.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0002\u0004\u0005B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lkotlinx/serialization/descriptors/PolymorphicKind;", "Lkotlinx/serialization/descriptors/SerialKind;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "SEALED", "OPEN", "Lkotlinx/serialization/descriptors/PolymorphicKind$OPEN;", "Lkotlinx/serialization/descriptors/PolymorphicKind$SEALED;", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
@ExperimentalSerializationApi
public abstract class PolymorphicKind extends SerialKind {
    public /* synthetic */ PolymorphicKind(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PolymorphicKind() {
        super(null);
    }

    /* JADX INFO: compiled from: SerialKinds.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lkotlinx/serialization/descriptors/PolymorphicKind$SEALED;", "Lkotlinx/serialization/descriptors/PolymorphicKind;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class SEALED extends PolymorphicKind {
        public static final SEALED INSTANCE = new SEALED();

        private SEALED() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: SerialKinds.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lkotlinx/serialization/descriptors/PolymorphicKind$OPEN;", "Lkotlinx/serialization/descriptors/PolymorphicKind;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class OPEN extends PolymorphicKind {
        public static final OPEN INSTANCE = new OPEN();

        private OPEN() {
            super(null);
        }
    }
}
