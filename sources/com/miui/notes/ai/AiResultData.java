package com.miui.notes.ai;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AiResultData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\u0010\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/ai/AiResultData;", "", "text", "", "type", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "getText", "()Ljava/lang/String;", "getType", "()I", "setType", "(I)V", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class AiResultData {
    private final String text;
    private int type;

    public static /* synthetic */ AiResultData copy$default(AiResultData aiResultData, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = aiResultData.text;
        }
        if ((i2 & 2) != 0) {
            i = aiResultData.type;
        }
        return aiResultData.copy(str, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getText() {
        return this.text;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getType() {
        return this.type;
    }

    public final AiResultData copy(String text, int type) {
        return new AiResultData(text, type);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AiResultData)) {
            return false;
        }
        AiResultData aiResultData = (AiResultData) other;
        return Intrinsics.areEqual(this.text, aiResultData.text) && this.type == aiResultData.type;
    }

    public int hashCode() {
        String str = this.text;
        return ((str == null ? 0 : str.hashCode()) * 31) + Integer.hashCode(this.type);
    }

    public String toString() {
        return "AiResultData(text=" + this.text + ", type=" + this.type + ")";
    }

    public AiResultData(String str, int i) {
        this.text = str;
        this.type = i;
    }

    public final String getText() {
        return this.text;
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }
}
