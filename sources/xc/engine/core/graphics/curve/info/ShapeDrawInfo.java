package xc.engine.core.graphics.curve.info;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import java.util.List;
import xc.engine.api.PenEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;

/* JADX INFO: loaded from: classes6.dex */
public class ShapeDrawInfo extends CurveInfo {
    private PointF mCurvePoint;
    private final PenEngine mPenEngine;
    public Canvas mCanvas = new Canvas();
    private final Paint mStrokePaint = new Paint();
    private final Paint mFillPaint = new Paint();

    public ShapeDrawInfo(PenEngine penEngine) {
        this.mPenEngine = penEngine;
    }

    public void drawShape(Bitmap bitmap, DataShape dataShape, float f, float f2) {
        this.mCurvePoint = getCurvePoint(this.mPenEngine.mCanvasOperator);
        preparePaint(dataShape);
        this.mCanvas.setBitmap(bitmap);
        int iSaveLayer = this.mCanvas.saveLayer(null, null);
        this.mCanvas.translate(f + this.mCurvePoint.x, f2 + this.mCurvePoint.y);
        drawPath(this.mCanvas, dataShape, iSaveLayer);
    }

    public void drawShape(List<DataCurve> list) {
        for (DataCurve dataCurve : list) {
            if (dataCurve.mDataState != 3) {
                DataShape dataShape = (DataShape) dataCurve;
                preparePaint(dataShape);
                drawPath(this.mCanvas, dataShape, this.mCanvas.saveLayer(null, null));
            }
        }
    }

    public void drawShape(Canvas canvas, DataShape dataShape) {
        preparePaint(dataShape);
        drawPath(canvas, dataShape, canvas.saveLayer(null, null));
    }

    private void drawPath(Canvas canvas, DataShape dataShape, int i) {
        Path path;
        Path path2;
        if (dataShape.mShapeProp.getPenSize() != 0.0f && dataShape.mShapeProp.getShapeType() != 2) {
            if (this.mEnableScale) {
                path2 = dataShape.mScaledShapePath;
            } else {
                path2 = dataShape.mShapePath;
            }
            canvas.drawPath(path2, this.mStrokePaint);
        }
        if (this.mEnableScale) {
            path = dataShape.mScaledShapePath;
        } else {
            path = dataShape.mShapePath;
        }
        canvas.drawPath(path, this.mFillPaint);
        canvas.restoreToCount(i);
    }

    private void preparePaint(DataShape dataShape) {
        this.mStrokePaint.setColor(this.mPenEngine.mCanvasOperator.getCurCurveColor(dataShape));
        this.mStrokePaint.setAlpha(dataShape.getAlpha());
        this.mStrokePaint.setStrokeWidth(dataShape.mShapeProp.getPenSize() * getDrawRatio(dataShape));
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setAntiAlias(true);
        this.mFillPaint.setColor(this.mPenEngine.mCanvasOperator.getShapeFillColor(dataShape));
        this.mFillPaint.setAlpha(dataShape.mShapeProp.getFillAlpha());
        this.mFillPaint.setStrokeWidth(dataShape.mShapeProp.getPenSize() * getDrawRatio(dataShape));
        this.mFillPaint.setStyle(Paint.Style.FILL);
        this.mFillPaint.setAntiAlias(true);
        this.mFillPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        if (dataShape.isLineOrArrow()) {
            this.mStrokePaint.setStrokeCap(Paint.Cap.BUTT);
            this.mStrokePaint.setStrokeJoin(Paint.Join.MITER);
            this.mStrokePaint.setColor(this.mPenEngine.mCanvasOperator.getShapeFillColor(dataShape));
            this.mStrokePaint.setAlpha(dataShape.mShapeProp.getFillAlpha());
            this.mFillPaint.setStrokeCap(Paint.Cap.BUTT);
            this.mFillPaint.setStrokeJoin(Paint.Join.MITER);
            this.mFillPaint.setStrokeWidth(1.0f);
            this.mFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        } else {
            this.mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
            this.mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
        }
        this.mStrokePaint.setPathEffect(null);
    }
}
