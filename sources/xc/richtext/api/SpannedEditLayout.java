package xc.richtext.api;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: loaded from: classes6.dex */
public class SpannedEditLayout extends ViewGroup {
    private boolean mFixMode;
    private int mFixWidth;
    public SpannedLayoutModel mSpannedLayoutModel;
    protected SpannedEditModel mSpannedMode;

    public SpannedEditLayout(Context context, ScreenRenderProvider screenRenderProvider) {
        this(context, null, 0, screenRenderProvider);
    }

    public SpannedEditLayout(Context context, AttributeSet attributeSet, int i, ScreenRenderProvider screenRenderProvider) {
        super(context, attributeSet, i);
        this.mSpannedLayoutModel = new SpannedLayoutModel(getContext().getApplicationContext(), this, screenRenderProvider);
        this.mSpannedMode = new SpannedEditModel(this, screenRenderProvider);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        SpannedEditModel spannedEditModel = this.mSpannedMode;
        if (spannedEditModel == null || !spannedEditModel.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        SpannedEditModel spannedEditModel = this.mSpannedMode;
        if (spannedEditModel != null && spannedEditModel.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            spannedLayoutModel.mSpannedEditLayout.getParent().requestDisallowInterceptTouchEvent(motionEvent.getPointerCount() == 1);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        SpannedEditModel spannedEditModel = this.mSpannedMode;
        if (spannedEditModel != null) {
            spannedEditModel.setVisibleSize(i, i2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        SpannedEditModel spannedEditModel = this.mSpannedMode;
        if (spannedEditModel != null) {
            spannedEditModel.dispatchDraw(canvas);
        }
    }

    public void enableSelectMode(boolean z) {
        SpannedEditModel spannedEditModel = this.mSpannedMode;
        if (spannedEditModel != null) {
            spannedEditModel.enableSelectMode(z);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        for (int i3 = 0; getChildCount() > 0 && i3 < getChildCount(); i3++) {
            if ((getChildAt(i3).getTag() instanceof SpannedData) && getChildAt(i3).getWidth() != ((int) ((SpannedData) getChildAt(i3).getTag()).getDrawRectF(this.mSpannedMode.screenRenderProvider).width())) {
                measureChild(getChildAt(i3), i, i2);
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            spannedLayoutModel.onLayout(true, i, i2, i3, i4);
        }
    }

    public SpannedLayoutModel getSpannedLayoutModel() {
        return this.mSpannedLayoutModel;
    }

    public SpannedEditModel getSpannedEditModel() {
        return this.mSpannedMode;
    }

    public void setUnLimit(boolean z) {
        this.mSpannedMode.setUnLimit(z);
    }

    public void setFixMode(boolean z) {
        this.mFixMode = z;
    }

    public void setFixWidth(int i) {
        this.mFixWidth = i;
    }

    public EngineBindContext getEngineBindContext() {
        return this.mSpannedLayoutModel.getEngineBindContext();
    }

    public void release() {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            spannedLayoutModel.release();
        }
        SpannedEditModel spannedEditModel = this.mSpannedMode;
        if (spannedEditModel != null) {
            spannedEditModel.release();
        }
    }

    public void setGetToolViewHeight(SpannedLayoutInterface spannedLayoutInterface) {
        SpannedLayoutModel spannedLayoutModel = this.mSpannedLayoutModel;
        if (spannedLayoutModel != null) {
            spannedLayoutModel.setCallBack(spannedLayoutInterface);
        }
    }
}
