package com.miui.floatwindow;

import android.content.res.Configuration;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.market.sdk.utils.Constants;
import com.miui.notes.excerpt.ExcerptInfo;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FloatWindowManagerAgent.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u0006\u0010\b\u001a\u00020\u0005J\u0006\u0010\t\u001a\u00020\u0005J\b\u0010\n\u001a\u00020\u0005H\u0007J\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\rJ\b\u0010\u0010\u001a\u00020\u0005H\u0007J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\rJ\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\rH\u0007J\u001a\u0010\u0015\u001a\u00020\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u000e\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010\u001d\u001a\u00020\u0005H\u0007J\b\u0010\u001e\u001a\u00020\u0005H\u0007J\b\u0010#\u001a\u00020\u0005H\u0007J\u0010\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\rH\u0007J\b\u0010&\u001a\u00020\u0005H\u0007J\b\u0010*\u001a\u00020\rH\u0007J\b\u0010+\u001a\u00020\u0005H\u0007J\u001a\u0010,\u001a\u00020\u00052\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020\rH\u0007J\b\u00100\u001a\u00020\u0005H\u0007J\u000e\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\rR\u001a\u0010\u001f\u001a\u00020\u00198FX\u0087\u0004¢\u0006\f\u0012\u0004\b \u0010\u0003\u001a\u0004\b!\u0010\"R\u001a\u0010'\u001a\u00020\r8FX\u0087\u0004¢\u0006\f\u0012\u0004\b(\u0010\u0003\u001a\u0004\b'\u0010)¨\u00063"}, d2 = {"Lcom/miui/floatwindow/FloatWindowManagerAgent;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onServiceConfigChanged", "", "newConfig", "Landroid/content/res/Configuration;", "startFloatWindowService", "stopFloatWindowService", "changeFullLayoutToGuideService", "updateScreenOn", "keepScreenOn", "", "showQuickNoteUI", "show", "updateDockMode", "onDockModeChanged", "enterDock", "changeGuideVisible", "visible", "showToast", "content", "", "time", "", "excerptInfoChange", Constants.JSON_FILTER_INFO, "Lcom/miui/notes/excerpt/ExcerptInfo;", "startContinuousExcerpt", "stopContinuousExcerpt", "excerptId", "getExcerptId$annotations", "getExcerptId", "()J", "pauseContinuousExcerpt", "setRecordState", "isInRecord", "resumeContinuousExcerpt", "isContinuousExcerpt", "isContinuousExcerpt$annotations", "()Z", "clickExcerptNote", "resetClickExcerptNote", "saveSingleExcerpt", "uuid", "Ljava/util/UUID;", "isFromCardClick", "notifyDataChanged", "hideOrShowCoverView", "isShow", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatWindowManagerAgent {
    public static final FloatWindowManagerAgent INSTANCE = new FloatWindowManagerAgent();

    @JvmStatic
    public static /* synthetic */ void getExcerptId$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void isContinuousExcerpt$annotations() {
    }

    private FloatWindowManagerAgent() {
    }

    public final void onServiceConfigChanged(Configuration newConfig) {
        FloatWindowManager.INSTANCE.getInstance().onServiceConfigChanged(newConfig);
    }

    public final void startFloatWindowService() {
        FloatWindowManager.INSTANCE.getInstance().createFloatTodoWindow();
    }

    public final void stopFloatWindowService() {
        FloatWindowManager.INSTANCE.getInstance().removeFloatTodoWindow();
    }

    @JvmStatic
    public static final void changeFullLayoutToGuideService() {
        FloatWindowManager.INSTANCE.getInstance().changeFullLayoutToGuide();
    }

    public final void updateScreenOn(boolean keepScreenOn) {
        FloatWindowManager.INSTANCE.getInstance().updateScreenOn(keepScreenOn);
    }

    public final void showQuickNoteUI(boolean show) {
        FloatWindowManager.INSTANCE.getInstance().showQuickNoteUI(show);
    }

    @JvmStatic
    public static final void updateDockMode() {
        FloatWindowManager.INSTANCE.getInstance().updateDockMode();
    }

    public final void onDockModeChanged(boolean enterDock) {
        FloatWindowManager.INSTANCE.getInstance().onDockModeChanged(enterDock);
    }

    @JvmStatic
    public static final void changeGuideVisible(boolean visible) {
        FloatWindowManager.INSTANCE.getInstance().changeGuideVisible(visible);
    }

    @JvmStatic
    public static final void showToast(String content, long time) {
        FloatWindowManager.INSTANCE.getInstance().showToast(content, time);
    }

    public final void excerptInfoChange(ExcerptInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        FloatWindowManager.INSTANCE.getInstance().excerptInfoChange(info);
    }

    @JvmStatic
    public static final void startContinuousExcerpt() {
        FloatWindowManager.INSTANCE.getInstance().startContinuousExcerpt();
    }

    @JvmStatic
    public static final void stopContinuousExcerpt() {
        FloatWindowManager.INSTANCE.getInstance().stopContinuousExcerpt();
    }

    public static final long getExcerptId() {
        return FloatWindowManager.INSTANCE.getInstance().getExcerptId();
    }

    @JvmStatic
    public static final void pauseContinuousExcerpt() {
        FloatWindowManager.INSTANCE.getInstance().pauseContinuousExcerpt();
    }

    @JvmStatic
    public static final void setRecordState(boolean isInRecord) {
        FloatWindowManager.INSTANCE.getInstance().setRecordState(isInRecord);
    }

    @JvmStatic
    public static final void resumeContinuousExcerpt() {
        FloatWindowManager.INSTANCE.getInstance().resumeContinuousExcerpt();
    }

    public static final boolean isContinuousExcerpt() {
        return FloatWindowManager.INSTANCE.getInstance().isContinuousExcerpt();
    }

    @JvmStatic
    public static final boolean clickExcerptNote() {
        return FloatWindowManager.INSTANCE.getInstance().clickExcerptNote();
    }

    @JvmStatic
    public static final void resetClickExcerptNote() {
        FloatWindowManager.INSTANCE.getInstance().resetClickExcerptNote();
    }

    @JvmStatic
    public static final void saveSingleExcerpt(UUID uuid, boolean isFromCardClick) {
        FloatWindowManager.INSTANCE.getInstance().saveSingleExcerpt(uuid, isFromCardClick);
    }

    @JvmStatic
    public static final void notifyDataChanged() {
        FloatWindowManager.INSTANCE.getInstance().notifyDataChanged();
    }

    public final void hideOrShowCoverView(boolean isShow) {
        FloatWindowManager.INSTANCE.getInstance().hideOrShowCoverView(isShow);
    }
}
