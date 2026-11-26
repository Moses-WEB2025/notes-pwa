package com.google.android.appfunctions.schema.clock.stopwatch.v1;

import android.app.PendingIntent;
import androidx.appfunctions.AppFunctionContext;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H¦@¢\u0006\u0002\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/stopwatch/v1/ShowStopwatch;", "", "showStopwatch", "Landroid/app/PendingIntent;", "appFunctionContext", "Landroidx/appfunctions/AppFunctionContext;", "(Landroidx/appfunctions/AppFunctionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.stopwatch.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public interface ShowStopwatch {
    Object showStopwatch(AppFunctionContext appFunctionContext, Continuation<? super PendingIntent> continuation);
}
