package com.miui.handwriting.common.data.dataset;

import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.data.bean.CanvasBgBean;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CanvasBgSet {
    public static final int[] BG_ARRAY = {0, 4, 1, 3};
    public static int GRID_STYLE_DASH_HORIZONTAL = 1;
    public static int GRID_STYLE_NULL = 0;
    public static int GRID_STYLE_SOLID_GRID = 3;
    public static int GRID_STYLE_SOLID_HORIZONTAL = 2;
    public static int PAPER_BLACK = 2;
    public static int PAPER_WHITE = 0;
    public static int PAPER_YELLOW = 1;
    private static List<Integer> bgColors = null;
    private static List<CanvasBgBean> canvasBgBeans = null;
    private static String curBgPicturePath = "";
    private static int curIndexSelectColor;
    private static int curIndexSelectGridStyle;

    public static List<Integer> getBgColors() {
        if (bgColors == null) {
            ArrayList arrayList = new ArrayList();
            bgColors = arrayList;
            arrayList.add(Integer.valueOf(ContextCompat.getColor(HandWritingViewApplication.INSTANCE.getApplication(), R.color.paper_white)));
            bgColors.add(Integer.valueOf(ContextCompat.getColor(HandWritingViewApplication.INSTANCE.getApplication(), R.color.paper_yellow)));
            bgColors.add(Integer.valueOf(ContextCompat.getColor(HandWritingViewApplication.INSTANCE.getApplication(), R.color.paper_black)));
        }
        return bgColors;
    }

    public static int getCurIndexSelectColor() {
        return curIndexSelectColor;
    }

    public static void setCurIndexSelectColor(int i) {
        curIndexSelectColor = i;
    }

    public static String getCurBgPicturePath() {
        return curBgPicturePath;
    }

    public static void setCurBgPicturePath(String str) {
        curBgPicturePath = str;
    }

    public static CanvasBgBean getCurSelectCanvasGrid() {
        return getBgGrid().get(curIndexSelectGridStyle);
    }

    public static int getCurIndexSelectGridStyle() {
        return curIndexSelectGridStyle;
    }

    public static void setCurIndexSelectGridStyle(int i) {
        curIndexSelectGridStyle = i;
    }

    public static int findIndex(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = BG_ARRAY;
            if (i2 >= iArr.length) {
                return 0;
            }
            if (iArr[i2] == i) {
                return i2;
            }
            i2++;
        }
    }

    public static List<CanvasBgBean> getBgGrid() {
        if (canvasBgBeans == null) {
            ArrayList arrayList = new ArrayList();
            canvasBgBeans = arrayList;
            arrayList.add(new CanvasBgBean(0, 0, 0));
            canvasBgBeans.add(new CanvasBgBean(4, 0, 0));
            canvasBgBeans.add(new CanvasBgBean(1, 0, 0));
            canvasBgBeans.add(new CanvasBgBean(3, 0, 0));
        }
        return canvasBgBeans;
    }
}
