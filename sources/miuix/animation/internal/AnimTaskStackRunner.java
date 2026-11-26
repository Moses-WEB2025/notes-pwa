package miuix.animation.internal;

import android.os.Trace;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import miuix.animation.IAnimTarget;
import miuix.animation.ValueTarget;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ColorProperty;
import miuix.animation.property.ViewPropertyExt;
import miuix.animation.styles.ForegroundColorStyle;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LogUtils;

/* JADX INFO: loaded from: classes5.dex */
class AnimTaskStackRunner {
    static int INIT_RESULT_CODE_FAILED = 1;
    static int INIT_RESULT_CODE_SUCCESS = 0;
    static int INIT_RESULT_CODE_VALUE_INVALID = 2;
    private static final String SECTION_TAG = "Folme.TaskRunner_doFrame";
    static final ThreadLocal<AnimData> animDataLocal = new ThreadLocal<>();
    static final ThreadLocal<List<UpdateInfo>> tempTaskUpdateList = new ThreadLocal<List<UpdateInfo>>() { // from class: miuix.animation.internal.AnimTaskStackRunner.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public List<UpdateInfo> initialValue() {
            return new ArrayList();
        }
    };

    AnimTaskStackRunner() {
    }

    /* JADX WARN: Code duplicated, block: B:83:0x01fe  */
    static void doAnimationFrame(AnimTask animTask, long j, long j2, int i, double d, boolean z) {
        AnimData animData;
        List<UpdateInfo> list;
        AnimTask animTask2;
        String str;
        AnimTask animTask3;
        AnimTask animTask4;
        int i2;
        int i3;
        UpdateInfo updateInfo;
        String str2;
        TransitionInfo transitionInfo;
        TransitionInfo transitionInfo2;
        boolean z2;
        AnimTask animTask5;
        boolean z3;
        AnimTask animTask6 = animTask;
        AnimScheduler animScheduler = animTask6.scheduler;
        if (animScheduler != null) {
            animData = (AnimData) CommonUtils.getLocal(animScheduler.mEngine.getObjPool(), animDataLocal, AnimData.class);
        } else {
            ThreadLocal<AnimData> threadLocal = animDataLocal;
            AnimData animData2 = threadLocal.get();
            if (animData2 == null) {
                animData2 = new AnimData();
                threadLocal.set(animData2);
            }
            animData = animData2;
        }
        animData.logEnabled = LogUtils.isLogDetailEnable() || LogUtils.isLogFrameEnable();
        if (animData.logEnabled) {
            Trace.beginSection("Folme.TaskRunner_doFrame " + Thread.currentThread().getId());
        }
        List<UpdateInfo> list2 = tempTaskUpdateList.get();
        boolean z4 = animData.logEnabled;
        String str3 = CommonUtils.TAG;
        if (z4) {
            LogUtils.logThread(CommonUtils.TAG, "↓---- TaskRunner.run start");
        }
        int i4 = 0;
        while (animTask6 != null) {
            AnimTask animTaskRemove = animTask6.remove();
            int i5 = i4 + 1;
            try {
                AnimStats animStats = animTask6.animStats;
                TransitionInfo transitionInfo3 = animTask6.info;
                animTask3 = animTaskRemove;
                try {
                    IAnimTarget iAnimTarget = transitionInfo3.target;
                    animStats.prepareOnFrameStart();
                    boolean zIsNeedSetup = animStats.isNeedSetup();
                    int animCount = animTask6.getAnimCount();
                    list2.clear();
                    list2.addAll(transitionInfo3.updateList);
                    int i6 = animTask6.startPos;
                    boolean z5 = false;
                    for (int i7 = animCount + i6; i6 < i7; i7 = i2) {
                        UpdateInfo updateInfo2 = list2.get(i6);
                        if (updateInfo2 == null) {
                            animTask5 = animTask6;
                            list = list2;
                            transitionInfo2 = transitionInfo3;
                            animTask2 = animTask3;
                            i2 = i7;
                            i3 = i6;
                            str = str3;
                        } else {
                            if (animData.logEnabled) {
                                list = list2;
                                try {
                                    LogUtils.logThread(str3, "------ data-start: info.id=" + transitionInfo3.id + " startImmediately=" + transitionInfo3.config.startImmediately + String.format(", p='%s'", updateInfo2.property.getName()) + " update.op=" + ((int) updateInfo2.animInfo.op) + ", " + animStats);
                                } catch (Exception e) {
                                    e = e;
                                    animTask2 = animTask3;
                                    str = str3;
                                    animTask3 = animTask2;
                                    LogUtils.logThread(str, "---- AnimTaskStackRunner.doAnimationFrame task:" + animTask3 + " failed: " + Log.getStackTraceString(e));
                                    str3 = str;
                                    animTask6 = animTask3;
                                    i4 = i5;
                                    list2 = list;
                                }
                            } else {
                                list = list2;
                            }
                            AnimSpecialConfig specialConfig = transitionInfo3.config.getSpecialConfig(updateInfo2.property.getName());
                            animData.from(updateInfo2, transitionInfo3.config, specialConfig);
                            if (zIsNeedSetup) {
                                animTask4 = animTask6;
                                i2 = i7;
                                i3 = i6;
                                updateInfo = updateInfo2;
                                str2 = ", ";
                                animTask2 = animTask3;
                                transitionInfo = transitionInfo3;
                                try {
                                    setup(animStats, animData, iAnimTarget, transitionInfo3.config, specialConfig, j, j2, transitionInfo3.key);
                                    if (animData.logEnabled) {
                                        printSetupLog(animData, iAnimTarget, transitionInfo, animStats);
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    str = str3;
                                    animTask3 = animTask2;
                                    LogUtils.logThread(str, "---- AnimTaskStackRunner.doAnimationFrame task:" + animTask3 + " failed: " + Log.getStackTraceString(e));
                                    str3 = str;
                                    animTask6 = animTask3;
                                    i4 = i5;
                                    list2 = list;
                                }
                            } else {
                                animTask4 = animTask6;
                                animTask2 = animTask3;
                                i2 = i7;
                                i3 = i6;
                                updateInfo = updateInfo2;
                                str2 = ", ";
                                transitionInfo = transitionInfo3;
                                if (animData.op == 6) {
                                    reuse(animStats, animData, iAnimTarget, transitionInfo.config, specialConfig, j, j2);
                                    if (animData.logEnabled) {
                                        LogUtils.logThread(str3, "++++++ data.reuse info.id=" + transitionInfo.id + String.format(", p='%s'", updateInfo.property.getName()) + " stats=" + animStats);
                                    }
                                }
                            }
                            if (animData.op == 1) {
                                transitionInfo2 = transitionInfo;
                                start(animStats, animData, iAnimTarget, j, j2, transitionInfo2);
                                updateInfo.animInfo.tintMode = animData.tintMode;
                                if (updateInfo.property == ViewPropertyExt.FOREGROUND) {
                                    ForegroundColorStyle.start(iAnimTarget, animData.tintMode);
                                }
                                if (animData.op == 5) {
                                    if (!transitionInfo2.hasSendNotifyStart && animScheduler != null) {
                                        animScheduler.executeNotifyTransitionBegin(transitionInfo2);
                                    }
                                    if (animStats.focusCount > 0 && transitionInfo2.config.isFocusPropertyForComplete(updateInfo.property)) {
                                        animStats.focusCount--;
                                    }
                                }
                            } else {
                                transitionInfo2 = transitionInfo;
                            }
                            if (animData.op == 2) {
                                if (!transitionInfo2.hasSendNotifyStart && animScheduler != null) {
                                    animScheduler.executeNotifyTransitionBegin(transitionInfo2);
                                }
                                if (animData.velocity == 0.0d) {
                                    try {
                                        if (transitionInfo2.config.startImmediately) {
                                            animData.justStart = false;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        str = str3;
                                        animTask3 = animTask2;
                                        LogUtils.logThread(str, "---- AnimTaskStackRunner.doAnimationFrame task:" + animTask3 + " failed: " + Log.getStackTraceString(e));
                                        str3 = str;
                                        animTask6 = animTask3;
                                        i4 = i5;
                                        list2 = list;
                                    }
                                }
                                if (animData.logEnabled) {
                                    LogUtils.logThread(str3, "++++++ data.update start data.justStart=" + animData.justStart);
                                }
                                str = str3;
                                TransitionInfo transitionInfo4 = transitionInfo2;
                                try {
                                    update(animStats, animData, iAnimTarget, j, j2, d, i, transitionInfo4);
                                    transitionInfo2 = transitionInfo4;
                                    if (animData.justEnd) {
                                        if (transitionInfo2.config.isFocusPropertyForComplete(updateInfo.property)) {
                                            z2 = true;
                                            try {
                                                animStats.focusEndCount++;
                                            } catch (Exception e4) {
                                                e = e4;
                                                animTask3 = animTask2;
                                                LogUtils.logThread(str, "---- AnimTaskStackRunner.doAnimationFrame task:" + animTask3 + " failed: " + Log.getStackTraceString(e));
                                                str3 = str;
                                                animTask6 = animTask3;
                                                i4 = i5;
                                                list2 = list;
                                            }
                                        } else {
                                            z2 = true;
                                        }
                                        if (animData.property == ViewPropertyExt.FOREGROUND) {
                                            ForegroundColorStyle.end(iAnimTarget, updateInfo);
                                        }
                                    } else {
                                        z2 = true;
                                    }
                                    if (LogUtils.isLogDesignEnable()) {
                                        Log.i(CommonUtils.D_TAG, String.format("update anim:{name:\"%s\", %s:%.10f, %s }", transitionInfo2.to.getAlias(), updateInfo.property.getName(), Double.valueOf(animData.value), Integer.toHexString((int) animData.value)));
                                    }
                                } catch (Exception e5) {
                                    e = e5;
                                    animTask3 = animTask2;
                                    LogUtils.logThread(str, "---- AnimTaskStackRunner.doAnimationFrame task:" + animTask3 + " failed: " + Log.getStackTraceString(e));
                                    str3 = str;
                                    animTask6 = animTask3;
                                    i4 = i5;
                                    list2 = list;
                                }
                            } else {
                                str = str3;
                                z2 = true;
                            }
                            animData.to(updateInfo);
                            if (animData.logEnabled) {
                                LogUtils.logThread(str, "------ data-end: info.id=" + transitionInfo2.id + String.format(", p='%s'", updateInfo.property.getName()) + " update.op=" + ((int) updateInfo.animInfo.op) + str2 + animStats);
                            }
                            if (z && AnimValueUtils.isValid(animData.value)) {
                                if (iAnimTarget instanceof ValueTarget) {
                                    animTask5 = animTask4;
                                    if (!animTask5.runInMainThread) {
                                        z3 = z2;
                                    }
                                    updateInfo.setTargetValue(iAnimTarget, z3);
                                    z5 = z3;
                                } else {
                                    animTask5 = animTask4;
                                }
                                z3 = z5;
                                updateInfo.setTargetValue(iAnimTarget, z3);
                                z5 = z3;
                            } else {
                                animTask5 = animTask4;
                            }
                        }
                        i6 = i3 + 1;
                        str3 = str;
                        animTask6 = animTask5;
                        transitionInfo3 = transitionInfo2;
                        list2 = list;
                        animTask3 = animTask2;
                    }
                    list = list2;
                    str = str3;
                } catch (Exception e6) {
                    e = e6;
                    list = list2;
                }
            } catch (Exception e7) {
                e = e7;
                list = list2;
                animTask2 = animTaskRemove;
            }
            str3 = str;
            animTask6 = animTask3;
            i4 = i5;
            list2 = list;
        }
        String str4 = str3;
        if (animData.logEnabled) {
            LogUtils.logThread(str4, "↑---- TaskRunner.run finish taskCount=" + i4);
            Trace.endSection();
        }
    }

    static void setup(AnimStats animStats, AnimData animData, IAnimTarget iAnimTarget, AnimConfig animConfig, AnimSpecialConfig animSpecialConfig, long j, long j2, Object obj) {
        double d = animData.startValue;
        if (d == Double.MAX_VALUE || d == 3.4028234663852886E38d || d == 2.147483647E9d) {
            animData.startValue = AnimValueUtils.getValue(iAnimTarget, animData.property, animData.startValue);
        }
        long j3 = j - j2;
        animData.initTime = j3;
        animStats.startedCount++;
        if (animData.op != 2 || animData.delay > 0) {
            animData.setOp((byte) 1);
            float fromSpeed = AnimConfigUtils.getFromSpeed(animConfig, animSpecialConfig);
            if (fromSpeed != Float.MAX_VALUE) {
                animData.velocity = fromSpeed;
            }
            if (animData.logEnabled) {
                LogUtils.logThread(CommonUtils.TAG, "++++++ data.setup path0");
                return;
            }
            return;
        }
        animData.startTime = j3;
        animData.delay = 0L;
        float fromSpeed2 = AnimConfigUtils.getFromSpeed(animConfig, animSpecialConfig);
        if (fromSpeed2 != Float.MAX_VALUE) {
            animData.velocity = fromSpeed2;
        }
        animStats.prepareCount--;
        setStartData(animData);
        if (animData.logEnabled) {
            LogUtils.logThread(CommonUtils.TAG, "++++++ data.setup path1");
            printSetupInUpdateLog(animData, iAnimTarget, obj);
        }
    }

    static void reuse(AnimStats animStats, AnimData animData, IAnimTarget iAnimTarget, AnimConfig animConfig, AnimSpecialConfig animSpecialConfig, long j, long j2) {
        if (AnimValueUtils.isInvalid(animData.startValue)) {
            animData.startValue = AnimValueUtils.getValue(iAnimTarget, animData.property, animData.startValue);
        }
        animData.initTime = j - j2;
        animData.setOp((byte) 1);
        if (animStats.failCount > 0) {
            animStats.failCount--;
        }
        if (animStats.focusEndCount > 0 && animConfig.isFocusPropertyForComplete(animData.property)) {
            animStats.focusEndCount--;
        }
        float fromSpeed = AnimConfigUtils.getFromSpeed(animConfig, animSpecialConfig);
        if (fromSpeed != Float.MAX_VALUE) {
            animData.velocity = fromSpeed;
        }
    }

    static void start(AnimStats animStats, AnimData animData, IAnimTarget iAnimTarget, long j, long j2, TransitionInfo transitionInfo) {
        long j3;
        if (animData.delay > 0) {
            if (animData.logEnabled) {
                j3 = j;
                printDelayTaskLog(animData, transitionInfo.key, j3);
            } else {
                j3 = j;
            }
            if (transitionInfo.currentTime < transitionInfo.startTime + (animData.delay * 1000000)) {
                return;
            }
            double value = AnimValueUtils.getValue(iAnimTarget, animData.property, Double.MAX_VALUE);
            if (value != Double.MAX_VALUE) {
                animData.startValue = value;
            }
            if (animData.logEnabled) {
                LogUtils.logThread(CommonUtils.TAG, "+++++ data.delay-start: time's up", "info.id=" + transitionInfo.id, String.format("p='%s'", animData.property.getName()));
            }
        } else {
            j3 = j;
        }
        animStats.prepareCount--;
        int iInitTask = initTask(iAnimTarget, animData, j3, j2);
        if (iInitTask != INIT_RESULT_CODE_SUCCESS) {
            finishProperty(animStats, animData);
            if (animData.logEnabled) {
                if (iInitTask == INIT_RESULT_CODE_FAILED) {
                    printSetValueFailedLog(animData, transitionInfo.key);
                    return;
                } else {
                    if (iInitTask == INIT_RESULT_CODE_VALUE_INVALID) {
                        printValueInvalidFailedLog(animData, transitionInfo.key);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        setStartData(animData);
        if (animData.logEnabled) {
            printStartFinishLog(animData, iAnimTarget, transitionInfo.key);
        }
    }

    private static void update(AnimStats animStats, AnimData animData, IAnimTarget iAnimTarget, long j, long j2, double d, int i, TransitionInfo transitionInfo) {
        double d2;
        if (animData.velocity == 0.0d && animData.justStart) {
            d2 = 0.0d;
        } else {
            animData.frameCount++;
            d2 = d;
        }
        animStats.updateCount++;
        animData.frameInterval = d2;
        animData.duration += d2;
        if (animData.property == ViewPropertyExt.FOREGROUND || animData.property == ViewPropertyExt.BACKGROUND || (animData.property instanceof ColorProperty)) {
            FolmeCore.doAnimationFrame(iAnimTarget, true, animData, j, d2, i);
        } else {
            FolmeCore.doAnimationFrame(iAnimTarget, false, animData, j, d2, i);
            if (animData.logEnabled) {
                LogUtils.logThread(CommonUtils.TAG, "------ data.update doAnimationFrame: info.id=" + transitionInfo.id, String.format("p='%s'", animData.property.getName()), "value=" + animData.value, "velocity=" + animData.velocity);
            }
        }
        if (animData.justStart) {
            animData.justStart = false;
        }
        if (animData.op == 3) {
            animData.justEnd = true;
            animStats.endCount++;
        }
        if (animData.logEnabled) {
            printUpdateAnimLog(animData, iAnimTarget, transitionInfo, d2);
        }
    }

    private static void setStartData(AnimData animData) {
        animData.progress = 0.0d;
        animData.reset();
    }

    private static int initTask(IAnimTarget iAnimTarget, AnimData animData, long j, long j2) {
        if ((animData.property instanceof ViewPropertyExt.ForegroundProperty) && !ForegroundColorStyle.isValid(iAnimTarget, animData)) {
            animData.value = animData.targetValue;
            animData.progress = 1.0d;
            return INIT_RESULT_CODE_FAILED;
        }
        if (!setValues(animData)) {
            return INIT_RESULT_CODE_FAILED;
        }
        if (isValueInvalid(animData)) {
            animData.reset();
            animData.value = animData.startValue;
            return INIT_RESULT_CODE_VALUE_INVALID;
        }
        animData.startTime = j - j2;
        animData.frameCount = 0;
        animData.setOp((byte) 2);
        return INIT_RESULT_CODE_SUCCESS;
    }

    private static boolean setValues(AnimData animData) {
        if (AnimValueUtils.isValid(animData.value)) {
            if (AnimValueUtils.isInvalid(animData.startValue)) {
                animData.startValue = animData.value;
            }
            return true;
        }
        if (!AnimValueUtils.isValid(animData.startValue)) {
            return false;
        }
        animData.value = animData.startValue;
        return true;
    }

    private static void finishProperty(AnimStats animStats, AnimData animData) {
        animData.setOp((byte) 5);
        animStats.failCount++;
    }

    private static boolean isValueInvalid(AnimData animData) {
        return animData.startValue == animData.targetValue && Math.abs(animData.velocity) < 16.66666603088379d;
    }

    private static void printDelayTaskLog(AnimData animData, Object obj, long j) {
        LogUtils.logThread(CommonUtils.TAG, "++++++ data.start:check delay", "tag=" + obj + "@" + obj.hashCode(), String.format("p='%s'", animData.property.getName()), "delay=" + animData.delay, "op=" + ((int) animData.op), "initTime=" + animData.initTime, "totalT_ms=" + ((j * 1.0d) / 1000000.0d));
    }

    private static void printSetValueFailedLog(AnimData animData, Object obj) {
        LogUtils.logThread(CommonUtils.TAG, "++++++ data.start:setValueFailed, break", String.format("p='%s'", animData.property.getName()), "tag=" + obj + "@" + obj.hashCode(), "op=" + ((int) animData.op), "value=" + animData.value, "start-v=" + animData.startValue, "target-v= " + animData.targetValue);
    }

    private static void printValueInvalidFailedLog(AnimData animData, Object obj) {
        LogUtils.logThread(CommonUtils.TAG, "++++++ data.start:valueInvalidFailedLog, start-v equal target-v, so break", String.format("p='%s'", animData.property.getName()), "tag=" + obj + "@" + obj.hashCode(), "op=" + ((int) animData.op), "value=" + animData.value, "start-v=" + animData.startValue, "target-v=" + animData.targetValue, "velocity=" + animData.velocity);
    }

    private static void printStartFinishLog(AnimData animData, IAnimTarget iAnimTarget, Object obj) {
        LogUtils.logThread(CommonUtils.TAG, "++++++ data.start:finish", String.format("p='%s'", animData.property.getName()), "tag=" + obj + "@" + obj.hashCode(), "op=" + ((int) animData.op), "value=" + animData.value, "start-v=" + animData.startValue, "target-v=" + animData.targetValue, "progress=" + animData.progress, "ease=" + animData.ease, "delay=" + animData.delay, "velocity=" + animData.velocity, "target=" + iAnimTarget);
    }

    private static void printSetupLog(AnimData animData, IAnimTarget iAnimTarget, TransitionInfo transitionInfo, AnimStats animStats) {
        LogUtils.logThread(CommonUtils.TAG, "++++++ data.setup: info.id=" + transitionInfo.id, String.format("p='%s'", animData.property.getName()), "tag=" + transitionInfo.key + "@" + transitionInfo.key.hashCode(), "value=" + animData.value, "start-v=" + animData.startValue, "target-v=" + animData.targetValue, "progress=" + animData.progress, "animStats=" + animStats, "ease=" + animData.ease, "velocity=" + animData.velocity, "delay=" + animData.delay, "op=" + ((int) animData.op), "target=" + iAnimTarget);
    }

    private static void printSetupInUpdateLog(AnimData animData, IAnimTarget iAnimTarget, Object obj) {
        LogUtils.logThread(CommonUtils.TAG, "++++++ data.setup when op is update and no delay: " + String.format("p='%s'", animData.property.getName()), "tag=" + obj + "@" + obj.hashCode(), "value=" + animData.value, "start-v=" + animData.startValue, "target-v=" + animData.targetValue, "target=" + iAnimTarget, "ease=" + animData.ease, "progress=" + animData.progress, "velocity=" + animData.velocity, "delay=" + animData.delay, "op=" + ((int) animData.op));
    }

    private static void printUpdateAnimLog(AnimData animData, IAnimTarget iAnimTarget, TransitionInfo transitionInfo, double d) {
        LogUtils.logThread(CommonUtils.TAG, "------ data.update: info.id=" + transitionInfo.id, String.format("p='%s'", animData.property.getName()), "tag=" + transitionInfo.key + "@" + transitionInfo.key.hashCode(), "op=" + ((int) animData.op), "frame=" + animData.frameCount, "value=" + animData.value, "start-v=" + animData.startValue, "target-v=" + animData.targetValue, "value_hex=" + Integer.toHexString((int) animData.value), "delta_s=" + d, "interval=" + animData.frameInterval, "progress=" + animData.progress, "target=" + iAnimTarget, "justEnd=" + animData.justEnd, "init-t=" + animData.initTime, "start-t=" + animData.startTime, "velocity=" + animData.velocity);
    }
}
