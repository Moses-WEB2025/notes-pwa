package com.miui.maml.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.miui.maml.AnimatingDrawable;
import com.miui.maml.FancyDrawable;
import com.miui.maml.LifecycleResourceManager;
import com.miui.maml.MamlAdaptiveIconDrawable;
import com.miui.maml.RenderThread;
import com.miui.maml.ResourceManager;
import com.miui.maml.ScreenElementRoot;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import miui.content.res.IconCustomizer;

/* JADX INFO: loaded from: classes3.dex */
public class AppIconsHelper {
    public static final int INVALID_INDEX = -1;
    private static final int LAYER_NUM = 5;
    public static final int TIME_DAY = 86400000;
    public static final int TIME_HOUR = 3600000;
    public static final int TIME_MIN = 60000;
    private static RendererCoreCache mRendererCoreCache;
    private static int mThemeChanged;
    private static ArrayMap<String, WeakReference<ResourceManager>> mAnimatingIconsResourceManagers = new ArrayMap<>();
    private static ArrayMap<String, WeakReference<ResourceManager>> mMamlAdaptiveIconsResourceManagers = new ArrayMap<>();
    private static ArrayMap<String, WeakReference<ConfigFile>> mMamlAdaptiveIconsConfigFile = new ArrayMap<>();
    private static final RendererCoreCache.OnCreateRootCallback mOnCreateRootCallback = new RendererCoreCache.OnCreateRootCallback() { // from class: com.miui.maml.util.AppIconsHelper.1
        @Override // com.miui.maml.util.RendererCoreCache.OnCreateRootCallback
        public void onCreateRoot(ScreenElementRoot screenElementRoot) {
            if (screenElementRoot != null) {
                screenElementRoot.setScaleByDensity(true);
            }
        }
    };

    private AppIconsHelper() {
    }

    public static void cleanUp() {
        RenderThread.globalThreadStop();
    }

    public static Drawable getIconDrawable(Context context, ResolveInfo resolveInfo, PackageManager packageManager) {
        return getIconDrawable(context, resolveInfo, packageManager, 0L);
    }

    public static Drawable getIconDrawable(Context context, ResolveInfo resolveInfo, PackageManager packageManager, long j) {
        return getIconDrawable(context, resolveInfo.activityInfo != null ? resolveInfo.activityInfo : resolveInfo.serviceInfo, packageManager, j);
    }

    public static Drawable getIconDrawable(Context context, PackageItemInfo packageItemInfo, PackageManager packageManager) {
        return getIconDrawable(context, packageItemInfo, packageManager, 0L);
    }

    public static Drawable getIconDrawable(Context context, PackageItemInfo packageItemInfo, PackageManager packageManager, long j) {
        return getIconDrawable(context, packageItemInfo, packageManager, j, HideSdkDependencyUtils.UserHandle_getInstance_with_int(HideSdkDependencyUtils.Context_getUserId(context)));
    }

    public static Drawable getIconDrawable(Context context, PackageItemInfo packageItemInfo, PackageManager packageManager, long j, UserHandle userHandle) {
        Drawable iconDrawable = getIconDrawable(context, packageItemInfo, packageItemInfo.packageName, !(packageItemInfo instanceof ApplicationInfo) ? packageItemInfo.name : null, j, userHandle);
        return iconDrawable != null ? iconDrawable : packageItemInfo.loadIcon(packageManager);
    }

    public static Drawable getIconDrawable(Context context, String str, String str2, long j) {
        return getIconDrawable(context, str, str2, j, HideSdkDependencyUtils.UserHandle_getInstance_with_int(HideSdkDependencyUtils.Context_getUserId(context)));
    }

    public static Drawable getIconDrawable(Context context, String str, String str2, long j, UserHandle userHandle) {
        ActivityInfo activityInfo;
        try {
            activityInfo = context.getPackageManager().getActivityInfo(new ComponentName(str, str2), 0);
        } catch (Exception unused) {
            activityInfo = null;
        }
        return getIconDrawable(context, activityInfo, str, str2, j, userHandle);
    }

    public static Drawable getFancyIconDrawable(Context context, String str, String str2, long j, UserHandle userHandle, int i) {
        ActivityInfo activityInfo;
        try {
            activityInfo = context.getPackageManager().getActivityInfo(new ComponentName(str, str2), 0);
        } catch (Exception unused) {
            activityInfo = null;
        }
        return getIconDrawable(context, activityInfo, str, str2, j, userHandle, true, i);
    }

    public static Drawable getIconDrawable(Context context, PackageItemInfo packageItemInfo, String str, String str2, long j) {
        return getIconDrawable(context, packageItemInfo, str, str2, j, HideSdkDependencyUtils.UserHandle_getInstance_with_int(HideSdkDependencyUtils.Context_getUserId(context)));
    }

    public static Drawable getIconDrawable(Context context, PackageItemInfo packageItemInfo, String str, String str2, long j, UserHandle userHandle) {
        return getIconDrawable(context, packageItemInfo, str, str2, j, userHandle, false, -1);
    }

    public static Drawable getIconDrawable(Context context, PackageItemInfo packageItemInfo, String str, String str2, long j, UserHandle userHandle, boolean z, int i) {
        String str3;
        Drawable adaptiveDrawable;
        Drawable fancyDrawable;
        String fancyIconRelativePath;
        if (!IconCustomizer.isModIconEnabledForPackageName(str)) {
            return null;
        }
        if (mRendererCoreCache == null) {
            mRendererCoreCache = new RendererCoreCache(new Handler(Looper.getMainLooper()));
        }
        try {
            checkVersion(context);
            String str4 = str + str2 + HideSdkDependencyUtils.UserHandle_getIdentifier(userHandle);
            if (z) {
                str3 = str;
                adaptiveDrawable = null;
            } else {
                adaptiveDrawable = getAdaptiveDrawable(context, packageItemInfo, str, str2, j, userHandle);
                str3 = str;
            }
            if (adaptiveDrawable == null) {
                if (z && i != -1) {
                    String mamlAdaptiveIconRelativePath = IconCustomizer.getMamlAdaptiveIconRelativePath(packageItemInfo, str3, str2);
                    if (mamlAdaptiveIconRelativePath != null) {
                        String str5 = mamlAdaptiveIconRelativePath + i + "/fancy/";
                        String str6 = str4 + i;
                        RendererCoreCache.RendererCoreInfo rendererCoreInfo = mRendererCoreCache.get(str6, j);
                        if (rendererCoreInfo == null) {
                            rendererCoreInfo = mRendererCoreCache.get(str6, context, j, new FancyIconResourceLoader(str5), mOnCreateRootCallback);
                        }
                        fancyDrawable = (rendererCoreInfo == null || rendererCoreInfo.r == null) ? null : new FancyDrawable(rendererCoreInfo.r);
                        adaptiveDrawable = fancyDrawable;
                    }
                } else {
                    String animatingIconRelativePath = IconCustomizer.getAnimatingIconRelativePath(packageItemInfo, str3, str2);
                    if (animatingIconRelativePath != null && !z) {
                        ResourceManager resourceManagerCreateLifecycleResourceManagerFromeCache = createLifecycleResourceManagerFromeCache(mAnimatingIconsResourceManagers, animatingIconRelativePath + "quiet/", str4);
                        if (resourceManagerCreateLifecycleResourceManagerFromeCache != null) {
                            fancyDrawable = new AnimatingDrawable(context, str3, str2, resourceManagerCreateLifecycleResourceManagerFromeCache, userHandle);
                            context = context;
                            userHandle = userHandle;
                            adaptiveDrawable = fancyDrawable;
                        }
                    } else {
                        RendererCoreCache.RendererCoreInfo rendererCoreInfo2 = mRendererCoreCache.get(str4, j);
                        if (rendererCoreInfo2 == null) {
                            if (animatingIconRelativePath != null) {
                                fancyIconRelativePath = animatingIconRelativePath + "fancy/";
                            } else {
                                fancyIconRelativePath = IconCustomizer.getFancyIconRelativePath(packageItemInfo, str3, str2);
                            }
                            rendererCoreInfo2 = mRendererCoreCache.get(str4, context, j, new FancyIconResourceLoader(fancyIconRelativePath), mOnCreateRootCallback);
                        }
                        if (rendererCoreInfo2 == null || rendererCoreInfo2.r == null) {
                            adaptiveDrawable = null;
                        } else {
                            fancyDrawable = new FancyDrawable(rendererCoreInfo2.r);
                            adaptiveDrawable = fancyDrawable;
                        }
                    }
                }
            }
            if (adaptiveDrawable != null) {
                PortableUtils.getUserBadgedIcon(context, adaptiveDrawable, userHandle);
            }
            return adaptiveDrawable;
        } catch (Exception e) {
            Log.e("MAML AppIconsHelper", e.toString());
            return null;
        }
    }

    private static Drawable getAdaptiveDrawable(Context context, PackageItemInfo packageItemInfo, String str, String str2, long j, UserHandle userHandle) {
        ConfigFile configFile;
        AnimatingDrawable animatingDrawableCreateSubAnimatingDrawable;
        AnimatingDrawable animatingDrawableCreateSubAnimatingDrawable2;
        String str3 = str + str2 + userHandle.hashCode();
        String mamlAdaptiveIconRelativePath = IconCustomizer.getMamlAdaptiveIconRelativePath(packageItemInfo, str, str2);
        if (mamlAdaptiveIconRelativePath != null) {
            WeakReference<ConfigFile> weakReference = mMamlAdaptiveIconsConfigFile.get(str3);
            ConfigFile configFile2 = weakReference == null ? null : weakReference.get();
            if (configFile2 == null) {
                configFile2 = new ConfigFile();
                if (configFile2.loadByIconStream(mamlAdaptiveIconRelativePath + "config.xml")) {
                    mMamlAdaptiveIconsConfigFile.put(str3, new WeakReference<>(configFile2));
                    configFile = configFile2;
                } else {
                    configFile = null;
                }
            } else {
                configFile = configFile2;
            }
            ResourceManager resourceManagerCreateLifecycleResourceManagerFromeCache = createLifecycleResourceManagerFromeCache(mMamlAdaptiveIconsResourceManagers, mamlAdaptiveIconRelativePath + "quiet/", str3);
            if (resourceManagerCreateLifecycleResourceManagerFromeCache == null || (animatingDrawableCreateSubAnimatingDrawable = createSubAnimatingDrawable(context, configFile, str, str2, mamlAdaptiveIconRelativePath + 0, userHandle, 0)) == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 1; i < 5 && (animatingDrawableCreateSubAnimatingDrawable2 = createSubAnimatingDrawable(context, configFile, str, str2, mamlAdaptiveIconRelativePath + i, userHandle, i)) != null; i++) {
                arrayList.add(animatingDrawableCreateSubAnimatingDrawable2);
            }
            return new MamlAdaptiveIconDrawable(animatingDrawableCreateSubAnimatingDrawable, new LayerDrawable((Drawable[]) arrayList.toArray(new Drawable[0])), context, resourceManagerCreateLifecycleResourceManagerFromeCache);
        }
        return null;
    }

    private static int getLayerAnimIndex(ConfigFile configFile, int i) {
        if (configFile == null) {
            return -1;
        }
        try {
            return Integer.parseInt(configFile.getVariable("layer" + i));
        } catch (Exception unused) {
            return -1;
        }
    }

    private static boolean getUseQuietWhenAnimFromConfig(ConfigFile configFile, int i) {
        if (configFile == null) {
            return false;
        }
        boolean zEquals = TextUtils.equals("true", configFile.getVariable("use_quiet_when_anim_layer" + i));
        return !zEquals ? TextUtils.equals("true", configFile.getVariable("layer_only_quiet" + i)) : zEquals;
    }

    private static boolean getUseFancyWhenStaticFromConfig(ConfigFile configFile, int i) {
        if (configFile == null) {
            return false;
        }
        boolean zEquals = TextUtils.equals("true", configFile.getVariable("use_fancy_when_static_layer" + i));
        return !zEquals ? TextUtils.equals("true", configFile.getVariable("layer_only_fancy" + i)) : zEquals;
    }

    private static AnimatingDrawable createSubAnimatingDrawable(Context context, ConfigFile configFile, String str, String str2, String str3, UserHandle userHandle, int i) {
        boolean useQuietWhenAnimFromConfig;
        boolean useFancyWhenStaticFromConfig;
        if (configFile != null) {
            useQuietWhenAnimFromConfig = getUseQuietWhenAnimFromConfig(configFile, i);
            useFancyWhenStaticFromConfig = getUseFancyWhenStaticFromConfig(configFile, i);
        } else {
            useQuietWhenAnimFromConfig = false;
            useFancyWhenStaticFromConfig = false;
        }
        ResourceManager resourceManagerCreateLifecycleResourceManagerFromeCache = createLifecycleResourceManagerFromeCache(mAnimatingIconsResourceManagers, str3 + "/quiet/", str + str2 + userHandle.hashCode() + i);
        if (resourceManagerCreateLifecycleResourceManagerFromeCache == null) {
            Log.e("MAML AppIconsHelper", "fail to get rm, can't create subAD! package=" + str);
            return null;
        }
        if (configFile != null) {
            return new AnimatingDrawable(context, str, str2, resourceManagerCreateLifecycleResourceManagerFromeCache, userHandle, i, getLayerAnimIndex(configFile, i), useQuietWhenAnimFromConfig, useFancyWhenStaticFromConfig);
        }
        return new AnimatingDrawable(context, str, str2, resourceManagerCreateLifecycleResourceManagerFromeCache, userHandle, i);
    }

    private static void checkVersion(Context context) {
        int iConfiguration_getThemeChanged = HideSdkDependencyUtils.Configuration_getThemeChanged(context.getResources().getConfiguration());
        if (iConfiguration_getThemeChanged > mThemeChanged) {
            clearCache();
            mThemeChanged = iConfiguration_getThemeChanged;
        }
    }

    public static void clearCache() {
        RendererCoreCache rendererCoreCache = mRendererCoreCache;
        if (rendererCoreCache != null) {
            rendererCoreCache.clear();
        }
        ArrayMap<String, WeakReference<ResourceManager>> arrayMap = mAnimatingIconsResourceManagers;
        if (arrayMap != null) {
            arrayMap.clear();
        }
    }

    private static ResourceManager createLifecycleResourceManagerFromeCache(ArrayMap<String, WeakReference<ResourceManager>> arrayMap, String str, String str2) {
        if (arrayMap == null) {
            return null;
        }
        WeakReference<ResourceManager> weakReference = arrayMap.get(str2);
        ResourceManager resourceManager = weakReference != null ? weakReference.get() : null;
        if (resourceManager != null) {
            return resourceManager;
        }
        LifecycleResourceManager lifecycleResourceManager = new LifecycleResourceManager(new FancyIconResourceLoader(str), 3600000L, 360000L);
        arrayMap.put(str2, new WeakReference<>(lifecycleResourceManager));
        return lifecycleResourceManager;
    }
}
