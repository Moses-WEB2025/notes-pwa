package com.google.android.appfunctions.schema.fitness.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u001c\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/google/android/appfunctions/schema/fitness/v1/StartExerciseParams;", "", "exerciseType", "", "exerciseGoal", "Lcom/google/android/appfunctions/schema/fitness/v1/ExerciseGoal;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Lcom/google/android/appfunctions/schema/fitness/v1/ExerciseGoal;)V", "getExerciseType$annotations", "()V", "getExerciseType", "()Ljava/lang/String;", "getExerciseGoal", "()Lcom/google/android/appfunctions/schema/fitness/v1/ExerciseGoal;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.fitness.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class StartExerciseParams {
    public static final String EXERCISE_TYPE_ALPINE_SKIING = "ALPINE_SKIING";
    public static final String EXERCISE_TYPE_BACKPACKING = "BACKPACKING";
    public static final String EXERCISE_TYPE_BACK_EXTENSION = "BACK_EXTENSION";
    public static final String EXERCISE_TYPE_BADMINTON = "BADMINTON";
    public static final String EXERCISE_TYPE_BARBELL_SHOULDER_PRESS = "BARBELL_SHOULDER_PRESS";
    public static final String EXERCISE_TYPE_BASEBALL = "BASEBALL";
    public static final String EXERCISE_TYPE_BASKETBALL = "BASKETBALL";
    public static final String EXERCISE_TYPE_BENCH_PRESS = "BENCH_PRESS";
    public static final String EXERCISE_TYPE_BIKING = "BIKING";
    public static final String EXERCISE_TYPE_BIKING_STATIONARY = "BIKING_STATIONARY";
    public static final String EXERCISE_TYPE_BOOT_CAMP = "BOOT_CAMP";
    public static final String EXERCISE_TYPE_BOXING = "BOXING";
    public static final String EXERCISE_TYPE_BURPEE = "BURPEE";
    public static final String EXERCISE_TYPE_CALISTHENICS = "CALISTHENICS";
    public static final String EXERCISE_TYPE_CRICKET = "CRICKET";
    public static final String EXERCISE_TYPE_CROSS_COUNTRY_SKIING = "CROSS_COUNTRY_SKIING";
    public static final String EXERCISE_TYPE_CRUNCH = "CRUNCH";
    public static final String EXERCISE_TYPE_DANCING = "DANCING";
    public static final String EXERCISE_TYPE_DEADLIFT = "DEADLIFT";
    public static final String EXERCISE_TYPE_ELLIPTICAL = "ELLIPTICAL";
    public static final String EXERCISE_TYPE_EXERCISE_CLASS = "EXERCISE_CLASS";
    public static final String EXERCISE_TYPE_FENCING = "FENCING";
    public static final String EXERCISE_TYPE_FOOTBALL_AMERICAN = "FOOTBALL_AMERICAN";
    public static final String EXERCISE_TYPE_FOOTBALL_AUSTRALIAN = "FOOTBALL_AUSTRALIAN";
    public static final String EXERCISE_TYPE_FORWARD_TWIST = "FORWARD_TWIST";
    public static final String EXERCISE_TYPE_FRISBEE_DISC = "FRISBEE_DISC";
    public static final String EXERCISE_TYPE_GOLF = "GOLF";
    public static final String EXERCISE_TYPE_GUIDED_BREATHING = "GUIDED_BREATHING";
    public static final String EXERCISE_TYPE_GYMNASTICS = "GYMNASTICS";
    public static final String EXERCISE_TYPE_HANDBALL = "HANDBALL";
    public static final String EXERCISE_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING = "HIGH_INTENSITY_INTERVAL_TRAINING";
    public static final String EXERCISE_TYPE_HIKING = "HIKING";
    public static final String EXERCISE_TYPE_HORSE_RIDING = "HORSE_RIDING";
    public static final String EXERCISE_TYPE_ICE_HOCKEY = "ICE_HOCKEY";
    public static final String EXERCISE_TYPE_ICE_SKATING = "ICE_SKATING";
    public static final String EXERCISE_TYPE_INLINE_SKATING = "INLINE_SKATING";
    public static final String EXERCISE_TYPE_JUMPING_JACK = "JUMPING_JACK";
    public static final String EXERCISE_TYPE_JUMP_ROPE = "JUMP_ROPE";
    public static final String EXERCISE_TYPE_LAT_PULL_DOWN = "LAT_PULL_DOWN";
    public static final String EXERCISE_TYPE_LUNGE = "LUNGE";
    public static final String EXERCISE_TYPE_MARTIAL_ARTS = "MARTIAL_ARTS";
    public static final String EXERCISE_TYPE_MEDITATION = "MEDITATION";
    public static final String EXERCISE_TYPE_MOUNTAIN_BIKING = "MOUNTAIN_BIKING";
    public static final String EXERCISE_TYPE_ORIENTEERING = "ORIENTEERING";
    public static final String EXERCISE_TYPE_PADDLING = "PADDLING";
    public static final String EXERCISE_TYPE_PARA_GLIDING = "PARA_GLIDING";
    public static final String EXERCISE_TYPE_PILATES = "PILATES";
    public static final String EXERCISE_TYPE_PLANK = "PLANK";
    public static final String EXERCISE_TYPE_RACQUETBALL = "RACQUETBALL";
    public static final String EXERCISE_TYPE_ROCK_CLIMBING = "ROCK_CLIMBING";
    public static final String EXERCISE_TYPE_ROLLER_HOCKEY = "ROLLER_HOCKEY";
    public static final String EXERCISE_TYPE_ROLLER_SKATING = "ROLLER_SKATING";
    public static final String EXERCISE_TYPE_ROWING = "ROWING";
    public static final String EXERCISE_TYPE_ROWING_MACHINE = "ROWING_MACHINE";
    public static final String EXERCISE_TYPE_RUGBY = "RUGBY";
    public static final String EXERCISE_TYPE_RUNNING = "RUNNING";
    public static final String EXERCISE_TYPE_RUNNING_TREADMILL = "RUNNING_TREADMILL";
    public static final String EXERCISE_TYPE_SAILING = "SAILING";
    public static final String EXERCISE_TYPE_SCUBA_DIVING = "SCUBA_DIVING";
    public static final String EXERCISE_TYPE_SKATING = "SKATING";
    public static final String EXERCISE_TYPE_SKIING = "SKIING";
    public static final String EXERCISE_TYPE_SNOWBOARDING = "SNOWBOARDING";
    public static final String EXERCISE_TYPE_SNOWSHOEING = "SNOWSHOEING";
    public static final String EXERCISE_TYPE_SOCCER = "SOCCER";
    public static final String EXERCISE_TYPE_SOFTBALL = "SOFTBALL";
    public static final String EXERCISE_TYPE_SQUASH = "SQUASH";
    public static final String EXERCISE_TYPE_SQUAT = "SQUAT";
    public static final String EXERCISE_TYPE_STAIR_CLIMBING = "STAIR_CLIMBING";
    public static final String EXERCISE_TYPE_STAIR_CLIMBING_MACHINE = "STAIR_CLIMBING_MACHINE";
    public static final String EXERCISE_TYPE_STRENGTH_TRAINING = "STRENGTH_TRAINING";
    public static final String EXERCISE_TYPE_STRETCHING = "STRETCHING";
    public static final String EXERCISE_TYPE_SURFING = "SURFING";
    public static final String EXERCISE_TYPE_SWIMMING_OPEN_WATER = "SWIMMING_OPEN_WATER";
    public static final String EXERCISE_TYPE_SWIMMING_POOL = "SWIMMING_POOL";
    public static final String EXERCISE_TYPE_TABLE_TENNIS = "TABLE_TENNIS";
    public static final String EXERCISE_TYPE_TENNIS = "TENNIS";
    public static final String EXERCISE_TYPE_UNKNOWN = "UNKNOWN";
    public static final String EXERCISE_TYPE_UPPER_TWIST = "UPPER_TWIST";
    public static final String EXERCISE_TYPE_VOLLEYBALL = "VOLLEYBALL";
    public static final String EXERCISE_TYPE_WALKING = "WALKING";
    public static final String EXERCISE_TYPE_WATER_POLO = "WATER_POLO";
    public static final String EXERCISE_TYPE_WEIGHTLIFTING = "WEIGHTLIFTING";
    public static final String EXERCISE_TYPE_WORKOUT = "WORKOUT";
    public static final String EXERCISE_TYPE_YACHTING = "YACHTING";
    public static final String EXERCISE_TYPE_YOGA = "YOGA";
    private final ExerciseGoal exerciseGoal;
    private final String exerciseType;

    public StartExerciseParams(String exerciseType, ExerciseGoal exerciseGoal) {
        Intrinsics.checkNotNullParameter(exerciseType, "exerciseType");
        this.exerciseType = exerciseType;
        this.exerciseGoal = exerciseGoal;
    }

    public /* synthetic */ StartExerciseParams(String str, ExerciseGoal exerciseGoal, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : exerciseGoal);
    }

    public boolean equals(Object other) {
        if (!(other instanceof StartExerciseParams)) {
            return false;
        }
        StartExerciseParams startExerciseParams = (StartExerciseParams) other;
        return Intrinsics.areEqual(this.exerciseType, startExerciseParams.exerciseType) && Intrinsics.areEqual(this.exerciseGoal, startExerciseParams.exerciseGoal);
    }

    public final ExerciseGoal getExerciseGoal() {
        return this.exerciseGoal;
    }

    public final String getExerciseType() {
        return this.exerciseType;
    }

    public int hashCode() {
        return Objects.hash(this.exerciseType, this.exerciseGoal);
    }
}
