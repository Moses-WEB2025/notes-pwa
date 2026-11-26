package kotlin.reflect.jvm.internal.impl.km.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.Attributes;
import kotlin.reflect.jvm.internal.impl.km.InconsistentKotlinMetadataException;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.km.KmClassifier;
import kotlin.reflect.jvm.internal.impl.km.KmEffectInvocationKind;
import kotlin.reflect.jvm.internal.impl.km.KmEffectType;
import kotlin.reflect.jvm.internal.impl.km.KmFlexibleTypeUpperBound;
import kotlin.reflect.jvm.internal.impl.km.KmProperty;
import kotlin.reflect.jvm.internal.impl.km.KmPropertyAccessorAttributes;
import kotlin.reflect.jvm.internal.impl.km.KmType;
import kotlin.reflect.jvm.internal.impl.km.KmTypeParameter;
import kotlin.reflect.jvm.internal.impl.km.KmTypeProjection;
import kotlin.reflect.jvm.internal.impl.km.KmValueParameter;
import kotlin.reflect.jvm.internal.impl.km.KmVariance;
import kotlin.reflect.jvm.internal.impl.km.KmVersion;
import kotlin.reflect.jvm.internal.impl.km.KmVersionRequirement;
import kotlin.reflect.jvm.internal.impl.km.KmVersionRequirementLevel;
import kotlin.reflect.jvm.internal.impl.km.KmVersionRequirementVersionKind;
import kotlin.reflect.jvm.internal.impl.km.internal.extensions.MetadataExtensions;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.serialization.MutableVersionRequirementTable;

/* JADX INFO: compiled from: Writers.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class WritersKt {

    /* JADX INFO: compiled from: Writers.kt */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;

        static {
            int[] iArr = new int[KmVersionRequirementVersionKind.values().length];
            try {
                iArr[KmVersionRequirementVersionKind.LANGUAGE_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KmVersionRequirementVersionKind.COMPILER_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KmVersionRequirementVersionKind.API_VERSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KmVersionRequirementVersionKind.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[KmVersionRequirementLevel.values().length];
            try {
                iArr2[KmVersionRequirementLevel.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[KmVersionRequirementLevel.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[KmVersionRequirementLevel.HIDDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[KmEffectType.values().length];
            try {
                iArr3[KmEffectType.RETURNS_CONSTANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr3[KmEffectType.CALLS.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[KmEffectType.RETURNS_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[KmEffectInvocationKind.values().length];
            try {
                iArr4[KmEffectInvocationKind.AT_MOST_ONCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr4[KmEffectInvocationKind.EXACTLY_ONCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr4[KmEffectInvocationKind.AT_LEAST_ONCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$3 = iArr4;
        }
    }

    private static final ProtoBuf.TypeParameter.Builder writeTypeParameter(WriteContext writeContext, KmTypeParameter kmTypeParameter) {
        ProtoBuf.TypeParameter.Builder builderNewBuilder = ProtoBuf.TypeParameter.newBuilder();
        Iterator<T> it = kmTypeParameter.getUpperBounds().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addUpperBound(writeType(writeContext, (KmType) it.next()).build());
        }
        for (MetadataExtensions metadataExtensions : writeContext.getExtensions$kotlin_metadata()) {
            Intrinsics.checkNotNull(builderNewBuilder);
            metadataExtensions.writeTypeParameterExtensions(kmTypeParameter, builderNewBuilder, writeContext);
        }
        builderNewBuilder.setName(writeContext.get(kmTypeParameter.getName()));
        builderNewBuilder.setId(kmTypeParameter.getId());
        boolean zIsReified = Attributes.isReified(kmTypeParameter);
        if (zIsReified != ProtoBuf.TypeParameter.getDefaultInstance().getReified()) {
            builderNewBuilder.setReified(zIsReified);
        }
        if (kmTypeParameter.getVariance() == KmVariance.IN) {
            builderNewBuilder.setVariance(ProtoBuf.TypeParameter.Variance.IN);
        } else if (kmTypeParameter.getVariance() == KmVariance.OUT) {
            builderNewBuilder.setVariance(ProtoBuf.TypeParameter.Variance.OUT);
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    private static final ProtoBuf.Type.Argument.Builder writeTypeProjection(WriteContext writeContext, KmTypeProjection kmTypeProjection) {
        ProtoBuf.Type.Argument.Builder builderNewBuilder = ProtoBuf.Type.Argument.newBuilder();
        if (Intrinsics.areEqual(kmTypeProjection, KmTypeProjection.STAR)) {
            builderNewBuilder.setProjection(ProtoBuf.Type.Argument.Projection.STAR);
        } else {
            KmVariance kmVarianceComponent1 = kmTypeProjection.component1();
            KmType kmTypeComponent2 = kmTypeProjection.component2();
            if (kmVarianceComponent1 == null || kmTypeComponent2 == null) {
                throw new InconsistentKotlinMetadataException("Variance and type must be set for non-star type projection", null, 2, null);
            }
            if (kmVarianceComponent1 == KmVariance.IN) {
                builderNewBuilder.setProjection(ProtoBuf.Type.Argument.Projection.IN);
            } else if (kmVarianceComponent1 == KmVariance.OUT) {
                builderNewBuilder.setProjection(ProtoBuf.Type.Argument.Projection.OUT);
            }
            builderNewBuilder.setType(writeType(writeContext, kmTypeComponent2).build());
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    private static final ProtoBuf.Type.Builder writeType(WriteContext writeContext, KmType kmType) {
        ProtoBuf.Type.Builder builderNewBuilder = ProtoBuf.Type.newBuilder();
        KmClassifier classifier = kmType.getClassifier();
        if (classifier instanceof KmClassifier.Class) {
            builderNewBuilder.setClassName(writeContext.getClassName$kotlin_metadata(((KmClassifier.Class) classifier).getName()));
        } else if (classifier instanceof KmClassifier.TypeAlias) {
            builderNewBuilder.setTypeAliasName(writeContext.getClassName$kotlin_metadata(((KmClassifier.TypeAlias) classifier).getName()));
        } else {
            if (!(classifier instanceof KmClassifier.TypeParameter)) {
                throw new NoWhenBranchMatchedException();
            }
            builderNewBuilder.setTypeParameter(((KmClassifier.TypeParameter) classifier).getId());
        }
        Iterator<T> it = kmType.getArguments().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addArgument(writeTypeProjection(writeContext, (KmTypeProjection) it.next()));
        }
        KmType abbreviatedType = kmType.getAbbreviatedType();
        if (abbreviatedType != null) {
            builderNewBuilder.setAbbreviatedType(writeType(writeContext, abbreviatedType).build());
        }
        KmType outerType = kmType.getOuterType();
        if (outerType != null) {
            builderNewBuilder.setOuterType(writeType(writeContext, outerType).build());
        }
        KmFlexibleTypeUpperBound flexibleTypeUpperBound = kmType.getFlexibleTypeUpperBound();
        if (flexibleTypeUpperBound != null) {
            ProtoBuf.Type.Builder builderWriteType = writeType(writeContext, flexibleTypeUpperBound.getType());
            String typeFlexibilityId = flexibleTypeUpperBound.getTypeFlexibilityId();
            if (typeFlexibilityId != null) {
                builderNewBuilder.setFlexibleTypeCapabilitiesId(writeContext.get(typeFlexibilityId));
            }
            builderNewBuilder.setFlexibleUpperBound(builderWriteType.build());
        }
        for (MetadataExtensions metadataExtensions : writeContext.getExtensions$kotlin_metadata()) {
            Intrinsics.checkNotNull(builderNewBuilder);
            metadataExtensions.writeTypeExtensions(kmType, builderNewBuilder, writeContext);
        }
        if (Attributes.isNullable(kmType)) {
            builderNewBuilder.setNullable(true);
        }
        int flags$kotlin_metadata = kmType.getFlags$kotlin_metadata() >> 1;
        if (flags$kotlin_metadata != ProtoBuf.Type.getDefaultInstance().getFlags()) {
            builderNewBuilder.setFlags(flags$kotlin_metadata);
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    public static final ProtoBuf.Property.Builder writeProperty(WriteContext writeContext, KmProperty kmProperty) {
        Intrinsics.checkNotNullParameter(writeContext, "<this>");
        Intrinsics.checkNotNullParameter(kmProperty, "kmProperty");
        ProtoBuf.Property.Builder builderNewBuilder = ProtoBuf.Property.newBuilder();
        Iterator<T> it = kmProperty.getTypeParameters().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addTypeParameter(writeTypeParameter(writeContext, (KmTypeParameter) it.next()).build());
        }
        KmType receiverParameterType = kmProperty.getReceiverParameterType();
        if (receiverParameterType != null) {
            builderNewBuilder.setReceiverType(writeType(writeContext, receiverParameterType).build());
        }
        List<KmType> contextReceiverTypes = kmProperty.getContextReceiverTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiverTypes, 10));
        Iterator<T> it2 = contextReceiverTypes.iterator();
        while (it2.hasNext()) {
            arrayList.add(writeType(writeContext, (KmType) it2.next()).build());
        }
        builderNewBuilder.addAllContextReceiverType(arrayList);
        KmValueParameter setterParameter = kmProperty.getSetterParameter();
        if (setterParameter != null) {
            builderNewBuilder.setSetterValueParameter(writeValueParameter(writeContext, setterParameter).build());
        }
        builderNewBuilder.setReturnType(writeType(writeContext, kmProperty.getReturnType()).build());
        List<KmVersionRequirement> versionRequirements = kmProperty.getVersionRequirements();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it3 = versionRequirements.iterator();
        while (it3.hasNext()) {
            Integer numWriteVersionRequirement = writeVersionRequirement(writeContext, (KmVersionRequirement) it3.next());
            if (numWriteVersionRequirement != null) {
                arrayList2.add(numWriteVersionRequirement);
            }
        }
        builderNewBuilder.addAllVersionRequirement(arrayList2);
        for (MetadataExtensions metadataExtensions : writeContext.getExtensions$kotlin_metadata()) {
            Intrinsics.checkNotNull(builderNewBuilder);
            metadataExtensions.writePropertyExtensions(kmProperty, builderNewBuilder, writeContext);
        }
        builderNewBuilder.setName(writeContext.get(kmProperty.getName()));
        int flags$kotlin_metadata = kmProperty.getFlags$kotlin_metadata() | Flags.HAS_ANNOTATIONS.toFlags(Boolean.valueOf(!kmProperty.getAnnotations().isEmpty()));
        if (flags$kotlin_metadata != ProtoBuf.Property.getDefaultInstance().getFlags()) {
            builderNewBuilder.setFlags(flags$kotlin_metadata);
        }
        builderNewBuilder.setGetterFlags(kmProperty.getGetter().getFlags$kotlin_metadata() | Flags.HAS_ANNOTATIONS.toFlags(Boolean.valueOf(!kmProperty.getGetter().getAnnotations().isEmpty())));
        KmPropertyAccessorAttributes setter = kmProperty.getSetter();
        if (setter != null) {
            builderNewBuilder.setSetterFlags(Flags.HAS_ANNOTATIONS.toFlags(Boolean.valueOf(!setter.getAnnotations().isEmpty())) | setter.getFlags$kotlin_metadata());
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    private static final ProtoBuf.ValueParameter.Builder writeValueParameter(WriteContext writeContext, KmValueParameter kmValueParameter) {
        ProtoBuf.ValueParameter.Builder builderNewBuilder = ProtoBuf.ValueParameter.newBuilder();
        builderNewBuilder.setType(writeType(writeContext, kmValueParameter.getType()).build());
        KmType varargElementType = kmValueParameter.getVarargElementType();
        if (varargElementType != null) {
            builderNewBuilder.setVarargElementType(writeType(writeContext, varargElementType).build());
        }
        KmAnnotationArgument annotationParameterDefaultValue = kmValueParameter.getAnnotationParameterDefaultValue();
        if (annotationParameterDefaultValue != null) {
            builderNewBuilder.setAnnotationParameterDefaultValue(WriteUtilsKt.writeAnnotationArgument(annotationParameterDefaultValue, writeContext.getStrings()).build());
        }
        for (MetadataExtensions metadataExtensions : writeContext.getExtensions$kotlin_metadata()) {
            Intrinsics.checkNotNull(builderNewBuilder);
            metadataExtensions.writeValueParameterExtensions(kmValueParameter, builderNewBuilder, writeContext);
        }
        int flags$kotlin_metadata = kmValueParameter.getFlags$kotlin_metadata() | Flags.HAS_ANNOTATIONS.toFlags(Boolean.valueOf(!kmValueParameter.getAnnotations().isEmpty()));
        if (flags$kotlin_metadata != ProtoBuf.ValueParameter.getDefaultInstance().getFlags()) {
            builderNewBuilder.setFlags(flags$kotlin_metadata);
        }
        builderNewBuilder.setName(writeContext.get(kmValueParameter.getName()));
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    private static final Integer writeVersionRequirement(WriteContext writeContext, KmVersionRequirement kmVersionRequirement) {
        ProtoBuf.VersionRequirement.VersionKind versionKind;
        ProtoBuf.VersionRequirement.Level level;
        KmVersionRequirementVersionKind kind = kmVersionRequirement.getKind();
        KmVersionRequirementLevel level2 = kmVersionRequirement.getLevel();
        Integer errorCode = kmVersionRequirement.getErrorCode();
        String message = kmVersionRequirement.getMessage();
        final ProtoBuf.VersionRequirement.Builder builderNewBuilder = ProtoBuf.VersionRequirement.newBuilder();
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            versionKind = ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION;
        } else if (i == 2) {
            versionKind = ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION;
        } else {
            if (i != 3) {
                if (i == 4) {
                    return null;
                }
                throw new NoWhenBranchMatchedException();
            }
            versionKind = ProtoBuf.VersionRequirement.VersionKind.API_VERSION;
        }
        if (versionKind != builderNewBuilder.getDefaultInstanceForType().getVersionKind()) {
            builderNewBuilder.setVersionKind(versionKind);
        }
        int i2 = WhenMappings.$EnumSwitchMapping$1[level2.ordinal()];
        if (i2 == 1) {
            level = ProtoBuf.VersionRequirement.Level.WARNING;
        } else if (i2 == 2) {
            level = ProtoBuf.VersionRequirement.Level.ERROR;
        } else {
            if (i2 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            level = ProtoBuf.VersionRequirement.Level.HIDDEN;
        }
        if (level != builderNewBuilder.getDefaultInstanceForType().getLevel()) {
            builderNewBuilder.setLevel(level);
        }
        if (errorCode != null) {
            builderNewBuilder.setErrorCode(errorCode.intValue());
        }
        if (message != null) {
            builderNewBuilder.setMessage(writeContext.get(message));
        }
        KmVersion version = kmVersionRequirement.getVersion();
        new VersionRequirement.Version(version.component1(), version.component2(), version.component3()).encode(new Function1(builderNewBuilder) { // from class: kotlin.reflect.jvm.internal.impl.km.internal.WritersKt$$Lambda$0
            private final ProtoBuf.VersionRequirement.Builder arg$0;

            {
                this.arg$0 = builderNewBuilder;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return WritersKt.writeVersionRequirement$lambda$32(this.arg$0, ((Number) obj).intValue());
            }
        }, new Function1(builderNewBuilder) { // from class: kotlin.reflect.jvm.internal.impl.km.internal.WritersKt$$Lambda$1
            private final ProtoBuf.VersionRequirement.Builder arg$0;

            {
                this.arg$0 = builderNewBuilder;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return WritersKt.writeVersionRequirement$lambda$33(this.arg$0, ((Number) obj).intValue());
            }
        });
        MutableVersionRequirementTable versionRequirements$kotlin_metadata = writeContext.getVersionRequirements$kotlin_metadata();
        Intrinsics.checkNotNull(builderNewBuilder);
        return Integer.valueOf(versionRequirements$kotlin_metadata.get(builderNewBuilder));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeVersionRequirement$lambda$32(ProtoBuf.VersionRequirement.Builder builder, int i) {
        Intrinsics.checkNotNull(builder);
        builder.setVersion(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit writeVersionRequirement$lambda$33(ProtoBuf.VersionRequirement.Builder builder, int i) {
        Intrinsics.checkNotNull(builder);
        builder.setVersionFull(i);
        return Unit.INSTANCE;
    }
}
