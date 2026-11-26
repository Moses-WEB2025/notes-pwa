package com.miui.notes.ai.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import java.util.Base64;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: PackageNameUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0005J\u0010\u0010\u000b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/notes/ai/utils/PackageNameUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "WECHAT_ENCODED_PACKAGE_NAME", "DIANPING_ENCODED_PACKAGE_NAME", "isWechatPackage", "", "packageName", "isDianPingPackage", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PackageNameUtils {
    private static final String DIANPING_ENCODED_PACKAGE_NAME = "Y29tLmRpYW5waW5nLnYx";
    public static final PackageNameUtils INSTANCE = new PackageNameUtils();
    private static final String TAG = "PackageNameUtils";
    private static final String WECHAT_ENCODED_PACKAGE_NAME = "Y29tLnRlbmNlbnQubW0=";

    private PackageNameUtils() {
    }

    public final boolean isWechatPackage(String packageName) {
        try {
            byte[] bArrDecode = Base64.getDecoder().decode(WECHAT_ENCODED_PACKAGE_NAME);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
            boolean zAreEqual = Intrinsics.areEqual(new String(bArrDecode, Charsets.UTF_8), packageName);
            Logger.INSTANCE.d(TAG, "isWechatPackage " + zAreEqual);
            return zAreEqual;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "isWechatPackage error, e: " + e);
            return false;
        }
    }

    public final boolean isDianPingPackage(String packageName) {
        try {
            byte[] bArrDecode = Base64.getDecoder().decode(DIANPING_ENCODED_PACKAGE_NAME);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
            boolean zAreEqual = Intrinsics.areEqual(new String(bArrDecode, Charsets.UTF_8), packageName);
            Logger.INSTANCE.d(TAG, "isDianPingPackage " + zAreEqual);
            return zAreEqual;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "isDianPingPackage error, e: " + e);
            return false;
        }
    }
}
