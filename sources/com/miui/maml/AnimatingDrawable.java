package com.miui.maml;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.miui.maml.util.AppIconsHelper;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes3.dex */
public class AnimatingDrawable extends MamlDrawable {
    private static final String QUIET_IMAGE_NAME = "quietImage.png";
    private static final String TAG = "Maml.AnimatingDrawable";
    public static final int TIME_FANCY_CACHE = 0;
    private final int mAnimIndex;
    private final String mClassName;
    private final Context mContext;
    private FancyDrawable mFancyDrawable;
    private final int mLayerIndex;
    private final Object mLock;
    private final String mPackageName;
    private Drawable mQuietDrawable;
    private final ResourceManager mResourceManager;
    private boolean mUseFancyWhenStatic;
    private boolean mUseQuietWhenAnim;
    private final UserHandle mUser;

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public AnimatingDrawable(Context context, String str, String str2, ResourceManager resourceManager, UserHandle userHandle) {
        this(context, str, str2, resourceManager, userHandle, -1, -1, false, false);
    }

    public AnimatingDrawable(Context context, String str, String str2, ResourceManager resourceManager, UserHandle userHandle, int i) {
        this(context, str, str2, resourceManager, userHandle, i, -1, false, false);
    }

    public AnimatingDrawable(Context context, String str, String str2, ResourceManager resourceManager, UserHandle userHandle, int i, int i2) {
        this(context, str, str2, resourceManager, userHandle, i, i2, false, false);
    }

    public AnimatingDrawable(Context context, String str, String str2, ResourceManager resourceManager, UserHandle userHandle, int i, int i2, boolean z, boolean z2) {
        this.mUseFancyWhenStatic = false;
        this.mLock = new Object();
        this.mContext = context;
        this.mResourceManager = resourceManager;
        this.mPackageName = str;
        this.mClassName = str2;
        this.mUser = userHandle;
        this.mLayerIndex = i;
        this.mAnimIndex = i2;
        this.mUseQuietWhenAnim = z;
        this.mUseFancyWhenStatic = z2;
        init();
    }

    private void init() {
        this.mState = new AnimatingDrawableState(this.mContext, this.mPackageName, this.mClassName, this.mResourceManager, this.mUser, this.mLayerIndex, this.mAnimIndex, this.mUseQuietWhenAnim, this.mUseFancyWhenStatic);
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        this.mResourceManager.setExtraResource("den" + displayMetrics.densityDpi, displayMetrics.densityDpi);
        Drawable drawable = this.mResourceManager.getDrawable(this.mContext.getResources(), QUIET_IMAGE_NAME);
        this.mQuietDrawable = drawable;
        if (drawable != null) {
            setIntrinsicSize(drawable.getIntrinsicWidth(), this.mQuietDrawable.getIntrinsicHeight());
            Drawable drawableMutate = this.mQuietDrawable.mutate();
            this.mQuietDrawable = drawableMutate;
            drawableMutate.setBounds(0, 0, drawableMutate.getIntrinsicWidth(), this.mQuietDrawable.getIntrinsicHeight());
            if (this.mColorFilter != null) {
                this.mQuietDrawable.setColorFilter(this.mColorFilter);
                return;
            }
            return;
        }
        Log.e(TAG, "mQuietDrwable is null! package/class=" + this.mPackageName + InternalZipConstants.ZIP_FILE_SEPARATOR + this.mClassName);
    }

    public Drawable getQuietDrawable() {
        return this.mQuietDrawable;
    }

    public Drawable getStartDrawable() {
        synchronized (this.mLock) {
            prepareFancyDrawable();
            FancyDrawable fancyDrawable = this.mFancyDrawable;
            if (fancyDrawable == null) {
                return null;
            }
            return fancyDrawable.getStartDrawable();
        }
    }

    public int getAnimIndex() {
        int i = this.mAnimIndex;
        return i > -1 ? i : this.mLayerIndex;
    }

    @Deprecated
    public boolean isOnlyFancyWork() {
        return this.mUseFancyWhenStatic;
    }

    @Deprecated
    public boolean isOnlyQuietWork() {
        return this.mUseQuietWhenAnim;
    }

    public boolean useFancyWhenStatic() {
        return this.mUseFancyWhenStatic;
    }

    public boolean useQuietWhenAnim() {
        return this.mUseQuietWhenAnim;
    }

    public void prepareFancyDrawable() {
        if (this.mUseQuietWhenAnim) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mFancyDrawable != null) {
                return;
            }
            Drawable fancyIconDrawable = AppIconsHelper.getFancyIconDrawable(this.mContext, this.mPackageName, this.mClassName, 0L, this.mUser, this.mLayerIndex);
            if (fancyIconDrawable instanceof FancyDrawable) {
                if (this.mQuietDrawable == null && (this.mIntrinsicHeight <= 0 || this.mIntrinsicWidth <= 0)) {
                    setIntrinsicSize(this.mFancyDrawable.mIntrinsicWidth, this.mFancyDrawable.mIntrinsicHeight);
                }
                FancyDrawable fancyDrawable = (FancyDrawable) fancyIconDrawable;
                this.mFancyDrawable = fancyDrawable;
                fancyDrawable.setColorFilter(this.mColorFilter);
            }
        }
    }

    public Drawable getFancyDrawable() {
        prepareFancyDrawable();
        return this.mFancyDrawable;
    }

    public void clear() {
        synchronized (this.mLock) {
            FancyDrawable fancyDrawable = this.mFancyDrawable;
            if (fancyDrawable != null) {
                fancyDrawable.cleanUp();
            }
            this.mFancyDrawable = null;
        }
    }

    public void sendCommand(String str) {
        FancyDrawable fancyDrawable = this.mFancyDrawable;
        if (fancyDrawable != null) {
            fancyDrawable.getRoot().onCommand(str);
        }
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
    }

    @Override // com.miui.maml.MamlDrawable
    protected void drawIcon(Canvas canvas) {
        if (this.mQuietDrawable == null) {
            return;
        }
        try {
            int iSave = canvas.save();
            canvas.translate(getBounds().left, getBounds().top);
            canvas.scale(this.mWidth / this.mIntrinsicWidth, this.mHeight / this.mIntrinsicHeight, 0.0f, 0.0f);
            this.mQuietDrawable.draw(canvas);
            canvas.restoreToCount(iSave);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            Log.e(TAG, e2.toString());
        }
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.mQuietDrawable;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        Log.d(TAG, "setColorFilter");
        Drawable drawable = this.mQuietDrawable;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        if (this.mBadgeDrawable != null) {
            this.mBadgeDrawable.setColorFilter(colorFilter);
        }
        FancyDrawable fancyDrawable = this.mFancyDrawable;
        if (fancyDrawable != null) {
            fancyDrawable.setColorFilter(colorFilter);
        }
    }

    static final class AnimatingDrawableState extends MamlDrawable.MamlDrawableState {
        private final int mAnimIndex;
        private final String mClassName;
        private final Context mContext;
        private final int mLayerIndex;
        private final String mPackageName;
        private final ResourceManager mResourceManager;
        private final boolean mUseFancyWhenStatic;
        private final boolean mUseQuietWhenAnim;
        private final UserHandle mUser;

        public AnimatingDrawableState(Context context, String str, String str2, ResourceManager resourceManager, UserHandle userHandle, int i, int i2, boolean z, boolean z2) {
            this.mContext = context;
            this.mResourceManager = resourceManager;
            this.mPackageName = str;
            this.mClassName = str2;
            this.mUser = userHandle;
            this.mLayerIndex = i;
            this.mAnimIndex = i2;
            this.mUseQuietWhenAnim = z;
            this.mUseFancyWhenStatic = z2;
        }

        @Override // com.miui.maml.MamlDrawable.MamlDrawableState
        protected MamlDrawable createDrawable() {
            return new AnimatingDrawable(this.mContext, this.mPackageName, this.mClassName, this.mResourceManager, this.mUser, this.mLayerIndex, this.mAnimIndex, this.mUseQuietWhenAnim, this.mUseFancyWhenStatic);
        }
    }
}
