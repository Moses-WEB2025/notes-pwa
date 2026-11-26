package com.miui.notes.model;

import android.util.SparseIntArray;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FolderModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\b\u0016\u0018\u0000 (2\u00020\u0001:\u0001(B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0007\"\u0004\b\u001a\u0010\tR\u001a\u0010\u001b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0007\"\u0004\b\u001d\u0010\tR\u001a\u0010\u001e\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0007\"\u0004\b \u0010\tR(\u0010!\u001a\u0004\u0018\u00010\u000e2\b\u0010!\u001a\u0004\u0018\u00010\u000e8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b'\u0010\u0012¨\u0006)"}, d2 = {"Lcom/miui/notes/model/FolderModel;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "id", "", "getId", "()J", "setId", "(J)V", MiCloudConstants.PDC.J_PARENT_ID, "getParentId", "setParentId", "mSubject", "", "count", "", "getCount", "()I", "setCount", "(I)V", "type", "getType", "setType", "createdAt", "getCreatedAt", "setCreatedAt", "updatedAt", "getUpdatedAt", "setUpdatedAt", "stickAt", "getStickAt", "setStickAt", "subject", "getSubject", "()Ljava/lang/String;", "setSubject", "(Ljava/lang/String;)V", "iconResource", "getIconResource", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class FolderModel {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int FAKE_FOLDER_ID_BASE = -4096;
    private static final SparseIntArray ICON_SYSTEM_FOLDER_RESOURCES;
    public static final int ID_ALL_NOTES_FOLDER = -4097;
    public static final int ID_INVALID_FOLDER = -4098;
    private static final String TAG = "FolderModel";
    private int count;
    private long createdAt;
    private long id;
    private String mSubject;
    private long parentId;
    private long stickAt;
    private int type;
    private long updatedAt;

    @JvmStatic
    public static final boolean canCreateNote(long j) {
        return INSTANCE.canCreateNote(j);
    }

    @JvmStatic
    public static final String getSystemSubject(long j) {
        return INSTANCE.getSystemSubject(j);
    }

    @JvmStatic
    public static final boolean isCannotMoveFolder(long j) {
        return INSTANCE.isCannotMoveFolder(j);
    }

    @JvmStatic
    public static final boolean isCustomFolder(long j) {
        return INSTANCE.isCustomFolder(j);
    }

    @JvmStatic
    public static final boolean isFakeFolder(long j) {
        return INSTANCE.isFakeFolder(j);
    }

    @JvmStatic
    public static final boolean isSpecialFolder(long j) {
        return INSTANCE.isSpecialFolder(j);
    }

    @JvmStatic
    public static final boolean isSupportCreateMindNote(long j) {
        return INSTANCE.isSupportCreateMindNote(j);
    }

    @JvmStatic
    public static final boolean isUserFolder(long j) {
        return INSTANCE.isUserFolder(j);
    }

    public final long getId() {
        return this.id;
    }

    public final void setId(long j) {
        this.id = j;
    }

    public final long getParentId() {
        return this.parentId;
    }

    public final void setParentId(long j) {
        this.parentId = j;
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int i) {
        this.count = i;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final void setCreatedAt(long j) {
        this.createdAt = j;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final void setUpdatedAt(long j) {
        this.updatedAt = j;
    }

    public final long getStickAt() {
        return this.stickAt;
    }

    public final void setStickAt(long j) {
        this.stickAt = j;
    }

    public String getSubject() {
        long j = this.id;
        return j > 0 ? this.mSubject : INSTANCE.getSystemSubject(j);
    }

    public void setSubject(String str) {
        this.mSubject = str;
    }

    public final int getIconResource() {
        int i;
        long j = this.id;
        return (j > 0 || (i = ICON_SYSTEM_FOLDER_RESOURCES.get((int) j)) <= 0) ? R.drawable.ic_folder_custom : i;
    }

    /* JADX INFO: compiled from: FolderModel.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u000e\u0010\u0015\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/model/FolderModel$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "FAKE_FOLDER_ID_BASE", "", "ID_ALL_NOTES_FOLDER", "ID_INVALID_FOLDER", "ICON_SYSTEM_FOLDER_RESOURCES", "Landroid/util/SparseIntArray;", "getSystemSubject", "folderId", "", "isFakeFolder", "", "isUserFolder", "isSpecialFolder", "isSupportCreateMindNote", "isCannotMoveFolder", "isSystemFolder", "canCreateNote", "isCustomFolder", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final boolean isCustomFolder(long folderId) {
            return (folderId == -3 || folderId == -2 || folderId == 0 || folderId == -1 || folderId == -4097 || folderId == -5 || folderId == -6 || folderId == -7 || folderId == -8) ? false : true;
        }

        @JvmStatic
        public final boolean isFakeFolder(long folderId) {
            return folderId <= -4096;
        }

        @JvmStatic
        public final boolean isSpecialFolder(long folderId) {
            return folderId == -5 || folderId == -2 || folderId == -6 || folderId == -7 || folderId == -8;
        }

        @JvmStatic
        public final boolean isSupportCreateMindNote(long folderId) {
            return (folderId == -2 || folderId == -5) ? false : true;
        }

        @JvmStatic
        public final boolean isUserFolder(long folderId) {
            return folderId > 0;
        }

        private Companion() {
        }

        @JvmStatic
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
            if (folderId == -6) {
                String string7 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.excerpt);
                Intrinsics.checkNotNullExpressionValue(string7, "getString(...)");
                return string7;
            }
            if (folderId == -7) {
                String string8 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.blackboard_note);
                Intrinsics.checkNotNullExpressionValue(string8, "getString(...)");
                return string8;
            }
            if (folderId == -8) {
                String string9 = BaseNoteApp.INSTANCE.getInstance().getString(R.string.splash_note);
                Intrinsics.checkNotNullExpressionValue(string9, "getString(...)");
                return string9;
            }
            return "";
        }

        @JvmStatic
        public final boolean isCannotMoveFolder(long folderId) {
            return folderId == -4 || folderId == ((long) NoteModel.ID_TEMPARAY_FOLDER) || folderId == -3 || folderId == -2 || folderId == -5;
        }

        public final boolean isSystemFolder(long folderId) {
            return (isFakeFolder(folderId) || isUserFolder(folderId)) ? false : true;
        }

        @JvmStatic
        public final boolean canCreateNote(long folderId) {
            boolean z = folderId == 0 || folderId == -4097 || folderId == -4 || isUserFolder(folderId);
            Logger.INSTANCE.d(FolderModel.TAG, "canCreateNote: " + z + " with folder id " + folderId);
            return z;
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        ICON_SYSTEM_FOLDER_RESOURCES = sparseIntArray;
        sparseIntArray.put(-4097, R.drawable.ic_folder_all);
        sparseIntArray.put(0, R.drawable.ic_folder_quick);
        sparseIntArray.put(-2, R.drawable.ic_folder_call);
        sparseIntArray.put(-3, R.drawable.ic_folder_trash);
    }
}
