package com.miui.notes.ai;

import android.os.updater.MD5;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AigcPicMetaData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u0003J\b\u0010\f\u001a\u0004\u0018\u00010\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/ai/AigcPicMetaData;", "Ljava/io/Serializable;", "producerId", "", "propagateID", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "getProducerId", "()Ljava/lang/String;", "getPropagateID", "valuedJsonStr", "Lorg/json/JSONObject;", "getValuedJsonStr", "()Lorg/json/JSONObject;", "toStr", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class AigcPicMetaData implements Serializable {
    private final String producerId;
    private final String propagateID;
    private final JSONObject valuedJsonStr;

    public static /* synthetic */ AigcPicMetaData copy$default(AigcPicMetaData aigcPicMetaData, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = aigcPicMetaData.producerId;
        }
        if ((i & 2) != 0) {
            str2 = aigcPicMetaData.propagateID;
        }
        return aigcPicMetaData.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getProducerId() {
        return this.producerId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPropagateID() {
        return this.propagateID;
    }

    public final AigcPicMetaData copy(String producerId, String propagateID) {
        Intrinsics.checkNotNullParameter(producerId, "producerId");
        Intrinsics.checkNotNullParameter(propagateID, "propagateID");
        return new AigcPicMetaData(producerId, propagateID);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AigcPicMetaData)) {
            return false;
        }
        AigcPicMetaData aigcPicMetaData = (AigcPicMetaData) other;
        return Intrinsics.areEqual(this.producerId, aigcPicMetaData.producerId) && Intrinsics.areEqual(this.propagateID, aigcPicMetaData.propagateID);
    }

    public int hashCode() {
        return (this.producerId.hashCode() * 31) + this.propagateID.hashCode();
    }

    public String toString() {
        return "AigcPicMetaData(producerId=" + this.producerId + ", propagateID=" + this.propagateID + ")";
    }

    public AigcPicMetaData(String producerId, String propagateID) {
        Intrinsics.checkNotNullParameter(producerId, "producerId");
        Intrinsics.checkNotNullParameter(propagateID, "propagateID");
        this.producerId = producerId;
        this.propagateID = propagateID;
        this.valuedJsonStr = new JSONObject();
    }

    public final String getProducerId() {
        return this.producerId;
    }

    public final String getPropagateID() {
        return this.propagateID;
    }

    public final JSONObject getValuedJsonStr() {
        return this.valuedJsonStr;
    }

    public final String toStr() throws JSONException {
        String str = "$291110108551385082Q" + this.producerId + "$91110108551385082Q" + this.propagateID;
        String md5Digest = MD5.getMd5Digest(str);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("Type", "Content");
        jSONObject.put("Content", str);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("Label", "2");
        jSONObject2.put("value1", "91110108551385082Q");
        jSONObject2.put("value2", "producerId");
        jSONObject2.put("value3", "91110108551385082Q");
        jSONObject.put("Type", "LabelMetaData");
        jSONObject.put("LabelMetaData", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("Label", "2");
        jSONObject3.put("ContentProducer", "91110108551385082Q");
        jSONObject3.put("ProduceID", this.producerId);
        jSONObject3.put("ContentPropagator", "91110108551385082Q");
        jSONObject3.put("PropagateID", this.propagateID);
        jSONObject.put("Type", "BType");
        jSONObject.put("Binding", jSONObject3);
        jSONObject.put("Type", "OtherInfo");
        jSONObject.put("Start", 21);
        jSONObject.put("Length", jSONObject.toString().length());
        String string = jSONObject.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        JSONObject jSONObject4 = new JSONObject();
        jSONObject4.put("Type", "DS");
        jSONObject4.put("AlgID", "1.2.156.10197.1.501");
        jSONObject4.put("Params", JSONObject.NULL);
        jSONObject4.put("TBSData", string);
        jSONObject4.put("KeyID", "0");
        jSONObject4.put("Signature", md5Digest);
        String string2 = jSONObject4.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        JSONObject jSONObject5 = new JSONObject();
        jSONObject5.put("Label", "2");
        jSONObject5.put("ContentProducer", "91110108551385082Q");
        jSONObject5.put("ProduceID", this.producerId);
        jSONObject5.put("ContentPropagator", "91110108551385082Q");
        jSONObject5.put("PropagateID", this.propagateID);
        jSONObject5.put("ReservedCode1", MD5.getMd5Digest(string2));
        jSONObject5.put("ReservedCode2", MD5.getMd5Digest(string2));
        this.valuedJsonStr.put("AIGC", jSONObject5);
        Logger.INSTANCE.d("AigcPicMetaData", " toStr: " + this.valuedJsonStr);
        String string3 = this.valuedJsonStr.toString();
        Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
        return string3;
    }

    public final String getValuedJsonStr() {
        return this.valuedJsonStr.toString();
    }
}
