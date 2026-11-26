package org.jsoup.internal;

import java.lang.ref.SoftReference;
import java.util.ArrayDeque;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes6.dex */
public class SoftPool<T> {
    static final int MaxIdle = 12;
    private final Supplier<T> initializer;
    final ThreadLocal<SoftReference<ArrayDeque<T>>> threadLocalStack = ThreadLocal.withInitial(new Supplier() { // from class: org.jsoup.internal.SoftPool$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return SoftPool.lambda$new$0();
        }
    });

    public SoftPool(Supplier<T> supplier) {
        this.initializer = supplier;
    }

    static /* synthetic */ SoftReference lambda$new$0() {
        return new SoftReference(new ArrayDeque());
    }

    public T borrow() {
        ArrayDeque<T> stack = getStack();
        if (!stack.isEmpty()) {
            return stack.pop();
        }
        return this.initializer.get();
    }

    public void release(T t) {
        ArrayDeque<T> stack = getStack();
        if (stack.size() < 12) {
            stack.push(t);
        }
    }

    ArrayDeque<T> getStack() {
        ArrayDeque<T> arrayDeque = this.threadLocalStack.get().get();
        if (arrayDeque != null) {
            return arrayDeque;
        }
        ArrayDeque<T> arrayDeque2 = new ArrayDeque<>();
        this.threadLocalStack.set(new SoftReference<>(arrayDeque2));
        return arrayDeque2;
    }
}
