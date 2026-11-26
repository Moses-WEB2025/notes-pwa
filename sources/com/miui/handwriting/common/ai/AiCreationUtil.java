package com.miui.handwriting.common.ai;

import android.graphics.Rect;
import android.graphics.RectF;
import com.miui.handwriting.common.engine.export.BitmapHolder;
import com.miui.libhandwritingpage.view.PageItemView;
import java.util.List;
import xc.engine.data.pagedata.ICurve;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public class AiCreationUtil {
    public static final int HAS_NO_EMPTY_AREA = -1;
    public static final int INSERT_DOWN_IMAGE = 3;
    public static final int INSERT_LEFT_IMAGE = 2;
    public static final int INSERT_NEW_PAGE_IMAGE = 5;
    public static final int INSERT_RIGHT_IMAGE = 1;
    public static final int INSERT_UP_IMAGE = 4;
    private static final String TAG = "AiCreationUtil";

    public static RectF getInsertImgAreaFromSelect(RectF rectF, float f, float f2, float f3, int i) {
        RectF rectF2 = new RectF();
        if (rectF != null && !rectF.isEmpty()) {
            float f4 = (rectF.left + rectF.right) / 2.0f;
            float f5 = (rectF.top + rectF.bottom) / 2.0f;
            if (i == 1) {
                rectF2.left = (rectF.width() / 2.0f) + f4 + f;
                rectF2.right = f4 + (rectF.width() / 2.0f) + f + f2;
                float f6 = f3 / 2.0f;
                rectF2.top = f5 - f6;
                rectF2.bottom = f5 + f6;
            } else {
                if (i == 2) {
                    rectF2.right = (f4 - (rectF.width() / 2.0f)) - f;
                    rectF2.left = ((f4 - (rectF.width() / 2.0f)) - f) - f2;
                    float f7 = f3 / 2.0f;
                    rectF2.top = f5 - f7;
                    rectF2.bottom = f5 + f7;
                    return rectF2;
                }
                if (i == 3) {
                    rectF2.top = (rectF.height() / 2.0f) + f5 + f;
                    rectF2.bottom = f5 + (rectF.height() / 2.0f) + f + f3;
                    float f8 = f2 / 2.0f;
                    rectF2.left = f4 - f8;
                    rectF2.right = f4 + f8;
                    return rectF2;
                }
                if (i == 4) {
                    rectF2.bottom = (f5 - (rectF.height() / 2.0f)) - f;
                    rectF2.top = ((f5 - (rectF.height() / 2.0f)) - f) - f3;
                    float f9 = f2 / 2.0f;
                    rectF2.left = f4 - f9;
                    rectF2.right = f4 + f9;
                    return rectF2;
                }
            }
        }
        return rectF2;
    }

    public static RectF getInsetAreaForNewPage(RectF rectF, float f, float f2, float f3, float f4) {
        RectF rectF2 = new RectF();
        if (rectF != null && !rectF.isEmpty()) {
            float f5 = (rectF.left + rectF.right) / 2.0f;
            float f6 = f2 / 2.0f;
            rectF2.left = f5 - f6;
            rectF2.right = f5 + f6;
            float f7 = f + f4;
            rectF2.top = f7;
            rectF2.bottom = f7 + f3;
        }
        return rectF2;
    }

    public static RectF getInsertAreaForLimitPage(RectF rectF, float f, float f2) {
        RectF rectF2 = new RectF();
        if (rectF != null && !rectF.isEmpty()) {
            float f3 = (rectF.left + rectF.right) / 2.0f;
            float f4 = (rectF.top + rectF.bottom) / 2.0f;
            float f5 = f / 2.0f;
            rectF2.left = f3 - f5;
            rectF2.right = f3 + f5;
            float f6 = f2 / 2.0f;
            rectF2.top = f4 - f6;
            rectF2.bottom = f4 + f6;
        }
        return rectF2;
    }

    public static float getImageSizeFromText(RectF rectF, RectF rectF2, float f) {
        float fMax = Math.max(rectF.width(), rectF.height());
        if (rectF2 == null || rectF2.isEmpty()) {
            return fMax;
        }
        float fMax2 = Math.max(rectF2.width() * f, rectF2.height() * f);
        return Math.max(Math.min(fMax, fMax2 / 2.0f), fMax2 / 4.0f);
    }

    public static Rect convertRectFToRect(RectF rectF) {
        Rect rect = new Rect();
        rectF.roundOut(rect);
        return rect;
    }

    public static int[] adjustAiInsertAreaForLimit(PageItemView pageItemView, RectF rectF, RectF rectF2, float f, int i) {
        int iMin;
        if (rectF != null && rectF2 != null && pageItemView != null) {
            RectF visibleRectF = pageItemView.model.getVisibleRectF();
            RectF layout = pageItemView.getLayout();
            if (visibleRectF != null && layout != null) {
                int iMin2 = 0;
                if (rectF.left - f < visibleRectF.left) {
                    iMin = Math.min(Math.max(0, (int) (visibleRectF.left - layout.left)), (int) ((visibleRectF.left - rectF.left) + f));
                } else {
                    iMin = rectF.right + f > visibleRectF.right ? Math.min(Math.max(0, Math.min(Math.max(0, (int) (layout.right - visibleRectF.right)), (int) ((rectF.left - f) - visibleRectF.left))), (int) ((rectF.right + f) - visibleRectF.right)) * (-1) : 0;
                }
                if (rectF.top - f < visibleRectF.top) {
                    iMin2 = Math.min(Math.max(0, (int) (visibleRectF.top - layout.top)), (int) ((visibleRectF.top - rectF.top) + f));
                } else if (rectF.bottom + f > visibleRectF.bottom) {
                    iMin2 = Math.min(Math.max(0, Math.min(Math.max(0, (int) (layout.bottom - visibleRectF.bottom)), (int) ((rectF.top - f) - visibleRectF.top))), (int) ((rectF.bottom + f) - visibleRectF.bottom)) * (-1);
                }
                float f2 = iMin;
                rectF.left += f2;
                rectF.right += f2;
                float f3 = iMin2;
                rectF.top += f3;
                rectF.bottom += f3;
                float f4 = f2 * 2.0f;
                rectF2.left += f4;
                rectF2.right += f4;
                float f5 = f3 * 2.0f;
                rectF2.top += f5;
                rectF2.bottom += f5;
                BitmapHolder.INSTANCE.setOffsetX(iMin);
                BitmapHolder.INSTANCE.setOffsetY(iMin2);
                if (iMin2 < -10) {
                    float currentToolbarHeight = getCurrentToolbarHeight(pageItemView, i);
                    rectF2.top += currentToolbarHeight;
                    rectF2.bottom += currentToolbarHeight;
                }
                if (pageItemView.isUnLimitToNormal && layout.left > 0.0f) {
                    rectF.left += layout.left;
                    rectF.right += layout.left;
                }
                return new int[]{iMin, iMin2};
            }
        }
        return null;
    }

    public static int[] adjustAiInsertAreaForUnLimit(PageItemView pageItemView, RectF rectF, float f, int i) {
        RectF visibleRectF;
        int iMin;
        if (rectF == null || pageItemView == null || (visibleRectF = pageItemView.model.getVisibleRectF()) == null) {
            return null;
        }
        int iMin2 = 0;
        if (rectF.left - f < visibleRectF.left) {
            iMin = (int) ((visibleRectF.left - rectF.left) + f);
        } else {
            iMin = rectF.right + f > visibleRectF.right ? Math.min(Math.max(0, (int) ((rectF.left - f) - visibleRectF.left)), (int) ((rectF.right + f) - visibleRectF.right)) * (-1) : 0;
        }
        if (rectF.top - f < visibleRectF.top) {
            iMin2 = (int) ((visibleRectF.top - rectF.top) + f);
        } else if (rectF.bottom + f > visibleRectF.bottom) {
            iMin2 = Math.min(Math.max(0, (int) ((rectF.top - f) - visibleRectF.top)), (int) ((rectF.bottom + f) - visibleRectF.bottom)) * (-1);
        }
        float f2 = iMin;
        rectF.left += f2;
        rectF.right += f2;
        float f3 = iMin2;
        rectF.top += f3;
        rectF.bottom += f3;
        BitmapHolder.INSTANCE.setOffsetX(iMin);
        BitmapHolder.INSTANCE.setOffsetY(iMin2);
        float currentToolbarHeight = getCurrentToolbarHeight(pageItemView, i);
        rectF.top += currentToolbarHeight;
        rectF.bottom += currentToolbarHeight;
        return new int[]{iMin, iMin2};
    }

    public static int[] adjustAiInsertAreaForNewPage(PageItemView pageItemView, RectF rectF, RectF rectF2, float f, float f2, float f3) {
        if (pageItemView == null || rectF == null || rectF2 == null) {
            return null;
        }
        RectF layout = pageItemView.getLayout();
        int iMin = Math.min(Math.max(0, (int) (layout.bottom + f3)), Math.max(0, (int) (((f3 + (f2 * 2.0f)) + f) - (pageItemView.getVisHeight() - layout.bottom)))) * (-1);
        float f4 = iMin;
        rectF.top += f4;
        rectF.bottom += f4;
        rectF2.top += f4;
        rectF2.bottom += f4;
        BitmapHolder.INSTANCE.setOffsetY(iMin);
        if (pageItemView.isUnLimitToNormal && layout.left > 0.0f) {
            rectF.left += layout.left;
            rectF.right += layout.left;
        }
        return new int[]{0, iMin};
    }

    public static RectF findEmptyAreaForUnLimit(PageItemView pageItemView, RectF rectF, float f, float f2, float f3) {
        RectF rectF2 = new RectF();
        if (rectF != null && pageItemView != null) {
            RectF insertImgAreaFromSelect = getInsertImgAreaFromSelect(rectF, f3, f, f2, 1);
            if (!isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect), true)) {
                return insertImgAreaFromSelect;
            }
            RectF insertImgAreaFromSelect2 = getInsertImgAreaFromSelect(rectF, f3, f, f2, 2);
            if (!isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect2), true)) {
                return insertImgAreaFromSelect2;
            }
            RectF insertImgAreaFromSelect3 = getInsertImgAreaFromSelect(rectF, f3, f, f2, 3);
            if (!isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect3), true)) {
                return insertImgAreaFromSelect3;
            }
            RectF insertImgAreaFromSelect4 = getInsertImgAreaFromSelect(rectF, f3, f, f2, 4);
            if (!isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect4), true)) {
                return insertImgAreaFromSelect4;
            }
        }
        return rectF2;
    }

    public static int findEmptyAreaForLimit(PageItemView pageItemView, RectF rectF, float f, float f2, float f3) {
        if (rectF != null && pageItemView != null) {
            RectF insertImgAreaFromSelect = getInsertImgAreaFromSelect(rectF, f3, f, f2, 1);
            if (!isAiInsertAreaOutOfBound(pageItemView, insertImgAreaFromSelect) && !isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect), false)) {
                return 1;
            }
            RectF insertImgAreaFromSelect2 = getInsertImgAreaFromSelect(rectF, f3, f, f2, 2);
            if (!isAiInsertAreaOutOfBound(pageItemView, insertImgAreaFromSelect2) && !isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect2), false)) {
                return 2;
            }
            RectF insertImgAreaFromSelect3 = getInsertImgAreaFromSelect(rectF, f3, f, f2, 3);
            if (!isAiInsertAreaOutOfBound(pageItemView, insertImgAreaFromSelect3) && !isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect3), false)) {
                return 3;
            }
            RectF insertImgAreaFromSelect4 = getInsertImgAreaFromSelect(rectF, f3, f, f2, 4);
            if (!isAiInsertAreaOutOfBound(pageItemView, insertImgAreaFromSelect4) && !isAreaHasContent(pageItemView, new RectF(insertImgAreaFromSelect4), false)) {
                return 4;
            }
        }
        return -1;
    }

    public static boolean isAreaHasContent(PageItemView pageItemView, RectF rectF, boolean z) {
        if (!z) {
            RectF layout = pageItemView.getLayout();
            float fMax = Math.max(0.0f, layout.left);
            float fMax2 = Math.max(0.0f, layout.top);
            rectF.left -= fMax;
            rectF.top -= fMax2;
        }
        List<ICurve> curveData = pageItemView.getCreationEngine().getDataAccess().getCurveData(convertRectFToRect(pageItemView.getMode().getSpanAreaInCanvas(new RectF(rectF))));
        if (curveData != null && !curveData.isEmpty()) {
            return true;
        }
        List<SpannedData> dataList = pageItemView.getDataList(pageItemView.getMode().getSpanAreaInCanvasWithoutRadio(new RectF(rectF)));
        return (dataList == null || dataList.isEmpty()) ? false : true;
    }

    public static int getInsertTypeForLimitPageWithoutEmpty(PageItemView pageItemView, RectF rectF, float f, float f2, float f3) {
        if (!isAiInsertAreaOutOfBound(pageItemView, getInsertImgAreaFromSelect(rectF, f3, f, f2, 1))) {
            return 1;
        }
        if (isAiInsertAreaOutOfBound(pageItemView, getInsertImgAreaFromSelect(rectF, f3, f, f2, 2))) {
            return !isAiInsertAreaOutOfBound(pageItemView, getInsertImgAreaFromSelect(rectF, f3, f, f2, 3)) ? 3 : 5;
        }
        return 2;
    }

    public static boolean isAiInsertAreaOutOfBound(PageItemView pageItemView, RectF rectF) {
        RectF layout = pageItemView.getLayout();
        float f = layout.left;
        float f2 = layout.right;
        float f3 = layout.top;
        float f4 = layout.bottom;
        float f5 = rectF.left;
        float f6 = rectF.right;
        float f7 = rectF.top;
        float f8 = rectF.bottom;
        if (pageItemView.isUnLimitToNormal && pageItemView.getLayout().left > 0.0f) {
            f5 += pageItemView.getLayout().left;
            f6 += pageItemView.getLayout().left;
        }
        return f5 < f || f6 > f2 || f7 < f3 || f8 > f4;
    }

    public static int getCurrentToolbarHeight(PageItemView pageItemView, int i) {
        if (pageItemView == null) {
            return 0;
        }
        int[] iArr = {0, 0};
        pageItemView.getLocationInWindow(iArr);
        return Math.max(0, iArr[1] - i);
    }
}
