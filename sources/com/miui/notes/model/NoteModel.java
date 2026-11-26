package com.miui.notes.model;

import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.cloudservice.NotesConstants;
import com.miui.notes.data.Contact;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.richeditor.schema.HtmlParser;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NoteModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b+\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u0000 P2\u00020\u0001:\u0003NOPB\t\b\u0016¢\u0006\u0004\b\u0002\u0010\u0003B§\u0001\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\b\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\u0005\u0012\u0006\u0010\u000f\u001a\u00020\u0005\u0012\u0006\u0010\u0010\u001a\u00020\u0005\u0012\u0006\u0010\u0011\u001a\u00020\u0005\u0012\u0006\u0010\u0012\u001a\u00020\u0005\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\u0002\u0010\u0019R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001b\"\u0004\b\u001f\u0010\u001dR\u000e\u0010 \u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010\f\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010\r\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010'\"\u0004\b+\u0010)R\u001a\u0010\u000e\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001b\"\u0004\b-\u0010\u001dR\u001a\u0010\u000f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001b\"\u0004\b/\u0010\u001dR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001b\"\u0004\b1\u0010\u001dR\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u001b\"\u0004\b3\u0010\u001dR\u001e\u0010\u0012\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u001bR\u0010\u00106\u001a\u0004\u0018\u000107X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010#\"\u0004\b9\u0010%R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010#\"\u0004\b;\u0010%R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010#\"\u0004\b=\u0010%R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010#\"\u0004\b?\u0010%R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010#\"\u0004\bA\u0010%R\"\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\b\u00104\u001a\u0004\u0018\u00010\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\bB\u0010#R$\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bC\u0010'\"\u0004\bD\u0010)R\u0011\u0010E\u001a\u00020F8F¢\u0006\u0006\u001a\u0004\bE\u0010GR\u0013\u0010H\u001a\u0004\u0018\u0001078F¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0011\u0010K\u001a\u00020F8F¢\u0006\u0006\u001a\u0004\bK\u0010GR\u0011\u0010L\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\bM\u0010\u001b¨\u0006Q"}, d2 = {"Lcom/miui/notes/model/NoteModel;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "id", "", "folderId", "themeId", "", NotesConstants.JSON_KEY_COLOR_ID, "snippet", "", "widgetId", "widgetType", "createdAt", "updatedAt", "remindAt", "stickAt", "originFolderId", "plainText", "title", "noteType", "mindContent", "mindContentPlainText", "thumbnail", "(JJIILjava/lang/String;IIJJJJJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getId", "()J", "setId", "(J)V", "getFolderId", "setFolderId", "mThemeId", "mColorId", "getSnippet", "()Ljava/lang/String;", "setSnippet", "(Ljava/lang/String;)V", "getWidgetId", "()I", "setWidgetId", "(I)V", "getWidgetType", "setWidgetType", "getCreatedAt", "setCreatedAt", "getUpdatedAt", "setUpdatedAt", "getRemindAt", "setRemindAt", "getStickAt", "setStickAt", "value", "getOriginFolderId", "mContact", "Lcom/miui/notes/data/Contact;", "getPlainText", "setPlainText", "getTitle", "setTitle", "getNoteType", "setNoteType", "getMindContent", "setMindContent", "getMindContentPlainText", "setMindContentPlainText", "getThumbnail", "getThemeId", "setThemeId", "isCallNote", "", "()Z", HtmlParser.ContactElement.CLASS, "getContact", "()Lcom/miui/notes/data/Contact;", "isHiddenDeleted", "sortDate", "getSortDate", "NoteType", "DataType", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteModel {
    public static final int ID_BLACK_BOARD_FOLDER = -7;
    public static final int ID_CALL_RECORD_FOLDER = -2;
    public static final int ID_EXCERPT_FOLDER = -6;
    public static final int ID_HEAR_FOLDER = -5;
    public static final int ID_PRIVACY_FOLDER = -4;
    public static final int ID_ROOT_FOLDER = 0;
    public static final int ID_STYLUS_FOLDER = -8;
    public static final int ID_TRASH_FOLDER = -3;
    public static final String IN_VALID_FOLDER_SELECTION = "(parent_id>=0 OR parent_id=-2 OR parent_id=-4 OR parent_id=-5 OR parent_id=-6 OR parent_id=-7  OR parent_id=-8)";
    private long createdAt;
    private long folderId;
    private long id;
    private int mColorId;
    private Contact mContact;
    private int mThemeId;
    private String mindContent;
    private String mindContentPlainText;
    private String noteType;
    private long originFolderId;
    private String plainText;
    private long remindAt;
    private String snippet;
    private long stickAt;
    private String thumbnail;
    private String title;
    private long updatedAt;
    private int widgetId;
    private int widgetType;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int ID_TEMPARAY_FOLDER = -1;

    @JvmStatic
    public static final boolean isCallNote(long j, long j2) {
        return INSTANCE.isCallNote(j, j2);
    }

    @JvmStatic
    public static final boolean isHidden(long j, long j2) {
        return INSTANCE.isHidden(j, j2);
    }

    public final long getId() {
        return this.id;
    }

    public final void setId(long j) {
        this.id = j;
    }

    public final long getFolderId() {
        return this.folderId;
    }

    public final void setFolderId(long j) {
        this.folderId = j;
    }

    public final String getSnippet() {
        return this.snippet;
    }

    public final void setSnippet(String str) {
        this.snippet = str;
    }

    public final int getWidgetId() {
        return this.widgetId;
    }

    public final void setWidgetId(int i) {
        this.widgetId = i;
    }

    public final int getWidgetType() {
        return this.widgetType;
    }

    public final void setWidgetType(int i) {
        this.widgetType = i;
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

    public final long getRemindAt() {
        return this.remindAt;
    }

    public final void setRemindAt(long j) {
        this.remindAt = j;
    }

    public final long getStickAt() {
        return this.stickAt;
    }

    public final void setStickAt(long j) {
        this.stickAt = j;
    }

    public final long getOriginFolderId() {
        return this.originFolderId;
    }

    public final String getPlainText() {
        return this.plainText;
    }

    public final void setPlainText(String str) {
        this.plainText = str;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        this.title = str;
    }

    public final String getNoteType() {
        return this.noteType;
    }

    public final void setNoteType(String str) {
        this.noteType = str;
    }

    public final String getMindContent() {
        return this.mindContent;
    }

    public final void setMindContent(String str) {
        this.mindContent = str;
    }

    public final String getMindContentPlainText() {
        return this.mindContentPlainText;
    }

    public final void setMindContentPlainText(String str) {
        this.mindContentPlainText = str;
    }

    public final String getThumbnail() {
        return this.thumbnail;
    }

    public NoteModel() {
    }

    /* JADX INFO: compiled from: NoteModel.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/miui/notes/model/NoteModel$NoteType;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TYPE_COMMON", "", "TYPE_MIND", "TYPE_HAND_WRITE", "TYPE_MULTI_HAND_WRITE", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class NoteType {
        public static final NoteType INSTANCE = new NoteType();
        public static final String TYPE_COMMON = "common";
        public static final String TYPE_HAND_WRITE = "handwrite";
        public static final String TYPE_MIND = "mind";
        public static final String TYPE_MULTI_HAND_WRITE = "multi_handwrite";

        private NoteType() {
        }
    }

    /* JADX INFO: compiled from: NoteModel.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/miui/notes/model/NoteModel$DataType;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TYPE_COMMON", "", "TYPE_HAND_WRITE", "TYPE_MULTI_HAND_WRITE", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class DataType {
        public static final DataType INSTANCE = new DataType();
        public static final String TYPE_COMMON = "common";
        public static final String TYPE_HAND_WRITE = "handwrite";
        public static final String TYPE_MULTI_HAND_WRITE = "multi_handwrite";

        private DataType() {
        }
    }

    public NoteModel(long j, long j2, int i, int i2, String str, int i3, int i4, long j3, long j4, long j5, long j6, long j7, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.id = j;
        this.folderId = j2;
        this.mThemeId = i;
        this.mColorId = i2;
        this.snippet = str;
        this.widgetId = i3;
        this.widgetType = i4;
        this.createdAt = j3;
        this.updatedAt = j4;
        this.remindAt = j5;
        this.stickAt = j6;
        this.originFolderId = j7;
        this.plainText = str2;
        this.title = str3;
        this.noteType = str4;
        this.mindContent = str5;
        this.mindContentPlainText = str6;
        this.thumbnail = str7;
    }

    public final int getThemeId() {
        int i = this.mThemeId;
        return i > 0 ? i : this.mColorId;
    }

    public final void setThemeId(int i) {
        this.mThemeId = i;
        if (i >= 5) {
            i = 0;
        }
        this.mColorId = i;
    }

    public final boolean isCallNote() {
        long j = this.folderId;
        return j == -2 || (j == -4 && !TextUtils.isEmpty(Contact.getPhoneNumber(BaseNoteApp.INSTANCE.getInstance(), this.id, true)));
    }

    public final Contact getContact() {
        if (this.mContact == null && isCallNote()) {
            this.mContact = new Contact("", "", false);
        }
        return this.mContact;
    }

    public final boolean isHiddenDeleted() {
        return INSTANCE.isHiddenDeleted(this.folderId, this.originFolderId);
    }

    public final long getSortDate() {
        if (PreferenceUtils.getNoteSortWay(BaseNoteApp.INSTANCE.getInstance(), 1) == 0) {
            return this.createdAt;
        }
        return this.updatedAt;
    }

    /* JADX INFO: compiled from: NoteModel.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0007J\u0018\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014H\u0007J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087D¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0003R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/model/NoteModel$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ID_ROOT_FOLDER", "", "ID_TEMPARAY_FOLDER", "getID_TEMPARAY_FOLDER$annotations", "ID_CALL_RECORD_FOLDER", "ID_TRASH_FOLDER", "ID_PRIVACY_FOLDER", "ID_HEAR_FOLDER", "ID_EXCERPT_FOLDER", "ID_BLACK_BOARD_FOLDER", "ID_STYLUS_FOLDER", "IN_VALID_FOLDER_SELECTION", "", "isCallNote", "", "foldId", "", "id", "isHidden", "folderId", "originFolderId", "isHiddenDeleted", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Deprecated(message = "")
        public static /* synthetic */ void getID_TEMPARAY_FOLDER$annotations() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isHiddenDeleted(long folderId, long originFolderId) {
            return folderId == -3 && originFolderId == -4;
        }

        private Companion() {
        }

        @JvmStatic
        public final boolean isCallNote(long foldId, long id) {
            return foldId == -2 || (foldId == -4 && !TextUtils.isEmpty(Contact.getPhoneNumber(BaseNoteApp.INSTANCE.getInstance(), id, true)));
        }

        @JvmStatic
        public final boolean isHidden(long folderId, long originFolderId) {
            return folderId == -4 || isHiddenDeleted(folderId, originFolderId);
        }
    }
}
