package com.pvporbit.freetype;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes4.dex */
public class Library extends Utils.Pointer {
    public Library(long j) {
        super(j);
    }

    public boolean delete() {
        return FreeType.FT_Done_FreeType(this.pointer);
    }

    public Face newFace(String str, int i) {
        try {
            return newFace(Utils.loadFileToByteArray(str), i);
        } catch (IOException unused) {
            return null;
        }
    }

    public Face newFace(byte[] bArr, int i) {
        ByteBuffer byteBufferNewBuffer = Utils.newBuffer(bArr.length);
        byteBufferNewBuffer.order(ByteOrder.nativeOrder());
        byteBufferNewBuffer.limit(byteBufferNewBuffer.position() + bArr.length);
        Utils.fillBuffer(bArr, byteBufferNewBuffer, bArr.length);
        return newFace(byteBufferNewBuffer, i);
    }

    public Face newFace(ByteBuffer byteBuffer, int i) {
        long jFT_New_Memory_Face = FreeType.FT_New_Memory_Face(this.pointer, byteBuffer, byteBuffer.remaining(), i);
        if (jFT_New_Memory_Face == 0) {
            Utils.deleteBuffer(byteBuffer);
            return null;
        }
        return new Face(jFT_New_Memory_Face, byteBuffer);
    }

    public LibraryVersion getVersion() {
        return FreeType.FT_Library_Version(this.pointer);
    }
}
