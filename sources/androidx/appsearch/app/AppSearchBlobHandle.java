package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.IndentingStringBuilder;
import androidx.core.util.Preconditions;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchBlobHandle extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AppSearchBlobHandle> CREATOR = new StubCreators.AppSearchBlobHandleCreator();
    private static final int SHA_256_DIGEST_BYTE_LENGTH = 32;
    private final String mDatabaseName;
    private Integer mHashCode;
    private final String mNamespace;
    private final String mPackageName;
    private final byte[] mSha256Digest;

    AppSearchBlobHandle(byte[] bArr, String str, String str2, String str3) {
        this.mSha256Digest = (byte[]) Preconditions.checkNotNull(bArr);
        Preconditions.checkState(bArr.length == 32, "The input digest isn't a sha-256 digest.");
        this.mPackageName = (String) Preconditions.checkNotNull(str);
        this.mDatabaseName = (String) Preconditions.checkNotNull(str2);
        this.mNamespace = (String) Preconditions.checkNotNull(str3);
    }

    public byte[] getSha256Digest() {
        return this.mSha256Digest;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getDatabaseName() {
        return this.mDatabaseName;
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSearchBlobHandle)) {
            return false;
        }
        AppSearchBlobHandle appSearchBlobHandle = (AppSearchBlobHandle) obj;
        return Arrays.equals(this.mSha256Digest, appSearchBlobHandle.mSha256Digest) && this.mPackageName.equals(appSearchBlobHandle.mPackageName) && this.mDatabaseName.equals(appSearchBlobHandle.mDatabaseName) && this.mNamespace.equals(appSearchBlobHandle.mNamespace);
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Objects.hash(Integer.valueOf(Arrays.hashCode(this.mSha256Digest)), this.mPackageName, this.mDatabaseName, this.mNamespace));
        }
        return this.mHashCode.intValue();
    }

    public String toString() {
        IndentingStringBuilder indentingStringBuilder = new IndentingStringBuilder();
        indentingStringBuilder.append("{\n");
        indentingStringBuilder.increaseIndentLevel();
        indentingStringBuilder.append("packageName: \"").append(this.mPackageName).append("\",\n");
        indentingStringBuilder.append("databaseName: \"").append(this.mDatabaseName).append("\",\n");
        indentingStringBuilder.append("namespace: \"").append(this.mNamespace).append("\",\n");
        indentingStringBuilder.append("digest: \"");
        for (byte b : this.mSha256Digest) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                indentingStringBuilder.append((Object) '0');
            }
            indentingStringBuilder.append(hexString);
        }
        indentingStringBuilder.append("\",\n").decreaseIndentLevel();
        indentingStringBuilder.append(StringSubstitutor.DEFAULT_VAR_END);
        return indentingStringBuilder.toString();
    }

    public static AppSearchBlobHandle createWithSha256(byte[] bArr, String str, String str2, String str3) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkArgument(bArr.length == 32, "The digest is not a SHA-256 digest");
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(str3);
        return new AppSearchBlobHandle(bArr, str, str2, str3);
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.AppSearchBlobHandleCreator.writeToParcel(this, parcel, i);
    }
}
