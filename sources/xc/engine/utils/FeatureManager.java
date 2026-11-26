package xc.engine.utils;

import android.os.Build;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public class FeatureManager {
    private static FeatureManager instance;
    private final Map<String, Boolean> frontBufferMap = new HashMap();
    private final Map<String, Boolean> frameControlMap = new HashMap();
    private boolean enableMathRecognition = true;

    public boolean frontBufferEnable() {
        return true;
    }

    public static FeatureManager getInstance() {
        if (instance == null) {
            FeatureManager featureManager = new FeatureManager();
            instance = featureManager;
            featureManager.init();
        }
        return instance;
    }

    private void init() {
        this.frontBufferMap.put("jinghu", true);
        this.frontBufferMap.put("muyu", true);
        this.frontBufferMap.put("violin", true);
        this.frontBufferMap.put("flute", true);
        this.frontBufferMap.put("organ", true);
        this.frameControlMap.put("flute", true);
        this.frameControlMap.put("organ", true);
    }

    public boolean frameControlEnable() {
        Boolean orDefault = this.frameControlMap.getOrDefault(Build.DEVICE, false);
        if (orDefault != null) {
            return orDefault.booleanValue();
        }
        return false;
    }

    public boolean isEnableMathRecognition() {
        return this.enableMathRecognition;
    }

    public void setEnableMathRecognition(boolean z) {
        this.enableMathRecognition = z;
    }

    public boolean isSupportHighQuality() {
        boolean zContains = new ArrayList(Arrays.asList("yudi", "liuqin", "pipa", "sheng", "jinghu", "muyu", "uke", "turner", "violin", "piano", "yupei", "yili")).contains(Build.DEVICE);
        Log.d("TAG", "isSupportHighQuality: " + zContains);
        return zContains;
    }
}
