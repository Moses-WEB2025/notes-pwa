package com.market.sdk.homeguide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.BitmapFactory;
import com.market.sdk.utils.Build;
import com.market.sdk.utils.Language;
import com.market.sdk.utils.Log;
import com.market.sdk.utils.PrefUtils;
import com.market.sdk.utils.ReflectUtils;
import com.market.sdk.utils.Region;
import com.market.sdk.utils.ResourceUtils;
import com.market.sdk.utils.SettingsCompat;
import defpackage.R2;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class AppstoreUserGuide {
    private static final String PREF_KEY_NEED_SHOWN = "need_show_user_guide";
    private static final int SETTINGS_NOT_SHOW_GUIDE = 2;
    private static final int SETTINGS_SHOW_GUIDE = 1;
    private static final String TAG = "AppstoreUserGuide";
    public static final Set<String> sHomeUserGuideEnableLanguages;
    public static final Set<String> sHomeUserGuideEnableRegions;

    private int getLayerForWallPaper() {
        return R2.drawable.miuix_appcompat_action_mode_button_bg_single_disable_light;
    }

    static {
        HashSet hashSet = new HashSet();
        sHomeUserGuideEnableRegions = hashSet;
        HashSet hashSet2 = new HashSet();
        sHomeUserGuideEnableLanguages = hashSet2;
        hashSet.add(Region.IN);
        hashSet.add(Region.ID);
        hashSet.add(Region.RU);
        hashSet.add(Region.ES);
        hashSet2.add(Language.LA_EN);
        hashSet2.add(Language.LA_IN);
        hashSet2.add(Language.LA_RU);
        hashSet2.add(Language.LA_ES);
    }

    public boolean needShowUserGuide() {
        if (!Build.isInternational() || !PrefUtils.getBoolean(PREF_KEY_NEED_SHOWN, true, new PrefUtils.PrefFile[0])) {
            return false;
        }
        if (ResourceUtils.isBigFontMode()) {
            Log.d(TAG, "do not show appstore guide in big font mode");
            return false;
        }
        int i = SettingsCompat.System.getInt(AppGlobal.getContext(), "com.xiaomi.mipicks.need_show_user_guide_status", -1);
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        Set<String> set = sHomeUserGuideEnableRegions;
        if (!set.contains(Region.getRegion())) {
            Log.d(TAG, "region not match, current is: " + Region.getRegion() + ", expected is: " + set);
            return false;
        }
        Set<String> set2 = sHomeUserGuideEnableLanguages;
        if (!set2.contains(Locale.getDefault().getLanguage())) {
            Log.d(TAG, "language not match, current is: " + Locale.getDefault().getLanguage() + ", expected is: " + set2);
            return false;
        }
        if (AppstoreUserGuideService.getUserGuideIntent() != null) {
            return true;
        }
        Log.d(TAG, "no service found to show appstore guide");
        return false;
    }

    public String getTargetPackageName() {
        return "com.xiaomi.mipicks";
    }

    public String getTargetClassName() {
        return "com.xiaomi.market.ui.MarketTabActivity";
    }

    public void tryShowUserGuide(Bitmap bitmap, HomeUserGuideData homeUserGuideData, Interceptor interceptor) throws Throwable {
        Bitmap bitmapCaptureWallpaper = captureWallpaper();
        if (bitmapCaptureWallpaper == null) {
            Log.e(TAG, "capture wallpaper failed!");
            return;
        }
        try {
            BitmapFactory.saveToFile(createBackgroundBitmap(bitmapCaptureWallpaper, bitmap), homeUserGuideData.getLocalFilePath(), false);
        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
        }
        show(homeUserGuideData, interceptor);
    }

    private Bitmap captureWallpaper() {
        Class<?> cls = ReflectUtils.getClass("miui.util.ScreenshotUtils");
        String methodSignature = ReflectUtils.getMethodSignature(Bitmap.class, Context.class, Float.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE);
        int layerForWallPaper = getLayerForWallPaper();
        Bitmap bitmap = (Bitmap) ReflectUtils.invokeObject(cls, cls, "getScreenshot", methodSignature, AppGlobal.getContext(), Float.valueOf(1.0f), Integer.valueOf(layerForWallPaper), Integer.valueOf(layerForWallPaper), true);
        if (bitmap == null) {
            return null;
        }
        return bitmap;
    }

    private Bitmap createBackgroundBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Paint paint = new Paint();
        if (!bitmap2.isMutable()) {
            bitmap2 = Bitmap.createBitmap(ResourceUtils.getScreenWidth(), ResourceUtils.getScreenHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap2);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return bitmap2;
    }

    private void show(final HomeUserGuideData homeUserGuideData, final Interceptor interceptor) {
        if (AppstoreUserGuideService.getUserGuideIntent() == null) {
            Log.i(TAG, "can not found user guide service");
        } else {
            AppstoreUserGuideService.openService().tryShow(homeUserGuideData, new Callback() { // from class: com.market.sdk.homeguide.AppstoreUserGuide.1
                @Override // com.market.sdk.homeguide.Callback
                public boolean onIntercept() {
                    Interceptor interceptor2 = interceptor;
                    if (interceptor2 != null) {
                        return interceptor2.intercept();
                    }
                    return false;
                }

                @Override // com.market.sdk.homeguide.Callback
                public void onFinish(boolean z) {
                    if (!TextUtils.isEmpty(homeUserGuideData.getLocalFilePath())) {
                        new File(homeUserGuideData.getLocalFilePath()).delete();
                    }
                    if (z) {
                        return;
                    }
                    PrefUtils.setBoolean(AppstoreUserGuide.PREF_KEY_NEED_SHOWN, false, new PrefUtils.PrefFile[0]);
                }
            });
        }
    }
}
