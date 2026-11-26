package xc.engine.core.graphics.engine.core;

import android.opengl.GLES30;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: TexturePool.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0012\u0018\u0000  2\u00020\u0001:\u0001 B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J\u0006\u0010\u0012\u001a\u00020\u0006J\u0016\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0006J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\bJ\b\u0010\u001a\u001a\u00020\u0006H\u0002J\u0018\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0002J\u0006\u0010\u001c\u001a\u00020\u000fJ\b\u0010\u001d\u001a\u00020\u000fH\u0002J\u0016\u0010\u001e\u001a\u00020\u000f2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/core/graphics/engine/core/TexturePool;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tileTextureSet", "Ljava/util/concurrent/ConcurrentSkipListSet;", "", "screenTextureSet", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "totalTileTexCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "screenTexCounter", "screenWidth", "screenHeight", "onScreenResize", "", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "acquireTileTexture", "acquireScreenTexture", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "recycleTileTexture", "textureId", "recycleScreenTexture", "texture", "createTileTexture", "createScreenTexture", "onDestroy", "batchDestroyScreenTexture", "batchDestroyTile", "texSet", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TexturePool {
    private static final String TAG = "TexturePool";
    private final ConcurrentSkipListSet<Integer> tileTextureSet = new ConcurrentSkipListSet<>();
    private final ConcurrentSkipListSet<ScreenTexture> screenTextureSet = new ConcurrentSkipListSet<>();
    private final AtomicInteger totalTileTexCount = new AtomicInteger(0);
    private final AtomicInteger screenTexCounter = new AtomicInteger(0);
    private int screenWidth = 1;
    private int screenHeight = 1;

    public final void onScreenResize(int w, int h) {
        XLog.v(TAG, "xl-- [TexturePool] onScreenResize: " + w + " x " + h, new Object[0]);
        this.screenWidth = w;
        this.screenHeight = h;
        batchDestroyScreenTexture();
    }

    public final int acquireTileTexture() {
        if (this.tileTextureSet.isEmpty()) {
            return createTileTexture();
        }
        Integer numPollFirst = this.tileTextureSet.pollFirst();
        Intrinsics.checkNotNull(numPollFirst);
        return numPollFirst.intValue();
    }

    public final ScreenTexture acquireScreenTexture(int width, int height) {
        if (width == this.screenWidth && height == this.screenHeight && !this.screenTextureSet.isEmpty()) {
            ScreenTexture screenTexturePollFirst = this.screenTextureSet.pollFirst();
            Intrinsics.checkNotNull(screenTexturePollFirst);
            return screenTexturePollFirst;
        }
        return createScreenTexture(width, height);
    }

    public final void recycleTileTexture(int textureId) {
        if (textureId > 0) {
            this.tileTextureSet.add(Integer.valueOf(textureId));
        }
    }

    public final void recycleScreenTexture(ScreenTexture texture) {
        Intrinsics.checkNotNullParameter(texture, "texture");
        if (texture.isValid()) {
            if (texture.getWidth() == this.screenWidth && texture.getHeight() == this.screenHeight) {
                this.screenTextureSet.add(new ScreenTexture(texture));
            } else {
                GLUtils.INSTANCE.deleteTexture(texture.getTexId());
                this.screenTexCounter.decrementAndGet();
            }
        }
    }

    private final int createTileTexture() {
        int iCreateTexture = GLUtils.INSTANCE.createTexture(1024, 1024);
        this.totalTileTexCount.incrementAndGet();
        return iCreateTexture;
    }

    private final ScreenTexture createScreenTexture(int width, int height) {
        int iCreateTexture = GLUtils.INSTANCE.createTexture(width, height);
        this.screenTexCounter.incrementAndGet();
        return new ScreenTexture(iCreateTexture, width, height);
    }

    public final void onDestroy() {
        String str = TAG;
        XLog.v(str, "xl- tile tex pool destroy " + this.tileTextureSet.size() + "  total: " + this.totalTileTexCount, new Object[0]);
        batchDestroyTile(this.tileTextureSet);
        this.totalTileTexCount.set(0);
        batchDestroyScreenTexture();
        Log.i(str, "xl- screen tex counter: " + this.screenTexCounter.get());
    }

    private final void batchDestroyScreenTexture() {
        if (this.screenTextureSet.isEmpty()) {
            return;
        }
        int size = this.screenTextureSet.size();
        int[] iArr = new int[size];
        int i = 0;
        for (Object obj : this.screenTextureSet) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            iArr[i] = ((ScreenTexture) obj).getTexId();
            i = i2;
        }
        GLES30.glDeleteTextures(size, iArr, 0);
        this.screenTextureSet.clear();
        this.screenTexCounter.addAndGet(-size);
    }

    private final void batchDestroyTile(ConcurrentSkipListSet<Integer> texSet) {
        if (texSet.isEmpty()) {
            return;
        }
        int[] iArr = new int[texSet.size()];
        int i = 0;
        for (Object obj : texSet) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Integer num = (Integer) obj;
            Intrinsics.checkNotNull(num);
            iArr[i] = num.intValue();
            i = i2;
        }
        GLES30.glDeleteTextures(texSet.size(), iArr, 0);
        texSet.clear();
    }
}
