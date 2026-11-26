package com.miui.common.tool.softinputhelper;

import android.view.View;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.RomUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SoftKeyboardInputHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\tH\u0007¨\u0006\n"}, d2 = {"Lcom/miui/common/tool/softinputhelper/SoftKeyboardInputHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "boostKeyboardForFocusView", "", "view", "Landroid/view/View;", "onFailed", "Lkotlin/Function1;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SoftKeyboardInputHelper {
    public static final SoftKeyboardInputHelper INSTANCE = new SoftKeyboardInputHelper();

    private SoftKeyboardInputHelper() {
    }

    @JvmStatic
    public static final void boostKeyboardForFocusView(View view, Function1<? super Unit, Unit> onFailed) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(onFailed, "onFailed");
        RomUtils.INSTANCE.getHyperOsVersion();
        onFailed.invoke(Unit.INSTANCE);
    }
}
