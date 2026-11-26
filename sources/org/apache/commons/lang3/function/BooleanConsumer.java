package org.apache.commons.lang3.function;

import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface BooleanConsumer {
    public static final BooleanConsumer NOP = new BooleanConsumer() { // from class: org.apache.commons.lang3.function.BooleanConsumer$$ExternalSyntheticLambda0
        @Override // org.apache.commons.lang3.function.BooleanConsumer
        public final void accept(boolean z) {
            BooleanConsumer.lambda$static$0(z);
        }
    };

    static /* synthetic */ void lambda$static$0(boolean z) {
    }

    void accept(boolean z);

    static BooleanConsumer nop() {
        return NOP;
    }

    default BooleanConsumer andThen(final BooleanConsumer booleanConsumer) {
        Objects.requireNonNull(booleanConsumer);
        return new BooleanConsumer() { // from class: org.apache.commons.lang3.function.BooleanConsumer$$ExternalSyntheticLambda1
            @Override // org.apache.commons.lang3.function.BooleanConsumer
            public final void accept(boolean z) {
                BooleanConsumer.lambda$andThen$1(this.f$0, booleanConsumer, z);
            }
        };
    }

    static /* synthetic */ void lambda$andThen$1(BooleanConsumer _this, BooleanConsumer booleanConsumer, boolean z) {
        _this.accept(z);
        booleanConsumer.accept(z);
    }
}
