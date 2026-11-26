package com.miui.common.tool;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;

/* JADX INFO: compiled from: MiFontStyle.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0012\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001a\u0010\u0018\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000e¨\u0006\u001c"}, d2 = {"Lcom/miui/common/tool/MiFontStyle;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isVfFont", "", "()Z", "setVfFont", "(Z)V", "normal", "", "getNormal", "()I", "setNormal", "(I)V", "regular", "getRegular", "setRegular", "medium", "getMedium", "setMedium", "semibold", "getSemibold", "setSemibold", EditStat.ELEMENT_BOLD, "getBold", "setBold", "Builder", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiFontStyle {
    private boolean isVfFont;
    private int normal = 400;
    private int regular = 500;
    private int medium = 500;
    private int semibold = 800;
    private int bold = 700;

    /* JADX INFO: renamed from: isVfFont, reason: from getter */
    public final boolean getIsVfFont() {
        return this.isVfFont;
    }

    public final void setVfFont(boolean z) {
        this.isVfFont = z;
    }

    public final int getNormal() {
        return this.normal;
    }

    public final void setNormal(int i) {
        this.normal = i;
    }

    public final int getRegular() {
        return this.regular;
    }

    public final void setRegular(int i) {
        this.regular = i;
    }

    public final int getMedium() {
        return this.medium;
    }

    public final void setMedium(int i) {
        this.medium = i;
    }

    public final int getSemibold() {
        return this.semibold;
    }

    public final void setSemibold(int i) {
        this.semibold = i;
    }

    public final int getBold() {
        return this.bold;
    }

    public final void setBold(int i) {
        this.bold = i;
    }

    /* JADX INFO: compiled from: MiFontStyle.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0007¨\u0006\u000b"}, d2 = {"Lcom/miui/common/tool/MiFontStyle$Builder;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "build", "Lcom/miui/common/tool/MiFontStyle;", "miuiVersion", "", "isMiuiFont", "", "systemFontWeightOffset", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Builder {
        public static final Builder INSTANCE = new Builder();

        private Builder() {
        }

        @JvmStatic
        public static final MiFontStyle build(int miuiVersion, boolean isMiuiFont, int systemFontWeightOffset) {
            return new MiFontStyle();
        }
    }
}
