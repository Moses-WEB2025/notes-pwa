package kotlin.time;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Instant.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bs\u0018\u00002\u00020\u0001:\u0002\u0005\u0006J\b\u0010\u0002\u001a\u00020\u0003H&J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0003H&\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lkotlin/time/InstantParseResult;", "", "toInstant", "Lkotlin/time/Instant;", "toInstantOrNull", "Success", "Failure", "Lkotlin/time/InstantParseResult$Failure;", "Lkotlin/time/InstantParseResult$Success;", "kotlin-stdlib"}, k = 1, mv = {2, 2, 0}, xi = 48)
interface InstantParseResult {
    Instant toInstant();

    Instant toInstantOrNull();

    /* JADX INFO: compiled from: Instant.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lkotlin/time/InstantParseResult$Success;", "Lkotlin/time/InstantParseResult;", "epochSeconds", "", "nanosecondsOfSecond", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(JI)V", "getEpochSeconds", "()J", "getNanosecondsOfSecond", "()I", "toInstant", "Lkotlin/time/Instant;", "toInstantOrNull", "kotlin-stdlib"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Success implements InstantParseResult {
        private final long epochSeconds;
        private final int nanosecondsOfSecond;

        public Success(long j, int i) {
            this.epochSeconds = j;
            this.nanosecondsOfSecond = i;
        }

        public final long getEpochSeconds() {
            return this.epochSeconds;
        }

        public final int getNanosecondsOfSecond() {
            return this.nanosecondsOfSecond;
        }

        @Override // kotlin.time.InstantParseResult
        public Instant toInstant() {
            if (this.epochSeconds < Instant.INSTANCE.getMIN$kotlin_stdlib().getEpochSeconds() || this.epochSeconds > Instant.INSTANCE.getMAX$kotlin_stdlib().getEpochSeconds()) {
                throw new InstantFormatException("The parsed date is outside the range representable by Instant (Unix epoch second " + this.epochSeconds + CoreConstants.RIGHT_PARENTHESIS_CHAR);
            }
            return Instant.INSTANCE.fromEpochSeconds(this.epochSeconds, this.nanosecondsOfSecond);
        }

        @Override // kotlin.time.InstantParseResult
        public Instant toInstantOrNull() {
            if (this.epochSeconds < Instant.INSTANCE.getMIN$kotlin_stdlib().getEpochSeconds() || this.epochSeconds > Instant.INSTANCE.getMAX$kotlin_stdlib().getEpochSeconds()) {
                return null;
            }
            return Instant.INSTANCE.fromEpochSeconds(this.epochSeconds, this.nanosecondsOfSecond);
        }
    }

    /* JADX INFO: compiled from: Instant.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lkotlin/time/InstantParseResult$Failure;", "Lkotlin/time/InstantParseResult;", SNSAuthProvider.VALUE_SNS_ERROR, "", HtmlParser.InputElement.TAG, "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/CharSequence;)V", "getError", "()Ljava/lang/String;", "getInput", "()Ljava/lang/CharSequence;", "toInstant", "Lkotlin/time/Instant;", "toInstantOrNull", "kotlin-stdlib"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Failure implements InstantParseResult {
        private final String error;
        private final CharSequence input;

        @Override // kotlin.time.InstantParseResult
        public Instant toInstantOrNull() {
            return null;
        }

        public Failure(String error, CharSequence input) {
            Intrinsics.checkNotNullParameter(error, "error");
            Intrinsics.checkNotNullParameter(input, "input");
            this.error = error;
            this.input = input;
        }

        public final String getError() {
            return this.error;
        }

        public final CharSequence getInput() {
            return this.input;
        }

        @Override // kotlin.time.InstantParseResult
        public Instant toInstant() {
            throw new InstantFormatException(this.error + " when parsing an Instant from \"" + InstantKt.truncateForErrorMessage(this.input, 64) + '\"');
        }
    }
}
