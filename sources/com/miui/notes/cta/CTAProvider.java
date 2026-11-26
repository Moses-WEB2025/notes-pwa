package com.miui.notes.cta;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: CTAProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016JM\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J1\u0010\u0015\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u0017J;\u0010\u0018\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u0019J$\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001bH\u0016¨\u0006 "}, d2 = {"Lcom/miui/notes/cta/CTAProvider;", "Landroid/content/ContentProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreate", "", SmartAction.Feature.QUERY, "Landroid/database/Cursor;", CallMethod.ARG_URI, "Landroid/net/Uri;", "projection", "", "", "selection", "selectionArgs", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "getType", "insert", "values", "Landroid/content/ContentValues;", ToDoStat.DELETE, "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "call", "Landroid/os/Bundle;", XMLWriter.METHOD, "arg", SecureDatabaseHelper.TABLE_EXTRAS, "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CTAProvider extends ContentProvider {
    private static final String IS_ACCEPT = "is_accept";

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Logger.INSTANCE.d("CTAProvider", "onCreate");
        return false;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        throw new UnsupportedOperationException();
    }

    @Override // android.content.ContentProvider
    public Bundle call(String method, String arg, Bundle extras) {
        Intrinsics.checkNotNullParameter(method, "method");
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_accept", PreferenceUtils.isCTAAccepted(getContext()));
        return bundle;
    }
}
