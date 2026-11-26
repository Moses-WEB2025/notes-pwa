package com.miui.todo.cloudservice;

import android.accounts.Account;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.text.TextUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cloudservice.NoteE2EEManager;
import com.miui.notes.cloudservice.NoteSyncException;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.xiaomi.micloudsdk.exception.CloudParameterError;
import com.xiaomi.micloudsdk.exception.CloudRichMediaServerException;
import com.xiaomi.micloudsdk.exception.FileTooLargeException;
import com.xiaomi.micloudsdk.micloudrichmedia.MiCloudRichMediaManager2;
import com.xiaomi.micloudsdk.micloudrichmedia.MicloudDownload;
import com.xiaomi.micloudsdk.micloudrichmedia.UploadData;
import com.xiaomi.micloudsdk.micloudrichmedia.UploadResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import miuix.core.util.IOUtils;

/* JADX INFO: loaded from: classes4.dex */
class TodoMediaManager {
    private static final String TAG = "NoteMediaManager";
    private Context mContext;
    private MiCloudRichMediaManager2 mMediaManager;

    TodoMediaManager(Context context, Account account) {
        this.mContext = context;
        this.mMediaManager = new MiCloudRichMediaManager2(this.mContext, account, NoteE2EEManager.isE2EEOpen());
    }

    private String parseType() {
        return "note_rec";
    }

    private String parseExtension() {
        return ".mp3";
    }

    void upload(TodoEntity todoEntity) throws NoteSyncException {
        if (TextUtils.isEmpty(todoEntity.getAudioFileField())) {
            try {
                byte[] fileData = todoEntity.getFileData(this.mContext);
                if (fileData != null && fileData.length != 0) {
                    UploadResult uploadResultUpload = this.mMediaManager.upload(new UploadData(fileData, parseType(), parseExtension()));
                    if (uploadResultUpload == null || TextUtils.isEmpty(uploadResultUpload.fileId)) {
                        Logger.INSTANCE.w(TAG, "result  is fail " + uploadResultUpload);
                        throw new NoteSyncException("uploadFile fail. result=" + uploadResultUpload);
                    }
                    todoEntity.updateFileId(this.mContext, uploadResultUpload.fileId);
                    todoEntity.setAudioFileSize(todoEntity.getAudioFileSize(this.mContext));
                    TodoProviderAccessUtils.updateItem(todoEntity, true);
                    return;
                }
                Logger.INSTANCE.i(TAG, "Bytes of DataEntity is null,upload failed:content://notes/data/" + todoEntity.getId());
            } catch (NetworkErrorException | CloudParameterError | CloudRichMediaServerException | FileTooLargeException | IOException e) {
                throw new NoteSyncException(e);
            } catch (FileNotFoundException e2) {
                Logger.INSTANCE.w(TAG, "uploadFile: can't find file for data, id=" + todoEntity.getId() + ",exception:" + e2);
            }
        }
    }

    void download(TodoEntity todoEntity) throws Throwable {
        if (TextUtils.isEmpty(todoEntity.getAudioFileField()) || todoEntity.isAudioFileReady()) {
            return;
        }
        File file = new File(this.mContext.getCacheDir() + File.separator + todoEntity.getAudioFileField());
        MicloudDownload.Result resultDownload2 = MicloudDownload.Result.Error;
        FileInputStream fileInputStream = null;
        try {
            try {
                resultDownload2 = this.mMediaManager.download2(todoEntity.getAudioFileField(), parseType(), file);
                if (resultDownload2 == MicloudDownload.Result.OK) {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        todoEntity.updateAudioFile(IOUtils.toByteArray(fileInputStream2));
                        fileInputStream = fileInputStream2;
                    } catch (NetworkErrorException | CloudParameterError | CloudRichMediaServerException | IOException e) {
                        e = e;
                        throw new NoteSyncException(e);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (resultDownload2 == MicloudDownload.Result.OK && file.exists()) {
                            file.delete();
                        }
                        IOUtils.closeQuietly((InputStream) fileInputStream);
                        throw th;
                    }
                } else {
                    Logger.INSTANCE.w(TAG, "download result Fail ");
                }
                if (resultDownload2 == MicloudDownload.Result.OK && file.exists()) {
                    file.delete();
                }
                IOUtils.closeQuietly((InputStream) fileInputStream);
            } catch (NetworkErrorException | CloudParameterError | CloudRichMediaServerException | IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
