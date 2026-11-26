package com.miui.handwriting.common.view.thumbnail;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.constants.Creation;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import xc.engine.storage.pdf.PDFManager;
import xc.engine.storage.pdf.PDFWriter;
import xc.engine.utils.FileUtil;

/* JADX INFO: compiled from: ExportImageManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0013\u0018\u0000 :2\u00020\u0001:\u0002:;B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001dJ$\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010 \u001a\u00020\u0012J\u0016\u0010!\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001dJ\u000e\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001dJ,\u0010#\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010$\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0012J,\u0010%\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010&\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0012J\u0016\u0010'\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001dJ\b\u0010(\u001a\u00020)H\u0002J\b\u0010*\u001a\u00020\u001bH\u0002J\b\u0010+\u001a\u00020\u001bH\u0002J\b\u0010,\u001a\u00020\u001bH\u0002J\u0018\u0010-\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\nH\u0002J\u000e\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u001dH\u0002JF\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u00020\u00192\u0006\u00101\u001a\u00020\f2\u0006\u00102\u001a\u00020\b2\u0006\u00103\u001a\u00020\b2\u0006\u00104\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u00052\u0006\u00106\u001a\u00020)2\u0006\u0010 \u001a\u00020\u0012J\u0006\u00107\u001a\u00020\u001bJ \u00108\u001a\u00020\f2\u0006\u00101\u001a\u00020\f2\u0006\u00102\u001a\u00020\b2\u0006\u00103\u001a\u00020\bH\u0002J\u000e\u00109\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u0012R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "mStepCount", "", "mIndexList", "", "mBitmapPreviewList", "Landroid/graphics/Bitmap;", "mBitmapExportList", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "mCompletedIndex", "mCallback", "Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager$ExportCallback;", "mOutFilePath", "mPdfFileName", "mExportType", "mWriter", "Lxc/engine/storage/pdf/PDFWriter;", "mLastExportTime", "", "setBitmapPreviewList", "", "list", "", "loadBitmapPreviewList", "exportType", CallMethod.ARG_CALLBACK, "addPreviewBitmap", "getBitmapPreviewList", "startExportImage", "outFilePath", "startExportPdf", "pdfFileName", "addExportBitmap", "isRepeatExport", "", "createPdfWriter", "exportingPdf", "exportingImage", "clear", "generateNextList", "exportThumbnail", "id", DoodleBitmap.KEY_BITMAP, "thumbnailWidth", "thumbnailHeight", "dirPath", "lastThumbnailName", "isAutoSave", "previewFinish", "cropThumbnail", "unRegisterCallback", "Companion", "ExportCallback", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ExportImageManager {
    public static final int ERROR_INDEX = 1;
    public static final int EXPORT_TIME_GAP = 200;
    private ExportCallback mCallback;
    private int mCompletedIndex;
    private long mLastExportTime;
    private String mOutFilePath;
    private String mPdfFileName;
    private PDFWriter mWriter;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<ExportImageManager> instance$delegate = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.miui.handwriting.common.view.thumbnail.ExportImageManager$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ExportImageManager.instance_delegate$lambda$0();
        }
    });
    private final String TAG = "ExportImageManager";
    private final int mStepCount = 10;
    private List<Integer> mIndexList = new ArrayList();
    private final List<Bitmap> mBitmapPreviewList = new ArrayList();
    private final List<Bitmap> mBitmapExportList = new ArrayList();
    private final CoroutineScope mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    private int mExportType = 2;

    /* JADX INFO: compiled from: ExportImageManager.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0005H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0005H&J\u0016\u0010\r\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH&J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H&J \u0010\u0011\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H&J \u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager$ExportCallback;", "", "loadPreviewFinish", "", "exportType", "", "requirePreviewList", "list", "", "loadPreviewError", "errorCode", "exportProgress", "progress", "requireNextList", "exportFinish", "path", "", "exportError", "exportThumbnailFinish", "id", "", "values", "Landroid/content/ContentValues;", "isAutoSave", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface ExportCallback {
        void exportError(int errorCode, int exportType, String path);

        void exportFinish(int exportType, String path);

        void exportProgress(int progress);

        void exportThumbnailFinish(long id, ContentValues values, boolean isAutoSave);

        void loadPreviewError(int errorCode);

        void loadPreviewFinish(int exportType);

        void requireNextList(List<Integer> list);

        void requirePreviewList(List<Integer> list);
    }

    private ExportImageManager() {
    }

    public final void setBitmapPreviewList(List<Bitmap> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        clear(this.mBitmapPreviewList);
        this.mBitmapPreviewList.addAll(list);
    }

    public final void loadBitmapPreviewList(int exportType, List<Integer> list, ExportCallback callback) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (list.isEmpty()) {
            Log.e(this.TAG, "preview list is empty");
            return;
        }
        clear(this.mBitmapPreviewList);
        this.mExportType = exportType;
        this.mCallback = callback;
        this.mIndexList = list;
        this.mCompletedIndex = 0;
        List<Integer> listGenerateNextList = generateNextList();
        ExportCallback exportCallback = this.mCallback;
        if (exportCallback != null) {
            exportCallback.requirePreviewList(listGenerateNextList);
        }
    }

    public final void addPreviewBitmap(List<Bitmap> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.mBitmapPreviewList.addAll(list);
        this.mCompletedIndex += list.size();
        Log.d(this.TAG, "add preview index:" + this.mCompletedIndex + "----total:" + this.mIndexList.size());
        if (this.mCompletedIndex == this.mIndexList.size()) {
            ExportCallback exportCallback = this.mCallback;
            if (exportCallback != null) {
                exportCallback.loadPreviewFinish(this.mExportType);
                return;
            }
            return;
        }
        if (this.mCompletedIndex < this.mIndexList.size()) {
            ExportCallback exportCallback2 = this.mCallback;
            if (exportCallback2 != null) {
                exportCallback2.requirePreviewList(generateNextList());
                return;
            }
            return;
        }
        Log.e(this.TAG, "preview error completed:" + this.mCompletedIndex + "-----total:" + this.mIndexList.size());
        ExportCallback exportCallback3 = this.mCallback;
        if (exportCallback3 != null) {
            exportCallback3.loadPreviewError(1);
        }
    }

    public final List<Bitmap> getBitmapPreviewList() {
        return this.mBitmapPreviewList;
    }

    public final void startExportImage(int exportType, List<Integer> list, String outFilePath, ExportCallback callback) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(outFilePath, "outFilePath");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isRepeatExport()) {
            Log.e(this.TAG, "repeat export image return");
            return;
        }
        if (list.isEmpty()) {
            Log.e(this.TAG, "list is empty");
            return;
        }
        this.mExportType = exportType;
        this.mIndexList = list;
        this.mCompletedIndex = 0;
        this.mOutFilePath = outFilePath;
        this.mCallback = callback;
        List<Integer> listGenerateNextList = generateNextList();
        ExportCallback exportCallback = this.mCallback;
        if (exportCallback != null) {
            exportCallback.requireNextList(listGenerateNextList);
        }
    }

    public final void startExportPdf(int exportType, List<Integer> list, String pdfFileName, ExportCallback callback) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(pdfFileName, "pdfFileName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (isRepeatExport()) {
            Log.e(this.TAG, "repeat export pdf return");
            return;
        }
        if (list.isEmpty()) {
            Log.e(this.TAG, "list is empty");
            return;
        }
        this.mExportType = exportType;
        this.mIndexList = list;
        this.mCompletedIndex = 0;
        this.mPdfFileName = pdfFileName;
        this.mCallback = callback;
        List<Integer> listGenerateNextList = generateNextList();
        ExportCallback exportCallback = this.mCallback;
        if (exportCallback != null) {
            exportCallback.requireNextList(listGenerateNextList);
        }
    }

    public final void addExportBitmap(List<Bitmap> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        Log.d(this.TAG, "add export bitmap");
        clear(this.mBitmapExportList);
        this.mBitmapExportList.addAll(list);
        int i = this.mExportType;
        if (i == 1) {
            exportingPdf();
        } else if (i == 2) {
            exportingImage();
        } else {
            Log.e(this.TAG, "error type:" + this.mExportType);
        }
    }

    private final boolean isRepeatExport() {
        if (System.currentTimeMillis() - this.mLastExportTime < 200) {
            return true;
        }
        this.mLastExportTime = System.currentTimeMillis();
        return false;
    }

    private final void createPdfWriter() {
        if (this.mWriter == null) {
            PDFManager pDFManager = PDFManager.getInstance();
            String str = this.mPdfFileName;
            String str2 = null;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPdfFileName");
                str = null;
            }
            this.mWriter = pDFManager.createWriter(str);
            String str3 = this.mPdfFileName;
            if (str3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPdfFileName");
            } else {
                str2 = str3;
            }
            File file = new File(str2);
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            PDFWriter pDFWriter = this.mWriter;
            Intrinsics.checkNotNull(pDFWriter);
            pDFWriter.create();
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingPdf$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ExportImageManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingPdf$1", f = "ExportImageManager.kt", i = {0, 0}, l = {R2.array.ai_panel_widget_blend_colors}, m = "invokeSuspend", n = {DoodleBitmap.KEY_BITMAP, IntegerTokenConverter.CONVERTER_KEY}, s = {"L$0", "I$0"})
    static final class C02471 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        int I$1;
        Object L$0;
        int label;

        C02471(Continuation<? super C02471> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ExportImageManager.this.new C02471(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02471) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0034  */
        /* JADX WARN: Code duplicated, block: B:13:0x0042  */
        /* JADX WARN: Code duplicated, block: B:20:0x007f A[RETURN] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x007d -> B:21:0x0080). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                Method dump skipped, instruction units count: 374
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.view.thumbnail.ExportImageManager.C02471.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingPdf$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportImageManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingPdf$1$1", f = "ExportImageManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ ExportImageManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00691(ExportImageManager exportImageManager, Continuation<? super C00691> continuation) {
                super(2, continuation);
                this.this$0 = exportImageManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00691(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ExportCallback exportCallback;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.mCompletedIndex++;
                    if (this.this$0.mCompletedIndex <= this.this$0.mIndexList.size() && (exportCallback = this.this$0.mCallback) != null) {
                        exportCallback.exportProgress((this.this$0.mCompletedIndex * 100) / this.this$0.mIndexList.size());
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void exportingPdf() {
        createPdfWriter();
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new C02471(null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingImage$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ExportImageManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingImage$1", f = "ExportImageManager.kt", i = {0, 0, 0, 0}, l = {R2.array.chinese_symbol_animals}, m = "invokeSuspend", n = {"outFile", "outName", DoodleBitmap.KEY_BITMAP, IntegerTokenConverter.CONVERTER_KEY}, s = {"L$0", "L$1", "L$2", "I$0"})
    static final class C02461 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        C02461(Continuation<? super C02461> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ExportImageManager.this.new C02461(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02461) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x006f  */
        /* JADX WARN: Code duplicated, block: B:18:0x007d  */
        /* JADX WARN: Code duplicated, block: B:20:0x00aa  */
        /* JADX WARN: Code duplicated, block: B:21:0x00ae  */
        /* JADX WARN: Code duplicated, block: B:24:0x00f8 A[RETURN] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x00f6 -> B:25:0x00f9). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                Method dump skipped, instruction units count: 449
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.handwriting.common.view.thumbnail.ExportImageManager.C02461.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingImage$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportImageManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportingImage$1$1", f = "ExportImageManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00681 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ ExportImageManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00681(ExportImageManager exportImageManager, Continuation<? super C00681> continuation) {
                super(2, continuation);
                this.this$0 = exportImageManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00681(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00681) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ExportCallback exportCallback;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.mCompletedIndex++;
                    if (this.this$0.mCompletedIndex <= this.this$0.mIndexList.size() && (exportCallback = this.this$0.mCallback) != null) {
                        exportCallback.exportProgress((this.this$0.mCompletedIndex * 100) / this.this$0.mIndexList.size());
                    }
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void exportingImage() {
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new C02461(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clear(List<Bitmap> list) {
        Log.d(this.TAG, "clear bitmap list");
        if (list.isEmpty()) {
            return;
        }
        for (Bitmap bitmap : list) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        list.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Integer> generateNextList() {
        int size = this.mCompletedIndex + this.mStepCount;
        if (size > this.mIndexList.size()) {
            size = this.mIndexList.size();
        }
        return this.mIndexList.subList(this.mCompletedIndex, size);
    }

    public final void exportThumbnail(long id, Bitmap bitmap, int thumbnailWidth, int thumbnailHeight, String dirPath, String lastThumbnailName, boolean isAutoSave, ExportCallback callback) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(dirPath, "dirPath");
        Intrinsics.checkNotNullParameter(lastThumbnailName, "lastThumbnailName");
        Intrinsics.checkNotNullParameter(callback, "callback");
        File file = new File(FilePathConstant.getEntFilePathThumb(dirPath), String.valueOf(System.currentTimeMillis()));
        Log.d(this.TAG, "last:" + lastThumbnailName + "----current:" + file.getAbsolutePath());
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new AnonymousClass1(bitmap, thumbnailWidth, thumbnailHeight, file, lastThumbnailName, callback, id, isAutoSave, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportThumbnail$1, reason: invalid class name */
    /* JADX INFO: compiled from: ExportImageManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportThumbnail$1", f = "ExportImageManager.kt", i = {0, 0}, l = {256}, m = "invokeSuspend", n = {"resultBitmap", "contentValues"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Bitmap $bitmap;
        final /* synthetic */ ExportCallback $callback;
        final /* synthetic */ long $id;
        final /* synthetic */ boolean $isAutoSave;
        final /* synthetic */ String $lastThumbnailName;
        final /* synthetic */ File $outFile;
        final /* synthetic */ int $thumbnailHeight;
        final /* synthetic */ int $thumbnailWidth;
        Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Bitmap bitmap, int i, int i2, File file, String str, ExportCallback exportCallback, long j, boolean z, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$bitmap = bitmap;
            this.$thumbnailWidth = i;
            this.$thumbnailHeight = i2;
            this.$outFile = file;
            this.$lastThumbnailName = str;
            this.$callback = exportCallback;
            this.$id = j;
            this.$isAutoSave = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ExportImageManager.this.new AnonymousClass1(this.$bitmap, this.$thumbnailWidth, this.$thumbnailHeight, this.$outFile, this.$lastThumbnailName, this.$callback, this.$id, this.$isAutoSave, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap bitmapCropThumbnail = ExportImageManager.this.cropThumbnail(this.$bitmap, this.$thumbnailWidth, this.$thumbnailHeight);
                BitmapUtil.saveBitmapToFile(bitmapCropThumbnail, this.$outFile, true);
                ContentValues contentValues = new ContentValues();
                if (!this.$outFile.exists()) {
                    Log.e(ExportImageManager.this.TAG, "save thumbnail fail");
                    contentValues.put(Creation.THUMBNAIL_NAME, this.$lastThumbnailName);
                } else {
                    FileUtil.deleteFile(new File(this.$lastThumbnailName));
                    contentValues.put(Creation.THUMBNAIL_NAME, this.$outFile.getAbsolutePath());
                }
                this.L$0 = SpillingKt.nullOutSpilledVariable(bitmapCropThumbnail);
                this.L$1 = SpillingKt.nullOutSpilledVariable(contentValues);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C00671(this.$callback, this.$id, contentValues, this.$isAutoSave, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportThumbnail$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportImageManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.handwriting.common.view.thumbnail.ExportImageManager$exportThumbnail$1$1", f = "ExportImageManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00671 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ ExportCallback $callback;
            final /* synthetic */ ContentValues $contentValues;
            final /* synthetic */ long $id;
            final /* synthetic */ boolean $isAutoSave;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00671(ExportCallback exportCallback, long j, ContentValues contentValues, boolean z, Continuation<? super C00671> continuation) {
                super(2, continuation);
                this.$callback = exportCallback;
                this.$id = j;
                this.$contentValues = contentValues;
                this.$isAutoSave = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00671(this.$callback, this.$id, this.$contentValues, this.$isAutoSave, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00671) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.exportThumbnailFinish(this.$id, this.$contentValues, this.$isAutoSave);
                return Unit.INSTANCE;
            }
        }
    }

    public final void previewFinish() {
        this.mBitmapPreviewList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bitmap cropThumbnail(Bitmap bitmap, int thumbnailWidth, int thumbnailHeight) {
        float f = thumbnailWidth;
        float f2 = thumbnailHeight;
        float fMin = Math.min(bitmap.getWidth() / f, bitmap.getHeight() / f2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(thumbnailWidth, thumbnailHeight, Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Rect rect = new Rect(0, 0, (int) (f * fMin), (int) (f2 * fMin));
        Rect rect2 = new Rect(0, 0, thumbnailWidth, thumbnailHeight);
        Log.d(this.TAG, "bitmap width:" + bitmap.getWidth() + "-----height:" + bitmap.getHeight() + "-----thumbnail width:" + thumbnailWidth + "----height:" + thumbnailHeight + "---------src:" + rect + "-----dst:" + rect2);
        if (!bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, rect, rect2, (Paint) null);
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public final void unRegisterCallback(ExportCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (Intrinsics.areEqual(this.mCallback, callback)) {
            this.mCallback = null;
        }
    }

    /* JADX INFO: compiled from: ExportImageManager.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "instance", "Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager;", "getInstance", "()Lcom/miui/handwriting/common/view/thumbnail/ExportImageManager;", "instance$delegate", "Lkotlin/Lazy;", "ERROR_INDEX", "", "EXPORT_TIME_GAP", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ExportImageManager getInstance() {
            return (ExportImageManager) ExportImageManager.instance$delegate.getValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExportImageManager instance_delegate$lambda$0() {
        return new ExportImageManager();
    }
}
