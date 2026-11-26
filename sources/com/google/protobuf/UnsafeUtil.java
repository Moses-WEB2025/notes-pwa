package com.google.protobuf;

import com.xiaomi.mirror.RemoteDeviceInfo;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes2.dex */
final class UnsafeUtil {
    private static final Logger logger = Logger.getLogger(UnsafeUtil.class.getName());
    private static final Unsafe UNSAFE = getUnsafe();
    private static final MemoryAccessor MEMORY_ACCESSOR = getMemoryAccessor();
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
    private static final long BYTE_ARRAY_BASE_OFFSET = arrayBaseOffset(byte[].class);
    private static final long BOOLEAN_ARRAY_BASE_OFFSET = arrayBaseOffset(boolean[].class);
    private static final long BOOLEAN_ARRAY_INDEX_SCALE = arrayIndexScale(boolean[].class);
    private static final long INT_ARRAY_BASE_OFFSET = arrayBaseOffset(int[].class);
    private static final long INT_ARRAY_INDEX_SCALE = arrayIndexScale(int[].class);
    private static final long LONG_ARRAY_BASE_OFFSET = arrayBaseOffset(long[].class);
    private static final long LONG_ARRAY_INDEX_SCALE = arrayIndexScale(long[].class);
    private static final long FLOAT_ARRAY_BASE_OFFSET = arrayBaseOffset(float[].class);
    private static final long FLOAT_ARRAY_INDEX_SCALE = arrayIndexScale(float[].class);
    private static final long DOUBLE_ARRAY_BASE_OFFSET = arrayBaseOffset(double[].class);
    private static final long DOUBLE_ARRAY_INDEX_SCALE = arrayIndexScale(double[].class);
    private static final long OBJECT_ARRAY_BASE_OFFSET = arrayBaseOffset(Object[].class);
    private static final long OBJECT_ARRAY_INDEX_SCALE = arrayIndexScale(Object[].class);
    private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());

    private UnsafeUtil() {
    }

    static boolean hasUnsafeArrayOperations() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }

    static boolean hasUnsafeByteBufferOperations() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static <T> T allocateInstance(Class<T> cls) {
        try {
            return (T) UNSAFE.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static long objectFieldOffset(java.lang.reflect.Field field) {
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    private static int arrayBaseOffset(Class<?> cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int arrayIndexScale(Class<?> cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayIndexScale(cls);
        }
        return -1;
    }

    static byte getByte(Object obj, long j) {
        return MEMORY_ACCESSOR.getByte(obj, j);
    }

    static void putByte(Object obj, long j, byte b) {
        MEMORY_ACCESSOR.putByte(obj, j, b);
    }

    static int getInt(Object obj, long j) {
        return MEMORY_ACCESSOR.getInt(obj, j);
    }

    static void putInt(Object obj, long j, int i) {
        MEMORY_ACCESSOR.putInt(obj, j, i);
    }

    static long getLong(Object obj, long j) {
        return MEMORY_ACCESSOR.getLong(obj, j);
    }

    static void putLong(Object obj, long j, long j2) {
        MEMORY_ACCESSOR.putLong(obj, j, j2);
    }

    static boolean getBoolean(Object obj, long j) {
        return MEMORY_ACCESSOR.getBoolean(obj, j);
    }

    static void putBoolean(Object obj, long j, boolean z) {
        MEMORY_ACCESSOR.putBoolean(obj, j, z);
    }

    static float getFloat(Object obj, long j) {
        return MEMORY_ACCESSOR.getFloat(obj, j);
    }

    static void putFloat(Object obj, long j, float f) {
        MEMORY_ACCESSOR.putFloat(obj, j, f);
    }

    static double getDouble(Object obj, long j) {
        return MEMORY_ACCESSOR.getDouble(obj, j);
    }

    static void putDouble(Object obj, long j, double d) {
        MEMORY_ACCESSOR.putDouble(obj, j, d);
    }

    static Object getObject(Object obj, long j) {
        return MEMORY_ACCESSOR.getObject(obj, j);
    }

    static void putObject(Object obj, long j, Object obj2) {
        MEMORY_ACCESSOR.putObject(obj, j, obj2);
    }

    static byte getByte(byte[] bArr, long j) {
        return MEMORY_ACCESSOR.getByte(bArr, BYTE_ARRAY_BASE_OFFSET + j);
    }

    static void putByte(byte[] bArr, long j, byte b) {
        MEMORY_ACCESSOR.putByte(bArr, BYTE_ARRAY_BASE_OFFSET + j, b);
    }

    static int getInt(int[] iArr, long j) {
        return MEMORY_ACCESSOR.getInt(iArr, INT_ARRAY_BASE_OFFSET + (j * INT_ARRAY_INDEX_SCALE));
    }

    static void putInt(int[] iArr, long j, int i) {
        MEMORY_ACCESSOR.putInt(iArr, INT_ARRAY_BASE_OFFSET + (j * INT_ARRAY_INDEX_SCALE), i);
    }

    static long getLong(long[] jArr, long j) {
        return MEMORY_ACCESSOR.getLong(jArr, LONG_ARRAY_BASE_OFFSET + (j * LONG_ARRAY_INDEX_SCALE));
    }

    static void putLong(long[] jArr, long j, long j2) {
        MEMORY_ACCESSOR.putLong(jArr, LONG_ARRAY_BASE_OFFSET + (j * LONG_ARRAY_INDEX_SCALE), j2);
    }

    static boolean getBoolean(boolean[] zArr, long j) {
        return MEMORY_ACCESSOR.getBoolean(zArr, BOOLEAN_ARRAY_BASE_OFFSET + (j * BOOLEAN_ARRAY_INDEX_SCALE));
    }

    static void putBoolean(boolean[] zArr, long j, boolean z) {
        MEMORY_ACCESSOR.putBoolean(zArr, BOOLEAN_ARRAY_BASE_OFFSET + (j * BOOLEAN_ARRAY_INDEX_SCALE), z);
    }

    static float getFloat(float[] fArr, long j) {
        return MEMORY_ACCESSOR.getFloat(fArr, FLOAT_ARRAY_BASE_OFFSET + (j * FLOAT_ARRAY_INDEX_SCALE));
    }

    static void putFloat(float[] fArr, long j, float f) {
        MEMORY_ACCESSOR.putFloat(fArr, FLOAT_ARRAY_BASE_OFFSET + (j * FLOAT_ARRAY_INDEX_SCALE), f);
    }

    static double getDouble(double[] dArr, long j) {
        return MEMORY_ACCESSOR.getDouble(dArr, DOUBLE_ARRAY_BASE_OFFSET + (j * DOUBLE_ARRAY_INDEX_SCALE));
    }

    static void putDouble(double[] dArr, long j, double d) {
        MEMORY_ACCESSOR.putDouble(dArr, DOUBLE_ARRAY_BASE_OFFSET + (j * DOUBLE_ARRAY_INDEX_SCALE), d);
    }

    static Object getObject(Object[] objArr, long j) {
        return MEMORY_ACCESSOR.getObject(objArr, OBJECT_ARRAY_BASE_OFFSET + (j * OBJECT_ARRAY_INDEX_SCALE));
    }

    static void putObject(Object[] objArr, long j, Object obj) {
        MEMORY_ACCESSOR.putObject(objArr, OBJECT_ARRAY_BASE_OFFSET + (j * OBJECT_ARRAY_INDEX_SCALE), obj);
    }

    static void copyMemory(byte[] bArr, long j, long j2, long j3) {
        MEMORY_ACCESSOR.copyMemory(bArr, j, j2, j3);
    }

    static void copyMemory(long j, byte[] bArr, long j2, long j3) {
        MEMORY_ACCESSOR.copyMemory(j, bArr, j2, j3);
    }

    static void copyMemory(byte[] bArr, long j, byte[] bArr2, long j2, long j3) {
        System.arraycopy(bArr, (int) j, bArr2, (int) j2, (int) j3);
    }

    static byte getByte(long j) {
        return MEMORY_ACCESSOR.getByte(j);
    }

    static void putByte(long j, byte b) {
        MEMORY_ACCESSOR.putByte(j, b);
    }

    static int getInt(long j) {
        return MEMORY_ACCESSOR.getInt(j);
    }

    static void putInt(long j, int i) {
        MEMORY_ACCESSOR.putInt(j, i);
    }

    static long getLong(long j) {
        return MEMORY_ACCESSOR.getLong(j);
    }

    static void putLong(long j, long j2) {
        MEMORY_ACCESSOR.putLong(j, j2);
    }

    static long addressOffset(ByteBuffer byteBuffer) {
        return MEMORY_ACCESSOR.getLong(byteBuffer, BUFFER_ADDRESS_OFFSET);
    }

    static Object getStaticObject(java.lang.reflect.Field field) {
        return MEMORY_ACCESSOR.getStaticObject(field);
    }

    static Unsafe getUnsafe() {
        try {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.protobuf.UnsafeUtil.1
                @Override // java.security.PrivilegedExceptionAction
                public Unsafe run() throws Exception {
                    for (java.lang.reflect.Field field : Unsafe.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (Unsafe.class.isInstance(obj)) {
                            return (Unsafe) Unsafe.class.cast(obj);
                        }
                    }
                    return null;
                }
            });
        } catch (Throwable unused) {
            return null;
        }
    }

    private static MemoryAccessor getMemoryAccessor() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return null;
        }
        return new JvmMemoryAccessor(unsafe);
    }

    private static boolean supportsUnsafeArrayOperations() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
            return false;
        }
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        Unsafe unsafe = UNSAFE;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (bufferAddressField() == null) {
                return false;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            logger.log(Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
            return false;
        }
    }

    private static java.lang.reflect.Field bufferAddressField() {
        java.lang.reflect.Field field = field(Buffer.class, RemoteDeviceInfo.KEY_ADDRESS);
        if (field == null || field.getType() != Long.TYPE) {
            return null;
        }
        return field;
    }

    private static long fieldOffset(java.lang.reflect.Field field) {
        MemoryAccessor memoryAccessor;
        if (field == null || (memoryAccessor = MEMORY_ACCESSOR) == null) {
            return -1L;
        }
        return memoryAccessor.objectFieldOffset(field);
    }

    private static java.lang.reflect.Field field(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static abstract class MemoryAccessor {
        Unsafe unsafe;

        public abstract void copyMemory(long j, byte[] bArr, long j2, long j3);

        public abstract void copyMemory(byte[] bArr, long j, long j2, long j3);

        public abstract boolean getBoolean(Object obj, long j);

        public abstract byte getByte(long j);

        public abstract byte getByte(Object obj, long j);

        public abstract double getDouble(Object obj, long j);

        public abstract float getFloat(Object obj, long j);

        public abstract int getInt(long j);

        public abstract long getLong(long j);

        public abstract Object getStaticObject(java.lang.reflect.Field field);

        public abstract void putBoolean(Object obj, long j, boolean z);

        public abstract void putByte(long j, byte b);

        public abstract void putByte(Object obj, long j, byte b);

        public abstract void putDouble(Object obj, long j, double d);

        public abstract void putFloat(Object obj, long j, float f);

        public abstract void putInt(long j, int i);

        public abstract void putLong(long j, long j2);

        MemoryAccessor(Unsafe unsafe) {
            this.unsafe = unsafe;
        }

        public final long objectFieldOffset(java.lang.reflect.Field field) {
            return this.unsafe.objectFieldOffset(field);
        }

        public final int getInt(Object obj, long j) {
            return this.unsafe.getInt(obj, j);
        }

        public final void putInt(Object obj, long j, int i) {
            this.unsafe.putInt(obj, j, i);
        }

        public final long getLong(Object obj, long j) {
            return this.unsafe.getLong(obj, j);
        }

        public final void putLong(Object obj, long j, long j2) {
            this.unsafe.putLong(obj, j, j2);
        }

        public final Object getObject(Object obj, long j) {
            return this.unsafe.getObject(obj, j);
        }

        public final void putObject(Object obj, long j, Object obj2) {
            this.unsafe.putObject(obj, j, obj2);
        }

        public final int arrayBaseOffset(Class<?> cls) {
            return this.unsafe.arrayBaseOffset(cls);
        }

        public final int arrayIndexScale(Class<?> cls) {
            return this.unsafe.arrayIndexScale(cls);
        }
    }

    private static final class JvmMemoryAccessor extends MemoryAccessor {
        JvmMemoryAccessor(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j) {
            return this.unsafe.getByte(j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j, byte b) {
            this.unsafe.putByte(j, b);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j) {
            return this.unsafe.getInt(j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j, int i) {
            this.unsafe.putInt(j, i);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j) {
            return this.unsafe.getLong(j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j, long j2) {
            this.unsafe.putLong(j, j2);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(Object obj, long j) {
            return this.unsafe.getByte(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(Object obj, long j, byte b) {
            this.unsafe.putByte(obj, j, b);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(Object obj, long j) {
            return this.unsafe.getBoolean(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(Object obj, long j, boolean z) {
            this.unsafe.putBoolean(obj, j, z);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(Object obj, long j) {
            return this.unsafe.getFloat(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(Object obj, long j, float f) {
            this.unsafe.putFloat(obj, j, f);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(Object obj, long j) {
            return this.unsafe.getDouble(obj, j);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(Object obj, long j, double d) {
            this.unsafe.putDouble(obj, j, d);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j, byte[] bArr, long j2, long j3) {
            this.unsafe.copyMemory((Object) null, j, bArr, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + j2, j3);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j, long j2, long j3) {
            this.unsafe.copyMemory(bArr, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + j, (Object) null, j2, j3);
        }

        @Override // com.google.protobuf.UnsafeUtil.MemoryAccessor
        public Object getStaticObject(java.lang.reflect.Field field) {
            return getObject(this.unsafe.staticFieldBase(field), this.unsafe.staticFieldOffset(field));
        }
    }
}
