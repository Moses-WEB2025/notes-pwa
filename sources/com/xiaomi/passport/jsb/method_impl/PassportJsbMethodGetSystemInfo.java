package com.xiaomi.passport.jsb.method_impl;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.account.privacy_data.lib.ReflectionCalls;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import com.xiaomi.verificationsdk.internal.CheckRoot;
import com.xiaomi.verificationsdk.internal.CheckVirtual;
import com.xiaomi.verificationsdk.internal.UnknownValueException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGetSystemInfo extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context context = passportJsbWebView.getContext();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("isRooted", CheckRoot.isDeviceRooted());
            try {
                jSONObject2.put("isVirtualMachine", CheckVirtual.isRunInVirtual());
            } catch (UnknownValueException unused) {
            }
            jSONObject2.put("locale", Locale.getDefault().toString());
            jSONObject2.put("romBuildModel", Build.MODEL);
            jSONObject2.put("romBuildDevice", Build.DEVICE);
            jSONObject2.put("romAndroidVersionName", Build.VERSION.RELEASE);
            jSONObject2.put("romAndroidSDKCode", Build.VERSION.SDK_INT);
            jSONObject2.put("romVersionName", Build.VERSION.INCREMENTAL);
            try {
                Class<?> cls = Class.forName("miui.os.Build");
                jSONObject2.put("romMIUIIsInternational", ReflectionCalls.getStaticField(cls, "IS_INTERNATIONAL_BUILD"));
                jSONObject2.put("romMIUIIsTablet", ReflectionCalls.getStaticField(cls, "IS_TABLET"));
                jSONObject2.put("romMIUIRegion", ReflectionCalls.callStaticMethod("miui.os.Build", "getRegion", new Object[0]));
            } catch (ClassNotFoundException unused2) {
            }
            Object objCallStaticMethod = ReflectionCalls.callStaticMethod("android.os.SystemProperties", CallMethod.METHOD_GET, "ro.mi.os.version.incremental");
            if ((objCallStaticMethod instanceof String) && !TextUtils.isEmpty((String) objCallStaticMethod)) {
                jSONObject2.put("romOSIncremental", objCallStaticMethod);
            }
            Object objCallStaticMethod2 = ReflectionCalls.callStaticMethod("android.os.SystemProperties", CallMethod.METHOD_GET, "ro.mi.os.version.code");
            if ((objCallStaticMethod2 instanceof String) && !TextUtils.isEmpty((String) objCallStaticMethod2)) {
                jSONObject2.put("romOSVersionCode", objCallStaticMethod2);
            }
            Object objCallStaticMethod3 = ReflectionCalls.callStaticMethod("android.os.SystemProperties", CallMethod.METHOD_GET, "ro.mi.os.version.name");
            if ((objCallStaticMethod3 instanceof String) && !TextUtils.isEmpty((String) objCallStaticMethod3)) {
                jSONObject2.put("romOSVersionName", objCallStaticMethod3);
            }
            Object objCallStaticMethod4 = ReflectionCalls.callStaticMethod("android.os.SystemProperties", CallMethod.METHOD_GET, "ro.miui.ui.version.name");
            if (objCallStaticMethod4 instanceof String) {
                jSONObject2.put("romMIUIVersionName", objCallStaticMethod4);
            }
            jSONObject2.put("inMIUIProvisionStep", Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 1);
            jSONObject2.put("userSpaceId", XMPassportSettings.getUserHandleId());
            return new PassportJsbMethodResult(jSONObject2);
        } catch (JSONException unused3) {
            throw new PassportJsbMethodException(105, "should never happen");
        }
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "getSystemInfo";
    }
}
