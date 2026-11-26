package com.miui.richeditor.share.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.miui.common.tool.Logger;
import com.miui.common.tool.SmoothDrawHelper;
import com.miui.common.tool.UIUtils;
import com.miui.duokantext.Encoder;
import com.miui.duokantext.EncoderFactory;
import com.miui.notes.R;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.share.element.Element;
import com.miui.richeditor.share.element.ImageElement;
import com.miui.richeditor.share.render.common.ElementRender;
import com.miui.richeditor.share.render.common.ImageElementRender;
import com.miui.richeditor.share.utils.ImageElementPreloader;
import com.miui.richeditor.style.LinkCardSpanHelper;
import com.miui.richeditor.style.render.AudioSpanHelper;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Render {
    protected static final int MAX_IMAGE_HEIGHT = 65500;
    protected static final int RENDER_BUFFER_HEIGHT = 2048;
    protected Drawable mBackground;
    protected Bitmap mBgBitmap;
    protected int mBgDrawHeight;
    protected Context mContext;
    protected DrawFilter mDrawFilter;
    protected List<Element> mElements;
    protected int mHeight;
    protected List<ImageElementRender> mImageRenders;
    private WeakReference<BitmapLoadListener> mListener;
    protected Rect mPadding;
    protected int mPreloadHeight;
    protected int mRadius;
    protected List<ElementRender> mRenders;
    protected float mScale;
    protected int mScaleRound;
    protected long mThemeId;
    protected int mWidth;
    protected Bitmap mPreviewBm = null;
    protected Canvas mPreviewCanvas = new Canvas();
    protected Rect mPreviewBmRect = new Rect();
    protected RectF mPreviewBmRectF = new RectF();
    protected Paint mPreviewPaint = new Paint();
    protected Path mPreviewRoundPath = new Path();
    protected SmoothDrawHelper mSmoothDrawHelper = new SmoothDrawHelper();
    protected PorterDuffXfermode mSrcInMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    protected LinkCardSpanHelper mLinkCardDrawHelper = null;
    protected AudioSpanHelper mAudioSpanHelper = null;
    protected float mStrokeWidth = 0.0f;
    protected Path mViewRoundStrokePath = new Path();
    protected RectF mViewRoundStrokeRectF = new RectF();
    protected Paint mViewStrokePaint = new Paint();
    public int SHARE_PREVIEW_IMAGE_MAX_HEIGHT = 2000;
    public boolean shouldBlock = false;
    protected Bitmap mCachedBackgroundBitmap = null;
    protected final Object mBackgroundLocker = new Object();

    public interface BitmapLoadListener {
        void onLoadFinish(ImageElement imageElement, Bitmap bitmap);
    }

    protected int getImageQuality() {
        return 90;
    }

    public Render(Context context, List<Element> list, Drawable drawable, int i, long j) {
        this.mContext = context;
        this.mElements = list;
        this.mBackground = drawable;
        this.mWidth = i;
        this.mRenders = new ArrayList(list.size());
        this.mPreviewPaint.setAntiAlias(true);
        this.mViewStrokePaint.setAntiAlias(true);
        this.mViewStrokePaint.setStyle(Paint.Style.STROKE);
        PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mDrawFilter = paintFlagsDrawFilter;
        this.mPreviewCanvas.setDrawFilter(paintFlagsDrawFilter);
        this.mImageRenders = new ArrayList();
        this.mPadding = new Rect();
        this.mRadius = context.getResources().getDimensionPixelSize(R.dimen.share_preview_radius);
        this.mThemeId = j;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public Rect getBounds(Element element) {
        int iIndexOf = this.mElements.indexOf(element);
        if (iIndexOf < 0) {
            return null;
        }
        return this.mRenders.get(iIndexOf).getBounds();
    }

    public void setScale(float f) {
        this.mScale = f;
        this.mScaleRound = (int) (this.mRadius * f);
    }

    public void setStrokeStyle(int i, float f) {
        this.mStrokeWidth = f;
        this.mViewStrokePaint.setColor(i);
        this.mViewStrokePaint.setStrokeWidth(f);
    }

    public void setPreloadHeight(int i) {
        this.mPreloadHeight = i;
    }

    public void setBitmapLoadListener(BitmapLoadListener bitmapLoadListener) {
        this.mListener = new WeakReference<>(bitmapLoadListener);
    }

    public void draw(Canvas canvas, int i, int i2, boolean z) {
        onPreload(i, i2);
        onDraw(canvas, i, i2, z);
    }

    public void destroy() {
        Bitmap bitmap = this.mBgBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mBgBitmap.recycle();
        }
        Bitmap bitmap2 = this.mPreviewBm;
        if (bitmap2 != null && bitmap2.isRecycled()) {
            this.mPreviewBm.recycle();
        }
        Bitmap bitmap3 = this.mCachedBackgroundBitmap;
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            this.mCachedBackgroundBitmap.recycle();
            this.mCachedBackgroundBitmap = null;
        }
        Iterator<ElementRender> it = this.mRenders.iterator();
        while (it.hasNext()) {
            it.next().destroy();
        }
        this.mPreviewCanvas = null;
        this.mContext = null;
    }

    protected void onPreload(int i, int i2) {
        onPreloadBitmap(i, i2);
    }

    protected void onPreloadBitmap(int i, int i2) {
        List<? extends ImageElementPreloader> imageElementPreloaders = getImageElementPreloaders();
        int size = imageElementPreloaders.size();
        for (int i3 = 0; i3 < size; i3++) {
            ImageElementPreloader imageElementPreloader = imageElementPreloaders.get(i3);
            Rect bounds = getBounds(imageElementPreloader.getElement());
            if (bounds != null && (bounds.bottom < i - this.mPreloadHeight || bounds.top > this.mPreloadHeight + i2)) {
                imageElementPreloader.cancelTask();
                imageElementPreloader.setBitmap(null);
            } else if (imageElementPreloader.getBitmap() == null && this.mListener.get() != null) {
                imageElementPreloader.scheduleTask(this.mListener.get());
            }
        }
    }

    protected List<? extends ImageElementPreloader> getImageElementPreloaders() {
        return this.mImageRenders;
    }

    protected void drawBackgroundForFile(Canvas canvas) {
        synchronized (this.mBackgroundLocker) {
            Paint paint = new Paint();
            int iMin = Math.min(this.mHeight, MAX_IMAGE_HEIGHT);
            Bitmap bitmap = this.mCachedBackgroundBitmap;
            if (bitmap == null || bitmap.isRecycled() || this.mCachedBackgroundBitmap.getWidth() != this.mWidth || this.mCachedBackgroundBitmap.getHeight() != iMin) {
                Bitmap bitmap2 = this.mCachedBackgroundBitmap;
                if (bitmap2 != null && !bitmap2.isRecycled()) {
                    this.mCachedBackgroundBitmap.recycle();
                }
                this.mCachedBackgroundBitmap = UIUtils.drawableToBitmap(this.mBackground, this.mWidth, iMin, Bitmap.Config.ARGB_8888);
            }
            drawBigBitmapForFileNoRecycle(this.mCachedBackgroundBitmap, canvas, paint);
        }
    }

    protected void drawBackgroundForFile(Canvas canvas, int i) {
        synchronized (this.mBackgroundLocker) {
            Paint paint = new Paint();
            int iMin = Math.min(i, MAX_IMAGE_HEIGHT);
            Bitmap bitmap = this.mCachedBackgroundBitmap;
            if (bitmap == null || bitmap.isRecycled() || this.mCachedBackgroundBitmap.getWidth() != this.mWidth || this.mCachedBackgroundBitmap.getHeight() != iMin) {
                Bitmap bitmap2 = this.mCachedBackgroundBitmap;
                if (bitmap2 != null && !bitmap2.isRecycled()) {
                    this.mCachedBackgroundBitmap.recycle();
                }
                this.mCachedBackgroundBitmap = UIUtils.drawableToBitmap(this.mBackground, this.mWidth, iMin, Bitmap.Config.ARGB_8888);
            }
            drawBigBitmapForFileNoRecycle(this.mCachedBackgroundBitmap, canvas, paint);
        }
    }

    protected void drawBigBitmapForFileNoRecycle(Bitmap bitmap, Canvas canvas, Paint paint) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = height / 500;
        int i2 = 0;
        Rect rect = new Rect(0, 0, width, 0);
        while (i2 < i) {
            rect.top = i2 * 500;
            i2++;
            rect.bottom = i2 * 500;
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }
        rect.top = i * 500;
        rect.bottom = height;
        canvas.drawBitmap(bitmap, rect, rect, paint);
    }

    protected void onDraw(Canvas canvas, int i, int i2, boolean z) {
        if (this.mBgBitmap != null) {
            canvas.save();
            this.mPreviewBmRect.set(0, Math.max((int) ((i * 1.0f) / this.mScale), 0), getWidth(), (int) ((i2 * 1.0f) / this.mScale));
            canvas.setDrawFilter(this.mDrawFilter);
            int iMin = Math.min(this.mBgBitmap.getHeight() - this.mPreviewBmRect.top, this.mPreviewBmRect.height());
            if (iMin > 0) {
                this.mPreviewBmRectF.set(0.0f, 0.0f, this.mBgBitmap.getWidth(), iMin);
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mBgBitmap.getWidth(), iMin, Bitmap.Config.ARGB_8888);
                this.mPreviewBm = bitmapCreateBitmap;
                this.mPreviewCanvas.setBitmap(bitmapCreateBitmap);
                this.mPreviewPaint.setXfermode(null);
                this.mSmoothDrawHelper.getSmoothPathFromProvider(this.mPreviewRoundPath, this.mPreviewBmRectF, null, this.mRadius, 0.0f, 0.0f);
                this.mPreviewCanvas.drawPath(this.mPreviewRoundPath, this.mPreviewPaint);
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(this.mBgBitmap, this.mPreviewBmRect.left, this.mPreviewBmRect.top, this.mPreviewBmRect.width(), iMin);
                this.mPreviewPaint.setXfermode(this.mSrcInMode);
                this.mPreviewCanvas.drawBitmap(bitmapCreateBitmap2, (Rect) null, this.mPreviewBmRectF, this.mPreviewPaint);
                this.mPreviewPaint.setXfermode(null);
                Rect rect = this.mPreviewBmRect;
                rect.bottom = rect.top + iMin;
                canvas.drawBitmap(this.mPreviewBm, (Rect) null, this.mPreviewBmRect, (Paint) null);
                if (this.mStrokeWidth > 0.0f) {
                    this.mViewRoundStrokeRectF.set(this.mPreviewBmRect.left + this.mStrokeWidth, this.mPreviewBmRect.top + this.mStrokeWidth, this.mPreviewBmRect.right - this.mStrokeWidth, this.mPreviewBmRect.bottom - this.mStrokeWidth);
                    this.mSmoothDrawHelper.getSmoothPathFromProvider(this.mViewRoundStrokePath, this.mViewRoundStrokeRectF, null, this.mRadius + this.mStrokeWidth, 0.0f, 0.0f);
                    canvas.drawPath(this.mViewRoundStrokePath, this.mViewStrokePaint);
                }
            }
            canvas.restore();
        }
    }

    public boolean saveToFile(File file) {
        int iMin = Math.min(MAX_IMAGE_HEIGHT, this.mHeight);
        Encoder encoderCreate = EncoderFactory.getInstance().create(false);
        try {
            encoderCreate.createEncoder(file.getPath(), getImageQuality(), this.mWidth, iMin);
            encoderCreate.startCompress();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mWidth, 2048, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            int[] iArr = new int[this.mWidth * 2048];
            int size = this.mRenders.size();
            int i = 0;
            int i2 = 0;
            while (i < iMin && !this.shouldBlock) {
                int iMin2 = Math.min(i + 2048, iMin);
                canvas.save();
                canvas.translate(0.0f, -i);
                drawBackgroundForFile(canvas);
                canvas.restore();
                int bottom = i;
                while (i2 < size && !this.shouldBlock) {
                    Logger.INSTANCE.d("Render", "saveToFile renderIndex: " + i2);
                    ElementRender elementRender = this.mRenders.get(i2);
                    if ((elementRender.getTop() >= bottom && elementRender.getTop() < iMin2) || ((elementRender.getBottom() > bottom && elementRender.getBottom() < iMin2) || (elementRender.getTop() <= bottom && elementRender.getBottom() >= iMin2))) {
                        canvas.save();
                        canvas.translate(0.0f, elementRender.getTop() - i);
                        elementRender.render(canvas);
                        canvas.restore();
                        if (elementRender.getBottom() > iMin2) {
                            break;
                        }
                        if (elementRender.getBottom() == iMin2) {
                            i2++;
                            break;
                        }
                        bottom = elementRender.getBottom();
                        i2++;
                    }
                }
                int i3 = i2;
                if (!this.shouldBlock) {
                    int i4 = iMin2 - i;
                    int i5 = this.mWidth;
                    bitmapCreateBitmap.getPixels(iArr, 0, i5, 0, 0, i5, i4);
                    encoderCreate.savePixels(iArr, i4);
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    i = iMin2;
                }
                i2 = i3;
            }
            if (!this.shouldBlock) {
                encoderCreate.finishCompress();
                this.mBgBitmap = BitmapFactory.decodeFile(file.getPath());
                bitmapCreateBitmap.recycle();
                return true;
            }
            bitmapCreateBitmap.recycle();
            return false;
        } catch (IOException unused) {
        }
    }

    public boolean saveToFile(File file, int i) {
        int iMin = Math.min(MAX_IMAGE_HEIGHT, i);
        Encoder encoderCreate = EncoderFactory.getInstance().create(false);
        try {
            encoderCreate.createEncoder(file.getPath(), getImageQuality(), this.mWidth, iMin);
            encoderCreate.startCompress();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mWidth, 2048, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            int[] iArr = new int[this.mWidth * 2048];
            int size = this.mRenders.size();
            int i2 = 0;
            int i3 = 0;
            while (i2 < iMin && !this.shouldBlock) {
                int iMin2 = Math.min(i2 + 2048, iMin);
                canvas.save();
                canvas.translate(0.0f, -i2);
                if (iMin < this.SHARE_PREVIEW_IMAGE_MAX_HEIGHT) {
                    drawBackgroundForFile(canvas);
                } else {
                    drawBackgroundForFile(canvas, iMin + 2048);
                }
                canvas.restore();
                int bottom = i2;
                while (i3 < size && !this.shouldBlock) {
                    Logger.INSTANCE.d("Render", "saveToFile renderIndex: " + i3);
                    ElementRender elementRender = this.mRenders.get(i3);
                    if ((elementRender.getTop() >= bottom && elementRender.getTop() < iMin2) || ((elementRender.getBottom() > bottom && elementRender.getBottom() < iMin2) || (elementRender.getTop() <= bottom && elementRender.getBottom() >= iMin2))) {
                        canvas.save();
                        canvas.translate(0.0f, elementRender.getTop() - i2);
                        elementRender.render(canvas);
                        canvas.restore();
                        if (elementRender.getBottom() > iMin2) {
                            break;
                        }
                        if (elementRender.getBottom() == iMin2) {
                            i3++;
                            break;
                        }
                        bottom = elementRender.getBottom();
                        i3++;
                    }
                }
                int i4 = i3;
                if (!this.shouldBlock) {
                    int i5 = iMin2 - i2;
                    int i6 = this.mWidth;
                    bitmapCreateBitmap.getPixels(iArr, 0, i6, 0, 0, i6, i5);
                    encoderCreate.savePixels(iArr, i5);
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    i2 = iMin2;
                }
                i3 = i4;
            }
            if (this.shouldBlock) {
                Logger.INSTANCE.d("Render", "saveToFile task was dead: ");
                bitmapCreateBitmap.recycle();
                return false;
            }
            encoderCreate.finishCompress();
            this.mBgBitmap = BitmapFactory.decodeFile(file.getPath());
            bitmapCreateBitmap.recycle();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public LinkCardSpanHelper getLinkCardDrawHelper() {
        if (this.mLinkCardDrawHelper == null) {
            this.mLinkCardDrawHelper = new LinkCardSpanHelper(ResourceManager.getTheme((int) this.mThemeId));
        }
        return this.mLinkCardDrawHelper;
    }
}
