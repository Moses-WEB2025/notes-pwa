package com.hiero.logger.base.logger.utils;

import android.util.Log;
import com.hiero.logger.base.logger.Constants;
import com.hiero.logger.base.logger.core.LoggerUtils;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J0\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0011J*\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0002J(\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u0005J$\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u001eJ\u001e\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\fJ&\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u001c2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020#R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/hiero/logger/base/logger/utils/FileLogHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "dateFormat", "Ljava/text/SimpleDateFormat;", "shirkLogWithTimeRolling", "", "dir", "logFile", "Ljava/io/File;", "totalSize", "", "maxFileSize", "cutMode", "Lcom/hiero/logger/base/logger/utils/CutMode;", "shrinkFile", "file", "zip", "trimLogDir", "logDir", "maxCap", "pattern", "delimiters", "write2File", "buffer", "Ljava/nio/CharBuffer;", "onFileWriteFinished", "Lkotlin/Function0;", "zipFile", "source", "size", "write2Buffer", "", "byteBuffer", "tempBuff", "charArray", "", "writeImmediately", "logger_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FileLogHelper {
    private static final String TAG = "FileUtils";
    public static final FileLogHelper INSTANCE = new FileLogHelper();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH_mm_ss", Locale.CHINA);

    /* JADX INFO: compiled from: r8-map-id-fa599daeed02c8eb29951c75a413bf061de198422255035e71383705a9bcdd94 */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CutMode.values().length];
            try {
                iArr[CutMode.STRICT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CutMode.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FileLogHelper() {
    }

    public static /* synthetic */ void shirkLogWithTimeRolling$default(FileLogHelper fileLogHelper, String str, File file, int i, int i2, CutMode cutMode, int i3, Object obj) {
        if ((i3 & 16) != 0) {
            cutMode = CutMode.STRICT;
        }
        fileLogHelper.shirkLogWithTimeRolling(str, file, i, i2, cutMode);
    }

    private final void shrinkFile(File file, final File zip, int maxFileSize, CutMode cutMode) {
        Log.e(TAG, "[shrinkFile()]shrink file=" + file.getName() + ",len=" + maxFileSize + ", cutMode=" + cutMode);
        try {
            long length = file.length();
            final File file2 = new File(file.getParent(), "_" + file.getName());
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
            int i = WhenMappings.$EnumSwitchMapping$0[cutMode.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                file.renameTo(file2);
                ThreadsKt.thread$default(false, false, null, null, 0, new Function0() { // from class: com.hiero.logger.base.logger.utils.FileLogHelper$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return FileLogHelper.shrinkFile$lambda$2(file2, zip);
                    }
                }, 31, null);
                if (file.exists()) {
                    return;
                }
                file.createNewFile();
                return;
            }
            zipFile(file, maxFileSize, zip);
            FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            long j = maxFileSize;
            channel.transferTo(j, length - j, fileOutputStream.getChannel());
            channel.close();
            fileInputStream.close();
            fileOutputStream.close();
            file.delete();
            file2.renameTo(new File(file.getParent(), file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtils.INSTANCE.i(Constants.MODULE_TAG, TAG, "[shrinkFile()]error=" + e.getMessage());
        }
    }

    public static /* synthetic */ void shrinkFile$default(FileLogHelper fileLogHelper, File file, File file2, int i, CutMode cutMode, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            cutMode = CutMode.STRICT;
        }
        fileLogHelper.shrinkFile(file, file2, i, cutMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit shrinkFile$lambda$2(File file, File file2) throws IOException {
        INSTANCE.zipFile(file, (int) file.length(), file2);
        file.delete();
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void trimLogDir$default(FileLogHelper fileLogHelper, String str, int i, String str2, String str3, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            str3 = "-";
        }
        fileLogHelper.trimLogDir(str, i, str2, str3);
    }

    public final void shirkLogWithTimeRolling(String dir, File logFile, int totalSize, int maxFileSize, CutMode cutMode) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        Intrinsics.checkNotNullParameter(logFile, "logFile");
        Intrinsics.checkNotNullParameter(cutMode, "cutMode");
        trimLogDir$default(this, dir, totalSize, "-log.zip", null, 8, null);
        String str = dateFormat.format(new Date()) + "-" + System.currentTimeMillis() + "-log.zip";
        LoggerUtils.INSTANCE.i(Constants.MODULE_TAG, TAG, "[shirkLogWithTimeRolling()]shrink file=" + logFile.getName() + ",len=" + logFile.length() + ",totalCap:" + totalSize);
        try {
            File file = new File(dir, str);
            file.createNewFile();
            long length = logFile.length();
            if (length - ((long) maxFileSize) > 0) {
                shrinkFile(logFile, file, maxFileSize, cutMode);
            } else {
                zipFile(logFile, maxFileSize, file);
                Log.e(TAG, "invalid para " + length + " vs " + maxFileSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtils.INSTANCE.i(Constants.MODULE_TAG, TAG, "[shirkLogWithTimeRolling()]" + str + ", error=" + e.getMessage());
        }
    }

    public final synchronized void trimLogDir(String logDir, int maxCap, String pattern, String delimiters) {
        long j;
        long length;
        Intrinsics.checkNotNullParameter(logDir, "logDir");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        Log.e(Constants.MODULE_TAG, "trimDir " + logDir + " with max cap:" + maxCap);
        HashMap map = new HashMap();
        File[] fileArrListFiles = new File(logDir).listFiles();
        int i = 0;
        if (fileArrListFiles != null) {
            length = 0;
            for (File file : fileArrListFiles) {
                String name = file.getName();
                Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                if (StringsKt.endsWith$default(name, pattern, false, 2, (Object) null)) {
                    length = file.length() + length;
                    String name2 = file.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    map.put(Long.valueOf(Long.parseLong((String) StringsKt.split$default((CharSequence) StringsKt.substringBefore$default(name2, pattern, (String) null, 2, (Object) null), new String[]{delimiters}, false, 0, 6, (Object) null).get(1))), file);
                }
            }
            j = 0;
        } else {
            j = 0;
            length = 0;
        }
        long length2 = ((long) maxCap) - length;
        Set setKeySet = map.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        List listSorted = CollectionsKt.sorted(setKeySet);
        while (length2 < j && i < map.size()) {
            Object objRemove = map.remove(listSorted.get(i));
            Intrinsics.checkNotNull(objRemove);
            File file2 = (File) objRemove;
            length2 += file2.length();
            file2.delete();
            i++;
            Log.e(Constants.MODULE_TAG, "space(" + length2 + ") vs logCap(" + length + "), deleted old log -> " + file2.getName() + ", maxCap: " + maxCap);
        }
    }

    public final boolean write2Buffer(CharBuffer byteBuffer, CharBuffer tempBuff, char[] charArray, boolean writeImmediately) {
        Intrinsics.checkNotNullParameter(byteBuffer, "byteBuffer");
        Intrinsics.checkNotNullParameter(tempBuff, "tempBuff");
        Intrinsics.checkNotNullParameter(charArray, "charArray");
        int length = charArray.length;
        synchronized (byteBuffer) {
            int iCapacity = byteBuffer.capacity();
            int iPosition = byteBuffer.position();
            if (byteBuffer.position() + length <= iCapacity) {
                byteBuffer.put(charArray);
                if (!writeImmediately) {
                    return true;
                }
                Unit unit = Unit.INSTANCE;
            } else if (iPosition < iCapacity) {
                int i = iCapacity - iPosition;
                int i2 = length - i;
                Log.d(Constants.MODULE_TAG, "put partly string to backup buff,bufferCapacity=" + iCapacity + "(" + byteBuffer.capacity() + "),bufferPos=" + iPosition + "(" + byteBuffer.position() + "),writePos=" + i + "(" + byteBuffer.remaining() + "),charArraySize=" + length + ",tid " + Thread.currentThread().getId());
                byteBuffer.put(charArray, 0, i);
                synchronized (tempBuff) {
                    if (tempBuff.position() + i2 < tempBuff.capacity()) {
                        tempBuff.put(charArray, i, i2);
                        Log.d(Constants.MODULE_TAG, "put partly string to backup buff,bufferCapacity=" + iCapacity + ",bufferPos=" + iPosition + ",tid " + Thread.currentThread().getId());
                    } else {
                        Log.e(Constants.MODULE_TAG, "put partly string to backup buff failed,abort this log !!!tid " + Thread.currentThread().getId());
                    }
                }
            } else {
                synchronized (tempBuff) {
                    if (tempBuff.position() + length <= tempBuff.capacity()) {
                        tempBuff.put(charArray);
                        Log.e(Constants.MODULE_TAG, "put full string to backup buff,tid " + Thread.currentThread().getId());
                    } else {
                        Log.e(Constants.MODULE_TAG, "put full string to backup buff failed,abort this log !!!tid " + Thread.currentThread().getId());
                    }
                }
            }
            return false;
        }
    }

    public final void write2File(File file, CharBuffer buffer, Function0<Unit> onFileWriteFinished) {
        Throwable th;
        FileWriter fileWriter;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        Intrinsics.checkNotNullParameter(onFileWriteFinished, "onFileWriteFinished");
        synchronized (buffer) {
            if (file.exists()) {
                LoggerUtils loggerUtils = LoggerUtils.INSTANCE;
                loggerUtils.d(Constants.MODULE_TAG, TAG, StringUtils.SPACE + file.getName() + "is going to FIO,tid" + Thread.currentThread().getId() + ",buffPos " + buffer.position() + StringUtils.SPACE);
                FileWriter fileWriter2 = null;
                loggerUtils.d(Constants.MODULE_TAG, TAG, file.getName() + "  FIO start with thread id:" + Thread.currentThread().getId());
                fileWriter = new FileWriter(file, true);
                fileWriter.write(buffer.array(), 0, buffer.position());
                fileWriter.flush();
                LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, " FIO write finished with thread id " + Thread.currentThread().getId());
                onFileWriteFinished.invoke();
                fileWriter.close();
                buffer.clear();
                Unit unit = Unit.INSTANCE;
            } else {
                try {
                    file.createNewFile();
                    file.setWritable(true);
                } catch (IOException unused) {
                    LoggerUtils.INSTANCE.i(Constants.MODULE_TAG, TAG, "----create logfile Exception " + file.getName());
                }
                LoggerUtils loggerUtils2 = LoggerUtils.INSTANCE;
                loggerUtils2.d(Constants.MODULE_TAG, TAG, StringUtils.SPACE + file.getName() + "is going to FIO,tid" + Thread.currentThread().getId() + ",buffPos " + buffer.position() + StringUtils.SPACE);
                FileWriter fileWriter3 = null;
                try {
                    try {
                        loggerUtils2.d(Constants.MODULE_TAG, TAG, file.getName() + "  FIO start with thread id:" + Thread.currentThread().getId());
                        try {
                            fileWriter = new FileWriter(file, true);
                            try {
                                try {
                                    fileWriter.write(buffer.array(), 0, buffer.position());
                                } catch (Exception e) {
                                    e = e;
                                    e.printStackTrace();
                                    LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, "Exception:" + e);
                                    throw e;
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileWriter3 = fileWriter;
                                    if (fileWriter3 != null) {
                                        try {
                                            fileWriter3.close();
                                        } catch (IOException e2) {
                                            LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, "Exception:" + e2);
                                        }
                                    }
                                    buffer.clear();
                                    throw th;
                                }
                            } catch (IOException e3) {
                                LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, " FIO write failed with " + e3.getMessage() + " with thread id " + Thread.currentThread().getId());
                            }
                            fileWriter.flush();
                            LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, " FIO write finished with thread id " + Thread.currentThread().getId());
                            onFileWriteFinished.invoke();
                            try {
                                fileWriter.close();
                            } catch (IOException e4) {
                                LoggerUtils.INSTANCE.d(Constants.MODULE_TAG, TAG, "Exception:" + e4);
                            }
                            buffer.clear();
                            Unit unit2 = Unit.INSTANCE;
                        } catch (FileNotFoundException e5) {
                            e5.printStackTrace();
                            Log.e(Constants.MODULE_TAG, "file not found " + e5);
                            buffer.clear();
                            return;
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            throw th;
        }
    }

    public final void zipFile(File source, int size, File zip) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(zip, "zip");
        Log.e(Constants.MODULE_TAG, "zipFile start," + source.getPath() + "->" + zip.getPath());
        long jCurrentTimeMillis = System.currentTimeMillis();
        FileOutputStream fileOutputStream = new FileOutputStream(zip);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        zipOutputStream.setLevel(9);
        zipOutputStream.putNextEntry(new ZipEntry(source.getName()));
        FileInputStream fileInputStream = new FileInputStream(source);
        fileInputStream.getChannel().transferTo(0L, size, Channels.newChannel(zipOutputStream));
        zipOutputStream.finish();
        zipOutputStream.closeEntry();
        fileInputStream.close();
        fileOutputStream.close();
        zipOutputStream.close();
        Log.e(Constants.MODULE_TAG, "zipFile " + source + " finished->" + zip.getPath() + " with size " + zip.length() + ",with ts cost:" + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms,tid: " + Thread.currentThread().getId());
    }
}
