package androidx.lifecycle.viewmodel.internal;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.ViewModel;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JvmViewModelProviders.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J#\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\b¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Landroidx/lifecycle/viewmodel/internal/JvmViewModelProviders;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "createViewModel", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class JvmViewModelProviders {
    public static final JvmViewModelProviders INSTANCE = new JvmViewModelProviders();

    private JvmViewModelProviders() {
    }

    public final <T extends ViewModel> T createViewModel(Class<T> modelClass) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(modelClass, "modelClass");
        try {
            Constructor<T> declaredConstructor = modelClass.getDeclaredConstructor(new Class[0]);
            if (!Modifier.isPublic(declaredConstructor.getModifiers())) {
                throw new RuntimeException("Cannot create an instance of " + modelClass);
            }
            try {
                T tNewInstance = declaredConstructor.newInstance(new Object[0]);
                Intrinsics.checkNotNull(tNewInstance);
                return tNewInstance;
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e2) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e2);
            }
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e3);
        }
    }
}
