package xc.engine.core.graphics.engine.core;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BaseEngineStruct.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\tJ\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\b\u0010\u000e\u001a\u00020\tH\u0014R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lxc/engine/core/graphics/engine/core/BaseStruct;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "value", "", "isInitialized", "()Z", "init", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "destroy", "onInit", "onDestroy", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseStruct {
    private volatile boolean isInitialized;

    protected void onDestroy() {
    }

    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: renamed from: isInitialized, reason: from getter */
    public final boolean getIsInitialized() {
        return this.isInitialized;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.isInitialized) {
            return;
        }
        onInit(context);
        this.isInitialized = true;
    }

    public final void destroy() {
        if (this.isInitialized) {
            onDestroy();
            this.isInitialized = false;
        }
    }
}
