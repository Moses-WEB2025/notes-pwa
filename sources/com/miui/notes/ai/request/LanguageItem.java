package com.miui.notes.ai.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LanguageItem.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/ai/request/LanguageItem;", "", "code", "", "name", "enName", "cnName", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getName", "getEnName", "getCnName", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class LanguageItem {
    private final String cnName;
    private final String code;
    private final String enName;
    private final String name;

    public static /* synthetic */ LanguageItem copy$default(LanguageItem languageItem, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = languageItem.code;
        }
        if ((i & 2) != 0) {
            str2 = languageItem.name;
        }
        if ((i & 4) != 0) {
            str3 = languageItem.enName;
        }
        if ((i & 8) != 0) {
            str4 = languageItem.cnName;
        }
        return languageItem.copy(str, str2, str3, str4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getEnName() {
        return this.enName;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getCnName() {
        return this.cnName;
    }

    public final LanguageItem copy(String code, String name, String enName, String cnName) {
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(enName, "enName");
        Intrinsics.checkNotNullParameter(cnName, "cnName");
        return new LanguageItem(code, name, enName, cnName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LanguageItem)) {
            return false;
        }
        LanguageItem languageItem = (LanguageItem) other;
        return Intrinsics.areEqual(this.code, languageItem.code) && Intrinsics.areEqual(this.name, languageItem.name) && Intrinsics.areEqual(this.enName, languageItem.enName) && Intrinsics.areEqual(this.cnName, languageItem.cnName);
    }

    public int hashCode() {
        return (((((this.code.hashCode() * 31) + this.name.hashCode()) * 31) + this.enName.hashCode()) * 31) + this.cnName.hashCode();
    }

    public String toString() {
        return "LanguageItem(code=" + this.code + ", name=" + this.name + ", enName=" + this.enName + ", cnName=" + this.cnName + ")";
    }

    public LanguageItem(String code, String name, String enName, String cnName) {
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(enName, "enName");
        Intrinsics.checkNotNullParameter(cnName, "cnName");
        this.code = code;
        this.name = name;
        this.enName = enName;
        this.cnName = cnName;
    }

    public final String getCnName() {
        return this.cnName;
    }

    public final String getCode() {
        return this.code;
    }

    public final String getEnName() {
        return this.enName;
    }

    public final String getName() {
        return this.name;
    }
}
