package com.miui.maml.component;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.miui.maml.R;
import com.miui.maml.RendererController;
import com.miui.maml.ResourceLoader;
import com.miui.maml.ResourceManager;
import com.miui.maml.ScreenContext;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.VariableNames;
import com.miui.maml.data.Variables;
import com.miui.maml.util.AssetsResourceLoader;
import com.miui.maml.util.FolderResourceLoader;
import com.miui.maml.util.HideSdkDependencyUtils;
import com.miui.maml.util.MamlAccessHelper;
import com.miui.maml.util.MamlViewManager;
import com.miui.maml.util.Utils;
import com.miui.maml.util.ZipResourceLoader;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class MamlView extends FrameLayout implements RendererController.IRenderable, MamlViewManager {
    private static final String BLUR_VAR_NAME = "__blur_ratio";
    public static final int MODE_ASSETS_FOLDER = 2;
    public static final int MODE_FOLDER = 3;
    public static final int MODE_ZIP = 1;
    private static final String TAG = "MamlView";
    private static final String VERSION = "1.0.0";
    private static final int VERSION_CODE = 1;
    private boolean mAutoFinishRoot;
    private boolean mAutoRemoveCache;
    private boolean mCanvasParamsChanged;
    private final ScreenElementRoot.OnExternCommandListener mCommandListener;
    private WeakReference<OnExternCommandListener> mExternCommandListener;
    private volatile boolean mFinished;
    private boolean mHasDelay;
    private int mLastBlurRatio;
    private WindowManager.LayoutParams mLp;
    private MamlAccessHelper mMamlAccessHelper;
    protected boolean mNeedDisallowInterceptTouchEvent;
    private int mPivotX;
    private int mPivotY;
    protected ScreenElementRoot mRoot;
    private float mScale;
    private InnerView mView;
    private WindowManager mWindowManager;
    private float mX;
    private float mY;

    public interface OnExternCommandListener {
        void onCommand(String str, Double d, String str2);
    }

    public int getMamlVersionCode() {
        return 1;
    }

    public MamlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAutoFinishRoot = true;
        this.mCommandListener = new ScreenElementRoot.OnExternCommandListener() { // from class: com.miui.maml.component.MamlView.1
            @Override // com.miui.maml.ScreenElementRoot.OnExternCommandListener
            public void onCommand(String str, Double d, String str2) {
                OnExternCommandListener onExternCommandListener;
                if (MamlView.this.mExternCommandListener == null || (onExternCommandListener = (OnExternCommandListener) MamlView.this.mExternCommandListener.get()) == null) {
                    return;
                }
                onExternCommandListener.onCommand(str, d, str2);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MamlView);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.MamlView_path);
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.MamlView_innerPath);
        int i = typedArrayObtainStyledAttributes.getInt(R.styleable.MamlView_resMode, 2);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MamlView_autoRemoveCache, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MamlView_touchable, true);
        load(context, getResourceLoader(context, string, string2, i), z);
        setTouchable(z2);
    }

    public MamlView(Context context, String str, int i) {
        this(context, str, (String) null, i);
    }

    public MamlView(Context context, String str, int i, boolean z) {
        this(context, str, null, i, z);
    }

    public MamlView(Context context, String str, String str2, int i) {
        this(context, str, str2, i, false);
    }

    public MamlView(Context context, String str, String str2, int i, boolean z) {
        super(context);
        this.mAutoFinishRoot = true;
        this.mCommandListener = new ScreenElementRoot.OnExternCommandListener() { // from class: com.miui.maml.component.MamlView.1
            @Override // com.miui.maml.ScreenElementRoot.OnExternCommandListener
            public void onCommand(String str3, Double d, String str4) {
                OnExternCommandListener onExternCommandListener;
                if (MamlView.this.mExternCommandListener == null || (onExternCommandListener = (OnExternCommandListener) MamlView.this.mExternCommandListener.get()) == null) {
                    return;
                }
                onExternCommandListener.onCommand(str3, d, str4);
            }
        };
        load(context, getResourceLoader(context, str, str2, i), z);
    }

    public MamlView(Context context, ScreenElementRoot screenElementRoot) {
        this(context, screenElementRoot, 0L);
    }

    @Deprecated
    public MamlView(Context context, ScreenElementRoot screenElementRoot, long j) {
        super(context);
        this.mAutoFinishRoot = true;
        this.mCommandListener = new ScreenElementRoot.OnExternCommandListener() { // from class: com.miui.maml.component.MamlView.1
            @Override // com.miui.maml.ScreenElementRoot.OnExternCommandListener
            public void onCommand(String str3, Double d, String str4) {
                OnExternCommandListener onExternCommandListener;
                if (MamlView.this.mExternCommandListener == null || (onExternCommandListener = (OnExternCommandListener) MamlView.this.mExternCommandListener.get()) == null) {
                    return;
                }
                onExternCommandListener.onCommand(str3, d, str4);
            }
        };
        initMamlview(context, screenElementRoot);
        this.mAutoFinishRoot = false;
    }

    private ResourceLoader getResourceLoader(Context context, String str, String str2, int i) {
        if (Utils.isEmpty(str)) {
            return null;
        }
        if (i == 1) {
            return new ZipResourceLoader(str, str2);
        }
        if (i == 2) {
            return new AssetsResourceLoader(context, str);
        }
        if (i != 3) {
            return null;
        }
        return new FolderResourceLoader(str);
    }

    private void load(Context context, ResourceLoader resourceLoader, boolean z) {
        this.mAutoRemoveCache = z;
        if (resourceLoader != null) {
            ScreenElementRoot screenElementRoot = new ScreenElementRoot(new ScreenContext(context.getApplicationContext(), new ResourceManager(resourceLoader)));
            if (screenElementRoot.load()) {
                screenElementRoot.setKeepResource(true);
                initMamlview(context, screenElementRoot);
            }
        }
    }

    private void initMamlview(Context context, ScreenElementRoot screenElementRoot) {
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        screenElementRoot.getClass();
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mView = new InnerView(context);
        addView(this.mView, new ViewGroup.LayoutParams(-1, -1));
        this.mRoot = screenElementRoot;
        screenElementRoot.setViewManager(this);
        this.mRoot.setOnHoverChangeListener(new ScreenElementRoot.OnHoverChangeListener() { // from class: com.miui.maml.component.MamlView.2
            @Override // com.miui.maml.ScreenElementRoot.OnHoverChangeListener
            public void onHoverChange(String str) {
                MamlView.this.setContentDescription(str);
                MamlView.this.sendAccessibilityEvent(32768);
            }
        });
        init();
    }

    @Override // com.miui.maml.util.MamlViewManager
    public Context getViewContext() {
        return getContext();
    }

    @Override // com.miui.maml.util.MamlViewManager
    public Rect getViewLocationOnScreen() {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        int i = iArr[0];
        return new Rect(i, iArr[1], getMeasuredWidth() + i, iArr[1] + getMeasuredHeight());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        MamlAccessHelper mamlAccessHelper = this.mMamlAccessHelper;
        if (mamlAccessHelper == null || !mamlAccessHelper.dispatchHoverEvent(motionEvent)) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return true;
    }

    @Override // com.miui.maml.RendererController.IRenderable
    public void doRender() {
        this.mView.postInvalidate();
        blurBackground();
    }

    private class InnerView extends View {
        public InnerView(Context context) {
            super(context);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (MamlView.this.mRoot == null) {
                return;
            }
            if (MamlView.this.mCanvasParamsChanged) {
                int iSave = canvas.save();
                canvas.translate(MamlView.this.mX, MamlView.this.mY);
                if (MamlView.this.mScale != 0.0f) {
                    canvas.scale(MamlView.this.mScale, MamlView.this.mScale, MamlView.this.mPivotX, MamlView.this.mPivotY);
                }
                MamlView.this.mRoot.render(canvas);
                canvas.restoreToCount(iSave);
                return;
            }
            MamlView.this.mRoot.render(canvas);
        }
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
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setConfiguration(getResources().getConfiguration());
            this.mRoot.setMamlViewOnExternCommandListener(this.mCommandListener);
            this.mRoot.setRenderControllerRenderable(this);
            this.mRoot.attachToVsync();
            this.mRoot.selfInit();
            MamlAccessHelper mamlAccessHelper = new MamlAccessHelper(this.mRoot, this);
            this.mMamlAccessHelper = mamlAccessHelper;
            ViewCompat.setAccessibilityDelegate(this, mamlAccessHelper);
        }
    }

    @Deprecated
    public void cleanUp() {
        cleanUp(false);
    }

    @Deprecated
    public void cleanUp(boolean z) {
        setOnTouchListener(null);
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setKeepResource(z);
            this.mRoot.selfFinish();
            if (!z) {
                removeRelatedBitmapsRef();
            }
            Utils.triggerGC();
        }
    }

    @Override // android.view.View
    protected int getSuggestedMinimumWidth() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            return (int) screenElementRoot.getWidth();
        }
        return -1;
    }

    @Override // android.view.View
    protected int getSuggestedMinimumHeight() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            return (int) screenElementRoot.getHeight();
        }
        return -1;
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
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouch;
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            boolean zNeedDisallowInterceptTouchEvent = screenElementRoot.needDisallowInterceptTouchEvent();
            if (this.mNeedDisallowInterceptTouchEvent != zNeedDisallowInterceptTouchEvent) {
                getParent().requestDisallowInterceptTouchEvent(zNeedDisallowInterceptTouchEvent);
                this.mNeedDisallowInterceptTouchEvent = zNeedDisallowInterceptTouchEvent;
            }
            zOnTouch = this.mRoot.onTouch(motionEvent);
        } else {
            zOnTouch = false;
        }
        return zOnTouch || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.onHover(motionEvent);
        }
        return super.onHoverEvent(motionEvent);
    }

    public void setPath(String str) {
        load(getContext(), getResourceLoader(getContext(), str, null, 1), this.mAutoRemoveCache);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onResume();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onPause();
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.selfPause();
        }
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.selfResume();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            Variables variables = screenElementRoot.getContext().mVariables;
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
    }

    public void setScale(float f, int i, int i2) {
        this.mScale = f;
        this.mPivotX = i;
        this.mPivotY = i2;
        this.mCanvasParamsChanged = true;
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.requestUpdate();
        }
    }

    public void setTranslate(float f, float f2) {
        this.mX = f;
        this.mY = f2;
        this.mCanvasParamsChanged = true;
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.requestUpdate();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.onConfigurationChanged(configuration);
        }
    }

    @Deprecated
    public void setWindowLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.mLp = layoutParams;
    }

    private void blurBackground() {
        try {
            ScreenElementRoot screenElementRoot = this.mRoot;
            if (screenElementRoot == null || !screenElementRoot.isMamlBlurWindow() || this.mLp == null || !this.mRoot.getVariables().existsDouble(BLUR_VAR_NAME)) {
                return;
            }
            int i = (int) this.mRoot.getVariables().getDouble(BLUR_VAR_NAME);
            if (i < 0) {
                i = 0;
            } else if (i > 100) {
                i = 100;
            }
            if (i != this.mLastBlurRatio) {
                this.mLastBlurRatio = i;
                if (i == 0) {
                    this.mLp.flags &= -5;
                } else {
                    HideSdkDependencyUtils.WindowManager_LayoutParams_setLayoutParamsBlurRatio(this.mLp, (i * 1.0f) / 100.0f);
                    this.mLp.flags |= 4;
                }
                this.mWindowManager.updateViewLayout(this, this.mLp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void onDestory() {
        onDestroy();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        finish();
    }

    @Deprecated
    public void onCommand(String str) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.onCommand(str);
        }
    }

    public void sendCommand(String str) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.onCommand(str);
        }
    }

    public void setOnExternCommandListener(OnExternCommandListener onExternCommandListener) {
        this.mExternCommandListener = onExternCommandListener == null ? null : new WeakReference<>(onExternCommandListener);
    }

    public void putVariableString(String str, String str2) {
        putVariableString(str, str2, 0);
    }

    public void putVariableString(String str, String str2, int i) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.getVariables().put(str, str2, i);
        }
    }

    public void putVariableNumber(String str, double d) {
        putVariableNumber(str, d, 0);
    }

    public void putVariableNumber(String str, double d, int i) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.getVariables().put(str, d, i);
        }
    }

    public void putVariableObject(String str, Object obj) {
        putVariableObject(str, obj, 0);
    }

    public void putVariableObject(String str, Object obj, int i) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.getVariables().put(str, obj, i);
        }
    }

    public double getVariableNumber(String str) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            return Utils.getVariableNumber(str, screenElementRoot.getVariables());
        }
        return 0.0d;
    }

    public String getVariableString(String str) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            return Utils.getVariableString(str, screenElementRoot.getVariables());
        }
        return null;
    }

    @Deprecated
    public final void setKeepResource(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setKeepResource(z);
        }
    }

    public boolean isLoaded() {
        return this.mRoot != null;
    }

    public void setAutoFinishRoot(boolean z) {
        this.mAutoFinishRoot = z;
    }

    public String getMamlVersionName() {
        return "1.0.0";
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public boolean disableCutRoundCorner() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        return screenElementRoot != null && screenElementRoot.isDisableCutRoundCorner();
    }

    public void setCacheSize(int i) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.getContext().mResourceManager.setCacheSize(i);
        }
    }

    public void clearBitmapCache() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            ResourceManager resourceManager = screenElementRoot.getContext().mResourceManager;
            ResourceManager.clear();
        }
    }

    public void removeRelatedBitmapsRef() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.getContext().mResourceManager.clearByKeys();
        }
    }

    public void setTouchable(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setTouchable(z);
        }
    }

    public void setAutoDarkenWallpaper(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setAutoDarkenWallpaper(true);
        }
    }

    public void requestUpdate() {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.requestUpdate();
        }
    }

    public void setSaveConfigViaProvider(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setSaveConfigViaProvider(z);
        }
    }

    public void setSaveConfigOnlyInPause(boolean z) {
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.setSaveConfigViaProvider(z);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        ScreenElementRoot screenElementRoot = this.mRoot;
        int width = screenElementRoot != null ? (int) screenElementRoot.getWidth() : 0;
        ScreenElementRoot screenElementRoot2 = this.mRoot;
        int height = screenElementRoot2 != null ? (int) screenElementRoot2.getHeight() : 0;
        if (mode == Integer.MIN_VALUE && width > 0) {
            size = Math.min(size, width);
        }
        if (mode2 == Integer.MIN_VALUE && height > 0) {
            size2 = Math.min(size2, height);
        }
        setMeasuredDimension(size, size2);
    }

    private void finish() {
        if (this.mFinished || !this.mAutoFinishRoot) {
            return;
        }
        this.mFinished = true;
        setOnTouchListener(null);
        ScreenElementRoot screenElementRoot = this.mRoot;
        if (screenElementRoot != null) {
            screenElementRoot.selfFinish();
            this.mRoot.detachFromVsync();
            this.mRoot.getVariables().reset();
            removeAccessHelperRef();
            if (this.mAutoRemoveCache) {
                removeRelatedBitmapsRef();
            }
            this.mRoot = null;
        }
    }

    private void removeAccessHelperRef() {
        this.mRoot.setMamlAccessHelper(null);
        MamlAccessHelper mamlAccessHelper = this.mMamlAccessHelper;
        if (mamlAccessHelper != null) {
            mamlAccessHelper.removeRoot();
        }
    }
}
