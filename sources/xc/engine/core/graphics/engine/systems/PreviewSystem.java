package xc.engine.core.graphics.engine.systems;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.opengl.GLES30;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DoodleBitmap;
import com.miui.pad.feature.notes.multihandwrite.CreationEditFragment;
import defpackage.R2;
import java.nio.IntBuffer;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.core.graphics.engine.task.BaseAsyncGLTask;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: compiled from: PreviewSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0002\u0016\u0017B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\u0016\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PreviewSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "previewTextureId", "", "previewTextureTmpId", "previewFramebufferId", "previewFrameBufferTmpId", CreationEditFragment.INTENT_PARAM_PREVIEW_WIDTH, CreationEditFragment.INTENT_PARAM_PREVIEW_HEIGHT, "lastRequestTime", "", "onDestroy", "", "drawBrushPreviewImage", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", DoodleBitmap.KEY_BITMAP, "Landroid/graphics/Bitmap;", "Companion", "AsyncBrushPreviewTask", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreviewSystem extends BaseEngineStruct {
    private static final String TAG = "PreviewSystem";
    private long lastRequestTime;
    private int previewFrameBufferTmpId;
    private int previewFramebufferId;
    private int previewHeight;
    private int previewTextureId;
    private int previewTextureTmpId;
    private int previewWidth;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreviewSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.lastRequestTime = Instant.now().toEpochMilli();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLUtils.INSTANCE.deleteTexture(this.previewTextureId);
        GLUtils.INSTANCE.deleteTexture(this.previewTextureTmpId);
    }

    /* JADX INFO: compiled from: PreviewSystem.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lxc/engine/core/graphics/engine/systems/PreviewSystem$AsyncBrushPreviewTask;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inPreviewSystem", "Lxc/engine/core/graphics/engine/systems/PreviewSystem;", "inDataCurve", "Lxc/engine/data/pagedata/DataCurve;", "inBitmap", "Landroid/graphics/Bitmap;", "latch", "Ljava/util/concurrent/CountDownLatch;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/systems/PreviewSystem;Lxc/engine/data/pagedata/DataCurve;Landroid/graphics/Bitmap;Ljava/util/concurrent/CountDownLatch;)V", "previewSystem", "curveRenderer", "Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "curve", DoodleBitmap.KEY_BITMAP, "renderLatch", "execute", "", "res", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class AsyncBrushPreviewTask extends BaseAsyncGLTask {
        private final Bitmap bitmap;
        private final DataCurve curve;
        private final CurveRenderer curveRenderer;
        private final PreviewSystem previewSystem;
        private final CountDownLatch renderLatch;

        public AsyncBrushPreviewTask(PreviewSystem inPreviewSystem, DataCurve inDataCurve, Bitmap inBitmap, CountDownLatch latch) {
            Intrinsics.checkNotNullParameter(inPreviewSystem, "inPreviewSystem");
            Intrinsics.checkNotNullParameter(inDataCurve, "inDataCurve");
            Intrinsics.checkNotNullParameter(inBitmap, "inBitmap");
            Intrinsics.checkNotNullParameter(latch, "latch");
            this.previewSystem = inPreviewSystem;
            this.curveRenderer = inPreviewSystem.getCurveEngine().getCurveRenderer();
            this.curve = inDataCurve;
            this.bitmap = inBitmap;
            this.renderLatch = latch;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseAsyncGLTask
        public boolean execute(TileRenderResource res) {
            Intrinsics.checkNotNullParameter(res, "res");
            if (getCreateTimeInMilli() >= this.previewSystem.lastRequestTime) {
                if (this.bitmap.getWidth() != this.previewSystem.previewWidth || this.bitmap.getHeight() != this.previewSystem.previewHeight) {
                    this.previewSystem.previewWidth = this.bitmap.getWidth();
                    this.previewSystem.previewHeight = this.bitmap.getHeight();
                    GLUtils.INSTANCE.deleteTexture(this.previewSystem.previewTextureId);
                    GLUtils.INSTANCE.deleteTexture(this.previewSystem.previewTextureTmpId);
                    this.previewSystem.previewTextureId = GLUtils.INSTANCE.createTexture(this.previewSystem.previewWidth, this.previewSystem.previewHeight);
                    this.previewSystem.previewTextureTmpId = GLUtils.INSTANCE.createTexture(this.previewSystem.previewWidth, this.previewSystem.previewHeight);
                    if (this.previewSystem.previewFramebufferId > 0) {
                        GLUtils.INSTANCE.bindTextureToFramebuffer(this.previewSystem.previewFramebufferId, this.previewSystem.previewTextureId);
                        GLUtils.INSTANCE.bindTextureToFramebuffer(this.previewSystem.previewFrameBufferTmpId, this.previewSystem.previewTextureTmpId);
                    } else {
                        this.previewSystem.previewFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.previewSystem.previewTextureId);
                        this.previewSystem.previewFrameBufferTmpId = GLUtils.INSTANCE.createFramebufferFromTexture(this.previewSystem.previewTextureTmpId);
                    }
                }
                GLES30.glBindFramebuffer(36160, this.previewSystem.previewFramebufferId);
                GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES30.glClear(16384);
                GLES30.glViewport(0, 0, this.previewSystem.previewWidth, this.previewSystem.previewHeight);
                CurveRenderer.nativeRenderSingleCurve$default(this.curveRenderer, this.curve, new RectF(0.0f, 0.0f, this.bitmap.getWidth(), this.bitmap.getHeight()), this.previewSystem.previewFramebufferId, this.previewSystem.previewFrameBufferTmpId, this.previewSystem.previewTextureTmpId, 0.0f, 32, null);
                IntBuffer intBufferWrap = IntBuffer.wrap(new int[this.bitmap.getWidth() * this.bitmap.getHeight()]);
                GLES30.glReadPixels(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, R2.color.miuix_default_color_on_error_dark, intBufferWrap);
                this.bitmap.copyPixelsFromBuffer(intBufferWrap);
                this.curve.releasePointList();
                this.renderLatch.countDown();
                return false;
            }
            this.curve.releasePointList();
            this.renderLatch.countDown();
            return false;
        }
    }

    public final void drawBrushPreviewImage(DataCurve dataCurve, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(dataCurve, "dataCurve");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AsyncBrushPreviewTask asyncBrushPreviewTask = new AsyncBrushPreviewTask(this, dataCurve, bitmap, countDownLatch);
        this.lastRequestTime = asyncBrushPreviewTask.getCreateTimeInMilli();
        getCurveEngine().getTaskManager().enqueueTask(asyncBrushPreviewTask);
        countDownLatch.await();
    }
}
