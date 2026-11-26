package androidx.appsearch.builtintypes;

import androidx.appsearch.utils.DateTimeFormatValidator;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AlarmInstance extends Thing {
    public static final int STATUS_DISMISSED = 3;
    public static final int STATUS_FIRING = 2;
    public static final int STATUS_MISSED = 5;
    public static final int STATUS_SCHEDULED = 1;
    public static final int STATUS_SNOOZED = 4;
    public static final int STATUS_UNKNOWN = 0;
    private final String mScheduledTime;
    private final long mSnoozeDurationMillis;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    AlarmInstance(String str, String str2, int i, long j, long j2, String str3, List<String> list, String str4, String str5, String str6, List<PotentialAction> list2, String str7, int i2, long j3) {
        super(str, str2, i, j, j2, str3, list, str4, str5, str6, list2);
        this.mScheduledTime = (String) Preconditions.checkNotNull(str7);
        this.mStatus = i2;
        this.mSnoozeDurationMillis = j3;
    }

    public String getScheduledTime() {
        return this.mScheduledTime;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public long getSnoozeDurationMillis() {
        return this.mSnoozeDurationMillis;
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

        @Override // androidx.appsearch.builtintypes.AlarmInstance.BuilderImpl, androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ AlarmInstance build() {
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

        @Override // androidx.appsearch.builtintypes.AlarmInstance.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setSnoozeDurationMillis(long j) {
            return super.setSnoozeDurationMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.AlarmInstance.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setStatus(int i) {
            return super.setStatus(i);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setUrl(String str) {
            return super.setUrl(str);
        }

        public Builder(String str, String str2, String str3) {
            super(str, str2, str3);
        }

        public Builder(AlarmInstance alarmInstance) {
            super(alarmInstance);
        }
    }

    static class BuilderImpl<T extends BuilderImpl<T>> extends Thing.BuilderImpl<T> {
        protected final String mScheduledTime;
        protected long mSnoozeDurationMillis;
        protected int mStatus;

        BuilderImpl(String str, String str2, String str3) {
            super(str, str2);
            Preconditions.checkNotNull(str3);
            Preconditions.checkArgument(DateTimeFormatValidator.validateISO8601DateTime(str3), "scheduledTime must be in the format: yyyy-MM-ddTHH:mm:ss");
            this.mScheduledTime = str3;
            this.mSnoozeDurationMillis = -1L;
        }

        BuilderImpl(AlarmInstance alarmInstance) {
            super(new Thing.Builder(alarmInstance).build());
            this.mScheduledTime = alarmInstance.getScheduledTime();
            this.mStatus = alarmInstance.getStatus();
            this.mSnoozeDurationMillis = alarmInstance.getSnoozeDurationMillis();
        }

        public T setStatus(int i) {
            this.mStatus = i;
            return this;
        }

        public T setSnoozeDurationMillis(long j) {
            this.mSnoozeDurationMillis = j;
            return this;
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public AlarmInstance build() {
            return new AlarmInstance(this.mNamespace, this.mId, this.mDocumentScore, this.mCreationTimestampMillis, this.mDocumentTtlMillis, this.mName, this.mAlternateNames, this.mDescription, this.mImage, this.mUrl, this.mPotentialActions, this.mScheduledTime, this.mStatus, this.mSnoozeDurationMillis);
        }
    }
}
