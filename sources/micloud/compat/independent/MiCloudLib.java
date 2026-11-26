package micloud.compat.independent;

import com.xiaomi.micloudsdk.request.utils.RequestContext;
import micloud.compat.independent.request.BindAccountServiceCompat;
import micloud.compat.independent.request.BindAccountServiceCompatInternal;
import micloud.compat.independent.request.NetworkAvailabilityManagerCompat;
import micloud.compat.independent.request.NetworkAvailabilityManagerCompatInternal;
import micloud.compat.independent.request.RequestEnvBuilderCompat;
import micloud.compat.independent.request.RequestEnvBuilderCompatInternal;
import micloud.compat.independent.request.RequestInjectorCompactInternal;
import micloud.compat.independent.request.RequestInjectorCompat;
import micloud.compat.independent.sync.GdprUtilsCompat;
import micloud.compat.independent.sync.GdprUtilsCompatInternal;

/* JADX INFO: loaded from: classes5.dex */
public class MiCloudLib {
    public static void init() {
        BindAccountServiceCompat.setBindAccountServiceImpl(new BindAccountServiceCompatInternal());
        NetworkAvailabilityManagerCompat.setNetworkAvailabilityManagerCompatImpl(new NetworkAvailabilityManagerCompatInternal());
        RequestEnvBuilderCompat.setRequestEnvBuilderCompatImpl(new RequestEnvBuilderCompatInternal());
        RequestContext.setRequestEnv(RequestEnvBuilderCompat.build());
        RequestInjectorCompat.setRequestInjectorCompat(new RequestInjectorCompactInternal());
        GdprUtilsCompat.setGdprUtilsCompatImpl(new GdprUtilsCompatInternal());
    }
}
