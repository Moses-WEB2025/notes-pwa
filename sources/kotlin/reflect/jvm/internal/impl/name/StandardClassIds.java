package kotlin.reflect.jvm.internal.impl.name;

import com.miui.maml.elements.FunctionElement;
import com.miui.maml.elements.ListScreenElement;
import com.miui.maml.elements.ScreenElementArray;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: StandardClassIds.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class StandardClassIds {
    private static final ClassId AbstractMap;
    private static final ClassId Annotation;
    private static final ClassId AnnotationRetention;
    private static final ClassId AnnotationTarget;
    private static final ClassId Any;
    private static final ClassId Array;
    private static final ClassId AtomicArray;
    private static final ClassId AtomicBoolean;
    private static final ClassId AtomicInt;
    private static final ClassId AtomicIntArray;
    private static final ClassId AtomicLong;
    private static final ClassId AtomicLongArray;
    private static final ClassId AtomicReference;
    private static final FqName BASE_ANNOTATIONS_JVM_PACKAGE;
    private static final FqName BASE_ANNOTATION_PACKAGE;
    private static final FqName BASE_COLLECTIONS_PACKAGE;
    private static final FqName BASE_CONCURRENT_ATOMICS_PACKAGE;
    private static final FqName BASE_CONCURRENT_PACKAGE;
    private static final FqName BASE_CONTRACTS_PACKAGE;
    private static final FqName BASE_COROUTINES_INTRINSICS_PACKAGE;
    private static final FqName BASE_COROUTINES_PACKAGE;
    private static final FqName BASE_ENUMS_PACKAGE;
    private static final FqName BASE_EXPERIMENTAL_PACKAGE;
    private static final FqName BASE_INTERNAL_IR_PACKAGE;
    private static final FqName BASE_INTERNAL_PACKAGE;
    private static final FqName BASE_JS_PACKAGE;
    private static final FqName BASE_JVM_FUNCTIONS_PACKAGE;
    private static final FqName BASE_JVM_INTERNAL_PACKAGE;
    private static final FqName BASE_JVM_PACKAGE;
    private static final FqName BASE_KOTLIN_PACKAGE;
    private static final FqName BASE_RANGES_PACKAGE;
    private static final FqName BASE_REFLECT_PACKAGE;
    private static final FqName BASE_SEQUENCES_PACKAGE;
    private static final FqName BASE_TEST_PACKAGE;
    private static final FqName BASE_TEXT_PACKAGE;
    private static final ClassId Boolean;
    private static final ClassId Byte;
    private static final ClassId Char;
    private static final ClassId CharIterator;
    private static final ClassId CharRange;
    private static final ClassId CharSequence;
    private static final ClassId Cloneable;
    private static final ClassId Collection;
    private static final ClassId Comparable;
    private static final ClassId Continuation;
    private static final ClassId DeprecationLevel;
    private static final ClassId Double;
    private static final ClassId Enum;
    private static final ClassId EnumEntries;
    private static final ClassId Float;
    private static final ClassId Function;
    public static final StandardClassIds INSTANCE = new StandardClassIds();
    private static final ClassId Int;
    private static final ClassId IntRange;
    private static final ClassId Iterable;
    private static final ClassId Iterator;
    private static final ClassId KCallable;
    private static final ClassId KClass;
    private static final ClassId KFunction;
    private static final ClassId KMutableProperty;
    private static final ClassId KMutableProperty0;
    private static final ClassId KMutableProperty1;
    private static final ClassId KMutableProperty2;
    private static final ClassId KProperty;
    private static final ClassId KProperty0;
    private static final ClassId KProperty1;
    private static final ClassId KProperty2;
    private static final ClassId KType;
    private static final ClassId List;
    private static final ClassId ListIterator;
    private static final ClassId Long;
    private static final ClassId LongRange;
    private static final ClassId Map;
    private static final ClassId MapEntry;
    private static final ClassId MutableCollection;
    private static final ClassId MutableIterable;
    private static final ClassId MutableIterator;
    private static final ClassId MutableList;
    private static final ClassId MutableListIterator;
    private static final ClassId MutableMap;
    private static final ClassId MutableMapEntry;
    private static final ClassId MutableSet;
    private static final ClassId Nothing;
    private static final ClassId Number;
    private static final ClassId Result;
    private static final ClassId Set;
    private static final ClassId Short;
    private static final ClassId String;
    private static final ClassId Throwable;
    private static final ClassId UByte;
    private static final ClassId UInt;
    private static final ClassId ULong;
    private static final ClassId UShort;
    private static final ClassId Unit;
    private static final Set<ClassId> allBuiltinTypes;
    private static final Map<ClassId, ClassId> atomicArrayByPrimitive;
    private static final Map<ClassId, ClassId> atomicByPrimitive;
    private static final Set<FqName> builtInsPackages;
    private static final Set<FqName> builtInsPackagesWithDefaultNamedImport;
    private static final Set<ClassId> constantAllowedTypes;
    private static final Map<ClassId, ClassId> elementTypeByPrimitiveArrayType;
    private static final Map<ClassId, ClassId> elementTypeByUnsignedArrayType;
    private static final Map<ClassId, ClassId> primitiveArrayTypeByElementType;
    private static final Set<ClassId> primitiveTypes;
    private static final Set<ClassId> signedIntegerTypes;
    private static final Map<ClassId, ClassId> unsignedArrayTypeByElementType;
    private static final Set<ClassId> unsignedTypes;

    private StandardClassIds() {
    }

    static {
        FqName fqName = new FqName("kotlin");
        BASE_KOTLIN_PACKAGE = fqName;
        Name nameIdentifier = Name.identifier("reflect");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        FqName fqNameChild = fqName.child(nameIdentifier);
        BASE_REFLECT_PACKAGE = fqNameChild;
        Name nameIdentifier2 = Name.identifier("experimental");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        BASE_EXPERIMENTAL_PACKAGE = fqName.child(nameIdentifier2);
        Name nameIdentifier3 = Name.identifier("collections");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        FqName fqNameChild2 = fqName.child(nameIdentifier3);
        BASE_COLLECTIONS_PACKAGE = fqNameChild2;
        Name nameIdentifier4 = Name.identifier("sequences");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        BASE_SEQUENCES_PACKAGE = fqName.child(nameIdentifier4);
        Name nameIdentifier5 = Name.identifier("ranges");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        FqName fqNameChild3 = fqName.child(nameIdentifier5);
        BASE_RANGES_PACKAGE = fqNameChild3;
        Name nameIdentifier6 = Name.identifier("jvm");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        FqName fqNameChild4 = fqName.child(nameIdentifier6);
        BASE_JVM_PACKAGE = fqNameChild4;
        Name nameIdentifier7 = Name.identifier("js");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier7, "identifier(...)");
        BASE_JS_PACKAGE = fqName.child(nameIdentifier7);
        Name nameIdentifier8 = Name.identifier("annotations");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier8, "identifier(...)");
        FqName fqNameChild5 = fqName.child(nameIdentifier8);
        Name nameIdentifier9 = Name.identifier("jvm");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier9, "identifier(...)");
        BASE_ANNOTATIONS_JVM_PACKAGE = fqNameChild5.child(nameIdentifier9);
        Name nameIdentifier10 = Name.identifier("internal");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier10, "identifier(...)");
        BASE_JVM_INTERNAL_PACKAGE = fqNameChild4.child(nameIdentifier10);
        Name nameIdentifier11 = Name.identifier("functions");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier11, "identifier(...)");
        BASE_JVM_FUNCTIONS_PACKAGE = fqNameChild4.child(nameIdentifier11);
        Name nameIdentifier12 = Name.identifier("annotation");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier12, "identifier(...)");
        FqName fqNameChild6 = fqName.child(nameIdentifier12);
        BASE_ANNOTATION_PACKAGE = fqNameChild6;
        Name nameIdentifier13 = Name.identifier("internal");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier13, "identifier(...)");
        FqName fqNameChild7 = fqName.child(nameIdentifier13);
        BASE_INTERNAL_PACKAGE = fqNameChild7;
        Name nameIdentifier14 = Name.identifier("ir");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier14, "identifier(...)");
        BASE_INTERNAL_IR_PACKAGE = fqNameChild7.child(nameIdentifier14);
        Name nameIdentifier15 = Name.identifier("coroutines");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier15, "identifier(...)");
        FqName fqNameChild8 = fqName.child(nameIdentifier15);
        BASE_COROUTINES_PACKAGE = fqNameChild8;
        Name nameIdentifier16 = Name.identifier("intrinsics");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier16, "identifier(...)");
        BASE_COROUTINES_INTRINSICS_PACKAGE = fqNameChild8.child(nameIdentifier16);
        Name nameIdentifier17 = Name.identifier("enums");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier17, "identifier(...)");
        BASE_ENUMS_PACKAGE = fqName.child(nameIdentifier17);
        Name nameIdentifier18 = Name.identifier("contracts");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier18, "identifier(...)");
        BASE_CONTRACTS_PACKAGE = fqName.child(nameIdentifier18);
        Name nameIdentifier19 = Name.identifier("concurrent");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier19, "identifier(...)");
        FqName fqNameChild9 = fqName.child(nameIdentifier19);
        BASE_CONCURRENT_PACKAGE = fqNameChild9;
        Name nameIdentifier20 = Name.identifier("atomics");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier20, "identifier(...)");
        FqName fqNameChild10 = fqNameChild9.child(nameIdentifier20);
        BASE_CONCURRENT_ATOMICS_PACKAGE = fqNameChild10;
        Name nameIdentifier21 = Name.identifier("test");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier21, "identifier(...)");
        BASE_TEST_PACKAGE = fqName.child(nameIdentifier21);
        Name nameIdentifier22 = Name.identifier("text");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier22, "identifier(...)");
        BASE_TEXT_PACKAGE = fqName.child(nameIdentifier22);
        builtInsPackagesWithDefaultNamedImport = SetsKt.setOf((Object[]) new FqName[]{fqName, fqNameChild2, fqNameChild3, fqNameChild6});
        builtInsPackages = SetsKt.setOf((Object[]) new FqName[]{fqName, fqNameChild2, fqNameChild3, fqNameChild6, fqNameChild, fqNameChild7, fqNameChild8, fqNameChild10});
        Nothing = StandardClassIdsKt.baseId("Nothing");
        Unit = StandardClassIdsKt.baseId("Unit");
        Any = StandardClassIdsKt.baseId("Any");
        Enum = StandardClassIdsKt.baseId("Enum");
        Annotation = StandardClassIdsKt.baseId("Annotation");
        Array = StandardClassIdsKt.baseId(ScreenElementArray.TAG_NAME);
        ClassId classIdBaseId = StandardClassIdsKt.baseId("Boolean");
        Boolean = classIdBaseId;
        ClassId classIdBaseId2 = StandardClassIdsKt.baseId("Char");
        Char = classIdBaseId2;
        ClassId classIdBaseId3 = StandardClassIdsKt.baseId("Byte");
        Byte = classIdBaseId3;
        ClassId classIdBaseId4 = StandardClassIdsKt.baseId("Short");
        Short = classIdBaseId4;
        ClassId classIdBaseId5 = StandardClassIdsKt.baseId("Int");
        Int = classIdBaseId5;
        ClassId classIdBaseId6 = StandardClassIdsKt.baseId("Long");
        Long = classIdBaseId6;
        ClassId classIdBaseId7 = StandardClassIdsKt.baseId("Float");
        Float = classIdBaseId7;
        ClassId classIdBaseId8 = StandardClassIdsKt.baseId("Double");
        Double = classIdBaseId8;
        UByte = StandardClassIdsKt.unsignedId(classIdBaseId3);
        UShort = StandardClassIdsKt.unsignedId(classIdBaseId4);
        UInt = StandardClassIdsKt.unsignedId(classIdBaseId5);
        ULong = StandardClassIdsKt.unsignedId(classIdBaseId6);
        CharSequence = StandardClassIdsKt.baseId("CharSequence");
        String = StandardClassIdsKt.baseId("String");
        Throwable = StandardClassIdsKt.baseId("Throwable");
        Cloneable = StandardClassIdsKt.baseId("Cloneable");
        KProperty = StandardClassIdsKt.reflectId("KProperty");
        KMutableProperty = StandardClassIdsKt.reflectId("KMutableProperty");
        KProperty0 = StandardClassIdsKt.reflectId("KProperty0");
        KMutableProperty0 = StandardClassIdsKt.reflectId("KMutableProperty0");
        KProperty1 = StandardClassIdsKt.reflectId("KProperty1");
        KMutableProperty1 = StandardClassIdsKt.reflectId("KMutableProperty1");
        KProperty2 = StandardClassIdsKt.reflectId("KProperty2");
        KMutableProperty2 = StandardClassIdsKt.reflectId("KMutableProperty2");
        KFunction = StandardClassIdsKt.reflectId("KFunction");
        KClass = StandardClassIdsKt.reflectId("KClass");
        KCallable = StandardClassIdsKt.reflectId("KCallable");
        KType = StandardClassIdsKt.reflectId("KType");
        Comparable = StandardClassIdsKt.baseId("Comparable");
        Number = StandardClassIdsKt.baseId("Number");
        Function = StandardClassIdsKt.baseId(FunctionElement.TAG_NAME);
        Set<ClassId> of = SetsKt.setOf((Object[]) new ClassId[]{classIdBaseId, classIdBaseId2, classIdBaseId3, classIdBaseId4, classIdBaseId5, classIdBaseId6, classIdBaseId7, classIdBaseId8});
        primitiveTypes = of;
        signedIntegerTypes = SetsKt.setOf((Object[]) new ClassId[]{classIdBaseId3, classIdBaseId4, classIdBaseId5, classIdBaseId6});
        Set<ClassId> set = of;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        for (Object obj : set) {
            linkedHashMap.put(obj, StandardClassIdsKt.primitiveArrayId(((ClassId) obj).getShortClassName()));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        primitiveArrayTypeByElementType = linkedHashMap2;
        elementTypeByPrimitiveArrayType = StandardClassIdsKt.inverseMap(linkedHashMap2);
        Set<ClassId> of2 = SetsKt.setOf((Object[]) new ClassId[]{UByte, UShort, UInt, ULong});
        unsignedTypes = of2;
        Set<ClassId> set2 = of2;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set2, 10)), 16));
        for (Object obj2 : set2) {
            linkedHashMap3.put(obj2, StandardClassIdsKt.primitiveArrayId(((ClassId) obj2).getShortClassName()));
        }
        LinkedHashMap linkedHashMap4 = linkedHashMap3;
        unsignedArrayTypeByElementType = linkedHashMap4;
        elementTypeByUnsignedArrayType = StandardClassIdsKt.inverseMap(linkedHashMap4);
        Set<ClassId> set3 = primitiveTypes;
        Set<ClassId> set4 = unsignedTypes;
        Set setPlus = SetsKt.plus((Set) set3, (Iterable) set4);
        ClassId classId = String;
        constantAllowedTypes = SetsKt.plus((Set<? extends ClassId>) setPlus, classId);
        Continuation = StandardClassIdsKt.coroutinesId("Continuation");
        Iterator = StandardClassIdsKt.collectionsId("Iterator");
        Iterable = StandardClassIdsKt.collectionsId("Iterable");
        Collection = StandardClassIdsKt.collectionsId("Collection");
        List = StandardClassIdsKt.collectionsId(ListScreenElement.TAG_NAME);
        ListIterator = StandardClassIdsKt.collectionsId("ListIterator");
        Set = StandardClassIdsKt.collectionsId("Set");
        ClassId classIdCollectionsId = StandardClassIdsKt.collectionsId("Map");
        Map = classIdCollectionsId;
        AbstractMap = StandardClassIdsKt.collectionsId("AbstractMap");
        MutableIterator = StandardClassIdsKt.collectionsId("MutableIterator");
        CharIterator = StandardClassIdsKt.collectionsId("CharIterator");
        MutableIterable = StandardClassIdsKt.collectionsId("MutableIterable");
        MutableCollection = StandardClassIdsKt.collectionsId("MutableCollection");
        MutableList = StandardClassIdsKt.collectionsId("MutableList");
        MutableListIterator = StandardClassIdsKt.collectionsId("MutableListIterator");
        MutableSet = StandardClassIdsKt.collectionsId("MutableSet");
        ClassId classIdCollectionsId2 = StandardClassIdsKt.collectionsId("MutableMap");
        MutableMap = classIdCollectionsId2;
        Name nameIdentifier23 = Name.identifier("Entry");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier23, "identifier(...)");
        MapEntry = classIdCollectionsId.createNestedClassId(nameIdentifier23);
        Name nameIdentifier24 = Name.identifier("MutableEntry");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier24, "identifier(...)");
        MutableMapEntry = classIdCollectionsId2.createNestedClassId(nameIdentifier24);
        Result = StandardClassIdsKt.baseId("Result");
        IntRange = StandardClassIdsKt.rangesId("IntRange");
        LongRange = StandardClassIdsKt.rangesId("LongRange");
        CharRange = StandardClassIdsKt.rangesId("CharRange");
        AnnotationRetention = StandardClassIdsKt.annotationId("AnnotationRetention");
        AnnotationTarget = StandardClassIdsKt.annotationId("AnnotationTarget");
        DeprecationLevel = StandardClassIdsKt.baseId("DeprecationLevel");
        EnumEntries = StandardClassIdsKt.enumsId("EnumEntries");
        ClassId classIdAtomicsId = StandardClassIdsKt.atomicsId("AtomicBoolean");
        AtomicBoolean = classIdAtomicsId;
        ClassId classIdAtomicsId2 = StandardClassIdsKt.atomicsId("AtomicInt");
        AtomicInt = classIdAtomicsId2;
        ClassId classIdAtomicsId3 = StandardClassIdsKt.atomicsId("AtomicLong");
        AtomicLong = classIdAtomicsId3;
        AtomicReference = StandardClassIdsKt.atomicsId("AtomicReference");
        ClassId classId2 = Int;
        ClassId classId3 = Long;
        atomicByPrimitive = MapsKt.mapOf(TuplesKt.to(Boolean, classIdAtomicsId), TuplesKt.to(classId2, classIdAtomicsId2), TuplesKt.to(classId3, classIdAtomicsId3));
        AtomicArray = StandardClassIdsKt.atomicsId("AtomicArray");
        ClassId classIdAtomicsId4 = StandardClassIdsKt.atomicsId("AtomicIntArray");
        AtomicIntArray = classIdAtomicsId4;
        ClassId classIdAtomicsId5 = StandardClassIdsKt.atomicsId("AtomicLongArray");
        AtomicLongArray = classIdAtomicsId5;
        atomicArrayByPrimitive = MapsKt.mapOf(TuplesKt.to(classId2, classIdAtomicsId4), TuplesKt.to(classId3, classIdAtomicsId5));
        allBuiltinTypes = SetsKt.plus((Set<? extends ClassId>) SetsKt.plus((Set<? extends ClassId>) SetsKt.plus((Set<? extends ClassId>) SetsKt.plus((Set<? extends ClassId>) SetsKt.plus((Set) set3, (Iterable) set4), classId), Unit), Any), Enum);
    }

    public final FqName getBASE_KOTLIN_PACKAGE() {
        return BASE_KOTLIN_PACKAGE;
    }

    public final FqName getBASE_REFLECT_PACKAGE() {
        return BASE_REFLECT_PACKAGE;
    }

    public final FqName getBASE_COLLECTIONS_PACKAGE() {
        return BASE_COLLECTIONS_PACKAGE;
    }

    public final FqName getBASE_RANGES_PACKAGE() {
        return BASE_RANGES_PACKAGE;
    }

    public final FqName getBASE_ANNOTATION_PACKAGE() {
        return BASE_ANNOTATION_PACKAGE;
    }

    public final FqName getBASE_COROUTINES_PACKAGE() {
        return BASE_COROUTINES_PACKAGE;
    }

    public final FqName getBASE_ENUMS_PACKAGE() {
        return BASE_ENUMS_PACKAGE;
    }

    public final FqName getBASE_CONCURRENT_ATOMICS_PACKAGE() {
        return BASE_CONCURRENT_ATOMICS_PACKAGE;
    }

    public final ClassId getArray() {
        return Array;
    }

    public final ClassId getKFunction() {
        return KFunction;
    }

    public final ClassId getKClass() {
        return KClass;
    }

    public final ClassId getMutableList() {
        return MutableList;
    }

    public final ClassId getMutableSet() {
        return MutableSet;
    }

    public final ClassId getMutableMap() {
        return MutableMap;
    }

    public final ClassId getEnumEntries() {
        return EnumEntries;
    }
}
