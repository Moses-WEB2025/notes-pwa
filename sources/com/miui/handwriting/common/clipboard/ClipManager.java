package com.miui.handwriting.common.clipboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.DragAndDropPermissionsCompat;
import ch.qos.logback.core.CoreConstants;
import com.bumptech.glide.Glide;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Utils;
import com.miui.creation.common.R;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.libhandwritingpage.newbean.beanSelect.SelectCopyBean;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.miai.SDKConstants;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.core.util.SystemProperties;
import miuix.util.Patterns;
import net.lingala.zip4j.util.InternalZipConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import xc.engine.utils.FileUtil;

/* JADX INFO: compiled from: ClipManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001MB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002J\b\u0010!\u001a\u00020\u001fH\u0002J\b\u0010\"\u001a\u00020#H\u0007J\u0018\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020'H\u0007J\u001a\u0010(\u001a\u00020\u001f2\b\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010&\u001a\u00020'H\u0007J\u001a\u0010+\u001a\u00020\u001f2\b\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020\u000eH\u0003J\u0018\u0010/\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020*H\u0002J\u0010\u00102\u001a\u00020#2\u0006\u00103\u001a\u000204H\u0007J\u001e\u00105\u001a\u00020#2\u0006\u00106\u001a\u0002072\u0006\u00103\u001a\u0002042\u0006\u00108\u001a\u000209J\u001a\u0010:\u001a\u00020\u00052\u0006\u0010&\u001a\u00020'2\b\u0010;\u001a\u0004\u0018\u00010\u0014H\u0007J\u0010\u0010<\u001a\u00020\u00052\u0006\u0010&\u001a\u00020'H\u0007J\u0012\u0010=\u001a\u00020#2\b\u0010;\u001a\u0004\u0018\u00010\u0014H\u0007J\u0010\u0010>\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010?\u001a\u00020\u00052\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010@\u001a\u00020#2\u0006\u0010A\u001a\u00020\u0005H\u0002J\u0014\u0010B\u001a\u0004\u0018\u00010C2\b\u0010D\u001a\u0004\u0018\u00010EH\u0002J\u0018\u0010F\u001a\u00020#2\u0006\u0010&\u001a\u00020'2\u0006\u0010G\u001a\u00020#H\u0007J\b\u0010H\u001a\u00020#H\u0007J\u0012\u0010I\u001a\u0004\u0018\u00010\u00052\u0006\u0010J\u001a\u00020\u0005H\u0002J\u0010\u0010K\u001a\u00020#2\u0006\u0010L\u001a\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006N"}, d2 = {"Lcom/miui/handwriting/common/clipboard/ClipManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "PROVIDER_CLIP", "pageCopyData", "Lcom/miui/libhandwritingpage/newbean/beanSelect/SelectCopyBean;", "getPageCopyData", "()Lcom/miui/libhandwritingpage/newbean/beanSelect/SelectCopyBean;", "setPageCopyData", "(Lcom/miui/libhandwritingpage/newbean/beanSelect/SelectCopyBean;)V", "mTimeStamp", "", "getMTimeStamp", "()J", "setMTimeStamp", "(J)V", CallMethod.ARG_URI, "Landroid/net/Uri;", "getUri", "()Landroid/net/Uri;", "setUri", "(Landroid/net/Uri;)V", "onlineUri", "getOnlineUri", "()Ljava/lang/String;", "setOnlineUri", "(Ljava/lang/String;)V", "setMyUri", "", "setMyOnlineUri", "clearUri", "isSupportSuperClipboard", "", "copyContentToClipboard", "str", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "copyContentToSuperClipboard", "mBitmap", "Landroid/graphics/Bitmap;", "deleteImage", "picDir", "Ljava/io/File;", "currentTime", "saveImage", "path", DoodleBitmap.KEY_BITMAP, "isValidDragEventData", "event", "Landroid/view/DragEvent;", "getImageUrisFromDragEventInFragment", "activity", "Landroid/app/Activity;", CallMethod.ARG_CALLBACK, "Lcom/miui/handwriting/common/clipboard/ClipManager$UriGetCallback;", "getDragPath", "imageUri", "getCopyPath", "hasFormatSuffix", "isContainContent", "getCopyText", "htmlParse", "htmlText", "getPrimaryClip", "Landroid/content/ClipData;", "clipboardManager", "Landroid/content/ClipboardManager;", "isPasteFromLocal", "isContentEmpty", "isNeedNetwork", "downloadImageToTemp", "imageUrl", "isClipUrlAccurateValid", "urlString", "UriGetCallback", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ClipManager {
    public static final ClipManager INSTANCE = new ClipManager();
    private static final String PROVIDER_CLIP = "com.miui.notes.cliputils";
    private static final String TAG = "ClipUtils";
    private static long mTimeStamp;
    private static String onlineUri;
    private static SelectCopyBean pageCopyData;
    private static Uri uri;

    /* JADX INFO: compiled from: ClipManager.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lcom/miui/handwriting/common/clipboard/ClipManager$UriGetCallback;", "", "onGetUri", "", "imageUris", "Ljava/util/ArrayList;", "Landroid/net/Uri;", "Lkotlin/collections/ArrayList;", "onException", "reason", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface UriGetCallback {
        void onException(String reason);

        void onGetUri(ArrayList<Uri> imageUris);
    }

    private ClipManager() {
    }

    public final SelectCopyBean getPageCopyData() {
        return pageCopyData;
    }

    public final void setPageCopyData(SelectCopyBean selectCopyBean) {
        pageCopyData = selectCopyBean;
    }

    public final long getMTimeStamp() {
        return mTimeStamp;
    }

    public final void setMTimeStamp(long j) {
        mTimeStamp = j;
    }

    public final Uri getUri() {
        return uri;
    }

    public final void setUri(Uri uri2) {
        uri = uri2;
    }

    public final String getOnlineUri() {
        return onlineUri;
    }

    public final void setOnlineUri(String str) {
        onlineUri = str;
    }

    private final void setMyUri(Uri uri2) {
        uri = uri2;
        onlineUri = null;
    }

    private final void setMyOnlineUri(String onlineUri2) {
        uri = null;
        onlineUri = onlineUri2;
    }

    private final void clearUri() {
        uri = null;
        onlineUri = null;
    }

    @JvmStatic
    public static final boolean isSupportSuperClipboard() {
        return Intrinsics.areEqual(SystemProperties.get("persist.sys.support_super_clipboard", "0"), "1");
    }

    @JvmStatic
    public static final void copyContentToClipboard(String str, Context context) {
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("text/plain", str));
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new AnonymousClass1(context, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.clipboard.ClipManager$copyContentToClipboard$1, reason: invalid class name */
    /* JADX INFO: compiled from: ClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.clipboard.ClipManager$copyContentToClipboard$1", f = "ClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$context, continuation);
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
            Toast.makeText(this.$context, R.string.creation_toast_add_to_clip, 0).show();
            return Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void copyContentToSuperClipboard(Bitmap mBitmap, Context context) throws Throwable {
        Uri uriForFile;
        Intrinsics.checkNotNullParameter(context, "context");
        if (mBitmap != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Object systemService = context.getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ClipboardManager clipboardManager = (ClipboardManager) systemService;
            File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            Log.i(TAG, "copyContentToSuperClipboard AUTHORITY=com.miui.notes.cliputils, picDir=" + externalFilesDir);
            String str = externalFilesDir + '/' + jCurrentTimeMillis + Utils.JPG_SUFFIX;
            deleteImage(externalFilesDir, jCurrentTimeMillis);
            INSTANCE.saveImage(str, mBitmap);
            File file = new File(str);
            if (file.exists()) {
                uriForFile = FileProvider.getUriForFile(context, PROVIDER_CLIP, file);
            } else {
                Log.i(TAG, "copyContentToSuperClipboard " + file + " is not exist");
                uriForFile = null;
            }
            if (uriForFile != null) {
                Log.i(TAG, "copyContentToSuperClipboard  uri type=" + context.getContentResolver().getType(uriForFile));
                clipboardManager.setPrimaryClip(new ClipData(new ClipDescription("image/jpg", new String[]{context.getContentResolver().getType(uriForFile)}), new ClipData.Item(uriForFile)));
                mTimeStamp = System.currentTimeMillis();
                Toast.makeText(context, context.getResources().getString(R.string.creation_toast_add_to_clip), 0).show();
            }
        }
    }

    @JvmStatic
    private static final void deleteImage(File picDir, long currentTime) {
        File[] fileArrListFiles;
        if (picDir == null || !picDir.exists() || (fileArrListFiles = picDir.listFiles()) == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            if (file.exists()) {
                String name = file.getName();
                Log.i(TAG, "deleteImage file.name=" + name);
                Intrinsics.checkNotNull(name);
                Long longOrNull = StringsKt.toLongOrNull(StringsKt.substringBeforeLast$default(name, Utils.JPG_SUFFIX, (String) null, 2, (Object) null));
                if (longOrNull != null) {
                    Long l = longOrNull.longValue() < currentTime ? longOrNull : null;
                    if (l != null) {
                        l.longValue();
                        file.delete();
                    }
                }
            }
        }
    }

    private final void saveImage(String path, Bitmap bitmap) throws Throwable {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawColor(-1);
        FileOutputStream fileOutputStream = null;
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(path);
                    try {
                        bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream == null) {
                        } else {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    @JvmStatic
    public static final boolean isValidDragEventData(DragEvent event) {
        ClipDescription description;
        String[] strArrFilterMimeTypes;
        String[] strArrFilterMimeTypes2;
        Intrinsics.checkNotNullParameter(event, "event");
        ClipDescription clipDescription = event.getClipDescription();
        if (clipDescription != null && (strArrFilterMimeTypes2 = clipDescription.filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) != null) {
            return !(strArrFilterMimeTypes2.length == 0);
        }
        ClipData clipData = event.getClipData();
        if (clipData == null || (description = clipData.getDescription()) == null || (strArrFilterMimeTypes = description.filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) == null) {
            return false;
        }
        return !(strArrFilterMimeTypes.length == 0);
    }

    public final boolean getImageUrisFromDragEventInFragment(Activity activity, DragEvent event, UriGetCallback callback) {
        ClipDescription description;
        String[] strArrFilterMimeTypes;
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(callback, "callback");
        ArrayList<Uri> arrayList = new ArrayList<>();
        ClipData clipData = event.getClipData();
        if (clipData != null && (description = clipData.getDescription()) != null && (strArrFilterMimeTypes = description.filterMimeTypes(ShareAppsAdapter.TYPE_IMAGE)) != null && clipData.getItemCount() > 0) {
            if (!(strArrFilterMimeTypes.length == 0)) {
                DragAndDropPermissionsCompat dragAndDropPermissionsCompatRequestDragAndDropPermissions = ActivityCompat.requestDragAndDropPermissions(activity, event);
                if (Intrinsics.areEqual("content", event.getClipData().getItemAt(0).getUri().getScheme())) {
                    Log.d(TAG, "Requesting permissions.");
                    if (dragAndDropPermissionsCompatRequestDragAndDropPermissions == null) {
                        Log.d(TAG, "Drop permission request failed.");
                        return false;
                    }
                }
                int itemCount = clipData.getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    Uri uri2 = clipData.getItemAt(i).getUri();
                    Log.d(TAG, "getImageUrisFromDragEventInFragment: " + uri2);
                    try {
                        activity.grantUriPermission(activity.getPackageName(), uri2, 1);
                        Boolean.valueOf(arrayList.add(uri2));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Unit unit = Unit.INSTANCE;
                    }
                }
                if (!arrayList.isEmpty()) {
                    callback.onGetUri(arrayList);
                }
                return true;
            }
        }
        callback.onException("no image");
        return false;
    }

    @JvmStatic
    public static final String getDragPath(Context context, Uri imageUri) {
        String string;
        Intrinsics.checkNotNullParameter(context, "context");
        if (imageUri == null) {
            string = "";
        } else {
            String str = context.getExternalCacheDir() + "/import_tmp_copy";
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
            String path = imageUri.getPath();
            if (!hasFormatSuffix(imageUri)) {
                path = path + Utils.JPG_SUFFIX;
            }
            Intrinsics.checkNotNull(path);
            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) path, InternalZipConstants.ZIP_FILE_SEPARATOR, 0, false, 6, (Object) null);
            StringBuilder sbAppend = new StringBuilder().append(str);
            String strSubstring = path.substring(iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            string = sbAppend.append(StringsKt.replace$default(strSubstring, InternalZipConstants.ZIP_FILE_SEPARATOR, InternalZipConstants.ZIP_FILE_SEPARATOR + System.currentTimeMillis() + '_', false, 4, (Object) null)).toString();
            try {
                FileUtil.copyUriToFile(context, imageUri, new File(string));
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                Integer.valueOf(Log.e(TAG, "copyUriToFile error", e));
            }
        }
        Log.e(TAG, "targetPath:" + string);
        return string;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v6, types: [T, java.lang.String] */
    @JvmStatic
    public static final String getCopyPath(Context context) throws InterruptedException {
        Intrinsics.checkNotNullParameter(context, "context");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = "";
        String str = onlineUri;
        if (str != null) {
            BuildersKt__BuildersKt.runBlocking$default(null, new ClipManager$getCopyPath$1$1(objectRef, str, null), 1, null);
        }
        Uri uri2 = uri;
        if (uri2 != null) {
            String str2 = context.getExternalCacheDir() + "/import_tmp_copy";
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
            String path = uri2.getPath();
            if (!hasFormatSuffix(uri2)) {
                path = path + Utils.JPG_SUFFIX;
            }
            Intrinsics.checkNotNull(path);
            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) path, InternalZipConstants.ZIP_FILE_SEPARATOR, 0, false, 6, (Object) null);
            StringBuilder sbAppend = new StringBuilder().append(str2);
            String strSubstring = path.substring(iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            objectRef.element = sbAppend.append(StringsKt.replace$default(strSubstring, InternalZipConstants.ZIP_FILE_SEPARATOR, InternalZipConstants.ZIP_FILE_SEPARATOR + System.currentTimeMillis() + '_', false, 4, (Object) null)).toString();
            try {
                FileUtil.copyUriToFile(context, uri2, new File((String) objectRef.element));
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                Integer.valueOf(Log.e(TAG, "copyUriToFile error", e));
            }
        }
        Log.e(TAG, "targetPath:" + ((String) objectRef.element));
        return (String) objectRef.element;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x007c, code lost:
    
        if (r10 == false) goto L33;
     */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean hasFormatSuffix(android.net.Uri r10) {
        /*
            java.lang.String r1 = "ClipUtils"
            java.lang.String r0 = "isDragSupportType: "
            r2 = 0
            if (r10 == 0) goto L9d
            java.lang.String r10 = r10.toString()
            java.lang.String r3 = "toString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r3)
            r4 = r10
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4     // Catch: java.lang.Exception -> L80
            java.lang.String r5 = "."
            r8 = 6
            r9 = 0
            r6 = 0
            r7 = 0
            int r3 = kotlin.text.StringsKt.lastIndexOf$default(r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Exception -> L80
            r4 = 1
            int r3 = r3 + r4
            java.lang.String r3 = r10.substring(r3)     // Catch: java.lang.Exception -> L80
            java.lang.String r5 = "substring(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)     // Catch: java.lang.Exception -> L80
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L80
            r5.<init>(r0)     // Catch: java.lang.Exception -> L80
            java.lang.StringBuilder r0 = r5.append(r3)     // Catch: java.lang.Exception -> L80
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L80
            android.util.Log.d(r1, r0)     // Catch: java.lang.Exception -> L80
            int r0 = r3.hashCode()     // Catch: java.lang.Exception -> L80
            switch(r0) {
                case 97669: goto L76;
                case 102340: goto L6d;
                case 105441: goto L64;
                case 111145: goto L5b;
                case 3198679: goto L52;
                case 3198682: goto L49;
                case 3645340: goto L40;
                default: goto L3f;
            }     // Catch: java.lang.Exception -> L80
        L3f:
            goto L9d
        L40:
            java.lang.String r0 = "webp"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 == 0) goto L9d
            goto L7f
        L49:
            java.lang.String r0 = "heif"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 != 0) goto L7f
            goto L9d
        L52:
            java.lang.String r0 = "heic"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 != 0) goto L7f
            goto L9d
        L5b:
            java.lang.String r0 = "png"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 != 0) goto L7f
            goto L9d
        L64:
            java.lang.String r0 = "jpg"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 != 0) goto L7f
            goto L9d
        L6d:
            java.lang.String r0 = "gif"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 != 0) goto L7f
            goto L9d
        L76:
            java.lang.String r0 = "bmp"
            boolean r10 = r3.equals(r0)     // Catch: java.lang.Exception -> L80
            if (r10 != 0) goto L7f
            goto L9d
        L7f:
            return r4
        L80:
            r0 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "isDragSupportType: uriString is "
            r3.<init>(r4)
            java.lang.StringBuilder r10 = r3.append(r10)
            java.lang.String r3 = " , type is Error "
            java.lang.StringBuilder r10 = r10.append(r3)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r10 = r10.toString()
            android.util.Log.e(r1, r10)
        L9d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.clipboard.ClipManager.hasFormatSuffix(android.net.Uri):boolean");
    }

    @JvmStatic
    public static final boolean isContainContent(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = INSTANCE.getPrimaryClip((ClipboardManager) systemService);
        if (primaryClip != null) {
            int itemCount = primaryClip.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                ClipData.Item itemAt = primaryClip.getItemAt(i);
                Intrinsics.checkNotNullExpressionValue(itemAt, "getItemAt(...)");
                if (!TextUtils.isEmpty(itemAt.getHtmlText())) {
                    ClipManager clipManager = INSTANCE;
                    String htmlText = itemAt.getHtmlText();
                    Intrinsics.checkNotNullExpressionValue(htmlText, "getHtmlText(...)");
                    if (clipManager.htmlParse(htmlText)) {
                        return true;
                    }
                }
                if (itemAt.getUri() != null) {
                    Uri uri2 = itemAt.getUri();
                    if (uri2 != null) {
                        String strValueOf = String.valueOf(context.getContentResolver().getType(itemAt.getUri()));
                        if (StringsKt.contains$default((CharSequence) strValueOf, (CharSequence) "image/", false, 2, (Object) null)) {
                            INSTANCE.setMyUri(uri2);
                            return true;
                        }
                        if (Intrinsics.areEqual(SDKConstants.MimeType.HTML, strValueOf)) {
                            ClipManager clipManager2 = INSTANCE;
                            String strHtml = Jsoup.parse(itemAt.coerceToHtmlText(context).toString()).body().html();
                            Intrinsics.checkNotNullExpressionValue(strHtml, "html(...)");
                            if (clipManager2.htmlParse(strHtml)) {
                                return true;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                } else if (!TextUtils.isEmpty(itemAt.getText())) {
                    return true;
                }
            }
        }
        INSTANCE.clearUri();
        return false;
    }

    @JvmStatic
    public static final String getCopyText(Context context) {
        CharSequence text;
        String string;
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = INSTANCE.getPrimaryClip((ClipboardManager) systemService);
        return (primaryClip == null || primaryClip.getItemCount() <= 0 || (text = primaryClip.getItemAt(0).getText()) == null || (string = text.toString()) == null) ? "" : string;
    }

    private final boolean htmlParse(String htmlText) {
        Document document = Jsoup.parse(htmlText);
        final ArrayList arrayList = new ArrayList();
        NodeTraversor.traverse(new NodeVisitor() { // from class: com.miui.handwriting.common.clipboard.ClipManager.htmlParse.1
            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
            }

            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof Element) {
                    Element element = (Element) node;
                    String strAttr = element.attr("src");
                    if (!Intrinsics.areEqual(HtmlParser.ImageElement.TAG, element.tagName()) || TextUtils.isEmpty(strAttr)) {
                        return;
                    }
                    arrayList.add(strAttr);
                }
            }
        }, document);
        if (arrayList.isEmpty()) {
            return false;
        }
        Object obj = arrayList.get(0);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        setMyOnlineUri((String) obj);
        return true;
    }

    private final ClipData getPrimaryClip(ClipboardManager clipboardManager) {
        if (clipboardManager == null) {
            return null;
        }
        try {
            Object objInvoke = clipboardManager.getClass().getMethod("getUserPrimaryClip", new Class[0]).invoke(clipboardManager, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.content.ClipData");
            return (ClipData) objInvoke;
        } catch (Exception unused) {
            return clipboardManager.getPrimaryClip();
        }
    }

    @JvmStatic
    public static final boolean isPasteFromLocal(Context context, boolean isContentEmpty) {
        ClipDescription description;
        Intrinsics.checkNotNullParameter(context, "context");
        if (!isContentEmpty) {
            return false;
        }
        ClipManager clipManager = INSTANCE;
        if (uri == null && onlineUri == null) {
            return true;
        }
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipData primaryClip = clipManager.getPrimaryClip((ClipboardManager) systemService);
        return primaryClip == null || (description = primaryClip.getDescription()) == null || description.getTimestamp() <= mTimeStamp;
    }

    @JvmStatic
    public static final boolean isNeedNetwork() {
        return onlineUri != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String downloadImageToTemp(String imageUrl) {
        try {
            if (!isClipUrlAccurateValid(imageUrl)) {
                return null;
            }
            File file = Glide.with(HandWritingViewApplication.INSTANCE.getApplication()).downloadOnly().load(imageUrl).submit().get();
            Intrinsics.checkNotNullExpressionValue(file, "get(...)");
            return file.toString();
        } catch (Exception e) {
            Log.d(TAG, "downloadImageToTemp error: " + e);
            return "";
        }
    }

    private final boolean isClipUrlAccurateValid(String urlString) {
        String str = urlString;
        Matcher matcher = Patterns.WEB_URL.matcher(str);
        if (!matcher.find()) {
            return false;
        }
        String strGroup = matcher.group();
        Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
        return StringsKt.contains$default((CharSequence) str, (CharSequence) strGroup, false, 2, (Object) null);
    }
}
