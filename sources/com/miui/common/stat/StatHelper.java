package com.miui.common.stat;

import android.content.Context;
import android.text.TextUtils;
import com.miui.common.onetrack.IOneTrackStat;
import com.miui.common.onetrack.OneTrackStatImp;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.cta.CTAManager;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes2.dex */
public class StatHelper {
    private static String DEVICE_LEVEL = "device_level";
    private static String MODEL_TYPE = "model_type";
    private static String SCREEN_DIRECTION = "screen_direction";
    private static String SCREEN_TYPE = "screen_type";
    private static final String TAG = "Notes:StatHelper";
    private static String TRACK_ID = "track_id";
    private static AITextWidgetStat mAiTextStat;
    private static volatile IOneTrackStat mStatImplInstance;
    private static StatHelper mStatInstance;
    private String mDeviceLevel;
    private long mEnterTime;
    private String mModelType;
    private String mScreenDirection;
    private String mScreenType;
    private String mUUID;

    private static class Holder {
        private static boolean ENABLED = initialize();

        private Holder() {
        }

        private static boolean initialize() {
            Logger.INSTANCE.d(StatHelper.TAG, "initialize");
            if (RomUtils.isInternationalBuild()) {
                return false;
            }
            StatHelper.initAITextWidgetStat();
            if (CTAManager.getInstance().isAccepted(NoteApp.getInstance())) {
                doInitialize();
                return true;
            }
            CTAManager.getInstance().addListener(new CTAManager.CTAListener() { // from class: com.miui.common.stat.StatHelper.Holder.1
                @Override // com.miui.notes.cta.CTAManager.CTAListener
                public void onAccept() {
                    Holder.doInitialize();
                    Holder.ENABLED = true;
                }

                @Override // com.miui.notes.cta.CTAManager.CTAListener
                public void onReject() {
                    Holder.ENABLED = false;
                }
            });
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void doInitialize() {
            if (StatHelper.mStatInstance == null) {
                StatHelper.mStatInstance = new StatHelper(NoteApp.getInstance());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initAITextWidgetStat() {
        if (mAiTextStat == null) {
            mAiTextStat = new AITextWidgetStat();
        }
        mAiTextStat.setAITextWidgetStat(new AITextWidgetStat.AITextStatListener() { // from class: com.miui.common.stat.StatHelper$$ExternalSyntheticLambda0
            @Override // com.miui.notes.ai.stat.AITextWidgetStat.AITextStatListener
            public final void onAiTextStat(String str, String str2, Map map) {
                StatHelper.track(str, str2, map);
            }
        });
    }

    private StatHelper(Context context) {
        try {
            getStatImpInstance().init(context);
            this.mUUID = UUID.randomUUID().toString();
            this.mModelType = RomUtils.getModelType();
            this.mDeviceLevel = RomUtils.getDeviceLevel();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "OneTrack init error ,exception:" + e);
        }
        Logger.INSTANCE.i(TAG, "OneTrack has been initialized");
    }

    public static boolean isEnable() {
        return Holder.ENABLED;
    }

    public static IOneTrackStat getStatImpInstance() {
        if (mStatImplInstance == null) {
            mStatImplInstance = new OneTrackStatImp();
        }
        return mStatImplInstance;
    }

    public static long getEnterTime() {
        StatHelper statHelper = mStatInstance;
        if (statHelper == null) {
            return -1L;
        }
        return statHelper.mEnterTime;
    }

    public void builderUUID() {
        this.mUUID = UUID.randomUUID().toString();
        this.mEnterTime = System.currentTimeMillis();
    }

    public static StatHelper getStatInstance() {
        if (Holder.ENABLED) {
            return mStatInstance;
        }
        return null;
    }

    private void setCommonParams(Map<String, Object> map) {
        if (map != null) {
            map.put(TRACK_ID, this.mUUID);
            map.put(MODEL_TYPE, this.mModelType);
            map.put(DEVICE_LEVEL, this.mDeviceLevel);
        }
    }

    public static void track(String str, String str2, Map<String, Object> map) {
        StatHelper statHelper;
        if (!Holder.ENABLED || TextUtils.isEmpty(str) || (statHelper = mStatInstance) == null || map == null) {
            return;
        }
        statHelper.setCommonParams(map);
        getStatImpInstance().track(str, str2, map);
    }
}
