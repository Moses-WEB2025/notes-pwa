package com.miui.handwritecommon.api;

import android.app.Application;
import android.util.Log;
import android.view.ViewGroup;
import com.miui.handwritecommon.api.callback.SinglePageRecListener;
import com.miui.handwritecommon.api.multipage.IMultiPageHandView;
import com.miui.handwritecommon.api.multipage.MultiPageRecListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes2.dex */
public class HandViewProvider {
    private static final String TAG = "HandViewProvider";
    private static IHandViewBusiness handViewBusiness;
    public static final AtomicBoolean init = new AtomicBoolean(false);
    public static final AtomicBoolean initEngine = new AtomicBoolean(false);

    private static IHandViewBusiness getBusiness() {
        Log.d(TAG, "getBusiness() 被调用，调用堆栈：\n" + Log.getStackTraceString(new Throwable()));
        if (handViewBusiness == null) {
            Log.d(TAG, "getBusiness: 开始初始化 IHandViewBusiness 实例");
            try {
                Class<?> cls = Class.forName("com.miui.handwritten.ext.HandViewBusinessImpl");
                Log.d(TAG, "getBusiness className: " + cls.getName());
                handViewBusiness = (IHandViewBusiness) cls.newInstance();
                Log.d(TAG, "getBusiness: 实例初始化成功，地址：" + handViewBusiness.hashCode());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Log.e(TAG, "getBusiness: 初始化失败", e);
                Log.d(TAG, "getBusiness: 异常堆栈：\n" + Log.getStackTraceString(e));
            }
        } else {
            Log.d(TAG, "getBusiness: 使用缓存实例，地址：" + handViewBusiness.hashCode());
        }
        return handViewBusiness;
    }

    public static ISinglePageHandView addSinglePageView(ViewGroup viewGroup, int i, boolean z, boolean z2) {
        return getBusiness().addSinglePageView(viewGroup, i, z, z2);
    }

    public static void singlePageWholeRecognize(String str, SinglePageRecListener singlePageRecListener) {
        getBusiness().singlePageWholeRecognize(str, singlePageRecListener);
    }

    public static IMultiPageHandView addMultiPageView(ViewGroup viewGroup) {
        return getBusiness().addMultiPageView(viewGroup);
    }

    public static void multiPageRecognize(long j, String str, MultiPageRecListener multiPageRecListener) {
        getBusiness().multiPageWholeRecognize(j, str, multiPageRecListener);
    }

    public static int getSinglePageWhiteBg() {
        return getBusiness().getSinglePageWhiteBg();
    }

    public static int getSinglePageDotBg() {
        return getBusiness().getSinglePageDotBg();
    }

    public static int getSinglePageBlackBg() {
        return getBusiness().getSinglePageBlackBg();
    }

    public static void init(final Application application) {
        if (init.get()) {
            return;
        }
        new Thread(new Runnable() { // from class: com.miui.handwritecommon.api.HandViewProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HandViewProvider.lambda$init$0(application);
            }
        }).start();
    }

    static /* synthetic */ void lambda$init$0(Application application) {
        init.set(true);
        Log.i(TAG, "handwrite init ..");
        getBusiness().attachBaseContext(application);
        getBusiness().applicationOnCreate(application);
        Log.i(TAG, "handwrite init finished");
    }

    public static void initRecognizedEngine(Application application) {
        AtomicBoolean atomicBoolean = initEngine;
        if (!atomicBoolean.get()) {
            atomicBoolean.set(true);
            Log.i(TAG, "handwrite init recognize engine..");
            getBusiness().initRecognizedEngine(application);
            return;
        }
        Log.d(TAG, "initRecognizedEngine is already done");
    }
}
