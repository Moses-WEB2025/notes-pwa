package androidx.appsearch.builtintypes;

import android.content.Context;
import android.os.SystemClock;
import androidx.appsearch.utils.BootCountUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Timer extends Thing {
    public static final int STATUS_EXPIRED = 3;
    public static final int STATUS_MISSED = 4;
    public static final int STATUS_PAUSED = 2;
    public static final int STATUS_RESET = 5;
    public static final int STATUS_STARTED = 1;
    public static final int STATUS_UNKNOWN = 0;
    private final long mBaseTimeMillis;
    private final long mBaseTimeMillisInElapsedRealtime;
    private final int mBootCount;
    private final long mDurationMillis;
    private final long mOriginalDurationMillis;
    private final long mRemainingDurationMillis;
    private final String mRingtone;
    private final boolean mShouldVibrate;
    private final long mStartTimeMillis;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    Timer(String str, String str2, int i, long j, long j2, String str3, List<String> list, String str4, String str5, String str6, List<PotentialAction> list2, long j3, long j4, long j5, long j6, long j7, int i2, long j8, String str7, int i3, boolean z) {
        super(str, str2, i, j, j2, str3, list, str4, str5, str6, list2);
        this.mDurationMillis = j3;
        this.mOriginalDurationMillis = j4;
        this.mStartTimeMillis = j5;
        this.mBaseTimeMillis = j6;
        this.mBaseTimeMillisInElapsedRealtime = j7;
        this.mBootCount = i2;
        this.mRemainingDurationMillis = j8;
        this.mRingtone = str7;
        this.mStatus = i3;
        this.mShouldVibrate = z;
    }

    public long getDurationMillis() {
        return this.mDurationMillis;
    }

    public long getOriginalDurationMillis() {
        return this.mOriginalDurationMillis;
    }

    public long getStartTimeMillis() {
        return this.mStartTimeMillis;
    }

    public long getBaseTimeMillis() {
        return this.mBaseTimeMillis;
    }

    public long getBaseTimeMillisInElapsedRealtime() {
        return this.mBaseTimeMillisInElapsedRealtime;
    }

    public int getBootCount() {
        return this.mBootCount;
    }

    public long getRemainingDurationMillis() {
        return this.mRemainingDurationMillis;
    }

    public String getRingtone() {
        return this.mRingtone;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public boolean shouldVibrate() {
        return this.mShouldVibrate;
    }

    public long calculateBaseTimeMillis(Context context) {
        int currentBootCount = BootCountUtil.getCurrentBootCount(context);
        if (currentBootCount == -1 || currentBootCount != this.mBootCount) {
            return this.mBaseTimeMillis;
        }
        return System.currentTimeMillis() - (SystemClock.elapsedRealtime() - this.mBaseTimeMillisInElapsedRealtime);
    }

    public long calculateExpirationTimeMillis(Context context) {
        int i = this.mStatus;
        if (i == 2 || i == 5) {
            return Long.MAX_VALUE;
        }
        return calculateBaseTimeMillis(context) + this.mRemainingDurationMillis;
    }

    public long calculateCurrentRemainingDurationMillis(Context context) {
        int i = this.mStatus;
        if (i == 2 || i == 5) {
            return this.mRemainingDurationMillis;
        }
        return this.mRemainingDurationMillis - (System.currentTimeMillis() - calculateBaseTimeMillis(context));
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

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl, androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Timer build() {
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

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setBaseTimeMillis(long j, long j2, int i) {
            return super.setBaseTimeMillis(j, j2, i);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setBaseTimeMillis(Context context, long j, long j2) {
            return super.setBaseTimeMillis(context, j, j2);
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

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setDurationMillis(long j) {
            return super.setDurationMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setImage(String str) {
            return super.setImage(str);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setName(String str) {
            return super.setName(str);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setOriginalDurationMillis(long j) {
            return super.setOriginalDurationMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setPotentialActions(List list) {
            return super.setPotentialActions(list);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setRemainingDurationMillis(long j) {
            return super.setRemainingDurationMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setRingtone(String str) {
            return super.setRingtone(str);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setShouldVibrate(boolean z) {
            return super.setShouldVibrate(z);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setStartTimeMillis(long j) {
            return super.setStartTimeMillis(j);
        }

        @Override // androidx.appsearch.builtintypes.Timer.BuilderImpl
        public /* bridge */ /* synthetic */ BuilderImpl setStatus(int i) {
            return super.setStatus(i);
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public /* bridge */ /* synthetic */ Thing.BuilderImpl setUrl(String str) {
            return super.setUrl(str);
        }

        public Builder(String str, String str2) {
            super(str, str2);
        }

        public Builder(Timer timer) {
            super(timer);
        }
    }

    static class BuilderImpl<T extends BuilderImpl<T>> extends Thing.BuilderImpl<T> {
        protected long mBaseTimeMillis;
        protected long mBaseTimeMillisInElapsedRealtime;
        protected int mBootCount;
        protected long mDurationMillis;
        protected long mOriginalDurationMillis;
        protected long mRemainingDurationMillis;
        protected String mRingtone;
        protected boolean mShouldVibrate;
        protected long mStartTimeMillis;
        protected int mStatus;

        BuilderImpl(String str, String str2) {
            super(str, str2);
        }

        BuilderImpl(Timer timer) {
            super(new Thing.Builder(timer).build());
            this.mDurationMillis = timer.getDurationMillis();
            this.mOriginalDurationMillis = timer.getOriginalDurationMillis();
            this.mStartTimeMillis = timer.getStartTimeMillis();
            this.mBaseTimeMillis = timer.getBaseTimeMillis();
            this.mBaseTimeMillisInElapsedRealtime = timer.getBaseTimeMillisInElapsedRealtime();
            this.mBootCount = timer.getBootCount();
            this.mRemainingDurationMillis = timer.getRemainingDurationMillis();
            this.mRingtone = timer.getRingtone();
            this.mStatus = timer.getStatus();
            this.mShouldVibrate = timer.shouldVibrate();
        }

        public T setDurationMillis(long j) {
            this.mDurationMillis = j;
            return this;
        }

        public T setOriginalDurationMillis(long j) {
            this.mOriginalDurationMillis = j;
            return this;
        }

        public T setStartTimeMillis(long j) {
            this.mStartTimeMillis = j;
            return this;
        }

        public T setBaseTimeMillis(long j, long j2, int i) {
            this.mBaseTimeMillis = j;
            this.mBaseTimeMillisInElapsedRealtime = j2;
            this.mBootCount = i;
            return this;
        }

        public T setBaseTimeMillis(Context context, long j, long j2) {
            return (T) setBaseTimeMillis(j, j2, BootCountUtil.getCurrentBootCount(context));
        }

        public T setRemainingDurationMillis(long j) {
            this.mRemainingDurationMillis = j;
            return this;
        }

        public T setRingtone(String str) {
            this.mRingtone = str;
            return this;
        }

        public T setStatus(int i) {
            this.mStatus = i;
            return this;
        }

        public T setShouldVibrate(boolean z) {
            this.mShouldVibrate = z;
            return this;
        }

        @Override // androidx.appsearch.builtintypes.Thing.BuilderImpl
        public Timer build() {
            return new Timer(this.mNamespace, this.mId, this.mDocumentScore, this.mCreationTimestampMillis, this.mDocumentTtlMillis, this.mName, this.mAlternateNames, this.mDescription, this.mImage, this.mUrl, this.mPotentialActions, this.mDurationMillis, this.mOriginalDurationMillis, this.mStartTimeMillis, this.mBaseTimeMillis, this.mBaseTimeMillisInElapsedRealtime, this.mBootCount, this.mRemainingDurationMillis, this.mRingtone, this.mStatus, this.mShouldVibrate);
        }
    }
}
