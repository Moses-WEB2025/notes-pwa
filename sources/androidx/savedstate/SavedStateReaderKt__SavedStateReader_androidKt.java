package androidx.savedstate;

import android.os.Bundle;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SavedStateReader.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\n\u0010\u0004\u001a\u00060\u0002j\u0002`\u0003H\u0002¢\u0006\u0002\b\u0005\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00060\u0002j\u0002`\u0003H\u0002¢\u0006\u0002\b\b\u001a3\u0010\t\u001a\u00020\n*\u00060\u0002j\u0002`\u00032\n\u0010\u000b\u001a\u00060\fj\u0002`\r2\u0010\u0010\u000e\u001a\f\u0012\b\u0012\u00060\u0002j\u0002`\u00030\u000fH\u0002¢\u0006\u0002\b\u0010¨\u0006\u0011"}, d2 = {"contentDeepEquals", "", "Landroid/os/Bundle;", "Landroidx/savedstate/SavedState;", RemoteDeviceInfo.MANUFACTURER_OTHER, "contentDeepEquals$SavedStateReaderKt__SavedStateReader_androidKt", "contentDeepHashCode", "", "contentDeepHashCode$SavedStateReaderKt__SavedStateReader_androidKt", "contentDeepToString", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToString$SavedStateReaderKt__SavedStateReader_androidKt", "savedstate_release"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "androidx/savedstate/SavedStateReaderKt")
final /* synthetic */ class SavedStateReaderKt__SavedStateReader_androidKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean contentDeepEquals$SavedStateReaderKt__SavedStateReader_androidKt(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if (obj != obj2 && !Intrinsics.areEqual(obj, obj2)) {
                if (obj != null && obj2 != null) {
                    if ((obj instanceof Bundle) && (obj2 instanceof Bundle)) {
                        if (!contentDeepEquals$SavedStateReaderKt__SavedStateReader_androidKt((Bundle) obj, (Bundle) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof Object[]) && (obj2 instanceof Object[])) {
                        if (!ArraysKt.contentDeepEquals((Object[]) obj, (Object[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof byte[]) && (obj2 instanceof byte[])) {
                        if (!Arrays.equals((byte[]) obj, (byte[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof short[]) && (obj2 instanceof short[])) {
                        if (!Arrays.equals((short[]) obj, (short[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof int[]) && (obj2 instanceof int[])) {
                        if (!Arrays.equals((int[]) obj, (int[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof long[]) && (obj2 instanceof long[])) {
                        if (!Arrays.equals((long[]) obj, (long[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof float[]) && (obj2 instanceof float[])) {
                        if (!Arrays.equals((float[]) obj, (float[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof double[]) && (obj2 instanceof double[])) {
                        if (!Arrays.equals((double[]) obj, (double[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof char[]) && (obj2 instanceof char[])) {
                        if (!Arrays.equals((char[]) obj, (char[]) obj2)) {
                            return false;
                        }
                    } else if ((obj instanceof boolean[]) && (obj2 instanceof boolean[])) {
                        if (!Arrays.equals((boolean[]) obj, (boolean[]) obj2)) {
                            return false;
                        }
                    } else if (!Intrinsics.areEqual(obj, obj2)) {
                    }
                }
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int contentDeepHashCode$SavedStateReaderKt__SavedStateReader_androidKt(Bundle bundle) {
        int iHashCode;
        Iterator<String> it = bundle.keySet().iterator();
        int i = 1;
        while (it.hasNext()) {
            Object obj = bundle.get(it.next());
            if (obj instanceof Bundle) {
                iHashCode = contentDeepHashCode$SavedStateReaderKt__SavedStateReader_androidKt((Bundle) obj);
            } else if (obj instanceof Object[]) {
                iHashCode = ArraysKt.contentDeepHashCode((Object[]) obj);
            } else if (obj instanceof byte[]) {
                iHashCode = Arrays.hashCode((byte[]) obj);
            } else if (obj instanceof short[]) {
                iHashCode = Arrays.hashCode((short[]) obj);
            } else if (obj instanceof int[]) {
                iHashCode = Arrays.hashCode((int[]) obj);
            } else if (obj instanceof long[]) {
                iHashCode = Arrays.hashCode((long[]) obj);
            } else if (obj instanceof float[]) {
                iHashCode = Arrays.hashCode((float[]) obj);
            } else if (obj instanceof double[]) {
                iHashCode = Arrays.hashCode((double[]) obj);
            } else if (obj instanceof char[]) {
                iHashCode = Arrays.hashCode((char[]) obj);
            } else if (obj instanceof boolean[]) {
                iHashCode = Arrays.hashCode((boolean[]) obj);
            } else {
                iHashCode = obj != null ? obj.hashCode() : 0;
            }
            i = (i * 31) + iHashCode;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void contentDeepToString$SavedStateReaderKt__SavedStateReader_androidKt(Bundle bundle, StringBuilder sb, List<Bundle> list) {
        if (list.contains(bundle)) {
            sb.append("[...]");
            return;
        }
        list.add(bundle);
        sb.append('[');
        int i = 0;
        for (String str : bundle.keySet()) {
            int i2 = i + 1;
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(str + '=');
            Object obj = bundle.get(str);
            if (obj == null) {
                sb.append("null");
            } else if (obj instanceof Bundle) {
                contentDeepToString$SavedStateReaderKt__SavedStateReader_androidKt((Bundle) obj, sb, list);
                Unit unit = Unit.INSTANCE;
            } else if (obj instanceof Object[]) {
                sb.append(ArraysKt.contentDeepToString((Object[]) obj));
            } else if (obj instanceof byte[]) {
                String string = Arrays.toString((byte[]) obj);
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                sb.append(string);
            } else if (obj instanceof short[]) {
                String string2 = Arrays.toString((short[]) obj);
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                sb.append(string2);
            } else if (obj instanceof int[]) {
                String string3 = Arrays.toString((int[]) obj);
                Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                sb.append(string3);
            } else if (obj instanceof long[]) {
                String string4 = Arrays.toString((long[]) obj);
                Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
                sb.append(string4);
            } else if (obj instanceof float[]) {
                String string5 = Arrays.toString((float[]) obj);
                Intrinsics.checkNotNullExpressionValue(string5, "toString(...)");
                sb.append(string5);
            } else if (obj instanceof double[]) {
                String string6 = Arrays.toString((double[]) obj);
                Intrinsics.checkNotNullExpressionValue(string6, "toString(...)");
                sb.append(string6);
            } else if (obj instanceof char[]) {
                String string7 = Arrays.toString((char[]) obj);
                Intrinsics.checkNotNullExpressionValue(string7, "toString(...)");
                sb.append(string7);
            } else if (obj instanceof boolean[]) {
                String string8 = Arrays.toString((boolean[]) obj);
                Intrinsics.checkNotNullExpressionValue(string8, "toString(...)");
                sb.append(string8);
            } else {
                sb.append(obj.toString());
            }
            i = i2;
        }
        sb.append(']');
        list.remove(CollectionsKt.getLastIndex(list));
    }
}
