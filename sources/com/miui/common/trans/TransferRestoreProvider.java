package com.miui.common.trans;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.hardware.DataSpace;
import ch.qos.logback.classic.pattern.CallerDataConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.ZipUtils;
import com.miui.notes.ai.utils.LiteUtils;
import com.miui.notes.backup.AppConfigProtos;
import com.miui.notes.backup.ExtensionKt;
import com.miui.notes.backup.NoteManager;
import com.miui.notes.backup.NoteProtos;
import com.miui.notes.backup.SyncRootProtos;
import com.miui.notes.backup.TodoAppConfigProtos;
import com.miui.notes.backup.TodoManager;
import com.miui.notes.backup.TodoProtos;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.bean.TodoEntity;
import com.miui.todo.data.provider.TodoDatabaseAccessUtils;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.data.utils.GuideUtils;
import com.miui.todo.data.utils.TodoDataUtils;
import com.xiaomi.accounts.secure.SecureDatabaseHelper;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import miuix.smartaction.SmartAction;
import org.apache.commons.lang3.StringUtils;
import org.ccil.cowan.tagsoup.XMLWriter;

/* JADX INFO: compiled from: TransferRestoreProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0018\u001a\u00020\u0019H\u0016JK\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u000e\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u00052\u000e\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001f2\b\u0010\"\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0002\u0010#J\u0012\u0010$\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u001c\u0010%\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010&\u001a\u0004\u0018\u00010'H\u0016J/\u0010(\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010 \u001a\u0004\u0018\u00010\u00052\u000e\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001fH\u0016¢\u0006\u0002\u0010*J9\u0010+\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010&\u001a\u0004\u0018\u00010'2\b\u0010 \u001a\u0004\u0018\u00010\u00052\u000e\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001fH\u0016¢\u0006\u0002\u0010,J&\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020\u00052\b\u00100\u001a\u0004\u0018\u00010\u00052\b\u00101\u001a\u0004\u0018\u00010.H\u0016J\u0010\u00102\u001a\u00020.2\u0006\u00101\u001a\u00020.H\u0002J\u0012\u00103\u001a\u00020\u00192\b\u00104\u001a\u0004\u0018\u00010\u0005H\u0002J\u0018\u00105\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\u0005H\u0002J\u0018\u00107\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\u0005H\u0002J\u0018\u00108\u001a\u00020)2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00106\u001a\u00020\u0005H\u0002J\u0010\u00109\u001a\u00020)2\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J\u0012\u0010@\u001a\u00020=2\b\u0010A\u001a\u0004\u0018\u00010BH\u0002J\u0010\u0010C\u001a\u00020=2\u0006\u0010D\u001a\u00020EH\u0002J\u0012\u0010F\u001a\u00020=2\b\u0010A\u001a\u0004\u0018\u00010GH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lcom/miui/common/trans/TransferRestoreProvider;", "Landroid/content/ContentProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "NOTE_LITE_DEVICE", "", "NOTE_SYNCROOT_NAME", "NOTE_ATTACHMENT_NAME", "NOTE_MP3_NAME", "TAG", "mNoteManager", "Lcom/miui/notes/backup/NoteManager;", "getMNoteManager", "()Lcom/miui/notes/backup/NoteManager;", "mNoteManager$delegate", "Lkotlin/Lazy;", "mTodoManager", "Lcom/miui/notes/backup/TodoManager;", "getMTodoManager", "()Lcom/miui/notes/backup/TodoManager;", "mTodoManager$delegate", "mPbFilePath", "mMp3FilePath", "mAttachmentFilePath", "onCreate", "", SmartAction.Feature.QUERY, "Landroid/database/Cursor;", CallMethod.ARG_URI, "Landroid/net/Uri;", "projection", "", "selection", "selectionArgs", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "getType", "insert", "values", "Landroid/content/ContentValues;", ToDoStat.DELETE, "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "call", "Landroid/os/Bundle;", XMLWriter.METHOD, "arg", SecureDatabaseHelper.TABLE_EXTRAS, "transferFiles", "checkFileSafe", "fileName", "handlePbFile", "zipName", "handleMp3File", "handleAttachmentFile", "restoreData", "data", "Landroid/os/ParcelFileDescriptor;", "restoreNotesData", "", "notes", "Lcom/miui/notes/backup/NoteProtos$Notes;", "restoreNotesConfig", "appConfig", "Lcom/miui/notes/backup/AppConfigProtos$AppConfig;", "restoreTodoData", "todos", "Lcom/miui/notes/backup/TodoProtos$Todos;", "restoreTodoAppConfig", "Lcom/miui/notes/backup/TodoAppConfigProtos$AppConfig;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TransferRestoreProvider extends ContentProvider {
    private String mAttachmentFilePath;
    private String mMp3FilePath;
    private String mPbFilePath;
    private final String NOTE_LITE_DEVICE = "note_lite_device";
    private final String NOTE_SYNCROOT_NAME = "note_syncroot_name";
    private final String NOTE_ATTACHMENT_NAME = "note_attachment_name";
    private final String NOTE_MP3_NAME = "note_mp3_name";
    private final String TAG = "TransferRestoreProvider";

    /* JADX INFO: renamed from: mNoteManager$delegate, reason: from kotlin metadata */
    private final Lazy mNoteManager = LazyKt.lazy(new Function0() { // from class: com.miui.common.trans.TransferRestoreProvider$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TransferRestoreProvider.mNoteManager_delegate$lambda$0(this.f$0);
        }
    });

    /* JADX INFO: renamed from: mTodoManager$delegate, reason: from kotlin metadata */
    private final Lazy mTodoManager = LazyKt.lazy(new Function0() { // from class: com.miui.common.trans.TransferRestoreProvider$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TransferRestoreProvider.mTodoManager_delegate$lambda$1(this.f$0);
        }
    });

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return 0;
    }

    private final NoteManager getMNoteManager() {
        return (NoteManager) this.mNoteManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NoteManager mNoteManager_delegate$lambda$0(TransferRestoreProvider transferRestoreProvider) {
        Context context = transferRestoreProvider.getContext();
        Intrinsics.checkNotNull(context);
        return new NoteManager(context);
    }

    private final TodoManager getMTodoManager() {
        return (TodoManager) this.mTodoManager.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TodoManager mTodoManager_delegate$lambda$1(TransferRestoreProvider transferRestoreProvider) {
        Context context = transferRestoreProvider.getContext();
        Intrinsics.checkNotNull(context);
        return new TodoManager(context);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        return "";
    }

    @Override // android.content.ContentProvider
    public Bundle call(String method, String arg, Bundle extras) {
        Intrinsics.checkNotNullParameter(method, "method");
        Logger.INSTANCE.d(this.TAG, "call method " + method);
        if (Intrinsics.areEqual(method, "transfer")) {
            if (extras == null) {
                return null;
            }
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            Bundle bundleTransferFiles = transferFiles(extras);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return bundleTransferFiles;
        }
        if (!Intrinsics.areEqual(method, "isLiteDevice")) {
            return null;
        }
        Bundle bundle = new Bundle();
        if (LiteUtils.isAllLiteOrMiddle()) {
            Logger.INSTANCE.d(this.TAG, "transferFiles lite devices");
            bundle.putInt(this.NOTE_LITE_DEVICE, 0);
            return bundle;
        }
        bundle.putInt(this.NOTE_LITE_DEVICE, 1);
        return bundle;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:37:0x013d  */
    /* JADX WARN: Code duplicated, block: B:39:0x0141  */
    private final Bundle transferFiles(Bundle extras) {
        ArrayList<Uri> parcelableArrayList;
        String str;
        int i;
        String str2;
        String str3;
        Logger.INSTANCE.d(this.TAG, "transferFiles start");
        if (Build.VERSION.SDK_INT > 33) {
            parcelableArrayList = extras.getParcelableArrayList("android.intent.extra.STREAM", Uri.class);
        } else {
            parcelableArrayList = extras.getParcelableArrayList("android.intent.extra.STREAM");
        }
        String string = extras.getString(this.NOTE_SYNCROOT_NAME);
        String string2 = extras.getString(this.NOTE_MP3_NAME);
        String string3 = extras.getString(this.NOTE_ATTACHMENT_NAME);
        Bundle bundle = new Bundle();
        if (checkFileSafe(string)) {
            Logger.INSTANCE.d(this.TAG, "unsafe pbName " + string);
            return bundle;
        }
        if (checkFileSafe(string2)) {
            Logger.INSTANCE.d(this.TAG, "unsafe mp3Name " + string2);
            return bundle;
        }
        if (checkFileSafe(string3)) {
            Logger.INSTANCE.d(this.TAG, "unsafe attachmentName " + string3);
            return bundle;
        }
        if (parcelableArrayList != null) {
            for (Uri uri : parcelableArrayList) {
                Logger.INSTANCE.d(this.TAG, "authority is " + uri);
                if (string != null && string.length() > 0) {
                    String string4 = uri.toString();
                    Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
                    if (StringsKt.indexOf$default((CharSequence) string4, string, 0, false, 6, (Object) null) != -1) {
                        bundle.putInt(uri.toString(), handlePbFile(uri, string + ".zip"));
                        str2 = string3;
                        str3 = string2;
                    } else {
                        if (string2 != null) {
                            str = string2;
                            i = -1;
                            if (string3 != null) {
                            }
                        } else {
                            str = string2;
                            i = -1;
                            if (string3 != null) {
                            }
                        }
                        str2 = string3;
                        str3 = str;
                    }
                } else {
                    if (string2 != null || string2.length() <= 0) {
                        str = string2;
                        i = -1;
                    } else {
                        String string5 = uri.toString();
                        Intrinsics.checkNotNullExpressionValue(string5, "toString(...)");
                        String str4 = string5;
                        i = -1;
                        String str5 = string2;
                        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str4, str5, 0, false, 6, (Object) null);
                        str = str5;
                        if (iIndexOf$default != -1) {
                            bundle.putInt(uri.toString(), handleMp3File(uri, str + ".zip"));
                        }
                        str2 = string3;
                        str3 = str;
                    }
                    if (string3 != null || string3.length() <= 0) {
                        str2 = string3;
                        str3 = str;
                    } else {
                        String string6 = uri.toString();
                        Intrinsics.checkNotNullExpressionValue(string6, "toString(...)");
                        str2 = string3;
                        str3 = str;
                        if (StringsKt.indexOf$default((CharSequence) string6, str2, 0, false, 6, (Object) null) != i) {
                            bundle.putInt(uri.toString(), handleAttachmentFile(uri, str2 + ".zip"));
                        }
                    }
                }
                string2 = str3;
                string3 = str2;
            }
        }
        Logger logger = Logger.INSTANCE;
        String str6 = this.TAG;
        String string7 = bundle.toString();
        Intrinsics.checkNotNullExpressionValue(string7, "toString(...)");
        logger.d(str6, string7);
        return bundle;
    }

    private final boolean checkFileSafe(String fileName) {
        if (fileName == null) {
            return true;
        }
        String str = fileName;
        return StringsKt.contains$default((CharSequence) str, (CharSequence) ".", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) CallerDataConverter.DEFAULT_RANGE_DELIMITER, false, 2, (Object) null);
    }

    private final int handlePbFile(Uri uri, String zipName) throws IOException {
        File filesDir;
        String absolutePath;
        File filesDir2;
        ContentResolver contentResolver;
        File filesDir3;
        String absolutePath2;
        Logger.INSTANCE.d(this.TAG, "handlePbFile start uri:" + uri + " zip" + zipName);
        Context context = getContext();
        String absolutePath3 = null;
        String str = (context == null || (filesDir3 = context.getFilesDir()) == null || (absolutePath2 = filesDir3.getAbsolutePath()) == null) ? null : absolutePath2 + File.separator + zipName;
        this.mPbFilePath = str;
        File file = str != null ? new File(str) : null;
        if (file != null && !file.exists()) {
            file.createNewFile();
        }
        byte[] bArr = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Context context2 = getContext();
        InputStream inputStreamOpenInputStream = (context2 == null || (contentResolver = context2.getContentResolver()) == null) ? null : contentResolver.openInputStream(uri);
        if (inputStreamOpenInputStream != null) {
            while (true) {
                int i = inputStreamOpenInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
        }
        if (inputStreamOpenInputStream != null) {
            inputStreamOpenInputStream.close();
        }
        fileOutputStream.close();
        if (file != null && (absolutePath = file.getAbsolutePath()) != null) {
            Logger.INSTANCE.d(this.TAG, "unzip pbZip");
            Context context3 = getContext();
            ZipUtils.unZip(absolutePath, (context3 == null || (filesDir2 = context3.getFilesDir()) == null) ? null : filesDir2.getAbsolutePath());
            file.delete();
        }
        Context context4 = getContext();
        if (context4 != null && (filesDir = context4.getFilesDir()) != null) {
            absolutePath3 = filesDir.getAbsolutePath();
        }
        File file2 = new File(absolutePath3 + File.separator + StringsKt.replace$default(zipName, ".zip", ".pb", false, 4, (Object) null));
        if (!file2.exists()) {
            return -1;
        }
        Logger.INSTANCE.d(this.TAG, "pbUnzipFile exists");
        ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file2, DataSpace.RANGE_LIMITED);
        Intrinsics.checkNotNull(parcelFileDescriptorOpen);
        int iRestoreData = restoreData(parcelFileDescriptorOpen);
        file2.delete();
        return iRestoreData;
    }

    private final int handleMp3File(Uri uri, String zipName) throws IOException {
        String absolutePath;
        File externalFilesDir;
        ContentResolver contentResolver;
        File externalFilesDir2;
        String absolutePath2;
        Logger.INSTANCE.d(this.TAG, "handleMp3File start uri:" + uri + " zip:" + zipName);
        Context context = getContext();
        String absolutePath3 = null;
        String str = (context == null || (externalFilesDir2 = context.getExternalFilesDir(AudioUtils.NOTE_MP3_DIR_NAME)) == null || (absolutePath2 = externalFilesDir2.getAbsolutePath()) == null) ? null : absolutePath2 + File.separator + zipName;
        this.mMp3FilePath = str;
        File file = str != null ? new File(str) : null;
        if (file != null && !file.exists()) {
            file.createNewFile();
        }
        byte[] bArr = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Context context2 = getContext();
        InputStream inputStreamOpenInputStream = (context2 == null || (contentResolver = context2.getContentResolver()) == null) ? null : contentResolver.openInputStream(uri);
        if (inputStreamOpenInputStream != null) {
            while (true) {
                int i = inputStreamOpenInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
        }
        if (inputStreamOpenInputStream != null) {
            inputStreamOpenInputStream.close();
        }
        fileOutputStream.close();
        Context context3 = getContext();
        if (context3 != null && (externalFilesDir = context3.getExternalFilesDir(AudioUtils.NOTE_MP3_DIR_NAME)) != null) {
            absolutePath3 = externalFilesDir.getAbsolutePath();
        }
        if (file == null || (absolutePath = file.getAbsolutePath()) == null) {
            return -1;
        }
        Logger.INSTANCE.d(this.TAG, "unzip mp3Zip name:" + absolutePath + " size: " + file.length());
        ZipUtils.unZip(absolutePath, absolutePath3);
        file.delete();
        if (absolutePath3 != null) {
            File file2 = new File(absolutePath3);
            Logger logger = Logger.INSTANCE;
            String str2 = this.TAG;
            String string = Arrays.toString(file2.list());
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            logger.d(str2, "unzip mp3Zip result: " + string);
        }
        return 0;
    }

    private final int handleAttachmentFile(Uri uri, String zipName) throws IOException {
        String absolutePath;
        File filesDir;
        ContentResolver contentResolver;
        File filesDir2;
        String absolutePath2;
        Logger.INSTANCE.d(this.TAG, "handleAttachmentFile start uri:" + uri + " zip" + zipName);
        Context context = getContext();
        String str = null;
        String str2 = (context == null || (filesDir2 = context.getFilesDir()) == null || (absolutePath2 = filesDir2.getAbsolutePath()) == null) ? null : absolutePath2 + File.separator + zipName;
        this.mAttachmentFilePath = str2;
        File file = str2 != null ? new File(str2) : null;
        if (file != null && !file.exists()) {
            file.createNewFile();
        }
        byte[] bArr = new byte[1024];
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Context context2 = getContext();
        InputStream inputStreamOpenInputStream = (context2 == null || (contentResolver = context2.getContentResolver()) == null) ? null : contentResolver.openInputStream(uri);
        if (inputStreamOpenInputStream != null) {
            while (true) {
                int i = inputStreamOpenInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
        }
        if (inputStreamOpenInputStream != null) {
            inputStreamOpenInputStream.close();
        }
        fileOutputStream.close();
        Context context3 = getContext();
        if (context3 != null && (filesDir = context3.getFilesDir()) != null) {
            str = filesDir.getAbsolutePath() + File.separator + "attachment";
        }
        if (file == null || (absolutePath = file.getAbsolutePath()) == null) {
            return -1;
        }
        Logger.INSTANCE.d(this.TAG, "unzip attachmentZip name:" + absolutePath + " size: " + file.length());
        ZipUtils.unZip(absolutePath, str);
        file.delete();
        if (str != null) {
            File file2 = new File(str);
            Logger logger = Logger.INSTANCE;
            String str3 = this.TAG;
            String string = Arrays.toString(file2.list());
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            logger.d(str3, "unzip attachmentZip result: " + string);
        }
        return 0;
    }

    private final int restoreData(ParcelFileDescriptor data) throws IOException {
        NoteProtos.Notes note;
        TodoAppConfigProtos.AppConfig todoconfig;
        AppConfigProtos.AppConfig config;
        TodoProtos.Todos todo;
        Logger.INSTANCE.d(this.TAG, "onDataRestore");
        if (getContext() == null) {
            return -1;
        }
        PreferenceUtils.setAddIntroduction(getContext(), true);
        try {
            FileInputStream fileInputStream = new FileInputStream(data.getFileDescriptor());
            SyncRootProtos.SyncRoot from = SyncRootProtos.SyncRoot.parseFrom(fileInputStream);
            fileInputStream.close();
            if (from != null) {
                note = from.getNote();
                config = from.getConfig();
                todo = from.getTodo();
                todoconfig = from.getTodoconfig();
            } else {
                note = null;
                todoconfig = null;
                config = null;
                todo = null;
            }
            if (note == null) {
                Logger.INSTANCE.e(this.TAG, "notes is null");
                return -1;
            }
            restoreNotesData(note);
            restoreNotesConfig(config);
            if (todo == null) {
                Logger.INSTANCE.e(this.TAG, "todos is null");
                return -1;
            }
            restoreTodoData(todo);
            restoreTodoAppConfig(todoconfig);
            Logger.INSTANCE.i(this.TAG, "onDataRestore,notes count:" + note.getNoteCount() + ",todos:" + todo.getTodoCount() + ",folders :" + note.getFolderCount() + StringUtils.SPACE);
            PreferenceUtils.setAddTodoIntroduction(getContext(), true);
            Notes.Utils.updateAllAlarms(getContext());
            return 0;
        } catch (Exception e) {
            Logger.INSTANCE.e(this.TAG, "Exception in onDataRestore ,exception:" + ExceptionsKt.stackTraceToString(e));
            return -1;
        }
    }

    private final void restoreNotesData(NoteProtos.Notes notes) {
        NoteProtos.Notes notesClone = ExtensionKt.clone(notes);
        for (NoteProtos.NoteFolder noteFolder : notesClone.getFolderList()) {
            try {
                String subject = noteFolder.getSubject();
                if (subject != null && getMNoteManager().loadNoteFolderByTitle(subject) == null) {
                    NoteManager mNoteManager = getMNoteManager();
                    Intrinsics.checkNotNull(noteFolder);
                    mNoteManager.addNoteFolder(noteFolder);
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "Cannot add note folder ,exception:" + e);
            }
        }
        for (NoteProtos.NoteEntity noteEntity : notesClone.getNoteList()) {
            try {
                NoteManager mNoteManager2 = getMNoteManager();
                Intrinsics.checkNotNull(noteEntity);
                if (NoteManager.shouldRestore$default(mNoteManager2, noteEntity, false, 2, null)) {
                    NoteManager.insertNoteEntity$default(getMNoteManager(), noteEntity, false, 2, null);
                }
            } catch (Exception e2) {
                Logger.INSTANCE.e(this.TAG, "Cannot add note entity ,exception:" + e2);
            }
        }
    }

    private final void restoreNotesConfig(AppConfigProtos.AppConfig appConfig) {
        Logger.INSTANCE.d(this.TAG, "restoreNotesConfig:" + appConfig);
        if (appConfig != null) {
            try {
                String fontSize = appConfig.getFontSize();
                if (!TextUtils.isEmpty(fontSize)) {
                    try {
                        Context context = getContext();
                        String fontSize2 = appConfig.getFontSize();
                        Intrinsics.checkNotNullExpressionValue(fontSize2, "getFontSize(...)");
                        PreferenceUtils.setFontSize(context, Integer.parseInt(fontSize2));
                    } catch (NumberFormatException unused) {
                        Logger.INSTANCE.e(this.TAG, "AppConfig restore setFontSize error:  fontSize = " + fontSize);
                    }
                }
                String quickDelete = appConfig.getQuickDelete();
                if (!TextUtils.isEmpty(quickDelete)) {
                    PreferenceUtils.setSkipConfirmForQuickDelete(getContext(), Boolean.parseBoolean(quickDelete));
                }
                String sortMode = appConfig.getSortMode();
                if (!TextUtils.isEmpty(sortMode)) {
                    Context context2 = getContext();
                    Intrinsics.checkNotNull(sortMode);
                    PreferenceUtils.setNoteSortWay(context2, Integer.parseInt(sortMode));
                }
                String bigScreenLayouts = appConfig.getBigScreenLayouts();
                if (!TextUtils.isEmpty(bigScreenLayouts)) {
                    Context context3 = getContext();
                    Intrinsics.checkNotNull(bigScreenLayouts);
                    PreferenceUtils.setNoteHomePageArrayWay(context3, Integer.parseInt(bigScreenLayouts));
                }
                int listMode = appConfig.getListMode();
                if (listMode == 1) {
                    PreferenceUtils.setGridMode(getContext(), !com.miui.common.tool.LiteUtils.isLiteOrMiddle());
                } else if (listMode == 2) {
                    PreferenceUtils.setGridMode(getContext(), false);
                }
                Logger.INSTANCE.e(this.TAG, "set alertMode:" + appConfig.getAlertMode());
                PreferenceUtils.setRemindType(getContext(), appConfig.getAlertMode() == 1);
                String excerptLinkRemind = appConfig.getExcerptLinkRemind();
                if (TextUtils.isEmpty(excerptLinkRemind)) {
                    return;
                }
                PreferenceUtils.setEnableExcerptLinkFloat(getContext(), Boolean.parseBoolean(excerptLinkRemind));
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "AppConfig restore error: " + ExceptionsKt.stackTraceToString(e));
            }
        }
    }

    private final void restoreTodoData(TodoProtos.Todos todos) {
        TodoProtos.Todos todosClone = ExtensionKt.clone(todos, todos);
        Cursor cursorQueryAllUnFinishItem = TodoProviderAccessUtils.queryAllUnFinishItem();
        int count = cursorQueryAllUnFinishItem.getCount();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (count != 0) {
            while (cursorQueryAllUnFinishItem.moveToNext()) {
                long j = cursorQueryAllUnFinishItem.getLong(0);
                TodoEntity todoEntity = TodoDataUtils.getTodoEntity(cursorQueryAllUnFinishItem);
                Intrinsics.checkNotNullExpressionValue(todoEntity, "getTodoEntity(...)");
                if (!GuideUtils.isGuideTodo(todoEntity)) {
                    arrayList2.add(Long.valueOf(j));
                } else {
                    arrayList.add(Long.valueOf(j));
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
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "Cannot add todo entity ,exception:" + e);
            }
        }
        arrayList.addAll(arrayList2);
        TodoDatabaseAccessUtils.updateUnFinishedArray(arrayList);
    }

    private final void restoreTodoAppConfig(TodoAppConfigProtos.AppConfig appConfig) {
        if (appConfig != null) {
            try {
                String enableFloatMode = appConfig.getEnableFloatMode();
                if (!TextUtils.isEmpty(enableFloatMode)) {
                    PreferenceUtils.setEnableFloatMode(getContext(), Boolean.parseBoolean(enableFloatMode));
                    try {
                        Context context = getContext();
                        Settings.Secure.putInt(context != null ? context.getContentResolver() : null, "notes_pref_enable_float_mode", Boolean.parseBoolean(enableFloatMode) ? 1 : 0);
                    } catch (Exception unused) {
                    }
                }
                String finishShowMode = appConfig.getFinishShowMode();
                if (TextUtils.isEmpty(finishShowMode)) {
                    return;
                }
                PreferenceUtils.setShowFinishedTodo(getContext(), Boolean.parseBoolean(finishShowMode));
            } catch (Exception e) {
                Logger.INSTANCE.e(this.TAG, "AppConfig restore error: " + e);
            }
        }
    }
}
