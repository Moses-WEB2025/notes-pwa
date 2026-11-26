package com.google.android.appfunctions.schema.photos.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0005H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lcom/google/android/appfunctions/schema/photos/v1/FindAlbumsParams;", "", SmartAction.Feature.QUERY, "", "maxCount", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "getQuery", "()Ljava/lang/String;", "getMaxCount", "()I", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.photos.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FindAlbumsParams {
    private final int maxCount;
    private final String query;

    public FindAlbumsParams(String str, int i) {
        this.query = str;
        this.maxCount = i;
    }

    public /* synthetic */ FindAlbumsParams(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i2 & 1) ? null : str, i);
    }

    public boolean equals(Object other) {
        if (!(other instanceof FindAlbumsParams)) {
            return false;
        }
        FindAlbumsParams findAlbumsParams = (FindAlbumsParams) other;
        return Intrinsics.areEqual(this.query, findAlbumsParams.query) && this.maxCount == findAlbumsParams.maxCount;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public int hashCode() {
        return Objects.hash(this.query, Integer.valueOf(this.maxCount));
    }
}
