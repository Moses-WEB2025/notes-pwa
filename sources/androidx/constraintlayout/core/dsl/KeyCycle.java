package androidx.constraintlayout.core.dsl;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* JADX INFO: loaded from: classes.dex */
public class KeyCycle extends KeyAttribute {
    private static final String TAG = "KeyCycle";
    private float mWaveOffset;
    private float mWavePeriod;
    private float mWavePhase;
    private Wave mWaveShape;

    public enum Wave {
        SIN,
        SQUARE,
        TRIANGLE,
        SAW,
        REVERSE_SAW,
        COS
    }

    KeyCycle(int i, String str) {
        super(i, str);
        this.mWaveShape = null;
        this.mWavePeriod = Float.NaN;
        this.mWaveOffset = Float.NaN;
        this.mWavePhase = Float.NaN;
        this.TYPE = "KeyCycle";
    }

    public Wave getShape() {
        return this.mWaveShape;
    }

    public void setShape(Wave wave) {
        this.mWaveShape = wave;
    }

    public float getPeriod() {
        return this.mWavePeriod;
    }

    public void setPeriod(float f) {
        this.mWavePeriod = f;
    }

    public float getOffset() {
        return this.mWaveOffset;
    }

    public void setOffset(float f) {
        this.mWaveOffset = f;
    }

    public float getPhase() {
        return this.mWavePhase;
    }

    public void setPhase(float f) {
        this.mWavePhase = f;
    }

    @Override // androidx.constraintlayout.core.dsl.KeyAttribute
    protected void attributesToString(StringBuilder sb) {
        super.attributesToString(sb);
        if (this.mWaveShape != null) {
            sb.append("shape:'").append(this.mWaveShape).append("',\n");
        }
        append(sb, TypedValues.CycleType.S_WAVE_PERIOD, this.mWavePeriod);
        append(sb, "offset", this.mWaveOffset);
        append(sb, TypedValues.CycleType.S_WAVE_PHASE, this.mWavePhase);
    }
}
