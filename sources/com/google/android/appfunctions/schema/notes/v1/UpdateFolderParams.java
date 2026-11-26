package com.google.android.appfunctions.schema.notes.v1;

import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lcom/google/android/appfunctions/schema/notes/v1/UpdateFolderParams;", "", "folderId", "", "name", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getFolderId", "()Ljava/lang/String;", "getName", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.notes.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateFolderParams {
    private final String folderId;
    private final SetField<String> name;

    public UpdateFolderParams(String folderId, SetField<String> setField) {
        Intrinsics.checkNotNullParameter(folderId, "folderId");
        this.folderId = folderId;
        this.name = setField;
    }

    public /* synthetic */ UpdateFolderParams(String str, SetField setField, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateFolderParams)) {
            return false;
        }
        UpdateFolderParams updateFolderParams = (UpdateFolderParams) other;
        return Intrinsics.areEqual(this.folderId, updateFolderParams.folderId) && Intrinsics.areEqual(this.name, updateFolderParams.name);
    }

    public final String getFolderId() {
        return this.folderId;
    }

    public final SetField<String> getName() {
        return this.name;
    }

    public int hashCode() {
        return Objects.hash(this.folderId, this.name);
    }
}
