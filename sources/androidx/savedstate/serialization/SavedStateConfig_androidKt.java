package androidx.savedstate.serialization;

import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.savedstate.serialization.serializers.SizeFSerializer;
import androidx.savedstate.serialization.serializers.SizeSerializer;
import androidx.savedstate.serialization.serializers.SparseArraySerializer;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.modules.SerializersModule;
import kotlinx.serialization.modules.SerializersModuleBuilder;

/* JADX INFO: compiled from: SavedStateConfig.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0000¨\u0006\u0002"}, d2 = {"getDefaultSerializersModuleOnPlatform", "Lkotlinx/serialization/modules/SerializersModule;", "savedstate_release"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SavedStateConfig_androidKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final KSerializer getDefaultSerializersModuleOnPlatform$lambda$1$lambda$0(List argSerializers) {
        Intrinsics.checkNotNullParameter(argSerializers, "argSerializers");
        return new SparseArraySerializer((KSerializer) CollectionsKt.first(argSerializers));
    }

    public static final SerializersModule getDefaultSerializersModuleOnPlatform() {
        SerializersModuleBuilder serializersModuleBuilder = new SerializersModuleBuilder();
        serializersModuleBuilder.contextual(Reflection.getOrCreateKotlinClass(Size.class), SizeSerializer.INSTANCE);
        serializersModuleBuilder.contextual(Reflection.getOrCreateKotlinClass(SizeF.class), SizeFSerializer.INSTANCE);
        serializersModuleBuilder.contextual(Reflection.getOrCreateKotlinClass(SparseArray.class), new Function1() { // from class: androidx.savedstate.serialization.SavedStateConfig_androidKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SavedStateConfig_androidKt.getDefaultSerializersModuleOnPlatform$lambda$1$lambda$0((List) obj);
            }
        });
        return serializersModuleBuilder.build();
    }
}
