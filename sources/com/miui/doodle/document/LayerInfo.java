package com.miui.doodle.document;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: LayerInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \f2\u00020\u0001:\u0002\u000b\fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\t\u001a\u00020\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lcom/miui/doodle/document/LayerInfo;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "layerInfoItems", "", "Lcom/miui/doodle/document/LayerInfo$LayerInfoItem;", "getLayerInfoItems", "()Ljava/util/List;", "toJSONObject", "Lorg/json/JSONObject;", "LayerInfoItem", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LayerInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_LAYER_INFO = "layerInfo";
    private final List<LayerInfoItem> layerInfoItems = new ArrayList();

    @JvmStatic
    public static final LayerInfo fromJSONObject(JsonObject jsonObject) throws JsonParseException {
        return INSTANCE.fromJSONObject(jsonObject);
    }

    public final List<LayerInfoItem> getLayerInfoItems() {
        return this.layerInfoItems;
    }

    public final JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator<LayerInfoItem> it = this.layerInfoItems.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJSONObject());
        }
        jSONObject.put(KEY_LAYER_INFO, jSONArray);
        return jSONObject;
    }

    /* JADX INFO: compiled from: LayerInfo.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\r\u001a\u00020\u000eR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/miui/doodle/document/LayerInfo$LayerInfoItem;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", LayerInfoItem.KEY_BASE_PATH, "", "getBasePath", "()Ljava/lang/String;", "setBasePath", "(Ljava/lang/String;)V", LayerInfoItem.KEY_INFO_FILE, "getInfoFile", "setInfoFile", "toJSONObject", "Lorg/json/JSONObject;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class LayerInfoItem {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final String KEY_BASE_PATH = "basePath";
        private static final String KEY_INFO_FILE = "infoFile";
        private String basePath;
        private String infoFile;

        public final String getBasePath() {
            return this.basePath;
        }

        public final void setBasePath(String str) {
            this.basePath = str;
        }

        public final String getInfoFile() {
            return this.infoFile;
        }

        public final void setInfoFile(String str) {
            this.infoFile = str;
        }

        public final JSONObject toJSONObject() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(KEY_BASE_PATH, this.basePath);
            jSONObject.put(KEY_INFO_FILE, this.infoFile);
            return jSONObject;
        }

        /* JADX INFO: compiled from: LayerInfo.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/miui/doodle/document/LayerInfo$LayerInfoItem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_BASE_PATH", "", "KEY_INFO_FILE", "fromJSONObject", "Lcom/miui/doodle/document/LayerInfo$LayerInfoItem;", "jsonObject", "Lcom/google/gson/JsonObject;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final LayerInfoItem fromJSONObject(JsonObject jsonObject) throws JSONException {
                Intrinsics.checkNotNullParameter(jsonObject, "jsonObject");
                LayerInfoItem layerInfoItem = new LayerInfoItem();
                layerInfoItem.setBasePath(jsonObject.get(LayerInfoItem.KEY_BASE_PATH).getAsString());
                layerInfoItem.setInfoFile(jsonObject.get(LayerInfoItem.KEY_INFO_FILE).getAsString());
                return layerInfoItem;
            }
        }
    }

    /* JADX INFO: compiled from: LayerInfo.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/miui/doodle/document/LayerInfo$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_LAYER_INFO", "", "fromJSONObject", "Lcom/miui/doodle/document/LayerInfo;", "jsonObject", "Lcom/google/gson/JsonObject;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final LayerInfo fromJSONObject(JsonObject jsonObject) throws JsonParseException {
            Intrinsics.checkNotNullParameter(jsonObject, "jsonObject");
            LayerInfo layerInfo = new LayerInfo();
            JsonArray asJsonArray = jsonObject.get(LayerInfo.KEY_LAYER_INFO).getAsJsonArray();
            if (asJsonArray != null) {
                int size = asJsonArray.size();
                for (int i = 0; i < size; i++) {
                    List<LayerInfoItem> layerInfoItems = layerInfo.getLayerInfoItems();
                    LayerInfoItem.Companion companion = LayerInfoItem.INSTANCE;
                    JsonObject asJsonObject = asJsonArray.get(i).getAsJsonObject();
                    Intrinsics.checkNotNullExpressionValue(asJsonObject, "getAsJsonObject(...)");
                    layerInfoItems.add(companion.fromJSONObject(asJsonObject));
                }
            }
            return layerInfo;
        }
    }
}
