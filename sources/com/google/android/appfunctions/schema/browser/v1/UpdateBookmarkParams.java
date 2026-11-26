package com.google.android.appfunctions.schema.browser.v1;

import android.net.Uri;
import com.google.android.appfunctions.schema.types.v1.SetField;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005¢\u0006\u0004\b\b\u0010\tJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/browser/v1/UpdateBookmarkParams;", "", "bookmarkId", "", "name", "Lcom/google/android/appfunctions/schema/types/v1/SetField;", "url", "Landroid/net/Uri;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/types/v1/SetField;Lcom/google/android/appfunctions/schema/types/v1/SetField;)V", "getBookmarkId", "()Ljava/lang/String;", "getName", "()Lcom/google/android/appfunctions/schema/types/v1/SetField;", "getUrl", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.browser.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class UpdateBookmarkParams {
    private final String bookmarkId;
    private final SetField<String> name;
    private final SetField<Uri> url;

    /* JADX WARN: Multi-variable type inference failed */
    public UpdateBookmarkParams(String bookmarkId, SetField<String> setField, SetField<? extends Uri> setField2) {
        Intrinsics.checkNotNullParameter(bookmarkId, "bookmarkId");
        this.bookmarkId = bookmarkId;
        this.name = setField;
        this.url = setField2;
    }

    public /* synthetic */ UpdateBookmarkParams(String str, SetField setField, SetField setField2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : setField, (i & 4) != 0 ? null : setField2);
    }

    public boolean equals(Object other) {
        if (!(other instanceof UpdateBookmarkParams)) {
            return false;
        }
        UpdateBookmarkParams updateBookmarkParams = (UpdateBookmarkParams) other;
        return Intrinsics.areEqual(this.bookmarkId, updateBookmarkParams.bookmarkId) && Intrinsics.areEqual(this.name, updateBookmarkParams.name) && Intrinsics.areEqual(this.url, updateBookmarkParams.url);
    }

    public final String getBookmarkId() {
        return this.bookmarkId;
    }

    public final SetField<String> getName() {
        return this.name;
    }

    public final SetField<Uri> getUrl() {
        return this.url;
    }

    public int hashCode() {
        return Objects.hash(this.bookmarkId, this.name, this.url);
    }
}
