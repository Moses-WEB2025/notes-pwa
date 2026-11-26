package com.miui.notes.backup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import ch.qos.logback.core.CoreConstants;
import com.google.protobuf.ByteString;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import com.xiaomi.mimoverbackup.backup.IMiMoverBackup;
import com.xiaomi.mimoverbackup.utils.MiMoverBackupConstants;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MiMoverBackupService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0018\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u001a\u0010\u001b\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001f"}, d2 = {"Lcom/miui/notes/backup/MiMoverBackupImpl;", "Lcom/xiaomi/mimoverbackup/backup/IMiMoverBackup;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "BUFFER_SIZE", "", "getBUFFER_SIZE", "()I", "isCancel", "Ljava/util/concurrent/atomic/AtomicBoolean;", "mNoteManager", "Lcom/miui/notes/backup/NoteManager;", "onBackup", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "parcelFileDescriptor", "Landroid/os/ParcelFileDescriptor;", "backupSize", "", "getBackupSize", "()J", "protobufBackup", "onRestore", "protobufRestore", "getBackupInfo", "bundle", "Landroid/os/Bundle;", "onCancel", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiMoverBackupImpl implements IMiMoverBackup {
    private NoteManager mNoteManager;
    private final String TAG = "MiMoverBackupImpl";
    private final int BUFFER_SIZE = 32768;
    private final AtomicBoolean isCancel = new AtomicBoolean(false);

    public final int getBUFFER_SIZE() {
        return this.BUFFER_SIZE;
    }

    @Override // com.xiaomi.mimoverbackup.backup.IMiMoverBackup
    public void onBackup(Context context, ParcelFileDescriptor parcelFileDescriptor) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parcelFileDescriptor, "parcelFileDescriptor");
        Logger.INSTANCE.d(this.TAG, "onBackup start");
        protobufBackup(parcelFileDescriptor);
    }

    private final long getBackupSize() throws Throwable {
        NoteManager noteManager = new NoteManager(BaseNoteApp.INSTANCE.getInstance());
        this.mNoteManager = noteManager;
        Intrinsics.checkNotNull(noteManager);
        List<String> listPrepareIdsByType = noteManager.prepareIdsByType(0, true);
        Logger.INSTANCE.d(this.TAG, "buildSecretSyncRootProtos: noteIds:" + listPrepareIdsByType.size());
        long length = 0;
        long serializedSize = 0;
        for (String str : listPrepareIdsByType) {
            try {
                NoteManager noteManager2 = this.mNoteManager;
                Intrinsics.checkNotNull(noteManager2);
                Intrinsics.checkNotNull(str);
                NoteProtos.NoteEntity noteEntityLoadNoteEntity = noteManager2.loadNoteEntity(Long.parseLong(str), true);
                if (noteEntityLoadNoteEntity != null) {
                    serializedSize += (long) noteEntityLoadNoteEntity.getSerializedSize();
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "Cannot load note entity exception:" + e);
            }
        }
        Logger.INSTANCE.d(this.TAG, "buildSecretSyncRootProtos noteSize: " + serializedSize);
        NoteManager noteManager3 = this.mNoteManager;
        Intrinsics.checkNotNull(noteManager3);
        Iterator<Map.Entry<String, Uri>> attachesIterator = noteManager3.getAttachesIterator();
        while (attachesIterator.hasNext()) {
            Map.Entry<String, Uri> next = attachesIterator.next();
            Intrinsics.checkNotNull(next);
            length += new File((String) Objects.requireNonNull(AttachmentUtils.getAttachmentPath(BaseNoteApp.INSTANCE.getInstance(), next.getKey()))).length();
        }
        Logger.INSTANCE.d(this.TAG, "buildSecretSyncRootProtos fileSize: " + length);
        long j = serializedSize + length;
        Logger.INSTANCE.d(this.TAG, "buildSecretSyncRootProtos secretSize: " + j);
        return j;
    }

    private final void protobufBackup(ParcelFileDescriptor parcelFileDescriptor) throws Throwable {
        Logger logger;
        String str;
        StringBuilder sb;
        Throwable th;
        Logger.INSTANCE.d(this.TAG, "protobufBackup");
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        SecretSyncRootProtos.Task.Builder builderNewBuilder = SecretSyncRootProtos.Task.newBuilder();
        NoteManager noteManager = new NoteManager(BaseNoteApp.INSTANCE.getInstance());
        this.mNoteManager = noteManager;
        Intrinsics.checkNotNull(noteManager);
        List<String> listPrepareIdsByType = noteManager.prepareIdsByType(1, true);
        NoteManager noteManager2 = this.mNoteManager;
        Intrinsics.checkNotNull(noteManager2);
        List<String> listPrepareIdsByType2 = noteManager2.prepareIdsByType(0, true);
        NoteProtos.Notes.Builder builderNewBuilder2 = NoteProtos.Notes.newBuilder();
        Logger.INSTANCE.d(this.TAG, "protobufBackup: folderIds:" + listPrepareIdsByType.size() + "， noteIds:" + listPrepareIdsByType2.size());
        for (String str2 : listPrepareIdsByType) {
            try {
                NoteManager noteManager3 = this.mNoteManager;
                Intrinsics.checkNotNull(noteManager3);
                Intrinsics.checkNotNull(str2);
                NoteProtos.NoteFolder noteFolderLoadNoteFolder = noteManager3.loadNoteFolder(Long.parseLong(str2), true);
                if (noteFolderLoadNoteFolder != null) {
                    builderNewBuilder2.addFolder(noteFolderLoadNoteFolder);
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "Cannot load note folder exception:" + e);
            }
        }
        for (String str3 : listPrepareIdsByType2) {
            try {
                NoteManager noteManager4 = this.mNoteManager;
                Intrinsics.checkNotNull(noteManager4);
                Intrinsics.checkNotNull(str3);
                NoteProtos.NoteEntity noteEntityLoadNoteEntity = noteManager4.loadNoteEntity(Long.parseLong(str3), true);
                if (noteEntityLoadNoteEntity != null) {
                    builderNewBuilder2.addNote(noteEntityLoadNoteEntity);
                }
            } catch (Exception e2) {
                Logger.INSTANCE.e(this.TAG, "Cannot load note entity exception:" + e2);
            }
        }
        Logger.INSTANCE.d(this.TAG, "protobufBackup note: " + builderNewBuilder2.getNoteCount());
        ArrayList arrayList = new ArrayList();
        SecretSyncRootProtos.SecretNote secretNoteBuild = SecretSyncRootProtos.SecretNote.newBuilder().setNote(builderNewBuilder2.build()).build();
        Intrinsics.checkNotNullExpressionValue(secretNoteBuild, "build(...)");
        arrayList.add(secretNoteBuild);
        Logger.INSTANCE.d(this.TAG, "protobufBackup list note: " + ((SecretSyncRootProtos.SecretNote) arrayList.get(0)).getNote().getNoteCount());
        builderNewBuilder.addAllSecretNote(arrayList);
        ArrayList arrayList2 = new ArrayList();
        NoteManager noteManager5 = this.mNoteManager;
        Intrinsics.checkNotNull(noteManager5);
        Iterator<Map.Entry<String, Uri>> attachesIterator = noteManager5.getAttachesIterator();
        while (attachesIterator.hasNext()) {
            Map.Entry<String, Uri> next = attachesIterator.next();
            Intrinsics.checkNotNull(next);
            String key = next.getKey();
            Logger.INSTANCE.d(this.TAG, "protobufBackup: fileName " + key);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedInputStream bufferedInputStream = null;
            try {
                try {
                    BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(AttachmentUtils.getAttachmentPath(BaseNoteApp.INSTANCE.getInstance(), key)));
                    try {
                        try {
                            byte[] bArr = new byte[this.BUFFER_SIZE];
                            while (true) {
                                int i = bufferedInputStream2.read(bArr);
                                if (i == -1) {
                                    break;
                                }
                                try {
                                    byteArrayOutputStream.write(bArr, 0, i);
                                } catch (IOException e3) {
                                    e = e3;
                                    bufferedInputStream = bufferedInputStream2;
                                    Logger.INSTANCE.e(this.TAG, "write error,exception:" + e);
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (IOException e4) {
                                            Logger.INSTANCE.e(this.TAG, " bis close IOException: " + e4);
                                        }
                                    }
                                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                                    Logger.INSTANCE.d(this.TAG, "protobufBackup: dataSize " + byteArray.length);
                                    SecretSyncRootProtos.SecretFileInfo secretFileInfoBuild = SecretSyncRootProtos.SecretFileInfo.newBuilder().setFilename(key).setFiledata(ByteString.copyFrom(byteArray)).build();
                                    Intrinsics.checkNotNullExpressionValue(secretFileInfoBuild, "build(...)");
                                    arrayList2.add(secretFileInfoBuild);
                                }
                            }
                            try {
                                bufferedInputStream2.close();
                            } catch (IOException e5) {
                                Logger.INSTANCE.e(this.TAG, " bis close IOException: " + e5);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedInputStream = bufferedInputStream2;
                            if (bufferedInputStream == null) {
                                throw th;
                            }
                            try {
                                bufferedInputStream.close();
                                throw th;
                            } catch (IOException e6) {
                                Logger.INSTANCE.e(this.TAG, " bis close IOException: " + e6);
                                throw th;
                            }
                        }
                    } catch (IOException e7) {
                        e = e7;
                    }
                } catch (IOException e8) {
                    e = e8;
                }
                byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                Logger.INSTANCE.d(this.TAG, "protobufBackup: dataSize " + byteArray2.length);
                SecretSyncRootProtos.SecretFileInfo secretFileInfoBuild2 = SecretSyncRootProtos.SecretFileInfo.newBuilder().setFilename(key).setFiledata(ByteString.copyFrom(byteArray2)).build();
                Intrinsics.checkNotNullExpressionValue(secretFileInfoBuild2, "build(...)");
                arrayList2.add(secretFileInfoBuild2);
            } catch (Throwable th3) {
                th = th3;
            }
        }
        builderNewBuilder.addAllSecretFileInfo(arrayList2);
        try {
            try {
                builderNewBuilder.build().writeTo(autoCloseOutputStream);
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e9) {
                        e = e9;
                        logger = Logger.INSTANCE;
                        str = this.TAG;
                        sb = new StringBuilder("parcelFileDescriptor close IOException: ");
                        logger.e(str, sb.append(e).toString());
                    }
                }
            } catch (Throwable th4) {
                if (parcelFileDescriptor == null) {
                    throw th4;
                }
                try {
                    parcelFileDescriptor.close();
                    throw th4;
                } catch (IOException e10) {
                    Logger.INSTANCE.e(this.TAG, "parcelFileDescriptor close IOException: " + e10);
                    throw th4;
                }
            }
        } catch (IOException e11) {
            Logger.INSTANCE.e(this.TAG, "exception:" + e11);
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e12) {
                    e = e12;
                    logger = Logger.INSTANCE;
                    str = this.TAG;
                    sb = new StringBuilder("parcelFileDescriptor close IOException: ");
                    logger.e(str, sb.append(e).toString());
                }
            }
        }
        Logger.INSTANCE.d(this.TAG, "protobufBackup end");
    }

    @Override // com.xiaomi.mimoverbackup.backup.IMiMoverBackup
    public void onRestore(Context context, ParcelFileDescriptor parcelFileDescriptor) throws Throwable {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parcelFileDescriptor, "parcelFileDescriptor");
        Logger.INSTANCE.d(this.TAG, "onRestore");
        protobufRestore(context, parcelFileDescriptor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final void protobufRestore(Context context, ParcelFileDescriptor parcelFileDescriptor) throws Throwable {
        Logger logger;
        String str;
        StringBuilder sb;
        String str2;
        Logger.INSTANCE.d(this.TAG, "protobufRestore");
        ?? r2 = 0;
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    SecretSyncRootProtos.Task from = SecretSyncRootProtos.Task.parseFrom(fileInputStream2);
                    List<SecretSyncRootProtos.SecretNote> secretNoteList = from.getSecretNoteList();
                    Logger.INSTANCE.d(this.TAG, "protobufRestore secretNoteList ：" + secretNoteList.size());
                    this.mNoteManager = new NoteManager(BaseNoteApp.INSTANCE.getInstance());
                    for (SecretSyncRootProtos.SecretNote secretNote : secretNoteList) {
                        Logger.INSTANCE.d(this.TAG, "protobufRestore secretNote.getNote() ：" + secretNote.getNote().getNoteCount());
                        for (NoteProtos.NoteFolder noteFolder : secretNote.getNote().getFolderList()) {
                            try {
                                String subject = noteFolder.getSubject();
                                if (subject != null) {
                                    NoteManager noteManager = this.mNoteManager;
                                    Intrinsics.checkNotNull(noteManager);
                                    if (noteManager.loadNoteFolderByTitle(subject) == null) {
                                        NoteManager noteManager2 = this.mNoteManager;
                                        Intrinsics.checkNotNull(noteManager2);
                                        Intrinsics.checkNotNull(noteFolder);
                                        noteManager2.addNoteFolder(noteFolder);
                                    }
                                }
                            } catch (Exception e) {
                                Logger.INSTANCE.e(this.TAG, "Cannot add note folder exception:" + e);
                            }
                        }
                        for (NoteProtos.NoteEntity noteEntity : secretNote.getNote().getNoteList()) {
                            try {
                                NoteManager noteManager3 = this.mNoteManager;
                                Intrinsics.checkNotNull(noteManager3);
                                Intrinsics.checkNotNull(noteEntity);
                                if (noteManager3.shouldRestore(noteEntity, true)) {
                                    NoteManager noteManager4 = this.mNoteManager;
                                    Intrinsics.checkNotNull(noteManager4);
                                    noteManager4.insertNoteEntity(noteEntity, true);
                                }
                            } catch (Exception e2) {
                                Logger.INSTANCE.e(this.TAG, "Cannot add note entity exception:" + e2);
                            }
                        }
                    }
                    List<SecretSyncRootProtos.SecretFileInfo> secretFileInfoList = from.getSecretFileInfoList();
                    Logger.INSTANCE.d(this.TAG, "protobufRestore secretFileInfoList ：" + secretFileInfoList.size());
                    for (SecretSyncRootProtos.SecretFileInfo secretFileInfo : secretFileInfoList) {
                        NoteManager noteManager5 = this.mNoteManager;
                        Intrinsics.checkNotNull(noteManager5);
                        Uri attachesUriByName = noteManager5.getAttachesUriByName(secretFileInfo.getFilename());
                        Logger.INSTANCE.d(this.TAG, "protobufRestore fileName ：" + secretFileInfo.getFilename() + ",attachUri:" + attachesUriByName);
                        if (attachesUriByName != null) {
                            secretFileInfo.getFiledata().writeTo(context.getContentResolver().openOutputStream(attachesUriByName, AnimatedProperty.PROPERTY_NAME_W));
                        }
                    }
                    String str3 = "protobufRestore end";
                    Logger.INSTANCE.d(this.TAG, "protobufRestore end");
                    try {
                        fileInputStream2.close();
                        str2 = str3;
                    } catch (Exception e3) {
                        Logger logger2 = Logger.INSTANCE;
                        String str4 = this.TAG;
                        logger2.e(str4, "exception:" + e3);
                        str2 = str4;
                    }
                    try {
                        parcelFileDescriptor.close();
                        r2 = str2;
                    } catch (Exception e4) {
                        e = e4;
                        logger = Logger.INSTANCE;
                        str = this.TAG;
                        sb = new StringBuilder("exception:");
                        logger.e(str, sb.append(e).toString());
                    }
                } catch (IOException e5) {
                    e = e5;
                    fileInputStream = fileInputStream2;
                    Logger.INSTANCE.e(this.TAG, new StringBuilder().append(e).toString());
                    Object obj = fileInputStream;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                            obj = fileInputStream;
                        } catch (Exception e6) {
                            Logger logger3 = Logger.INSTANCE;
                            String str5 = this.TAG;
                            logger3.e(str5, "exception:" + e6);
                            obj = str5;
                        }
                    }
                    try {
                        parcelFileDescriptor.close();
                        r2 = obj;
                    } catch (Exception e7) {
                        e = e7;
                        logger = Logger.INSTANCE;
                        str = this.TAG;
                        sb = new StringBuilder("exception:");
                        logger.e(str, sb.append(e).toString());
                    }
                } catch (Throwable th) {
                    th = th;
                    r2 = fileInputStream2;
                    if (r2 != 0) {
                        try {
                            r2.close();
                        } catch (Exception e8) {
                            Logger.INSTANCE.e(this.TAG, "exception:" + e8);
                        }
                    }
                    try {
                        parcelFileDescriptor.close();
                        throw th;
                    } catch (Exception e9) {
                        Logger.INSTANCE.e(this.TAG, "exception:" + e9);
                        throw th;
                    }
                }
            } catch (IOException e10) {
                e = e10;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // com.xiaomi.mimoverbackup.backup.IMiMoverBackup
    public void getBackupInfo(Context context, Bundle bundle) throws Throwable {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        Logger.INSTANCE.d(this.TAG, "getBackupInfo");
        long backupSize = getBackupSize();
        bundle.putInt(MiMoverBackupConstants.RTN_DATA_COUNT, 0 == backupSize ? 0 : 1);
        bundle.putLong(MiMoverBackupConstants.RTN_DATA_SIZE, backupSize);
    }

    @Override // com.xiaomi.mimoverbackup.backup.IMiMoverBackup
    public void onCancel() {
        Logger.INSTANCE.d(this.TAG, "onCancel");
        this.isCancel.set(true);
    }
}
