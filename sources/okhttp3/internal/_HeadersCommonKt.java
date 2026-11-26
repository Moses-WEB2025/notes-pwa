package okhttp3.internal;

import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: -HeadersCommon.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0014\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007*\u00020\u00022\u0006\u0010\b\u001a\u00020\u0001H\u0000\u001a\u001e\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u000b0\n*\u00020\u0002H\u0000\u001a\f\u0010\f\u001a\u00020\r*\u00020\u0002H\u0000\u001a\u0016\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0000\u001a\f\u0010\u0012\u001a\u00020\u0004*\u00020\u0002H\u0000\u001a\f\u0010\u0013\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a%\u0010\u0014\u001a\u0004\u0018\u00010\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00162\u0006\u0010\b\u001a\u00020\u0001H\u0000¢\u0006\u0002\u0010\u0017\u001a\u001c\u0010\u0018\u001a\u00020\r*\u00020\r2\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u0001H\u0000\u001a\u0014\u0010\u001a\u001a\u00020\r*\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0002H\u0000\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\r2\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u0001H\u0000\u001a\u0014\u0010\u001d\u001a\u00020\r*\u00020\r2\u0006\u0010\b\u001a\u00020\u0001H\u0000\u001a\u001c\u0010\u001e\u001a\u00020\r*\u00020\r2\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u0001H\u0000\u001a\u0016\u0010\u001f\u001a\u0004\u0018\u00010\u0001*\u00020\r2\u0006\u0010\b\u001a\u00020\u0001H\u0000\u001a\f\u0010 \u001a\u00020\u0002*\u00020\rH\u0000\u001a\u0010\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\u0001H\u0000\u001a\u0018\u0010#\u001a\u00020\"2\u0006\u0010\u0019\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u0001H\u0000\u001a\f\u0010$\u001a\u00020\u0001*\u00020%H\u0002\u001a!\u0010&\u001a\u00020\u00022\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0016\"\u00020\u0001H\u0000¢\u0006\u0002\u0010(\u001a\u0018\u0010)\u001a\u00020\u0002*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010*H\u0000¨\u0006+"}, d2 = {"commonName", "", "Lokhttp3/Headers;", "index", "", "commonValue", "commonValues", "", "name", "commonIterator", "", "Lkotlin/Pair;", "commonNewBuilder", "Lokhttp3/Headers$Builder;", "commonEquals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "commonHashCode", "commonToString", "commonHeadersGet", "namesAndValues", "", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "commonAdd", "value", "commonAddAll", "headers", "commonAddLenient", "commonRemoveAll", "commonSet", "commonGet", "commonBuild", "headersCheckName", "", "headersCheckValue", "charCode", "", "commonHeadersOf", "inputNamesAndValues", "([Ljava/lang/String;)Lokhttp3/Headers;", "commonToHeaders", "", "okhttp"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class _HeadersCommonKt {
    public static final String commonName(Headers headers, int i) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        String str = (String) ArraysKt.getOrNull(headers.getNamesAndValues(), i * 2);
        if (str != null) {
            return str;
        }
        throw new IndexOutOfBoundsException("name[" + i + ']');
    }

    public static final String commonValue(Headers headers, int i) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        String str = (String) ArraysKt.getOrNull(headers.getNamesAndValues(), (i * 2) + 1);
        if (str != null) {
            return str;
        }
        throw new IndexOutOfBoundsException("value[" + i + ']');
    }

    public static final List<String> commonValues(Headers headers, String name) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        int size = headers.size();
        List<String> listUnmodifiableList = null;
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            if (StringsKt.equals(name, headers.name(i), true)) {
                if (arrayList == null) {
                    arrayList = new ArrayList(2);
                }
                arrayList.add(headers.value(i));
            }
        }
        if (arrayList != null) {
            listUnmodifiableList = Collections.unmodifiableList(arrayList);
            Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "unmodifiableList(...)");
        }
        return listUnmodifiableList == null ? CollectionsKt.emptyList() : listUnmodifiableList;
    }

    public static final Iterator<Pair<String, String>> commonIterator(Headers headers) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        int size = headers.size();
        Pair[] pairArr = new Pair[size];
        for (int i = 0; i < size; i++) {
            pairArr[i] = TuplesKt.to(headers.name(i), headers.value(i));
        }
        return ArrayIteratorKt.iterator(pairArr);
    }

    public static final Headers.Builder commonNewBuilder(Headers headers) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        Headers.Builder builder = new Headers.Builder();
        CollectionsKt.addAll(builder.getNamesAndValues$okhttp(), headers.getNamesAndValues());
        return builder;
    }

    public static final boolean commonEquals(Headers headers, Object obj) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        return (obj instanceof Headers) && Arrays.equals(headers.getNamesAndValues(), ((Headers) obj).getNamesAndValues());
    }

    public static final int commonHashCode(Headers headers) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        return Arrays.hashCode(headers.getNamesAndValues());
    }

    public static final String commonToString(Headers headers) {
        Intrinsics.checkNotNullParameter(headers, "<this>");
        StringBuilder sb = new StringBuilder();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String strName = headers.name(i);
            String strValue = headers.value(i);
            sb.append(strName);
            sb.append(": ");
            if (_UtilCommonKt.isSensitiveHeader(strName)) {
                strValue = "██";
            }
            sb.append(strValue);
            sb.append(StringUtils.LF);
        }
        return sb.toString();
    }

    public static final String commonHeadersGet(String[] namesAndValues, String name) {
        Intrinsics.checkNotNullParameter(namesAndValues, "namesAndValues");
        Intrinsics.checkNotNullParameter(name, "name");
        int length = namesAndValues.length - 2;
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(length, 0, -2);
        if (progressionLastElement > length) {
            return null;
        }
        while (!StringsKt.equals(name, namesAndValues[length], true)) {
            if (length == progressionLastElement) {
                return null;
            }
            length -= 2;
        }
        return namesAndValues[length + 1];
    }

    public static final Headers.Builder commonAdd(Headers.Builder builder, String name, String value) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        headersCheckName(name);
        headersCheckValue(value, name);
        commonAddLenient(builder, name, value);
        return builder;
    }

    public static final Headers.Builder commonAddAll(Headers.Builder builder, Headers headers) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(headers, "headers");
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            commonAddLenient(builder, headers.name(i), headers.value(i));
        }
        return builder;
    }

    public static final Headers.Builder commonAddLenient(Headers.Builder builder, String name, String value) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        builder.getNamesAndValues$okhttp().add(name);
        builder.getNamesAndValues$okhttp().add(StringsKt.trim((CharSequence) value).toString());
        return builder;
    }

    public static final Headers.Builder commonRemoveAll(Headers.Builder builder, String name) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        int i = 0;
        while (i < builder.getNamesAndValues$okhttp().size()) {
            if (StringsKt.equals(name, builder.getNamesAndValues$okhttp().get(i), true)) {
                builder.getNamesAndValues$okhttp().remove(i);
                builder.getNamesAndValues$okhttp().remove(i);
                i -= 2;
            }
            i += 2;
        }
        return builder;
    }

    public static final Headers.Builder commonSet(Headers.Builder builder, String name, String value) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        headersCheckName(name);
        headersCheckValue(value, name);
        builder.removeAll(name);
        commonAddLenient(builder, name, value);
        return builder;
    }

    public static final String commonGet(Headers.Builder builder, String name) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        int size = builder.getNamesAndValues$okhttp().size() - 2;
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(size, 0, -2);
        if (progressionLastElement > size) {
            return null;
        }
        while (!StringsKt.equals(name, builder.getNamesAndValues$okhttp().get(size), true)) {
            if (size == progressionLastElement) {
                return null;
            }
            size -= 2;
        }
        return builder.getNamesAndValues$okhttp().get(size + 1);
    }

    public static final Headers commonBuild(Headers.Builder builder) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        return new Headers((String[]) builder.getNamesAndValues$okhttp().toArray(new String[0]));
    }

    public static final void headersCheckName(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String str = name;
        if (str.length() <= 0) {
            throw new IllegalArgumentException("name is empty".toString());
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = name.charAt(i);
            if ('!' > cCharAt || cCharAt >= 127) {
                throw new IllegalArgumentException(("Unexpected char 0x" + charCode(cCharAt) + " at " + i + " in header name: " + name).toString());
            }
        }
    }

    public static final void headersCheckValue(String value, String name) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(name, "name");
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = value.charAt(i);
            if (cCharAt != '\t' && (' ' > cCharAt || cCharAt >= 127)) {
                throw new IllegalArgumentException(("Unexpected char 0x" + charCode(cCharAt) + " at " + i + " in " + name + " value" + (_UtilCommonKt.isSensitiveHeader(name) ? "" : ": " + value)).toString());
            }
        }
    }

    private static final String charCode(char c) {
        String string = Integer.toString(c, CharsKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string.length() < 2 ? "0" + string : string;
    }

    public static final Headers commonHeadersOf(String... inputNamesAndValues) {
        Intrinsics.checkNotNullParameter(inputNamesAndValues, "inputNamesAndValues");
        if (inputNamesAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values".toString());
        }
        String[] strArr = (String[]) Arrays.copyOf(inputNamesAndValues, inputNamesAndValues.length);
        int length = strArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (strArr[i2] == null) {
                throw new IllegalArgumentException("Headers cannot be null".toString());
            }
            strArr[i2] = StringsKt.trim((CharSequence) inputNamesAndValues[i2]).toString();
        }
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, strArr.length - 1, 2);
        if (progressionLastElement >= 0) {
            while (true) {
                String str = strArr[i];
                String str2 = strArr[i + 1];
                headersCheckName(str);
                headersCheckValue(str2, str);
                if (i == progressionLastElement) {
                    break;
                }
                i += 2;
            }
        }
        return new Headers(strArr);
    }

    public static final Headers commonToHeaders(Map<String, String> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        String[] strArr = new String[map.size() * 2];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String string = StringsKt.trim((CharSequence) key).toString();
            String string2 = StringsKt.trim((CharSequence) value).toString();
            headersCheckName(string);
            headersCheckValue(string2, string);
            strArr[i] = string;
            strArr[i + 1] = string2;
            i += 2;
        }
        return new Headers(strArr);
    }
}
