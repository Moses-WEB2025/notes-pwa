package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import androidx.collection.internal.RuntimeHelpersKt;
import com.miui.notes.preference.PreferenceProviderHelper;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: SieveCache.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0016\n\u0002\b\u001d\n\u0002\u0010\u0015\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0002Bà\u0001\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0003\u0010\u0006\u001a\u00020\u0005\u00128\b\u0002\u0010\u0007\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00118\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00050\b\u0012%\b\u0002\u0010\r\u001a\u001f\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0006\u0012\u0004\u0018\u00018\u00010\u000e\u0012d\b\u0002\u0010\u000f\u001a^\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00118\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0011\u0012\u0015\u0012\u0013\u0018\u00018\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u00150\u0010¢\u0006\u0002\u0010\u0016J\r\u00103\u001a\u00020\u0015H\u0000¢\u0006\u0002\b4J&\u00105\u001a\u00020\u00132\u0018\u00106\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00130\bH\u0086\bø\u0001\u0000J\u0006\u00107\u001a\u00020\u0013J&\u00107\u001a\u00020\u00132\u0018\u00106\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00130\bH\u0086\bø\u0001\u0000J\u0016\u00108\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u00109J\u0013\u0010:\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00028\u0000¢\u0006\u0002\u00109J\u0013\u0010;\u001a\u00020\u00132\u0006\u0010\f\u001a\u00028\u0001¢\u0006\u0002\u00109J\u0006\u0010\u001e\u001a\u00020\u0005J&\u0010\u001e\u001a\u00020\u00052\u0018\u00106\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00130\bH\u0086\bø\u0001\u0000J\r\u0010<\u001a\u00020\u0015H\u0000¢\u0006\u0002\b=J\u0013\u0010>\u001a\u00020\u00132\b\u0010?\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\u0006\u0010@\u001a\u00020\u0015J\b\u0010A\u001a\u00020\u0005H\u0002J\u0010\u0010B\u001a\u00020\u00052\u0006\u0010C\u001a\u00020\u0005H\u0002J\u0015\u0010D\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010EJ\u0015\u0010F\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010EJ\u0010\u0010G\u001a\u00020\u00152\u0006\u0010H\u001a\u00020IH\u0002J\u0010\u0010G\u001a\u00020\u00152\u0006\u0010H\u001a\u00020+H\u0002JD\u0010J\u001a\u00020\u001526\u0010K\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00118\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00150\bH\u0086\bø\u0001\u0000J/\u0010L\u001a\u00020\u00152!\u0010K\u001a\u001d\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(M\u0012\u0004\u0012\u00020\u00150\u000eH\u0081\bø\u0001\u0000J/\u0010N\u001a\u00020\u00152!\u0010K\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00150\u000eH\u0086\bø\u0001\u0000J/\u0010O\u001a\u00020\u00152!\u0010K\u001a\u001d\u0012\u0013\u0012\u00118\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00150\u000eH\u0086\bø\u0001\u0000J\u0018\u0010P\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000b\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010QJ\b\u0010R\u001a\u00020\u0005H\u0016J\b\u0010S\u001a\u00020\u0015H\u0002J\u0010\u0010T\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0005H\u0002J\u0010\u0010U\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\u0006\u0010V\u001a\u00020\u0013J\u0006\u0010W\u001a\u00020\u0013J\u0011\u0010X\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0005H\u0082\bJ\u0016\u0010Y\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00028\u0000H\u0086\n¢\u0006\u0002\u0010ZJ\u0017\u0010Y\u001a\u00020\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000[H\u0086\nJ\u0017\u0010Y\u001a\u00020\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\\H\u0086\nJ\u001e\u0010Y\u001a\u00020\u00152\u000e\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000&H\u0086\n¢\u0006\u0002\u0010]J\u0017\u0010Y\u001a\u00020\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000^H\u0086\nJ\u0017\u0010Y\u001a\u00020\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000_H\u0086\nJ\u0011\u0010`\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0005H\u0082\bJ\u0006\u0010a\u001a\u00020\u0013J\u001d\u0010b\u001a\u00020\u00152\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010dH\u0086\nJ\u001d\u0010b\u001a\u00020\u00152\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000H\u0086\nJ*\u0010b\u001a\u00020\u00152\u001a\u0010e\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010f0&H\u0086\n¢\u0006\u0002\u0010gJ\u001d\u0010b\u001a\u00020\u00152\u0012\u0010h\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010fH\u0086\nJ#\u0010b\u001a\u00020\u00152\u0018\u0010e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010f0^H\u0086\nJ\u001d\u0010b\u001a\u00020\u00152\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010iH\u0086\nJ#\u0010b\u001a\u00020\u00152\u0018\u0010e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010f0_H\u0086\nJ\u001d\u0010j\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00028\u0001¢\u0006\u0002\u0010kJ\u001a\u0010l\u001a\u00020\u00152\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010dJ\u001a\u0010l\u001a\u00020\u00152\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000J'\u0010l\u001a\u00020\u00152\u001a\u0010e\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010f0&¢\u0006\u0002\u0010gJ \u0010l\u001a\u00020\u00152\u0018\u0010e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010f0^J\u001a\u0010l\u001a\u00020\u00152\u0012\u0010c\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010iJ \u0010l\u001a\u00020\u00152\u0018\u0010e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010f0_J\u0015\u0010m\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000b\u001a\u00028\u0000¢\u0006\u0002\u0010QJ\u001b\u0010m\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00028\u0001¢\u0006\u0002\u0010nJ \u0010o\u001a\u00020\u00152\u0018\u00106\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00130\bJ\u0011\u0010p\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0005H\u0082\bJ\u0017\u0010q\u001a\u0004\u0018\u00018\u00012\u0006\u0010M\u001a\u00020\u0005H\u0002¢\u0006\u0002\u0010rJ\u0010\u0010s\u001a\u00020\u00152\b\b\u0001\u0010\u0004\u001a\u00020\u0005J\u0015\u0010t\u001a\u00020\u00152\u0006\u0010u\u001a\u00020\u0005H\u0000¢\u0006\u0002\bvJ\u001e\u0010w\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00028\u0001H\u0086\n¢\u0006\u0002\u0010xJ\b\u0010y\u001a\u00020zH\u0016J\u000e\u0010{\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0005R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u00058FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\u001dR+\u0010\r\u001a\u001f\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0006\u0012\u0004\u0018\u00018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020&8\u0000@\u0000X\u0081\u000e¢\u0006\n\n\u0002\u0010(\u0012\u0004\b'\u0010 R\u0011\u0010\u0004\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b)\u0010\u001dR\u0018\u0010*\u001a\u00020+8\u0000@\u0000X\u0081\u000e¢\u0006\b\n\u0000\u0012\u0004\b,\u0010 R\u000e\u0010-\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000Rj\u0010\u000f\u001a^\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00118\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0011\u0012\u0015\u0012\u0013\u0018\u00018\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u00150\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010.\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b/\u0010\u001dR>\u0010\u0007\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00118\u0001¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u00101\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020&8\u0000@\u0000X\u0081\u000e¢\u0006\n\n\u0002\u0010(\u0012\u0004\b2\u0010 \u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006|"}, d2 = {"Landroidx/collection/SieveCache;", "K", "", "V", "maxSize", "", "initialCapacity", "sizeOf", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "key", "value", "createValueFromKey", "Lkotlin/Function1;", "onEntryRemoved", "Lkotlin/Function4;", "oldValue", "newValue", "", "evicted", "", "(IILkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function4;)V", "_capacity", "_count", "_maxSize", "_size", "capacity", "getCapacity", "()I", "count", "getCount$annotations", "()V", "getCount", "growthLimit", "hand", "head", PreferenceProviderHelper.URI_PARAM_KEYS, "", "getKeys$annotations", "[Ljava/lang/Object;", "getMaxSize", "metadata", "", "getMetadata$annotations", "nodes", "size", "getSize", "tail", "values", "getValues$annotations", "adjustStorage", "adjustStorage$collection", "all", "predicate", "any", "contains", "(Ljava/lang/Object;)Z", "containsKey", "containsValue", "dropDeletes", "dropDeletes$collection", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "evictAll", "findEvictionCandidate", "findFirstAvailableSlot", "hash1", "findInsertIndex", "(Ljava/lang/Object;)I", "findKeyIndex", "fixupNodes", "mapping", "", "forEach", "block", "forEachIndexed", "index", "forEachKey", "forEachValue", CallMethod.METHOD_GET, "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "initializeGrowth", "initializeMetadata", "initializeStorage", "isEmpty", "isNotEmpty", "markNodeVisited", "minusAssign", "(Ljava/lang/Object;)V", "Landroidx/collection/ObjectList;", "Landroidx/collection/ScatterSet;", "([Ljava/lang/Object;)V", "", "Lkotlin/sequences/Sequence;", "moveNodeToHead", "none", "plusAssign", "from", "Landroidx/collection/ScatterMap;", "pairs", "Lkotlin/Pair;", "([Lkotlin/Pair;)V", "pair", "", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "remove", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "removeIf", "removeNode", "removeValueAt", "(I)Ljava/lang/Object;", "resize", "resizeStorage", "newCapacity", "resizeStorage$collection", "set", "(Ljava/lang/Object;Ljava/lang/Object;)V", "toString", "", "trimToSize", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SieveCache<K, V> {
    private int _capacity;
    private int _count;
    private int _maxSize;
    private int _size;
    private final Function1<K, V> createValueFromKey;
    private int growthLimit;
    private int hand;
    private int head;
    public Object[] keys;
    public long[] metadata;
    private long[] nodes;
    private final Function4<K, V, V, Boolean, Unit> onEntryRemoved;
    private final Function2<K, V, Integer> sizeOf;
    private int tail;
    public Object[] values;

    public static /* synthetic */ void getCount$annotations() {
    }

    public static /* synthetic */ void getKeys$annotations() {
    }

    public static /* synthetic */ void getMetadata$annotations() {
    }

    public static /* synthetic */ void getValues$annotations() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SieveCache(int i, int i2, Function2<? super K, ? super V, Integer> sizeOf, Function1<? super K, ? extends V> createValueFromKey, Function4<? super K, ? super V, ? super V, ? super Boolean, Unit> onEntryRemoved) {
        Intrinsics.checkNotNullParameter(sizeOf, "sizeOf");
        Intrinsics.checkNotNullParameter(createValueFromKey, "createValueFromKey");
        Intrinsics.checkNotNullParameter(onEntryRemoved, "onEntryRemoved");
        this.sizeOf = sizeOf;
        this.createValueFromKey = createValueFromKey;
        this.onEntryRemoved = onEntryRemoved;
        this.metadata = ScatterMapKt.EmptyGroup;
        this.keys = ContainerHelpersKt.EMPTY_OBJECTS;
        this.values = ContainerHelpersKt.EMPTY_OBJECTS;
        this.nodes = SieveCacheKt.getEmptyNodes();
        this.head = Integer.MAX_VALUE;
        this.tail = Integer.MAX_VALUE;
        this.hand = Integer.MAX_VALUE;
        if (!(i > 0)) {
            RuntimeHelpersKt.throwIllegalArgumentException("maxSize must be > 0");
        }
        this._maxSize = i;
        initializeStorage(ScatterMapKt.unloadedCapacity(i2));
    }

    public /* synthetic */ SieveCache(int i, int i2, AnonymousClass1 anonymousClass1, AnonymousClass2 anonymousClass2, AnonymousClass3 anonymousClass3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? 6 : i2, (i3 & 4) != 0 ? new Function2<K, V, Integer>() { // from class: androidx.collection.SieveCache.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function2
            public final Integer invoke(K k, V v) {
                Intrinsics.checkNotNullParameter(k, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(v, "<anonymous parameter 1>");
                return 1;
            }
        } : anonymousClass1, (i3 & 8) != 0 ? new Function1() { // from class: androidx.collection.SieveCache.2
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(K it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return null;
            }
        } : anonymousClass2, (i3 & 16) != 0 ? new Function4<K, V, V, Boolean, Unit>() { // from class: androidx.collection.SieveCache.3
            public final void invoke(K k, V v, V v2, boolean z) {
                Intrinsics.checkNotNullParameter(k, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(v, "<anonymous parameter 1>");
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Unit invoke(Object obj, Object obj2, Object obj3, Boolean bool) {
                invoke(obj, obj2, obj3, bool.booleanValue());
                return Unit.INSTANCE;
            }
        } : anonymousClass3);
    }

    /* JADX INFO: renamed from: getSize, reason: from getter */
    public final int get_size() {
        return this._size;
    }

    /* JADX INFO: renamed from: getMaxSize, reason: from getter */
    public final int get_maxSize() {
        return this._maxSize;
    }

    /* JADX INFO: renamed from: getCount, reason: from getter */
    public final int get_count() {
        return this._count;
    }

    /* JADX INFO: renamed from: getCapacity, reason: from getter */
    public final int get_capacity() {
        return this._capacity;
    }

    public final boolean any() {
        return this._count != 0;
    }

    public final boolean none() {
        return this._count == 0;
    }

    public final boolean isEmpty() {
        return this._count == 0;
    }

    public final boolean isNotEmpty() {
        return this._count != 0;
    }

    private final void initializeStorage(int initialCapacity) {
        long[] emptyNodes;
        int iMax = initialCapacity > 0 ? Math.max(7, ScatterMapKt.normalizeCapacity(initialCapacity)) : 0;
        this._capacity = iMax;
        initializeMetadata(iMax);
        this.keys = iMax == 0 ? ContainerHelpersKt.EMPTY_OBJECTS : new Object[iMax];
        this.values = iMax == 0 ? ContainerHelpersKt.EMPTY_OBJECTS : new Object[iMax];
        if (iMax == 0) {
            emptyNodes = SieveCacheKt.getEmptyNodes();
        } else {
            long[] jArr = new long[iMax];
            ArraysKt.fill$default(jArr, 4611686018427387903L, 0, 0, 6, (Object) null);
            emptyNodes = jArr;
        }
        this.nodes = emptyNodes;
    }

    private final void initializeMetadata(int capacity) {
        long[] jArr;
        if (capacity == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            long[] jArr2 = new long[((capacity + 15) & (-8)) >> 3];
            ArraysKt.fill$default(jArr2, -9187201950435737472L, 0, 0, 6, (Object) null);
            int i = capacity >> 3;
            long j = 255 << ((capacity & 7) << 3);
            jArr2[i] = (jArr2[i] & (~j)) | j;
            jArr = jArr2;
        }
        this.metadata = jArr;
        initializeGrowth();
    }

    private final void initializeGrowth() {
        this.growthLimit = ScatterMapKt.loadedCapacity(this._capacity) - get_count();
    }

    public final V get(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        int iFindKeyIndex = findKeyIndex(key);
        if (iFindKeyIndex < 0) {
            V vInvoke = this.createValueFromKey.invoke(key);
            if (vInvoke == null) {
                return null;
            }
            put(key, vInvoke);
            return vInvoke;
        }
        long[] jArr = this.nodes;
        jArr[iFindKeyIndex] = (jArr[iFindKeyIndex] & 4611686018427387903L) | 4611686018427387904L;
        return (V) this.values[iFindKeyIndex];
    }

    public final void set(K key, V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        put(key, value);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final V put(K key, V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        int iFindInsertIndex = findInsertIndex(key);
        if (iFindInsertIndex < 0) {
            iFindInsertIndex = ~iFindInsertIndex;
        }
        Object[] objArr = this.values;
        V v = (V) objArr[iFindInsertIndex];
        objArr[iFindInsertIndex] = value;
        this.keys[iFindInsertIndex] = key;
        int iIntValue = this._size + this.sizeOf.invoke(key, value).intValue();
        this._size = iIntValue;
        if (v != null) {
            this._size = iIntValue - ((Number) this.sizeOf.invoke(key, v)).intValue();
            this.onEntryRemoved.invoke(key, v, value, false);
            trimToSize(this._maxSize);
            return v;
        }
        trimToSize(this._maxSize);
        long[] jArr = this.nodes;
        int i = this.head;
        jArr[iFindInsertIndex] = (((long) i) & SieveCacheKt.NodeLinkMask) | 4611686016279904256L;
        if (i != Integer.MAX_VALUE) {
            jArr[i] = (jArr[i] & SieveCacheKt.NodeMetaAndNextMask) | ((SieveCacheKt.NodeLinkMask & ((long) iFindInsertIndex)) << 31);
        }
        this.head = iFindInsertIndex;
        if (this.tail == Integer.MAX_VALUE) {
            this.tail = iFindInsertIndex;
        }
        return v;
    }

    public final void putAll(Pair<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairs) {
            put(pair.component1(), pair.component2());
        }
    }

    public final void putAll(Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairs) {
            put(pair.component1(), pair.component2());
        }
    }

    public final void putAll(Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairs) {
            put(pair.component1(), pair.component2());
        }
    }

    public final void plusAssign(Pair<? extends K, ? extends V> pair) {
        Intrinsics.checkNotNullParameter(pair, "pair");
        put(pair.getFirst(), pair.getSecond());
    }

    public final void plusAssign(Pair<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        putAll(pairs);
    }

    public final void plusAssign(Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        putAll(pairs);
    }

    public final void plusAssign(Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        putAll(pairs);
    }

    public final void plusAssign(Map<K, ? extends V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        putAll(from);
    }

    public final void plusAssign(ScatterMap<K, V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        putAll(from);
    }

    public final void plusAssign(SieveCache<K, V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        putAll(from);
    }

    public final V remove(K key) {
        V vRemoveValueAt;
        Intrinsics.checkNotNullParameter(key, "key");
        int iFindKeyIndex = findKeyIndex(key);
        if (iFindKeyIndex < 0 || (vRemoveValueAt = removeValueAt(iFindKeyIndex)) == null) {
            return null;
        }
        this._size -= this.sizeOf.invoke(key, vRemoveValueAt).intValue();
        this.onEntryRemoved.invoke(key, vRemoveValueAt, null, false);
        return vRemoveValueAt;
    }

    public final boolean remove(K key, V value) {
        V vRemoveValueAt;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        int iFindKeyIndex = findKeyIndex(key);
        if (iFindKeyIndex < 0 || !Intrinsics.areEqual(this.values[iFindKeyIndex], value) || (vRemoveValueAt = removeValueAt(iFindKeyIndex)) == null) {
            return false;
        }
        this._size -= this.sizeOf.invoke(key, vRemoveValueAt).intValue();
        this.onEntryRemoved.invoke(key, vRemoveValueAt, null, false);
        return true;
    }

    public final void minusAssign(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        remove(key);
    }

    public final void minusAssign(K[] keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (K k : keys) {
            remove(k);
        }
    }

    public final void minusAssign(Iterable<? extends K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        Iterator<? extends K> it = keys.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    public final void minusAssign(Sequence<? extends K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        Iterator<? extends K> it = keys.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final void resize(int maxSize) {
        this._maxSize = maxSize;
        trimToSize(maxSize);
    }

    public final void trimToSize(int maxSize) {
        int iFindEvictionCandidate;
        while (this._size > maxSize && get_count() != 0 && (iFindEvictionCandidate = findEvictionCandidate()) != Integer.MAX_VALUE) {
            Object obj = this.keys[iFindEvictionCandidate];
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
            V vRemoveValueAt = removeValueAt(iFindEvictionCandidate);
            if (vRemoveValueAt != null) {
                this._size -= this.sizeOf.invoke((K) obj, vRemoveValueAt).intValue();
                this.onEntryRemoved.invoke((K) obj, vRemoveValueAt, null, true);
            }
        }
    }

    public final void forEach(Function2<? super K, ? super V, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj = objArr[i4];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        Object obj2 = objArr2[i4];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        block.invoke(obj, obj2);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void forEachKey(Function1<? super K, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Object[] objArr = this.keys;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        Object obj = objArr[(i << 3) + i3];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        block.invoke(obj);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void forEachValue(Function1<? super V, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Object[] objArr = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        Object obj = objArr[(i << 3) + i3];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        block.invoke(obj);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final int count() {
        return get_size();
    }

    public final boolean contains(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return findKeyIndex(key) >= 0;
    }

    public final boolean containsKey(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return findKeyIndex(key) >= 0;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x004f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:18:0x0051 A[LOOP:0: B:5:0x0011->B:18:0x0051, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:21:0x0054 A[SYNTHETIC] */
    public final boolean containsValue(V value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Object[] objArr = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            Object obj = objArr[(i << 3) + i3];
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                            if (Intrinsics.areEqual(value, obj)) {
                                return true;
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 == 8) {
                        if (i != length) {
                            i++;
                        }
                    }
                } else if (i != length) {
                    i++;
                }
            }
        }
        return false;
    }

    private final int findEvictionCandidate() {
        long[] jArr = this.nodes;
        int i = this.hand;
        if (i == Integer.MAX_VALUE) {
            i = this.tail;
        }
        while (i != Integer.MAX_VALUE) {
            long j = jArr[i];
            if (((int) ((j >> 62) & 1)) == 0) {
                break;
            }
            int i2 = (int) (SieveCacheKt.NodeLinkMask & (j >> 31));
            jArr[i] = 4611686018427387903L & j;
            i = i2 != Integer.MAX_VALUE ? i2 : this.tail;
        }
        int i3 = (int) (SieveCacheKt.NodeLinkMask & (jArr[i] >> 31));
        this.hand = i3 != Integer.MAX_VALUE ? i3 : Integer.MAX_VALUE;
        return i;
    }

    private final void moveNodeToHead(int index) {
        long[] jArr = this.nodes;
        int i = this.head;
        jArr[index] = (((long) i) & SieveCacheKt.NodeLinkMask) | 4611686016279904256L;
        if (i != Integer.MAX_VALUE) {
            jArr[i] = ((((long) index) & SieveCacheKt.NodeLinkMask) << 31) | (jArr[i] & SieveCacheKt.NodeMetaAndNextMask);
        }
        this.head = index;
        if (this.tail == Integer.MAX_VALUE) {
            this.tail = index;
        }
    }

    private final V removeValueAt(int index) {
        this._count--;
        long[] jArr = this.metadata;
        int i = this._capacity;
        int i2 = index >> 3;
        int i3 = (index & 7) << 3;
        long j = (jArr[i2] & (~(255 << i3))) | (254 << i3);
        jArr[i2] = j;
        jArr[(((index - 7) & i) + (i & 7)) >> 3] = j;
        this.keys[index] = null;
        Object[] objArr = this.values;
        V v = (V) objArr[index];
        objArr[index] = null;
        long[] jArr2 = this.nodes;
        long j2 = jArr2[index];
        int i4 = (int) ((j2 >> 31) & SieveCacheKt.NodeLinkMask);
        int i5 = (int) (j2 & SieveCacheKt.NodeLinkMask);
        if (i4 != Integer.MAX_VALUE) {
            jArr2[i4] = (jArr2[i4] & SieveCacheKt.NodeMetaAndPreviousMask) | (((long) i5) & SieveCacheKt.NodeLinkMask);
        } else {
            this.head = i5;
        }
        if (i5 != Integer.MAX_VALUE) {
            jArr2[i5] = ((((long) i4) & SieveCacheKt.NodeLinkMask) << 31) | (jArr2[i5] & SieveCacheKt.NodeMetaAndNextMask);
        } else {
            this.tail = i4;
        }
        if (this.hand == index) {
            this.hand = i4;
        }
        jArr2[index] = 4611686018427387903L;
        return v;
    }

    private final void removeNode(int index) {
        long[] jArr = this.nodes;
        long j = jArr[index];
        int i = (int) ((j >> 31) & SieveCacheKt.NodeLinkMask);
        int i2 = (int) (j & SieveCacheKt.NodeLinkMask);
        if (i == Integer.MAX_VALUE) {
            this.head = i2;
        } else {
            jArr[i] = (jArr[i] & SieveCacheKt.NodeMetaAndPreviousMask) | (((long) i2) & SieveCacheKt.NodeLinkMask);
        }
        if (i2 != Integer.MAX_VALUE) {
            jArr[i2] = ((((long) i) & SieveCacheKt.NodeLinkMask) << 31) | (jArr[i2] & SieveCacheKt.NodeMetaAndNextMask);
        } else {
            this.tail = i;
        }
        if (this.hand == index) {
            this.hand = i;
        }
        jArr[index] = 4611686018427387903L;
    }

    private final void markNodeVisited(int index) {
        long[] jArr = this.nodes;
        jArr[index] = (jArr[index] & 4611686018427387903L) | 4611686018427387904L;
    }

    private final int findFirstAvailableSlot(int hash1) {
        int i = this._capacity;
        int i2 = hash1 & i;
        int i3 = 0;
        while (true) {
            long[] jArr = this.metadata;
            int i4 = i2 >> 3;
            int i5 = (i2 & 7) << 3;
            long j = ((jArr[i4 + 1] << (64 - i5)) & ((-i5) >> 63)) | (jArr[i4] >>> i5);
            long j2 = j & ((~j) << 7) & (-9187201950435737472L);
            if (j2 != 0) {
                return (i2 + (Long.numberOfTrailingZeros(j2) >> 3)) & i;
            }
            i3 += 8;
            i2 = (i2 + i3) & i;
        }
    }

    public final void adjustStorage$collection() {
        if (this._capacity > 8 && Long.compareUnsigned(ULong.m2706constructorimpl(ULong.m2706constructorimpl(get_count()) * 32), ULong.m2706constructorimpl(ULong.m2706constructorimpl(this._capacity) * 25)) <= 0) {
            dropDeletes$collection();
        } else {
            resizeStorage$collection(ScatterMapKt.nextCapacity(this._capacity));
        }
    }

    public final void dropDeletes$collection() {
        Object[] objArr;
        long[] jArr = this.metadata;
        if (jArr == null) {
            return;
        }
        int i = this._capacity;
        Object[] objArr2 = this.keys;
        Object[] objArr3 = this.values;
        long[] jArr2 = this.nodes;
        long[] jArr3 = new long[i];
        long j = SieveCacheKt.InvalidMapping;
        int i2 = 0;
        ArraysKt.fill(jArr3, SieveCacheKt.InvalidMapping, 0, i);
        int i3 = (i + 7) >> 3;
        for (int i4 = 0; i4 < i3; i4++) {
            long j2 = jArr[i4] & (-9187201950435737472L);
            jArr[i4] = (-72340172838076674L) & ((~j2) + (j2 >>> 7));
        }
        int lastIndex = ArraysKt.getLastIndex(jArr);
        int i5 = lastIndex - 1;
        jArr[i5] = (jArr[i5] & 72057594037927935L) | (-72057594037927936L);
        jArr[lastIndex] = jArr[0];
        int i6 = 0;
        while (i6 != i) {
            int i7 = i6 >> 3;
            int i8 = (i6 & 7) << 3;
            long j3 = (jArr[i7] >> i8) & 255;
            if (j3 != 128 && j3 == 254) {
                Object obj = objArr2[i6];
                int iHashCode = (obj != null ? obj.hashCode() : i2) * ScatterMapKt.MurmurHashC1;
                int i9 = iHashCode ^ (iHashCode << 16);
                int i10 = i9 >>> 7;
                long j4 = j;
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i10);
                int i11 = i10 & i;
                if (((iFindFirstAvailableSlot - i11) & i) / 8 == ((i6 - i11) & i) / 8) {
                    int i12 = i2;
                    int i13 = i6;
                    jArr[i7] = (((long) (i9 & 127)) << i8) | ((~(255 << i8)) & jArr[i7]);
                    if (jArr3[i13] == j4) {
                        long j5 = i13;
                        jArr3[i13] = j5 | (j5 << 32);
                    }
                    jArr[jArr.length - 1] = jArr[i12];
                    i6 = i13 + 1;
                    j = j4;
                    i2 = i12;
                } else {
                    int i14 = i2;
                    int i15 = iFindFirstAvailableSlot >> 3;
                    long j6 = jArr[i15];
                    int i16 = (iFindFirstAvailableSlot & 7) << 3;
                    if (((j6 >> i16) & 255) == 128) {
                        objArr = objArr2;
                        jArr[i15] = (j6 & (~(255 << i16))) | (((long) (i9 & 127)) << i16);
                        jArr[i7] = (jArr[i7] & (~(255 << i8))) | (128 << i8);
                        objArr[iFindFirstAvailableSlot] = objArr[i6];
                        objArr[i6] = null;
                        objArr3[iFindFirstAvailableSlot] = objArr3[i6];
                        objArr3[i6] = null;
                        jArr2[iFindFirstAvailableSlot] = jArr2[i6];
                        jArr2[i6] = 4611686018427387903L;
                        int i17 = (int) ((jArr3[i6] >> 32) & InternalZipConstants.ZIP_64_SIZE_LIMIT);
                        if (i17 != Integer.MAX_VALUE) {
                            jArr3[i17] = (jArr3[i17] & (-4294967296L)) | ((long) iFindFirstAvailableSlot);
                            jArr3[i6] = (jArr3[i6] & InternalZipConstants.ZIP_64_SIZE_LIMIT) | (-4294967296L);
                        } else {
                            jArr3[i6] = (((long) Integer.MAX_VALUE) << 32) | ((long) iFindFirstAvailableSlot);
                        }
                        jArr3[iFindFirstAvailableSlot] = (((long) i6) << 32) | ((long) Integer.MAX_VALUE);
                    } else {
                        objArr = objArr2;
                        jArr[i15] = (((long) (i9 & 127)) << i16) | (j6 & (~(255 << i16)));
                        Object obj2 = objArr[iFindFirstAvailableSlot];
                        objArr[iFindFirstAvailableSlot] = objArr[i6];
                        objArr[i6] = obj2;
                        Object obj3 = objArr3[iFindFirstAvailableSlot];
                        objArr3[iFindFirstAvailableSlot] = objArr3[i6];
                        objArr3[i6] = obj3;
                        long j7 = jArr2[iFindFirstAvailableSlot];
                        jArr2[iFindFirstAvailableSlot] = jArr2[i6];
                        jArr2[i6] = j7;
                        int i18 = (int) ((jArr3[i6] >> 32) & InternalZipConstants.ZIP_64_SIZE_LIMIT);
                        if (i18 != Integer.MAX_VALUE) {
                            long j8 = iFindFirstAvailableSlot;
                            jArr3[i18] = (jArr3[i18] & (-4294967296L)) | j8;
                            jArr3[i6] = (jArr3[i6] & InternalZipConstants.ZIP_64_SIZE_LIMIT) | (j8 << 32);
                        } else {
                            long j9 = iFindFirstAvailableSlot;
                            jArr3[i6] = j9 | (j9 << 32);
                            i18 = i6;
                        }
                        jArr3[iFindFirstAvailableSlot] = (((long) i18) << 32) | ((long) i6);
                        i6--;
                    }
                    jArr[jArr.length - 1] = jArr[i14];
                    i6++;
                    i = i;
                    j = j4;
                    i2 = i14;
                    objArr2 = objArr;
                }
            } else {
                i6++;
            }
        }
        initializeGrowth();
        fixupNodes(jArr3);
    }

    public final void resizeStorage$collection(int newCapacity) {
        long[] jArr = this.metadata;
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr2 = this.nodes;
        int i = this._capacity;
        int[] iArr = new int[i];
        initializeStorage(newCapacity);
        long[] jArr3 = this.metadata;
        Object[] objArr3 = this.keys;
        Object[] objArr4 = this.values;
        long[] jArr4 = this.nodes;
        int i2 = this._capacity;
        int i3 = 0;
        while (i3 < i) {
            if (((jArr[i3 >> 3] >> ((i3 & 7) << 3)) & 255) < 128) {
                Object obj = objArr[i3];
                int iHashCode = (obj != null ? obj.hashCode() : 0) * ScatterMapKt.MurmurHashC1;
                int i4 = iHashCode ^ (iHashCode << 16);
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i4 >>> 7);
                long j = i4 & 127;
                int i5 = iFindFirstAvailableSlot >> 3;
                int i6 = (iFindFirstAvailableSlot & 7) << 3;
                long j2 = (jArr3[i5] & (~(255 << i6))) | (j << i6);
                jArr3[i5] = j2;
                jArr3[(((iFindFirstAvailableSlot - 7) & i2) + (i2 & 7)) >> 3] = j2;
                objArr3[iFindFirstAvailableSlot] = obj;
                objArr4[iFindFirstAvailableSlot] = objArr2[i3];
                jArr4[iFindFirstAvailableSlot] = jArr2[i3];
                iArr[i3] = iFindFirstAvailableSlot;
            }
            i3++;
            jArr = jArr;
            objArr = objArr;
        }
        fixupNodes(iArr);
    }

    private final void fixupNodes(long[] mapping) {
        long[] jArr = this.nodes;
        int length = jArr.length;
        int i = 0;
        while (true) {
            int i2 = Integer.MAX_VALUE;
            if (i >= length) {
                break;
            }
            long j = jArr[i];
            int i3 = (int) ((j >> 31) & SieveCacheKt.NodeLinkMask);
            int i4 = (int) (j & SieveCacheKt.NodeLinkMask);
            long j2 = ((j & SieveCacheKt.NodeMetaMask) | ((long) (i3 == Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) (mapping[i3] & InternalZipConstants.ZIP_64_SIZE_LIMIT)))) << 31;
            if (i4 != Integer.MAX_VALUE) {
                i2 = (int) (InternalZipConstants.ZIP_64_SIZE_LIMIT & mapping[i4]);
            }
            jArr[i] = ((long) i2) | j2;
            i++;
        }
        int i5 = this.head;
        if (i5 != Integer.MAX_VALUE) {
            this.head = (int) (mapping[i5] & InternalZipConstants.ZIP_64_SIZE_LIMIT);
        }
        int i6 = this.tail;
        if (i6 != Integer.MAX_VALUE) {
            this.tail = (int) (mapping[i6] & InternalZipConstants.ZIP_64_SIZE_LIMIT);
        }
        int i7 = this.hand;
        if (i7 != Integer.MAX_VALUE) {
            this.hand = (int) (mapping[i7] & InternalZipConstants.ZIP_64_SIZE_LIMIT);
        }
    }

    private final void fixupNodes(int[] mapping) {
        long[] jArr = this.nodes;
        int length = jArr.length;
        int i = 0;
        while (true) {
            int i2 = Integer.MAX_VALUE;
            if (i >= length) {
                break;
            }
            long j = jArr[i];
            int i3 = (int) ((j >> 31) & SieveCacheKt.NodeLinkMask);
            int i4 = (int) (j & SieveCacheKt.NodeLinkMask);
            long j2 = ((j & SieveCacheKt.NodeMetaMask) | ((long) (i3 == Integer.MAX_VALUE ? Integer.MAX_VALUE : mapping[i3]))) << 31;
            if (i4 != Integer.MAX_VALUE) {
                i2 = mapping[i4];
            }
            jArr[i] = j2 | ((long) i2);
            i++;
        }
        int i5 = this.head;
        if (i5 != Integer.MAX_VALUE) {
            this.head = mapping[i5];
        }
        int i6 = this.tail;
        if (i6 != Integer.MAX_VALUE) {
            this.tail = mapping[i6];
        }
        int i7 = this.hand;
        if (i7 != Integer.MAX_VALUE) {
            this.hand = mapping[i7];
        }
    }

    public final void forEachIndexed(Function1<? super Integer, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        block.invoke(Integer.valueOf((i << 3) + i3));
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0076 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:28:0x0078 A[LOOP:0: B:16:0x002d->B:28:0x0078, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:33:0x007b A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SieveCache)) {
            return false;
        }
        SieveCache sieveCache = (SieveCache) other;
        if (sieveCache.get_size() != get_size() || sieveCache._count != this._count) {
            return false;
        }
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                            Object obj2 = objArr2[i4];
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                            if (!Intrinsics.areEqual(obj2, sieveCache.get(obj))) {
                                return false;
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 == 8) {
                        if (i != length) {
                            i++;
                        }
                    }
                } else if (i != length) {
                    i++;
                }
            }
        }
        return true;
    }

    public String toString() {
        return "SieveCache[maxSize=" + this._maxSize + ", size=" + this._size + ", capacity=" + this._capacity + ", count=" + this._count + ']';
    }

    public final void putAll(Map<K, ? extends V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        for (Map.Entry<K, ? extends V> entry : from.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void putAll(ScatterMap<K, V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        Object[] objArr = from.keys;
        Object[] objArr2 = from.values;
        long[] jArr = from.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        put(objArr[i4], objArr2[i4]);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void putAll(SieveCache<K, V> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        Object[] objArr = from.keys;
        Object[] objArr2 = from.values;
        long[] jArr = from.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj = objArr[i4];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        Object obj2 = objArr2[i4];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        put(obj, obj2);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void removeIf(Function2<? super K, ? super V, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj = this.keys[i4];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        Object obj2 = this.values[i4];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        if (predicate.invoke(obj, obj2).booleanValue()) {
                            V vRemoveValueAt = removeValueAt(i4);
                            if (vRemoveValueAt == null) {
                                return;
                            }
                            this._size -= this.sizeOf.invoke((K) obj, vRemoveValueAt).intValue();
                            this.onEntryRemoved.invoke((K) obj, vRemoveValueAt, null, false);
                        } else {
                            continue;
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void minusAssign(ScatterSet<K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        Object[] objArr = keys.elements;
        long[] jArr = keys.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        remove(objArr[(i << 3) + i3]);
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void minusAssign(ObjectList<K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        Object[] objArr = keys.content;
        int i = keys._size;
        for (int i2 = 0; i2 < i; i2++) {
            remove(objArr[i2]);
        }
    }

    public final boolean all(Function2<? super K, ? super V, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return true;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj = objArr[i4];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        Object obj2 = objArr2[i4];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        if (!predicate.invoke(obj, obj2).booleanValue()) {
                            return false;
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return true;
                }
            }
            if (i == length) {
                return true;
            }
            i++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x005f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:18:0x0061 A[LOOP:0: B:5:0x0013->B:18:0x0061, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:21:0x0064 A[SYNTHETIC] */
    public final boolean any(Function2<? super K, ? super V, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                            Object obj2 = objArr2[i4];
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                            if (predicate.invoke(obj, obj2).booleanValue()) {
                                return true;
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 == 8) {
                        if (i != length) {
                            i++;
                        }
                    }
                } else if (i != length) {
                    i++;
                }
            }
        }
        return false;
    }

    public final int count(Function2<? super K, ? super V, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return 0;
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i3 = 8 - ((~(i - length)) >>> 31);
                for (int i4 = 0; i4 < i3; i4++) {
                    if ((255 & j) < 128) {
                        int i5 = (i << 3) + i4;
                        Object obj = objArr[i5];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        Object obj2 = objArr2[i5];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        if (predicate.invoke(obj, obj2).booleanValue()) {
                            i2++;
                        }
                    }
                    j >>= 8;
                }
                if (i3 != 8) {
                    return i2;
                }
            }
            if (i == length) {
                return i2;
            }
            i++;
        }
    }

    private final int findKeyIndex(K key) {
        int i = 0;
        int iHashCode = (key != null ? key.hashCode() : 0) * ScatterMapKt.MurmurHashC1;
        int i2 = iHashCode ^ (iHashCode << 16);
        int i3 = i2 & 127;
        int i4 = this._capacity;
        int i5 = i2 >>> 7;
        while (true) {
            int i6 = i5 & i4;
            long[] jArr = this.metadata;
            int i7 = i6 >> 3;
            int i8 = (i6 & 7) << 3;
            long j = ((jArr[i7 + 1] << (64 - i8)) & ((-i8) >> 63)) | (jArr[i7] >>> i8);
            long j2 = (((long) i3) * ScatterMapKt.BitmaskLsb) ^ j;
            for (long j3 = (~j2) & (j2 - ScatterMapKt.BitmaskLsb) & (-9187201950435737472L); j3 != 0; j3 &= j3 - 1) {
                int iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i6) & i4;
                if (Intrinsics.areEqual(this.keys[iNumberOfTrailingZeros], key)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((j & ((~j) << 6) & (-9187201950435737472L)) != 0) {
                return -1;
            }
            i += 8;
            i5 = i6 + i;
        }
    }

    private final int findInsertIndex(K key) {
        int iHashCode = (key != null ? key.hashCode() : 0) * ScatterMapKt.MurmurHashC1;
        int i = iHashCode ^ (iHashCode << 16);
        int i2 = i >>> 7;
        int i3 = i & 127;
        int i4 = this._capacity;
        int i5 = i2 & i4;
        int i6 = 0;
        while (true) {
            long[] jArr = this.metadata;
            int i7 = i5 >> 3;
            int i8 = (i5 & 7) << 3;
            long j = ((jArr[i7 + 1] << (64 - i8)) & ((-i8) >> 63)) | (jArr[i7] >>> i8);
            long j2 = i3;
            int i9 = i3;
            long j3 = j ^ (j2 * ScatterMapKt.BitmaskLsb);
            for (long j4 = (~j3) & (j3 - ScatterMapKt.BitmaskLsb) & (-9187201950435737472L); j4 != 0; j4 &= j4 - 1) {
                int iNumberOfTrailingZeros = (i5 + (Long.numberOfTrailingZeros(j4) >> 3)) & i4;
                if (Intrinsics.areEqual(this.keys[iNumberOfTrailingZeros], key)) {
                    return iNumberOfTrailingZeros;
                }
            }
            if ((((~j) << 6) & j & (-9187201950435737472L)) != 0) {
                int iFindFirstAvailableSlot = findFirstAvailableSlot(i2);
                if (this.growthLimit == 0 && ((this.metadata[iFindFirstAvailableSlot >> 3] >> ((iFindFirstAvailableSlot & 7) << 3)) & 255) != 254) {
                    adjustStorage$collection();
                    iFindFirstAvailableSlot = findFirstAvailableSlot(i2);
                }
                this._count++;
                int i10 = this.growthLimit;
                long[] jArr2 = this.metadata;
                int i11 = iFindFirstAvailableSlot >> 3;
                long j5 = jArr2[i11];
                int i12 = (iFindFirstAvailableSlot & 7) << 3;
                this.growthLimit = i10 - (((j5 >> i12) & 255) == 128 ? 1 : 0);
                int i13 = this._capacity;
                long j6 = ((~(255 << i12)) & j5) | (j2 << i12);
                jArr2[i11] = j6;
                jArr2[(((iFindFirstAvailableSlot - 7) & i13) + (i13 & 7)) >> 3] = j6;
                return ~iFindFirstAvailableSlot;
            }
            i6 += 8;
            i5 = (i5 + i6) & i4;
            i3 = i9;
        }
    }

    public int hashCode() {
        Object[] objArr = this.keys;
        Object[] objArr2 = this.values;
        long[] jArr = this.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return 0;
        }
        int i = 0;
        int iHashCode = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        int i4 = (i << 3) + i3;
                        Object obj = objArr[i4];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type K of androidx.collection.SieveCache");
                        Object obj2 = objArr2[i4];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type V of androidx.collection.SieveCache");
                        iHashCode += obj2.hashCode() ^ obj.hashCode();
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return iHashCode;
                }
            }
            if (i == length) {
                return iHashCode;
            }
            i++;
        }
    }
}
