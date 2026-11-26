package xc.engine.core.algorithm.estimate;

/* JADX INFO: loaded from: classes6.dex */
public class ParamsEstimate {
    private int historyCount;
    private int miniTrace;
    private int time;

    public ParamsEstimate() {
        this.time = 37;
    }

    public ParamsEstimate(int i, int i2, int i3) {
        this.historyCount = i2;
        this.time = i;
        this.miniTrace = i3;
    }

    public ParamsEstimate set(ParamsEstimate paramsEstimate) {
        this.historyCount = paramsEstimate.historyCount;
        this.time = paramsEstimate.time;
        this.miniTrace = paramsEstimate.miniTrace;
        return this;
    }

    public int getMiniTrace() {
        return this.miniTrace;
    }

    public int getHistoryCount() {
        return this.historyCount;
    }

    public int getTime() {
        return this.time;
    }

    public ParamsEstimate setTime(int i) {
        this.time = i;
        return this;
    }
}
