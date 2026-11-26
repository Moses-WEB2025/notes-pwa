package com.google.android.appfunctions.schema.messages.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
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
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\n\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b¢\u0006\u0004\b\u000f\u0010\u0010J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\"\u001a\u00020#H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\f\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019¨\u0006%"}, d2 = {"Lcom/google/android/appfunctions/schema/messages/v1/Message;", "", "id", "", "messageType", "body", "senderId", "recipientsIds", "", "dateSent", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "dateReceived", "dateRead", "messageAttachments", "Lcom/google/android/appfunctions/schema/messages/v1/Attachment;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/util/List;)V", "getId", "()Ljava/lang/String;", "getMessageType$annotations", "()V", "getMessageType", "getBody", "getSenderId", "getRecipientsIds", "()Ljava/util/List;", "getDateSent", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getDateReceived", "getDateRead", "getMessageAttachments", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.messages.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Message {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.messages.v1.MessageForeground";
    public static final String MESSAGE_TYPE_DRAFT = "DRAFT";
    public static final String MESSAGE_TYPE_RECEIVED = "RECEIVED";
    public static final String MESSAGE_TYPE_SENT = "SENT";
    private final String body;
    private final DateTime dateRead;
    private final DateTime dateReceived;
    private final DateTime dateSent;
    private final String id;
    private final List<Attachment> messageAttachments;
    private final String messageType;
    private final List<String> recipientsIds;
    private final String senderId;

    public Message(String id, String messageType, String body, String senderId, List<String> recipientsIds, DateTime dateTime, DateTime dateTime2, DateTime dateTime3, List<Attachment> messageAttachments) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(messageType, "messageType");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(senderId, "senderId");
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        Intrinsics.checkNotNullParameter(messageAttachments, "messageAttachments");
        this.id = id;
        this.messageType = messageType;
        this.body = body;
        this.senderId = senderId;
        this.recipientsIds = recipientsIds;
        this.dateSent = dateTime;
        this.dateReceived = dateTime2;
        this.dateRead = dateTime3;
        this.messageAttachments = messageAttachments;
    }

    public /* synthetic */ Message(String str, String str2, String str3, String str4, List list, DateTime dateTime, DateTime dateTime2, DateTime dateTime3, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, (i & 16) != 0 ? CollectionsKt.emptyList() : list, (i & 32) != 0 ? null : dateTime, (i & 64) != 0 ? null : dateTime2, (i & 128) != 0 ? null : dateTime3, (i & 256) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Message)) {
            return false;
        }
        Message message = (Message) other;
        return Intrinsics.areEqual(this.id, message.id) && Intrinsics.areEqual(this.messageType, message.messageType) && Intrinsics.areEqual(this.body, message.body) && Intrinsics.areEqual(this.senderId, message.senderId) && Intrinsics.areEqual(this.recipientsIds, message.recipientsIds) && Intrinsics.areEqual(this.dateSent, message.dateSent) && Intrinsics.areEqual(this.dateReceived, message.dateReceived) && Intrinsics.areEqual(this.dateRead, message.dateRead) && Intrinsics.areEqual(this.messageAttachments, message.messageAttachments);
    }

    public final String getBody() {
        return this.body;
    }

    public final DateTime getDateRead() {
        return this.dateRead;
    }

    public final DateTime getDateReceived() {
        return this.dateReceived;
    }

    public final DateTime getDateSent() {
        return this.dateSent;
    }

    public final String getId() {
        return this.id;
    }

    public final List<Attachment> getMessageAttachments() {
        return this.messageAttachments;
    }

    public final String getMessageType() {
        return this.messageType;
    }

    public final List<String> getRecipientsIds() {
        return this.recipientsIds;
    }

    public final String getSenderId() {
        return this.senderId;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.messageType, this.body, this.senderId, this.recipientsIds, this.dateSent, this.dateReceived, this.dateRead, this.messageAttachments);
    }
}
