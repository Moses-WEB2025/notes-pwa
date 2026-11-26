package androidx.appsearch.builtintypes;

import androidx.appsearch.app.DocumentClassFactoryRegistry;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class GlobalSearchApplicationInfo {
    public static final int APPLICATION_TYPE_CONSUMER = 1;
    public static final int APPLICATION_TYPE_PRODUCER = 0;
    private final int mApplicationType;
    private final String mId;
    private final String mNamespace;
    private final List<String> mSchemaTypes;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationType {
    }

    GlobalSearchApplicationInfo(String str, String str2, int i, List<String> list) {
        this.mNamespace = (String) Preconditions.checkNotNull(str);
        this.mId = (String) Preconditions.checkNotNull(str2);
        this.mApplicationType = i;
        this.mSchemaTypes = Collections.unmodifiableList(list);
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public String getId() {
        return this.mId;
    }

    public int getApplicationType() {
        return this.mApplicationType;
    }

    public List<String> getSchemaTypes() {
        return this.mSchemaTypes;
    }

    public static final class Builder {
        private final int mApplicationType;
        private final String mId;
        private final String mNamespace;
        private List<String> mSchemaTypes = Collections.emptyList();

        public Builder(String str, String str2, int i) {
            this.mNamespace = (String) Preconditions.checkNotNull(str);
            this.mId = (String) Preconditions.checkNotNull(str2);
            this.mApplicationType = Preconditions.checkArgumentInRange(i, 0, 1, "applicationType");
        }

        public Builder setSchemaTypes(List<String> list) {
            Preconditions.checkNotNull(list);
            for (int i = 0; i < list.size(); i++) {
                Preconditions.checkNotNull(list.get(i));
            }
            this.mSchemaTypes = list;
            return this;
        }

        public Builder setDocumentClasses(List<Class<?>> list) throws AppSearchException {
            Preconditions.checkNotNull(list);
            DocumentClassFactoryRegistry documentClassFactoryRegistry = DocumentClassFactoryRegistry.getInstance();
            ArrayList arrayList = new ArrayList(list.size());
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(documentClassFactoryRegistry.getOrCreateFactory((Class) Preconditions.checkNotNull(list.get(i))).getSchemaName());
            }
            this.mSchemaTypes = arrayList;
            return this;
        }

        public GlobalSearchApplicationInfo build() {
            return new GlobalSearchApplicationInfo(this.mNamespace, this.mId, this.mApplicationType, this.mSchemaTypes);
        }
    }
}
