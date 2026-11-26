package com.miui.notes.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteEntityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 '2\u00020\u0001:\u0001'B\t\b\u0016¢\u0006\u0004\b\u0002\u0010\u0003B\u0011\b\u0012\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0006B\u0013\b\u0016\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0004\b\u0002\u0010\bJ\b\u0010 \u001a\u00020!H\u0016J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020!H\u0016J&\u0010&\u001a\"\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u001cj\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u001dR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001a\u0010\u0018\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R1\u0010\u001b\u001a\"\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u001cj\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u001d¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006("}, d2 = {"Lcom/miui/notes/model/DataInfo;", "Landroid/os/Parcelable;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "data", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "from", "(Lcom/miui/notes/model/DataInfo;)V", "content", "", "getContent", "()Ljava/lang/String;", "setContent", "(Ljava/lang/String;)V", "callDate", "", "getCallDate", "()J", "setCallDate", "(J)V", "callNumber", "getCallNumber", "setCallNumber", "textId", "getTextId", "setTextId", "attachmentTypeMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getAttachmentTypeMap", "()Ljava/util/HashMap;", "describeContents", "", "writeToParcel", "", "dest", "flags", "cloneAttachmentTypeMap", "CREATOR", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DataInfo implements Parcelable {

    /* JADX INFO: renamed from: CREATOR, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final HashMap<String, String> attachmentTypeMap;
    private long callDate;
    private String callNumber;
    private String content;
    private long textId;

    public /* synthetic */ DataInfo(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String getContent() {
        return this.content;
    }

    public final void setContent(String str) {
        this.content = str;
    }

    public final long getCallDate() {
        return this.callDate;
    }

    public final void setCallDate(long j) {
        this.callDate = j;
    }

    public final String getCallNumber() {
        return this.callNumber;
    }

    public final void setCallNumber(String str) {
        this.callNumber = str;
    }

    public final long getTextId() {
        return this.textId;
    }

    public final void setTextId(long j) {
        this.textId = j;
    }

    public final HashMap<String, String> getAttachmentTypeMap() {
        return this.attachmentTypeMap;
    }

    public DataInfo() {
        this.attachmentTypeMap = new HashMap<>();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.content);
        dest.writeLong(this.callDate);
        dest.writeString(this.callNumber);
        dest.writeLong(this.textId);
        dest.writeMap(this.attachmentTypeMap);
    }

    private DataInfo(Parcel parcel) {
        this.content = parcel.readString();
        this.callDate = parcel.readLong();
        this.callNumber = parcel.readString();
        this.textId = parcel.readLong();
        this.attachmentTypeMap = parcel.readHashMap(null);
    }

    public DataInfo(DataInfo dataInfo) {
        if (dataInfo == null) {
            throw new IllegalArgumentException("Copy error,DataInfo should not be null!".toString());
        }
        this.content = dataInfo.content;
        this.callDate = dataInfo.callDate;
        this.callNumber = dataInfo.callNumber;
        this.textId = dataInfo.textId;
        this.attachmentTypeMap = dataInfo.cloneAttachmentTypeMap();
    }

    public final HashMap<String, String> cloneAttachmentTypeMap() {
        HashMap<String, String> map = this.attachmentTypeMap;
        if (map != null) {
            return new HashMap<>(map);
        }
        return null;
    }

    /* JADX INFO: renamed from: com.miui.notes.model.DataInfo$CREATOR, reason: from kotlin metadata */
    /* JADX INFO: compiled from: NoteEntityHelper.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001d\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lcom/miui/notes/model/DataInfo$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/miui/notes/model/DataInfo;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/miui/notes/model/DataInfo;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion implements Parcelable.Creator<DataInfo> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataInfo createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new DataInfo(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataInfo[] newArray(int size) {
            return new DataInfo[size];
        }
    }
}
