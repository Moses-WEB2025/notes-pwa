package org.apache.commons.lang3.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableConsumer;

/* JADX INFO: loaded from: classes6.dex */
public class EventListenerSupport<L> implements Serializable {
    private static final long serialVersionUID = 3593265990380473632L;
    private List<L> listeners;
    private transient L[] prototypeArray;
    private transient L proxy;

    protected class ProxyInvocationHandler implements InvocationHandler {
        private final FailableConsumer<Throwable, IllegalAccessException> handler;

        public ProxyInvocationHandler(EventListenerSupport eventListenerSupport) {
            this(new FailableConsumer() { // from class: org.apache.commons.lang3.event.EventListenerSupport$ProxyInvocationHandler$$ExternalSyntheticLambda0
                @Override // org.apache.commons.lang3.function.FailableConsumer
                public final void accept(Object obj) {
                    ExceptionUtils.rethrow((Throwable) obj);
                }
            });
        }

        public ProxyInvocationHandler(FailableConsumer<Throwable, IllegalAccessException> failableConsumer) {
            this.handler = (FailableConsumer) Objects.requireNonNull(failableConsumer);
        }

        protected void handle(Throwable th) throws Throwable {
            this.handler.accept(th);
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Iterator it = EventListenerSupport.this.listeners.iterator();
            while (it.hasNext()) {
                try {
                    method.invoke(it.next(), objArr);
                } catch (Throwable th) {
                    handle(th);
                }
            }
            return null;
        }
    }

    public static <T> EventListenerSupport<T> create(Class<T> cls) {
        return new EventListenerSupport<>(cls);
    }

    private EventListenerSupport() {
        this.listeners = new CopyOnWriteArrayList();
    }

    public EventListenerSupport(Class<L> cls) {
        this(cls, Thread.currentThread().getContextClassLoader());
    }

    public EventListenerSupport(Class<L> cls, ClassLoader classLoader) {
        this();
        Objects.requireNonNull(cls, "listenerInterface");
        Objects.requireNonNull(classLoader, "classLoader");
        Validate.isTrue(cls.isInterface(), "Class %s is not an interface", cls.getName());
        initializeTransientFields(cls, classLoader);
    }

    public void addListener(L l) {
        addListener(l, true);
    }

    public void addListener(L l, boolean z) {
        Objects.requireNonNull(l, "listener");
        if (z || !this.listeners.contains(l)) {
            this.listeners.add(l);
        }
    }

    protected InvocationHandler createInvocationHandler() {
        return new ProxyInvocationHandler(this);
    }

    private void createProxy(Class<L> cls, ClassLoader classLoader) {
        this.proxy = cls.cast(Proxy.newProxyInstance(classLoader, new Class[]{cls}, createInvocationHandler()));
    }

    public L fire() {
        return this.proxy;
    }

    int getListenerCount() {
        return this.listeners.size();
    }

    public L[] getListeners() {
        return (L[]) this.listeners.toArray(this.prototypeArray);
    }

    private void initializeTransientFields(Class<L> cls, ClassLoader classLoader) {
        this.prototypeArray = (L[]) ArrayUtils.newInstance(cls, 0);
        createProxy(cls, classLoader);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Object[] objArr = (Object[]) objectInputStream.readObject();
        this.listeners = new CopyOnWriteArrayList(objArr);
        initializeTransientFields(ArrayUtils.getComponentType(objArr), Thread.currentThread().getContextClassLoader());
    }

    public void removeListener(L l) {
        this.listeners.remove(Objects.requireNonNull(l, "listener"));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ArrayList arrayList = new ArrayList();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
        for (L l : this.listeners) {
            try {
                objectOutputStream2.writeObject(l);
                arrayList.add(l);
            } catch (IOException unused) {
                objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
            }
        }
        objectOutputStream.writeObject(arrayList.toArray(this.prototypeArray));
    }
}
