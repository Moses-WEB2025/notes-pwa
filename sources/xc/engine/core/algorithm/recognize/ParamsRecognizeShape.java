package xc.engine.core.algorithm.recognize;

/* JADX INFO: loaded from: classes6.dex */
public class ParamsRecognizeShape {
    private float distanceOffset;
    private float miniLineLength;
    private int timeLot;

    public ParamsRecognizeShape() {
        this.timeLot = 850;
        this.miniLineLength = 51.0f;
        this.distanceOffset = 17.0f;
    }

    public ParamsRecognizeShape(int i, float f, float f2) {
        this.timeLot = i;
        this.distanceOffset = f;
        this.miniLineLength = f2;
    }

    public ParamsRecognizeShape set(ParamsRecognizeShape paramsRecognizeShape) {
        this.timeLot = paramsRecognizeShape.timeLot;
        this.distanceOffset = paramsRecognizeShape.distanceOffset;
        this.miniLineLength = paramsRecognizeShape.miniLineLength;
        return this;
    }

    public int getTimeLot() {
        return this.timeLot;
    }

    public float getDistanceOffset() {
        return this.distanceOffset;
    }

    public float getMiniLineLength() {
        return this.miniLineLength;
    }
}
