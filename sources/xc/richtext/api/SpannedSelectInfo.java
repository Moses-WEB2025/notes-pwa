package xc.richtext.api;

import android.graphics.Path;
import android.graphics.PointF;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedSelectInfo {
    public static int SELECT_ACTION_BOTTOM = 89;
    public static int SELECT_ACTION_CROP = 16;
    public static int SELECT_ACTION_LEFT = 90;
    public static int SELECT_ACTION_MASK = 63;
    public static int SELECT_ACTION_MIXED = 12;
    public static int SELECT_ACTION_MOVE = 2;
    public static int SELECT_ACTION_NULL = 1;
    public static int SELECT_ACTION_RIGHT = 88;
    public static int SELECT_ACTION_ROTATE = 8;
    public static int SELECT_ACTION_SCALE = 4;
    public static int SELECT_ACTION_TOP = 87;
    private float mTotalAngle;
    private float mTotalScale;
    private int mSelectType = SELECT_ACTION_NULL;
    private PointF mCenter = new PointF();
    private Path mSelectPath = new Path();

    public int getSelectTypeMask() {
        return this.mSelectType & SELECT_ACTION_MASK;
    }

    public void setSelectType(int i) {
        this.mSelectType = i;
    }

    public PointF getCenter() {
        return this.mCenter;
    }

    public void setCenter(PointF pointF) {
        this.mCenter = pointF;
    }

    public float getTotalScale() {
        return this.mTotalScale;
    }

    public float getTotalAngle() {
        return this.mTotalAngle;
    }

    public void stopAction() {
        this.mSelectType = SELECT_ACTION_NULL;
    }

    public void scaleInfo(float f) {
        this.mTotalScale = f;
    }

    public void rotateInfo(float f) {
        this.mTotalAngle = f;
    }

    public Path getSelectPath() {
        return this.mSelectPath;
    }

    public void setSelectPath(Path path) {
        this.mSelectPath = path;
    }

    public void setSelectData(float[] fArr) {
        if (fArr[4] != 0.0f && fArr[5] != 0.0f) {
            Path path = new Path();
            this.mSelectPath = path;
            path.reset();
            this.mSelectPath.moveTo(fArr[0], fArr[1]);
            this.mSelectPath.lineTo(fArr[2], fArr[3]);
            this.mSelectPath.lineTo(fArr[4], fArr[5]);
            this.mSelectPath.lineTo(fArr[6], fArr[7]);
            this.mSelectPath.lineTo(fArr[0], fArr[1]);
            return;
        }
        this.mSelectPath = null;
    }

    public void cancel() {
        this.mTotalScale = 0.0f;
        this.mTotalAngle = 0.0f;
    }

    public void reset() {
        cancel();
        this.mSelectPath = null;
    }
}
