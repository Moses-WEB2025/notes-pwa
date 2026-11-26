package com.miui.notes.ai.utils;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.core.util.SystemProperties;

/* JADX INFO: compiled from: Constant.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/ai/utils/Constant;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "FONT_MIPRO_PATH", "", "getFONT_MIPRO_PATH", "()Ljava/lang/String;", "POLISH_TITLE_TEXT_WGHT", "PANEL_AI_TITLE_TEXT_WGHT", "RECOMMEND_TEXT_WGHT", "INPUT_TEXT_WGHT", "AI_WIDGET_TAB_TEXT_WGHT", "AI_BUTTOM_TEXT_WGHT", "STOP_TEXT_WGHT", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Constant {
    public static final String AI_BUTTOM_TEXT_WGHT = "370";
    public static final String AI_WIDGET_TAB_TEXT_WGHT = "380";
    private static final String FONT_MIPRO_PATH;
    public static final String INPUT_TEXT_WGHT = "400";
    public static final Constant INSTANCE = new Constant();
    public static final String PANEL_AI_TITLE_TEXT_WGHT = "400";
    public static final String POLISH_TITLE_TEXT_WGHT = "430";
    public static final String RECOMMEND_TEXT_WGHT = "400";
    public static final String STOP_TEXT_WGHT = "450";

    private Constant() {
    }

    public final String getFONT_MIPRO_PATH() {
        return FONT_MIPRO_PATH;
    }

    static {
        String str = SystemProperties.get("ro.miui.ui.font.mi_font_path", "/system/fonts/MiSansVF.ttf");
        Intrinsics.checkNotNullExpressionValue(str, "get(...)");
        FONT_MIPRO_PATH = str;
    }
}
