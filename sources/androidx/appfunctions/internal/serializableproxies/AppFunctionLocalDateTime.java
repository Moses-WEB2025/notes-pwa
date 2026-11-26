package androidx.appfunctions.internal.serializableproxies;

import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.data.VariableNames;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.time.LocalDateTime;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppFunctionLocalDateTime.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u0000 $2\u00020\u0001:\u0001$B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0006\u0010\u0014\u001a\u00020\u0015J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003JO\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010!\u001a\u00020\u0003HÖ\u0081\u0004J\n\u0010\"\u001a\u00020#HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r¨\u0006%"}, d2 = {"Landroidx/appfunctions/internal/serializableproxies/AppFunctionLocalDateTime;", "", VariableNames.VAR_YEAR, "", VariableNames.VAR_MONTH, "dayOfMonth", "hour", VariableNames.VAR_MINUTE, VariableNames.VAR_SECOND, "nanoOfSecond", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(IIIIIII)V", "getYear", "()I", "getMonth", "getDayOfMonth", "getHour", "getMinute", "getSecond", "getNanoOfSecond", "toLocalDateTime", "Ljava/time/LocalDateTime;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "Companion", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
public final /* data */ class AppFunctionLocalDateTime {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int dayOfMonth;
    private final int hour;
    private final int minute;
    private final int month;
    private final int nanoOfSecond;
    private final int second;
    private final int year;

    public static /* synthetic */ AppFunctionLocalDateTime copy$default(AppFunctionLocalDateTime appFunctionLocalDateTime, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Object obj) {
        if ((i8 & 1) != 0) {
            i = appFunctionLocalDateTime.year;
        }
        if ((i8 & 2) != 0) {
            i2 = appFunctionLocalDateTime.month;
        }
        if ((i8 & 4) != 0) {
            i3 = appFunctionLocalDateTime.dayOfMonth;
        }
        if ((i8 & 8) != 0) {
            i4 = appFunctionLocalDateTime.hour;
        }
        if ((i8 & 16) != 0) {
            i5 = appFunctionLocalDateTime.minute;
        }
        if ((i8 & 32) != 0) {
            i6 = appFunctionLocalDateTime.second;
        }
        if ((i8 & 64) != 0) {
            i7 = appFunctionLocalDateTime.nanoOfSecond;
        }
        int i9 = i6;
        int i10 = i7;
        int i11 = i5;
        int i12 = i3;
        return appFunctionLocalDateTime.copy(i, i2, i12, i4, i11, i9, i10);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getYear() {
        return this.year;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getMonth() {
        return this.month;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getDayOfMonth() {
        return this.dayOfMonth;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getHour() {
        return this.hour;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getMinute() {
        return this.minute;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final int getSecond() {
        return this.second;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final int getNanoOfSecond() {
        return this.nanoOfSecond;
    }

    public final AppFunctionLocalDateTime copy(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        return new AppFunctionLocalDateTime(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppFunctionLocalDateTime)) {
            return false;
        }
        AppFunctionLocalDateTime appFunctionLocalDateTime = (AppFunctionLocalDateTime) other;
        return this.year == appFunctionLocalDateTime.year && this.month == appFunctionLocalDateTime.month && this.dayOfMonth == appFunctionLocalDateTime.dayOfMonth && this.hour == appFunctionLocalDateTime.hour && this.minute == appFunctionLocalDateTime.minute && this.second == appFunctionLocalDateTime.second && this.nanoOfSecond == appFunctionLocalDateTime.nanoOfSecond;
    }

    public int hashCode() {
        return (((((((((((Integer.hashCode(this.year) * 31) + Integer.hashCode(this.month)) * 31) + Integer.hashCode(this.dayOfMonth)) * 31) + Integer.hashCode(this.hour)) * 31) + Integer.hashCode(this.minute)) * 31) + Integer.hashCode(this.second)) * 31) + Integer.hashCode(this.nanoOfSecond);
    }

    public String toString() {
        return "AppFunctionLocalDateTime(year=" + this.year + ", month=" + this.month + ", dayOfMonth=" + this.dayOfMonth + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", nanoOfSecond=" + this.nanoOfSecond + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public AppFunctionLocalDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.year = i;
        this.month = i2;
        this.dayOfMonth = i3;
        this.hour = i4;
        this.minute = i5;
        this.second = i6;
        this.nanoOfSecond = i7;
    }

    public final int getYear() {
        return this.year;
    }

    public final int getMonth() {
        return this.month;
    }

    public final int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public final int getHour() {
        return this.hour;
    }

    public final int getMinute() {
        return this.minute;
    }

    public final int getSecond() {
        return this.second;
    }

    public final int getNanoOfSecond() {
        return this.nanoOfSecond;
    }

    public final LocalDateTime toLocalDateTime() {
        LocalDateTime localDateTimeOf = LocalDateTime.of(this.year, this.month, this.dayOfMonth, this.hour, this.minute, this.second, this.nanoOfSecond);
        Intrinsics.checkNotNullExpressionValue(localDateTimeOf, "of(...)");
        return localDateTimeOf;
    }

    /* JADX INFO: compiled from: AppFunctionLocalDateTime.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Landroidx/appfunctions/internal/serializableproxies/AppFunctionLocalDateTime$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "fromLocalDateTime", "Landroidx/appfunctions/internal/serializableproxies/AppFunctionLocalDateTime;", "localDateTime", "Ljava/time/LocalDateTime;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AppFunctionLocalDateTime fromLocalDateTime(LocalDateTime localDateTime) {
            Intrinsics.checkNotNullParameter(localDateTime, "localDateTime");
            return new AppFunctionLocalDateTime(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano());
        }
    }
}
