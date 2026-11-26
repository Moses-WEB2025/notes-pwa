package com.miui.maml.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.util.Utils;
import java.util.ArrayList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class ImageNumberScreenElement extends ImageScreenElement {
    public static final String TAG_NAME = "ImageNumber";
    public static final String TAG_NAME1 = "ImageChars";
    private String LOG_TAG;
    private int mBmpHeight;
    private int mBmpWidth;
    private Bitmap mCachedBmp;
    private Canvas mCachedCanvas;
    private ArrayList<CharName> mNameMap;
    private Expression mNumExpression;
    private String mOldSrc;
    private double mPreNumber;
    private String mPreStr;
    private int mSpace;
    private Expression mSpaceExpression;
    private Expression mStrExpression;
    private String mStrValue;

    private class CharName {

        /* JADX INFO: renamed from: ch, reason: collision with root package name */
        public char f6ch;
        public String name;

        public CharName(char c, String str) {
            this.f6ch = c;
            this.name = str;
        }
    }

    public ImageNumberScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.LOG_TAG = "ImageNumberScreenElement";
        this.mPreNumber = Double.MIN_VALUE;
        this.mNumExpression = Expression.build(getVariables(), getAttr(element, "number"));
        this.mStrExpression = Expression.build(getVariables(), getAttr(element, TypedValues.Custom.S_STRING));
        this.mSpaceExpression = Expression.build(getVariables(), getAttr(element, "space"));
        String attr = getAttr(element, "charNameMap");
        if (TextUtils.isEmpty(attr)) {
            return;
        }
        this.mNameMap = new ArrayList<>();
        for (String str : attr.split(",")) {
            this.mNameMap.add(new CharName(str.charAt(0), str.substring(1)));
        }
    }

    @Override // com.miui.maml.elements.ImageScreenElement, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        Expression expression = this.mSpaceExpression;
        this.mSpace = expression == null ? 0 : (int) scale(expression.evaluate());
        this.mCurrentBitmap.setBitmap(this.mCachedBmp);
    }

    @Override // com.miui.maml.elements.ImageScreenElement, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        String strDoubleToString;
        super.doTick(j);
        if (this.mNumExpression == null && this.mStrExpression == null && this.mStrValue == null) {
            if (this.mCachedBmp != null) {
                this.mCachedBmp = null;
                this.mPreStr = null;
                this.mCurrentBitmap.setBitmap(null);
                updateBitmapVars();
                return;
            }
            return;
        }
        String src = getSrc();
        boolean zEquals = TextUtils.equals(src, this.mOldSrc);
        Expression expression = this.mNumExpression;
        if (expression != null) {
            double dEvaluate = evaluate(expression);
            if (dEvaluate == this.mPreNumber && zEquals) {
                return;
            }
            this.mPreNumber = dEvaluate;
            strDoubleToString = Utils.doubleToString(dEvaluate);
        } else {
            Expression expression2 = this.mStrExpression;
            if (expression2 == null && this.mStrValue == null) {
                strDoubleToString = null;
            } else {
                String str = this.mStrValue;
                String strEvaluateStr = str != null ? str : evaluateStr(expression2);
                if (TextUtils.equals(strEvaluateStr, this.mPreStr) && zEquals) {
                    return;
                }
                this.mPreStr = strEvaluateStr;
                strDoubleToString = strEvaluateStr;
            }
        }
        Bitmap bitmap = this.mCachedBmp;
        if (bitmap != null) {
            bitmap.eraseColor(0);
        }
        this.mOldSrc = src;
        this.mBmpWidth = 0;
        int length = strDoubleToString != null ? strDoubleToString.length() : 0;
        for (int i = 0; i < length; i++) {
            Bitmap numberBitmap = getNumberBitmap(src, charToStr(strDoubleToString.charAt(i)));
            if (numberBitmap == null) {
                Log.e(this.LOG_TAG, "Fail to get bitmap for number " + String.valueOf(strDoubleToString.charAt(i)));
                return;
            }
            int width = this.mBmpWidth + numberBitmap.getWidth();
            int height = numberBitmap.getHeight();
            Bitmap bitmap2 = this.mCachedBmp;
            int width2 = bitmap2 == null ? 0 : bitmap2.getWidth();
            Bitmap bitmap3 = this.mCachedBmp;
            int height2 = bitmap3 == null ? 0 : bitmap3.getHeight();
            if (width > width2 || height > height2) {
                Bitmap bitmap4 = this.mCachedBmp;
                if (width > width2) {
                    int i2 = length - i;
                    width2 = this.mBmpWidth + (numberBitmap.getWidth() * i2) + (this.mSpace * (i2 - 1));
                }
                if (height <= height2) {
                    height = height2;
                }
                this.mBmpHeight = height;
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width2, height, Bitmap.Config.ARGB_8888);
                this.mCachedBmp = bitmapCreateBitmap;
                bitmapCreateBitmap.setDensity(numberBitmap.getDensity());
                this.mCurrentBitmap.setBitmap(this.mCachedBmp);
                Canvas canvas = new Canvas(this.mCachedBmp);
                this.mCachedCanvas = canvas;
                if (bitmap4 != null) {
                    canvas.drawBitmap(bitmap4, 0.0f, 0.0f, (Paint) null);
                }
            }
            this.mCachedCanvas.drawBitmap(numberBitmap, this.mBmpWidth, 0.0f, (Paint) null);
            int width3 = this.mBmpWidth + numberBitmap.getWidth();
            this.mBmpWidth = width3;
            if (i < length - 1) {
                this.mBmpWidth = width3 + this.mSpace;
            }
        }
        this.mCurrentBitmap.updateVersion();
        updateBitmapVars();
    }

    public void setValue(double d) {
        setValue(Utils.doubleToString(d));
    }

    public void setValue(String str) {
        this.mStrValue = str;
        requestUpdate();
    }

    @Override // com.miui.maml.elements.ImageScreenElement
    protected void updateBitmapImpl(boolean z) {
        this.mCurrentBitmap.setBitmap(this.mCachedBmp);
        updateBitmapVars();
    }

    @Override // com.miui.maml.elements.ImageScreenElement, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        this.mPreNumber = Double.MIN_VALUE;
        this.mPreStr = null;
    }

    private String charToStr(char c) {
        ArrayList<CharName> arrayList = this.mNameMap;
        if (arrayList != null) {
            for (CharName charName : arrayList) {
                if (charName.f6ch == c) {
                    return charName.name;
                }
            }
        }
        if (c == '.') {
            return "dot";
        }
        return String.valueOf(c);
    }

    @Override // com.miui.maml.elements.ImageScreenElement
    protected int getBitmapWidth() {
        return this.mBmpWidth;
    }

    @Override // com.miui.maml.elements.ImageScreenElement
    protected int getBitmapHeight() {
        return this.mBmpHeight;
    }

    private Bitmap getNumberBitmap(String str, String str2) {
        return getContext().mResourceManager.getBitmap(Utils.addFileNameSuffix(str, str2));
    }
}
