package kotlinx.serialization.descriptors;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MagicApiIntrinsics;
import kotlin.reflect.KType;
import kotlin.text.StringsKt;
import kotlinx.serialization.ExperimentalSerializationApi;
import kotlinx.serialization.InternalSerializationApi;
import kotlinx.serialization.SerializersKt;
import kotlinx.serialization.internal.ArrayListClassDesc;
import kotlinx.serialization.internal.HashMapClassDesc;
import kotlinx.serialization.internal.HashSetClassDesc;
import kotlinx.serialization.internal.PrimitivesKt;
import kotlinx.serialization.internal.SerialDescriptorForNullable;

/* JADX INFO: compiled from: SerialDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000b\n\u0000\u001aB\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0005\"\u00020\u00012\u0019\b\u0002\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\n¢\u0006\u0002\u0010\u000b\u001a\u0016\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e\u001a\u0018\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0001H\u0007\u001aL\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00122\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0005\"\u00020\u00012\u0019\b\u0002\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0007¢\u0006\u0002\u0010\u0014\u001a\u0011\u0010\u0015\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0016\u0018\u0001H\u0086\b\u001a\u000e\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0010\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u0019\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0016\u0018\u0001H\u0087\b\u001a\u0018\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0007\u001a\u0019\u0010\u001b\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u001e\u0018\u0001\"\u0006\b\u0001\u0010\u001f\u0018\u0001H\u0087\b\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0001H\u0007\u001a\u0011\u0010 \u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0016\u0018\u0001H\u0087\b\u001a7\u0010)\u001a\u00020\t\"\u0006\b\u0000\u0010\u0016\u0018\u0001*\u00020\b2\u0006\u0010*\u001a\u00020\u00032\u000e\b\u0002\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,2\b\b\u0002\u0010.\u001a\u00020/H\u0086\b\"\u001b\u0010!\u001a\u00020\u0001*\u00020\u00018F¢\u0006\f\u0012\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u001e\u0010&\u001a\u00020\u0001*\u00020\u00018FX\u0087\u0004¢\u0006\f\u0012\u0004\b'\u0010#\u001a\u0004\b(\u0010%¨\u00060"}, d2 = {"buildClassSerialDescriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "serialName", "", "typeParameters", "", "builderAction", "Lkotlin/Function1;", "Lkotlinx/serialization/descriptors/ClassSerialDescriptorBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;[Lkotlinx/serialization/descriptors/SerialDescriptor;Lkotlin/jvm/functions/Function1;)Lkotlinx/serialization/descriptors/SerialDescriptor;", "PrimitiveSerialDescriptor", "kind", "Lkotlinx/serialization/descriptors/PrimitiveKind;", "SerialDescriptor", "original", "buildSerialDescriptor", "Lkotlinx/serialization/descriptors/SerialKind;", "builder", "(Ljava/lang/String;Lkotlinx/serialization/descriptors/SerialKind;[Lkotlinx/serialization/descriptors/SerialDescriptor;Lkotlin/jvm/functions/Function1;)Lkotlinx/serialization/descriptors/SerialDescriptor;", "serialDescriptor", ExifInterface.GPS_DIRECTION_TRUE, "type", "Lkotlin/reflect/KType;", "listSerialDescriptor", "elementDescriptor", "mapSerialDescriptor", "keyDescriptor", "valueDescriptor", "K", "V", "setSerialDescriptor", "nullable", "getNullable$annotations", "(Lkotlinx/serialization/descriptors/SerialDescriptor;)V", "getNullable", "(Lkotlinx/serialization/descriptors/SerialDescriptor;)Lkotlinx/serialization/descriptors/SerialDescriptor;", "nonNullOriginal", "getNonNullOriginal$annotations", "getNonNullOriginal", "element", "elementName", "annotations", "", "", "isOptional", "", "kotlinx-serialization-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SerialDescriptorsKt {
    @ExperimentalSerializationApi
    public static /* synthetic */ void getNonNullOriginal$annotations(SerialDescriptor serialDescriptor) {
    }

    public static /* synthetic */ void getNullable$annotations(SerialDescriptor serialDescriptor) {
    }

    public static /* synthetic */ SerialDescriptor buildClassSerialDescriptor$default(String str, SerialDescriptor[] serialDescriptorArr, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1() { // from class: kotlinx.serialization.descriptors.SerialDescriptorsKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return SerialDescriptorsKt.buildClassSerialDescriptor$lambda$0((ClassSerialDescriptorBuilder) obj2);
                }
            };
        }
        return buildClassSerialDescriptor(str, serialDescriptorArr, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildClassSerialDescriptor$lambda$0(ClassSerialDescriptorBuilder classSerialDescriptorBuilder) {
        Intrinsics.checkNotNullParameter(classSerialDescriptorBuilder, "<this>");
        return Unit.INSTANCE;
    }

    public static final SerialDescriptor buildClassSerialDescriptor(String serialName, SerialDescriptor[] typeParameters, Function1<? super ClassSerialDescriptorBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        if (StringsKt.isBlank(serialName)) {
            throw new IllegalArgumentException("Blank serial names are prohibited".toString());
        }
        ClassSerialDescriptorBuilder classSerialDescriptorBuilder = new ClassSerialDescriptorBuilder(serialName);
        builderAction.invoke(classSerialDescriptorBuilder);
        return new SerialDescriptorImpl(serialName, StructureKind.CLASS.INSTANCE, classSerialDescriptorBuilder.getElementNames$kotlinx_serialization_core().size(), ArraysKt.toList(typeParameters), classSerialDescriptorBuilder);
    }

    public static final SerialDescriptor PrimitiveSerialDescriptor(String serialName, PrimitiveKind kind) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (StringsKt.isBlank(serialName)) {
            throw new IllegalArgumentException("Blank serial names are prohibited".toString());
        }
        return PrimitivesKt.PrimitiveDescriptorSafe(serialName, kind);
    }

    @ExperimentalSerializationApi
    public static final SerialDescriptor SerialDescriptor(String serialName, SerialDescriptor original) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(original, "original");
        if (StringsKt.isBlank(serialName)) {
            throw new IllegalArgumentException("Blank serial names are prohibited".toString());
        }
        if (original.getKind() instanceof PrimitiveKind) {
            throw new IllegalArgumentException("For primitive descriptors please use 'PrimitiveSerialDescriptor' instead".toString());
        }
        if (Intrinsics.areEqual(serialName, original.getSerialName())) {
            throw new IllegalArgumentException(("The name of the wrapped descriptor (" + serialName + ") cannot be the same as the name of the original descriptor (" + original.getSerialName() + CoreConstants.RIGHT_PARENTHESIS_CHAR).toString());
        }
        return new WrappedSerialDescriptor(serialName, original);
    }

    public static /* synthetic */ SerialDescriptor buildSerialDescriptor$default(String str, SerialKind serialKind, SerialDescriptor[] serialDescriptorArr, Function1 function1, int i, Object obj) {
        if ((i & 8) != 0) {
            function1 = new Function1() { // from class: kotlinx.serialization.descriptors.SerialDescriptorsKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return SerialDescriptorsKt.buildSerialDescriptor$lambda$6((ClassSerialDescriptorBuilder) obj2);
                }
            };
        }
        return buildSerialDescriptor(str, serialKind, serialDescriptorArr, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSerialDescriptor$lambda$6(ClassSerialDescriptorBuilder classSerialDescriptorBuilder) {
        Intrinsics.checkNotNullParameter(classSerialDescriptorBuilder, "<this>");
        return Unit.INSTANCE;
    }

    @InternalSerializationApi
    public static final SerialDescriptor buildSerialDescriptor(String serialName, SerialKind kind, SerialDescriptor[] typeParameters, Function1<? super ClassSerialDescriptorBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(builder, "builder");
        if (StringsKt.isBlank(serialName)) {
            throw new IllegalArgumentException("Blank serial names are prohibited".toString());
        }
        if (Intrinsics.areEqual(kind, StructureKind.CLASS.INSTANCE)) {
            throw new IllegalArgumentException("For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead".toString());
        }
        ClassSerialDescriptorBuilder classSerialDescriptorBuilder = new ClassSerialDescriptorBuilder(serialName);
        builder.invoke(classSerialDescriptorBuilder);
        return new SerialDescriptorImpl(serialName, kind, classSerialDescriptorBuilder.getElementNames$kotlinx_serialization_core().size(), ArraysKt.toList(typeParameters), classSerialDescriptorBuilder);
    }

    public static final /* synthetic */ <T> SerialDescriptor serialDescriptor() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return SerializersKt.serializer((KType) null).getDescriptor();
    }

    public static final SerialDescriptor serialDescriptor(KType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return SerializersKt.serializer(type).getDescriptor();
    }

    @ExperimentalSerializationApi
    public static final SerialDescriptor listSerialDescriptor(SerialDescriptor elementDescriptor) {
        Intrinsics.checkNotNullParameter(elementDescriptor, "elementDescriptor");
        return new ArrayListClassDesc(elementDescriptor);
    }

    @ExperimentalSerializationApi
    public static final /* synthetic */ <T> SerialDescriptor listSerialDescriptor() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return listSerialDescriptor(SerializersKt.serializer((KType) null).getDescriptor());
    }

    @ExperimentalSerializationApi
    public static final SerialDescriptor mapSerialDescriptor(SerialDescriptor keyDescriptor, SerialDescriptor valueDescriptor) {
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueDescriptor, "valueDescriptor");
        return new HashMapClassDesc(keyDescriptor, valueDescriptor);
    }

    @ExperimentalSerializationApi
    public static final /* synthetic */ <K, V> SerialDescriptor mapSerialDescriptor() {
        Intrinsics.reifiedOperationMarker(6, "K");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        SerialDescriptor descriptor = SerializersKt.serializer((KType) null).getDescriptor();
        Intrinsics.reifiedOperationMarker(6, "V");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return mapSerialDescriptor(descriptor, SerializersKt.serializer((KType) null).getDescriptor());
    }

    @ExperimentalSerializationApi
    public static final SerialDescriptor setSerialDescriptor(SerialDescriptor elementDescriptor) {
        Intrinsics.checkNotNullParameter(elementDescriptor, "elementDescriptor");
        return new HashSetClassDesc(elementDescriptor);
    }

    @ExperimentalSerializationApi
    public static final /* synthetic */ <T> SerialDescriptor setSerialDescriptor() {
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return setSerialDescriptor(SerializersKt.serializer((KType) null).getDescriptor());
    }

    public static final SerialDescriptor getNullable(SerialDescriptor serialDescriptor) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        return serialDescriptor.isNullable() ? serialDescriptor : new SerialDescriptorForNullable(serialDescriptor);
    }

    public static final SerialDescriptor getNonNullOriginal(SerialDescriptor serialDescriptor) {
        Intrinsics.checkNotNullParameter(serialDescriptor, "<this>");
        return serialDescriptor instanceof SerialDescriptorForNullable ? ((SerialDescriptorForNullable) serialDescriptor).getOriginal() : serialDescriptor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void element$default(ClassSerialDescriptorBuilder classSerialDescriptorBuilder, String elementName, List annotations, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            annotations = CollectionsKt.emptyList();
        }
        if ((i & 4) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(classSerialDescriptorBuilder, "<this>");
        Intrinsics.checkNotNullParameter(elementName, "elementName");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        classSerialDescriptorBuilder.element(elementName, SerializersKt.serializer((KType) null).getDescriptor(), annotations, z);
    }

    public static final /* synthetic */ <T> void element(ClassSerialDescriptorBuilder classSerialDescriptorBuilder, String elementName, List<? extends Annotation> annotations, boolean z) {
        Intrinsics.checkNotNullParameter(classSerialDescriptorBuilder, "<this>");
        Intrinsics.checkNotNullParameter(elementName, "elementName");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.reifiedOperationMarker(6, ExifInterface.GPS_DIRECTION_TRUE);
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        classSerialDescriptorBuilder.element(elementName, SerializersKt.serializer((KType) null).getDescriptor(), annotations, z);
    }
}
