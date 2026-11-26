package xc.engine.core.graphics.engine.systems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: ExportSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000  2\u00020\u0001:\u0003 !\"B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00020\u0010J0\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u00142\u001a\u0010\u0015\u001a\u0016\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0014\u0012\u0004\u0012\u00020\u00120\u0016J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0010H\u0002J8\u0010\u0019\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u00142\u0006\u0010\u0018\u001a\u00020\u00102\u001a\u0010\u0015\u001a\u0016\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0014\u0012\u0004\u0012\u00020\u00120\u0016J,\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00102\u0014\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u00120\u0016J4\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u00102\u0014\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u00120\u0016J\u0006\u0010\u001f\u001a\u00020\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lxc/engine/core/graphics/engine/systems/ExportSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "mScope", "Lkotlinx/coroutines/CoroutineScope;", "exportArea", "Landroid/graphics/Bitmap;", "areaWS", "Landroid/graphics/RectF;", "exportSize", "Landroid/graphics/Point;", "getPageThumbnail", "pageIndex", "", "getPageThumbnailList", "", "list", "", CallMethod.ARG_CALLBACK, "Lkotlin/Function1;", "getPageThumbnailCustomSize", "bitmapWidth", "getPageThumbnailCustomList", "getTargetPageThumbnail", "index", "getUnLimitThumbnail", "rectF", "bitmapHeight", "finish", "Companion", "ExportParam", "ExportAreaTask", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ExportSystem extends BaseEngineStruct {
    public static final float SINGLE_MAX_LENGTH = 8192.0f;
    private final CoroutineScope mScope;
    private static final String TAG = "ExportSystem";

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExportSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.mScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    }

    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lxc/engine/core/graphics/engine/systems/ExportSystem$ExportParam;", "", "curveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", "areaWS", "Landroid/graphics/RectF;", "exportSize", "Landroid/graphics/Point;", "renderLatch", "Ljava/util/concurrent/CountDownLatch;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;Landroid/graphics/RectF;Landroid/graphics/Point;Ljava/util/concurrent/CountDownLatch;Landroid/graphics/Bitmap;)V", "getCurveEngine", "()Lxc/engine/core/graphics/engine/MiCurveEngine;", "getAreaWS", "()Landroid/graphics/RectF;", "getExportSize", "()Landroid/graphics/Point;", "getRenderLatch", "()Ljava/util/concurrent/CountDownLatch;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ExportParam {
        private final RectF areaWS;
        private Bitmap bitmap;
        private final MiCurveEngine curveEngine;
        private final Point exportSize;
        private final CountDownLatch renderLatch;

        public ExportParam(MiCurveEngine curveEngine, RectF areaWS, Point exportSize, CountDownLatch renderLatch, Bitmap bitmap) {
            Intrinsics.checkNotNullParameter(curveEngine, "curveEngine");
            Intrinsics.checkNotNullParameter(areaWS, "areaWS");
            Intrinsics.checkNotNullParameter(exportSize, "exportSize");
            Intrinsics.checkNotNullParameter(renderLatch, "renderLatch");
            this.curveEngine = curveEngine;
            this.areaWS = areaWS;
            this.exportSize = exportSize;
            this.renderLatch = renderLatch;
            this.bitmap = bitmap;
        }

        public /* synthetic */ ExportParam(MiCurveEngine miCurveEngine, RectF rectF, Point point, CountDownLatch countDownLatch, Bitmap bitmap, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(miCurveEngine, rectF, point, countDownLatch, (i & 16) != 0 ? null : bitmap);
        }

        public final MiCurveEngine getCurveEngine() {
            return this.curveEngine;
        }

        public final RectF getAreaWS() {
            return this.areaWS;
        }

        public final Point getExportSize() {
            return this.exportSize;
        }

        public final CountDownLatch getRenderLatch() {
            return this.renderLatch;
        }

        public final Bitmap getBitmap() {
            return this.bitmap;
        }

        public final void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lxc/engine/core/graphics/engine/systems/ExportSystem$ExportAreaTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inParam", "Lxc/engine/core/graphics/engine/systems/ExportSystem$ExportParam;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/ExportSystem$ExportParam;)V", "param", "getParam", "()Lxc/engine/core/graphics/engine/systems/ExportSystem$ExportParam;", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ExportAreaTask extends BaseAsyncGLTask {
        private final ExportParam param;

        public ExportAreaTask(ExportParam inParam) {
            Intrinsics.checkNotNullParameter(inParam, "inParam");
            this.param = inParam;
        }

        public final ExportParam getParam() {
            return this.param;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) throws Throwable {
            int i;
            boolean z;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            Intrinsics.checkNotNullParameter(res, "res");
            int iCreateTexture = GLUtils.INSTANCE.createTexture(this.param.getExportSize().x, this.param.getExportSize().y);
            int iCreateTexture2 = GLUtils.INSTANCE.createTexture(this.param.getExportSize().x, this.param.getExportSize().y);
            int iCreateTexture3 = GLUtils.INSTANCE.createTexture(this.param.getExportSize().x, this.param.getExportSize().y);
            int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture);
            int iCreateFramebufferFromTexture2 = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture2);
            int iCreateFramebufferFromTexture3 = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture3);
            boolean zDoesAreaHasCurve = this.param.getCurveEngine().getCurveSystem().getMarkCurveLayer().doesAreaHasCurve(this.param.getAreaWS());
            boolean z2 = this.param.getCurveEngine().getPageSystem().getIsPdfMode() && zDoesAreaHasCurve;
            if (z2) {
                this.param.getCurveEngine().getBgSystem().renderArea(iCreateFramebufferFromTexture, this.param.getExportSize(), this.param.getAreaWS());
            } else {
                GLES30.glViewport(0, 0, this.param.getExportSize().x, this.param.getExportSize().y);
                GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
                GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES30.glClear(16384);
            }
            ImageSystem.renderArea$default(this.param.getCurveEngine().getImgSystem(), iCreateFramebufferFromTexture, this.param.getExportSize(), this.param.getAreaWS(), false, 8, null);
            TextSystem.renderArea$default(this.param.getCurveEngine().getTextSystem(), iCreateFramebufferFromTexture, this.param.getExportSize(), this.param.getAreaWS(), false, 8, null);
            if (zDoesAreaHasCurve) {
                i = iCreateTexture3;
                i3 = 1;
                TileSystem.renderArea$default(this.param.getCurveEngine().getCurveSystem().getMarkCurveLayer(), this.param.getAreaWS(), iCreateFramebufferFromTexture3, iCreateFramebufferFromTexture2, iCreateTexture2, this.param.getExportSize().x, this.param.getExportSize().y, false, 64, null);
                GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
                i5 = 3553;
                i4 = 771;
                z = false;
                this.param.getCurveEngine().getComposeSystem().multiplyBlend(iCreateFramebufferFromTexture2, iCreateTexture, i, false, true);
                i2 = iCreateFramebufferFromTexture2;
                GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, iCreateTexture);
                GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, this.param.getExportSize().x, this.param.getExportSize().y, 0);
            } else {
                i = iCreateTexture3;
                z = false;
                i2 = iCreateFramebufferFromTexture2;
                i3 = 1;
                i4 = 771;
                i5 = 3553;
            }
            if (z2) {
                i6 = iCreateFramebufferFromTexture3;
            } else {
                this.param.getCurveEngine().getBgSystem().renderArea(i2, this.param.getExportSize(), this.param.getAreaWS());
                ComposeSystem.normalBlend$default(this.param.getCurveEngine().getComposeSystem(), iCreateFramebufferFromTexture3, iCreateTexture2, iCreateTexture, false, false, 24, null);
                i6 = iCreateFramebufferFromTexture3;
                iCreateTexture2 = iCreateTexture2;
                iCreateTexture = iCreateTexture;
                GLES30.glBindTexture(i5, iCreateTexture);
                GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, this.param.getExportSize().x, this.param.getExportSize().y, 0);
            }
            if (this.param.getCurveEngine().getCurveSystem().getMainCurveLayer().doesAreaHasCurve(this.param.getAreaWS())) {
                int i9 = i4;
                i7 = i2;
                TileSystem.renderArea$default(this.param.getCurveEngine().getCurveSystem().getMainCurveLayer(), this.param.getAreaWS(), i6, i7, iCreateTexture2, this.param.getExportSize().x, this.param.getExportSize().y, false, 64, null);
                GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
                GLES30.glBlendFuncSeparate(i3, i9, i3, i9);
                i8 = i;
                TileRenderer.drawFullScreen$default(this.param.getCurveEngine().getTileRenderer(), i8, false, false, 0.0f, 14, null);
            } else {
                i7 = i2;
                i8 = i;
            }
            if (this.param.getCurveEngine().getSelectSystem().getBIsActivated()) {
                this.param.getCurveEngine().getSelectSystem().renderSelectedOverlay(iCreateFramebufferFromTexture, new RectF(this.param.getAreaWS().left, this.param.getAreaWS().top, this.param.getAreaWS().right, this.param.getAreaWS().bottom), this.param.getExportSize());
            }
            int i10 = iCreateTexture2;
            int i11 = i7;
            this.param.getCurveEngine().getMathSystem().renderArea(iCreateFramebufferFromTexture, this.param.getExportSize(), this.param.getAreaWS(), i11, i10);
            TileRenderer.drawRGBAtoBGRA$default(this.param.getCurveEngine().getTileRenderer(), i11, iCreateTexture, false, 4, null);
            this.param.setBitmap(GLUtils.INSTANCE.getBitmapFromFramebuffer(i11, this.param.getExportSize().x, this.param.getExportSize().y));
            GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
            GLUtils.INSTANCE.deleteFramebuffer(i11);
            GLUtils.INSTANCE.deleteFramebuffer(i6);
            GLUtils.INSTANCE.deleteTexture(iCreateTexture);
            GLUtils.INSTANCE.deleteTexture(i10);
            GLUtils.INSTANCE.deleteTexture(i8);
            this.param.getRenderLatch().countDown();
            return z;
        }
    }

    public final Bitmap exportArea(RectF areaWS, Point exportSize) throws InterruptedException {
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        Intrinsics.checkNotNullParameter(exportSize, "exportSize");
        Log.e(TAG, "xl-- exportArea: " + areaWS + ", exportSize: " + exportSize);
        if (exportSize.x <= 0 || exportSize.y <= 0) {
            return null;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExportParam exportParam = new ExportParam(getCurveEngine(), areaWS, exportSize, countDownLatch, null, 16, null);
        getCurveEngine().getTaskManager().enqueueTask(new ExportAreaTask(exportParam));
        countDownLatch.await();
        return exportParam.getBitmap();
    }

    public final Bitmap getPageThumbnail(int pageIndex) {
        int i;
        if (getCurveEngine().getPageMode() == MiCurveEngine.EPageMode.Infinite) {
            return null;
        }
        PageSystem.PageData pageData = getCurveEngine().getPageSystem().getPageData(pageIndex);
        if (pageData.getArea().isEmpty()) {
            return null;
        }
        float fWidth = pageData.getArea().width() / pageData.getArea().height();
        int i2 = 600;
        if (fWidth > 1.0f) {
            i2 = (int) (600 * fWidth);
            i = 600;
        } else {
            i = (int) (600 / fWidth);
        }
        return exportArea(pageData.getArea(), new Point(i2, i));
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailList$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailList$1", f = "ExportSystem.kt", i = {0}, l = {R2.array.ai_panel_widget_blend_modes}, m = "invokeSuspend", n = {"bitmapList"}, s = {"L$0"})
    static final class C04691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<List<Bitmap>, Unit> $callback;
        final /* synthetic */ List<Integer> $list;
        Object L$0;
        int label;
        final /* synthetic */ ExportSystem this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C04691(List<Integer> list, ExportSystem exportSystem, Function1<? super List<Bitmap>, Unit> function1, Continuation<? super C04691> continuation) {
            super(2, continuation);
            this.$list = list;
            this.this$0 = exportSystem;
            this.$callback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C04691(this.$list, this.this$0, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ArrayList arrayList = new ArrayList();
                Iterator<Integer> it = this.$list.iterator();
                while (it.hasNext()) {
                    int iIntValue = it.next().intValue();
                    Bitmap pageThumbnail = this.this$0.getPageThumbnail(iIntValue);
                    if (pageThumbnail == null) {
                        Log.e(ExportSystem.TAG, "get thumbnail empty index of:" + iIntValue);
                    }
                    arrayList.add(pageThumbnail);
                }
                this.L$0 = SpillingKt.nullOutSpilledVariable(arrayList);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C01561(this.$callback, arrayList, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailList$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportSystem.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailList$1$1", f = "ExportSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01561 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<Bitmap> $bitmapList;
            final /* synthetic */ Function1<List<Bitmap>, Unit> $callback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C01561(Function1<? super List<Bitmap>, Unit> function1, List<Bitmap> list, Continuation<? super C01561> continuation) {
                super(2, continuation);
                this.$callback = function1;
                this.$bitmapList = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01561(this.$callback, this.$bitmapList, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01561) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.invoke(this.$bitmapList);
                return Unit.INSTANCE;
            }
        }
    }

    public final void getPageThumbnailList(List<Integer> list, Function1<? super List<Bitmap>, Unit> callback) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Log.d(TAG, "get thumbnail list:" + list.size());
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new C04691(list, this, callback, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Bitmap getPageThumbnailCustomSize(int pageIndex, int bitmapWidth) {
        int i;
        if (getCurveEngine().getPageMode() == MiCurveEngine.EPageMode.Infinite) {
            return null;
        }
        PageSystem.PageData pageData = getCurveEngine().getPageSystem().getPageData(pageIndex);
        if (pageData.getArea().isEmpty()) {
            return null;
        }
        float fWidth = pageData.getArea().width() / pageData.getArea().height();
        if (fWidth > 1.0f) {
            int i2 = (int) (bitmapWidth * fWidth);
            i = bitmapWidth;
            bitmapWidth = i2;
        } else {
            i = (int) (bitmapWidth / fWidth);
        }
        return exportArea(pageData.getArea(), new Point(bitmapWidth, i));
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailCustomList$1, reason: invalid class name */
    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailCustomList$1", f = "ExportSystem.kt", i = {0}, l = {R2.array.am_pms}, m = "invokeSuspend", n = {"bitmapList"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bitmapWidth;
        final /* synthetic */ Function1<List<Bitmap>, Unit> $callback;
        final /* synthetic */ List<Integer> $list;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(List<Integer> list, int i, Function1<? super List<Bitmap>, Unit> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$list = list;
            this.$bitmapWidth = i;
            this.$callback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ExportSystem.this.new AnonymousClass1(this.$list, this.$bitmapWidth, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ExportSystem.this.getCurveEngine().initSpanData();
                ArrayList arrayList = new ArrayList();
                Iterator<Integer> it = this.$list.iterator();
                while (it.hasNext()) {
                    int iIntValue = it.next().intValue();
                    Bitmap pageThumbnailCustomSize = ExportSystem.this.getPageThumbnailCustomSize(iIntValue, this.$bitmapWidth);
                    Log.d(ExportSystem.TAG, "get thumbnail index of:" + iIntValue + "-----bitmap:" + pageThumbnailCustomSize);
                    arrayList.add(pageThumbnailCustomSize);
                }
                ExportSystem.this.getCurveEngine().releaseSpanData();
                this.L$0 = SpillingKt.nullOutSpilledVariable(arrayList);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C01551(this.$callback, arrayList, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailCustomList$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportSystem.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getPageThumbnailCustomList$1$1", f = "ExportSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01551 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<Bitmap> $bitmapList;
            final /* synthetic */ Function1<List<Bitmap>, Unit> $callback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C01551(Function1<? super List<Bitmap>, Unit> function1, List<Bitmap> list, Continuation<? super C01551> continuation) {
                super(2, continuation);
                this.$callback = function1;
                this.$bitmapList = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01551(this.$callback, this.$bitmapList, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01551) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.invoke(this.$bitmapList);
                return Unit.INSTANCE;
            }
        }
    }

    public final void getPageThumbnailCustomList(List<Integer> list, int bitmapWidth, Function1<? super List<Bitmap>, Unit> callback) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Log.d(TAG, "get thumbnail list:" + list.size());
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new AnonymousClass1(list, bitmapWidth, callback, null), 3, null);
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getTargetPageThumbnail$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getTargetPageThumbnail$1", f = "ExportSystem.kt", i = {0}, l = {195}, m = "invokeSuspend", n = {DoodleBitmap.KEY_BITMAP}, s = {"L$0"})
    static final class C04701 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bitmapWidth;
        final /* synthetic */ Function1<Bitmap, Unit> $callback;
        final /* synthetic */ int $index;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C04701(int i, int i2, Function1<? super Bitmap, Unit> function1, Continuation<? super C04701> continuation) {
            super(2, continuation);
            this.$index = i;
            this.$bitmapWidth = i2;
            this.$callback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ExportSystem.this.new C04701(this.$index, this.$bitmapWidth, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04701) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap pageThumbnailCustomSize = ExportSystem.this.getPageThumbnailCustomSize(this.$index, this.$bitmapWidth);
                this.L$0 = SpillingKt.nullOutSpilledVariable(pageThumbnailCustomSize);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C01571(this.$callback, pageThumbnailCustomSize, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getTargetPageThumbnail$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportSystem.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getTargetPageThumbnail$1$1", f = "ExportSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01571 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Bitmap $bitmap;
            final /* synthetic */ Function1<Bitmap, Unit> $callback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C01571(Function1<? super Bitmap, Unit> function1, Bitmap bitmap, Continuation<? super C01571> continuation) {
                super(2, continuation);
                this.$callback = function1;
                this.$bitmap = bitmap;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01571(this.$callback, this.$bitmap, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01571) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.invoke(this.$bitmap);
                return Unit.INSTANCE;
            }
        }
    }

    public final void getTargetPageThumbnail(int index, int bitmapWidth, Function1<? super Bitmap, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new C04701(index, bitmapWidth, callback, null), 3, null);
    }

    public final void getUnLimitThumbnail(RectF rectF, int bitmapWidth, int bitmapHeight, Function1<? super Bitmap, Unit> callback) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Intrinsics.checkNotNullParameter(callback, "callback");
        XLog.d(TAG, "target width:" + bitmapWidth + "------height:" + bitmapHeight, new Object[0]);
        float f = bitmapWidth;
        if (f <= 8192.0f && bitmapHeight <= 8192.0f) {
            BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new C04711(bitmapWidth, bitmapHeight, this, rectF, callback, null), 3, null);
            return;
        }
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        float f2 = bitmapHeight;
        if (f2 > 8192.0f) {
            booleanRef.element = true;
        }
        BuildersKt__Builders_commonKt.launch$default(this.mScope, null, null, new AnonymousClass2(bitmapWidth, bitmapHeight, booleanRef, rectF, (int) Math.max((float) Math.ceil(f / 8192.0f), (float) Math.ceil(f2 / 8192.0f)), this, callback, null), 3, null);
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$1", f = "ExportSystem.kt", i = {0, 0}, l = {207}, m = "invokeSuspend", n = {"point", DoodleBitmap.KEY_BITMAP}, s = {"L$0", "L$1"})
    static final class C04711 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bitmapHeight;
        final /* synthetic */ int $bitmapWidth;
        final /* synthetic */ Function1<Bitmap, Unit> $callback;
        final /* synthetic */ RectF $rectF;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ ExportSystem this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C04711(int i, int i2, ExportSystem exportSystem, RectF rectF, Function1<? super Bitmap, Unit> function1, Continuation<? super C04711> continuation) {
            super(2, continuation);
            this.$bitmapWidth = i;
            this.$bitmapHeight = i2;
            this.this$0 = exportSystem;
            this.$rectF = rectF;
            this.$callback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C04711(this.$bitmapWidth, this.$bitmapHeight, this.this$0, this.$rectF, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C04711) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws InterruptedException {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Point point = new Point(this.$bitmapWidth, this.$bitmapHeight);
                Bitmap bitmapExportArea = this.this$0.exportArea(this.$rectF, point);
                this.L$0 = SpillingKt.nullOutSpilledVariable(point);
                this.L$1 = SpillingKt.nullOutSpilledVariable(bitmapExportArea);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C01581(this.$callback, bitmapExportArea, null), this) == coroutine_suspended) {
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

        /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ExportSystem.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$1$1", f = "ExportSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01581 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Bitmap $bitmap;
            final /* synthetic */ Function1<Bitmap, Unit> $callback;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C01581(Function1<? super Bitmap, Unit> function1, Bitmap bitmap, Continuation<? super C01581> continuation) {
                super(2, continuation);
                this.$callback = function1;
                this.$bitmap = bitmap;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01581(this.$callback, this.$bitmap, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01581) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.invoke(this.$bitmap);
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$2, reason: invalid class name */
    /* JADX INFO: compiled from: ExportSystem.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$2", f = "ExportSystem.kt", i = {0, 0, 0, 0, 0, 0, 0, 0}, l = {R2.attr.actionBarFavoriteIcon}, m = "invokeSuspend", n = {"resultBitmap", "canvas", "paint", "percent", "lastLeft", "lastTop", "drawLeft", "drawTop"}, s = {"L$0", "L$1", "L$2", "F$0", "F$1", "F$2", "F$3", "F$4"})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $bitmapHeight;
        final /* synthetic */ int $bitmapWidth;
        final /* synthetic */ Function1<Bitmap, Unit> $callback;
        final /* synthetic */ int $count;
        final /* synthetic */ RectF $rectF;
        final /* synthetic */ Ref.BooleanRef $vertical;
        float F$0;
        float F$1;
        float F$2;
        float F$3;
        float F$4;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ ExportSystem this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(int i, int i2, Ref.BooleanRef booleanRef, RectF rectF, int i3, ExportSystem exportSystem, Function1<? super Bitmap, Unit> function1, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$bitmapWidth = i;
            this.$bitmapHeight = i2;
            this.$vertical = booleanRef;
            this.$rectF = rectF;
            this.$count = i3;
            this.this$0 = exportSystem;
            this.$callback = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$bitmapWidth, this.$bitmapHeight, this.$vertical, this.$rectF, this.$count, this.this$0, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws InterruptedException {
            int i;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.$bitmapWidth, this.$bitmapHeight, Bitmap.Config.ARGB_8888);
                Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                Paint paint = new Paint();
                float f = 8192.0f / this.$bitmapWidth;
                if (this.$vertical.element) {
                    f = 8192.0f / this.$bitmapHeight;
                }
                float f2 = this.$rectF.left;
                float f3 = this.$rectF.top;
                float width = 0.0f;
                int i3 = 0;
                float height = 0.0f;
                while (i3 < this.$count) {
                    int i4 = i3 * 8192;
                    int iMin = Math.min(this.$bitmapWidth - i4, 8192);
                    int iMin2 = this.$bitmapHeight;
                    float fMin = Math.min((this.$rectF.width() * f) + f2, this.$rectF.right);
                    RectF rectF = new RectF(f2, this.$rectF.top, fMin, this.$rectF.bottom);
                    if (this.$vertical.element) {
                        i = this.$bitmapWidth;
                        iMin2 = Math.min(this.$bitmapHeight - i4, 8192);
                        float fMin2 = Math.min((this.$rectF.height() * f) + f3, this.$rectF.bottom);
                        rectF = new RectF(this.$rectF.left, f3, this.$rectF.right, fMin2);
                        f3 = fMin2;
                    } else {
                        i = iMin;
                    }
                    Point point = new Point(i, iMin2);
                    Log.d(ExportSystem.TAG, "temp rect:" + rectF + "-----temp point：" + point);
                    Bitmap bitmapExportArea = this.this$0.exportArea(rectF, point);
                    if (bitmapExportArea == null) {
                        Log.e(ExportSystem.TAG, "error bitmap is null index :" + i3);
                    } else {
                        canvas.drawBitmap(bitmapExportArea, width, height, paint);
                        if (this.$vertical.element) {
                            height += bitmapExportArea.getHeight();
                        } else {
                            width += bitmapExportArea.getWidth();
                        }
                        bitmapExportArea.recycle();
                        Log.d(ExportSystem.TAG, "draw left:" + width + "-----top:" + height);
                    }
                    i3++;
                    f2 = fMin;
                }
                this.L$0 = SpillingKt.nullOutSpilledVariable(bitmapCreateBitmap);
                this.L$1 = SpillingKt.nullOutSpilledVariable(canvas);
                this.L$2 = SpillingKt.nullOutSpilledVariable(paint);
                this.F$0 = f;
                this.F$1 = f2;
                this.F$2 = f3;
                this.F$3 = width;
                this.F$4 = height;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass1(this.$callback, bitmapCreateBitmap, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: ExportSystem.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "xc.engine.core.graphics.engine.systems.ExportSystem$getUnLimitThumbnail$2$1", f = "ExportSystem.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<Bitmap, Unit> $callback;
            final /* synthetic */ Bitmap $resultBitmap;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(Function1<? super Bitmap, Unit> function1, Bitmap bitmap, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$callback = function1;
                this.$resultBitmap = bitmap;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$callback, this.$resultBitmap, continuation);
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
                this.$callback.invoke(this.$resultBitmap);
                return Unit.INSTANCE;
            }
        }
    }

    public final void finish() {
        CoroutineScopeKt.cancel$default(this.mScope, null, 1, null);
    }
}
