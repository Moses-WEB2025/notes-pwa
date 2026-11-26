package com.google.android.appfunctions.schema.files.v1;

import com.google.android.appfunctions.schema.types.v1.DateTime;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/files/v1/File;", "", "id", "", "name", "dateCreated", "Lcom/google/android/appfunctions/schema/types/v1/DateTime;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/DateTime;)V", "getId", "()Ljava/lang/String;", "getName", "getDateCreated", "()Lcom/google/android/appfunctions/schema/types/v1/DateTime;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.files.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class File {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.files.v1.FileForeground";
    private final DateTime dateCreated;
    private final String id;
    private final String name;

    public File(String id, String name, DateTime dateCreated) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(dateCreated, "dateCreated");
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public boolean equals(Object other) {
        if (!(other instanceof File)) {
            return false;
        }
        File file = (File) other;
        return Intrinsics.areEqual(this.id, file.id) && Intrinsics.areEqual(this.name, file.name) && Intrinsics.areEqual(this.dateCreated, file.dateCreated);
    }

    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.name, this.dateCreated);
    }
}
