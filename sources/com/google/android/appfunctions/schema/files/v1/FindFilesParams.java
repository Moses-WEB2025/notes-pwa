package com.google.android.appfunctions.schema.files.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B'\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0006H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lcom/google/android/appfunctions/schema/files/v1/FindFilesParams;", "", SmartAction.Feature.QUERY, "", "fileType", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;I)V", "getQuery", "()Ljava/lang/String;", "getFileType$annotations", "()V", "getFileType", "getMaxCount", "()I", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.files.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindFilesParams {
    public static final String FILE_TYPE_AUDIO = "AUDIO";
    public static final String FILE_TYPE_DOCUMENT = "DOCUMENT";
    public static final String FILE_TYPE_IMAGE = "IMAGE";
    public static final String FILE_TYPE_VIDEO = "VIDEO";
    private final String fileType;
    private final int maxCount;
    private final String query;

    public FindFilesParams(String str, String str2, int i) {
        this.query = str;
        this.fileType = str2;
        this.maxCount = i;
    }

    public /* synthetic */ FindFilesParams(String str, String str2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, (i2 & 2) != 0 ? null : str2, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindFilesParams)) {
            return false;
        }
        FindFilesParams findFilesParams = (FindFilesParams) other;
        return Intrinsics.areEqual(this.query, findFilesParams.query) && Intrinsics.areEqual(this.fileType, findFilesParams.fileType) && this.maxCount == findFilesParams.maxCount;
    }

    public final String getFileType() {
        return this.fileType;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.fileType, Integer.valueOf(this.maxCount));
    }
}
