package ch.qos.logback.core.util;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.spi.ContextAwareBase;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes.dex */
public class FileUtil extends ContextAwareBase {
    static final int BUF_SIZE = 32768;

    public FileUtil(Context context) {
        setContext(context);
    }

    public static boolean createMissingParentDirectories(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return true;
        }
        parentFile.mkdirs();
        return parentFile.exists();
    }

    public static URL fileToURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unexpected exception on file [" + file + "]", e);
        }
    }

    public static String prefixRelativePath(String str, String str2) {
        return (str == null || OptionHelper.isEmpty(str.trim()) || new File(str2).isAbsolute()) ? str2 : str + InternalZipConstants.ZIP_FILE_SEPARATOR + str2;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:52:? A[SYNTHETIC] */
    public void copy(String str, String str2) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        IOException e;
        IOException iOException;
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(str));
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
                    try {
                        byte[] bArr = new byte[32768];
                        while (true) {
                            int i = bufferedInputStream2.read(bArr);
                            if (i == -1) {
                                bufferedInputStream2.close();
                                try {
                                    bufferedOutputStream.close();
                                    return;
                                } catch (IOException e2) {
                                    iOException = e2;
                                    e = iOException;
                                    String str3 = "Failed to copy [" + str + "] to [" + str2 + "]";
                                    addError(str3, e);
                                    throw new RolloverFailure(str3);
                                } catch (Throwable th) {
                                    th = th;
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    if (bufferedOutputStream != null) {
                                        throw th;
                                    }
                                    try {
                                        bufferedOutputStream.close();
                                        throw th;
                                    } catch (IOException unused2) {
                                        throw th;
                                    }
                                }
                            }
                            bufferedOutputStream.write(bArr, 0, i);
                        }
                    } catch (IOException e3) {
                        e = e3;
                        String str4 = "Failed to copy [" + str + "] to [" + str2 + "]";
                        addError(str4, e);
                        throw new RolloverFailure(str4);
                    }
                } catch (IOException e4) {
                    iOException = e4;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream = null;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (bufferedOutputStream != null) {
                        throw th;
                    }
                    bufferedOutputStream.close();
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }
}
