package com.google.android.appfunctions.schema.memory.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.notes.basefeature.NotesNormalConstants;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010¨\u0006\u001f"}, d2 = {"Lcom/google/android/appfunctions/schema/memory/v1/MemoryItem;", "", "id", "", "title", NotesNormalConstants.SUMMARY_TABLE_NAME, Creation.TEXT_CONTENT, "keywords", "", "createdAt", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "lastUpdatedAt", "additionalMetadataJson", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Lcom/google/android/appfunctions/schema/types/v1/DateTime;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getTitle", "getSummary", "getTextContent", "getKeywords", "()Ljava/util/List;", "getCreatedAt", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "getLastUpdatedAt", "getAdditionalMetadataJson", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.memory.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class MemoryItem {
    private final String additionalMetadataJson;
    private final DateTime createdAt;
    private final String id;
    private final List<String> keywords;
    private final DateTime lastUpdatedAt;
    private final String summary;
    private final String textContent;
    private final String title;

    public MemoryItem(String id, String str, String str2, String textContent, List<String> keywords, DateTime createdAt, DateTime lastUpdatedAt, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(textContent, "textContent");
        Intrinsics.checkNotNullParameter(keywords, "keywords");
        Intrinsics.checkNotNullParameter(createdAt, "createdAt");
        Intrinsics.checkNotNullParameter(lastUpdatedAt, "lastUpdatedAt");
        this.id = id;
        this.title = str;
        this.summary = str2;
        this.textContent = textContent;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.additionalMetadataJson = str3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ MemoryItem(String str, String str2, String str3, String str4, List list, DateTime dateTime, DateTime dateTime2, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        List listEmptyList = (i & 16) != 0 ? CollectionsKt.emptyList() : list;
        int i2 = i & 2;
        this(str, i2 != 0 ? null : str2, (i & 4) != 0 ? null : str3, str4, listEmptyList, dateTime, dateTime2, (i & 128) != 0 ? null : str5);
    }

    public boolean equals(Object other) {
        if (!(other instanceof MemoryItem)) {
            return false;
        }
        MemoryItem memoryItem = (MemoryItem) other;
        return Intrinsics.areEqual(this.id, memoryItem.id) && Intrinsics.areEqual(this.title, memoryItem.title) && Intrinsics.areEqual(this.summary, memoryItem.summary) && Intrinsics.areEqual(this.textContent, memoryItem.textContent) && Intrinsics.areEqual(this.keywords, memoryItem.keywords) && Intrinsics.areEqual(this.createdAt, memoryItem.createdAt) && Intrinsics.areEqual(this.lastUpdatedAt, memoryItem.lastUpdatedAt) && Intrinsics.areEqual(this.additionalMetadataJson, memoryItem.additionalMetadataJson);
    }

    public final String getAdditionalMetadataJson() {
        return this.additionalMetadataJson;
    }

    public final DateTime getCreatedAt() {
        return this.createdAt;
    }

    public final String getId() {
        return this.id;
    }

    public final List<String> getKeywords() {
        return this.keywords;
    }

    public final DateTime getLastUpdatedAt() {
        return this.lastUpdatedAt;
    }

    public final String getSummary() {
        return this.summary;
    }

    public final String getTextContent() {
        return this.textContent;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.summary, this.textContent, this.keywords, this.createdAt, this.lastUpdatedAt, this.additionalMetadataJson);
    }
}
