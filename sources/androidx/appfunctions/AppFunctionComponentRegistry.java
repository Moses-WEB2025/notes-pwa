package androidx.appfunctions;

import androidx.appfunctions.metadata.AppFunctionMetadataKt;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;

/* JADX INFO: compiled from: AppFunctionComponentRegistry.kt */
/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0087\u0002\u0018\u00002\u00020\u0001B&\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0007R\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\bR\u0015\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\t"}, d2 = {"Landroidx/appfunctions/AppFunctionComponentRegistry;", "", "componentCategory", "", "componentNames", "", "componentDocStrings", "()Ljava/lang/String;", "()[Ljava/lang/String;", AppFunctionMetadataKt.APP_FUNCTION_NAMESPACE}, k = 1, mv = {2, 1, 0}, xi = 48)
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS})
@Retention(RetentionPolicy.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
public @interface AppFunctionComponentRegistry {
    String componentCategory();

    String[] componentDocStrings() default {};

    String[] componentNames();
}
