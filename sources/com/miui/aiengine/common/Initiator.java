package com.miui.aiengine.common;

import android.app.Application;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.base.BaseApplicationInitiator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Initiator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lcom/miui/aiengine/common/Initiator;", "Lcom/miui/aiengine/common/base/BaseApplicationInitiator;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onAppInit", "", "app", "Landroid/app/Application;", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Initiator extends BaseApplicationInitiator {
    public Initiator() {
        super(NetConfig.TAG);
    }

    @Override // com.miui.aiengine.common.base.IInitiator
    public void onAppInit(Application app) {
        Intrinsics.checkNotNullParameter(app, "app");
        RequestManager.INSTANCE.init(app);
    }
}
