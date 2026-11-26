package com.miui.notes.cloudservice;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class DataEntity {
    public static final int DATA_COLUMN_CONTENT_INDEX = 3;
    public static final int DATA_COLUMN_DATA1_INDEX = 4;
    public static final int DATA_COLUMN_DATA2_INDEX = 5;
    public static final int DATA_COLUMN_DATA3_INDEX = 6;
    public static final int DATA_COLUMN_DATA4_INDEX = 7;
    public static final int DATA_COLUMN_DATA5_INDEX = 8;
    public static final int DATA_COLUMN_DIRTY_INDEX = 10;
    public static final int DATA_COLUMN_FILE_ID_INDEX = 9;
    public static final int DATA_COLUMN_ID_INDEX = 0;
    public static final int DATA_COLUMN_MIME_TYPE_INDEX = 1;
    public static final int DATA_COLUMN_NOTE_ID_INDEX = 2;
    public static final int DATA_CONTENT_TYPE_INDEX = 11;
    public static final int DATA_NEED_PUSH_INDEX = 13;
    public static final int DATA_NEED_UPDATE_INDEX = 12;
    public static final String[] DATA_PROJECTIONS = {"_id", Notes.Data.MIME_TYPE, "note_id", "content", "data1", Notes.Data.DATA2, "data3", Notes.Data.DATA4, Notes.Data.DATA5, "file_id", Notes.Data.DIRTY, Notes.Data.DATA_CONTENT_TYPE, Notes.Data.DATA_NEED_UPDATE, Notes.Data.DATA_NEED_PUSH};
    private static final String TAG = "DataEntity";
    private String mContent;
    private long mData1;
    private long mData2;
    private String mData3;
    private String mData4;
    private String mData5;
    private String mDataContentType;
    private int mDirty;
    private String mFileId;
    private long mId;
    private String mMimeType;
    private int mNeedPush;
    private int mNeedUpdate;
    private long mNoteId;

    public static DataEntity valueOf(Cursor cursor) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.mId = cursor.getLong(0);
        dataEntity.mMimeType = cursor.getString(1);
        dataEntity.mNoteId = cursor.getLong(2);
        dataEntity.mContent = cursor.getString(3);
        dataEntity.mData1 = cursor.getLong(4);
        dataEntity.mData2 = cursor.getLong(5);
        dataEntity.mData3 = cursor.getString(6);
        dataEntity.mData4 = cursor.getString(7);
        dataEntity.mData5 = cursor.getString(8);
        dataEntity.mFileId = cursor.getString(9);
        dataEntity.mDirty = cursor.getInt(10);
        dataEntity.mDataContentType = cursor.getString(11);
        dataEntity.mNeedUpdate = cursor.getInt(12);
        dataEntity.mNeedPush = cursor.getInt(13);
        return dataEntity;
    }

    public static ArrayList<DataEntity> getDatasByNote(Context context, long j) throws NoteSyncException {
        Cursor cursorQuery = context.getContentResolver().query(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, DATA_PROJECTIONS, "note_id=" + j, null, null);
        if (cursorQuery == null) {
            throw new NoteSyncException("Fail to getDatasByNote, cursor is null, noteId=" + j);
        }
        try {
            ArrayList<DataEntity> arrayList = new ArrayList<>(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(valueOf(cursorQuery));
            }
            cursorQuery.close();
            return arrayList;
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    public static Cursor getFileMissedCursor(Context context) {
        try {
            return context.getContentResolver().query(Uri.withAppendedPath(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, "missed"), DATA_PROJECTIONS, null, null, null);
        } catch (Exception e) {
            Logger.INSTANCE.i(TAG, e.getMessage());
            return null;
        }
    }

    public static DataEntity newTextData(String str) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.mMimeType = Notes.TextData.CONTENT_ITEM_TYPE;
        dataEntity.mContent = str;
        return dataEntity;
    }

    public static DataEntity newMultiTextData(String str) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.mMimeType = Notes.TextData.CONTENT_ITEM_TYPE;
        if (!TextUtils.isEmpty(str)) {
            dataEntity.mContent = str;
        } else {
            dataEntity.mContent = NoteApp.getInstance().getString(R.string.pad_note_list_not_text_stub);
        }
        dataEntity.mDataContentType = "multi_handwrite";
        return dataEntity;
    }

    public static DataEntity newCallData(String str, long j) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.mMimeType = Notes.CallData.CONTENT_ITEM_TYPE;
        dataEntity.setPhoneNumber(str);
        dataEntity.setPhoneTime(j);
        return dataEntity;
    }

    public static DataEntity newFileData(String str, String str2, String str3) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.mMimeType = str;
        dataEntity.mFileId = str2;
        dataEntity.mContent = str3;
        return dataEntity;
    }

    public static DataEntity newMultiNoteFileData(String str, String str2, String str3) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.mMimeType = str;
        dataEntity.mFileId = str2;
        dataEntity.mContent = str3;
        dataEntity.mDataContentType = "multi_handwrite";
        return dataEntity;
    }

    public static void markDataDirty(Context context, long j) {
        Logger.INSTANCE.i(TAG, "markDataDirty id :" + j);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.DIRTY, (Integer) 1);
        contentValues.put("file_id", "");
        context.getContentResolver().update(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "note_id=" + j, null);
    }

    private DataEntity() {
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (isCallData()) {
            jSONObject.put("phone", getPhoneNumber());
            jSONObject.put("time", getPhoneTime());
            return jSONObject;
        }
        if (isTextData()) {
            throw new UnsupportedOperationException("Can't convert text data to json");
        }
        jSONObject.put("fileId", this.mFileId);
        jSONObject.put("mimeType", this.mMimeType);
        jSONObject.put("digest", this.mContent);
        return jSONObject;
    }

    public long getId() {
        return this.mId;
    }

    public void setNoteId(long j) {
        this.mNoteId = j;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String getDataContentType() {
        return this.mDataContentType;
    }

    public String getContent() {
        return this.mContent;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public String getFileId() {
        return this.mFileId;
    }

    public boolean isTextData() {
        return Notes.TextData.CONTENT_ITEM_TYPE.equals(this.mMimeType);
    }

    public boolean isCallData() {
        return Notes.CallData.CONTENT_ITEM_TYPE.equals(this.mMimeType);
    }

    public boolean isFileData() {
        return (isTextData() || isCallData()) ? false : true;
    }

    public boolean isDirty() {
        return this.mDirty != 0;
    }

    public void setDirty(boolean z) {
        this.mDirty = z ? 1 : 0;
    }

    public boolean isNeedUpdate() {
        return this.mNeedUpdate != 0;
    }

    public void setNeedUpdate(boolean z) {
        this.mNeedUpdate = z ? 1 : 0;
    }

    public boolean isNeedPush() {
        return this.mNeedPush != 0;
    }

    public void setNeedPush(boolean z) {
        this.mNeedPush = z ? 1 : 0;
    }

    public void updateFile(Context context, byte[] bArr) throws NoteSyncException {
        OutputStream outputStream = null;
        try {
            try {
                Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId);
                OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uriWithAppendedId);
                if (outputStreamOpenOutputStream == null) {
                    throw new NoteSyncException("Fail to openOutputStream for uri: " + uriWithAppendedId);
                }
                outputStreamOpenOutputStream.write(bArr);
                if (outputStreamOpenOutputStream != null) {
                    try {
                        outputStreamOpenOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e2) {
                throw new NoteSyncException(e2);
            } catch (IOException e3) {
                throw new NoteSyncException(e3);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    outputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public void updateFileId(Context context, String str) throws NoteSyncException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("file_id", str);
        contentValues.put(Notes.Data.DATA_NEED_PUSH, (Integer) 0);
        if (context.getContentResolver().update(ContentUris.withAppendedId(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), contentValues, null, null) <= 0) {
            throw new NoteSyncException("Fail to update file_id, data_id=" + this.mId);
        }
        this.mFileId = str;
        setNeedPush(false);
    }

    public void updateNeedUpdateField(Context context, boolean z) throws NoteSyncException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.DATA_NEED_UPDATE, Integer.valueOf(z ? 1 : 0));
        if (context.getContentResolver().update(ContentUris.withAppendedId(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId), contentValues, null, null) <= 0) {
            throw new NoteSyncException("Fail to update file_id, data_id=" + this.mId);
        }
        setNeedUpdate(z);
    }

    public byte[] getFileData(Context context) throws FileNotFoundException, NoteSyncException {
        InputStream inputStreamOpenInputStream;
        Uri uriWithAppendedId = ContentUris.withAppendedId(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId);
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uriWithAppendedId);
        } catch (FileNotFoundException e) {
            Logger.INSTANCE.i(TAG, "failed to open files supported by provider at content://notes/data/" + this.mId + e.getMessage());
            inputStreamOpenInputStream = null;
        }
        if (inputStreamOpenInputStream == null) {
            throw new NoteSyncException("Fail to openInputStream for uri: " + uriWithAppendedId);
        }
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[8192];
                while (true) {
                    int i = inputStreamOpenInputStream.read(bArr);
                    if (i <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    throw th;
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    inputStreamOpenInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return byteArray;
            } catch (Throwable th) {
                inputStreamOpenInputStream.close();
                throw th;
            }
        } catch (IOException e4) {
            throw new NoteSyncException(e4);
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0039 A[DONT_GENERATE, EXC_TOP_SPLITTER, PHI: r4 r5
  0x0039: PHI (r4v3 boolean) = (r4v2 boolean), (r4v5 boolean) binds: [B:16:0x006f, B:7:0x0037] A[DONT_GENERATE, DONT_INLINE]
  0x0039: PHI (r5v3 java.io.InputStream) = (r5v1 java.io.InputStream), (r5v5 java.io.InputStream) binds: [B:16:0x006f, B:7:0x0037] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    public boolean isFileExisted(Context context) {
        boolean z = false;
        InputStream inputStreamOpenInputStream = null;
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(ContentUris.withAppendedId(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, this.mId));
            z = inputStreamOpenInputStream != null;
            Logger.INSTANCE.i(TAG, "isUploading:files supported by provider at content://notes/data/" + this.mId + ":" + z);
        } catch (FileNotFoundException e) {
            Logger.INSTANCE.i(TAG, "isUploading:files supported by provider at content://notes/data/" + this.mId + ":" + z + ",exception:" + e.getMessage());
        } finally {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return z;
    }

    public String getFileExtension() {
        return "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(this.mMimeType);
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.Data.MIME_TYPE, this.mMimeType);
        contentValues.put(Notes.Data.DIRTY, Integer.valueOf(this.mDirty));
        if (isTextData()) {
            String str = this.mContent;
            if (str != null) {
                contentValues.put("content", str);
            }
        } else if (isCallData()) {
            contentValues.put("data1", Long.valueOf(this.mData1));
            contentValues.put("data3", this.mData3);
        } else {
            String str2 = this.mContent;
            if (str2 != null) {
                contentValues.put("content", str2);
            }
            String str3 = this.mFileId;
            if (str3 != null) {
                contentValues.put("file_id", str3);
            }
        }
        if (!TextUtils.isEmpty(this.mDataContentType)) {
            contentValues.put(Notes.Data.DATA_CONTENT_TYPE, this.mDataContentType);
        }
        return contentValues;
    }

    private String getPhoneNumber() {
        return this.mData3;
    }

    private void setPhoneNumber(String str) {
        this.mData3 = str;
    }

    private long getPhoneTime() {
        return this.mData1;
    }

    private void setPhoneTime(long j) {
        this.mData1 = j;
    }
}
