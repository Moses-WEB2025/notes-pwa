package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class VisibilityPermissionConfig extends AbstractSafeParcelable {
    final int[] mAllRequiredPermissions;
    private GenericDocument mGenericDocument;
    private Integer mHashCode;
    public static final Parcelable.Creator<VisibilityPermissionConfig> CREATOR = new StubCreators.VisibilityPermissionConfigCreator();
    public static final String SCHEMA_TYPE = "VisibilityPermissionType";
    public static final String ALL_REQUIRED_PERMISSIONS_PROPERTY = "allRequiredPermissions";
    public static final AppSearchSchema SCHEMA = new AppSearchSchema.Builder(SCHEMA_TYPE).addProperty(new AppSearchSchema.LongPropertyConfig.Builder(ALL_REQUIRED_PERMISSIONS_PROPERTY).setCardinality(1).build()).build();

    VisibilityPermissionConfig(int[] iArr) {
        this.mAllRequiredPermissions = iArr;
    }

    public VisibilityPermissionConfig(Set<Integer> set) {
        this.mAllRequiredPermissions = toInts((Set) Objects.requireNonNull(set));
    }

    public Set<Integer> getAllRequiredPermissions() {
        return toIntegerSet(this.mAllRequiredPermissions);
    }

    private static int[] toInts(Set<Integer> set) {
        int[] iArr = new int[set.size()];
        Iterator<Integer> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    private static Set<Integer> toIntegerSet(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        ArraySet arraySet = new ArraySet(iArr.length);
        for (int i : iArr) {
            arraySet.add(Integer.valueOf(i));
        }
        return arraySet;
    }

    public GenericDocument toGenericDocument() {
        if (this.mGenericDocument == null) {
            GenericDocument.Builder builder = new GenericDocument.Builder("", "", SCHEMA_TYPE);
            int[] iArr = this.mAllRequiredPermissions;
            if (iArr != null) {
                long[] jArr = new long[iArr.length];
                int i = 0;
                while (true) {
                    int[] iArr2 = this.mAllRequiredPermissions;
                    if (i >= iArr2.length) {
                        break;
                    }
                    jArr[i] = iArr2[i];
                    i++;
                }
                builder.setPropertyLong(ALL_REQUIRED_PERMISSIONS_PROPERTY, jArr);
            }
            builder.setCreationTimestampMillis(0L);
            this.mGenericDocument = builder.build();
        }
        return this.mGenericDocument;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Arrays.hashCode(this.mAllRequiredPermissions));
        }
        return this.mHashCode.intValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof VisibilityPermissionConfig) {
            return Arrays.equals(this.mAllRequiredPermissions, ((VisibilityPermissionConfig) obj).mAllRequiredPermissions);
        }
        return false;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.VisibilityPermissionConfigCreator.writeToParcel(this, parcel, i);
    }
}
