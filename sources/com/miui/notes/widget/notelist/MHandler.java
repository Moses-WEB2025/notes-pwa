package com.miui.notes.widget.notelist;

import android.os.Handler;
import android.os.Looper;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WidgetNoteListFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lcom/miui/notes/widget/notelist/MHandler;", "Landroid/os/Handler;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/os/Looper;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MHandler extends Handler {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MHandler(Looper looper) {
        super(looper);
        Intrinsics.checkNotNullParameter(looper, "looper");
    }
}
