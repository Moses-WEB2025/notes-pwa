package com.miui.doodle.document;

import android.graphics.Bitmap;
import android.graphics.RectF;
import com.google.gson.stream.JsonReader;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.PenTextureManager;
import com.miui.doodle.utils.UIUtils;
import defpackage.R2;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Dot.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 72\u00020\u0001:\u00017Bm\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0006\u0010/\u001a\u00020\nJ\b\u00100\u001a\u000201H\u0002J\u0006\u00102\u001a\u00020(J\b\u00103\u001a\u00020\nH\u0016J\u000e\u00104\u001a\u00020\u00032\u0006\u00105\u001a\u00020\u0000J\u000e\u00106\u001a\u00020\u00002\u0006\u00105\u001a\u00020\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000e\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b!\u0010 R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013R\u0011\u0010#\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010)\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0013R\u0013\u0010+\u001a\u0004\u0018\u00010,8F¢\u0006\u0006\u001a\u0004\b-\u0010.¨\u00068"}, d2 = {"Lcom/miui/doodle/document/Dot;", "", "x", "", "y", Dot.KEY_Z, "t", "", "size", "textureId", "", Dot.KEY_ANGLE, "color", "", "alpha", "scale", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FFFJFLjava/lang/String;FIIF)V", "getX", "()F", "setX", "(F)V", "getY", "setY", "getZ", "getT", "()J", "getSize", "getTextureId", "()Ljava/lang/String;", "getAngle", "getColor", "()I", "getAlpha", "getScale", "rect", "Landroid/graphics/RectF;", "getRect", "()Landroid/graphics/RectF;", "cachedJSONObject", "Lorg/json/JSONObject;", "halfSize", "getHalfSize", "texture", "Landroid/graphics/Bitmap;", "getTexture", "()Landroid/graphics/Bitmap;", "getKey", "cacheJSONObject", "", "toJSONObject", "toString", "distanceTo", "that", "middleDot", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Dot {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_ALPHA = "alpha";
    private static final String KEY_ANGLE = "angle";
    private static final String KEY_COLOR = "color";
    private static final String KEY_SCALE = "scale";
    private static final String KEY_SIZE = "size";
    private static final String KEY_T = "t";
    private static final String KEY_TEXTURE_ID = "texture_id";
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_Z = "z";
    private final int alpha;
    private final float angle;
    private final JSONObject cachedJSONObject;
    private final int color;
    private final float halfSize;
    private final RectF rect;
    private final float scale;
    private final float size;
    private final long t;
    private final String textureId;
    private float x;
    private float y;
    private final float z;

    public Dot() {
        this(0.0f, 0.0f, 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, R2.attr.endActionMenuEnabled, null);
    }

    @JvmStatic
    public static final Dot fromJSONReader(JsonReader jsonReader) throws JSONException {
        return INSTANCE.fromJSONReader(jsonReader);
    }

    public Dot(float f, float f2, float f3, long j, float f4, String str, float f5, int i, int i2, float f6) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.t = j;
        this.size = f4;
        this.textureId = str;
        this.angle = f5;
        this.color = i;
        this.alpha = i2;
        this.scale = f6;
        this.cachedJSONObject = new JSONObject();
        float f7 = f4 / 2.0f;
        this.halfSize = f7;
        float f8 = this.x;
        float f9 = this.y;
        this.rect = new RectF(f8 - f7, f9 - f7, f8 + f7, f9 + f7);
    }

    public /* synthetic */ Dot(float f, float f2, float f3, long j, float f4, String str, float f5, int i, int i2, float f6, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0.0f : f, (i3 & 2) != 0 ? 0.0f : f2, (i3 & 4) != 0 ? 0.0f : f3, (i3 & 8) != 0 ? 0L : j, (i3 & 16) != 0 ? 0.0f : f4, (i3 & 32) != 0 ? null : str, (i3 & 64) != 0 ? 0.0f : f5, (i3 & 128) != 0 ? 0 : i, (i3 & 256) != 0 ? 0 : i2, (i3 & 512) != 0 ? 1.0f : f6);
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final float getZ() {
        return this.z;
    }

    public final long getT() {
        return this.t;
    }

    public final float getSize() {
        return this.size;
    }

    public final String getTextureId() {
        return this.textureId;
    }

    public final float getAngle() {
        return this.angle;
    }

    public final int getColor() {
        return this.color;
    }

    public final int getAlpha() {
        return this.alpha;
    }

    public final float getScale() {
        return this.scale;
    }

    public final RectF getRect() {
        return this.rect;
    }

    public final float getHalfSize() {
        return this.halfSize;
    }

    public final Bitmap getTexture() {
        if (this.textureId != null) {
            return PenTextureManager.INSTANCE.getTexture(this.textureId);
        }
        return null;
    }

    public final String getKey() {
        if (this.textureId == null) {
            String str = ":" + this.color + ":" + this.size;
            Intrinsics.checkNotNullExpressionValue(str, "toString(...)");
            String strIntern = str.intern();
            Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
            return strIntern;
        }
        String str2 = this.textureId + ":" + this.color + ":" + this.size;
        Intrinsics.checkNotNullExpressionValue(str2, "toString(...)");
        String strIntern2 = str2.intern();
        Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
        return strIntern2;
    }

    private final void cacheJSONObject() throws JSONException {
        this.cachedJSONObject.put("x", this.x);
        this.cachedJSONObject.put("y", this.y);
        this.cachedJSONObject.put(KEY_Z, this.z);
        this.cachedJSONObject.put("t", this.t);
        this.cachedJSONObject.put("size", this.size);
        this.cachedJSONObject.put("alpha", this.alpha);
        this.cachedJSONObject.put(KEY_ANGLE, this.angle);
        this.cachedJSONObject.put("color", this.color);
        this.cachedJSONObject.put("scale", this.scale);
        this.cachedJSONObject.put(KEY_TEXTURE_ID, this.textureId);
    }

    public final JSONObject toJSONObject() throws JSONException {
        cacheJSONObject();
        return this.cachedJSONObject;
    }

    public String toString() {
        return "Dot{mX=" + this.x + ", mY=" + this.y + ", mSize=" + this.size + StringSubstitutor.DEFAULT_VAR_END;
    }

    public final float distanceTo(Dot that) {
        Intrinsics.checkNotNullParameter(that, "that");
        return UIUtils.INSTANCE.dotsDistance(this.x, this.y, that.x, that.y);
    }

    public final Dot middleDot(Dot that) {
        Intrinsics.checkNotNullParameter(that, "that");
        return new Dot((this.x + that.x) / 2.0f, (this.y + that.y) / 2.0f, 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null);
    }

    /* JADX INFO: compiled from: Dot.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J(\u0010\u0013\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/doodle/document/Dot$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "KEY_X", "", "KEY_Y", "KEY_Z", "KEY_T", "KEY_SIZE", "KEY_TEXTURE_ID", "KEY_ANGLE", "KEY_COLOR", "KEY_ALPHA", "KEY_SCALE", "fromJSONReader", "Lcom/miui/doodle/document/Dot;", "mJsonReader", "Lcom/google/gson/stream/JsonReader;", "calcCrossedDot", "d1", "md1", "d2", "md2", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @JvmStatic
        public final Dot fromJSONReader(JsonReader mJsonReader) throws JSONException, IOException {
            Intrinsics.checkNotNullParameter(mJsonReader, "mJsonReader");
            mJsonReader.beginObject();
            float fNextDouble = 0.0f;
            float fNextDouble2 = 0.0f;
            float fNextDouble3 = 0.0f;
            float fNextDouble4 = 0.0f;
            float fNextDouble5 = 0.0f;
            float fNextDouble6 = 0.0f;
            long jNextLong = 0;
            int iNextInt = 0;
            int iNextInt2 = 0;
            String strNextString = "";
            while (mJsonReader.hasNext()) {
                String strNextName = mJsonReader.nextName();
                if (strNextName != null) {
                    switch (strNextName.hashCode()) {
                        case -1471954241:
                            if (strNextName.equals(Dot.KEY_TEXTURE_ID)) {
                                strNextString = mJsonReader.nextString();
                                Intrinsics.checkNotNullExpressionValue(strNextString, "nextString(...)");
                            }
                            break;
                        case 116:
                            if (strNextName.equals("t")) {
                                jNextLong = mJsonReader.nextLong();
                            }
                            break;
                        case 120:
                            if (strNextName.equals("x")) {
                                fNextDouble = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 121:
                            if (strNextName.equals("y")) {
                                fNextDouble2 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 122:
                            if (strNextName.equals(Dot.KEY_Z)) {
                                fNextDouble3 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 3530753:
                            if (strNextName.equals("size")) {
                                fNextDouble4 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 92909918:
                            if (strNextName.equals("alpha")) {
                                iNextInt2 = mJsonReader.nextInt();
                            }
                            break;
                        case 92960979:
                            if (strNextName.equals(Dot.KEY_ANGLE)) {
                                fNextDouble5 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        case 94842723:
                            if (strNextName.equals("color")) {
                                iNextInt = mJsonReader.nextInt();
                            }
                            break;
                        case 109250890:
                            if (strNextName.equals("scale")) {
                                fNextDouble6 = (float) mJsonReader.nextDouble();
                            }
                            break;
                        default:
                            break;
                    }
                }
                mJsonReader.skipValue();
            }
            mJsonReader.endObject();
            return new Dot(fNextDouble, fNextDouble2, fNextDouble3, jNextLong, fNextDouble4, strNextString, fNextDouble5, iNextInt, iNextInt2, fNextDouble6);
        }

        public final Dot calcCrossedDot(Dot d1, Dot md1, Dot d2, Dot md2) {
            float x;
            float f;
            float f2;
            float f3;
            float fMin;
            Intrinsics.checkNotNullParameter(d1, "d1");
            Intrinsics.checkNotNullParameter(md1, "md1");
            Intrinsics.checkNotNullParameter(d2, "d2");
            Intrinsics.checkNotNullParameter(md2, "md2");
            if (d1.getX() == md1.getX() && d2.getX() == md2.getX()) {
                return null;
            }
            if (d1.getX() == md1.getX()) {
                float y = (d2.getY() - md2.getY()) / (d2.getX() - md2.getX());
                float y2 = d2.getY() - (d2.getX() * y);
                x = d1.getX();
                f = (y * x) + y2;
            } else {
                if (d2.getX() == md2.getX()) {
                    float y3 = (d1.getY() - md1.getY()) / (d1.getX() - md1.getX());
                    float y4 = d1.getY() - (d1.getX() * y3);
                    float x2 = d1.getX();
                    f2 = (y3 * x2) + y4;
                    f3 = x2;
                } else {
                    float y5 = (d1.getY() - md1.getY()) / (d1.getX() - md1.getX());
                    float y6 = d1.getY() - (d1.getX() * y5);
                    float y7 = (d2.getY() - md2.getY()) / (d2.getX() - md2.getX());
                    float y8 = d2.getY() - (d2.getX() * y7);
                    if (y5 == y7) {
                        return null;
                    }
                    x = (y8 - y6) / (y5 - y7);
                    f = (y5 * x) + y6;
                }
                fMin = Math.min(d1.getX(), md1.getX());
                float fMax = Math.max(d1.getX(), md1.getX());
                float fMin2 = Math.min(d1.getY(), md1.getY());
                float fMax2 = Math.max(d1.getY(), md1.getY());
                if (f3 >= fMin || f3 > fMax || f2 < fMin2 || f2 > fMax2) {
                    return null;
                }
                return new Dot(f3, f2, 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null);
            }
            f2 = f;
            f3 = x;
            fMin = Math.min(d1.getX(), md1.getX());
            float fMax3 = Math.max(d1.getX(), md1.getX());
            float fMin3 = Math.min(d1.getY(), md1.getY());
            float fMax4 = Math.max(d1.getY(), md1.getY());
            if (f3 >= fMin) {
            }
            return null;
        }
    }
}
