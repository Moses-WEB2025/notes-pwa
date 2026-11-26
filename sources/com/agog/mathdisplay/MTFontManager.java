package com.agog.mathdisplay;

import android.content.Context;
import android.content.res.AssetManager;
import ch.qos.logback.core.CoreConstants;
import com.agog.mathdisplay.parse.MathDisplayException;
import com.agog.mathdisplay.render.MTFont;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTFontManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/agog/mathdisplay/MTFontManager;", "", "()V", "Companion", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTFontManager {
    private static AssetManager assets;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final HashMap<String, MTFont> nameToFontMap = new HashMap<>();

    /* JADX INFO: compiled from: MTFontManager.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u0004\u0018\u00010\bJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000eR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/agog/mathdisplay/MTFontManager$Companion;", "", "()V", "assets", "Landroid/content/res/AssetManager;", "nameToFontMap", "Ljava/util/HashMap;", "", "Lcom/agog/mathdisplay/render/MTFont;", "Lkotlin/collections/HashMap;", "defaultFont", "fontWithName", "name", "size", "", "latinModernFontWithSize", "setContext", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "termesFontWithSize", "xitsFontWithSize", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MTFont fontWithName(String name, float size) throws MathDisplayException {
            Intrinsics.checkNotNullParameter(name, "name");
            MTFont mTFont = (MTFont) MTFontManager.nameToFontMap.get(name);
            if (mTFont != null) {
                return mTFont.getFontSize() == size ? mTFont : mTFont.copyFontWithSize(size);
            }
            AssetManager assetManager = MTFontManager.assets;
            if (assetManager == null) {
                throw new MathDisplayException("MTFontManager assets is null");
            }
            MTFont mTFont2 = new MTFont(assetManager, name, size, false, 8, null);
            MTFontManager.nameToFontMap.put(name, mTFont2);
            return mTFont2;
        }

        public final void setContext(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            MTFontManager.assets = context.getAssets();
        }

        public final MTFont latinModernFontWithSize(float size) {
            return fontWithName("latinmodern-math", size);
        }

        public final MTFont xitsFontWithSize(float size) {
            return fontWithName("xits-math", size);
        }

        public final MTFont termesFontWithSize(float size) {
            return fontWithName("texgyretermes-math", size);
        }

        public final MTFont defaultFont() {
            return latinModernFontWithSize(20.0f);
        }
    }
}
