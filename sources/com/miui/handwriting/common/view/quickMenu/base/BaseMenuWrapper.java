package com.miui.handwriting.common.view.quickMenu.base;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.utils.PenMenuRes;
import com.miui.handwriting.common.view.quickMenu.utils.ScreenModeHelper;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: compiled from: BaseMenuWrapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\u0018\u001a\u00020\u00192\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u001bJ\u0018\u0010\u001c\u001a\u00020\u00192\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u001bJ\u0006\u0010\u001d\u001a\u00020\u0019J\u0006\u0010\u001e\u001a\u00020\u0019J\u0018\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0013H\u0004J\b\u0010#\u001a\u00020\u0019H\u0014J\b\u0010$\u001a\u00020\u0019H\u0014J\b\u0010%\u001a\u00020\u0019H\u0014J\b\u0010&\u001a\u00020\u0019H\u0014J\b\u0010'\u001a\u00020\u0019H&R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006("}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/BaseMenuWrapper;", "Landroid/widget/FrameLayout;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "anchor", "Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;)V", "getAnchor", "()Lcom/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu;", "menuRes", "Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "getMenuRes", "()Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "mAnimState", "Lmiuix/animation/IStateStyle;", "mScreenModeHelper", "Lcom/miui/handwriting/common/view/quickMenu/utils/ScreenModeHelper;", "scaleRatio", "", "mCenterTrackOvalRect", "Landroid/graphics/RectF;", "getMCenterTrackOvalRect", "()Landroid/graphics/RectF;", "executeOpenAnimation", "", "onCompleted", "Lkotlin/Function0;", "executeDismissAnimation", "destory", "cancelAnimation", "setShadow", "shadowPath", "Landroid/graphics/Path;", "elevationHeight", "onOpenAnimationStart", "onOpenAnimationEnd", "onDismissAnimationStart", "onDismissAnimationEnd", "notifyMenuOpened", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseMenuWrapper extends FrameLayout {
    private final StylusPenQuickMenu anchor;
    private IStateStyle mAnimState;
    private final RectF mCenterTrackOvalRect;
    private final ScreenModeHelper mScreenModeHelper;
    private final PenMenuRes menuRes;
    private float scaleRatio;

    public abstract void notifyMenuOpened();

    protected void onDismissAnimationEnd() {
    }

    protected void onDismissAnimationStart() {
    }

    protected void onOpenAnimationEnd() {
    }

    protected void onOpenAnimationStart() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.anchor = stylusPenQuickMenu;
        PenMenuRes penMenuRes = new PenMenuRes(context);
        this.menuRes = penMenuRes;
        ScreenModeHelper screenModeHelper = new ScreenModeHelper(context);
        this.mScreenModeHelper = screenModeHelper;
        this.scaleRatio = screenModeHelper.transformScale(screenModeHelper.getQuickMenuScale());
        RectF rectF = new RectF();
        this.mCenterTrackOvalRect = rectF;
        float outerCircleRadius = penMenuRes.getOuterCircleRadius();
        float innerCircleRadius = penMenuRes.getInnerCircleRadius();
        float f = 2;
        float f2 = outerCircleRadius * f;
        rectF.set(new RectF(0.0f, 0.0f, f2, f2));
        float f3 = (outerCircleRadius - innerCircleRadius) / f;
        rectF.inset(f3, f3);
    }

    public /* synthetic */ BaseMenuWrapper(Context context, StylusPenQuickMenu stylusPenQuickMenu, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : stylusPenQuickMenu);
    }

    protected final StylusPenQuickMenu getAnchor() {
        return this.anchor;
    }

    protected final PenMenuRes getMenuRes() {
        return this.menuRes;
    }

    protected final RectF getMCenterTrackOvalRect() {
        return this.mCenterTrackOvalRect;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void executeOpenAnimation$default(BaseMenuWrapper baseMenuWrapper, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: executeOpenAnimation");
        }
        if ((i & 1) != 0) {
            function0 = null;
        }
        baseMenuWrapper.executeOpenAnimation(function0);
    }

    public final void executeOpenAnimation(final Function0<Unit> onCompleted) {
        setScaleX(0.5f);
        setScaleY(0.5f);
        setAlpha(0.0f);
        AnimConfig animConfig = new AnimConfig();
        animConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(0.75f, 0.39f), new float[0]);
        animConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(0.75f, 0.39f), new float[0]);
        animConfig.setSpecial(ViewProperty.ALPHA, FolmeEase.sinOut(300L), new float[0]);
        animConfig.setEase(FolmeEase.spring(0.75f, 0.39f));
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper.executeOpenAnimation.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object toTag) {
                Function0<Unit> function0 = onCompleted;
                if (function0 != null) {
                    function0.invoke();
                }
                this.onOpenAnimationEnd();
            }
        });
        if (this.mAnimState == null) {
            this.mAnimState = Folme.use((View) this).state();
        }
        onOpenAnimationStart();
        IStateStyle iStateStyle = this.mAnimState;
        if (iStateStyle != null) {
            iStateStyle.to(ViewProperty.SCALE_X, Float.valueOf(1.0f), ViewProperty.SCALE_Y, Float.valueOf(1.0f), ViewProperty.ALPHA, Float.valueOf(1.0f), animConfig);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void executeDismissAnimation$default(BaseMenuWrapper baseMenuWrapper, Function0 function0, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: executeDismissAnimation");
        }
        if ((i & 1) != 0) {
            function0 = null;
        }
        baseMenuWrapper.executeDismissAnimation(function0);
    }

    public final void executeDismissAnimation(final Function0<Unit> onCompleted) {
        AnimConfig animConfig = new AnimConfig();
        animConfig.setSpecial(ViewProperty.SCALE_X, FolmeEase.spring(0.75f, 0.39f), new float[0]);
        animConfig.setSpecial(ViewProperty.SCALE_Y, FolmeEase.spring(0.75f, 0.39f), new float[0]);
        animConfig.setSpecial(ViewProperty.ALPHA, FolmeEase.sinOut(150L), new float[0]);
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper.executeDismissAnimation.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object toTag) {
                Function0<Unit> function0 = onCompleted;
                if (function0 != null) {
                    function0.invoke();
                }
                this.onDismissAnimationEnd();
            }
        });
        onDismissAnimationStart();
        IStateStyle iStateStyle = this.mAnimState;
        if (iStateStyle != null) {
            iStateStyle.to(ViewProperty.SCALE_X, Float.valueOf(0.5f), ViewProperty.SCALE_Y, Float.valueOf(0.5f), ViewProperty.ALPHA, Float.valueOf(0.0f), animConfig);
        }
    }

    public final void destory() {
        cancelAnimation();
        this.mAnimState = null;
    }

    public final void cancelAnimation() {
        IStateStyle iStateStyle = this.mAnimState;
        if (iStateStyle != null) {
            iStateStyle.cancel();
        }
        IStateStyle iStateStyle2 = this.mAnimState;
        if (iStateStyle2 != null) {
            iStateStyle2.clean();
        }
    }

    protected final void setShadow(final Path shadowPath, float elevationHeight) {
        Intrinsics.checkNotNullParameter(shadowPath, "shadowPath");
        setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper.setShadow.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setAlpha(BaseMenuWrapper.this.scaleRatio * 0.7f);
                outline.setPath(shadowPath);
            }
        });
        setOutlineSpotShadowColor(this.menuRes.getMenuShadowColor());
        setElevation(elevationHeight * (this.scaleRatio - 0.2f));
    }
}
