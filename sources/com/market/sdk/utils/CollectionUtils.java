package com.market.sdk.utils;

import android.os.Bundle;
import android.util.LongSparseArray;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: classes2.dex */
public class CollectionUtils {
    public static Bundle convertMapToStringBundle(Map<String, String> map) {
        Bundle bundle = new Bundle();
        if (map != null) {
            for (String str : map.keySet()) {
                bundle.putString(str, map.get(str));
            }
        }
        return bundle;
    }

    public static Map<String, String> convertBundleToStringMap(Bundle bundle) {
        ConcurrentHashMap concurrentHashMapNewConconrrentHashMap = newConconrrentHashMap();
        for (String str : bundle.keySet()) {
            concurrentHashMapNewConconrrentHashMap.put(str, bundle.getString(str));
        }
        return concurrentHashMapNewConconrrentHashMap;
    }

    public static <T, K> HashMap<T, K> newHashMap() {
        return new HashMap<>();
    }

    public static <T, K> ConcurrentHashMap<T, K> newConconrrentHashMap() {
        return new ConcurrentHashMap<>();
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> SparseArray<T> newSparseArray() {
        return new SparseArray<>();
    }

    public static <T> LongSparseArray<T> newLongSparseArray() {
        return new LongSparseArray<>();
    }

    public static <T> CopyOnWriteArraySet<T> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    public static <T> ConcurrentLinkedQueue<T> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    public static <T> HashSet<T> newHashSet() {
        return new HashSet<>();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
