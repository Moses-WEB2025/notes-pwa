package com.miui.pc.model;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.R;
import com.miui.notes.model.FolderModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PadFolderModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0007¢\u0006\u0004\b\u0002\u0010\u0003R(\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0004\u001a\u0004\u0018\u00010\u00058V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Lcom/miui/pc/model/PadFolderModel;", "Lcom/miui/notes/model/FolderModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "subject", "", "getSubject", "()Ljava/lang/String;", "setSubject", "(Ljava/lang/String;)V", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PadFolderModel extends FolderModel {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // com.miui.notes.model.FolderModel
    public String getSubject() {
        long id = getId();
        return id > 0 ? super.getSubject() : INSTANCE.getSystemSubject(id);
    }

    @Override // com.miui.notes.model.FolderModel
    public void setSubject(String str) {
        super.setSubject(str);
    }

    /* JADX INFO: compiled from: PadFolderModel.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lcom/miui/pc/model/PadFolderModel$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getSystemSubject", "", "folderId", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getSystemSubject(long folderId) {
            if (folderId == -2) {
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.call_folder_name);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return string;
            }
            if (folderId == -3) {
                String string2 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.trash_folder_name);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return string2;
            }
            if (folderId == 0) {
                String string3 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.unclassfied_folder_name);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                return string3;
            }
            if (folderId == -4097) {
                String string4 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.pc_all_folder_name);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                return string4;
            }
            if (folderId == -4) {
                String string5 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.hidden_notes_fragment_title);
                Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
                return string5;
            }
            if (folderId == -5) {
                String string6 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.hear_note);
                Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
                return string6;
            }
            return "";
        }
    }
}
