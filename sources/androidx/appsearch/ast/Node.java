package androidx.appsearch.ast;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface Node {
    default List<Node> getChildren() {
        return Collections.emptyList();
    }
}
