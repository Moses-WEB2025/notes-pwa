package com.miui.maml;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.ViewCompat;
import com.miui.maml.data.VariableNames;
import com.miui.maml.data.Variables;
import com.miui.maml.util.MamlAccessHelper;
import com.miui.maml.util.Utils;

/* JADX INFO: loaded from: classes3.dex */
public class MiAdvancedView extends View implements RendererController.IRenderable {
    private static final String LOG_TAG = "MiAdvancedView";
    private boolean mLoggedHardwareRender;
    private MamlAccessHelper mMamlAccessHelper;
    protected boolean mNeedDisallowInterceptTouchEvent;
    private boolean mPaused;
    private int mPivotX;
    private int mPivotY;
    protected ScreenElementRoot mRoot;
    private float mScale;
    private boolean mUseExternalRenderThread;

    public MiAdvancedView(Context context, ScreenElementRoot screenElementRoot) {
        super(context);
        this.mPaused = true;
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.mRoot = screenElementRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setOnHoverChangeListener(new ScreenElementRoot.OnHoverChangeListener() { // from class: com.miui.maml.MiAdvancedView.1
                @Override // com.miui.maml.ScreenElementRoot.OnHoverChangeListener
                public void onHoverChange(String str) {
                    MiAdvancedView.this.setContentDescription(str);
                    MiAdvancedView.this.sendAccessibilityEvent(32768);
                }
            });
        }
        init();
    }

    @Deprecated
    public MiAdvancedView(Context context, ScreenElementRoot screenElementRoot, RenderThread renderThread) {
        this(context, screenElementRoot);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            accessibilityNodeInfo.setText(screenElementRoot.getRawAttr("accessibilityText"));
        }
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    public void init() {
        this.mRoot.setRenderControllerRenderable(this);
        this.mRoot.setConfiguration(getResources().getConfiguration());
        this.mRoot.attachToVsync();
        this.mRoot.selfInit();
        MamlAccessHelper mamlAccessHelper = new MamlAccessHelper(this.mRoot, this);
        this.mMamlAccessHelper = mamlAccessHelper;
        ViewCompat.setAccessibilityDelegate(this, mamlAccessHelper);
    }

    public void cleanUp() {
        cleanUp(false);
    }

    public void cleanUp(boolean z) {
        this.mRoot.setKeepResource(z);
        setOnTouchListener(null);
        this.mRoot.detachFromVsync();
        this.mRoot.selfFinish();
        if (!z) {
            removeAccessHelperRef();
        }
        if (Utils.onMuiltDisplayType2()) {
            return;
        }
        Utils.triggerGC();
    }

    public final ScreenElementRoot getRoot() {
        return this.mRoot;
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        return (int) this.mRoot.getWidth();
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        return (int) this.mRoot.getHeight();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            onResume();
        } else if (i == 4 || i == 8) {
            onPause();
        }
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        MamlAccessHelper mamlAccessHelper = this.mMamlAccessHelper;
        if (mamlAccessHelper == null || !mamlAccessHelper.dispatchHoverEvent(motionEvent)) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            boolean zNeedDisallowInterceptTouchEvent = screenElementRoot.needDisallowInterceptTouchEvent();
            if (this.mNeedDisallowInterceptTouchEvent != zNeedDisallowInterceptTouchEvent) {
                getParent().requestDisallowInterceptTouchEvent(zNeedDisallowInterceptTouchEvent);
                this.mNeedDisallowInterceptTouchEvent = zNeedDisallowInterceptTouchEvent;
            }
            this.mRoot.onTouch(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.onHover(motionEvent);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (!this.mLoggedHardwareRender) {
            Log.d(LOG_TAG, "canvas hardware render: " + canvas.isHardwareAccelerated());
            this.mLoggedHardwareRender = true;
        }
        if (this.mScale != 0.0f) {
            int iSave = canvas.save();
            float f = this.mScale;
            canvas.scale(f, f, this.mPivotX, this.mPivotY);
            this.mRoot.render(canvas);
            canvas.restoreToCount(iSave);
            return;
        }
        this.mRoot.render(canvas);
    }

    public void onPause() {
        this.mPaused = true;
        this.mRoot.selfPause();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Variables variables = this.mRoot.getContext().mVariables;
        variables.put(VariableNames.VARIABLE_VIEW_WIDTH, (i3 - i) / this.mRoot.getScale());
        variables.put(VariableNames.VARIABLE_VIEW_HEIGHT, (i4 - i2) / this.mRoot.getScale());
        Object parent = getParent();
        while (parent instanceof View) {
            View view = (View) parent;
            i += view.getLeft() - view.getScrollX();
            i2 += view.getTop() - view.getScrollY();
            parent = view.getParent();
        }
        variables.put(VariableNames.VARIABLE_VIEW_X, i / this.mRoot.getScale());
        variables.put(VariableNames.VARIABLE_VIEW_Y, i2 / this.mRoot.getScale());
        this.mRoot.requestUpdate();
    }

    public void onResume() {
        this.mPaused = false;
        this.mRoot.selfResume();
    }

    @Override // com.miui.maml.RendererController.IRenderable
    public void doRender() {
        postInvalidate();
    }

    public void setScale(float f, int i, int i2) {
        this.mScale = f;
        this.mPivotX = i;
        this.mPivotY = i2;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mRoot.onConfigurationChanged(configuration);
    }

    private void removeAccessHelperRef() {
        this.mRoot.setMamlAccessHelper(null);
        MamlAccessHelper mamlAccessHelper = this.mMamlAccessHelper;
        if (mamlAccessHelper != null) {
            mamlAccessHelper.removeRoot();
        }
    }
}
