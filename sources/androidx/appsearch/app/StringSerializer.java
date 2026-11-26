package androidx.appsearch.app;

/* JADX INFO: loaded from: classes.dex */
public interface StringSerializer<T> {
    T deserialize(String str);

    String serialize(T t);
}
