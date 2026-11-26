package com.miui.libhandwritingpreview.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.TimeUtils;
import com.miui.handwriting.common.view.page.StateView;
import com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.TitleColorChangeListener;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.model.MiuiHandViewPreviewModel;
import com.miui.libhandwritingpreview.system.PreCamera;
import com.miui.libhandwritingpreview.system.PreExportSystem;
import com.miui.maml.folme.AnimatedProperty;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CreationPaintPreviewLayout.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B/\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\b\u0010*\u001a\u00020+H\u0002J\u000e\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020.J\u0010\u0010/\u001a\u00020+2\u0006\u00100\u001a\u000201H\u0014J\u0010\u00102\u001a\u00020!2\u0006\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020!2\u0006\u00106\u001a\u000204H\u0014J.\u00107\u001a\u00020+2\u0006\u00108\u001a\u00020\t2\u0006\u00109\u001a\u00020\t2\u0006\u0010-\u001a\u00020.2\u0006\u0010:\u001a\u00020.2\u0006\u0010;\u001a\u00020.J\u0018\u0010<\u001a\u00020+2\b\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010?\u001a\u00020\u001fJ\u000e\u0010@\u001a\u00020+2\u0006\u0010?\u001a\u00020\u001fJ\u0010\u0010A\u001a\u00020+2\u0006\u0010B\u001a\u00020\tH\u0016J\u0016\u0010C\u001a\u00020+2\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\t0EH\u0016J\u0018\u0010F\u001a\u00020+2\u0006\u0010G\u001a\u00020\t2\u0006\u0010H\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006I"}, d2 = {"Lcom/miui/libhandwritingpreview/view/CreationPaintPreviewLayout;", "Landroid/widget/FrameLayout;", "Lcom/miui/handwriting/common/view/thumbnail/ThumbnailRecyclerManager$ThumbnailListener;", "Lcom/miui/libhandwritingpage/listener/TitleColorChangeListener;", "mContext", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "mPageInkViewModel", "Lcom/miui/libhandwritingpreview/model/MiuiHandViewPreviewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/util/AttributeSet;ILcom/miui/libhandwritingpreview/model/MiuiHandViewPreviewModel;)V", "pageInkView", "Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", "getPageInkView", "()Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", "mTitleView", "Landroid/view/View;", "touchStartPos1", "Landroid/graphics/PointF;", "mKeyListener", "Landroid/text/method/KeyListener;", "titleView", "Landroid/widget/EditText;", "getTitleView", "()Landroid/widget/EditText;", "mTitleTime", "Landroid/widget/TextView;", "mCreationTime", "", "isTitleFocus", "", "mStateView", "Lcom/miui/libhandwritingpage/listener/PageStateView;", "thumbnailManager", "Lcom/miui/handwriting/common/view/thumbnail/ThumbnailRecyclerManager;", "getThumbnailManager", "()Lcom/miui/handwriting/common/view/thumbnail/ThumbnailRecyclerManager;", "setThumbnailManager", "(Lcom/miui/handwriting/common/view/thumbnail/ThumbnailRecyclerManager;)V", "initView", "", "updateBackgroundMargin", "scale", "", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "dispatchTouchEvent", "ev", "Landroid/view/MotionEvent;", "dispatchHoverEvent", "event", "update", "index", "count", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "setTitleAndTime", "title", "", "time", "setTitleTime", "onItemClick", "position", "onImageLoad", "list", "", "onTitleColorChange", "colorIndex", "modeColorIndex", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CreationPaintPreviewLayout extends FrameLayout implements ThumbnailRecyclerManager.ThumbnailListener, TitleColorChangeListener {
    private boolean isTitleFocus;
    private final Context mContext;
    private long mCreationTime;
    private final KeyListener mKeyListener;
    private final MiuiHandViewPreviewModel mPageInkViewModel;
    private final PageStateView mStateView;
    private final TextView mTitleTime;
    private final View mTitleView;
    private final CreationShieldPrePageInkView pageInkView;
    private ThumbnailRecyclerManager thumbnailManager;
    private final EditText titleView;
    private final PointF touchStartPos1;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreationPaintPreviewLayout(Context mContext, AttributeSet attributeSet, MiuiHandViewPreviewModel mPageInkViewModel) {
        this(mContext, attributeSet, 0, mPageInkViewModel, 4, null);
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mPageInkViewModel, "mPageInkViewModel");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreationPaintPreviewLayout(Context mContext, MiuiHandViewPreviewModel mPageInkViewModel) {
        this(mContext, null, 0, mPageInkViewModel, 6, null);
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mPageInkViewModel, "mPageInkViewModel");
    }

    public final void updateBackgroundMargin(float scale) {
    }

    public /* synthetic */ CreationPaintPreviewLayout(Context context, AttributeSet attributeSet, int i, MiuiHandViewPreviewModel miuiHandViewPreviewModel, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i, miuiHandViewPreviewModel);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreationPaintPreviewLayout(Context mContext, AttributeSet attributeSet, int i, MiuiHandViewPreviewModel mPageInkViewModel) {
        super(mContext, attributeSet, i);
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mPageInkViewModel, "mPageInkViewModel");
        this.mContext = mContext;
        this.mPageInkViewModel = mPageInkViewModel;
        this.touchStartPos1 = new PointF();
        LayoutInflater.from(mContext).inflate(R.layout.creation_paint_layout, this);
        CreationShieldPrePageInkView creationShieldPrePageInkView = new CreationShieldPrePageInkView(mContext);
        this.pageInkView = creationShieldPrePageInkView;
        mPageInkViewModel.init(creationShieldPrePageInkView.getPageInkFunc());
        creationShieldPrePageInkView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.creation_paint_container);
        frameLayout.addView(creationShieldPrePageInkView);
        View viewFindViewById = findViewById(R.id.title);
        this.mTitleView = viewFindViewById;
        EditText editText = (EditText) findViewById(R.id.creation_title);
        this.titleView = editText;
        View viewFindViewById2 = findViewById(R.id.creation_title_time);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mTitleTime = (TextView) viewFindViewById2;
        KeyListener keyListener = editText.getKeyListener();
        Intrinsics.checkNotNullExpressionValue(keyListener, "getKeyListener(...)");
        this.mKeyListener = keyListener;
        creationShieldPrePageInkView.setHeadView(viewFindViewById);
        StateView stateView = new StateView(mContext);
        this.mStateView = stateView;
        mPageInkViewModel.setStateView(stateView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 80;
        frameLayout.addView(stateView.getView(), layoutParams);
        initView();
        mPageInkViewModel.setTitleChangeListener(this);
    }

    public final CreationShieldPrePageInkView getPageInkView() {
        return this.pageInkView;
    }

    public final EditText getTitleView() {
        return this.titleView;
    }

    public final ThumbnailRecyclerManager getThumbnailManager() {
        return this.thumbnailManager;
    }

    public final void setThumbnailManager(ThumbnailRecyclerManager thumbnailRecyclerManager) {
        this.thumbnailManager = thumbnailRecyclerManager;
    }

    private final void initView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        layoutParams.bottomMargin = -getResources().getDimensionPixelSize(R.dimen.creation_footer_view_height_min);
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.libhandwritingpreview.view.CreationPaintPreviewLayout$$ExternalSyntheticLambda1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return CreationPaintPreviewLayout.initView$lambda$0(this.f$0, view, windowInsets);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final WindowInsets initView$lambda$0(CreationPaintPreviewLayout creationPaintPreviewLayout, View v, WindowInsets insets) {
        PagePreDrawView mDrawView;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCamera camera;
        PreICreationEngine creationEngine2;
        PreMiCurveEngine curveEngine2;
        PreCamera camera2;
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(insets, "insets");
        Insets insets2 = insets.getInsets(WindowInsets.Type.ime());
        Intrinsics.checkNotNullExpressionValue(insets2, "getInsets(...)");
        int i = insets2.bottom;
        if (!insets.isVisible(8)) {
            creationPaintPreviewLayout.isTitleFocus = false;
            PagePreDrawView mDrawView2 = creationPaintPreviewLayout.pageInkView.getPageInkFunc().getMDrawView();
            if (mDrawView2 != null && (creationEngine2 = mDrawView2.getCreationEngine()) != null && (curveEngine2 = creationEngine2.getCurveEngine()) != null && (camera2 = curveEngine2.getCamera()) != null) {
                camera2.setTitleFocus(false);
                return insets;
            }
        } else if (i > 0 && (mDrawView = creationPaintPreviewLayout.pageInkView.getPageInkFunc().getMDrawView()) != null && (creationEngine = mDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (camera = curveEngine.getCamera()) != null) {
            camera.setTitleFocus(true);
        }
        return insets;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        PreMiCurveEngine curveEngine;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        EditText editText = this.titleView;
        Intrinsics.checkNotNull(editText);
        editText.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_text));
        this.titleView.setHintTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_hint));
        this.mTitleTime.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_time_color));
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.creation_action_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.titleView.setCompoundDrawablesRelative(drawable, null, null, null);
        this.mTitleTime.setText(TimeUtils.formatTimestamp(this.mCreationTime, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
        if (this.mPageInkViewModel.getCurrentItemView() != null) {
            PagePreItemView currentItemView = this.mPageInkViewModel.getCurrentItemView();
            Intrinsics.checkNotNull(currentItemView);
            PreICreationEngine creationEngine = currentItemView.getCreationEngine();
            if (creationEngine == null || (curveEngine = creationEngine.getCurveEngine()) == null) {
                return;
            }
            curveEngine.onConfigurationChanged(newConfig);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ThumbnailRecyclerManager thumbnailRecyclerManager;
        View view;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCamera camera;
        ThumbnailRecyclerManager thumbnailRecyclerManager2;
        View view2;
        ThumbnailRecyclerManager thumbnailRecyclerManager3;
        View view3;
        Intrinsics.checkNotNullParameter(ev, "ev");
        int pointerCount = ev.getPointerCount();
        int action = ev.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.isTitleFocus && (view3 = this.mTitleView) != null && view3.getVisibility() == 0) {
                        EditText editText = this.titleView;
                        Intrinsics.checkNotNull(editText);
                        return editText.dispatchTouchEvent(ev);
                    }
                    if (pointerCount == 1 && (thumbnailRecyclerManager3 = this.thumbnailManager) != null) {
                        Intrinsics.checkNotNull(thumbnailRecyclerManager3);
                        if (thumbnailRecyclerManager3.getShow()) {
                            return super.dispatchTouchEvent(ev);
                        }
                    }
                    return this.pageInkView.dispatchTouchEvent(ev);
                }
                return this.pageInkView.dispatchTouchEvent(ev);
            }
            if (this.isTitleFocus && (view2 = this.mTitleView) != null && view2.getVisibility() == 0) {
                EditText editText2 = this.titleView;
                Intrinsics.checkNotNull(editText2);
                if (!editText2.hasFocus() && RomUtils.isPadDevice()) {
                    this.titleView.requestFocus();
                }
                return this.titleView.dispatchTouchEvent(ev);
            }
            this.isTitleFocus = false;
            EditText editText3 = this.titleView;
            if (editText3 != null) {
                editText3.clearFocus();
                this.titleView.setKeyListener(null);
                this.titleView.setEllipsize(TextUtils.TruncateAt.END);
                this.titleView.setFocusable(false);
                this.titleView.setFocusableInTouchMode(false);
                this.titleView.setClickable(false);
            }
            if (pointerCount == 1 && (thumbnailRecyclerManager2 = this.thumbnailManager) != null) {
                Intrinsics.checkNotNull(thumbnailRecyclerManager2);
                if (thumbnailRecyclerManager2.getShow()) {
                    return super.dispatchTouchEvent(ev);
                }
            }
            this.pageInkView.dispatchTouchEvent(ev);
            return true;
        }
        if (pointerCount == 1 && (view = this.mTitleView) != null && view.getVisibility() == 0) {
            Rect rect = new Rect();
            EditText editText4 = this.titleView;
            Intrinsics.checkNotNull(editText4);
            if (editText4.getLocalVisibleRect(rect)) {
                if (rect.contains((int) ev.getX(), (int) ev.getY()) && RomUtils.isPadDevice()) {
                    this.isTitleFocus = true;
                    this.titleView.setFocusable(true);
                    this.titleView.setFocusableInTouchMode(true);
                    this.titleView.setClickable(true);
                    if (!this.titleView.hasFocus()) {
                        this.titleView.requestFocus();
                    }
                    this.titleView.setKeyListener(this.mKeyListener);
                    this.titleView.setEllipsize(null);
                    PagePreDrawView mDrawView = this.pageInkView.getPageInkFunc().getMDrawView();
                    if (mDrawView != null && (creationEngine = mDrawView.getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && (camera = curveEngine.getCamera()) != null) {
                        camera.setTitleFocus(this.isTitleFocus);
                    }
                    return this.titleView.dispatchTouchEvent(ev);
                }
                this.titleView.clearFocus();
                this.titleView.setKeyListener(null);
                this.titleView.setEllipsize(TextUtils.TruncateAt.END);
                this.isTitleFocus = false;
                this.titleView.setFocusable(false);
                this.titleView.setFocusableInTouchMode(false);
                this.titleView.setClickable(false);
            }
        }
        View view4 = this.mTitleView;
        if (view4 != null && view4.getVisibility() != 0) {
            this.isTitleFocus = false;
            EditText editText5 = this.titleView;
            if (editText5 != null) {
                editText5.clearFocus();
                this.titleView.setKeyListener(null);
                this.titleView.setEllipsize(TextUtils.TruncateAt.END);
                this.titleView.setFocusable(false);
                this.titleView.setFocusableInTouchMode(false);
                this.titleView.setClickable(false);
            }
        }
        if (pointerCount == 1 && (thumbnailRecyclerManager = this.thumbnailManager) != null) {
            Intrinsics.checkNotNull(thumbnailRecyclerManager);
            if (thumbnailRecyclerManager.getShow()) {
                return super.dispatchTouchEvent(ev);
            }
        }
        return this.pageInkView.dispatchTouchEvent(ev);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int pointerCount = event.getPointerCount();
        int action = event.getAction() & 255;
        if (action != 9) {
            if (action == 10) {
                if (this.isTitleFocus) {
                    EditText editText = this.titleView;
                    Intrinsics.checkNotNull(editText);
                    if (!editText.hasFocus()) {
                        this.titleView.requestFocus();
                        EditText editText2 = this.titleView;
                        editText2.setSelection(editText2.getEditableText().length());
                    }
                }
                if (pointerCount == 1 && this.mTitleView != null) {
                    this.touchStartPos1.set(event.getX(), event.getY());
                    int[] iArr = {0, 0};
                    EditText editText3 = this.titleView;
                    Intrinsics.checkNotNull(editText3);
                    editText3.getLocationInWindow(iArr);
                    int[] iArr2 = {0, 0};
                    this.pageInkView.getLocationInWindow(iArr2);
                    int[] iArr3 = {0, 0};
                    this.pageInkView.getRootView().getLocationInWindow(iArr3);
                    int i = iArr[0];
                    RectF rectF = new RectF(i, iArr[1] - (iArr2[1] - iArr3[1]), i + this.titleView.getWidth(), (iArr[1] + this.titleView.getHeight()) - (iArr2[1] - iArr3[1]));
                    if (this.touchStartPos1.x > rectF.left && this.touchStartPos1.x < rectF.right && this.touchStartPos1.y > rectF.top && this.touchStartPos1.y < rectF.bottom) {
                        this.isTitleFocus = true;
                        if (!this.titleView.hasFocus()) {
                            this.titleView.requestFocus();
                            EditText editText4 = this.titleView;
                            editText4.setSelection(editText4.getEditableText().length());
                        }
                        this.titleView.setKeyListener(this.mKeyListener);
                        this.titleView.setEllipsize(null);
                    } else {
                        this.titleView.setKeyListener(null);
                        this.titleView.setEllipsize(TextUtils.TruncateAt.END);
                        this.isTitleFocus = false;
                    }
                }
            }
        } else if (pointerCount == 1 && this.mTitleView != null) {
            this.touchStartPos1.set(event.getX(), event.getY());
            int[] iArr4 = {0, 0};
            EditText editText5 = this.titleView;
            Intrinsics.checkNotNull(editText5);
            editText5.getLocationInWindow(iArr4);
            int[] iArr5 = {0, 0};
            this.pageInkView.getLocationInWindow(iArr5);
            int[] iArr6 = {0, 0};
            this.pageInkView.getRootView().getLocationInWindow(iArr6);
            int i2 = iArr4[0];
            RectF rectF2 = new RectF(i2, iArr4[1] - (iArr5[1] - iArr6[1]), i2 + this.titleView.getWidth(), (iArr4[1] + this.titleView.getHeight()) - (iArr5[1] - iArr6[1]));
            if (this.touchStartPos1.x > rectF2.left && this.touchStartPos1.x < rectF2.right && this.touchStartPos1.y > rectF2.top && this.touchStartPos1.y < rectF2.bottom) {
                this.isTitleFocus = true;
                if (!this.titleView.hasFocus()) {
                    this.titleView.requestFocus();
                    EditText editText6 = this.titleView;
                    editText6.setSelection(editText6.getEditableText().length());
                }
                this.titleView.setKeyListener(this.mKeyListener);
                this.titleView.setEllipsize(null);
            } else {
                this.titleView.setKeyListener(null);
                this.titleView.setEllipsize(TextUtils.TruncateAt.END);
                this.isTitleFocus = false;
            }
        }
        return super.dispatchHoverEvent(event);
    }

    public final void update(int index, int count, float scale, float x, float y) {
        this.mStateView.update(index, count, scale, x, y);
    }

    public final void setTitleAndTime(String title, long time) {
        EditText editText = this.titleView;
        Intrinsics.checkNotNull(editText);
        editText.setText(title);
        this.mTitleTime.setText(TimeUtils.formatTimestamp(time, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
        this.mCreationTime = time;
    }

    public final void setTitleTime(long time) {
        this.mTitleTime.setText(TimeUtils.formatTimestamp(time, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
        this.mCreationTime = time;
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager.ThumbnailListener
    public void onItemClick(int position) {
        this.mPageInkViewModel.jumpPage(position, false);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager.ThumbnailListener
    public void onImageLoad(final List<Integer> list) {
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreExportSystem exportSystem;
        Intrinsics.checkNotNullParameter(list, "list");
        PagePreItemView currentItemView = this.mPageInkViewModel.getCurrentItemView();
        if (currentItemView == null || (creationEngine = currentItemView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (exportSystem = curveEngine.getExportSystem()) == null) {
            return;
        }
        exportSystem.getPageThumbnailCustomList(list, 300, new Function1() { // from class: com.miui.libhandwritingpreview.view.CreationPaintPreviewLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CreationPaintPreviewLayout.onImageLoad$lambda$1(this.f$0, list, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onImageLoad$lambda$1(CreationPaintPreviewLayout creationPaintPreviewLayout, List list, List list2) {
        ThumbnailRecyclerManager thumbnailRecyclerManager = creationPaintPreviewLayout.thumbnailManager;
        if (thumbnailRecyclerManager != null) {
            Intrinsics.checkNotNull(thumbnailRecyclerManager);
            thumbnailRecyclerManager.setBitmapList(list, list2);
        }
        return Unit.INSTANCE;
    }

    @Override // com.miui.libhandwritingpage.listener.TitleColorChangeListener
    public void onTitleColorChange(int colorIndex, int modeColorIndex) {
        int color = Color.parseColor("#99000000");
        int color2 = Color.parseColor("#99FFFFFF");
        if (colorIndex == 0 && modeColorIndex == -1) {
            EditText editText = this.titleView;
            Intrinsics.checkNotNull(editText);
            editText.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (colorIndex == 1 && modeColorIndex == -1) {
            EditText editText2 = this.titleView;
            Intrinsics.checkNotNull(editText2);
            editText2.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (colorIndex == 2 && modeColorIndex == -1) {
            EditText editText3 = this.titleView;
            Intrinsics.checkNotNull(editText3);
            editText3.setTextColor(color2);
            this.mTitleTime.setTextColor(color2);
            return;
        }
        if ((colorIndex == 0 || colorIndex == 1) && modeColorIndex == 4) {
            EditText editText4 = this.titleView;
            Intrinsics.checkNotNull(editText4);
            editText4.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (colorIndex == 2 && modeColorIndex == 4) {
            EditText editText5 = this.titleView;
            Intrinsics.checkNotNull(editText5);
            editText5.setTextColor(color2);
            this.mTitleTime.setTextColor(color2);
            return;
        }
        if ((colorIndex == 0 || colorIndex == 1) && modeColorIndex == 5) {
            EditText editText6 = this.titleView;
            Intrinsics.checkNotNull(editText6);
            editText6.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (colorIndex == 2 && modeColorIndex == 5) {
            EditText editText7 = this.titleView;
            Intrinsics.checkNotNull(editText7);
            editText7.setTextColor(color2);
            this.mTitleTime.setTextColor(color2);
        }
    }
}
