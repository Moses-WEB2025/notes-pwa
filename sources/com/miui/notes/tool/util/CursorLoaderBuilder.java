package com.miui.notes.tool.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CursorLoaderBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 +2\u00020\u0001:\u0002*+B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u0018\u001a\u00020\u00002\b\u0010\u0019\u001a\u0004\u0018\u00010\rJ\u001b\u0010\u001a\u001a\u00020\u00002\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f¢\u0006\u0002\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\u00002\b\u0010\u001e\u001a\u0004\u0018\u00010\u0010J\u001b\u0010\u001f\u001a\u00020\u00002\u000e\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f¢\u0006\u0002\u0010\u001cJ\u0010\u0010!\u001a\u00020\u00002\b\u0010\"\u001a\u0004\u0018\u00010\u0010J\u0016\u0010#\u001a\u00020\u00002\u000e\u0010$\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016J\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00170&JK\u0010'\u001a\u00020(2\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00102\u000e\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\b\u0010\"\u001a\u0004\u0018\u00010\u0010H\u0002¢\u0006\u0002\u0010)R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0011R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/miui/notes/tool/util/CursorLoaderBuilder;", "", "iLoadManager", "Landroidx/loader/app/LoaderManager;", "iId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/loader/app/LoaderManager;I)V", "getILoadManager", "()Landroidx/loader/app/LoaderManager;", "getIId", "()I", "iUri", "Landroid/net/Uri;", "iProjection", "", "", "[Ljava/lang/String;", "iSelection", "iSelectionArgs", "iSortOrder", "iCallbacks", "Landroidx/loader/app/LoaderManager$LoaderCallbacks;", "Landroid/database/Cursor;", "setUri", "uri", "setProjection", CursorLoaderBuilder.EXTRA_PROJECTION, "([Ljava/lang/String;)Lcom/miui/notes/tool/util/CursorLoaderBuilder;", "setSelection", CursorLoaderBuilder.EXTRA_SELECTION, "setSelectionArgs", CursorLoaderBuilder.EXTRA_SELECTION_ARGS, "setSortOrder", CursorLoaderBuilder.EXTRA_SORT_ORDER, "setCallbacks", "callbacks", "build", "Landroidx/loader/content/Loader;", "getQueryBundle", "Landroid/os/Bundle;", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/os/Bundle;", "DefaultLoaderCallbacks", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CursorLoaderBuilder {
    private static final String EXTRA_PROJECTION = "projection";
    private static final String EXTRA_SELECTION = "selection";
    private static final String EXTRA_SELECTION_ARGS = "selectionArgs";
    private static final String EXTRA_SORT_ORDER = "sortOrder";
    private static final String EXTRA_URI = "uri";
    private LoaderManager.LoaderCallbacks<Cursor> iCallbacks;
    private final int iId;
    private final LoaderManager iLoadManager;
    private String[] iProjection;
    private String iSelection;
    private String[] iSelectionArgs;
    private String iSortOrder;
    private Uri iUri;

    public CursorLoaderBuilder(LoaderManager iLoadManager, int i) {
        Intrinsics.checkNotNullParameter(iLoadManager, "iLoadManager");
        this.iLoadManager = iLoadManager;
        this.iId = i;
    }

    public final int getIId() {
        return this.iId;
    }

    public final LoaderManager getILoadManager() {
        return this.iLoadManager;
    }

    /* JADX INFO: compiled from: CursorLoaderBuilder.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\"\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016Je\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0010\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0010\u0010\u0013\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0018\u00010\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0011H\u0004¢\u0006\u0002\u0010\u0015J \u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u00062\b\u0010\u0019\u001a\u0004\u0018\u00010\u0002H\u0016J\u0018\u0010\u001a\u001a\u00020\u00172\u000e\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006H\u0016¨\u0006\u001b"}, d2 = {"Lcom/miui/notes/tool/util/CursorLoaderBuilder$DefaultLoaderCallbacks;", "Landroidx/loader/app/LoaderManager$LoaderCallbacks;", "Landroid/database/Cursor;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onCreateLoader", "Landroidx/loader/content/Loader;", "id", "", "args", "Landroid/os/Bundle;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "uri", "Landroid/net/Uri;", CursorLoaderBuilder.EXTRA_PROJECTION, "", "", CursorLoaderBuilder.EXTRA_SELECTION, CursorLoaderBuilder.EXTRA_SELECTION_ARGS, CursorLoaderBuilder.EXTRA_SORT_ORDER, "(Landroid/content/Context;ILandroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroidx/loader/content/Loader;", "onLoadFinished", "", "loader", "data", "onLoaderReset", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static class DefaultLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Intrinsics.checkNotNullParameter(loader, "loader");
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public void onLoaderReset(Loader<Cursor> loader) {
            Intrinsics.checkNotNullParameter(loader, "loader");
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
            Uri uri = args != null ? (Uri) args.getParcelable("uri") : null;
            Intrinsics.checkNotNull(uri);
            return onCreateLoader(companion, id, uri, args.getStringArray(CursorLoaderBuilder.EXTRA_PROJECTION), args.getString(CursorLoaderBuilder.EXTRA_SELECTION), args.getStringArray(CursorLoaderBuilder.EXTRA_SELECTION_ARGS), args.getString(CursorLoaderBuilder.EXTRA_SORT_ORDER));
        }

        protected final Loader<Cursor> onCreateLoader(Context context, int id, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(uri, "uri");
            return new CursorLoader(context, uri, projection, selection, selectionArgs, sortOrder);
        }
    }

    public final CursorLoaderBuilder setUri(Uri uri) {
        this.iUri = uri;
        return this;
    }

    public final CursorLoaderBuilder setProjection(String[] projection) {
        this.iProjection = projection;
        return this;
    }

    public final CursorLoaderBuilder setSelection(String selection) {
        this.iSelection = selection;
        return this;
    }

    public final CursorLoaderBuilder setSelectionArgs(String[] selectionArgs) {
        this.iSelectionArgs = selectionArgs;
        return this;
    }

    public final CursorLoaderBuilder setSortOrder(String sortOrder) {
        this.iSortOrder = sortOrder;
        return this;
    }

    public final CursorLoaderBuilder setCallbacks(LoaderManager.LoaderCallbacks<Cursor> callbacks) {
        this.iCallbacks = callbacks;
        return this;
    }

    public final Loader<Cursor> build() {
        Bundle queryBundle = getQueryBundle(this.iUri, this.iProjection, this.iSelection, this.iSelectionArgs, this.iSortOrder);
        if (this.iCallbacks == null) {
            this.iCallbacks = new DefaultLoaderCallbacks();
        }
        LoaderManager loaderManager = this.iLoadManager;
        int i = this.iId;
        LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = this.iCallbacks;
        Intrinsics.checkNotNull(loaderCallbacks);
        Loader<Cursor> loaderRestartLoader = loaderManager.restartLoader(i, queryBundle, loaderCallbacks);
        Intrinsics.checkNotNullExpressionValue(loaderRestartLoader, "restartLoader(...)");
        return loaderRestartLoader;
    }

    private final Bundle getQueryBundle(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("uri", uri);
        bundle.putStringArray(EXTRA_PROJECTION, projection);
        bundle.putString(EXTRA_SELECTION, selection);
        bundle.putStringArray(EXTRA_SELECTION_ARGS, selectionArgs);
        bundle.putString(EXTRA_SORT_ORDER, sortOrder);
        return bundle;
    }
}
