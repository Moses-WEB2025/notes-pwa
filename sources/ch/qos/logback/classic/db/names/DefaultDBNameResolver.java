package ch.qos.logback.classic.db.names;

import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class DefaultDBNameResolver implements DBNameResolver {
    @Override // ch.qos.logback.classic.db.names.DBNameResolver
    public <N extends Enum<?>> String getColumnName(N n) {
        return n.toString().toLowerCase(Locale.US);
    }

    @Override // ch.qos.logback.classic.db.names.DBNameResolver
    public <N extends Enum<?>> String getTableName(N n) {
        return n.toString().toLowerCase(Locale.US);
    }
}
