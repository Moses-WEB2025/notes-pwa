package com.google.android.appfunctions.schema.browser.v1;

import android.net.Uri;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lcom/google/android/appfunctions/schema/browser/v1/ShowSiteParams;", "", "url", "Landroid/net/Uri;", "shouldShowInNewTab", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/net/Uri;Z)V", "getUrl", "()Landroid/net/Uri;", "getShouldShowInNewTab", "()Z", "equals", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.browser.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ShowSiteParams {
    private final boolean shouldShowInNewTab;
    private final Uri url;

    public ShowSiteParams(Uri url, boolean z) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.shouldShowInNewTab = z;
    }

    public boolean equals(Object other) {
        if (!(other instanceof ShowSiteParams)) {
            return false;
        }
        ShowSiteParams showSiteParams = (ShowSiteParams) other;
        return Intrinsics.areEqual(this.url, showSiteParams.url) && this.shouldShowInNewTab == showSiteParams.shouldShowInNewTab;
    }

    public final boolean getShouldShowInNewTab() {
        return this.shouldShowInNewTab;
    }

    public final Uri getUrl() {
        return this.url;
    }

    public int hashCode() {
        return Objects.hash(this.url, Boolean.valueOf(this.shouldShowInNewTab));
    }
}
