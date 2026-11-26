package skin.support.content.res;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.core.graphics.ColorUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SkinCompatThemeUtils {
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();
    static final int[] DISABLED_STATE_SET = {-16842910};
    static final int[] ENABLED_STATE_SET = {R.attr.state_enabled};
    static final int[] WINDOW_FOCUSED_STATE_SET = {R.attr.state_window_focused};
    static final int[] FOCUSED_STATE_SET = {R.attr.state_focused};
    static final int[] ACTIVATED_STATE_SET = {R.attr.state_activated};
    static final int[] ACCELERATED_STATE_SET = {R.attr.state_accelerated};
    static final int[] HOVERED_STATE_SET = {R.attr.state_hovered};
    static final int[] DRAG_CAN_ACCEPT_STATE_SET = {R.attr.state_drag_can_accept};
    static final int[] DRAG_HOVERED_STATE_SET = {R.attr.state_drag_hovered};
    static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    static final int[] SELECTED_STATE_SET = {R.attr.state_selected};
    static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = {-16842919, -16842908};
    static final int[] EMPTY_STATE_SET = new int[0];
    private static final int[] TEMP_ARRAY = new int[1];

    public static int getTextColorPrimaryResId(Context context) {
        return getResId(context, new int[]{R.attr.textColorPrimary});
    }

    public static int getStatusBarColorResId(Context context) {
        return getResId(context, new int[]{R.attr.statusBarColor});
    }

    public static int getWindowBackgroundResId(Context context) {
        return getResId(context, new int[]{R.attr.windowBackground});
    }

    static int getResId(Context context, int[] iArr) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    public static int getThemeAttrColor(Context context, int i) {
        int[] iArr = TEMP_ARRAY;
        iArr[0] = i;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        try {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                return SkinCompatResources.getColor(context, resourceId);
            }
            return 0;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static ColorStateList getThemeAttrColorStateList(Context context, int i) {
        int[] iArr = TEMP_ARRAY;
        iArr[0] = i;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        try {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
            if (resourceId != 0) {
                return SkinCompatResources.getColorStateList(context, resourceId);
            }
            return null;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static int getDisabledThemeAttrColor(Context context, int i) {
        ColorStateList themeAttrColorStateList = getThemeAttrColorStateList(context, i);
        if (themeAttrColorStateList != null && themeAttrColorStateList.isStateful()) {
            return themeAttrColorStateList.getColorForState(DISABLED_STATE_SET, themeAttrColorStateList.getDefaultColor());
        }
        TypedValue typedValue = getTypedValue();
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        return getThemeAttrColor(context, i, typedValue.getFloat());
    }

    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = TL_TYPED_VALUE;
        TypedValue typedValue = threadLocal.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        threadLocal.set(typedValue2);
        return typedValue2;
    }

    static int getThemeAttrColor(Context context, int i, float f) {
        int themeAttrColor = getThemeAttrColor(context, i);
        return ColorUtils.setAlphaComponent(themeAttrColor, Math.round(Color.alpha(themeAttrColor) * f));
    }
}
