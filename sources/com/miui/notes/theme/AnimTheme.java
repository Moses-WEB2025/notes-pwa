package com.miui.notes.theme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.Logger;
import com.miui.maml.component.MamlView;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;
import java.io.IOException;
import java.io.InputStream;
import miuix.graphics.BitmapFactory;

/* JADX INFO: loaded from: classes4.dex */
public class AnimTheme extends SimpleTheme {
    private static final String RES_BASE_PATH = "animtheme/";
    private int mGridBodyStyle;
    private int mGridTimeStyle;
    private int mGridTitleStyle;
    private int mIcon;
    protected boolean mIsInit;
    protected boolean mIsLoad;
    protected MamlView mMamlView;
    private int mName;
    protected String mResName;

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    public AnimTheme(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15) {
        this(i, i4, i5, i6, i7, i11, i12, i13, i14, i15);
        this.mIcon = i2;
        this.mName = i3;
        this.mGridTitleStyle = i8;
        this.mGridBodyStyle = i9;
        this.mGridTimeStyle = i10;
    }

    public AnimTheme(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        super(i, i2, i3, i4, i5);
        this.mResName = "theme_leaf";
        this.mIsLoad = false;
        this.mIsInit = false;
        this.mCursorColor = i6;
        this.mHandleColor = i7;
        this.mHighLightColor = i8;
        this.mLinkedColor = i9;
        this.mDoneTextColor = i10;
        this.mThemeStyle = R.style.NoteTheme_Edit_Dark;
    }

    private MamlView createView(Context context) {
        return new MamlView(context, RES_BASE_PATH + this.mResName, 2, true);
    }

    public synchronized MamlView getBackgroundView(Context context) {
        MamlView mamlView;
        MamlView mamlView2 = this.mMamlView;
        if (mamlView2 != null) {
            Context context2 = mamlView2.getContext();
            if (context2 instanceof Activity) {
                Activity activity = (Activity) context2;
                if (activity.isDestroyed() || activity.isFinishing()) {
                    Logger.INSTANCE.d("AnimTheme", "MamlView context is destroyed, recreating...");
                    forceCleanup();
                }
            }
        }
        if (this.mIsLoad && (mamlView = this.mMamlView) != null) {
            return mamlView;
        }
        MamlView mamlViewCreateView = createView(context);
        this.mMamlView = mamlViewCreateView;
        mamlViewCreateView.setCacheSize(1);
        MamlView mamlView3 = this.mMamlView;
        if (mamlView3 != null) {
            this.mIsLoad = true;
        }
        return mamlView3;
    }

    public synchronized void forceCleanup() {
        MamlView mamlView = this.mMamlView;
        if (mamlView != null) {
            try {
                mamlView.clearBitmapCache();
                this.mMamlView.removeRelatedBitmapsRef();
                this.mMamlView.onDestroy();
            } catch (Exception e) {
                Logger.INSTANCE.e("AnimTheme", "Error during forceCleanup: " + e);
            }
            this.mMamlView = null;
            this.mIsLoad = false;
            this.mIsInit = false;
        }
    }

    public MamlView getBackgroundViewForSns(Context context) {
        return new MamlView(context, RES_BASE_PATH + this.mResName, 2, true);
    }

    public void reload() {
        MamlView mamlView;
        if (!this.mIsLoad || (mamlView = this.mMamlView) == null) {
            return;
        }
        if (!this.mIsInit) {
            this.mIsInit = true;
            mamlView.init();
        }
        this.mMamlView.sendCommand("init");
    }

    public void resume() {
        MamlView mamlView;
        if (!this.mIsLoad || (mamlView = this.mMamlView) == null) {
            return;
        }
        mamlView.sendCommand("resume");
        this.mMamlView.onResume();
    }

    public void pause() {
        MamlView mamlView;
        if (!this.mIsLoad || (mamlView = this.mMamlView) == null) {
            return;
        }
        mamlView.onPause();
    }

    @Override // com.miui.richeditor.theme.Theme
    public synchronized void onDestroyView() {
        Logger.INSTANCE.d("AnimTheme", " onDestroyView-MamlView");
        MamlView mamlView = this.mMamlView;
        if (mamlView != null && !mamlView.isAttachedToWindow()) {
            Logger.INSTANCE.i("AnimTheme", "AnimTheme onDestroyView");
            this.mMamlView.clearBitmapCache();
            this.mMamlView.removeRelatedBitmapsRef();
            this.mMamlView.onDestroy();
            this.mMamlView = null;
            this.mIsLoad = false;
        }
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        return context.getResources().getDrawable(R.drawable.v12_theme_default_fg);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            this.mFgPadding = new Rect();
            context.getResources().getDrawable(R.drawable.v12_theme_default_fg).getPadding(this.mFgPadding);
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        return context.getResources().getDrawable(R.drawable.v8_theme_arty_grid_bg);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return this.mGridTitleStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ShareStyle getShareStyle(Context context, long j) {
        Theme.ShareStyle shareStyle = super.getShareStyle(context, j);
        shareStyle.mShowFooterDivider = true;
        return shareStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return ContextCompat.getDrawable(context, this.mIcon);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_default_shadow);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return this.mGridBodyStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(this.mName);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return this.mGridTimeStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_DARK;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getShareBackground(Context context, long j) {
        return context.getResources().getDrawable(R.drawable.v8_theme_white_share_bg);
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        return ACTION_BAR_STYLE_DARK;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        return TOOL_BAR_STYLE_DARK;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_bottom);
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_top);
    }

    protected static Bitmap getImageFromAssetsFile(Context context, String str, float f) {
        IOException iOException;
        Bitmap bitmap = null;
        try {
            InputStream inputStreamOpen = context.getResources().getAssets().open(str);
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
            if (f != 1.0f) {
                try {
                    Matrix matrix = new Matrix();
                    matrix.postScale(f, f);
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeStream, 0, 0, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight(), matrix, true);
                    bitmapDecodeStream.recycle();
                    bitmap = bitmapCreateBitmap;
                } catch (IOException e) {
                    iOException = e;
                    bitmap = bitmapDecodeStream;
                    iOException.printStackTrace();
                    return bitmap;
                }
            } else {
                bitmap = bitmapDecodeStream;
            }
            inputStreamOpen.close();
            return bitmap;
        } catch (IOException e2) {
            iOException = e2;
        }
    }

    protected static Bitmap getImageFromAssetsFile(Context context, String str, float f, float f2) {
        IOException iOException;
        Bitmap bitmap = null;
        try {
            InputStream inputStreamOpen = context.getResources().getAssets().open(str);
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
            if (f != 1.0f) {
                try {
                    Matrix matrix = new Matrix();
                    matrix.postScale(f, f);
                    matrix.postRotate(f2);
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeStream, 0, 0, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight(), matrix, true);
                    bitmapDecodeStream.recycle();
                    bitmap = bitmapCreateBitmap;
                } catch (IOException e) {
                    iOException = e;
                    bitmap = bitmapDecodeStream;
                    iOException.printStackTrace();
                    return bitmap;
                }
            } else {
                bitmap = bitmapDecodeStream;
            }
            inputStreamOpen.close();
            return bitmap;
        } catch (IOException e2) {
            iOException = e2;
        }
    }
}
