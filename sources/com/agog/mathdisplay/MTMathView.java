package com.agog.mathdisplay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.agog.mathdisplay.parse.MTLineStyle;
import com.agog.mathdisplay.parse.MTMathList;
import com.agog.mathdisplay.parse.MTMathListBuilder;
import com.agog.mathdisplay.parse.MTParseError;
import com.agog.mathdisplay.parse.MTParseErrors;
import com.agog.mathdisplay.render.MTFont;
import com.agog.mathdisplay.render.MTMathListDisplay;
import com.agog.mathdisplay.render.MTTypesetter;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 Q2\u00020\u0001:\u0003QRSB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010F\u001a\u00020\u0010H\u0002J\u0010\u0010G\u001a\u00020\u00102\u0006\u0010H\u001a\u00020IH\u0002J\b\u0010J\u001a\u00020KH\u0002J\u0010\u0010L\u001a\u00020M2\u0006\u0010H\u001a\u00020IH\u0014J\u0018\u0010N\u001a\u00020M2\u0006\u0010O\u001a\u00020\u00072\u0006\u0010P\u001a\u00020\u0007H\u0014R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\u00020\f8BX\u0082\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u0018X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR(\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R$\u0010\"\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001a\"\u0004\b$\u0010%R$\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020&@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0011\u0010,\u001a\u00020-¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R$\u00101\u001a\u0002002\u0006\u0010\u001b\u001a\u000200@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R(\u00106\u001a\u0004\u0018\u00010\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R$\u0010<\u001a\u00020;2\u0006\u0010\u001b\u001a\u00020;@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R$\u0010A\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010E¨\u0006T"}, d2 = {"Lcom/agog/mathdisplay/MTMathView;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "_mathList", "Lcom/agog/mathdisplay/parse/MTMathList;", "currentStyle", "Lcom/agog/mathdisplay/parse/MTLineStyle;", "getCurrentStyle", "()Lcom/agog/mathdisplay/parse/MTLineStyle;", "displayErrorInline", "", "getDisplayErrorInline", "()Z", "setDisplayErrorInline", "(Z)V", "displayList", "Lcom/agog/mathdisplay/render/MTMathListDisplay;", "errorFontSize", "", "getErrorFontSize", "()F", "value", "Lcom/agog/mathdisplay/render/MTFont;", "font", "getFont", "()Lcom/agog/mathdisplay/render/MTFont;", "setFont", "(Lcom/agog/mathdisplay/render/MTFont;)V", "fontSize", "getFontSize", "setFontSize", "(F)V", "Lcom/agog/mathdisplay/MTMathView$MTMathViewMode;", "labelMode", "getLabelMode", "()Lcom/agog/mathdisplay/MTMathView$MTMathViewMode;", "setLabelMode", "(Lcom/agog/mathdisplay/MTMathView$MTMathViewMode;)V", "lastError", "Lcom/agog/mathdisplay/parse/MTParseError;", "getLastError", "()Lcom/agog/mathdisplay/parse/MTParseError;", "", "latex", "getLatex", "()Ljava/lang/String;", "setLatex", "(Ljava/lang/String;)V", "mathList", "getMathList", "()Lcom/agog/mathdisplay/parse/MTMathList;", "setMathList", "(Lcom/agog/mathdisplay/parse/MTMathList;)V", "Lcom/agog/mathdisplay/MTMathView$MTTextAlignment;", "textAlignment", "getTextAlignment", "()Lcom/agog/mathdisplay/MTMathView$MTTextAlignment;", "setTextAlignment", "(Lcom/agog/mathdisplay/MTMathView$MTTextAlignment;)V", "textColor", "getTextColor", "()I", "setTextColor", "(I)V", "displayError", "drawError", "canvas", "Landroid/graphics/Canvas;", "errorBounds", "Landroid/graphics/Rect;", "onDraw", "", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "Companion", "MTMathViewMode", "MTTextAlignment", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTMathView extends View {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private MTMathList _mathList;
    private MTLineStyle currentStyle;
    private boolean displayErrorInline;
    private MTMathListDisplay displayList;
    private final float errorFontSize;
    private MTFont font;
    private float fontSize;
    private MTMathViewMode labelMode;
    private final MTParseError lastError;
    private String latex;
    private MTMathList mathList;
    private MTTextAlignment textAlignment;
    private int textColor;

    /* JADX INFO: compiled from: MTMathView.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/agog/mathdisplay/MTMathView$MTMathViewMode;", "", "(Ljava/lang/String;I)V", "KMTMathViewModeDisplay", "KMTMathViewModeText", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public enum MTMathViewMode {
        KMTMathViewModeDisplay,
        KMTMathViewModeText
    }

    /* JADX INFO: compiled from: MTMathView.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/agog/mathdisplay/MTMathView$MTTextAlignment;", "", "(Ljava/lang/String;I)V", "KMTTextAlignmentLeft", "KMTTextAlignmentCenter", "KMTTextAlignmentRight", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public enum MTTextAlignment {
        KMTTextAlignmentLeft,
        KMTTextAlignmentCenter,
        KMTTextAlignmentRight
    }

    /* JADX INFO: compiled from: MTMathView.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[MTMathViewMode.values().length];
            iArr[MTMathViewMode.KMTMathViewModeDisplay.ordinal()] = 1;
            iArr[MTMathViewMode.KMTMathViewModeText.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[MTTextAlignment.values().length];
            iArr2[MTTextAlignment.KMTTextAlignmentLeft.ordinal()] = 1;
            iArr2[MTTextAlignment.KMTTextAlignmentCenter.ordinal()] = 2;
            iArr2[MTTextAlignment.KMTTextAlignmentRight.ordinal()] = 3;
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MTMathView(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MTMathView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ MTMathView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MTMathView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.lastError = new MTParseError(null, null, 3, null);
        this.latex = "";
        this.displayErrorInline = true;
        MTFontManager.INSTANCE.setContext(context);
        this.font = MTFontManager.INSTANCE.defaultFont();
        this.fontSize = 20.0f;
        this.labelMode = MTMathViewMode.KMTMathViewModeDisplay;
        this.textColor = -16777216;
        this.textAlignment = MTTextAlignment.KMTTextAlignmentLeft;
        this.currentStyle = MTLineStyle.KMTLineStyleDisplay;
        this.errorFontSize = 20.0f;
    }

    public final MTParseError getLastError() {
        return this.lastError;
    }

    public final MTMathList getMathList() {
        return this.mathList;
    }

    public final void setMathList(MTMathList mTMathList) {
        this.mathList = mTMathList;
        if (mTMathList != null) {
            setLatex(MTMathListBuilder.INSTANCE.toLatexString(mTMathList));
        }
    }

    public final String getLatex() {
        return this.latex;
    }

    public final void setLatex(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.latex = value;
        MTMathList mTMathListBuildFromString = MTMathListBuilder.INSTANCE.buildFromString(this.latex, this.lastError);
        if (this.lastError.getErrorcode() != MTParseErrors.ErrorNone) {
            this._mathList = null;
        } else {
            this._mathList = mTMathListBuildFromString;
        }
        this.displayList = null;
        requestLayout();
        invalidate();
    }

    /* JADX INFO: compiled from: MTMathView.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"Lcom/agog/mathdisplay/MTMathView$Companion;", "", "()V", "convertDpToPixel", "", "dp", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final float convertDpToPixel(float dp) {
            return Math.round(dp * (Resources.getSystem().getDisplayMetrics().densityDpi / 160.0f));
        }
    }

    public final boolean getDisplayErrorInline() {
        return this.displayErrorInline;
    }

    public final void setDisplayErrorInline(boolean z) {
        this.displayErrorInline = z;
    }

    public final MTFont getFont() {
        return this.font;
    }

    public final void setFont(MTFont mTFont) {
        this.font = mTFont;
        this.displayList = null;
        requestLayout();
        invalidate();
    }

    public final float getFontSize() {
        return this.fontSize;
    }

    public final void setFontSize(float f) {
        this.fontSize = f;
        MTFont mTFont = this.font;
        if (mTFont != null) {
            setFont(mTFont.copyFontWithSize(f));
        }
    }

    public final MTMathViewMode getLabelMode() {
        return this.labelMode;
    }

    public final void setLabelMode(MTMathViewMode value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.labelMode = value;
        this.displayList = null;
        requestLayout();
        invalidate();
    }

    public final int getTextColor() {
        return this.textColor;
    }

    public final void setTextColor(int i) {
        this.textColor = i;
        MTMathListDisplay mTMathListDisplay = this.displayList;
        if (mTMathListDisplay != null) {
            mTMathListDisplay.setTextColor(i);
        }
        invalidate();
    }

    @Override // android.view.View
    public final MTTextAlignment getTextAlignment() {
        return this.textAlignment;
    }

    public final void setTextAlignment(MTTextAlignment value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.textAlignment = value;
        requestLayout();
        invalidate();
    }

    private final MTLineStyle getCurrentStyle() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.labelMode.ordinal()];
        if (i == 1) {
            return MTLineStyle.KMTLineStyleDisplay;
        }
        if (i == 2) {
            return MTLineStyle.KMTLineStyleText;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean displayError() {
        return this.lastError.getErrorcode() != MTParseErrors.ErrorNone && this.displayErrorInline;
    }

    public final float getErrorFontSize() {
        return this.errorFontSize;
    }

    private final boolean drawError(Canvas canvas) {
        if (!displayError()) {
            return false;
        }
        Paint paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT);
        canvas.drawPaint(paint);
        paint.setColor(-65536);
        paint.setTextSize(INSTANCE.convertDpToPixel(this.errorFontSize));
        canvas.drawText(this.lastError.getErrordesc(), 0.0f, -errorBounds().top, paint);
        return true;
    }

    private final Rect errorBounds() {
        if (displayError()) {
            Paint paint = new Paint();
            paint.setTypeface(Typeface.DEFAULT);
            paint.setTextSize(INSTANCE.convertDpToPixel(this.errorFontSize));
            Rect rect = new Rect();
            String errordesc = this.lastError.getErrordesc();
            String errordesc2 = this.lastError.getErrordesc();
            Intrinsics.checkNotNull(errordesc2);
            paint.getTextBounds(errordesc, 0, errordesc2.length(), rect);
            return rect;
        }
        return new Rect(0, 0, 0, 0);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int paddingLeft;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (drawError(canvas)) {
            return;
        }
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = this.displayList;
        MTMathList mTMathList = this._mathList;
        if (mTMathList != null && mTMathListDisplayCreateLineForMathList == null) {
            MTTypesetter.Companion companion = MTTypesetter.INSTANCE;
            MTFont mTFont = this.font;
            Intrinsics.checkNotNull(mTFont);
            mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(mTMathList, mTFont, getCurrentStyle());
            this.displayList = mTMathListDisplayCreateLineForMathList;
        }
        if (mTMathListDisplayCreateLineForMathList != null) {
            mTMathListDisplayCreateLineForMathList.setTextColor(this.textColor);
            int i = WhenMappings.$EnumSwitchMapping$1[this.textAlignment.ordinal()];
            if (i == 1) {
                paddingLeft = getPaddingLeft();
            } else if (i == 2) {
                paddingLeft = ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - ((int) mTMathListDisplayCreateLineForMathList.getWidth())) / 2) + getPaddingLeft();
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                paddingLeft = (getWidth() - ((int) mTMathListDisplayCreateLineForMathList.getWidth())) - getPaddingRight();
            }
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            float ascent = mTMathListDisplayCreateLineForMathList.getAscent() + mTMathListDisplayCreateLineForMathList.getDescent();
            float f = this.fontSize;
            float f2 = 2;
            if (ascent < f / f2) {
                ascent = f / f2;
            }
            float descent = ((height - ascent) / f2) + mTMathListDisplayCreateLineForMathList.getDescent() + getPaddingBottom();
            mTMathListDisplayCreateLineForMathList.getPosition().setX(paddingLeft);
            mTMathListDisplayCreateLineForMathList.getPosition().setY(descent);
            canvas.save();
            canvas.translate(0.0f, getHeight());
            canvas.scale(1.0f, -1.0f);
            mTMathListDisplayCreateLineForMathList.draw(canvas);
            canvas.restore();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float ascent;
        float width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        MTMathListDisplay mTMathListDisplayCreateLineForMathList = this.displayList;
        MTMathList mTMathList = this._mathList;
        if (mTMathList != null && mTMathListDisplayCreateLineForMathList == null) {
            MTTypesetter.Companion companion = MTTypesetter.INSTANCE;
            MTFont mTFont = this.font;
            Intrinsics.checkNotNull(mTFont);
            mTMathListDisplayCreateLineForMathList = companion.createLineForMathList(mTMathList, mTFont, getCurrentStyle());
            this.displayList = mTMathListDisplayCreateLineForMathList;
        }
        if (mTMathListDisplayCreateLineForMathList != null) {
            ascent = mTMathListDisplayCreateLineForMathList.getAscent() + mTMathListDisplayCreateLineForMathList.getDescent() + paddingTop;
            width = mTMathListDisplayCreateLineForMathList.getWidth() + paddingLeft;
        } else {
            ascent = 0.0f;
            width = 0.0f;
        }
        Rect rectErrorBounds = errorBounds();
        setMeasuredDimension((int) (Math.max(width, rectErrorBounds.width()) + 1.0f), (int) (Math.max(ascent, rectErrorBounds.height()) + 1.0f));
    }
}
