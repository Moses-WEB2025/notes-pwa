package androidx.appsearch.safeparcel;

/* JADX INFO: loaded from: classes.dex */
public interface SafeParcelable {

    public @interface Class {
        String creator();

        boolean doNotParcelTypeDefaultValues() default false;

        boolean validate() default false;
    }

    int describeContents();
}
