package kotlin.reflect.jvm.internal.impl.km.internal;

import java.util.Iterator;
import java.util.List;
import kotlin.DeprecationLevel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.InconsistentKotlinMetadataException;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotation;
import kotlin.reflect.jvm.internal.impl.km.KmClass;
import kotlin.reflect.jvm.internal.impl.km.KmClassifier;
import kotlin.reflect.jvm.internal.impl.km.KmConstantValue;
import kotlin.reflect.jvm.internal.impl.km.KmConstructor;
import kotlin.reflect.jvm.internal.impl.km.KmContract;
import kotlin.reflect.jvm.internal.impl.km.KmDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.km.KmEffect;
import kotlin.reflect.jvm.internal.impl.km.KmEffectExpression;
import kotlin.reflect.jvm.internal.impl.km.KmEffectInvocationKind;
import kotlin.reflect.jvm.internal.impl.km.KmEffectType;
import kotlin.reflect.jvm.internal.impl.km.KmEnumEntry;
import kotlin.reflect.jvm.internal.impl.km.KmFlexibleTypeUpperBound;
import kotlin.reflect.jvm.internal.impl.km.KmFunction;
import kotlin.reflect.jvm.internal.impl.km.KmProperty;
import kotlin.reflect.jvm.internal.impl.km.KmType;
import kotlin.reflect.jvm.internal.impl.km.KmTypeAlias;
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
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;

/* JADX INFO: compiled from: Readers.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ReadersKt {

    /* JADX INFO: compiled from: Readers.kt */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;
        public static final /* synthetic */ int[] $EnumSwitchMapping$5;
        public static final /* synthetic */ int[] $EnumSwitchMapping$6;

        static {
            int[] iArr = new int[ProtoBuf.TypeParameter.Variance.values().length];
            try {
                iArr[ProtoBuf.TypeParameter.Variance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.TypeParameter.Variance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.TypeParameter.Variance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProtoBuf.Type.Argument.Projection.values().length];
            try {
                iArr2[ProtoBuf.Type.Argument.Projection.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProtoBuf.Type.Argument.Projection.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProtoBuf.Type.Argument.Projection.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ProtoBuf.Type.Argument.Projection.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[ProtoBuf.VersionRequirement.VersionKind.values().length];
            try {
                iArr3[ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr3[ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[ProtoBuf.VersionRequirement.VersionKind.API_VERSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[DeprecationLevel.values().length];
            try {
                iArr4[DeprecationLevel.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr4[DeprecationLevel.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr4[DeprecationLevel.HIDDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$3 = iArr4;
            int[] iArr5 = new int[ProtoBuf.Effect.EffectType.values().length];
            try {
                iArr5[ProtoBuf.Effect.EffectType.RETURNS_CONSTANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr5[ProtoBuf.Effect.EffectType.CALLS.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr5[ProtoBuf.Effect.EffectType.RETURNS_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            $EnumSwitchMapping$4 = iArr5;
            int[] iArr6 = new int[ProtoBuf.Effect.InvocationKind.values().length];
            try {
                iArr6[ProtoBuf.Effect.InvocationKind.AT_MOST_ONCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr6[ProtoBuf.Effect.InvocationKind.EXACTLY_ONCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr6[ProtoBuf.Effect.InvocationKind.AT_LEAST_ONCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused19) {
            }
            $EnumSwitchMapping$5 = iArr6;
            int[] iArr7 = new int[ProtoBuf.Expression.ConstantValue.values().length];
            try {
                iArr7[ProtoBuf.Expression.ConstantValue.TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr7[ProtoBuf.Expression.ConstantValue.FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr7[ProtoBuf.Expression.ConstantValue.NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused22) {
            }
            $EnumSwitchMapping$6 = iArr7;
        }
    }

    public static /* synthetic */ KmClass toKmClass$default(ProtoBuf.Class r0, NameResolver nameResolver, boolean z, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        return toKmClass(r0, nameResolver, z, list);
    }

    public static final KmClass toKmClass(ProtoBuf.Class r10, NameResolver strings, boolean z, List<? extends Object> contextExtensions) {
        Intrinsics.checkNotNullParameter(r10, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(contextExtensions, "contextExtensions");
        KmClass kmClass = new KmClass();
        ProtoBuf.TypeTable typeTable = r10.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
        TypeTable typeTable2 = new TypeTable(typeTable);
        VersionRequirementTable.Companion companion = VersionRequirementTable.Companion;
        ProtoBuf.VersionRequirementTable versionRequirementTable = r10.getVersionRequirementTable();
        Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
        ReadContext readContext = new ReadContext(strings, typeTable2, companion.create(versionRequirementTable), z, null, contextExtensions, 16, null);
        List<ProtoBuf.TypeParameter> typeParameterList = r10.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ReadContext readContextWithTypeParameters$kotlin_metadata = readContext.withTypeParameters$kotlin_metadata(typeParameterList);
        kmClass.setFlags$kotlin_metadata(r10.getFlags());
        kmClass.setName(readContextWithTypeParameters$kotlin_metadata.className$kotlin_metadata(r10.getFqName()));
        List<ProtoBuf.TypeParameter> typeParameterList2 = r10.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList2, "getTypeParameterList(...)");
        List<KmTypeParameter> typeParameters = kmClass.getTypeParameters();
        for (ProtoBuf.TypeParameter typeParameter : typeParameterList2) {
            Intrinsics.checkNotNull(typeParameter);
            typeParameters.add(toKmTypeParameter(typeParameter, readContextWithTypeParameters$kotlin_metadata));
        }
        List<ProtoBuf.Type> listSupertypes = ProtoTypeTableUtilKt.supertypes(r10, readContextWithTypeParameters$kotlin_metadata.getTypes());
        List<KmType> supertypes = kmClass.getSupertypes();
        Iterator<T> it = listSupertypes.iterator();
        while (it.hasNext()) {
            supertypes.add(toKmType((ProtoBuf.Type) it.next(), readContextWithTypeParameters$kotlin_metadata));
        }
        List<ProtoBuf.Constructor> constructorList = r10.getConstructorList();
        Intrinsics.checkNotNullExpressionValue(constructorList, "getConstructorList(...)");
        List<KmConstructor> constructors = kmClass.getConstructors();
        for (ProtoBuf.Constructor constructor : constructorList) {
            Intrinsics.checkNotNull(constructor);
            constructors.add(toKmConstructor(constructor, readContextWithTypeParameters$kotlin_metadata));
        }
        List<ProtoBuf.Function> functionList = r10.getFunctionList();
        Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
        List<ProtoBuf.Property> propertyList = r10.getPropertyList();
        Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
        List<ProtoBuf.TypeAlias> typeAliasList = r10.getTypeAliasList();
        Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
        visitDeclarations(kmClass, functionList, propertyList, typeAliasList, readContextWithTypeParameters$kotlin_metadata);
        if (r10.hasCompanionObjectName()) {
            kmClass.setCompanionObject(readContextWithTypeParameters$kotlin_metadata.get(r10.getCompanionObjectName()));
        }
        List<Integer> nestedClassNameList = r10.getNestedClassNameList();
        Intrinsics.checkNotNullExpressionValue(nestedClassNameList, "getNestedClassNameList(...)");
        List<String> nestedClasses = kmClass.getNestedClasses();
        for (Integer num : nestedClassNameList) {
            Intrinsics.checkNotNull(num);
            nestedClasses.add(readContextWithTypeParameters$kotlin_metadata.get(num.intValue()));
        }
        Iterator<ProtoBuf.EnumEntry> it2 = r10.getEnumEntryList().iterator();
        while (true) {
            if (it2.hasNext()) {
                ProtoBuf.EnumEntry next = it2.next();
                if (!next.hasName()) {
                    throw new InconsistentKotlinMetadataException("No name for EnumEntry", null, 2, null);
                }
                kmClass.getEnumEntries().add(readContextWithTypeParameters$kotlin_metadata.get(next.getName()));
                List<KmEnumEntry> kmEnumEntries = kmClass.getKmEnumEntries();
                Intrinsics.checkNotNull(next);
                kmEnumEntries.add(toKmEnumEntry(next, readContextWithTypeParameters$kotlin_metadata));
            } else {
                List<Integer> sealedSubclassFqNameList = r10.getSealedSubclassFqNameList();
                Intrinsics.checkNotNullExpressionValue(sealedSubclassFqNameList, "getSealedSubclassFqNameList(...)");
                List<String> sealedSubclasses = kmClass.getSealedSubclasses();
                for (Integer num2 : sealedSubclassFqNameList) {
                    Intrinsics.checkNotNull(num2);
                    sealedSubclasses.add(readContextWithTypeParameters$kotlin_metadata.className$kotlin_metadata(num2.intValue()));
                }
                if (r10.hasInlineClassUnderlyingPropertyName()) {
                    kmClass.setInlineClassUnderlyingPropertyName(readContextWithTypeParameters$kotlin_metadata.get(r10.getInlineClassUnderlyingPropertyName()));
                }
                ProtoBuf.Type typeLoadInlineClassUnderlyingType = loadInlineClassUnderlyingType(r10, readContextWithTypeParameters$kotlin_metadata);
                kmClass.setInlineClassUnderlyingType(typeLoadInlineClassUnderlyingType != null ? toKmType(typeLoadInlineClassUnderlyingType, readContextWithTypeParameters$kotlin_metadata) : null);
                List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(r10, readContextWithTypeParameters$kotlin_metadata.getTypes());
                List<KmType> contextReceiverTypes = kmClass.getContextReceiverTypes();
                Iterator<T> it3 = listContextReceiverTypes.iterator();
                while (it3.hasNext()) {
                    contextReceiverTypes.add(toKmType((ProtoBuf.Type) it3.next(), readContextWithTypeParameters$kotlin_metadata));
                }
                List<Integer> versionRequirementList = r10.getVersionRequirementList();
                Intrinsics.checkNotNullExpressionValue(versionRequirementList, "getVersionRequirementList(...)");
                List<KmVersionRequirement> versionRequirements = kmClass.getVersionRequirements();
                for (Integer num3 : versionRequirementList) {
                    Intrinsics.checkNotNull(num3);
                    versionRequirements.add(readVersionRequirement(num3.intValue(), readContextWithTypeParameters$kotlin_metadata));
                }
                Iterator<T> it4 = readContextWithTypeParameters$kotlin_metadata.getExtensions$kotlin_metadata().iterator();
                while (it4.hasNext()) {
                    ((MetadataExtensions) it4.next()).readClassExtensions(kmClass, r10, readContextWithTypeParameters$kotlin_metadata);
                }
                return kmClass;
            }
        }
    }

    private static final KmEnumEntry toKmEnumEntry(ProtoBuf.EnumEntry enumEntry, ReadContext readContext) {
        KmEnumEntry kmEnumEntry = new KmEnumEntry(readContext.get(enumEntry.getName()));
        Iterator<T> it = readContext.getExtensions$kotlin_metadata().iterator();
        while (it.hasNext()) {
            ((MetadataExtensions) it.next()).readEnumEntryExtensions(kmEnumEntry, enumEntry, readContext);
        }
        return kmEnumEntry;
    }

    private static final ProtoBuf.Type loadInlineClassUnderlyingType(ProtoBuf.Class r7, ReadContext readContext) {
        ProtoBuf.Type typeInlineClassUnderlyingType = ProtoTypeTableUtilKt.inlineClassUnderlyingType(r7, readContext.getTypes());
        if (typeInlineClassUnderlyingType != null) {
            return typeInlineClassUnderlyingType;
        }
        if (!r7.hasInlineClassUnderlyingPropertyName()) {
            return null;
        }
        List<ProtoBuf.Property> propertyList = r7.getPropertyList();
        Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
        Iterator<T> it = propertyList.iterator();
        boolean z = false;
        Object obj = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            ProtoBuf.Property property = (ProtoBuf.Property) next;
            Intrinsics.checkNotNull(property);
            if (ProtoTypeTableUtilKt.receiverType(property, readContext.getTypes()) == null && Intrinsics.areEqual(readContext.get(property.getName()), readContext.get(r7.getInlineClassUnderlyingPropertyName()))) {
                if (!z) {
                    z = true;
                    obj = next;
                }
            }
            obj = null;
            break;
        }
        ProtoBuf.Property property2 = (ProtoBuf.Property) obj;
        if (property2 != null) {
            return ProtoTypeTableUtilKt.returnType(property2, readContext.getTypes());
        }
        return null;
    }

    private static final void visitDeclarations(KmDeclarationContainer kmDeclarationContainer, List<ProtoBuf.Function> list, List<ProtoBuf.Property> list2, List<ProtoBuf.TypeAlias> list3, ReadContext readContext) {
        List<KmFunction> functions = kmDeclarationContainer.getFunctions();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            functions.add(toKmFunction((ProtoBuf.Function) it.next(), readContext));
        }
        List<KmProperty> properties = kmDeclarationContainer.getProperties();
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            properties.add(toKmProperty((ProtoBuf.Property) it2.next(), readContext));
        }
        List<KmTypeAlias> typeAliases = kmDeclarationContainer.getTypeAliases();
        Iterator<T> it3 = list3.iterator();
        while (it3.hasNext()) {
            typeAliases.add(toKmTypeAlias((ProtoBuf.TypeAlias) it3.next(), readContext));
        }
    }

    private static final KmConstructor toKmConstructor(ProtoBuf.Constructor constructor, ReadContext readContext) {
        KmConstructor kmConstructor = new KmConstructor(constructor.getFlags());
        List<ProtoBuf.ValueParameter> valueParameterList = constructor.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        List<KmValueParameter> valueParameters = kmConstructor.getValueParameters();
        for (ProtoBuf.ValueParameter valueParameter : valueParameterList) {
            Intrinsics.checkNotNull(valueParameter);
            valueParameters.add(toKmValueParameter(valueParameter, readContext));
        }
        List<Integer> versionRequirementList = constructor.getVersionRequirementList();
        Intrinsics.checkNotNullExpressionValue(versionRequirementList, "getVersionRequirementList(...)");
        List<KmVersionRequirement> versionRequirements = kmConstructor.getVersionRequirements();
        for (Integer num : versionRequirementList) {
            Intrinsics.checkNotNull(num);
            versionRequirements.add(readVersionRequirement(num.intValue(), readContext));
        }
        Iterator<T> it = readContext.getExtensions$kotlin_metadata().iterator();
        while (it.hasNext()) {
            ((MetadataExtensions) it.next()).readConstructorExtensions(kmConstructor, constructor, readContext);
        }
        return kmConstructor;
    }

    private static final KmFunction toKmFunction(ProtoBuf.Function function, ReadContext readContext) {
        KmFunction kmFunction = new KmFunction(function.getFlags(), readContext.get(function.getName()));
        List<ProtoBuf.TypeParameter> typeParameterList = function.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ReadContext readContextWithTypeParameters$kotlin_metadata = readContext.withTypeParameters$kotlin_metadata(typeParameterList);
        List<ProtoBuf.TypeParameter> typeParameterList2 = function.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList2, "getTypeParameterList(...)");
        List<KmTypeParameter> typeParameters = kmFunction.getTypeParameters();
        for (ProtoBuf.TypeParameter typeParameter : typeParameterList2) {
            Intrinsics.checkNotNull(typeParameter);
            typeParameters.add(toKmTypeParameter(typeParameter, readContextWithTypeParameters$kotlin_metadata));
        }
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(function, readContextWithTypeParameters$kotlin_metadata.getTypes());
        kmFunction.setReceiverParameterType(typeReceiverType != null ? toKmType(typeReceiverType, readContextWithTypeParameters$kotlin_metadata) : null);
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(function, readContextWithTypeParameters$kotlin_metadata.getTypes());
        List<KmType> contextReceiverTypes = kmFunction.getContextReceiverTypes();
        Iterator<T> it = listContextReceiverTypes.iterator();
        while (it.hasNext()) {
            contextReceiverTypes.add(toKmType((ProtoBuf.Type) it.next(), readContextWithTypeParameters$kotlin_metadata));
        }
        List<ProtoBuf.ValueParameter> valueParameterList = function.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
        List<KmValueParameter> valueParameters = kmFunction.getValueParameters();
        for (ProtoBuf.ValueParameter valueParameter : valueParameterList) {
            Intrinsics.checkNotNull(valueParameter);
            valueParameters.add(toKmValueParameter(valueParameter, readContextWithTypeParameters$kotlin_metadata));
        }
        kmFunction.setReturnType(toKmType(ProtoTypeTableUtilKt.returnType(function, readContextWithTypeParameters$kotlin_metadata.getTypes()), readContextWithTypeParameters$kotlin_metadata));
        if (function.hasContract()) {
            ProtoBuf.Contract contract = function.getContract();
            Intrinsics.checkNotNullExpressionValue(contract, "getContract(...)");
            kmFunction.setContract(toKmContract(contract, readContextWithTypeParameters$kotlin_metadata));
        }
        List<Integer> versionRequirementList = function.getVersionRequirementList();
        Intrinsics.checkNotNullExpressionValue(versionRequirementList, "getVersionRequirementList(...)");
        List<KmVersionRequirement> versionRequirements = kmFunction.getVersionRequirements();
        for (Integer num : versionRequirementList) {
            Intrinsics.checkNotNull(num);
            versionRequirements.add(readVersionRequirement(num.intValue(), readContextWithTypeParameters$kotlin_metadata));
        }
        Iterator<T> it2 = readContextWithTypeParameters$kotlin_metadata.getExtensions$kotlin_metadata().iterator();
        while (it2.hasNext()) {
            ((MetadataExtensions) it2.next()).readFunctionExtensions(kmFunction, function, readContextWithTypeParameters$kotlin_metadata);
        }
        return kmFunction;
    }

    public static final KmProperty toKmProperty(ProtoBuf.Property property, ReadContext outer) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        Intrinsics.checkNotNullParameter(outer, "outer");
        KmProperty kmProperty = new KmProperty(property.getFlags(), outer.get(property.getName()), getPropertyGetterFlags(property), getPropertySetterFlags(property));
        List<ProtoBuf.TypeParameter> typeParameterList = property.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ReadContext readContextWithTypeParameters$kotlin_metadata = outer.withTypeParameters$kotlin_metadata(typeParameterList);
        List<ProtoBuf.TypeParameter> typeParameterList2 = property.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList2, "getTypeParameterList(...)");
        List<KmTypeParameter> typeParameters = kmProperty.getTypeParameters();
        for (ProtoBuf.TypeParameter typeParameter : typeParameterList2) {
            Intrinsics.checkNotNull(typeParameter);
            typeParameters.add(toKmTypeParameter(typeParameter, readContextWithTypeParameters$kotlin_metadata));
        }
        ProtoBuf.Type typeReceiverType = ProtoTypeTableUtilKt.receiverType(property, readContextWithTypeParameters$kotlin_metadata.getTypes());
        kmProperty.setReceiverParameterType(typeReceiverType != null ? toKmType(typeReceiverType, readContextWithTypeParameters$kotlin_metadata) : null);
        List<ProtoBuf.Type> listContextReceiverTypes = ProtoTypeTableUtilKt.contextReceiverTypes(property, readContextWithTypeParameters$kotlin_metadata.getTypes());
        List<KmType> contextReceiverTypes = kmProperty.getContextReceiverTypes();
        Iterator<T> it = listContextReceiverTypes.iterator();
        while (it.hasNext()) {
            contextReceiverTypes.add(toKmType((ProtoBuf.Type) it.next(), readContextWithTypeParameters$kotlin_metadata));
        }
        if (property.hasSetterValueParameter()) {
            ProtoBuf.ValueParameter setterValueParameter = property.getSetterValueParameter();
            Intrinsics.checkNotNullExpressionValue(setterValueParameter, "getSetterValueParameter(...)");
            kmProperty.setSetterParameter(toKmValueParameter(setterValueParameter, readContextWithTypeParameters$kotlin_metadata));
        }
        kmProperty.setReturnType(toKmType(ProtoTypeTableUtilKt.returnType(property, readContextWithTypeParameters$kotlin_metadata.getTypes()), readContextWithTypeParameters$kotlin_metadata));
        List<Integer> versionRequirementList = property.getVersionRequirementList();
        Intrinsics.checkNotNullExpressionValue(versionRequirementList, "getVersionRequirementList(...)");
        List<KmVersionRequirement> versionRequirements = kmProperty.getVersionRequirements();
        for (Integer num : versionRequirementList) {
            Intrinsics.checkNotNull(num);
            versionRequirements.add(readVersionRequirement(num.intValue(), readContextWithTypeParameters$kotlin_metadata));
        }
        Iterator<T> it2 = readContextWithTypeParameters$kotlin_metadata.getExtensions$kotlin_metadata().iterator();
        while (it2.hasNext()) {
            ((MetadataExtensions) it2.next()).readPropertyExtensions(kmProperty, property, readContextWithTypeParameters$kotlin_metadata);
        }
        return kmProperty;
    }

    private static final KmTypeAlias toKmTypeAlias(ProtoBuf.TypeAlias typeAlias, ReadContext readContext) {
        KmTypeAlias kmTypeAlias = new KmTypeAlias(typeAlias.getFlags(), readContext.get(typeAlias.getName()));
        List<ProtoBuf.TypeParameter> typeParameterList = typeAlias.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
        ReadContext readContextWithTypeParameters$kotlin_metadata = readContext.withTypeParameters$kotlin_metadata(typeParameterList);
        List<ProtoBuf.TypeParameter> typeParameterList2 = typeAlias.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(typeParameterList2, "getTypeParameterList(...)");
        List<KmTypeParameter> typeParameters = kmTypeAlias.getTypeParameters();
        for (ProtoBuf.TypeParameter typeParameter : typeParameterList2) {
            Intrinsics.checkNotNull(typeParameter);
            typeParameters.add(toKmTypeParameter(typeParameter, readContextWithTypeParameters$kotlin_metadata));
        }
        kmTypeAlias.setUnderlyingType(toKmType(ProtoTypeTableUtilKt.underlyingType(typeAlias, readContextWithTypeParameters$kotlin_metadata.getTypes()), readContextWithTypeParameters$kotlin_metadata));
        kmTypeAlias.setExpandedType(toKmType(ProtoTypeTableUtilKt.expandedType(typeAlias, readContextWithTypeParameters$kotlin_metadata.getTypes()), readContextWithTypeParameters$kotlin_metadata));
        List<ProtoBuf.Annotation> annotationList = typeAlias.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
        List<KmAnnotation> annotations = kmTypeAlias.getAnnotations();
        for (ProtoBuf.Annotation annotation : annotationList) {
            Intrinsics.checkNotNull(annotation);
            annotations.add(ReadUtilsKt.readAnnotation(annotation, readContextWithTypeParameters$kotlin_metadata.getStrings()));
        }
        List<Integer> versionRequirementList = typeAlias.getVersionRequirementList();
        Intrinsics.checkNotNullExpressionValue(versionRequirementList, "getVersionRequirementList(...)");
        List<KmVersionRequirement> versionRequirements = kmTypeAlias.getVersionRequirements();
        for (Integer num : versionRequirementList) {
            Intrinsics.checkNotNull(num);
            versionRequirements.add(readVersionRequirement(num.intValue(), readContextWithTypeParameters$kotlin_metadata));
        }
        Iterator<T> it = readContextWithTypeParameters$kotlin_metadata.getExtensions$kotlin_metadata().iterator();
        while (it.hasNext()) {
            ((MetadataExtensions) it.next()).readTypeAliasExtensions(kmTypeAlias, typeAlias, readContextWithTypeParameters$kotlin_metadata);
        }
        return kmTypeAlias;
    }

    private static final KmValueParameter toKmValueParameter(ProtoBuf.ValueParameter valueParameter, ReadContext readContext) {
        KmValueParameter kmValueParameter = new KmValueParameter(valueParameter.getFlags(), readContext.get(valueParameter.getName()));
        kmValueParameter.setType(toKmType(ProtoTypeTableUtilKt.type(valueParameter, readContext.getTypes()), readContext));
        ProtoBuf.Type typeVarargElementType = ProtoTypeTableUtilKt.varargElementType(valueParameter, readContext.getTypes());
        kmValueParameter.setVarargElementType(typeVarargElementType != null ? toKmType(typeVarargElementType, readContext) : null);
        if (valueParameter.hasAnnotationParameterDefaultValue()) {
            ProtoBuf.Annotation.Argument.Value annotationParameterDefaultValue = valueParameter.getAnnotationParameterDefaultValue();
            Intrinsics.checkNotNullExpressionValue(annotationParameterDefaultValue, "getAnnotationParameterDefaultValue(...)");
            kmValueParameter.setAnnotationParameterDefaultValue(ReadUtilsKt.readAnnotationArgument(annotationParameterDefaultValue, readContext.getStrings()));
        }
        Iterator<T> it = readContext.getExtensions$kotlin_metadata().iterator();
        while (it.hasNext()) {
            ((MetadataExtensions) it.next()).readValueParameterExtensions(kmValueParameter, valueParameter, readContext);
        }
        return kmValueParameter;
    }

    private static final KmTypeParameter toKmTypeParameter(ProtoBuf.TypeParameter typeParameter, ReadContext readContext) {
        KmVariance kmVariance;
        ProtoBuf.TypeParameter.Variance variance = typeParameter.getVariance();
        if (variance == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
        if (i == 1) {
            kmVariance = KmVariance.IN;
        } else if (i == 2) {
            kmVariance = KmVariance.OUT;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            kmVariance = KmVariance.INVARIANT;
        }
        KmTypeParameter kmTypeParameter = new KmTypeParameter(getTypeParameterFlags(typeParameter), readContext.get(typeParameter.getName()), typeParameter.getId(), kmVariance);
        List<ProtoBuf.Type> listUpperBounds = ProtoTypeTableUtilKt.upperBounds(typeParameter, readContext.getTypes());
        List<KmType> upperBounds = kmTypeParameter.getUpperBounds();
        Iterator<T> it = listUpperBounds.iterator();
        while (it.hasNext()) {
            upperBounds.add(toKmType((ProtoBuf.Type) it.next(), readContext));
        }
        Iterator<T> it2 = readContext.getExtensions$kotlin_metadata().iterator();
        while (it2.hasNext()) {
            ((MetadataExtensions) it2.next()).readTypeParameterExtensions(kmTypeParameter, typeParameter, readContext);
        }
        return kmTypeParameter;
    }

    private static final KmType toKmType(ProtoBuf.Type type, ReadContext readContext) {
        KmClassifier.TypeParameter typeParameter;
        KmType kmType;
        KmVariance kmVariance;
        KmType kmType2 = new KmType(getTypeFlags(type));
        KmFlexibleTypeUpperBound kmFlexibleTypeUpperBound = null;
        kmFlexibleTypeUpperBound = null;
        if (type.hasClassName()) {
            typeParameter = new KmClassifier.Class(readContext.className$kotlin_metadata(type.getClassName()));
        } else if (type.hasTypeAliasName()) {
            typeParameter = new KmClassifier.TypeAlias(readContext.className$kotlin_metadata(type.getTypeAliasName()));
        } else if (type.hasTypeParameter()) {
            typeParameter = new KmClassifier.TypeParameter(type.getTypeParameter());
        } else if (type.hasTypeParameterName()) {
            Integer typeParameterId$kotlin_metadata = readContext.getTypeParameterId$kotlin_metadata(type.getTypeParameterName());
            if (typeParameterId$kotlin_metadata == null) {
                throw new InconsistentKotlinMetadataException("No type parameter id for " + readContext.get(type.getTypeParameterName()), null, 2, null);
            }
            typeParameter = new KmClassifier.TypeParameter(typeParameterId$kotlin_metadata.intValue());
        } else {
            throw new InconsistentKotlinMetadataException("No classifier (class, type alias or type parameter) recorded for Type", null, 2, null);
        }
        kmType2.setClassifier(typeParameter);
        for (ProtoBuf.Type.Argument argument : type.getArgumentList()) {
            ProtoBuf.Type.Argument.Projection projection = argument.getProjection();
            if (projection == null) {
                throw new IllegalArgumentException("Required value was null.".toString());
            }
            int i = WhenMappings.$EnumSwitchMapping$1[projection.ordinal()];
            if (i == 1) {
                kmVariance = KmVariance.IN;
            } else if (i == 2) {
                kmVariance = KmVariance.OUT;
            } else if (i == 3) {
                kmVariance = KmVariance.INVARIANT;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                kmVariance = null;
            }
            if (kmVariance != null) {
                Intrinsics.checkNotNull(argument);
                ProtoBuf.Type type2 = ProtoTypeTableUtilKt.type(argument, readContext.getTypes());
                if (type2 == null) {
                    throw new InconsistentKotlinMetadataException("No type argument for non-STAR projection in Type", null, 2, null);
                }
                kmType2.getArguments().add(new KmTypeProjection(kmVariance, toKmType(type2, readContext)));
            } else {
                kmType2.getArguments().add(KmTypeProjection.STAR);
            }
        }
        ProtoBuf.Type typeAbbreviatedType = ProtoTypeTableUtilKt.abbreviatedType(type, readContext.getTypes());
        kmType2.setAbbreviatedType(typeAbbreviatedType != null ? toKmType(typeAbbreviatedType, readContext) : null);
        ProtoBuf.Type typeOuterType = ProtoTypeTableUtilKt.outerType(type, readContext.getTypes());
        kmType2.setOuterType(typeOuterType != null ? toKmType(typeOuterType, readContext) : null);
        ProtoBuf.Type typeFlexibleUpperBound = ProtoTypeTableUtilKt.flexibleUpperBound(type, readContext.getTypes());
        if (typeFlexibleUpperBound != null && (kmType = toKmType(typeFlexibleUpperBound, readContext)) != null) {
            kmFlexibleTypeUpperBound = new KmFlexibleTypeUpperBound(kmType, type.hasFlexibleTypeCapabilitiesId() ? readContext.get(type.getFlexibleTypeCapabilitiesId()) : null);
        }
        kmType2.setFlexibleTypeUpperBound(kmFlexibleTypeUpperBound);
        Iterator<T> it = readContext.getExtensions$kotlin_metadata().iterator();
        while (it.hasNext()) {
            ((MetadataExtensions) it.next()).readTypeExtensions(kmType2, type, readContext);
        }
        return kmType2;
    }

    private static final KmVersionRequirement readVersionRequirement(int i, ReadContext readContext) {
        KmVersionRequirementVersionKind kmVersionRequirementVersionKind;
        KmVersionRequirementLevel kmVersionRequirementLevel;
        VersionRequirement.Version version;
        KmVersionRequirement kmVersionRequirement = new KmVersionRequirement();
        VersionRequirement versionRequirementCreate = VersionRequirement.Companion.create(i, readContext.getStrings(), readContext.getVersionRequirements$kotlin_metadata());
        if (versionRequirementCreate == null && !readContext.getIgnoreUnknownVersionRequirements$kotlin_metadata()) {
            throw new InconsistentKotlinMetadataException("No VersionRequirement with the given id in the table", null, 2, null);
        }
        ProtoBuf.VersionRequirement.VersionKind kind = versionRequirementCreate != null ? versionRequirementCreate.getKind() : null;
        int i2 = kind == null ? -1 : WhenMappings.$EnumSwitchMapping$2[kind.ordinal()];
        if (i2 == -1) {
            kmVersionRequirementVersionKind = KmVersionRequirementVersionKind.UNKNOWN;
        } else if (i2 == 1) {
            kmVersionRequirementVersionKind = KmVersionRequirementVersionKind.LANGUAGE_VERSION;
        } else if (i2 == 2) {
            kmVersionRequirementVersionKind = KmVersionRequirementVersionKind.COMPILER_VERSION;
        } else {
            if (i2 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            kmVersionRequirementVersionKind = KmVersionRequirementVersionKind.API_VERSION;
        }
        DeprecationLevel level = versionRequirementCreate != null ? versionRequirementCreate.getLevel() : null;
        int i3 = level == null ? -1 : WhenMappings.$EnumSwitchMapping$3[level.ordinal()];
        if (i3 == -1) {
            kmVersionRequirementLevel = KmVersionRequirementLevel.HIDDEN;
        } else if (i3 == 1) {
            kmVersionRequirementLevel = KmVersionRequirementLevel.WARNING;
        } else if (i3 == 2) {
            kmVersionRequirementLevel = KmVersionRequirementLevel.ERROR;
        } else {
            if (i3 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            kmVersionRequirementLevel = KmVersionRequirementLevel.HIDDEN;
        }
        kmVersionRequirement.setKind(kmVersionRequirementVersionKind);
        kmVersionRequirement.setLevel(kmVersionRequirementLevel);
        kmVersionRequirement.setErrorCode(versionRequirementCreate != null ? versionRequirementCreate.getErrorCode() : null);
        kmVersionRequirement.setMessage(versionRequirementCreate != null ? versionRequirementCreate.getMessage() : null);
        if (versionRequirementCreate == null || (version = versionRequirementCreate.getVersion()) == null) {
            version = VersionRequirement.Version.INFINITY;
        }
        kmVersionRequirement.setVersion(new KmVersion(version.component1(), version.component2(), version.component3()));
        return kmVersionRequirement;
    }

    private static final KmContract toKmContract(ProtoBuf.Contract contract, ReadContext readContext) {
        KmEffectType kmEffectType;
        KmEffectInvocationKind kmEffectInvocationKind;
        KmContract kmContract = new KmContract();
        for (ProtoBuf.Effect effect : contract.getEffectList()) {
            if (effect.hasEffectType()) {
                ProtoBuf.Effect.EffectType effectType = effect.getEffectType();
                if (effectType == null) {
                    throw new IllegalArgumentException("Required value was null.".toString());
                }
                int i = WhenMappings.$EnumSwitchMapping$4[effectType.ordinal()];
                if (i == 1) {
                    kmEffectType = KmEffectType.RETURNS_CONSTANT;
                } else if (i == 2) {
                    kmEffectType = KmEffectType.CALLS;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    kmEffectType = KmEffectType.RETURNS_NOT_NULL;
                }
                if (effect.hasKind()) {
                    ProtoBuf.Effect.InvocationKind kind = effect.getKind();
                    if (kind == null) {
                        throw new IllegalArgumentException("Required value was null.".toString());
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$5[kind.ordinal()];
                    if (i2 == 1) {
                        kmEffectInvocationKind = KmEffectInvocationKind.AT_MOST_ONCE;
                    } else if (i2 == 2) {
                        kmEffectInvocationKind = KmEffectInvocationKind.EXACTLY_ONCE;
                    } else {
                        if (i2 != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        kmEffectInvocationKind = KmEffectInvocationKind.AT_LEAST_ONCE;
                    }
                } else {
                    kmEffectInvocationKind = null;
                }
                List<KmEffect> effects = kmContract.getEffects();
                Intrinsics.checkNotNull(effect);
                effects.add(toKmEffect(effect, kmEffectType, kmEffectInvocationKind, readContext));
            }
        }
        return kmContract;
    }

    private static final KmEffect toKmEffect(ProtoBuf.Effect effect, KmEffectType kmEffectType, KmEffectInvocationKind kmEffectInvocationKind, ReadContext readContext) {
        KmEffect kmEffect = new KmEffect(kmEffectType, kmEffectInvocationKind);
        List<ProtoBuf.Expression> effectConstructorArgumentList = effect.getEffectConstructorArgumentList();
        Intrinsics.checkNotNullExpressionValue(effectConstructorArgumentList, "getEffectConstructorArgumentList(...)");
        List<KmEffectExpression> constructorArguments = kmEffect.getConstructorArguments();
        for (ProtoBuf.Expression expression : effectConstructorArgumentList) {
            Intrinsics.checkNotNull(expression);
            constructorArguments.add(toKmEffectExpression(expression, readContext));
        }
        if (effect.hasConclusionOfConditionalEffect()) {
            ProtoBuf.Expression conclusionOfConditionalEffect = effect.getConclusionOfConditionalEffect();
            Intrinsics.checkNotNullExpressionValue(conclusionOfConditionalEffect, "getConclusionOfConditionalEffect(...)");
            kmEffect.setConclusion(toKmEffectExpression(conclusionOfConditionalEffect, readContext));
        }
        return kmEffect;
    }

    private static final KmEffectExpression toKmEffectExpression(ProtoBuf.Expression expression, ReadContext readContext) {
        Boolean bool;
        KmEffectExpression kmEffectExpression = new KmEffectExpression();
        kmEffectExpression.setFlags$kotlin_metadata(expression.getFlags());
        kmEffectExpression.setParameterIndex(expression.hasValueParameterReference() ? Integer.valueOf(expression.getValueParameterReference()) : null);
        if (expression.hasConstantValue()) {
            ProtoBuf.Expression.ConstantValue constantValue = expression.getConstantValue();
            if (constantValue != null) {
                int i = WhenMappings.$EnumSwitchMapping$6[constantValue.ordinal()];
                if (i == 1) {
                    bool = true;
                } else if (i == 2) {
                    bool = false;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    bool = null;
                }
                kmEffectExpression.setConstantValue(new KmConstantValue(bool));
            } else {
                throw new IllegalArgumentException("Required value was null.".toString());
            }
        }
        ProtoBuf.Type typeIsInstanceType = ProtoTypeTableUtilKt.isInstanceType(expression, readContext.getTypes());
        kmEffectExpression.setInstanceType(typeIsInstanceType != null ? toKmType(typeIsInstanceType, readContext) : null);
        List<ProtoBuf.Expression> andArgumentList = expression.getAndArgumentList();
        Intrinsics.checkNotNullExpressionValue(andArgumentList, "getAndArgumentList(...)");
        List<KmEffectExpression> andArguments = kmEffectExpression.getAndArguments();
        for (ProtoBuf.Expression expression2 : andArgumentList) {
            Intrinsics.checkNotNull(expression2);
            andArguments.add(toKmEffectExpression(expression2, readContext));
        }
        List<ProtoBuf.Expression> orArgumentList = expression.getOrArgumentList();
        Intrinsics.checkNotNullExpressionValue(orArgumentList, "getOrArgumentList(...)");
        List<KmEffectExpression> orArguments = kmEffectExpression.getOrArguments();
        for (ProtoBuf.Expression expression3 : orArgumentList) {
            Intrinsics.checkNotNull(expression3);
            orArguments.add(toKmEffectExpression(expression3, readContext));
        }
        return kmEffectExpression;
    }

    private static final int getTypeFlags(ProtoBuf.Type type) {
        boolean nullable = type.getNullable();
        return (nullable ? 1 : 0) + (type.getFlags() << 1);
    }

    private static final int getTypeParameterFlags(ProtoBuf.TypeParameter typeParameter) {
        return typeParameter.getReified() ? 1 : 0;
    }

    public static final int getPropertyGetterFlags(ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        return property.hasGetterFlags() ? property.getGetterFlags() : getDefaultPropertyAccessorFlags(property.getFlags());
    }

    public static final int getPropertySetterFlags(ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        return property.hasSetterFlags() ? property.getSetterFlags() : getDefaultPropertyAccessorFlags(property.getFlags());
    }

    public static final int getDefaultPropertyAccessorFlags(int i) {
        Boolean bool = Flags.HAS_ANNOTATIONS.get(i);
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return Flags.getAccessorFlags(bool.booleanValue(), Flags.VISIBILITY.get(i), Flags.MODALITY.get(i), false, false, false);
    }
}
