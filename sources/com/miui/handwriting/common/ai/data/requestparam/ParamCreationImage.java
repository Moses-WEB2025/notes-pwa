package com.miui.handwriting.common.ai.data.requestparam;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.Layer;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParamCreationImage.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b¢\u0006\u0004\b\u000b\u0010\fJ\b\u0010\u0016\u001a\u00020\u0003H\u0016J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\bHÆ\u0003J\t\u0010\u001c\u001a\u00020\bHÆ\u0003J\t\u0010\u001d\u001a\u00020\bHÆ\u0003JU\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\bHÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020\bHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\n\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013¨\u0006#"}, d2 = {"Lcom/miui/handwriting/common/ai/data/requestparam/ParamCreationImage;", "", "imageData", "", "themeId", "theme", "prompt", "rate", "", Layer.KEY_HEIGHT, Layer.KEY_WIDTH, ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;III)V", "getImageData", "()Ljava/lang/String;", "getThemeId", "getTheme", "getPrompt", "getRate", "()I", "getHeight", "getWidth", "toString", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ParamCreationImage {
    private final int height;
    private final String imageData;
    private final String prompt;
    private final int rate;
    private final String theme;
    private final String themeId;
    private final int width;

    public static /* synthetic */ ParamCreationImage copy$default(ParamCreationImage paramCreationImage, String str, String str2, String str3, String str4, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            str = paramCreationImage.imageData;
        }
        if ((i4 & 2) != 0) {
            str2 = paramCreationImage.themeId;
        }
        if ((i4 & 4) != 0) {
            str3 = paramCreationImage.theme;
        }
        if ((i4 & 8) != 0) {
            str4 = paramCreationImage.prompt;
        }
        if ((i4 & 16) != 0) {
            i = paramCreationImage.rate;
        }
        if ((i4 & 32) != 0) {
            i2 = paramCreationImage.height;
        }
        if ((i4 & 64) != 0) {
            i3 = paramCreationImage.width;
        }
        int i5 = i2;
        int i6 = i3;
        int i7 = i;
        String str5 = str3;
        return paramCreationImage.copy(str, str2, str5, str4, i7, i5, i6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getImageData() {
        return this.imageData;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getThemeId() {
        return this.themeId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getTheme() {
        return this.theme;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getPrompt() {
        return this.prompt;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getRate() {
        return this.rate;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final int getHeight() {
        return this.height;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final int getWidth() {
        return this.width;
    }

    public final ParamCreationImage copy(String imageData, String themeId, String theme, String prompt, int rate, int height, int width) {
        Intrinsics.checkNotNullParameter(imageData, "imageData");
        return new ParamCreationImage(imageData, themeId, theme, prompt, rate, height, width);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParamCreationImage)) {
            return false;
        }
        ParamCreationImage paramCreationImage = (ParamCreationImage) other;
        return Intrinsics.areEqual(this.imageData, paramCreationImage.imageData) && Intrinsics.areEqual(this.themeId, paramCreationImage.themeId) && Intrinsics.areEqual(this.theme, paramCreationImage.theme) && Intrinsics.areEqual(this.prompt, paramCreationImage.prompt) && this.rate == paramCreationImage.rate && this.height == paramCreationImage.height && this.width == paramCreationImage.width;
    }

    public int hashCode() {
        int iHashCode = this.imageData.hashCode() * 31;
        String str = this.themeId;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.theme;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.prompt;
        return ((((((iHashCode3 + (str3 != null ? str3.hashCode() : 0)) * 31) + Integer.hashCode(this.rate)) * 31) + Integer.hashCode(this.height)) * 31) + Integer.hashCode(this.width);
    }

    public ParamCreationImage(String imageData, String str, String str2, String str3, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(imageData, "imageData");
        this.imageData = imageData;
        this.themeId = str;
        this.theme = str2;
        this.prompt = str3;
        this.rate = i;
        this.height = i2;
        this.width = i3;
    }

    public final String getImageData() {
        return this.imageData;
    }

    public final String getThemeId() {
        return this.themeId;
    }

    public final String getTheme() {
        return this.theme;
    }

    public final String getPrompt() {
        return this.prompt;
    }

    public final int getRate() {
        return this.rate;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public String toString() {
        return "imageData:" + this.imageData.length() + " themeId:" + this.themeId + " theme:" + this.theme + " prompt:" + this.prompt + "  rate:" + this.rate + " height:" + this.height + " width:" + this.width;
    }
}
