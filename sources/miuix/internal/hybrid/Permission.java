package miuix.internal.hybrid;

/* JADX INFO: loaded from: classes5.dex */
public class Permission {
    private boolean applySubdomains;
    private boolean forbidden;
    private String uri;

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public boolean isApplySubdomains() {
        return this.applySubdomains;
    }

    public void setApplySubdomains(boolean z) {
        this.applySubdomains = z;
    }

    public boolean isForbidden() {
        return this.forbidden;
    }

    public void setForbidden(boolean z) {
        this.forbidden = z;
    }
}
