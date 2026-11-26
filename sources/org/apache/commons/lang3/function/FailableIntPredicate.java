package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableIntPredicate<E extends Throwable> {
    public static final FailableIntPredicate FALSE = new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda1
        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public final boolean test(int i) {
            return FailableIntPredicate.lambda$static$0(i);
        }
    };
    public static final FailableIntPredicate TRUE = new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda2
        @Override // org.apache.commons.lang3.function.FailableIntPredicate
        public final boolean test(int i) {
            return FailableIntPredicate.lambda$static$1(i);
        }
    };

    static /* synthetic */ boolean lambda$static$0(int i) throws Throwable {
        return false;
    }

    static /* synthetic */ boolean lambda$static$1(int i) throws Throwable {
        return true;
    }

    boolean test(int i) throws Throwable;

    static <E extends Throwable> FailableIntPredicate<E> falsePredicate() {
        return FALSE;
    }

    static <E extends Throwable> FailableIntPredicate<E> truePredicate() {
        return TRUE;
    }

    default FailableIntPredicate<E> and(final FailableIntPredicate<E> failableIntPredicate) {
        Objects.requireNonNull(failableIntPredicate);
        return new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableIntPredicate
            public final boolean test(int i) {
                return FailableIntPredicate.lambda$and$2(this.f$0, failableIntPredicate, i);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$2(FailableIntPredicate _this, FailableIntPredicate failableIntPredicate, int i) throws Throwable {
        return _this.test(i) && failableIntPredicate.test(i);
    }

    static /* synthetic */ boolean lambda$negate$3(FailableIntPredicate _this, int i) throws Throwable {
        return !_this.test(i);
    }

    default FailableIntPredicate<E> negate() {
        return new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda4
            @Override // org.apache.commons.lang3.function.FailableIntPredicate
            public final boolean test(int i) {
                return FailableIntPredicate.lambda$negate$3(this.f$0, i);
            }
        };
    }

    default FailableIntPredicate<E> or(final FailableIntPredicate<E> failableIntPredicate) {
        Objects.requireNonNull(failableIntPredicate);
        return new FailableIntPredicate() { // from class: org.apache.commons.lang3.function.FailableIntPredicate$$ExternalSyntheticLambda3
            @Override // org.apache.commons.lang3.function.FailableIntPredicate
            public final boolean test(int i) {
                return FailableIntPredicate.lambda$or$4(this.f$0, failableIntPredicate, i);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$4(FailableIntPredicate _this, FailableIntPredicate failableIntPredicate, int i) throws Throwable {
        return _this.test(i) || failableIntPredicate.test(i);
    }
}
