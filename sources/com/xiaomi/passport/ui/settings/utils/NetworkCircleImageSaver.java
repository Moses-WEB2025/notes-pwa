package com.xiaomi.passport.ui.settings.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.CloudCoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkCircleImageSaver {
    private static final String CACHE_IMAGE_DIR = "img";
    private static final String TAG = "NetworkImageSaver";

    public static boolean saveNetworkImageDefault(Context context, String str) {
        File defaultNetworkImageCacheFile;
        if (TextUtils.isEmpty(str) || (defaultNetworkImageCacheFile = getDefaultNetworkImageCacheFile(context, str)) == null) {
            return false;
        }
        clearCacheDirIfNeeded(defaultNetworkImageCacheFile.getParentFile());
        if (defaultNetworkImageCacheFile.isFile() && defaultNetworkImageCacheFile.exists()) {
            AccountLogger.log(TAG, "file " + defaultNetworkImageCacheFile + " exists");
            return false;
        }
        try {
            SimpleRequest.StreamContent asStream = SimpleRequest.getAsStream(str, null, null);
            try {
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(asStream.getStream());
                if (bitmapDecodeStream == null) {
                    return false;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                Paint paint = new Paint();
                Rect rect = new Rect(0, 0, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight());
                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(-16777216);
                canvas.drawCircle(bitmapDecodeStream.getWidth() / 2, bitmapDecodeStream.getHeight() / 2, bitmapDecodeStream.getWidth() / 2, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmapDecodeStream, rect, rect, paint);
                FileOutputStream fileOutputStream = new FileOutputStream(defaultNetworkImageCacheFile);
                bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                bitmapDecodeStream.recycle();
                bitmapCreateBitmap.recycle();
                fileOutputStream.close();
                return true;
            } catch (IOException e) {
                AccountLogger.log(TAG, "failed to save avatar", e);
                return false;
            } finally {
                asStream.closeStream();
            }
            asStream.closeStream();
        } catch (AccessDeniedException e2) {
            AccountLogger.log(TAG, "access denied when download avatar", e2);
            return false;
        } catch (AuthenticationFailureException e3) {
            AccountLogger.log(TAG, "auth failed when download avatar", e3);
            return false;
        } catch (IOException e4) {
            AccountLogger.log(TAG, "IO error when download avatar", e4);
            return false;
        }
    }

    private static void clearCacheDirIfNeeded(File file) {
        File[] fileArrListFiles;
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length > 20) {
            ArrayList arrayList = new ArrayList();
            for (File file2 : fileArrListFiles) {
                arrayList.add(new Pair(file2, Long.valueOf(file2.lastModified())));
            }
            Collections.sort(arrayList, new Comparator<Pair<File, Long>>() { // from class: com.xiaomi.passport.ui.settings.utils.NetworkCircleImageSaver.1
                @Override // java.util.Comparator
                public int compare(Pair<File, Long> pair, Pair<File, Long> pair2) {
                    if (((Long) pair.second).longValue() < ((Long) pair2.second).longValue()) {
                        return -1;
                    }
                    return pair.second == pair2.second ? 0 : 1;
                }
            });
            for (int i = 0; i < arrayList.size() - 10; i++) {
                ((File) ((Pair) arrayList.get(i)).first).delete();
            }
        }
    }

    public static File getDefaultNetworkImageCacheFile(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(context.getExternalCacheDir(), "img");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, CloudCoder.getMd5DigestUpperCase(str));
    }
}
