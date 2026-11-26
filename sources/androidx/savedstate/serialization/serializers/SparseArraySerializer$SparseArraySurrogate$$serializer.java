package androidx.savedstate.serialization.serializers;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.preference.PreferenceProviderHelper;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;

/* JADX INFO: compiled from: BuiltInSerializer.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000<\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0002\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\t\b\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u0017\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00020\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u0015\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\n¢\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eJ\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00020\u0003J\u0015\u0010\u0014\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\n¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"androidx/savedstate/serialization/serializers/SparseArraySerializer.SparseArraySurrogate.$serializer", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/serialization/internal/GeneratedSerializer;", "Landroidx/savedstate/serialization/serializers/SparseArraySerializer$SparseArraySurrogate;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "typeSerial0", "Lkotlinx/serialization/KSerializer;", "(Lkotlinx/serialization/KSerializer;)V", "childSerializers", "", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "typeParametersSerializers", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
@Deprecated(level = DeprecationLevel.HIDDEN, message = "This synthesized declaration should not be used directly")
public /* synthetic */ class SparseArraySerializer$SparseArraySurrogate$$serializer<T> implements GeneratedSerializer<SparseArraySerializer.SparseArraySurrogate<T>> {
    private final SerialDescriptor descriptor;
    private final /* synthetic */ KSerializer<?> typeSerial0;

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.SerializationStrategy, kotlinx.serialization.DeserializationStrategy
    public final SerialDescriptor getDescriptor() {
        return this.descriptor;
    }

    private SparseArraySerializer$SparseArraySurrogate$$serializer() {
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("androidx.savedstate.serialization.serializers.SparseArraySerializer.SparseArraySurrogate", this, 2);
        pluginGeneratedSerialDescriptor.addElement(PreferenceProviderHelper.URI_PARAM_KEYS, false);
        pluginGeneratedSerialDescriptor.addElement("values", false);
        this.descriptor = pluginGeneratedSerialDescriptor;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SparseArraySerializer$SparseArraySurrogate$$serializer(KSerializer<T> typeSerial0) {
        this();
        Intrinsics.checkNotNullParameter(typeSerial0, "typeSerial0");
        this.typeSerial0 = typeSerial0;
    }

    private final /* synthetic */ KSerializer getTypeSerial0() {
        return this.typeSerial0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.serialization.internal.GeneratedSerializer
    public final KSerializer<?>[] childSerializers() {
        return new KSerializer[]{SparseArraySerializer.SparseArraySurrogate.$childSerializers[0].getValue(), new ArrayListSerializer(this.typeSerial0)};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    public final SparseArraySerializer.SparseArraySurrogate<T> deserialize(Decoder decoder) {
        List list;
        List list2;
        int i;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor serialDescriptor = this.descriptor;
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(serialDescriptor);
        Lazy[] lazyArr = SparseArraySerializer.SparseArraySurrogate.$childSerializers;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            list2 = (List) compositeDecoderBeginStructure.decodeSerializableElement(serialDescriptor, 0, (DeserializationStrategy) lazyArr[0].getValue(), null);
            list = (List) compositeDecoderBeginStructure.decodeSerializableElement(serialDescriptor, 1, new ArrayListSerializer(this.typeSerial0), null);
            i = 3;
        } else {
            boolean z = true;
            int i2 = 0;
            List list3 = null;
            List list4 = null;
            while (z) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(serialDescriptor);
                if (iDecodeElementIndex == -1) {
                    z = false;
                } else if (iDecodeElementIndex == 0) {
                    list4 = (List) compositeDecoderBeginStructure.decodeSerializableElement(serialDescriptor, 0, (DeserializationStrategy) lazyArr[0].getValue(), list4);
                    i2 |= 1;
                } else {
                    if (iDecodeElementIndex != 1) {
                        throw new UnknownFieldException(iDecodeElementIndex);
                    }
                    list3 = (List) compositeDecoderBeginStructure.decodeSerializableElement(serialDescriptor, 1, new ArrayListSerializer(this.typeSerial0), list3);
                    i2 |= 2;
                }
            }
            list = list3;
            list2 = list4;
            i = i2;
        }
        compositeDecoderBeginStructure.endStructure(serialDescriptor);
        return new SparseArraySerializer.SparseArraySurrogate<>(i, list2, list, null);
    }

    @Override // kotlinx.serialization.SerializationStrategy
    public final void serialize(Encoder encoder, SparseArraySerializer.SparseArraySurrogate<T> value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerialDescriptor serialDescriptor = this.descriptor;
        CompositeEncoder compositeEncoderBeginStructure = encoder.beginStructure(serialDescriptor);
        SparseArraySerializer.SparseArraySurrogate.write$Self$savedstate_release(value, compositeEncoderBeginStructure, serialDescriptor, this.typeSerial0);
        compositeEncoderBeginStructure.endStructure(serialDescriptor);
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    public final KSerializer<?>[] typeParametersSerializers() {
        return new KSerializer[]{this.typeSerial0};
    }
}
