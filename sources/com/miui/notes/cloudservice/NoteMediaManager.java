package com.miui.notes.cloudservice;

import android.accounts.Account;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.miui.common.tool.AudioUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.HandWriteDataUtils;
import com.miui.notes.tool.AttachmentUtils;
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
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NoteMediaManager {
    private static final String TAG = "NoteMediaManager";
    private Context mContext;
    private MiCloudRichMediaManager2 mMediaManager;

    public NoteMediaManager(Context context, Account account) {
        this.mContext = context;
        this.mMediaManager = new MiCloudRichMediaManager2(this.mContext, account, NoteE2EEManager.isE2EEOpen());
    }

    private String parseType(DataEntity dataEntity) {
        if (AudioUtils.MP3_MINI_TYPE.equals(dataEntity.getMimeType())) {
            return "note_rec";
        }
        return ("ent/file".equals(dataEntity.getMimeType()) || HandWriteDataUtils.IINK_FILE_TYPE.equals(dataEntity.getMimeType())) ? "note_ent" : "note_img";
    }

    private String parseExtension(DataEntity dataEntity) {
        if (AudioUtils.MP3_MINI_TYPE.equals(dataEntity.getMimeType())) {
            return ".mp3";
        }
        if ("ent/file".equals(dataEntity.getMimeType())) {
            return ".ent";
        }
        if (HandWriteDataUtils.IINK_FILE_TYPE.equals(dataEntity.getMimeType())) {
            return HandWriteDataUtils.IINK_SUFFIX;
        }
        return dataEntity.getFileExtension();
    }

    public void upload(DataEntity dataEntity) throws NoteSyncException {
        try {
            byte[] fileData = dataEntity.getFileData(this.mContext);
            if (fileData != null && fileData.length != 0) {
                UploadResult uploadResultUpload = this.mMediaManager.upload(new UploadData(fileData, parseType(dataEntity), parseExtension(dataEntity)));
                if (uploadResultUpload == null || TextUtils.isEmpty(uploadResultUpload.fileId)) {
                    throw new NoteSyncException("uploadFile fail. result=" + uploadResultUpload);
                }
                dataEntity.updateFileId(this.mContext, uploadResultUpload.fileId);
                return;
            }
            Logger.INSTANCE.i(TAG, "Bytes of DataEntity is null,upload failed:content://notes/data/" + dataEntity.getId());
        } catch (NetworkErrorException e) {
            throw new NoteSyncException(e);
        } catch (CloudParameterError e2) {
            throw new NoteSyncException(e2);
        } catch (CloudRichMediaServerException e3) {
            throw new NoteSyncException(e3);
        } catch (FileTooLargeException e4) {
            Logger.INSTANCE.w(TAG, "The file exceeds the limit" + dataEntity.getId() + StringUtils.SPACE + e4);
        } catch (FileNotFoundException e5) {
            Logger.INSTANCE.w(TAG, "uploadFile: can't find file for data, id=" + dataEntity.getId() + StringUtils.SPACE + e5);
        } catch (IOException e6) {
            throw new NoteSyncException(e6);
        }
    }

    /* JADX WARN: Code duplicated, block: B:112:0x01be  */
    /* JADX WARN: Code duplicated, block: B:117:0x01fc  */
    /* JADX WARN: Code duplicated, block: B:120:0x020c  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v9, types: [java.io.InputStream] */
    public void download(DataEntity dataEntity) throws Throwable {
        String str;
        String str2;
        String type;
        String str3;
        FileInputStream fileInputStream;
        String str4;
        FileInputStream fileInputStream2;
        ?? Append = new StringBuilder().append(this.mContext.getCacheDir()).append(File.separator);
        ?? fileId = dataEntity.getFileId();
        File file = new File(Append.append(fileId).toString());
        MicloudDownload.Result resultDownload2 = MicloudDownload.Result.Error;
        try {
            try {
                Logger logger = Logger.INSTANCE;
                str = HandWriteDataUtils.DOWNLOAD_IMG_HANDWRITE_ACTION;
                try {
                    logger.d(TAG, "start download " + dataEntity.getContent());
                    resultDownload2 = this.mMediaManager.download2(dataEntity.getFileId(), parseType(dataEntity), file);
                    if (resultDownload2 == MicloudDownload.Result.OK) {
                        FileInputStream fileInputStream3 = new FileInputStream(file);
                        try {
                            byte[] byteArray = IOUtils.toByteArray(fileInputStream3);
                            fileInputStream2 = fileInputStream3;
                            if ("multi_handwrite".equals(dataEntity.getDataContentType())) {
                                try {
                                    String attachmentPath = AttachmentUtils.getAttachmentPath(NoteApp.getInstance(), dataEntity.getContent());
                                    str2 = "com.miui.notes.handwrite.download";
                                    try {
                                        File file2 = new File(attachmentPath.substring(0, attachmentPath.lastIndexOf(File.separator)));
                                        if (!file2.exists()) {
                                            file2.mkdirs();
                                            file2.setExecutable(true, false);
                                        }
                                    } catch (NetworkErrorException e) {
                                        e = e;
                                        throw new NoteSyncException(e);
                                    } catch (CloudParameterError e2) {
                                        e = e2;
                                        throw new NoteSyncException(e);
                                    } catch (CloudRichMediaServerException e3) {
                                        e = e3;
                                        throw new NoteSyncException(e);
                                    } catch (IOException e4) {
                                        e = e4;
                                        throw new NoteSyncException(e);
                                    } catch (Throwable th) {
                                        th = th;
                                        fileId = fileInputStream2;
                                        if (resultDownload2 == MicloudDownload.Result.OK) {
                                            file.delete();
                                        }
                                        IOUtils.closeQuietly((InputStream) fileId);
                                        Intent intent = new Intent();
                                        intent.setPackage(NoteApp.getInstance().getPackageName());
                                        intent.putExtra("entName", dataEntity.getContent());
                                        type = parseType(dataEntity);
                                        Logger.INSTANCE.i(TAG, String.format("download end %s -> %s", dataEntity.getContent(), type));
                                        if (type.equals("note_ent")) {
                                            str3 = str2;
                                        } else {
                                            str3 = str2;
                                        }
                                        Logger.INSTANCE.i(TAG, "Sending broadcast with action: ".concat(str3));
                                        intent.setAction(str3);
                                        NoteApp.getInstance().sendBroadcast(intent);
                                        throw th;
                                    }
                                } catch (NetworkErrorException e5) {
                                    e = e5;
                                    throw new NoteSyncException(e);
                                } catch (CloudParameterError e6) {
                                    e = e6;
                                    throw new NoteSyncException(e);
                                } catch (CloudRichMediaServerException e7) {
                                    e = e7;
                                    throw new NoteSyncException(e);
                                } catch (IOException e8) {
                                    e = e8;
                                    throw new NoteSyncException(e);
                                } catch (Throwable th2) {
                                    th = th2;
                                    str2 = "com.miui.notes.handwrite.download";
                                    fileId = fileInputStream2;
                                    if (resultDownload2 == MicloudDownload.Result.OK && file.exists()) {
                                        file.delete();
                                    }
                                    IOUtils.closeQuietly((InputStream) fileId);
                                    Intent intent2 = new Intent();
                                    intent2.setPackage(NoteApp.getInstance().getPackageName());
                                    intent2.putExtra("entName", dataEntity.getContent());
                                    type = parseType(dataEntity);
                                    Logger.INSTANCE.i(TAG, String.format("download end %s -> %s", dataEntity.getContent(), type));
                                    if (type.equals("note_ent") || !type.equals("note_img")) {
                                        str3 = str2;
                                    } else {
                                        intent2.putExtra(AITextWidgetStat.TYPE_IMAGE, dataEntity.getContent());
                                        str3 = str;
                                    }
                                    Logger.INSTANCE.i(TAG, "Sending broadcast with action: ".concat(str3));
                                    intent2.setAction(str3);
                                    NoteApp.getInstance().sendBroadcast(intent2);
                                    throw th;
                                }
                            } else {
                                str2 = "com.miui.notes.handwrite.download";
                            }
                            dataEntity.updateFile(this.mContext, byteArray);
                            if (TextUtils.equals(dataEntity.getDataContentType(), "multi_handwrite")) {
                                dataEntity.updateNeedUpdateField(NoteApp.getInstance(), false);
                            }
                            fileInputStream = fileInputStream2;
                        } catch (NetworkErrorException e9) {
                            e = e9;
                            fileInputStream2 = fileInputStream3;
                        } catch (CloudParameterError e10) {
                            e = e10;
                            fileInputStream2 = fileInputStream3;
                        } catch (CloudRichMediaServerException e11) {
                            e = e11;
                            fileInputStream2 = fileInputStream3;
                        } catch (IOException e12) {
                            e = e12;
                            fileInputStream2 = fileInputStream3;
                        } catch (Throwable th3) {
                            th = th3;
                            fileInputStream2 = fileInputStream3;
                        }
                    } else {
                        str2 = "com.miui.notes.handwrite.download";
                        fileInputStream = null;
                    }
                    if (resultDownload2 == MicloudDownload.Result.OK && file.exists()) {
                        file.delete();
                    }
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    Intent intent3 = new Intent();
                    intent3.setPackage(NoteApp.getInstance().getPackageName());
                    intent3.putExtra("entName", dataEntity.getContent());
                    String type2 = parseType(dataEntity);
                    Logger.INSTANCE.i(TAG, String.format("download end %s -> %s", dataEntity.getContent(), type2));
                    if (!type2.equals("note_ent") && type2.equals("note_img")) {
                        intent3.putExtra(AITextWidgetStat.TYPE_IMAGE, dataEntity.getContent());
                        str4 = str;
                    } else {
                        str4 = str2;
                    }
                    Logger.INSTANCE.i(TAG, "Sending broadcast with action: ".concat(str4));
                    intent3.setAction(str4);
                    NoteApp.getInstance().sendBroadcast(intent3);
                } catch (NetworkErrorException e13) {
                    e = e13;
                    throw new NoteSyncException(e);
                } catch (CloudParameterError e14) {
                    e = e14;
                    throw new NoteSyncException(e);
                } catch (CloudRichMediaServerException e15) {
                    e = e15;
                    throw new NoteSyncException(e);
                } catch (IOException e16) {
                    e = e16;
                    throw new NoteSyncException(e);
                } catch (Throwable th4) {
                    th = th4;
                    str2 = "com.miui.notes.handwrite.download";
                    fileId = 0;
                    if (resultDownload2 == MicloudDownload.Result.OK) {
                        file.delete();
                    }
                    IOUtils.closeQuietly((InputStream) fileId);
                    Intent intent4 = new Intent();
                    intent4.setPackage(NoteApp.getInstance().getPackageName());
                    intent4.putExtra("entName", dataEntity.getContent());
                    type = parseType(dataEntity);
                    Logger.INSTANCE.i(TAG, String.format("download end %s -> %s", dataEntity.getContent(), type));
                    if (type.equals("note_ent")) {
                        str3 = str2;
                    } else {
                        str3 = str2;
                    }
                    Logger.INSTANCE.i(TAG, "Sending broadcast with action: ".concat(str3));
                    intent4.setAction(str3);
                    NoteApp.getInstance().sendBroadcast(intent4);
                    throw th;
                }
            } catch (NetworkErrorException e17) {
                e = e17;
            } catch (CloudParameterError e18) {
                e = e18;
            } catch (CloudRichMediaServerException e19) {
                e = e19;
            } catch (IOException e20) {
                e = e20;
            } catch (Throwable th5) {
                th = th5;
                str = HandWriteDataUtils.DOWNLOAD_IMG_HANDWRITE_ACTION;
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }
}
