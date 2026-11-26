package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzb;
import com.google.android.appfunctions.schema.internal.dependencies.zzc;
import defpackage.Hello;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzb<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> implements zzce {
    @Deprecated
    protected static <T> void addAll(Iterable<T> iterable, Collection<? super T> collection) {
        addAll((Iterable) iterable, (List) collection);
    }

    private static <T> void addAllCheckingNulls(Iterable<T> iterable, List<? super T> list) {
        if (iterable instanceof Collection) {
            int size = ((Collection) iterable).size();
            if (list instanceof ArrayList) {
                ((ArrayList) list).ensureCapacity(list.size() + size);
            } else if (list instanceof zzcq) {
                ((zzcq) list).zze(list.size() + size);
            }
        }
        int size2 = list.size();
        if (!(iterable instanceof List) || !(iterable instanceof RandomAccess)) {
            for (Object obj : iterable) {
                if (obj == null) {
                    resetListAndThrow(list, size2);
                }
                list.add(obj);
            }
            return;
        }
        List list2 = (List) iterable;
        int size3 = list2.size();
        for (int i = 0; i < size3; i++) {
            Hello hello = (Object) list2.get(i);
            if (hello == null) {
                resetListAndThrow(list, size2);
            }
            list.add(hello);
        }
    }

    private String getReadingExceptionMessage(String str) {
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 16 + String.valueOf(str).length() + 44);
        sb.append("Reading ");
        sb.append(name);
        sb.append(" from a ");
        sb.append(str);
        sb.append(" threw an IOException (should never happen).");
        return sb.toString();
    }

    protected static zzdd newUninitializedMessageException(zzcf zzcfVar) {
        return new zzdd(zzcfVar);
    }

    private static void resetListAndThrow(List<?> list, int i) {
        int size = list.size() - i;
        StringBuilder sb = new StringBuilder(String.valueOf(size).length() + 26);
        sb.append("Element at index ");
        sb.append(size);
        sb.append(" is null.");
        String string = sb.toString();
        int size2 = list.size();
        while (true) {
            size2--;
            if (size2 < i) {
                throw new NullPointerException(string);
            }
            list.remove(size2);
        }
    }

    @Override // 
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType mo353clone();

    protected abstract BuilderType internalMergeFrom(MessageType messagetype);

    public boolean mergeDelimitedFrom(InputStream inputStream) throws IOException {
        return mergeDelimitedFrom(inputStream, zzaj.zza());
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m356mergeFrom(zzt zztVar) throws zzbl {
        try {
            zzx zzxVarZzj = zztVar.zzj();
            m358mergeFrom(zzxVarZzj);
            zzxVarZzj.zzb(0);
            return this;
        } catch (zzbl e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(getReadingExceptionMessage("ByteString"), e2);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public abstract BuilderType mergeFrom(zzx zzxVar, zzaj zzajVar) throws IOException;

    protected static <T> void addAll(Iterable<T> iterable, List<? super T> list) {
        byte[] bArr = zzbj.zzb;
        iterable.getClass();
        if (!(iterable instanceof zzbr)) {
            if (iterable instanceof zzco) {
                list.addAll((Collection) iterable);
                return;
            } else {
                addAllCheckingNulls(iterable, list);
                return;
            }
        }
        List listZza = ((zzbr) iterable).zza();
        zzbr zzbrVar = (zzbr) list;
        int size = list.size();
        for (Object obj : listZza) {
            if (obj == null) {
                int size2 = zzbrVar.size() - size;
                StringBuilder sb = new StringBuilder(String.valueOf(size2).length() + 26);
                sb.append("Element at index ");
                sb.append(size2);
                sb.append(" is null.");
                String string = sb.toString();
                int size3 = zzbrVar.size();
                while (true) {
                    size3--;
                    if (size3 < size) {
                        throw new NullPointerException(string);
                    }
                    zzbrVar.remove(size3);
                }
            } else if (obj instanceof zzt) {
                zzbrVar.zzb();
            } else if (obj instanceof byte[]) {
                byte[] bArr2 = (byte[]) obj;
                zzt.zzm(bArr2, 0, bArr2.length);
                zzbrVar.zzb();
            } else {
                zzbrVar.add((String) obj);
            }
        }
    }

    public boolean mergeDelimitedFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
        int i = inputStream.read();
        if (i == -1) {
            return false;
        }
        m360mergeFrom((InputStream) new zza(inputStream, zzx.zzM(i, inputStream)), zzajVar);
        return true;
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m357mergeFrom(zzt zztVar, zzaj zzajVar) throws zzbl {
        try {
            zzx zzxVarZzj = zztVar.zzj();
            mergeFrom(zzxVarZzj, zzajVar);
            zzxVarZzj.zzb(0);
            return this;
        } catch (zzbl e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(getReadingExceptionMessage("ByteString"), e2);
        }
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m358mergeFrom(zzx zzxVar) throws IOException {
        return (BuilderType) mergeFrom(zzxVar, zzaj.zza());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public BuilderType mergeFrom(zzcf zzcfVar) {
        if (!getDefaultInstanceForType().getClass().isInstance(zzcfVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return (BuilderType) internalMergeFrom((zzc) zzcfVar);
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m359mergeFrom(InputStream inputStream) throws IOException {
        zzx zzxVarZzF = zzx.zzF(inputStream, 4096);
        m358mergeFrom(zzxVarZzF);
        zzxVarZzF.zzb(0);
        return this;
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m360mergeFrom(InputStream inputStream, zzaj zzajVar) throws IOException {
        zzx zzxVarZzF = zzx.zzF(inputStream, 4096);
        mergeFrom(zzxVarZzF, zzajVar);
        zzxVarZzF.zzb(0);
        return this;
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m361mergeFrom(byte[] bArr) throws zzbl {
        return (BuilderType) mo354mergeFrom(bArr, 0, bArr.length);
    }

    @Override // 
    /* JADX INFO: renamed from: mergeFrom */
    public BuilderType mo354mergeFrom(byte[] bArr, int i, int i2) throws zzbl {
        try {
            zzx zzxVarZzG = zzx.zzG(bArr, i, i2, false);
            m358mergeFrom(zzxVarZzG);
            zzxVarZzG.zzb(0);
            return this;
        } catch (zzbl e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(getReadingExceptionMessage("byte array"), e2);
        }
    }

    @Override // 
    /* JADX INFO: renamed from: mergeFrom */
    public BuilderType mo355mergeFrom(byte[] bArr, int i, int i2, zzaj zzajVar) throws zzbl {
        try {
            zzx zzxVarZzG = zzx.zzG(bArr, i, i2, false);
            mergeFrom(zzxVarZzG, zzajVar);
            zzxVarZzG.zzb(0);
            return this;
        } catch (zzbl e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(getReadingExceptionMessage("byte array"), e2);
        }
    }

    /* JADX INFO: renamed from: mergeFrom, reason: merged with bridge method [inline-methods] */
    public BuilderType m362mergeFrom(byte[] bArr, zzaj zzajVar) throws zzbl {
        return (BuilderType) mo355mergeFrom(bArr, 0, bArr.length, zzajVar);
    }
}
