package androidx.savedstate;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SavedStateWriter.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u000b\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0013\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0087@\u0018\u00002\u00020\u0001B\u0015\b\u0001\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010¢\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014¢\u0006\u0004\b\u0015\u0010\u0016J\u001d\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0018¢\u0006\u0004\b\u0019\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u001c¢\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010\u001f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020 ¢\u0006\u0004\b!\u0010\"J\u001d\u0010#\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020$¢\u0006\u0004\b%\u0010&J\u001d\u0010'\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020(¢\u0006\u0004\b)\u0010*J\u0015\u0010+\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b,\u0010-J'\u0010.\u001a\u00020\b\"\b\b\u0000\u0010/*\u0002002\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u0002H/¢\u0006\u0004\b1\u00102J'\u00103\u001a\u00020\b\"\b\b\u0000\u0010/*\u0002042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u0002H/¢\u0006\u0004\b5\u00106J\u001d\u00107\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u000208¢\u0006\u0004\b9\u0010:J\u001d\u0010;\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020<¢\u0006\u0004\b=\u0010>J\u001d\u0010?\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b@\u0010AJ#\u0010B\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020$0C¢\u0006\u0004\bD\u0010EJ#\u0010F\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00180C¢\u0006\u0004\bG\u0010EJ'\u0010H\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0010\u0010\u000b\u001a\f\u0012\b\u0012\u00060\u0003j\u0002`\u00040C¢\u0006\u0004\bI\u0010EJ#\u0010J\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0C¢\u0006\u0004\bK\u0010EJ-\u0010L\u001a\u00020\b\"\b\b\u0000\u0010/*\u0002002\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H/0C¢\u0006\u0004\bM\u0010EJ\u001d\u0010N\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020O¢\u0006\u0004\bP\u0010QJ\u001d\u0010R\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020S¢\u0006\u0004\bT\u0010UJ#\u0010V\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00180W¢\u0006\u0004\bX\u0010YJ\u001d\u0010Z\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020[¢\u0006\u0004\b\\\u0010]J\u001d\u0010^\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020_¢\u0006\u0004\b`\u0010aJ\u001d\u0010b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020c¢\u0006\u0004\bd\u0010eJ\u001d\u0010f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020g¢\u0006\u0004\bh\u0010iJ'\u0010j\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0010\u0010\u000b\u001a\f\u0012\b\u0012\u00060\u0003j\u0002`\u00040W¢\u0006\u0004\bk\u0010lJ#\u0010m\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0W¢\u0006\u0004\bn\u0010oJ-\u0010p\u001a\u00020\b\"\b\b\u0000\u0010/*\u0002002\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H/0W¢\u0006\u0004\bq\u0010rJ-\u0010s\u001a\u00020\b\"\b\b\u0000\u0010/*\u0002002\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H/0t¢\u0006\u0004\bu\u0010vJ!\u0010w\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0004\bx\u0010yJ\u0019\u0010z\u001a\u00020\b2\n\u0010{\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0004\b|\u0010}J\u0015\u0010~\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u007f\u0010-J\u0010\u0010\u0080\u0001\u001a\u00020\b¢\u0006\u0006\b\u0081\u0001\u0010\u0082\u0001J\u001e\u0010\u0083\u0001\u001a\u00020\u00102\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0006\b\u0085\u0001\u0010\u0086\u0001J\u0013\u0010\u0087\u0001\u001a\u00020$HÖ\u0001¢\u0006\u0006\b\u0088\u0001\u0010\u0089\u0001J\u0013\u0010\u008a\u0001\u001a\u00020\nHÖ\u0001¢\u0006\u0006\b\u008b\u0001\u0010\u008c\u0001R\u0012\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0088\u0001\u0002\u0092\u0001\u00060\u0003j\u0002`\u0004¨\u0006\u008d\u0001"}, d2 = {"Landroidx/savedstate/SavedStateWriter;", "", "source", "Landroid/os/Bundle;", "Landroidx/savedstate/SavedState;", "constructor-impl", "(Landroid/os/Bundle;)Landroid/os/Bundle;", "putBinder", "", "key", "", "value", "Landroid/os/IBinder;", "putBinder-impl", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/os/IBinder;)V", "putBoolean", "", "putBoolean-impl", "(Landroid/os/Bundle;Ljava/lang/String;Z)V", "putChar", "", "putChar-impl", "(Landroid/os/Bundle;Ljava/lang/String;C)V", "putCharSequence", "", "putCharSequence-impl", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/lang/CharSequence;)V", "putDouble", "", "putDouble-impl", "(Landroid/os/Bundle;Ljava/lang/String;D)V", "putFloat", "", "putFloat-impl", "(Landroid/os/Bundle;Ljava/lang/String;F)V", "putInt", "", "putInt-impl", "(Landroid/os/Bundle;Ljava/lang/String;I)V", "putLong", "", "putLong-impl", "(Landroid/os/Bundle;Ljava/lang/String;J)V", "putNull", "putNull-impl", "(Landroid/os/Bundle;Ljava/lang/String;)V", "putParcelable", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/os/Parcelable;", "putParcelable-impl", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/os/Parcelable;)V", "putJavaSerializable", "Ljava/io/Serializable;", "putJavaSerializable-impl", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/io/Serializable;)V", "putSize", "Landroid/util/Size;", "putSize-impl", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/util/Size;)V", "putSizeF", "Landroid/util/SizeF;", "putSizeF-impl", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/util/SizeF;)V", "putString", "putString-impl", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/lang/String;)V", "putIntList", "", "putIntList-impl", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/util/List;)V", "putCharSequenceList", "putCharSequenceList-impl", "putSavedStateList", "putSavedStateList-impl", "putStringList", "putStringList-impl", "putParcelableList", "putParcelableList-impl", "putBooleanArray", "", "putBooleanArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[Z)V", "putCharArray", "", "putCharArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[C)V", "putCharSequenceArray", "", "putCharSequenceArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[Ljava/lang/CharSequence;)V", "putDoubleArray", "", "putDoubleArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[D)V", "putFloatArray", "", "putFloatArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[F)V", "putIntArray", "", "putIntArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[I)V", "putLongArray", "", "putLongArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[J)V", "putSavedStateArray", "putSavedStateArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[Landroid/os/Bundle;)V", "putStringArray", "putStringArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[Ljava/lang/String;)V", "putParcelableArray", "putParcelableArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;[Landroid/os/Parcelable;)V", "putSparseParcelableArray", "Landroid/util/SparseArray;", "putSparseParcelableArray-impl", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/util/SparseArray;)V", "putSavedState", "putSavedState-impl", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/os/Bundle;)V", "putAll", "from", "putAll-impl", "(Landroid/os/Bundle;Landroid/os/Bundle;)V", "remove", "remove-impl", "clear", "clear-impl", "(Landroid/os/Bundle;)V", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "equals-impl", "(Landroid/os/Bundle;Ljava/lang/Object;)Z", "hashCode", "hashCode-impl", "(Landroid/os/Bundle;)I", "toString", "toString-impl", "(Landroid/os/Bundle;)Ljava/lang/String;", "savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
@JvmInline
public final class SavedStateWriter {
    private final Bundle source;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ SavedStateWriter m231boximpl(Bundle bundle) {
        return new SavedStateWriter(bundle);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static Bundle m233constructorimpl(Bundle source) {
        Intrinsics.checkNotNullParameter(source, "source");
        return source;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m234equalsimpl(Bundle bundle, Object obj) {
        return (obj instanceof SavedStateWriter) && Intrinsics.areEqual(bundle, ((SavedStateWriter) obj).getSource());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m235equalsimpl0(Bundle bundle, Bundle bundle2) {
        return Intrinsics.areEqual(bundle, bundle2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m236hashCodeimpl(Bundle bundle) {
        return bundle.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m270toStringimpl(Bundle bundle) {
        return "SavedStateWriter(source=" + bundle + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public boolean equals(Object obj) {
        return m234equalsimpl(this.source, obj);
    }

    public int hashCode() {
        return m236hashCodeimpl(this.source);
    }

    public String toString() {
        return m270toStringimpl(this.source);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ Bundle getSource() {
        return this.source;
    }

    private /* synthetic */ SavedStateWriter(Bundle bundle) {
        this.source = bundle;
    }

    /* JADX INFO: renamed from: putBinder-impl, reason: not valid java name */
    public static final void m238putBinderimpl(Bundle bundle, String key, IBinder value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putBinder(key, value);
    }

    /* JADX INFO: renamed from: putBoolean-impl, reason: not valid java name */
    public static final void m239putBooleanimpl(Bundle bundle, String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putBoolean(key, z);
    }

    /* JADX INFO: renamed from: putChar-impl, reason: not valid java name */
    public static final void m241putCharimpl(Bundle bundle, String key, char c) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putChar(key, c);
    }

    /* JADX INFO: renamed from: putCharSequence-impl, reason: not valid java name */
    public static final void m243putCharSequenceimpl(Bundle bundle, String key, CharSequence value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putCharSequence(key, value);
    }

    /* JADX INFO: renamed from: putDouble-impl, reason: not valid java name */
    public static final void m246putDoubleimpl(Bundle bundle, String key, double d) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putDouble(key, d);
    }

    /* JADX INFO: renamed from: putFloat-impl, reason: not valid java name */
    public static final void m248putFloatimpl(Bundle bundle, String key, float f) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putFloat(key, f);
    }

    /* JADX INFO: renamed from: putInt-impl, reason: not valid java name */
    public static final void m250putIntimpl(Bundle bundle, String key, int i) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putInt(key, i);
    }

    /* JADX INFO: renamed from: putLong-impl, reason: not valid java name */
    public static final void m254putLongimpl(Bundle bundle, String key, long j) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putLong(key, j);
    }

    /* JADX INFO: renamed from: putNull-impl, reason: not valid java name */
    public static final void m256putNullimpl(Bundle bundle, String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putString(key, null);
    }

    /* JADX INFO: renamed from: putParcelable-impl, reason: not valid java name */
    public static final <T extends Parcelable> void m257putParcelableimpl(Bundle bundle, String key, T value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putParcelable(key, value);
    }

    /* JADX INFO: renamed from: putJavaSerializable-impl, reason: not valid java name */
    public static final <T extends Serializable> void m253putJavaSerializableimpl(Bundle bundle, String key, T value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putSerializable(key, value);
    }

    /* JADX INFO: renamed from: putSize-impl, reason: not valid java name */
    public static final void m263putSizeimpl(Bundle bundle, String key, Size value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putSize(key, value);
    }

    /* JADX INFO: renamed from: putSizeF-impl, reason: not valid java name */
    public static final void m264putSizeFimpl(Bundle bundle, String key, SizeF value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putSizeF(key, value);
    }

    /* JADX INFO: renamed from: putString-impl, reason: not valid java name */
    public static final void m266putStringimpl(Bundle bundle, String key, String value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putString(key, value);
    }

    /* JADX INFO: renamed from: putIntList-impl, reason: not valid java name */
    public static final void m252putIntListimpl(Bundle bundle, String key, List<Integer> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putIntegerArrayList(key, SavedStateWriterKt.toArrayListUnsafe(value));
    }

    /* JADX INFO: renamed from: putCharSequenceList-impl, reason: not valid java name */
    public static final void m245putCharSequenceListimpl(Bundle bundle, String key, List<? extends CharSequence> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putCharSequenceArrayList(key, SavedStateWriterKt.toArrayListUnsafe(value));
    }

    /* JADX INFO: renamed from: putSavedStateList-impl, reason: not valid java name */
    public static final void m262putSavedStateListimpl(Bundle bundle, String key, List<Bundle> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        m259putParcelableListimpl(bundle, key, value);
    }

    /* JADX INFO: renamed from: putStringList-impl, reason: not valid java name */
    public static final void m268putStringListimpl(Bundle bundle, String key, List<String> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putStringArrayList(key, SavedStateWriterKt.toArrayListUnsafe(value));
    }

    /* JADX INFO: renamed from: putParcelableList-impl, reason: not valid java name */
    public static final <T extends Parcelable> void m259putParcelableListimpl(Bundle bundle, String key, List<? extends T> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putParcelableArrayList(key, SavedStateWriterKt.toArrayListUnsafe(value));
    }

    /* JADX INFO: renamed from: putBooleanArray-impl, reason: not valid java name */
    public static final void m240putBooleanArrayimpl(Bundle bundle, String key, boolean[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putBooleanArray(key, value);
    }

    /* JADX INFO: renamed from: putCharArray-impl, reason: not valid java name */
    public static final void m242putCharArrayimpl(Bundle bundle, String key, char[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putCharArray(key, value);
    }

    /* JADX INFO: renamed from: putCharSequenceArray-impl, reason: not valid java name */
    public static final void m244putCharSequenceArrayimpl(Bundle bundle, String key, CharSequence[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putCharSequenceArray(key, value);
    }

    /* JADX INFO: renamed from: putDoubleArray-impl, reason: not valid java name */
    public static final void m247putDoubleArrayimpl(Bundle bundle, String key, double[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putDoubleArray(key, value);
    }

    /* JADX INFO: renamed from: putFloatArray-impl, reason: not valid java name */
    public static final void m249putFloatArrayimpl(Bundle bundle, String key, float[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putFloatArray(key, value);
    }

    /* JADX INFO: renamed from: putIntArray-impl, reason: not valid java name */
    public static final void m251putIntArrayimpl(Bundle bundle, String key, int[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putIntArray(key, value);
    }

    /* JADX INFO: renamed from: putLongArray-impl, reason: not valid java name */
    public static final void m255putLongArrayimpl(Bundle bundle, String key, long[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putLongArray(key, value);
    }

    /* JADX INFO: renamed from: putSavedStateArray-impl, reason: not valid java name */
    public static final void m261putSavedStateArrayimpl(Bundle bundle, String key, Bundle[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        m258putParcelableArrayimpl(bundle, key, value);
    }

    /* JADX INFO: renamed from: putStringArray-impl, reason: not valid java name */
    public static final void m267putStringArrayimpl(Bundle bundle, String key, String[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putStringArray(key, value);
    }

    /* JADX INFO: renamed from: putParcelableArray-impl, reason: not valid java name */
    public static final <T extends Parcelable> void m258putParcelableArrayimpl(Bundle bundle, String key, T[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putParcelableArray(key, value);
    }

    /* JADX INFO: renamed from: putSparseParcelableArray-impl, reason: not valid java name */
    public static final <T extends Parcelable> void m265putSparseParcelableArrayimpl(Bundle bundle, String key, SparseArray<T> value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putSparseParcelableArray(key, value);
    }

    /* JADX INFO: renamed from: putSavedState-impl, reason: not valid java name */
    public static final void m260putSavedStateimpl(Bundle bundle, String key, Bundle value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        bundle.putBundle(key, value);
    }

    /* JADX INFO: renamed from: putAll-impl, reason: not valid java name */
    public static final void m237putAllimpl(Bundle bundle, Bundle from) {
        Intrinsics.checkNotNullParameter(from, "from");
        bundle.putAll(from);
    }

    /* JADX INFO: renamed from: remove-impl, reason: not valid java name */
    public static final void m269removeimpl(Bundle bundle, String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.remove(key);
    }

    /* JADX INFO: renamed from: clear-impl, reason: not valid java name */
    public static final void m232clearimpl(Bundle bundle) {
        bundle.clear();
    }
}
