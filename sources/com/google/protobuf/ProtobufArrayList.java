package com.google.protobuf;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
final class ProtobufArrayList<E> extends AbstractProtobufList<E> {
    private static final ProtobufArrayList<Object> EMPTY_LIST;
    private final List<E> list;

    static {
        ProtobufArrayList<Object> protobufArrayList = new ProtobufArrayList<>(new ArrayList(0));
        EMPTY_LIST = protobufArrayList;
        protobufArrayList.makeImmutable();
    }

    public static <E> ProtobufArrayList<E> emptyList() {
        return (ProtobufArrayList<E>) EMPTY_LIST;
    }

    ProtobufArrayList() {
        this(new ArrayList(10));
    }

    private ProtobufArrayList(List<E> list) {
        this.list = list;
    }

    @Override // com.google.protobuf.Internal.ProtobufList, com.google.protobuf.Internal.BooleanList
    public ProtobufArrayList<E> mutableCopyWithCapacity(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.list);
        return new ProtobufArrayList<>(arrayList);
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        ensureIsMutable();
        this.list.add(i, e);
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        return this.list.get(i);
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public E remove(int i) {
        ensureIsMutable();
        E eRemove = this.list.remove(i);
        this.modCount++;
        return eRemove;
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        ensureIsMutable();
        E e2 = this.list.set(i, e);
        this.modCount++;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.list.size();
    }
}
