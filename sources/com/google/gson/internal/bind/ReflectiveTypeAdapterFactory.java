package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.GsonTypes;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.ReflectionAccessFilterHelper;
import com.google.gson.internal.TroubleshootingGuide;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final List<ReflectionAccessFilter> reflectionFilters;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory, List<ReflectionAccessFilter> list) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        this.reflectionFilters = list;
    }

    private boolean includeField(Field field, boolean z) {
        return !this.excluder.excludeField(field, z);
    }

    private List<String> getFieldNames(Field field) {
        String strTranslateName;
        List<String> listAlternateNames;
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            strTranslateName = this.fieldNamingPolicy.translateName(field);
            listAlternateNames = this.fieldNamingPolicy.alternateNames(field);
        } else {
            String strValue = serializedName.value();
            List<String> listAsList = Arrays.asList(serializedName.alternate());
            strTranslateName = strValue;
            listAlternateNames = listAsList;
        }
        if (listAlternateNames.isEmpty()) {
            return Collections.singletonList(strTranslateName);
        }
        ArrayList arrayList = new ArrayList(listAlternateNames.size() + 1);
        arrayList.add(strTranslateName);
        arrayList.addAll(listAlternateNames);
        return arrayList;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        if (ReflectionHelper.isAnonymousOrNonStaticLocal(rawType)) {
            return new TypeAdapter<T>() { // from class: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.1
                @Override // com.google.gson.TypeAdapter
                /* JADX INFO: renamed from: read */
                public T read2(JsonReader jsonReader) throws IOException {
                    jsonReader.skipValue();
                    return null;
                }

                @Override // com.google.gson.TypeAdapter
                public void write(JsonWriter jsonWriter, T t) throws IOException {
                    jsonWriter.nullValue();
                }

                public String toString() {
                    return "AnonymousOrNonStaticLocalClassAdapter";
                }
            };
        }
        ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
        if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
            throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + rawType + ". Register a TypeAdapter for this type or adjust the access filter.");
        }
        boolean z = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
        if (ReflectionHelper.isRecord(rawType)) {
            return new RecordAdapter(rawType, getBoundFields(gson, typeToken, rawType, z, true), z);
        }
        return new FieldReflectionAdapter(this.constructorConstructor.get(typeToken, true), getBoundFields(gson, typeToken, rawType, z, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <M extends AccessibleObject & Member> void checkAccessible(Object obj, M m) {
        if (Modifier.isStatic(m.getModifiers())) {
            obj = null;
        }
        if (ReflectionAccessFilterHelper.canAccess(m, obj)) {
            return;
        }
        throw new JsonIOException(ReflectionHelper.getAccessibleObjectDescription(m, true) + " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type.");
    }

    private BoundField createBoundField(Gson gson, Field field, final Method method, String str, TypeToken<?> typeToken, boolean z, final boolean z2) {
        final TypeAdapter<?> typeAdapterRuntimeTypeWrapper;
        final boolean zIsPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        int modifiers = field.getModifiers();
        final boolean z3 = true;
        if (!Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
            z3 = false;
        }
        JsonAdapter jsonAdapter = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> typeAdapter = jsonAdapter != null ? this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter, false) : null;
        boolean z4 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        final TypeAdapter<?> typeAdapter2 = typeAdapter;
        if (z) {
            typeAdapterRuntimeTypeWrapper = z4 ? typeAdapter2 : new TypeAdapterRuntimeTypeWrapper<>(gson, typeAdapter2, typeToken.getType());
        } else {
            typeAdapterRuntimeTypeWrapper = typeAdapter2;
        }
        return new BoundField(str, field) { // from class: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.2
            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void write(JsonWriter jsonWriter, Object obj) throws IllegalAccessException, IOException {
                Object objInvoke;
                if (z2) {
                    Method method2 = method;
                    if (method2 == null) {
                        ReflectiveTypeAdapterFactory.checkAccessible(obj, this.field);
                    } else {
                        ReflectiveTypeAdapterFactory.checkAccessible(obj, method2);
                    }
                }
                Method method3 = method;
                if (method3 != null) {
                    try {
                        objInvoke = method3.invoke(obj, new Object[0]);
                    } catch (InvocationTargetException e) {
                        throw new JsonIOException("Accessor " + ReflectionHelper.getAccessibleObjectDescription(method, false) + " threw exception", e.getCause());
                    }
                } else {
                    objInvoke = this.field.get(obj);
                }
                if (objInvoke == obj) {
                    return;
                }
                jsonWriter.name(this.serializedName);
                typeAdapterRuntimeTypeWrapper.write(jsonWriter, objInvoke);
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void readIntoArray(JsonReader jsonReader, int i, Object[] objArr) throws JsonParseException, IOException {
                Object obj = typeAdapter2.read2(jsonReader);
                if (obj == null && zIsPrimitive) {
                    throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + jsonReader.getPath());
                }
                objArr[i] = obj;
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void readIntoField(JsonReader jsonReader, Object obj) throws IllegalAccessException, IOException {
                Object obj2 = typeAdapter2.read2(jsonReader);
                if (obj2 == null && zIsPrimitive) {
                    return;
                }
                if (z2) {
                    ReflectiveTypeAdapterFactory.checkAccessible(obj, this.field);
                } else if (z3) {
                    throw new JsonIOException("Cannot set value of 'static final' " + ReflectionHelper.getAccessibleObjectDescription(this.field, false));
                }
                this.field.set(obj, obj2);
            }
        };
    }

    private static class FieldsData {
        public static final FieldsData EMPTY = new FieldsData(Collections.emptyMap(), Collections.emptyList());
        public final Map<String, BoundField> deserializedFields;
        public final List<BoundField> serializedFields;

        public FieldsData(Map<String, BoundField> map, List<BoundField> list) {
            this.deserializedFields = map;
            this.serializedFields = list;
        }
    }

    private static IllegalArgumentException createDuplicateFieldException(Class<?> cls, String str, Field field, Field field2) {
        throw new IllegalArgumentException("Class " + cls.getName() + " declares multiple JSON fields named '" + str + "'; conflict is caused by fields " + ReflectionHelper.fieldToString(field) + " and " + ReflectionHelper.fieldToString(field2) + "\nSee " + TroubleshootingGuide.createUrl("duplicate-fields"));
    }

    private FieldsData getBoundFields(Gson gson, TypeToken<?> typeToken, Class<?> cls, boolean z, boolean z2) {
        boolean z3;
        TypeToken<?> typeToken2;
        boolean z4;
        int i;
        int i2;
        BoundField boundField;
        if (cls.isInterface()) {
            return FieldsData.EMPTY;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        TypeToken<?> typeToken3 = typeToken;
        boolean z5 = z;
        Class<?> rawType = cls;
        while (rawType != Object.class) {
            Field[] declaredFields = rawType.getDeclaredFields();
            boolean z6 = true;
            boolean z7 = false;
            if (rawType != cls && declaredFields.length > 0) {
                ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
                if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
                    throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + rawType + " (supertype of " + cls + "). Register a TypeAdapter for this type or adjust the access filter.");
                }
                z5 = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            }
            boolean z8 = z5;
            int length = declaredFields.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = i3;
                Field field = declaredFields[i4];
                boolean zIncludeField = this.includeField(field, z6);
                boolean zIncludeField2 = this.includeField(field, z7);
                if (zIncludeField || zIncludeField2) {
                    Method accessor = null;
                    if (!z2) {
                        z3 = zIncludeField2;
                    } else if (Modifier.isStatic(field.getModifiers())) {
                        z3 = z7;
                    } else {
                        accessor = ReflectionHelper.getAccessor(rawType, field);
                        if (!z8) {
                            ReflectionHelper.makeAccessible(accessor);
                        }
                        if (accessor.getAnnotation(SerializedName.class) != null && field.getAnnotation(SerializedName.class) == null) {
                            throw new JsonIOException("@SerializedName on " + ReflectionHelper.getAccessibleObjectDescription(accessor, z7) + " is not supported");
                        }
                        z3 = zIncludeField2;
                    }
                    if (!z8 && accessor == null) {
                        ReflectionHelper.makeAccessible(field);
                    }
                    Type typeResolve = GsonTypes.resolve(typeToken3.getType(), rawType, field.getGenericType());
                    List<String> fieldNames = this.getFieldNames(field);
                    typeToken2 = typeToken3;
                    z4 = false;
                    String str = fieldNames.get(0);
                    i = i4;
                    i2 = length;
                    BoundField boundFieldCreateBoundField = this.createBoundField(gson, field, accessor, str, TypeToken.get(typeResolve), zIncludeField, z8);
                    if (z3) {
                        for (String str2 : fieldNames) {
                            BoundField boundField2 = (BoundField) linkedHashMap.put(str2, boundFieldCreateBoundField);
                            if (boundField2 != null) {
                                throw createDuplicateFieldException(cls, str2, boundField2.field, field);
                            }
                        }
                    }
                    if (zIncludeField && (boundField = (BoundField) linkedHashMap2.put(str, boundFieldCreateBoundField)) != null) {
                        throw createDuplicateFieldException(cls, str, boundField.field, field);
                    }
                } else {
                    i2 = length;
                    i = i4;
                    typeToken2 = typeToken3;
                    z4 = z7;
                }
                i3 = i + 1;
                this = this;
                z7 = z4;
                typeToken3 = typeToken2;
                length = i2;
                z6 = true;
            }
            typeToken3 = TypeToken.get(GsonTypes.resolve(typeToken3.getType(), rawType, rawType.getGenericSuperclass()));
            rawType = typeToken3.getRawType();
            z5 = z8;
        }
        return new FieldsData(linkedHashMap, new ArrayList(linkedHashMap2.values()));
    }

    static abstract class BoundField {
        final Field field;
        final String fieldName;
        final String serializedName;

        abstract void readIntoArray(JsonReader jsonReader, int i, Object[] objArr) throws JsonParseException, IOException;

        abstract void readIntoField(JsonReader jsonReader, Object obj) throws IllegalAccessException, IOException;

        abstract void write(JsonWriter jsonWriter, Object obj) throws IllegalAccessException, IOException;

        protected BoundField(String str, Field field) {
            this.serializedName = str;
            this.field = field;
            this.fieldName = field.getName();
        }
    }

    public static abstract class Adapter<T, A> extends TypeAdapter<T> {
        private final FieldsData fieldsData;

        abstract A createAccumulator();

        abstract T finalize(A a);

        abstract void readField(A a, JsonReader jsonReader, BoundField boundField) throws IllegalAccessException, IOException;

        Adapter(FieldsData fieldsData) {
            this.fieldsData = fieldsData;
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                Iterator<BoundField> it = this.fieldsData.serializedFields.iterator();
                while (it.hasNext()) {
                    it.next().write(jsonWriter, t);
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            }
        }

        @Override // com.google.gson.TypeAdapter
        /* JADX INFO: renamed from: read */
        public T read2(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            A aCreateAccumulator = createAccumulator();
            Map<String, BoundField> map = this.fieldsData.deserializedFields;
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    BoundField boundField = map.get(jsonReader.nextName());
                    if (boundField == null) {
                        jsonReader.skipValue();
                    } else {
                        readField(aCreateAccumulator, jsonReader, boundField);
                    }
                }
                jsonReader.endObject();
                return finalize(aCreateAccumulator);
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            } catch (IllegalStateException e2) {
                throw new JsonSyntaxException(e2);
            }
        }
    }

    private static final class FieldReflectionAdapter<T> extends Adapter<T, T> {
        private final ObjectConstructor<T> constructor;

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        T finalize(T t) {
            return t;
        }

        FieldReflectionAdapter(ObjectConstructor<T> objectConstructor, FieldsData fieldsData) {
            super(fieldsData);
            this.constructor = objectConstructor;
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        T createAccumulator() {
            return this.constructor.construct();
        }

        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        void readField(T t, JsonReader jsonReader, BoundField boundField) throws IllegalAccessException, IOException {
            boundField.readIntoField(jsonReader, t);
        }
    }

    private static final class RecordAdapter<T> extends Adapter<T, Object[]> {
        static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = primitiveDefaults();
        private final Map<String, Integer> componentIndices;
        private final Constructor<T> constructor;
        private final Object[] constructorArgsDefaults;

        RecordAdapter(Class<T> cls, FieldsData fieldsData, boolean z) {
            super(fieldsData);
            this.componentIndices = new HashMap();
            Constructor<T> canonicalRecordConstructor = ReflectionHelper.getCanonicalRecordConstructor(cls);
            this.constructor = canonicalRecordConstructor;
            if (z) {
                ReflectiveTypeAdapterFactory.checkAccessible(null, canonicalRecordConstructor);
            } else {
                ReflectionHelper.makeAccessible(canonicalRecordConstructor);
            }
            String[] recordComponentNames = ReflectionHelper.getRecordComponentNames(cls);
            for (int i = 0; i < recordComponentNames.length; i++) {
                this.componentIndices.put(recordComponentNames[i], Integer.valueOf(i));
            }
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            this.constructorArgsDefaults = new Object[parameterTypes.length];
            for (int i2 = 0; i2 < parameterTypes.length; i2++) {
                this.constructorArgsDefaults[i2] = PRIMITIVE_DEFAULTS.get(parameterTypes[i2]);
            }
        }

        private static Map<Class<?>, Object> primitiveDefaults() {
            HashMap map = new HashMap();
            map.put(Byte.TYPE, (byte) 0);
            map.put(Short.TYPE, (short) 0);
            map.put(Integer.TYPE, 0);
            map.put(Long.TYPE, 0L);
            map.put(Float.TYPE, Float.valueOf(0.0f));
            map.put(Double.TYPE, Double.valueOf(0.0d));
            map.put(Character.TYPE, (char) 0);
            map.put(Boolean.TYPE, false);
            return map;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public Object[] createAccumulator() {
            return (Object[]) this.constructorArgsDefaults.clone();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public void readField(Object[] objArr, JsonReader jsonReader, BoundField boundField) throws IOException {
            Integer num = this.componentIndices.get(boundField.fieldName);
            if (num == null) {
                throw new IllegalStateException("Could not find the index in the constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' for field with name '" + boundField.fieldName + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
            }
            boundField.readIntoArray(jsonReader, num.intValue(), objArr);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T finalize(Object[] objArr) {
            try {
                return this.constructor.newInstance(objArr);
            } catch (IllegalAccessException e) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
            } catch (IllegalArgumentException | InstantiationException e2) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e3.getCause());
            }
        }
    }
}
