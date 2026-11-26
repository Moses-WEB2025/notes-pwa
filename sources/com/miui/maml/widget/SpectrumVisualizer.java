package com.miui.maml.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.miui.maml.R;
import com.miui.maml.util.SystemProperties;
import com.miui.notes.ai.stat.AITextWidgetStat;

/* JADX INFO: loaded from: classes3.dex */
public class SpectrumVisualizer extends ImageView {
    private static final int CONSIDER_SAMPLE_LENGTH = 160;
    private static final String TAG = "SpectrumVisualizer";
    private static final int VISUALIZATION_SAMPLE_LENGTH = 256;
    private float INDEX_SCALE_FACTOR;
    private final int MAX_VALID_SAMPLE;
    private float SAMPLE_SCALE_FACTOR;
    private float VISUALIZE_DESC_HEIGHT;
    int mAlphaWidthNum;
    private AudioManager mAudioManager;
    private Bitmap mCachedBitmap;
    private Canvas mCachedCanvas;
    int mCellSize;
    int mDotbarHeight;
    private DotBarDrawer mDrawer;
    private boolean mEnableDrawing;
    private boolean mIsEnableUpdate;
    private boolean mIsNeedCareStreamActive;
    private Visualizer.OnDataCaptureListener mOnDataCaptureListener;
    Paint mPaint;
    int[] mPixels;
    float[] mPointData;
    private short[] mSampleBuf;
    int mShadowDotbarHeight;
    int[] mShadowPixels;
    private boolean mSoftDrawEnabled;
    private int mVisualizationHeight;
    int mVisualizationHeightNum;
    private int mVisualizationWidth;
    int mVisualizationWidthNum;
    private Visualizer mVisualizer;
    public static boolean IS_LPA_DECODE = SystemProperties.getBoolean("persist.sys.lpa.decode", false);
    private static final int RES_DEFAULT_SLIDING_PANEL_ID = R.drawable.sliding_panel_visualization_bg;
    private static final int RES_DEFAULT_SLIDING_DOT_BAR_ID = R.drawable.sliding_panel_visualization_dot_bar;
    private static final int RES_DEFAULT_SLIDING_SHADOW_DOT_BAR_ID = R.drawable.sliding_panel_visualization_shadow_dot_bar;

    private interface DotBarDrawer {
        void drawDotBar(Canvas canvas, int i);
    }

    private void init(Context context, AttributeSet attributeSet) {
    }

    public SpectrumVisualizer(Context context) {
        this(context, null);
    }

    public SpectrumVisualizer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SpectrumVisualizer(Context context, AttributeSet attributeSet, int i) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.mSampleBuf = new short[160];
        this.mSoftDrawEnabled = true;
        this.mOnDataCaptureListener = new Visualizer.OnDataCaptureListener() { // from class: com.miui.maml.widget.SpectrumVisualizer.1
            @Override // android.media.audiofx.Visualizer.OnDataCaptureListener
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bArr, int i2) {
            }

            @Override // android.media.audiofx.Visualizer.OnDataCaptureListener
            public void onFftDataCapture(Visualizer visualizer, byte[] bArr, int i2) {
                SpectrumVisualizer.this.update(bArr);
            }
        };
        this.MAX_VALID_SAMPLE = 20;
        this.mEnableDrawing = true;
        this.mIsNeedCareStreamActive = true;
        boolean z = false;
        this.mAlphaWidthNum = 0;
        this.mAudioManager = (AudioManager) context.getSystemService(AITextWidgetStat.TYPE_AUDIO);
        Bitmap bitmap = null;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SpectrumVisualizer);
            drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.SpectrumVisualizer_sliding_panel);
            drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.SpectrumVisualizer_sliding_dot_bar);
            drawable3 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.SpectrumVisualizer_sliding_shadow_dot_bar);
            boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SpectrumVisualizer_symmetry, false);
            this.mAlphaWidthNum = typedArrayObtainStyledAttributes.getInt(R.styleable.SpectrumVisualizer_alpha_width, this.mAlphaWidthNum);
            this.mIsEnableUpdate = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SpectrumVisualizer_update_enable, false);
            this.mIsNeedCareStreamActive = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SpectrumVisualizer_care_streamactive, false);
            typedArrayObtainStyledAttributes.recycle();
            z = z2;
        } else {
            drawable = null;
            drawable2 = null;
            drawable3 = null;
        }
        Bitmap bitmap2 = ((BitmapDrawable) (drawable == null ? context.getResources().getDrawable(RES_DEFAULT_SLIDING_PANEL_ID) : drawable)).getBitmap();
        Bitmap bitmap3 = ((BitmapDrawable) (drawable2 == null ? context.getResources().getDrawable(RES_DEFAULT_SLIDING_DOT_BAR_ID) : drawable2)).getBitmap();
        if (z) {
            bitmap = ((BitmapDrawable) (drawable3 == null ? context.getResources().getDrawable(RES_DEFAULT_SLIDING_SHADOW_DOT_BAR_ID) : drawable3)).getBitmap();
        }
        setBitmaps(bitmap2, bitmap3, bitmap);
    }

    public void setBitmaps(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        setImageBitmap(bitmap);
        setBitmaps(bitmap.getWidth(), bitmap.getHeight(), bitmap2, bitmap3);
    }

    public void setBitmaps(int i, int i2, Bitmap bitmap, Bitmap bitmap2) {
        this.mVisualizationWidth = i;
        this.mVisualizationHeight = i2;
        this.mCellSize = bitmap.getWidth();
        int height = bitmap.getHeight();
        this.mDotbarHeight = height;
        int i3 = this.mVisualizationHeight;
        if (height > i3) {
            this.mDotbarHeight = i3;
        }
        int i4 = this.mCellSize;
        int i5 = this.mDotbarHeight;
        int[] iArr = new int[i4 * i5];
        this.mPixels = iArr;
        bitmap.getPixels(iArr, 0, i4, 0, 0, i4, i5);
        int i6 = this.mVisualizationWidth;
        int i7 = this.mCellSize;
        int i8 = i6 / i7;
        this.mVisualizationWidthNum = i8;
        int i9 = this.mDotbarHeight / i7;
        this.mVisualizationHeightNum = i9;
        this.SAMPLE_SCALE_FACTOR = 20.0f / i9;
        this.INDEX_SCALE_FACTOR = (float) Math.log(i8 / 3);
        this.VISUALIZE_DESC_HEIGHT = 1.0f / this.mVisualizationHeightNum;
        int i10 = this.mVisualizationWidthNum;
        this.mPointData = new float[i10];
        if (this.mAlphaWidthNum == 0) {
            this.mAlphaWidthNum = i10 / 2;
        }
        this.mShadowPixels = null;
        if (bitmap2 != null) {
            int height2 = bitmap2.getHeight();
            this.mShadowDotbarHeight = height2;
            int i11 = this.mDotbarHeight;
            int i12 = height2 + i11;
            int i13 = this.mVisualizationHeight;
            if (i12 > i13) {
                this.mShadowDotbarHeight = i13 - i11;
            }
            int i14 = this.mShadowDotbarHeight;
            int i15 = this.mCellSize;
            if (i14 < i15) {
                this.mDrawer = new AsymmetryDotBar();
                return;
            }
            int[] iArr2 = new int[i15 * i14];
            this.mShadowPixels = iArr2;
            bitmap2.getPixels(iArr2, 0, i15, 0, 0, i15, i14);
            this.mDrawer = new SymmetryDotBar();
            return;
        }
        this.mDrawer = new AsymmetryDotBar();
    }

    public void setAlphaNum(int i) {
        if (i <= 0) {
            this.mAlphaWidthNum = 0;
            return;
        }
        int i2 = this.mVisualizationWidthNum;
        if (i > i2 / 2) {
            i = i2 / 2;
        }
        this.mAlphaWidthNum = i;
    }

    public int getVisualHeight() {
        return this.mVisualizationHeight;
    }

    public int getVisualWidth() {
        return this.mVisualizationWidth;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mEnableDrawing) {
            if (this.mSoftDrawEnabled) {
                canvas.drawBitmap(drawToBitmap(), 0.0f, 0.0f, (Paint) null);
            } else {
                drawInternal(canvas);
            }
        }
    }

    public void setSoftDrawEnabled(boolean z) {
        Bitmap bitmap;
        this.mSoftDrawEnabled = z;
        if (z || (bitmap = this.mCachedBitmap) == null) {
            return;
        }
        bitmap.recycle();
        this.mCachedBitmap = null;
        this.mCachedCanvas = null;
    }

    private Bitmap drawToBitmap() {
        Bitmap bitmapCreateBitmap = this.mCachedBitmap;
        Canvas canvas = this.mCachedCanvas;
        if (bitmapCreateBitmap != null && (bitmapCreateBitmap.getWidth() != getWidth() || bitmapCreateBitmap.getHeight() != getHeight())) {
            bitmapCreateBitmap.recycle();
            bitmapCreateBitmap = null;
        }
        if (bitmapCreateBitmap == null) {
            bitmapCreateBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            this.mCachedBitmap = bitmapCreateBitmap;
            canvas = new Canvas(bitmapCreateBitmap);
            this.mCachedCanvas = canvas;
        }
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        drawInternal(canvas);
        return bitmapCreateBitmap;
    }

    private void drawInternal(Canvas canvas) {
        this.mPaint.setAlpha(255);
        int i = this.mVisualizationWidthNum;
        int i2 = this.mAlphaWidthNum;
        int i3 = i - i2;
        while (i2 < i3) {
            this.mDrawer.drawDotBar(canvas, i2);
            i2++;
        }
        for (int i4 = this.mAlphaWidthNum; i4 > 0; i4--) {
            this.mPaint.setAlpha((i4 * 255) / this.mAlphaWidthNum);
            this.mDrawer.drawDotBar(canvas, i4 - 1);
            this.mDrawer.drawDotBar(canvas, this.mVisualizationWidthNum - i4);
        }
    }

    public boolean isUpdateEnabled() {
        return this.mIsEnableUpdate;
    }

    public void enableDrawing(boolean z) {
        this.mEnableDrawing = z;
    }

    public void enableUpdate(boolean z) {
        Visualizer visualizer;
        try {
            if (this.mIsEnableUpdate != z) {
                if (z && this.mVisualizer == null) {
                    if (IS_LPA_DECODE) {
                        Log.v("SpectrumVisualizer", "lpa decode is on, can't enable");
                    } else {
                        Visualizer visualizer2 = new Visualizer(0);
                        this.mVisualizer = visualizer2;
                        if (!visualizer2.getEnabled()) {
                            this.mVisualizer.setCaptureSize(512);
                            this.mVisualizer.setDataCaptureListener(this.mOnDataCaptureListener, Visualizer.getMaxCaptureRate(), false, true);
                            this.mVisualizer.setEnabled(true);
                        }
                    }
                } else if (!z && (visualizer = this.mVisualizer) != null) {
                    visualizer.setEnabled(false);
                    this.mVisualizer.release();
                    this.mVisualizer = null;
                }
                this.mIsEnableUpdate = z;
            }
        } catch (IllegalStateException | RuntimeException unused) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void update(byte[] bArr) {
        float f;
        float f2;
        if (this.mIsNeedCareStreamActive && !this.mAudioManager.isMusicActive()) {
            enableDrawing(false);
            return;
        }
        enableDrawing(true);
        if (bArr == null) {
            return;
        }
        short[] sArr = this.mSampleBuf;
        int length = sArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            byte b = bArr[i2];
            byte b2 = bArr[i2 + 1];
            int iSqrt = (int) Math.sqrt((b * b) + (b2 * b2));
            if (iSqrt >= 32767) {
                iSqrt = 32767;
            }
            sArr[i] = (short) iSqrt;
        }
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.mVisualizationWidthNum; i5++) {
            int iMax = 0;
            while (i4 < length) {
                iMax = Math.max(iMax, (int) sArr[i3]);
                i3++;
                i4 += this.mVisualizationWidthNum;
            }
            i4 -= length;
            if (iMax > 1) {
                float fLog = (float) (Math.log(i5 + 2) / ((double) this.INDEX_SCALE_FACTOR));
                f = (iMax - 1) * fLog * fLog;
            } else {
                f = 0.0f;
            }
            if (f > 20.0f) {
                f2 = this.mVisualizationHeightNum;
            } else {
                f2 = f / this.SAMPLE_SCALE_FACTOR;
            }
            float[] fArr = this.mPointData;
            fArr[i5] = Math.max(f2 / this.mVisualizationHeightNum, fArr[i5] - this.VISUALIZE_DESC_HEIGHT);
        }
        invalidate();
    }

    class AsymmetryDotBar implements DotBarDrawer {
        AsymmetryDotBar() {
        }

        @Override // com.miui.maml.widget.SpectrumVisualizer.DotBarDrawer
        public void drawDotBar(Canvas canvas, int i) {
            int i2 = ((int) (((double) ((SpectrumVisualizer.this.mDotbarHeight * (1.0f - SpectrumVisualizer.this.mPointData[i])) / SpectrumVisualizer.this.mCellSize)) + 0.5d)) * SpectrumVisualizer.this.mCellSize;
            if (i2 < SpectrumVisualizer.this.mDotbarHeight) {
                canvas.drawBitmap(SpectrumVisualizer.this.mPixels, SpectrumVisualizer.this.mCellSize * i2, SpectrumVisualizer.this.mCellSize, SpectrumVisualizer.this.mCellSize * i, i2, SpectrumVisualizer.this.mCellSize, SpectrumVisualizer.this.mDotbarHeight - i2, true, SpectrumVisualizer.this.mPaint);
            }
        }
    }

    class SymmetryDotBar implements DotBarDrawer {
        SymmetryDotBar() {
        }

        @Override // com.miui.maml.widget.SpectrumVisualizer.DotBarDrawer
        public void drawDotBar(Canvas canvas, int i) {
            int i2 = ((int) (((double) ((SpectrumVisualizer.this.mDotbarHeight * (1.0f - SpectrumVisualizer.this.mPointData[i])) / SpectrumVisualizer.this.mCellSize)) + 0.5d)) * SpectrumVisualizer.this.mCellSize;
            if (i2 < SpectrumVisualizer.this.mDotbarHeight) {
                canvas.drawBitmap(SpectrumVisualizer.this.mPixels, SpectrumVisualizer.this.mCellSize * i2, SpectrumVisualizer.this.mCellSize, SpectrumVisualizer.this.mCellSize * i, i2, SpectrumVisualizer.this.mCellSize, SpectrumVisualizer.this.mDotbarHeight - i2, true, SpectrumVisualizer.this.mPaint);
            }
            int i3 = ((int) (((double) ((SpectrumVisualizer.this.mShadowDotbarHeight * SpectrumVisualizer.this.mPointData[i]) / SpectrumVisualizer.this.mCellSize)) + 0.5d)) * SpectrumVisualizer.this.mCellSize;
            if (i3 > SpectrumVisualizer.this.mShadowDotbarHeight) {
                i3 = SpectrumVisualizer.this.mShadowDotbarHeight;
            }
            int i4 = i3;
            if (i4 > 0) {
                canvas.drawBitmap(SpectrumVisualizer.this.mShadowPixels, 0, SpectrumVisualizer.this.mCellSize, SpectrumVisualizer.this.mCellSize * i, SpectrumVisualizer.this.mDotbarHeight, SpectrumVisualizer.this.mCellSize, i4, true, SpectrumVisualizer.this.mPaint);
            }
        }
    }
}
