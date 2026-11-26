package kotlin.reflect.jvm.internal.impl.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AttributeArrayOwner.kt */
/* JADX INFO: loaded from: classes5.dex */
public abstract class AttributeArrayOwner<K, T> extends AbstractArrayMapOwner<K, T> {
    private ArrayMap<T> arrayMap;

    protected AttributeArrayOwner(ArrayMap<T> arrayMap) {
        Intrinsics.checkNotNullParameter(arrayMap, "arrayMap");
        this.arrayMap = arrayMap;
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.AbstractArrayMapOwner
    protected final ArrayMap<T> getArrayMap() {
        return this.arrayMap;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AttributeArrayOwner() {
        EmptyArrayMap emptyArrayMap = EmptyArrayMap.INSTANCE;
        Intrinsics.checkNotNull(emptyArrayMap, "null cannot be cast to non-null type org.jetbrains.kotlin.util.ArrayMap<T of org.jetbrains.kotlin.util.AttributeArrayOwner>");
        this(emptyArrayMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.reflect.jvm.internal.impl.util.AbstractArrayMapOwner
    protected final void registerComponent(String keyQualifiedName, T value) {
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        Intrinsics.checkNotNullParameter(value, "value");
        int id = getTypeRegistry().getId(keyQualifiedName);
        int size = this.arrayMap.getSize();
        if (size == 0) {
            ArrayMap<T> arrayMap = this.arrayMap;
            if (!(arrayMap instanceof EmptyArrayMap)) {
                throw new IllegalStateException(buildDiagnosticMessage(arrayMap, 0, "EmptyArrayMap"));
            }
            this.arrayMap = new OneElementArrayMap(value, id);
            return;
        }
        if (size == 1) {
            ArrayMap<T> arrayMap2 = this.arrayMap;
            try {
                Intrinsics.checkNotNull(arrayMap2, "null cannot be cast to non-null type org.jetbrains.kotlin.util.OneElementArrayMap<T of org.jetbrains.kotlin.util.AttributeArrayOwner>");
                OneElementArrayMap oneElementArrayMap = (OneElementArrayMap) arrayMap2;
                if (oneElementArrayMap.getIndex() == id) {
                    this.arrayMap = new OneElementArrayMap(value, id);
                    return;
                } else {
                    ArrayMapImpl arrayMapImpl = new ArrayMapImpl();
                    arrayMapImpl.set(oneElementArrayMap.getIndex(), oneElementArrayMap.getValue());
                    this.arrayMap = arrayMapImpl;
                }
            } catch (ClassCastException e) {
                throw new IllegalStateException(buildDiagnosticMessage(arrayMap2, 1, "OneElementArrayMap"), e);
            }
        }
        this.arrayMap.set(id, value);
    }

    private final String buildDiagnosticMessage(ArrayMap<T> arrayMap, int i, String str) {
        T next;
        StringBuilder sb = new StringBuilder();
        sb.append("Race condition happened, the size of ArrayMap is " + i + " but it isn't an `" + str + '`').append('\n');
        sb.append("Type: " + arrayMap.getClass()).append('\n');
        StringBuilder sb2 = new StringBuilder("[\n");
        Map<String, Integer> mapAllValuesThreadUnsafeForRendering = getTypeRegistry().allValuesThreadUnsafeForRendering();
        ArrayMap<T> arrayMap2 = arrayMap;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayMap2, 10));
        int i2 = 0;
        for (T t : arrayMap2) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Iterator<T> it = mapAllValuesThreadUnsafeForRendering.entrySet().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (((Number) ((Map.Entry) next).getValue()).intValue() != i2);
            arrayList.add(sb2.append("  " + ((Map.Entry) next) + '[' + i2 + "]: " + t).append('\n'));
            i2 = i3;
        }
        sb2.append("]\n");
        sb.append("Content: " + sb2.toString()).append('\n');
        return sb.toString();
    }
}
