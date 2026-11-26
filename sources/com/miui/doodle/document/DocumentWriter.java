package com.miui.doodle.document;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.png.PNGChunk;
import com.miui.doodle.document.png.PNGReader;
import com.miui.doodle.document.png.PNGWriter;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.doodle.utils.Utils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.onetrack.api.ba;
import defpackage.R2;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONException;

/* JADX INFO: compiled from: DocumentWriter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 (2\u00020\u0001:\u0002'(B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\"\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J4\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u00072\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010\u0012\u001a\u00020\u00112\b\u0010%\u001a\u0004\u0018\u00010\u0007H\u0002J\u0006\u0010&\u001a\u00020\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/miui/doodle/document/DocumentWriter;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "doodleDocument", "Lcom/miui/doodle/document/DoodleDocument;", "filepath", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/document/DoodleDocument;Ljava/lang/String;)V", "pngWriter", "Lcom/miui/doodle/document/png/PNGWriter;", "pngReader", "Lcom/miui/doodle/document/png/PNGReader;", "doodlePNGFile", "Ljava/io/File;", "write", "", "ignoreCache", "addZipEntry", "", "zos", "Ljava/util/zip/ZipOutputStream;", "entryName", "data", "", "writeDocumentInfo", "writePreview", "writePreviewByCache", "cachekey", "writeLayers", "addBitmapEntry", "Lcom/miui/doodle/document/DocumentWriter$ZipItem;", "id", "layerBasePath", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "cacheKey", "setIsNeedCacheBgImageToFile", "ZipItem", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DocumentWriter {
    public static final String BACKGROUND = "background";
    public static final String LAYERS_BASE_PATH = "layers";
    public static final String LAYERS_JSON = "layers.json";
    public static final String LAYER_INFO_JSON = "layer-info.json";
    public static final String MANIFEST = "manifest.json";
    public static final String PREVIEW_IMAGE = "preview.png";
    private static final String TAG = "DocumentWriter";
    private final DoodleDocument doodleDocument;
    private final File doodlePNGFile;
    private PNGReader pngReader;
    private PNGWriter pngWriter;

    public DocumentWriter(Context context, DoodleDocument doodleDocument, String filepath) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(doodleDocument, "doodleDocument");
        Intrinsics.checkNotNullParameter(filepath, "filepath");
        this.doodleDocument = doodleDocument;
        this.doodlePNGFile = new File(filepath);
    }

    /* JADX INFO: compiled from: DocumentWriter.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/miui/doodle/document/DocumentWriter$ZipItem;", "", "path", "", "data", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;[B)V", "getPath", "()Ljava/lang/String;", "getData", "()[B", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "component1", "component2", "copy", "toString", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class ZipItem {
        private final byte[] data;
        private final String path;

        public static /* synthetic */ ZipItem copy$default(ZipItem zipItem, String str, byte[] bArr, int i, Object obj) {
            if ((i & 1) != 0) {
                str = zipItem.path;
            }
            if ((i & 2) != 0) {
                bArr = zipItem.data;
            }
            return zipItem.copy(str, bArr);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getPath() {
            return this.path;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final byte[] getData() {
            return this.data;
        }

        public final ZipItem copy(String path, byte[] data) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(data, "data");
            return new ZipItem(path, data);
        }

        public String toString() {
            return "ZipItem(path=" + this.path + ", data=" + Arrays.toString(this.data) + ")";
        }

        public ZipItem(String path, byte[] data) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(data, "data");
            this.path = path;
            this.data = data;
        }

        public final String getPath() {
            return this.path;
        }

        public final byte[] getData() {
            return this.data;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.miui.doodle.document.DocumentWriter.ZipItem");
            ZipItem zipItem = (ZipItem) other;
            return Intrinsics.areEqual(this.path, zipItem.path) && Arrays.equals(this.data, zipItem.data);
        }

        public int hashCode() {
            return (this.path.hashCode() * 31) + Arrays.hashCode(this.data);
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00e1 A[Catch: Exception -> 0x015d, TryCatch #1 {Exception -> 0x015d, blocks: (B:3:0x0017, B:7:0x00a1, B:9:0x00a5, B:10:0x00a9, B:26:0x00d5, B:28:0x00e1, B:29:0x00e6, B:31:0x0110, B:13:0x00ae, B:22:0x00cc, B:24:0x00d0, B:34:0x014c, B:36:0x0150, B:40:0x015c, B:39:0x0159, B:5:0x0022, B:6:0x0058, B:21:0x00ba), top: B:46:0x0017, inners: #0, #2, #4 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0110 A[Catch: Exception -> 0x015d, TRY_LEAVE, TryCatch #1 {Exception -> 0x015d, blocks: (B:3:0x0017, B:7:0x00a1, B:9:0x00a5, B:10:0x00a9, B:26:0x00d5, B:28:0x00e1, B:29:0x00e6, B:31:0x0110, B:13:0x00ae, B:22:0x00cc, B:24:0x00d0, B:34:0x014c, B:36:0x0150, B:40:0x015c, B:39:0x0159, B:5:0x0022, B:6:0x0058, B:21:0x00ba), top: B:46:0x0017, inners: #0, #2, #4 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [T, java.util.zip.ZipOutputStream] */
    public final boolean write(boolean ignoreCache) {
        byte[] bArr;
        PNGChunk pNGChunkCreateDoodleChunk;
        List<PNGChunk> list;
        Object objWritePreview;
        ZipOutputStream zipOutputStream;
        Object obj = "write: documentInfo cost ";
        long jCurrentTimeMillis = System.currentTimeMillis();
        System.currentTimeMillis();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            try {
                try {
                    try {
                        objectRef.element = new ZipOutputStream(byteArrayOutputStream, StandardCharsets.UTF_8);
                        writeDocumentInfo((ZipOutputStream) objectRef.element);
                        Logger.INSTANCE.d(TAG, "write: documentInfo cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        objWritePreview = writePreview((ZipOutputStream) objectRef.element);
                        try {
                            Logger.INSTANCE.d(TAG, "write: previewData cost " + (System.currentTimeMillis() - jCurrentTimeMillis2));
                            BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(ignoreCache, objectRef, null), 1, null);
                            Logger.INSTANCE.d(TAG, "write: save bitmap " + (System.currentTimeMillis() - jCurrentTimeMillis));
                            ((ZipOutputStream) objectRef.element).flush();
                            ((ZipOutputStream) objectRef.element).close();
                            bArr = objWritePreview;
                            if (objectRef.element != 0) {
                                zipOutputStream = (ZipOutputStream) objectRef.element;
                                obj = objWritePreview;
                                zipOutputStream.close();
                                bArr = obj;
                            }
                        } catch (Exception e) {
                            e = e;
                            Logger.INSTANCE.e(TAG, "write failed." + e);
                            bArr = objWritePreview;
                            if (objectRef.element != 0) {
                                zipOutputStream = (ZipOutputStream) objectRef.element;
                                obj = objWritePreview;
                            }
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            if (this.doodlePNGFile.exists()) {
                                this.doodlePNGFile.delete();
                            }
                            this.doodlePNGFile.createNewFile();
                            this.pngReader = new PNGReader(null, bArr);
                            PNGWriter pNGWriter = new PNGWriter(this.doodlePNGFile);
                            this.pngWriter = pNGWriter;
                            Intrinsics.checkNotNull(pNGWriter);
                            Intrinsics.checkNotNull(byteArray);
                            pNGChunkCreateDoodleChunk = pNGWriter.createDoodleChunk(byteArray);
                            PNGReader pNGReader = this.pngReader;
                            Intrinsics.checkNotNull(pNGReader);
                            list = pNGReader.read();
                            if (list != null) {
                                List mutableList = CollectionsKt.toMutableList((Collection) list);
                                mutableList.add(list.size() - 1, pNGChunkCreateDoodleChunk);
                                PNGWriter pNGWriter2 = this.pngWriter;
                                Intrinsics.checkNotNull(pNGWriter2);
                                pNGWriter2.write(CollectionsKt.toList(mutableList));
                            }
                            Logger.INSTANCE.d(TAG, "Write: finished: " + (System.currentTimeMillis() - jCurrentTimeMillis));
                            return true;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        objWritePreview = null;
                    }
                    byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                    if (this.doodlePNGFile.exists()) {
                        this.doodlePNGFile.delete();
                    }
                    this.doodlePNGFile.createNewFile();
                    this.pngReader = new PNGReader(null, bArr);
                    PNGWriter pNGWriter3 = new PNGWriter(this.doodlePNGFile);
                    this.pngWriter = pNGWriter3;
                    Intrinsics.checkNotNull(pNGWriter3);
                    Intrinsics.checkNotNull(byteArray2);
                    pNGChunkCreateDoodleChunk = pNGWriter3.createDoodleChunk(byteArray2);
                    PNGReader pNGReader2 = this.pngReader;
                    Intrinsics.checkNotNull(pNGReader2);
                    list = pNGReader2.read();
                    if (list != null) {
                        List mutableList2 = CollectionsKt.toMutableList((Collection) list);
                        mutableList2.add(list.size() - 1, pNGChunkCreateDoodleChunk);
                        PNGWriter pNGWriter4 = this.pngWriter;
                        Intrinsics.checkNotNull(pNGWriter4);
                        pNGWriter4.write(CollectionsKt.toList(mutableList2));
                    }
                    Logger.INSTANCE.d(TAG, "Write: finished: " + (System.currentTimeMillis() - jCurrentTimeMillis));
                    return true;
                } catch (Throwable th) {
                    if (objectRef.element == 0) {
                        throw th;
                    }
                    try {
                        ((ZipOutputStream) objectRef.element).close();
                        throw th;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        throw th;
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                bArr = obj;
            }
        } catch (Exception e5) {
            Logger.INSTANCE.e(TAG, "write file failed." + e5);
            return false;
        }
    }

    /* JADX INFO: renamed from: com.miui.doodle.document.DocumentWriter$write$1, reason: invalid class name */
    /* JADX INFO: compiled from: DocumentWriter.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.document.DocumentWriter$write$1", f = "DocumentWriter.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, l = {R2.array.ai_input_gradient_border_colors}, m = "invokeSuspend", n = {"$this$runBlocking", "jobs", "basePath", "layersForSave", "layerIterator", "$this$forEach$iv", "element$iv", ba.e, "subStart", "subEnd", "$i$f$forEach", "$i$a$-forEach-DocumentWriter$write$1$6"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$9", "L$10", "J$0", "J$1", "I$0", "I$1"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $ignoreCache;
        final /* synthetic */ Ref.ObjectRef<ZipOutputStream> $zos;
        int I$0;
        int I$1;
        long J$0;
        long J$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$10;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(boolean z, Ref.ObjectRef<ZipOutputStream> objectRef, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$ignoreCache = z;
            this.$zos = objectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = DocumentWriter.this.new AnonymousClass1(this.$ignoreCache, this.$zos, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:58:0x031b  */
        /* JADX WARN: Code duplicated, block: B:60:0x036f A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:61:0x0370  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x0370 -> B:62:0x037a). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r29) {
            /*
                Method dump skipped, instruction units count: 925
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.doodle.document.DocumentWriter.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: com.miui.doodle.document.DocumentWriter$write$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: DocumentWriter.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/doodle/document/DocumentWriter$ZipItem;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.document.DocumentWriter$write$1$1", f = "DocumentWriter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ZipItem>, Object> {
            final /* synthetic */ boolean $ignoreCache;
            int label;
            final /* synthetic */ DocumentWriter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00331(boolean z, DocumentWriter documentWriter, Continuation<? super C00331> continuation) {
                super(2, continuation);
                this.$ignoreCache = z;
                this.this$0 = documentWriter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00331(this.$ignoreCache, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ZipItem> continuation) {
                return ((C00331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                boolean z;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                boolean z2 = this.$ignoreCache;
                if (!this.this$0.doodleDocument.getDocumentInfo().getEditImage() || TextUtils.isEmpty(this.this$0.doodleDocument.getCacheBgImageKey())) {
                    z = z2;
                } else {
                    if (this.this$0.doodleDocument.getMIsNeedCacheBgImageToFile()) {
                        this.this$0.doodleDocument.cacheBgImageToFile();
                        this.this$0.doodleDocument.setMIsNeedCacheBgImageToFile(false);
                    }
                    z = false;
                }
                DocumentWriter documentWriter = this.this$0;
                return documentWriter.addBitmapEntry("background", "", documentWriter.doodleDocument.getBackgroundBitmap(), z, this.this$0.doodleDocument.getCacheBgImageKey());
            }
        }

        /* JADX INFO: renamed from: com.miui.doodle.document.DocumentWriter$write$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: DocumentWriter.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lcom/miui/doodle/document/DocumentWriter$ZipItem;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.document.DocumentWriter$write$1$2", f = "DocumentWriter.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ZipItem>, Object> {
            final /* synthetic */ boolean $ignoreCache;
            final /* synthetic */ Layer $layer;
            final /* synthetic */ String $layerBasePath;
            int label;
            final /* synthetic */ DocumentWriter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(DocumentWriter documentWriter, Layer layer, String str, boolean z, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = documentWriter;
                this.$layer = layer;
                this.$layerBasePath = str;
                this.$ignoreCache = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, this.$layer, this.$layerBasePath, this.$ignoreCache, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ZipItem> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    return this.this$0.addBitmapEntry(this.$layer.getId(), this.$layerBasePath, this.$layer.getBackgroundBitmap(), this.$ignoreCache, this.$layer.getCacheImageKey());
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addZipEntry(ZipOutputStream zos, String entryName, byte[] data) throws IOException {
        zos.putNextEntry(new ZipEntry(entryName));
        if (data != null) {
            if (!(data.length == 0)) {
                zos.write(data);
                new OutputStreamWriter(zos, Charsets.UTF_8);
            }
        }
        zos.closeEntry();
    }

    private final void writeDocumentInfo(ZipOutputStream zos) throws JSONException, IOException {
        String string = this.doodleDocument.getDocumentInfo().toJSONObject().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Charset UTF_8 = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
        byte[] bytes = string.getBytes(UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        addZipEntry(zos, MANIFEST, bytes);
    }

    private final byte[] writePreview(ZipOutputStream zos) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap previewBitmap = this.doodleDocument.getPreviewBitmap();
        if (previewBitmap != null) {
            previewBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        addZipEntry(zos, PREVIEW_IMAGE, byteArray);
        Intrinsics.checkNotNull(byteArray);
        return byteArray;
    }

    private final byte[] writePreviewByCache(ZipOutputStream zos, String cachekey) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), cachekey));
        byte[] bArr = new byte[1024];
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                fileInputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                addZipEntry(zos, PREVIEW_IMAGE, byteArray);
                Intrinsics.checkNotNull(byteArray);
                return byteArray;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeLayers(ZipOutputStream zos) throws JSONException, IOException {
        byte[] layerJsonCache;
        List<Layer> layers = this.doodleDocument.getLayers();
        if (layers.isEmpty()) {
            return;
        }
        LayerInfo layerInfo = new LayerInfo();
        int size = layers.size();
        for (int i = 1; i < size; i++) {
            Layer layer = layers.get(i);
            String str = "/layers/" + layer.getId();
            String str2 = str + "/layer-info.json";
            LayerInfo.LayerInfoItem layerInfoItem = new LayerInfo.LayerInfoItem();
            layerInfoItem.setBasePath(str);
            layerInfoItem.setInfoFile(str2);
            layerInfo.getLayerInfoItems().add(layerInfoItem);
            if (layer.getCacheDataInfoKey() != null) {
                layerJsonCache = layer.getLayerJsonCache();
            } else {
                String string = layer.toJSONObject().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                Charset UTF_8 = StandardCharsets.UTF_8;
                Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                byte[] bytes = string.getBytes(UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                layerJsonCache = bytes;
            }
            addZipEntry(zos, str2, layerJsonCache);
        }
        String string2 = layerInfo.toJSONObject().toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        Charset UTF_9 = StandardCharsets.UTF_8;
        Intrinsics.checkNotNullExpressionValue(UTF_9, "UTF_8");
        byte[] bytes2 = string2.getBytes(UTF_9);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        addZipEntry(zos, LAYERS_JSON, bytes2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:18:0x0076  */
    public final ZipItem addBitmapEntry(String id, String layerBasePath, Bitmap bitmap, boolean ignoreCache, String cacheKey) {
        boolean zCompress;
        long jCurrentTimeMillis = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean z = true;
        byte[] byteArray = new byte[1];
        if (!ignoreCache && !TextUtils.isEmpty(cacheKey)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(Utils.INSTANCE.getDoodleCacheDir(), cacheKey));
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                }
                fileInputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray2 = byteArrayOutputStream.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray2, "toByteArray(...)");
                byteArray = byteArray2;
            } catch (Exception e) {
                Logger.INSTANCE.w(TAG, "write: addBitmapEntryByCache error " + e);
            }
        } else {
            if (Build.VERSION.SDK_INT >= 30) {
                if (bitmap != null) {
                    zCompress = bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 50, byteArrayOutputStream);
                } else {
                    zCompress = false;
                }
            } else if (bitmap != null) {
                zCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            } else {
                zCompress = false;
            }
            Logger.INSTANCE.d(TAG, "write: addBitmapEntry bitmap.compress result " + zCompress);
            byteArray = byteArrayOutputStream.toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            z = false;
        }
        Logger.INSTANCE.d(TAG, "write: addBitmapEntry " + id + " cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " byCache " + z);
        return new ZipItem(layerBasePath + InternalZipConstants.ZIP_FILE_SEPARATOR + id, byteArray);
    }

    public final void setIsNeedCacheBgImageToFile() {
        if (this.doodleDocument.getMIsNeedCacheBgImageToFile()) {
            this.doodleDocument.setMIsNeedCacheBgImageToFile(false);
        }
    }
}
