package ch.qos.logback.core.util;

import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes.dex */
public class DefaultInvocationGate implements InvocationGate {
    static final int DEFAULT_MASK = 15;
    static final int MASK_DECREASE_RIGHT_SHIFT_COUNT = 2;
    private static final long MASK_DECREASE_THRESHOLD = 800;
    private static final long MASK_INCREASE_THRESHOLD = 100;
    private static final int MAX_MASK = 65535;
    private long invocationCounter;
    long lowerLimitForMaskMatch;
    private volatile long mask;
    private long maxDelayThreshold;
    private long minDelayThreshold;
    long upperLimitForNoMaskMatch;

    public DefaultInvocationGate() {
        this(MASK_INCREASE_THRESHOLD, MASK_DECREASE_THRESHOLD, System.currentTimeMillis());
    }

    public DefaultInvocationGate(long j, long j2, long j3) {
        this.mask = 15L;
        this.invocationCounter = 0L;
        this.minDelayThreshold = j;
        this.maxDelayThreshold = j2;
        this.lowerLimitForMaskMatch = j + j3;
        this.upperLimitForNoMaskMatch = j3 + j2;
    }

    private void decreaseMask() {
        this.mask >>>= 2;
    }

    private void increaseMask() {
        if (this.mask >= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            return;
        }
        this.mask = (this.mask << 1) | 1;
    }

    private void updateLimits(long j) {
        this.lowerLimitForMaskMatch = this.minDelayThreshold + j;
        this.upperLimitForNoMaskMatch = j + this.maxDelayThreshold;
    }

    public long getInvocationCounter() {
        return this.invocationCounter;
    }

    long getMask() {
        return this.mask;
    }

    @Override // ch.qos.logback.core.util.InvocationGate
    public final boolean isTooSoon(long j) {
        long j2 = this.invocationCounter;
        this.invocationCounter = 1 + j2;
        boolean z = (j2 & this.mask) == this.mask;
        if (z) {
            if (j < this.lowerLimitForMaskMatch) {
                increaseMask();
            }
            updateLimits(j);
        } else if (j > this.upperLimitForNoMaskMatch) {
            decreaseMask();
            updateLimits(j);
            return false;
        }
        return !z;
    }
}
