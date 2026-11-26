package kotlin.reflect.jvm.internal.impl.metadata.serialization;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder;

/* JADX INFO: compiled from: MutableTable.kt */
/* JADX INFO: loaded from: classes5.dex */
final class TableElementWrapper<Element extends GeneratedMessageLite.Builder<?, Element>> {
    private final Element builder;
    private final byte[] bytes;
    private final int hashCode;

    public TableElementWrapper(Element builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.builder = builder;
        byte[] byteArray = builder.build().toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        this.bytes = byteArray;
        this.hashCode = Arrays.hashCode(byteArray);
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        return (obj instanceof TableElementWrapper) && Arrays.equals(this.bytes, ((TableElementWrapper) obj).bytes);
    }
}
