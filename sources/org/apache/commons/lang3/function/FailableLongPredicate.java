package org.apache.commons.lang3.function;

import java.lang.Throwable;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface FailableLongPredicate<E extends Throwable> {
    public static final FailableLongPredicate FALSE = new FailableLongPredicate() { // from class: org.apache.commons.lang3.function.FailableLongPredicate$$ExternalSyntheticLambda3
        @Override // org.apache.commons.lang3.function.FailableLongPredicate
        public final boolean test(long j) {
            return FailableLongPredicate.lambda$static$0(j);
        }
    };
    public static final FailableLongPredicate TRUE = new FailableLongPredicate() { // from class: org.apache.commons.lang3.function.FailableLongPredicate$$ExternalSyntheticLambda4
        @Override // org.apache.commons.lang3.function.FailableLongPredicate
        public final boolean test(long j) {
            return FailableLongPredicate.lambda$static$1(j);
        }
    };

    static /* synthetic */ boolean lambda$static$0(long j) throws Throwable {
        return false;
    }

    static /* synthetic */ boolean lambda$static$1(long j) throws Throwable {
        return true;
    }

    boolean test(long j) throws Throwable;

    static <E extends Throwable> FailableLongPredicate<E> falsePredicate() {
        return FALSE;
    }

    static <E extends Throwable> FailableLongPredicate<E> truePredicate() {
        return TRUE;
    }

    default FailableLongPredicate<E> and(final FailableLongPredicate<E> failableLongPredicate) {
        Objects.requireNonNull(failableLongPredicate);
        return new FailableLongPredicate() { // from class: org.apache.commons.lang3.function.FailableLongPredicate$$ExternalSyntheticLambda0
            @Override // org.apache.commons.lang3.function.FailableLongPredicate
            public final boolean test(long j) {
                return FailableLongPredicate.lambda$and$2(this.f$0, failableLongPredicate, j);
            }
        };
    }

    static /* synthetic */ boolean lambda$and$2(FailableLongPredicate _this, FailableLongPredicate failableLongPredicate, long j) throws Throwable {
        return _this.test(j) && failableLongPredicate.test(j);
    }

    static /* synthetic */ boolean lambda$negate$3(FailableLongPredicate _this, long j) throws Throwable {
        return !_this.test(j);
    }

    default FailableLongPredicate<E> negate() {
        return new FailableLongPredicate() { // from class: org.apache.commons.lang3.function.FailableLongPredicate$$ExternalSyntheticLambda1
            @Override // org.apache.commons.lang3.function.FailableLongPredicate
            public final boolean test(long j) {
                return FailableLongPredicate.lambda$negate$3(this.f$0, j);
            }
        };
    }

    default FailableLongPredicate<E> or(final FailableLongPredicate<E> failableLongPredicate) {
        Objects.requireNonNull(failableLongPredicate);
        return new FailableLongPredicate() { // from class: org.apache.commons.lang3.function.FailableLongPredicate$$ExternalSyntheticLambda2
            @Override // org.apache.commons.lang3.function.FailableLongPredicate
            public final boolean test(long j) {
                return FailableLongPredicate.lambda$or$4(this.f$0, failableLongPredicate, j);
            }
        };
    }

    static /* synthetic */ boolean lambda$or$4(FailableLongPredicate _this, FailableLongPredicate failableLongPredicate, long j) throws Throwable {
        return _this.test(j) || failableLongPredicate.test(j);
    }
}
