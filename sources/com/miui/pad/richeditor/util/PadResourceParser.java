package com.miui.pad.richeditor.util;

import android.content.Context;
import android.util.TypedValue;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PadResourceParser {
    public static final int BG_DEFAULT_COLOR = 0;
    public static final int BG_DEFAULT_FONT_SIZE = 1;
    public static final int BLUE = 1;
    public static final int GREEN = 3;
    public static final int RED = 4;
    public static final int TEXT_LARGE = 2;
    public static final int TEXT_MEDIUM = 1;
    public static final int TEXT_SMALL = 0;
    public static final int TEXT_SUPER = 3;
    public static final int WHITE = 2;
    public static final int YELLOW = 0;

    public static int getDefaultBgId(Context context) {
        return 0;
    }

    public static class SubFolderResources {
        public static int[] colorSet = {R.color.subfolder_actionbar_background_color1, R.color.subfolder_actionbar_background_color2, R.color.subfolder_actionbar_background_color3, R.color.subfolder_actionbar_background_color4};

        public static int getThemeColor(long j) {
            int[] iArr = colorSet;
            return iArr[(int) (j % ((long) iArr.length))];
        }
    }

    public static class ColorDrawableResources {
        private static final int[] RESOURCES = {R.drawable.ic_list_yellow, R.drawable.ic_list_blue, R.drawable.ic_list_white, R.drawable.ic_list_green, R.drawable.ic_list_red};

        public static int getResource(int i) {
            return RESOURCES[i];
        }
    }

    public static class WidgetResources {
        private static final int[] BG_RESOURCES = {R.drawable.widget_title_bg_yellow, R.drawable.widget_title_bg_blue, R.drawable.widget_title_bg_white, R.drawable.widget_title_bg_green, R.drawable.widget_title_bg_red};

        public static int getWidgetTitleBgResource(int i) {
            return BG_RESOURCES[i];
        }
    }

    public static class TextAppearanceResources {
        public static final float[] FONT_SCALE = {0.8f, 1.0f, 1.25f, 1.5f};
        private static TypedValue mTmpValue = new TypedValue();

        public static float getSpecialFontSize(Context context, int i) {
            return getFontSize(context, R.dimen.text_special_font_size, i);
        }

        public static float getEditFontSpSize(Context context, int i) {
            return getFontSpSize(context, R.dimen.text_font_size, i);
        }

        public static float getEditFontSize(Context context, int i) {
            return getFontSize(context, R.dimen.text_font_size, i);
        }

        public static float getWidgetFontSize(Context context, int i) {
            return getFontSize(context, R.dimen.widget_text_size, i);
        }

        private static float getFontSize(Context context, int i, int i2) {
            float[] fArr = FONT_SCALE;
            if (i2 >= fArr.length) {
                i2 = 1;
            }
            return fArr[i2] * context.getResources().getDimensionPixelSize(i);
        }

        private static float getFontSpSize(Context context, int i, int i2) {
            float[] fArr = FONT_SCALE;
            if (i2 >= fArr.length) {
                i2 = 1;
            }
            return fArr[i2] * getSpSizeFromDef(context, i);
        }

        public static int getResourcesSize() {
            return FONT_SCALE.length;
        }

        public static float getSpSizeFromDef(Context context, int i) {
            float fComplexToFloat;
            synchronized (mTmpValue) {
                TypedValue typedValue = mTmpValue;
                context.getResources().getValue(i, typedValue, true);
                fComplexToFloat = TypedValue.complexToFloat(typedValue.data);
            }
            return fComplexToFloat;
        }
    }
}
