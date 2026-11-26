package androidx.appsearch.app;

/* JADX INFO: loaded from: classes.dex */
public interface LongSerializer<T> {
    T deserialize(long j);

    long serialize(T t);
}
