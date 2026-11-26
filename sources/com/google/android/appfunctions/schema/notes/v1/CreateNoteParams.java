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
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\r¨\u0006\u0018"}, d2 = {"Lcom/google/android/appfunctions/schema/notes/v1/CreateNoteParams;", "", "title", "", "content", "attachments", "", "Lcom/google/android/appfunctions/schema/notes/v1/Attachment;", "folderId", "externalId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "getContent", "getAttachments", "()Ljava/util/List;", "getFolderId", "getExternalId", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.notes.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CreateNoteParams {
    private final List<Attachment> attachments;
    private final String content;
    private final String externalId;
    private final String folderId;
    private final String title;

    public CreateNoteParams(String title, String str, List<Attachment> attachments, String str2, String str3) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        this.title = title;
        this.content = str;
        this.attachments = attachments;
        this.folderId = str2;
        this.externalId = str3;
    }

    public /* synthetic */ CreateNoteParams(String str, String str2, List list, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4);
    }

    public boolean equals(Object other) {
        if (!(other instanceof CreateNoteParams)) {
            return false;
        }
        CreateNoteParams createNoteParams = (CreateNoteParams) other;
        return Intrinsics.areEqual(this.title, createNoteParams.title) && Intrinsics.areEqual(this.content, createNoteParams.content) && Intrinsics.areEqual(this.attachments, createNoteParams.attachments) && Intrinsics.areEqual(this.folderId, createNoteParams.folderId) && Intrinsics.areEqual(this.externalId, createNoteParams.externalId);
    }

    public final List<Attachment> getAttachments() {
        return this.attachments;
    }

    public final String getContent() {
        return this.content;
    }

    public final String getExternalId() {
        return this.externalId;
    }

    public final String getFolderId() {
        return this.folderId;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.title, this.content, this.attachments, this.folderId, this.externalId);
    }
}
