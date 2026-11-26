package androidx.appsearch.app;

import androidx.appsearch.exceptions.AppSearchException;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class DocumentClassFactoryRegistry {
    private static final String GEN_CLASS_PREFIX = "$$__AppSearch__";
    private static volatile DocumentClassFactoryRegistry sInstance;
    private final Map<Class<?>, DocumentClassFactory<?>> mFactories = new HashMap();

    private DocumentClassFactoryRegistry() {
    }

    public static DocumentClassFactoryRegistry getInstance() {
        if (sInstance == null) {
            synchronized (DocumentClassFactoryRegistry.class) {
                if (sInstance == null) {
                    sInstance = new DocumentClassFactoryRegistry();
                }
            }
        }
        return sInstance;
    }

    public <T> DocumentClassFactory<T> getOrCreateFactory(Class<T> cls) throws AppSearchException {
        DocumentClassFactory<T> documentClassFactory;
        Preconditions.checkNotNull(cls);
        synchronized (this) {
            documentClassFactory = (DocumentClassFactory) this.mFactories.get(cls);
        }
        if (documentClassFactory != null) {
            return documentClassFactory;
        }
        DocumentClassFactory<?> documentClassFactory2 = (DocumentClassFactory<T>) loadFactoryByReflection(cls);
        synchronized (this) {
            DocumentClassFactory<?> documentClassFactory3 = this.mFactories.get(cls);
            if (documentClassFactory3 == null) {
                this.mFactories.put((Class<?>) cls, documentClassFactory2);
            } else {
                documentClassFactory2 = (DocumentClassFactory<T>) documentClassFactory3;
            }
        }
        return (DocumentClassFactory<T>) documentClassFactory2;
    }

    public <T> DocumentClassFactory<T> getOrCreateFactory(T t) throws AppSearchException {
        Preconditions.checkNotNull(t);
        return getOrCreateFactory((Class) t.getClass());
    }

    private DocumentClassFactory<?> loadFactoryByReflection(Class<?> cls) throws AppSearchException {
        String str;
        Package r0 = cls.getPackage();
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new AppSearchException(2, "Failed to find simple name for document class \"" + cls + "\". Perhaps it is anonymous?");
        }
        if (r0 == null) {
            str = "";
        } else {
            str = r0.getName() + ".";
            canonicalName = canonicalName.substring(str.length()).replace(".", "$$__");
        }
        String str2 = str + GEN_CLASS_PREFIX + canonicalName;
        try {
            try {
                return (DocumentClassFactory) Class.forName(str2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception e) {
                throw new AppSearchException(2, "Failed to construct document class converter \"" + str2 + "\"", e);
            }
        } catch (ClassNotFoundException e2) {
            Class<? super Object> superclass = cls.getSuperclass();
            Class<?>[] interfaces = cls.getInterfaces();
            if (superclass == Object.class) {
                superclass = null;
            }
            int length = interfaces.length;
            if (superclass != null) {
                length++;
            }
            if (length == 1) {
                if (superclass != null) {
                    return loadFactoryByReflection(superclass);
                }
                return loadFactoryByReflection(interfaces[0]);
            }
            String str3 = "Failed to find document class converter \"" + str2 + "\". Perhaps the annotation processor was not run or the class was proguarded out?";
            if (length > 1) {
                str3 = str3 + " Or, this class may not have been annotated with @Document, and there is an ambiguity to determine a unique @Document annotated parent class/interface.";
            }
            throw new AppSearchException(2, str3, e2);
        }
    }
}
