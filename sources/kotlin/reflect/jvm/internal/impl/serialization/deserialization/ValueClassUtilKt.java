package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.InlineClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.MultiFieldValueClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueClassRepresentation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.model.RigidTypeMarker;

/* JADX INFO: compiled from: ValueClassUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ValueClassUtilKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T extends RigidTypeMarker> ValueClassRepresentation<T> loadValueClassRepresentation(ProtoBuf.Class r1, NameResolver nameResolver, TypeTable typeTable, Function1<? super ProtoBuf.Type, ? extends T> typeDeserializer, Function1<? super Name, ? extends T> typeOfPublicProperty) {
        T tInvoke;
        Intrinsics.checkNotNullParameter(r1, "<this>");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
        Intrinsics.checkNotNullParameter(typeOfPublicProperty, "typeOfPublicProperty");
        if (r1.getMultiFieldValueClassUnderlyingNameCount() > 0) {
            Pair<List<Name>, List<ProtoBuf.Type>> pairLoadMultiFieldValueClassRepresentation = loadMultiFieldValueClassRepresentation(r1, nameResolver, typeTable);
            List<Name> listComponent1 = pairLoadMultiFieldValueClassRepresentation.component1();
            List<ProtoBuf.Type> listComponent2 = pairLoadMultiFieldValueClassRepresentation.component2();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listComponent2, 10));
            Iterator<T> it = listComponent2.iterator();
            while (it.hasNext()) {
                arrayList.add(typeDeserializer.invoke(it.next()));
            }
            return new MultiFieldValueClassRepresentation(CollectionsKt.zip(listComponent1, arrayList));
        }
        if (!r1.hasInlineClassUnderlyingPropertyName()) {
            return null;
        }
        Name name = NameResolverUtilKt.getName(nameResolver, r1.getInlineClassUnderlyingPropertyName());
        ProtoBuf.Type typeInlineClassUnderlyingType = ProtoTypeTableUtilKt.inlineClassUnderlyingType(r1, typeTable);
        if ((typeInlineClassUnderlyingType == null || (tInvoke = typeDeserializer.invoke(typeInlineClassUnderlyingType)) == null) && (tInvoke = typeOfPublicProperty.invoke(name)) == null) {
            throw new IllegalStateException(("cannot determine underlying type for value class " + NameResolverUtilKt.getName(nameResolver, r1.getFqName()) + " with property " + name).toString());
        }
        return new InlineClassRepresentation(name, tInvoke);
    }

    public static final Pair<List<Name>, List<ProtoBuf.Type>> loadMultiFieldValueClassRepresentation(ProtoBuf.Class r6, NameResolver nameResolver, TypeTable typeTable) {
        ArrayList multiFieldValueClassUnderlyingTypeList;
        Intrinsics.checkNotNullParameter(r6, "<this>");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List<Integer> multiFieldValueClassUnderlyingNameList = r6.getMultiFieldValueClassUnderlyingNameList();
        Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingNameList, "getMultiFieldValueClassUnderlyingNameList(...)");
        List<Integer> list = multiFieldValueClassUnderlyingNameList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Integer num : list) {
            Intrinsics.checkNotNull(num);
            arrayList.add(NameResolverUtilKt.getName(nameResolver, num.intValue()));
        }
        ArrayList arrayList2 = arrayList;
        Pair pair = TuplesKt.to(Integer.valueOf(r6.getMultiFieldValueClassUnderlyingTypeIdCount()), Integer.valueOf(r6.getMultiFieldValueClassUnderlyingTypeCount()));
        if (!Intrinsics.areEqual(pair, TuplesKt.to(Integer.valueOf(arrayList2.size()), 0))) {
            if (!Intrinsics.areEqual(pair, TuplesKt.to(0, Integer.valueOf(arrayList2.size())))) {
                throw new IllegalStateException(("class " + NameResolverUtilKt.getName(nameResolver, r6.getFqName()) + " has illegal multi-field value class representation").toString());
            }
            multiFieldValueClassUnderlyingTypeList = r6.getMultiFieldValueClassUnderlyingTypeList();
        } else {
            List<Integer> multiFieldValueClassUnderlyingTypeIdList = r6.getMultiFieldValueClassUnderlyingTypeIdList();
            Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingTypeIdList, "getMultiFieldValueClassUnderlyingTypeIdList(...)");
            List<Integer> list2 = multiFieldValueClassUnderlyingTypeIdList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (Integer num2 : list2) {
                Intrinsics.checkNotNull(num2);
                arrayList3.add(typeTable.get(num2.intValue()));
            }
            multiFieldValueClassUnderlyingTypeList = arrayList3;
        }
        return TuplesKt.to(arrayList2, multiFieldValueClassUnderlyingTypeList);
    }
}
