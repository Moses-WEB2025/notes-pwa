package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzas;
import com.google.android.appfunctions.schema.internal.dependencies.zzay;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public class zzas<MessageType extends zzay<MessageType, BuilderType>, BuilderType extends zzas<MessageType, BuilderType>> extends zzb<MessageType, BuilderType> {
    private final MessageType defaultInstance;
    protected MessageType instance;

    protected zzas(MessageType messagetype) {
        this.defaultInstance = messagetype;
        if (messagetype.isMutable()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.instance = (MessageType) newMutableInstance();
    }

    private static <MessageType> void mergeFromInstance(MessageType messagetype, MessageType messagetype2) {
        zzcp.zza().zzb(messagetype.getClass()).zzd(messagetype, messagetype2);
    }

    private MessageType newMutableInstance() {
        return (MessageType) this.defaultInstance.newMutableInstance();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public final MessageType build() {
        MessageType messagetype = (MessageType) buildPartial();
        if (messagetype.isInitialized()) {
            return messagetype;
        }
        throw newUninitializedMessageException(messagetype);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public MessageType buildPartial() {
        if (!this.instance.isMutable()) {
            return this.instance;
        }
        this.instance.makeImmutable();
        return this.instance;
    }

    public final BuilderType clear() {
        if (this.defaultInstance.isMutable()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.instance = (MessageType) newMutableInstance();
        return this;
    }

    protected final void copyOnWrite() {
        if (this.instance.isMutable()) {
            return;
        }
        copyOnWriteInternal();
    }

    protected void copyOnWriteInternal() {
        MessageType messagetype = (MessageType) newMutableInstance();
        mergeFromInstance(messagetype, this.instance);
        this.instance = messagetype;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcg
    public MessageType getDefaultInstanceForType() {
        return this.defaultInstance;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcg
    public final boolean isInitialized() {
        return zzay.isInitialized(this.instance, false);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzb
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public BuilderType mo353clone() {
        BuilderType buildertype = (BuilderType) getDefaultInstanceForType().newBuilderForType();
        buildertype.instance = (MessageType) buildPartial();
        return buildertype;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzb
    public BuilderType internalMergeFrom(MessageType messagetype) {
        return (BuilderType) mergeFrom((zzay) messagetype);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzb, com.google.android.appfunctions.schema.internal.dependencies.zzce
    public BuilderType mergeFrom(zzx zzxVar, zzaj zzajVar) throws IOException {
        copyOnWrite();
        try {
            zzcp.zza().zzb(this.instance.getClass()).zzg(this.instance, zzy.zza(zzxVar), zzajVar);
            return this;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                throw ((IOException) e.getCause());
            }
            throw e;
        }
    }

    /* JADX INFO: renamed from: clear, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ zzce m351clear() {
        clear();
        return this;
    }

    public BuilderType mergeFrom(MessageType messagetype) {
        if (getDefaultInstanceForType().equals(messagetype)) {
            return this;
        }
        copyOnWrite();
        mergeFromInstance(this.instance, messagetype);
        return this;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzb
    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType mo354mergeFrom(byte[] bArr, int i, int i2) throws zzbl {
        return (BuilderType) mo355mergeFrom(bArr, i, i2, zzaj.zza());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzb
    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType mo355mergeFrom(byte[] bArr, int i, int i2, zzaj zzajVar) throws zzbl {
        copyOnWrite();
        try {
            zzcp.zza().zzb(this.instance.getClass()).zzj(this.instance, bArr, i, i + i2, new zzg(zzajVar));
            return this;
        } catch (zzbl e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
        } catch (IndexOutOfBoundsException unused) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
    }
}
