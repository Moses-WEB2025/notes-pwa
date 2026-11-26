package org.apache.commons.lang3.concurrent.locks;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;
import org.apache.commons.lang3.builder.AbstractSupplier;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.Suppliers;

/* JADX INFO: loaded from: classes6.dex */
public class LockingVisitors {

    public static class LockVisitor<O, L> {
        private final L lock;
        private final O object;
        private final Supplier<Lock> readLockSupplier;
        private final Supplier<Lock> writeLockSupplier;

        public static class LVBuilder<O, L, B extends LVBuilder<O, L, B>> extends AbstractSupplier<LockVisitor<O, L>, B, RuntimeException> {
            L lock;
            O object;
            private Supplier<Lock> readLockSupplier;
            private Supplier<Lock> writeLockSupplier;

            @Override // org.apache.commons.lang3.function.FailableSupplier
            public LockVisitor<O, L> get() {
                return new LockVisitor<>(this);
            }

            Supplier<Lock> getReadLockSupplier() {
                return this.readLockSupplier;
            }

            Supplier<Lock> getWriteLockSupplier() {
                return this.writeLockSupplier;
            }

            public B setLock(L l) {
                this.lock = l;
                return asThis();
            }

            public B setObject(O o) {
                this.object = o;
                return asThis();
            }

            public B setReadLockSupplier(Supplier<Lock> supplier) {
                this.readLockSupplier = supplier;
                return asThis();
            }

            public B setWriteLockSupplier(Supplier<Lock> supplier) {
                this.writeLockSupplier = supplier;
                return asThis();
            }
        }

        private LockVisitor(LVBuilder<O, L, ?> lVBuilder) {
            this.object = (O) Objects.requireNonNull(lVBuilder.object, "object");
            this.lock = (L) Objects.requireNonNull(lVBuilder.lock, "lock");
            this.readLockSupplier = (Supplier) Objects.requireNonNull(((LVBuilder) lVBuilder).readLockSupplier, "readLockSupplier");
            this.writeLockSupplier = (Supplier) Objects.requireNonNull(((LVBuilder) lVBuilder).writeLockSupplier, "writeLockSupplier");
        }

        protected LockVisitor(O o, L l, Supplier<Lock> supplier, Supplier<Lock> supplier2) {
            this.object = (O) Objects.requireNonNull(o, "object");
            this.lock = (L) Objects.requireNonNull(l, "lock");
            this.readLockSupplier = (Supplier) Objects.requireNonNull(supplier, "readLockSupplier");
            this.writeLockSupplier = (Supplier) Objects.requireNonNull(supplier2, "writeLockSupplier");
        }

        public void acceptReadLocked(FailableConsumer<O, ?> failableConsumer) {
            lockAcceptUnlock(this.readLockSupplier, failableConsumer);
        }

        public void acceptWriteLocked(FailableConsumer<O, ?> failableConsumer) {
            lockAcceptUnlock(this.writeLockSupplier, failableConsumer);
        }

        public <T> T applyReadLocked(FailableFunction<O, T, ?> failableFunction) {
            return (T) lockApplyUnlock(this.readLockSupplier, failableFunction);
        }

        public <T> T applyWriteLocked(FailableFunction<O, T, ?> failableFunction) {
            return (T) lockApplyUnlock(this.writeLockSupplier, failableFunction);
        }

        public L getLock() {
            return this.lock;
        }

        public O getObject() {
            return this.object;
        }

        protected void lockAcceptUnlock(Supplier<Lock> supplier, FailableConsumer<O, ?> failableConsumer) {
            Lock lock = (Lock) Objects.requireNonNull((Lock) Suppliers.get(supplier), "lock");
            lock.lock();
            try {
                Failable.accept((FailableConsumer<O, E>) failableConsumer, this.object);
            } finally {
                lock.unlock();
            }
        }

        protected <T> T lockApplyUnlock(Supplier<Lock> supplier, FailableFunction<O, T, ?> failableFunction) {
            Lock lock = (Lock) Objects.requireNonNull((Lock) Suppliers.get(supplier), "lock");
            lock.lock();
            try {
                return (T) Failable.apply(failableFunction, this.object);
            } finally {
                lock.unlock();
            }
        }
    }

    public static class ReadWriteLockVisitor<O> extends LockVisitor<O, ReadWriteLock> {

        public static class Builder<O> extends LockVisitor.LVBuilder<O, ReadWriteLock, Builder<O>> {
            @Override // org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor.LVBuilder, org.apache.commons.lang3.function.FailableSupplier
            public ReadWriteLockVisitor<O> get() {
                return new ReadWriteLockVisitor<>(this);
            }

            @Override // org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor.LVBuilder
            public Builder<O> setLock(ReadWriteLock readWriteLock) {
                Objects.requireNonNull(readWriteLock);
                setReadLockSupplier(new LockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda0(readWriteLock));
                Objects.requireNonNull(readWriteLock);
                setWriteLockSupplier(new LockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda1(readWriteLock));
                return (Builder) super.setLock(readWriteLock);
            }
        }

        public static <O> Builder<O> builder() {
            return new Builder<>();
        }

        private ReadWriteLockVisitor(Builder<O> builder) {
            super(builder);
        }

        protected ReadWriteLockVisitor(O o, ReadWriteLock readWriteLock) {
            Objects.requireNonNull(readWriteLock);
            LockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda0 lockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda0 = new LockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda0(readWriteLock);
            Objects.requireNonNull(readWriteLock);
            super(o, readWriteLock, lockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda0, new LockingVisitors$ReadWriteLockVisitor$$ExternalSyntheticLambda1(readWriteLock));
        }
    }

    public static class ReentrantLockVisitor<O> extends LockVisitor<O, ReentrantLock> {
        static /* synthetic */ Lock lambda$new$0(ReentrantLock reentrantLock) {
            return reentrantLock;
        }

        static /* synthetic */ Lock lambda$new$1(ReentrantLock reentrantLock) {
            return reentrantLock;
        }

        public static class Builder<O> extends LockVisitor.LVBuilder<O, ReentrantLock, Builder<O>> {
            static /* synthetic */ Lock lambda$setLock$0(ReentrantLock reentrantLock) {
                return reentrantLock;
            }

            static /* synthetic */ Lock lambda$setLock$1(ReentrantLock reentrantLock) {
                return reentrantLock;
            }

            @Override // org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor.LVBuilder, org.apache.commons.lang3.function.FailableSupplier
            public ReentrantLockVisitor<O> get() {
                return new ReentrantLockVisitor<>(this);
            }

            @Override // org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor.LVBuilder
            public Builder<O> setLock(final ReentrantLock reentrantLock) {
                setReadLockSupplier(new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$ReentrantLockVisitor$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return LockingVisitors.ReentrantLockVisitor.Builder.lambda$setLock$0(reentrantLock);
                    }
                });
                setWriteLockSupplier(new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$ReentrantLockVisitor$Builder$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return LockingVisitors.ReentrantLockVisitor.Builder.lambda$setLock$1(reentrantLock);
                    }
                });
                return (Builder) super.setLock(reentrantLock);
            }
        }

        public static <O> Builder<O> builder() {
            return new Builder<>();
        }

        private ReentrantLockVisitor(Builder<O> builder) {
            super(builder);
        }

        protected ReentrantLockVisitor(O o, final ReentrantLock reentrantLock) {
            super(o, reentrantLock, new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$ReentrantLockVisitor$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return LockingVisitors.ReentrantLockVisitor.lambda$new$0(reentrantLock);
                }
            }, new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$ReentrantLockVisitor$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return LockingVisitors.ReentrantLockVisitor.lambda$new$1(reentrantLock);
                }
            });
        }
    }

    public static class StampedLockVisitor<O> extends LockVisitor<O, StampedLock> {

        public static class Builder<O> extends LockVisitor.LVBuilder<O, StampedLock, Builder<O>> {
            @Override // org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor.LVBuilder, org.apache.commons.lang3.function.FailableSupplier
            public StampedLockVisitor<O> get() {
                return new StampedLockVisitor<>(this);
            }

            @Override // org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor.LVBuilder
            public Builder<O> setLock(final StampedLock stampedLock) {
                Objects.requireNonNull(stampedLock);
                setReadLockSupplier(new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$StampedLockVisitor$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return stampedLock.asReadLock();
                    }
                });
                Objects.requireNonNull(stampedLock);
                setWriteLockSupplier(new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$StampedLockVisitor$Builder$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return stampedLock.asWriteLock();
                    }
                });
                return (Builder) super.setLock(stampedLock);
            }
        }

        public static <O> Builder<O> builder() {
            return new Builder<>();
        }

        private StampedLockVisitor(Builder<O> builder) {
            super(builder);
        }

        protected StampedLockVisitor(O o, final StampedLock stampedLock) {
            Objects.requireNonNull(stampedLock);
            Supplier supplier = new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$StampedLockVisitor$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return stampedLock.asReadLock();
                }
            };
            Objects.requireNonNull(stampedLock);
            super(o, stampedLock, supplier, new Supplier() { // from class: org.apache.commons.lang3.concurrent.locks.LockingVisitors$StampedLockVisitor$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return stampedLock.asWriteLock();
                }
            });
        }
    }

    public static <O> ReadWriteLockVisitor<O> create(O o, ReadWriteLock readWriteLock) {
        return new ReadWriteLockVisitor<>(o, readWriteLock);
    }

    public static <O> ReentrantLockVisitor<O> create(O o, ReentrantLock reentrantLock) {
        return new ReentrantLockVisitor<>(o, reentrantLock);
    }

    public static <O> ReentrantLockVisitor<O> reentrantLockVisitor(O o) {
        return create(o, new ReentrantLock());
    }

    public static <O> ReadWriteLockVisitor<O> reentrantReadWriteLockVisitor(O o) {
        return create(o, new ReentrantReadWriteLock());
    }

    public static <O> StampedLockVisitor<O> stampedLockVisitor(O o) {
        return new StampedLockVisitor<>(o, new StampedLock());
    }

    @Deprecated
    public LockingVisitors() {
    }
}
