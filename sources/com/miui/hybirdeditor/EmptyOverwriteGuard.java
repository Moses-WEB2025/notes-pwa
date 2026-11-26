package com.miui.hybirdeditor;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;

/* JADX INFO: compiled from: EmptyOverwriteGuard.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J2\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/miui/hybirdeditor/EmptyOverwriteGuard;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "USER_EDIT_VALID_WINDOW_MS", "", "NO_USER_EDIT", "decide", "Lcom/miui/hybirdeditor/EmptyOverwriteGuard$Decision;", "newDataIsEmpty", "", "originDataIsEmpty", "dataFedToFrontend", "userEditAgoMs", "windowMs", "Decision", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class EmptyOverwriteGuard {
    public static final EmptyOverwriteGuard INSTANCE = new EmptyOverwriteGuard();
    public static final long NO_USER_EDIT = -1;
    public static final long USER_EDIT_VALID_WINDOW_MS = 3000;

    @JvmStatic
    public static final Decision decide(boolean z, boolean z2, boolean z3, long j) {
        return decide$default(z, z2, z3, j, 0L, 16, null);
    }

    private EmptyOverwriteGuard() {
    }

    /* JADX INFO: compiled from: EmptyOverwriteGuard.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lcom/miui/hybirdeditor/EmptyOverwriteGuard$Decision;", "", "accepted", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;IZ)V", "getAccepted", "()Z", "ACCEPT_NOT_EMPTY", "ACCEPT_ORIGIN_ALREADY_EMPTY", "ACCEPT_USER_DRIVEN", "REJECT_FRONTEND_NOT_FED", "REJECT_NOT_USER_DRIVEN", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public enum Decision {
        ACCEPT_NOT_EMPTY(true),
        ACCEPT_ORIGIN_ALREADY_EMPTY(true),
        ACCEPT_USER_DRIVEN(true),
        REJECT_FRONTEND_NOT_FED(false),
        REJECT_NOT_USER_DRIVEN(false);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean accepted;

        public static EnumEntries<Decision> getEntries() {
            return $ENTRIES;
        }

        Decision(boolean z) {
            this.accepted = z;
        }

        public final boolean getAccepted() {
            return this.accepted;
        }
    }

    public static /* synthetic */ Decision decide$default(boolean z, boolean z2, boolean z3, long j, long j2, int i, Object obj) {
        if ((i & 16) != 0) {
            j2 = USER_EDIT_VALID_WINDOW_MS;
        }
        return decide(z, z2, z3, j, j2);
    }

    @JvmStatic
    public static final Decision decide(boolean newDataIsEmpty, boolean originDataIsEmpty, boolean dataFedToFrontend, long userEditAgoMs, long windowMs) {
        if (!newDataIsEmpty) {
            return Decision.ACCEPT_NOT_EMPTY;
        }
        if (originDataIsEmpty) {
            return Decision.ACCEPT_ORIGIN_ALREADY_EMPTY;
        }
        if (!dataFedToFrontend) {
            return Decision.REJECT_FRONTEND_NOT_FED;
        }
        if (userEditAgoMs < 0 || userEditAgoMs > windowMs) {
            return Decision.REJECT_NOT_USER_DRIVEN;
        }
        return Decision.ACCEPT_USER_DRIVEN;
    }
}
