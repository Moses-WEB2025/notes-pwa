package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionContext;
import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH¦@¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lcom/google/android/appfunctions/schema/clock/alarms/v1/FindAlarms;", "", "findAlarms", "", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/Alarm;", "appFunctionContext", "Landroidx/appfunctions/AppFunctionContext;", "findAlarmsParams", "Lcom/google/android/appfunctions/schema/clock/alarms/v1/FindAlarmsParams;", "(Landroidx/appfunctions/AppFunctionContext;Lcom/google/android/appfunctions/schema/clock/alarms/v1/FindAlarmsParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.clock.alarms.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public interface FindAlarms {
    Object findAlarms(AppFunctionContext appFunctionContext, FindAlarmsParams findAlarmsParams, Continuation<? super List<Alarm>> continuation);
}
