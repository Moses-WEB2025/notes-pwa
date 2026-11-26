package androidx.appsearch.safeparcel;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.util.Preconditions;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PackageIdentifierParcelCreator implements Parcelable.Creator<PackageIdentifierParcel> {
    private static final String PACKAGE_NAME_FIELD = "packageName";
    private static final String SHA256_CERTIFICATE_FIELD = "sha256Certificate";

    private static PackageIdentifierParcel createPackageIdentifierFromBundle(Bundle bundle) {
        Objects.requireNonNull(bundle);
        return new PackageIdentifierParcel((String) Preconditions.checkNotNull(bundle.getString("packageName")), (byte[]) Preconditions.checkNotNull(bundle.getByteArray(SHA256_CERTIFICATE_FIELD)));
    }

    private static Bundle createBundleFromPackageIdentifier(PackageIdentifierParcel packageIdentifierParcel) {
        Objects.requireNonNull(packageIdentifierParcel);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", packageIdentifierParcel.getPackageName());
        bundle.putByteArray(SHA256_CERTIFICATE_FIELD, packageIdentifierParcel.getSha256Certificate());
        return bundle;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public PackageIdentifierParcel createFromParcel(Parcel parcel) {
        return createPackageIdentifierFromBundle((Bundle) Preconditions.checkNotNull(parcel.readBundle(getClass().getClassLoader())));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.os.Parcelable.Creator
    public PackageIdentifierParcel[] newArray(int i) {
        return new PackageIdentifierParcel[i];
    }

    public static void writeToParcel(PackageIdentifierParcel packageIdentifierParcel, Parcel parcel, int i) {
        parcel.writeBundle(createBundleFromPackageIdentifier(packageIdentifierParcel));
    }
}
