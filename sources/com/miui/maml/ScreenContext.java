package com.miui.maml;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.miui.maml.data.ContextVariables;
import com.miui.maml.data.Variables;
import com.miui.maml.elements.ScreenElementFactory;
import com.miui.maml.util.Utils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class ScreenContext {
    public final Context mContext;
    public final ContextVariables mContextVariables;
    public final ScreenElementFactory mFactory;
    private final Handler mHandler;
    private HashMap<String, ObjectFactory> mObjectFactories;
    public final ResourceManager mResourceManager;
    public final Variables mVariables;

    public ScreenContext(Context context, ResourceManager resourceManager) {
        this(context, resourceManager, new ScreenElementFactory());
    }

    public ScreenContext(Context context, ResourceLoader resourceLoader) {
        this(context, resourceLoader, new ScreenElementFactory());
    }

    public ScreenContext(Context context, ResourceLoader resourceLoader, ScreenElementFactory screenElementFactory) {
        this(context, new ResourceManager(resourceLoader), screenElementFactory);
    }

    public ScreenContext(Context context, ResourceManager resourceManager, ScreenElementFactory screenElementFactory) {
        this(context, resourceManager, screenElementFactory, new Variables());
    }

    public ScreenContext(Context context, ResourceManager resourceManager, ScreenElementFactory screenElementFactory, Variables variables) {
        Context applicationContext = context.getApplicationContext();
        Utils.initContextIfNeed(applicationContext);
        this.mContext = applicationContext != null ? applicationContext : context;
        this.mResourceManager = resourceManager;
        this.mFactory = screenElementFactory;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mVariables = variables;
        this.mContextVariables = new ContextVariables();
    }

    public boolean postDelayed(Runnable runnable, long j) {
        return this.mHandler.postDelayed(runnable, j);
    }

    public void removeCallbacks(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public final synchronized void registerObjectFactory(String str, ObjectFactory objectFactory) {
        try {
            if (objectFactory == null) {
                HashMap<String, ObjectFactory> map = this.mObjectFactories;
                if (map != null) {
                    map.remove(str);
                }
            } else {
                if (!str.equals(objectFactory.getName())) {
                    throw new IllegalArgumentException("ObjectFactory name mismatchs " + str);
                }
                if (this.mObjectFactories == null) {
                    this.mObjectFactories = new HashMap<>();
                }
                ObjectFactory objectFactory2 = this.mObjectFactories.get(str);
                for (ObjectFactory old = objectFactory2; old != null; old = old.getOld()) {
                    if (old == objectFactory) {
                        return;
                    }
                }
                objectFactory.setOld(objectFactory2);
                this.mObjectFactories.put(str, objectFactory);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized <T extends ObjectFactory> T getObjectFactory(String str) {
        T t;
        t = null;
        try {
            HashMap<String, ObjectFactory> map = this.mObjectFactories;
            if (map != null) {
                t = (T) map.get(str);
            }
        } catch (ClassCastException unused) {
            return null;
        }
        return t;
    }
}
