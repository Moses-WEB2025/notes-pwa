package xc.engine.core.graphics.engine.structs;

import android.opengl.GLES30;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.TexturePool;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: Tile.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u0003J\u0006\u0010\u001d\u001a\u00020\u0003R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u0005R\u001a\u0010\u000f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u0005R\u001a\u0010\u0012\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\r\"\u0004\b\u0014\u0010\u0005¨\u0006\u001e"}, d2 = {"Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "", "count", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "fbCount", "framebufferIds", "", "currentFbId", "Ljava/util/concurrent/atomic/AtomicInteger;", "tmpTextureId", "getTmpTextureId", "()I", "setTmpTextureId", "tmpFramebufferId", "getTmpFramebufferId", "setTmpFramebufferId", "tmpScreenFramebufferId", "getTmpScreenFramebufferId", "setTmpScreenFramebufferId", "init", "", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "destroy", "incrementFbId", "bindTileTexToFramebuffer", "textureId", "getCurrentFramebufferId", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TileRenderResource {
    private AtomicInteger currentFbId;
    private final int fbCount;
    private final int[] framebufferIds;
    private int tmpFramebufferId;
    private int tmpScreenFramebufferId;
    private int tmpTextureId;

    public TileRenderResource() {
        this(0, 1, null);
    }

    public TileRenderResource(int i) {
        this.fbCount = i;
        this.framebufferIds = new int[3];
        this.currentFbId = new AtomicInteger(0);
    }

    public /* synthetic */ TileRenderResource(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 3 : i);
    }

    public final int getTmpTextureId() {
        return this.tmpTextureId;
    }

    public final void setTmpTextureId(int i) {
        this.tmpTextureId = i;
    }

    public final int getTmpFramebufferId() {
        return this.tmpFramebufferId;
    }

    public final void setTmpFramebufferId(int i) {
        this.tmpFramebufferId = i;
    }

    public final int getTmpScreenFramebufferId() {
        return this.tmpScreenFramebufferId;
    }

    public final void setTmpScreenFramebufferId(int i) {
        this.tmpScreenFramebufferId = i;
    }

    public final void init(TexturePool texturePool) {
        Intrinsics.checkNotNullParameter(texturePool, "texturePool");
        GLES30.glGenFramebuffers(this.fbCount, this.framebufferIds, 0);
        this.tmpTextureId = texturePool.acquireTileTexture();
        this.tmpFramebufferId = GLUtils.INSTANCE.createFramebufferFromTexture(this.tmpTextureId);
        this.tmpScreenFramebufferId = GLUtils.INSTANCE.createFramebufferOnly();
    }

    public final void destroy(TexturePool texturePool) {
        Intrinsics.checkNotNullParameter(texturePool, "texturePool");
        GLUtils.INSTANCE.deleteFramebuffer(this.tmpFramebufferId);
        GLUtils.INSTANCE.deleteFramebuffer(this.tmpScreenFramebufferId);
        GLES30.glDeleteFramebuffers(this.fbCount, this.framebufferIds, 0);
        texturePool.recycleTileTexture(this.tmpTextureId);
    }

    private final void incrementFbId() {
        this.currentFbId.incrementAndGet();
        if (this.currentFbId.get() > this.fbCount - 1) {
            this.currentFbId.set(0);
        }
    }

    public final void bindTileTexToFramebuffer(int textureId) {
        incrementFbId();
        GLUtils.INSTANCE.bindTextureToFramebuffer(this.framebufferIds[this.currentFbId.get()], textureId);
    }

    public final int getCurrentFramebufferId() {
        return this.framebufferIds[this.currentFbId.get()];
    }
}
