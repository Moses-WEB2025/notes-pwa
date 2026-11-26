package com.miui.doodle.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import miuix.core.util.IOUtils;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007J\u001c\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u000e\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J\u0016\u0010\u001f\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0012J\u0006\u0010!\u001a\u00020\"J\u0006\u0010#\u001a\u00020\"J\u0006\u0010$\u001a\u00020\fJ\u0006\u0010%\u001a\u00020\fJ\u0010\u0010&\u001a\u00020\f2\b\u0010'\u001a\u0004\u0018\u00010\"J\u000e\u0010(\u001a\u00020\f2\u0006\u0010'\u001a\u00020\"J\u0018\u0010)\u001a\u0004\u0018\u00010\"2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u0005J\u0006\u0010,\u001a\u00020\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/miui/doodle/utils/Utils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "KEY_PICK_LIMITED_NUMBER", "", "KEY_PICK_UPPER_BOUND", "GALLERY_PKG_NAME", "ACTION_GALLERY_ALBUM", "selectImage", "", "fragment", "Landroidx/fragment/app/Fragment;", "requestCode", "limitedNumber", "checkIntent", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "hideSoftInput", "view", "Landroid/view/View;", "parseUpperFolder", CallMethod.ARG_URI, "Landroid/net/Uri;", "getBitMapOptions", "Landroid/graphics/BitmapFactory$Options;", "showSoftInput", Constants.FORCE, "getDoodleCacheDir", "Ljava/io/File;", "getDoodlePreloadCacheDir", "clearDoodleCacheDir", "clearDoodlePreloadCacheDir", "clearFilesInFolder", "dir", "clearFilesAndFolderByFolder", "copyFile", "oldPath", "newPath", "isRTL", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Utils {
    private static final String ACTION_GALLERY_ALBUM = "com.miui.gallery.action.VIEW_ALBUM";
    private static final String GALLERY_PKG_NAME = "com.miui.gallery";
    public static final Utils INSTANCE = new Utils();
    private static final int KEY_PICK_LIMITED_NUMBER = 10;
    private static final String KEY_PICK_UPPER_BOUND = "pick-upper-bound";
    private static final String TAG = "libDoodle:Utils";

    private Utils() {
    }

    public final void selectImage(Fragment fragment, int requestCode) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        selectImage(fragment, 10, requestCode);
    }

    public final void selectImage(Fragment fragment, int limitedNumber, int requestCode) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setFlags(524288);
        intent.putExtra("pick-upper-bound", limitedNumber);
        intent.setType(ShareAppsAdapter.TYPE_IMAGE);
        intent.setPackage("com.miui.gallery");
        if (!checkIntent(fragment.getContext(), intent)) {
            intent.setPackage(null);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    private final boolean checkIntent(Context context, Intent intent) {
        if (context == null || intent == null) {
            return false;
        }
        List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        Intrinsics.checkNotNullExpressionValue(listQueryIntentActivities, "queryIntentActivities(...)");
        return listQueryIntentActivities.size() > 0;
    }

    public final void hideSoftInput(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        Object systemService = view.getContext().getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @JvmStatic
    public static final String parseUpperFolder(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        String encodedPath = uri.getEncodedPath();
        StringBuilder sb = new StringBuilder();
        if (encodedPath == null) {
            return null;
        }
        String[] strArr = (String[]) new Regex(InternalZipConstants.ZIP_FILE_SEPARATOR).split(encodedPath, 0).toArray(new String[0]);
        if (strArr.length >= 3) {
            sb.append(strArr[strArr.length - 3]).append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        }
        if (strArr.length < 2) {
            return null;
        }
        sb.append(strArr[strArr.length - 2]);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v3 */
    @JvmStatic
    public static final BitmapFactory.Options getBitMapOptions(Context context, Uri uri) throws Throwable {
        InputStream inputStreamOpenInputStream;
        Intrinsics.checkNotNullParameter(context, "context");
        ?? r0 = 0;
        options = null;
        BitmapFactory.Options options = null;
        try {
            try {
                ContentResolver contentResolver = context.getContentResolver();
                Intrinsics.checkNotNull(uri);
                inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                try {
                    BitmapFactory.Options options2 = new BitmapFactory.Options();
                    options2.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options2);
                    if (options2.outHeight > 0 && options2.outWidth > 0) {
                        options = options2;
                    }
                    IOUtils.closeQuietly(inputStreamOpenInputStream);
                    return options;
                } catch (FileNotFoundException e) {
                    e = e;
                    e.printStackTrace();
                    IOUtils.closeQuietly(inputStreamOpenInputStream);
                    return null;
                }
            } catch (FileNotFoundException e2) {
                e = e2;
                inputStreamOpenInputStream = null;
            } catch (Throwable th) {
                th = th;
                IOUtils.closeQuietly((InputStream) r0);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r0 = context;
            IOUtils.closeQuietly((InputStream) r0);
            throw th;
        }
    }

    public final void showSoftInput(View view, boolean force) {
        Intrinsics.checkNotNullParameter(view, "view");
        Object systemService = view.getContext().getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).showSoftInput(view, 1);
    }

    public final File getDoodleCacheDir() {
        File cacheDir = DoodleCacheManager.INSTANCE.getCacheDir();
        Boolean boolValueOf = cacheDir != null ? Boolean.valueOf(cacheDir.exists()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (!boolValueOf.booleanValue()) {
            File cacheDir2 = DoodleCacheManager.INSTANCE.getCacheDir();
            Intrinsics.checkNotNull(cacheDir2);
            cacheDir2.mkdirs();
        }
        File cacheDir3 = DoodleCacheManager.INSTANCE.getCacheDir();
        Intrinsics.checkNotNull(cacheDir3);
        return cacheDir3;
    }

    public final File getDoodlePreloadCacheDir() {
        File cachePreDir = DoodleCacheManager.INSTANCE.getCachePreDir();
        Boolean boolValueOf = cachePreDir != null ? Boolean.valueOf(cachePreDir.exists()) : null;
        Intrinsics.checkNotNull(boolValueOf);
        if (!boolValueOf.booleanValue()) {
            File cachePreDir2 = DoodleCacheManager.INSTANCE.getCachePreDir();
            Intrinsics.checkNotNull(cachePreDir2);
            cachePreDir2.mkdirs();
        }
        File cachePreDir3 = DoodleCacheManager.INSTANCE.getCachePreDir();
        Intrinsics.checkNotNull(cachePreDir3);
        return cachePreDir3;
    }

    public final void clearDoodleCacheDir() {
        clearFilesInFolder(getDoodleCacheDir());
    }

    public final void clearDoodlePreloadCacheDir() {
        try {
            clearFilesInFolder(getDoodlePreloadCacheDir());
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "e:" + e);
        }
    }

    public final void clearFilesInFolder(File dir) {
        File[] fileArrListFiles;
        if (dir != null && dir.exists() && dir.isDirectory() && (fileArrListFiles = dir.listFiles()) != null) {
            for (File file : fileArrListFiles) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    Utils utils = INSTANCE;
                    Intrinsics.checkNotNull(file);
                    utils.clearFilesAndFolderByFolder(file);
                }
            }
        }
    }

    public final void clearFilesAndFolderByFolder(File dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    Utils utils = INSTANCE;
                    Intrinsics.checkNotNull(file);
                    utils.clearFilesAndFolderByFolder(file);
                }
            }
        }
        dir.delete();
    }

    public final File copyFile(String oldPath, String newPath) {
        Intrinsics.checkNotNullParameter(oldPath, "oldPath");
        Intrinsics.checkNotNullParameter(newPath, "newPath");
        try {
            File file = new File(oldPath);
            if (!file.exists()) {
                Logger.INSTANCE.e("Utils", "copyFile:  oldFile not exist.");
                return null;
            }
            if (!file.isFile()) {
                Logger.INSTANCE.e("Utils", "copyFile:  oldFile not file.");
                return null;
            }
            if (!file.canRead()) {
                Logger.INSTANCE.e("Utils", "copyFile:  oldFile cannot read.");
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(oldPath);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                Unit unit = Unit.INSTANCE;
                if (-1 != i) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return new File(newPath);
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    public final boolean isRTL() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
    }
}
