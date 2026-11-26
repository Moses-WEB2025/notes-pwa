package miuix.search.recommendation;

/* JADX INFO: loaded from: classes6.dex */
public interface SimpleList {
    void add(int i, CharSequence charSequence);

    void clear();

    CharSequence get(int i);

    void remove(int i);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }
}
