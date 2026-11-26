package androidx.appsearch.builtintypes;

import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Thing {
    private final List<String> mAlternateNames;
    private final long mCreationTimestampMillis;
    private final String mDescription;
    private final int mDocumentScore;
    private final long mDocumentTtlMillis;
    private final String mId;
    private final String mImage;
    private final String mName;
    private final String mNamespace;
    private final List<PotentialAction> mPotentialActions;
    private final String mUrl;

    Thing(String str, String str2, int i, long j, long j2, String str3, List<String> list, String str4, String str5, String str6, List<PotentialAction> list2) {
        this.mNamespace = (String) Preconditions.checkNotNull(str);
        this.mId = (String) Preconditions.checkNotNull(str2);
        this.mDocumentScore = i;
        this.mCreationTimestampMillis = j;
        this.mDocumentTtlMillis = j2;
        this.mName = str3;
        if (list == null) {
            this.mAlternateNames = Collections.emptyList();
        } else {
            this.mAlternateNames = Collections.unmodifiableList(list);
        }
        this.mDescription = str4;
        this.mImage = str5;
        this.mUrl = str6;
        if (list2 == null) {
            this.mPotentialActions = Collections.emptyList();
        } else {
            this.mPotentialActions = Collections.unmodifiableList(list2);
        }
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public String getId() {
        return this.mId;
    }

    public int getDocumentScore() {
        return this.mDocumentScore;
    }

    public long getCreationTimestampMillis() {
        return this.mCreationTimestampMillis;
    }

    public long getDocumentTtlMillis() {
        return this.mDocumentTtlMillis;
    }

    public String getName() {
        return this.mName;
    }

    public List<String> getAlternateNames() {
        return this.mAlternateNames;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getImage() {
        return this.mImage;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public List<PotentialAction> getPotentialActions() {
        return this.mPotentialActions;
    }

    public static final class Builder extends BuilderImpl<Builder> {
        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl addAlternateName(String str) {
            return super.addAlternateName(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl addPotentialAction(PotentialAction potentialAction) {
            return super.addPotentialAction(potentialAction);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing build() {
            return super.build();
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl clearAlternateNames() {
            return super.clearAlternateNames();
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl clearPotentialActions() {
            return super.clearPotentialActions();
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setAlternateNames(List list) {
            return super.setAlternateNames(list);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setCreationTimestampMillis(long j) {
            return super.setCreationTimestampMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setDescription(String str) {
            return super.setDescription(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setDocumentScore(int i) {
            return super.setDocumentScore(i);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setDocumentTtlMillis(long j) {
            return super.setDocumentTtlMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setImage(String str) {
            return super.setImage(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setName(String str) {
            return super.setName(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setPotentialActions(List list) {
            return super.setPotentialActions(list);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setUrl(String str) {
            return super.setUrl(str);
        }

        public Builder(String str, String str2) {
            super(str, str2);
        }

        public Builder(Thing thing) {
            super(thing);
        }
    }

    static class BuilderImpl<T extends BuilderImpl<T>> {
        protected List<String> mAlternateNames;
        private boolean mBuilt;
        protected long mCreationTimestampMillis;
        protected String mDescription;
        protected int mDocumentScore;
        protected long mDocumentTtlMillis;
        protected final String mId;
        protected String mImage;
        protected String mName;
        protected final String mNamespace;
        protected List<PotentialAction> mPotentialActions;
        protected String mUrl;

        BuilderImpl(String str, String str2) {
            this.mAlternateNames = new ArrayList();
            this.mPotentialActions = new ArrayList();
            this.mBuilt = false;
            this.mNamespace = (String) Preconditions.checkNotNull(str);
            this.mId = (String) Preconditions.checkNotNull(str2);
            this.mCreationTimestampMillis = -1L;
        }

        BuilderImpl(Thing thing) {
            this(thing.getNamespace(), thing.getId());
            this.mDocumentScore = thing.getDocumentScore();
            this.mCreationTimestampMillis = thing.getCreationTimestampMillis();
            this.mDocumentTtlMillis = thing.getDocumentTtlMillis();
            this.mName = thing.getName();
            this.mAlternateNames = new ArrayList(thing.getAlternateNames());
            this.mDescription = thing.getDescription();
            this.mImage = thing.getImage();
            this.mUrl = thing.getUrl();
            this.mPotentialActions = new ArrayList(thing.getPotentialActions());
        }

        public T setDocumentScore(int i) {
            resetIfBuilt();
            this.mDocumentScore = i;
            return this;
        }

        public T setCreationTimestampMillis(long j) {
            resetIfBuilt();
            this.mCreationTimestampMillis = j;
            return this;
        }

        public T setDocumentTtlMillis(long j) {
            resetIfBuilt();
            this.mDocumentTtlMillis = j;
            return this;
        }

        public T setName(String str) {
            resetIfBuilt();
            this.mName = str;
            return this;
        }

        public T addAlternateName(String str) {
            resetIfBuilt();
            Preconditions.checkNotNull(str);
            this.mAlternateNames.add(str);
            return this;
        }

        public T setAlternateNames(List<String> list) {
            resetIfBuilt();
            clearAlternateNames();
            if (list != null) {
                this.mAlternateNames.addAll(list);
            }
            return this;
        }

        public T clearAlternateNames() {
            resetIfBuilt();
            this.mAlternateNames.clear();
            return this;
        }

        public T setDescription(String str) {
            resetIfBuilt();
            this.mDescription = str;
            return this;
        }

        public T setImage(String str) {
            resetIfBuilt();
            this.mImage = str;
            return this;
        }

        public T setUrl(String str) {
            resetIfBuilt();
            this.mUrl = str;
            return this;
        }

        public T addPotentialAction(PotentialAction potentialAction) {
            resetIfBuilt();
            Preconditions.checkNotNull(potentialAction);
            this.mPotentialActions.add(potentialAction);
            return this;
        }

        public T setPotentialActions(List<PotentialAction> list) {
            resetIfBuilt();
            clearPotentialActions();
            if (list != null) {
                this.mPotentialActions.addAll(list);
            }
            return this;
        }

        public T clearPotentialActions() {
            resetIfBuilt();
            this.mPotentialActions.clear();
            return this;
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mAlternateNames = new ArrayList(this.mAlternateNames);
                this.mPotentialActions = new ArrayList(this.mPotentialActions);
                this.mBuilt = false;
            }
        }

        public Thing build() {
            this.mBuilt = true;
            return new Thing(this.mNamespace, this.mId, this.mDocumentScore, this.mCreationTimestampMillis, this.mDocumentTtlMillis, this.mName, this.mAlternateNames, this.mDescription, this.mImage, this.mUrl, this.mPotentialActions);
        }
    }
}
