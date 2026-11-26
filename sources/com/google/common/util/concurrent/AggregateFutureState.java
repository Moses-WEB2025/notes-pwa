package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
abstract class AggregateFutureState<OutputT> extends AbstractFuture.TrustedFuture<OutputT> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log = Logger.getLogger(AggregateFutureState.class.getName());
    private volatile int remaining;

    @CheckForNull
    private volatile Set<Throwable> seenExceptions = null;

    abstract void addInitialException(Set<Throwable> seen);

    static /* synthetic */ int access$306(AggregateFutureState aggregateFutureState) {
        int i = aggregateFutureState.remaining - 1;
        aggregateFutureState.remaining = i;
        return i;
    }

    static {
        AtomicHelper synchronizedAtomicHelper;
        Throwable th = null;
        byte b = 0;
        try {
            synchronizedAtomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
        } catch (Error | RuntimeException e) {
            synchronizedAtomicHelper = new SynchronizedAtomicHelper();
            th = e;
        }
        ATOMIC_HELPER = synchronizedAtomicHelper;
        if (th != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
    }

    AggregateFutureState(int remainingFutures) {
        this.remaining = remainingFutures;
    }

    final Set<Throwable> getOrInitSeenExceptions() {
        Set<Throwable> set = this.seenExceptions;
        if (set != null) {
            return set;
        }
        Set<Throwable> setNewConcurrentHashSet = Sets.newConcurrentHashSet();
        addInitialException(setNewConcurrentHashSet);
        ATOMIC_HELPER.compareAndSetSeenExceptions(this, null, setNewConcurrentHashSet);
        return (Set) Objects.requireNonNull(this.seenExceptions);
    }

    final int decrementRemainingAndGet() {
        return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
    }

    final void clearSeenExceptions() {
        this.seenExceptions = null;
    }

    private static abstract class AtomicHelper {
        abstract void compareAndSetSeenExceptions(AggregateFutureState<?> state, @CheckForNull Set<Throwable> expect, Set<Throwable> update);

        abstract int decrementAndGetRemainingCount(AggregateFutureState<?> state);

        private AtomicHelper() {
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicIntegerFieldUpdater<AggregateFutureState<?>> remainingCountUpdater;
        final AtomicReferenceFieldUpdater<AggregateFutureState<?>, Set<Throwable>> seenExceptionsUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater seenExceptionsUpdater, AtomicIntegerFieldUpdater remainingCountUpdater) {
            super();
            this.seenExceptionsUpdater = seenExceptionsUpdater;
            this.remainingCountUpdater = remainingCountUpdater;
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        void compareAndSetSeenExceptions(AggregateFutureState<?> state, @CheckForNull Set<Throwable> expect, Set<Throwable> update) {
            AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(this.seenExceptionsUpdater, state, expect, update);
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        int decrementAndGetRemainingCount(AggregateFutureState<?> state) {
            return this.remainingCountUpdater.decrementAndGet(state);
        }
    }

    private static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        void compareAndSetSeenExceptions(AggregateFutureState<?> state, @CheckForNull Set<Throwable> expect, Set<Throwable> update) {
            synchronized (state) {
                if (((AggregateFutureState) state).seenExceptions == expect) {
                    ((AggregateFutureState) state).seenExceptions = update;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFutureState.AtomicHelper
        int decrementAndGetRemainingCount(AggregateFutureState<?> state) {
            int iAccess$306;
            synchronized (state) {
                iAccess$306 = AggregateFutureState.access$306(state);
            }
            return iAccess$306;
        }
    }
}
