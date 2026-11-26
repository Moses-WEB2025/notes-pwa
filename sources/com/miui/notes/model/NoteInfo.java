package com.miui.notes.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Language;
import com.miui.notes.cloudservice.NotesConstants;
import com.miui.notes.provider.Notes;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteEntity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b&\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 b2\u00020\u0001:\u0001bB\t\b\u0016¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0000¢\u0006\u0004\b\u0002\u0010\u0005B\u0011\b\u0012\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\u0002\u0010\bJ\u0006\u0010W\u001a\u00020XJ\b\u0010Y\u001a\u00020\u0016H\u0016J\u0013\u0010Z\u001a\u00020+2\b\u0010[\u001a\u0004\u0018\u00010\\H\u0096\u0002J\b\u0010]\u001a\u00020\u0016H\u0016J\u0018\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020\u00072\u0006\u0010a\u001a\u00020\u0016H\u0016R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0018\"\u0004\b\u001d\u0010\u001aR\u001a\u0010\u001e\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0018\"\u0004\b \u0010\u001aR\u001a\u0010!\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0018\"\u0004\b#\u0010\u001aR\u001a\u0010$\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\f\"\u0004\b&\u0010\u000eR\u001a\u0010'\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\f\"\u0004\b)\u0010\u000eR\u001a\u0010*\u001a\u00020+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001c\u00100\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001c\u00106\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00103\"\u0004\b8\u00105R\u001c\u00109\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u00103\"\u0004\b;\u00105R\u001c\u0010<\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u00103\"\u0004\b>\u00105R\u001c\u0010?\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u00103\"\u0004\bA\u00105R\u001c\u0010B\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u00103\"\u0004\bD\u00105R\u001c\u0010E\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u00103\"\u0004\bG\u00105R\u001c\u0010H\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u00103\"\u0004\bJ\u00105R\u001a\u0010K\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\f\"\u0004\bM\u0010\u000eR\u001a\u0010N\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0018\"\u0004\bP\u0010\u001aR\u001a\u0010Q\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010\u0018\"\u0004\bS\u0010\u001aR\u001c\u0010T\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u00103\"\u0004\bV\u00105¨\u0006c"}, d2 = {"Lcom/miui/notes/model/NoteInfo;", "Landroid/os/Parcelable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "noteInfo", "(Lcom/miui/notes/model/NoteInfo;)V", Language.LA_IN, "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "id", "", "getId", "()J", "setId", "(J)V", MiCloudConstants.PDC.J_PARENT_ID, "getParentId", "setParentId", NotesConstants.JSON_KEY_ALERT_DATE, "getAlertDate", "setAlertDate", "bgColorId", "", "getBgColorId", "()I", "setBgColorId", "(I)V", "themeId", "getThemeId", "setThemeId", "widgetId", "getWidgetId", "setWidgetId", "widgetType", "getWidgetType", "setWidgetType", "createdDate", "getCreatedDate", "setCreatedDate", "modifiedDate", "getModifiedDate", "setModifiedDate", "localModified", "", "getLocalModified", "()Z", "setLocalModified", "(Z)V", "content", "", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "title", "getTitle", "setTitle", "sourceName", "getSourceName", "setSourceName", "sourcePackage", "getSourcePackage", "setSourcePackage", "sourceIntent", "getSourceIntent", "setSourceIntent", "noteType", "getNoteType", "setNoteType", "mindContent", "getMindContent", "setMindContent", "mindContentPlainText", "getMindContentPlainText", "setMindContentPlainText", "syncId", "getSyncId", "setSyncId", "noteTextCount", "getNoteTextCount", "setNoteTextCount", "aiTextUsed", "getAiTextUsed", "setAiTextUsed", "aigcDataId", "getAigcDataId", "setAigcDataId", "buildValues", "Landroid/content/ContentValues;", "hashCode", "equals", "o", "", "describeContents", "writeToParcel", "", "dest", "flags", "CREATOR", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class NoteInfo implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int aiTextUsed;
    private String aigcDataId;
    private long alertDate;
    private int bgColorId;
    private String content;
    private long createdDate;
    private long id;
    private boolean localModified;
    private String mindContent;
    private String mindContentPlainText;
    private long modifiedDate;
    private int noteTextCount;
    private String noteType;
    private long parentId;
    private String sourceIntent;
    private String sourceName;
    private String sourcePackage;
    private long syncId;
    private int themeId;
    private String title;
    private int widgetId;
    private int widgetType;

    public /* synthetic */ NoteInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public final long getAlertDate() {
        return this.alertDate;
    }

    public final void setAlertDate(long j) {
        this.alertDate = j;
    }

    public final int getBgColorId() {
        return this.bgColorId;
    }

    public final void setBgColorId(int i) {
        this.bgColorId = i;
    }

    public final int getThemeId() {
        return this.themeId;
    }

    public final void setThemeId(int i) {
        this.themeId = i;
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

    public final long getCreatedDate() {
        return this.createdDate;
    }

    public final void setCreatedDate(long j) {
        this.createdDate = j;
    }

    public final long getModifiedDate() {
        return this.modifiedDate;
    }

    public final void setModifiedDate(long j) {
        this.modifiedDate = j;
    }

    public final boolean getLocalModified() {
        return this.localModified;
    }

    public final void setLocalModified(boolean z) {
        this.localModified = z;
    }

    public final String getContent() {
        return this.content;
    }

    public final void setContent(String str) {
        this.content = str;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        this.title = str;
    }

    public final String getSourceName() {
        return this.sourceName;
    }

    public final void setSourceName(String str) {
        this.sourceName = str;
    }

    public final String getSourcePackage() {
        return this.sourcePackage;
    }

    public final void setSourcePackage(String str) {
        this.sourcePackage = str;
    }

    public final String getSourceIntent() {
        return this.sourceIntent;
    }

    public final void setSourceIntent(String str) {
        this.sourceIntent = str;
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

    public final long getSyncId() {
        return this.syncId;
    }

    public final void setSyncId(long j) {
        this.syncId = j;
    }

    public final int getNoteTextCount() {
        return this.noteTextCount;
    }

    public final void setNoteTextCount(int i) {
        this.noteTextCount = i;
    }

    public final int getAiTextUsed() {
        return this.aiTextUsed;
    }

    public final void setAiTextUsed(int i) {
        this.aiTextUsed = i;
    }

    public final String getAigcDataId() {
        return this.aigcDataId;
    }

    public final void setAigcDataId(String str) {
        this.aigcDataId = str;
    }

    public NoteInfo() {
        this.id = -2L;
        this.syncId = -1L;
        this.aigcDataId = "";
        this.parentId = 0L;
        this.alertDate = 0L;
        this.bgColorId = 0;
        this.themeId = 0;
        this.widgetId = 0;
        this.widgetType = -1;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.createdDate = jCurrentTimeMillis;
        this.modifiedDate = jCurrentTimeMillis;
        this.content = "";
        this.localModified = true;
        this.title = "";
        this.noteType = "common";
        this.mindContent = "";
        this.mindContentPlainText = "";
    }

    public NoteInfo(NoteInfo noteInfo) {
        Intrinsics.checkNotNullParameter(noteInfo, "noteInfo");
        this.id = -2L;
        this.syncId = -1L;
        this.aigcDataId = "";
        this.id = noteInfo.id;
        this.parentId = noteInfo.parentId;
        this.alertDate = noteInfo.alertDate;
        this.bgColorId = noteInfo.bgColorId;
        this.themeId = noteInfo.themeId;
        this.widgetId = noteInfo.widgetId;
        this.widgetType = noteInfo.widgetType;
        this.createdDate = noteInfo.createdDate;
        this.modifiedDate = noteInfo.modifiedDate;
        this.localModified = noteInfo.localModified;
        this.content = noteInfo.content;
        this.sourceName = noteInfo.sourceName;
        this.sourcePackage = noteInfo.sourcePackage;
        this.sourceIntent = noteInfo.sourceIntent;
        this.title = noteInfo.title;
        this.noteType = noteInfo.noteType;
        this.mindContent = noteInfo.mindContent;
        this.mindContentPlainText = noteInfo.mindContentPlainText;
        this.syncId = noteInfo.syncId;
        this.noteTextCount = noteInfo.noteTextCount;
        this.aiTextUsed = noteInfo.aiTextUsed;
        this.aigcDataId = noteInfo.aigcDataId;
    }

    public final ContentValues buildValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("parent_id", Long.valueOf(this.parentId));
        contentValues.put(Notes.Note.ALERTED_DATE, Long.valueOf(this.alertDate));
        contentValues.put(Notes.Note.BG_COLOR_ID, Integer.valueOf(this.bgColorId));
        contentValues.put("theme_id", Integer.valueOf(this.themeId));
        contentValues.put("widget_id", Integer.valueOf(this.widgetId));
        contentValues.put("widget_type", Integer.valueOf(this.widgetType));
        contentValues.put("created_date", Long.valueOf(this.createdDate));
        contentValues.put("modified_date", Long.valueOf(this.modifiedDate));
        contentValues.put("snippet", this.content);
        contentValues.put(Notes.Note.SOURCE_NAME, this.sourceName);
        contentValues.put(Notes.Note.SOURCE_PACKAGE, this.sourcePackage);
        contentValues.put(Notes.Note.SOURCE_INTENT, this.sourceIntent);
        contentValues.put(Notes.Note.LOCAL_MODIFIED, Integer.valueOf(this.localModified ? 1 : 0));
        contentValues.put("type", (Integer) 0);
        contentValues.put("title", this.title);
        contentValues.put(Notes.Note.NOTE_CONTENT_TYPE, this.noteType);
        contentValues.put(Notes.Note.MIND_CONTENT, this.mindContent);
        contentValues.put(Notes.Note.MIND_CONTENT_PLAIN_TEXT, this.mindContentPlainText);
        contentValues.put(Notes.Note.NOTE_TEXT_COUNT, Integer.valueOf(this.noteTextCount));
        contentValues.put(Notes.Note.AI_TEXT_USED, Integer.valueOf(this.aiTextUsed));
        contentValues.put(Notes.Note.AIGC_DATA_ID, this.aigcDataId);
        return contentValues;
    }

    public int hashCode() {
        long j = this.parentId;
        long j2 = this.alertDate;
        int i = (((((((((((((int) (j ^ (j >>> 32))) + 31) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + this.bgColorId) * 31) + this.themeId) * 31) + this.widgetId) * 31) + this.widgetType) * 31;
        String str = this.content;
        int iHashCode = 0;
        int iHashCode2 = (i + ((str == null || str == null) ? 0 : str.hashCode())) * 31;
        String str2 = this.title;
        int iHashCode3 = (iHashCode2 + ((str2 == null || str2 == null) ? 0 : str2.hashCode())) * 31;
        String str3 = this.noteType;
        int iHashCode4 = (iHashCode3 + ((str3 == null || str3 == null) ? 0 : str3.hashCode())) * 31;
        String str4 = this.mindContent;
        int iHashCode5 = (iHashCode4 + ((str4 == null || str4 == null) ? 0 : str4.hashCode())) * 31;
        String str5 = this.mindContentPlainText;
        if (str5 != null && str5 != null) {
            iHashCode = str5.hashCode();
        }
        return iHashCode5 + iHashCode;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteInfo)) {
            return false;
        }
        NoteInfo noteInfo = (NoteInfo) o;
        return this.parentId == noteInfo.parentId && this.alertDate == noteInfo.alertDate && this.bgColorId == noteInfo.bgColorId && this.themeId == noteInfo.themeId && this.widgetId == noteInfo.widgetId && this.widgetType == noteInfo.widgetType && TextUtils.equals(this.content, noteInfo.content) && TextUtils.equals(this.title, noteInfo.title) && TextUtils.equals(this.noteType, noteInfo.noteType) && TextUtils.equals(this.mindContent, noteInfo.mindContent) && TextUtils.equals(this.mindContentPlainText, noteInfo.mindContentPlainText);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeLong(this.id);
        dest.writeLong(this.parentId);
        dest.writeLong(this.alertDate);
        dest.writeInt(this.bgColorId);
        dest.writeInt(this.themeId);
        dest.writeInt(this.widgetId);
        dest.writeInt(this.widgetType);
        dest.writeLong(this.createdDate);
        dest.writeLong(this.modifiedDate);
        dest.writeString(this.content);
        dest.writeString(this.sourceName);
        dest.writeString(this.sourcePackage);
        dest.writeString(this.sourceIntent);
        dest.writeByte(this.localModified ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.noteType);
        dest.writeString(this.mindContent);
        dest.writeString(this.mindContentPlainText);
    }

    private NoteInfo(Parcel parcel) {
        this.id = -2L;
        this.syncId = -1L;
        this.aigcDataId = "";
        this.id = parcel.readLong();
        this.parentId = parcel.readLong();
        this.alertDate = parcel.readLong();
        this.bgColorId = parcel.readInt();
        this.themeId = parcel.readInt();
        this.widgetId = parcel.readInt();
        this.widgetType = parcel.readInt();
        this.createdDate = parcel.readLong();
        this.modifiedDate = parcel.readLong();
        this.content = parcel.readString();
        this.sourceName = parcel.readString();
        this.sourcePackage = parcel.readString();
        this.sourceIntent = parcel.readString();
        this.localModified = parcel.readByte() != 0;
        this.title = parcel.readString();
        this.noteType = parcel.readString();
        this.mindContent = parcel.readString();
        this.mindContentPlainText = parcel.readString();
    }

    /* JADX INFO: renamed from: com.miui.notes.model.NoteInfo$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: NoteEntity.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/miui/notes/model/NoteInfo$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/notes/model/NoteInfo;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/miui/notes/model/NoteInfo;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<NoteInfo> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoteInfo createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new NoteInfo(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    }
}
