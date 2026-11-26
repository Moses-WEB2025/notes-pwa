package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;

/* JADX INFO: compiled from: Enums.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u001b\n\u0002\b\u0006\u001a9\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0001¢\u0006\u0002\u0010\b\u001a_\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00072\u0014\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00070\u0007H\u0001¢\u0006\u0002\u0010\r\u001ao\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00072\u0014\u0010\u000f\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00070\u00072\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0007H\u0001¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"createSimpleEnumSerializer", "Lkotlinx/serialization/KSerializer;", ExifInterface.GPS_DIRECTION_TRUE, "", "serialName", "", "values", "", "(Ljava/lang/String;[Ljava/lang/Enum;)Lkotlinx/serialization/KSerializer;", "createMarkedEnumSerializer", "names", "annotations", "", "(Ljava/lang/String;[Ljava/lang/Enum;[Ljava/lang/String;[[Ljava/lang/annotation/Annotation;)Lkotlinx/serialization/KSerializer;", "createAnnotatedEnumSerializer", "entryAnnotations", "classAnnotations", "(Ljava/lang/String;[Ljava/lang/Enum;[Ljava/lang/String;[[Ljava/lang/annotation/Annotation;[Ljava/lang/annotation/Annotation;)Lkotlinx/serialization/KSerializer;", "kotlinx-serialization-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class EnumsKt {
    public static final <T extends Enum<T>> KSerializer<T> createSimpleEnumSerializer(String serialName, T[] values) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(values, "values");
        return new EnumSerializer(serialName, values);
    }

    public static final <T extends Enum<T>> KSerializer<T> createMarkedEnumSerializer(String serialName, T[] values, String[] names, Annotation[][] annotations) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(names, "names");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        EnumDescriptor enumDescriptor = new EnumDescriptor(serialName, values.length);
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            T t = values[i];
            int i3 = i2 + 1;
            String strName = (String) ArraysKt.getOrNull(names, i2);
            if (strName == null) {
                strName = t.name();
            }
            PluginGeneratedSerialDescriptor.addElement$default(enumDescriptor, strName, false, 2, null);
            Annotation[] annotationArr = (Annotation[]) ArraysKt.getOrNull(annotations, i2);
            if (annotationArr != null) {
                for (Annotation annotation : annotationArr) {
                    enumDescriptor.pushAnnotation(annotation);
                }
            }
            i++;
            i2 = i3;
        }
        return new EnumSerializer(serialName, values, enumDescriptor);
    }

    public static final <T extends Enum<T>> KSerializer<T> createAnnotatedEnumSerializer(String serialName, T[] values, String[] names, Annotation[][] entryAnnotations, Annotation[] annotationArr) {
        Intrinsics.checkNotNullParameter(serialName, "serialName");
        Intrinsics.checkNotNullParameter(values, "values");
        Intrinsics.checkNotNullParameter(names, "names");
        Intrinsics.checkNotNullParameter(entryAnnotations, "entryAnnotations");
        EnumDescriptor enumDescriptor = new EnumDescriptor(serialName, values.length);
        if (annotationArr != null) {
            for (Annotation annotation : annotationArr) {
                enumDescriptor.pushClassAnnotation(annotation);
            }
        }
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            T t = values[i];
            int i3 = i2 + 1;
            String strName = (String) ArraysKt.getOrNull(names, i2);
            if (strName == null) {
                strName = t.name();
            }
            PluginGeneratedSerialDescriptor.addElement$default(enumDescriptor, strName, false, 2, null);
            Annotation[] annotationArr2 = (Annotation[]) ArraysKt.getOrNull(entryAnnotations, i2);
            if (annotationArr2 != null) {
                for (Annotation annotation2 : annotationArr2) {
                    enumDescriptor.pushAnnotation(annotation2);
                }
            }
            i++;
            i2 = i3;
        }
        return new EnumSerializer(serialName, values, enumDescriptor);
    }
}
