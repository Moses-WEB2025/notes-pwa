package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: BaseAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\tH\u0016J\b\u0010\u000b\u001a\u00020\tH\u0016J\b\u0010\f\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\r"}, d2 = {"Lcom/miui/doodle/feature/action/BaseAction;", "Lcom/miui/doodle/feature/action/IAction;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "enableCacheToFile", "", "getEnableCacheToFile", "()Z", "cacheToFile", "", "restoreFromFile", "releaseJobs", "destroy", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseAction implements IAction {
    private final boolean enableCacheToFile = true;

    @Override // com.miui.doodle.feature.action.IAction
    public void cacheToFile() {
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void destroy() {
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void releaseJobs() {
    }

    @Override // com.miui.doodle.feature.action.IAction
    public void restoreFromFile() {
    }

    protected final boolean getEnableCacheToFile() {
        return this.enableCacheToFile;
    }
}
