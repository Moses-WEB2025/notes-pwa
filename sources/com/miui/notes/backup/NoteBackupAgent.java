package com.miui.notes.backup;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.text.TextUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoDatabaseAccessUtils;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.GuideUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miui.app.backup.BackupManager;
import miui.app.backup.BackupMeta;
import miui.app.backup.FullBackupAgent;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: NoteBackupAgent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0014J\u001a\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0012H\u0014J$\u0010\u001a\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001b\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u001d\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\n\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002J\u0012\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u001fH\u0002J\n\u0010#\u001a\u0004\u0018\u00010$H\u0002J\u0012\u0010%\u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010$H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f¨\u0006&"}, d2 = {"Lcom/miui/notes/backup/NoteBackupAgent;", "Lmiui/app/backup/FullBackupAgent;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tag", "", "mNoteManager", "Lcom/miui/notes/backup/NoteManager;", "getMNoteManager", "()Lcom/miui/notes/backup/NoteManager;", "mNoteManager$delegate", "Lkotlin/Lazy;", "mTodoManager", "Lcom/miui/notes/backup/TodoManager;", "getMTodoManager", "()Lcom/miui/notes/backup/TodoManager;", "mTodoManager$delegate", "getVersion", "", "feature", "onDataRestore", "meta", "Lmiui/app/backup/BackupMeta;", "data", "Landroid/os/ParcelFileDescriptor;", "onFullBackup", "onAttachRestore", "fd", "attachName", "onRestoreEnd", "backupNotesConfig", "Lcom/miui/notes/backup/AppConfigProtos$AppConfig;", "restoreNotesConfig", "", "appConfig", "backupTodoAppConfig", "Lcom/miui/notes/backup/TodoAppConfigProtos$AppConfig;", "restoreTodoAppConfig", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteBackupAgent extends FullBackupAgent {
    private final String tag = "NoteBackupAgent";

    /* JADX INFO: renamed from: mNoteManager$delegate, reason: from kotlin metadata */
    private final Lazy mNoteManager = LazyKt.lazy(new Function0() { // from class: com.miui.notes.backup.NoteBackupAgent$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NoteBackupAgent.mNoteManager_delegate$lambda$0(this.f$0);
        }
    });

    /* JADX INFO: renamed from: mTodoManager$delegate, reason: from kotlin metadata */
    private final Lazy mTodoManager = LazyKt.lazy(new Function0() { // from class: com.miui.notes.backup.NoteBackupAgent$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NoteBackupAgent.mTodoManager_delegate$lambda$1(this.f$0);
        }
    });

    protected int getVersion(int feature) {
        return 1;
    }

    private final NoteManager getMNoteManager() {
        return (NoteManager) this.mNoteManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NoteManager mNoteManager_delegate$lambda$0(NoteBackupAgent noteBackupAgent) {
        Context applicationContext = noteBackupAgent.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return new NoteManager(applicationContext);
    }

    private final TodoManager getMTodoManager() {
        return (TodoManager) this.mTodoManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TodoManager mTodoManager_delegate$lambda$1(NoteBackupAgent noteBackupAgent) {
        Context applicationContext = noteBackupAgent.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return new TodoManager(applicationContext);
    }

    /* JADX WARN: Code duplicated, block: B:74:0x021c  */
    /* JADX WARN: Multi-variable type inference failed */
    protected int onDataRestore(BackupMeta meta, ParcelFileDescriptor data) throws Throwable {
        FileInputStream fileInputStream;
        Exception e;
        TodoAppConfigProtos.AppConfig todoconfig;
        NoteProtos.Notes note;
        AppConfigProtos.AppConfig config;
        TodoProtos.Todos todo;
        Intrinsics.checkNotNullParameter(data, "data");
        Logger.INSTANCE.d(this.tag, "onDataRestore");
        Context context = (Context) this;
        BackupManager.getBackupManager(context).setIsNeedBeKilled(false);
        PreferenceUtils.setAddIntroduction(context, true);
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(data.getFileDescriptor());
            try {
                try {
                    SyncRootProtos.SyncRoot from = SyncRootProtos.SyncRoot.parseFrom(fileInputStream);
                    fileInputStream.close();
                    if (from != null) {
                        note = from.getNote();
                        config = from.getConfig();
                        todo = from.getTodo();
                        todoconfig = from.getTodoconfig();
                    } else {
                        todoconfig = null;
                        note = null;
                        config = null;
                        todo = null;
                    }
                    if (note == null) {
                        fileInputStream.close();
                        return 6;
                    }
                    restoreNotesConfig(config);
                    restoreTodoAppConfig(todoconfig);
                    NoteProtos.Notes notesClone = ExtensionKt.clone(note);
                    for (NoteProtos.NoteFolder noteFolder : notesClone.getFolderList()) {
                        try {
                            String subject = noteFolder.getSubject();
                            if (subject != null && getMNoteManager().loadNoteFolderByTitle(subject) == null) {
                                NoteManager mNoteManager = getMNoteManager();
                                Intrinsics.checkNotNull(noteFolder);
                                mNoteManager.addNoteFolder(noteFolder);
                            }
                        } catch (Exception e2) {
                            Logger.INSTANCE.e(this.tag, "Cannot add note folder ,exception:" + e2);
                        }
                    }
                    for (NoteProtos.NoteEntity noteEntity : notesClone.getNoteList()) {
                        try {
                            NoteManager mNoteManager2 = getMNoteManager();
                            Intrinsics.checkNotNull(noteEntity);
                            if (NoteManager.shouldRestore$default(mNoteManager2, noteEntity, false, 2, null)) {
                                NoteManager.insertNoteEntity$default(getMNoteManager(), noteEntity, false, 2, null);
                            }
                        } catch (Exception e3) {
                            Logger.INSTANCE.e(this.tag, "Cannot add note entity ,exception:" + e3);
                        }
                    }
                    if (todo == null) {
                        fileInputStream.close();
                        return 6;
                    }
                    TodoProtos.Todos todosClone = ExtensionKt.clone(todo, todo);
                    Cursor cursorQueryAllUnFinishItem = TodoProviderAccessUtils.queryAllUnFinishItem();
                    int count = cursorQueryAllUnFinishItem.getCount();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    if (count != 0) {
                        while (cursorQueryAllUnFinishItem.moveToNext()) {
                            long j = cursorQueryAllUnFinishItem.getLong(0);
                            TodoEntity todoEntity = TodoDataUtils.getTodoEntity(cursorQueryAllUnFinishItem);
                            Intrinsics.checkNotNullExpressionValue(todoEntity, "getTodoEntity(...)");
                            if (GuideUtils.isGuideTodo(todoEntity)) {
                                arrayList.add(Long.valueOf(j));
                            } else {
                                arrayList2.add(Long.valueOf(j));
                            }
                        }
                    }
                    cursorQueryAllUnFinishItem.close();
                    for (TodoProtos.TodoEntity todoEntity2 : todosClone.getTodoList()) {
                        try {
                            TodoManager mTodoManager = getMTodoManager();
                            Intrinsics.checkNotNull(todoEntity2);
                            long jRestoreInfo = mTodoManager.restoreInfo(todoEntity2);
                            if (jRestoreInfo >= 0) {
                                if (jRestoreInfo > 0) {
                                    arrayList2.remove(Long.valueOf(jRestoreInfo));
                                }
                                arrayList.add(Long.valueOf(getMTodoManager().insertTodoEntity(todoEntity2)));
                            }
                        } catch (Exception e4) {
                            Logger.INSTANCE.e(this.tag, "Cannot add todo entity ,exception:" + e4);
                        }
                    }
                    Logger.INSTANCE.i(this.tag, "onDataRestore,notes count:" + notesClone.getNoteCount() + ",todos:" + todosClone.getTodoCount() + ",folders :" + notesClone.getFolderCount() + StringUtils.SPACE);
                    arrayList.addAll(arrayList2);
                    TodoDatabaseAccessUtils.updateUnFinishedArray(arrayList);
                    PreferenceUtils.setAddTodoIntroduction((Context) this, true);
                    Notes.Utils.updateAllAlarms(getApplicationContext());
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                Logger.INSTANCE.e(this.tag, "Exception in onDataRestore ,exception:" + ExceptionsKt.stackTraceToString(e));
                if (fileInputStream != null) {
                }
                return 0;
            }
        } catch (Exception e6) {
            fileInputStream = null;
            e = e6;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
        fileInputStream.close();
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:53:0x0243 A[LOOP:4: B:51:0x023d->B:53:0x0243, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:58:0x0261  */
    /* JADX WARN: Code duplicated, block: B:88:0x0231 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x022d A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    protected int onFullBackup(ParcelFileDescriptor data, int feature) throws Throwable {
        FileOutputStream fileOutputStream;
        Iterator<Map.Entry<String, Uri>> attachesIterator;
        Iterator<Map.Entry<String, Uri>> attachesIterator2;
        String key;
        Uri value;
        String separator;
        Intrinsics.checkNotNullParameter(data, "data");
        Logger.INSTANCE.d(this.tag, "onFullBackup--");
        BackupManager.getBackupManager((Context) this).setIsNeedBeKilled(false);
        NoteProtos.Notes.Builder builderNewBuilder = NoteProtos.Notes.newBuilder();
        builderNewBuilder.setVersion(13);
        FileOutputStream fileOutputStream2 = null;
        List<String> listPrepareIdsByType$default = NoteManager.prepareIdsByType$default(getMNoteManager(), 1, false, 2, null);
        List<String> listPrepareIdsByType$default2 = NoteManager.prepareIdsByType$default(getMNoteManager(), 0, false, 2, null);
        Logger.INSTANCE.d(this.tag, "backup notes count:" + listPrepareIdsByType$default2.size() + ",folder size:" + listPrepareIdsByType$default.size());
        for (String str : listPrepareIdsByType$default) {
            try {
                NoteManager mNoteManager = getMNoteManager();
                Intrinsics.checkNotNull(str);
                builderNewBuilder.addFolder(mNoteManager.loadNoteFolder(Long.parseLong(str)));
            } catch (Exception e) {
                Logger.INSTANCE.e(this.tag, "Cannot load note folder ,exception:" + e);
            }
        }
        for (String str2 : listPrepareIdsByType$default2) {
            try {
                Logger.INSTANCE.d(this.tag, "load notes " + str2);
                NoteManager mNoteManager2 = getMNoteManager();
                Intrinsics.checkNotNull(str2);
                NoteProtos.NoteEntity noteEntityLoadNoteEntity = mNoteManager2.loadNoteEntity(Long.parseLong(str2));
                if (noteEntityLoadNoteEntity != null) {
                    builderNewBuilder.addNote(noteEntityLoadNoteEntity);
                }
            } catch (Exception e2) {
                Logger.INSTANCE.e(this.tag, "Cannot load note entity ,exception:" + e2);
            }
        }
        TodoProtos.Todos.Builder builderNewBuilder2 = TodoProtos.Todos.newBuilder();
        builderNewBuilder2.setVersion(1);
        List<String> listPrepareIds = getMTodoManager().prepareIds();
        Logger.INSTANCE.d(this.tag, "backup todos count:" + listPrepareIds.size());
        for (String str3 : listPrepareIds) {
            Logger.INSTANCE.d(this.tag, "load todos " + str3);
            try {
                TodoManager mTodoManager = getMTodoManager();
                Intrinsics.checkNotNull(str3);
                TodoProtos.TodoEntity todoEntityLoadTodoEntity = mTodoManager.loadTodoEntity(Long.parseLong(str3));
                if (todoEntityLoadTodoEntity != null) {
                    builderNewBuilder2.addTodo(todoEntityLoadTodoEntity);
                }
            } catch (Exception e3) {
                Logger.INSTANCE.e(this.tag, "Cannot load todo entity ,exception:" + ExceptionsKt.stackTraceToString(e3));
            }
        }
        SyncRootProtos.SyncRoot.Builder todoconfig = SyncRootProtos.SyncRoot.newBuilder().setNote(builderNewBuilder.build()).setConfig(backupNotesConfig()).setTodo(builderNewBuilder2.build()).setTodoconfig(backupTodoAppConfig());
        try {
            fileOutputStream = new FileOutputStream(data.getFileDescriptor());
            try {
                try {
                    todoconfig.build().writeTo(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                Logger.INSTANCE.e(this.tag, "Cannot export note ,exception:" + ExceptionsKt.stackTraceToString(e));
                if (fileOutputStream != null) {
                }
                attachesIterator = getMNoteManager().getAttachesIterator();
                while (attachesIterator.hasNext()) {
                    Map.Entry<String, Uri> next = attachesIterator.next();
                    Intrinsics.checkNotNull(next);
                    key = next.getKey();
                    value = next.getValue();
                    Logger.INSTANCE.d(this.tag, "onFullBackup:fileName=" + key);
                    Intrinsics.checkNotNull(key);
                    separator = File.separator;
                    Intrinsics.checkNotNullExpressionValue(separator, "separator");
                    if (StringsKt.contains$default((CharSequence) key, (CharSequence) separator, false, 2, (Object) null)) {
                        addAttachedFile(key);
                    } else {
                        addAttachedFile(value, key);
                    }
                }
                attachesIterator2 = getMTodoManager().getAttachesIterator();
                while (attachesIterator2.hasNext()) {
                    Map.Entry<String, Uri> next2 = attachesIterator2.next();
                    Intrinsics.checkNotNull(next2);
                    addAttachedFile(next2.getValue(), next2.getKey());
                }
                return 0;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            throw th;
        }
        fileOutputStream.close();
        attachesIterator = getMNoteManager().getAttachesIterator();
        while (attachesIterator.hasNext()) {
            Map.Entry<String, Uri> next3 = attachesIterator.next();
            Intrinsics.checkNotNull(next3);
            key = next3.getKey();
            value = next3.getValue();
            Logger.INSTANCE.d(this.tag, "onFullBackup:fileName=" + key);
            Intrinsics.checkNotNull(key);
            separator = File.separator;
            Intrinsics.checkNotNullExpressionValue(separator, "separator");
            if (StringsKt.contains$default((CharSequence) key, (CharSequence) separator, false, 2, (Object) null)) {
                addAttachedFile(key);
            } else {
                addAttachedFile(value, key);
            }
        }
        attachesIterator2 = getMTodoManager().getAttachesIterator();
        while (attachesIterator2.hasNext()) {
            Map.Entry<String, Uri> next4 = attachesIterator2.next();
            Intrinsics.checkNotNull(next4);
            addAttachedFile(next4.getValue(), next4.getKey());
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v13, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v24, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v7, types: [android.net.Uri, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    protected int onAttachRestore(BackupMeta meta, ParcelFileDescriptor fd, String attachName) throws Throwable {
        Logger logger;
        String str;
        StringBuilder sb;
        Intrinsics.checkNotNullParameter(fd, "fd");
        Logger.INSTANCE.d(this.tag, "onAttachRestore:attachName=" + attachName);
        Intrinsics.checkNotNull(attachName);
        OutputStream outputStream = null;
        if (StringsKt.contains$default((CharSequence) attachName, (CharSequence) InternalZipConstants.ZIP_FILE_SEPARATOR, false, 2, (Object) null)) {
            attachName = InternalZipConstants.ZIP_FILE_SEPARATOR + attachName;
        }
        Uri attachesUriByName = getMNoteManager().getAttachesUriByName(attachName);
        FileInputStream fileInputStream = attachesUriByName;
        if (attachesUriByName == null) {
            fileInputStream = getMTodoManager().getAttachesUriByName(attachName);
        }
        try {
            try {
                ?? contentResolver = getContentResolver();
                Intrinsics.checkNotNull(fileInputStream);
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(fileInputStream, AnimatedProperty.PROPERTY_NAME_W);
                if (outputStreamOpenOutputStream == null) {
                    return 1;
                }
                try {
                    fileInputStream = new FileInputStream(fd.getFileDescriptor());
                    try {
                        byte[] bArr = new byte[8192];
                        try {
                            while (true) {
                                int i = fileInputStream.read(bArr);
                                if (i > 0) {
                                    outputStreamOpenOutputStream.write(bArr, 0, i);
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e) {
                                        Logger.INSTANCE.e(this.tag, " attachOutputStream close IOException: " + e);
                                    }
                                }
                                logger.e(str, sb.append(e).toString());
                                return 0;
                            }
                            fileInputStream.close();
                        } catch (IOException e2) {
                            e = e2;
                            logger = Logger.INSTANCE;
                            str = this.tag;
                            sb = new StringBuilder("attachInputStream close IOException: ");
                            logger.e(str, sb.append(e).toString());
                        }
                        outputStreamOpenOutputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                        outputStream = outputStreamOpenOutputStream;
                        fileInputStream = fileInputStream;
                        Logger.INSTANCE.e(this.tag, "onAttachRestore IOException: " + e);
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e4) {
                                Logger.INSTANCE.e(this.tag, " attachOutputStream close IOException: " + e4);
                            }
                        }
                        if (fileInputStream != 0) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e5) {
                                e = e5;
                                logger = Logger.INSTANCE;
                                str = this.tag;
                                sb = new StringBuilder("attachInputStream close IOException: ");
                                logger.e(str, sb.append(e).toString());
                            }
                        }
                        return 0;
                    } catch (IllegalArgumentException e6) {
                        e = e6;
                        outputStream = outputStreamOpenOutputStream;
                        fileInputStream = fileInputStream;
                        Logger.INSTANCE.e(this.tag, "onAttachRestore IllegalArgumentException: " + ExceptionsKt.stackTraceToString(e));
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e7) {
                                Logger.INSTANCE.e(this.tag, " attachOutputStream close IOException: " + e7);
                            }
                        }
                        if (fileInputStream != 0) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e8) {
                                e = e8;
                                logger = Logger.INSTANCE;
                                str = this.tag;
                                sb = new StringBuilder("attachInputStream close IOException: ");
                                logger.e(str, sb.append(e).toString());
                            }
                        }
                        return 0;
                    } catch (Throwable th) {
                        th = th;
                        outputStream = outputStreamOpenOutputStream;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e9) {
                                Logger.INSTANCE.e(this.tag, " attachOutputStream close IOException: " + e9);
                            }
                        }
                        if (fileInputStream == 0) {
                            throw th;
                        }
                        try {
                            fileInputStream.close();
                            throw th;
                        } catch (IOException e10) {
                            Logger.INSTANCE.e(this.tag, "attachInputStream close IOException: " + e10);
                            throw th;
                        }
                    }
                } catch (IOException e11) {
                    e = e11;
                    fileInputStream = 0;
                } catch (IllegalArgumentException e12) {
                    e = e12;
                    fileInputStream = 0;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = 0;
                }
                return 0;
            } catch (IOException e13) {
                e = e13;
                fileInputStream = 0;
            } catch (IllegalArgumentException e14) {
                e = e14;
                fileInputStream = 0;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = 0;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    protected int onRestoreEnd(BackupMeta meta) throws IOException {
        Logger.INSTANCE.d(this.tag, "onRestoreEnd");
        return super.onRestoreEnd(meta);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final AppConfigProtos.AppConfig backupNotesConfig() {
        String strValueOf;
        Logger.INSTANCE.d(this.tag, "backupNotesConfig");
        AppConfigProtos.AppConfig.Builder builderNewBuilder = AppConfigProtos.AppConfig.newBuilder();
        builderNewBuilder.setFontSize(String.valueOf(PreferenceUtils.getFontSize(getApplicationContext(), 1)));
        builderNewBuilder.setQuickDelete(String.valueOf(PreferenceUtils.getSkipConfirmForQuickDelete(getApplicationContext())));
        builderNewBuilder.setSortMode(String.valueOf(PreferenceUtils.getNoteSortWay(getApplicationContext(), 1)));
        int noteHomePageArrayWay = PreferenceUtils.getNoteHomePageArrayWay(getApplicationContext(), 0);
        boolean gridMode = PreferenceUtils.getGridMode(getApplicationContext());
        if (!RomUtils.isPadMode()) {
            strValueOf = gridMode ? "1" : "0";
        } else {
            strValueOf = String.valueOf(noteHomePageArrayWay);
        }
        builderNewBuilder.setBigScreenLayouts(strValueOf);
        builderNewBuilder.setListMode((!RomUtils.isFoldDevice() ? gridMode : noteHomePageArrayWay == 1) ? 2 : 1);
        Context context = (Context) this;
        int i = PreferenceUtils.getRemindType(context) ? 1 : 2;
        Logger.INSTANCE.d(this.tag, "set alert mode " + i);
        builderNewBuilder.setAlertMode(i);
        builderNewBuilder.setExcerptLinkRemind(String.valueOf(PreferenceUtils.getEnableExcerptLinkFloat(context)));
        return builderNewBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void restoreNotesConfig(AppConfigProtos.AppConfig appConfig) {
        Logger.INSTANCE.d(this.tag, "restoreNotesConfig:" + appConfig);
        if (appConfig != null) {
            try {
                String fontSize = appConfig.getFontSize();
                if (!TextUtils.isEmpty(fontSize)) {
                    try {
                        Context applicationContext = getApplicationContext();
                        String fontSize2 = appConfig.getFontSize();
                        Intrinsics.checkNotNullExpressionValue(fontSize2, "getFontSize(...)");
                        PreferenceUtils.setFontSize(applicationContext, Integer.parseInt(fontSize2));
                    } catch (NumberFormatException unused) {
                        Logger.INSTANCE.e(this.tag, "AppConfig restore setFontSize error:  fontSize = " + fontSize);
                    }
                }
                String quickDelete = appConfig.getQuickDelete();
                if (!TextUtils.isEmpty(quickDelete)) {
                    PreferenceUtils.setSkipConfirmForQuickDelete(getApplicationContext(), Boolean.parseBoolean(quickDelete));
                }
                String sortMode = appConfig.getSortMode();
                if (!TextUtils.isEmpty(sortMode)) {
                    Context applicationContext2 = getApplicationContext();
                    Intrinsics.checkNotNull(sortMode);
                    PreferenceUtils.setNoteSortWay(applicationContext2, Integer.parseInt(sortMode));
                }
                String bigScreenLayouts = appConfig.getBigScreenLayouts();
                if (!TextUtils.isEmpty(bigScreenLayouts)) {
                    Context applicationContext3 = getApplicationContext();
                    Intrinsics.checkNotNull(bigScreenLayouts);
                    PreferenceUtils.setNoteHomePageArrayWay(applicationContext3, Integer.parseInt(bigScreenLayouts));
                }
                int listMode = appConfig.getListMode();
                if (listMode == 1) {
                    PreferenceUtils.setGridMode(getApplicationContext(), !LiteUtils.isLiteOrMiddle());
                } else if (listMode == 2) {
                    PreferenceUtils.setGridMode(getApplicationContext(), false);
                }
                Logger.INSTANCE.d(this.tag, "set alertMode:" + appConfig.getAlertMode());
                PreferenceUtils.setRemindType((Context) this, appConfig.getAlertMode() == 1);
                String excerptLinkRemind = appConfig.getExcerptLinkRemind();
                if (TextUtils.isEmpty(excerptLinkRemind)) {
                    return;
                }
                PreferenceUtils.setEnableExcerptLinkFloat((Context) this, Boolean.parseBoolean(excerptLinkRemind));
            } catch (Exception e) {
                Logger.INSTANCE.e(this.tag, "AppConfig restore error: " + ExceptionsKt.stackTraceToString(e));
            }
        }
    }

    private final TodoAppConfigProtos.AppConfig backupTodoAppConfig() {
        TodoAppConfigProtos.AppConfig.Builder builderNewBuilder = TodoAppConfigProtos.AppConfig.newBuilder();
        builderNewBuilder.setEnableFloatMode(String.valueOf(PreferenceUtils.getEnableFloatMode(BaseNoteApp.INSTANCE.getInstance())));
        builderNewBuilder.setFinishShowMode(String.valueOf(PreferenceUtils.getShowFinishedTodo(BaseNoteApp.INSTANCE.getInstance())));
        return builderNewBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void restoreTodoAppConfig(TodoAppConfigProtos.AppConfig appConfig) {
        if (appConfig != null) {
            try {
                String enableFloatMode = appConfig.getEnableFloatMode();
                if (!TextUtils.isEmpty(enableFloatMode)) {
                    if (getApplicationContext() != null) {
                        Logger.INSTANCE.i(this.tag, "setEnableFloatMode:" + enableFloatMode);
                    }
                    PreferenceUtils.setEnableFloatMode((Context) this, Boolean.parseBoolean(enableFloatMode));
                    try {
                        Settings.Secure.putInt(getContentResolver(), "notes_pref_enable_float_mode", Boolean.parseBoolean(enableFloatMode) ? 1 : 0);
                    } catch (Exception unused) {
                    }
                }
                String finishShowMode = appConfig.getFinishShowMode();
                if (TextUtils.isEmpty(finishShowMode)) {
                    return;
                }
                PreferenceUtils.setShowFinishedTodo((Context) this, Boolean.parseBoolean(finishShowMode));
            } catch (Exception e) {
                Logger.INSTANCE.e(this.tag, "AppConfig restore error: " + e);
            }
        }
    }
}
