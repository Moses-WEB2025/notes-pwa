package com.google.android.appfunctions.schema.messages.v1;

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
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/messages/v1/SendMessageParams;", "", "body", "", "recipientsIds", "", "messageAttachments", "Lcom/google/android/appfunctions/schema/messages/v1/Attachment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getBody", "()Ljava/lang/String;", "getRecipientsIds", "()Ljava/util/List;", "getMessageAttachments", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.messages.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SendMessageParams {
    private final String body;
    private final List<Attachment> messageAttachments;
    private final List<String> recipientsIds;

    public SendMessageParams(String body, List<String> recipientsIds, List<Attachment> messageAttachments) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        Intrinsics.checkNotNullParameter(messageAttachments, "messageAttachments");
        this.body = body;
        this.recipientsIds = recipientsIds;
        this.messageAttachments = messageAttachments;
    }

    public /* synthetic */ SendMessageParams(String str, List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? CollectionsKt.emptyList() : list, (i & 4) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof SendMessageParams)) {
            return false;
        }
        SendMessageParams sendMessageParams = (SendMessageParams) other;
        return Intrinsics.areEqual(this.body, sendMessageParams.body) && Intrinsics.areEqual(this.recipientsIds, sendMessageParams.recipientsIds) && Intrinsics.areEqual(this.messageAttachments, sendMessageParams.messageAttachments);
    }

    public final String getBody() {
        return this.body;
    }

    public final List<Attachment> getMessageAttachments() {
        return this.messageAttachments;
    }

    public final List<String> getRecipientsIds() {
        return this.recipientsIds;
    }

    public int hashCode() {
        return Objects.hash(this.body, this.recipientsIds, this.messageAttachments);
    }
}
