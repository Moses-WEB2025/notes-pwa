package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.DisplayUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class DarkColorTheme extends ColorTheme {
    private int mBgHighLightColor;
    private int mBlurMixColor;
    private int mBlurMixColor2;
    private int mBlurMixColor3;
    public int mDirIndicatorBgColor;
    public int mDirIndicatorIconColor;
    private int mEditInputBgColor;
    public int mEditorHintColor;
    private int mGridBackgroundColor;
    private int mGridBodyStyle;
    private int mGridBorderColor;
    private int mGridTimeStyle;
    private int mGridTitleStyle;
    private int mIcon;
    private int mIconBg;
    private int mLinkLinkColor;
    private int mLinkTitleColor;
    private int mName;
    private Theme.StatusBarStyle mStatusBarStyle;
    private int mWidgetTitleBackground;

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurRadius() {
        return 150;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getWidgetTitleColor() {
        return R.color.title_primary_text_dark;
    }

    public DarkColorTheme(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21, int i22, int i23, int i24, int i25, int i26, int i27, int i28, int i29, int i30, int i31) {
        super(i, i2, i10, i6, i7, i8, i9, i17, i18, i19, i20, i21);
        this.mIcon = i3;
        this.mName = i4;
        this.mIconBg = i5;
        this.mGridTitleStyle = i11;
        this.mGridBodyStyle = i12;
        this.mGridTimeStyle = i13;
        this.mGridBackgroundColor = i14;
        this.mGridBorderColor = i15;
        this.mWidgetTitleBackground = i16;
        this.mBlurMixColor = i22;
        this.mBlurMixColor2 = i23;
        this.mBlurMixColor3 = i24;
        this.mEditorHintColor = i25;
        this.mDirIndicatorBgColor = i26;
        this.mDirIndicatorIconColor = i27;
        this.mBgHighLightColor = i28;
        this.mEditInputBgColor = i29;
        this.mLinkTitleColor = i30;
        this.mLinkLinkColor = i31;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        Resources resources = context.getResources();
        resources.getDrawable(this.mIcon);
        return resources.getDrawable(this.mIcon);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(this.mName);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, this.mIconBg);
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        if (this.mActionBarStyle == null) {
            this.mActionBarStyle = super.getActionBarStyle(context);
            this.mActionBarStyle.setHeaderInfoColor(this.mHeaderInfoTextColor);
        }
        return this.mActionBarStyle;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        if (this.mStatusBarStyle == null) {
            Theme.StatusBarStyle statusBarStyle = new Theme.StatusBarStyle();
            this.mStatusBarStyle = statusBarStyle;
            statusBarStyle.mFixed = false;
            this.mStatusBarStyle.mStatusBar = 1;
        }
        return this.mStatusBarStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{this.mLinkTitleColor, this.mLinkLinkColor});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getShareBackground(Context context, long j) {
        return new LayerDrawable(new Drawable[]{getBackgroundDrawable(context), context.getResources().getDrawable(R.drawable.v8_theme_white_share_bg)});
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        GradientDrawable gradientDrawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.v12_ic_grid_item_frame);
        if (!DisplayUtils.isNightMode()) {
            gradientDrawable.setStroke(2, context.getResources().getColor(this.mGridBorderColor));
        }
        return gradientDrawable;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        GradientDrawable gradientDrawable = (GradientDrawable) context.getDrawable(R.drawable.v12_grid_item_bg).mutate();
        gradientDrawable.setColorFilter(context.getResources().getColor(this.mGridBackgroundColor), PorterDuff.Mode.SRC_ATOP);
        gradientDrawable.setStroke(2, context.getResources().getColor(this.mGridBorderColor));
        return gradientDrawable;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return this.mGridTitleStyle;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return this.mGridBodyStyle;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return this.mGridTimeStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getWidgetTitleBackground() {
        return this.mWidgetTitleBackground;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getCursorColor() {
        return this.mCursorColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getHandleColor() {
        return this.mHandleColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getHighLightColor() {
        return this.mHighLightColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getLinkedColor() {
        return this.mLinkedColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return this.mBlurMixColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return this.mBlurMixColor2;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return this.mEditorHintColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return this.mDirIndicatorBgColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return this.mDirIndicatorIconColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return this.mBgHighLightColor;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return this.mEditInputBgColor;
    }
}
