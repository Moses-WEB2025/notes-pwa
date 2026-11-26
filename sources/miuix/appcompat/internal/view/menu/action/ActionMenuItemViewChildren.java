package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.R;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes5.dex */
public class ActionMenuItemViewChildren {
    private static final int DEFAULT_IMAGE_VIEW_SIZE_DP = 28;
    private static final int ITEM_TEXT_SIZE_DP = 11;
    private static final int ITEM_TEXT_SIZE_DP_IN_LARGE_FONT = 16;
    private static final int ITEM_TEXT_SIZE_DP_IN_LARGE_FONT_TRUNCATED = 14;
    private static final int ITEM_TEXT_SIZE_DP_TRUNCATED = 10;
    private static final float PRESSED_ALPHA_DARK = 0.53f;
    private static final float PRESSED_ALPHA_LIGHT = 0.6f;
    private int mAntiVerticalTruncatedPadding;
    private int mDensityDpi;
    private ImageView mImageView;
    private LinearLayout mParent;
    private TextView mTextView;
    private boolean mLargerFontEnabled = false;
    private float mPressedAlpha = PRESSED_ALPHA_LIGHT;

    public ActionMenuItemViewChildren(final LinearLayout linearLayout) {
        this.mAntiVerticalTruncatedPadding = 0;
        this.mParent = linearLayout;
        Context context = linearLayout.getContext();
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        LinearLayout.inflate(context, R.layout.miuix_appcompat_action_menu_item_child_layout, linearLayout);
        this.mImageView = (ImageView) linearLayout.findViewById(R.id.action_menu_item_child_icon);
        this.mTextView = (TextView) linearLayout.findViewById(R.id.action_menu_item_child_text);
        this.mImageView.setForceDarkAllowed(false);
        this.mAntiVerticalTruncatedPadding = linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_small_text_anti_truncated_vertical_padding);
        this.mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        updatePressedAlpha();
        linearLayout.post(new Runnable() { // from class: miuix.appcompat.internal.view.menu.action.ActionMenuItemViewChildren.1
            @Override // java.lang.Runnable
            public void run() {
                Folme.useAt(linearLayout).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setAlpha(ActionMenuItemViewChildren.this.mPressedAlpha, ITouchStyle.TouchType.DOWN).setAlpha(1.0f, ITouchStyle.TouchType.UP).clearTintColor().handleTouchOf(linearLayout, new AnimConfig[0]);
                Folme.useAt(linearLayout).hover().setAlpha(1.0f, new IHoverStyle.HoverType[0]).setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf(linearLayout, new AnimConfig[0]);
            }
        });
    }

    private void updatePressedAlpha() {
        this.mPressedAlpha = isDarkMode() ? PRESSED_ALPHA_DARK : PRESSED_ALPHA_LIGHT;
    }

    private boolean isDarkMode() {
        Resources resources = this.mParent.getContext().getResources();
        return (resources == null || resources.getConfiguration() == null || (resources.getConfiguration().uiMode & 48) != 32) ? false : true;
    }

    private boolean isTextVerticallyTruncated() {
        Layout layout;
        TextView textView = this.mTextView;
        if (textView == null || (layout = textView.getLayout()) == null) {
            return false;
        }
        return layout.getHeight() > (this.mTextView.getMeasuredHeight() - this.mTextView.getPaddingTop()) - this.mTextView.getPaddingBottom();
    }

    void onConfigurationChanged(Configuration configuration) {
        int i = configuration.densityDpi;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            int iDp2px = MiuixUIUtils.dp2px(this.mImageView.getContext(), 28.0f);
            this.mImageView.setLayoutParams(new LinearLayout.LayoutParams(iDp2px, iDp2px));
            setLargeFontEnabled(this.mLargerFontEnabled);
        }
        if ((configuration.uiMode & 48) != (this.mParent.getResources().getConfiguration().uiMode & 48)) {
            updatePressedAlpha();
        }
    }

    public void setLargeFontEnabled(boolean z) {
        this.mLargerFontEnabled = z;
        if (z) {
            this.mTextView.setTextSize(1, 16.0f);
        } else {
            this.mTextView.setTextSize(1, 11.0f);
        }
    }

    public boolean adjustTextVerticalTruncated() {
        return this.mTextView != null && isTextVerticallyTruncated();
    }

    public void handleVerticalTextTruncated(Rect rect) {
        if (this.mLargerFontEnabled) {
            this.mTextView.setTextSize(1, 14.0f);
        } else {
            this.mTextView.setTextSize(1, 10.0f);
        }
        this.mTextView.setPadding(rect.left, rect.top + this.mAntiVerticalTruncatedPadding, rect.right, rect.bottom + this.mAntiVerticalTruncatedPadding);
    }

    public void resetTextSizeAndPaddings(Rect rect) {
        if (this.mLargerFontEnabled) {
            this.mTextView.setTextSize(1, 16.0f);
        } else {
            this.mTextView.setTextSize(1, 11.0f);
        }
        if (rect != null) {
            this.mTextView.setPadding(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void setEnabled(boolean z) {
        this.mImageView.setEnabled(z);
        this.mTextView.setEnabled(z);
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    public void setIcon(Drawable drawable) {
        if (this.mImageView.getDrawable() != drawable) {
            this.mImageView.setImageDrawable(drawable);
        }
    }

    public void setSelected(boolean z) {
        this.mImageView.setSelected(z);
        this.mTextView.setSelected(z);
    }

    public void setContentDescription(CharSequence charSequence) {
        if (charSequence == null || TextUtils.isEmpty(charSequence)) {
            this.mParent.setContentDescription(this.mTextView.getText());
        } else {
            this.mParent.setContentDescription(charSequence);
        }
    }
}
