package com.miui.handwriting.common.ai;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.aiengine.common.TimeOutConfig;
import com.miui.libhandwritingpage.utils.RomUtils;
import kotlin.Metadata;

/* JADX INFO: compiled from: NetConfig.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b!\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0002R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0011\u0010\u000f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0011\u0010\u0011\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0011\u0010\u0015\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/miui/handwriting/common/ai/NetConfig;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "getBaseUrl", "", "URL_GET_VERSION", "getURL_GET_VERSION", "()Ljava/lang/String;", "URL_THEME", "getURL_THEME", "URL_UPLOAD_META", "getURL_UPLOAD_META", "URL_CREATION", "getURL_CREATION", "URL_ENHANCE", "getURL_ENHANCE", "URL_GET_REFERENCE", "getURL_GET_REFERENCE", "URL_GET_THEME_BY_FILE", "getURL_GET_THEME_BY_FILE", "URL_GET_THEME_BY_PROMPT", "getURL_GET_THEME_BY_PROMPT", "AES_GCM_256", "PARAM_MI_ID", "PARAM_OA_ID", "PARAM_TIMESTAMP", "PARAM_DATA", "PARAM_FILE", "PARAM_THEME", "PARAM_THEME_ID", "PARAM_PROMPT", "PARAM_RATE", "PARAM_REQUEST_ID", "PARAM_CREATION_TYPE", "PARAM_CREATION_NUM", "PARAM_GUILD_FILE", "PARAM_MASK_COORDINATES", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NetConfig {
    public static final String AES_GCM_256 = "AES-GCM-256";
    public static final NetConfig INSTANCE;
    public static final String PARAM_CREATION_NUM = "num";
    public static final String PARAM_CREATION_TYPE = "type";
    public static final String PARAM_DATA = "data";
    public static final String PARAM_FILE = "file";
    public static final String PARAM_GUILD_FILE = "guideFile";
    public static final String PARAM_MASK_COORDINATES = "maskCoordinates";
    public static final String PARAM_MI_ID = "miId";
    public static final String PARAM_OA_ID = "oaid";
    public static final String PARAM_PROMPT = "prompt";
    public static final String PARAM_RATE = "rate";
    public static final String PARAM_REQUEST_ID = "requestId";
    public static final String PARAM_THEME = "theme";
    public static final String PARAM_THEME_ID = "themeId";
    public static final String PARAM_TIMESTAMP = "timestamp";
    private static final String URL_CREATION;
    private static final String URL_ENHANCE;
    private static final String URL_GET_REFERENCE;
    private static final String URL_GET_THEME_BY_FILE;
    private static final String URL_GET_THEME_BY_PROMPT;
    private static final String URL_GET_VERSION;
    private static final String URL_THEME;
    private static final String URL_UPLOAD_META;

    private NetConfig() {
    }

    private final String getBaseUrl() {
        if (RomUtils.isInternationalBuild()) {
            return "https://avatar-ai.engine.intl.mi.com";
        }
        return "https://avatar-ai.sec.miui.com";
    }

    static {
        NetConfig netConfig = new NetConfig();
        INSTANCE = netConfig;
        com.miui.aiengine.common.NetConfig.INSTANCE.setTimeOutConfig(new TimeOutConfig(60, 60, 60, 60));
        com.miui.aiengine.common.NetConfig.INSTANCE.setLogOn(true);
        URL_GET_VERSION = netConfig.getBaseUrl() + "/creationV2/getVersion";
        URL_THEME = netConfig.getBaseUrl() + "/creationV2/listTheme";
        URL_UPLOAD_META = netConfig.getBaseUrl() + "/creationV2/uploadMeta";
        URL_CREATION = netConfig.getBaseUrl() + "/creationV2/create";
        URL_ENHANCE = netConfig.getBaseUrl() + "/creationV2/enhance";
        URL_GET_REFERENCE = netConfig.getBaseUrl() + "/creationV2/listType";
        URL_GET_THEME_BY_FILE = netConfig.getBaseUrl() + "/creationV2/polish";
        URL_GET_THEME_BY_PROMPT = netConfig.getBaseUrl() + "/creationV2/getTheme";
    }

    public final String getURL_GET_VERSION() {
        return URL_GET_VERSION;
    }

    public final String getURL_THEME() {
        return URL_THEME;
    }

    public final String getURL_UPLOAD_META() {
        return URL_UPLOAD_META;
    }

    public final String getURL_CREATION() {
        return URL_CREATION;
    }

    public final String getURL_ENHANCE() {
        return URL_ENHANCE;
    }

    public final String getURL_GET_REFERENCE() {
        return URL_GET_REFERENCE;
    }

    public final String getURL_GET_THEME_BY_FILE() {
        return URL_GET_THEME_BY_FILE;
    }

    public final String getURL_GET_THEME_BY_PROMPT() {
        return URL_GET_THEME_BY_PROMPT;
    }
}
