package okio;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Options.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u00162\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0016B!\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0011\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0011H\u0096\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0080\u0004¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0017"}, d2 = {"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "getTrie$okio", "()[I", "size", "", "getSize", "()I", CallMethod.METHOD_GET, "index", "Companion", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Options extends AbstractList<ByteString> implements RandomAccess {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    @JvmStatic
    public static final Options of(ByteString... byteStringArr) {
        return INSTANCE.of(byteStringArr);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Object) byteString);
    }

    /* JADX INFO: renamed from: getByteStrings$okio, reason: from getter */
    public final ByteString[] getByteStrings() {
        return this.byteStrings;
    }

    /* JADX INFO: renamed from: getTrie$okio, reason: from getter */
    public final int[] getTrie() {
        return this.trie;
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    /* JADX INFO: renamed from: getSize */
    public int get_size() {
        return this.byteStrings.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ByteString get(int index) {
        return this.byteStrings[index];
    }

    /* JADX INFO: compiled from: Options.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010 \n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J!\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\"\u00020\bH\u0007¢\u0006\u0002\u0010\tJT\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00112\b\b\u0002\u0010\u0014\u001a\u00020\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u0012H\u0002R\u0018\u0010\u0016\u001a\u00020\r*\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lokio/Options$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "of", "Lokio/Options;", "byteStrings", "", "Lokio/ByteString;", "([Lokio/ByteString;)Lokio/Options;", "buildTrieRecursive", "", "nodeOffset", "", "node", "Lokio/Buffer;", "byteStringOffset", "", "", "fromIndex", "toIndex", "indexes", "intCount", "getIntCount", "(Lokio/Buffer;)J", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Options of(ByteString... byteStrings) {
            Intrinsics.checkNotNullParameter(byteStrings, "byteStrings");
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (byteStrings.length == 0) {
                return new Options(new ByteString[0], new int[]{0, -1}, defaultConstructorMarker);
            }
            List mutableList = ArraysKt.toMutableList(byteStrings);
            CollectionsKt.sort(mutableList);
            int size = mutableList.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(-1);
            }
            ArrayList arrayList2 = arrayList;
            int length = byteStrings.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                arrayList2.set(CollectionsKt.binarySearch$default(mutableList, byteStrings[i2], 0, 0, 6, (Object) null), Integer.valueOf(i3));
                i2++;
                i3++;
            }
            if (((ByteString) mutableList.get(0)).size() <= 0) {
                throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
            }
            int i4 = 0;
            while (i4 < mutableList.size()) {
                ByteString byteString = (ByteString) mutableList.get(i4);
                int i5 = i4 + 1;
                int i6 = i5;
                while (i6 < mutableList.size()) {
                    ByteString byteString2 = (ByteString) mutableList.get(i6);
                    if (!byteString2.startsWith(byteString)) {
                        break;
                    }
                    if (byteString2.size() == byteString.size()) {
                        throw new IllegalArgumentException(("duplicate option: " + byteString2).toString());
                    }
                    if (((Number) arrayList2.get(i6)).intValue() > ((Number) arrayList2.get(i4)).intValue()) {
                        mutableList.remove(i6);
                        ((Number) arrayList2.remove(i6)).intValue();
                    } else {
                        i6++;
                    }
                }
                i4 = i5;
            }
            Buffer buffer = new Buffer();
            buildTrieRecursive$default(this, 0L, buffer, 0, mutableList, 0, 0, arrayList2, 53, null);
            int intCount = (int) getIntCount(buffer);
            int[] iArr = new int[intCount];
            for (int i7 = 0; i7 < intCount; i7++) {
                iArr[i7] = buffer.readInt();
            }
            Object[] objArrCopyOf = Arrays.copyOf(byteStrings, byteStrings.length);
            Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
            return new Options((ByteString[]) objArrCopyOf, iArr, defaultConstructorMarker);
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                j = 0;
            }
            companion.buildTrieRecursive(j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final void buildTrieRecursive(long nodeOffset, Buffer node, int byteStringOffset, List<? extends ByteString> byteStrings, int fromIndex, int toIndex, List<Integer> indexes) {
            int i;
            int i2;
            int i3;
            long j;
            int i4 = byteStringOffset;
            if (fromIndex >= toIndex) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            for (int i5 = fromIndex; i5 < toIndex; i5++) {
                if (byteStrings.get(i5).size() < i4) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            ByteString byteString = byteStrings.get(fromIndex);
            ByteString byteString2 = byteStrings.get(toIndex - 1);
            if (i4 == byteString.size()) {
                int iIntValue = indexes.get(fromIndex).intValue();
                int i6 = fromIndex + 1;
                ByteString byteString3 = byteStrings.get(i6);
                i = i6;
                i2 = iIntValue;
                byteString = byteString3;
            } else {
                i = fromIndex;
                i2 = -1;
            }
            if (byteString.getByte(i4) != byteString2.getByte(i4)) {
                int i7 = 1;
                for (int i8 = i + 1; i8 < toIndex; i8++) {
                    if (byteStrings.get(i8 - 1).getByte(i4) != byteStrings.get(i8).getByte(i4)) {
                        i7++;
                    }
                }
                long intCount = nodeOffset + getIntCount(node) + ((long) 2) + ((long) (i7 * 2));
                node.writeInt(i7);
                node.writeInt(i2);
                for (int i9 = i; i9 < toIndex; i9++) {
                    byte b = byteStrings.get(i9).getByte(i4);
                    if (i9 == i || b != byteStrings.get(i9 - 1).getByte(i4)) {
                        node.writeInt(b & 255);
                    }
                }
                Buffer buffer = new Buffer();
                while (i < toIndex) {
                    byte b2 = byteStrings.get(i).getByte(i4);
                    int i10 = i + 1;
                    int i11 = i10;
                    while (true) {
                        if (i11 >= toIndex) {
                            i3 = toIndex;
                            break;
                        } else {
                            if (b2 != byteStrings.get(i11).getByte(i4)) {
                                i3 = i11;
                                break;
                            }
                            i11++;
                        }
                    }
                    if (i10 == i3 && i4 + 1 == byteStrings.get(i).size()) {
                        node.writeInt(indexes.get(i).intValue());
                        j = intCount;
                    } else {
                        node.writeInt(((int) (getIntCount(buffer) + intCount)) * (-1));
                        j = intCount;
                        buildTrieRecursive(j, buffer, i4 + 1, byteStrings, i, i3, indexes);
                    }
                    intCount = j;
                    i = i3;
                }
                Long.valueOf(node.writeAll(buffer));
                return;
            }
            int iMin = Math.min(byteString.size(), byteString2.size());
            int i12 = 0;
            for (int i13 = i4; i13 < iMin && byteString.getByte(i13) == byteString2.getByte(i13); i13++) {
                i12++;
            }
            long intCount2 = nodeOffset + getIntCount(node) + ((long) 2) + ((long) i12) + 1;
            node.writeInt(-i12);
            node.writeInt(i2);
            int i14 = i4 + i12;
            while (i4 < i14) {
                node.writeInt(byteString.getByte(i4) & 255);
                i4++;
            }
            if (i + 1 == toIndex) {
                if (i14 != byteStrings.get(i).size()) {
                    throw new IllegalStateException("Check failed.");
                }
                node.writeInt(indexes.get(i).intValue());
            } else {
                Buffer buffer2 = new Buffer();
                node.writeInt(((int) (getIntCount(buffer2) + intCount2)) * (-1));
                buildTrieRecursive(intCount2, buffer2, i14, byteStrings, i, toIndex, indexes);
                Long.valueOf(node.writeAll(buffer2));
            }
        }

        private final long getIntCount(Buffer buffer) {
            return buffer.size() / ((long) 4);
        }
    }
}
