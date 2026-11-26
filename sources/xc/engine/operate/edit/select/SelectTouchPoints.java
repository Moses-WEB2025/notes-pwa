package xc.engine.operate.edit.select;

import android.graphics.Matrix;
import android.graphics.Path;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import xc.engine.operate.touch.TouchPoint;

/* JADX INFO: loaded from: classes6.dex */
public class SelectTouchPoints {
    public Path mPath = new Path();
    public List<TouchPoint> mTouchPoints = new ArrayList();

    public static byte[] convertTouchPointsToByteArray(SelectTouchPoints selectTouchPoints) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        for (TouchPoint touchPoint : selectTouchPoints.mTouchPoints) {
            dataOutputStream.writeFloat(touchPoint.getX());
            dataOutputStream.writeFloat(touchPoint.getY());
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void copyTouchPoints(SelectTouchPoints selectTouchPoints) {
        this.mPath = new Path(selectTouchPoints.mPath);
        this.mTouchPoints.clear();
        for (TouchPoint touchPoint : selectTouchPoints.mTouchPoints) {
            this.mTouchPoints.add(new TouchPoint(touchPoint.getX(), touchPoint.getY(), touchPoint.getTime()));
        }
    }

    public void doMove(float f, float f2) {
        this.mPath.offset(f, f2);
        for (TouchPoint touchPoint : this.mTouchPoints) {
            touchPoint.setX(touchPoint.getX() + f);
            touchPoint.setY(touchPoint.getY() + f2);
        }
    }

    public void doScale(float f, float f2, float f3, float f4) {
        Matrix matrix = new Matrix();
        matrix.setScale(f3, f4, f, f2);
        this.mPath.transform(matrix);
        for (TouchPoint touchPoint : this.mTouchPoints) {
            touchPoint.setX(((touchPoint.getX() - f) * f3) + f);
            touchPoint.setY(((touchPoint.getY() - f2) * f4) + f2);
        }
    }

    public void doRotate(double d, float f, float f2) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) d, f, f2);
        this.mPath.transform(matrix);
        double radians = (float) Math.toRadians(-d);
        for (TouchPoint touchPoint : this.mTouchPoints) {
            float x = touchPoint.getX() - f;
            double y = touchPoint.getY() - f2;
            float fCos = (float) ((((double) x) * Math.cos(radians)) + (Math.sin(radians) * y) + ((double) f));
            float fSin = (float) ((((double) (-x)) * Math.sin(radians)) + (y * Math.cos(radians)) + ((double) f2));
            touchPoint.setX(fCos);
            touchPoint.setY(fSin);
        }
    }

    public void startSelectPath(float f, float f2) {
        this.mTouchPoints.add(new TouchPoint(f, f2, System.currentTimeMillis()));
        this.mPath.moveTo(f, f2);
    }

    public void selectPathLineTo(float f, float f2) {
        this.mTouchPoints.add(new TouchPoint(f, f2, System.currentTimeMillis()));
        this.mPath.lineTo(f, f2);
    }
}
