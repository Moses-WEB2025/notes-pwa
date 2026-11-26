package com.miui.handwriting.common.view.handview;

import android.content.Context;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.libhandwritingpage.view.PageInkView;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.function.IntSupplier;

/* JADX INFO: loaded from: classes3.dex */
public class CreationShieldPageInkView extends PageInkView {
    private IntSupplier getToolViewHeightFn;
    private int initX;
    private int initY;
    private boolean intercept;
    private boolean isShieldEvent;
    private PageAIListener listener;
    private Context mContext;
    private IMiuiHandWritingModel mHandWritingModel;
    private boolean mShowHeadView;

    public interface PageAIListener {
        void doPageAI();
    }

    @Override // com.miui.libhandwritingpage.view.PageInkView
    public int getToolViewHeight() {
        IntSupplier intSupplier = this.getToolViewHeightFn;
        if (intSupplier != null) {
            return intSupplier.getAsInt();
        }
        return 0;
    }

    public void setToolViewHeightCallback(IntSupplier intSupplier) {
        this.getToolViewHeightFn = intSupplier;
    }

    public CreationShieldPageInkView(Context context) {
        super(context, HandWritingManager.INSTANCE.getViewType());
        this.mShowHeadView = true;
        this.isShieldEvent = false;
        this.intercept = false;
        this.mContext = context;
    }

    public void setShieldEvent(boolean z) {
        this.isShieldEvent = z;
    }

    public boolean isShieldEvent() {
        return this.isShieldEvent;
    }

    public void setListener(PageAIListener pageAIListener) {
        this.listener = pageAIListener;
    }

    public void setShowHeadView(boolean z) {
        this.mShowHeadView = z;
        updateHeadView();
    }

    @Override // com.miui.libhandwritingpage.view.PageInkView
    public void updateHeadView() {
        if (!this.mShowHeadView) {
            View headView = getHeadView();
            if (headView != null) {
                headView.setVisibility(8);
                return;
            }
            return;
        }
        super.updateHeadView();
    }

    private View getHeadView() {
        try {
            Field declaredField = PageInkView.class.getDeclaredField("mHeadView");
            declaredField.setAccessible(true);
            return (View) declaredField.get(this);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.miui.libhandwritingpage.view.PageInkView, android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.intercept = true;
            this.initX = (int) motionEvent.getX();
            this.initY = (int) motionEvent.getY();
            this.intercept = false;
            if (!this.mContext.getSharedPreferences(Constants.CREATION_SHPREF, 0).getBoolean("isOnlyPenWrite", false) || motionEvent.getToolType(motionEvent.getActionIndex()) == 2) {
                Iterator<RectF> it = getPageInkFunc().getVisibleItemRectFList().iterator();
                while (it.hasNext()) {
                    if (it.next().contains(this.initX, this.initY)) {
                        this.intercept = true;
                        break;
                    }
                }
            }
        }
        if (action == 5) {
            this.intercept = false;
        }
        if (action == 1 && this.intercept && this.isShieldEvent) {
            getPageInkFunc().onCancelEvent();
            if (motionEvent.getEventTime() - motionEvent.getDownTime() < 300) {
                int iAbs = Math.abs(((int) motionEvent.getX()) - this.initX);
                int iAbs2 = Math.abs(((int) motionEvent.getY()) - this.initY);
                int scaledTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
                PageAIListener pageAIListener = this.listener;
                if (pageAIListener != null && iAbs <= scaledTouchSlop && iAbs2 <= scaledTouchSlop) {
                    pageAIListener.doPageAI();
                }
            }
            motionEvent.setAction(3);
            return super.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public IMiuiHandWritingModel getHandWritingModel() {
        return this.mHandWritingModel;
    }

    public void setHandWritingModel(IMiuiHandWritingModel iMiuiHandWritingModel) {
        this.mHandWritingModel = iMiuiHandWritingModel;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mContext = null;
    }
}
