package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

/* JADX INFO: loaded from: classes5.dex */
public class SplashScreenViewGroup extends ViewGroup {
    public static final int ICON_RADIUS = 24;
    public static final float ICON_TOP_SPACE_RATIO = 0.48f;
    public int mIconSize;
    private RoundCornerImageView mIconView;

    public SplashScreenViewGroup(Context context) {
        this(context, null);
    }

    public SplashScreenViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SplashScreenViewGroup(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SplashScreenViewGroup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIconSize = 96;
        init(context);
    }

    private void init(Context context) {
        this.mIconView = new RoundCornerImageView(context);
        int iDp2px = dp2px(context, this.mIconSize);
        this.mIconSize = iDp2px;
        addView(this.mIconView, iDp2px, iDp2px);
        clipCornerRadius(context);
    }

    public static int dp2px(Context context, float f) {
        return (int) ((f * (context.getResources().getConfiguration().densityDpi / 160.0f)) + 0.5f);
    }

    private void clipCornerRadius(Context context) {
        float fDp2px = dp2px(context, 24.0f);
        this.mIconView.setCornerRadius(fDp2px);
        if (Build.VERSION.SDK_INT > 29) {
            this.mIconView.setCornerRadiusEnabled(false);
            this.mIconView.setOutlineProvider(new SplashScreenOutlineProvider(fDp2px));
            this.mIconView.setClipToOutline(true);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSize, 1073741824);
        this.mIconView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getChildCount() != 1) {
            return;
        }
        View childAt = getChildAt(0);
        int measuredWidth = childAt.getMeasuredWidth();
        int measuredHeight = childAt.getMeasuredHeight();
        int measuredWidth2 = (getMeasuredWidth() - measuredWidth) / 2;
        int measuredHeight2 = (int) ((getMeasuredHeight() - measuredHeight) * 0.48f);
        childAt.layout(measuredWidth2, measuredHeight2, measuredWidth + measuredWidth2, measuredHeight + measuredHeight2);
    }

    public void setIcon(Drawable drawable, boolean z) {
        RoundCornerImageView roundCornerImageView = this.mIconView;
        if (roundCornerImageView != null) {
            roundCornerImageView.setNeedStroke(z);
            this.mIconView.setImageDrawable(drawable);
        }
    }

    public void setIcon(int i, boolean z) {
        RoundCornerImageView roundCornerImageView = this.mIconView;
        if (roundCornerImageView != null) {
            roundCornerImageView.setNeedStroke(z);
            this.mIconView.setImageDrawable(i);
        }
    }

    public void setIcon(Drawable drawable) {
        setIcon(drawable, false);
    }

    public void setIcon(int i) {
        setIcon(i, false);
    }

    public static class SplashScreenOutlineProvider extends ViewOutlineProvider {
        float radius;

        public SplashScreenOutlineProvider(float f) {
            this.radius = f;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            Path path = new Path();
            RectF rectF = new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
            float f = this.radius;
            path.addRoundRect(rectF, new float[]{f, f, f, f, f, f, f, f}, Path.Direction.CW);
            outline.setAlpha(0.0f);
            outline.setConvexPath(path);
        }
    }
}
