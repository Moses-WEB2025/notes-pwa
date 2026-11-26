package com.miui.doodle.feature.color;

import android.graphics.Paint;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.core.IDoodleColor;
import com.miui.doodle.base.core.IDoodleItem;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DoodleColor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0000H\u0016J\u0006\u0010\u0010\u001a\u00020\u0011R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0013"}, d2 = {"Lcom/miui/doodle/feature/color/DoodleColor;", "Lcom/miui/doodle/base/core/IDoodleColor;", "color", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "getColor", "()I", "setColor", "config", "", "doodleItem", "Lcom/miui/doodle/base/core/IDoodleItem;", "paint", "Landroid/graphics/Paint;", "copy", "toJSONObject", "Lorg/json/JSONObject;", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodleColor implements IDoodleColor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_COLOR = "color";
    private static final String TAG = "DoodleColor";
    private int color;

    @JvmStatic
    public static final DoodleColor fromJSONObject(JSONObject jSONObject) throws JSONException {
        return INSTANCE.fromJSONObject(jSONObject);
    }

    public DoodleColor(int i) {
        this.color = i;
    }

    public final int getColor() {
        return this.color;
    }

    public final void setColor(int i) {
        this.color = i;
    }

    @Override // com.miui.doodle.base.core.IDoodleColor
    public void config(IDoodleItem doodleItem, Paint paint) {
        Intrinsics.checkNotNullParameter(doodleItem, "doodleItem");
        Intrinsics.checkNotNullParameter(paint, "paint");
        paint.setColor(this.color);
        paint.setShader(null);
    }

    @Override // com.miui.doodle.base.core.IDoodleColor
    public DoodleColor copy() {
        return new DoodleColor(this.color);
    }

    public final JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("color", this.color);
        return jSONObject;
    }

    /* JADX INFO: compiled from: DoodleColor.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/miui/doodle/feature/color/DoodleColor$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "KEY_COLOR", "fromJSONObject", "Lcom/miui/doodle/feature/color/DoodleColor;", "jsonObject", "Lorg/json/JSONObject;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final DoodleColor fromJSONObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) {
                return null;
            }
            return new DoodleColor(jsonObject.optInt("color"));
        }
    }
}
