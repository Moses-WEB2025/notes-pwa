package com.miui.notes.tool;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import ch.qos.logback.core.CoreConstants;
import com.bumptech.glide.Glide;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.ImageUtils;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.SmoothDrawHelper;
import com.miui.common.tool.UIUtils;
import com.miui.doodle.document.DocumentReader;
import com.miui.doodle.document.Layer;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.handwritecommon.api.util.FilePathUtils;
import com.miui.notes.R;
import com.miui.notes.excerpt.LinkUtils;
import com.miui.notes.excerpt.MediaUtils;
import com.miui.notes.excerpt.NotifyUtils;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.opensdk.file.utils.FileSDKUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import miuix.core.util.FileUtils;
import miuix.core.util.IOUtils;
import miuix.security.DigestUtils;
import miuix.text.utilities.ExtraTextUtils;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: AttachmentUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001^B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0007J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u0007J\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u0007J\u0012\u0010\u001b\u001a\u00020\t2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0007J\u000e\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u001e\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0010\u0010\u001f\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0010\u0010 \u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0003J\u000e\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u0013J\u0010\u0010#\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0014\u0010$\u001a\u0004\u0018\u00010\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0013H\u0007J\u0014\u0010&\u001a\u0004\u0018\u00010\u00052\b\u0010'\u001a\u0004\u0018\u00010(H\u0002J\u001a\u0010)\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010*\u001a\u0004\u0018\u00010\u0005J\u001a\u0010.\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0007J\u0010\u0010/\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\b\u00100\u001a\u00020\u0010H\u0007J\u0018\u00101\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u00102\u001a\u000203J\u0018\u00101\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u00104\u001a\u000205J\u0010\u00106\u001a\u0004\u0018\u00010\u00052\u0006\u00107\u001a\u00020\u0005J\u001a\u00108\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u00104\u001a\u0004\u0018\u000105J&\u00108\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u00104\u001a\u0004\u0018\u0001052\b\u00109\u001a\u0004\u0018\u00010\u0005H\u0007J$\u00108\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u00102\u001a\u0004\u0018\u0001032\u0006\u0010:\u001a\u00020\tH\u0007J\u001c\u0010;\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010<\u001a\u0004\u0018\u00010\u0013H\u0007J\u0018\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\u00132\u0006\u0010?\u001a\u00020\u0013H\u0002J\u0012\u0010:\u001a\u00020\u00102\b\u0010@\u001a\u0004\u0018\u00010\u0013H\u0002J\u001e\u00108\u001a\u0004\u0018\u00010\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\"\u001a\u0004\u0018\u00010\u0013H\u0007J\u0018\u0010A\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\b\u00102\u001a\u0004\u0018\u000103J \u0010B\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u00102\u001a\u0002032\b\u0010C\u001a\u0004\u0018\u00010\u0013J\u0014\u0010D\u001a\u0004\u0018\u00010\u00052\b\u0010E\u001a\u0004\u0018\u00010\u0005H\u0007J$\u0010F\u001a\u00020\t2\u0006\u00104\u001a\u0002052\b\u0010C\u001a\u0004\u0018\u00010\u00132\b\b\u0002\u0010A\u001a\u00020\tH\u0007J(\u0010F\u001a\u00020\t2\u0006\u00104\u001a\u0002052\u0006\u0010G\u001a\u00020\u000e2\b\u0010C\u001a\u0004\u0018\u00010\u00132\u0006\u0010A\u001a\u00020\tJ4\u0010H\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010J\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010I2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010K\u001a\u00020J2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u0007J8\u0010L\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0006\u0012\u0004\u0018\u00010N0M2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010K\u001a\u00020J2\u0010\u0010O\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010PJ,\u0010Q\u001a\u0004\u0018\u0001052\b\u0010R\u001a\u0004\u0018\u00010\u00052\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010U\u001a\u00020\u000eH\u0007J,\u0010V\u001a\u0002052\b\u0010W\u001a\u0004\u0018\u00010\u00152\u0006\u0010X\u001a\u00020\u000e2\u0006\u0010Y\u001a\u00020\u000e2\b\u00102\u001a\u0004\u0018\u000103H\u0007J4\u0010Z\u001a\u0002052\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010X\u001a\u00020\u000e2\u0006\u0010Y\u001a\u00020\u000e2\b\u00102\u001a\u0004\u0018\u0001032\u0006\u0010[\u001a\u00020\tH\u0007J\u0010\u0010\\\u001a\u00020\t2\b\u0010]\u001a\u0004\u0018\u00010\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010+\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u0006_"}, d2 = {"Lcom/miui/notes/tool/AttachmentUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "sFreshAttachments", "", "sCleanAttachmentFileFinished", "", "DOODLE_SUFFIX", "DOODLE_TYPE", "IMAGE_SUFFIX_JPEG", "compressTarget", "", "recordFreshAttachment", "", "name", "getAttachmentDir", "Ljava/io/File;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "mkdirs", "dir", "getAttachmentPath", FileSDKUtils.J_FILENAME, "getDoodleThumbnailPath", "isDoodleSuffix", "fileName", "cleanAttachmentFile", "checkAudioFile", "checkMissedAudioFile", "deleteIsolatedAttachmentFileBlocking", "deleteFiles", "file", "verifyAttachmentFileBlocking", "tryGetImageMimeType", "imageFile", "getImageMimeType", "inputStream", "Ljava/io/InputStream;", "saveJsonToTemp", "json", "entFileName", "getEntFileName", "()Ljava/lang/String;", "getRecognizeText", "getHandWriteShareDir", "deleteHandWriteShareImage", "saveImageToTemp", CallMethod.ARG_URI, "Landroid/net/Uri;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "downloadImageToTemp", "imageUrl", "saveImageFile", "oldSha1Digest", "compress", "saveAudioFile", "audioFile", "copyFile", "source", TypedValues.AttributesType.S_TARGET, "tmpFile", "isContainAlpha", "saveAttachmentFile", "dstFile", "getFileSha1Digest", "filePath", "saveBitmap", "quality", "getDataIdAndMimeTypeByAttachmentName", "Landroid/util/Pair;", "", NotifyUtils.KEY_NOTE_ID, "getImageInfosByName", "", "Lcom/miui/notes/tool/AttachmentUtils$ImageInfo;", "imageNames", "", "clipImageAttachment", "imagePath", "expectWidth", "expectHeight", "radius", "resizeImageAttachment", "mContext", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "getThumbnailFromAttachment", "speedFirst", "isBitmapNameValidate", "bitmapName", "ImageInfo", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AttachmentUtils {
    public static final String DOODLE_SUFFIX = ".doodle";
    public static final String DOODLE_TYPE = "application/miuidoodle";
    public static final String IMAGE_SUFFIX_JPEG = ".jpeg";
    public static final AttachmentUtils INSTANCE = new AttachmentUtils();
    private static final String TAG = "AttachmentUtils";
    private static final int compressTarget = 20971520;
    private static volatile boolean sCleanAttachmentFileFinished;
    private static final Set<String> sFreshAttachments;

    public final boolean saveBitmap(Bitmap bitmap, File file) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        return saveBitmap$default(this, bitmap, file, false, 4, null);
    }

    private AttachmentUtils() {
    }

    static {
        Set<String> setNewSetFromMap = Collections.newSetFromMap(new ConcurrentHashMap());
        Intrinsics.checkNotNullExpressionValue(setNewSetFromMap, "newSetFromMap(...)");
        sFreshAttachments = setNewSetFromMap;
    }

    @JvmStatic
    public static final void recordFreshAttachment(String name) {
        if (sCleanAttachmentFileFinished) {
            return;
        }
        sFreshAttachments.add(name);
    }

    @JvmStatic
    public static final File getAttachmentDir(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File filesDir = context.getFilesDir();
        if (filesDir == null) {
            Logger.INSTANCE.e(TAG, "Fail to retrieve files directory");
            return null;
        }
        File file = new File(filesDir.getAbsolutePath() + "/attachment");
        boolean zExists = file.exists();
        if (INSTANCE.mkdirs(file)) {
            if (!zExists) {
                file.setExecutable(true, false);
            }
            return file;
        }
        Logger.INSTANCE.e(TAG, "Fail to create attachment directory: " + file.getPath());
        return null;
    }

    private final boolean mkdirs(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return true;
            }
            Logger.INSTANCE.e(TAG, "File path is not a directory: " + dir.getPath());
            return false;
        }
        if (dir.mkdirs()) {
            return true;
        }
        Logger.INSTANCE.e(TAG, "Fail to create directory: " + dir.getPath());
        return false;
    }

    @JvmStatic
    public static final String getAttachmentPath(Context context, String filename) {
        File attachmentDir;
        Intrinsics.checkNotNullParameter(context, "context");
        if (AudioUtils.isMp3Suffix(filename)) {
            attachmentDir = AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME);
        } else {
            attachmentDir = getAttachmentDir(context);
        }
        if (attachmentDir == null) {
            return null;
        }
        if (filename != null) {
            String path = attachmentDir.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            if (StringsKt.contains$default((CharSequence) filename, (CharSequence) path, false, 2, (Object) null)) {
                return filename;
            }
        }
        return attachmentDir.getPath() + InternalZipConstants.ZIP_FILE_SEPARATOR + filename;
    }

    @JvmStatic
    public static final String getDoodleThumbnailPath(Context context, String filename) {
        Intrinsics.checkNotNullParameter(context, "context");
        String attachmentPath = getAttachmentPath(context, filename);
        if (attachmentPath == null) {
            return null;
        }
        return new DocumentReader(new File(attachmentPath)).extractPreviewImageToTmp(context);
    }

    @JvmStatic
    public static final boolean isDoodleSuffix(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            Intrinsics.checkNotNull(fileName);
            if (StringsKt.endsWith$default(fileName, DOODLE_SUFFIX, false, 2, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    public final void cleanAttachmentFile(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Thread thread = new Thread(new Runnable() { // from class: com.miui.notes.tool.AttachmentUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AttachmentUtils.cleanAttachmentFile$lambda$0(context);
            }
        }, "cleanAttachmentFile");
        thread.setPriority(1);
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cleanAttachmentFile$lambda$0(Context context) {
        AttachmentUtils attachmentUtils = INSTANCE;
        attachmentUtils.deleteIsolatedAttachmentFileBlocking(context);
        attachmentUtils.verifyAttachmentFileBlocking(context);
        sCleanAttachmentFileFinished = true;
        sFreshAttachments.clear();
    }

    public final void checkAudioFile(final Context context) {
        Thread thread = new Thread(new Runnable() { // from class: com.miui.notes.tool.AttachmentUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AttachmentUtils.checkAudioFile$lambda$1(context);
            }
        }, "checkAudioFile");
        thread.setPriority(1);
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkAudioFile$lambda$1(Context context) {
        try {
            INSTANCE.checkMissedAudioFile(context);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "error in checkAudioFile " + e);
        }
        sCleanAttachmentFileFinished = true;
        sFreshAttachments.clear();
    }

    public final void checkMissedAudioFile(Context context) {
        File fileObtainMp3FileDir;
        final Set<String> setCheckNoteAudioValidity = AudioUtils.checkNoteAudioValidity(context, null);
        if (setCheckNoteAudioValidity == null || (fileObtainMp3FileDir = AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME)) == null) {
            return;
        }
        fileObtainMp3FileDir.listFiles(new FileFilter() { // from class: com.miui.notes.tool.AttachmentUtils.checkMissedAudioFile.1
            @Override // java.io.FileFilter
            public boolean accept(File pathname) {
                Intrinsics.checkNotNullParameter(pathname, "pathname");
                String name = pathname.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                if (StringsKt.endsWith$default("temp_audio_file", name, false, 2, (Object) null)) {
                    return false;
                }
                if (!AudioUtils.isMp3FileNameFormat(pathname.getName())) {
                    Logger.INSTANCE.i(AttachmentUtils.TAG, pathname.getName() + " is deleted because of name format.");
                    pathname.delete();
                } else if (!setCheckNoteAudioValidity.contains(pathname.getName()) && !AttachmentUtils.sFreshAttachments.contains(pathname.getName())) {
                    Logger.INSTANCE.i(AttachmentUtils.TAG, pathname.getName() + " is deleted because it is not used.");
                    pathname.delete();
                }
                return false;
            }
        });
    }

    private final void deleteIsolatedAttachmentFileBlocking(Context context) {
        File attachmentDir = getAttachmentDir(context);
        if (attachmentDir == null) {
            return;
        }
        final HashSet hashSet = new HashSet();
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"content"}, "mime_type!=? AND mime_type!=?", new String[]{Notes.TextData.CONTENT_ITEM_TYPE, Notes.CallData.CONTENT_ITEM_TYPE}, null);
                if (cursorQuery != null) {
                    if (cursorQuery.getColumnIndex("content") < 0) {
                        Logger.INSTANCE.w(TAG, "Fail to delete isolated attachment file, maybe in guest mode");
                        cursorQuery.close();
                        return;
                    } else {
                        while (cursorQuery.moveToNext()) {
                            hashSet.add(cursorQuery.getString(0));
                        }
                        attachmentDir.listFiles(new FileFilter() { // from class: com.miui.notes.tool.AttachmentUtils$$ExternalSyntheticLambda0
                            @Override // java.io.FileFilter
                            public final boolean accept(File file) {
                                return AttachmentUtils.deleteIsolatedAttachmentFileBlocking$lambda$2(hashSet, file);
                            }
                        });
                        cursorQuery.close();
                        return;
                    }
                }
                Logger.INSTANCE.w(TAG, "Fail to delete isolated attachment file, cursor is null");
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "fail to load all attchment names " + e);
                if (0 != 0) {
                    cursor.close();
                }
            }
        } catch (Throwable th) {
            if (0 == 0) {
                throw th;
            }
            cursor.close();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean deleteIsolatedAttachmentFileBlocking$lambda$2(HashSet hashSet, File file) {
        if (hashSet.contains(file.getName()) || sFreshAttachments.contains(file.getName())) {
            return false;
        }
        Logger.INSTANCE.i(TAG, file.getName() + " is deleted by Isolated Mechanism");
        file.delete();
        return false;
    }

    public final void deleteFiles(File file) {
        File[] fileArrListFiles;
        Intrinsics.checkNotNullParameter(file, "file");
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            Iterator it = ArrayIteratorKt.iterator(fileArrListFiles);
            while (it.hasNext()) {
                File file2 = (File) it.next();
                if (file2.isFile()) {
                    file2.delete();
                } else if (file2.isDirectory()) {
                    Intrinsics.checkNotNull(file2);
                    deleteFiles(file2);
                }
            }
        }
        file.delete();
    }

    private final void verifyAttachmentFileBlocking(Context context) {
        File attachmentDir = getAttachmentDir(context);
        if (attachmentDir == null) {
            return;
        }
        attachmentDir.listFiles(new FileFilter() { // from class: com.miui.notes.tool.AttachmentUtils.verifyAttachmentFileBlocking.1
            @Override // java.io.FileFilter
            public boolean accept(File pathname) {
                String fileSha1Digest;
                Intrinsics.checkNotNullParameter(pathname, "pathname");
                if (pathname.isDirectory()) {
                    fileSha1Digest = "";
                } else {
                    fileSha1Digest = AttachmentUtils.getFileSha1Digest(pathname.getAbsolutePath());
                }
                if (!Intrinsics.areEqual(pathname.getName(), fileSha1Digest) && !Intrinsics.areEqual(pathname.getName(), fileSha1Digest + AttachmentUtils.DOODLE_SUFFIX) && !AttachmentUtils.INSTANCE.isBitmapNameValidate(pathname.getName())) {
                    String name = pathname.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                    if (!StringsKt.endsWith$default(name, ".ent", false, 2, (Object) null) && !AttachmentUtils.sFreshAttachments.contains(pathname.getName())) {
                        Logger.INSTANCE.i(AttachmentUtils.TAG, pathname.getName() + " is deleted by SHA-1 Mechanism");
                        pathname.delete();
                    }
                }
                return false;
            }
        });
    }

    @JvmStatic
    public static final String tryGetImageMimeType(File imageFile) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        if (imageFile == null || !imageFile.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(imageFile);
            try {
                try {
                    String imageMimeType = INSTANCE.getImageMimeType(fileInputStream);
                    IOUtils.closeQuietly((InputStream) fileInputStream);
                    return imageMimeType;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    IOUtils.closeQuietly((InputStream) fileInputStream2);
                    throw th;
                }
            } catch (FileNotFoundException e) {
                e = e;
                Logger.INSTANCE.w(TAG, "Can open attachment uri with exception:" + e.getMessage());
                IOUtils.closeQuietly((InputStream) fileInputStream);
                return null;
            }
        } catch (FileNotFoundException e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly((InputStream) fileInputStream2);
            throw th;
        }
    }

    private final String getImageMimeType(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        return options.outMimeType;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0058 A[Catch: Exception -> 0x007a, TryCatch #3 {Exception -> 0x007a, blocks: (B:21:0x004a, B:23:0x0058, B:25:0x006a, B:26:0x006d, B:28:0x0073), top: B:47:0x004a }] */
    /* JADX WARN: Code duplicated, block: B:25:0x006a A[Catch: Exception -> 0x007a, TryCatch #3 {Exception -> 0x007a, blocks: (B:21:0x004a, B:23:0x0058, B:25:0x006a, B:26:0x006d, B:28:0x0073), top: B:47:0x004a }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0073 A[Catch: Exception -> 0x007a, TRY_LEAVE, TryCatch #3 {Exception -> 0x007a, blocks: (B:21:0x004a, B:23:0x0058, B:25:0x006a, B:26:0x006d, B:28:0x0073), top: B:47:0x004a }] */
    /* JADX WARN: Code duplicated, block: B:50:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:? A[RETURN, SYNTHETIC] */
    public final String saveJsonToTemp(Context context, String json) throws Throwable {
        OutputStreamWriter outputStreamWriter;
        String fileSha1Digest;
        String attachmentPath;
        File file;
        Intrinsics.checkNotNullParameter(context, "context");
        File attachmentDir = getAttachmentDir(context);
        OutputStreamWriter outputStreamWriter2 = null;
        if (attachmentDir == null) {
            return null;
        }
        File file2 = new File(attachmentDir, String.valueOf(System.currentTimeMillis()));
        recordFreshAttachment(file2.getName());
        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file2));
            try {
                try {
                    try {
                        outputStreamWriter.write(json);
                    } catch (Throwable th) {
                        th = th;
                        outputStreamWriter2 = outputStreamWriter;
                        if (outputStreamWriter2 != null) {
                            try {
                                outputStreamWriter2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    if (outputStreamWriter != null) {
                    }
                    fileSha1Digest = getFileSha1Digest(file2.getAbsolutePath());
                    attachmentPath = getAttachmentPath(context, fileSha1Digest);
                    if (attachmentPath != null) {
                        return null;
                    }
                    file = new File(attachmentPath);
                    recordFreshAttachment(file.getName());
                    if (file.exists()) {
                        file.delete();
                    }
                    if (file2.renameTo(file)) {
                        return null;
                    }
                    file.setReadable(true, false);
                    return fileSha1Digest;
                }
                fileSha1Digest = getFileSha1Digest(file2.getAbsolutePath());
                attachmentPath = getAttachmentPath(context, fileSha1Digest);
                if (attachmentPath != null) {
                    return null;
                }
                file = new File(attachmentPath);
                recordFreshAttachment(file.getName());
                if (file.exists()) {
                    file.delete();
                }
                if (file2.renameTo(file)) {
                    return null;
                }
                file.setReadable(true, false);
                return fileSha1Digest;
            } catch (Exception e3) {
                e3.printStackTrace();
                if (!file2.exists()) {
                    return null;
                }
                file2.delete();
                return null;
            }
        } catch (Exception e4) {
            e = e4;
            outputStreamWriter = null;
        } catch (Throwable th2) {
            th = th2;
            if (outputStreamWriter2 != null) {
                outputStreamWriter2.close();
            }
            throw th;
        }
        try {
            outputStreamWriter.close();
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    public final String getEntFileName() {
        String str = System.currentTimeMillis() + ".ent";
        recordFreshAttachment(str);
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    @JvmStatic
    public static final String getRecognizeText(Context context, String fileName) throws Throwable {
        String string;
        Intrinsics.checkNotNullParameter(context, "context");
        String attachmentPath = getAttachmentPath(context, fileName);
        ?? r5 = 0;
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(attachmentPath)));
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }
                    string = sb.toString();
                    String str = "toString(...)";
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    try {
                        bufferedReader2.close();
                        r5 = str;
                    } catch (IOException e) {
                        e.printStackTrace();
                        r5 = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader = bufferedReader2;
                    e.printStackTrace();
                    Logger.INSTANCE.e(TAG, "getRecognizeText error: " + e);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    string = "";
                    r5 = bufferedReader;
                } catch (Throwable th) {
                    th = th;
                    r5 = bufferedReader2;
                    if (r5 != 0) {
                        try {
                            r5.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
            }
            return string;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final File getHandWriteShareDir(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File attachmentDir = getAttachmentDir(context);
        if (attachmentDir == null) {
            return null;
        }
        File file = new File(attachmentDir.getAbsolutePath() + "/handwrite_share");
        boolean zExists = file.exists();
        if (!mkdirs(file)) {
            return null;
        }
        if (!zExists) {
            file.setExecutable(true, false);
        }
        return file;
    }

    @JvmStatic
    public static final void deleteHandWriteShareImage() {
        File[] fileArrListFiles;
        File singlePageExportDir = FilePathUtils.getSinglePageExportDir(BaseNoteApp.INSTANCE.getInstance());
        if (singlePageExportDir == null || !singlePageExportDir.exists() || !singlePageExportDir.isDirectory() || (fileArrListFiles = singlePageExportDir.listFiles()) == null) {
            return;
        }
        int length = fileArrListFiles.length;
        for (int i = 0; i < length; i++) {
            if (fileArrListFiles[i].exists()) {
                fileArrListFiles[i].delete();
            }
        }
    }

    public final String saveImageToTemp(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        File attachmentDir = getAttachmentDir(context);
        if (attachmentDir == null) {
            return null;
        }
        File file = new File(attachmentDir, System.currentTimeMillis() + IMAGE_SUFFIX_JPEG);
        recordFreshAttachment(file.getName());
        if (saveAttachmentFile(context, uri, file)) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public final String saveImageToTemp(Context context, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        File attachmentDir = getAttachmentDir(context);
        if (attachmentDir == null) {
            return null;
        }
        File file = new File(attachmentDir, String.valueOf(System.currentTimeMillis()));
        recordFreshAttachment(file.getName());
        if (saveBitmap(bitmap, file, true)) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public final String downloadImageToTemp(String imageUrl) {
        Intrinsics.checkNotNullParameter(imageUrl, "imageUrl");
        try {
            if (LinkUtils.INSTANCE.isClipUrlAccurateValid(imageUrl)) {
                File file = Glide.with(BaseNoteApp.INSTANCE.getInstance()).downloadOnly().load(imageUrl).submit().get();
                if (MediaUtils.isSkipImg(file.getAbsolutePath())) {
                    return null;
                }
                return saveImageFile(BaseNoteApp.INSTANCE.getInstance(), file);
            }
            return saveImageFile((Context) BaseNoteApp.INSTANCE.getInstance(), Uri.fromFile(new File(imageUrl)), true);
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "downloadImageToTemp error: " + e);
            return null;
        }
    }

    public final String saveImageFile(Context context, Bitmap bitmap) {
        File attachmentDir;
        String fileSha1Digest;
        String attachmentPath;
        Intrinsics.checkNotNullParameter(context, "context");
        String str = null;
        if (bitmap == null || bitmap.isRecycled() || (attachmentDir = getAttachmentDir(context)) == null) {
            return null;
        }
        File file = new File(attachmentDir, String.valueOf(System.currentTimeMillis()));
        recordFreshAttachment(file.getName());
        try {
            if (saveBitmap(bitmap, file, true) && (attachmentPath = getAttachmentPath(context, (fileSha1Digest = getFileSha1Digest(file.getAbsolutePath())))) != null) {
                File file2 = new File(attachmentPath);
                recordFreshAttachment(file2.getName());
                if (file2.exists()) {
                    file2.delete();
                }
                if (file.renameTo(file2)) {
                    file2.setReadable(true, false);
                    str = fileSha1Digest;
                } else {
                    Logger.INSTANCE.e(TAG, "Fail to rename file from " + file.getPath() + " to " + file2.getPath());
                }
            }
            return str;
        } finally {
            if (file.exists()) {
                file.delete();
            }
            bitmap.recycle();
        }
    }

    @JvmStatic
    public static final String saveImageFile(Context context, Bitmap bitmap, String oldSha1Digest) {
        Intrinsics.checkNotNullParameter(context, "context");
        AttachmentUtils attachmentUtils = INSTANCE;
        File attachmentDir = getAttachmentDir(context);
        String str = null;
        if (attachmentDir == null) {
            return null;
        }
        File file = new File(attachmentDir, String.valueOf(System.currentTimeMillis()));
        recordFreshAttachment(file.getName());
        try {
            Intrinsics.checkNotNull(bitmap);
            if (saveBitmap$default(attachmentUtils, bitmap, file, false, 4, null)) {
                String fileSha1Digest = getFileSha1Digest(file.getAbsolutePath());
                String attachmentPath = getAttachmentPath(context, fileSha1Digest);
                if (attachmentPath != null) {
                    File file2 = new File(attachmentPath);
                    recordFreshAttachment(file2.getName());
                    if (file2.exists()) {
                        Logger.INSTANCE.e(TAG, "delete old sha1Digest  " + fileSha1Digest);
                        file2.delete();
                    }
                    if (file.renameTo(file2)) {
                        file2.setReadable(true, false);
                        if (!TextUtils.isEmpty(oldSha1Digest) && !Intrinsics.areEqual(oldSha1Digest, fileSha1Digest)) {
                            File file3 = new File(getAttachmentPath(context, oldSha1Digest));
                            if (file3.exists()) {
                                Logger.INSTANCE.e(TAG, "delete old oldSha1Digest" + oldSha1Digest);
                                file3.delete();
                            }
                        }
                        str = fileSha1Digest;
                    } else {
                        Logger.INSTANCE.e(TAG, "Fail to rename file from " + file.getPath() + " to " + file2.getPath());
                    }
                }
            } else {
                Logger.INSTANCE.e(TAG, "Fail to save attachment: " + file.getPath());
            }
            if (file.exists()) {
                Logger.INSTANCE.e(TAG, "delete old tmpFile" + file);
                file.delete();
            }
            bitmap.recycle();
            return str;
        } catch (Throwable th) {
            if (file.exists()) {
                Logger.INSTANCE.e(TAG, "delete old tmpFile" + file);
                file.delete();
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
            throw th;
        }
    }

    @JvmStatic
    public static final String saveImageFile(Context context, Uri uri, boolean compress) throws Throwable {
        String fileSha1Digest;
        Intrinsics.checkNotNullParameter(context, "context");
        Bitmap bitmap = null;
        if (uri == null) {
            Logger.INSTANCE.e(TAG, "saveImageFile Fail uri is null");
            return null;
        }
        AttachmentUtils attachmentUtils = INSTANCE;
        boolean zIsContainAlpha = attachmentUtils.isContainAlpha(context, uri);
        File attachmentDir = getAttachmentDir(context);
        if (attachmentDir == null) {
            return null;
        }
        File file = new File(attachmentDir, String.valueOf(System.currentTimeMillis()));
        recordFreshAttachment(file.getName());
        try {
            if (attachmentUtils.saveAttachmentFile(context, uri, file)) {
                ImageUtils imageUtils = ImageUtils.INSTANCE;
                String absolutePath = file.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                Bitmap bitmapCreateThumbnail = imageUtils.createThumbnail(absolutePath, false, false);
                try {
                    if (bitmapCreateThumbnail != null) {
                        if (attachmentUtils.saveBitmap(bitmapCreateThumbnail, file, zIsContainAlpha)) {
                            if (compress) {
                                attachmentUtils.compress(file);
                            }
                            fileSha1Digest = getFileSha1Digest(file.getAbsolutePath());
                            String attachmentPath = getAttachmentPath(context, fileSha1Digest);
                            if (attachmentPath != null) {
                                File file2 = new File(attachmentPath);
                                recordFreshAttachment(file2.getName());
                                if (file2.exists()) {
                                    file2.delete();
                                }
                                if (file.renameTo(file2)) {
                                    file2.setReadable(true, false);
                                } else {
                                    Logger.INSTANCE.e(TAG, "Fail to rename file from " + file.getPath() + " to " + file2.getPath());
                                }
                            }
                            bitmap = bitmapCreateThumbnail;
                        } else {
                            Logger.INSTANCE.e(TAG, "Fail to save bitmap: " + file.getPath());
                        }
                    } else {
                        Logger.INSTANCE.e(TAG, "Fail to create thumbnail: " + file.getPath());
                    }
                    fileSha1Digest = null;
                    bitmap = bitmapCreateThumbnail;
                } catch (Throwable th) {
                    th = th;
                    bitmap = bitmapCreateThumbnail;
                    if (file.exists()) {
                        file.delete();
                    }
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    throw th;
                }
            } else {
                Logger.INSTANCE.e(TAG, "Fail to save attachment: " + file.getPath());
                fileSha1Digest = null;
            }
            if (file.exists()) {
                file.delete();
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
            return fileSha1Digest;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @JvmStatic
    public static final String saveAudioFile(Context context, File audioFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        String str = null;
        if (audioFile == null || !audioFile.exists()) {
            Logger.INSTANCE.e(TAG, "saveAudioFile Fail audioFile is null or not exists");
            return null;
        }
        File fileObtainMp3FileDir = AudioUtils.obtainMp3FileDir(AudioUtils.NOTE_MP3_DIR_NAME);
        if (fileObtainMp3FileDir == null) {
            Logger.INSTANCE.e(TAG, "Fail to get mp3 directory");
            return null;
        }
        File file = new File(fileObtainMp3FileDir, System.currentTimeMillis() + "_tmp");
        AttachmentUtils attachmentUtils = INSTANCE;
        recordFreshAttachment(file.getName());
        try {
            try {
                attachmentUtils.copyFile(audioFile, file);
                if (file.exists() && file.length() > 0) {
                    String str2 = getFileSha1Digest(file.getAbsolutePath()) + ".mp3";
                    File file2 = new File(fileObtainMp3FileDir, str2);
                    recordFreshAttachment(file2.getName());
                    if (file2.exists()) {
                        file2.delete();
                    }
                    if (file.renameTo(file2)) {
                        file2.setReadable(true, false);
                        try {
                            Logger.INSTANCE.i(TAG, "Successfully saved audio file: " + str2);
                            str = str2;
                        } catch (Exception e) {
                            e = e;
                            str = str2;
                            Logger.INSTANCE.e(TAG, "Error saving audio file: " + e.getMessage());
                            return str;
                        }
                    } else {
                        Logger.INSTANCE.e(TAG, "Fail to rename file from " + file.getPath() + " to " + file2.getPath());
                    }
                } else {
                    Logger.INSTANCE.e(TAG, "Fail to copy audio file: " + file.getPath());
                }
                return str;
            } catch (Exception e2) {
                e = e2;
            }
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private final void copyFile(File source, File target) throws IOException {
        FileChannel channel = new FileInputStream(source).getChannel();
        FileChannel channel2 = new FileOutputStream(target).getChannel();
        try {
            channel2.transferFrom(channel, 0L, channel.size());
        } finally {
            channel.close();
            channel2.close();
        }
    }

    /* JADX WARN: Code duplicated, block: B:113:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:114:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:73:0x0127  */
    /* JADX WARN: Code duplicated, block: B:90:0x0131 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x011d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:99:0x00fe A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.miui.common.tool.Logger] */
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
    private final void compress(File tmpFile) throws Throwable {
        Bitmap bitmapDecodeFile;
        ByteArrayOutputStream byteArrayOutputStream;
        Exception e;
        boolean zIsRecycled;
        Bitmap bitmap;
        FileOutputStream fileOutputStream;
        Throwable th;
        IOException e2;
        boolean zIsRecycled2;
        if (tmpFile == null || !tmpFile.exists()) {
            return;
        }
        ?? r9 = Logger.INSTANCE;
        ?? r3 = "fileSize:";
        ?? r0 = "fileSize:" + tmpFile.length();
        r9.d(TAG, r0);
        if (tmpFile.length() < 20971520) {
            return;
        }
        try {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                int i = 1;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(tmpFile.getAbsolutePath(), options);
                while ((options.outWidth / i) * (options.outHeight / i) > compressTarget) {
                    i *= 2;
                }
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inSampleSize = i;
                bitmapDecodeFile = BitmapFactory.decodeFile(tmpFile.getAbsolutePath(), options2);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        int i2 = 90;
                        bitmapDecodeFile.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        while (byteArrayOutputStream.toByteArray().length > compressTarget) {
                            byteArrayOutputStream.reset();
                            i2 -= 10;
                            if (i2 < 0) {
                                break;
                            } else {
                                bitmapDecodeFile.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
                            }
                        }
                        try {
                            fileOutputStream = new FileOutputStream(tmpFile);
                            try {
                                try {
                                    try {
                                        try {
                                            fileOutputStream.write(byteArrayOutputStream.toByteArray());
                                            fileOutputStream.flush();
                                        } catch (Throwable th2) {
                                            th = th2;
                                            if (fileOutputStream != null) {
                                                try {
                                                    fileOutputStream.close();
                                                } catch (IOException e3) {
                                                    e3.printStackTrace();
                                                }
                                            }
                                            throw th;
                                        }
                                    } catch (IOException e4) {
                                        e2 = e4;
                                        e2.printStackTrace();
                                        if (fileOutputStream != null) {
                                        }
                                        UIUtils.runOnUIThread(new Runnable() { // from class: com.miui.notes.tool.AttachmentUtils.compress.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
                                                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                                                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.insert_image_size_limit_tip);
                                                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                                                String str = String.format(string, Arrays.copyOf(new Object[]{20}, 1));
                                                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                                                Toast.makeText(companion, str, 0).show();
                                            }
                                        });
                                        Logger.INSTANCE.d(TAG, "quality:" + i2 + " sampleSize:" + i + " fileSize:" + tmpFile.length());
                                        byteArrayOutputStream.close();
                                        if (bitmapDecodeFile != null) {
                                            zIsRecycled2 = bitmapDecodeFile.isRecycled();
                                            bitmap = bitmapDecodeFile;
                                            if (zIsRecycled2) {
                                                return;
                                            }
                                            bitmap.recycle();
                                        }
                                    }
                                    fileOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                                byteArrayOutputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        } catch (IOException e7) {
                            fileOutputStream = null;
                            e2 = e7;
                        } catch (Throwable th3) {
                            fileOutputStream = null;
                            th = th3;
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw th;
                        }
                        UIUtils.runOnUIThread(new Runnable() { // from class: com.miui.notes.tool.AttachmentUtils.compress.1
                            @Override // java.lang.Runnable
                            public void run() {
                                BaseNoteApp companion = BaseNoteApp.INSTANCE.getInstance();
                                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.insert_image_size_limit_tip);
                                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                                String str = String.format(string, Arrays.copyOf(new Object[]{20}, 1));
                                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                                Toast.makeText(companion, str, 0).show();
                            }
                        });
                        Logger.INSTANCE.d(TAG, "quality:" + i2 + " sampleSize:" + i + " fileSize:" + tmpFile.length());
                        if (bitmapDecodeFile != null) {
                            zIsRecycled2 = bitmapDecodeFile.isRecycled();
                            bitmap = bitmapDecodeFile;
                            if (zIsRecycled2) {
                                return;
                            }
                            bitmap.recycle();
                        }
                    } catch (Exception e8) {
                        e = e8;
                        e.printStackTrace();
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e9) {
                                e9.printStackTrace();
                            }
                        }
                        if (bitmapDecodeFile == null) {
                        }
                        zIsRecycled = bitmapDecodeFile.isRecycled();
                        bitmap = bitmapDecodeFile;
                        if (zIsRecycled) {
                        }
                    }
                } catch (Exception e10) {
                    e = e10;
                    byteArrayOutputStream = null;
                    bitmapDecodeFile = bitmapDecodeFile;
                    e = e;
                    e.printStackTrace();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    if (bitmapDecodeFile == null) {
                        zIsRecycled = bitmapDecodeFile.isRecycled();
                        bitmap = bitmapDecodeFile;
                        if (zIsRecycled) {
                        }
                        bitmap.recycle();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    r3 = 0;
                    r0 = bitmapDecodeFile;
                    th = th;
                    if (r3 != 0) {
                        try {
                            r3.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                        }
                    }
                    if (r0 == 0) {
                        throw th;
                    }
                    throw th;
                }
            } catch (Exception e12) {
                e = e12;
                bitmapDecodeFile = null;
                byteArrayOutputStream = null;
            } catch (Throwable th5) {
                th = th5;
                r0 = 0;
                r3 = 0;
            }
        } catch (Throwable th6) {
            th = th6;
            if (r3 != 0) {
                r3.close();
            }
            if (r0 == 0 || r0.isRecycled()) {
                throw th;
            }
            r0.recycle();
            throw th;
        }
    }

    @JvmStatic
    public static final String saveImageFile(Context context, File file) {
        if (context == null) {
            Logger.INSTANCE.e(TAG, "saveImageFile Fail context is null");
            return null;
        }
        if (getAttachmentDir(context) == null) {
            Logger.INSTANCE.e(TAG, "saveImageFile Fail dstDir is null");
            return null;
        }
        if (file == null) {
            Logger.INSTANCE.e(TAG, "saveImageFromBitmap Fail to create thumbnail: path null");
            return null;
        }
        String fileSha1Digest = getFileSha1Digest(file.getAbsolutePath());
        String attachmentPath = getAttachmentPath(context, fileSha1Digest);
        if (attachmentPath != null) {
            File file2 = new File(attachmentPath);
            recordFreshAttachment(file2.getName());
            if (file2.exists()) {
                file2.delete();
            }
            if (file.renameTo(file2)) {
                file2.setReadable(true, false);
                return fileSha1Digest;
            }
            Logger.INSTANCE.e(TAG, "saveImageFromBitmap Fail to rename file from " + file.getPath() + " to " + file2.getPath());
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:43:0x003a A[EXC_TOP_SPLITTER, PHI: r7
  0x003a: PHI (r7v7 java.io.InputStream) = (r7v4 java.io.InputStream), (r7v9 java.io.InputStream) binds: [B:14:0x0038, B:28:0x0051] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final boolean isContainAlpha(Context context, Uri uri) throws Throwable {
        InputStream inputStreamOpenInputStream;
        Intrinsics.checkNotNullParameter(context, "context");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream inputStream = null;
        if (uri != null) {
            try {
                inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                try {
                    try {
                        BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    } catch (Throwable th) {
                        th = th;
                        inputStream = inputStreamOpenInputStream;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    Logger.INSTANCE.d(TAG, "Fail to open inputStream. " + e);
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            } catch (Exception e4) {
                e = e4;
                inputStreamOpenInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        } else {
            inputStreamOpenInputStream = null;
        }
        if (inputStreamOpenInputStream != null) {
            inputStreamOpenInputStream.close();
        }
        String str = options.outMimeType;
        if (str != null) {
            return StringsKt.endsWith$default(str, "png", false, 2, (Object) null) || StringsKt.endsWith$default(str, "bmp", false, 2, (Object) null);
        }
        return false;
    }

    public final boolean saveAttachmentFile(Context context, Uri uri, File dstFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        InputStream inputStreamOpenInputStream = null;
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            return FileUtils.copyToFile(inputStreamOpenInputStream, dstFile);
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "Save attachment file failed " + e);
            return false;
        } finally {
            IOUtils.closeQuietly(inputStreamOpenInputStream);
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @JvmStatic
    public static final String getFileSha1Digest(String filePath) {
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
            try {
                try {
                    String hexReadable = ExtraTextUtils.toHexReadable(DigestUtils.get(bufferedInputStream, "SHA-1"));
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused) {
                    }
                    return hexReadable;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream2 = bufferedInputStream;
                    if (bufferedInputStream2 != null) {
                        try {
                            bufferedInputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e) {
                e = e;
                Logger.INSTANCE.e(TAG, "Fail to getFileSha1Digest " + e);
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            bufferedInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedInputStream2 != null) {
                bufferedInputStream2.close();
            }
            throw th;
        }
    }

    public static /* synthetic */ boolean saveBitmap$default(AttachmentUtils attachmentUtils, Bitmap bitmap, File file, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return attachmentUtils.saveBitmap(bitmap, file, z);
    }

    public final boolean saveBitmap(Bitmap bitmap, File dstFile, boolean isContainAlpha) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        return saveBitmap(bitmap, 90, dstFile, isContainAlpha);
    }

    public final boolean saveBitmap(Bitmap bitmap, int quality, File dstFile, boolean isContainAlpha) throws Throwable {
        Bitmap bitmapScaledBitMap;
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        FileOutputStream fileOutputStream = null;
        if (isContainAlpha) {
            bitmapScaledBitMap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(bitmapScaledBitMap);
            canvas.drawColor(DisplayUtils.isNightMode() ? -16777216 : -1);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        } else {
            bitmapScaledBitMap = bitmap;
        }
        boolean zCompress = false;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(dstFile);
                try {
                    Intrinsics.checkNotNull(bitmapScaledBitMap);
                    fileOutputStream = fileOutputStream2;
                    zCompress = bitmapScaledBitMap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                    if (!zCompress) {
                        bitmapScaledBitMap = ImageUtils.INSTANCE.scaledBitMap(bitmap);
                        Intrinsics.checkNotNull(bitmapScaledBitMap);
                        fileOutputStream = fileOutputStream2;
                        zCompress = bitmapScaledBitMap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                    }
                    try {
                        fileOutputStream2.close();
                    } catch (IOException unused) {
                    }
                    if (!Intrinsics.areEqual(bitmapScaledBitMap, bitmap)) {
                        bitmapScaledBitMap.recycle();
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    Logger.INSTANCE.e(TAG, "saveBitmap fail " + e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    if (!Intrinsics.areEqual(bitmapScaledBitMap, bitmap)) {
                        Intrinsics.checkNotNull(bitmapScaledBitMap);
                    }
                    return zCompress;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    if (Intrinsics.areEqual(bitmapScaledBitMap, bitmap)) {
                        throw th;
                    }
                    Intrinsics.checkNotNull(bitmapScaledBitMap);
                    bitmapScaledBitMap.recycle();
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                e = e2;
            }
            return zCompress;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @JvmStatic
    public static final Pair<Long, String> getDataIdAndMimeTypeByAttachmentName(Context context, long noteId, String filename) {
        Intrinsics.checkNotNullParameter(context, "context");
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Data.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"_id", Notes.Data.MIME_TYPE}, "content=? AND note_id=?", new String[]{filename, String.valueOf(noteId)}, null);
        if (cursorQuery == null) {
            return null;
        }
        try {
            if (cursorQuery.moveToNext()) {
                return new Pair<>(Long.valueOf(cursorQuery.getLong(0)), cursorQuery.getString(1));
            }
            return null;
        } finally {
            cursorQuery.close();
        }
    }

    public final Map<String, ImageInfo> getImageInfosByName(Context context, long noteId, List<String> imageNames) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (imageNames == null || imageNames.isEmpty()) {
            return new LinkedHashMap();
        }
        Cursor cursorQuery = context.getContentResolver().query(NotesProvider.appendFlag(Notes.Data.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"_id", "content", Notes.Data.MIME_TYPE}, "content IN ('" + TextUtils.join("','", imageNames) + "') AND note_id=" + noteId, null, null);
        if (cursorQuery != null) {
            try {
                HashMap map = new HashMap(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    String string = cursorQuery.getString(1);
                    map.put(string, new ImageInfo(j, string, cursorQuery.getString(2)));
                }
                return map;
            } finally {
                cursorQuery.close();
            }
        }
        return new LinkedHashMap();
    }

    @JvmStatic
    public static final Bitmap clipImageAttachment(String imagePath, int expectWidth, int expectHeight, int radius) {
        int i;
        boolean z;
        Rect rect;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        Paint paint = null;
        if (i2 <= 0 || i3 <= 0) {
            return null;
        }
        if (expectWidth * i3 > expectHeight * i2) {
            i = i2 / expectWidth;
            z = false;
        } else {
            i = i3 / expectHeight;
            z = true;
        }
        int iMax = (int) Math.max(i, 1.0d);
        options.inJustDecodeBounds = false;
        options.inSampleSize = iMax;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(imagePath, options);
        if (bitmapDecodeFile == null) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(expectWidth, expectHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        if (z) {
            int width = (bitmapDecodeFile.getWidth() - ((int) Math.max((bitmapDecodeFile.getHeight() * expectWidth) / expectHeight, 1.0d))) / 2;
            rect = new Rect(width, 0, bitmapDecodeFile.getWidth() - width, bitmapDecodeFile.getHeight());
        } else {
            int height = (bitmapDecodeFile.getHeight() - ((int) Math.max((bitmapDecodeFile.getWidth() * expectHeight) / expectWidth, 1.0d))) / 2;
            rect = new Rect(0, height, bitmapDecodeFile.getWidth(), bitmapDecodeFile.getHeight() - height);
        }
        if (radius > 0) {
            paint = new Paint();
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0.0f, 0.0f, expectWidth, expectHeight);
            new SmoothDrawHelper();
            Path path = new Path();
            float f = radius;
            path.addRoundRect(rectF, f, f, Path.Direction.CW);
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        canvas.drawBitmap(bitmapDecodeFile, rect, new Rect(0, 0, expectWidth, expectHeight), paint);
        bitmapDecodeFile.recycle();
        return bitmapCreateBitmap;
    }

    @Deprecated(message = "")
    public final Bitmap resizeImageAttachment(Context mContext, int width, int height, Uri uri) {
        BitmapDecoder bitmapDecoder = new BitmapDecoder(mContext, uri);
        int height2 = (bitmapDecoder.getHeight() * width) / bitmapDecoder.getWidth();
        int iMax = (int) Math.max(width, 1.0d);
        int iMax2 = (int) Math.max(height2, 1.0d);
        if (iMax2 <= height) {
            height = iMax2;
        } else if (LiteUtils.isLiteOrMiddle()) {
            iMax = (int) Math.max((bitmapDecoder.getWidth() * height) / bitmapDecoder.getHeight(), 1.0d);
            height = (int) Math.max(height, 1.0d);
        }
        Bitmap element = bitmapDecoder.getScaledBitmap(iMax, height, 48);
        if (!element.isMutable()) {
            Bitmap.Config config = element.getConfig();
            element = config != null ? element.copy(config, true) : null;
        }
        Intrinsics.checkNotNullExpressionValue(element, "element");
        return element;
    }

    @JvmStatic
    public static final Bitmap getThumbnailFromAttachment(Context context, int width, int height, Uri uri, boolean speedFirst) {
        BitmapDecoder bitmapDecoder = new BitmapDecoder(context, uri);
        if (speedFirst && Intrinsics.areEqual("image/png", bitmapDecoder.getMimeType()) && width * height > 3000000) {
            height = (int) (height * 0.5f);
            width = (int) (width * 0.5f);
        }
        int height2 = (bitmapDecoder.getHeight() * width) / bitmapDecoder.getWidth();
        int iMax = (int) Math.max(width, 1.0d);
        int iMax2 = (int) Math.max(height2, 1.0d);
        if (iMax2 <= height) {
            height = iMax2;
        } else if (LiteUtils.isLiteOrMiddle()) {
            iMax = (int) Math.max((bitmapDecoder.getWidth() * height) / bitmapDecoder.getHeight(), 1.0d);
            height = (int) Math.max(height, 1.0d);
        }
        Bitmap element = bitmapDecoder.getScaledBitmap(iMax, height, 48);
        if (!element.isMutable()) {
            Bitmap.Config config = element.getConfig();
            element = config != null ? element.copy(config, true) : null;
        }
        Intrinsics.checkNotNullExpressionValue(element, "element");
        return element;
    }

    public final boolean isBitmapNameValidate(String bitmapName) {
        int iLastIndexOf$default;
        if (bitmapName != null && (iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) bitmapName, ".", 0, false, 6, (Object) null)) != -1) {
            bitmapName = bitmapName.substring(0, iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(bitmapName, "substring(...)");
        }
        try {
            return UUID.fromString(bitmapName) != null;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    /* JADX INFO: compiled from: AttachmentUtils.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/miui/notes/tool/AttachmentUtils$ImageInfo;", "", "id", "", "imageName", "", "mimeType", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(JLjava/lang/String;Ljava/lang/String;)V", "getImageName", "()Ljava/lang/String;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ImageInfo {
        public final long id;
        private final String imageName;
        public final String mimeType;

        public ImageInfo(long j, String str, String str2) {
            this.id = j;
            this.imageName = str;
            this.mimeType = str2;
        }

        public final String getImageName() {
            return this.imageName;
        }
    }
}
