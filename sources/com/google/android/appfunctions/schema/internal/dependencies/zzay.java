package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzas;
import com.google.android.appfunctions.schema.internal.dependencies.zzay;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzay<MessageType extends zzay<MessageType, BuilderType>, BuilderType extends zzas<MessageType, BuilderType>> extends zzc<MessageType, BuilderType> {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Class<?>, zzay<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected zzdf unknownFields = zzdf.zza();

    private static <MessageType extends zzau<MessageType, BuilderType>, BuilderType, T> zzaw<MessageType, T> checkIsLite(zzag<MessageType, T> zzagVar) {
        return (zzaw) zzagVar;
    }

    private static <T extends zzay<T, ?>> T checkMessageInitialized(T t) throws zzbl {
        if (t == null || t.isInitialized()) {
            return t;
        }
        throw t.newUninitializedMessageException().zza();
    }

    private int computeSerializedSize(zzct<?> zzctVar) {
        if (zzctVar != null) {
            return zzctVar.zze(this);
        }
        return zzcp.zza().zzb(getClass()).zze(this);
    }

    protected static zzba emptyBooleanList() {
        return zzi.zzd();
    }

    protected static zzbb emptyDoubleList() {
        return zzaf.zzd();
    }

    protected static zzbf emptyFloatList() {
        return zzaq.zzd();
    }

    protected static zzbg emptyIntList() {
        return zzaz.zzd();
    }

    protected static zzbh emptyLongList() {
        return zzbu.zzd();
    }

    protected static <E> zzbi<E> emptyProtobufList() {
        return zzcq.zzd();
    }

    private void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == zzdf.zza()) {
            this.unknownFields = zzdf.zzb();
        }
    }

    static <T extends zzay> T getDefaultInstance(Class<T> cls) {
        zzay<?, ?> zzayVar = defaultInstanceMap.get(cls);
        if (zzayVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzayVar = defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzayVar != null) {
            return zzayVar;
        }
        zzay<?, ?> defaultInstanceForType = ((zzay) zzdm.zzc(cls)).getDefaultInstanceForType();
        if (defaultInstanceForType == null) {
            throw new IllegalStateException();
        }
        defaultInstanceMap.put(cls, defaultInstanceForType);
        return defaultInstanceForType;
    }

    static Method getMethodOrDie(Class cls, String str, Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            String name = cls.getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 43 + String.valueOf(str).length() + 2);
            sb.append("Generated message class \"");
            sb.append(name);
            sb.append("\" missing method \"");
            sb.append(str);
            sb.append("\".");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    static Object invokeOrDie(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static zzba mutableCopy(zzba zzbaVar) {
        int size = zzbaVar.size();
        return zzbaVar.zzh(size + size);
    }

    protected static Object newMessageInfo(zzcf zzcfVar, String str, Object[] objArr) {
        return new zzcr(zzcfVar, str, objArr);
    }

    public static <ContainingType extends zzcf, Type> zzaw<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingtype, zzcf zzcfVar, zzbd zzbdVar, int i, zzdt zzdtVar, boolean z, Class cls) {
        return new zzaw<>(containingtype, zzcq.zzd(), zzcfVar, new zzav(zzbdVar, i, zzdtVar, true, z), cls);
    }

    public static <ContainingType extends zzcf, Type> zzaw<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingtype, Type type, zzcf zzcfVar, zzbd zzbdVar, int i, zzdt zzdtVar, Class cls) {
        return new zzaw<>(containingtype, type, zzcfVar, new zzav(zzbdVar, i, zzdtVar, false, false), cls);
    }

    protected static <T extends zzay<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream) throws zzbl {
        T t2 = (T) parsePartialDelimitedFrom(t, inputStream, zzaj.zza());
        checkMessageInitialized(t2);
        return t2;
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, zzt zztVar) throws zzbl {
        T t2 = (T) parseFrom(t, zztVar, zzaj.zza());
        checkMessageInitialized(t2);
        return t2;
    }

    private static <T extends zzay<T, ?>> T parsePartialDelimitedFrom(T t, InputStream inputStream, zzaj zzajVar) throws zzbl {
        try {
            int i = inputStream.read();
            if (i == -1) {
                return null;
            }
            zzx zzxVarZzF = zzx.zzF(new zza(inputStream, zzx.zzM(i, inputStream)), 4096);
            T t2 = (T) parsePartialFrom(t, zzxVarZzF, zzajVar);
            zzxVarZzF.zzb(0);
            return t2;
        } catch (zzbl e) {
            if (e.zzb()) {
                throw new zzbl(e);
            }
            throw e;
        } catch (IOException e2) {
            throw new zzbl(e2);
        }
    }

    private static <T extends zzay<T, ?>> T parsePartialFrom(T t, zzt zztVar, zzaj zzajVar) throws zzbl {
        zzx zzxVarZzj = zztVar.zzj();
        T t2 = (T) parsePartialFrom(t, zzxVarZzj, zzajVar);
        zzxVarZzj.zzb(0);
        return t2;
    }

    protected static <T extends zzay> void registerDefaultInstance(Class<T> cls, T t) {
        t.markImmutable();
        defaultInstanceMap.put(cls, t);
    }

    Object buildMessageInfo() throws Exception {
        return dynamicMethod(zzax.zzc, null, null);
    }

    void clearMemoizedHashCode() {
        this.memoizedHashCode = 0;
    }

    void clearMemoizedSerializedSize() {
        setMemoizedSerializedSize(Integer.MAX_VALUE);
    }

    int computeHashCode() {
        return zzcp.zza().zzb(getClass()).zzc(this);
    }

    protected final <MessageType2 extends zzay<MessageType2, BuilderType2>, BuilderType2 extends zzas<MessageType2, BuilderType2>> BuilderType2 createBuilder() {
        return (BuilderType2) dynamicMethod(zzax.zze, null, null);
    }

    protected abstract Object dynamicMethod(zzax zzaxVar, Object obj, Object obj2);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return zzcp.zza().zzb(getClass()).zzb(this, (zzay) obj);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcg
    public final MessageType getDefaultInstanceForType() {
        return (MessageType) dynamicMethod(zzax.zzf, null, null);
    }

    int getMemoizedHashCode() {
        return this.memoizedHashCode;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzc
    int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    public final zzcn<MessageType> getParserForType() {
        return (zzcn) dynamicMethod(zzax.zzg, null, null);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public int getSerializedSize() {
        return getSerializedSize(null);
    }

    public int hashCode() {
        if (isMutable()) {
            return computeHashCode();
        }
        if (hashCodeIsNotMemoized()) {
            setMemoizedHashCode(computeHashCode());
        }
        return getMemoizedHashCode();
    }

    boolean hashCodeIsNotMemoized() {
        return getMemoizedHashCode() == 0;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcg
    public final boolean isInitialized() {
        return isInitialized(this, true);
    }

    boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    protected void makeImmutable() {
        zzcp.zza().zzb(getClass()).zzk(this);
        markImmutable();
    }

    void markImmutable() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    protected void mergeLengthDelimitedField(int i, zzt zztVar) {
        ensureUnknownFieldsInitialized();
        zzdf zzdfVar = this.unknownFields;
        zzdfVar.zze();
        if (i == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        zzdfVar.zzk((i << 3) | 2, zztVar);
    }

    protected final void mergeUnknownFields(zzdf zzdfVar) {
        this.unknownFields = zzdf.zzc(this.unknownFields, zzdfVar);
    }

    protected void mergeVarintField(int i, int i2) {
        ensureUnknownFieldsInitialized();
        zzdf zzdfVar = this.unknownFields;
        zzdfVar.zze();
        if (i == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        zzdfVar.zzk(i << 3, Long.valueOf(i2));
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public final BuilderType newBuilderForType() {
        return (BuilderType) dynamicMethod(zzax.zze, null, null);
    }

    MessageType newMutableInstance() {
        return (MessageType) dynamicMethod(zzax.zzd, null, null);
    }

    protected boolean parseUnknownField(int i, zzx zzxVar) throws IOException {
        if ((i & 7) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.zzl(i, zzxVar);
    }

    void setMemoizedHashCode(int i) {
        this.memoizedHashCode = i;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzc
    void setMemoizedSerializedSize(int i) {
        if (i >= 0) {
            this.memoizedSerializedSize = i | (this.memoizedSerializedSize & Integer.MIN_VALUE);
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 42);
        sb.append("serialized size must be non-negative, was ");
        sb.append(i);
        throw new IllegalStateException(sb.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public final BuilderType toBuilder() {
        return (BuilderType) ((zzas) dynamicMethod(zzax.zze, null, null)).mergeFrom(this);
    }

    public String toString() {
        return zzch.zza(this, super.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public void writeTo(zzad zzadVar) throws IOException {
        zzcp.zza().zzb(getClass()).zzf(this, zzae.zza(zzadVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T extends zzay<T, ?>> boolean isInitialized(T t, boolean z) {
        byte bByteValue = ((Byte) t.dynamicMethod(zzax.zza, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzl = zzcp.zza().zzb(t.getClass()).zzl(t);
        if (z) {
            t.dynamicMethod(zzax.zzb, true != zZzl ? null : t, null);
        }
        return zZzl;
    }

    protected final <MessageType2 extends zzay<MessageType2, BuilderType2>, BuilderType2 extends zzas<MessageType2, BuilderType2>> BuilderType2 createBuilder(MessageType2 messagetype2) {
        return (BuilderType2) createBuilder().mergeFrom(messagetype2);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzc
    int getSerializedSize(zzct zzctVar) {
        if (!isMutable()) {
            if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
                return getMemoizedSerializedSize();
            }
            int iComputeSerializedSize = computeSerializedSize(zzctVar);
            setMemoizedSerializedSize(iComputeSerializedSize);
            return iComputeSerializedSize;
        }
        int iComputeSerializedSize2 = computeSerializedSize(zzctVar);
        if (iComputeSerializedSize2 >= 0) {
            return iComputeSerializedSize2;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(iComputeSerializedSize2).length() + 42);
        sb.append("serialized size must be non-negative, was ");
        sb.append(iComputeSerializedSize2);
        throw new IllegalStateException(sb.toString());
    }

    protected static zzbb mutableCopy(zzbb zzbbVar) {
        int size = zzbbVar.size();
        return zzbbVar.zzh(size + size);
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, zzt zztVar, zzaj zzajVar) throws zzbl {
        T t2 = (T) parsePartialFrom(t, zztVar, zzajVar);
        checkMessageInitialized(t2);
        return t2;
    }

    protected static <T extends zzay<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream, zzaj zzajVar) throws zzbl {
        T t2 = (T) parsePartialDelimitedFrom(t, inputStream, zzajVar);
        checkMessageInitialized(t2);
        return t2;
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, zzx zzxVar) throws zzbl {
        return (T) parseFrom(t, zzxVar, zzaj.zza());
    }

    protected static zzbf mutableCopy(zzbf zzbfVar) {
        int size = zzbfVar.size();
        return zzbfVar.zzh(size + size);
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, zzx zzxVar, zzaj zzajVar) throws zzbl {
        T t2 = (T) parsePartialFrom(t, zzxVar, zzajVar);
        checkMessageInitialized(t2);
        return t2;
    }

    protected static <T extends zzay<T, ?>> T parsePartialFrom(T t, zzx zzxVar) throws zzbl {
        return (T) parsePartialFrom(t, zzxVar, zzaj.zza());
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, InputStream inputStream) throws zzbl {
        T t2 = (T) parsePartialFrom(t, zzx.zzF(inputStream, 4096), zzaj.zza());
        checkMessageInitialized(t2);
        return t2;
    }

    static <T extends zzay<T, ?>> T parsePartialFrom(T t, zzx zzxVar, zzaj zzajVar) throws zzbl {
        T t2 = (T) t.newMutableInstance();
        try {
            zzct zzctVarZzb = zzcp.zza().zzb(t2.getClass());
            zzctVarZzb.zzg(t2, zzy.zza(zzxVar), zzajVar);
            zzctVarZzb.zzk(t2);
            return t2;
        } catch (zzbl e) {
            if (e.zzb()) {
                throw new zzbl(e);
            }
            throw e;
        } catch (zzdd e2) {
            throw e2.zza();
        } catch (IOException e3) {
            if (e3.getCause() instanceof zzbl) {
                throw ((zzbl) e3.getCause());
            }
            throw new zzbl(e3);
        } catch (RuntimeException e4) {
            if (e4.getCause() instanceof zzbl) {
                throw ((zzbl) e4.getCause());
            }
            throw e4;
        }
    }

    protected static zzbg mutableCopy(zzbg zzbgVar) {
        int size = zzbgVar.size();
        return zzbgVar.zzh(size + size);
    }

    protected static zzbh mutableCopy(zzbh zzbhVar) {
        int size = zzbhVar.size();
        return zzbhVar.zzh(size + size);
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, InputStream inputStream, zzaj zzajVar) throws zzbl {
        T t2 = (T) parsePartialFrom(t, zzx.zzF(inputStream, 4096), zzajVar);
        checkMessageInitialized(t2);
        return t2;
    }

    protected static <E> zzbi<E> mutableCopy(zzbi<E> zzbiVar) {
        int size = zzbiVar.size();
        return zzbiVar.zzh(size + size);
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer) throws zzbl {
        return (T) parseFrom(t, byteBuffer, zzaj.zza());
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0035  */
    protected static <T extends zzay<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer, zzaj zzajVar) throws zzbl {
        zzx zzxVarZzG;
        int i = zzx.zzf;
        boolean z = false;
        if (byteBuffer.hasArray()) {
            zzxVarZzG = zzx.zzG(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining(), false);
        } else if (byteBuffer.isDirect()) {
            int i2 = zzw.zza;
            if (!zzdm.zzb()) {
                int iRemaining = byteBuffer.remaining();
                byte[] bArr = new byte[iRemaining];
                byteBuffer.duplicate().get(bArr);
                zzxVarZzG = zzx.zzG(bArr, 0, iRemaining, true);
            } else {
                zzxVarZzG = new zzw(byteBuffer, z, null);
            }
        } else {
            int iRemaining2 = byteBuffer.remaining();
            byte[] bArr2 = new byte[iRemaining2];
            byteBuffer.duplicate().get(bArr2);
            zzxVarZzG = zzx.zzG(bArr2, 0, iRemaining2, true);
        }
        T t2 = (T) parseFrom(t, zzxVarZzG, zzajVar);
        checkMessageInitialized(t2);
        return t2;
    }

    private static <T extends zzay<T, ?>> T parsePartialFrom(T t, byte[] bArr, int i, int i2, zzaj zzajVar) throws zzbl {
        if (i2 == 0) {
            return t;
        }
        T t2 = (T) t.newMutableInstance();
        try {
            zzct zzctVarZzb = zzcp.zza().zzb(t2.getClass());
            zzctVarZzb.zzj(t2, bArr, i, i + i2, new zzg(zzajVar));
            zzctVarZzb.zzk(t2);
            return t2;
        } catch (zzbl e) {
            if (e.zzb()) {
                throw new zzbl(e);
            }
            throw e;
        } catch (zzdd e2) {
            throw e2.zza();
        } catch (IOException e3) {
            if (e3.getCause() instanceof zzbl) {
                throw ((zzbl) e3.getCause());
            }
            throw new zzbl(e3);
        } catch (IndexOutOfBoundsException unused) {
            throw new zzbl("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, byte[] bArr) throws zzbl {
        T t2 = (T) parsePartialFrom(t, bArr, 0, bArr.length, zzaj.zza());
        checkMessageInitialized(t2);
        return t2;
    }

    protected static <T extends zzay<T, ?>> T parseFrom(T t, byte[] bArr, zzaj zzajVar) throws zzbl {
        T t2 = (T) parsePartialFrom(t, bArr, 0, bArr.length, zzajVar);
        checkMessageInitialized(t2);
        return t2;
    }
}
