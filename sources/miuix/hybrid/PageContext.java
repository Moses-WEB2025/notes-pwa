package miuix.hybrid;

/* JADX INFO: loaded from: classes5.dex */
public class PageContext {
    private String id;
    private String url;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public int hashCode() {
        String str = this.id;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            String str = this.id;
            String str2 = ((PageContext) obj).id;
            if (str == str2) {
                return true;
            }
            if (str != null && str2 != null && str.equals(str2)) {
                return true;
            }
        }
        return false;
    }
}
