package xc.engine.core.graphics.engine.systems;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.math.iink.bean.BoundingBox;

/* JADX INFO: compiled from: MathSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010'\n\u0000*\u0001\u0000\b\n\u0018\u00002\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\bH\u0014¨\u0006\t"}, d2 = {"xc/engine/core/graphics/engine/systems/MathSystem$outBoundBoxCheck$1", "Ljava/util/LinkedHashMap;", "Lxc/engine/math/iink/bean/BoundingBox;", "", "Lkotlin/collections/LinkedHashMap;", "removeEldestEntry", "", "eldest", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MathSystem$outBoundBoxCheck$1 extends LinkedHashMap<BoundingBox, Integer> {
    MathSystem$outBoundBoxCheck$1() {
        super(100, 0.75f, true);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof BoundingBox) {
            return containsKey((BoundingBox) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsKey(BoundingBox boundingBox) {
        return super.containsKey((Object) boundingBox);
    }

    public /* bridge */ boolean containsValue(Integer num) {
        return super.containsValue((Object) num);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof Integer) {
            return containsValue((Integer) obj);
        }
        return false;
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set<Map.Entry<BoundingBox, Integer>> entrySet() {
        return getEntries();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Integer get(Object obj) {
        if (obj instanceof BoundingBox) {
            return get((BoundingBox) obj);
        }
        return null;
    }

    public /* bridge */ Integer get(BoundingBox boundingBox) {
        return (Integer) super.get((Object) boundingBox);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object get(Object obj) {
        if (obj instanceof BoundingBox) {
            return get((BoundingBox) obj);
        }
        return null;
    }

    public /* bridge */ Set<Map.Entry<BoundingBox, Integer>> getEntries() {
        return super.entrySet();
    }

    public /* bridge */ Set<BoundingBox> getKeys() {
        return super.keySet();
    }

    public final /* bridge */ Integer getOrDefault(Object obj, Integer num) {
        return !(obj instanceof BoundingBox) ? num : getOrDefault((BoundingBox) obj, num);
    }

    public /* bridge */ Integer getOrDefault(BoundingBox boundingBox, Integer num) {
        return (Integer) super.getOrDefault((Object) boundingBox, num);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof BoundingBox) ? obj2 : getOrDefault((BoundingBox) obj, (Integer) obj2);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    public /* bridge */ Collection<Integer> getValues() {
        return super.values();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set<BoundingBox> keySet() {
        return getKeys();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Integer remove(Object obj) {
        if (obj instanceof BoundingBox) {
            return remove((BoundingBox) obj);
        }
        return null;
    }

    public /* bridge */ Integer remove(BoundingBox boundingBox) {
        return (Integer) super.remove((Object) boundingBox);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object remove(Object obj) {
        if (obj instanceof BoundingBox) {
            return remove((BoundingBox) obj);
        }
        return null;
    }

    @Override // java.util.HashMap, java.util.Map
    public final /* bridge */ boolean remove(Object obj, Object obj2) {
        if ((obj instanceof BoundingBox) && (obj2 instanceof Integer)) {
            return remove((BoundingBox) obj, (Integer) obj2);
        }
        return false;
    }

    public /* bridge */ boolean remove(BoundingBox boundingBox, Integer num) {
        return super.remove((Object) boundingBox, (Object) num);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Collection<Integer> values() {
        return getValues();
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<BoundingBox, Integer> eldest) {
        Intrinsics.checkNotNullParameter(eldest, "eldest");
        return size() > 100;
    }
}
