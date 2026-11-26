package com.miui.handwriting.common.mask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.ScreenModeUtils;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.utils.UIUtils;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.core.util.MiuixUIUtils;
import miuix.smooth.SmoothFrameLayout2;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class CanvasElementTransactionMask extends FrameLayout {
    private static final float BALL_SIZE_SCALE = 0.76f;
    private static final float BG_START_SCALE = 0.98f;
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.76f;
    public static final String SHARE_ELEMENT_TRANSITION_NAME = "canvas_ai_image";
    private static final String TAG = "CanvasElementTransactionMask";
    private View mBackgroundView;
    private AnimConfig mBallAnimConfig;
    private View mBallView;
    private ImageView mCanvasMaskView;
    private Action mCompleteAction;
    private AnimConfig mCornerAnimConfig;
    ValueProperty mCornerValueProperty;
    private RoundedImageView mGhostView;
    private AnimConfig mImgAnimConfig;
    private ImageView mMaskView;
    private SmoothFrameLayout2 mSmoothFrameLayout;
    private float mToolbarHeight;

    public interface Action {
        void run();
    }

    public CanvasElementTransactionMask(Context context) {
        this(context, null, 0);
    }

    public CanvasElementTransactionMask(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CanvasElementTransactionMask(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mImgAnimConfig = new AnimConfig();
        this.mBallAnimConfig = new AnimConfig();
        this.mCornerAnimConfig = new AnimConfig();
        this.mCornerValueProperty = new ValueProperty("cornerRadius", 0.03f);
        this.mToolbarHeight = 0.0f;
        initView();
    }

    private void initView() {
        this.mImgAnimConfig.setEase(FolmeEase.spring(1.0f, 0.4f));
        this.mImgAnimConfig.setSpecial(ViewProperty.Y, FolmeEase.spring(1.0f, 0.45f), new float[0]);
        this.mImgAnimConfig.setSpecial(ViewProperty.ALPHA, FolmeEase.spring(1.0f, 0.15f), 100L, new float[0]);
        this.mBallAnimConfig.setEase(FolmeEase.spring(1.0f, 0.4f));
        this.mBallAnimConfig.setSpecial(ViewProperty.Y, FolmeEase.spring(1.0f, 0.45f), new float[0]);
        this.mBallAnimConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(1.0f, 0.55f), 100L, new float[0]);
        this.mBallAnimConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(1.0f, 0.55f), 100L, new float[0]);
        this.mBallAnimConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.1
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                super.onUpdate(obj, collection);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (CanvasElementTransactionMask.this.mBallView != null) {
                    Folme.clean(CanvasElementTransactionMask.this.mBallView);
                }
                if (CanvasElementTransactionMask.this.mCompleteAction != null) {
                    CanvasElementTransactionMask.this.mCompleteAction.run();
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                if (CanvasElementTransactionMask.this.mBallView != null) {
                    Folme.clean(CanvasElementTransactionMask.this.mBallView);
                }
                if (CanvasElementTransactionMask.this.mCompleteAction != null) {
                    CanvasElementTransactionMask.this.mCompleteAction.run();
                }
            }
        });
        this.mCornerAnimConfig.setEase(FolmeEase.spring(1.0f, 0.25f));
        this.mCornerAnimConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.2
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
                super.onUpdate(obj, collection);
                UpdateInfo updateInfoFindBy = UpdateInfo.findBy(collection, CanvasElementTransactionMask.this.mCornerValueProperty);
                if (updateInfoFindBy == null || CanvasElementTransactionMask.this.mSmoothFrameLayout == null) {
                    return;
                }
                CanvasElementTransactionMask.this.mSmoothFrameLayout.setCornerRadius(updateInfoFindBy.getFloatValue());
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Folme.clean("cornerRadius");
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                Folme.clean("cornerRadius");
            }
        });
    }

    private void createCanvasMaskView(RectF rectF, CanvasMaskEntity canvasMaskEntity, float f, boolean z) {
        releaseCanvasMaskView();
        RectF rectF2 = new RectF(rectF.left - f, rectF.top - f, rectF.right + f, rectF.bottom + f);
        int iWidth = ((int) rectF2.width()) + canvasMaskEntity.getExpandWidth();
        int iHeight = ((int) rectF2.height()) + canvasMaskEntity.getExpandHeight();
        if (UIUtils.isRTL()) {
            float windowWidth = UIUtils.getWindowWidth(getContext()) - iWidth;
            rectF2.left -= windowWidth;
            rectF2.right -= windowWidth;
        }
        this.mCanvasMaskView = new ImageView(getContext());
        Bitmap maskBitmap = canvasMaskEntity.getMaskBitmap();
        if (maskBitmap != null) {
            this.mCanvasMaskView.setImageBitmap(maskBitmap);
        }
        this.mCanvasMaskView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mCanvasMaskView.setX(rectF2.left + canvasMaskEntity.getOffsetX());
        this.mCanvasMaskView.setY(rectF2.top + canvasMaskEntity.getOffsetY());
        this.mCanvasMaskView.setVisibility(z ? 0 : 4);
        float angle = canvasMaskEntity.getAngle();
        if (angle != 0.0f) {
            this.mCanvasMaskView.setBackgroundColor(canvasMaskEntity.getBgColor());
        }
        this.mCanvasMaskView.setRotation(angle);
        addView(this.mCanvasMaskView, new FrameLayout.LayoutParams(iWidth, iHeight));
    }

    public PointF createImageMaskView(RectF rectF, float f, float f2, Bitmap bitmap, boolean z) {
        this.mSmoothFrameLayout = new SmoothFrameLayout2(getContext());
        this.mMaskView = new ImageView(getContext());
        int iWidth = (int) rectF.width();
        int iHeight = (int) rectF.height();
        Math.max(iWidth, iHeight);
        addView(this.mSmoothFrameLayout, new FrameLayout.LayoutParams(iWidth, iHeight));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mSmoothFrameLayout.addView(this.mMaskView, layoutParams);
        if (z) {
            View view = new View(getContext());
            view.setBackgroundColor(-16711936);
            view.setAlpha(0.4f);
            this.mSmoothFrameLayout.addView(view, layoutParams);
        }
        this.mMaskView.setImageBitmap(bitmap);
        this.mMaskView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mSmoothFrameLayout.setCornerRadius(f);
        Folme.useAt(this.mSmoothFrameLayout).state().setTo(ViewProperty.X, Float.valueOf(rectF.left), ViewProperty.Y, Float.valueOf(rectF.top), ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.ROTATION, Float.valueOf(f2));
        return new PointF(iWidth, iHeight);
    }

    public PointF createImageMaskView(RectF rectF, float f, float f2, CanvasMaskEntity canvasMaskEntity, boolean z) {
        this.mSmoothFrameLayout = new SmoothFrameLayout2(getContext());
        this.mMaskView = new ImageView(getContext());
        int iWidth = ((int) rectF.width()) + canvasMaskEntity.getExpandWidth();
        int iHeight = ((int) rectF.height()) + canvasMaskEntity.getExpandHeight();
        Math.max(iWidth, iHeight);
        addView(this.mSmoothFrameLayout, new FrameLayout.LayoutParams(iWidth, iHeight));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mSmoothFrameLayout.addView(this.mMaskView, layoutParams);
        if (z) {
            View view = new View(getContext());
            view.setBackgroundColor(-16711936);
            view.setAlpha(0.4f);
            this.mSmoothFrameLayout.addView(view, layoutParams);
        }
        Bitmap contentBitmap = canvasMaskEntity.getContentBitmap();
        if (contentBitmap != null) {
            this.mMaskView.setImageBitmap(contentBitmap);
        }
        this.mMaskView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mSmoothFrameLayout.setCornerRadius(f);
        return new PointF(iWidth, iHeight);
    }

    public PointF createImageMaskView(RectF rectF, float f, String str) {
        return createImageMaskView(rectF, f, 0.0f, getDrawBitmap(str, rectF.width()), false);
    }

    public Bitmap getDrawBitmap(String str, float f) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(str, options);
        int i2 = options.outWidth;
        if (i2 == 0 || options.outHeight == 0) {
            return null;
        }
        int i3 = (int) (i2 / f);
        if (i3 >= 6) {
            i = 8;
        } else if (i3 > 2) {
            i = 4;
        } else if (i3 > 1) {
            i = 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        if (((options.outWidth / i) * options.outHeight) / i > 78643200) {
            return BitmapFactory.decodeFile(str, options).copy(Bitmap.Config.ARGB_4444, false);
        }
        return BitmapFactory.decodeFile(str, options);
    }

    private PointF createBallView() {
        Resources resources;
        int i;
        this.mBallView = new View(getContext());
        float fDp2px = MiuixUIUtils.dp2px(getContext(), 480.0f);
        int i2 = (int) fDp2px;
        addView(this.mBallView, new FrameLayout.LayoutParams(i2, i2));
        if (UIUtils.isNightMode()) {
            resources = getResources();
            i = R.drawable.creation_ai_ball_frame1_dark;
        } else {
            resources = getResources();
            i = R.drawable.creation_ai_ball_frame1_light;
        }
        this.mBallView.setBackground(resources.getDrawable(i));
        return new PointF(fDp2px, fDp2px);
    }

    private void createBackgroundView() {
        this.mBackgroundView = new View(getContext());
        addView(this.mBackgroundView, new FrameLayout.LayoutParams(-1, -1));
        this.mBackgroundView.setBackgroundColor(getContext().getColor(R.color.ai_creation_gen_bg_color));
    }

    public void insertElementToCanvas(final RectF rectF, final RectF rectF2, final String str, final float f, final Action action) {
        removeChildrenIfExist();
        post(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m727x18f736d7(rectF, rectF2, str, action, f);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$insertElementToCanvas$0$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m727x18f736d7(RectF rectF, RectF rectF2, String str, final Action action, float f) {
        float dimension = getResources().getDimension(R.dimen.creation_tools_head_height);
        RectF rectF3 = new RectF(rectF);
        RectF rectF4 = new RectF(rectF2);
        rectF4.offset(0.0f, dimension);
        boolean zIsSmallWindowMode = UIUtils.isSmallWindowMode(getContext());
        float statusBarHeight = UIUtils.getStatusBarHeight(getContext());
        if (zIsSmallWindowMode) {
            rectF4.offset(0.0f, statusBarHeight);
        }
        String str2 = TAG;
        Log.d(str2, "insertElementToCanvas, srcRect: " + rectF3 + ", dstRect: " + rectF4 + ", srcSize: " + rectF3.width() + "," + rectF3.height() + ", dstSize: " + rectF4.width() + "," + rectF4.height() + ", small: " + zIsSmallWindowMode + ", barHeight: " + statusBarHeight);
        float dimension2 = getResources().getDimension(R.dimen.ai_generate_picture_corner_radius);
        Log.d(str2, "insertElementToCanvas, create mask view size: " + createImageMaskView(rectF3, dimension2, str));
        float fWidth = rectF2.width() / rectF.width();
        float fHeight = rectF2.height() / rectF.height();
        this.mSmoothFrameLayout.setPivotX(0.0f);
        this.mSmoothFrameLayout.setPivotY(0.0f);
        Log.d(str2, "insertElementToCanvas, scale factor: " + fWidth + ", " + fHeight);
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(FolmeEase.spring(1.0f, 0.35f));
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.3
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Folme.clean(CanvasElementTransactionMask.this.mSmoothFrameLayout);
                Log.d(CanvasElementTransactionMask.TAG, "onComplete, x: " + CanvasElementTransactionMask.this.mSmoothFrameLayout.getX() + ", y: " + CanvasElementTransactionMask.this.mSmoothFrameLayout.getY() + ", w: " + CanvasElementTransactionMask.this.mSmoothFrameLayout.getWidth() + ", h: " + CanvasElementTransactionMask.this.mSmoothFrameLayout.getHeight());
                action.run();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                Folme.clean(CanvasElementTransactionMask.this.mSmoothFrameLayout);
                action.run();
            }
        });
        Folme.useAt(this.mSmoothFrameLayout).state().to(ViewProperty.X, Float.valueOf(rectF4.left), ViewProperty.Y, Float.valueOf(rectF4.top), ViewProperty.SCALE_X, Float.valueOf(fWidth), ViewProperty.SCALE_Y, Float.valueOf(fHeight), ViewProperty.ROTATION, Float.valueOf(f), ViewProperty.ALPHA, Float.valueOf(1.0f), animConfig);
        Folme.useValue("cornerRadius").setTo(this.mCornerValueProperty, Float.valueOf(dimension2));
        Folme.useValue("cornerRadius").to(this.mCornerValueProperty, Float.valueOf(0.0f), this.mCornerAnimConfig);
    }

    public void displayWithoutAnim(final RectF rectF, final String str) {
        removeChildrenIfExist();
        post(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m724xe5d35a33(rectF, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$displayWithoutAnim$1$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m724xe5d35a33(RectF rectF, String str) {
        getResources().getDimension(R.dimen.creation_tools_head_height);
        Log.d(TAG, "insertElementToCanvas, create mask view size: " + createImageMaskView(new RectF(rectF), getResources().getDimension(R.dimen.ai_generate_picture_corner_radius), str));
        createBackgroundView();
        IStateStyle iStateStyleState = Folme.useAt(this.mBackgroundView).state();
        ViewProperty viewProperty = ViewProperty.SCALE_X;
        Float fValueOf = Float.valueOf(1.0f);
        iStateStyleState.setTo(viewProperty, fValueOf, ViewProperty.SCALE_Y, fValueOf);
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(FolmeEase.spring(1.0f, 0.3f));
        Folme.useAt(this.mBackgroundView).state().to(ViewProperty.SCALE_X, fValueOf, ViewProperty.SCALE_Y, fValueOf, ViewProperty.AUTO_ALPHA, fValueOf, animConfig);
    }

    public void createGhostShareElementView(RectF rectF) {
        this.mGhostView = new RoundedImageView(getContext());
        addView(this.mGhostView, new FrameLayout.LayoutParams((int) rectF.width(), (int) rectF.height()));
        this.mGhostView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mGhostView.setVisibility(8);
        this.mGhostView.setTransitionName(SHARE_ELEMENT_TRANSITION_NAME);
    }

    public View getGhostView() {
        return this.mGhostView;
    }

    public void displayGhostViewForShareElementAnim(final RectF rectF, String str) {
        Log.d(TAG, "displayGhostViewForShareElementAnim, dstRect: " + rectF);
        final Bitmap drawBitmap = getDrawBitmap(str, rectF.width());
        post(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m723x8e732b68(rectF, drawBitmap);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$displayGhostViewForShareElementAnim$2$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m723x8e732b68(RectF rectF, Bitmap bitmap) {
        if (this.mToolbarHeight <= 0.0f) {
            this.mToolbarHeight = getResources().getDimension(R.dimen.creation_tools_head_height);
        }
        RectF rectF2 = new RectF(rectF);
        rectF2.offset(0.0f, this.mToolbarHeight);
        if (this.mGhostView != null) {
            Log.d(TAG, "displayGhostViewForShareElementAnim, start update location of ghost view");
            ViewGroup.LayoutParams layoutParams = this.mGhostView.getLayoutParams();
            layoutParams.width = (int) rectF2.width();
            layoutParams.height = (int) rectF2.height();
            this.mGhostView.setLayoutParams(layoutParams);
            this.mGhostView.setX(rectF2.left);
            this.mGhostView.setY(rectF2.top);
            this.mGhostView.setImageBitmap(bitmap);
            this.mGhostView.setmCornerRadius(0.0f);
            this.mGhostView.setVisibility(0);
        }
    }

    public void extractElementFromCanvas(final RectF rectF, final Boolean bool, final CanvasMaskEntity canvasMaskEntity, final Action action) {
        removeChildrenIfExist();
        post(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m725x100be35b(canvasMaskEntity, rectF, bool, action);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$extractElementFromCanvas$3$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m725x100be35b(CanvasMaskEntity canvasMaskEntity, RectF rectF, Boolean bool, Action action) {
        this.mToolbarHeight = canvasMaskEntity.getToolBarHeight();
        RectF rectF2 = new RectF(rectF);
        float f = this.mToolbarHeight;
        Float fValueOf = Float.valueOf(0.0f);
        rectF2.offset(0.0f, f);
        boolean zIsRTL = DisplayUtils.isRTL();
        if (bool.booleanValue()) {
            createCanvasMaskView(rectF2, canvasMaskEntity, 5.0f, !zIsRTL);
        } else {
            createCanvasMaskView(rectF2, canvasMaskEntity, 2.0f, !zIsRTL);
        }
        createBackgroundView();
        PointF pointFCreateImageMaskView = createImageMaskView(rectF2, 500.0f, 0.0f, canvasMaskEntity, false);
        float screenScale = ScreenModeUtils.getScreenScale(getContext());
        String str = TAG;
        Log.d(str, "extractElementFromCanvas_screenScaleRate: " + screenScale);
        PointF pointFCreateBallView = createBallView();
        createGhostShareElementView(rectF2);
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;
        float f2 = width - (pointFCreateImageMaskView.x / 2.0f);
        float f3 = height - (pointFCreateImageMaskView.y / 2.0f);
        float fCenterX = rectF2.centerX() - (pointFCreateBallView.x / 2.0f);
        float fCenterY = rectF2.centerY() - (pointFCreateBallView.y / 2.0f);
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(FolmeEase.spring(1.0f, 0.3f));
        if (zIsRTL) {
            this.mSmoothFrameLayout.setAlpha(0.0f);
            float windowWidth = UIUtils.getWindowWidth(getContext()) - pointFCreateImageMaskView.x;
            Folme.useAt(this.mSmoothFrameLayout).state().setTo(ViewProperty.X, Float.valueOf((rectF2.left + canvasMaskEntity.getOffsetX()) - windowWidth), ViewProperty.Y, Float.valueOf(rectF2.top + canvasMaskEntity.getOffsetY()), ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.ROTATION, fValueOf).to(ViewProperty.X, Float.valueOf(rectF2.left + canvasMaskEntity.getOffsetX()), getRtlAnimConfig()).then(ViewProperty.X, Float.valueOf(f2), ViewProperty.Y, Float.valueOf(f3), ViewProperty.ALPHA, fValueOf, this.mImgAnimConfig);
            Folme.useValue("cornerRadius").setTo(this.mCornerValueProperty, fValueOf);
            Folme.useValue("cornerRadius").to(this.mCornerValueProperty, Float.valueOf(500.0f), this.mCornerAnimConfig);
            float f4 = screenScale * 0.76f;
            IStateStyle iStateStyle = Folme.useAt(this.mBallView).state().setTo(ViewProperty.X, Float.valueOf(fCenterX - windowWidth), ViewProperty.Y, Float.valueOf(fCenterY), ViewProperty.SCALE_X, Float.valueOf(f4), ViewProperty.SCALE_Y, Float.valueOf(f4), ViewProperty.ALPHA, fValueOf).to(ViewProperty.X, Float.valueOf(fCenterX));
            float f5 = screenScale * 1.0f;
            iStateStyle.then(ViewProperty.X, Float.valueOf(width - (pointFCreateBallView.x / 2.0f)), ViewProperty.Y, Float.valueOf(height - (pointFCreateBallView.y / 2.0f)), ViewProperty.SCALE_X, Float.valueOf(f5), ViewProperty.SCALE_Y, Float.valueOf(f5), ViewProperty.ALPHA, Float.valueOf(1.0f), this.mBallAnimConfig);
            float x = this.mBackgroundView.getX();
            Folme.useAt(this.mBackgroundView).state().setTo(ViewProperty.X, Float.valueOf(x - windowWidth), ViewProperty.SCALE_X, Float.valueOf(BG_START_SCALE), ViewProperty.SCALE_Y, Float.valueOf(BG_START_SCALE), ViewProperty.AUTO_ALPHA, fValueOf).to(ViewProperty.X, Float.valueOf(x)).then(ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.AUTO_ALPHA, Float.valueOf(1.0f), animConfig);
        } else {
            Folme.useAt(this.mSmoothFrameLayout).state().setTo(ViewProperty.X, Float.valueOf(rectF2.left + canvasMaskEntity.getOffsetX()), ViewProperty.Y, Float.valueOf(rectF2.top + canvasMaskEntity.getOffsetY()), ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.ROTATION, fValueOf).to(ViewProperty.X, Float.valueOf(f2), ViewProperty.Y, Float.valueOf(f3), ViewProperty.ALPHA, fValueOf, this.mImgAnimConfig);
            Folme.useValue("cornerRadius").setTo(this.mCornerValueProperty, fValueOf);
            Folme.useValue("cornerRadius").to(this.mCornerValueProperty, Float.valueOf(500.0f), this.mCornerAnimConfig);
            float f6 = screenScale * 0.76f;
            IStateStyle to = Folme.useAt(this.mBallView).state().setTo(ViewProperty.X, Float.valueOf(fCenterX), ViewProperty.Y, Float.valueOf(fCenterY), ViewProperty.SCALE_X, Float.valueOf(f6), ViewProperty.SCALE_Y, Float.valueOf(f6), ViewProperty.ALPHA, fValueOf);
            float f7 = screenScale * 1.0f;
            to.to(ViewProperty.X, Float.valueOf(width - (pointFCreateBallView.x / 2.0f)), ViewProperty.Y, Float.valueOf(height - (pointFCreateBallView.y / 2.0f)), ViewProperty.SCALE_X, Float.valueOf(f7), ViewProperty.SCALE_Y, Float.valueOf(f7), ViewProperty.ALPHA, Float.valueOf(1.0f), this.mBallAnimConfig);
            Folme.useAt(this.mBackgroundView).state().setTo(ViewProperty.SCALE_X, Float.valueOf(BG_START_SCALE), ViewProperty.SCALE_Y, Float.valueOf(BG_START_SCALE), ViewProperty.AUTO_ALPHA, fValueOf).to(ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.AUTO_ALPHA, Float.valueOf(1.0f), animConfig);
        }
        this.mCompleteAction = action;
        Log.d(str, "extractElementFromCanvas, rectF: " + rectF + ", realRect: " + rectF2);
    }

    public void cancelBallAnm() {
        View view = this.mBallView;
        if (view == null || view.getAlpha() <= 0.0f) {
            return;
        }
        Folme.useAt(this.mBallView).state().cancel();
        this.mBallView.setAlpha(0.0f);
    }

    public void scalePreviewImage(final RectF rectF, final RectF rectF2, final CanvasMaskEntity canvasMaskEntity, final float f, final float f2, final float f3, final boolean z, final Action action) {
        removeChildrenIfExist();
        post(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m729xa1bfef62(canvasMaskEntity, rectF, z, f2, f, action, rectF2, f3);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$scalePreviewImage$4$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m729xa1bfef62(CanvasMaskEntity canvasMaskEntity, RectF rectF, boolean z, float f, float f2, final Action action, RectF rectF2, float f3) {
        this.mToolbarHeight = canvasMaskEntity.getToolBarHeight();
        RectF rectF3 = new RectF(rectF);
        Float fValueOf = Float.valueOf(0.0f);
        if (z) {
            rectF3.offset(0.0f, this.mToolbarHeight);
        }
        boolean zIsRTL = DisplayUtils.isRTL();
        createCanvasMaskView(rectF3, canvasMaskEntity, 5.0f, !zIsRTL);
        PointF pointFCreateImageMaskView = createImageMaskView(rectF3, f, f2, canvasMaskEntity, false);
        createGhostShareElementView(rectF3);
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(FolmeEase.spring(0.9f, 0.4f));
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.4
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                Folme.clean(CanvasElementTransactionMask.this.mSmoothFrameLayout);
                Log.d(CanvasElementTransactionMask.TAG, "scalePreview >> onComplete size: " + CanvasElementTransactionMask.this.mSmoothFrameLayout.getWidth() + ", " + CanvasElementTransactionMask.this.mSmoothFrameLayout.getHeight() + ", img size: " + CanvasElementTransactionMask.this.mMaskView.getWidth() + ", " + CanvasElementTransactionMask.this.mMaskView.getHeight());
                action.run();
            }
        });
        if (zIsRTL) {
            this.mSmoothFrameLayout.setAlpha(0.0f);
            Folme.useAt(this.mSmoothFrameLayout).state().setTo(ViewProperty.X, Float.valueOf(rectF3.left - (UIUtils.getWindowWidth(getContext()) - pointFCreateImageMaskView.x)), ViewProperty.Y, Float.valueOf(rectF3.top), ViewProperty.WIDTH, Float.valueOf(pointFCreateImageMaskView.x), ViewProperty.HEIGHT, Float.valueOf(pointFCreateImageMaskView.y)).to(ViewProperty.X, Float.valueOf(rectF3.left), getRtlAnimConfig()).then(ViewProperty.X, Float.valueOf(rectF2.left), ViewProperty.Y, Float.valueOf(rectF2.top), ViewProperty.WIDTH, Float.valueOf(rectF2.width()), ViewProperty.HEIGHT, Float.valueOf(rectF2.height()), ViewProperty.ROTATION, fValueOf, animConfig);
        } else {
            Folme.useAt(this.mSmoothFrameLayout).state().setTo(ViewProperty.X, Float.valueOf(rectF3.left), ViewProperty.Y, Float.valueOf(rectF3.top), ViewProperty.WIDTH, Float.valueOf(pointFCreateImageMaskView.x), ViewProperty.HEIGHT, Float.valueOf(pointFCreateImageMaskView.y)).to(ViewProperty.X, Float.valueOf(rectF2.left), ViewProperty.Y, Float.valueOf(rectF2.top), ViewProperty.WIDTH, Float.valueOf(rectF2.width()), ViewProperty.HEIGHT, Float.valueOf(rectF2.height()), ViewProperty.ROTATION, fValueOf, animConfig);
        }
        Folme.useValue("cornerRadius").setTo(this.mCornerValueProperty, Float.valueOf(f));
        Folme.useValue("cornerRadius").to(this.mCornerValueProperty, Float.valueOf(f3), this.mCornerAnimConfig);
        Log.d(TAG, "scalePreview, srcRect: " + rectF + ", dstRect: " + rectF2 + ", degree: " + f2 + ", cornerStart: " + f);
    }

    private AnimConfig getRtlAnimConfig() {
        AnimConfig animConfig = new AnimConfig();
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.5
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                if (CanvasElementTransactionMask.this.mSmoothFrameLayout != null && CanvasElementTransactionMask.this.mSmoothFrameLayout.getAlpha() == 0.0f) {
                    CanvasElementTransactionMask.this.mSmoothFrameLayout.setAlpha(1.0f);
                }
                if (CanvasElementTransactionMask.this.mCanvasMaskView == null || CanvasElementTransactionMask.this.mCanvasMaskView.getVisibility() != 4) {
                    return;
                }
                CanvasElementTransactionMask.this.mCanvasMaskView.setVisibility(0);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (CanvasElementTransactionMask.this.mSmoothFrameLayout != null && CanvasElementTransactionMask.this.mSmoothFrameLayout.getAlpha() == 0.0f) {
                    CanvasElementTransactionMask.this.mSmoothFrameLayout.setAlpha(1.0f);
                }
                if (CanvasElementTransactionMask.this.mCanvasMaskView == null || CanvasElementTransactionMask.this.mCanvasMaskView.getVisibility() != 4) {
                    return;
                }
                CanvasElementTransactionMask.this.mCanvasMaskView.setVisibility(0);
            }
        });
        return animConfig;
    }

    public void fadeIntoCanvas(final Bitmap bitmap, final RectF rectF, final Action action) {
        removeChildrenIfExist();
        post(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m726x3dee5c54(rectF, bitmap, action);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$fadeIntoCanvas$5$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m726x3dee5c54(RectF rectF, Bitmap bitmap, final Action action) {
        float dimension = getResources().getDimension(R.dimen.creation_tools_head_height);
        RectF rectF2 = new RectF(rectF);
        rectF2.offset(0.0f, dimension);
        Log.d(TAG, "fadeIntoCanvas, create mask view size: " + createImageMaskView(rectF2, 0.0f, 0.0f, bitmap, false));
        AnimConfig animConfig = new AnimConfig();
        Float fValueOf = Float.valueOf(1.0f);
        animConfig.setEase(FolmeEase.spring(1.0f, 0.35f));
        animConfig.setDelay(450L);
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.6
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                action.run();
            }
        });
        IStateStyle iStateStyleState = Folme.useAt(this.mSmoothFrameLayout).state();
        ViewProperty viewProperty = ViewProperty.X;
        Float fValueOf2 = Float.valueOf(rectF2.left);
        ViewProperty viewProperty2 = ViewProperty.Y;
        Float fValueOf3 = Float.valueOf(rectF2.top);
        ViewProperty viewProperty3 = ViewProperty.SCALE_X;
        Float fValueOf4 = Float.valueOf(0.95f);
        iStateStyleState.setTo(viewProperty, fValueOf2, viewProperty2, fValueOf3, viewProperty3, fValueOf4, ViewProperty.SCALE_Y, fValueOf4, ViewProperty.ALPHA, Float.valueOf(0.0f));
        Folme.useAt(this.mSmoothFrameLayout).state().to(ViewProperty.SCALE_X, fValueOf, ViewProperty.SCALE_Y, fValueOf, ViewProperty.ALPHA, fValueOf, animConfig);
    }

    public void removeChildrenIfExist() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
    }

    public void release() {
        postDelayed(new Runnable() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m728xe2afaece();
            }
        }, 800L);
    }

    /* JADX INFO: renamed from: lambda$release$6$com-miui-handwriting-common-mask-CanvasElementTransactionMask, reason: not valid java name */
    /* synthetic */ void m728xe2afaece() {
        SmoothFrameLayout2 smoothFrameLayout2 = this.mSmoothFrameLayout;
        if (smoothFrameLayout2 != null) {
            Folme.clean(smoothFrameLayout2);
            removeView(this.mSmoothFrameLayout);
            this.mSmoothFrameLayout = null;
            this.mMaskView = null;
        }
        View view = this.mBallView;
        if (view != null) {
            Folme.clean(view);
            removeView(this.mBallView);
            this.mBallView = null;
        }
    }

    public void releaseCanvasMaskView() {
        ImageView imageView = this.mCanvasMaskView;
        if (imageView != null) {
            removeView(imageView);
            this.mCanvasMaskView = null;
        }
    }

    public void clearBackgroundWithAnim() {
        if (this.mBackgroundView == null) {
            return;
        }
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(FolmeEase.spring(1.0f, 0.3f));
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.mask.CanvasElementTransactionMask.7
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                CanvasElementTransactionMask.this.removeBackground();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                CanvasElementTransactionMask.this.removeBackground();
            }
        });
        Folme.useAt(this.mBackgroundView).state().to(animConfig, ViewProperty.ALPHA, Float.valueOf(0.0f));
    }

    public void removeBackground() {
        View view = this.mBackgroundView;
        if (view != null) {
            Folme.clean(view);
            removeView(this.mBackgroundView);
            this.mBackgroundView = null;
        }
    }
}
