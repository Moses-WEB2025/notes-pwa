package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import ch.qos.logback.core.pattern.parser.Parser;
import com.miui.maml.elements.FunctionElement;
import com.miui.maml.elements.ListScreenElement;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class PredefinedEnhancementInfoKt {
    private static final Map<String, PredefinedFunctionEnhancementInfo> PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;
    private static final JavaTypeQualifiers NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NULLABLE, null, false, false, 8, null);
    private static final JavaTypeQualifiers NOT_PLATFORM = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, false, false, 8, null);
    private static final JavaTypeQualifiers NOT_NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, true, false, 8, null);

    static {
        final SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        final String strJavaLang = signatureBuildingComponents.javaLang("Object");
        final String strJavaFunction = signatureBuildingComponents.javaFunction("Predicate");
        final String strJavaFunction2 = signatureBuildingComponents.javaFunction(FunctionElement.TAG_NAME);
        final String strJavaFunction3 = signatureBuildingComponents.javaFunction("Consumer");
        final String strJavaFunction4 = signatureBuildingComponents.javaFunction("BiFunction");
        final String strJavaFunction5 = signatureBuildingComponents.javaFunction("BiConsumer");
        final String strJavaFunction6 = signatureBuildingComponents.javaFunction("UnaryOperator");
        final String strJavaUtil = signatureBuildingComponents.javaUtil("stream/Stream");
        final String strJavaUtil2 = signatureBuildingComponents.javaUtil("Optional");
        SignatureEnhancementBuilder signatureEnhancementBuilder = new SignatureEnhancementBuilder();
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("Iterator")), "forEachRemaining", null, new Function1(strJavaFunction3) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$0
            private final String arg$0;

            {
                this.arg$0 = strJavaFunction3;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$1$lambda$0(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaLang("Iterable")), "spliterator", null, new Function1(signatureBuildingComponents) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$1
            private final SignatureBuildingComponents arg$0;

            {
                this.arg$0 = signatureBuildingComponents;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$3$lambda$2(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("Collection"));
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder, "removeIf", null, new Function1(strJavaFunction) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$2
            private final String arg$0;

            {
                this.arg$0 = strJavaFunction;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$7$lambda$4(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder, "stream", null, new Function1(strJavaUtil) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$3
            private final String arg$0;

            {
                this.arg$0 = strJavaUtil;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$7$lambda$5(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder, "parallelStream", null, new Function1(strJavaUtil) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$4
            private final String arg$0;

            {
                this.arg$0 = strJavaUtil;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$7$lambda$6(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder2 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil(ListScreenElement.TAG_NAME));
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder2, "replaceAll", null, new Function1(strJavaFunction6) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$5
            private final String arg$0;

            {
                this.arg$0 = strJavaFunction6;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$8(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        classEnhancementBuilder2.function("addFirst", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$6
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$9(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder2.function("addLast", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$7
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$10(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder2.function("removeFirst", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$8
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$11(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder2.function("removeLast", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$9
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$12(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder3 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("LinkedList"));
        classEnhancementBuilder3.function("addFirst", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$10
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$14(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder3.function("addLast", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$11
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$15(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder3.function("removeFirst", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$12
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$16(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder3.function("removeLast", "2.1", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$13
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$17(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder4 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("LinkedHashSet"));
        classEnhancementBuilder4.function("addFirst", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$14
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$19(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder4.function("addLast", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$15
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$20(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder4.function("removeFirst", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$16
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$21(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder4.function("removeLast", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$17
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$22(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder4.function("getFirst", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$18
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$23(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder4.function("getLast", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$19
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$24(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder5 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("Map"));
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "forEach", null, new Function1(strJavaFunction5) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$20
            private final String arg$0;

            {
                this.arg$0 = strJavaFunction5;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$26(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "putIfAbsent", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$21
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$27(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, Parser.REPLACE_CONVERTER_WORD, null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$22
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$28(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, Parser.REPLACE_CONVERTER_WORD, null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$23
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$29(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "replaceAll", null, new Function1(strJavaFunction4) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$24
            private final String arg$0;

            {
                this.arg$0 = strJavaFunction4;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$30(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "compute", null, new Function1(strJavaLang, strJavaFunction4) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$25
            private final String arg$0;
            private final String arg$1;

            {
                this.arg$0 = strJavaLang;
                this.arg$1 = strJavaFunction4;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$31(this.arg$0, this.arg$1, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "computeIfAbsent", null, new Function1(strJavaLang, strJavaFunction2) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$26
            private final String arg$0;
            private final String arg$1;

            {
                this.arg$0 = strJavaLang;
                this.arg$1 = strJavaFunction2;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$32(this.arg$0, this.arg$1, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "computeIfPresent", null, new Function1(strJavaLang, strJavaFunction4) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$27
            private final String arg$0;
            private final String arg$1;

            {
                this.arg$0 = strJavaLang;
                this.arg$1 = strJavaFunction4;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$33(this.arg$0, this.arg$1, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder5, "merge", null, new Function1(strJavaLang, strJavaFunction4) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$28
            private final String arg$0;
            private final String arg$1;

            {
                this.arg$0 = strJavaLang;
                this.arg$1 = strJavaFunction4;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$34(this.arg$0, this.arg$1, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder6 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("LinkedHashMap"));
        classEnhancementBuilder6.function("putFirst", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$29
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$38$lambda$36(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        classEnhancementBuilder6.function("putLast", "2.2", new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$30
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$38$lambda$37(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        });
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder7 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, strJavaUtil2);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder7, "empty", null, new Function1(strJavaUtil2) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$31
            private final String arg$0;

            {
                this.arg$0 = strJavaUtil2;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$39(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder7, "of", null, new Function1(strJavaLang, strJavaUtil2) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$32
            private final String arg$0;
            private final String arg$1;

            {
                this.arg$0 = strJavaLang;
                this.arg$1 = strJavaUtil2;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$40(this.arg$0, this.arg$1, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder7, "ofNullable", null, new Function1(strJavaLang, strJavaUtil2) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$33
            private final String arg$0;
            private final String arg$1;

            {
                this.arg$0 = strJavaLang;
                this.arg$1 = strJavaUtil2;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$41(this.arg$0, this.arg$1, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder7, CallMethod.METHOD_GET, null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$34
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$42(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(classEnhancementBuilder7, "ifPresent", null, new Function1(strJavaFunction3) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$35
            private final String arg$0;

            {
                this.arg$0 = strJavaFunction3;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$43(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaLang("ref/Reference")), CallMethod.METHOD_GET, null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$36
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$46$lambda$45(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, strJavaFunction), "test", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$37
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$48$lambda$47(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaFunction("BiPredicate")), "test", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$38
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$50$lambda$49(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, strJavaFunction3), "accept", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$39
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$52$lambda$51(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, strJavaFunction5), "accept", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$40
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$54$lambda$53(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, strJavaFunction2), "apply", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$41
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$56$lambda$55(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, strJavaFunction4), "apply", null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$42
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$58$lambda$57(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        SignatureEnhancementBuilder.ClassEnhancementBuilder.function$default(new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaFunction("Supplier")), CallMethod.METHOD_GET, null, new Function1(strJavaLang) { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$$Lambda$43
            private final String arg$0;

            {
                this.arg$0 = strJavaLang;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return PredefinedEnhancementInfoKt.PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$60$lambda$59(this.arg$0, (SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
            }
        }, 2, null);
        PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE = signatureEnhancementBuilder.build();
    }

    public static final Map<String, PredefinedFunctionEnhancementInfo> getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE() {
        return PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$1$lambda$0(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$3$lambda$2(SignatureBuildingComponents signatureBuildingComponents, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        String strJavaUtil = signatureBuildingComponents.javaUtil("Spliterator");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.returns(strJavaUtil, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$7$lambda$4(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers, javaTypeQualifiers);
        function.returns(JvmPrimitiveType.BOOLEAN);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$7$lambda$5(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.returns(str, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$7$lambda$6(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.returns(str, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$8(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$9(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$10(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$11(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$13$lambda$12(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$14(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$15(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$16(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$18$lambda$17(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$19(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$20(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$21(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$22(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$23(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$25$lambda$24(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$26(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$27(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(str, NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$28(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(str, NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$29(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(JvmPrimitiveType.BOOLEAN);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$30(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers, javaTypeQualifiers, javaTypeQualifiers, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$31(String str, String str2, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        JavaTypeQualifiers javaTypeQualifiers2 = NULLABLE;
        function.parameter(str2, javaTypeQualifiers, javaTypeQualifiers, javaTypeQualifiers2, javaTypeQualifiers2);
        function.returns(str, javaTypeQualifiers2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$32(String str, String str2, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str2, javaTypeQualifiers, javaTypeQualifiers, javaTypeQualifiers);
        function.returns(str, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$33(String str, String str2, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        JavaTypeQualifiers javaTypeQualifiers2 = NULLABLE;
        function.parameter(str2, javaTypeQualifiers, javaTypeQualifiers, NOT_NULLABLE, javaTypeQualifiers2);
        function.returns(str, javaTypeQualifiers2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$35$lambda$34(String str, String str2, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        JavaTypeQualifiers javaTypeQualifiers2 = NOT_NULLABLE;
        function.parameter(str, javaTypeQualifiers2);
        JavaTypeQualifiers javaTypeQualifiers3 = NULLABLE;
        function.parameter(str2, javaTypeQualifiers, javaTypeQualifiers2, javaTypeQualifiers2, javaTypeQualifiers3);
        function.returns(str, javaTypeQualifiers3);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$38$lambda$36(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(str, NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$38$lambda$37(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(str, NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$39(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM, NOT_NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$40(String str, String str2, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_NULLABLE;
        function.parameter(str, javaTypeQualifiers);
        function.returns(str2, NOT_PLATFORM, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$41(String str, String str2, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NULLABLE);
        function.returns(str2, NOT_PLATFORM, NOT_NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$42(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$44$lambda$43(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM, NOT_NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$46$lambda$45(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NULLABLE);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$48$lambda$47(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        function.returns(JvmPrimitiveType.BOOLEAN);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$50$lambda$49(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(JvmPrimitiveType.BOOLEAN);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$52$lambda$51(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.parameter(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$54$lambda$53(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$56$lambda$55(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.returns(str, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$58$lambda$57(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        JavaTypeQualifiers javaTypeQualifiers = NOT_PLATFORM;
        function.parameter(str, javaTypeQualifiers);
        function.parameter(str, javaTypeQualifiers);
        function.returns(str, javaTypeQualifiers);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$lambda$62$lambda$61$lambda$60$lambda$59(String str, SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder function) {
        Intrinsics.checkNotNullParameter(function, "$this$function");
        function.returns(str, NOT_PLATFORM);
        return Unit.INSTANCE;
    }
}
