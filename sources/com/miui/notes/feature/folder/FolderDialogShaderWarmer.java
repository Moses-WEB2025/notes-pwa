package com.miui.notes.feature.folder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.media.ImageReader;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import androidx.core.content.ContextCompat;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.R;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FolderDialogShaderWarmer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0003J\u0018\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/feature/folder/FolderDialogShaderWarmer;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "WARMUP_W", "", "WARMUP_H", "warmed", "Ljava/util/concurrent/atomic/AtomicBoolean;", "warmUpIfNeeded", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "renderToOffscreenSurface", "buildRootRenderNode", "Landroid/graphics/RenderNode;", "drawBackground", "canvas", "Landroid/graphics/Canvas;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FolderDialogShaderWarmer {
    private static final String TAG = "FolderShaderWarmer";
    private static final int WARMUP_H = 240;
    private static final int WARMUP_W = 720;
    public static final FolderDialogShaderWarmer INSTANCE = new FolderDialogShaderWarmer();
    private static final AtomicBoolean warmed = new AtomicBoolean(false);

    private FolderDialogShaderWarmer() {
    }

    public final void warmUpIfNeeded(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (warmed.compareAndSet(false, true)) {
            final Context applicationContext = context.getApplicationContext();
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.miui.notes.feature.folder.FolderDialogShaderWarmer$$ExternalSyntheticLambda0
                @Override // android.os.MessageQueue.IdleHandler
                public final boolean queueIdle() {
                    return FolderDialogShaderWarmer.warmUpIfNeeded$lambda$0(applicationContext);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean warmUpIfNeeded$lambda$0(Context context) {
        try {
            FolderDialogShaderWarmer folderDialogShaderWarmer = INSTANCE;
            Intrinsics.checkNotNull(context);
            folderDialogShaderWarmer.renderToOffscreenSurface(context);
        } catch (Throwable th) {
            Log.w(TAG, "warmUp failed: " + th.getMessage());
            warmed.set(false);
        }
        return false;
    }

    private final void renderToOffscreenSurface(Context context) {
        ImageReader imageReaderNewInstance = ImageReader.newInstance(720, 240, 1, 1, 768L);
        Intrinsics.checkNotNullExpressionValue(imageReaderNewInstance, "newInstance(...)");
        HardwareRenderer hardwareRenderer = new HardwareRenderer();
        try {
            hardwareRenderer.setSurface(imageReaderNewInstance.getSurface());
            hardwareRenderer.setContentRoot(buildRootRenderNode(context));
            hardwareRenderer.createRenderRequest().setVsyncTime(System.nanoTime()).syncAndDraw();
        } finally {
            hardwareRenderer.destroy();
            imageReaderNewInstance.close();
        }
    }

    private final RenderNode buildRootRenderNode(Context context) {
        RenderNode renderNode = new RenderNode("folder-dialog-shader-warm");
        renderNode.setPosition(0, 0, 720, 240);
        RecordingCanvas recordingCanvasBeginRecording = renderNode.beginRecording();
        Intrinsics.checkNotNullExpressionValue(recordingCanvasBeginRecording, "beginRecording(...)");
        try {
            drawBackground(context, recordingCanvasBeginRecording);
            return renderNode;
        } finally {
            renderNode.endRecording();
        }
    }

    private final void drawBackground(Context context, Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.common_folder_dialog_list_bg_shape);
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, 720, 240);
        drawable.draw(canvas);
    }
}
