package com.miui.notes.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.preference.AbstractSharedPreferences;
import com.miui.notes.preference.PreferenceDatabaseHelper;
import com.miui.richeditor.schema.HtmlParser;
import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotesSharedPreferenceProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000e\u000fB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/provider/NotesSharedPreferenceProvider;", "Lcom/miui/notes/preference/PreferenceDatabaseHelper$SharedPreferenceProvider;", "mContext", "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "getSharedPreference", "Landroid/content/SharedPreferences;", "dispose", "", "sp", "sQLiteSharedPreference", "getSQLiteSharedPreference", "()Landroid/content/SharedPreferences;", "SQLiteSharedPreferences", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NotesSharedPreferenceProvider implements PreferenceDatabaseHelper.SharedPreferenceProvider {
    private static final String TAG = "NotesSPProvider";
    private final Context mContext;

    public NotesSharedPreferenceProvider(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
    }

    @Override // com.miui.notes.preference.PreferenceDatabaseHelper.SharedPreferenceProvider
    public SharedPreferences getSharedPreference() {
        SharedPreferences sQLiteSharedPreference = getSQLiteSharedPreference();
        if (sQLiteSharedPreference == null) {
            sQLiteSharedPreference = this.mContext.getSharedPreferences("com.miui.notes_preferences", 0);
        }
        Intrinsics.checkNotNull(sQLiteSharedPreference);
        return sQLiteSharedPreference;
    }

    @Override // com.miui.notes.preference.PreferenceDatabaseHelper.SharedPreferenceProvider
    public void dispose(SharedPreferences sp) {
        Intrinsics.checkNotNullParameter(sp, "sp");
        if (sp instanceof SQLiteSharedPreferences) {
            SQLiteDatabase database = ((SQLiteSharedPreferences) sp).getDatabase();
            database.execSQL("DROP TABLE pref;");
            database.close();
        }
    }

    private final SharedPreferences getSQLiteSharedPreference() {
        try {
            String path = this.mContext.getDatabasePath("note.db").getPath();
            Logger.INSTANCE.d(TAG, "getSQLiteSharedPreference ,path" + path);
            File databasePath = this.mContext.getDatabasePath("note.db");
            File parentFile = databasePath != null ? databasePath.getParentFile() : null;
            if (parentFile != null && !parentFile.exists()) {
                Logger.INSTANCE.e(TAG, "create db dir first!!!!!,ret:" + parentFile.mkdirs());
            }
            boolean z = false;
            SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(path, null, 0, null);
            Cursor cursorRawQuery = sQLiteDatabaseOpenDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE name='pref' AND type='table'", null);
            Intrinsics.checkNotNullExpressionValue(cursorRawQuery, "rawQuery(...)");
            Logger.INSTANCE.d(TAG, "getSQLiteSharedPreference/open cursor");
            try {
                if (cursorRawQuery.moveToNext() && cursorRawQuery.getInt(0) > 0) {
                    z = true;
                }
                cursorRawQuery.close();
                Logger.INSTANCE.d(TAG, "getSQLiteSharedPreference/close cursor");
                if (!z) {
                    return null;
                }
                Intrinsics.checkNotNull(sQLiteDatabaseOpenDatabase);
                return new SQLiteSharedPreferences(sQLiteDatabaseOpenDatabase, "pref");
            } catch (Throwable th) {
                cursorRawQuery.close();
                Logger.INSTANCE.d(TAG, "getSQLiteSharedPreference/close cursor");
                throw th;
            }
        } catch (SQLiteException e) {
            Logger.INSTANCE.e(TAG, "Fail to open database:,exception:" + e + "," + Log.getStackTraceString(e));
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NotesSharedPreferenceProvider.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/provider/NotesSharedPreferenceProvider$SQLiteSharedPreferences;", "Lcom/miui/notes/preference/AbstractSharedPreferences;", "database", "Landroid/database/sqlite/SQLiteDatabase;", HtmlParser.TableElement.TAG, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V", "getDatabase", "()Landroid/database/sqlite/SQLiteDatabase;", "loadFromDatabase", "", "db", "edit", "Landroid/content/SharedPreferences$Editor;", "registerOnSharedPreferenceChangeListener", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "unregisterOnSharedPreferenceChangeListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    static final class SQLiteSharedPreferences extends AbstractSharedPreferences {
        private final SQLiteDatabase database;

        public final SQLiteDatabase getDatabase() {
            return this.database;
        }

        public SQLiteSharedPreferences(SQLiteDatabase database, String table) {
            Intrinsics.checkNotNullParameter(database, "database");
            Intrinsics.checkNotNullParameter(table, "table");
            this.database = database;
            loadFromDatabase(database, table);
        }

        public final void loadFromDatabase(SQLiteDatabase db, String table) {
            Intrinsics.checkNotNullParameter(db, "db");
            Intrinsics.checkNotNullParameter(table, "table");
            Logger.INSTANCE.d(NotesSharedPreferenceProvider.TAG, "loadFromDatabase/open cursor");
            final Cursor cursorQuery = db.query(table, new String[]{"key", "value"}, null, null, null, null, null);
            Intrinsics.checkNotNullExpressionValue(cursorQuery, "query(...)");
            NoteEntityHelper.INSTANCE.useWithClose(cursorQuery, "", new Function1() { // from class: com.miui.notes.provider.NotesSharedPreferenceProvider$SQLiteSharedPreferences$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return NotesSharedPreferenceProvider.SQLiteSharedPreferences.loadFromDatabase$lambda$0(this.f$0, cursorQuery, (Cursor) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit loadFromDatabase$lambda$0(SQLiteSharedPreferences sQLiteSharedPreferences, Cursor cursor, Cursor it) {
            Intrinsics.checkNotNullParameter(it, "it");
            sQLiteSharedPreferences.mPrefs.clear();
            while (cursor.moveToNext()) {
                sQLiteSharedPreferences.mPrefs.put(cursor.getString(0), cursor.getString(1));
            }
            return Unit.INSTANCE;
        }

        @Override // android.content.SharedPreferences
        public SharedPreferences.Editor edit() {
            throw new UnsupportedOperationException();
        }

        @Override // android.content.SharedPreferences
        public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            throw new UnsupportedOperationException();
        }

        @Override // android.content.SharedPreferences
        public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            throw new UnsupportedOperationException();
        }
    }
}
