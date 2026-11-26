package kotlin.reflect.jvm.internal.impl.km.internal;

import java.util.Iterator;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.ClassNameKt;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotation;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.serialization.StringTable;
import net.lingala.zip4j.util.InternalZipConstants;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: compiled from: WriteUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class WriteUtilsKt {
    public static final ProtoBuf.Annotation.Builder writeAnnotation(KmAnnotation kmAnnotation, StringTable strings) {
        Intrinsics.checkNotNullParameter(kmAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ProtoBuf.Annotation.Builder builderNewBuilder = ProtoBuf.Annotation.newBuilder();
        builderNewBuilder.setId(getClassNameIndex(strings, kmAnnotation.getClassName()));
        for (Map.Entry<String, KmAnnotationArgument> entry : kmAnnotation.getArguments().entrySet()) {
            String key = entry.getKey();
            KmAnnotationArgument value = entry.getValue();
            ProtoBuf.Annotation.Argument.Builder builderNewBuilder2 = ProtoBuf.Annotation.Argument.newBuilder();
            builderNewBuilder2.setNameId(strings.getStringIndex(key));
            builderNewBuilder2.setValue(writeAnnotationArgument(value, strings).build());
            builderNewBuilder.addArgument(builderNewBuilder2);
        }
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
        return builderNewBuilder;
    }

    public static final ProtoBuf.Annotation.Argument.Value.Builder writeAnnotationArgument(KmAnnotationArgument kmAnnotationArgument, StringTable strings) {
        Intrinsics.checkNotNullParameter(kmAnnotationArgument, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ProtoBuf.Annotation.Argument.Value.Builder builderNewBuilder = ProtoBuf.Annotation.Argument.Value.newBuilder();
        if (kmAnnotationArgument instanceof KmAnnotationArgument.ByteValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.BYTE);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.ByteValue) kmAnnotationArgument).getValue().byteValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.CharValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.CHAR);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.CharValue) kmAnnotationArgument).getValue().charValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.ShortValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.SHORT);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.ShortValue) kmAnnotationArgument).getValue().shortValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.IntValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.INT);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.IntValue) kmAnnotationArgument).getValue().intValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.LongValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.LONG);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.LongValue) kmAnnotationArgument).getValue().longValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.FloatValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.FLOAT);
            builderNewBuilder.setFloatValue(((KmAnnotationArgument.FloatValue) kmAnnotationArgument).getValue().floatValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.DoubleValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.DOUBLE);
            builderNewBuilder.setDoubleValue(((KmAnnotationArgument.DoubleValue) kmAnnotationArgument).getValue().doubleValue());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.BooleanValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.BooleanValue) kmAnnotationArgument).getValue().booleanValue() ? 1L : 0L);
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.UByteValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.BYTE);
            builderNewBuilder.setIntValue(((long) ((KmAnnotationArgument.UByteValue) kmAnnotationArgument).m3816getValuew2LRezQ()) & 255);
            builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.UShortValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.SHORT);
            builderNewBuilder.setIntValue(((long) ((KmAnnotationArgument.UShortValue) kmAnnotationArgument).m3819getValueMh2AYeg()) & WebSocketProtocol.PAYLOAD_SHORT_MAX);
            builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.UIntValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.INT);
            builderNewBuilder.setIntValue(((long) ((KmAnnotationArgument.UIntValue) kmAnnotationArgument).m3817getValuepVg5ArA()) & InternalZipConstants.ZIP_64_SIZE_LIMIT);
            builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.ULongValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.LONG);
            builderNewBuilder.setIntValue(((KmAnnotationArgument.ULongValue) kmAnnotationArgument).m3818getValuesVKNKU());
            builderNewBuilder.setFlags(Flags.IS_UNSIGNED.toFlags((Boolean) true));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.StringValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.STRING);
            builderNewBuilder.setStringValue(strings.getStringIndex(((KmAnnotationArgument.StringValue) kmAnnotationArgument).getValue()));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.KClassValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.CLASS);
            builderNewBuilder.setClassId(getClassNameIndex(strings, ((KmAnnotationArgument.KClassValue) kmAnnotationArgument).getClassName()));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.ArrayKClassValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.CLASS);
            KmAnnotationArgument.ArrayKClassValue arrayKClassValue = (KmAnnotationArgument.ArrayKClassValue) kmAnnotationArgument;
            builderNewBuilder.setClassId(getClassNameIndex(strings, arrayKClassValue.getClassName()));
            builderNewBuilder.setArrayDimensionCount(arrayKClassValue.getArrayDimensionCount());
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.EnumValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.ENUM);
            KmAnnotationArgument.EnumValue enumValue = (KmAnnotationArgument.EnumValue) kmAnnotationArgument;
            builderNewBuilder.setClassId(getClassNameIndex(strings, enumValue.getEnumClassName()));
            builderNewBuilder.setEnumValueId(strings.getStringIndex(enumValue.getEnumEntryName()));
        } else if (kmAnnotationArgument instanceof KmAnnotationArgument.AnnotationValue) {
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION);
            builderNewBuilder.setAnnotation(writeAnnotation(((KmAnnotationArgument.AnnotationValue) kmAnnotationArgument).getAnnotation(), strings).build());
        } else {
            if (!(kmAnnotationArgument instanceof KmAnnotationArgument.ArrayValue)) {
                throw new NoWhenBranchMatchedException();
            }
            builderNewBuilder.setType(ProtoBuf.Annotation.Argument.Value.Type.ARRAY);
            Iterator<KmAnnotationArgument> it = ((KmAnnotationArgument.ArrayValue) kmAnnotationArgument).getElements().iterator();
            while (it.hasNext()) {
                builderNewBuilder.addArrayElement(writeAnnotationArgument(it.next(), strings));
            }
        }
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "apply(...)");
        return builderNewBuilder;
    }

    public static final int getClassNameIndex(StringTable stringTable, String name) {
        Intrinsics.checkNotNullParameter(stringTable, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        if (ClassNameKt.isLocalClassName(name)) {
            String strSubstring = name.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return stringTable.getQualifiedClassNameIndex(strSubstring, true);
        }
        return stringTable.getQualifiedClassNameIndex(name, false);
    }
}
