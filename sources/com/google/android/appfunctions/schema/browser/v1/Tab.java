package com.google.android.appfunctions.schema.browser.v1;

import android.net.Uri;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/browser/v1/Tab;", "", "id", "", "title", "url", "Landroid/net/Uri;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;)V", "getId", "()Ljava/lang/String;", "getTitle", "getUrl", "()Landroid/net/Uri;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.browser.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Tab {
    public static final String ASSIST_FOREGROUND_TYPE = "com.google.android.appfunctions.schema.browser.v1.TabForeground";
    private final String id;
    private final String title;
    private final Uri url;

    public Tab(String id, String title, Uri url) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(url, "url");
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Tab)) {
            return false;
        }
        Tab tab = (Tab) other;
        return Intrinsics.areEqual(this.id, tab.id) && Intrinsics.areEqual(this.title, tab.title) && Intrinsics.areEqual(this.url, tab.url);
    }

    public final String getId() {
        return this.id;
    }

    public final String getTitle() {
        return this.title;
    }

    public final Uri getUrl() {
        return this.url;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.url);
    }
}
