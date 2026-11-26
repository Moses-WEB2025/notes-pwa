package com.miui.doodle.document;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import ch.qos.logback.core.CoreConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.R;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.document.png.PNGChunk;
import com.miui.doodle.document.png.PNGReader;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.utils.BitmapDecoder;
import com.miui.doodle.utils.DisplayUtils;
import com.miui.doodle.utils.RomUtils;
import com.miui.doodle.utils.Utils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.AttachmentUtils;
import dalvik.system.ZipPathValidator;
import defpackage.R2;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/* JADX INFO: compiled from: DocumentReader.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tJ \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ(\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002J\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0007H\u0002J\u0018\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0007JN\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u001a\u0010 \u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00140!R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/miui/doodle/document/DocumentReader;", "", "mDoodlePNGFile", "Ljava/io/File;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/io/File;)V", "extractPreviewImageToTmp", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "read", "Lcom/miui/doodle/document/DoodleDocument;", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "isPreload", "", "addLayerToDocumentByBitmap", "", "doodleDocument", "background", "Landroid/graphics/Bitmap;", DoodleBitmap.KEY_BITMAP, "readBitmap", "zipFile", "Ljava/util/zip/ZipFile;", "zipEntryName", "readString", "readJsonReader", "Lcom/miui/doodle/document/Layer;", "layerWidth", "", "layerHeight", "readBitmapFunc", "Lkotlin/Function2;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DocumentReader {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "DocumentReader";
    private final File mDoodlePNGFile;

    public DocumentReader(File mDoodlePNGFile) {
        Intrinsics.checkNotNullParameter(mDoodlePNGFile, "mDoodlePNGFile");
        this.mDoodlePNGFile = mDoodlePNGFile;
    }

    /* JADX WARN: Code duplicated, block: B:132:0x017b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:144:0x024d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x0266 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x019b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:172:? A[Catch: Exception -> 0x017f, all -> 0x0248, SYNTHETIC, TRY_LEAVE, TryCatch #15 {all -> 0x0248, blocks: (B:5:0x002d, B:6:0x0034, B:8:0x003f, B:22:0x00a9, B:27:0x00c6, B:30:0x00cb, B:25:0x00ae, B:99:0x0207, B:66:0x017b, B:73:0x019b, B:77:0x01b6, B:76:0x01a0, B:71:0x0183, B:53:0x013c, B:58:0x0159, B:61:0x015f, B:56:0x0141), top: B:124:0x002d }] */
    /* JADX WARN: Code duplicated, block: B:174:? A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v25, types: [T, java.lang.Object, java.util.zip.ZipEntry] */
    public final String extractPreviewImageToTmp(Context context) throws Throwable {
        ZipInputStream zipInputStream;
        ZipFile zipFile;
        String str;
        ZipInputStream zipInputStream2;
        Throwable th;
        StringBuilder sb;
        Logger logger;
        IOException e;
        Throwable th2;
        String str2 = DocumentWriter.PREVIEW_IMAGE;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            zipFile = new ZipFile(this.mDoodlePNGFile);
            try {
                zipInputStream2 = new ZipInputStream(new FileInputStream(this.mDoodlePNGFile));
                try {
                    try {
                        Ref.ObjectRef objectRef = new Ref.ObjectRef();
                        InputStream inputStream = null;
                        BufferedOutputStream bufferedOutputStream = null;
                        while (true) {
                            ?? nextEntry = zipInputStream2.getNextEntry();
                            Intrinsics.checkNotNull(nextEntry);
                            objectRef.element = nextEntry;
                            if (nextEntry == 0) {
                                str = null;
                                try {
                                    zipInputStream2.close();
                                } catch (IOException e2) {
                                    Logger.INSTANCE.e(TAG, "zin failed.exception:" + e2);
                                }
                                try {
                                    zipFile.close();
                                    break;
                                } catch (IOException e3) {
                                    e = e3;
                                    logger = Logger.INSTANCE;
                                    sb = new StringBuilder("zipFile failed.exception:");
                                    logger.e(TAG, sb.append(e).toString());
                                }
                            } else if (Intrinsics.areEqual(str2, ((ZipEntry) objectRef.element).getName())) {
                                try {
                                    inputStream = zipFile.getInputStream((ZipEntry) objectRef.element);
                                    str = null;
                                    try {
                                        File file = new File(Utils.INSTANCE.getDoodleCacheDir(), new StringBuilder().append(System.currentTimeMillis()).toString());
                                        if (!file.exists()) {
                                            file.mkdirs();
                                        }
                                        File file2 = new File(file, str2);
                                        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file2));
                                        try {
                                            byte[] bArr = new byte[4096];
                                            Ref.IntRef intRef = new Ref.IntRef();
                                            while (true) {
                                                int i = inputStream.read(bArr);
                                                intRef.element = i;
                                                if (i <= 0) {
                                                    break;
                                                }
                                                bufferedOutputStream2.write(bArr, 0, intRef.element);
                                            }
                                            String absolutePath = file2.getAbsolutePath();
                                            try {
                                                bufferedOutputStream2.close();
                                            } catch (IOException e4) {
                                                Logger.INSTANCE.e(TAG, "bufferedOutputStream failed.exception:" + e4);
                                            }
                                            if (inputStream != null) {
                                                try {
                                                    inputStream.close();
                                                } catch (IOException e5) {
                                                    Logger.INSTANCE.e(TAG, "inputStream failed.exception:" + e5);
                                                }
                                            }
                                            try {
                                                zipInputStream2.close();
                                            } catch (IOException e6) {
                                                Logger.INSTANCE.e(TAG, "zin failed.exception:" + e6);
                                            }
                                            try {
                                                zipFile.close();
                                            } catch (IOException e7) {
                                                Logger.INSTANCE.e(TAG, "zipFile failed.exception:" + e7);
                                            }
                                            return absolutePath;
                                        } catch (Exception e8) {
                                            e = e8;
                                            bufferedOutputStream = bufferedOutputStream2;
                                            try {
                                                Logger.INSTANCE.e(TAG, "tmpFile failed.exception:" + e);
                                                if (bufferedOutputStream != null) {
                                                    try {
                                                        try {
                                                            bufferedOutputStream.close();
                                                        } catch (Exception e9) {
                                                            e = e9;
                                                        }
                                                    } catch (IOException e10) {
                                                        Logger.INSTANCE.e(TAG, "bufferedOutputStream failed.exception:" + e10);
                                                    }
                                                }
                                                if (inputStream != null) {
                                                    try {
                                                        inputStream.close();
                                                    } catch (IOException e11) {
                                                        Logger.INSTANCE.e(TAG, "inputStream failed.exception:" + e11);
                                                    }
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                                th2 = th;
                                                if (bufferedOutputStream != null) {
                                                    try {
                                                        bufferedOutputStream.close();
                                                    } catch (IOException e12) {
                                                        Logger.INSTANCE.e(TAG, "bufferedOutputStream failed.exception:" + e12);
                                                    }
                                                }
                                                if (inputStream == null) {
                                                    throw th2;
                                                }
                                                try {
                                                    inputStream.close();
                                                    throw th2;
                                                } catch (IOException e13) {
                                                    Logger.INSTANCE.e(TAG, "inputStream failed.exception:" + e13);
                                                    throw th2;
                                                }
                                            }
                                        } catch (Throwable th4) {
                                            th2 = th4;
                                            bufferedOutputStream = bufferedOutputStream2;
                                            if (bufferedOutputStream != null) {
                                                bufferedOutputStream.close();
                                            }
                                            if (inputStream == null) {
                                                throw th2;
                                            }
                                            inputStream.close();
                                            throw th2;
                                        }
                                    } catch (Exception e14) {
                                        e = e14;
                                    }
                                } catch (Exception e15) {
                                    e = e15;
                                    str = null;
                                } catch (Throwable th5) {
                                    th = th5;
                                    th2 = th;
                                    if (bufferedOutputStream != null) {
                                        bufferedOutputStream.close();
                                    }
                                    if (inputStream == null) {
                                        throw th2;
                                    }
                                    inputStream.close();
                                    throw th2;
                                }
                            }
                            Logger.INSTANCE.e(TAG, "getPreviewBitmap failed.exception:" + e);
                            if (zipInputStream2 != null) {
                                try {
                                    zipInputStream2.close();
                                } catch (IOException e16) {
                                    Logger.INSTANCE.e(TAG, "zin failed.exception:" + e16);
                                }
                            }
                            if (zipFile != null) {
                                try {
                                    zipFile.close();
                                } catch (IOException e17) {
                                    e = e17;
                                    logger = Logger.INSTANCE;
                                    sb = new StringBuilder("zipFile failed.exception:");
                                    logger.e(TAG, sb.append(e).toString());
                                }
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        zipInputStream = zipInputStream2;
                        if (zipInputStream != null) {
                            try {
                                zipInputStream.close();
                            } catch (IOException e18) {
                                Logger.INSTANCE.e(TAG, "zin failed.exception:" + e18);
                            }
                        }
                        if (zipFile != null) {
                            throw th;
                        }
                        try {
                            zipFile.close();
                            throw th;
                        } catch (IOException e19) {
                            Logger.INSTANCE.e(TAG, "zipFile failed.exception:" + e19);
                            throw th;
                        }
                    }
                } catch (Exception e20) {
                    e = e20;
                    str = null;
                }
            } catch (Exception e21) {
                e = e21;
                str = null;
                zipInputStream2 = null;
            } catch (Throwable th7) {
                th = th7;
                zipInputStream = null;
                th = th;
                if (zipInputStream != null) {
                    zipInputStream.close();
                }
                if (zipFile != null) {
                    throw th;
                }
                zipFile.close();
                throw th;
            }
        } catch (Exception e22) {
            e = e22;
            str = null;
            zipFile = null;
            zipInputStream2 = null;
        } catch (Throwable th8) {
            th = th8;
            zipInputStream = null;
            zipFile = null;
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:124:0x01ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:133:0x01d0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:149:0x03c0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:158:? A[Catch: Exception -> 0x03c9, SYNTHETIC, TRY_LEAVE, TryCatch #6 {Exception -> 0x03c9, blocks: (B:13:0x0093, B:22:0x0104, B:44:0x0147, B:46:0x014d, B:47:0x0150, B:50:0x0174, B:108:0x0384, B:25:0x0109, B:113:0x03c0, B:117:0x03c8, B:116:0x03c5, B:43:0x0144), top: B:132:0x0093, inners: #15, #17 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x014d A[Catch: Exception -> 0x03c9, TryCatch #6 {Exception -> 0x03c9, blocks: (B:13:0x0093, B:22:0x0104, B:44:0x0147, B:46:0x014d, B:47:0x0150, B:50:0x0174, B:108:0x0384, B:25:0x0109, B:113:0x03c0, B:117:0x03c8, B:116:0x03c5, B:43:0x0144), top: B:132:0x0093, inners: #15, #17 }] */
    /* JADX WARN: Code duplicated, block: B:50:0x0174 A[Catch: Exception -> 0x03c9, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x03c9, blocks: (B:13:0x0093, B:22:0x0104, B:44:0x0147, B:46:0x014d, B:47:0x0150, B:50:0x0174, B:108:0x0384, B:25:0x0109, B:113:0x03c0, B:117:0x03c8, B:116:0x03c5, B:43:0x0144), top: B:132:0x0093, inners: #15, #17 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x0217  */
    /* JADX WARN: Code duplicated, block: B:70:0x0234 A[Catch: Exception -> 0x0376, TryCatch #9 {Exception -> 0x0376, blocks: (B:68:0x021b, B:70:0x0234, B:71:0x023b, B:73:0x0261), top: B:137:0x021b }] */
    /* JADX WARN: Code duplicated, block: B:73:0x0261 A[Catch: Exception -> 0x0376, TRY_LEAVE, TryCatch #9 {Exception -> 0x0376, blocks: (B:68:0x021b, B:70:0x0234, B:71:0x023b, B:73:0x0261), top: B:137:0x021b }] */
    /* JADX WARN: Code duplicated, block: B:77:0x0286 A[Catch: Exception -> 0x030e, TryCatch #1 {Exception -> 0x030e, blocks: (B:74:0x0267, B:75:0x0280, B:77:0x0286, B:79:0x02a0, B:80:0x02ac), top: B:126:0x0267 }] */
    /* JADX WARN: Code duplicated, block: B:79:0x02a0 A[Catch: Exception -> 0x030e, TryCatch #1 {Exception -> 0x030e, blocks: (B:74:0x0267, B:75:0x0280, B:77:0x0286, B:79:0x02a0, B:80:0x02ac), top: B:126:0x0267 }] */
    /* JADX WARN: Code duplicated, block: B:84:0x02d3 A[Catch: Exception -> 0x0309, TryCatch #5 {Exception -> 0x0309, blocks: (B:82:0x02cd, B:84:0x02d3, B:86:0x02dc, B:87:0x02e5), top: B:130:0x02cd }] */
    /* JADX WARN: Code duplicated, block: B:86:0x02dc A[Catch: Exception -> 0x0309, TryCatch #5 {Exception -> 0x0309, blocks: (B:82:0x02cd, B:84:0x02d3, B:86:0x02dc, B:87:0x02e5), top: B:130:0x02cd }] */
    /* JADX WARN: Code duplicated, block: B:95:0x0330 A[Catch: Exception -> 0x0374, TryCatch #10 {Exception -> 0x0374, blocks: (B:94:0x0311, B:96:0x0356, B:95:0x0330), top: B:139:0x025f }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.miui.doodle.document.DocumentReader] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [int] */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.miui.doodle.base.core.IDoodle] */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    public final DoodleDocument read(Context context, IDoodle doodle, boolean isPreload) throws Throwable {
        DoodleDocument doodleDocument;
        final ZipFile zipFile;
        Bitmap bitmapDecodeStream;
        Bitmap bitmap;
        Bitmap bitmap2;
        ?? r4;
        ?? r1;
        long jCurrentTimeMillis;
        DocumentInfo documentInfoFromJSONObject;
        Bitmap bitmapCreateBitmap;
        ?? r5;
        DoodleDocument doodleDocument2;
        IDoodle iDoodle;
        final Ref.IntRef intRef;
        final Ref.IntRef intRef2;
        Layer jsonReader;
        Throwable th;
        FileOutputStream fileOutputStream;
        Throwable th2;
        final DocumentReader documentReader = this;
        String absolutePath = "0 readDoodleChunk cost ";
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        System.currentTimeMillis();
        int realWidth = DisplayUtils.INSTANCE.getRealWidth(context);
        int realHeight = DisplayUtils.INSTANCE.getRealHeight(context);
        if (RomUtils.INSTANCE.isPadDevice()) {
            if (realWidth < realHeight) {
                realWidth = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_floating_window_fixed_width_minor) - 1;
                realHeight = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_floating_window_fixed_height_minor) - 1;
            } else {
                realHeight = (int) ((((double) realHeight) * 0.8d) - ((double) 1));
                realWidth = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_floating_window_fixed_width_minor) - 1;
            }
        }
        try {
            PNGChunk doodleChunk = new PNGReader(documentReader.mDoodlePNGFile, null, 2, null).readDoodleChunk();
            if (doodleChunk == null) {
                return null;
            }
            doodleDocument = null;
            int i = realWidth;
            try {
                try {
                    Logger.INSTANCE.d(TAG, "0 readDoodleChunk cost " + (System.currentTimeMillis() - jCurrentTimeMillis2));
                    try {
                        long jCurrentTimeMillis3 = System.currentTimeMillis();
                        File doodlePreloadCacheDir = Utils.INSTANCE.getDoodlePreloadCacheDir();
                        if (!doodlePreloadCacheDir.exists()) {
                            doodlePreloadCacheDir.mkdirs();
                        }
                        File file = new File(doodlePreloadCacheDir, System.currentTimeMillis() + AttachmentUtils.DOODLE_SUFFIX);
                        fileOutputStream = new FileOutputStream(file);
                        try {
                            try {
                                fileOutputStream.write(doodleChunk.getChunkData());
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                absolutePath = file.getAbsolutePath();
                                try {
                                    try {
                                        Logger.INSTANCE.d(TAG, "1 toCache cost " + (System.currentTimeMillis() - jCurrentTimeMillis3));
                                        fileOutputStream.close();
                                    } catch (Exception e) {
                                        e = e;
                                        fileOutputStream = fileOutputStream;
                                        try {
                                            Logger.INSTANCE.e(TAG, "extra DoodleFile failed." + e);
                                            if (fileOutputStream != null) {
                                                fileOutputStream.close();
                                            }
                                            if (Build.VERSION.SDK_INT >= 34) {
                                                ZipPathValidator.clearCallback();
                                            }
                                            zipFile = new ZipFile(new File(absolutePath));
                                            bitmapDecodeStream = BitmapFactory.decodeStream(zipFile.getInputStream(new ZipEntry(DocumentWriter.PREVIEW_IMAGE)));
                                            bitmap = documentReader.readBitmap(zipFile, "/background");
                                            if (bitmap == null) {
                                                Logger.INSTANCE.w(TAG, "waring!! the doodle background is null, will create a new bg w = " + context.getResources().getDisplayMetrics().widthPixels + " h " + context.getResources().getDisplayMetrics().heightPixels);
                                                bitmap = Bitmap.createBitmap(i, realHeight, Bitmap.Config.ARGB_8888);
                                                bitmap.eraseColor(0);
                                            }
                                            bitmap2 = bitmap;
                                            jCurrentTimeMillis = System.currentTimeMillis();
                                            try {
                                                documentInfoFromJSONObject = DocumentInfo.INSTANCE.fromJSONObject(new JSONObject(documentReader.readString(zipFile, DocumentWriter.MANIFEST)));
                                                if (documentInfoFromJSONObject.getEditImage()) {
                                                    bitmapCreateBitmap = bitmap2;
                                                } else {
                                                    try {
                                                        if (bitmap2.getWidth() >= i) {
                                                            try {
                                                                if (bitmap2.getHeight() < realHeight) {
                                                                    bitmapCreateBitmap = bitmap2;
                                                                }
                                                            } catch (Exception e2) {
                                                                e = e2;
                                                                bitmap2 = bitmap2;
                                                                r4 = doodle;
                                                                r1 = documentReader;
                                                                Logger.INSTANCE.i(TAG, "DocumentReader.read MANIFEST failed. e " + e);
                                                                DoodleDocument doodleDocument3 = new DoodleDocument(bitmap2.getWidth(), bitmap2.getHeight(), true);
                                                                doodleDocument3.setDocumentInfo(new DocumentInfo());
                                                                doodleDocument3.setBg(bitmap2);
                                                                Intrinsics.checkNotNull(bitmapDecodeStream);
                                                                r1.addLayerToDocumentByBitmap(r4, doodleDocument3, bitmap2, bitmapDecodeStream);
                                                                return doodleDocument3;
                                                            }
                                                        }
                                                        int iCoerceAtLeast = RangesKt.coerceAtLeast(bitmap2.getWidth(), i);
                                                        int iCoerceAtLeast2 = RangesKt.coerceAtLeast(bitmap2.getHeight(), realHeight);
                                                        Logger.INSTANCE.d(TAG, "recreate Bg w " + iCoerceAtLeast + " h " + iCoerceAtLeast2);
                                                        bitmapCreateBitmap = Bitmap.createBitmap(iCoerceAtLeast, iCoerceAtLeast2, Bitmap.Config.ARGB_8888);
                                                    } catch (Exception e3) {
                                                        e = e3;
                                                        bitmap2 = bitmap2;
                                                        r4 = doodle;
                                                        r1 = documentReader;
                                                        Logger.INSTANCE.i(TAG, "DocumentReader.read MANIFEST failed. e " + e);
                                                        DoodleDocument doodleDocument4 = new DoodleDocument(bitmap2.getWidth(), bitmap2.getHeight(), true);
                                                        doodleDocument4.setDocumentInfo(new DocumentInfo());
                                                        doodleDocument4.setBg(bitmap2);
                                                        Intrinsics.checkNotNull(bitmapDecodeStream);
                                                        r1.addLayerToDocumentByBitmap(r4, doodleDocument4, bitmap2, bitmapDecodeStream);
                                                        return doodleDocument4;
                                                    }
                                                }
                                                doodleDocument2 = new DoodleDocument(bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), true);
                                                doodleDocument2.setDocumentInfo(documentInfoFromJSONObject);
                                                doodleDocument2.setBg(bitmapCreateBitmap);
                                                doodleDocument2.setPreviewBitmap(bitmapDecodeStream);
                                                if (isPreload) {
                                                    ((DoodleView) doodle).setDocument(doodleDocument2);
                                                }
                                                Logger.INSTANCE.d(TAG, "2 Preview and manifest cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
                                                long jCurrentTimeMillis4 = System.currentTimeMillis();
                                                r5 = (documentInfoFromJSONObject.getVersion() > 0L ? 1 : (documentInfoFromJSONObject.getVersion() == 0L ? 0 : -1));
                                                try {
                                                    if (r5 <= 0) {
                                                        try {
                                                            JsonObject asJsonObject = JsonParser.parseString(documentReader.readString(zipFile, DocumentWriter.LAYERS_JSON)).getAsJsonObject();
                                                            LayerInfo.Companion companion = LayerInfo.INSTANCE;
                                                            Intrinsics.checkNotNull(asJsonObject);
                                                            documentReader = documentReader;
                                                            for (final LayerInfo.LayerInfoItem layerInfoItem : companion.fromJSONObject(asJsonObject).getLayerInfoItems()) {
                                                                intRef = new Ref.IntRef();
                                                                intRef.element = i;
                                                                intRef2 = new Ref.IntRef();
                                                                intRef2.element = realHeight;
                                                                if (documentInfoFromJSONObject.getEditImage()) {
                                                                    intRef.element = bitmapCreateBitmap.getWidth();
                                                                    intRef2.element = bitmapCreateBitmap.getHeight();
                                                                }
                                                                long jCurrentTimeMillis5 = System.currentTimeMillis();
                                                                String infoFile = layerInfoItem.getInfoFile();
                                                                int i2 = intRef.element;
                                                                int i3 = intRef2.element;
                                                                final DocumentInfo documentInfo = documentInfoFromJSONObject;
                                                                Function2<? super String, ? super Boolean, Bitmap> function2 = new Function2() { // from class: com.miui.doodle.document.DocumentReader$$ExternalSyntheticLambda0
                                                                    @Override // kotlin.jvm.functions.Function2
                                                                    public final Object invoke(Object obj, Object obj2) {
                                                                        return DocumentReader.read$lambda$2(this.f$0, zipFile, layerInfoItem, documentInfo, intRef, intRef2, (String) obj, ((Boolean) obj2).booleanValue());
                                                                    }
                                                                };
                                                                documentReader = this;
                                                                iDoodle = doodle;
                                                                try {
                                                                    jsonReader = documentReader.readJsonReader(zipFile, infoFile, iDoodle, i2, i3, function2);
                                                                    if (jsonReader != null) {
                                                                        doodleDocument2.getLayers().add(jsonReader);
                                                                    }
                                                                    if (jsonReader == null) {
                                                                        Intrinsics.checkNotNull(bitmapCreateBitmap);
                                                                        Intrinsics.checkNotNull(bitmapDecodeStream);
                                                                        documentReader.addLayerToDocumentByBitmap(iDoodle, doodleDocument2, bitmapCreateBitmap, bitmapDecodeStream);
                                                                    }
                                                                    Logger.INSTANCE.d(TAG, " 2-3 read JsonReader cost " + (System.currentTimeMillis() - jCurrentTimeMillis5));
                                                                    documentInfoFromJSONObject = documentInfo;
                                                                    documentReader = documentReader;
                                                                } catch (Exception e4) {
                                                                    e = e4;
                                                                    Logger.INSTANCE.i(TAG, "DocumentReader.read layersJson failed. e " + e);
                                                                    Intrinsics.checkNotNull(bitmapDecodeStream);
                                                                    documentReader.addLayerToDocumentByBitmap(iDoodle, doodleDocument2, bitmapCreateBitmap, bitmapDecodeStream);
                                                                    documentReader = documentReader;
                                                                    r5 = iDoodle;
                                                                    Logger.INSTANCE.d(TAG, "3 addLayers cost " + (System.currentTimeMillis() - jCurrentTimeMillis4));
                                                                    return doodleDocument2;
                                                                }
                                                            }
                                                            r5 = doodle;
                                                            documentReader = documentReader;
                                                        } catch (Exception e5) {
                                                            e = e5;
                                                            iDoodle = doodle;
                                                        }
                                                    } else {
                                                        IDoodle iDoodle2 = doodle;
                                                        Logger.INSTANCE.i(TAG, "document version is " + documentInfoFromJSONObject.getVersion() + ", open with compatible mode");
                                                        Intrinsics.checkNotNull(bitmapDecodeStream);
                                                        documentReader.addLayerToDocumentByBitmap(iDoodle2, doodleDocument2, bitmapCreateBitmap, bitmapDecodeStream);
                                                        documentReader = documentReader;
                                                        r5 = iDoodle2;
                                                    }
                                                    Logger.INSTANCE.d(TAG, "3 addLayers cost " + (System.currentTimeMillis() - jCurrentTimeMillis4));
                                                    return doodleDocument2;
                                                } catch (Exception e6) {
                                                    e = e6;
                                                    bitmap2 = bitmapCreateBitmap;
                                                    r1 = documentReader;
                                                    r4 = r5;
                                                    Logger.INSTANCE.i(TAG, "DocumentReader.read MANIFEST failed. e " + e);
                                                    DoodleDocument doodleDocument5 = new DoodleDocument(bitmap2.getWidth(), bitmap2.getHeight(), true);
                                                    doodleDocument5.setDocumentInfo(new DocumentInfo());
                                                    doodleDocument5.setBg(bitmap2);
                                                    Intrinsics.checkNotNull(bitmapDecodeStream);
                                                    r1.addLayerToDocumentByBitmap(r4, doodleDocument5, bitmap2, bitmapDecodeStream);
                                                    return doodleDocument5;
                                                }
                                            } catch (Exception e7) {
                                                e = e7;
                                                r4 = doodle;
                                                bitmap2 = bitmap2;
                                                r1 = documentReader;
                                            }
                                        } catch (Throwable th3) {
                                            th2 = th3;
                                            th = th2;
                                            if (fileOutputStream == null) {
                                                throw th;
                                            }
                                            try {
                                                fileOutputStream.close();
                                                throw th;
                                            } catch (IOException e8) {
                                                e8.printStackTrace();
                                                throw th;
                                            }
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        fileOutputStream = fileOutputStream;
                                        if (fileOutputStream == null) {
                                            throw th;
                                        }
                                        fileOutputStream.close();
                                        throw th;
                                    }
                                } catch (Exception e9) {
                                    e = e9;
                                }
                            } catch (Throwable th5) {
                                th2 = th5;
                                th = th2;
                                if (fileOutputStream == null) {
                                    throw th;
                                }
                                fileOutputStream.close();
                                throw th;
                            }
                        } catch (Exception e10) {
                            e = e10;
                            absolutePath = null;
                        }
                    } catch (Exception e11) {
                        e = e11;
                        fileOutputStream = null;
                        absolutePath = null;
                    } catch (Throwable th6) {
                        th = th6;
                        fileOutputStream = null;
                    }
                } catch (IOException e12) {
                    e12.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 34) {
                    ZipPathValidator.clearCallback();
                }
                zipFile = new ZipFile(new File(absolutePath));
                bitmapDecodeStream = BitmapFactory.decodeStream(zipFile.getInputStream(new ZipEntry(DocumentWriter.PREVIEW_IMAGE)));
                bitmap = documentReader.readBitmap(zipFile, "/background");
                if (bitmap == null) {
                    Logger.INSTANCE.w(TAG, "waring!! the doodle background is null, will create a new bg w = " + context.getResources().getDisplayMetrics().widthPixels + " h " + context.getResources().getDisplayMetrics().heightPixels);
                    bitmap = Bitmap.createBitmap(i, realHeight, Bitmap.Config.ARGB_8888);
                    bitmap.eraseColor(0);
                }
                bitmap2 = bitmap;
                try {
                    jCurrentTimeMillis = System.currentTimeMillis();
                    documentInfoFromJSONObject = DocumentInfo.INSTANCE.fromJSONObject(new JSONObject(documentReader.readString(zipFile, DocumentWriter.MANIFEST)));
                    if (documentInfoFromJSONObject.getEditImage()) {
                        if (bitmap2.getWidth() >= i) {
                            if (bitmap2.getHeight() < realHeight) {
                                bitmapCreateBitmap = bitmap2;
                            }
                        }
                        int iCoerceAtLeast3 = RangesKt.coerceAtLeast(bitmap2.getWidth(), i);
                        int iCoerceAtLeast4 = RangesKt.coerceAtLeast(bitmap2.getHeight(), realHeight);
                        Logger.INSTANCE.d(TAG, "recreate Bg w " + iCoerceAtLeast3 + " h " + iCoerceAtLeast4);
                        bitmapCreateBitmap = Bitmap.createBitmap(iCoerceAtLeast3, iCoerceAtLeast4, Bitmap.Config.ARGB_8888);
                    } else {
                        bitmapCreateBitmap = bitmap2;
                    }
                    try {
                        doodleDocument2 = new DoodleDocument(bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), true);
                        doodleDocument2.setDocumentInfo(documentInfoFromJSONObject);
                        doodleDocument2.setBg(bitmapCreateBitmap);
                        doodleDocument2.setPreviewBitmap(bitmapDecodeStream);
                        if (isPreload) {
                            ((DoodleView) doodle).setDocument(doodleDocument2);
                        }
                        Logger.INSTANCE.d(TAG, "2 Preview and manifest cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
                        long jCurrentTimeMillis6 = System.currentTimeMillis();
                        r5 = (documentInfoFromJSONObject.getVersion() > 0L ? 1 : (documentInfoFromJSONObject.getVersion() == 0L ? 0 : -1));
                        if (r5 <= 0) {
                            JsonObject asJsonObject2 = JsonParser.parseString(documentReader.readString(zipFile, DocumentWriter.LAYERS_JSON)).getAsJsonObject();
                            LayerInfo.Companion companion2 = LayerInfo.INSTANCE;
                            Intrinsics.checkNotNull(asJsonObject2);
                            documentReader = documentReader;
                            while (r16.hasNext()) {
                                intRef = new Ref.IntRef();
                                intRef.element = i;
                                intRef2 = new Ref.IntRef();
                                intRef2.element = realHeight;
                                if (documentInfoFromJSONObject.getEditImage()) {
                                    intRef.element = bitmapCreateBitmap.getWidth();
                                    intRef2.element = bitmapCreateBitmap.getHeight();
                                }
                                long jCurrentTimeMillis7 = System.currentTimeMillis();
                                String infoFile2 = layerInfoItem.getInfoFile();
                                int i4 = intRef.element;
                                int i5 = intRef2.element;
                                final DocumentInfo documentInfo2 = documentInfoFromJSONObject;
                                Function2<? super String, ? super Boolean, Bitmap> function3 = new Function2() { // from class: com.miui.doodle.document.DocumentReader$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        return DocumentReader.read$lambda$2(this.f$0, zipFile, layerInfoItem, documentInfo2, intRef, intRef2, (String) obj, ((Boolean) obj2).booleanValue());
                                    }
                                };
                                documentReader = this;
                                iDoodle = doodle;
                                jsonReader = documentReader.readJsonReader(zipFile, infoFile2, iDoodle, i4, i5, function3);
                                if (jsonReader != null) {
                                    doodleDocument2.getLayers().add(jsonReader);
                                }
                                if (jsonReader == null) {
                                    Intrinsics.checkNotNull(bitmapCreateBitmap);
                                    Intrinsics.checkNotNull(bitmapDecodeStream);
                                    documentReader.addLayerToDocumentByBitmap(iDoodle, doodleDocument2, bitmapCreateBitmap, bitmapDecodeStream);
                                }
                                Logger.INSTANCE.d(TAG, " 2-3 read JsonReader cost " + (System.currentTimeMillis() - jCurrentTimeMillis7));
                                documentInfoFromJSONObject = documentInfo2;
                                documentReader = documentReader;
                            }
                            r5 = doodle;
                            documentReader = documentReader;
                        } else {
                            IDoodle iDoodle3 = doodle;
                            Logger.INSTANCE.i(TAG, "document version is " + documentInfoFromJSONObject.getVersion() + ", open with compatible mode");
                            Intrinsics.checkNotNull(bitmapDecodeStream);
                            documentReader.addLayerToDocumentByBitmap(iDoodle3, doodleDocument2, bitmapCreateBitmap, bitmapDecodeStream);
                            documentReader = documentReader;
                            r5 = iDoodle3;
                        }
                        Logger.INSTANCE.d(TAG, "3 addLayers cost " + (System.currentTimeMillis() - jCurrentTimeMillis6));
                        return doodleDocument2;
                    } catch (Exception e13) {
                        e = e13;
                        r5 = doodle;
                    }
                } catch (Exception e14) {
                    e = e14;
                }
            } catch (Exception e15) {
                e = e15;
            }
        } catch (Exception e16) {
            e = e16;
            doodleDocument = null;
        }
        Logger.INSTANCE.e(TAG, "DocumentReader.read failed. " + e);
        return doodleDocument;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Bitmap read$lambda$2(DocumentReader documentReader, ZipFile zipFile, LayerInfo.LayerInfoItem layerInfoItem, DocumentInfo documentInfo, Ref.IntRef intRef, Ref.IntRef intRef2, String bitmapId, boolean z) throws IOException {
        Intrinsics.checkNotNullParameter(bitmapId, "bitmapId");
        Bitmap bitmap = documentReader.readBitmap(zipFile, layerInfoItem.getBasePath() + InternalZipConstants.ZIP_FILE_SEPARATOR + bitmapId);
        if (bitmap != null) {
            return (documentInfo.getEditImage() || z) ? bitmap : BitmapDecoder.INSTANCE.fitBitmapToDisplay(bitmap, intRef.element, intRef2.element);
        }
        return Bitmap.createBitmap(intRef.element, intRef2.element, Bitmap.Config.ARGB_8888);
    }

    private final void addLayerToDocumentByBitmap(IDoodle doodle, DoodleDocument doodleDocument, Bitmap background, Bitmap bitmap) {
        int width = background.getWidth();
        Intrinsics.checkNotNull(background);
        Layer layer = new Layer(width, background.getHeight(), null, null, 0, 0, 0, 0, false, false, 1020, null);
        doodleDocument.getLayers().add(layer);
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new AnonymousClass1(doodle, doodleDocument, bitmap, layer, null), 2, null);
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.DocumentReader$addLayerToDocumentByBitmap$1, reason: invalid class name */
    /* JADX INFO: compiled from: DocumentReader.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.document.DocumentReader$addLayerToDocumentByBitmap$1", f = "DocumentReader.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Bitmap $bitmap;
        final /* synthetic */ IDoodle $doodle;
        final /* synthetic */ DoodleDocument $doodleDocument;
        final /* synthetic */ Layer $layer;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(IDoodle iDoodle, DoodleDocument doodleDocument, Bitmap bitmap, Layer layer, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$doodle = iDoodle;
            this.$doodleDocument = doodleDocument;
            this.$bitmap = bitmap;
            this.$layer = layer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$doodle, this.$doodleDocument, this.$bitmap, this.$layer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            IDoodle iDoodle = this.$doodle;
            Intrinsics.checkNotNull(iDoodle, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleView");
            ((DoodleView) iDoodle).setDocument(this.$doodleDocument);
            Bitmap.Config config = this.$bitmap.getConfig();
            Bitmap bitmapCopy = config != null ? this.$bitmap.copy(config, true) : null;
            LayerBitmap layerBitmap = new LayerBitmap(null, bitmapCopy, bitmapCopy != null ? new Canvas(bitmapCopy) : null, 2, new ArrayList(), 1, null);
            DoodleBitmap doodleBitmap = new DoodleBitmap(this.$doodle, bitmapCopy, null, 0.0f, 0.0f, null, 0 == true ? 1 : 0, 0, R2.attr.actionBarEditIcon, null);
            doodleBitmap.setItemScale(1.0f);
            layerBitmap.getItems().add(doodleBitmap);
            this.$layer.getLayerBitmapList().add(layerBitmap);
            this.$layer.addItem(doodleBitmap);
            return Unit.INSTANCE;
        }
    }

    private final Bitmap readBitmap(ZipFile zipFile, String zipEntryName) throws IOException {
        InputStream inputStream = zipFile.getInputStream(new ZipEntry(zipEntryName));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public final String readString(ZipFile zipFile, String zipEntryName) throws IOException {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(new ZipEntry(zipEntryName))));
        StringBuilder sb = new StringBuilder();
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                sb.append(line).append(StringUtils.LF);
            } else {
                Logger.INSTANCE.d(TAG, "readString string append in cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
                bufferedReader.close();
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        }
    }

    public final Layer readJsonReader(ZipFile zipFile, String zipEntryName, IDoodle doodle, int layerWidth, int layerHeight, Function2<? super String, ? super Boolean, Bitmap> readBitmapFunc) throws IOException {
        Layer layerFromJSONReader;
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Intrinsics.checkNotNullParameter(doodle, "doodle");
        Intrinsics.checkNotNullParameter(readBitmapFunc, "readBitmapFunc");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(new ZipEntry(zipEntryName))));
        JsonReader jsonReader = new JsonReader(bufferedReader);
        try {
            layerFromJSONReader = Layer.INSTANCE.fromJSONReader(doodle, jsonReader, layerWidth, layerHeight, readBitmapFunc);
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Read Layer JsonReader Error " + e + ", Rebuild by PreviewBitmap");
            layerFromJSONReader = null;
        }
        jsonReader.close();
        bufferedReader.close();
        return layerFromJSONReader;
    }

    /* JADX INFO: compiled from: DocumentReader.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/miui/doodle/document/DocumentReader$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "isDoodleFile", "", "filepath", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isDoodleFile(String filepath) {
            try {
                return new PNGReader(new File(filepath), null, 2, null).hasDoodleChunk();
            } catch (Exception e) {
                Logger.INSTANCE.e(DocumentReader.TAG, "isDoodleFile failed. " + e);
                return false;
            }
        }
    }
}
