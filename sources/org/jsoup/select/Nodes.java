package org.jsoup.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Node;

/* JADX INFO: loaded from: classes6.dex */
public class Nodes<T extends Node> extends ArrayList<T> {
    public Nodes() {
    }

    public Nodes(int i) {
        super(i);
    }

    public Nodes(Collection<T> collection) {
        super(collection);
    }

    public Nodes(List<T> list) {
        super(list);
    }

    @SafeVarargs
    public Nodes(T... tArr) {
        super(Arrays.asList(tArr));
    }

    @Override // java.util.ArrayList
    public Nodes<T> clone() {
        Nodes<T> nodes = new Nodes<>(size());
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            nodes.add(it.next().mo4587clone());
        }
        return nodes;
    }

    public ArrayList<T> asList() {
        return new ArrayList<>(this);
    }

    public Nodes<T> remove() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().remove();
        }
        return this;
    }

    public String outerHtml() {
        return (String) stream().map(new Function() { // from class: org.jsoup.select.Nodes$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Node) obj).outerHtml();
            }
        }).collect(StringUtil.joining(StringUtils.LF));
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return outerHtml();
    }

    public Nodes<T> before(String str) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().before(str);
        }
        return this;
    }

    public Nodes<T> after(String str) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().after(str);
        }
        return this;
    }

    public Nodes<T> wrap(String str) {
        Validate.notEmpty(str);
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().wrap(str);
        }
        return this;
    }

    public T first() {
        if (isEmpty()) {
            return null;
        }
        return get(0);
    }

    public T last() {
        if (isEmpty()) {
            return null;
        }
        return get(size() - 1);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T set(int i, T t) {
        Validate.notNull(t);
        T t2 = (T) super.set(i, t);
        t2.replaceWith(t);
        return t2;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T remove(int i) {
        T t = (T) super.remove(i);
        t.remove();
        return t;
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        int iIndexOf = super.indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    public T deselect(int i) {
        return (T) super.remove(i);
    }

    public boolean deselect(Object obj) {
        return super.remove(obj);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        remove();
        super.clear();
    }

    public void deselectAll() {
        super.clear();
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean retainAll(Collection<?> collection) {
        Iterator<T> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.ArrayList, java.util.Collection
    public boolean removeIf(Predicate<? super T> predicate) {
        Iterator<T> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.ArrayList, java.util.List
    public void replaceAll(UnaryOperator<T> unaryOperator) {
        for (int i = 0; i < size(); i++) {
            set(i, (Node) unaryOperator.apply(get(i)));
        }
    }
}
