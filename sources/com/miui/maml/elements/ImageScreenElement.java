package com.miui.maml.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.animation.SourcesAnimation;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.util.TextFormatter;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import miuix.graphics.BitmapFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class ImageScreenElement extends AnimatedScreenElement implements BitmapProvider.IBitmapHolder {
    private static final String LOG_TAG = "ImageScreenElement";
    public static final String MASK_TAG_NAME = "Mask";
    private static final double PI = 3.1415926535897d;
    public static final String TAG_NAME = "Image";
    private static final String VAR_BMP_HEIGHT = "bmp_height";
    private static final String VAR_BMP_WIDTH = "bmp_width";
    private static final String VAR_HAS_BITMAP = "has_bitmap";
    private boolean mAntiAlias;
    protected BitmapProvider.VersionedBitmap mBitmap;
    private BitmapProvider mBitmapProvider;
    private Bitmap mBlurBitmap;
    private int mBlurRadius;
    private IndexedVariable mBmpSizeHeightVar;
    private IndexedVariable mBmpSizeWidthVar;
    protected BitmapProvider.VersionedBitmap mCurrentBitmap;
    private Rect mDesRect;
    private Expression mExpH;
    private Expression mExpSrcH;
    private Expression mExpSrcW;
    private Expression mExpSrcX;
    private Expression mExpSrcY;
    private Expression mExpW;
    private int mH;
    private IndexedVariable mHasBitmapVar;
    private boolean mHasSrcRect;
    private boolean mHasWidthAndHeight;
    private boolean mIsLoadAsyncSet;
    private boolean mLoadAsync;
    private Paint mMaskPaint;
    private ArrayList<Mask> mMasks;
    private int mMeshHeight;
    private float[] mMeshVerts;
    private int mMeshWidth;
    protected Paint mPaint;
    private boolean mPendingBlur;
    private int mRawBlurRadius;
    private boolean mRetainWhenInvisible;
    private pair<Double, Double> mRotateXYpair;
    private SourcesAnimation mSources;
    private String mSrc;
    private TextFormatter mSrcFormatter;
    private int mSrcH;
    private Expression mSrcIdExpression;
    private Rect mSrcRect;
    private int mSrcW;
    private int mSrcX;
    private int mSrcY;
    private int mW;
    private Expression mXfermodeNumExp;

    private static class pair<T1, T2> {
        public T1 p1;
        public T2 p2;

        private pair() {
        }
    }

    private class Mask extends ImageScreenElement {
        private boolean mAlignAbsolute;

        @Override // com.miui.maml.elements.ImageScreenElement, com.miui.maml.elements.ScreenElement
        protected void doRender(Canvas canvas) {
        }

        public Mask(Element element, ScreenElementRoot screenElementRoot) {
            super(element, screenElementRoot);
            if (getAttr(element, "align").equalsIgnoreCase("absolute")) {
                this.mAlignAbsolute = true;
            }
        }

        public final boolean isAlignAbsolute() {
            return this.mAlignAbsolute;
        }
    }

    public ImageScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mBitmap = new BitmapProvider.VersionedBitmap(null);
        this.mCurrentBitmap = new BitmapProvider.VersionedBitmap(null);
        this.mPaint = new Paint();
        this.mMaskPaint = new Paint();
        this.mDesRect = new Rect();
        this.mW = -1;
        this.mH = -1;
        load(element);
    }

    public void setSrcType(String str) {
        this.mBitmapProvider = BitmapProvider.create(this.mRoot, str);
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        TextFormatter textFormatter = this.mSrcFormatter;
        this.mSrc = textFormatter != null ? textFormatter.getText() : null;
        this.mBitmap.reset();
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().init();
            }
        }
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            bitmapProvider.init(getSrc());
        }
        if (isVisible()) {
            updateBitmap(this.mLoadAsync);
        }
        int iScale = (int) scale(this.mRawBlurRadius);
        this.mBlurRadius = iScale;
        if (iScale > 0) {
            this.mPendingBlur = true;
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getX() {
        float x = super.getX();
        SourcesAnimation sourcesAnimation = this.mSources;
        return sourcesAnimation != null ? x + scale(sourcesAnimation.getX()) : x;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getY() {
        float y = super.getY();
        SourcesAnimation sourcesAnimation = this.mSources;
        return sourcesAnimation != null ? y + scale(sourcesAnimation.getY()) : y;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getWidth() {
        int bitmapWidth;
        float width = super.getWidth();
        if (width >= 0.0f) {
            return width;
        }
        if (this.mHasSrcRect) {
            bitmapWidth = this.mSrcW;
        } else {
            bitmapWidth = getBitmapWidth();
        }
        return bitmapWidth;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getHeight() {
        int bitmapHeight;
        float height = super.getHeight();
        if (height >= 0.0f) {
            return height;
        }
        if (this.mHasSrcRect) {
            bitmapHeight = this.mSrcH;
        } else {
            bitmapHeight = getBitmapHeight();
        }
        return bitmapHeight;
    }

    public final String getSrc() {
        String src;
        Expression expression;
        SourcesAnimation sourcesAnimation = this.mSources;
        if (sourcesAnimation != null) {
            src = sourcesAnimation.getSrc();
        } else {
            src = this.mSrc;
        }
        return (src == null || (expression = this.mSrcIdExpression) == null) ? src : Utils.addFileNameSuffix(src, String.valueOf((long) expression.evaluate()));
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != this.mBitmap.getBitmap()) {
            this.mBitmap.setBitmap(bitmap);
            updateBitmap(this.mLoadAsync);
            requestUpdate();
        }
    }

    public void setSrc(String str) {
        TextFormatter textFormatter = this.mSrcFormatter;
        if (textFormatter != null) {
            textFormatter.setText(str);
        }
    }

    public void setSrcId(double d) {
        Expression expression = this.mSrcIdExpression;
        if (expression != null && (expression instanceof Expression.NumberExpression)) {
            ((Expression.NumberExpression) expression).setValue(d);
        } else {
            this.mSrcIdExpression = new Expression.NumberExpression(String.valueOf(d));
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            bitmapProvider.finish();
        }
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().finish();
            }
        }
        this.mBitmap.reset();
        this.mCurrentBitmap.reset();
        this.mBlurBitmap = null;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        super.pause();
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().pause();
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().resume();
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().reset(j);
            }
        }
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            bitmapProvider.reset();
        }
        if (this.mBlurRadius > 0) {
            this.mPendingBlur = true;
        }
    }

    @Override // com.miui.maml.elements.BitmapProvider.IBitmapHolder
    public BitmapProvider.VersionedBitmap getBitmap(String str) {
        return this.mCurrentBitmap;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        Paint paint = this.mPaint;
        if (paint != null) {
            paint.setColorFilter(colorFilter);
        }
    }

    protected void loadMesh(Element element) {
        String attr = getAttr(element, "mesh");
        int iIndexOf = attr.indexOf(",");
        if (iIndexOf != -1) {
            try {
                this.mMeshWidth = Integer.parseInt(attr.substring(0, iIndexOf));
                this.mMeshHeight = Integer.parseInt(attr.substring(iIndexOf + 1));
            } catch (NumberFormatException unused) {
                Log.w(LOG_TAG, "Invalid mesh format:" + attr);
            }
            if (this.mMeshWidth == 0 || this.mMeshHeight == 0) {
                return;
            }
            String attr2 = getAttr(element, "meshVertsArr");
            Object obj = getVariables().get(attr2);
            if (obj != null && (obj instanceof float[])) {
                this.mMeshVerts = (float[]) obj;
                return;
            }
            this.mMeshHeight = 0;
            this.mMeshWidth = 0;
            Log.w(LOG_TAG, "Invalid meshVertsArr:" + attr2 + "  undifined or not float[] type");
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected BaseAnimation onCreateAnimation(String str, Element element) {
        if (SourcesAnimation.TAG_NAME.equals(str)) {
            SourcesAnimation sourcesAnimation = new SourcesAnimation(element, this);
            this.mSources = sourcesAnimation;
            return sourcesAnimation;
        }
        return super.onCreateAnimation(str, element);
    }

    protected int getBitmapWidth() {
        Bitmap bitmap = this.mCurrentBitmap.getBitmap();
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        return 0;
    }

    protected int getBitmapHeight() {
        Bitmap bitmap = this.mCurrentBitmap.getBitmap();
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        return 0;
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void doRender(Canvas canvas) {
        int i;
        Canvas canvas2 = canvas;
        Bitmap bitmap = this.mCurrentBitmap.getBitmap();
        if (bitmap == null) {
            return;
        }
        if (this.mPendingBlur) {
            if (this.mBlurBitmap == null || bitmap.getWidth() != this.mBlurBitmap.getWidth() || bitmap.getHeight() != this.mBlurBitmap.getHeight()) {
                this.mBlurBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            }
            this.mPendingBlur = false;
            this.mBlurBitmap = BitmapFactory.fastBlur(this.mRoot.getContext().mContext, bitmap, this.mBlurBitmap, this.mBlurRadius);
        }
        Bitmap bitmap2 = this.mBlurBitmap;
        if (bitmap2 != null && this.mBlurRadius > 0) {
            bitmap = bitmap2;
        }
        this.mPaint.setAlpha(getAlpha());
        int density = canvas2.getDensity();
        canvas2.setDensity(0);
        float width = getWidth();
        float height = getHeight();
        float width2 = super.getWidth();
        float height2 = super.getHeight();
        if (width == 0.0f || height == 0.0f) {
            return;
        }
        int left = (int) getLeft(0.0f, width);
        int top = (int) getTop(0.0f, height);
        canvas2.save();
        if (this.mMasks.size() == 0) {
            if (bitmap.getNinePatchChunk() != null) {
                NinePatch ninePatch = getContext().mResourceManager.getNinePatch(getSrc());
                if (ninePatch != null) {
                    this.mDesRect.set(left, top, (int) (left + width), (int) (top + height));
                    ninePatch.draw(canvas2, this.mDesRect, this.mPaint);
                } else {
                    Log.e(LOG_TAG, "the image contains ninepatch chunk but couldn't get NinePatch object: " + getSrc());
                }
            } else if (width2 > 0.0f || height2 > 0.0f || this.mSrcRect != null) {
                this.mDesRect.set(left, top, (int) (left + width), (int) (top + height));
                Rect rect = this.mSrcRect;
                if (rect != null) {
                    int i2 = this.mSrcX;
                    int i3 = this.mSrcY;
                    rect.set(i2, i3, this.mSrcW + i2, this.mSrcH + i3);
                }
                canvas2.drawBitmap(bitmap, this.mSrcRect, this.mDesRect, this.mPaint);
            } else {
                Bitmap bitmap3 = bitmap;
                int i4 = this.mMeshWidth;
                if (i4 > 0 && (i = this.mMeshHeight) > 0) {
                    canvas.drawBitmapMesh(bitmap3, i4, i, this.mMeshVerts, 0, null, 0, this.mPaint);
                    canvas2 = canvas;
                } else {
                    canvas2 = canvas;
                    canvas2.drawBitmap(bitmap3, left, top, this.mPaint);
                }
            }
        } else {
            Bitmap bitmap4 = bitmap;
            float f = left;
            float f2 = top;
            canvas2.saveLayer(f, f2, ((int) Math.ceil(width)) + left, ((int) Math.ceil(height)) + top, this.mPaint, 31);
            if (width2 > 0.0f || height2 > 0.0f || this.mSrcRect != null) {
                this.mDesRect.set(left, top, ((int) width) + left, ((int) height) + top);
                Rect rect2 = this.mSrcRect;
                if (rect2 != null) {
                    int i5 = this.mSrcX;
                    int i6 = this.mSrcY;
                    rect2.set(i5, i6, this.mSrcW + i5, this.mSrcH + i6);
                }
                canvas2.drawBitmap(bitmap4, this.mSrcRect, this.mDesRect, this.mPaint);
            } else {
                canvas2.drawBitmap(bitmap4, f, f2, this.mPaint);
            }
            Iterator<Mask> it = this.mMasks.iterator();
            while (it.hasNext()) {
                renderWithMask(canvas2, it.next(), left, top);
            }
            canvas2.restore();
        }
        canvas2.restore();
        canvas2.setDensity(density);
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        super.doTick(j);
        if (isVisible()) {
            TextFormatter textFormatter = this.mSrcFormatter;
            this.mSrc = textFormatter != null ? textFormatter.getText() : null;
            ArrayList<Mask> arrayList = this.mMasks;
            if (arrayList != null) {
                Iterator<Mask> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().doTick(j);
                }
            }
            Expression expression = this.mXfermodeNumExp;
            if (expression != null) {
                this.mPaint.setXfermode(new PorterDuffXfermode(Utils.getPorterDuffMode((int) expression.evaluate())));
            }
            if (this.mHasSrcRect) {
                this.mSrcX = (int) scale(evaluate(this.mExpSrcX));
                this.mSrcY = (int) scale(evaluate(this.mExpSrcY));
                this.mSrcW = (int) scale(evaluate(this.mExpSrcW));
                this.mSrcH = (int) scale(evaluate(this.mExpSrcH));
            }
            if (this.mHasWidthAndHeight) {
                this.mW = (int) scale(evaluate(this.mExpW));
                this.mH = (int) scale(evaluate(this.mExpH));
            }
            if (this.mTintChanged) {
                this.mPaint.setColorFilter(this.mTintFilter);
            }
            updateBitmap(this.mLoadAsync);
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void onSetAnimBefore() {
        super.onSetAnimBefore();
        this.mSources = null;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void onSetAnimEnable(BaseAnimation baseAnimation) {
        if (baseAnimation instanceof SourcesAnimation) {
            this.mSources = (SourcesAnimation) baseAnimation;
        } else {
            super.onSetAnimEnable(baseAnimation);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        super.playAnim(j, j2, j3, z, z2);
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().playAnim(j, j2, j3, z, z2);
            }
        }
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            bitmapProvider.reset();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void pauseAnim(long j) {
        super.pauseAnim(j);
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().pauseAnim(j);
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void resumeAnim(long j) {
        super.resumeAnim(j);
        ArrayList<Mask> arrayList = this.mMasks;
        if (arrayList != null) {
            Iterator<Mask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().resumeAnim(j);
            }
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void onVisibilityChange(boolean z) {
        super.onVisibilityChange(z);
        if (z) {
            updateBitmap(this.mLoadAsync);
        } else {
            if (this.mRetainWhenInvisible) {
                return;
            }
            BitmapProvider bitmapProvider = this.mBitmapProvider;
            if (bitmapProvider != null) {
                bitmapProvider.finish();
            }
            this.mCurrentBitmap.reset();
        }
    }

    protected void updateBitmapVars() {
        if (this.mHasName) {
            this.mBmpSizeWidthVar.set(descale(getBitmapWidth()));
            this.mBmpSizeHeightVar.set(descale(getBitmapHeight()));
            this.mHasBitmapVar.set(this.mCurrentBitmap.getBitmap() != null ? 1.0d : 0.0d);
        }
    }

    protected void updateBitmapImpl(boolean z) {
        BitmapProvider.VersionedBitmap bitmap = getBitmap(z);
        if (this.mBlurRadius > 0 && !BitmapProvider.VersionedBitmap.equals(bitmap, this.mCurrentBitmap)) {
            this.mPendingBlur = true;
        }
        this.mCurrentBitmap.set(bitmap);
        updateBitmapVars();
    }

    protected BitmapProvider.VersionedBitmap getBitmap(boolean z) {
        if (this.mBitmap.getBitmap() != null) {
            return this.mBitmap;
        }
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            return bitmapProvider.getBitmap(getSrc(), !z, this.mW, this.mH);
        }
        return null;
    }

    private void load(Element element) {
        if (element == null) {
            return;
        }
        Variables variables = getVariables();
        this.mSrcFormatter = TextFormatter.fromElement(variables, element, "src", "srcFormat", "srcParas", "srcExp", "srcFormatExp");
        this.mSrcIdExpression = Expression.build(variables, getAttr(element, "srcid"));
        boolean z = !getAttr(element, "antiAlias").equals("false");
        this.mAntiAlias = z;
        this.mPaint.setFilterBitmap(z);
        this.mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.mMaskPaint.setFilterBitmap(this.mAntiAlias);
        this.mExpSrcX = Expression.build(variables, getAttr(element, "srcX"));
        this.mExpSrcY = Expression.build(variables, getAttr(element, "srcY"));
        this.mExpSrcW = Expression.build(variables, getAttr(element, "srcW"));
        this.mExpSrcH = Expression.build(variables, getAttr(element, "srcH"));
        this.mExpW = Expression.build(variables, getAttr(element, AnimatedProperty.PROPERTY_NAME_W));
        this.mExpH = Expression.build(variables, getAttr(element, AnimatedProperty.PROPERTY_NAME_H));
        if (this.mExpSrcW != null && this.mExpSrcH != null) {
            this.mHasSrcRect = true;
            this.mSrcRect = new Rect();
        }
        if (this.mExpH != null && this.mExpW != null) {
            this.mHasWidthAndHeight = true;
        }
        this.mRawBlurRadius = getAttrAsInt(element, "blur", 0);
        loadMesh(element);
        Expression expressionBuild = Expression.build(variables, getAttr(element, "xfermodeNum"));
        this.mXfermodeNumExp = expressionBuild;
        if (expressionBuild == null) {
            this.mPaint.setXfermode(new PorterDuffXfermode(Utils.getPorterDuffMode(getAttr(element, "xfermode"))));
        }
        boolean z2 = Boolean.parseBoolean(getAttr(element, "useVirtualScreen"));
        String attr = getAttr(element, "srcType");
        if (z2) {
            attr = "VirtualScreen";
        }
        setSrcType(attr);
        String attr2 = getAttr(element, "loadAsync");
        if (!TextUtils.isEmpty(attr2)) {
            this.mLoadAsync = Boolean.parseBoolean(attr2);
            this.mIsLoadAsyncSet = true;
        }
        this.mRetainWhenInvisible = Boolean.parseBoolean(getAttr(element, "retainWhenInvisible"));
        if (this.mHasName) {
            this.mBmpSizeWidthVar = new IndexedVariable(this.mName + ".bmp_width", variables, true);
            this.mBmpSizeHeightVar = new IndexedVariable(this.mName + ".bmp_height", variables, true);
            this.mHasBitmapVar = new IndexedVariable(this.mName + ".has_bitmap", variables, true);
        }
        loadMask(element);
    }

    private void loadMask(Element element) {
        if (this.mMasks == null) {
            this.mMasks = new ArrayList<>();
        }
        this.mMasks.clear();
        NodeList elementsByTagName = element.getElementsByTagName(MASK_TAG_NAME);
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            this.mMasks.add(new Mask((Element) elementsByTagName.item(i), this.mRoot));
        }
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [T1, T2, java.lang.Double] */
    /* JADX WARN: Type inference failed for: r6v2, types: [T1, java.lang.Double] */
    /* JADX WARN: Type inference failed for: r6v4, types: [T2, java.lang.Double] */
    private void rotateXY(double d, double d2, double d3, pair<Double, Double> pairVar) {
        double dSqrt = Math.sqrt((d * d) + (d2 * d2));
        ?? ValueOf = Double.valueOf(0.0d);
        if (dSqrt > 0.0d) {
            double dAcos = (PI - Math.acos(d / dSqrt)) - d3;
            pairVar.p1 = Double.valueOf(d + (Math.cos(dAcos) * dSqrt));
            pairVar.p2 = Double.valueOf(d2 - (dSqrt * Math.sin(dAcos)));
        } else {
            pairVar.p1 = ValueOf;
            pairVar.p2 = ValueOf;
        }
    }

    private void renderWithMask(Canvas canvas, Mask mask, int i, int i2) {
        double x;
        double dCos;
        double d;
        Bitmap bitmap = getContext().mResourceManager.getBitmap(mask.getSrc());
        if (bitmap == null) {
            return;
        }
        canvas.save();
        double x2 = mask.getX();
        double y = mask.getY();
        float rotation = mask.getRotation();
        if (mask.isAlignAbsolute()) {
            float rotation2 = getRotation();
            if (rotation2 == 0.0f) {
                dCos = y - ((double) i2);
                d = x2 - ((double) i);
            } else {
                float f = rotation - rotation2;
                double d2 = (((double) rotation2) * PI) / 180.0d;
                float pivotX = getPivotX();
                float pivotY = getPivotY();
                if (this.mRotateXYpair == null) {
                    this.mRotateXYpair = new pair<>();
                }
                rotateXY(pivotX, pivotY, d2, this.mRotateXYpair);
                double dDoubleValue = ((double) i) + this.mRotateXYpair.p1.doubleValue();
                double dDoubleValue2 = ((double) i2) + this.mRotateXYpair.p2.doubleValue();
                rotateXY(descale(mask.getPivotX()), descale(mask.getPivotY()), (((double) mask.getRotation()) * PI) / 180.0d, this.mRotateXYpair);
                double dScale = (x2 + ((double) scale(this.mRotateXYpair.p1.doubleValue()))) - dDoubleValue;
                double dScale2 = (y + ((double) scale(this.mRotateXYpair.p2.doubleValue()))) - dDoubleValue2;
                double dSqrt = Math.sqrt((dScale * dScale) + (dScale2 * dScale2));
                double dAsin = Math.asin(dScale / dSqrt);
                double d3 = dScale2 > 0.0d ? d2 + dAsin : (d2 + PI) - dAsin;
                double dSin = Math.sin(d3) * dSqrt;
                dCos = dSqrt * Math.cos(d3);
                d = dSin;
                rotation = f;
            }
            x = d - ((double) getX());
            y = dCos - ((double) getY());
        } else {
            x = x2;
        }
        canvas.rotate(rotation, (float) (((double) mask.getPivotX()) + x + ((double) i)), (float) (((double) mask.getPivotY()) + y + ((double) i2)));
        int i3 = (int) x;
        int i4 = (int) y;
        int iRound = Math.round(mask.getWidth());
        if (iRound < 0) {
            iRound = bitmap.getWidth();
        }
        int iRound2 = Math.round(mask.getHeight());
        if (iRound2 < 0) {
            iRound2 = bitmap.getHeight();
        }
        int i5 = i3 + i;
        int i6 = i4 + i2;
        this.mDesRect.set(i5, i6, iRound + i5, iRound2 + i6);
        this.mMaskPaint.setAlpha(mask.getAlpha());
        canvas.drawBitmap(bitmap, (Rect) null, this.mDesRect, this.mMaskPaint);
        canvas.restore();
    }

    private void updateBitmap(boolean z) {
        updateBitmapImpl(z);
        if (this.mIsLoadAsyncSet || z) {
            return;
        }
        this.mLoadAsync = true;
    }
}
