package com.google.android.appfunctions.schema.devicestate.v1;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
@Retention(RetentionPolicy.SOURCE)
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\b\u0002\b\u0087\u0002\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002B\u0000¨\u0006\u0003"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/Sensitivity;", "", "Companion", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
public @interface Sensitivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.zza;
    public static final String MUST_PROVIDE_UNDO = "MUST_PROVIDE_UNDO";
    public static final String REQUIRES_CONFIRMATION = "REQUIRES_CONFIRMATION";

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/google/android/appfunctions/schema/devicestate/v1/Sensitivity$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "MUST_PROVIDE_UNDO", "", "REQUIRES_CONFIRMATION", "java.com.google.android.libraries.llm.appfunctions.appfunctions_schema2.src.com.google.android.appfunctions.schema.devicestate.v1_v1"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public static final String MUST_PROVIDE_UNDO = "MUST_PROVIDE_UNDO";
        public static final String REQUIRES_CONFIRMATION = "REQUIRES_CONFIRMATION";
        static final /* synthetic */ Companion zza = new Companion();

        private Companion() {
        }
    }
}
