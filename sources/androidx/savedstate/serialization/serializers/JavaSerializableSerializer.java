package androidx.savedstate.serialization.serializers;

import androidx.exifinterface.media.ExifInterface;
import androidx.savedstate.SavedStateReader;
import androidx.savedstate.SavedStateWriter;
import androidx.savedstate.serialization.SavedStateDecoder;
import androidx.savedstate.serialization.SavedStateEncoder;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorsKt;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;

/* JADX INFO: compiled from: BuiltInSerializer.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Landroidx/savedstate/serialization/serializers/JavaSerializableSerializer;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/io/Serializable;", "Lkotlinx/serialization/KSerializer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "(Lkotlinx/serialization/encoding/Encoder;Ljava/io/Serializable;)V", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "(Lkotlinx/serialization/encoding/Decoder;)Ljava/io/Serializable;", "savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public abstract class JavaSerializableSerializer<T extends Serializable> implements KSerializer<T> {
    private final SerialDescriptor descriptor = SerialDescriptorsKt.buildClassSerialDescriptor$default("java.io.Serializable", new SerialDescriptor[0], null, 4, null);

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public final void serialize(Encoder encoder, T value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        if (!(encoder instanceof SavedStateEncoder)) {
            throw new IllegalArgumentException(BuiltInSerializerKt.encoderErrorMessage(this.descriptor.getSerialName(), encoder).toString());
        }
        SavedStateEncoder savedStateEncoder = (SavedStateEncoder) encoder;
        SavedStateWriter.m253putJavaSerializableimpl(SavedStateWriter.m233constructorimpl(savedStateEncoder.getSavedState()), savedStateEncoder.getKey(), value);
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    public final T deserialize(Decoder decoder) {
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        if (!(decoder instanceof SavedStateDecoder)) {
            throw new IllegalArgumentException(BuiltInSerializerKt.decoderErrorMessage(this.descriptor.getSerialName(), decoder).toString());
        }
        SavedStateDecoder savedStateDecoder = (SavedStateDecoder) decoder;
        T t = (T) SavedStateReader.m185getJavaSerializableimpl(SavedStateReader.m147constructorimpl(savedStateDecoder.getSavedState()), savedStateDecoder.getKey(), Reflection.getOrCreateKotlinClass(Serializable.class));
        Intrinsics.checkNotNull(t, "null cannot be cast to non-null type T of androidx.savedstate.serialization.serializers.JavaSerializableSerializer");
        return t;
    }
}
