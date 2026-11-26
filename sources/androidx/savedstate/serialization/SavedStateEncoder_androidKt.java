package androidx.savedstate.serialization;

import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;
import androidx.exifinterface.media.ExifInterface;
import androidx.savedstate.serialization.serializers.CharSequenceArraySerializer;
import androidx.savedstate.serialization.serializers.CharSequenceListSerializer;
import androidx.savedstate.serialization.serializers.CharSequenceSerializer;
import androidx.savedstate.serialization.serializers.DefaultJavaSerializableSerializer;
import androidx.savedstate.serialization.serializers.DefaultParcelableSerializer;
import androidx.savedstate.serialization.serializers.IBinderSerializer;
import androidx.savedstate.serialization.serializers.ParcelableArraySerializer;
import androidx.savedstate.serialization.serializers.ParcelableListSerializer;
import androidx.savedstate.serialization.serializers.SparseParcelableArraySerializer;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.Encoder;

/* JADX INFO: compiled from: SavedStateEncoder.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u0002H\u0002H\u0000¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"encodeFormatSpecificTypesOnPlatform", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/savedstate/serialization/SavedStateEncoder;", "strategy", "Lkotlinx/serialization/SerializationStrategy;", "value", "(Landroidx/savedstate/serialization/SavedStateEncoder;Lkotlinx/serialization/SerializationStrategy;Ljava/lang/Object;)Z", "savedstate_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SavedStateEncoder_androidKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> boolean encodeFormatSpecificTypesOnPlatform(SavedStateEncoder savedStateEncoder, SerializationStrategy<? super T> strategy, T t) {
        Intrinsics.checkNotNullParameter(savedStateEncoder, "<this>");
        Intrinsics.checkNotNullParameter(strategy, "strategy");
        SerialDescriptor descriptor = strategy.getDescriptor();
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicCharSequenceDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type kotlin.CharSequence");
            CharSequenceSerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (CharSequence) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicParcelableDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type android.os.Parcelable");
            DefaultParcelableSerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (Parcelable) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicJavaSerializableDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type java.io.Serializable");
            DefaultJavaSerializableSerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (Serializable) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicIBinderDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type android.os.IBinder");
            IBinderSerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (IBinder) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getCharSequenceArrayDescriptor()) || Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicCharSequenceArrayDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type kotlin.Array<kotlin.CharSequence>");
            CharSequenceArraySerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (CharSequence[]) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getCharSequenceListDescriptor()) || Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicCharSequenceListDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type kotlin.collections.List<kotlin.CharSequence>");
            CharSequenceListSerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (List<? extends CharSequence>) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getParcelableArrayDescriptor()) || Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicParcelableArrayDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type kotlin.Array<android.os.Parcelable>");
            ParcelableArraySerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (Parcelable[]) t);
            return true;
        }
        if (Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getParcelableListDescriptor()) || Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicParcelableListDescriptor())) {
            Intrinsics.checkNotNull(t, "null cannot be cast to non-null type kotlin.collections.List<android.os.Parcelable>");
            ParcelableListSerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (List<? extends Parcelable>) t);
            return true;
        }
        if (!Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getSparseParcelableArrayDescriptor()) && !Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getPolymorphicSparseParcelableArrayDescriptor()) && !Intrinsics.areEqual(descriptor, SavedStateCodecUtils_androidKt.getNullablePolymorphicSparseParcelableArrayDescriptor())) {
            return false;
        }
        Intrinsics.checkNotNull(t, "null cannot be cast to non-null type android.util.SparseArray<android.os.Parcelable>");
        SparseParcelableArraySerializer.INSTANCE.serialize((Encoder) savedStateEncoder, (SparseArray<Parcelable>) t);
        return true;
    }
}
