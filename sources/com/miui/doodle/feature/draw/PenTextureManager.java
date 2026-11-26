package com.miui.doodle.feature.draw;

import android.graphics.Bitmap;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PenTextureManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0010J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0013\u001a\u00020\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/miui/doodle/feature/draw/PenTextureManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", PenTextureManager.HardPencil00, "", PenTextureManager.WaterColor00, PenTextureManager.WaterColor01, PenTextureManager.WaterColor02, PenTextureManager.WaterColor03, PenTextureManager.WaterColor04, PenTextureManager.MarkPen00, PenTextureManager.Pen00, PenTextureManager.Eraser00, "mCache", "", "Landroid/graphics/Bitmap;", "addTexture", "", "key", DoodleBitmap.KEY_BITMAP, "getTexture", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PenTextureManager {
    public static final String Eraser00 = "Eraser00";
    public static final String HardPencil00 = "HardPencil00";
    public static final String MarkPen00 = "MarkPen00";
    public static final String Pen00 = "Pen00";
    public static final String WaterColor00 = "WaterColor00";
    public static final String WaterColor01 = "WaterColor01";
    public static final String WaterColor02 = "WaterColor02";
    public static final String WaterColor03 = "WaterColor03";
    public static final String WaterColor04 = "WaterColor04";
    public static final PenTextureManager INSTANCE = new PenTextureManager();
    private static final Map<String, Bitmap> mCache = new HashMap();

    private PenTextureManager() {
    }

    public final void addTexture(String key, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        mCache.put(key, bitmap);
    }

    public final Bitmap getTexture(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return mCache.get(key);
    }
}
