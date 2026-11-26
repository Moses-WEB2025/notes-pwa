package ch.qos.logback.classic.android;

import android.database.sqlite.SQLiteDatabase;
import ch.qos.logback.core.util.Duration;

/* JADX INFO: loaded from: classes.dex */
public interface SQLiteLogCleaner {
    void performLogCleanup(SQLiteDatabase sQLiteDatabase, Duration duration);
}
