package kotlinx.serialization;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Target({})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.PROPERTY})
@Retention(RetentionPolicy.RUNTIME)
@MustBeDocumented
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\u0005B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0006"}, d2 = {"Lkotlinx/serialization/EncodeDefault;", "", "mode", "Lkotlinx/serialization/EncodeDefault$Mode;", "()Lkotlinx/serialization/EncodeDefault$Mode;", "Mode", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
@ExperimentalSerializationApi
@Documented
public @interface EncodeDefault {

    /* JADX INFO: compiled from: Annotations.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lkotlinx/serialization/EncodeDefault$Mode;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "ALWAYS", "NEVER", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    @ExperimentalSerializationApi
    public enum Mode {
        ALWAYS,
        NEVER;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Mode> getEntries() {
            return $ENTRIES;
        }
    }

    Mode mode() default Mode.ALWAYS;
}
