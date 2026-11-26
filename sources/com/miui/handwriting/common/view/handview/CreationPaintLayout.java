package com.miui.handwriting.common.view.handview;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
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
import com.miui.creation.common.R;
import com.miui.handwriting.common.latex.LatexPopupWindow;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.tools.TimeUtils;
import com.miui.handwriting.common.view.page.FooterView;
import com.miui.handwriting.common.view.page.StateView;
import com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager;
import com.miui.libhandwritingpage.listener.PageChangeListener;
import com.miui.libhandwritingpage.listener.PageFooterView;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.TitleColorChangeListener;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import xc.engine.api.CreationPageSDK;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.utils.TouchEventUtils;

/* JADX INFO: loaded from: classes3.dex */
public class CreationPaintLayout extends FrameLayout implements ThumbnailRecyclerManager.ThumbnailListener, PageChangeListener, TitleColorChangeListener {
    private boolean isTitleFocus;
    private final Context mContext;
    private long mCreationTime;
    private PageFooterView mFooterView;
    private KeyListener mKeyListener;
    private LatexPopupWindow mLatexPopupWindow;
    private final Runnable mLatexPopupWindowDismissRunnable;
    private final IMiuiHandWritingModel mPageInkViewModel;
    private CreationShieldPageInkView mShieldPageInkView;
    private PointF mSmartFormulaDownPointF;
    private MathSystem.FormulaLatex mSmartFormulaLatex;
    private PageStateView mStateView;
    private ThumbnailRecyclerManager mThumbnailRecyclerManager;
    private EditText mTitle;
    private TextView mTitleTime;
    private View mTitleView;
    private PointF touchStartPos1;

    public void updateBackgroundMargin(float f) {
    }

    public CreationPaintLayout(Context context) {
        this(context, null);
    }

    public CreationPaintLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, null);
    }

    public CreationPaintLayout(Context context, AttributeSet attributeSet, int i, IMiuiHandWritingModel iMiuiHandWritingModel) {
        super(context, attributeSet, i);
        this.touchStartPos1 = new PointF();
        this.mSmartFormulaLatex = null;
        this.mSmartFormulaDownPointF = null;
        this.mLatexPopupWindowDismissRunnable = new Runnable() { // from class: com.miui.handwriting.common.view.handview.CreationPaintLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (CreationPaintLayout.this.mLatexPopupWindow != null) {
                    CreationPaintLayout.this.mLatexPopupWindow.dismiss();
                }
            }
        };
        this.mContext = context;
        this.mPageInkViewModel = iMiuiHandWritingModel;
        LayoutInflater.from(context).inflate(R.layout.creation_paint_layout, this);
        CreationShieldPageInkView creationShieldPageInkView = new CreationShieldPageInkView(context);
        this.mShieldPageInkView = creationShieldPageInkView;
        iMiuiHandWritingModel.init(creationShieldPageInkView.getPageInkFunc());
        this.mShieldPageInkView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.creation_paint_container);
        frameLayout.addView(this.mShieldPageInkView);
        this.mTitleView = findViewById(R.id.title);
        this.mTitle = (EditText) findViewById(R.id.creation_title);
        this.mTitleTime = (TextView) findViewById(R.id.creation_title_time);
        this.mKeyListener = this.mTitle.getKeyListener();
        this.mShieldPageInkView.setHeadView(this.mTitleView);
        StateView stateView = new StateView(context);
        this.mStateView = stateView;
        iMiuiHandWritingModel.setStateView(stateView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 80;
        frameLayout.addView(this.mStateView.getView(), layoutParams);
        initView(iMiuiHandWritingModel);
        iMiuiHandWritingModel.setTitleChangeListener(this);
        initLatexPopupWindow();
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.miui.handwriting.common.view.handview.CreationPaintLayout.2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                int i2 = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
                if (!windowInsets.isVisible(8) && i2 <= 0) {
                    CreationPaintLayout.this.isTitleFocus = false;
                    CreationPaintLayout.this.getPageInkView().setTitleFocus(CreationPaintLayout.this.isTitleFocus);
                }
                return windowInsets;
            }
        });
    }

    private void initLatexPopupWindow() {
        LatexPopupWindow latexPopupWindow = new LatexPopupWindow(this.mContext);
        this.mLatexPopupWindow = latexPopupWindow;
        latexPopupWindow.setAnchorView(this);
    }

    private void initView(IMiuiHandWritingModel iMiuiHandWritingModel) {
        this.mFooterView = new FooterView(this.mContext, iMiuiHandWritingModel);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        layoutParams.bottomMargin = -getResources().getDimensionPixelSize(R.dimen.creation_footer_view_height_min);
        addView(this.mFooterView.getFooterView(), layoutParams);
        this.mShieldPageInkView.setPageFootView(this.mFooterView);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFooterView.onConfigurationChanged();
        if (!this.mPageInkViewModel.isUnlimited()) {
            this.mTitle.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_text));
            this.mTitle.setHintTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_hint));
            this.mTitleTime.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_time_color));
        }
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.creation_action_edit);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mTitle.setCompoundDrawablesRelative(drawable, null, null, null);
        this.mTitleTime.setText(TimeUtils.formatTimestamp(this.mCreationTime, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
        if (this.mPageInkViewModel.getCurrentItemView() != null) {
            this.mPageInkViewModel.getCurrentItemView().getCreationEngine().getCurveEngine().onConfigurationChanged(configuration);
        }
        dismissLatexPopupWindow();
    }

    public CreationShieldPageInkView getPageInkView() {
        return this.mShieldPageInkView;
    }

    public void dismissLatexPopupWindow() {
        CreationPageSDK.with(this.mContext).removeCallbacks(this.mLatexPopupWindowDismissRunnable);
        this.mLatexPopupWindow.dismiss();
        this.mSmartFormulaDownPointF = null;
    }

    private void handleSmartFormulaLatexOnUp(MotionEvent motionEvent) {
        MiCurveEngine miCurveEngine = this.mPageInkViewModel.getMiCurveEngine();
        if (this.mSmartFormulaDownPointF == null || miCurveEngine == null) {
            return;
        }
        boolean zIsClickEventWithIgnoreTimeInterval = TouchEventUtils.isClickEventWithIgnoreTimeInterval(this.mSmartFormulaDownPointF, miCurveEngine.getWorldSpacePointFromScreenSpacePoint(motionEvent.getX(), motionEvent.getY()));
        if (this.mSmartFormulaLatex == null || !zIsClickEventWithIgnoreTimeInterval) {
            return;
        }
        this.mPageInkViewModel.onCancelEvent();
        RectF resultRectF = this.mSmartFormulaLatex.getResultRectF();
        int iWidth = (int) (resultRectF.left + (resultRectF.width() / 2.0f));
        this.mLatexPopupWindow.showAtLocation(0, new Point(iWidth, ((int) resultRectF.top) + getTop()), new Point(iWidth, ((int) resultRectF.bottom) + getTop()), new Rect(getLeft() + 20, getTop() + 20, getRight() - 20, getBottom() - 20), this.mSmartFormulaLatex.getLatexString());
        this.mSmartFormulaLatex = null;
        this.mSmartFormulaDownPointF = null;
        CreationPageSDK.with(this.mContext).postDelayed(this.mLatexPopupWindowDismissRunnable, 10000L);
    }

    public MathSystem.FormulaLatex getLatexByPoint(float f, float f2) {
        MiCurveEngine miCurveEngine = this.mPageInkViewModel.getMiCurveEngine();
        if (miCurveEngine == null) {
            return null;
        }
        this.mSmartFormulaDownPointF = miCurveEngine.getWorldSpacePointFromScreenSpacePoint(f, f2);
        return miCurveEngine.getMathSystem().findLatexByPoint(this.mSmartFormulaDownPointF);
    }

    private void handleSmartFormulaLatexOnDown(MotionEvent motionEvent) {
        if (this.mLatexPopupWindow.isShowing()) {
            dismissLatexPopupWindow();
            this.mPageInkViewModel.onCancelEvent();
            return;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        MiCurveEngine miCurveEngine = this.mPageInkViewModel.getMiCurveEngine();
        if (miCurveEngine != null) {
            this.mSmartFormulaDownPointF = miCurveEngine.getWorldSpacePointFromScreenSpacePoint(x, y);
            MathSystem.FormulaLatex formulaLatexFindLatexByPoint = miCurveEngine.getMathSystem().findLatexByPoint(this.mSmartFormulaDownPointF);
            if (formulaLatexFindLatexByPoint != null) {
                this.mSmartFormulaLatex = new MathSystem.FormulaLatex(formulaLatexFindLatexByPoint.getLatexString().replaceFirst("(=|\\\\simeq).*", "=").replaceAll("dfrac", "frac"), miCurveEngine.getScreenSpaceRectFromWorldSpaceRect(formulaLatexFindLatexByPoint.getResultRectF()));
            } else {
                this.mSmartFormulaLatex = null;
                this.mSmartFormulaDownPointF = null;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        ThumbnailRecyclerManager thumbnailRecyclerManager;
        View view;
        ThumbnailRecyclerManager thumbnailRecyclerManager2;
        View view2;
        ThumbnailRecyclerManager thumbnailRecyclerManager3;
        View view3;
        int pointerCount = motionEvent.getPointerCount();
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.isTitleFocus && (view3 = this.mTitleView) != null && view3.getVisibility() == 0) {
                        return this.mTitle.dispatchTouchEvent(motionEvent);
                    }
                    if (pointerCount == 1 && (thumbnailRecyclerManager3 = this.mThumbnailRecyclerManager) != null && thumbnailRecyclerManager3.getShow()) {
                        return super.dispatchTouchEvent(motionEvent);
                    }
                    return this.mShieldPageInkView.dispatchTouchEvent(motionEvent);
                }
                return this.mShieldPageInkView.dispatchTouchEvent(motionEvent);
            }
            if (this.isTitleFocus && (view2 = this.mTitleView) != null && view2.getVisibility() == 0) {
                if (!this.mTitle.hasFocus()) {
                    this.mTitle.requestFocus();
                }
                return this.mTitle.dispatchTouchEvent(motionEvent);
            }
            this.isTitleFocus = false;
            EditText editText = this.mTitle;
            if (editText != null) {
                editText.clearFocus();
                this.mTitle.setKeyListener(null);
                this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
                this.mTitle.setFocusable(false);
                this.mTitle.setFocusableInTouchMode(false);
                this.mTitle.setClickable(false);
            }
            if (pointerCount == 1 && (thumbnailRecyclerManager2 = this.mThumbnailRecyclerManager) != null && thumbnailRecyclerManager2.getShow()) {
                return super.dispatchTouchEvent(motionEvent);
            }
            handleSmartFormulaLatexOnUp(motionEvent);
            this.mShieldPageInkView.dispatchTouchEvent(motionEvent);
            return true;
        }
        if (pointerCount == 1 && (view = this.mTitleView) != null && view.getVisibility() == 0) {
            Rect rect = new Rect();
            if (this.mTitle.getLocalVisibleRect(rect)) {
                int scrollX = this.mTitle.getScrollX();
                boolean z = scrollX > 0 && rect.contains(((int) motionEvent.getX()) + scrollX, (int) motionEvent.getY());
                if (rect.contains((int) motionEvent.getX(), (int) motionEvent.getY()) || z) {
                    this.isTitleFocus = true;
                    this.mTitle.setFocusable(true);
                    this.mTitle.setFocusableInTouchMode(true);
                    this.mTitle.setClickable(true);
                    if (!this.mTitle.hasFocus()) {
                        this.mTitle.requestFocus();
                    }
                    this.mTitle.setKeyListener(this.mKeyListener);
                    this.mTitle.setEllipsize(null);
                    getPageInkView().setTitleFocus(this.isTitleFocus);
                    return this.mTitle.dispatchTouchEvent(motionEvent);
                }
                this.mTitle.clearFocus();
                this.mTitle.setKeyListener(null);
                this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
                this.isTitleFocus = false;
                this.mTitle.setFocusable(false);
                this.mTitle.setFocusableInTouchMode(false);
                this.mTitle.setClickable(false);
            }
        }
        View view4 = this.mTitleView;
        if (view4 != null && view4.getVisibility() != 0) {
            this.isTitleFocus = false;
            EditText editText2 = this.mTitle;
            if (editText2 != null) {
                editText2.clearFocus();
                this.mTitle.setKeyListener(null);
                this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
                this.mTitle.setFocusable(false);
                this.mTitle.setFocusableInTouchMode(false);
                this.mTitle.setClickable(false);
            }
        }
        if (pointerCount == 1 && (thumbnailRecyclerManager = this.mThumbnailRecyclerManager) != null && thumbnailRecyclerManager.getShow()) {
            return super.dispatchTouchEvent(motionEvent);
        }
        boolean zDispatchTouchEvent = this.mShieldPageInkView.dispatchTouchEvent(motionEvent);
        handleSmartFormulaLatexOnDown(motionEvent);
        return zDispatchTouchEvent;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00e1  */
    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        RectF rectF;
        int pointerCount = motionEvent.getPointerCount();
        int action = motionEvent.getAction() & 255;
        if (action == 9) {
            if (pointerCount == 1 && this.mTitleView != null) {
                this.touchStartPos1.set(motionEvent.getX(), motionEvent.getY());
                int[] iArr = {0, 0};
                this.mTitle.getLocationInWindow(iArr);
                int[] iArr2 = {0, 0};
                this.mShieldPageInkView.getLocationInWindow(iArr2);
                int[] iArr3 = {0, 0};
                this.mShieldPageInkView.getRootView().getLocationInWindow(iArr3);
                int i = iArr[0];
                rectF = new RectF(i, iArr[1] - (iArr2[1] - iArr3[1]), i + this.mTitle.getWidth(), (iArr[1] + this.mTitle.getHeight()) - (iArr2[1] - iArr3[1]));
                if (this.touchStartPos1.x <= rectF.left && this.touchStartPos1.x < rectF.right && this.touchStartPos1.y > rectF.top && this.touchStartPos1.y < rectF.bottom) {
                    this.isTitleFocus = true;
                    if (!this.mTitle.hasFocus()) {
                        this.mTitle.requestFocus();
                        EditText editText = this.mTitle;
                        editText.setSelection(editText.getEditableText().length());
                    }
                    this.mTitle.setKeyListener(this.mKeyListener);
                    this.mTitle.setEllipsize(null);
                } else {
                    this.mTitle.setKeyListener(null);
                    this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
                    this.isTitleFocus = false;
                }
            }
        } else if (action == 10) {
            if (this.isTitleFocus && !this.mTitle.hasFocus()) {
                this.mTitle.requestFocus();
                EditText editText2 = this.mTitle;
                editText2.setSelection(editText2.getEditableText().length());
            }
            if (pointerCount == 1) {
                this.touchStartPos1.set(motionEvent.getX(), motionEvent.getY());
                int[] iArr4 = {0, 0};
                this.mTitle.getLocationInWindow(iArr4);
                int[] iArr5 = {0, 0};
                this.mShieldPageInkView.getLocationInWindow(iArr5);
                int[] iArr6 = {0, 0};
                this.mShieldPageInkView.getRootView().getLocationInWindow(iArr6);
                int i2 = iArr4[0];
                rectF = new RectF(i2, iArr4[1] - (iArr5[1] - iArr6[1]), i2 + this.mTitle.getWidth(), (iArr4[1] + this.mTitle.getHeight()) - (iArr5[1] - iArr6[1]));
                if (this.touchStartPos1.x <= rectF.left) {
                    this.mTitle.setKeyListener(null);
                    this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
                    this.isTitleFocus = false;
                } else {
                    this.mTitle.setKeyListener(null);
                    this.mTitle.setEllipsize(TextUtils.TruncateAt.END);
                    this.isTitleFocus = false;
                }
            }
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    public void update(int i, int i2, float f, float f2, float f3) {
        this.mStateView.update(i, i2, f, f2, f3);
    }

    public void setTitleAndTime(String str, long j) {
        this.mTitle.setText(str);
        this.mTitleTime.setText(TimeUtils.formatTimestamp(j, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
        this.mCreationTime = j;
    }

    public EditText getTitleView() {
        return this.mTitle;
    }

    public void setTitleTime(long j) {
        this.mTitleTime.setText(TimeUtils.formatTimestamp(j, this.mContext.getResources().getString(R.string.creation_edit_title_time)));
        this.mCreationTime = j;
    }

    public ThumbnailRecyclerManager getThumbnailManager() {
        return this.mThumbnailRecyclerManager;
    }

    public void setPageIndexVisible(boolean z) {
        PageStateView pageStateView = this.mStateView;
        if (pageStateView instanceof StateView) {
            ((StateView) pageStateView).showPageIndex(z);
        }
    }

    public void setTitleViewVisible(boolean z) {
        CreationShieldPageInkView creationShieldPageInkView = this.mShieldPageInkView;
        if (creationShieldPageInkView != null) {
            creationShieldPageInkView.setShowHeadView(z);
        }
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager.ThumbnailListener
    public void onItemClick(int i) {
        this.mPageInkViewModel.jumpPage(i, false);
    }

    @Override // com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager.ThumbnailListener
    public void onImageLoad(final List<Integer> list) {
        this.mPageInkViewModel.getCurrentItemView().getCreationEngine().getCurveEngine().getExportSystem().getPageThumbnailCustomList(list, 300, new Function1() { // from class: com.miui.handwriting.common.view.handview.CreationPaintLayout$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m732xaffc1d74(list, (List) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onImageLoad$0$com-miui-handwriting-common-view-handview-CreationPaintLayout, reason: not valid java name */
    /* synthetic */ Unit m732xaffc1d74(List list, List list2) {
        ThumbnailRecyclerManager thumbnailRecyclerManager = this.mThumbnailRecyclerManager;
        if (thumbnailRecyclerManager == null) {
            return null;
        }
        thumbnailRecyclerManager.setBitmapList(list, list2);
        return null;
    }

    @Override // com.miui.libhandwritingpage.listener.PageChangeListener
    public void onPageSelectChange(int i) {
        ThumbnailRecyclerManager thumbnailRecyclerManager = this.mThumbnailRecyclerManager;
        if (thumbnailRecyclerManager == null || !thumbnailRecyclerManager.getShow()) {
            return;
        }
        this.mThumbnailRecyclerManager.scrollToPosition(i);
    }

    @Override // com.miui.libhandwritingpage.listener.PageChangeListener
    public void onPageAdd(int i) {
        ThumbnailRecyclerManager thumbnailRecyclerManager = this.mThumbnailRecyclerManager;
        if (thumbnailRecyclerManager != null) {
            thumbnailRecyclerManager.addNewPage(i);
        }
    }

    @Override // com.miui.libhandwritingpage.listener.PageChangeListener
    public void onPageInsert(int i, int i2) {
        ThumbnailRecyclerManager thumbnailRecyclerManager = this.mThumbnailRecyclerManager;
        if (thumbnailRecyclerManager == null || !thumbnailRecyclerManager.getShow()) {
            return;
        }
        this.mThumbnailRecyclerManager.insetPage(i, i2);
    }

    @Override // com.miui.libhandwritingpage.listener.PageChangeListener
    public void onPageDelete(List<Integer> list) {
        ThumbnailRecyclerManager thumbnailRecyclerManager = this.mThumbnailRecyclerManager;
        if (thumbnailRecyclerManager == null || !thumbnailRecyclerManager.getShow()) {
            return;
        }
        this.mThumbnailRecyclerManager.removePage(list);
    }

    @Override // com.miui.libhandwritingpage.listener.TitleColorChangeListener
    public void onTitleColorChange(int i, int i2) {
        int color = Color.parseColor("#99000000");
        int color2 = Color.parseColor("#99FFFFFF");
        if (i == 0 && i2 == -1) {
            this.mTitle.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (i == 1 && i2 == -1) {
            this.mTitle.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (i == 2 && i2 == -1) {
            this.mTitle.setTextColor(color2);
            this.mTitleTime.setTextColor(color2);
            return;
        }
        if ((i == 0 || i == 1) && i2 == 4) {
            this.mTitle.setTextColor(color);
            this.mTitleTime.setTextColor(color);
            return;
        }
        if (i == 2 && i2 == 4) {
            this.mTitle.setTextColor(color2);
            this.mTitleTime.setTextColor(color2);
            return;
        }
        if ((i == 0 || i == 1) && i2 == 5) {
            this.mTitle.setTextColor(color);
            this.mTitleTime.setTextColor(color);
        } else if (i == 2 && i2 == 5) {
            this.mTitle.setTextColor(color2);
            this.mTitleTime.setTextColor(color2);
        }
    }
}
