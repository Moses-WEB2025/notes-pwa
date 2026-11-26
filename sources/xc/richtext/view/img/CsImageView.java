package xc.richtext.view.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import xc.richtext.operate.edit.SpannedSelectObject;

/* JADX INFO: loaded from: classes3.dex */
public class CsImageView extends View {
    public static final String TAG = "CsImageView";
    public CsImageViewModel mCsImageViewModel;
    private final RectF mRectF;

    public CsImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRectF = new RectF();
        this.mCsImageViewModel = new CsImageViewModel(this);
    }

    public CsImageViewModel getImageViewModel() {
        return this.mCsImageViewModel;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        SpannedImageManager.LoadRunnable loadRunnable;
        Bitmap bitmap;
        super.onDraw(canvas);
        CsImageViewModel csImageViewModel = this.mCsImageViewModel;
        if (csImageViewModel == null || (loadRunnable = csImageViewModel.mLoadRunable) == null) {
            return;
        }
        if ((!loadRunnable.isNeedRelease && this.mCsImageViewModel.mLoadRunable.mBitmap == null) || (bitmap = this.mCsImageViewModel.mLoadRunable.mBitmap) == null || bitmap.isRecycled()) {
            return;
        }
        try {
            RectF drawRectF = this.mCsImageViewModel.mSpannedData.getDrawRectF(this.mCsImageViewModel.mEngineBindContext.screenRenderProvider);
            RectF rotatedBoundingBox = this.mCsImageViewModel.mSpannedData.getRotatedBoundingBox(this.mCsImageViewModel.mEngineBindContext.screenRenderProvider);
            SpannedSelectObject spannedSelectObject = this.mCsImageViewModel.mEngineBindContext.mSpannedEditOperator.mSpannedSelectObject;
            if (spannedSelectObject != null && spannedSelectObject.isContainId(this.mCsImageViewModel.mSpannedData.mId)) {
                drawRectF.set(this.mCsImageViewModel.mEngineBindContext.mSpannedEditOperator.mSpannedSelectObject.getRectFById(this.mCsImageViewModel.mSpannedData.mId));
            }
            this.mRectF.set(0.0f, 0.0f, drawRectF.width(), drawRectF.height());
            float f = drawRectF.left - rotatedBoundingBox.left;
            float f2 = drawRectF.top - rotatedBoundingBox.top;
            float fWidth = rotatedBoundingBox.width() / 2.0f;
            float fHeight = rotatedBoundingBox.height() / 2.0f;
            canvas.save();
            float f3 = this.mCsImageViewModel.mSpannedData.mDegrees;
            if (f3 != 0.0f) {
                canvas.rotate(f3, fWidth, fHeight);
            }
            canvas.translate(f, f2);
            canvas.drawBitmap(bitmap, (Rect) null, this.mRectF, this.mCsImageViewModel.mPaint);
            canvas.restore();
            canvas.drawPoint(fWidth, fHeight, this.mCsImageViewModel.mPaint);
        } catch (Exception unused) {
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CsImageViewModel csImageViewModel = this.mCsImageViewModel;
        if (csImageViewModel != null) {
            csImageViewModel.release();
        }
        this.mCsImageViewModel = null;
    }
}
