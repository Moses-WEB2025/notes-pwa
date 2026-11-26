package com.miui.libhandwritingpage.view.listenerProxy;

import android.graphics.Path;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.edit.select.OnSelectModelListener;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: compiled from: OnSelectModeListenerProxy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\"\u0010\u0014\u001a\u00020\t2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0018H\u0016J*\u0010\u0014\u001a\u00020\t2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\"\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u000b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u000bH\u0016J\u0018\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\tH\u0016J*\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020#2\u0006\u0010'\u001a\u00020#2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020\u000eH\u0016J*\u0010+\u001a\u00020\t2\u0006\u0010&\u001a\u00020#2\u0006\u0010'\u001a\u00020#2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020\u000eH\u0016J\u0010\u0010,\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u000bH\u0016J\u001a\u0010-\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u0010\u0010.\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u000bH\u0016J\u0010\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\u000bH\u0016J\b\u00101\u001a\u00020\tH\u0016J\b\u00102\u001a\u00020\tH\u0016J\u0010\u00103\u001a\u00020\u000e2\u0006\u00104\u001a\u00020\u000bH\u0016J\b\u00105\u001a\u00020\tH\u0016J\b\u00106\u001a\u00020\tH\u0016J\b\u00107\u001a\u00020\tH\u0016R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lcom/miui/libhandwritingpage/view/listenerProxy/OnSelectModeListenerProxy;", "Lxc/engine/operate/edit/select/OnSelectModelListener;", "listener", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/operate/edit/select/OnSelectModelListener;)V", "ref", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "onStepChanged", "", "stepType", "", "pageIndex", "dismissLoadingDialog", "", "onPageSizeChanged", "pageSizeConfig", "Lxc/engine/utils/PageSizeConfig;", "setSelectStateI", "isInSelecting", "onObjectSelected", "selectRectF", "Lxc/engine/operate/edit/select/SRectF;", "dataCurveList", "", "Lxc/engine/data/pagedata/DataCurve;", "isFromAI", "onDataCopied", "selectActionType", "bArr", "", "i2", "onSelectLimit", "limitType", "f", "", "onCancelSelect", "onObjectSelectedPath", "offX", "offY", "path", "Landroid/graphics/Path;", "showAntLine", "onObjectSelectedPathForOutLine", "onStartAction", "onDoingAction", "onEndAction", "onSelectedHashCode", "hashCode", "onStartPasteFromCopy", "isPointLimit", "isPagePointLimit", "pointCount", "finishSelect", "onCanvasChanged", "onSelectedDeletedEnd", "libHandWritingPage_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class OnSelectModeListenerProxy implements OnSelectModelListener {
    private final WeakReference<OnSelectModelListener> ref;

    public OnSelectModeListenerProxy(OnSelectModelListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.ref = new WeakReference<>(listener);
    }

    @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
    public void onStepChanged(int stepType, int pageIndex, boolean dismissLoadingDialog) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onStepChanged(stepType, pageIndex, dismissLoadingDialog);
        }
    }

    @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
    public void onPageSizeChanged(PageSizeConfig pageSizeConfig) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onPageSizeChanged(pageSizeConfig);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void setSelectStateI(boolean isInSelecting) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.setSelectStateI(isInSelecting);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelected(SRectF selectRectF, List<DataCurve> dataCurveList) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelected(selectRectF, dataCurveList);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelected(SRectF selectRectF, List<DataCurve> dataCurveList, boolean isFromAI) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelected(selectRectF, dataCurveList, isFromAI);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onDataCopied(int selectActionType, byte[] bArr, int i2) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onDataCopied(selectActionType, bArr, i2);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectLimit(int limitType, float f) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onSelectLimit(limitType, f);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onCancelSelect() {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onCancelSelect();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelectedPath(float offX, float offY, Path path, boolean showAntLine) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPath(offX, offY, path, showAntLine);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelectedPathForOutLine(float offX, float offY, Path path, boolean showAntLine) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onObjectSelectedPathForOutLine(offX, offY, path, showAntLine);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onStartAction(int selectActionType) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onStartAction(selectActionType);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onDoingAction(int selectActionType, SRectF selectRectF) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onDoingAction(selectActionType, selectRectF);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onEndAction(int selectActionType) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onEndAction(selectActionType);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectedHashCode(int hashCode) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onSelectedHashCode(hashCode);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onStartPasteFromCopy() {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onStartPasteFromCopy();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void isPointLimit() {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.isPointLimit();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public boolean isPagePointLimit(int pointCount) {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            return onSelectModelListener.isPagePointLimit(pointCount);
        }
        return false;
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void finishSelect() {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.finishSelect();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onCanvasChanged() {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onCanvasChanged();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectedDeletedEnd() {
        OnSelectModelListener onSelectModelListener = this.ref.get();
        if (onSelectModelListener != null) {
            onSelectModelListener.onSelectedDeletedEnd();
        }
    }
}
