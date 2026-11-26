package com.google.android.appfunctions.schema.notes.v1;

import android.net.Uri;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"Lcom/google/android/appfunctions/schema/notes/v1/Attachment;", "", "displayName", "", "mimeType", CallMethod.ARG_URI, "Landroid/net/Uri;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;)V", "getDisplayName", "()Ljava/lang/String;", "getMimeType", "getUri", "()Landroid/net/Uri;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.notes.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Attachment {
    private final String displayName;
    private final String mimeType;
    private final Uri uri;

    public Attachment(String displayName, String str, Uri uri) {
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.displayName = displayName;
        this.mimeType = str;
        this.uri = uri;
    }

    public /* synthetic */ Attachment(String str, String str2, Uri uri, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, uri);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Attachment)) {
            return false;
        }
        Attachment attachment = (Attachment) other;
        return Intrinsics.areEqual(this.displayName, attachment.displayName) && Intrinsics.areEqual(this.mimeType, attachment.mimeType) && Intrinsics.areEqual(this.uri, attachment.uri);
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final String getMimeType() {
        return this.mimeType;
    }

    public final Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Objects.hash(this.displayName, this.mimeType, this.uri);
    }
}
