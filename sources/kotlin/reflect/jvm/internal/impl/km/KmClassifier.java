package kotlin.reflect.jvm.internal.impl.km;

import ch.qos.logback.core.CoreConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Nodes.kt */
/* JADX INFO: loaded from: classes5.dex */
public abstract class KmClassifier {
    public /* synthetic */ KmClassifier(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private KmClassifier() {
    }

    /* JADX INFO: compiled from: Nodes.kt */
    public static final class Class extends KmClassifier {
        private final String name;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Class) && Intrinsics.areEqual(this.name, ((Class) obj).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return "Class(name=" + this.name + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Class(String name) {
            super(null);
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
        }

        public final String getName() {
            return this.name;
        }
    }

    /* JADX INFO: compiled from: Nodes.kt */
    public static final class TypeParameter extends KmClassifier {
        private final int id;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TypeParameter) && this.id == ((TypeParameter) obj).id;
        }

        public int hashCode() {
            return Integer.hashCode(this.id);
        }

        public String toString() {
            return "TypeParameter(id=" + this.id + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        public TypeParameter(int i) {
            super(null);
            this.id = i;
        }

        public final int getId() {
            return this.id;
        }
    }

    /* JADX INFO: compiled from: Nodes.kt */
    public static final class TypeAlias extends KmClassifier {
        private final String name;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TypeAlias) && Intrinsics.areEqual(this.name, ((TypeAlias) obj).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return "TypeAlias(name=" + this.name + CoreConstants.RIGHT_PARENTHESIS_CHAR;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TypeAlias(String name) {
            super(null);
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
        }

        public final String getName() {
            return this.name;
        }
    }
}
