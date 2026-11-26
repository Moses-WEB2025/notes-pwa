package androidx.appsearch.builtintypes.properties;

import androidx.core.util.Preconditions;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class Keyword {
    final String mAsText;
    String mNamespace = "";
    String mId = "";

    public Keyword(String str) {
        this.mAsText = (String) Preconditions.checkNotNull(str);
    }

    public String asText() {
        return this.mAsText;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mAsText, ((Keyword) obj).mAsText);
    }

    public int hashCode() {
        return Objects.hashCode(this.mAsText);
    }
}
