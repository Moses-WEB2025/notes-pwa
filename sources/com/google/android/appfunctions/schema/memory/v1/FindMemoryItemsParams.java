package com.google.android.appfunctions.schema.memory.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0018\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r¨\u0006\u0019"}, d2 = {"Lcom/google/android/appfunctions/schema/memory/v1/FindMemoryItemsParams;", "", SmartAction.Feature.QUERY, "", "updatedAfter", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "updatedBefore", "maxCount", "", "queryLocale", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/Integer;Ljava/lang/String;)V", "getQuery", "()Ljava/lang/String;", "getUpdatedAfter", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getUpdatedBefore", "getMaxCount", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getQueryLocale", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.memory.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindMemoryItemsParams {
    private final Integer maxCount;
    private final String query;
    private final String queryLocale;
    private final DateTime updatedAfter;
    private final DateTime updatedBefore;

    public FindMemoryItemsParams(String query, DateTime dateTime, DateTime dateTime2, Integer num, String str) {
        Intrinsics.checkNotNullParameter(query, "query");
        this.query = query;
        this.updatedAfter = dateTime;
        this.updatedBefore = dateTime2;
        this.maxCount = num;
        this.queryLocale = str;
    }

    public /* synthetic */ FindMemoryItemsParams(String str, DateTime dateTime, DateTime dateTime2, Integer num, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : dateTime, (i & 4) != 0 ? null : dateTime2, (i & 8) != 0 ? null : num, (i & 16) != 0 ? null : str2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindMemoryItemsParams)) {
            return false;
        }
        FindMemoryItemsParams findMemoryItemsParams = (FindMemoryItemsParams) other;
        return Intrinsics.areEqual(this.query, findMemoryItemsParams.query) && Intrinsics.areEqual(this.updatedAfter, findMemoryItemsParams.updatedAfter) && Intrinsics.areEqual(this.updatedBefore, findMemoryItemsParams.updatedBefore) && Intrinsics.areEqual(this.maxCount, findMemoryItemsParams.maxCount) && Intrinsics.areEqual(this.queryLocale, findMemoryItemsParams.queryLocale);
    }

    public final Integer getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final String getQueryLocale() {
        return this.queryLocale;
    }

    public final DateTime getUpdatedAfter() {
        return this.updatedAfter;
    }

    public final DateTime getUpdatedBefore() {
        return this.updatedBefore;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.updatedAfter, this.updatedBefore, this.maxCount, this.queryLocale);
    }
}
