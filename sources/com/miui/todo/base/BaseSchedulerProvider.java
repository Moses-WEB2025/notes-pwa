package com.miui.todo.base;

import io.reactivex.Scheduler;
import kotlin.Metadata;

/* JADX INFO: compiled from: BaseSchedulerProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&¨\u0006\u0006À\u0006\u0003"}, d2 = {"Lcom/miui/todo/base/BaseSchedulerProvider;", "", "computation", "Lio/reactivex/Scheduler;", "io", "ui", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface BaseSchedulerProvider {
    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
