package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzb;
import com.google.android.appfunctions.schema.internal.dependencies.zzc;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> implements zzcf {
    protected int memoizedHashCode = 0;

    protected static <T> void addAll(Iterable<T> iterable, List<? super T> list) {
        zzb.addAll((Iterable) iterable, (List) list);
    }

    protected static void checkByteStringIsUtf8(zzt zztVar) throws IllegalArgumentException {
        if (!zztVar.zzg()) {
            throw new IllegalArgumentException("Byte string is not UTF-8.");
        }
    }

    private String getSerializingExceptionMessage(String str) {
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 18 + String.valueOf(str).length() + 44);
        sb.append("Serializing ");
        sb.append(name);
        sb.append(" to a ");
        sb.append(str);
        sb.append(" threw an IOException (should never happen).");
        return sb.toString();
    }

    int getMemoizedSerializedSize() {
        throw new UnsupportedOperationException();
    }

    int getSerializedSize(zzct zzctVar) {
        int memoizedSerializedSize = getMemoizedSerializedSize();
        if (memoizedSerializedSize != -1) {
            return memoizedSerializedSize;
        }
        int iZze = zzctVar.zze(this);
        setMemoizedSerializedSize(iZze);
        return iZze;
    }

    public zzck mutableCopy() {
        throw new UnsupportedOperationException("mutableCopy() is not implemented.");
    }

    zzdd newUninitializedMessageException() {
        return new zzdd(this);
    }

    void setMemoizedSerializedSize(int i) {
        throw new UnsupportedOperationException();
    }

    public byte[] toByteArray() {
        try {
            int serializedSize = getSerializedSize();
            byte[] bArr = new byte[serializedSize];
            int i = zzad.zzf;
            zzaa zzaaVar = new zzaa(bArr, 0, serializedSize);
            writeTo(zzaaVar);
            zzaaVar.zzB();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException(getSerializingExceptionMessage("byte array"), e);
        }
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public zzt toByteString() {
        try {
            int serializedSize = getSerializedSize();
            zzt zztVar = zzt.zza;
            byte[] bArr = new byte[serializedSize];
            int i = zzad.zzf;
            zzaa zzaaVar = new zzaa(bArr, 0, serializedSize);
            writeTo(zzaaVar);
            zzaaVar.zzB();
            return new zzr(bArr);
        } catch (IOException e) {
            throw new RuntimeException(getSerializingExceptionMessage("ByteString"), e);
        }
    }

    public void writeDelimitedTo(OutputStream outputStream) throws IOException {
        int serializedSize = getSerializedSize();
        zzac zzacVar = new zzac(outputStream, zzad.zzw(zzad.zzx(serializedSize) + serializedSize));
        zzacVar.zzp(serializedSize);
        writeTo(zzacVar);
        zzacVar.zzv();
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        zzac zzacVar = new zzac(outputStream, zzad.zzw(getSerializedSize()));
        writeTo(zzacVar);
        zzacVar.zzv();
    }
}
