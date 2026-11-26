package androidx.appsearch.safeparcel;

import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractSafeParcelable implements SafeParcelable {
    static final String NULL = "SAFE_PARCELABLE_NULL_STRING";

    public @interface Constructor {
    }

    public @interface Field {
        String defaultValue() default "SAFE_PARCELABLE_NULL_STRING";

        String defaultValueUnchecked() default "SAFE_PARCELABLE_NULL_STRING";

        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        int id();

        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface Indicator {
        String getter() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public @interface Param {
        int id();
    }

    public @interface RemovedParam {
        String defaultValue() default "SAFE_PARCELABLE_NULL_STRING";

        String defaultValueUnchecked() default "SAFE_PARCELABLE_NULL_STRING";

        int id();
    }

    public @interface Reserved {
        int[] value();
    }

    public @interface VersionField {
        String getter() default "SAFE_PARCELABLE_NULL_STRING";

        int id();

        String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

    @Override // androidx.appsearch.safeparcel.SafeParcelable
    public final int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }
}
