package com.miui.notes.page.viewmodel;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: OperationResult.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/miui/notes/page/viewmodel/Operation;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "DELETE", "HIDE", "MOVE", "STICK", "UN_HIDE", "AllCheck", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public enum Operation {
    DELETE,
    HIDE,
    MOVE,
    STICK,
    UN_HIDE,
    AllCheck;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<Operation> getEntries() {
        return $ENTRIES;
    }
}
