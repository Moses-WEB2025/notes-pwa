package androidx.appsearch.builtintypes;

import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public class PotentialAction {
    private final String mDescription;
    final String mId;
    private final String mName;
    final String mNamespace;
    private final String mUri;

    PotentialAction(String str, String str2, String str3, String str4, String str5) {
        this.mNamespace = (String) Preconditions.checkNotNull(str);
        this.mId = (String) Preconditions.checkNotNull(str2);
        this.mName = str3;
        this.mDescription = str4;
        this.mUri = str5;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getName() {
        return this.mName;
    }

    public String getUri() {
        return this.mUri;
    }

    public static final class Builder {
        private String mDescription;
        private String mName;
        private String mUri;

        public Builder() {
        }

        public Builder(PotentialAction potentialAction) {
            this.mName = potentialAction.getName();
            this.mDescription = potentialAction.getDescription();
            this.mUri = potentialAction.getUri();
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setDescription(String str) {
            this.mDescription = str;
            return this;
        }

        public Builder setUri(String str) {
            this.mUri = str;
            return this;
        }

        public PotentialAction build() {
            return new PotentialAction("", "", this.mName, this.mDescription, this.mUri);
        }
    }
}
