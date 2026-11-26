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
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0013\b\u0007\u0018\u00002\u00020\u0001B[\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0004\b\u000e\u0010\u000fJ\u0013\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001f\u001a\u00020\rH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006 "}, d2 = {"Lcom/google/android/appfunctions/schema/messages/v1/FindMessagesParams;", "", "senderId", "", "recipientsIds", "", "startTime", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "endTime", SmartAction.Feature.QUERY, "read", "", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;Ljava/lang/Boolean;I)V", "getSenderId", "()Ljava/lang/String;", "getRecipientsIds", "()Ljava/util/List;", "getStartTime", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getEndTime", "getQuery", "getRead", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getMaxCount", "()I", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.messages.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindMessagesParams {
    private final DateTime endTime;
    private final int maxCount;
    private final String query;
    private final Boolean read;
    private final List<String> recipientsIds;
    private final String senderId;
    private final DateTime startTime;

    public FindMessagesParams(String str, List<String> recipientsIds, DateTime dateTime, DateTime dateTime2, String str2, Boolean bool, int i) {
        Intrinsics.checkNotNullParameter(recipientsIds, "recipientsIds");
        this.senderId = str;
        this.recipientsIds = recipientsIds;
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.query = str2;
        this.read = bool;
        this.maxCount = i;
    }

    public /* synthetic */ FindMessagesParams(String str, List list, DateTime dateTime, DateTime dateTime2, String str2, Boolean bool, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? CollectionsKt.emptyList() : list, (i2 & 4) != 0 ? null : dateTime, (i2 & 8) != 0 ? null : dateTime2, (i2 & 16) != 0 ? null : str2, (i2 & 32) != 0 ? null : bool, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindMessagesParams)) {
            return false;
        }
        FindMessagesParams findMessagesParams = (FindMessagesParams) other;
        return Intrinsics.areEqual(this.senderId, findMessagesParams.senderId) && Intrinsics.areEqual(this.recipientsIds, findMessagesParams.recipientsIds) && Intrinsics.areEqual(this.startTime, findMessagesParams.startTime) && Intrinsics.areEqual(this.endTime, findMessagesParams.endTime) && Intrinsics.areEqual(this.query, findMessagesParams.query) && Intrinsics.areEqual(this.read, findMessagesParams.read) && this.maxCount == findMessagesParams.maxCount;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final Boolean getRead() {
        return this.read;
    }

    public final List<String> getRecipientsIds() {
        return this.recipientsIds;
    }

    public final String getSenderId() {
        return this.senderId;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.senderId, this.recipientsIds, this.startTime, this.endTime, this.query, this.read, Integer.valueOf(this.maxCount));
    }
}
