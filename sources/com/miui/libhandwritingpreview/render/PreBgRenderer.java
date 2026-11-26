package com.miui.libhandwritingpreview.render;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES30;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.system.PreBgSystem;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.gl.GLUtils;

/* JADX INFO: compiled from: PreBgRenderer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\bH\u0014J8\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0011J6\u0010\u0017\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/miui/libhandwritingpreview/render/PreBgRenderer;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "styleProgramId", "", "tileProgramId", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "renderStyle", "ndcRect", "Landroid/graphics/RectF;", "bgArea", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "bgStyle", "Lcom/miui/libhandwritingpreview/system/PreBgSystem$EBgStyle;", "saveRatio", "", "styleColor", "renderTile", "styleTexArea", "styleTexId", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreBgRenderer extends BaseStruct {
    private static final String TAG = "PreBgRenderer";
    private int styleProgramId;
    private int tileProgramId;

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.styleProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(211, 211);
        this.tileProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(211, 212);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.styleProgramId);
        GLES30.glDeleteProgram(this.tileProgramId);
    }

    public static /* synthetic */ void renderStyle$default(PreBgRenderer preBgRenderer, RectF rectF, RectF rectF2, Color color, PreBgSystem.EBgStyle eBgStyle, float f, Color color2, int i, Object obj) {
        if ((i & 32) != 0) {
            color2 = new Color();
        }
        preBgRenderer.renderStyle(rectF, rectF2, color, eBgStyle, f, color2);
    }

    public final void renderStyle(RectF ndcRect, RectF bgArea, Color bgColor, PreBgSystem.EBgStyle bgStyle, float saveRatio, Color styleColor) {
        Intrinsics.checkNotNullParameter(ndcRect, "ndcRect");
        Intrinsics.checkNotNullParameter(bgArea, "bgArea");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(bgStyle, "bgStyle");
        Intrinsics.checkNotNullParameter(styleColor, "styleColor");
        GLES30.glUseProgram(this.styleProgramId);
        GLES30.glUniform4f(0, ndcRect.left, ndcRect.top, ndcRect.right, ndcRect.bottom);
        GLES30.glUniform4f(1, bgColor.getR(), bgColor.getG(), bgColor.getB(), bgColor.getA());
        GLES30.glUniform4f(2, styleColor.getR(), styleColor.getG(), styleColor.getB(), styleColor.getA());
        GLES30.glUniform4f(3, bgArea.left, bgArea.top, bgArea.right, bgArea.bottom);
        GLES30.glUniform1i(4, bgStyle.ordinal());
        GLES30.glUniform1f(5, saveRatio);
        GLES30.glDrawArrays(5, 0, 4);
    }

    public final void renderTile(RectF ndcRect, RectF bgArea, RectF styleTexArea, int styleTexId, Color bgColor, PreBgSystem.EBgStyle bgStyle) {
        Intrinsics.checkNotNullParameter(ndcRect, "ndcRect");
        Intrinsics.checkNotNullParameter(bgArea, "bgArea");
        Intrinsics.checkNotNullParameter(styleTexArea, "styleTexArea");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(bgStyle, "bgStyle");
        Color color = new Color(0.65f, 0.65f, 0.65f, 1.0f);
        GLES30.glUseProgram(this.tileProgramId);
        GLES30.glUniform4f(0, ndcRect.left, ndcRect.top, ndcRect.right, ndcRect.bottom);
        GLES30.glUniform4f(1, bgColor.getR(), bgColor.getG(), bgColor.getB(), bgColor.getA());
        GLES30.glUniform4f(2, color.getR(), color.getG(), color.getB(), color.getA());
        GLES30.glUniform4f(3, bgArea.left, bgArea.top, bgArea.right, bgArea.bottom);
        GLES30.glUniform4f(4, styleTexArea.left, styleTexArea.top, styleTexArea.right, styleTexArea.bottom);
        GLES30.glUniform1i(5, bgStyle.ordinal());
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, styleTexId);
        GLES30.glUniform1i(6, 1);
        GLES30.glDrawArrays(5, 0, 4);
    }
}
