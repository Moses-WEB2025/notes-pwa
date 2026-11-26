package com.miui.todo.utils;

import com.miui.todo.base.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* JADX INFO: loaded from: classes4.dex */
public class SchedulerProvider implements BaseSchedulerProvider {
    private static SchedulerProvider sInstance;

    private SchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (sInstance == null) {
            sInstance = new SchedulerProvider();
        }
        return sInstance;
    }

    @Override // com.miui.todo.base.BaseSchedulerProvider
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override // com.miui.todo.base.BaseSchedulerProvider
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override // com.miui.todo.base.BaseSchedulerProvider
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    public void release() {
        sInstance.io().shutdown();
        sInstance.computation().shutdown();
        sInstance.ui().shutdown();
    }
}
