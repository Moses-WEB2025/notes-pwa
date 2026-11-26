package com.miui.notes.base.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AccessibilityDelegateUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0012\u0010\u0019\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R2\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\n\u0010\u0003\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/base/accessibility/AccessibilityDelegateUtil;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "Lcom/miui/notes/base/accessibility/AccessibilityWidgetType;", "", "getCallback$annotations", "getCallback", "()Lkotlin/jvm/functions/Function1;", "setCallback", "(Lkotlin/jvm/functions/Function1;)V", "registerAccessibilityWidgetTypes", "", "setButtonAccessibilityDelegate", "view", "Landroid/view/View;", "setDoneAccessibilityDelegate", "setCheckBoxAccessibilityDelegate", "setSelectedAccessibilityDelegate", "type", "", "setButtonClassAccessibilityDelegate", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AccessibilityDelegateUtil {
    public static final AccessibilityDelegateUtil INSTANCE = new AccessibilityDelegateUtil();
    private static final String TAG = "AccessibilityDelegateUtil";
    private static Function1<? super AccessibilityWidgetType, Integer> callback;

    @JvmStatic
    public static /* synthetic */ void getCallback$annotations() {
    }

    private AccessibilityDelegateUtil() {
    }

    public static final Function1<AccessibilityWidgetType, Integer> getCallback() {
        return callback;
    }

    public static final void setCallback(Function1<? super AccessibilityWidgetType, Integer> function1) {
        callback = function1;
    }

    public final void registerAccessibilityWidgetTypes(Function1<? super AccessibilityWidgetType, Integer> callback2) {
        Intrinsics.checkNotNullParameter(callback2, "callback");
        callback = callback2;
    }

    @JvmStatic
    public static final void setButtonAccessibilityDelegate(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Function1<? super AccessibilityWidgetType, Integer> function1 = callback;
        if (function1 != null) {
            AccessibilityDelegate accessibilityDelegate = new AccessibilityDelegate(function1.invoke(AccessibilityWidgetType.BUTTON).intValue());
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
            if (accessibilityNodeInfoCreateAccessibilityNodeInfo == null) {
                return;
            }
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCreateAccessibilityNodeInfo);
            view.setAccessibilityDelegate(accessibilityDelegate);
            return;
        }
        Logger.INSTANCE.e(TAG, "BUTTON Skip accessibility: callback is null");
    }

    @JvmStatic
    public static final void setDoneAccessibilityDelegate(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Function1<? super AccessibilityWidgetType, Integer> function1 = callback;
        if (function1 != null) {
            AccessibilityDelegate accessibilityDelegate = new AccessibilityDelegate(function1.invoke(AccessibilityWidgetType.DONE).intValue());
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
            Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCreateAccessibilityNodeInfo);
            view.setAccessibilityDelegate(accessibilityDelegate);
            return;
        }
        Logger.INSTANCE.e(TAG, "DONE Skip accessibility: callback is null");
    }

    @JvmStatic
    public static final void setCheckBoxAccessibilityDelegate(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Function1<? super AccessibilityWidgetType, Integer> function1 = callback;
        if (function1 != null) {
            AccessibilityDelegate accessibilityDelegate = new AccessibilityDelegate(function1.invoke(AccessibilityWidgetType.CHECKBOX).intValue());
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
            Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCreateAccessibilityNodeInfo);
            view.setAccessibilityDelegate(accessibilityDelegate);
            return;
        }
        Logger.INSTANCE.e(TAG, "CHECKBOX Skip accessibility: callback is null");
    }

    @JvmStatic
    public static final void setSelectedAccessibilityDelegate(View view, boolean type) {
        Intrinsics.checkNotNullParameter(view, "view");
        SelectAccessibilityDelegate selectAccessibilityDelegate = new SelectAccessibilityDelegate(type);
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
        Intrinsics.checkNotNull(accessibilityNodeInfoCreateAccessibilityNodeInfo);
        selectAccessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCreateAccessibilityNodeInfo);
        view.setAccessibilityDelegate(selectAccessibilityDelegate);
    }

    @JvmStatic
    public static final void setButtonClassAccessibilityDelegate(View view) {
        if (view != null) {
            view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.notes.base.accessibility.AccessibilityDelegateUtil.setButtonClassAccessibilityDelegate.1
                @Override // android.view.View.AccessibilityDelegate
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                    Intrinsics.checkNotNullParameter(host, "host");
                    Intrinsics.checkNotNullParameter(info, "info");
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setClassName("android.widget.Button");
                }
            });
        }
    }
}
