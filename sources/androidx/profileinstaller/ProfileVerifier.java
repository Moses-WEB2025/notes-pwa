package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.Build;
import androidx.concurrent.futures.ResolvableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ProfileVerifier {
    private static final String CUR_PROFILES_BASE_DIR = "/data/misc/profiles/cur/0/";
    private static final String PROFILE_FILE_NAME = "primary.prof";
    private static final String PROFILE_INSTALLED_CACHE_FILE_NAME = "profileInstalled";
    private static final String REF_PROFILES_BASE_DIR = "/data/misc/profiles/ref/";
    private static final String TAG = "ProfileVerifier";
    private static final ResolvableFuture<CompilationStatus> sFuture = ResolvableFuture.create();
    private static final Object SYNC_OBJ = new Object();
    private static CompilationStatus sCompilationStatus = null;

    private ProfileVerifier() {
    }

    public static CompilationStatus writeProfileVerification(Context context) {
        return writeProfileVerification(context, false);
    }

    /* JADX WARN: Code duplicated, block: B:105:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:110:0x00fe A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    /* JADX WARN: Code duplicated, block: B:19:0x002a  */
    /* JADX WARN: Code duplicated, block: B:21:0x002d A[Catch: all -> 0x003e, IOException -> 0x0041, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0041, blocks: (B:14:0x0016, B:21:0x002d, B:30:0x003d, B:29:0x003a), top: B:107:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0048 A[Catch: all -> 0x003e, TryCatch #6 {, blocks: (B:9:0x000c, B:11:0x0010, B:14:0x0016, B:21:0x002d, B:34:0x0042, B:36:0x0048, B:37:0x004e, B:39:0x0050, B:45:0x0074, B:51:0x0098, B:52:0x009c, B:54:0x00ae, B:63:0x00bf, B:65:0x00c5, B:68:0x00ca, B:80:0x00e2, B:83:0x00e8, B:86:0x00ef, B:88:0x00f8, B:93:0x0104, B:94:0x0108, B:90:0x00fe, B:57:0x00b5, B:58:0x00b9, B:96:0x010a, B:97:0x0110, B:30:0x003d, B:29:0x003a), top: B:112:0x000c, inners: #0, #2 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0050 A[Catch: all -> 0x003e, TryCatch #6 {, blocks: (B:9:0x000c, B:11:0x0010, B:14:0x0016, B:21:0x002d, B:34:0x0042, B:36:0x0048, B:37:0x004e, B:39:0x0050, B:45:0x0074, B:51:0x0098, B:52:0x009c, B:54:0x00ae, B:63:0x00bf, B:65:0x00c5, B:68:0x00ca, B:80:0x00e2, B:83:0x00e8, B:86:0x00ef, B:88:0x00f8, B:93:0x0104, B:94:0x0108, B:90:0x00fe, B:57:0x00b5, B:58:0x00b9, B:96:0x010a, B:97:0x0110, B:30:0x003d, B:29:0x003a), top: B:112:0x000c, inners: #0, #2 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x0073  */
    /* JADX WARN: Code duplicated, block: B:50:0x0097  */
    /* JADX WARN: Code duplicated, block: B:60:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:69:0x00cd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:70:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:71:0x00d2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:72:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:73:0x00d6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:74:0x00d8  */
    static CompilationStatus writeProfileVerification(Context context, boolean z) {
        int i;
        boolean z2;
        File file;
        boolean z3;
        File file2;
        long length;
        boolean z4;
        File file3;
        Cache fromFile;
        Cache cache;
        AssetFileDescriptor assetFileDescriptorOpenFd;
        CompilationStatus compilationStatus;
        if (!z && (compilationStatus = sCompilationStatus) != null) {
            return compilationStatus;
        }
        synchronized (SYNC_OBJ) {
            if (!z) {
                CompilationStatus compilationStatus2 = sCompilationStatus;
                if (compilationStatus2 != null) {
                    return compilationStatus2;
                }
                i = 0;
                try {
                    assetFileDescriptorOpenFd = context.getAssets().openFd("dexopt/baseline.prof");
                    try {
                        if (assetFileDescriptorOpenFd.getLength() > 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (assetFileDescriptorOpenFd != null) {
                            assetFileDescriptorOpenFd.close();
                        }
                        if (Build.VERSION.SDK_INT == 30) {
                            return setCompilationStatus(262144, false, false, z2);
                        }
                        file = new File(new File(REF_PROFILES_BASE_DIR, context.getPackageName()), PROFILE_FILE_NAME);
                        long length2 = file.length();
                        if (file.exists() || length2 <= 0) {
                            z3 = false;
                        } else {
                            z3 = true;
                        }
                        file2 = new File(new File(CUR_PROFILES_BASE_DIR, context.getPackageName()), PROFILE_FILE_NAME);
                        length = file2.length();
                        if (file2.exists() || length <= 0) {
                            z4 = false;
                        } else {
                            z4 = true;
                        }
                        try {
                            long packageLastUpdateTime = getPackageLastUpdateTime(context);
                            file3 = new File(context.getFilesDir(), PROFILE_INSTALLED_CACHE_FILE_NAME);
                            if (file3.exists()) {
                                try {
                                    fromFile = Cache.readFromFile(file3);
                                } catch (IOException unused) {
                                    return setCompilationStatus(131072, z3, z4, z2);
                                }
                            } else {
                                fromFile = null;
                            }
                            if (fromFile == null && fromFile.mPackageLastUpdateTime == packageLastUpdateTime && fromFile.mResultCode != 2) {
                                i = fromFile.mResultCode;
                            } else if (!z2) {
                                i = 327680;
                            } else if (z3) {
                                i = 1;
                            } else if (z4) {
                                i = 2;
                            }
                            if (z && z4 && i != 1) {
                                i = 2;
                            }
                            if (fromFile != null && fromFile.mResultCode == 2 && i == 1 && length2 < fromFile.mInstalledCurrentProfileSize) {
                                i = 3;
                            }
                            int i2 = i;
                            cache = new Cache(1, i2, packageLastUpdateTime, length);
                            if (fromFile != null || !fromFile.equals(cache)) {
                                try {
                                    cache.writeOnFile(file3);
                                } catch (IOException unused2) {
                                    i2 = 196608;
                                }
                            }
                            return setCompilationStatus(i2, z3, z4, z2);
                        } catch (PackageManager.NameNotFoundException unused3) {
                            return setCompilationStatus(65536, z3, z4, z2);
                        }
                    } catch (Throwable th) {
                        if (assetFileDescriptorOpenFd == null) {
                            throw th;
                        }
                        try {
                            assetFileDescriptorOpenFd.close();
                            throw th;
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                            throw th;
                        }
                    }
                } catch (IOException unused4) {
                    z2 = false;
                }
            } else {
                i = 0;
                assetFileDescriptorOpenFd = context.getAssets().openFd("dexopt/baseline.prof");
                if (assetFileDescriptorOpenFd.getLength() > 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (assetFileDescriptorOpenFd != null) {
                    assetFileDescriptorOpenFd.close();
                }
                if (Build.VERSION.SDK_INT == 30) {
                    return setCompilationStatus(262144, false, false, z2);
                }
                file = new File(new File(REF_PROFILES_BASE_DIR, context.getPackageName()), PROFILE_FILE_NAME);
                long length3 = file.length();
                if (file.exists()) {
                    z3 = false;
                } else {
                    z3 = false;
                }
                file2 = new File(new File(CUR_PROFILES_BASE_DIR, context.getPackageName()), PROFILE_FILE_NAME);
                length = file2.length();
                if (file2.exists()) {
                    z4 = false;
                } else {
                    z4 = false;
                }
                long packageLastUpdateTime2 = getPackageLastUpdateTime(context);
                file3 = new File(context.getFilesDir(), PROFILE_INSTALLED_CACHE_FILE_NAME);
                if (file3.exists()) {
                    fromFile = Cache.readFromFile(file3);
                } else {
                    fromFile = null;
                }
                if (fromFile == null) {
                    if (!z2) {
                        i = 327680;
                    } else if (z3) {
                        i = 1;
                    } else if (z4) {
                        i = 2;
                    }
                } else if (!z2) {
                    i = 327680;
                } else if (z3) {
                    i = 1;
                } else if (z4) {
                    i = 2;
                }
                if (z) {
                    i = 2;
                }
                if (fromFile != null) {
                    i = 3;
                }
                int i3 = i;
                cache = new Cache(1, i3, packageLastUpdateTime2, length);
                if (fromFile != null) {
                    cache.writeOnFile(file3);
                } else {
                    cache.writeOnFile(file3);
                }
                return setCompilationStatus(i3, z3, z4, z2);
            }
            throw th;
        }
    }

    private static CompilationStatus setCompilationStatus(int i, boolean z, boolean z2, boolean z3) {
        CompilationStatus compilationStatus = new CompilationStatus(i, z, z2, z3);
        sCompilationStatus = compilationStatus;
        sFuture.set(compilationStatus);
        return sCompilationStatus;
    }

    private static long getPackageLastUpdateTime(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (Build.VERSION.SDK_INT >= 33) {
            return Api33Impl.getPackageInfo(packageManager, context).lastUpdateTime;
        }
        return packageManager.getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
    }

    public static ListenableFuture<CompilationStatus> getCompilationStatusAsync() {
        return sFuture;
    }

    static class Cache {
        private static final int SCHEMA = 1;
        final long mInstalledCurrentProfileSize;
        final long mPackageLastUpdateTime;
        final int mResultCode;
        final int mSchema;

        Cache(int i, int i2, long j, long j2) {
            this.mSchema = i;
            this.mResultCode = i2;
            this.mPackageLastUpdateTime = j;
            this.mInstalledCurrentProfileSize = j2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof Cache)) {
                Cache cache = (Cache) obj;
                if (this.mResultCode == cache.mResultCode && this.mPackageLastUpdateTime == cache.mPackageLastUpdateTime && this.mSchema == cache.mSchema && this.mInstalledCurrentProfileSize == cache.mInstalledCurrentProfileSize) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mResultCode), Long.valueOf(this.mPackageLastUpdateTime), Integer.valueOf(this.mSchema), Long.valueOf(this.mInstalledCurrentProfileSize));
        }

        void writeOnFile(File file) throws IOException {
            file.delete();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                dataOutputStream.writeInt(this.mSchema);
                dataOutputStream.writeInt(this.mResultCode);
                dataOutputStream.writeLong(this.mPackageLastUpdateTime);
                dataOutputStream.writeLong(this.mInstalledCurrentProfileSize);
                dataOutputStream.close();
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }

        static Cache readFromFile(File file) throws IOException {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            try {
                Cache cache = new Cache(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readLong(), dataInputStream.readLong());
                dataInputStream.close();
                return cache;
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }
    }

    public static class CompilationStatus {
        public static final int RESULT_CODE_COMPILED_WITH_PROFILE = 1;
        public static final int RESULT_CODE_COMPILED_WITH_PROFILE_NON_MATCHING = 3;
        public static final int RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ = 131072;
        public static final int RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE = 196608;
        private static final int RESULT_CODE_ERROR_CODE_BIT_SHIFT = 16;
        public static final int RESULT_CODE_ERROR_NO_PROFILE_EMBEDDED = 327680;
        public static final int RESULT_CODE_ERROR_PACKAGE_NAME_DOES_NOT_EXIST = 65536;
        public static final int RESULT_CODE_ERROR_UNSUPPORTED_API_VERSION = 262144;

        @Deprecated
        public static final int RESULT_CODE_NO_PROFILE = 0;
        public static final int RESULT_CODE_NO_PROFILE_INSTALLED = 0;
        public static final int RESULT_CODE_PROFILE_ENQUEUED_FOR_COMPILATION = 2;
        private final boolean mHasCurrentProfile;
        private final boolean mHasEmbeddedProfile;
        private final boolean mHasReferenceProfile;
        final int mResultCode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ResultCode {
        }

        CompilationStatus(int i, boolean z, boolean z2, boolean z3) {
            this.mResultCode = i;
            this.mHasCurrentProfile = z2;
            this.mHasReferenceProfile = z;
            this.mHasEmbeddedProfile = z3;
        }

        public int getProfileInstallResultCode() {
            return this.mResultCode;
        }

        public boolean isCompiledWithProfile() {
            return this.mHasReferenceProfile;
        }

        public boolean hasProfileEnqueuedForCompilation() {
            return this.mHasCurrentProfile;
        }

        public boolean appApkHasEmbeddedProfile() {
            return this.mHasEmbeddedProfile;
        }
    }

    private static class Api33Impl {
        private Api33Impl() {
        }

        static PackageInfo getPackageInfo(PackageManager packageManager, Context context) throws PackageManager.NameNotFoundException {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(0L));
        }
    }
}
