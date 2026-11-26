package com.google.i18n.phonenumbers.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes2.dex */
public class RegexCache {
    private LRUCache<String, Pattern> cache;

    public RegexCache(int i) {
        this.cache = new LRUCache<>(i);
    }

    public Pattern getPatternForRegex(String str) {
        Pattern pattern = this.cache.get(str);
        if (pattern != null) {
            return pattern;
        }
        Pattern patternCompile = Pattern.compile(str);
        this.cache.put(str, patternCompile);
        return patternCompile;
    }

    boolean containsRegex(String str) {
        return this.cache.containsKey(str);
    }

    private static class LRUCache<K, V> {
        private LinkedHashMap<K, V> map;
        private int size;

        public LRUCache(int i) {
            this.size = i;
            this.map = new LinkedHashMap<K, V>(((i * 4) / 3) + 1, 0.75f, true) { // from class: com.google.i18n.phonenumbers.internal.RegexCache.LRUCache.1
                @Override // java.util.LinkedHashMap
                protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
                    return size() > LRUCache.this.size;
                }
            };
        }

        public synchronized V get(K k) {
            return this.map.get(k);
        }

        public synchronized void put(K k, V v) {
            this.map.put(k, v);
        }

        public synchronized boolean containsKey(K k) {
            return this.map.containsKey(k);
        }
    }
}
