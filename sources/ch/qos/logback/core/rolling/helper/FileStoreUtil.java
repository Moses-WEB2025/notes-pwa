package ch.qos.logback.core.rolling.helper;

import ch.qos.logback.core.rolling.RolloverFailure;
import java.io.File;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class FileStoreUtil {
    static final String FILES_CLASS_STR = "java.nio.file.Files";
    static final String PATH_CLASS_STR = "java.nio.file.Path";

    public static boolean areOnSameFileStore(File file, File file2) throws RolloverFailure {
        if (!file.exists()) {
            throw new IllegalArgumentException("File [" + file + "] does not exist.");
        }
        if (!file2.exists()) {
            throw new IllegalArgumentException("File [" + file2 + "] does not exist.");
        }
        try {
            Class<?> cls = Class.forName(PATH_CLASS_STR);
            Class<?> cls2 = Class.forName(FILES_CLASS_STR);
            Method method = File.class.getMethod("toPath", new Class[0]);
            Method method2 = cls2.getMethod("getFileStore", cls);
            return method2.invoke(null, method.invoke(file, new Object[0])).equals(method2.invoke(null, method.invoke(file2, new Object[0])));
        } catch (Exception e) {
            throw new RolloverFailure("Failed to check file store equality for [" + file + "] and [" + file2 + "]", e);
        }
    }
}
