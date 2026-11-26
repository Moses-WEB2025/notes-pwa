package kotlin.reflect.jvm.internal.impl.km;

import ch.qos.logback.core.CoreConstants;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class KmAnnotation {
    private final Map<String, KmAnnotationArgument> arguments;
    private final String className;

    /* JADX WARN: Multi-variable type inference failed */
    public KmAnnotation(String className, Map<String, ? extends KmAnnotationArgument> arguments) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.className = className;
        this.arguments = arguments;
    }

    public final Map<String, KmAnnotationArgument> getArguments() {
        return this.arguments;
    }

    public final String getClassName() {
        return this.className;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KmAnnotation)) {
            return false;
        }
        KmAnnotation kmAnnotation = (KmAnnotation) obj;
        return Intrinsics.areEqual(this.className, kmAnnotation.className) && Intrinsics.areEqual(this.arguments, kmAnnotation.arguments);
    }

    public int hashCode() {
        return (this.className.hashCode() * 31) + this.arguments.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence toString$lambda$0(Pair pair) {
        Intrinsics.checkNotNullParameter(pair, "<destruct>");
        return ((String) pair.component1()) + " = " + ((KmAnnotationArgument) pair.component2());
    }

    public String toString() {
        return "@" + this.className + CoreConstants.LEFT_PARENTHESIS_CHAR + CollectionsKt.joinToString$default(MapsKt.toList(this.arguments), null, null, null, 0, null, new Function1() { // from class: kotlin.reflect.jvm.internal.impl.km.KmAnnotation$$Lambda$0
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return KmAnnotation.toString$lambda$0((Pair) obj);
            }
        }, 31, null) + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }
}
