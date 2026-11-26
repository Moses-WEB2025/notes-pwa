package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ColorTheme extends SimpleTheme {
    private final int mBackgroundColor;

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return 0;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return 0;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return 0;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return null;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return null;
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return null;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected boolean showGridItemImage(Context context) {
        return true;
    }

    public ColorTheme(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i3, i4, i5, i6);
        this.mBackgroundColor = i2;
        this.mUseDarkCheckBox = false;
    }

    public ColorTheme(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        super(i, i2, i4, i5, i6, i7, i8, i9, i10, i11, i12);
        this.mBackgroundColor = i3;
        this.mUseDarkCheckBox = false;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ShareStyle getShareStyle(Context context, long j) {
        Theme.ShareStyle shareStyle = super.getShareStyle(context, j);
        shareStyle.mShowFooterDivider = true;
        return shareStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        return new ColorDrawable(context.getResources().getColor(this.mBackgroundColor));
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        return context.getResources().getDrawable(R.drawable.v12_theme_default_fg);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            Resources resources = context.getResources();
            this.mFgPadding = new Rect();
            resources.getDrawable(R.drawable.v12_theme_default_fg).getPadding(this.mFgPadding);
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        GradientDrawable gradientDrawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.v12_grid_item_bg).mutate();
        gradientDrawable.setColor(context.getResources().getColor(this.mBackgroundColor));
        return gradientDrawable;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_bottom);
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_top);
    }
}
