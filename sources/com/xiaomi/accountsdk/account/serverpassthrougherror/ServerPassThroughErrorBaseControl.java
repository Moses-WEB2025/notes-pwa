package com.xiaomi.accountsdk.account.serverpassthrougherror;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.xiaomi.accountsdk.account.serverpassthrougherror.base.ButtonLocalClickListener;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ButtonInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.ErrorHandleInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.NativePageInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ServerPassThroughErrorBaseControl {
    private static final String TAG = "ServerPassThroughErrorBaseControl";

    protected abstract Dialog createDialog(Activity activity, ErrorHandleInfo errorHandleInfo);

    protected abstract Map<String, NativePageInfo> getNativePageInfoMap();

    protected abstract boolean jumpToDefWebPageWhenNativeJumpErr(Activity activity, ButtonInfo buttonInfo);

    final void handleError(Activity activity, ErrorHandleInfo errorHandleInfo) {
        AccountLogger.log(TAG, "handleError>>>" + errorHandleInfo);
        if (activity == null || activity.isFinishing()) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$xiaomi$accountsdk$account$serverpassthrougherror$data$ErrorHandleInfo$HandleType[errorHandleInfo.handleType.ordinal()];
        if (i == 1) {
            showToast(activity, errorHandleInfo);
        } else if (i == 2) {
            showDialog(activity, errorHandleInfo);
        } else {
            handleNoneType(activity, errorHandleInfo);
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorBaseControl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$serverpassthrougherror$data$ErrorHandleInfo$HandleType;

        static {
            int[] iArr = new int[ErrorHandleInfo.HandleType.values().length];
            $SwitchMap$com$xiaomi$accountsdk$account$serverpassthrougherror$data$ErrorHandleInfo$HandleType = iArr;
            try {
                iArr[ErrorHandleInfo.HandleType.TOAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$serverpassthrougherror$data$ErrorHandleInfo$HandleType[ErrorHandleInfo.HandleType.DIALOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    protected void handleNoneType(Activity activity, ErrorHandleInfo errorHandleInfo) {
        AccountLogger.log(TAG, "handleNoneType>>>" + errorHandleInfo);
    }

    protected void showToast(Activity activity, ErrorHandleInfo errorHandleInfo) {
        Toast.makeText(activity.getApplicationContext(), errorHandleInfo.msgContent, 0).show();
    }

    protected void showDialog(Activity activity, ErrorHandleInfo errorHandleInfo) {
        Dialog dialogCreateDialog = createDialog(activity, errorHandleInfo);
        if (dialogCreateDialog == null) {
            return;
        }
        dialogCreateDialog.show();
    }

    public final void clickDialogClickable(Activity activity, ButtonInfo buttonInfo) {
        ButtonLocalClickListener localClickListener = buttonInfo.getLocalClickListener();
        if (localClickListener == null || !localClickListener.onClick(buttonInfo)) {
            tryJumpToNativePageOrDefWebPage(activity, buttonInfo);
        }
    }

    protected final void tryJumpToNativePageOrDefWebPage(Activity activity, ButtonInfo buttonInfo) {
        if (activity == null || activity.isFinishing() || buttonInfo == null || jumpToNativePage(activity, buttonInfo)) {
            return;
        }
        jumpToDefWebPage(activity, buttonInfo);
    }

    private boolean jumpToNativePage(Activity activity, ButtonInfo buttonInfo) {
        Map<String, NativePageInfo> nativePageInfoMap;
        NativePageInfo nativePageInfo;
        String nativePage = buttonInfo.getNativePage();
        if (TextUtils.isEmpty(nativePage) || (nativePageInfoMap = getNativePageInfoMap()) == null || (nativePageInfo = nativePageInfoMap.get(nativePage)) == null) {
            return false;
        }
        try {
            activity.startActivity(createJumpIntent(activity, nativePageInfo, buttonInfo.getPageParams()));
            return true;
        } catch (Exception e) {
            AccountLogger.log(TAG, "jumpToNativePage>>>err:" + e.getMessage() + "   " + buttonInfo);
            return false;
        }
    }

    private boolean jumpToDefWebPage(Activity activity, ButtonInfo buttonInfo) {
        if (TextUtils.isEmpty(buttonInfo.getExtraWebUrl())) {
            return false;
        }
        return jumpToDefWebPageWhenNativeJumpErr(activity, buttonInfo);
    }

    protected final Intent createJumpIntent(Activity activity, NativePageInfo nativePageInfo, Map<String, Object> map) {
        if (nativePageInfo.customJumpIntentInterface != null) {
            return nativePageInfo.customJumpIntentInterface.getCustomIntent(activity, nativePageInfo, map);
        }
        Intent intent = new Intent(activity, nativePageInfo.pageClass);
        intent.putExtras(createJumpPageParamsBundle(map));
        return intent;
    }

    public static Bundle createJumpPageParamsBundle(Map<String, Object> map) {
        Bundle bundle = new Bundle();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    bundle.putString(key, (String) value);
                } else if (value instanceof Boolean) {
                    bundle.putBoolean(key, ((Boolean) value).booleanValue());
                } else if (value instanceof Integer) {
                    bundle.putInt(key, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    bundle.putLong(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    bundle.putFloat(key, ((Float) value).floatValue());
                } else if (value instanceof Double) {
                    bundle.putDouble(key, ((Double) value).doubleValue());
                } else if (value instanceof int[]) {
                    bundle.putIntArray(key, (int[]) value);
                } else if (value instanceof long[]) {
                    bundle.putLongArray(key, (long[]) value);
                } else if (value instanceof String[]) {
                    bundle.putStringArray(key, (String[]) value);
                } else if (value instanceof boolean[]) {
                    bundle.putBooleanArray(key, (boolean[]) value);
                } else if (value instanceof float[]) {
                    bundle.putFloatArray(key, (float[]) value);
                } else if (value instanceof double[]) {
                    bundle.putDoubleArray(key, (double[]) value);
                }
            }
        }
        return bundle;
    }
}
