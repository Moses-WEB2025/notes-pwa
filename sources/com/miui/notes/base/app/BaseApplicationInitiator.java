package com.miui.notes.base.app;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: BaseApplicationInitiator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016JO\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0010\u0010\r\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0010\u0010\u0010\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u00010\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0015H\u0016J1\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\u0010\u0010\u000f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u00010\u000eH\u0016¢\u0006\u0002\u0010\u0018J;\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00152\b\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0010\u0010\u0010\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0004\u0018\u00010\u000eH\u0016¢\u0006\u0002\u0010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/notes/base/app/BaseApplicationInitiator;", "Landroid/content/ContentProvider;", "Lcom/miui/notes/base/app/IInitiator;", "moduleName", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "onCreate", "", SmartAction.Feature.QUERY, "Landroid/database/Cursor;", "p0", "Landroid/net/Uri;", "p1", "", "p2", "p3", "p4", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "getType", "insert", "Landroid/content/ContentValues;", ToDoStat.DELETE, "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "common_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class BaseApplicationInitiator extends ContentProvider implements IInitiator {
    private final String moduleName;

    @Override // android.content.ContentProvider
    public int delete(Uri p0, String p1, String[] p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri p0, ContentValues p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri p0, String[] p1, String p2, String[] p3, String p4) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri p0, ContentValues p1, String p2, String[] p3) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return 0;
    }

    public BaseApplicationInitiator(String moduleName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        this.moduleName = moduleName;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Context context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Application");
        onAppInit((Application) context);
        Logger.INSTANCE.d(this.moduleName, "onAppInitFinished:" + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return true;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return "";
    }
}
