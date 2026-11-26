package miuix.internal.hybrid;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes5.dex */
public class Feature {
    private String name;
    private Map<String, String> params = new HashMap();

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> map) {
        this.params = map;
    }

    public void clearParams() {
        this.params.clear();
    }

    public String getParam(String str) {
        return this.params.get(str);
    }

    public void setParam(String str, String str2) {
        this.params.put(str, str2);
    }
}
