package com.miui.common.tool;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.softinputhelper.BaseKeyboardHeightProvider;
import com.miui.common.tool.softinputhelper.KeyboardHeightListener;
import com.miui.common.tool.softinputhelper.PhoneHybridKeyboardHeightProvider;
import com.miui.common.tool.softinputhelper.QKeyboardHeightProvider;
import com.miui.common.tool.softinputhelper.RKeyboardHeightProvider;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KeyboardHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B/\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bJ\u0006\u0010\u0015\u001a\u00020\u000eJ\u0006\u0010\u0016\u001a\u00020\u000eR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0018"}, d2 = {"Lcom/miui/common/tool/KeyboardHelper;", "", "content", "Landroid/view/View;", "decorView", "listener", "Lcom/miui/common/tool/softinputhelper/KeyboardHeightListener;", "isHybridEditor", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;Landroid/view/View;Lcom/miui/common/tool/softinputhelper/KeyboardHeightListener;Z)V", "mHeightProvider", "Lcom/miui/common/tool/softinputhelper/BaseKeyboardHeightProvider;", "setEnable", "", "enable", "setForceMode", "imeHeight", "", "getImeHeight", "()I", "update", "release", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class KeyboardHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static int IME_MASK = 8;
    private static boolean ShowKeyboard;
    private BaseKeyboardHeightProvider mHeightProvider;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KeyboardHelper(View view, View view2, KeyboardHeightListener listener) {
        this(view, view2, listener, false, 8, null);
        Intrinsics.checkNotNullParameter(listener, "listener");
    }

    @JvmStatic
    public static final boolean isHardwareKeyboard(Context context) {
        return INSTANCE.isHardwareKeyboard(context);
    }

    @JvmStatic
    public static final boolean isKeyboardVisibleInMultiWindowMode(Activity activity) {
        return INSTANCE.isKeyboardVisibleInMultiWindowMode(activity);
    }

    public KeyboardHelper(View view, View view2, KeyboardHeightListener listener, boolean z) {
        QKeyboardHeightProvider qKeyboardHeightProvider;
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (UIUtils.isAboveAndroidR()) {
            if (z && com.miui.notes.ai.utils.RomUtils.isPhoneDevice()) {
                qKeyboardHeightProvider = new PhoneHybridKeyboardHeightProvider(view, view2, listener);
            } else {
                qKeyboardHeightProvider = new RKeyboardHeightProvider(view, view2, listener);
            }
        } else {
            qKeyboardHeightProvider = new QKeyboardHeightProvider(view, view2, listener);
        }
        this.mHeightProvider = qKeyboardHeightProvider;
    }

    public /* synthetic */ KeyboardHelper(View view, View view2, KeyboardHeightListener keyboardHeightListener, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, view2, keyboardHeightListener, (i & 8) != 0 ? false : z);
    }

    public final void setEnable(boolean enable) {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        Intrinsics.checkNotNull(baseKeyboardHeightProvider);
        baseKeyboardHeightProvider.setEnable(enable);
    }

    public final void setForceMode(boolean enable) {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        Intrinsics.checkNotNull(baseKeyboardHeightProvider);
        baseKeyboardHeightProvider.setForceMode(enable);
    }

    public final int getImeHeight() {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        Intrinsics.checkNotNull(baseKeyboardHeightProvider);
        return baseKeyboardHeightProvider.getImeHeight();
    }

    public final void update() {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        if (baseKeyboardHeightProvider != null) {
            Intrinsics.checkNotNull(baseKeyboardHeightProvider);
            baseKeyboardHeightProvider.update();
        }
    }

    public final void release() {
        BaseKeyboardHeightProvider baseKeyboardHeightProvider = this.mHeightProvider;
        Intrinsics.checkNotNull(baseKeyboardHeightProvider);
        baseKeyboardHeightProvider.release();
    }

    /* JADX INFO: compiled from: KeyboardHelper.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0010\u001a\u00020\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0007R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0016"}, d2 = {"Lcom/miui/common/tool/KeyboardHelper$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "IME_MASK", "", "getIME_MASK", "()I", "setIME_MASK", "(I)V", "ShowKeyboard", "", "getShowKeyboard", "()Z", "setShowKeyboard", "(Z)V", "isKeyboardVisibleInMultiWindowMode", "activity", "Landroid/app/Activity;", "isHardwareKeyboard", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getIME_MASK() {
            return KeyboardHelper.IME_MASK;
        }

        public final void setIME_MASK(int i) {
            KeyboardHelper.IME_MASK = i;
        }

        public final boolean getShowKeyboard() {
            return KeyboardHelper.ShowKeyboard;
        }

        public final void setShowKeyboard(boolean z) {
            KeyboardHelper.ShowKeyboard = z;
        }

        @JvmStatic
        public final boolean isKeyboardVisibleInMultiWindowMode(Activity activity) {
            if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView().getRootWindowInsets() == null) {
                return false;
            }
            return activity.getWindow().getDecorView().getRootWindowInsets().isVisible(getIME_MASK());
        }

        @JvmStatic
        public final boolean isHardwareKeyboard(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return context.getResources().getConfiguration().keyboard == 2;
        }
    }
}
