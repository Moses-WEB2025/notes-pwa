package androidx.appsearch.app;

/* JADX INFO: loaded from: classes.dex */
public abstract class Migrator {
    public abstract GenericDocument onDowngrade(int i, int i2, GenericDocument genericDocument);

    public abstract GenericDocument onUpgrade(int i, int i2, GenericDocument genericDocument);

    public abstract boolean shouldMigrate(int i, int i2);
}
