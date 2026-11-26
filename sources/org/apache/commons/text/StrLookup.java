package org.apache.commons.text;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import org.apache.commons.text.lookup.StringLookup;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public abstract class StrLookup<V> implements StringLookup {
    private static final StrLookup<String> NONE_LOOKUP = new MapStrLookup(null);
    private static final StrLookup<String> SYSTEM_PROPERTIES_LOOKUP = new SystemPropertiesStrLookup();

    private static final class MapStrLookup<V> extends StrLookup<V> {
        private final Map<String, V> map;

        private MapStrLookup(Map<String, V> map) {
            this.map = map == null ? Collections.emptyMap() : map;
        }

        @Override // org.apache.commons.text.lookup.StringLookup
        public String lookup(String str) {
            return Objects.toString(this.map.get(str), null);
        }

        public String toString() {
            return super.toString() + " [map=" + this.map + "]";
        }
    }

    private static final class ResourceBundleLookup extends StrLookup<String> {
        private final ResourceBundle resourceBundle;

        private ResourceBundleLookup(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
        }

        @Override // org.apache.commons.text.lookup.StringLookup
        public String lookup(String str) {
            ResourceBundle resourceBundle = this.resourceBundle;
            if (resourceBundle == null || str == null || !resourceBundle.containsKey(str)) {
                return null;
            }
            return this.resourceBundle.getString(str);
        }

        public String toString() {
            return super.toString() + " [resourceBundle=" + this.resourceBundle + "]";
        }
    }

    private static final class SystemPropertiesStrLookup extends StrLookup<String> {
        private SystemPropertiesStrLookup() {
        }

        @Override // org.apache.commons.text.lookup.StringLookup
        public String lookup(String str) {
            if (str.isEmpty()) {
                return null;
            }
            try {
                return System.getProperty(str);
            } catch (SecurityException unused) {
                return null;
            }
        }
    }

    public static <V> StrLookup<V> mapLookup(Map<String, V> map) {
        return new MapStrLookup(map);
    }

    public static StrLookup<?> noneLookup() {
        return NONE_LOOKUP;
    }

    public static StrLookup<String> resourceBundleLookup(ResourceBundle resourceBundle) {
        return new ResourceBundleLookup(resourceBundle);
    }

    public static StrLookup<String> systemPropertiesLookup() {
        return SYSTEM_PROPERTIES_LOOKUP;
    }

    protected StrLookup() {
    }
}
