package okio;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TypedOptions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 \u0017*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00060\u0004j\u0002`\u0005:\u0001\u0017B\u001d\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0016\u0010\u0014\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00020\u0011H\u0096\u0002¢\u0006\u0002\u0010\u0016R\u0014\u0010\b\u001a\u00020\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0018"}, d2 = {"Lokio/TypedOptions;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "list", "", "options", "Lokio/Options;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/util/List;Lokio/Options;)V", "getOptions$okio", "()Lokio/Options;", "getList$okio", "()Ljava/util/List;", "size", "", "getSize", "()I", CallMethod.METHOD_GET, "index", "(I)Ljava/lang/Object;", "Companion", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class TypedOptions<T> extends AbstractList<T> implements RandomAccess {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<T> list;
    private final Options options;

    @JvmStatic
    public static final <T> TypedOptions<T> of(Iterable<? extends T> iterable, Function1<? super T, ? extends ByteString> function1) {
        return INSTANCE.of(iterable, function1);
    }

    /* JADX INFO: renamed from: getOptions$okio, reason: from getter */
    public final Options getOptions() {
        return this.options;
    }

    public TypedOptions(List<? extends T> list, Options options) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        List<T> list2 = CollectionsKt.toList(list);
        this.list = list2;
        if (list2.size() != options.size()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public final List<T> getList$okio() {
        return this.list;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    /* JADX INFO: renamed from: getSize */
    public int get_size() {
        return this.list.size();
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public T get(int index) {
        return this.list.get(index);
    }

    /* JADX INFO: compiled from: TypedOptions.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J>\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\b\b\u0001\u0010\u0006*\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u000b0\nH\u0087\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\f"}, d2 = {"Lokio/TypedOptions$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "of", "Lokio/TypedOptions;", ExifInterface.GPS_DIRECTION_TRUE, "values", "", "encode", "Lkotlin/Function1;", "Lokio/ByteString;", "okio"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final <T> TypedOptions<T> of(Iterable<? extends T> values, Function1<? super T, ? extends ByteString> encode) {
            Intrinsics.checkNotNullParameter(values, "values");
            Intrinsics.checkNotNullParameter(encode, "encode");
            List list = CollectionsKt.toList(values);
            Options.Companion companion = Options.INSTANCE;
            int size = list.size();
            ByteString[] byteStringArr = new ByteString[size];
            for (int i = 0; i < size; i++) {
                byteStringArr[i] = encode.invoke((Object) list.get(i));
            }
            return new TypedOptions<>(list, companion.of(byteStringArr));
        }
    }
}
