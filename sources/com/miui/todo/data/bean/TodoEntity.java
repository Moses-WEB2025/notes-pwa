package com.miui.todo.data.bean;

import android.content.ContentValues;
import android.content.Context;
import com.google.gson.Gson;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cloudservice.NoteSyncException;
import com.miui.todo.data.Todo;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import com.xiaomi.opensdk.pdc.SyncException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class TodoEntity extends TodoBaseEntity {
    private static final String TAG = "TodoEntity";
    private static final Gson mGson = new Gson();
    private String audioFileField;
    private long audioFileSize;
    private long createTime;
    private long customSortId;
    private long expireTime;
    private int folderId;
    private int hideType;
    private long id;
    private int isFinish;
    private long lastModifiedTime;
    private int localStatus;
    private long markFinishTime;
    private int serverStatus;
    private int source;
    private long syncEtag;
    private long syncId;

    public TodoEntity() {
    }

    public TodoEntity(long j, String str, int i, long j2, long j3, int i2, long j4, long j5, long j6, int i3, String str2, long j7, String str3, int i4, int i5, long j8, long j9, int i6, String str4, long j10, int i7, int i8, int i9, long j11, int i10, int i11) {
        super(str, j2, i, i3, str2, str3, i5, i10, i11, j10);
        this.id = j;
        this.expireTime = j3;
        this.isFinish = i2;
        this.markFinishTime = j4;
        this.lastModifiedTime = j5;
        this.createTime = j6;
        this.customSortId = j7;
        this.hideType = i4;
        this.syncId = j8;
        this.syncEtag = j9;
        this.serverStatus = i6;
        this.audioFileField = str4;
        this.folderId = i7;
        this.localStatus = i8;
        this.source = i9;
        this.audioFileSize = j11;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long j) {
        this.expireTime = j;
    }

    public int getIsFinish() {
        return this.isFinish;
    }

    public void setIsFinish(int i) {
        this.isFinish = i;
    }

    public long getMarkFinishTime() {
        return this.markFinishTime;
    }

    public void setMarkFinishTime(long j) {
        this.markFinishTime = j;
    }

    public long getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public void setLastModifiedTime(long j) {
        this.lastModifiedTime = j;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public long getCustomSortId() {
        return this.customSortId;
    }

    public void setCustomSortId(long j) {
        this.customSortId = j;
    }

    public int getHideType() {
        return this.hideType;
    }

    public void setHideType(int i) {
        this.hideType = i;
    }

    public long getSyncId() {
        return this.syncId;
    }

    public void setSyncId(long j) {
        this.syncId = j;
    }

    public long getSyncEtag() {
        return this.syncEtag;
    }

    public void setSyncEtag(long j) {
        this.syncEtag = j;
    }

    public int getServerStatus() {
        return this.serverStatus;
    }

    public void setServerStatus(int i) {
        this.serverStatus = i;
    }

    public String getAudioFileField() {
        return this.audioFileField;
    }

    public void setAudioFileField(String str) {
        this.audioFileField = str;
    }

    public int getFolderId() {
        return this.folderId;
    }

    public void setFolderId(int i) {
        this.folderId = i;
    }

    public int getLocalStatus() {
        return this.localStatus;
    }

    public void setLocalStatus(int i) {
        this.localStatus = i;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int i) {
        this.source = i;
    }

    public long getAudioFileSize() {
        return this.audioFileSize;
    }

    public void setAudioFileSize(long j) {
        this.audioFileSize = j;
    }

    public TodoEntity copy() {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(this.id);
        todoEntity.setExpireTime(getExpireTime());
        todoEntity.setIsFinish(getIsFinish());
        todoEntity.setMarkFinishTime(getMarkFinishTime());
        todoEntity.setLastModifiedTime(getLastModifiedTime());
        todoEntity.setCreateTime(getCreateTime());
        String content = getContent();
        if (content != null) {
            todoEntity.setContent(content);
        }
        todoEntity.setRemindTime(getRemindTime());
        todoEntity.setRemindType(getRemindType());
        todoEntity.setInputType(getInputType());
        String audioFileName = getAudioFileName();
        if (audioFileName != null) {
            todoEntity.setAudioFileName(audioFileName);
        }
        todoEntity.setCustomSortId(getCustomSortId());
        todoEntity.setPlainText(getPlainText());
        todoEntity.setHideType(getHideType());
        todoEntity.setRemindRepeatType(getRemindRepeatType());
        todoEntity.setSyncId(getSyncId());
        todoEntity.setSyncEtag(getSyncEtag());
        todoEntity.setServerStatus(getServerStatus());
        todoEntity.setAudioFileField(getAudioFileField());
        todoEntity.setFirstRemindTime(getFirstRemindTime());
        todoEntity.setFolderId(getFolderId());
        todoEntity.setLocalStatus(getLocalStatus());
        todoEntity.setSource(getSource());
        todoEntity.setAudioFileSize(getAudioFileSize());
        todoEntity.setColorLabel(getColorLabel());
        todoEntity.setListType(getListType());
        return todoEntity;
    }

    public void updateFileId(Context context, String str) throws NoteSyncException {
        ContentValues contentValues = TodoDataUtils.getContentValues(this, false);
        contentValues.put(Todo.AUDIO_FILE_FIELD, str);
        if (TodoProviderAccessUtils.updateItem(this.id, contentValues, true) <= 0) {
            throw new NoteSyncException("Fail to update file_id, todo_id=" + this.id);
        }
        this.audioFileField = str;
    }

    public byte[] getFileData(Context context) throws FileNotFoundException, NoteSyncException {
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.TODO_MP3_DIR_NAME), getAudioFileName());
        if (!file.exists()) {
            throw new NoteSyncException("Fail to find File: " + getAudioFileName());
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[8192];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i > 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        try {
                            fileInputStream.close();
                            return byteArray;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return byteArray;
                        }
                    }
                }
            } catch (IOException e2) {
                throw new NoteSyncException(e2);
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    public long getAudioFileSize(Context context) throws FileNotFoundException, NoteSyncException {
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.TODO_MP3_DIR_NAME), getAudioFileName());
        if (file.exists()) {
            return file.length();
        }
        return 0L;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x007e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    public void updateAudioFile(byte[] bArr) throws Throwable {
        Throwable th;
        IOException e;
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.TODO_MP3_DIR_NAME), getAudioFileName());
        boolean zExists = file.exists();
        ?? r1 = zExists;
        if (!zExists) {
            try {
                boolean zCreateNewFile = file.createNewFile();
                r1 = zCreateNewFile;
                if (!zCreateNewFile) {
                    boolean zExists2 = file.exists();
                    r1 = zExists2;
                    if (!zExists2) {
                        throw new NoteSyncException("Fail to update File: " + getAudioFileName());
                    }
                }
            } catch (IOException e2) {
                Logger.INSTANCE.d(TAG, "updateAudioFile " + e2);
                throw new NoteSyncException(e2);
            }
        }
        try {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(bArr);
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                } catch (IOException e4) {
                    e = e4;
                    throw new NoteSyncException(e);
                }
            } catch (IOException e5) {
                e = e5;
            } catch (Throwable th2) {
                r1 = 0;
                th = th2;
                if (r1 != 0) {
                    try {
                        r1.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (r1 != 0) {
                r1.close();
            }
            throw th;
        }
    }

    public boolean isAudioFileReady() {
        File file = new File(AudioUtils.obtainMp3FileDir(AudioUtils.TODO_MP3_DIR_NAME), getAudioFileName());
        return file.exists() && file.length() == this.audioFileSize;
    }

    public JSONObject toJson() throws SyncException {
        try {
            return new JSONObject(mGson.toJson(this));
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "toJson error" + e);
            throw new SyncException("TodoEntity toJson error");
        }
    }
}
