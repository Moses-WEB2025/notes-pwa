package org.apache.commons.lang3.concurrent;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes6.dex */
public class EventCountCircuitBreaker extends AbstractCircuitBreaker<Integer> {
    private static final Map<AbstractCircuitBreaker.State, AbstractStateStrategy> STRATEGY_MAP = createStrategyMap();
    private final AtomicReference<CheckIntervalData> checkIntervalData;
    private final long closingInterval;
    private final int closingThreshold;
    private final long openingInterval;
    private final int openingThreshold;

    private static abstract class AbstractStateStrategy {
        protected abstract long fetchCheckInterval(EventCountCircuitBreaker eventCountCircuitBreaker);

        public abstract boolean isStateTransition(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2);

        private AbstractStateStrategy() {
        }

        public boolean isCheckIntervalFinished(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, long j) {
            return j - checkIntervalData.getCheckIntervalStart() > fetchCheckInterval(eventCountCircuitBreaker);
        }
    }

    private static final class CheckIntervalData {
        private final long checkIntervalStart;
        private final int eventCount;

        CheckIntervalData(int i, long j) {
            this.eventCount = i;
            this.checkIntervalStart = j;
        }

        public long getCheckIntervalStart() {
            return this.checkIntervalStart;
        }

        public int getEventCount() {
            return this.eventCount;
        }

        public CheckIntervalData increment(int i) {
            return i == 0 ? this : new CheckIntervalData(getEventCount() + i, getCheckIntervalStart());
        }
    }

    private static final class StateStrategyClosed extends AbstractStateStrategy {
        private StateStrategyClosed() {
            super();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.AbstractStateStrategy
        protected long fetchCheckInterval(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getOpeningInterval();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.AbstractStateStrategy
        public boolean isStateTransition(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2) {
            return checkIntervalData2.getEventCount() > eventCountCircuitBreaker.getOpeningThreshold();
        }
    }

    private static final class StateStrategyOpen extends AbstractStateStrategy {
        private StateStrategyOpen() {
            super();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.AbstractStateStrategy
        protected long fetchCheckInterval(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getClosingInterval();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.AbstractStateStrategy
        public boolean isStateTransition(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2) {
            return checkIntervalData2.getCheckIntervalStart() != checkIntervalData.getCheckIntervalStart() && checkIntervalData.getEventCount() < eventCountCircuitBreaker.getClosingThreshold();
        }
    }

    private static Map<AbstractCircuitBreaker.State, AbstractStateStrategy> createStrategyMap() {
        EnumMap enumMap = new EnumMap(AbstractCircuitBreaker.State.class);
        enumMap.put(AbstractCircuitBreaker.State.CLOSED, new StateStrategyClosed());
        enumMap.put(AbstractCircuitBreaker.State.OPEN, new StateStrategyOpen());
        return enumMap;
    }

    private static AbstractStateStrategy stateStrategy(AbstractCircuitBreaker.State state) {
        return STRATEGY_MAP.get(state);
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit) {
        this(i, j, timeUnit, i);
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit, int i2) {
        this(i, j, timeUnit, i2, j, timeUnit);
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit, int i2, long j2, TimeUnit timeUnit2) {
        this.checkIntervalData = new AtomicReference<>(new CheckIntervalData(0, 0L));
        this.openingThreshold = i;
        this.openingInterval = timeUnit.toNanos(j);
        this.closingThreshold = i2;
        this.closingInterval = timeUnit2.toNanos(j2);
    }

    private void changeStateAndStartNewCheckInterval(AbstractCircuitBreaker.State state) {
        changeState(state);
        this.checkIntervalData.set(new CheckIntervalData(0, nanoTime()));
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean checkState() {
        return performStateCheck(0);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void close() {
        super.close();
        this.checkIntervalData.set(new CheckIntervalData(0, nanoTime()));
    }

    public long getClosingInterval() {
        return this.closingInterval;
    }

    public int getClosingThreshold() {
        return this.closingThreshold;
    }

    public long getOpeningInterval() {
        return this.openingInterval;
    }

    public int getOpeningThreshold() {
        return this.openingThreshold;
    }

    public boolean incrementAndCheckState() {
        return incrementAndCheckState((Integer) 1);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean incrementAndCheckState(Integer num) {
        return performStateCheck(num.intValue());
    }

    long nanoTime() {
        return System.nanoTime();
    }

    private CheckIntervalData nextCheckIntervalData(int i, CheckIntervalData checkIntervalData, AbstractCircuitBreaker.State state, long j) {
        if (stateStrategy(state).isCheckIntervalFinished(this, checkIntervalData, j)) {
            return new CheckIntervalData(i, j);
        }
        return checkIntervalData.increment(i);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void open() {
        super.open();
        this.checkIntervalData.set(new CheckIntervalData(0, nanoTime()));
    }

    private boolean performStateCheck(int i) {
        AbstractCircuitBreaker.State stateOppositeState;
        CheckIntervalData checkIntervalData;
        EventCountCircuitBreaker eventCountCircuitBreaker;
        CheckIntervalData checkIntervalDataNextCheckIntervalData;
        while (true) {
            long jNanoTime = this.nanoTime();
            stateOppositeState = this.state.get();
            checkIntervalData = this.checkIntervalData.get();
            eventCountCircuitBreaker = this;
            int i2 = i;
            checkIntervalDataNextCheckIntervalData = eventCountCircuitBreaker.nextCheckIntervalData(i2, checkIntervalData, stateOppositeState, jNanoTime);
            if (eventCountCircuitBreaker.updateCheckIntervalData(checkIntervalData, checkIntervalDataNextCheckIntervalData)) {
                break;
            }
            this = eventCountCircuitBreaker;
            i = i2;
        }
        if (stateStrategy(stateOppositeState).isStateTransition(eventCountCircuitBreaker, checkIntervalData, checkIntervalDataNextCheckIntervalData)) {
            stateOppositeState = stateOppositeState.oppositeState();
            eventCountCircuitBreaker.changeStateAndStartNewCheckInterval(stateOppositeState);
        }
        return !isOpen(stateOppositeState);
    }

    private boolean updateCheckIntervalData(CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2) {
        return checkIntervalData == checkIntervalData2 || AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(this.checkIntervalData, checkIntervalData, checkIntervalData2);
    }
}
