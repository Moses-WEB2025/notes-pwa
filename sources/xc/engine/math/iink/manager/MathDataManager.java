package xc.engine.math.iink.manager;

import android.graphics.RectF;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.PageInkModelInterface;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.math.iink.bean.MathResult;
import xc.engine.math.iink.callback.MathResultCallback;
import xc.engine.math.iink.callback.MathSelectCallback;
import xc.engine.math.iink.ui.RecognitionModel;

/* JADX INFO: compiled from: MathDataManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001a\u0018\u0000 I2\u00020\u0001:\u0001IB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0006J\u000e\u0010\"\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0006J\u001c\u0010#\u001a\u00020 2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010'\u001a\u00020(J\u0006\u0010)\u001a\u00020 J\u0016\u0010*\u001a\u00020 2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\nJ\u000e\u0010-\u001a\u00020 2\u0006\u0010+\u001a\u00020\tJ\u0016\u0010.\u001a\u00020 2\u0006\u0010+\u001a\u00020\t2\u0006\u0010/\u001a\u000200J\u000e\u00101\u001a\u00020 2\u0006\u0010+\u001a\u00020\tJ\u0010\u00102\u001a\u0004\u0018\u00010\n2\u0006\u0010+\u001a\u00020\tJ\u000e\u00103\u001a\u00020 2\u0006\u00104\u001a\u00020\rJ\u0006\u00105\u001a\u00020\u0014J\u000e\u00106\u001a\u00020 2\u0006\u00107\u001a\u00020\rJ\u0006\u00108\u001a\u00020\rJ\u000e\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020\rJ\u0006\u0010;\u001a\u00020\rJ\u0006\u0010<\u001a\u00020\u0014J\u000e\u0010=\u001a\u00020 2\u0006\u0010>\u001a\u00020&J\u0014\u0010=\u001a\u00020 2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020&0%J\u0014\u0010@\u001a\u00020 2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020&0%J\u000e\u0010B\u001a\u00020 2\u0006\u0010C\u001a\u00020\u0012J\b\u0010D\u001a\u0004\u0018\u00010\u0012J\u000e\u0010E\u001a\u00020 2\u0006\u0010F\u001a\u00020\rJ\u000e\u0010G\u001a\u00020 2\u0006\u0010H\u001a\u00020\u0014J\u0006\u0010\u0019\u001a\u00020\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006J"}, d2 = {"Lxc/engine/math/iink/manager/MathDataManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mathSelectCallBackList", "", "Lxc/engine/math/iink/callback/MathSelectCallback;", "mathResultCallbackMap", "Ljava/util/HashMap;", "", "Lxc/engine/math/iink/callback/MathResultCallback;", "Lkotlin/collections/HashMap;", "currentSelectPenType", "", "currentSelectState", "currentBehavior", "mathSystem", "Ljava/lang/ref/WeakReference;", "Lxc/engine/core/graphics/engine/systems/MathSystem;", "isSelect", "", "()Z", "setSelect", "(Z)V", "isMathAutoSelect", "hasUserStartedDrawing", "copyAndPasteStatus", "getCopyAndPasteStatus", "()I", "setCopyAndPasteStatus", "(I)V", "registerMathSelectCallBack", "", "mathSelectCallBack", "removeMathSelectCallBack", "notifyMathSelectCallBack", "curves", "", "Lxc/engine/data/pagedata/DataCurve;", "rectF", "Landroid/graphics/RectF;", "notifyCancelSelectCallBack", "registerMathResultCallback", "tag", "mathResultCallback", "removeMathResultCallback", "notifyMathResultCallback", "result", "Lxc/engine/math/iink/bean/MathResult;", "notifyMathResultDeleteCallBack", "getMathResultCallback", "setSelectState", "state", "isMathSelect", "setCurrentSelectPenType", "type", "getCurrentSelectPenType", "setBehavior", "behavior", "getBehavior", "isCanCancelMathSelect", "doDeleteMathData", "dataCurve", "dataCurveList", "disShowMathResult", "curveList", "setMathSystem", "system", "getMathSystem", "notifyPageDataChanged", "pageIndex", "setUserStartedDrawing", "started", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MathDataManager {
    public static final int AI_SELECT = 8;
    public static final int BALLPOINT = 2;
    public static final int CURRENT_COPY_BEHAVIOR = 3;
    public static final int CURRENT_NORMAL_BEHAVIOR = 2;
    public static final int CURRENT_NORMAL_STATE = 0;
    public static final int CURRENT_SELECT_STATE = 1;
    public static final int ERASER = 7;
    public static final int INK_PEN = 1;
    public static final int MARK = 4;
    public static final int PENCIL = 3;
    public static final int SELECT = 6;
    private static final String TAG = "MathDataManager";
    public static final int WATER_COLOR = 10;
    private int copyAndPasteStatus;
    private int currentSelectPenType;
    private int currentSelectState;
    public boolean hasUserStartedDrawing;
    public boolean isMathAutoSelect;
    private boolean isSelect;
    private WeakReference<MathSystem> mathSystem;
    private final List<MathSelectCallback> mathSelectCallBackList = new ArrayList();
    private final HashMap<String, MathResultCallback> mathResultCallbackMap = new HashMap<>();
    private int currentBehavior = 2;

    /* JADX INFO: renamed from: isSelect, reason: from getter */
    public final boolean getIsSelect() {
        return this.isSelect;
    }

    public final void setSelect(boolean z) {
        this.isSelect = z;
    }

    public final int getCopyAndPasteStatus() {
        return this.copyAndPasteStatus;
    }

    public final void setCopyAndPasteStatus(int i) {
        this.copyAndPasteStatus = i;
    }

    public final void registerMathSelectCallBack(MathSelectCallback mathSelectCallBack) {
        Intrinsics.checkNotNullParameter(mathSelectCallBack, "mathSelectCallBack");
        this.mathSelectCallBackList.add(mathSelectCallBack);
    }

    public final void removeMathSelectCallBack(MathSelectCallback mathSelectCallBack) {
        Intrinsics.checkNotNullParameter(mathSelectCallBack, "mathSelectCallBack");
        this.mathSelectCallBackList.remove(mathSelectCallBack);
    }

    public final void notifyMathSelectCallBack(List<? extends DataCurve> curves, RectF rectF) {
        Intrinsics.checkNotNullParameter(curves, "curves");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        Iterator<T> it = this.mathSelectCallBackList.iterator();
        while (it.hasNext()) {
            ((MathSelectCallback) it.next()).onMathSelectRequired(rectF, curves);
        }
    }

    public final void notifyCancelSelectCallBack() {
        Iterator<T> it = this.mathSelectCallBackList.iterator();
        while (it.hasNext()) {
            ((MathSelectCallback) it.next()).onExitSelect();
        }
    }

    public final void registerMathResultCallback(String tag, MathResultCallback mathResultCallback) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(mathResultCallback, "mathResultCallback");
        this.mathResultCallbackMap.put(tag, mathResultCallback);
    }

    public final void removeMathResultCallback(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        this.mathResultCallbackMap.remove(tag);
    }

    public final void notifyMathResultCallback(String tag, MathResult result) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(result, "result");
        MathResultCallback mathResultCallback = this.mathResultCallbackMap.get(tag);
        if (mathResultCallback != null) {
            mathResultCallback.onMathResult(result);
        }
    }

    public final void notifyMathResultDeleteCallBack(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        MathResultCallback mathResultCallback = this.mathResultCallbackMap.get(tag);
        if (mathResultCallback != null) {
            mathResultCallback.doDelete();
        }
    }

    public final MathResultCallback getMathResultCallback(String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return this.mathResultCallbackMap.get(tag);
    }

    public final void setSelectState(int state) {
        Log.d(TAG, "setSelectState: " + state);
        this.currentSelectState = state;
    }

    public final boolean isMathSelect() {
        return this.currentSelectState == 1;
    }

    public final void setCurrentSelectPenType(int type) {
        Log.d(TAG, "setCurrentSelectPenType: " + type);
        this.currentSelectPenType = type;
    }

    public final int getCurrentSelectPenType() {
        return this.currentSelectPenType;
    }

    public final void setBehavior(int behavior) {
        this.currentBehavior = behavior;
    }

    /* JADX INFO: renamed from: getBehavior, reason: from getter */
    public final int getCurrentBehavior() {
        return this.currentBehavior;
    }

    public final boolean isCanCancelMathSelect() {
        int i;
        Log.d(TAG, "isCanCancelMathSelect: CurrentSelectState:" + this.currentSelectState + ",CurrentSelectPenType:" + this.currentSelectPenType + ",CurrentSelectBehavior:" + this.currentBehavior);
        return this.currentSelectState == 1 && (i = this.currentSelectPenType) != 6 && i != 8 && this.currentBehavior == 2;
    }

    public final void doDeleteMathData(DataCurve dataCurve) {
        boolean z;
        RecognitionModel recognitionModel;
        Intrinsics.checkNotNullParameter(dataCurve, "dataCurve");
        Log.d(TAG, "doDeleteMathData: ");
        ArrayList arrayList = new ArrayList();
        String str = dataCurve.strokeId;
        if (str == null || str.length() == 0) {
            z = false;
        } else {
            String strPadEnd = dataCurve.strokeId;
            if (strPadEnd.length() < 20) {
                Intrinsics.checkNotNull(strPadEnd);
                strPadEnd = StringsKt.padEnd(strPadEnd, 20, '0');
                Log.d(TAG, "doDeleteMathData: padded strokeId from " + dataCurve.strokeId.length() + " to 20 chars");
            }
            Intrinsics.checkNotNull(strPadEnd);
            arrayList.add(strPadEnd);
            dataCurve.strokeId = "";
            z = true;
        }
        ArrayList arrayList2 = arrayList;
        if (!arrayList2.isEmpty()) {
            String str2 = dataCurve.recordID;
            String str3 = str2 != null ? str2 : "";
            MathSystem mathSystem = getMathSystem();
            if (mathSystem != null && (recognitionModel = mathSystem.getRecognitionModel()) != null) {
                recognitionModel.erase((String[]) arrayList2.toArray(new String[0]), CollectionsKt.listOf(str3));
            }
        }
        if (z) {
            notifyPageDataChanged(dataCurve.pageIndex);
        }
    }

    public final void doDeleteMathData(List<? extends DataCurve> dataCurveList) {
        RecognitionModel recognitionModel;
        Intrinsics.checkNotNullParameter(dataCurveList, "dataCurveList");
        if (dataCurveList.isEmpty()) {
            return;
        }
        Log.d(TAG, "doDeleteMathData: count=" + dataCurveList.size());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (DataCurve dataCurve : dataCurveList) {
            synchronized (this) {
                String strPadEnd = dataCurve.strokeId;
                String str = strPadEnd;
                if (str != null && str.length() != 0) {
                    if (strPadEnd.length() < 20) {
                        strPadEnd = StringsKt.padEnd(strPadEnd, 20, '0');
                    }
                    arrayList.add(strPadEnd);
                    String str2 = dataCurve.recordID;
                    if (str2 == null) {
                        str2 = "";
                    }
                    arrayList2.add(str2);
                    dataCurve.strokeId = "";
                    dataCurve.recordID = "";
                    linkedHashSet.add(Integer.valueOf(dataCurve.pageIndex));
                } else {
                    String str3 = dataCurve.recordID;
                    if (str3 == null) {
                        str3 = "";
                    }
                    arrayList2.add(str3);
                    dataCurve.recordID = "";
                }
                Unit unit = Unit.INSTANCE;
            }
        }
        ArrayList arrayList3 = arrayList;
        if (!arrayList3.isEmpty()) {
            MathSystem mathSystem = getMathSystem();
            if (mathSystem != null && (recognitionModel = mathSystem.getRecognitionModel()) != null) {
                recognitionModel.erase((String[]) arrayList3.toArray(new String[0]), arrayList2);
            }
        } else if (!dataCurveList.isEmpty()) {
            Log.d(TAG, "doDeleteMathData: no valid strokeIds to erase, but cleaned up " + dataCurveList.size() + " dataCurves");
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            notifyPageDataChanged(((Number) it.next()).intValue());
        }
    }

    public final void disShowMathResult(List<? extends DataCurve> curveList) {
        String string;
        RecognitionModel recognitionModel;
        RecognitionModel recognitionModel2;
        String itemsBlockId;
        Intrinsics.checkNotNullParameter(curveList, "curveList");
        Log.d(TAG, "doDeleteMathData: ");
        ArrayList arrayList = new ArrayList();
        String strokeId = curveList.get(0).strokeId;
        Intrinsics.checkNotNullExpressionValue(strokeId, "strokeId");
        arrayList.add(strokeId);
        MathSystem mathSystem = getMathSystem();
        if (mathSystem == null || (recognitionModel2 = mathSystem.getRecognitionModel()) == null || (itemsBlockId = recognitionModel2.getItemsBlockId((String) arrayList.get(0))) == null || (string = itemsBlockId.toString()) == null) {
            string = "";
        }
        if (mathSystem == null || (recognitionModel = mathSystem.getRecognitionModel()) == null) {
            return;
        }
        recognitionModel.notifyMathResultClear(string);
    }

    public final void setMathSystem(MathSystem system) {
        Intrinsics.checkNotNullParameter(system, "system");
        this.mathSystem = new WeakReference<>(system);
    }

    public final MathSystem getMathSystem() {
        WeakReference<MathSystem> weakReference = this.mathSystem;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public final void notifyPageDataChanged(int pageIndex) {
        MiCurveEngine theCurveEngine;
        PageInkModelInterface pageInkModelInterface;
        try {
            MathSystem mathSystem = getMathSystem();
            if (mathSystem != null && (theCurveEngine = mathSystem.getTheCurveEngine()) != null && (pageInkModelInterface = theCurveEngine.getPageInkModelInterface()) != null) {
                pageInkModelInterface.mathDataHaveChange(pageIndex);
            }
            Log.d(TAG, "notifyPageDataChanged: pageIndex=" + pageIndex);
        } catch (Exception e) {
            Log.w(TAG, "notifyPageDataChanged failed: " + e.getMessage());
        }
    }

    public final void setUserStartedDrawing(boolean started) {
        this.hasUserStartedDrawing = started;
    }

    /* JADX INFO: renamed from: hasUserStartedDrawing, reason: from getter */
    public final boolean getHasUserStartedDrawing() {
        return this.hasUserStartedDrawing;
    }
}
