package com.xiaomi.ext;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.xiaomi.common.Optional;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes4.dex */
class OptionalAdapter<T> extends TypeAdapter<Optional<T>> {
    private final TypeAdapter<T> delegate;

    public OptionalAdapter(TypeAdapter<T> typeAdapter) {
        this.delegate = typeAdapter;
    }

    @Override // com.google.gson.TypeAdapter
    public void write(JsonWriter jsonWriter, Optional<T> optional) throws IOException {
        if (optional == null || !optional.isPresent()) {
            jsonWriter.nullValue();
        } else {
            this.delegate.write(jsonWriter, optional.get());
        }
    }

    @Override // com.google.gson.TypeAdapter
    /* JADX INFO: renamed from: read */
    public Optional<T> read2(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return Optional.empty();
        }
        return Optional.of(this.delegate.read2(jsonReader));
    }

    public static OptionalAdapter getInstance(Gson gson, TypeToken typeToken) {
        TypeAdapter<T> adapter;
        Type type = typeToken.getType();
        if (type instanceof ParameterizedType) {
            adapter = gson.getAdapter(TypeToken.get(((ParameterizedType) type).getActualTypeArguments()[0]));
        } else if (type instanceof Class) {
            adapter = gson.getAdapter(Object.class);
        } else {
            throw new JsonIOException("Unexpected type type:" + type.getClass());
        }
        return new OptionalAdapter(adapter);
    }
}
