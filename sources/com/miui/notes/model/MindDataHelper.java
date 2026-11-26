package com.miui.notes.model;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* JADX INFO: compiled from: MindDataHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\b8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\r\u0010\u0003\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/model/MindDataHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "USE_CORRECT_FORMAT", "", "isCorrectFormatContent", "mindContentData", "", "convertToOldFormatContent", "newContent", "convertToCorrectFormatContent", "mindDataTag", "getMindDataTag$annotations", "getMindDataTag", "()Ljava/lang/String;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MindDataHelper {
    public static final MindDataHelper INSTANCE = new MindDataHelper();
    public static final boolean USE_CORRECT_FORMAT = false;

    @JvmStatic
    public static /* synthetic */ void getMindDataTag$annotations() {
    }

    private MindDataHelper() {
    }

    @JvmStatic
    public static final boolean isCorrectFormatContent(String mindContentData) {
        Intrinsics.checkNotNullParameter(mindContentData, "mindContentData");
        try {
            String strSubstring = mindContentData.substring(0, 15);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return MindEntity.MindPrefix.contentEquals(strSubstring);
        } catch (Exception e) {
            Logger.INSTANCE.e("MindDataHelper", "isCorrectFormatContent error ");
            e.printStackTrace();
            return false;
        }
    }

    @JvmStatic
    public static final String convertToOldFormatContent(String newContent) {
        Intrinsics.checkNotNullParameter(newContent, "newContent");
        return new Regex("[']").replace(new Regex("[\\\\]").replace(newContent, "\\\\\\\\"), "\\\\'");
    }

    @JvmStatic
    public static final String convertToCorrectFormatContent(String newContent) {
        Intrinsics.checkNotNullParameter(newContent, "newContent");
        return new Regex("(\\\\){2}").replace(new Regex("(\\\\')").replace(newContent, "'"), "\\\\");
    }

    public static final String getMindDataTag() {
        return MindEntity.MindPrefixError;
    }
}
