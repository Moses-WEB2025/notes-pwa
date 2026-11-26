package com.miui.handwriting.common.data.bean;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import ch.qos.logback.core.CoreConstants;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.HSLColor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CurveProp;

/* JADX INFO: loaded from: classes3.dex */
public class PenBean extends MiuiCurveProp {
    public static final int BALLPOINT_DEFAULT_SIZE = 6;
    public static final int BALLPOINT_MAX_SIZE = 22;
    public static final int BALLPOINT_MIN_SIZE = 1;
    public static final int CRITICAL_VALUE = 3200;
    private static final Map<PenType, Integer> DEFAULT_SIZE_MAP;
    public static final int INK_DEFAULT_SIZE = 15;
    public static final int INK_MAX_SIZE = 30;
    public static final int INK_MIN_SIZE = 1;
    public static final int MARK_DEFAULT_SIZE = 30;
    public static final int MARK_MAX_SIZE = 65;
    public static final int MARK_MIN_SIZE = 5;
    private static final Map<PenType, Integer> MAX_SIZE_MAP;
    private static final Map<PenType, Integer> MIN_SIZE_MAP;
    public static final int PENCIL_DEFAULT_SIZE = 3;
    public static final int PENCIL_MAX_SIZE = 6;
    public static final int PENCIL_MIN_SIZE = 1;
    public static final int PEN_INK_TIP_SIZE = 2;
    public static final int WATER_COLOR_DEFAULT_SIZE = 30;
    public static final int WATER_COLOR_MAX_SIZE = 65;
    public static final int WATER_COLOR_MIN_SIZE = 5;
    public static int defaultAlpha = 0;
    public static int maxAlpha = 0;
    public static int maxSensitivity = 0;
    public static int maxTipSize = 4;
    public static int minAlpha = 0;
    public static int minAlphaValue = 0;
    public static int minSensitivity = 0;
    public static int minTipSize = 1;
    private int mCheckedItemIndex;
    private int[] mDefaultColorArray;
    private HSLColor[] mDefaultHSLArray;
    private HSLColor mHslColor;
    private int maxSize;
    private int minSize;
    public String penName;

    public static int getInkPenTipSize() {
        return 2;
    }

    static {
        HashMap map = new HashMap();
        MIN_SIZE_MAP = map;
        HashMap map2 = new HashMap();
        MAX_SIZE_MAP = map2;
        HashMap map3 = new HashMap();
        DEFAULT_SIZE_MAP = map3;
        map.put(PenType.BALL, 1);
        map.put(PenType.PENCIL, 1);
        map.put(PenType.INK, 1);
        map.put(PenType.MARK, 5);
        map.put(PenType.WATER_COLOR, 5);
        map2.put(PenType.BALL, 22);
        map2.put(PenType.PENCIL, 6);
        map2.put(PenType.INK, 30);
        map2.put(PenType.MARK, 65);
        map2.put(PenType.WATER_COLOR, 65);
        map3.put(PenType.BALL, 6);
        map3.put(PenType.PENCIL, 3);
        map3.put(PenType.INK, 15);
        map3.put(PenType.MARK, 30);
        map3.put(PenType.WATER_COLOR, 30);
        maxAlpha = 255;
        minAlpha = 0;
        minAlphaValue = 3;
        maxSensitivity = 1;
        minSensitivity = 0;
        defaultAlpha = 255;
    }

    public void setDefaultColorArray(int i, int i2) {
        this.mDefaultColorArray[i] = i2;
    }

    public int[] getDefaultColorArray() {
        return this.mDefaultColorArray;
    }

    public void setDefaultColorArray(int[] iArr) {
        this.mDefaultColorArray = iArr;
        setDefaultHSLArray(iArr);
    }

    public void setDefaultHSLArray(int[] iArr) {
        if (iArr == null) {
            iArr = new int[]{-12539393, -15043, -34235};
        }
        if (this.mDefaultHSLArray == null) {
            this.mDefaultHSLArray = new HSLColor[iArr.length];
        }
        for (int i = 0; i < iArr.length; i++) {
            setDefaultHSLArray(i, new HSLColor(ColorUtils.calculateARGBColor(255, iArr[i])));
        }
        setHSLColor(new HSLColor(ColorUtils.calculateARGBColor(255, getCurrentColor())));
    }

    public void setDefaultHSLArray(int i, HSLColor hSLColor) {
        this.mDefaultHSLArray[i] = hSLColor;
    }

    public HSLColor[] getDefaultHSLArray() {
        return this.mDefaultHSLArray;
    }

    public HSLColor getHSLColor() {
        return this.mHslColor;
    }

    public void setHSLColor(HSLColor hSLColor) {
        this.penColor = hSLColor.getColor();
        this.mHslColor = hSLColor;
    }

    @Override // com.miui.handwriting.common.data.bean.MiuiCurveProp
    public int getPenColor() {
        return this.mHslColor.getColor();
    }

    public int getCheckedItemIndex() {
        return this.mCheckedItemIndex;
    }

    public void setCheckedItemIndex(int i) {
        this.mCheckedItemIndex = i;
    }

    public int getCheckedItemIndex(int i) {
        if (this.mDefaultColorArray == null) {
            return -1;
        }
        int i2 = 0;
        while (true) {
            int[] iArr = this.mDefaultColorArray;
            if (i2 >= iArr.length) {
                return -1;
            }
            if (iArr[i2] == i) {
                return i2;
            }
            i2++;
        }
    }

    public int getCurrentColor() {
        return this.mDefaultColorArray[this.mCheckedItemIndex];
    }

    public PenBean(PenType penType) {
        super(penType, -16777216, getDefaultPenSize(penType), defaultAlpha);
        this.mHslColor = new HSLColor(0);
        this.mDefaultColorArray = new int[]{-12539393, -15043, -34235};
        this.mDefaultHSLArray = new HSLColor[]{new HSLColor(-12539393), new HSLColor(-15043), new HSLColor(-34235)};
        this.minSize = getMinPenSize(penType);
        this.maxSize = getMaxPenSize(penType);
    }

    public static boolean isBigScreen() {
        return getMaxScreen() >= 3200;
    }

    public static int getMaxScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) HandWritingViewApplication.INSTANCE.getApplication().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static int getDefaultPenSize(PenType penType) {
        return DEFAULT_SIZE_MAP.getOrDefault(penType, 3).intValue();
    }

    public static int getMaxPenSize(PenType penType) {
        return MAX_SIZE_MAP.getOrDefault(penType, 6).intValue();
    }

    public static int getMinPenSize(PenType penType) {
        return MIN_SIZE_MAP.getOrDefault(penType, 1).intValue();
    }

    public int getMinSize() {
        return this.minSize;
    }

    public void setMinSize(int i) {
        this.minSize = i;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int i) {
        this.maxSize = i;
    }

    public String getPenName() {
        return this.penName;
    }

    public void setPenName(String str) {
        this.penName = str;
    }

    public void setPenDefaultColor() {
        HSLColor[] hSLColorArr = this.mDefaultHSLArray;
        if (hSLColorArr == null || hSLColorArr.length <= 0) {
            return;
        }
        setHSLColor(hSLColorArr[0]);
    }

    public String toString() {
        return "PenBean{minSize=" + this.minSize + ", maxSize=" + this.maxSize + ", mDefaultColorArray=" + Arrays.toString(this.mDefaultColorArray) + ", mDefaultHSLArray=" + Arrays.toString(this.mDefaultHSLArray) + ", mCheckedItemIndex=" + this.mCheckedItemIndex + ", penName='" + this.penName + "', penType=" + this.penType + ", penColor=" + this.penColor + ", penSize=" + this.penSize + ", penAlpha=" + this.penAlpha + ", penColorWheel=" + this.penColorWheel + ", sensitivity=" + this.sensitivity + ", tipSize=" + this.tipSize + CoreConstants.CURLY_RIGHT;
    }

    public CurveProp convert() {
        CurveProp curveProp = new CurveProp(this.penType, this.penColor, this.penSize, this.penAlpha);
        curveProp.setSensitivity(this.sensitivity);
        curveProp.setTipSize(this.tipSize);
        curveProp.setPenColorWheel(this.penColorWheel);
        return curveProp;
    }
}
