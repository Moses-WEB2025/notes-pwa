package androidx.appsearch.builtintypes;

import androidx.core.util.Preconditions;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class WebPage extends Thing {
    public static final String SCHEMA_NAME = "builtin:WebPage";
    private final ImageObject mFavicon;

    WebPage(String str, String str2, int i, long j, long j2, String str3, List<String> list, String str4, String str5, String str6, List<PotentialAction> list2, ImageObject imageObject) {
        super(str, str2, i, j, j2, str3, list, str4, str5, str6, list2);
        this.mFavicon = imageObject;
    }

    public ImageObject getFavicon() {
        return this.mFavicon;
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

        @Override // androidx.appsearch.builtintypes.WebPage.BuilderImpl, androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ WebPage build() {
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

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setCreationTimestampMillis(long j) {
            return super.setCreationTimestampMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setDescription(String str) {
            return super.setDescription(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setDocumentScore(int i) {
            return super.setDocumentScore(i);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setDocumentTtlMillis(long j) {
            return super.setDocumentTtlMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.WebPage.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setFavicon(ImageObject imageObject) {
            return super.setFavicon(imageObject);
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

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setUrl(String str) {
            return super.setUrl(str);
        }

        public Builder(String str, String str2) {
            super(str, str2);
        }

        public Builder(WebPage webPage) {
            super(webPage);
        }
    }

    static class BuilderImpl<Self extends BuilderImpl<Self>> extends Thing.BuilderImpl<Self> {
        private ImageObject mFavicon;

        BuilderImpl(String str, String str2) {
            super(str, str2);
        }

        BuilderImpl(WebPage webPage) {
            super(new Thing.Builder((Thing) Preconditions.checkNotNull(webPage)).build());
            this.mFavicon = webPage.getFavicon();
        }

        public Self setFavicon(ImageObject imageObject) {
            this.mFavicon = imageObject;
            return this;
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public WebPage build() {
            return new WebPage(this.mNamespace, this.mId, this.mDocumentScore, this.mCreationTimestampMillis, this.mDocumentTtlMillis, this.mName, this.mAlternateNames, this.mDescription, this.mImage, this.mUrl, this.mPotentialActions, this.mFavicon);
        }
    }
}
