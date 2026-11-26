package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableDoublePredicate<E extends Throwable> {
    public static final FailableDoublePredicate FALSE = new FailableDoublePredicate() { // from class: org.apache.commons.lang3.function.FailableDoublePredicate$$ExternalSyntheticLambda3
        @Override // org.apache.commons.lang3.function.FailableDoublePredicate
        public final boolean test(double d) {
            return FailableDoublePredicate.lambda$static$0(d);
        }
    };
    public static final FailableDoublePredicate TRUE = new FailableDoublePredicate() { // from class: org.apache.commons.lang3.function.FailableDoublePredicate$$ExternalSyntheticLambda4
        @Override // org.apache.commons.lang3.function.FailableDoublePredicate
        public final boolean test(double d) {
            return FailableDoublePredicate.lambda$static$1(d);
        }
    };

    static /* synthetic */ boolean lambda$static$0(double d) throws Throwable {
        return false;
    }

    static /* synthetic */ boolean lambda$static$1(double d) throws Throwable {
        return true;
    }

    boolean test(double d) throws Throwable;

    static <E extends Throwable> FailableDoublePredicate<E> falsePredicate() {
        return FALSE;
    }

    static <E extends Throwable> FailableDoublePredicate<E> truePredicate() {
        return TRUE;
    }

    default FailableDoublePredicate<E> and(final FailableDoublePredicate<E> failableDoublePredicate) {
        Objects.requireNonNull(failableDoublePredicate);
        return new FailableDoublePredicate() { // from class: org.apache.commons.lang3.function.FailableDoublePredicate$$ExternalSyntheticLambda1
            @Override // org.apache.commons.lang3.function.FailableDoublePredicate
            public final boolean test(double d) {
                return FailableDoublePredicate.lambda$and$2(this.f$0, failableDoublePredicate, d);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$2(FailableDoublePredicate _this, FailableDoublePredicate failableDoublePredicate, double d) throws Throwable {
        return _this.test(d) && failableDoublePredicate.test(d);
    }

    static /* synthetic */ boolean lambda$negate$3(FailableDoublePredicate _this, double d) throws Throwable {
        return !_this.test(d);
    }

    default FailableDoublePredicate<E> negate() {
        return new FailableDoublePredicate() { // from class: org.apache.commons.lang3.function.FailableDoublePredicate$$ExternalSyntheticLambda2
            @Override // org.apache.commons.lang3.function.FailableDoublePredicate
            public final boolean test(double d) {
                return FailableDoublePredicate.lambda$negate$3(this.f$0, d);
            }
        };
    }

    default FailableDoublePredicate<E> or(final FailableDoublePredicate<E> failableDoublePredicate) {
        Objects.requireNonNull(failableDoublePredicate);
        return new FailableDoublePredicate() { // from class: org.apache.commons.lang3.function.FailableDoublePredicate$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableDoublePredicate
            public final boolean test(double d) {
                return FailableDoublePredicate.lambda$or$4(this.f$0, failableDoublePredicate, d);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$4(FailableDoublePredicate _this, FailableDoublePredicate failableDoublePredicate, double d) throws Throwable {
        return _this.test(d) || failableDoublePredicate.test(d);
    }
}
