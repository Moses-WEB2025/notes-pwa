package androidx.savedstate.serialization;

import android.os.IBinder;
import android.os.Parcelable;
import androidx.savedstate.serialization.serializers.CharSequenceSerializer;
import androidx.savedstate.serialization.serializers.DefaultParcelableSerializer;
import androidx.savedstate.serialization.serializers.SparseArraySerializer;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;
import kotlinx.serialization.PolymorphicSerializer;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* JADX INFO: compiled from: SavedStateCodecUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b$\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0014\u0010\u0006\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u0014\u0010\b\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u001a\u0010\n\u001a\u00020\u0001X\u0080\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u0003\"\u001a\u0010\u000e\u001a\u00020\u0001X\u0080\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u0003\"\u0014\u0010\u0011\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0003\"\u0014\u0010\u0013\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0003\"\u001a\u0010\u0015\u001a\u00020\u0001X\u0080\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0016\u0010\f\u001a\u0004\b\u0017\u0010\u0003\"\u001a\u0010\u0018\u001a\u00020\u0001X\u0080\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0019\u0010\f\u001a\u0004\b\u001a\u0010\u0003\"\u0014\u0010\u001b\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0003\"\u0014\u0010\u001d\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0003\"\u0014\u0010\u001f\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0003\"\u0014\u0010!\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0003\"\u0014\u0010#\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0003¨\u0006%"}, d2 = {"polymorphicCharSequenceDescriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getPolymorphicCharSequenceDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "polymorphicParcelableDescriptor", "getPolymorphicParcelableDescriptor", "polymorphicJavaSerializableDescriptor", "getPolymorphicJavaSerializableDescriptor", "polymorphicIBinderDescriptor", "getPolymorphicIBinderDescriptor", "parcelableArrayDescriptor", "getParcelableArrayDescriptor$annotations", "()V", "getParcelableArrayDescriptor", "polymorphicParcelableArrayDescriptor", "getPolymorphicParcelableArrayDescriptor$annotations", "getPolymorphicParcelableArrayDescriptor", "parcelableListDescriptor", "getParcelableListDescriptor", "polymorphicParcelableListDescriptor", "getPolymorphicParcelableListDescriptor", "charSequenceArrayDescriptor", "getCharSequenceArrayDescriptor$annotations", "getCharSequenceArrayDescriptor", "polymorphicCharSequenceArrayDescriptor", "getPolymorphicCharSequenceArrayDescriptor$annotations", "getPolymorphicCharSequenceArrayDescriptor", "charSequenceListDescriptor", "getCharSequenceListDescriptor", "polymorphicCharSequenceListDescriptor", "getPolymorphicCharSequenceListDescriptor", "sparseParcelableArrayDescriptor", "getSparseParcelableArrayDescriptor", "polymorphicSparseParcelableArrayDescriptor", "getPolymorphicSparseParcelableArrayDescriptor", "nullablePolymorphicSparseParcelableArrayDescriptor", "getNullablePolymorphicSparseParcelableArrayDescriptor", "savedstate_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SavedStateCodecUtils_androidKt {
    private static final SerialDescriptor polymorphicCharSequenceDescriptor = new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(CharSequence.class)).getDescriptor();
    private static final SerialDescriptor polymorphicParcelableDescriptor = new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(Parcelable.class)).getDescriptor();
    private static final SerialDescriptor polymorphicJavaSerializableDescriptor = new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(Serializable.class)).getDescriptor();
    private static final SerialDescriptor polymorphicIBinderDescriptor = new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(IBinder.class)).getDescriptor();
    private static final SerialDescriptor parcelableArrayDescriptor = BuiltinSerializersKt.ArraySerializer(Reflection.getOrCreateKotlinClass(Parcelable.class), DefaultParcelableSerializer.INSTANCE).getDescriptor();
    private static final SerialDescriptor polymorphicParcelableArrayDescriptor = BuiltinSerializersKt.ArraySerializer(Reflection.getOrCreateKotlinClass(Parcelable.class), new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(Parcelable.class))).getDescriptor();
    private static final SerialDescriptor parcelableListDescriptor = BuiltinSerializersKt.ListSerializer(DefaultParcelableSerializer.INSTANCE).getDescriptor();
    private static final SerialDescriptor polymorphicParcelableListDescriptor = BuiltinSerializersKt.ListSerializer(new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(Parcelable.class))).getDescriptor();
    private static final SerialDescriptor charSequenceArrayDescriptor = BuiltinSerializersKt.ArraySerializer(Reflection.getOrCreateKotlinClass(CharSequence.class), CharSequenceSerializer.INSTANCE).getDescriptor();
    private static final SerialDescriptor polymorphicCharSequenceArrayDescriptor = BuiltinSerializersKt.ArraySerializer(Reflection.getOrCreateKotlinClass(CharSequence.class), new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(CharSequence.class))).getDescriptor();
    private static final SerialDescriptor charSequenceListDescriptor = BuiltinSerializersKt.ListSerializer(CharSequenceSerializer.INSTANCE).getDescriptor();
    private static final SerialDescriptor polymorphicCharSequenceListDescriptor = BuiltinSerializersKt.ListSerializer(new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(CharSequence.class))).getDescriptor();
    private static final SerialDescriptor sparseParcelableArrayDescriptor = new SparseArraySerializer(DefaultParcelableSerializer.INSTANCE).getDescriptor();
    private static final SerialDescriptor polymorphicSparseParcelableArrayDescriptor = new SparseArraySerializer(new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(Parcelable.class))).getDescriptor();
    private static final SerialDescriptor nullablePolymorphicSparseParcelableArrayDescriptor = new SparseArraySerializer(BuiltinSerializersKt.getNullable(new PolymorphicSerializer(Reflection.getOrCreateKotlinClass(Parcelable.class)))).getDescriptor();

    public static /* synthetic */ void getCharSequenceArrayDescriptor$annotations() {
    }

    public static /* synthetic */ void getParcelableArrayDescriptor$annotations() {
    }

    public static /* synthetic */ void getPolymorphicCharSequenceArrayDescriptor$annotations() {
    }

    public static /* synthetic */ void getPolymorphicParcelableArrayDescriptor$annotations() {
    }

    public static final SerialDescriptor getPolymorphicCharSequenceDescriptor() {
        return polymorphicCharSequenceDescriptor;
    }

    public static final SerialDescriptor getPolymorphicParcelableDescriptor() {
        return polymorphicParcelableDescriptor;
    }

    public static final SerialDescriptor getPolymorphicJavaSerializableDescriptor() {
        return polymorphicJavaSerializableDescriptor;
    }

    public static final SerialDescriptor getPolymorphicIBinderDescriptor() {
        return polymorphicIBinderDescriptor;
    }

    public static final SerialDescriptor getParcelableArrayDescriptor() {
        return parcelableArrayDescriptor;
    }

    public static final SerialDescriptor getPolymorphicParcelableArrayDescriptor() {
        return polymorphicParcelableArrayDescriptor;
    }

    public static final SerialDescriptor getParcelableListDescriptor() {
        return parcelableListDescriptor;
    }

    public static final SerialDescriptor getPolymorphicParcelableListDescriptor() {
        return polymorphicParcelableListDescriptor;
    }

    public static final SerialDescriptor getCharSequenceArrayDescriptor() {
        return charSequenceArrayDescriptor;
    }

    public static final SerialDescriptor getPolymorphicCharSequenceArrayDescriptor() {
        return polymorphicCharSequenceArrayDescriptor;
    }

    public static final SerialDescriptor getCharSequenceListDescriptor() {
        return charSequenceListDescriptor;
    }

    public static final SerialDescriptor getPolymorphicCharSequenceListDescriptor() {
        return polymorphicCharSequenceListDescriptor;
    }

    public static final SerialDescriptor getSparseParcelableArrayDescriptor() {
        return sparseParcelableArrayDescriptor;
    }

    public static final SerialDescriptor getPolymorphicSparseParcelableArrayDescriptor() {
        return polymorphicSparseParcelableArrayDescriptor;
    }

    public static final SerialDescriptor getNullablePolymorphicSparseParcelableArrayDescriptor() {
        return nullablePolymorphicSparseParcelableArrayDescriptor;
    }
}
