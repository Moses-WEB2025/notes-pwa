package com.miui.handwriting.common.view.popup;

import android.content.Context;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.dataset.OperatorSet;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.PermissionUtils;
import com.miui.handwriting.common.tools.ScreenShotUtil;
import com.miui.handwriting.common.view.colorpicker.ColorPickerAlphaView;
import com.miui.handwriting.common.view.colorpicker.ColorPickerGrid;
import com.miui.handwriting.common.view.colorpicker.ColorPickerSlider;
import com.miui.handwriting.common.view.colorpicker.ColorSelectView;
import com.miui.handwriting.common.view.colorpicker.CustomViewPager;
import com.miui.handwriting.common.view.colorpicker.FloatingPicker;
import com.miui.libhandwritingpage.utils.MetaReflectUtil;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import defpackage.R2;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import miuix.miuixbasewidget.widget.FilterSortView;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeColorPickerPopupWindow extends PopupWindow {
    private static final int MAX_RETRY_COUNT = 3;
    private static final String TAG = "ColorPickerPopupWindow";
    private static final int UPDATE_ALL = 2;
    private static final int UPDATE_GRID = 0;
    private static final int UPDATE_SLIDER = 1;
    private ActionCallBack actionCallBack;
    private boolean isFirstTime;
    private int[] locationForRecognize;
    private int mAlpha;
    private ColorPickerAlphaView mAlphaSeekBar;
    private TextView mAlphaSeekBarProgress;
    public ColorSelectView mCenterColorView;
    private int mColor;
    private int[] mColors;
    private View mContentView;
    private Context mContext;
    private PopupWindow.OnDismissListener mDismissListener;
    private View mDisplayBlock;
    private AtomicBoolean mFloatinPickerIsShowing;
    private FloatingPicker mFloatingPicker;
    private View mFloatingPickerView;
    private boolean mFromClick;
    private Bitmap mFullScreenBitmap;
    private FilterSortView.TabView mGridButton;
    private ColorPickerGrid mGridColorPicker;
    public ColorSelectView mLeftColorView;
    private WeakReference<IMiuiHandWritingModel> mPageInkViewModel;
    private WindowManager.LayoutParams mParams;
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mParentColorChangeListener;
    private View mPicker;
    public ColorSelectView mRightColorView;
    private int mScreenHeight;
    private ImageView mScreenShotView;
    private int mScreenWidth;
    private FilterSortView.TabView mSliderButton;
    private ColorPickerSlider mSliderColorPicker;
    private FilterSortView mTabView;
    private CustomViewPager mViewPage;
    private WindowManager mWindowManager;
    private View showAnchor;
    private int showXOffset;
    private int showYOffset;

    public interface ActionCallBack {
        void onAction(int i);
    }

    private void setDataToView() {
    }

    public ShapeColorPickerPopupWindow(Context context) {
        this(context, null);
    }

    public ShapeColorPickerPopupWindow(Context context, IMiuiHandWritingModel iMiuiHandWritingModel) {
        super(context);
        this.mColor = -1;
        this.mAlpha = 255;
        this.mFloatinPickerIsShowing = new AtomicBoolean(false);
        this.locationForRecognize = new int[2];
        this.mFromClick = true;
        this.isFirstTime = true;
        this.mColors = context.getResources().getIntArray(R.array.creation_colors);
        this.mContext = context;
        View viewInflate = View.inflate(context, R.layout.shape_popup_color_picker, null);
        this.mContentView = viewInflate;
        setContentView(viewInflate);
        setWidth(-2);
        setHeight(-2);
        Log.d(TAG, "mContentView:" + this.mContentView);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(null);
        this.mPageInkViewModel = new WeakReference<>(iMiuiHandWritingModel);
        initView();
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override // android.widget.PopupWindow
    public View getContentView() {
        return this.mContentView;
    }

    public void initView() {
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        this.mScreenWidth = displayMetrics.widthPixels;
        this.mScreenHeight = displayMetrics.heightPixels;
        this.mGridColorPicker = new ColorPickerGrid(getContext());
        this.mSliderColorPicker = new ColorPickerSlider(getContext());
        this.mGridColorPicker.addOnColorChangeListener(new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.1
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                ShapeColorPickerPopupWindow.this.colorChange(colorPickerChangeEvent, 1);
            }
        });
        this.mSliderColorPicker.addOnColorChangeListener(new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.2
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                ShapeColorPickerPopupWindow.this.colorChange(colorPickerChangeEvent, 0);
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mGridColorPicker);
        arrayList.add(this.mSliderColorPicker);
        CustomViewPager customViewPager = (CustomViewPager) this.mContentView.findViewById(R.id.custom_view_pager);
        this.mViewPage = customViewPager;
        customViewPager.setViews(arrayList);
        FilterSortView filterSortView = (FilterSortView) this.mContentView.findViewById(R.id.popup_tab);
        this.mTabView = filterSortView;
        filterSortView.setTabIncatorVisibility(8);
        this.mGridButton = (FilterSortView.TabView) this.mContentView.findViewById(R.id.popup_grid_button);
        this.mSliderButton = (FilterSortView.TabView) this.mContentView.findViewById(R.id.popup_slider_button);
        this.mGridButton.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ShapeColorPickerPopupWindow.this.mViewPage.setCurrentPage(0);
            }
        });
        this.mSliderButton.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ShapeColorPickerPopupWindow.this.mViewPage.setCurrentPage(1);
            }
        });
        this.mViewPage.setCurrentPage(0);
        this.mTabView.setFilteredTab(this.mGridButton);
        this.mDisplayBlock = this.mContentView.findViewById(R.id.display_block);
        this.mAlphaSeekBar = (ColorPickerAlphaView) this.mContentView.findViewById(R.id.alpha_seek_bar);
        this.mAlphaSeekBarProgress = (TextView) this.mContentView.findViewById(R.id.alpha_seek_bar_progress);
        this.mAlphaSeekBar.addOnColorChangeListener(new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.5
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                ShapeColorPickerPopupWindow.this.colorChange(colorPickerChangeEvent, 0);
            }
        });
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.popup_color_picker_floating_picker, (ViewGroup) null);
        this.mFloatingPickerView = viewInflate;
        this.mFloatingPicker = (FloatingPicker) viewInflate.findViewById(R.id.float_picker);
        this.mScreenShotView = (ImageView) this.mFloatingPickerView.findViewById(R.id.float_background);
        this.mFloatingPickerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.6
            int startX;
            int startY;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.startX = (int) motionEvent.getRawX();
                    this.startY = (int) motionEvent.getRawY();
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setX(this.startX - (ShapeColorPickerPopupWindow.this.mFloatingPicker.getWidth() / 2.0f));
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setY(this.startY - (ShapeColorPickerPopupWindow.this.mFloatingPicker.getWidth() / 2.0f));
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setPixelColor(ShapeColorPickerPopupWindow.this.getPixelsFromScreen(this.startX, this.startY));
                } else if (action == 1) {
                    try {
                        ShapeColorPickerPopupWindow.this.mWindowManager.removeView(ShapeColorPickerPopupWindow.this.mFloatingPickerView);
                        ShapeColorPickerPopupWindow.this.mFloatinPickerIsShowing.set(false);
                    } catch (Exception unused) {
                        Log.d(ShapeColorPickerPopupWindow.TAG, " remove FloatingPickerView error ");
                    }
                    ShapeColorPickerPopupWindow.this.showInnerAtLocation();
                    ShapeColorPickerPopupWindow.this.colorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(0, ColorUtils.calculateARGBColor(255, ShapeColorPickerPopupWindow.this.mFloatingPicker.getCenterColor()), true), 2);
                } else if (action == 2) {
                    int rawX = (int) motionEvent.getRawX();
                    int rawY = (int) motionEvent.getRawY();
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setX(rawX - (ShapeColorPickerPopupWindow.this.mFloatingPicker.getWidth() / 2.0f));
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setY(rawY - (ShapeColorPickerPopupWindow.this.mFloatingPicker.getWidth() / 2.0f));
                    if (rawX == ShapeColorPickerPopupWindow.this.mScreenWidth) {
                        rawX--;
                    }
                    if (rawY == ShapeColorPickerPopupWindow.this.mScreenHeight) {
                        rawY--;
                    }
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setPixelColor(ShapeColorPickerPopupWindow.this.getPixelsFromScreen(rawX, rawY));
                }
                return true;
            }
        });
        this.mFloatingPicker.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.7
            float initialTouchX;
            float initialTouchY;
            float initialX;
            float initialY;
            boolean mode = false;
            float offsetX;
            float offsetY;
            int screenHeight;
            int screenWidth;
            int viewHeight;
            int viewWidth;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.mode = false;
                    this.initialX = view.getX();
                    this.initialY = view.getY();
                    this.initialTouchX = motionEvent.getRawX();
                    this.initialTouchY = motionEvent.getRawY();
                    this.viewWidth = view.getWidth();
                    this.viewHeight = view.getHeight();
                    this.screenWidth = ShapeColorPickerPopupWindow.this.mScreenWidth;
                    this.screenHeight = ShapeColorPickerPopupWindow.this.mScreenHeight;
                } else if (action == 1) {
                    try {
                        ShapeColorPickerPopupWindow.this.mWindowManager.removeView(ShapeColorPickerPopupWindow.this.mFloatingPickerView);
                        ShapeColorPickerPopupWindow.this.mFloatinPickerIsShowing.set(false);
                    } catch (Exception unused) {
                        Log.d(ShapeColorPickerPopupWindow.TAG, " remove FloatingPickerView error ");
                    }
                    ShapeColorPickerPopupWindow.this.showInnerAtLocation();
                    ShapeColorPickerPopupWindow.this.colorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(0, ColorUtils.calculateARGBColor(255, ShapeColorPickerPopupWindow.this.mFloatingPicker.getCenterColor()), true), 2);
                } else if (action == 2) {
                    float rawX = motionEvent.getRawX() - this.initialTouchX;
                    float rawY = motionEvent.getRawY() - this.initialTouchY;
                    float f = this.initialX + rawX;
                    float f2 = this.initialY + rawY;
                    int i = this.viewWidth;
                    if ((i / 2) + f < 0.0f) {
                        f = (-i) / 2;
                    } else {
                        float f3 = (i / 2) + f;
                        int i2 = this.screenWidth;
                        if (f3 > i2) {
                            f = i2 - (i / 2);
                        }
                    }
                    int i3 = this.viewHeight;
                    if ((i3 / 2) + f2 < 0.0f) {
                        f2 = (-i3) / 2;
                    } else {
                        float f4 = (i3 / 2) + f2;
                        int i4 = this.screenHeight;
                        if (f4 > i4) {
                            f2 = i4 - (i3 / 2);
                        }
                    }
                    view.setX(f);
                    view.setY(f2);
                    int[] iArr = new int[2];
                    view.getLocationOnScreen(iArr);
                    int width = iArr[0] + (view.getWidth() / 2);
                    int height = iArr[1] + (view.getHeight() / 2);
                    if (width == ShapeColorPickerPopupWindow.this.mScreenWidth) {
                        width--;
                    }
                    if (height == ShapeColorPickerPopupWindow.this.mScreenHeight) {
                        height--;
                    }
                    ShapeColorPickerPopupWindow.this.mFloatingPicker.setPixelColor(ShapeColorPickerPopupWindow.this.getPixelsFromScreen(width, height));
                }
                return true;
            }
        });
        this.mPicker = this.mContentView.findViewById(R.id.color_picker);
        this.mDismissListener = new PopupWindow.OnDismissListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.8
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                ShapeColorPickerPopupWindow.this.setOnDismissListener(null);
                ShapeColorPickerPopupWindow.this.showFloatingPickerView();
                ShapeColorPickerPopupWindow.this.mFromClick = true;
            }
        };
        this.mPicker.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Utils.isFastClick(view.getId())) {
                    return;
                }
                if (!Settings.canDrawOverlays(ShapeColorPickerPopupWindow.this.mContext)) {
                    PermissionUtils.requestAlertWindowPermission(Utils.getActivity(ShapeColorPickerPopupWindow.this.mContext));
                    return;
                }
                if (ShapeColorPickerPopupWindow.this.isShowing()) {
                    ShapeColorPickerPopupWindow.this.mContentView.getLocationInWindow(ShapeColorPickerPopupWindow.this.locationForRecognize);
                    ShapeColorPickerPopupWindow shapeColorPickerPopupWindow = ShapeColorPickerPopupWindow.this;
                    shapeColorPickerPopupWindow.setOnDismissListener(shapeColorPickerPopupWindow.mDismissListener);
                    ShapeColorPickerPopupWindow.this.mFromClick = false;
                    ShapeColorPickerPopupWindow.this.dismiss();
                }
            }
        });
        this.mAlphaSeekBar.setMin(0);
    }

    private void showInner() {
        View view = this.showAnchor;
        if (view == null || !view.isAttachedToWindow()) {
            return;
        }
        showAsDropDown(this.showAnchor, this.showXOffset, this.showYOffset);
        setOnDismissListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInnerAtLocation() {
        View view = this.showAnchor;
        if (view == null || !view.isAttachedToWindow() || this.mPageInkViewModel.get() == null || this.mPageInkViewModel.get().getSelectShapeProp() == null) {
            return;
        }
        showAtLocation(this.showAnchor, 0, this.showXOffset, this.showYOffset);
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
        new Thread(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m796xcc9ab798(iMin, iMin2, width, height, iArr, bitmap);
            }
        }, "ScreenshotThread").start();
    }

    /* JADX INFO: renamed from: lambda$showFloatingPickerView$1$com-miui-handwriting-common-view-popup-ShapeColorPickerPopupWindow, reason: not valid java name */
    /* synthetic */ void m796xcc9ab798(int i, int i2, int i3, int i4, int[] iArr, Bitmap bitmap) {
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
        this.mContentView.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m795x302cbb39(screenshot);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showFloatingPickerView$0$com-miui-handwriting-common-view-popup-ShapeColorPickerPopupWindow, reason: not valid java name */
    /* synthetic */ void m795x302cbb39(Bitmap bitmap) {
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
    public void colorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent, int i) {
        int mode = colorPickerChangeEvent.getMode();
        int color = colorPickerChangeEvent.getColor();
        if (mode == 0) {
            this.mColor = color;
            int iCalculateARGBColor = ColorUtils.calculateARGBColor(this.mAlpha, color);
            this.mColor = iCalculateARGBColor;
            if (i == 0) {
                updateGridColorPicker();
            } else if (i == 1) {
                this.mSliderColorPicker.updateCurrentColor(ColorUtils.caculateOriginalHSL(iCalculateARGBColor));
            } else if (i == 2) {
                updateGridColorPicker();
                this.mAlpha = 255;
                this.mAlphaSeekBar.updateProgress(255);
                int iCalculateARGBColor2 = ColorUtils.calculateARGBColor(this.mAlpha, this.mColor);
                this.mColor = iCalculateARGBColor2;
                this.mSliderColorPicker.updateCurrentColor(ColorUtils.caculateOriginalHSL(iCalculateARGBColor2));
            }
            this.mAlphaSeekBar.updateCurrentBackground(this.mColor);
        } else if (mode == 1) {
            this.mAlpha = color;
        }
        this.mAlphaSeekBarProgress.setText(String.format(this.mContext.getResources().getString(R.string.creation_color_picker_popup_alpha_text_progress), NumberFormat.getPercentInstance().format(this.mAlpha / 255.0f)));
        changeDisplayBlock(this.mDisplayBlock);
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = this.mParentColorChangeListener;
        if (onColorPickerColorChangeListener != null) {
            onColorPickerColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(0, ColorUtils.calculateARGBColor(this.mAlpha, this.mColor), colorPickerChangeEvent.getNeedUpdate()));
        }
    }

    private void changeDisplayBlock(View view) {
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{view.getBackground()});
        layerDrawable.setColorFilter(ColorUtils.calculateARGBColor(this.mAlpha, this.mColor), PorterDuff.Mode.SRC);
        view.setBackground(layerDrawable);
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

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mParentColorChangeListener = onColorPickerColorChangeListener;
    }

    public void addActionCallBack(ActionCallBack actionCallBack) {
        this.actionCallBack = actionCallBack;
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

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2) {
        this.showAnchor = view;
        this.showXOffset = i;
        this.showYOffset = i2;
        super.showAsDropDown(view, i, i2);
    }

    @Override // android.widget.PopupWindow
    public void showAtLocation(View view, int i, int i2, int i3) {
        this.showAnchor = view;
        this.showXOffset = i2;
        this.showYOffset = i3;
        super.showAtLocation(view, i, i2, i3);
    }

    public void setMultiDefaultColor(View view) {
        ColorSelectView colorSelectView = this.mLeftColorView;
        colorSelectView.setCheck(colorSelectView.getId() == view.getId());
        ColorSelectView colorSelectView2 = this.mCenterColorView;
        colorSelectView2.setCheck(colorSelectView2.getId() == view.getId());
        ColorSelectView colorSelectView3 = this.mRightColorView;
        colorSelectView3.setCheck(colorSelectView3.getId() == view.getId());
    }

    public void setPickViewColor(int i) {
        setColor(i);
        setDataToView();
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (this.mFromClick) {
            this.actionCallBack.onAction(OperatorSet.ACTION_SHOW_SHAPE_EDIT);
        }
        super.dismiss();
    }

    public void dismissImmediately() {
        dismissFloatingPickerView();
        setAnimationStyle(0);
        update();
        View view = this.mContentView;
        if (view != null) {
            view.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.10
                @Override // java.lang.Runnable
                public void run() {
                    ShapeColorPickerPopupWindow.this.dismiss();
                }
            });
        } else {
            dismiss();
        }
    }

    public void setFromClick(boolean z) {
        this.mFromClick = z;
    }
}
