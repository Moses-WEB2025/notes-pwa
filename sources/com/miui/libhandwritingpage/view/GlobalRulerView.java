package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import xc.engine.operate.rule.GlobalRulerOperator;
import xc.engine.operate.rule.IGlobalRulerListener;
import xc.engine.operate.rule.IGlobalRulerOperator;

/* JADX INFO: loaded from: classes3.dex */
public class GlobalRulerView extends View implements IGlobalRulerListener {
    private RectF globalOffset;
    private final IGlobalRulerOperator globalRulerOperator;
    private boolean hasDownOnRuler;

    public GlobalRulerView(Context context) {
        super(context, null, 0);
        this.hasDownOnRuler = false;
        this.globalRulerOperator = new GlobalRulerOperator();
    }

    public IGlobalRulerOperator getRulerManipulation() {
        return this.globalRulerOperator;
    }

    public void setOffset(RectF rectF) {
        this.globalOffset = new RectF(rectF);
        IGlobalRulerOperator iGlobalRulerOperator = this.globalRulerOperator;
        if (iGlobalRulerOperator != null) {
            iGlobalRulerOperator.setGlobalOffset(new RectF(rectF));
        }
    }

    public RectF getOffset() {
        return this.globalOffset;
    }

    public boolean isUsingRuler() {
        return this.hasDownOnRuler;
    }

    public void enableRuler(boolean z) {
        IGlobalRulerOperator iGlobalRulerOperator = this.globalRulerOperator;
        if (iGlobalRulerOperator != null) {
            iGlobalRulerOperator.enableRuler(z);
            this.globalRulerOperator.setGlobalRulerListener(this);
        }
        postInvalidate();
    }

    public void removeListener() {
        IGlobalRulerOperator iGlobalRulerOperator = this.globalRulerOperator;
        if (iGlobalRulerOperator != null) {
            iGlobalRulerOperator.setGlobalRulerListener(null);
        }
    }

    public void onSizeChanged(int i, int i2) {
        IGlobalRulerOperator iGlobalRulerOperator = this.globalRulerOperator;
        if (iGlobalRulerOperator != null) {
            iGlobalRulerOperator.onSizeChanged(i, i2);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        IGlobalRulerOperator iGlobalRulerOperator = this.globalRulerOperator;
        if (iGlobalRulerOperator != null) {
            iGlobalRulerOperator.onDraw(canvas);
        }
    }

    @Override // xc.engine.operate.rule.IGlobalRulerListener
    public void onGlobalRulerDataChanged() {
        postInvalidate();
    }

    @Override // xc.engine.operate.rule.IGlobalRulerListener
    public void hasDownOnRuler(boolean z) {
        this.hasDownOnRuler = z;
    }
}
