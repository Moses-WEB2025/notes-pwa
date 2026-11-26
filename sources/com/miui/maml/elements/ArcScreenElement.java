package com.miui.maml.elements;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.Variables;
import miuix.navigation.SplitLayout;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class ArcScreenElement extends GeometryScreenElement {
    public static final String TAG_NAME = "Arc";
    private float mAngle;
    private Expression mAngleExp;
    private Path mArcPath;
    private boolean mClose;
    RectF mOvalRect;
    private float mSweep;
    private Expression mSweepExp;

    public ArcScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mArcPath = new Path();
        this.mOvalRect = new RectF();
        Variables variables = screenElementRoot.getVariables();
        this.mAngleExp = Expression.build(variables, getAttr(element, "startAngle"));
        this.mSweepExp = Expression.build(variables, getAttr(element, "sweep"));
        this.mClose = Boolean.parseBoolean(getAttr(element, SplitLayout.TAG_CLOSE));
        this.mAlign = ScreenElement.Align.CENTER;
        this.mAlignV = ScreenElement.AlignV.CENTER;
    }

    @Override // com.miui.maml.elements.GeometryScreenElement
    protected void onDraw(Canvas canvas, GeometryScreenElement.DrawMode drawMode) {
        float width = getWidth();
        float height = getHeight();
        float f = 0.0f - (width / 2.0f);
        float f2 = 0.0f - (height / 2.0f);
        this.mArcPath.reset();
        this.mOvalRect.left = f;
        this.mOvalRect.top = f2;
        this.mOvalRect.right = f + width;
        this.mOvalRect.bottom = f2 + height;
        if (Math.abs(this.mSweep) >= 360.0f) {
            canvas.drawOval(this.mOvalRect, this.mPaint);
            return;
        }
        if (this.mClose) {
            this.mArcPath.moveTo(this.mOvalRect.centerX(), this.mOvalRect.centerY());
        }
        this.mArcPath.arcTo(this.mOvalRect, this.mAngle, this.mSweep, !this.mClose);
        if (this.mClose) {
            this.mArcPath.close();
        }
        canvas.drawPath(this.mArcPath, this.mPaint);
    }

    @Override // com.miui.maml.elements.GeometryScreenElement, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    protected void doTick(long j) {
        super.doTick(j);
        if (isVisible()) {
            this.mRoot.getVariables();
            this.mAngle = (float) this.mAngleExp.evaluate();
            this.mSweep = (float) this.mSweepExp.evaluate();
        }
    }
}
