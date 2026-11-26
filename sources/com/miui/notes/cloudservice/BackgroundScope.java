package com.miui.notes.cloudservice;

import ch.qos.logback.core.joran.action.Action;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* JADX INFO: compiled from: BackgroundScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/miui/notes/cloudservice/BackgroundScope;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", Action.SCOPE_ATTRIBUTE, "Lkotlinx/coroutines/CoroutineScope;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BackgroundScope {
    public static final BackgroundScope INSTANCE = new BackgroundScope();
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getIO()));

    private BackgroundScope() {
    }

    public final CoroutineScope getScope() {
        return scope;
    }
}
