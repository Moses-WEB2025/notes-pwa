package com.miui.libhandwritingpreview.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import kotlin.Metadata;

/* JADX INFO: compiled from: PagePreDrawView.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u0018\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0015J\u0018\u0010\u0019\u001a\u00020\u00112\b\u0010\u001a\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\f\u001a\u0004\u0018\u00010\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006!"}, d2 = {"Lcom/miui/libhandwritingpreview/view/PagePreDrawView;", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "textureView", "Lcom/miui/libhandwritingpreview/view/PagePreTextureView;", "view", "Landroid/view/View;", "getView", "()Landroid/view/View;", "creationEngine", "Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;", "getCreationEngine", "()Lcom/miui/libhandwritingpreview/interfaces/PreICreationEngine;", "bindItemView", "", "pageItemView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "needDelSelectCurves", "onCanvasDataChanged", "o", "viewInvalidate", "", "setCanvasSize", Layer.KEY_WIDTH, "", Layer.KEY_HEIGHT, "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PagePreDrawView {
    private PagePreTextureView textureView;

    public PagePreDrawView(Context context) {
        this.textureView = new PagePreTextureView(context);
    }

    public final View getView() {
        return this.textureView;
    }

    public final PreICreationEngine getCreationEngine() {
        return this.textureView.getCreationEngine();
    }

    public final void bindItemView(PagePreItemView pageItemView) {
        this.textureView.bindItemView(pageItemView);
    }

    public final boolean onTouchEvent(MotionEvent event, boolean needDelSelectCurves) {
        return this.textureView.onTouchEvent(event, needDelSelectCurves);
    }

    public final void onCanvasDataChanged(Object o, int viewInvalidate) {
        this.textureView.onCanvasDataChanged(null, viewInvalidate);
    }

    public final void setCanvasSize(float width, float height) {
        this.textureView.setCanvasSize(width, height);
    }
}
