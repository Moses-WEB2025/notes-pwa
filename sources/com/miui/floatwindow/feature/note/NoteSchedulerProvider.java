package com.miui.floatwindow.feature.note;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.todo.base.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteSchedulerProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \b2\u00020\u0001:\u0001\bB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0005H\u0016J\b\u0010\u0007\u001a\u00020\u0005H\u0016¨\u0006\t"}, d2 = {"Lcom/miui/floatwindow/feature/note/NoteSchedulerProvider;", "Lcom/miui/todo/base/BaseSchedulerProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "computation", "Lio/reactivex/Scheduler;", "io", "ui", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteSchedulerProvider implements BaseSchedulerProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static NoteSchedulerProvider sInstance;

    public /* synthetic */ NoteSchedulerProvider(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private NoteSchedulerProvider() {
    }

    @Override // com.miui.todo.base.BaseSchedulerProvider
    public Scheduler computation() {
        Scheduler schedulerComputation = Schedulers.computation();
        Intrinsics.checkNotNullExpressionValue(schedulerComputation, "computation(...)");
        return schedulerComputation;
    }

    @Override // com.miui.todo.base.BaseSchedulerProvider
    public Scheduler io() {
        Scheduler schedulerIo = Schedulers.io();
        Intrinsics.checkNotNullExpressionValue(schedulerIo, "io(...)");
        return schedulerIo;
    }

    @Override // com.miui.todo.base.BaseSchedulerProvider
    public Scheduler ui() {
        Scheduler schedulerMainThread = AndroidSchedulers.mainThread();
        Intrinsics.checkNotNullExpressionValue(schedulerMainThread, "mainThread(...)");
        return schedulerMainThread;
    }

    /* JADX INFO: compiled from: NoteSchedulerProvider.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/floatwindow/feature/note/NoteSchedulerProvider$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sInstance", "Lcom/miui/floatwindow/feature/note/NoteSchedulerProvider;", "instance", "getInstance", "()Lcom/miui/floatwindow/feature/note/NoteSchedulerProvider;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final synchronized NoteSchedulerProvider getInstance() {
            NoteSchedulerProvider noteSchedulerProvider;
            if (NoteSchedulerProvider.sInstance == null) {
                NoteSchedulerProvider.sInstance = new NoteSchedulerProvider(null);
            }
            noteSchedulerProvider = NoteSchedulerProvider.sInstance;
            Intrinsics.checkNotNull(noteSchedulerProvider);
            return noteSchedulerProvider;
        }
    }
}
