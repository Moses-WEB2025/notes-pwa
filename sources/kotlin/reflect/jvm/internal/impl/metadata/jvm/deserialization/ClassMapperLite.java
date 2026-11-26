package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import com.market.sdk.reflect.Field;
import com.miui.maml.elements.FunctionElement;
import com.miui.maml.elements.ListScreenElement;
import com.miui.maml.elements.ScreenElementArray;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ClassMapperLite.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class ClassMapperLite {
    public static final ClassMapperLite INSTANCE = new ClassMapperLite();

    /* JADX INFO: renamed from: kotlin, reason: collision with root package name */
    private static final String f8kotlin = CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Character[]{'k', 'o', 't', 'l', 'i', 'n'}), "", null, null, 0, null, null, 62, null);
    private static final Map<String, String> map;

    private ClassMapperLite() {
    }

    static {
        char c;
        char c2;
        char c3;
        char c4;
        int i = 0;
        char c5 = 3;
        char c6 = 4;
        char c7 = 5;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        char c8 = '\b';
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Boolean", Field.BOOLEAN_SIGNATURE_PRIMITIVE, "Char", Field.CHAR_SIGNATURE_PRIMITIVE, "Byte", "B", "Short", "S", "Int", Field.INT_SIGNATURE_PRIMITIVE, "Float", Field.FLOAT_SIGNATURE_PRIMITIVE, "Long", Field.LONG_SIGNATURE_PRIMITIVE, "Double", Field.DOUBLE_SIGNATURE_PRIMITIVE});
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, listListOf.size() - 1, 2);
        if (progressionLastElement >= 0) {
            while (true) {
                StringBuilder sb = new StringBuilder();
                c = c5;
                String str = f8kotlin;
                c2 = c6;
                c3 = c7;
                int i2 = i + 1;
                c4 = c8;
                linkedHashMap.put(sb.append(str).append('/').append((String) listListOf.get(i)).toString(), listListOf.get(i2));
                linkedHashMap.put(str + '/' + ((String) listListOf.get(i)) + ScreenElementArray.TAG_NAME, "[" + ((String) listListOf.get(i2)));
                if (i == progressionLastElement) {
                    break;
                }
                i += 2;
                c5 = c;
                c6 = c2;
                c8 = c4;
                c7 = c3;
            }
        } else {
            c = 3;
            c2 = 4;
            c3 = 5;
            c4 = '\b';
        }
        linkedHashMap.put(f8kotlin + "/Unit", "V");
        map$lambda$0$add(linkedHashMap, "Any", "java/lang/Object");
        map$lambda$0$add(linkedHashMap, "Nothing", "java/lang/Void");
        map$lambda$0$add(linkedHashMap, "Annotation", "java/lang/annotation/Annotation");
        String[] strArr = new String[7];
        strArr[0] = "String";
        strArr[1] = "CharSequence";
        strArr[2] = "Throwable";
        strArr[c] = "Cloneable";
        strArr[c2] = "Number";
        strArr[c3] = "Comparable";
        strArr[6] = "Enum";
        for (String str2 : CollectionsKt.listOf((Object[]) strArr)) {
            map$lambda$0$add(linkedHashMap, str2, "java/lang/" + str2);
        }
        String[] strArr2 = new String[6];
        strArr2[0] = "Iterator";
        strArr2[1] = "Collection";
        strArr2[2] = ListScreenElement.TAG_NAME;
        strArr2[c] = "Set";
        strArr2[c2] = "Map";
        strArr2[c3] = "ListIterator";
        for (String str3 : CollectionsKt.listOf((Object[]) strArr2)) {
            map$lambda$0$add(linkedHashMap, "collections/" + str3, "java/util/" + str3);
            map$lambda$0$add(linkedHashMap, "collections/Mutable" + str3, "java/util/" + str3);
        }
        map$lambda$0$add(linkedHashMap, "collections/Iterable", "java/lang/Iterable");
        map$lambda$0$add(linkedHashMap, "collections/MutableIterable", "java/lang/Iterable");
        map$lambda$0$add(linkedHashMap, "collections/Map.Entry", "java/util/Map$Entry");
        map$lambda$0$add(linkedHashMap, "collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        for (int i3 = 0; i3 < 23; i3++) {
            String str4 = FunctionElement.TAG_NAME + i3;
            StringBuilder sb2 = new StringBuilder();
            String str5 = f8kotlin;
            map$lambda$0$add(linkedHashMap, str4, sb2.append(str5).append("/jvm/functions/Function").append(i3).toString());
            map$lambda$0$add(linkedHashMap, "reflect/KFunction" + i3, str5 + "/reflect/KFunction");
        }
        String[] strArr3 = new String[9];
        strArr3[0] = "Char";
        strArr3[1] = "Byte";
        strArr3[2] = "Short";
        strArr3[c] = "Int";
        strArr3[c2] = "Float";
        strArr3[c3] = "Long";
        strArr3[6] = "Double";
        strArr3[7] = "String";
        strArr3[c4] = "Enum";
        for (String str6 : CollectionsKt.listOf((Object[]) strArr3)) {
            map$lambda$0$add(linkedHashMap, str6 + ".Companion", f8kotlin + "/jvm/internal/" + str6 + "CompanionObject");
        }
        map = linkedHashMap;
    }

    private static final void map$lambda$0$add(Map<String, String> map2, String str, String str2) {
        map2.put(f8kotlin + '/' + str, "L" + str2 + ';');
    }

    @JvmStatic
    public static final String mapClass(String classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        String str = map.get(classId);
        return str == null ? "L" + StringsKt.replace$default(classId, '.', '$', false, 4, (Object) null) + ';' : str;
    }
}
