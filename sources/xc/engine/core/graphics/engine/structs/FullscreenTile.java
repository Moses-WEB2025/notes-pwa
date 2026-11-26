package xc.engine.core.graphics.engine.structs;

import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.ScreenTexture;

/* JADX INFO: compiled from: Tile.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\t¨\u0006\u0013"}, d2 = {"Lxc/engine/core/graphics/engine/structs/FullscreenTile;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "texture", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "getTexture", "()Lxc/engine/core/graphics/engine/core/ScreenTexture;", "setTexture", "(Lxc/engine/core/graphics/engine/core/ScreenTexture;)V", "screenRect", "Landroid/graphics/RectF;", "getScreenRect", "()Landroid/graphics/RectF;", "setScreenRect", "(Landroid/graphics/RectF;)V", "screenTmpTex", "getScreenTmpTex", "setScreenTmpTex", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FullscreenTile {
    private ScreenTexture texture = new ScreenTexture(0, 0, 0, 7, null);
    private RectF screenRect = new RectF();
    private ScreenTexture screenTmpTex = new ScreenTexture(0, 0, 0, 7, null);

    public final ScreenTexture getTexture() {
        return this.texture;
    }

    public final void setTexture(ScreenTexture screenTexture) {
        Intrinsics.checkNotNullParameter(screenTexture, "<set-?>");
        this.texture = screenTexture;
    }

    public final RectF getScreenRect() {
        return this.screenRect;
    }

    public final void setScreenRect(RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<set-?>");
        this.screenRect = rectF;
    }

    public final ScreenTexture getScreenTmpTex() {
        return this.screenTmpTex;
    }

    public final void setScreenTmpTex(ScreenTexture screenTexture) {
        Intrinsics.checkNotNullParameter(screenTexture, "<set-?>");
        this.screenTmpTex = screenTexture;
    }
}
