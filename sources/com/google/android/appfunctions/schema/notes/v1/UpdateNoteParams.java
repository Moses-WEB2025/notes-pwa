package com.google.android.appfunctions.schema.notes.v1;

import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.excerpt.NotifyUtils;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001Ba\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005\u0012\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0018\u00010\u0005\u0012\u0012\b\u0002\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0006\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u001f\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u001b\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010¨\u0006\u0019"}, d2 = {"Lcom/google/android/appfunctions/schema/notes/v1/UpdateNoteParams;", "", NotifyUtils.KEY_NOTE_ID, "", "title", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "content", "attachments", "", "Lcom/google/android/appfunctions/schema/notes/v1/Attachment;", "folderId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getNoteId", "()Ljava/lang/String;", "getTitle", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getContent", "getAttachments", "getFolderId", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.notes.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateNoteParams {
    private final SetField<List<Attachment>> attachments;
    private final SetField<String> content;
    private final SetField<String> folderId;
    private final String noteId;
    private final SetField<String> title;

    /* JADX WARN: Multi-variable type inference failed */
    public UpdateNoteParams(String noteId, SetField<String> setField, SetField<String> setField2, SetField<? extends List<Attachment>> setField3, SetField<String> setField4) {
        Intrinsics.checkNotNullParameter(noteId, "noteId");
        this.noteId = noteId;
        this.title = setField;
        this.content = setField2;
        this.attachments = setField3;
        this.folderId = setField4;
    }

    public /* synthetic */ UpdateNoteParams(String str, SetField setField, SetField setField2, SetField setField3, SetField setField4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2, (i & 8) != 0 ? null : setField3, (i & 16) != 0 ? null : setField4);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateNoteParams)) {
            return false;
        }
        UpdateNoteParams updateNoteParams = (UpdateNoteParams) other;
        return Intrinsics.areEqual(this.noteId, updateNoteParams.noteId) && Intrinsics.areEqual(this.title, updateNoteParams.title) && Intrinsics.areEqual(this.content, updateNoteParams.content) && Intrinsics.areEqual(this.attachments, updateNoteParams.attachments) && Intrinsics.areEqual(this.folderId, updateNoteParams.folderId);
    }

    public final SetField<List<Attachment>> getAttachments() {
        return this.attachments;
    }

    public final SetField<String> getContent() {
        return this.content;
    }

    public final SetField<String> getFolderId() {
        return this.folderId;
    }

    public final String getNoteId() {
        return this.noteId;
    }

    public final SetField<String> getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.noteId, this.title, this.content, this.attachments, this.folderId);
    }
}
