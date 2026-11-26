package androidx.test.internal.platform;

import android.os.StrictMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/* JADX INFO: loaded from: classes.dex */
public final class ServiceLoaderWrapper {

    public interface Factory<T> {
        T create();
    }

    private ServiceLoaderWrapper() {
    }

    public static <T> List<T> loadService(Class<T> serviceClass) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        ArrayList arrayList = new ArrayList();
        Iterator it = ServiceLoader.load(serviceClass).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        return arrayList;
    }

    public static <T> T loadSingleService(Class<T> cls, Factory<T> factory) {
        T t = (T) loadSingleServiceOrNull(cls);
        return t == null ? factory.create() : t;
    }

    public static <T> T loadSingleServiceOrNull(Class<T> cls) {
        List listFilter = filter(loadService(cls));
        String str = null;
        if (listFilter.isEmpty()) {
            return null;
        }
        if (listFilter.size() == 1) {
            return (T) listFilter.get(0);
        }
        Iterator it = listFilter.iterator();
        while (it.hasNext()) {
            str = (str != null ? str + ", " : ": ") + it.next().getClass().getName();
        }
        throw new IllegalStateException("Found more than one implementation for " + cls.getName() + str);
    }

    private static <T> List<T> filter(List<T> services) {
        HashSet hashSet = new HashSet();
        Iterator<T> it = services.iterator();
        while (it.hasNext()) {
            Supersedes supersedes = (Supersedes) it.next().getClass().getAnnotation(Supersedes.class);
            if (supersedes != null) {
                hashSet.add(supersedes.value());
            }
        }
        if (hashSet.isEmpty()) {
            return services;
        }
        ArrayList arrayList = new ArrayList();
        for (T t : services) {
            if (!hashSet.contains(t.getClass())) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }
}
