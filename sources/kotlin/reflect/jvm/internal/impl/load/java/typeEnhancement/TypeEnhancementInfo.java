package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class TypeEnhancementInfo {
    private final Map<Integer, JavaTypeQualifiers> map;

    public TypeEnhancementInfo(Map<Integer, JavaTypeQualifiers> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    public final Map<Integer, JavaTypeQualifiers> getMap() {
        return this.map;
    }

    public final TypeEnhancementInfo copyForWarnings() {
        Map<Integer, JavaTypeQualifiers> map = this.map;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), JavaTypeQualifiers.copy$default((JavaTypeQualifiers) entry.getValue(), null, null, false, true, 7, null));
        }
        return new TypeEnhancementInfo(linkedHashMap);
    }
}
