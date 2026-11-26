package com.xiaomi.xms.ai.recorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UIKit.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/xms/ai/recorder/UIKit;", "", "()V", "openRecordUI", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "AIRecorderSDK_debug"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class UIKit {
    public static final UIKit INSTANCE = new UIKit();

    private UIKit() {
    }

    @JvmStatic
    public static final void openRecordUI(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intent intent = new Intent("miui.intent.action.recorder.record");
        intent.setPackage(Constants.RECORDER_PACKAGE_NAME);
        Bundle bundle = new Bundle();
        bundle.putBoolean("extra_from_component", true);
        Log.d("AIRecorder", "openRecordUI " + context.getPackageName());
        bundle.putString("extra_from_package_name", context.getPackageName());
        intent.putExtra("extra_data_bundle", bundle);
        ContextCompat.startActivity(context, intent, null);
    }
}
