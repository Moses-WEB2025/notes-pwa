package com.google.android.appfunctions.schema.fitness.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R\u001c\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/google/android/appfunctions/schema/fitness/v1/ShowFitnessMetricParams;", "", "metricType", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "getMetricType$annotations", "()V", "getMetricType", "()Ljava/lang/String;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.fitness.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ShowFitnessMetricParams {
    public static final String METRIC_TYPE_BLOOD_PRESSURE = "BLOOD_PRESSURE";
    public static final String METRIC_TYPE_BLOOD_SUGAR = "BLOOD_SUGAR";
    public static final String METRIC_TYPE_BODY_MASS_INDEX = "BODY_MASS_INDEX";
    public static final String METRIC_TYPE_BODY_TEMPERATURE = "BODY_TEMPERATURE";
    public static final String METRIC_TYPE_DISTANCE = "DISTANCE";
    public static final String METRIC_TYPE_ELEVATION_GAIN = "ELEVATION_GAIN";
    public static final String METRIC_TYPE_FLOOR = "FLOOR";
    public static final String METRIC_TYPE_HEART_RATE = "HEART_RATE";
    public static final String METRIC_TYPE_INSULIN_INTAKE = "INSULIN_INTAKE";
    public static final String METRIC_TYPE_LAP = "LAP";
    public static final String METRIC_TYPE_MENSTRUATION_CYCLE = "MENSTRUATION_CYCLE";
    public static final String METRIC_TYPE_OTHER = "OTHER";
    public static final String METRIC_TYPE_PACE = "PACE";
    public static final String METRIC_TYPE_SLEEP_DURATION = "SLEEP_DURATION";
    public static final String METRIC_TYPE_STEP = "STEP";
    public static final String METRIC_TYPE_WAIST_SIZE = "WAIST_SIZE";
    public static final String METRIC_TYPE_WATER_CONSUMPTION = "WATER_CONSUMPTION";
    public static final String METRIC_TYPE_WEIGHT = "WEIGHT";
    public static final String METRIC_TYPE_WORKOUT_CALORIES = "WORKOUT_CALORIES";
    private final String metricType;

    public ShowFitnessMetricParams(String metricType) {
        Intrinsics.checkNotNullParameter(metricType, "metricType");
        this.metricType = metricType;
    }

    public boolean equals(Object other) {
        return (other instanceof ShowFitnessMetricParams) && Intrinsics.areEqual(this.metricType, ((ShowFitnessMetricParams) other).metricType);
    }

    public final String getMetricType() {
        return this.metricType;
    }

    public int hashCode() {
        return Objects.hash(this.metricType);
    }
}
