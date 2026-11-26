package skin.support.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.core.view.LayoutInflaterCompat;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import skin.support.SkinCompatManager;
import skin.support.annotation.Skinable;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatThemeUtils;
import skin.support.observe.SkinObservable;
import skin.support.observe.SkinObserver;
import skin.support.utils.Slog;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: classes6.dex */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "SkinActivityLifecycle";
    private static volatile SkinActivityLifecycle sInstance;
    private WeakReference<Activity> mCurActivityRef;
    private WeakHashMap<Context, SkinCompatDelegate> mSkinDelegateMap;
    private WeakHashMap<Context, LazySkinObserver> mSkinObserverMap;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    public static SkinActivityLifecycle init(Application application) {
        if (sInstance == null) {
            synchronized (SkinActivityLifecycle.class) {
                if (sInstance == null) {
                    sInstance = new SkinActivityLifecycle(application);
                }
            }
        }
        return sInstance;
    }

    private SkinActivityLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(this);
        installLayoutFactory(application);
        SkinCompatManager.getInstance().addObserver(getObserver(application));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (isContextSkinEnable(activity)) {
            installLayoutFactory(activity);
            updateWindowBackground(activity);
            if (activity instanceof SkinCompatSupportable) {
                ((SkinCompatSupportable) activity).applySkin();
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.mCurActivityRef = new WeakReference<>(activity);
        if (isContextSkinEnable(activity)) {
            LazySkinObserver observer = getObserver(activity);
            SkinCompatManager.getInstance().addObserver(observer);
            observer.updateSkinIfNeeded();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        if (isContextSkinEnable(activity)) {
            SkinCompatManager.getInstance().deleteObserver(getObserver(activity));
            this.mSkinObserverMap.remove(activity);
            this.mSkinDelegateMap.remove(activity);
        }
    }

    private void installLayoutFactory(Context context) {
        try {
            LayoutInflaterCompat.setFactory2(LayoutInflater.from(context), getSkinDelegate(context));
        } catch (Throwable unused) {
            Slog.i("SkinActivity", "A factory has already been set on this LayoutInflater");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SkinCompatDelegate getSkinDelegate(Context context) {
        if (this.mSkinDelegateMap == null) {
            this.mSkinDelegateMap = new WeakHashMap<>();
        }
        SkinCompatDelegate skinCompatDelegate = this.mSkinDelegateMap.get(context);
        if (skinCompatDelegate != null) {
            return skinCompatDelegate;
        }
        SkinCompatDelegate skinCompatDelegateCreate = SkinCompatDelegate.create(context);
        this.mSkinDelegateMap.put(context, skinCompatDelegateCreate);
        return skinCompatDelegateCreate;
    }

    private LazySkinObserver getObserver(Context context) {
        if (this.mSkinObserverMap == null) {
            this.mSkinObserverMap = new WeakHashMap<>();
        }
        LazySkinObserver lazySkinObserver = this.mSkinObserverMap.get(context);
        if (lazySkinObserver != null) {
            return lazySkinObserver;
        }
        LazySkinObserver lazySkinObserver2 = new LazySkinObserver(context);
        this.mSkinObserverMap.put(context, lazySkinObserver2);
        return lazySkinObserver2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindowBackground(Activity activity) {
        Drawable drawable;
        if (SkinCompatManager.getInstance().isSkinWindowBackgroundEnable()) {
            int windowBackgroundResId = SkinCompatThemeUtils.getWindowBackgroundResId(activity);
            if (SkinCompatHelper.checkResourceId(windowBackgroundResId) == 0 || (drawable = SkinCompatResources.getDrawable(activity, windowBackgroundResId)) == null) {
                return;
            }
            activity.getWindow().setBackgroundDrawable(drawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isContextSkinEnable(Context context) {
        return SkinCompatManager.getInstance().isSkinAllActivityEnable() || context.getClass().getAnnotation(Skinable.class) != null || (context instanceof SkinCompatSupportable);
    }

    private class LazySkinObserver implements SkinObserver {
        private final Context mContext;
        private boolean mMarkNeedUpdate = false;

        LazySkinObserver(Context context) {
            this.mContext = context;
        }

        @Override // skin.support.observe.SkinObserver
        public void updateSkin(SkinObservable skinObservable, Object obj) {
            if (SkinActivityLifecycle.this.mCurActivityRef == null || this.mContext == SkinActivityLifecycle.this.mCurActivityRef.get() || !(this.mContext instanceof Activity)) {
                updateSkinForce();
            } else {
                this.mMarkNeedUpdate = true;
            }
        }

        void updateSkinIfNeeded() {
            if (this.mMarkNeedUpdate) {
                updateSkinForce();
            }
        }

        void updateSkinForce() {
            if (Slog.DEBUG) {
                Slog.i(SkinActivityLifecycle.TAG, "Context: " + this.mContext + " updateSkinForce");
            }
            Context context = this.mContext;
            if (context == null) {
                return;
            }
            if ((context instanceof Activity) && SkinActivityLifecycle.this.isContextSkinEnable(context)) {
                SkinActivityLifecycle.this.updateWindowBackground((Activity) this.mContext);
            }
            SkinActivityLifecycle.this.getSkinDelegate(this.mContext).applySkin();
            Object obj = this.mContext;
            if (obj instanceof SkinCompatSupportable) {
                ((SkinCompatSupportable) obj).applySkin();
            }
            this.mMarkNeedUpdate = false;
        }
    }
}
