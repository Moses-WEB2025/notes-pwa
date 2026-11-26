package androidx.savedstate.serialization;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.AbstractDecoder;
import kotlinx.serialization.modules.SerializersModule;
import kotlinx.serialization.modules.SerializersModuleBuildersKt;

/* JADX INFO: compiled from: SavedStateDecoder.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Landroidx/savedstate/serialization/EmptyArrayDecoder;", "Lkotlinx/serialization/encoding/AbstractDecoder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "serializersModule", "Lkotlinx/serialization/modules/SerializersModule;", "getSerializersModule", "()Lkotlinx/serialization/modules/SerializersModule;", "decodeElementIndex", "", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "savedstate_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
final class EmptyArrayDecoder extends AbstractDecoder {
    public static final EmptyArrayDecoder INSTANCE = new EmptyArrayDecoder();
    private static final SerializersModule serializersModule = SerializersModuleBuildersKt.EmptySerializersModule();

    @Override // kotlinx.serialization.encoding.CompositeDecoder
    public int decodeElementIndex(SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return -1;
    }

    private EmptyArrayDecoder() {
    }

    @Override // kotlinx.serialization.encoding.Decoder, kotlinx.serialization.encoding.CompositeDecoder
    public SerializersModule getSerializersModule() {
        return serializersModule;
    }
}
