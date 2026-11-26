package com.miui.doodle.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import ch.qos.logback.core.CoreConstants;
import com.google.android.material.timepicker.TimeModel;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DebugPoint;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.notes.provider.Notes;
import defpackage.R2;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: DrawUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J.\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J6\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J&\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fJ6\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000fJ&\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u000fJ\u0016\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u000fJ\u001e\u0010/\u001a\u0002002\u0006\u0010,\u001a\u00020-2\u0006\u00101\u001a\u00020+2\u0006\u00102\u001a\u00020+J\u001a\u00103\u001a\u0002002\b\u00104\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020+H\u0002J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020+H\u0007J&\u00105\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020-2\u000e\u00106\u001a\n\u0012\u0004\u0012\u000208\u0018\u0001072\u0006\u00109\u001a\u00020+R\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006:"}, d2 = {"Lcom/miui/doodle/utils/DrawUtil;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "lastDrawTime", "", "getLastDrawTime", "()J", "setLastDrawTime", "(J)V", "drawCircle", "", "canvas", "Landroid/graphics/Canvas;", "cx", "", "cy", "radius", "paint", "Landroid/graphics/Paint;", "drawRect", "sx", "sy", "dx", "dy", "computeAngle", "px1", "py1", "px2", "py2", "rotatePoint", "Landroid/graphics/PointF;", "coords", "degree", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "px", "py", "scaleRect", "rect", "Landroid/graphics/Rect;", "scale", "dip2px", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "dpValue", "obtainLocalInteger", "", "number", "quantityId", "obtainLocalNumber", "format", "printDebugPointsToFile", "points", "", "Lcom/miui/doodle/feature/draw/DebugPoint;", "index", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DrawUtil {
    public static final DrawUtil INSTANCE = new DrawUtil();
    private static long lastDrawTime;

    private DrawUtil() {
    }

    public long getLastDrawTime() {
        return lastDrawTime;
    }

    public void setLastDrawTime(long j) {
        lastDrawTime = j;
    }

    public final void drawCircle(Canvas canvas, float cx, float cy, float radius, Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        canvas.drawCircle(cx, cy, radius, paint);
    }

    public final void drawRect(Canvas canvas, float sx, float sy, float dx, float dy, Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (sx < dx) {
            if (sy < dy) {
                canvas.drawRect(sx, sy, dx, dy, paint);
                return;
            } else {
                canvas.drawRect(sx, dy, dx, sy, paint);
                return;
            }
        }
        if (sy < dy) {
            canvas.drawRect(dx, sy, sx, dy, paint);
        } else {
            canvas.drawRect(dx, dy, sx, sy, paint);
        }
    }

    public final float computeAngle(float px1, float py1, float px2, float py2) {
        float f;
        float f2 = px2 - px1;
        float f3 = py2 - py1;
        float fAtan = (float) ((((double) ((float) Math.atan(f3 / f2))) / 6.283185307179586d) * ((double) 360));
        if (f2 >= 0.0f && f3 == 0.0f) {
            return 0.0f;
        }
        if (f2 < 0.0f && f3 == 0.0f) {
            return 180.0f;
        }
        if (f2 == 0.0f && f3 > 0.0f) {
            return 90.0f;
        }
        if (f2 == 0.0f && f3 < 0.0f) {
            return 270.0f;
        }
        if (f2 <= 0.0f || f3 <= 0.0f) {
            if ((f2 < 0.0f && f3 > 0.0f) || (f2 < 0.0f && f3 < 0.0f)) {
                f = R2.array.ai_tag_top_item_gradient_border_colors;
            } else if (f2 > 0.0f && f3 < 0.0f) {
                f = 360;
            }
            return fAtan + f;
        }
        return fAtan;
    }

    public final PointF rotatePoint(PointF coords, float degree, float x, float y, float px, float py) {
        Intrinsics.checkNotNullParameter(coords, "coords");
        if (degree % 360 == 0.0f) {
            coords.x = x;
            coords.y = y;
            return coords;
        }
        float f = (float) ((((double) degree) * 3.141592653589793d) / ((double) R2.array.ai_tag_top_item_gradient_border_colors));
        double d = x - px;
        double d2 = f;
        double d3 = y - py;
        coords.x = (float) (((Math.cos(d2) * d) - (Math.sin(d2) * d3)) + ((double) px));
        coords.y = (float) ((d * Math.sin(d2)) + (d3 * Math.cos(d2)) + ((double) py));
        return coords;
    }

    public final void scaleRect(Rect rect, float scale, float px, float py) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        rect.left = (int) ((px - ((px - rect.left) * scale)) + 0.5f);
        rect.right = (int) ((px - ((px - rect.right) * scale)) + 0.5f);
        rect.top = (int) ((py - ((py - rect.top) * scale)) + 0.5f);
        rect.bottom = (int) ((py - (scale * (py - rect.bottom))) + 0.5f);
    }

    public final int dip2px(Context context, float dpValue) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public final String obtainLocalInteger(Context context, int number, int quantityId) {
        Intrinsics.checkNotNullParameter(context, "context");
        return obtainLocalNumber(context.getResources().getQuantityText(quantityId, number).toString(), number);
    }

    private final String obtainLocalNumber(String format, int number) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNull(format);
        String str = String.format(locale, format, Arrays.copyOf(new Object[]{Integer.valueOf(number)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    @JvmStatic
    public static final String obtainLocalInteger(int number) {
        return INSTANCE.obtainLocalNumber(TimeModel.NUMBER_FORMAT, number);
    }

    public final void printDebugPointsToFile(Context context, List<DebugPoint> points, int index) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        if (points == null || points.size() <= 0) {
            return;
        }
        String str = "debugPoints_" + index + ".json";
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str);
        contentValues.put(Notes.Data.MIME_TYPE, "text/txt");
        Uri uriInsert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
        Intrinsics.checkNotNull(uriInsert);
        OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
        try {
            OutputStream outputStream = outputStreamOpenOutputStream;
            Gson gson = new Gson();
            if (outputStream != null) {
                byte[] bytes = gson.toJson(points).toString().getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                outputStream.write(bytes);
            }
            if (outputStream != null) {
                outputStream.flush();
            }
            if (outputStream != null) {
                outputStream.close();
                Unit unit = Unit.INSTANCE;
            }
            CloseableKt.closeFinally(outputStreamOpenOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(outputStreamOpenOutputStream, th);
                throw th2;
            }
        }
    }
}
