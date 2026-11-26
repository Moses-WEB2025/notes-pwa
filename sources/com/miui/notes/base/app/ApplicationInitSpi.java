package com.miui.notes.base.app;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;

/* JADX INFO: compiled from: ApplicationInitSpi.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lcom/miui/notes/base/app/ApplicationInitSpi;", "", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "isMainProcess", "", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface ApplicationInitSpi {
    void onInit(Context context, boolean isMainProcess);
}
