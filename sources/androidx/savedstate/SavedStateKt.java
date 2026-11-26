package androidx.savedstate;

import android.os.Bundle;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"androidx/savedstate/SavedStateKt__SavedStateKt", "androidx/savedstate/SavedStateKt__SavedState_androidKt"}, k = 4, mv = {2, 0, 0}, xi = 48)
public final class SavedStateKt {
    public static final <T> T read(Bundle bundle, Function1<? super SavedStateReader, ? extends T> function1) {
        return (T) SavedStateKt__SavedStateKt.read(bundle, function1);
    }

    public static final Bundle savedState(Bundle bundle, Function1<? super SavedStateWriter, Unit> function1) {
        return SavedStateKt__SavedState_androidKt.savedState(bundle, function1);
    }

    public static final Bundle savedState(Map<String, ? extends Object> map, Function1<? super SavedStateWriter, Unit> function1) {
        return SavedStateKt__SavedState_androidKt.savedState(map, function1);
    }

    public static final <T> T write(Bundle bundle, Function1<? super SavedStateWriter, ? extends T> function1) {
        return (T) SavedStateKt__SavedStateKt.write(bundle, function1);
    }
}
