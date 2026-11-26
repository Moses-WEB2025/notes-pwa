package kotlin.reflect.jvm.internal.impl.km.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotation;
import kotlin.reflect.jvm.internal.impl.km.KmAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;

/* JADX INFO: compiled from: ReadUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ReadUtilsKt {

    /* JADX INFO: compiled from: ReadUtils.kt */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.Annotation.Argument.Value.Type.values().length];
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.BYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.INT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.CHAR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.DOUBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.CLASS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ENUM.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ProtoBuf.Annotation.Argument.Value.Type.ARRAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final KmAnnotation readAnnotation(ProtoBuf.Annotation annotation, NameResolver strings) {
        Intrinsics.checkNotNullParameter(annotation, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        String className = getClassName(strings, annotation.getId());
        List<ProtoBuf.Annotation.Argument> argumentList = annotation.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        ArrayList arrayList = new ArrayList();
        for (ProtoBuf.Annotation.Argument argument : argumentList) {
            ProtoBuf.Annotation.Argument.Value value = argument.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            KmAnnotationArgument annotationArgument = readAnnotationArgument(value, strings);
            Pair pair = annotationArgument != null ? TuplesKt.to(strings.getString(argument.getNameId()), annotationArgument) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return new KmAnnotation(className, MapsKt.toMap(arrayList));
    }

    public static final KmAnnotationArgument readAnnotationArgument(ProtoBuf.Annotation.Argument.Value value, NameResolver strings) {
        Intrinsics.checkNotNullParameter(value, "<this>");
        Intrinsics.checkNotNullParameter(strings, "strings");
        if (Flags.IS_UNSIGNED.get(value.getFlags()).booleanValue()) {
            ProtoBuf.Annotation.Argument.Value.Type type = value.getType();
            int i = type != null ? WhenMappings.$EnumSwitchMapping$0[type.ordinal()] : -1;
            if (i == 1) {
                return new KmAnnotationArgument.UByteValue(UByte.m2550constructorimpl((byte) value.getIntValue()), null);
            }
            if (i == 2) {
                return new KmAnnotationArgument.UShortValue(UShort.m2813constructorimpl((short) value.getIntValue()), null);
            }
            if (i == 3) {
                return new KmAnnotationArgument.UIntValue(UInt.m2627constructorimpl((int) value.getIntValue()), null);
            }
            if (i == 4) {
                return new KmAnnotationArgument.ULongValue(ULong.m2706constructorimpl(value.getIntValue()), null);
            }
            throw new IllegalStateException(("Cannot read value of unsigned type: " + value.getType()).toString());
        }
        ProtoBuf.Annotation.Argument.Value.Type type2 = value.getType();
        switch (type2 != null ? WhenMappings.$EnumSwitchMapping$0[type2.ordinal()] : -1) {
            case -1:
                return null;
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                return new KmAnnotationArgument.ByteValue((byte) value.getIntValue());
            case 2:
                return new KmAnnotationArgument.ShortValue((short) value.getIntValue());
            case 3:
                return new KmAnnotationArgument.IntValue((int) value.getIntValue());
            case 4:
                return new KmAnnotationArgument.LongValue(value.getIntValue());
            case 5:
                return new KmAnnotationArgument.CharValue((char) value.getIntValue());
            case 6:
                return new KmAnnotationArgument.FloatValue(value.getFloatValue());
            case 7:
                return new KmAnnotationArgument.DoubleValue(value.getDoubleValue());
            case 8:
                return new KmAnnotationArgument.BooleanValue(value.getIntValue() != 0);
            case 9:
                return new KmAnnotationArgument.StringValue(strings.getString(value.getStringValue()));
            case 10:
                String className = getClassName(strings, value.getClassId());
                if (value.getArrayDimensionCount() == 0) {
                    return new KmAnnotationArgument.KClassValue(className);
                }
                return new KmAnnotationArgument.ArrayKClassValue(className, value.getArrayDimensionCount());
            case 11:
                return new KmAnnotationArgument.EnumValue(getClassName(strings, value.getClassId()), strings.getString(value.getEnumValueId()));
            case 12:
                ProtoBuf.Annotation annotation = value.getAnnotation();
                Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
                return new KmAnnotationArgument.AnnotationValue(readAnnotation(annotation, strings));
            case 13:
                List<ProtoBuf.Annotation.Argument.Value> arrayElementList = value.getArrayElementList();
                Intrinsics.checkNotNullExpressionValue(arrayElementList, "getArrayElementList(...)");
                ArrayList arrayList = new ArrayList();
                for (ProtoBuf.Annotation.Argument.Value value2 : arrayElementList) {
                    Intrinsics.checkNotNull(value2);
                    KmAnnotationArgument annotationArgument = readAnnotationArgument(value2, strings);
                    if (annotationArgument != null) {
                        arrayList.add(annotationArgument);
                    }
                }
                return new KmAnnotationArgument.ArrayValue(arrayList);
        }
    }

    public static final String getClassName(NameResolver nameResolver, int i) {
        Intrinsics.checkNotNullParameter(nameResolver, "<this>");
        String qualifiedClassName = nameResolver.getQualifiedClassName(i);
        return nameResolver.isLocalClassName(i) ? "." + qualifiedClassName : qualifiedClassName;
    }
}
