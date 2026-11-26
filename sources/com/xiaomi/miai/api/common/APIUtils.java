package com.xiaomi.miai.api.common;

import ch.qos.logback.core.CoreConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.ext.GsonExtAdapterFactory;
import com.xiaomi.miai.api.Response;
import java.util.ArrayList;
import java.util.Iterator;
import miui.cloud.CloudPushConstants;

/* JADX INFO: loaded from: classes4.dex */
public class APIUtils extends AbstractAPIUtils {
    private static Gson gson = createBuilder(false).create();
    private static Gson prettyPrinterGson = createBuilder(true).create();

    private static GsonBuilder createBuilder(boolean z) {
        GsonBuilder gsonBuilderRegisterTypeAdapterFactory = new GsonBuilder().registerTypeAdapterFactory(new GsonExtAdapterFactory());
        return z ? gsonBuilderRegisterTypeAdapterFactory.setPrettyPrinting() : gsonBuilderRegisterTypeAdapterFactory;
    }

    public static Gson getGson() {
        return gson;
    }

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static String toJsonString(Object obj, boolean z) {
        return (z ? prettyPrinterGson : gson).toJson(obj);
    }

    public static <T> T fromJsonString(String str, Class<T> cls) {
        return (T) gson.fromJson(str, (Class) cls);
    }

    public static <T> T fromJsonNode(JsonElement jsonElement, Class<T> cls) {
        return (T) gson.fromJson(jsonElement, (Class) cls);
    }

    public static <T> Response<T> getResponse(String str, Class<T> cls) {
        return (Response) gson.fromJson(str, TypeToken.getParameterized(Response.class, cls).getType());
    }

    public static <T> Instruction<T> readInstruction(String str) throws JsonException {
        return readInstruction(requireJsonObject(JsonParser.parseString(str)));
    }

    public static <T> Instruction<T> readInstruction(JsonObject jsonObject) throws JsonException {
        InstructionHeader instructionHeader = (InstructionHeader) fromJsonNode(jsonObject.get("header"), InstructionHeader.class);
        Object objFindPayload = findPayload(instructionHeader, jsonObject);
        if (objFindPayload == null) {
            JsonObject asJsonObject = jsonObject.getAsJsonObject(CloudPushConstants.XML_PAYLOAD);
            if (asJsonObject == null) {
                asJsonObject = new JsonObject();
            }
            return new Instruction<>(instructionHeader, asJsonObject);
        }
        return new Instruction<>(instructionHeader, objFindPayload);
    }

    public static <T> Event<T> readEvent(String str) throws JsonException {
        return readEvent(requireJsonObject(JsonParser.parseString(str)));
    }

    public static <T> Event<T> readEvent(JsonObject jsonObject) throws JsonException {
        ArrayList arrayList;
        EventHeader eventHeader = (EventHeader) fromJsonNode(jsonObject.get("header"), EventHeader.class);
        JsonElement jsonElement = jsonObject.get(CoreConstants.CONTEXT_SCOPE_VALUE);
        if (jsonElement == null || !jsonElement.isJsonArray()) {
            arrayList = null;
        } else {
            JsonArray jsonArray = (JsonArray) jsonElement;
            arrayList = new ArrayList(jsonArray.size());
            Iterator<JsonElement> it = jsonArray.iterator();
            while (it.hasNext()) {
                Context context = readContext(it.next().getAsJsonObject());
                if (context != null) {
                    arrayList.add(context);
                }
            }
        }
        Object objFindPayload = findPayload(eventHeader, jsonObject);
        if (objFindPayload == null) {
            return null;
        }
        return new Event<>(arrayList, eventHeader, objFindPayload);
    }

    public static <T> Context<T> readContext(String str) throws JsonException {
        return readContext(requireJsonObject(JsonParser.parseString(str)));
    }

    public static <T> Context<T> readContext(JsonObject jsonObject) throws JsonException {
        ContextHeader contextHeader = (ContextHeader) fromJsonNode(jsonObject.get("header"), ContextHeader.class);
        Object objFindPayload = findPayload(contextHeader, jsonObject);
        if (objFindPayload == null) {
            return null;
        }
        return new Context<>(contextHeader, objFindPayload);
    }

    private static JsonObject requireJsonObject(JsonElement jsonElement) throws JsonException {
        if (jsonElement.isJsonObject()) {
            return (JsonObject) jsonElement;
        }
        throw new JsonException(String.format("The json node is not JsonObject %s.", jsonElement));
    }

    private static <H extends MessageHeader<H>, P> P findPayload(H h, JsonObject jsonObject) throws JsonException {
        Class<?> clsFindClass = mapping.findClass(h.getNamespace(), h.getName());
        if (clsFindClass != null) {
            return (P) fromJsonNode(jsonObject.get(CloudPushConstants.XML_PAYLOAD), clsFindClass);
        }
        return null;
    }
}
