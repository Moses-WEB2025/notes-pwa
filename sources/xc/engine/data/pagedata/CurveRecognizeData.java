package xc.engine.data.pagedata;

import ch.qos.logback.core.CoreConstants;

/* JADX INFO: compiled from: CurveRecognizeManager.java */
/* JADX INFO: loaded from: classes6.dex */
class CurveRecognizeData {
    public String action;
    public int curveId;
    public int penColor;
    public float penSize;
    public int penType;
    public int pointCount;
    public long timestamp;
    public float[] xPoints;
    public float[] yPoints;

    CurveRecognizeData() {
    }

    public String toString() {
        return "CurveRecognizeData{curveId=" + this.curveId + ", action='" + this.action + "', timestamp=" + this.timestamp + ", xPoints=" + arrayToString(this.xPoints) + ", yPoints=" + arrayToString(this.yPoints) + ", pointCount=" + this.pointCount + ", penType=" + this.penType + ", penSize=" + this.penSize + ", penColor=" + this.penColor + CoreConstants.CURLY_RIGHT;
    }

    private String arrayToString(float[] fArr) {
        if (fArr == null) {
            return "null";
        }
        int iMin = Math.min(fArr.length, 5);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < iMin; i++) {
            sb.append(String.format("%.1f", Float.valueOf(fArr[i])));
            if (i < iMin - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        if (fArr.length > 5) {
            sb.append(", ...(+" + (fArr.length - 5) + "points)");
        }
        return sb.toString();
    }
}
