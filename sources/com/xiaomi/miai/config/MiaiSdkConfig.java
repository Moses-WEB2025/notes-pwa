package com.xiaomi.miai.config;

import ch.qos.logback.core.CoreConstants;
import com.xiaomi.miai.log.Logger;
import com.xiaomi.miai.utils.Requires;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class MiaiSdkConfig {
    private final Map<Key<?>, Object> configs;
    private final Logger logger;

    public static class Key<T> {
        private final String name;
        private final Type type;

        public Key(String str, Type type) {
            this.type = type;
            Requires.notNull(str, "name cannot be null.");
            this.name = str;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return "Key{name='" + this.name + "', type=" + this.type + CoreConstants.CURLY_RIGHT;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Key key = (Key) obj;
                if (Objects.equals(this.name, key.name) && Objects.equals(this.type, key.type)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.name, this.type);
        }
    }

    public MiaiSdkConfig() {
        this.logger = Logger.getLogger("MiaiSdkConfig");
        this.configs = new HashMap();
    }

    private MiaiSdkConfig(Map<Key<?>, Object> map) {
        this.logger = Logger.getLogger("MiaiSdkConfig");
        this.configs = new HashMap(map);
    }

    public <T> MiaiSdkConfig put(Key<T> key, T t) {
        Requires.notNull(key, "Add config failed. key is null.");
        Requires.notNull(t, "Add config failed. value is null.");
        if (this.configs.put(key, t) != null) {
            this.logger.info("The config for key {} exists already, replaced by the new value {}.", key, t);
        }
        return this;
    }

    public <T> T get(Key<T> key) {
        Requires.isTrue(this.configs.containsKey(key), "Config for key %s doesn't exist, please set it.", key);
        return (T) this.configs.get(key);
    }

    public <T> T getOrElse(Key<T> key, T t) {
        T t2 = (T) this.configs.get(key);
        return t2 == null ? t : t2;
    }

    public <T> T getOrNull(Key<T> key) {
        return (T) this.configs.get(key);
    }

    public <T> boolean containsKey(Key<T> key) {
        return this.configs.containsKey(key);
    }

    public MiaiSdkConfig copy() {
        return new MiaiSdkConfig(this.configs);
    }
}
