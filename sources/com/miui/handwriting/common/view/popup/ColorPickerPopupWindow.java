package com.miui.handwriting.common.view.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.HSLColor;
import com.miui.handwriting.common.tools.PermissionUtils;
import com.miui.handwriting.common.tools.ScreenShotUtil;
import com.miui.handwriting.common.view.colorpicker.ColorPickerAlphaView;
import com.miui.handwriting.common.view.colorpicker.ColorPickerGrid;
import com.miui.handwriting.common.view.colorpicker.ColorPickerSlider;
import com.miui.handwriting.common.view.colorpicker.ColorSelectView;
import com.miui.handwriting.common.view.colorpicker.CustomViewPager;
import com.miui.handwriting.common.view.colorpicker.FloatingPicker;
import com.miui.handwriting.common.view.quickMenu.utils.QuickMenuScale;
import com.miui.libhandwritingpage.utils.MetaReflectUtil;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import defpackage.R2;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import miuix.miuixbasewidget.widget.FilterSortView;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerPopupWindow extends BaseNormalPopupWindow {
    private static final int MAX_RETRY_COUNT = 3;
    private static final String TAG = "ColorPickerPopupWindow";
    private static final int UPDATE_ALL = 2;
    private static final int UPDATE_GRID = 0;
    private static final int UPDATE_SLIDER = 1;
    public static volatile boolean sForceGridOnly = false;
    public static volatile boolean sForceHueOnly = false;
    private boolean isFirstTime;
    private int[] locationForRecognize;
    private int mAlpha;
    private ColorPickerAlphaView mAlphaSeekBar;
    private TextView mAlphaSeekBarProgress;
    public ColorSelectView mCenterColorView;
    private int mColor;
    protected int[] mColors;
    private View mDisplayBlock;
    private AtomicBoolean mFloatinPickerIsShowing;
    private FloatingPicker mFloatingPicker;
    private View mFloatingPickerView;
    private Bitmap mFullScreenBitmap;
    private FilterSortView.TabView mGridButton;
    protected ColorPickerGrid mGridColorPicker;
    private boolean mGridOnly;
    private HSLColor mHslColor;
    protected QuickMenuScale mLayoutScaleType;
    public ColorSelectView mLeftColorView;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private boolean mPanelOnly;
    private WindowManager.LayoutParams mParams;
    private OnColorPickerColorChangeListener mParentColorChangeListener;
    private View mPicker;
    public ColorSelectView mRightColorView;
    private int mScreenHeight;
    private ImageView mScreenShotView;
    private int mScreenWidth;
    private FilterSortView.TabView mSliderButton;
    private ColorPickerSlider mSliderColorPicker;
    private FilterSortView mTabView;
    protected CustomViewPager mViewPage;
    private WindowManager mWindowManager;
    private View showAnchor;
    private int showXOffset;
    private int showYOffset;

    @FunctionalInterface
    public interface OnColorPickerColorChangeListener {
        void onColorChange(ColorPickerChangeEvent colorPickerChangeEvent);
    }

    private void setDataToView() {
    }

    public ColorPickerPopupWindow(Context context) {
        this(context, false);
        detectGridOnlyFromContext(context);
    }

    public ColorPickerPopupWindow(Boolean bool, Context context) {
        super(context);
        this.mColor = -1;
        this.mAlpha = 255;
        this.mFloatinPickerIsShowing = new AtomicBoolean(false);
        this.mGridOnly = false;
        this.mPanelOnly = false;
        this.locationForRecognize = new int[2];
        this.isFirstTime = true;
        this.mLayoutScaleType = QuickMenuScale.ONE;
    }

    public ColorPickerPopupWindow(Context context, int i) {
        super(context);
        this.mColor = -1;
        this.mAlpha = 255;
        this.mFloatinPickerIsShowing = new AtomicBoolean(false);
        this.mGridOnly = false;
        this.mPanelOnly = false;
        this.locationForRecognize = new int[2];
        this.isFirstTime = true;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        initLayout(context, i);
        this.mColors = context.getResources().getIntArray(R.array.creation_colors);
    }

    public ColorPickerPopupWindow(Context context, boolean z) {
        super(context);
        this.mColor = -1;
        this.mAlpha = 255;
        this.mFloatinPickerIsShowing = new AtomicBoolean(false);
        this.mGridOnly = false;
        this.mPanelOnly = false;
        this.locationForRecognize = new int[2];
        this.isFirstTime = true;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        initLayout(context, R.layout.popup_color_picker);
        this.mColors = context.getResources().getIntArray(R.array.creation_colors);
    }

    @Override // com.miui.handwriting.common.view.popup.ArrowPopupWindow
    public Context getContext() {
        return this.mContext;
    }

    private void detectGridOnlyFromContext(Context context) {
        Activity activity;
        try {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                activity = Utils.getActivity(context);
            }
        } catch (Throwable unused) {
            activity = null;
        }
        try {
            if (activity != null) {
                Intent intent = activity.getIntent();
                boolean booleanExtra = intent.getBooleanExtra("external_launch", false);
                boolean booleanExtra2 = intent.getBooleanExtra("limited_toolbar", false);
                this.mGridOnly = false;
                this.mPanelOnly = booleanExtra || booleanExtra2;
                Log.d(TAG, "ColorPickerPopupWindow: external=" + booleanExtra + ", limited=" + booleanExtra2 + ", mPanelOnly=" + this.mPanelOnly);
            } else {
                this.mGridOnly = false;
                this.mPanelOnly = false;
            }
        } catch (Throwable unused2) {
        }
    }

    @Override // com.miui.handwriting.common.view.popup.ArrowPopupWindow, android.widget.PopupWindow
    public View getContentView() {
        return this.mContentView;
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    public void initView() {
        initScreenMetrics();
        initColorPickers();
        initViewPager();
        initTabButtons();
        initAlphaControls();
        initFloatingPicker();
        initPickerButton();
    }

    private void initScreenMetrics() {
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        this.mScreenWidth = displayMetrics.widthPixels;
        this.mScreenHeight = displayMetrics.heightPixels;
    }

    private void initColorPickers() {
        this.mGridColorPicker = new ColorPickerGrid(getContext(), this.mLayoutScaleType);
        this.mSliderColorPicker = new ColorPickerSlider(getContext(), this.mLayoutScaleType);
        this.mGridColorPicker.addOnColorChangeListener(new OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.1
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerChangeEvent colorPickerChangeEvent) {
                ColorPickerPopupWindow.this.colorChange(colorPickerChangeEvent, 1);
            }
        });
        this.mSliderColorPicker.addOnColorChangeListener(new OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.2
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerChangeEvent colorPickerChangeEvent) {
                ColorPickerPopupWindow.this.colorChange(colorPickerChangeEvent, 0);
            }
        });
    }

    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mGridColorPicker);
        if (!this.mGridOnly && !sForceGridOnly && !sForceHueOnly && !this.mPanelOnly) {
            arrayList.add(this.mSliderColorPicker);
        }
        CustomViewPager customViewPager = (CustomViewPager) this.mContentView.findViewById(R.id.custom_view_pager);
        this.mViewPage = customViewPager;
        customViewPager.setViews(arrayList);
        this.mViewPage.setCurrentPage(0);
        configureSimpleMode();
    }

    private void configureSimpleMode() {
        if (this.mGridOnly || sForceGridOnly || sForceHueOnly || this.mPanelOnly) {
            this.mTabView.setVisibility(8);
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.mGridColorPicker);
            this.mViewPage.setViews(arrayList);
            this.mViewPage.setCurrentPage(0);
            hideNonPanelViews();
        }
    }

    private void hideNonPanelViews() {
        try {
            View viewFindViewById = this.mGridColorPicker.findViewById(R.id.color_picker_grid);
            if (viewFindViewById instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) viewFindViewById;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (childAt.getId() != R.id.color_picker_panel) {
                        childAt.setVisibility(8);
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    private void initTabButtons() {
        FilterSortView filterSortView = (FilterSortView) this.mContentView.findViewById(R.id.popup_tab);
        this.mTabView = filterSortView;
        filterSortView.setTabIncatorVisibility(8);
        this.mGridButton = (FilterSortView.TabView) this.mContentView.findViewById(R.id.popup_grid_button);
        this.mSliderButton = (FilterSortView.TabView) this.mContentView.findViewById(R.id.popup_slider_button);
        this.mGridButton.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ColorPickerPopupWindow.this.mViewPage.setCurrentPage(0);
            }
        });
        if (!this.mGridOnly && !sForceGridOnly && !this.mPanelOnly && !sForceHueOnly) {
            this.mSliderButton.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ColorPickerPopupWindow.this.mViewPage.setCurrentPage(1);
                }
            });
        }
        this.mTabView.setFilteredTab(this.mGridButton);
    }

    private void initAlphaControls() {
        this.mDisplayBlock = this.mContentView.findViewById(R.id.display_block);
        this.mAlphaSeekBar = (ColorPickerAlphaView) this.mContentView.findViewById(R.id.alpha_seek_bar);
        this.mAlphaSeekBarProgress = (TextView) this.mContentView.findViewById(R.id.alpha_seek_bar_progress);
        configureAlphaVisibility();
        this.mAlphaSeekBar.addOnColorChangeListener(new OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.5
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerChangeEvent colorPickerChangeEvent) {
                ColorPickerPopupWindow.this.colorChange(colorPickerChangeEvent, 0);
            }
        });
    }

    private void configureAlphaVisibility() {
        if (this.mGridOnly || sForceGridOnly || sForceHueOnly || this.mPanelOnly) {
            ColorPickerAlphaView colorPickerAlphaView = this.mAlphaSeekBar;
            if (colorPickerAlphaView != null) {
                colorPickerAlphaView.setVisibility(8);
            }
            TextView textView = this.mAlphaSeekBarProgress;
            if (textView != null) {
                textView.setVisibility(8);
            }
            View view = this.mDisplayBlock;
            if (view != null) {
                view.setVisibility(8);
            }
            View viewFindViewById = this.mContentView.findViewById(R.id.alpha_text_transparent);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
            }
        }
    }

    private void initFloatingPicker() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.popup_color_picker_floating_picker, (ViewGroup) null);
        this.mFloatingPickerView = viewInflate;
        this.mFloatingPicker = (FloatingPicker) viewInflate.findViewById(R.id.float_picker);
        this.mScreenShotView = (ImageView) this.mFloatingPickerView.findViewById(R.id.float_background);
        this.mFloatingPickerView.setOnTouchListener(createFloatingPickerViewTouchListener());
        this.mFloatingPicker.setOnTouchListener(createFloatingPickerTouchListener());
    }

    private View.OnTouchListener createFloatingPickerViewTouchListener() {
        return new View.OnTouchListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.6
            int startX;
            int startY;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.startX = (int) motionEvent.getRawX();
                    int rawY = (int) motionEvent.getRawY();
                    this.startY = rawY;
                    ColorPickerPopupWindow.this.updateFloatingPickerPosition(this.startX, rawY);
                    ColorPickerPopupWindow.this.mFloatingPicker.setPixelColor(ColorPickerPopupWindow.this.getPixelsFromScreen(this.startX, this.startY));
                } else if (action == 1) {
                    ColorPickerPopupWindow.this.dismissFloatingPickerAndUpdateColor();
                } else if (action == 2) {
                    int rawX = (int) motionEvent.getRawX();
                    int rawY2 = (int) motionEvent.getRawY();
                    ColorPickerPopupWindow.this.updateFloatingPickerPosition(rawX, rawY2);
                    ColorPickerPopupWindow.this.mFloatingPicker.setPixelColor(ColorPickerPopupWindow.this.getPixelsFromScreen(rawX, rawY2));
                }
                return true;
            }
        };
    }

    private View.OnTouchListener createFloatingPickerTouchListener() {
        return new View.OnTouchListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.7
            float initialTouchX;
            float initialTouchY;
            float initialX;
            float initialY;
            int screenHeight;
            int screenWidth;
            int viewHeight;
            int viewWidth;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.initialX = view.getX();
                    this.initialY = view.getY();
                    this.initialTouchX = motionEvent.getRawX();
                    this.initialTouchY = motionEvent.getRawY();
                    this.viewWidth = view.getWidth();
                    this.viewHeight = view.getHeight();
                    this.screenWidth = ColorPickerPopupWindow.this.mScreenWidth;
                    this.screenHeight = ColorPickerPopupWindow.this.mScreenHeight;
                } else if (action == 1) {
                    ColorPickerPopupWindow.this.dismissFloatingPickerAndUpdateColor();
                } else if (action == 2) {
                    ColorPickerPopupWindow.this.handleFloatingPickerMove(view, motionEvent, this.initialX, this.initialY, this.initialTouchX, this.initialTouchY, this.viewWidth, this.viewHeight, this.screenWidth, this.screenHeight);
                }
                return true;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingPickerPosition(int i, int i2) {
        FloatingPicker floatingPicker = this.mFloatingPicker;
        floatingPicker.setX(i - (floatingPicker.getWidth() / 2.0f));
        FloatingPicker floatingPicker2 = this.mFloatingPicker;
        floatingPicker2.setY(i2 - (floatingPicker2.getWidth() / 2.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFloatingPickerMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4, int i, int i2, int i3, int i4) {
        float rawX = motionEvent.getRawX() - f3;
        float rawY = f2 + (motionEvent.getRawY() - f4);
        float fConstrainPosition = constrainPosition(f + rawX, i, i3);
        float fConstrainPosition2 = constrainPosition(rawY, i2, i4);
        view.setX(fConstrainPosition);
        view.setY(fConstrainPosition2);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int width = iArr[0] + (view.getWidth() / 2);
        int height = iArr[1] + (view.getHeight() / 2);
        if (width == this.mScreenWidth) {
            width--;
        }
        if (height == this.mScreenHeight) {
            height--;
        }
        this.mFloatingPicker.setPixelColor(getPixelsFromScreen(width, height));
    }

    private float constrainPosition(float f, int i, int i2) {
        int i3 = i / 2;
        float f2 = i3 + f;
        if (f2 < 0.0f) {
            return (-i) / 2;
        }
        return f2 > ((float) i2) ? i2 - i3 : f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissFloatingPickerAndUpdateColor() {
        try {
            this.mWindowManager.removeView(this.mFloatingPickerView);
            this.mFloatinPickerIsShowing.set(false);
        } catch (Exception unused) {
            Log.d(TAG, " remove FloatingPickerView error ");
        }
        showInner();
        colorChange(new ColorPickerChangeEvent(0, new HSLColor(ColorUtils.calculateARGBColor(255, this.mFloatingPicker.getCenterColor()))), 2);
    }

    private void initPickerButton() {
        this.mPicker = this.mContentView.findViewById(R.id.color_picker);
        this.mOnDismissListener = new PopupWindow.OnDismissListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.8
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                ColorPickerPopupWindow.this.setOnDismissListener(null);
                if (ColorPickerPopupWindow.this.mGridOnly || ColorPickerPopupWindow.sForceGridOnly || ColorPickerPopupWindow.sForceHueOnly) {
                    return;
                }
                ColorPickerPopupWindow.this.showFloatingPickerView();
            }
        };
        this.mPicker.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!Settings.canDrawOverlays(ColorPickerPopupWindow.this.mContext)) {
                    PermissionUtils.requestAlertWindowPermission(Utils.getActivity(ColorPickerPopupWindow.this.mContext));
                } else if (ColorPickerPopupWindow.this.isShowing()) {
                    ColorPickerPopupWindow.this.mContentView.getLocationInWindow(ColorPickerPopupWindow.this.locationForRecognize);
                    ColorPickerPopupWindow colorPickerPopupWindow = ColorPickerPopupWindow.this;
                    colorPickerPopupWindow.setOnDismissListener(colorPickerPopupWindow.mOnDismissListener);
                    ColorPickerPopupWindow.this.dismiss();
                }
            }
        });
    }

    public int initPopupWindow() {
        int iCalculateOffsetY = calculateOffsetY();
        adjustPopupWindowHeight(calculateContentHeight(iCalculateOffsetY));
        configureViewPageHeight();
        configureSimpleModeLayout();
        return iCalculateOffsetY;
    }

    private int calculateOffsetY() {
        return (int) ((getContext().getResources().getDimension(R.dimen.creation_brush_head_height) - getContext().getResources().getDimension(R.dimen.creation_tool_color_item_size)) - this.mContext.getResources().getDimension(R.dimen.creation_tool_color_margin_top));
    }

    private int calculateContentHeight(int i) {
        int multiHeight = DisplayUtils.getMultiHeight(Utils.getActivity(this.mContext));
        int dimension = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height);
        int statusBarHeight = UIUtils.getStatusBarHeight(this.mContext);
        int navigationBarHeightFromProp = UIUtils.getNavigationBarHeightFromProp(this.mContext);
        int i2 = (((multiHeight - dimension) - i) - statusBarHeight) - navigationBarHeightFromProp;
        Log.d(TAG, "contentH: " + i2 + ", offsetY: " + i + ", statusBarH: " + statusBarHeight + ", navBarH: " + navigationBarHeightFromProp);
        return i2;
    }

    private void adjustPopupWindowHeight(int i) {
        ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        this.mContentView.measure(0, 0);
        int measuredHeight = this.mContentView.getMeasuredHeight();
        Log.d(TAG, "popupWinH: " + measuredHeight);
        if (i < calculateLimitHeight(measuredHeight)) {
            adjustHeightForLimitedSpace(layoutParams, i);
        }
    }

    private int calculateLimitHeight(int i) {
        return this.mViewPage.getHeight() == 0 ? i + this.mViewPage.getViewHeight() : i;
    }

    private void adjustHeightForLimitedSpace(ViewGroup.LayoutParams layoutParams, int i) {
        layoutParams.height = Math.min(i, (int) this.mContext.getResources().getDimension(R.dimen.creation_color_picker_popup_height));
        this.mContentView.setLayoutParams(layoutParams);
    }

    private void configureViewPageHeight() {
        this.mViewPage.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m753xd8f6ef6f();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$configureViewPageHeight$0$com-miui-handwriting-common-view-popup-ColorPickerPopupWindow, reason: not valid java name */
    /* synthetic */ void m753xd8f6ef6f() {
        ViewGroup.LayoutParams layoutParams = this.mViewPage.getLayoutParams();
        layoutParams.height = this.mViewPage.getViewHeight();
        this.mViewPage.setLayoutParams(layoutParams);
    }

    private void configureSimpleModeLayout() {
        if (sForceGridOnly || sForceHueOnly || this.mGridOnly || this.mPanelOnly) {
            adjustRootPadding();
            removeScrollMargins();
            centerColorPickerPanel();
        }
    }

    private void adjustRootPadding() {
        View viewFindViewById = this.mContentView.findViewById(R.id.ll_root);
        if (viewFindViewById != null) {
            viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), viewFindViewById.getPaddingTop(), viewFindViewById.getPaddingRight(), 0);
        }
    }

    private void removeScrollMargins() {
        try {
            View view = (View) ((View) this.mViewPage.getParent()).getParent();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.topMargin = 0;
                marginLayoutParams.bottomMargin = 0;
                view.setLayoutParams(marginLayoutParams);
            }
        } catch (Throwable unused) {
        }
    }

    private void centerColorPickerPanel() {
        try {
            View viewFindViewById = this.mGridColorPicker.findViewById(R.id.color_picker_grid);
            View viewFindViewById2 = this.mGridColorPicker.findViewById(R.id.color_picker_panel);
            hideUnnecessaryViews();
            centerGridContainer(viewFindViewById);
            centerPanel(viewFindViewById2);
            centerRootContainer();
            if (viewFindViewById != null) {
                viewFindViewById.requestLayout();
            }
        } catch (Throwable unused) {
        }
    }

    private void hideUnnecessaryViews() {
        CustomViewPager customViewPager = this.mViewPage;
        if (customViewPager != null) {
            customViewPager.setVisibility(0);
        }
        FilterSortView filterSortView = this.mTabView;
        if (filterSortView != null) {
            filterSortView.setVisibility(8);
        }
        ColorPickerAlphaView colorPickerAlphaView = this.mAlphaSeekBar;
        if (colorPickerAlphaView != null) {
            colorPickerAlphaView.setVisibility(8);
        }
        TextView textView = this.mAlphaSeekBarProgress;
        if (textView != null) {
            textView.setVisibility(8);
        }
        View view = this.mDisplayBlock;
        if (view != null) {
            view.setVisibility(8);
        }
        View view2 = this.mPicker;
        if (view2 != null) {
            view2.setVisibility(8);
        }
    }

    private void centerGridContainer(View view) {
        if (view instanceof LinearLayout) {
            ((LinearLayout) view).setGravity(1);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
                layoutParams2.width = -2;
                layoutParams2.gravity = 1;
                view.setLayoutParams(layoutParams2);
            }
        }
    }

    private void centerPanel(View view) {
        if (view == null || !(view.getLayoutParams() instanceof LinearLayout.LayoutParams)) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
        view.requestLayout();
    }

    private void centerRootContainer() {
        View viewFindViewById = this.mContentView.findViewById(R.id.ll_root);
        if (viewFindViewById instanceof LinearLayout) {
            ((LinearLayout) viewFindViewById).setGravity(1);
        }
    }

    protected void showInner() {
        View view = this.showAnchor;
        if (view == null || !view.isAttachedToWindow()) {
            return;
        }
        showAsDropDown(this.showAnchor, this.showXOffset, this.showYOffset);
        setOnDismissListener(null);
    }

    public void dismissFloatingPickerView() {
        if (this.mFloatinPickerIsShowing.get()) {
            try {
                this.mWindowManager.removeViewImmediate(this.mFloatingPickerView);
            } catch (Exception unused) {
                Log.d(TAG, " removeViewImmediate  error ");
            }
        }
    }

    public void showFloatingPickerView() {
        final Bitmap bitmap;
        final int width = this.locationForRecognize[0] + (this.mContentView.getWidth() / 2);
        final int height = this.locationForRecognize[1] + (this.mContentView.getHeight() / 2);
        int width2 = this.mContentView.getWidth();
        int height2 = this.mContentView.getHeight();
        final int iMin = Math.min(30, width2 / 2);
        final int iMin2 = Math.min(1, height2 / 2);
        final int[] iArr = new int[iMin * iMin2];
        Bitmap bitmap2 = null;
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width2, height2, Bitmap.Config.ARGB_8888);
            try {
                this.mContentView.draw(new Canvas(bitmapCreateBitmap));
                try {
                    bitmapCreateBitmap.getPixels(iArr, 0, iMin, width2 / 2, height2 / 2, iMin, iMin2);
                    bitmap = bitmapCreateBitmap;
                } catch (Exception e) {
                    e = e;
                    iArr = iArr;
                    bitmap2 = bitmapCreateBitmap;
                    Log.e(TAG, "capture view bitmap fail", e);
                    bitmap = bitmap2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
        }
        new Thread(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m755xe811c080(iMin, iMin2, width, height, iArr, bitmap);
            }
        }, "ScreenshotThread").start();
    }

    /* JADX INFO: renamed from: lambda$showFloatingPickerView$2$com-miui-handwriting-common-view-popup-ColorPickerPopupWindow, reason: not valid java name */
    /* synthetic */ void m755xe811c080(int i, int i2, int i3, int i4, int[] iArr, Bitmap bitmap) {
        final Bitmap screenshot;
        try {
            int[] iArr2 = new int[i * i2];
            boolean z = true;
            int i5 = 0;
            while (true) {
                int i6 = i5 + 1;
                screenshot = ScreenShotUtil.getScreenshot(this.mContext);
                if (screenshot != null) {
                    screenshot.getPixels(iArr2, 0, i, i3, i4, i, i2);
                    for (int i7 = 0; i7 < iArr.length; i7++) {
                        if (iArr[i7] != iArr2[i7]) {
                            z = false;
                            break;
                        }
                    }
                    Log.i(TAG, " loop time " + i6 + " flag " + z);
                }
                if (!z || i6 > 3) {
                    break;
                } else {
                    i5 = i6;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "region recognize fail", e);
            screenshot = ScreenShotUtil.getScreenshot(this.mContext);
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        this.mContentView.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m754xf4823c3f(screenshot);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showFloatingPickerView$1$com-miui-handwriting-common-view-popup-ColorPickerPopupWindow, reason: not valid java name */
    /* synthetic */ void m754xf4823c3f(Bitmap bitmap) {
        this.mFullScreenBitmap = bitmap;
        if (bitmap != null) {
            this.mScreenShotView.setImageBitmap(bitmap);
            if (this.isFirstTime) {
                int[] iArr = new int[25];
                try {
                    this.mFullScreenBitmap.getPixels(iArr, 0, 5, (this.mFullScreenBitmap.getWidth() / 2) - 2, (this.mFullScreenBitmap.getHeight() / 2) - 2, 5, 5);
                    this.mFloatingPicker.setPixelColor(iArr);
                    this.isFirstTime = false;
                } catch (Exception e) {
                    Log.e(TAG, "get center pixels fail", e);
                    return;
                }
            }
            WindowManager.LayoutParams layoutParamsCreateWindowParams = createWindowParams();
            this.mParams = layoutParamsCreateWindowParams;
            try {
                this.mWindowManager.addView(this.mFloatingPickerView, layoutParamsCreateWindowParams);
                this.mFloatinPickerIsShowing.set(true);
                return;
            } catch (Exception e2) {
                Log.e(TAG, " add FloatingPickerView error ", e2);
                return;
            }
        }
        Toast.makeText(this.mContext, R.string.creation_color_picker_screenshot_fail, 0).show();
        Log.d(TAG, " getScreenshot fail ");
    }

    public void resetView() {
        this.isFirstTime = true;
        initView();
    }

    public boolean getFloatingPickerIsShowing() {
        return this.mFloatinPickerIsShowing.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getPixelsFromScreen(int i, int i2) {
        int[] iArr = new int[25];
        int i3 = 0;
        for (int i4 = i - 2; i4 <= i + 2; i4++) {
            for (int i5 = i2 - 2; i5 <= i2 + 2; i5++) {
                if (i4 >= 0 && i4 < this.mScreenWidth && i5 >= 0 && i5 < this.mScreenHeight) {
                    iArr[i3] = this.mFullScreenBitmap.getPixel(i4, i5);
                } else {
                    iArr[i3] = -16777216;
                }
                i3++;
            }
        }
        return iArr;
    }

    private WindowManager.LayoutParams createWindowParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        boolean zIsAboveAndroidS = UIUtils.isAboveAndroidS();
        int iIntValue = R2.attr.preferenceCheckableItemSetMaskPaddingEnd;
        if (zIsAboveAndroidS) {
            try {
                MetaReflectUtil.callObjectMethod2(layoutParams, "setTrustedOverlay", null, new Object[0]);
                Log.i(TAG, "setTrustedOverlay success: " + this);
            } catch (Throwable th) {
                Log.e(TAG, "setTrustedOverlay failed, " + th.toString());
            }
        } else {
            try {
                iIntValue = ((Integer) MetaReflectUtil.getStaticObjectField(WindowManager.LayoutParams.class, "TYPE_TRUSTED_APPLICATION_OVERLAY", Integer.TYPE)).intValue();
                Log.d(TAG, " set TYPE_TRUSTED_APPLICATION_OVERLAY success");
            } catch (Throwable th2) {
                Log.d(TAG, "error in " + th2);
            }
        }
        layoutParams.type = iIntValue;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        layoutParams.width = i;
        layoutParams.height = i2;
        layoutParams.flags = 787200;
        layoutParams.gravity = 49;
        layoutParams.format = -3;
        layoutParams.x = 0;
        layoutParams.y = 0;
        try {
            layoutParams.getClass().getField("privateFlags").set(layoutParams, Integer.valueOf(((Integer) layoutParams.getClass().getField("privateFlags").get(layoutParams)).intValue() | 64));
        } catch (Exception unused) {
            Log.e(TAG, "prohibit Window Animator failed");
        }
        return layoutParams;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void colorChange(ColorPickerChangeEvent colorPickerChangeEvent, int i) {
        int mode = colorPickerChangeEvent.getMode();
        if (mode == 0) {
            HSLColor hslColor = colorPickerChangeEvent.getHslColor();
            this.mHslColor = hslColor;
            hslColor.setAlpha(this.mAlpha);
            this.mColor = this.mHslColor.getColor();
            if (i == 0) {
                updateGridColorPicker();
            } else if (i == 1) {
                this.mSliderColorPicker.updateCurrentColor(this.mHslColor);
            } else if (i == 2) {
                this.mAlpha = 255;
                this.mAlphaSeekBar.updateProgress(255);
                this.mHslColor.setAlpha(this.mAlpha);
                updateGridColorPicker();
                this.mSliderColorPicker.updateCurrentColor(this.mHslColor);
            }
            this.mAlphaSeekBar.updateCurrentBackground(this.mHslColor.getColor());
        } else if (mode == 1) {
            int color = colorPickerChangeEvent.getColor();
            this.mAlpha = color;
            this.mHslColor.setAlpha(color);
        }
        this.mAlphaSeekBarProgress.setText(String.format(this.mContext.getResources().getString(R.string.creation_color_picker_popup_alpha_text_progress), NumberFormat.getPercentInstance().format(this.mAlpha / 255.0f)));
        changeDisplayBlock(this.mDisplayBlock);
        OnColorPickerColorChangeListener onColorPickerColorChangeListener = this.mParentColorChangeListener;
        if (onColorPickerColorChangeListener != null) {
            onColorPickerColorChangeListener.onColorChange(new ColorPickerChangeEvent(0, this.mHslColor));
        }
    }

    private void changeDisplayBlock(View view) {
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{view.getBackground()});
        layerDrawable.setColorFilter(ColorUtils.calculateARGBColor(this.mAlpha, this.mColor), PorterDuff.Mode.SRC);
        view.setBackground(layerDrawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyParams(Map<String, Object> map) {
        final Class<?> cls = getClass();
        map.forEach(new BiConsumer() { // from class: com.miui.handwriting.common.view.popup.ColorPickerPopupWindow$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ColorPickerPopupWindow.lambda$applyParams$3(cls, this, (String) obj, obj2);
            }
        });
    }

    static /* synthetic */ void lambda$applyParams$3(Class cls, ColorPickerPopupWindow colorPickerPopupWindow, String str, Object obj) {
        try {
            cls.getDeclaredField(str).set(colorPickerPopupWindow, obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void setColor(int i) {
        this.mColor = i;
        this.mAlpha = Color.alpha(i);
        this.mAlphaSeekBar.updateCurrentBackground(ColorUtils.calculateARGBColor(255, this.mColor));
        this.mAlphaSeekBar.setProgress(this.mAlpha);
        this.mAlphaSeekBarProgress.setText(String.format(this.mContext.getResources().getString(R.string.creation_color_picker_popup_alpha_text_progress), NumberFormat.getPercentInstance().format(this.mAlpha / 255.0f)));
        updateGridColorPicker();
        this.mSliderColorPicker.updateCurrentColor(ColorUtils.caculateOriginalHSL(this.mColor));
        changeDisplayBlock(this.mDisplayBlock);
    }

    public void setColor(HSLColor hSLColor) {
        int color = hSLColor.getColor();
        this.mColor = color;
        this.mHslColor = hSLColor;
        this.mAlpha = Color.alpha(color);
        this.mAlphaSeekBar.updateCurrentBackground(ColorUtils.calculateARGBColor(255, this.mColor));
        this.mAlphaSeekBar.setProgress(this.mAlpha);
        this.mAlphaSeekBarProgress.setText(String.format(this.mContext.getResources().getString(R.string.creation_color_picker_popup_alpha_text_progress), NumberFormat.getPercentInstance().format(this.mAlpha / 255.0f)));
        updateGridColorPicker();
        this.mSliderColorPicker.updateCurrentColor(this.mHslColor);
        changeDisplayBlock(this.mDisplayBlock);
    }

    public void addOnColorChangeListener(OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mParentColorChangeListener = onColorPickerColorChangeListener;
    }

    private boolean hasSameColor(int i) {
        int iRgb = Color.rgb(Color.red(i), Color.green(i), Color.blue(i));
        for (int i2 : this.mColors) {
            if (iRgb == i2) {
                return true;
            }
        }
        return false;
    }

    private void updateGridColorPicker() {
        if (hasSameColor(this.mColor)) {
            this.mGridColorPicker.setPanelColor(Color.rgb(Color.red(this.mColor), Color.green(this.mColor), Color.blue(this.mColor)));
        } else {
            this.mGridColorPicker.clearSelect();
        }
    }

    @Override // com.miui.handwriting.common.view.popup.ArrowPopupWindow, android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2) {
        this.showAnchor = view;
        this.showXOffset = i;
        this.showYOffset = i2;
        super.showAsDropDown(view, i, i2);
    }

    public void setMultiDefaultColor(View view) {
        ColorSelectView colorSelectView = this.mLeftColorView;
        colorSelectView.setCheck(colorSelectView.getId() == view.getId());
        ColorSelectView colorSelectView2 = this.mCenterColorView;
        colorSelectView2.setCheck(colorSelectView2.getId() == view.getId());
        ColorSelectView colorSelectView3 = this.mRightColorView;
        colorSelectView3.setCheck(colorSelectView3.getId() == view.getId());
    }

    public static class Builder {
        private final Context mContext;
        private final Map<String, Object> mParams = new HashMap();

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setColor(int i) {
            this.mParams.put("mColor", Integer.valueOf(i));
            return this;
        }

        public Builder setPenColorChangeListener(OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
            this.mParams.put("mOnPenColorChangeListener", onColorPickerColorChangeListener);
            return this;
        }

        public ColorPickerPopupWindow create() {
            ColorPickerPopupWindow colorPickerPopupWindow = new ColorPickerPopupWindow(this.mContext);
            colorPickerPopupWindow.applyParams(this.mParams);
            return colorPickerPopupWindow;
        }

        public ColorPickerPopupWindow create(boolean z) {
            ColorPickerPopupWindow colorPickerPopupWindow = new ColorPickerPopupWindow(this.mContext, z);
            colorPickerPopupWindow.applyParams(this.mParams);
            return colorPickerPopupWindow;
        }
    }

    public static class ColorPickerChangeEvent {
        public static final int ALPHA = 1;
        public static final int COLOR = 0;
        public static final int HUE = 2;
        public static final int LIGHTNESS = 4;
        public static final int SATURATION = 3;
        private int mColor;
        private HSLColor mHslColor;
        private int mMode;
        private boolean mNeedUpdate;

        public ColorPickerChangeEvent(int i, int i2) {
            this.mNeedUpdate = true;
            this.mColor = i2;
            this.mMode = i;
        }

        public ColorPickerChangeEvent(int i, int i2, boolean z) {
            this(i, i2);
            this.mNeedUpdate = z;
        }

        public ColorPickerChangeEvent(int i, HSLColor hSLColor) {
            this.mNeedUpdate = true;
            this.mColor = hSLColor.getColor();
            this.mMode = i;
            this.mHslColor = hSLColor;
        }

        public ColorPickerChangeEvent(int i, HSLColor hSLColor, boolean z) {
            this(i, hSLColor);
            this.mNeedUpdate = z;
        }

        public int getColor() {
            return this.mColor;
        }

        public void setColor(int i) {
            this.mColor = i;
        }

        public HSLColor getHslColor() {
            return this.mHslColor;
        }

        public void setHslColor(HSLColor hSLColor) {
            this.mHslColor = hSLColor;
        }

        public int getMode() {
            return this.mMode;
        }

        public void setNeedUpdate(boolean z) {
            this.mNeedUpdate = z;
        }

        public boolean getNeedUpdate() {
            return this.mNeedUpdate;
        }
    }
}
