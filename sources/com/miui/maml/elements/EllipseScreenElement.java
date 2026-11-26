package com.miui.maml.elements;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.miui.maml.ScreenElementRoot;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class EllipseScreenElement extends GeometryScreenElement {
    public static final String TAG_NAME = "Ellipse";

    public EllipseScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mAlign = ScreenElement.Align.CENTER;
        this.mAlignV = ScreenElement.AlignV.CENTER;
    }

    @Override // com.miui.maml.elements.GeometryScreenElement
    protected void onDraw(Canvas canvas, GeometryScreenElement.DrawMode drawMode) {
        float width = getWidth();
        float height = getHeight();
        if (width < 0.0f || height < 0.0f) {
            return;
        }
        if (drawMode == GeometryScreenElement.DrawMode.STROKE_OUTER) {
            width += this.mWeight;
            height += this.mWeight;
        } else if (drawMode == GeometryScreenElement.DrawMode.STROKE_INNER) {
            width -= this.mWeight;
            height -= this.mWeight;
            if (width < 0.0f || height < 0.0f) {
                return;
            }
        }
        float f = 0.0f - (width / 2.0f);
        float f2 = 0.0f - (height / 2.0f);
        canvas.drawOval(new RectF(f, f2, width + f, height + f2), this.mPaint);
    }
}
