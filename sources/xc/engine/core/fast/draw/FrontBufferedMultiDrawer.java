package xc.engine.core.fast.draw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.util.Log;
import androidx.graphics.lowlatency.BufferInfo;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.PenEngine;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.operate.canvas.ICanvasOperateListener;
import xc.engine.operate.canvas.track.IOperator;
import xc.engine.operate.canvas.track.curve.CurveOperator;
import xc.engine.operate.canvas.track.delete.DeleteProp;

/* JADX INFO: compiled from: FrontBufferedMultiDrawer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000S\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b*\u0001\u0013\u0018\u0000 %2\u00020\u0001:\u0001%B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J \u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u00162\b\u0010\u0010\u001a\u0004\u0018\u00010\u001eJ\n\u0010\u001f\u001a\u0004\u0018\u00010\u0018H\u0002J\u0010\u0010 \u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\"\u001a\u00020\u0016H\u0002J\u0006\u0010#\u001a\u00020\u0016J\u0006\u0010$\u001a\u00020\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014¨\u0006&"}, d2 = {"Lxc/engine/core/fast/draw/FrontBufferedMultiDrawer;", "", "penEngine", "Lxc/engine/api/PenEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/api/PenEngine;)V", "curveEngine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", "getCurveEngine", "()Lxc/engine/core/graphics/engine/MiCurveEngine;", "setCurveEngine", "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "mWindowRect", "Landroid/graphics/Rect;", "isViewportInitialized", "", "deleteProp", "", "mReFreshBroadcastReceiver", "xc/engine/core/fast/draw/FrontBufferedMultiDrawer$mReFreshBroadcastReceiver$1", "Lxc/engine/core/fast/draw/FrontBufferedMultiDrawer$mReFreshBroadcastReceiver$1;", "fastDrawingAtFrontNew", "", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "param", "Lxc/engine/core/fast/data/CurveSegment;", "setDeleteProp", "Lxc/engine/operate/canvas/track/delete/DeleteProp;", "obtainCurrentCurveData", "updateViewport", "solidifyDrawingAtDoubleBuffer", "registerBroadCast", "clearContentForce", "onDestroy", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrontBufferedMultiDrawer {
    private MiCurveEngine curveEngine;
    private float deleteProp;
    private boolean isViewportInitialized;
    private final FrontBufferedMultiDrawer$mReFreshBroadcastReceiver$1 mReFreshBroadcastReceiver;
    private Rect mWindowRect;
    private final PenEngine penEngine;
    private static final String TAG = "FrontBufferedMultiDrawer";
    private static final String RECEIVER_ACTION_RENDER = "com.miui.creation.render";

    public final void clearContentForce() {
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [xc.engine.core.fast.draw.FrontBufferedMultiDrawer$mReFreshBroadcastReceiver$1] */
    public FrontBufferedMultiDrawer(PenEngine penEngine) {
        Intrinsics.checkNotNullParameter(penEngine, "penEngine");
        this.penEngine = penEngine;
        this.curveEngine = new MiCurveEngine(penEngine);
        this.mWindowRect = new Rect();
        this.mReFreshBroadcastReceiver = new BroadcastReceiver() { // from class: xc.engine.core.fast.draw.FrontBufferedMultiDrawer$mReFreshBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, FrontBufferedMultiDrawer.RECEIVER_ACTION_RENDER)) {
                    Log.d(FrontBufferedMultiDrawer.TAG, "onReceive: onCanvasDataChanged");
                    ICanvasOperateListener iCanvasOperateListener = this.this$0.penEngine.mCanvasOperator.mCanvasOperateListener;
                    if (iCanvasOperateListener != null) {
                        iCanvasOperateListener.onCanvasDataChanged(null, 11);
                    }
                }
            }
        };
    }

    public final MiCurveEngine getCurveEngine() {
        return this.curveEngine;
    }

    public final void setCurveEngine(MiCurveEngine miCurveEngine) {
        Intrinsics.checkNotNullParameter(miCurveEngine, "<set-?>");
        this.curveEngine = miCurveEngine;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0016 A[Catch: all -> 0x0024, Exception -> 0x0026, TryCatch #2 {Exception -> 0x0026, blocks: (B:4:0x000e, B:5:0x0012, B:7:0x0016, B:8:0x001c), top: B:15:0x000e }] */
    public final void fastDrawingAtFrontNew(DataCurve dataCurve, BufferInfo bufferInfo, CurveSegment param) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        Intrinsics.checkNotNullParameter(param, "param");
        if (dataCurve == null) {
            try {
                dataCurve = obtainCurrentCurveData();
                if (!this.isViewportInitialized) {
                    updateViewport(bufferInfo);
                    this.isViewportInitialized = true;
                }
                this.curveEngine.onRenderSingleBuffer(bufferInfo, dataCurve, param, this.deleteProp);
                return;
            } catch (Exception e) {
                Log.w(TAG, "xqw- fastDrawingAtFrontNew:" + e);
                return;
            }
        }
        if (!this.isViewportInitialized) {
            updateViewport(bufferInfo);
            this.isViewportInitialized = true;
        }
        this.curveEngine.onRenderSingleBuffer(bufferInfo, dataCurve, param, this.deleteProp);
        return;
        throw th;
    }

    public final void setDeleteProp(DeleteProp deleteProp) {
        Float fValueOf = deleteProp != null ? Float.valueOf(deleteProp.getDeleteRadius()) : null;
        Intrinsics.checkNotNull(fValueOf);
        this.deleteProp = fValueOf.floatValue();
    }

    private final DataCurve obtainCurrentCurveData() {
        IOperator iOperator = this.penEngine.mTouchEventOperator.mOperator;
        if (iOperator != null && (iOperator instanceof CurveOperator)) {
            return ((CurveOperator) iOperator).getCurDataCurve();
        }
        return null;
    }

    private final void updateViewport(BufferInfo bufferInfo) {
        int width = bufferInfo.getWidth();
        int height = bufferInfo.getHeight();
        if (this.mWindowRect.width() == width && this.mWindowRect.height() == height) {
            return;
        }
        this.mWindowRect.set(0, 0, width, height);
        this.curveEngine.onScreenResize(width, height);
    }

    public final void solidifyDrawingAtDoubleBuffer(BufferInfo bufferInfo) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        if (!this.curveEngine.getIsInitialized()) {
            MiCurveEngine miCurveEngine = this.curveEngine;
            Context mContext = this.penEngine.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            miCurveEngine.init(mContext);
        }
        updateViewport(bufferInfo);
        registerBroadCast();
        this.curveEngine.onRenderMultiBuffer(bufferInfo);
    }

    private final void registerBroadCast() {
        try {
            LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).unregisterReceiver(this.mReFreshBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_RENDER);
        LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).registerReceiver(this.mReFreshBroadcastReceiver, intentFilter);
    }

    public final void onDestroy() {
        if (this.curveEngine.getIsInitialized()) {
            this.curveEngine.destroy();
        }
        try {
            LocalBroadcastManager.getInstance(CreationPageSDK.sApplication).unregisterReceiver(this.mReFreshBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error unregistering receiver", e);
        }
    }
}
