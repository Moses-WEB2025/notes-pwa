package skin.support;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;
import skin.support.app.SkinActivityLifecycle;
import skin.support.app.SkinLayoutInflater;
import skin.support.app.SkinWrapper;
import skin.support.content.res.SkinCompatResources;
import skin.support.load.SkinAssetsLoader;
import skin.support.load.SkinBuildInLoader;
import skin.support.load.SkinNoneLoader;
import skin.support.load.SkinPrefixBuildInLoader;
import skin.support.observe.SkinObservable;
import skin.support.utils.SkinPreference;

/* JADX INFO: loaded from: classes6.dex */
public class SkinCompatManager extends SkinObservable {
    public static final int SKIN_LOADER_STRATEGY_ASSETS = 0;
    public static final int SKIN_LOADER_STRATEGY_BUILD_IN = 1;
    public static final int SKIN_LOADER_STRATEGY_NONE = -1;
    public static final int SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN = 2;
    private static volatile SkinCompatManager sInstance;
    private final Context mAppContext;
    private final Object mLock = new Object();
    private boolean mLoading = false;
    private List<SkinWrapper> mWrappers = new ArrayList();
    private List<SkinLayoutInflater> mInflaters = new ArrayList();
    private List<SkinLayoutInflater> mHookInflaters = new ArrayList();
    private SparseArray<SkinLoaderStrategy> mStrategyMap = new SparseArray<>();
    private boolean mSkinAllActivityEnable = true;
    private boolean mSkinStatusBarColorEnable = false;
    private boolean mSkinWindowBackgroundColorEnable = true;

    public interface SkinLoaderListener {
        void onFailed(String str);

        void onStart();

        void onSuccess();
    }

    public interface SkinLoaderStrategy {
        ColorStateList getColor(Context context, String str, int i);

        ColorStateList getColorStateList(Context context, String str, int i);

        Drawable getDrawable(Context context, String str, int i);

        String getTargetResourceEntryName(Context context, String str, int i);

        int getType();

        String loadSkinInBackground(Context context, String str);
    }

    public static SkinCompatManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinCompatManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatManager(context);
                }
            }
        }
        SkinPreference.init(context);
        return sInstance;
    }

    public static SkinCompatManager getInstance() {
        return sInstance;
    }

    public static SkinCompatManager withoutActivity(Application application) {
        init(application);
        SkinActivityLifecycle.init(application);
        return sInstance;
    }

    private SkinCompatManager(Context context) {
        this.mAppContext = context.getApplicationContext();
        initLoaderStrategy();
    }

    private void initLoaderStrategy() {
        this.mStrategyMap.put(-1, new SkinNoneLoader());
        this.mStrategyMap.put(0, new SkinAssetsLoader());
        this.mStrategyMap.put(1, new SkinBuildInLoader());
        this.mStrategyMap.put(2, new SkinPrefixBuildInLoader());
    }

    public Context getContext() {
        return this.mAppContext;
    }

    public SkinCompatManager addStrategy(SkinLoaderStrategy skinLoaderStrategy) {
        this.mStrategyMap.put(skinLoaderStrategy.getType(), skinLoaderStrategy);
        return this;
    }

    public SparseArray<SkinLoaderStrategy> getStrategies() {
        return this.mStrategyMap;
    }

    public SkinCompatManager addInflater(SkinLayoutInflater skinLayoutInflater) {
        if (skinLayoutInflater instanceof SkinWrapper) {
            this.mWrappers.add((SkinWrapper) skinLayoutInflater);
        }
        this.mInflaters.add(skinLayoutInflater);
        return this;
    }

    public List<SkinWrapper> getWrappers() {
        return this.mWrappers;
    }

    public List<SkinLayoutInflater> getInflaters() {
        return this.mInflaters;
    }

    @Deprecated
    public SkinCompatManager addHookInflater(SkinLayoutInflater skinLayoutInflater) {
        this.mHookInflaters.add(skinLayoutInflater);
        return this;
    }

    @Deprecated
    public List<SkinLayoutInflater> getHookInflaters() {
        return this.mHookInflaters;
    }

    @Deprecated
    public String getCurSkinName() {
        return SkinPreference.getInstance().getSkinName();
    }

    public void restoreDefaultTheme() {
        loadSkin("", -1);
    }

    public SkinCompatManager setSkinAllActivityEnable(boolean z) {
        this.mSkinAllActivityEnable = z;
        return this;
    }

    public boolean isSkinAllActivityEnable() {
        return this.mSkinAllActivityEnable;
    }

    @Deprecated
    public SkinCompatManager setSkinStatusBarColorEnable(boolean z) {
        this.mSkinStatusBarColorEnable = z;
        return this;
    }

    @Deprecated
    public boolean isSkinStatusBarColorEnable() {
        return this.mSkinStatusBarColorEnable;
    }

    public SkinCompatManager setSkinWindowBackgroundEnable(boolean z) {
        this.mSkinWindowBackgroundColorEnable = z;
        return this;
    }

    public boolean isSkinWindowBackgroundEnable() {
        return this.mSkinWindowBackgroundColorEnable;
    }

    public AsyncTask loadSkin() {
        String skinName = SkinPreference.getInstance().getSkinName();
        int skinStrategy = SkinPreference.getInstance().getSkinStrategy();
        if (TextUtils.isEmpty(skinName) || skinStrategy == -1) {
            return null;
        }
        return loadSkin(skinName, null, skinStrategy);
    }

    public AsyncTask loadSkin(SkinLoaderListener skinLoaderListener) {
        String skinName = SkinPreference.getInstance().getSkinName();
        int skinStrategy = SkinPreference.getInstance().getSkinStrategy();
        if (TextUtils.isEmpty(skinName) || skinStrategy == -1) {
            return null;
        }
        return loadSkin(skinName, skinLoaderListener, skinStrategy);
    }

    @Deprecated
    public AsyncTask loadSkin(String str) {
        return loadSkin(str, (SkinLoaderListener) null);
    }

    @Deprecated
    public AsyncTask loadSkin(String str, SkinLoaderListener skinLoaderListener) {
        return loadSkin(str, skinLoaderListener, 0);
    }

    public AsyncTask loadSkin(String str, int i) {
        return loadSkin(str, null, i);
    }

    public AsyncTask loadSkin(String str, SkinLoaderListener skinLoaderListener, int i) {
        SkinLoaderStrategy skinLoaderStrategy = this.mStrategyMap.get(i);
        if (skinLoaderStrategy == null) {
            return null;
        }
        return new SkinLoadTask(skinLoaderListener, skinLoaderStrategy).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str);
    }

    private class SkinLoadTask extends AsyncTask<String, Void, String> {
        private final SkinLoaderListener mListener;
        private final SkinLoaderStrategy mStrategy;

        SkinLoadTask(SkinLoaderListener skinLoaderListener, SkinLoaderStrategy skinLoaderStrategy) {
            this.mListener = skinLoaderListener;
            this.mStrategy = skinLoaderStrategy;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            SkinLoaderListener skinLoaderListener = this.mListener;
            if (skinLoaderListener != null) {
                skinLoaderListener.onStart();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            synchronized (SkinCompatManager.this.mLock) {
                while (SkinCompatManager.this.mLoading) {
                    try {
                        SkinCompatManager.this.mLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SkinCompatManager.this.mLoading = true;
            }
            try {
                if (strArr.length == 1) {
                    if (TextUtils.isEmpty(this.mStrategy.loadSkinInBackground(SkinCompatManager.this.mAppContext, strArr[0]))) {
                        SkinCompatResources.getInstance().reset(this.mStrategy);
                        return "";
                    }
                    return strArr[0];
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            SkinCompatResources.getInstance().reset();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            synchronized (SkinCompatManager.this.mLock) {
                try {
                    if (str != null) {
                        SkinPreference.getInstance().setSkinName(str).setSkinStrategy(this.mStrategy.getType()).commitEditor();
                        SkinCompatManager.this.notifyUpdateSkin();
                        SkinLoaderListener skinLoaderListener = this.mListener;
                        if (skinLoaderListener != null) {
                            skinLoaderListener.onSuccess();
                        }
                    } else {
                        SkinPreference.getInstance().setSkinName("").setSkinStrategy(-1).commitEditor();
                        SkinLoaderListener skinLoaderListener2 = this.mListener;
                        if (skinLoaderListener2 != null) {
                            skinLoaderListener2.onFailed("皮肤资源获取失败");
                        }
                    }
                    SkinCompatManager.this.mLoading = false;
                    SkinCompatManager.this.mLock.notifyAll();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public String getSkinPackageName(String str) {
        return this.mAppContext.getPackageManager().getPackageArchiveInfo(str, 1).packageName;
    }

    public Resources getSkinResources(String str) {
        try {
            PackageInfo packageArchiveInfo = this.mAppContext.getPackageManager().getPackageArchiveInfo(str, 0);
            packageArchiveInfo.applicationInfo.sourceDir = str;
            packageArchiveInfo.applicationInfo.publicSourceDir = str;
            Resources resourcesForApplication = this.mAppContext.getPackageManager().getResourcesForApplication(packageArchiveInfo.applicationInfo);
            Resources resources = this.mAppContext.getResources();
            return new Resources(resourcesForApplication.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
