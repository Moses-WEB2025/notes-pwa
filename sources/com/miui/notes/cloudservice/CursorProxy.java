package com.miui.notes.cloudservice;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class CursorProxy extends CursorWrapper {
    private static final int MAX_RELOAD_NUM = 1;
    private static final String TAG = "CursorProxy";
    private CallBack mCallBack;
    private Context mContext;
    private Cursor mCursor;
    private String[] mProjection;
    private int mReloadNum;
    private String mSelection;
    private String[] mSelectionArgs;
    private String mSortOrder;
    private Uri mUri;

    public interface CallBack {
        void onCrash() throws CursorCrashException;

        void onMoveToNext(Cursor cursor) throws NoteSyncException;

        void onReload();
    }

    public static class CallBackNullException extends Exception {
        private static final String message = "CallBack should not be null";

        public CallBackNullException() {
            super(message);
        }
    }

    public static class CursorNullException extends Exception {
        private static final String message = "Cursor:Instantiation Error";

        public CursorNullException() {
            super(message);
        }
    }

    public static class CursorCrashException extends NoteSyncException {
        private static final String message = "Cursor crash";

        public CursorCrashException() {
            super(message, false);
        }
    }

    public CursorProxy(Context context, CallBack callBack, Uri uri, String[] strArr, String str, String[] strArr2, String str2) throws CallBackNullException, CursorNullException {
        super(null);
        this.mReloadNum = 0;
        this.mContext = context;
        this.mCallBack = callBack;
        if (callBack == null) {
            throw new CallBackNullException();
        }
        this.mUri = uri;
        this.mProjection = strArr;
        this.mSelection = str;
        this.mSelectionArgs = strArr2;
        this.mSortOrder = str2;
        load();
    }

    private void load() throws CursorNullException {
        Cursor cursorQuery = query();
        this.mCursor = cursorQuery;
        if (cursorQuery == null) {
            throw new CursorNullException();
        }
    }

    private boolean reLoad() throws CursorCrashException {
        if (this.mReloadNum < 1) {
            this.mCallBack.onReload();
            try {
                load();
                this.mCursor.moveToFirst();
                this.mReloadNum++;
                Logger.INSTANCE.i(TAG, "Cursor illegalStateException,cursor load again");
                return true;
            } catch (CursorNullException e) {
                Logger.INSTANCE.e(TAG, e.toString());
                this.mCallBack.onCrash();
                return false;
            }
        }
        Logger.INSTANCE.e(TAG, "Cursor illegalStateException,finish");
        this.mCallBack.onCrash();
        return false;
    }

    private Cursor query() {
        return this.mContext.getContentResolver().query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder);
    }

    @Override // android.database.CursorWrapper
    public Cursor getWrappedCursor() {
        return this.mCursor;
    }

    @Override // android.database.CursorWrapper, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isClosed() {
        return this.mCursor.isClosed();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getCount() {
        return this.mCursor.getCount();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    @Deprecated
    public void deactivate() {
        this.mCursor.deactivate();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean moveToFirst() {
        return this.mCursor.moveToFirst();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnCount() {
        return this.mCursor.getColumnCount();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnIndex(String str) {
        return this.mCursor.getColumnIndex(str);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
        return this.mCursor.getColumnIndexOrThrow(str);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getColumnName(int i) {
        return this.mCursor.getColumnName(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String[] getColumnNames() {
        return this.mCursor.getColumnNames();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public double getDouble(int i) {
        return this.mCursor.getDouble(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public Bundle getExtras() {
        return this.mCursor.getExtras();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public float getFloat(int i) {
        return this.mCursor.getFloat(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getInt(int i) {
        return this.mCursor.getInt(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public long getLong(int i) {
        return this.mCursor.getLong(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public short getShort(int i) {
        return this.mCursor.getShort(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        return this.mCursor.getString(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        this.mCursor.copyStringToBuffer(i, charArrayBuffer);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public byte[] getBlob(int i) {
        return this.mCursor.getBlob(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean getWantsAllOnMoveCalls() {
        return this.mCursor.getWantsAllOnMoveCalls();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isAfterLast() {
        return this.mCursor.isAfterLast();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isBeforeFirst() {
        return this.mCursor.isBeforeFirst();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isFirst() {
        return this.mCursor.isFirst();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isLast() {
        return this.mCursor.isLast();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getType(int i) {
        return this.mCursor.getType(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isNull(int i) {
        return this.mCursor.isNull(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean moveToLast() {
        return this.mCursor.moveToLast();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean move(int i) {
        return this.mCursor.move(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean moveToPosition(int i) {
        Cursor cursor = this.mCursor;
        if (cursor == null || cursor.isClosed()) {
            return false;
        }
        return this.mCursor.moveToPosition(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean moveToNext() {
        return this.mCursor.moveToNext();
    }

    private boolean moveToNextThrowException() throws CursorCrashException {
        try {
            return this.mCursor.moveToNext();
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.e(TAG, "moveToNextThrowException error:" + e.toString());
            return false;
        } catch (IllegalStateException unused) {
            return reLoad();
        }
    }

    public void traverse() throws NoteSyncException {
        while (moveToNextThrowException()) {
            this.mCallBack.onMoveToNext(this.mCursor);
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getPosition() {
        return this.mCursor.getPosition();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean moveToPrevious() {
        return this.mCursor.moveToPrevious();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
        this.mCursor.registerContentObserver(contentObserver);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mCursor.registerDataSetObserver(dataSetObserver);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    @Deprecated
    public boolean requery() {
        return this.mCursor.requery();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public Bundle respond(Bundle bundle) {
        return this.mCursor.respond(bundle);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        this.mCursor.setNotificationUri(contentResolver, uri);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public Uri getNotificationUri() {
        return this.mCursor.getNotificationUri();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
        this.mCursor.unregisterContentObserver(contentObserver);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mCursor.unregisterDataSetObserver(dataSetObserver);
    }
}
