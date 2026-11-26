package androidx.appsearch.builtintypes;

import android.net.Uri;
import androidx.appsearch.app.StringSerializer;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MobileApplication extends Thing {
    private final List<String> mAlternateNames;
    private final String mClassName;
    private final String mDisplayName;
    private final Uri mIconUri;
    private final String mPackageName;
    private final byte[] mSha256Certificate;
    private final long mUpdatedTimestampMillis;

    MobileApplication(String str, String str2, int i, long j, long j2, String str3, List<String> list, String str4, String str5, String str6, List<PotentialAction> list2, String str7, String str8, Uri uri, byte[] bArr, long j3, String str9) {
        super(str, str2, i, j, j2, str3, list, str4, str5, str6, list2);
        this.mPackageName = (String) Preconditions.checkNotNull(str7);
        this.mDisplayName = str8;
        if (list == null) {
            this.mAlternateNames = Collections.emptyList();
        } else {
            this.mAlternateNames = Collections.unmodifiableList(list);
        }
        this.mIconUri = uri;
        this.mSha256Certificate = (byte[]) Preconditions.checkNotNull(bArr);
        this.mUpdatedTimestampMillis = j3;
        this.mClassName = str9;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    @Override // androidx.appsearch.builtintypes.Thing
    public List<String> getAlternateNames() {
        return this.mAlternateNames;
    }

    public Uri getIconUri() {
        return this.mIconUri;
    }

    public byte[] getSha256Certificate() {
        return this.mSha256Certificate;
    }

    public long getUpdatedTimestampMillis() {
        return this.mUpdatedTimestampMillis;
    }

    public String getClassName() {
        return this.mClassName;
    }

    public static final class Builder extends BuilderImpl<Builder> {
        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl addAlternateName(String str) {
            return super.addAlternateName(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl addPotentialAction(PotentialAction potentialAction) {
            return super.addPotentialAction(potentialAction);
        }

        @Override // androidx.appsearch.builtintypes.MobileApplication.BuilderImpl, androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ MobileApplication build() {
            return super.build();
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl clearAlternateNames() {
            return super.clearAlternateNames();
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl clearPotentialActions() {
            return super.clearPotentialActions();
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setAlternateNames(List list) {
            return super.setAlternateNames(list);
        }

        @Override // androidx.appsearch.builtintypes.MobileApplication.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setClassName(String str) {
            return super.setClassName(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setCreationTimestampMillis(long j) {
            return super.setCreationTimestampMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setDescription(String str) {
            return super.setDescription(str);
        }

        @Override // androidx.appsearch.builtintypes.MobileApplication.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setDisplayName(String str) {
            return super.setDisplayName(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setDocumentScore(int i) {
            return super.setDocumentScore(i);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setDocumentTtlMillis(long j) {
            return super.setDocumentTtlMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.MobileApplication.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setIconUri(Uri uri) {
            return super.setIconUri(uri);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setImage(String str) {
            return super.setImage(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setName(String str) {
            return super.setName(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setPotentialActions(List list) {
            return super.setPotentialActions(list);
        }

        @Override // androidx.appsearch.builtintypes.MobileApplication.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setUpdatedTimestampMillis(long j) {
            return super.setUpdatedTimestampMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setUrl(String str) {
            return super.setUrl(str);
        }

        public Builder(String str, String str2, String str3, byte[] bArr) {
            super((String) Preconditions.checkNotNull(str), (String) Preconditions.checkNotNull(str2), (String) Preconditions.checkNotNull(str3), (byte[]) Preconditions.checkNotNull(bArr));
        }

        public Builder(MobileApplication mobileApplication) {
            super((MobileApplication) Preconditions.checkNotNull(mobileApplication));
        }
    }

    static class BuilderImpl<T extends BuilderImpl<T>> extends Thing.BuilderImpl<T> {
        private boolean mBuilt;
        private String mClassName;
        private String mDisplayName;
        private Uri mIconUri;
        private final String mPackageName;
        private final byte[] mSha256Certificate;
        private long mUpdatedTimestampMillis;

        BuilderImpl(String str, String str2, String str3, byte[] bArr) {
            super(str2, str);
            this.mBuilt = false;
            this.mPackageName = (String) Preconditions.checkNotNull(str3);
            this.mSha256Certificate = (byte[]) Preconditions.checkNotNull(bArr);
        }

        BuilderImpl(MobileApplication mobileApplication) {
            super(new Thing.Builder(mobileApplication).build());
            this.mBuilt = false;
            Preconditions.checkNotNull(mobileApplication);
            this.mPackageName = mobileApplication.mPackageName;
            this.mDisplayName = mobileApplication.mDisplayName;
            this.mIconUri = mobileApplication.mIconUri;
            this.mSha256Certificate = mobileApplication.mSha256Certificate;
            this.mUpdatedTimestampMillis = mobileApplication.mUpdatedTimestampMillis;
            this.mClassName = mobileApplication.mClassName;
        }

        public T setDisplayName(String str) {
            resetIfBuilt();
            this.mDisplayName = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public T setIconUri(Uri uri) {
            resetIfBuilt();
            this.mIconUri = (Uri) Preconditions.checkNotNull(uri);
            return this;
        }

        public T setUpdatedTimestampMillis(long j) {
            resetIfBuilt();
            this.mUpdatedTimestampMillis = j;
            return this;
        }

        public T setClassName(String str) {
            resetIfBuilt();
            this.mClassName = (String) Preconditions.checkNotNull(str);
            return this;
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mAlternateNames = new ArrayList(this.mAlternateNames);
                this.mBuilt = false;
            }
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public MobileApplication build() {
            this.mBuilt = true;
            return new MobileApplication(this.mNamespace, this.mId, this.mDocumentScore, this.mCreationTimestampMillis, this.mDocumentTtlMillis, this.mName, this.mAlternateNames, this.mDescription, this.mImage, this.mUrl, this.mPotentialActions, this.mPackageName, this.mDisplayName, this.mIconUri, this.mSha256Certificate, this.mUpdatedTimestampMillis, this.mClassName);
        }
    }

    static class IconUriAsUri implements StringSerializer<Uri> {
        IconUriAsUri() {
        }

        @Override // androidx.appsearch.app.StringSerializer
        public String serialize(Uri uri) {
            return uri.toString();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.appsearch.app.StringSerializer
        public Uri deserialize(String str) {
            if (str == null) {
                return null;
            }
            try {
                return Uri.parse(str);
            } catch (RuntimeException unused) {
                return null;
            }
        }
    }
}
