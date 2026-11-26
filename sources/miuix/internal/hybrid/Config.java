package miuix.internal.hybrid;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes5.dex */
public class Config {
    private String content;
    private Security security;
    private String vendor;
    private Map<String, Feature> features = new HashMap();
    private Map<String, String> preferences = new HashMap();
    private Map<String, Permission> permissions = new HashMap();

    public Security getSecurity() {
        return this.security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String str) {
        this.vendor = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public Map<String, Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(Map<String, Feature> map) {
        this.features = map;
    }

    public void clearFeatures() {
        this.features.clear();
    }

    public Feature getFeature(String str) {
        return this.features.get(str);
    }

    public void addFeature(Feature feature) {
        this.features.put(feature.getName(), feature);
    }

    public Map<String, String> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Map<String, String> map) {
        this.preferences = map;
    }

    public void clearPreferences() {
        this.preferences.clear();
    }

    public String getPreference(String str) {
        return this.preferences.get(str);
    }

    public void setPreference(String str, String str2) {
        this.preferences.put(str, str2);
    }

    public Map<String, Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Map<String, Permission> map) {
        this.permissions = map;
    }

    public void clearPermissions() {
        this.permissions.clear();
    }

    public Permission getPermission(String str) {
        return this.permissions.get(str);
    }

    public void addPermission(Permission permission) {
        this.permissions.put(permission.getUri(), permission);
    }
}
