package com.miui.handwriting.common.tools;

import android.app.Application;
import android.graphics.RectF;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.data.dataset.CanvasBgSet;
import com.miui.handwriting.common.view.popup.MoreActionPopupWindow;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class BgGridConfigurationHelper {
    private static final String TAG = "BgGridConfigurationHelper";

    public static Map<Integer, PagePreviewData> getPreviewData(int i, PagePreviewData pagePreviewData, int i2, int i3) {
        Application application = HandWritingViewApplication.INSTANCE.getApplication();
        float fMin = Math.min(com.miui.libhandwritingpage.utils.UIUtils.getRealWidth(application), com.miui.libhandwritingpage.utils.UIUtils.getRealHeight(application)) - (getDimenValue(R.dimen.creation_paint_view_margin) * 2.0f);
        Log.d(TAG, "showWidth:" + fMin);
        int i4 = (int) (1.5311258f * fMin);
        HashMap map = new HashMap();
        pagePreviewData.bgColor = CanvasBgSet.getBgColors().get(i2).intValue();
        pagePreviewData.bgGrid = CanvasBgSet.BG_ARRAY[i3];
        if (pagePreviewData.bgImage == null) {
            pagePreviewData.bgImage = "";
        }
        int color = ContextCompat.getColor(application, i2 == MoreActionPopupWindow.BLACK_COLOR_INDEX ? R.color.paper_line_dark : R.color.paper_line_light);
        BgGridConfiguration bgGridConfiguration = new BgGridConfiguration();
        bgGridConfiguration.isDottedLine = true;
        bgGridConfiguration.lineColor = color;
        bgGridConfiguration.pointColor = color;
        float dimenValue = getDimenValue(R.dimen.canvas_bg_dotted_edge);
        float dimenValue2 = getDimenValue(R.dimen.canvas_line_width);
        float dimenValue3 = getDimenValue(R.dimen.canvas_bg_point_radius);
        float dimenValue4 = getDimenValue(R.dimen.canvas_line_interval);
        float dimenValue5 = getDimenValue(R.dimen.canvas_point_interval);
        float dimenValue6 = getDimenValue(R.dimen.canvas_line_horizontal_edge);
        Log.d(TAG, "pointRadius*2:" + (dimenValue3 * 2.0f));
        Log.d(TAG, "pointInterval:" + dimenValue5);
        Log.d(TAG, "lineInterval:" + dimenValue4);
        float f = i4;
        Log.d(TAG, "calculateOffsetVertical:" + calculateOffset(f, dimenValue5));
        bgGridConfiguration.pointMargin = new RectF(calculateOffset(fMin, dimenValue5), calculateOffset(f, dimenValue5), 0.0f, 0.0f);
        if (CanvasBgSet.BG_ARRAY[i3] == 1) {
            bgGridConfiguration.isShieldLine = true;
            bgGridConfiguration.lineMargin = new RectF(dimenValue6, (-dimenValue2) / 2.0f, dimenValue6, 0.0f);
        } else if (CanvasBgSet.BG_ARRAY[i3] == 3) {
            bgGridConfiguration.isShieldLine = false;
            bgGridConfiguration.lineMargin = new RectF(calculateOffset(fMin, dimenValue4), (-dimenValue4) / 2.0f, 0.0f, 0.0f);
        } else {
            bgGridConfiguration.isShieldLine = false;
            bgGridConfiguration.lineMargin = new RectF(calculateOffset(fMin, dimenValue4), (-dimenValue2) / 2.0f, 0.0f, 0.0f);
        }
        bgGridConfiguration.dottedLine = new float[]{dimenValue, dimenValue};
        bgGridConfiguration.lineInterval = dimenValue4;
        bgGridConfiguration.lineWidth = dimenValue2;
        bgGridConfiguration.pointInterval = dimenValue5;
        bgGridConfiguration.pointRadius = dimenValue3;
        pagePreviewData.bgConfig = bgGridConfiguration;
        Log.d(TAG, "mBgGridConfiguration:" + new Gson().toJson(pagePreviewData));
        map.put(Integer.valueOf(i), pagePreviewData);
        return map;
    }

    private static float calculateOffset(float f, float f2) {
        float f3 = f % f2;
        Log.d(TAG, "edge:" + f3);
        float f4 = f2 - f3;
        if (f4 < f2 / 5.0f) {
            f4 += f2;
            Log.d(TAG, "change distance:" + f4);
        }
        float f5 = (-f4) / 2.0f;
        Log.d(TAG, "offset:" + f5);
        return f5;
    }

    private static float getDimenValue(int i) {
        return HandWritingViewApplication.INSTANCE.getApplication().getResources().getDimensionPixelSize(i);
    }
}
