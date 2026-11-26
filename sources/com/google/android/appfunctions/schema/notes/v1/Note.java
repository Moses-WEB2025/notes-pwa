package com.google.android.appfunctions.schema.notes.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\r¨\u0006\u0019"}, d2 = {"Lcom/google/android/appfunctions/schema/notes/v1/Note;", "", "id", "", "title", "content", "attachments", "", "Lcom/google/android/appfunctions/schema/notes/v1/Attachment;", "folderId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getTitle", "getContent", "getAttachments", "()Ljava/util/List;", "getFolderId", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.notes.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Note {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.notes.v1.NoteForeground";
    private final List<Attachment> attachments;
    private final String content;
    private final String folderId;
    private final String id;
    private final String title;

    public Note(String id, String title, String str, List<Attachment> attachments, String str2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        this.id = id;
        this.title = title;
        this.content = str;
        this.attachments = attachments;
        this.folderId = str2;
    }

    public /* synthetic */ Note(String str, String str2, String str3, List list, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? CollectionsKt.emptyList() : list, (i & 16) != 0 ? null : str4);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Note)) {
            return false;
        }
        Note note = (Note) other;
        return Intrinsics.areEqual(this.id, note.id) && Intrinsics.areEqual(this.title, note.title) && Intrinsics.areEqual(this.content, note.content) && Intrinsics.areEqual(this.attachments, note.attachments) && Intrinsics.areEqual(this.folderId, note.folderId);
    }

    public final List<Attachment> getAttachments() {
        return this.attachments;
    }

    public final String getContent() {
        return this.content;
    }

    public final String getFolderId() {
        return this.folderId;
    }

    public final String getId() {
        return this.id;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.content, this.attachments, this.folderId);
    }
}
