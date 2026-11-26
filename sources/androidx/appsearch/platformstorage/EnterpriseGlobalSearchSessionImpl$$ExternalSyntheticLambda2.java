package androidx.appsearch.platformstorage;

import android.app.appsearch.GetSchemaResponse;
import androidx.appsearch.platformstorage.converter.GetSchemaResponseToPlatformConverter;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class EnterpriseGlobalSearchSessionImpl$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return GetSchemaResponseToPlatformConverter.toJetpackGetSchemaResponse((GetSchemaResponse) obj);
    }
}
