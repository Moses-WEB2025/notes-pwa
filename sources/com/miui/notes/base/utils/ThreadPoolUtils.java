package com.miui.notes.base.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThreadPoolUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/miui/notes/base/utils/ThreadPoolUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "backgroundWorker", "Ljava/util/concurrent/ExecutorService;", "getBackgroundWorker", "()Ljava/util/concurrent/ExecutorService;", "backgroundWorker$delegate", "Lkotlin/Lazy;", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ThreadPoolUtils {
    public static final ThreadPoolUtils INSTANCE = new ThreadPoolUtils();

    /* JADX INFO: renamed from: backgroundWorker$delegate, reason: from kotlin metadata */
    private static final Lazy backgroundWorker = LazyKt.lazy(new Function0() { // from class: com.miui.notes.base.utils.ThreadPoolUtils$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ThreadPoolUtils.backgroundWorker_delegate$lambda$0();
        }
    });

    private ThreadPoolUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExecutorService backgroundWorker_delegate$lambda$0() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    public final ExecutorService getBackgroundWorker() {
        Object value = backgroundWorker.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (ExecutorService) value;
    }
}
