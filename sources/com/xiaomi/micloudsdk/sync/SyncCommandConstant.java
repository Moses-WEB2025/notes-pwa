package com.xiaomi.micloudsdk.sync;

import android.util.ArrayMap;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SyncCommandConstant {
    public static final String ACTION_SYNC_COMMAND = "com.xiaomi.micloud.action.SYNC_COMMAND";
    public static final String KEY_COMMAND = "key_command";
    static final Map<String, String> SUPPORT_COMMAND_PKGS;
    public static final String VALUE_COMMAND_CANCEL_SYNC = "value_command_cancel_sync";

    private SyncCommandConstant() {
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        SUPPORT_COMMAND_PKGS = arrayMap;
        arrayMap.put("com.miui.gallery.cloud.provider", TapShareConstant.GALLERY_PACKAGE_NAME);
    }
}
