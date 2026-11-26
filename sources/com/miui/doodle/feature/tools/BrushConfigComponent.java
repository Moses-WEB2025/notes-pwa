package com.miui.doodle.feature.tools;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.doodle.R;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.RomUtils;
import com.miui.doodle.utils.UIUtils;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: compiled from: BrushConfigComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001c\u0018\u00002\u00020\u0001:\u0002MNB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0003H\u0002J\b\u0010.\u001a\u00020,H\u0002J\b\u0010/\u001a\u00020,H\u0002J\b\u00100\u001a\u00020,H\u0002J\u0010\u00101\u001a\u00020,2\u0006\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u00020,H\u0002J\b\u00105\u001a\u00020,H\u0002J\b\u00106\u001a\u00020,H\u0002J\b\u00107\u001a\u00020,H\u0002J\u0010\u00108\u001a\u00020,2\u0006\u00109\u001a\u00020\u0003H\u0002J\u0018\u0010\u001b\u001a\u00020,2\u0006\u00109\u001a\u00020\u00032\u0006\u0010:\u001a\u00020\u001dH\u0002J\u0018\u0010;\u001a\u00020,2\u0006\u00109\u001a\u00020\u00032\u0006\u0010:\u001a\u00020\u001dH\u0002J\b\u0010<\u001a\u00020,H\u0016J\b\u0010=\u001a\u00020,H\u0016J \u0010>\u001a\u00020,2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010I\u001a\u00020,2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002J\b\u0010J\u001a\u00020,H\u0002J\b\u0010K\u001a\u00020,H\u0002J\b\u0010L\u001a\u00020,H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0017X\u0082.¢\u0006\u0004\n\u0002\u0010\u0018R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0003X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001d@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u000e\u0010*\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR$\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR$\u0010\b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010D\"\u0004\bH\u0010F¨\u0006O"}, d2 = {"Lcom/miui/doodle/feature/tools/BrushConfigComponent;", "Lcom/miui/doodle/feature/tools/IBrushConfig;", "container", "Landroid/view/View;", "alpha", "", "colorInt", "", "strokeLevel", "configListener", "Lcom/miui/doodle/feature/tools/BrushConfigListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;FIILcom/miui/doodle/feature/tools/BrushConfigListener;)V", "alphaContainer", "strokeContainer", "alphaDetailsContainer", "alphaThumb", "alphaBg", "Landroid/widget/ImageView;", "alphaSample", "alphaValue", "Landroid/widget/TextView;", "strokeItems", "", "[Landroid/view/View;", "strokeBgAnimator", "Landroid/view/ViewPropertyAnimator;", "selectStrokeItem", "value", "", "isShow", "()Z", "thumbMinTranX", "thumbMaxTranX", "thumbTouchRect", "Landroid/graphics/Rect;", "buttonTalkback", "", "getButtonTalkback", "()Ljava/lang/String;", "setButtonTalkback", "(Ljava/lang/String;)V", "unSelectedBackgroundColor", "initView", "", "rootView", "initAlphaView", "initAlphaConfigParams", "updateAlphaThumbView", "updateAlphaBgView", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "initStrokeView", "updateStrokeView", "initAlphaDetailsView", "updateAlphaDetailsView", "onStrokeItemClick", "strokeItem", "needAnim", "unSelectStrokeItem", "show", "dismiss", "updateParams", "getAlpha", "()F", "setAlpha", "(F)V", "getColorInt", "()I", "setColorInt", "(I)V", "getStrokeLevel", "setStrokeLevel", "showAlphaDetail", "announceAlphaValue", "setupAlphaNodeInfo", "clearViewAnimator", "DoodlePencilSelectedAccessibilityDelegate", "DoodlePencilUnselectedAccessibilityDelegate", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BrushConfigComponent implements IBrushConfig {
    private float alpha;
    private ImageView alphaBg;
    private View alphaContainer;
    private View alphaDetailsContainer;
    private View alphaSample;
    private View alphaThumb;
    private TextView alphaValue;
    private String buttonTalkback;
    private int colorInt;
    private final BrushConfigListener configListener;
    private final View container;
    private boolean isShow;
    private View selectStrokeItem;
    private ViewPropertyAnimator strokeBgAnimator;
    private View strokeContainer;
    private View[] strokeItems;
    private int strokeLevel;
    private float thumbMaxTranX;
    private float thumbMinTranX;
    private final Rect thumbTouchRect;
    private final int unSelectedBackgroundColor;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initView$lambda$1(View view) {
    }

    public BrushConfigComponent(View container, float f, int i, int i2, BrushConfigListener configListener) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(configListener, "configListener");
        this.container = container;
        this.configListener = configListener;
        this.thumbTouchRect = new Rect();
        String string = container.getContext().getString(R.string.button_talkback);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.buttonTalkback = string;
        this.unSelectedBackgroundColor = container.getContext().getResources().getColor(R.color.doodle_brush_config_un_select_color);
        this.alpha = f;
        this.colorInt = i;
        this.strokeLevel = i2;
        initView(container);
    }

    public /* synthetic */ BrushConfigComponent(View view, float f, int i, int i2, BrushConfigListener brushConfigListener, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, (i3 & 2) != 0 ? 1.0f : f, (i3 & 4) != 0 ? -16777216 : i, (i3 & 8) != 0 ? 1 : i2, brushConfigListener);
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    /* JADX INFO: renamed from: isShow, reason: from getter */
    public boolean getIsShow() {
        return this.isShow;
    }

    public final String getButtonTalkback() {
        return this.buttonTalkback;
    }

    public final void setButtonTalkback(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.buttonTalkback = str;
    }

    private final void initView(View rootView) {
        View viewFindViewById = rootView.findViewById(R.id.alpha_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.alphaContainer = viewFindViewById;
        View view = null;
        if (viewFindViewById == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            viewFindViewById = null;
        }
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BrushConfigComponent.initView$lambda$0(view2);
            }
        });
        View viewFindViewById2 = rootView.findViewById(R.id.stroke_width_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.strokeContainer = viewFindViewById2;
        if (viewFindViewById2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            viewFindViewById2 = null;
        }
        viewFindViewById2.setImportantForAccessibility(2);
        View view2 = this.strokeContainer;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
        } else {
            view = view2;
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                BrushConfigComponent.initView$lambda$1(view3);
            }
        });
        View viewFindViewById3 = rootView.findViewById(R.id.alpha_details_container);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.alphaDetailsContainer = viewFindViewById3;
        initAlphaView();
        initStrokeView();
        initAlphaDetailsView();
    }

    private final void initAlphaView() {
        View view = this.alphaContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view = null;
        }
        View viewFindViewById = view.findViewById(R.id.alpha_thumb);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.alphaThumb = viewFindViewById;
        if (viewFindViewById == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaThumb");
            viewFindViewById = null;
        }
        viewFindViewById.setClickable(false);
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{getColorInt(), 16777215 & getColorInt()});
        gradientDrawable.setCornerRadius(120.0f);
        Drawable drawable = ContextCompat.getDrawable(this.container.getContext(), R.drawable.doodle_brush_config_alpha_bg);
        Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
        LayerDrawable layerDrawable = drawableMutate instanceof LayerDrawable ? (LayerDrawable) drawableMutate : null;
        if (layerDrawable != null) {
            layerDrawable.addLayer(gradientDrawable);
        }
        View view3 = this.alphaContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view3 = null;
        }
        View viewFindViewById2 = view3.findViewById(R.id.alpha_bg);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        ImageView imageView = (ImageView) viewFindViewById2;
        this.alphaBg = imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
            imageView = null;
        }
        imageView.setImageDrawable(layerDrawable);
        View view4 = this.alphaContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view4 = null;
        }
        view4.setFocusable(true);
        View view5 = this.alphaContainer;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view5 = null;
        }
        view5.setFocusableInTouchMode(true);
        View view6 = this.alphaContainer;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view6 = null;
        }
        view6.setClickable(true);
        View view7 = this.alphaContainer;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
        } else {
            view2 = view7;
        }
        view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent.initAlphaView.1
            private float mDownTranX;
            private float mDownX;
            private float mDownY;

            public final float getMDownX() {
                return this.mDownX;
            }

            public final void setMDownX(float f) {
                this.mDownX = f;
            }

            public final float getMDownY() {
                return this.mDownY;
            }

            public final void setMDownY(float f) {
                this.mDownY = f;
            }

            public final float getMDownTranX() {
                return this.mDownTranX;
            }

            public final void setMDownTranX(float f) {
                this.mDownTranX = f;
            }

            /* JADX WARN: Code duplicated, block: B:17:0x004c  */
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(event, "event");
                if (!BrushConfigComponent.this.thumbTouchRect.contains((int) event.getX(), (int) event.getY())) {
                    BrushConfigComponent.this.showAlphaDetail(false);
                    return false;
                }
                int action = event.getAction();
                View view8 = null;
                if (action == 0) {
                    this.mDownX = event.getX();
                    this.mDownY = event.getY();
                    View view9 = BrushConfigComponent.this.alphaThumb;
                    if (view9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("alphaThumb");
                    } else {
                        view8 = view9;
                    }
                    this.mDownTranX = view8.getTranslationX();
                    BrushConfigComponent.this.showAlphaDetail(true);
                } else if (action == 1) {
                    BrushConfigComponent.this.announceAlphaValue();
                    BrushConfigComponent.this.showAlphaDetail(false);
                } else if (action == 2) {
                    float x = event.getX() - this.mDownX;
                    View view10 = BrushConfigComponent.this.alphaThumb;
                    if (view10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("alphaThumb");
                    } else {
                        view8 = view10;
                    }
                    moveThumb(view8, x);
                } else if (action == 3) {
                    BrushConfigComponent.this.announceAlphaValue();
                    BrushConfigComponent.this.showAlphaDetail(false);
                }
                return true;
            }

            private final void moveThumb(View view8, float moveX) {
                float f = this.mDownTranX + moveX;
                if (f > BrushConfigComponent.this.thumbMaxTranX) {
                    f = BrushConfigComponent.this.thumbMaxTranX;
                }
                if (f < BrushConfigComponent.this.thumbMinTranX) {
                    f = BrushConfigComponent.this.thumbMinTranX;
                }
                Intrinsics.checkNotNull(view8);
                view8.setTranslationX(f);
                float f2 = ((int) (((f - BrushConfigComponent.this.thumbMinTranX) / (BrushConfigComponent.this.thumbMaxTranX - BrushConfigComponent.this.thumbMinTranX)) * 100)) / 100.0f;
                Logger.INSTANCE.i("BrushConfig", "setAlpha " + f2 + " moveX = " + moveX + " mDownTranX = " + this.mDownTranX);
                BrushConfigComponent.this.setAlpha(f2);
            }
        });
    }

    private final void initAlphaConfigParams() {
        ImageView imageView = this.alphaBg;
        ImageView imageView2 = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
            imageView = null;
        }
        imageView.getHitRect(this.thumbTouchRect);
        ImageView imageView3 = this.alphaBg;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
            imageView3 = null;
        }
        imageView3.setClickable(false);
        this.thumbTouchRect.top -= 30;
        this.thumbTouchRect.bottom += 30;
        Rect rect = this.thumbTouchRect;
        int i = rect.right;
        View view = this.alphaThumb;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaThumb");
            view = null;
        }
        rect.right = i + view.getWidth();
        this.thumbTouchRect.left = 0;
        this.thumbMaxTranX = 0.0f;
        View view2 = this.alphaThumb;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaThumb");
            view2 = null;
        }
        float width = view2.getWidth();
        ImageView imageView4 = this.alphaBg;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
        } else {
            imageView2 = imageView4;
        }
        this.thumbMinTranX = width - imageView2.getWidth();
        updateAlphaThumbView();
    }

    private final void updateAlphaThumbView() {
        float alpha = ((this.thumbMaxTranX - this.thumbMinTranX) * getAlpha()) + this.thumbMinTranX;
        View view = this.alphaThumb;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaThumb");
            view = null;
        }
        view.setTranslationX(alpha);
    }

    private final void updateAlphaBgView(Context context) {
        View view = this.alphaSample;
        ImageView imageView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSample");
            view = null;
        }
        view.setBackgroundColor(getColorInt());
        View view2 = this.alphaSample;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSample");
            view2 = null;
        }
        view2.setAlpha(getAlpha());
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{getColorInt(), 16777215 & getColorInt()});
        gradientDrawable.setCornerRadius(120.0f);
        if (RomUtils.INSTANCE.isFoldDevice()) {
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.doodle_brush_config_alpha_bg_full);
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            Intrinsics.checkNotNull(drawableMutate, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
            LayerDrawable layerDrawable = (LayerDrawable) drawableMutate;
            layerDrawable.addLayer(gradientDrawable);
            ImageView imageView2 = this.alphaBg;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
            } else {
                imageView = imageView2;
            }
            imageView.setImageDrawable(layerDrawable);
            return;
        }
        Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.doodle_brush_config_alpha_bg);
        Drawable drawableMutate2 = drawable2 != null ? drawable2.mutate() : null;
        Intrinsics.checkNotNull(drawableMutate2, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        LayerDrawable layerDrawable2 = (LayerDrawable) drawableMutate2;
        layerDrawable2.addLayer(gradientDrawable);
        ImageView imageView3 = this.alphaBg;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaBg");
        } else {
            imageView = imageView3;
        }
        imageView.setImageDrawable(layerDrawable2);
    }

    private final void initStrokeView() {
        View[] viewArr = new View[5];
        View view = this.strokeContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view = null;
        }
        View viewFindViewById = view.findViewById(R.id.item1);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        viewArr[0] = viewFindViewById;
        View view2 = this.strokeContainer;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view2 = null;
        }
        View viewFindViewById2 = view2.findViewById(R.id.item2);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        viewArr[1] = viewFindViewById2;
        View view3 = this.strokeContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view3 = null;
        }
        View viewFindViewById3 = view3.findViewById(R.id.item3);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        viewArr[2] = viewFindViewById3;
        View view4 = this.strokeContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view4 = null;
        }
        View viewFindViewById4 = view4.findViewById(R.id.item4);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        viewArr[3] = viewFindViewById4;
        View view5 = this.strokeContainer;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view5 = null;
        }
        View viewFindViewById5 = view5.findViewById(R.id.item5);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        viewArr[4] = viewFindViewById5;
        this.strokeItems = viewArr;
        int length = viewArr.length;
        for (int i = 0; i < length; i++) {
            View[] viewArr2 = this.strokeItems;
            if (viewArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokeItems");
                viewArr2 = null;
            }
            View view6 = viewArr2[i];
            View viewFindViewById6 = view6.findViewById(R.id.brush_stroke_item_content);
            viewFindViewById6.setClipToOutline(true);
            viewFindViewById6.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent.initStrokeView.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view7, Outline outline) {
                    Intrinsics.checkNotNullParameter(view7, "view");
                    Intrinsics.checkNotNullParameter(outline, "outline");
                    outline.setOval(new Rect(0, 0, view7.getWidth(), view7.getHeight()));
                }
            });
            viewFindViewById6.setBackgroundColor(this.unSelectedBackgroundColor);
            view6.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view7) {
                    BrushConfigComponent.initStrokeView$lambda$2(this.f$0, view7);
                }
            });
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view6.createAccessibilityNodeInfo();
            DoodlePencilSelectedAccessibilityDelegate doodlePencilSelectedAccessibilityDelegate = new DoodlePencilSelectedAccessibilityDelegate(this.buttonTalkback);
            DoodlePencilUnselectedAccessibilityDelegate doodlePencilUnselectedAccessibilityDelegate = new DoodlePencilUnselectedAccessibilityDelegate(this.buttonTalkback);
            if (i == getStrokeLevel() - 1) {
                Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                doodlePencilSelectedAccessibilityDelegate.onInitializeAccessibilityNodeInfo(view6, accessibilityNodeInfoCreateAccessibilityNodeInfo);
                view6.setAccessibilityDelegate(doodlePencilSelectedAccessibilityDelegate);
                this.selectStrokeItem = view6;
                selectStrokeItem(view6, false);
            } else {
                Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                doodlePencilUnselectedAccessibilityDelegate.onInitializeAccessibilityNodeInfo(view6, accessibilityNodeInfoCreateAccessibilityNodeInfo);
                view6.setAccessibilityDelegate(doodlePencilUnselectedAccessibilityDelegate);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initStrokeView$lambda$2(BrushConfigComponent brushConfigComponent, View view) {
        Intrinsics.checkNotNull(view);
        brushConfigComponent.onStrokeItemClick(view);
    }

    private final void updateStrokeView() {
        View[] viewArr = this.strokeItems;
        View view = null;
        if (viewArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeItems");
            viewArr = null;
        }
        int length = viewArr.length;
        for (int i = 0; i < length; i++) {
            View[] viewArr2 = this.strokeItems;
            if (viewArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokeItems");
                viewArr2 = null;
            }
            View view2 = viewArr2[i];
            if (i == getStrokeLevel() - 1) {
                View view3 = this.selectStrokeItem;
                if (view3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("selectStrokeItem");
                    view3 = null;
                }
                if (view3 != view2) {
                    View view4 = this.selectStrokeItem;
                    if (view4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("selectStrokeItem");
                        view4 = null;
                    }
                    unSelectStrokeItem(view4, false);
                    this.selectStrokeItem = view2;
                }
            }
        }
        View view5 = this.selectStrokeItem;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectStrokeItem");
        } else {
            view = view5;
        }
        selectStrokeItem(view, false);
    }

    private final void initAlphaDetailsView() {
        View view = this.alphaDetailsContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaDetailsContainer");
            view = null;
        }
        View viewFindViewById = view.findViewById(R.id.alpha_details_sample);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.alphaSample = viewFindViewById;
        if (viewFindViewById == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSample");
            viewFindViewById = null;
        }
        viewFindViewById.setClipToOutline(true);
        View view3 = this.alphaSample;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSample");
            view3 = null;
        }
        view3.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent.initAlphaDetailsView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view4, Outline outline) {
                Intrinsics.checkNotNullParameter(view4, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setOval(new Rect(0, 0, view4.getWidth(), view4.getHeight()));
            }
        });
        View view4 = this.alphaDetailsContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaDetailsContainer");
        } else {
            view2 = view4;
        }
        View viewFindViewById2 = view2.findViewById(R.id.alpha_details_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.alphaValue = (TextView) viewFindViewById2;
    }

    private final void updateAlphaDetailsView() {
        View view = this.alphaSample;
        TextView textView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSample");
            view = null;
        }
        view.setBackgroundColor(getColorInt());
        View view2 = this.alphaSample;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaSample");
            view2 = null;
        }
        view2.setAlpha(getAlpha());
        String str = DrawUtil.obtainLocalInteger((int) (getAlpha() * 100)) + "%";
        TextView textView2 = this.alphaValue;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaValue");
            textView2 = null;
        }
        textView2.setText(str);
        TextView textView3 = this.alphaValue;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaValue");
        } else {
            textView = textView3;
        }
        Object systemService = textView.getContext().getSystemService("accessibility");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.accessibility.AccessibilityManager");
    }

    private final void onStrokeItemClick(View strokeItem) {
        View view = this.selectStrokeItem;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectStrokeItem");
            view = null;
        }
        int i = 1;
        unSelectStrokeItem(view, true);
        this.selectStrokeItem = strokeItem;
        if (strokeItem == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectStrokeItem");
            strokeItem = null;
        }
        selectStrokeItem(strokeItem, true);
        View view3 = this.selectStrokeItem;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectStrokeItem");
        } else {
            view2 = view3;
        }
        int id = view2.getId();
        if (id != R.id.item1) {
            if (id == R.id.item2) {
                i = 2;
            } else if (id == R.id.item3) {
                i = 3;
            } else if (id == R.id.item4) {
                i = 4;
            } else if (id == R.id.item5) {
                i = 5;
            }
        }
        setStrokeLevel(i);
    }

    private final void selectStrokeItem(View strokeItem, boolean needAnim) {
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = strokeItem.createAccessibilityNodeInfo();
        DoodlePencilSelectedAccessibilityDelegate doodlePencilSelectedAccessibilityDelegate = new DoodlePencilSelectedAccessibilityDelegate(this.buttonTalkback);
        Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
        doodlePencilSelectedAccessibilityDelegate.onInitializeAccessibilityNodeInfo(strokeItem, accessibilityNodeInfoCreateAccessibilityNodeInfo);
        strokeItem.setAccessibilityDelegate(doodlePencilSelectedAccessibilityDelegate);
        View viewFindViewById = strokeItem.findViewById(R.id.brush_stroke_item_select_bg);
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context context = viewFindViewById.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        viewFindViewById.setElevation(uIUtils.dip2px(context, 1.0f));
        if (needAnim) {
            ViewPropertyAnimator viewPropertyAnimatorAnimate = viewFindViewById.animate();
            this.strokeBgAnimator = viewPropertyAnimatorAnimate;
            if (viewPropertyAnimatorAnimate != null) {
                viewPropertyAnimatorAnimate.scaleY(1.3f).scaleX(1.3f).setInterpolator(new EaseManager.SpringInterpolator()).setDuration(200L).start();
            }
        } else {
            viewFindViewById.setScaleX(1.3f);
            viewFindViewById.setScaleY(1.3f);
        }
        View viewFindViewById2 = strokeItem.findViewById(R.id.brush_stroke_item_content);
        UIUtils uIUtils2 = UIUtils.INSTANCE;
        Context context2 = viewFindViewById.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        viewFindViewById2.setElevation(uIUtils2.dip2px(context2, 2.0f));
        viewFindViewById2.setBackgroundColor(getColorInt());
    }

    private final void unSelectStrokeItem(View strokeItem, boolean needAnim) {
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = strokeItem.createAccessibilityNodeInfo();
        DoodlePencilUnselectedAccessibilityDelegate doodlePencilUnselectedAccessibilityDelegate = new DoodlePencilUnselectedAccessibilityDelegate(this.buttonTalkback);
        Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
        doodlePencilUnselectedAccessibilityDelegate.onInitializeAccessibilityNodeInfo(strokeItem, accessibilityNodeInfoCreateAccessibilityNodeInfo);
        strokeItem.setAccessibilityDelegate(doodlePencilUnselectedAccessibilityDelegate);
        View viewFindViewById = strokeItem.findViewById(R.id.brush_stroke_item_select_bg);
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context context = viewFindViewById.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        viewFindViewById.setElevation(uIUtils.dip2px(context, 0.0f));
        if (needAnim) {
            ViewPropertyAnimator viewPropertyAnimatorAnimate = viewFindViewById.animate();
            this.strokeBgAnimator = viewPropertyAnimatorAnimate;
            if (viewPropertyAnimatorAnimate != null) {
                viewPropertyAnimatorAnimate.scaleY(1.0f).scaleX(1.0f).setInterpolator(new EaseManager.SpringInterpolator()).setDuration(200L).start();
            }
        } else {
            viewFindViewById.setScaleX(1.0f);
            viewFindViewById.setScaleY(1.0f);
        }
        View viewFindViewById2 = strokeItem.findViewById(R.id.brush_stroke_item_content);
        UIUtils uIUtils2 = UIUtils.INSTANCE;
        Context context2 = viewFindViewById.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        viewFindViewById2.setElevation(uIUtils2.dip2px(context2, 0.0f));
        viewFindViewById2.setBackgroundColor(this.unSelectedBackgroundColor);
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void show() {
        this.isShow = true;
        View view = this.alphaContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view = null;
        }
        view.setAlpha(0.0f);
        View view3 = this.strokeContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view3 = null;
        }
        view3.setAlpha(0.0f);
        View view4 = this.alphaDetailsContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaDetailsContainer");
        } else {
            view2 = view4;
        }
        view2.setAlpha(0.0f);
        this.container.setVisibility(0);
        this.container.post(new Runnable() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BrushConfigComponent.show$lambda$6(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void show$lambda$6(final BrushConfigComponent brushConfigComponent) {
        brushConfigComponent.initAlphaConfigParams();
        View view = brushConfigComponent.alphaContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view = null;
        }
        view.setTranslationY(20.0f);
        View view3 = brushConfigComponent.alphaContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view3 = null;
        }
        view3.setAlpha(0.0f);
        View[] viewArr = new View[1];
        View view4 = brushConfigComponent.alphaContainer;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view4 = null;
        }
        viewArr[0] = view4;
        Folme.useAt(viewArr).state().to(ViewProperty.TRANSLATION_Y, 0).to(ViewProperty.ALPHA, Float.valueOf(1.0f));
        View view5 = brushConfigComponent.strokeContainer;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view5 = null;
        }
        view5.setTranslationY(20.0f);
        View view6 = brushConfigComponent.strokeContainer;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view6 = null;
        }
        view6.setAlpha(0.0f);
        View[] viewArr2 = new View[1];
        View view7 = brushConfigComponent.strokeContainer;
        if (view7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
            view7 = null;
        }
        viewArr2[0] = view7;
        Folme.useAt(viewArr2).state().to(ViewProperty.TRANSLATION_Y, 0).to(ViewProperty.ALPHA, Float.valueOf(1.0f));
        View view8 = brushConfigComponent.alphaContainer;
        if (view8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
        } else {
            view2 = view8;
        }
        view2.postDelayed(new Runnable() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                BrushConfigComponent.show$lambda$6$lambda$5(this.f$0);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void show$lambda$6$lambda$5(BrushConfigComponent brushConfigComponent) {
        View view = brushConfigComponent.alphaContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view = null;
        }
        view.requestFocus();
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void dismiss() {
        this.isShow = false;
        View[] viewArr = new View[1];
        View view = this.alphaContainer;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view = null;
        }
        viewArr[0] = view;
        Folme.useAt(viewArr).state().to(ViewProperty.TRANSLATION_Y, 20).to(ViewProperty.ALPHA, Float.valueOf(0.0f));
        View[] viewArr2 = new View[1];
        View view3 = this.strokeContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokeContainer");
        } else {
            view2 = view3;
        }
        viewArr2[0] = view2;
        Folme.useAt(viewArr2).state().to(ViewProperty.TRANSLATION_Y, 20).to(ViewProperty.ALPHA, Float.valueOf(0.0f));
        showAlphaDetail(false);
        this.container.postDelayed(new Runnable() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrushConfigComponent.dismiss$lambda$7(this.f$0);
            }
        }, 210L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void dismiss$lambda$7(BrushConfigComponent brushConfigComponent) {
        brushConfigComponent.container.setVisibility(8);
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void updateParams(int colorInt, float alpha, int strokeLevel) {
        setColorInt(colorInt);
        setAlpha(alpha);
        setStrokeLevel(strokeLevel);
        updateAlphaThumbView();
        Context context = this.container.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        updateAlphaBgView(context);
        updateStrokeView();
        updateAlphaDetailsView();
        setupAlphaNodeInfo();
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public float getAlpha() {
        return this.alpha;
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void setAlpha(float f) {
        this.alpha = f;
        updateAlphaDetailsView();
        this.configListener.onSetAlpha(f);
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public int getColorInt() {
        return this.colorInt;
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void setColorInt(int i) {
        this.colorInt = i;
        Context context = this.container.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        updateAlphaBgView(context);
        updateStrokeView();
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public int getStrokeLevel() {
        return this.strokeLevel;
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void setStrokeLevel(int i) {
        this.strokeLevel = i;
        updateStrokeView();
        this.configListener.onSetStrokeLevel(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showAlphaDetail(boolean isShow) {
        View view = null;
        if (isShow) {
            View[] viewArr = new View[1];
            View view2 = this.alphaDetailsContainer;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alphaDetailsContainer");
            } else {
                view = view2;
            }
            viewArr[0] = view;
            Folme.useAt(viewArr).state().to(ViewProperty.TRANSLATION_Y, 0).to(ViewProperty.ALPHA, Float.valueOf(1.0f));
            return;
        }
        View[] viewArr2 = new View[1];
        View view3 = this.alphaDetailsContainer;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaDetailsContainer");
        } else {
            view = view3;
        }
        viewArr2[0] = view;
        Folme.useAt(viewArr2).state().to(ViewProperty.TRANSLATION_Y, 20).to(ViewProperty.ALPHA, Float.valueOf(0.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void announceAlphaValue() {
        Object systemService = this.container.getContext().getSystemService("accessibility");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.accessibility.AccessibilityManager");
        if (((AccessibilityManager) systemService).isEnabled()) {
            int alpha = (int) (getAlpha() * 100);
            String str = this.container.getContext().getString(R.string.doodle_brush_config_alpha) + "，" + DrawUtil.obtainLocalInteger(alpha) + "%，" + this.container.getContext().getString(R.string.slider_talkback);
            View view = this.alphaContainer;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
                view = null;
            }
            view.announceForAccessibility(str);
        }
    }

    private final void setupAlphaNodeInfo() {
        View view = this.alphaContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alphaContainer");
            view = null;
        }
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.doodle.feature.tools.BrushConfigComponent.setupAlphaNodeInfo.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                Intrinsics.checkNotNullParameter(host, "host");
                Intrinsics.checkNotNullParameter(info, "info");
                super.onInitializeAccessibilityNodeInfo(host, info);
                int alpha = (int) (BrushConfigComponent.this.getAlpha() * 100);
                BrushConfigComponent brushConfigComponent = BrushConfigComponent.this;
                StringBuilder sb = new StringBuilder();
                sb.append(brushConfigComponent.container.getContext().getString(R.string.doodle_brush_config_alpha));
                sb.append("，");
                sb.append(DrawUtil.obtainLocalInteger(alpha) + "%");
                sb.append("，");
                sb.append(brushConfigComponent.container.getContext().getString(R.string.slider_talkback));
                String string = sb.toString();
                info.setContentDescription(string);
                info.setText(string);
            }
        });
    }

    @Override // com.miui.doodle.feature.tools.IBrushConfig
    public void clearViewAnimator() {
        ViewPropertyAnimator viewPropertyAnimator = this.strokeBgAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    /* JADX INFO: compiled from: BrushConfigComponent.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/miui/doodle/feature/tools/BrushConfigComponent$DoodlePencilSelectedAccessibilityDelegate;", "Landroid/view/View$AccessibilityDelegate;", "buttonTalkback", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "mbuttonTalkback", "getMbuttonTalkback", "()Ljava/lang/String;", "onInitializeAccessibilityNodeInfo", "", "host", "Landroid/view/View;", Constants.JSON_FILTER_INFO, "Landroid/view/accessibility/AccessibilityNodeInfo;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class DoodlePencilSelectedAccessibilityDelegate extends View.AccessibilityDelegate {
        private final String mbuttonTalkback;

        public final String getMbuttonTalkback() {
            return this.mbuttonTalkback;
        }

        public DoodlePencilSelectedAccessibilityDelegate(String buttonTalkback) {
            Intrinsics.checkNotNullParameter(buttonTalkback, "buttonTalkback");
            this.mbuttonTalkback = buttonTalkback;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            Intrinsics.checkNotNullParameter(host, "host");
            Intrinsics.checkNotNullParameter(info, "info");
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setSelected(true);
            info.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", this.mbuttonTalkback);
        }
    }

    /* JADX INFO: compiled from: BrushConfigComponent.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/miui/doodle/feature/tools/BrushConfigComponent$DoodlePencilUnselectedAccessibilityDelegate;", "Landroid/view/View$AccessibilityDelegate;", "buttonTalkback", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "mbuttonTalkback", "getMbuttonTalkback", "()Ljava/lang/String;", "onInitializeAccessibilityNodeInfo", "", "host", "Landroid/view/View;", Constants.JSON_FILTER_INFO, "Landroid/view/accessibility/AccessibilityNodeInfo;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class DoodlePencilUnselectedAccessibilityDelegate extends View.AccessibilityDelegate {
        private final String mbuttonTalkback;

        public final String getMbuttonTalkback() {
            return this.mbuttonTalkback;
        }

        public DoodlePencilUnselectedAccessibilityDelegate(String buttonTalkback) {
            Intrinsics.checkNotNullParameter(buttonTalkback, "buttonTalkback");
            this.mbuttonTalkback = buttonTalkback;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            Intrinsics.checkNotNullParameter(host, "host");
            Intrinsics.checkNotNullParameter(info, "info");
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setSelected(false);
            info.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", this.mbuttonTalkback);
        }
    }
}
