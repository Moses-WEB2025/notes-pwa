package okhttp3;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.text.StringsKt;
import okhttp3.internal._HeadersCommonKt;
import okhttp3.internal.http.DateFormattingKt;

/* JADX INFO: compiled from: Headers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\u0018\u0000 *2\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001:\u0002)*B\u0017\b\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\u0003H\u0086\u0002J\u0010\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\f\u001a\u00020\u0003J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\f\u001a\u00020\u0003H\u0007J\r\u0010\u0011\u001a\u00020\u0012H\u0007¢\u0006\u0002\b\u0014J\u000e\u0010\f\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0012J\u000e\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0012J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u001a2\u0006\u0010\f\u001a\u00020\u0003J\u0006\u0010\u001b\u001a\u00020\u001cJ\u001b\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u001eH\u0096\u0002J\u0006\u0010\u001f\u001a\u00020 J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0096\u0002J\b\u0010%\u001a\u00020\u0012H\u0016J\b\u0010&\u001a\u00020\u0003H\u0016J\u0018\u0010'\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u001a0(R\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0080\u0004¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0011\u001a\u00020\u00128G¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013¨\u0006+"}, d2 = {"Lokhttp3/Headers;", "", "Lkotlin/Pair;", "", "namesAndValues", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "([Ljava/lang/String;)V", "getNamesAndValues$okhttp", "()[Ljava/lang/String;", "[Ljava/lang/String;", CallMethod.METHOD_GET, "name", "getDate", "Ljava/util/Date;", "getInstant", "Ljava/time/Instant;", "size", "", "()I", "-deprecated_size", "index", "value", "names", "", "values", "", "byteCount", "", "iterator", "", "newBuilder", "Lokhttp3/Headers$Builder;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "toString", "toMultimap", "", "Builder", "Companion", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Headers implements Iterable<Pair<? extends String, ? extends String>>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Headers EMPTY = new Headers(new String[0]);
    private final String[] namesAndValues;

    @JvmStatic
    public static final Headers of(Map<String, String> map) {
        return INSTANCE.of(map);
    }

    @JvmStatic
    public static final Headers of(String... strArr) {
        return INSTANCE.of(strArr);
    }

    public Headers(String[] namesAndValues) {
        Intrinsics.checkNotNullParameter(namesAndValues, "namesAndValues");
        this.namesAndValues = namesAndValues;
    }

    /* JADX INFO: renamed from: getNamesAndValues$okhttp, reason: from getter */
    public final String[] getNamesAndValues() {
        return this.namesAndValues;
    }

    public final String get(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return _HeadersCommonKt.commonHeadersGet(this.namesAndValues, name);
    }

    public final Date getDate(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String str = get(name);
        if (str != null) {
            return DateFormattingKt.toHttpDateOrNull(str);
        }
        return null;
    }

    public final Instant getInstant(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        Date date = getDate(name);
        if (date != null) {
            return date.toInstant();
        }
        return null;
    }

    public final int size() {
        return this.namesAndValues.length / 2;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = {}))
    /* JADX INFO: renamed from: -deprecated_size, reason: not valid java name */
    public final int m4418deprecated_size() {
        return size();
    }

    public final String name(int index) {
        return _HeadersCommonKt.commonName(this, index);
    }

    public final String value(int index) {
        return _HeadersCommonKt.commonValue(this, index);
    }

    public final Set<String> names() {
        TreeSet treeSet = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        int size = size();
        for (int i = 0; i < size; i++) {
            treeSet.add(name(i));
        }
        Set<String> setUnmodifiableSet = Collections.unmodifiableSet(treeSet);
        Intrinsics.checkNotNullExpressionValue(setUnmodifiableSet, "unmodifiableSet(...)");
        return setUnmodifiableSet;
    }

    public final List<String> values(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return _HeadersCommonKt.commonValues(this, name);
    }

    public final long byteCount() {
        String[] strArr = this.namesAndValues;
        long length = strArr.length * 2;
        int length2 = strArr.length;
        for (int i = 0; i < length2; i++) {
            length += (long) this.namesAndValues[i].length();
        }
        return length;
    }

    @Override // java.lang.Iterable
    public Iterator<Pair<? extends String, ? extends String>> iterator() {
        return _HeadersCommonKt.commonIterator(this);
    }

    public final Builder newBuilder() {
        return _HeadersCommonKt.commonNewBuilder(this);
    }

    public boolean equals(Object other) {
        return _HeadersCommonKt.commonEquals(this, other);
    }

    public int hashCode() {
        return _HeadersCommonKt.commonHashCode(this);
    }

    public String toString() {
        return _HeadersCommonKt.commonToString(this);
    }

    public final Map<String, List<String>> toMultimap() {
        TreeMap treeMap = new TreeMap(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        int size = size();
        for (int i = 0; i < size; i++) {
            String strName = name(i);
            Locale US = Locale.US;
            Intrinsics.checkNotNullExpressionValue(US, "US");
            String lowerCase = strName.toLowerCase(US);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            ArrayList arrayList = (List) treeMap.get(lowerCase);
            if (arrayList == null) {
                arrayList = new ArrayList(2);
                treeMap.put(lowerCase, arrayList);
            }
            arrayList.add(value(i));
        }
        return treeMap;
    }

    /* JADX INFO: compiled from: Headers.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u000bJ\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006J\u0016\u0010\u000f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006J\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0013J\u0018\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0014H\u0007J\u0019\u0010\u0015\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0013H\u0086\u0002J\u0019\u0010\u0015\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0014H\u0087\u0002J\u001d\u0010\t\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u000bJ\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0006J\u0019\u0010\u0015\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006H\u0086\u0002J\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0086\u0002J\u0006\u0010\u0018\u001a\u00020\u0012R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0019"}, d2 = {"Lokhttp3/Headers$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "namesAndValues", "", "", "getNamesAndValues$okhttp", "()Ljava/util/List;", "addLenient", "line", "addLenient$okhttp", "add", "name", "value", "addUnsafeNonAscii", "addAll", "headers", "Lokhttp3/Headers;", "Ljava/util/Date;", "Ljava/time/Instant;", "set", "removeAll", CallMethod.METHOD_GET, "build", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList(20);

        public final List<String> getNamesAndValues$okhttp() {
            return this.namesAndValues;
        }

        public final Builder addLenient$okhttp(String line) {
            Intrinsics.checkNotNullParameter(line, "line");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) line, CoreConstants.COLON_CHAR, 1, false, 4, (Object) null);
            if (iIndexOf$default != -1) {
                String strSubstring = line.substring(0, iIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                String strSubstring2 = line.substring(iIndexOf$default + 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                addLenient$okhttp(strSubstring, strSubstring2);
                return this;
            }
            if (line.charAt(0) == ':') {
                String strSubstring3 = line.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
                addLenient$okhttp("", strSubstring3);
                return this;
            }
            addLenient$okhttp("", line);
            return this;
        }

        public final Builder add(String line) {
            Intrinsics.checkNotNullParameter(line, "line");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) line, CoreConstants.COLON_CHAR, 0, false, 6, (Object) null);
            if (iIndexOf$default == -1) {
                throw new IllegalArgumentException(("Unexpected header: " + line).toString());
            }
            String strSubstring = line.substring(0, iIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            String string = StringsKt.trim((CharSequence) strSubstring).toString();
            String strSubstring2 = line.substring(iIndexOf$default + 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            add(string, strSubstring2);
            return this;
        }

        public final Builder add(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            return _HeadersCommonKt.commonAdd(this, name, value);
        }

        public final Builder addUnsafeNonAscii(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            _HeadersCommonKt.headersCheckName(name);
            addLenient$okhttp(name, value);
            return this;
        }

        public final Builder addAll(Headers headers) {
            Intrinsics.checkNotNullParameter(headers, "headers");
            return _HeadersCommonKt.commonAddAll(this, headers);
        }

        public final Builder add(String name, Date value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            return add(name, DateFormattingKt.toHttpDateString(value));
        }

        public final Builder add(String name, Instant value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            Date dateFrom = Date.from(value);
            Intrinsics.checkNotNullExpressionValue(dateFrom, "from(...)");
            return add(name, dateFrom);
        }

        public final Builder set(String name, Date value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            return set(name, DateFormattingKt.toHttpDateString(value));
        }

        public final Builder set(String name, Instant value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            Date dateFrom = Date.from(value);
            Intrinsics.checkNotNullExpressionValue(dateFrom, "from(...)");
            return set(name, dateFrom);
        }

        public final Builder addLenient$okhttp(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            return _HeadersCommonKt.commonAddLenient(this, name, value);
        }

        public final Builder removeAll(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            return _HeadersCommonKt.commonRemoveAll(this, name);
        }

        public final Builder set(String name, String value) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(value, "value");
            return _HeadersCommonKt.commonSet(this, name, value);
        }

        public final String get(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            return _HeadersCommonKt.commonGet(this, name);
        }

        public final Headers build() {
            return _HeadersCommonKt.commonBuild(this);
        }
    }

    /* JADX INFO: compiled from: Headers.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J#\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b\"\u00020\tH\u0007¢\u0006\u0004\b\n\u0010\u000bJ#\u0010\n\u001a\u00020\u00052\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b\"\u00020\tH\u0007¢\u0006\u0004\b\f\u0010\u000bJ\u001d\u0010\r\u001a\u00020\u0005*\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u000eH\u0007¢\u0006\u0002\b\nJ!\u0010\n\u001a\u00020\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u000eH\u0007¢\u0006\u0002\b\fR\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokhttp3/Headers$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "EMPTY", "Lokhttp3/Headers;", "headersOf", "namesAndValues", "", "", "of", "([Ljava/lang/String;)Lokhttp3/Headers;", "-deprecated_of", "toHeaders", "", "headers", "okhttp"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Headers of(String... namesAndValues) {
            Intrinsics.checkNotNullParameter(namesAndValues, "namesAndValues");
            return _HeadersCommonKt.commonHeadersOf((String[]) Arrays.copyOf(namesAndValues, namesAndValues.length));
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "function name changed", replaceWith = @ReplaceWith(expression = "headersOf(*namesAndValues)", imports = {}))
        /* JADX INFO: renamed from: -deprecated_of, reason: not valid java name */
        public final Headers m4420deprecated_of(String... namesAndValues) {
            Intrinsics.checkNotNullParameter(namesAndValues, "namesAndValues");
            return of((String[]) Arrays.copyOf(namesAndValues, namesAndValues.length));
        }

        @JvmStatic
        public final Headers of(Map<String, String> map) {
            Intrinsics.checkNotNullParameter(map, "<this>");
            return _HeadersCommonKt.commonToHeaders(map);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "function moved to extension", replaceWith = @ReplaceWith(expression = "headers.toHeaders()", imports = {}))
        /* JADX INFO: renamed from: -deprecated_of, reason: not valid java name */
        public final Headers m4419deprecated_of(Map<String, String> headers) {
            Intrinsics.checkNotNullParameter(headers, "headers");
            return of(headers);
        }
    }
}
