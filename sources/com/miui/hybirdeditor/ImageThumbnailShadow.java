package com.miui.hybirdeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.UIUtils;
import com.miui.hybirdeditor.jsbridge.EmbedElementInfoBean;
import com.miui.notes.NoteApp;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes3.dex */
public class ImageThumbnailShadow {
    private RectF mBound;
    private Context mContext;
    private int mDeltaX;
    private int mDeltaY;
    private String mImagePath;
    private AppCompatImageView mImageShadowView;
    private EmbedElementInfoBean mInfoBean;
    private float mScaleGap;
    private Drawable mShadowDrawable;
    private Paint mShadowPaint;
    private Paint mShadowPaintTwo;
    private final float mShadowLayerRadius = NoteApp.getInstance().getResources().getDimension(R.dimen.image_drag_shadow_shadow_layer_radius);
    private final float mShadowLayerX = NoteApp.getInstance().getResources().getDimension(R.dimen.image_drag_shadow_shadow_layer_x);
    private final float mShadowLayerY = NoteApp.getInstance().getResources().getDimension(R.dimen.image_drag_shadow_shadow_layer_y);
    private final float mShadowLayerRadiusTwo = NoteApp.getInstance().getResources().getDimension(R.dimen.image_drag_shadow_shadow_layer_radius_two);
    private final float mShadowLayerXTwo = NoteApp.getInstance().getResources().getDimension(R.dimen.image_drag_shadow_shadow_layer_x_two);
    private final float mShadowLayerYTwo = NoteApp.getInstance().getResources().getDimension(R.dimen.image_drag_shadow_shadow_layer_y_two);
    private float mProcess = 0.0f;
    private View.DragShadowBuilder mDragShadowBuilder = null;
    private boolean mIsNeedHeightCrop = false;
    private boolean mIsNeedWidthCrop = false;

    public ImageThumbnailShadow(Context context, EmbedElementInfoBean embedElementInfoBean, String str) {
        this.mContext = context;
        this.mInfoBean = embedElementInfoBean;
        this.mImagePath = str;
    }

    public View.DragShadowBuilder createImageThumbnailShadow() {
        AppCompatImageView appCompatImageView = (AppCompatImageView) View.inflate(this.mContext, R.layout.note_edit_image_drag_thumbnail, null);
        if (appCompatImageView == null) {
            throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mImageShadowView = appCompatImageView;
        int elementWidth = this.mInfoBean.getElementWidth();
        int elementHeight = this.mInfoBean.getElementHeight();
        this.mIsNeedHeightCrop = elementHeight / elementWidth > 5;
        this.mIsNeedWidthCrop = elementWidth / elementHeight > 5;
        this.mScaleGap = (this.mContext.getResources().getDimensionPixelSize(R.dimen.image_drag_width) * 1.0f) / this.mInfoBean.getElementWidth();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), ImageUtils.INSTANCE.createThumbnail(this.mImagePath, true, this.mIsNeedHeightCrop || this.mIsNeedWidthCrop));
        this.mShadowDrawable = bitmapDrawable;
        bitmapDrawable.setAlpha(255);
        appCompatImageView.setImageDrawable(this.mShadowDrawable);
        appCompatImageView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        float f = this.mScaleGap;
        if (f > 1.0f) {
            appCompatImageView.layout(0, 0, (int) (this.mIsNeedWidthCrop ? elementHeight * 5 : elementWidth * f), (int) (this.mIsNeedHeightCrop ? elementWidth * 5 : f * elementHeight));
        } else {
            appCompatImageView.layout(0, 0, this.mIsNeedWidthCrop ? elementHeight * 5 : elementWidth, this.mIsNeedHeightCrop ? elementWidth * 5 : elementHeight);
        }
        appCompatImageView.invalidate();
        Paint paint = new Paint();
        this.mShadowPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mShadowPaint.setColor(0);
        this.mShadowPaint.setShadowLayer(this.mShadowLayerRadius, this.mShadowLayerX, this.mShadowLayerY, this.mContext.getColor(R.color.image_drag_shadow_shadow_layer_color));
        Paint paint2 = new Paint();
        this.mShadowPaintTwo = paint2;
        paint2.setColor(0);
        this.mShadowPaintTwo.setStyle(Paint.Style.STROKE);
        this.mShadowPaintTwo.setShadowLayer(this.mShadowLayerRadiusTwo, this.mShadowLayerXTwo, this.mShadowLayerYTwo, this.mContext.getColor(R.color.image_drag_shadow_shadow_layer_color));
        RectF rectF = new RectF();
        this.mBound = rectF;
        float f2 = this.mScaleGap;
        if (f2 > 1.0f) {
            rectF.set(0.0f, 0.0f, (int) (this.mIsNeedWidthCrop ? elementHeight * 5 : elementWidth * f2), (int) (this.mIsNeedHeightCrop ? elementWidth * 5 : f2 * elementHeight));
        } else {
            rectF.set(0.0f, 0.0f, this.mIsNeedWidthCrop ? elementHeight * 5 : elementWidth, this.mIsNeedHeightCrop ? elementWidth * 5 : elementHeight);
        }
        this.mProcess = 0.0f;
        if (!UIUtils.isAboveAndroidS()) {
            this.mProcess = 1.0f;
        }
        return updateImageThumbnailShadow(this.mProcess);
    }

    public View.DragShadowBuilder updateImageThumbnailShadow(float f) {
        if (this.mIsNeedHeightCrop || this.mIsNeedWidthCrop) {
            f = 1.0f;
        }
        this.mProcess = f;
        float dimensionPixelSize = 1.0f - ((1.0f - ((this.mContext.getResources().getDimensionPixelSize(R.dimen.image_drag_width) * 1.0f) / this.mInfoBean.getElementWidth())) * this.mProcess);
        this.mDeltaX = this.mInfoBean.clickedXInScreen - this.mInfoBean.eLeft;
        if (this.mIsNeedHeightCrop) {
            this.mDeltaY = (this.mInfoBean.clickedYInScreen - this.mInfoBean.eTop) / (this.mInfoBean.getElementHeight() / this.mInfoBean.getElementWidth());
        } else {
            this.mDeltaY = this.mInfoBean.clickedYInScreen - this.mInfoBean.eTop;
        }
        this.mImageShadowView.setScaleX(dimensionPixelSize);
        this.mImageShadowView.setScaleY(dimensionPixelSize);
        DragShadowBuilder dragShadowBuilder = new DragShadowBuilder(this.mImageShadowView);
        this.mDragShadowBuilder = dragShadowBuilder;
        return dragShadowBuilder;
    }

    public View.DragShadowBuilder setAlpha() {
        this.mShadowDrawable.setAlpha(127);
        this.mImageShadowView.setImageDrawable(this.mShadowDrawable);
        return this.mDragShadowBuilder;
    }

    protected class DragShadowBuilder extends View.DragShadowBuilder {
        public DragShadowBuilder(View view) {
            super(view);
        }

        @Override // android.view.View.DragShadowBuilder
        public void onProvideShadowMetrics(Point point, Point point2) {
            super.onProvideShadowMetrics(point, point2);
            point2.set(ImageThumbnailShadow.this.mDeltaX, ImageThumbnailShadow.this.mDeltaY);
        }

        @Override // android.view.View.DragShadowBuilder
        public void onDrawShadow(Canvas canvas) {
            View view = getView();
            if (view != null) {
                canvas.save();
                view.getDrawingRect(new Rect());
                PointF pointF = new PointF(view.getScaleX(), view.getScaleY());
                canvas.translate(ImageThumbnailShadow.this.mDeltaX * (1.0f - pointF.x), ImageThumbnailShadow.this.mDeltaY * (1.0f - pointF.y));
                canvas.scale(pointF.x, pointF.y);
                canvas.drawRoundRect(ImageThumbnailShadow.this.mBound, ImageThumbnailShadow.this.mShadowLayerX, ImageThumbnailShadow.this.mShadowLayerY, ImageThumbnailShadow.this.mShadowPaint);
                canvas.drawRoundRect(ImageThumbnailShadow.this.mBound, ImageThumbnailShadow.this.mShadowLayerXTwo, ImageThumbnailShadow.this.mShadowLayerYTwo, ImageThumbnailShadow.this.mShadowPaintTwo);
                view.draw(canvas);
                canvas.restore();
            }
        }
    }
}
