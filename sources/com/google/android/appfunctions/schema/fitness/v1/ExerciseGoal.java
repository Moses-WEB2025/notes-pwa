package com.google.android.appfunctions.schema.fitness.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B+\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u000b\u0010\tR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\f\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/google/android/appfunctions/schema/fitness/v1/ExerciseGoal;", "", "durationMillis", "", "distanceMeters", "calories", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V", "getDurationMillis", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getDistanceMeters", "getCalories", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.fitness.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ExerciseGoal {
    private final Long calories;
    private final Long distanceMeters;
    private final Long durationMillis;

    public ExerciseGoal() {
        this(null, null, null, 7, null);
    }

    public ExerciseGoal(Long l, Long l2, Long l3) {
        this.durationMillis = l;
        this.distanceMeters = l2;
        this.calories = l3;
    }

    public /* synthetic */ ExerciseGoal(Long l, Long l2, Long l3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(1 == (i & 1) ? null : l, (i & 2) != 0 ? null : l2, (i & 4) != 0 ? null : l3);
    }

    public boolean equals(Object other) {
        if (!(other instanceof ExerciseGoal)) {
            return false;
        }
        ExerciseGoal exerciseGoal = (ExerciseGoal) other;
        return Intrinsics.areEqual(this.durationMillis, exerciseGoal.durationMillis) && Intrinsics.areEqual(this.distanceMeters, exerciseGoal.distanceMeters) && Intrinsics.areEqual(this.calories, exerciseGoal.calories);
    }

    public final Long getCalories() {
        return this.calories;
    }

    public final Long getDistanceMeters() {
        return this.distanceMeters;
    }

    public final Long getDurationMillis() {
        return this.durationMillis;
    }

    public int hashCode() {
        return Objects.hash(this.durationMillis, this.distanceMeters, this.calories);
    }
}
