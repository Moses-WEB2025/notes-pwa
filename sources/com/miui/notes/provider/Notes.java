package com.miui.notes.provider;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.BaseColumns;
import androidx.hardware.DataSpace;
import com.miui.common.tool.UIUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.NoteModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class Notes {
    public static final String AUTHORITY = "notes";
    public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    private static final String NOTES_PACKAGE_NAME = "com.miui.notes";
    private static final String TAG = "Notes";

    public interface Account extends BaseColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final Uri CONTENT_URI = Uri.parse("content://notes/account");
        public static final String DATA = "data";
    }

    public interface CallData extends Data {
        public static final String CALL_DATE = "data1";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/call_note";
        public static final String PHONE_NUMBER = "data3";
    }

    public interface HandWriteData extends Data {
        public static final String DATA_CONTENT_TYPE = "multi_handwrite";
    }

    public static class Intents {
        public static final String INTENT_ACTION_REFRESH_ALARM = "com.miui.notes.action.REFRESH_ALARM";
        public static final String INTENT_ACTION_REFRESH_WIDGET = "com.miui.notes.action.REFRESH_WIDGET";
        public static final String INTENT_EXTRA_ALERT_DATE = "com.miui.notes.alert_date";
        public static final String INTENT_EXTRA_BACKGROUND_ID = "com.miui.notes.background_color_id";
        public static final String INTENT_EXTRA_CALL_DATE = "com.miui.notes.call_date";
        public static final String INTENT_EXTRA_CONTACT_NAME = "com.miui.notes.contact_name";
        public static final String INTENT_EXTRA_FOLDER_ID = "com.miui.notes.folder_id";
        public static final String INTENT_EXTRA_NEW_STYLE = "com.miui.notes.new_style";
        public static final String INTENT_EXTRA_SNIPPET = "com.miui.notes.snippet";
        public static final String INTENT_EXTRA_SNIPPET_LIST = "com.miui.notes.snippet.list";
        public static final String INTENT_EXTRA_SOURCE_INTENT = "com.miui.notes.source_intent";
        public static final String INTENT_EXTRA_SOURCE_NAME = "com.miui.notes.source_name";
        public static final String INTENT_EXTRA_WIDGET_ID = "com.miui.notes.widget_id";
        public static final String INTENT_EXTRA_WIDGET_TYPE = "com.miui.notes.widget_type";
    }

    public interface TextData extends Data {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/text_note";
    }

    public static Uri appendSyncAdapterFlag(Uri uri) {
        return uri.buildUpon().appendQueryParameter(CALLER_IS_SYNCADAPTER, "true").build();
    }

    public static final class Utils {
        public static final int CLEAR_ACCOUNT_WIPE_ALL = 0;
        public static final int CLEAR_ACCOUNT_WIPE_NONE = 2;
        public static final int CLEAR_ACCOUNT_WIPE_SYNC = 1;
        private static final int IMAGE_DIMENSION_MAX = 1920;
        private static final String KEY_DATA_BYTES = "data_bytes";
        private static final String KEY_DATA_VALUES = "data_values";

        public static Bitmap createThumbnail(String str) throws Throwable {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmapScaleBitmap = scaleBitmap(str, options);
            if (bitmapScaleBitmap == null) {
                Logger.INSTANCE.e("Notes", "Fail to createThumbnail");
                return null;
            }
            Bitmap bitmapRotateBitmap = rotateBitmap(str, bitmapScaleBitmap, options.outMimeType);
            if (bitmapRotateBitmap != null) {
                return bitmapRotateBitmap;
            }
            Logger.INSTANCE.e("Notes", "Fail to rotateBitmap");
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1 */
        /* JADX WARN: Type inference failed for: r1v12 */
        private static Bitmap scaleBitmap(String str, BitmapFactory.Options options) {
            String str2;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            int i = options.outWidth;
            int i2 = options.outHeight;
            if (i > 0 && i2 > 0) {
                try {
                    if (i <= 1920 && i2 <= 1920) {
                        return miuix.graphics.BitmapFactory.decodeBitmap(str, false);
                    }
                    float fMax = Math.max(i, i2) / 1920.0f;
                    int iMax = Math.max(1, (int) (i / fMax));
                    int iMax2 = Math.max(1, (int) (i2 / fMax));
                    int i3 = 1;
                    for (int i4 = (int) fMax; i4 > 1; i4 >>= 1) {
                        i3 <<= 1;
                    }
                    str2 = 7372800;
                    try {
                        if ((i * i2) / (i3 * i3) > 7372800) {
                            return scaleBitmapByRegion(str, i, i2, iMax, iMax2, i3);
                        }
                        return miuix.graphics.BitmapFactory.decodeBitmap(str, iMax, iMax2, false);
                    } catch (IOException e) {
                        e = e;
                        Logger.INSTANCE.e("Notes", "Fail to decode " + str2 + ",with exception:" + e);
                        return null;
                    }
                } catch (IOException e2) {
                    e = e2;
                    str2 = str;
                }
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:43:0x00f2 A[Catch: all -> 0x0109, TRY_LEAVE, TryCatch #6 {all -> 0x0109, blocks: (B:7:0x0044, B:9:0x0050, B:11:0x0059, B:13:0x006d, B:15:0x0076, B:17:0x008a, B:41:0x00dc, B:43:0x00f2), top: B:69:0x0044 }] */
        /* JADX WARN: Code duplicated, block: B:45:0x00f7  */
        /* JADX WARN: Code duplicated, block: B:47:0x00fb  */
        /* JADX WARN: Code duplicated, block: B:49:0x0100  */
        /* JADX WARN: Code duplicated, block: B:51:0x0105  */
        /* JADX WARN: Code duplicated, block: B:56:0x010d  */
        /* JADX WARN: Code duplicated, block: B:58:0x0112  */
        /* JADX WARN: Code duplicated, block: B:60:0x0117  */
        private static Bitmap scaleBitmapByRegion(String str, int i, int i2, int i3, int i4, int i5) throws Throwable {
            Bitmap bitmap;
            Bitmap bitmapDecodeRegion;
            Canvas canvas;
            Bitmap bitmap2;
            Bitmap bitmap3;
            Bitmap bitmap4;
            BitmapRegionDecoder bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance(str, false);
            int i6 = i >> 1;
            int i7 = i2 >> 1;
            int i8 = i3 >> 1;
            int i9 = i4 >> 1;
            try {
                try {
                    Rect rect = new Rect();
                    Rect rect2 = new Rect();
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
                    try {
                        canvas = new Canvas(bitmapCreateBitmap);
                        Bitmap bitmap5 = null;
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = i5;
                            rect.set(0, 0, i6, i7);
                            rect2.set(0, 0, i8, i9);
                            bitmapDecodeRegion = bitmapRegionDecoderNewInstance.decodeRegion(rect, options);
                            try {
                                try {
                                    bitmap2 = bitmapCreateBitmap;
                                    bitmap = null;
                                    try {
                                        canvas.drawBitmap(bitmapDecodeRegion, (Rect) null, rect2, (Paint) null);
                                        bitmapDecodeRegion.recycle();
                                        bitmap5 = null;
                                        try {
                                            rect.set(i6, 0, i, i7);
                                            rect2.set(i8, 0, i3, i9);
                                            Bitmap bitmapDecodeRegion2 = bitmapRegionDecoderNewInstance.decodeRegion(rect, options);
                                            canvas.drawBitmap(bitmapDecodeRegion2, (Rect) null, rect2, (Paint) null);
                                            bitmapDecodeRegion2.recycle();
                                            bitmap5 = null;
                                            rect.set(0, i7, i6, i2);
                                            rect2.set(0, i9, i8, i4);
                                            bitmapDecodeRegion = bitmapRegionDecoderNewInstance.decodeRegion(rect, options);
                                            bitmap = null;
                                            canvas.drawBitmap(bitmapDecodeRegion, (Rect) null, rect2, (Paint) null);
                                            bitmapDecodeRegion.recycle();
                                            rect.set(i6, i7, i, i2);
                                            rect2.set(i8, i9, i3, i4);
                                            bitmapDecodeRegion = bitmapRegionDecoderNewInstance.decodeRegion(rect, options);
                                            canvas.drawBitmap(bitmapDecodeRegion, (Rect) null, rect2, (Paint) null);
                                            bitmapDecodeRegion.recycle();
                                            if (bitmapRegionDecoderNewInstance != null) {
                                                bitmapRegionDecoderNewInstance.recycle();
                                            }
                                            if (bitmapDecodeRegion != null) {
                                                bitmapDecodeRegion.recycle();
                                            }
                                            releaseCanvas(canvas);
                                            return bitmap2;
                                        } catch (OutOfMemoryError e) {
                                            e = e;
                                            bitmap = bitmap5;
                                            Logger.INSTANCE.e("Notes", "Fail to scaleBitmapByRegion,with exception:" + e);
                                            if (bitmap2 != null) {
                                                bitmap2.recycle();
                                                bitmap4 = bitmap;
                                            } else {
                                                bitmap4 = bitmap2;
                                            }
                                            if (bitmapRegionDecoderNewInstance != null) {
                                                bitmapRegionDecoderNewInstance.recycle();
                                            }
                                            if (bitmapDecodeRegion != null) {
                                                bitmapDecodeRegion.recycle();
                                            }
                                            if (canvas != null) {
                                                releaseCanvas(canvas);
                                            }
                                            return bitmap4;
                                        }
                                    } catch (OutOfMemoryError e2) {
                                        e = e2;
                                        Logger.INSTANCE.e("Notes", "Fail to scaleBitmapByRegion,with exception:" + e);
                                        if (bitmap2 != null) {
                                            bitmap2.recycle();
                                            bitmap4 = bitmap;
                                        } else {
                                            bitmap4 = bitmap2;
                                        }
                                        if (bitmapRegionDecoderNewInstance != null) {
                                            bitmapRegionDecoderNewInstance.recycle();
                                        }
                                        if (bitmapDecodeRegion != null) {
                                            bitmapDecodeRegion.recycle();
                                        }
                                        if (canvas != null) {
                                            releaseCanvas(canvas);
                                        }
                                        return bitmap4;
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    bitmap3 = bitmapDecodeRegion;
                                    if (bitmapRegionDecoderNewInstance != null) {
                                        bitmapRegionDecoderNewInstance.recycle();
                                    }
                                    if (bitmap3 != null) {
                                        bitmap3.recycle();
                                    }
                                    if (canvas != null) {
                                        releaseCanvas(canvas);
                                    }
                                    throw th;
                                }
                            } catch (OutOfMemoryError e3) {
                                e = e3;
                                bitmap2 = bitmapCreateBitmap;
                            }
                        } catch (OutOfMemoryError e4) {
                            e = e4;
                            bitmap2 = bitmapCreateBitmap;
                            bitmap = null;
                            bitmapDecodeRegion = null;
                        } catch (Throwable th2) {
                            th = th2;
                            bitmap3 = null;
                            if (bitmapRegionDecoderNewInstance != null) {
                                bitmapRegionDecoderNewInstance.recycle();
                            }
                            if (bitmap3 != null) {
                                bitmap3.recycle();
                            }
                            if (canvas != null) {
                                releaseCanvas(canvas);
                            }
                            throw th;
                        }
                    } catch (OutOfMemoryError e5) {
                        e = e5;
                        bitmap2 = bitmapCreateBitmap;
                        bitmap = null;
                        bitmapDecodeRegion = null;
                        canvas = null;
                    }
                } catch (OutOfMemoryError e6) {
                    e = e6;
                    bitmap = null;
                    bitmapDecodeRegion = null;
                    canvas = null;
                    bitmap2 = null;
                }
            } catch (Throwable th3) {
                th = th3;
                bitmap3 = null;
                canvas = null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v12 */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14 */
        /* JADX WARN: Type inference failed for: r2v4, types: [int] */
        /* JADX WARN: Type inference failed for: r2v5, types: [android.graphics.Canvas] */
        /* JADX WARN: Type inference failed for: r2v6 */
        private static Bitmap rotateBitmap(String str, Bitmap bitmap, String str2) throws Throwable {
            int i;
            ?? r2;
            Canvas canvas;
            OutOfMemoryError e;
            Bitmap bitmapCreateBitmap;
            if ("image/jpeg".equals(str2)) {
                try {
                    int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
                    if (attributeInt != 1) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        Matrix matrix = new Matrix();
                        if (attributeInt != 3) {
                            if (attributeInt != 6) {
                                r2 = height;
                                i = width;
                                if (attributeInt == 8) {
                                    float f = width / 2;
                                    matrix.postRotate(270.0f, f, f);
                                }
                            } else {
                                float f2 = height / 2;
                                matrix.postRotate(90.0f, f2, f2);
                            }
                            r2 = width;
                            i = height;
                        } else {
                            matrix.postRotate(180.0f, width / 2, height / 2);
                            r2 = height;
                            i = width;
                        }
                        try {
                            try {
                                try {
                                    bitmapCreateBitmap = Bitmap.createBitmap(i, r2, Bitmap.Config.ARGB_8888);
                                    try {
                                        canvas = new Canvas(bitmapCreateBitmap);
                                        try {
                                            canvas.drawBitmap(bitmap, matrix, null);
                                            bitmap.recycle();
                                            releaseCanvas(canvas);
                                            return bitmapCreateBitmap;
                                        } catch (OutOfMemoryError e2) {
                                            e = e2;
                                            Logger.INSTANCE.e("Notes", "Fail to rotateBitmap,with exception:" + e);
                                            bitmap.recycle();
                                            if (canvas != null) {
                                                releaseCanvas(canvas);
                                            }
                                            return bitmapCreateBitmap;
                                        }
                                    } catch (OutOfMemoryError e3) {
                                        canvas = null;
                                        e = e3;
                                    }
                                } catch (Throwable th) {
                                    r2 = 0;
                                    th = th;
                                    bitmap.recycle();
                                    if (r2 != 0) {
                                        releaseCanvas(r2);
                                    }
                                    throw th;
                                }
                            } catch (OutOfMemoryError e4) {
                                canvas = null;
                                e = e4;
                                bitmapCreateBitmap = null;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                } catch (IOException e5) {
                    Logger.INSTANCE.e("Notes", "createThumbnail fail,with exception:" + e5);
                    return bitmap;
                }
            }
            return bitmap;
        }

        private static void releaseCanvas(Canvas canvas) {
            try {
                Method declaredMethod = Canvas.class.getDeclaredMethod("release", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(canvas, new Object[0]);
            } catch (Exception e) {
                Logger.INSTANCE.w("Notes", "invoke Canvas.release failed,with exception:" + e);
            }
        }

        public static boolean clearAccount(Context context, boolean z) {
            return clearAccount(context, z ? 0 : 2);
        }

        public static boolean clearAccount(Context context, int i) {
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
            arrayList.add(getAccountDeleteOP());
            if (i == 0) {
                arrayList.add(getNoteDeleteOP(false));
            } else {
                if (i == 1) {
                    arrayList.add(getNoteDeleteOP(true));
                } else if (i != 2) {
                    Logger.INSTANCE.w("Notes", "Unknown wipeMode: " + i);
                }
                arrayList.add(getTemporaryDeleteOP());
                arrayList.add(getSyncClearOP());
                arrayList.add(getDirtyUpdateOP());
            }
            try {
                context.getContentResolver().applyBatch("notes", arrayList);
                if (i != 2) {
                    updateAllWidgets(context);
                }
                return true;
            } catch (OperationApplicationException e) {
                Logger.INSTANCE.e("Notes", "Fail to clear account,with exception:" + e);
                return false;
            } catch (RemoteException e2) {
                Logger.INSTANCE.e("Notes", "Fail to clear account,with exception:" + e2);
                return false;
            }
        }

        private static ContentProviderOperation getAccountDeleteOP() {
            return ContentProviderOperation.newDelete(Account.CONTENT_URI).build();
        }

        private static ContentProviderOperation getNoteDeleteOP(boolean z) {
            String str;
            if (!z) {
                str = "_id>0";
            } else {
                str = "_id>0 AND sync_id>0 AND local_modified=0";
            }
            return ContentProviderOperation.newDelete(Note.CONTENT_URI_FOR_SYNC_ADAPTER).withSelection(str, null).build();
        }

        private static ContentProviderOperation getTemporaryDeleteOP() {
            return ContentProviderOperation.newDelete(Note.CONTENT_URI_FOR_SYNC_ADAPTER).withSelection("not (parent_id>=0 OR parent_id=-2 OR parent_id=-4)", null).build();
        }

        private static ContentProviderOperation getSyncClearOP() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("sync_id", (Integer) 0);
            contentValues.put("sync_tag", "");
            contentValues.put(Note.LOCAL_MODIFIED, (Integer) 1);
            contentValues.put("version", (Integer) 0);
            contentValues.put("origin_parent_id", (Integer) 0);
            contentValues.put("account_id", (Integer) 0);
            contentValues.put(Note.SYNC_DATA1, (Integer) 0);
            contentValues.put(Note.SYNC_DATA2, (Integer) 0);
            contentValues.put(Note.SYNC_DATA3, (Integer) 0);
            contentValues.put(Note.SYNC_DATA4, (Integer) 0);
            contentValues.put(Note.SYNC_DATA5, (Integer) 0);
            contentValues.put(Note.SYNC_DATA6, "");
            contentValues.put(Note.SYNC_DATA7, "");
            contentValues.put(Note.SYNC_DATA8, "");
            return ContentProviderOperation.newUpdate(Note.CONTENT_URI_FOR_SYNC_ADAPTER).withValues(contentValues).withSelection("_id>0", null).build();
        }

        private static ContentProviderOperation getDirtyUpdateOP() {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Data.DIRTY, (Integer) 1);
            contentValues.put("file_id", "");
            return ContentProviderOperation.newUpdate(Data.CONTENT_URI_FOR_SYNC_ADAPTER).withValues(contentValues).build();
        }

        public static int updateNoteMultiAtomic(Context context, long j, ContentValues contentValues, ArrayList<ContentValues> arrayList, String str, String[] strArr) {
            addDataValuesToNoteValues(contentValues, arrayList);
            return context.getContentResolver().update(ContentUris.withAppendedId(Uri.parse("content://notes/note/atomic_multi_page"), j), contentValues, str, strArr);
        }

        public static int updateNoteAtomic(Context context, long j, ContentValues contentValues, ArrayList<ContentValues> arrayList, String str, String[] strArr) {
            return updateNoteAtomic(context, j, contentValues, arrayList, str, strArr, false);
        }

        public static int updateNoteAtomic(Context context, long j, ContentValues contentValues, ArrayList<ContentValues> arrayList, String str, String[] strArr, boolean z) {
            addDataValuesToNoteValues(contentValues, arrayList);
            Uri uriWithAppendedId = ContentUris.withAppendedId(Note.CONTENT_URI_ATOMIC, j);
            if (z) {
                uriWithAppendedId = Notes.appendSyncAdapterFlag(uriWithAppendedId);
            }
            return context.getContentResolver().update(uriWithAppendedId, contentValues, str, strArr);
        }

        public static Uri insertNoteAtomic(Context context, ContentValues contentValues, ArrayList<ContentValues> arrayList) {
            return insertNoteAtomic(context, contentValues, arrayList, false);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v8, types: [android.content.ContentResolver] */
        /* JADX WARN: Type inference failed for: r4v10 */
        /* JADX WARN: Type inference failed for: r4v14 */
        /* JADX WARN: Type inference failed for: r4v15 */
        /* JADX WARN: Type inference failed for: r4v16 */
        /* JADX WARN: Type inference failed for: r4v17 */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r4v3 */
        /* JADX WARN: Type inference failed for: r4v4, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r4v9 */
        /* JADX WARN: Type inference failed for: r8v0, types: [android.content.ContentValues] */
        /* JADX WARN: Type inference failed for: r8v1, types: [android.net.Uri] */
        /* JADX WARN: Type inference failed for: r8v2, types: [android.net.Uri] */
        /* JADX WARN: Type inference failed for: r8v3, types: [android.content.ContentValues] */
        /* JADX WARN: Type inference failed for: r8v4, types: [android.net.Uri] */
        /* JADX WARN: Type inference failed for: r8v5 */
        /* JADX WARN: Type inference failed for: r8v6 */
        /* JADX WARN: Type inference failed for: r8v7, types: [android.net.Uri] */
        public static Uri insertNoteAtomic(Context context, ContentValues contentValues, ArrayList<ContentValues> arrayList, boolean z) throws Throwable {
            StringBuilder sbAppend;
            Logger logger;
            Logger.INSTANCE.d("Notes", "insertNoteAtomic");
            addDataValuesToNoteValues(contentValues, arrayList);
            Uri uriAppendSyncAdapterFlag = Note.CONTENT_URI_ATOMIC;
            if (z) {
                uriAppendSyncAdapterFlag = Notes.appendSyncAdapterFlag(uriAppendSyncAdapterFlag);
            }
            if (Build.VERSION.SDK_INT >= 30 && contentValues.getAsString(Note.NOTE_CONTENT_TYPE).startsWith(NoteModel.NoteType.TYPE_MIND) && z) {
                ContentValues contentValues2 = new ContentValues((ContentValues) contentValues);
                String asString = contentValues2.getAsString(Note.MIND_CONTENT);
                contentValues2.remove(Note.MIND_CONTENT);
                try {
                    File file = new File(context.getFilesDir(), contentValues2.getAsString("sync_id"));
                    if (file.exists()) {
                        file.delete();
                    }
                    ?? r4 = 0;
                    r4 = 0;
                    FileOutputStream fileOutputStream = null;
                    try {
                        try {
                            file.createNewFile();
                            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                            try {
                                fileOutputStream2.write(asString.getBytes());
                                try {
                                    fileOutputStream2.close();
                                } catch (Exception e) {
                                    Logger logger2 = Logger.INSTANCE;
                                    sbAppend = new StringBuilder().append(e.getMessage()).append(StringUtils.SPACE);
                                    logger = logger2;
                                    logger.e("Notes", sbAppend.toString());
                                    r4 = logger;
                                }
                            } catch (IOException e2) {
                                e = e2;
                                fileOutputStream = fileOutputStream2;
                                Logger.INSTANCE.e("Notes", e.getMessage() + StringUtils.SPACE);
                                r4 = fileOutputStream;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                        r4 = fileOutputStream;
                                    } catch (Exception e3) {
                                        Logger logger3 = Logger.INSTANCE;
                                        sbAppend = new StringBuilder().append(e3.getMessage()).append(StringUtils.SPACE);
                                        logger = logger3;
                                        logger.e("Notes", sbAppend.toString());
                                        r4 = logger;
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                r4 = fileOutputStream2;
                                if (r4 != 0) {
                                    try {
                                        r4.close();
                                    } catch (Exception e4) {
                                        Logger.INSTANCE.e("Notes", e4.getMessage() + StringUtils.SPACE);
                                    }
                                }
                                throw th;
                            }
                        } catch (IOException e5) {
                            e = e5;
                        }
                        contentValues2.put(Note.MIND_CONTENT_PATH, file.getPath());
                        contentValues = context.getContentResolver().insert(uriAppendSyncAdapterFlag, contentValues2);
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e6) {
                    Logger.INSTANCE.e("Notes", e6.getMessage() + StringUtils.SPACE);
                    contentValues = context.getContentResolver().insert(uriAppendSyncAdapterFlag, contentValues);
                }
            } else {
                contentValues = context.getContentResolver().insert(uriAppendSyncAdapterFlag, contentValues);
            }
            if (contentValues != 0) {
                UIUtils.updateWidgetNotes(context);
            }
            return contentValues;
        }

        public static void addDataValuesToNoteValues(ContentValues contentValues, ArrayList<ContentValues> arrayList) {
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            removeSnippetIfHasDataContent(contentValues, arrayList);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(KEY_DATA_VALUES, arrayList);
            contentValues.put(KEY_DATA_BYTES, marshall(bundle));
        }

        private static void removeSnippetIfHasDataContent(ContentValues contentValues, ArrayList<ContentValues> arrayList) {
            String contentFromData;
            if (contentValues.containsKey("snippet") && (contentFromData = getContentFromData(arrayList)) != null && contentFromData.equals(contentValues.getAsString("snippet"))) {
                contentValues.put("snippet", (String) null);
            }
        }

        private static String getContentFromData(ArrayList<ContentValues> arrayList) {
            for (ContentValues contentValues : arrayList) {
                if (contentValues.containsKey(Data.MIME_TYPE) && TextData.CONTENT_ITEM_TYPE.equals(contentValues.getAsString(Data.MIME_TYPE)) && contentValues.containsKey("content")) {
                    return contentValues.getAsString("content");
                }
            }
            return null;
        }

        private static synchronized byte[] marshall(Bundle bundle) {
            byte[] bArrMarshall;
            Parcel parcelObtain = Parcel.obtain();
            try {
                bundle.writeToParcel(parcelObtain, 0);
                bArrMarshall = parcelObtain.marshall();
                parcelObtain.recycle();
            } catch (Throwable th) {
                parcelObtain.recycle();
                throw th;
            }
            return bArrMarshall;
        }

        private static synchronized Bundle unmarshall(byte[] bArr) {
            Bundle bundle;
            Parcel parcelObtain = Parcel.obtain();
            try {
                parcelObtain.unmarshall(bArr, 0, bArr.length);
                parcelObtain.setDataPosition(0);
                bundle = parcelObtain.readBundle();
                parcelObtain.recycle();
            } catch (Throwable th) {
                parcelObtain.recycle();
                throw th;
            }
            return bundle;
        }

        public static ArrayList<ContentValues> removeDataValuesFromNoteValues(ContentValues contentValues) {
            String contentFromData;
            byte[] asByteArray = contentValues.getAsByteArray(KEY_DATA_BYTES);
            contentValues.remove(KEY_DATA_BYTES);
            if (asByteArray == null) {
                return null;
            }
            ArrayList<ContentValues> parcelableArrayList = unmarshall(asByteArray).getParcelableArrayList(KEY_DATA_VALUES);
            if (contentValues.containsKey("snippet") && contentValues.getAsString("snippet") == null && (contentFromData = getContentFromData(parcelableArrayList)) != null) {
                contentValues.put("snippet", contentFromData);
            }
            return parcelableArrayList;
        }

        public static void updateAllAlarms(Context context) {
            Intent intent = new Intent(Intents.INTENT_ACTION_REFRESH_ALARM);
            intent.setPackage("com.miui.notes");
            context.sendBroadcast(intent);
        }

        public static void updateAllWidgets(Context context) {
            Intent intent = new Intent(Intents.INTENT_ACTION_REFRESH_WIDGET);
            intent.setPackage("com.miui.notes");
            intent.addFlags(DataSpace.RANGE_LIMITED);
            context.sendBroadcast(intent);
            UIUtils.updateWidgetNotes(context);
        }

        public static int[] getUnsyncedCount(Context context) {
            int i;
            int i2 = 0;
            Cursor cursorQuery = context.getContentResolver().query(Note.CONTENT_URI_FOR_SYNC_ADAPTER, new String[]{"_id", "type"}, "local_modified=1 AND _id>0 AND (sync_id>0 OR (sync_id<=0 AND snippet<>''))", null, null);
            if (cursorQuery != null) {
                i = 0;
                while (cursorQuery.moveToNext()) {
                    try {
                        int i3 = cursorQuery.getInt(1);
                        if (i3 == 0) {
                            i2++;
                        } else if (i3 == 1) {
                            i++;
                        }
                    } catch (Throwable th) {
                        cursorQuery.close();
                        throw th;
                    }
                }
                cursorQuery.close();
            } else {
                Logger.INSTANCE.e("Notes", "getUnsyncedCount: cursor is null");
                i = 0;
            }
            return new int[]{i2, i};
        }
    }

    public interface Data extends BaseColumns {
        public static final String CONTENT = "content";
        public static final Uri CONTENT_URI;
        public static final Uri CONTENT_URI_FOR_SYNC_ADAPTER;
        public static final String CREATED_DATE = "created_date";
        public static final String DATA1 = "data1";
        public static final String DATA2 = "data2";
        public static final String DATA3 = "data3";
        public static final String DATA4 = "data4";
        public static final String DATA5 = "data5";
        public static final String DATA_CONTENT_TYPE = "data_content_type";
        public static final String DATA_NEED_PUSH = "need_push";
        public static final String DATA_NEED_UPDATE = "need_update";
        public static final String DIRTY = "dirty";
        public static final String FILE_ID = "file_id";
        public static final String ID = "_id";
        public static final Uri MEDIA_URI;
        public static final String MIME_TYPE = "mime_type";
        public static final String MODIFIED_DATE = "modified_date";
        public static final String NOTE_ID = "note_id";

        static {
            Uri uri = Uri.parse("content://notes/data");
            CONTENT_URI = uri;
            CONTENT_URI_FOR_SYNC_ADAPTER = Notes.appendSyncAdapterFlag(uri);
            MEDIA_URI = Uri.parse("content://notes/data/media");
        }
    }

    public interface Note extends BaseColumns {
        public static final String ACCOUNT_ID = "account_id";
        public static final String AIGC_DATA_ID = "aigc_data_id";
        public static final String AI_TEXT_USED = "ai_text_used";
        public static final String ALERTED_DATE = "alert_date";
        public static final String ALERT_TAG = "alert_tag";
        public static final String BG_COLOR_ID = "bg_color_id";
        public static final Uri CONTENT_URI;
        public static final Uri CONTENT_URI_ATOMIC;
        public static final Uri CONTENT_URI_FOR_SYNC_ADAPTER;
        public static final String CREATED_DATE = "created_date";
        public static final String CURRENT_PAGE_NUM = "current_page_num";
        public static final String CURRENT_PAGE_SCALE = "current_page_scale";
        public static final String DELETE_IMAGES = "delete_images";
        public static final String DELETION_TAG = "deletion_tag";
        public static final String ENT_NAME = "ent_name";
        public static final String EXTERNAL_ID = "external_id";
        public static final String HAS_ATTACHMENT = "has_attachment";
        public static final String ID = "_id";
        public static final int ID_BLACK_BOARD_FOLDER = -7;
        public static final int ID_CALL_RECORD_FOLDER = -2;
        public static final int ID_EXCERPT_FOLDER = -6;
        public static final int ID_HEAR_FOLDER = -5;
        public static final int ID_PRIVACY_FOLDER = -4;
        public static final int ID_ROOT_FOLDER = 0;
        public static final int ID_STYLUS_FOLDER = -8;
        public static final int ID_TEMPARAY_FOLDER = -1;
        public static final int ID_TRASH_FOLDER = -3;
        public static final String IMAGES = "images";
        public static final String IN_VALID_FOLDER_SELECTION = "(parent_id>=0 OR parent_id=-2 OR parent_id=-4)";
        public static final String IS_EMPTY = "is_empty";
        public static final String LOCAL_MODIFIED = "local_modified";
        public static final String MIND_CONTENT = "mind_content";
        public static final String MIND_CONTENT_PATH = "mind_file_path";
        public static final String MIND_CONTENT_PLAIN_TEXT = "mind_content_plain_text";
        public static final String MODIFIED_DATE = "modified_date";
        public static final String MOVED_DATE = "moved_date";
        public static final String NOTES_COUNT = "notes_count";
        public static final String NOTE_CONTENT_TYPE = "note_content_type";
        public static final String NOTE_TEXT_COUNT = "note_text_count";
        public static final String ORIGIN_PARENT_ID = "origin_parent_id";
        public static final String PARENT_ID = "parent_id";
        public static final String PLAIN_TEXT = "plain_text";
        public static final String RECOGNIZED_FILE_NAMES = "recognized_file_names";
        public static final String SNIPPET = "snippet";
        public static final String SOURCE_INTENT = "source_intent";
        public static final String SOURCE_NAME = "source_name";
        public static final String SOURCE_PACKAGE = "source_package";
        public static final String STICK_DATE = "stick_date";
        public static final String SUBJECT = "subject";
        public static final String SYNC_DATA1 = "sync_data1";
        public static final String SYNC_DATA2 = "sync_data2";
        public static final String SYNC_DATA3 = "sync_data3";
        public static final String SYNC_DATA4 = "sync_data4";
        public static final String SYNC_DATA5 = "sync_data5";
        public static final String SYNC_DATA6 = "sync_data6";
        public static final String SYNC_DATA7 = "sync_data7";
        public static final String SYNC_DATA8 = "sync_data8";
        public static final String SYNC_ID = "sync_id";
        public static final String SYNC_TAG = "sync_tag";
        public static final String THEME_ID = "theme_id";
        public static final String THUMBNAIL = "thumbnail";
        public static final String TITLE = "title";
        public static final String TYPE = "type";
        public static final int TYPE_FOLDER = 1;
        public static final int TYPE_NOTE = 0;
        public static final int TYPE_SYSTEM = 2;
        public static final int TYPE_WIDGET_2x1_ICON = 6;
        public static final int TYPE_WIDGET_2x1_NOTES = 5;
        public static final int TYPE_WIDGET_2x1_TODO = 7;
        public static final int TYPE_WIDGET_2x2 = 1;
        public static final int TYPE_WIDGET_4x2 = 2;
        public static final int TYPE_WIDGET_4x4 = 3;
        public static final int TYPE_WIDGET_8x4 = 4;
        public static final int TYPE_WIDGET_INVALID = -1;
        public static final String VALID_FOLDER_SELECTION = "((type=1 AND parent_id=0) OR _id=-2)";
        public static final String VERSION = "version";
        public static final String WEB_IMAGES = "web_images";
        public static final String WIDGET_ID = "widget_id";
        public static final String WIDGET_TYPE = "widget_type";

        static {
            Uri uri = Uri.parse("content://notes/note");
            CONTENT_URI = uri;
            CONTENT_URI_FOR_SYNC_ADAPTER = Notes.appendSyncAdapterFlag(uri);
            CONTENT_URI_ATOMIC = Uri.parse("content://notes/note/atomic");
        }
    }
}
