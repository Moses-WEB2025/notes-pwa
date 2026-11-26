package cn.kuaipan.android.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class JsonUtils {
    public static Object parserArray(Reader reader) throws JSONException, IOException {
        JsonReader jsonReader = new JsonReader(reader);
        Object obj = parserArray(jsonReader);
        if (jsonReader.peek() == JsonToken.END_DOCUMENT) {
            return obj;
        }
        throw new JSONException("Document not end of EOF");
    }

    public static Object parserObject(Reader reader) throws JSONException, IOException {
        JsonReader jsonReader = new JsonReader(reader);
        Object obj = parserObject(jsonReader);
        if (jsonReader.peek() == JsonToken.END_DOCUMENT) {
            return obj;
        }
        throw new JSONException("Document not end of EOF");
    }

    public static Object parser(InputStream inputStream) throws JSONException, IOException {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
        Object rVar = parser(jsonReader);
        if (jsonReader.peek() == JsonToken.END_DOCUMENT) {
            return rVar;
        }
        throw new JSONException("Document not end of EOF");
    }

    public static Object parser(Reader reader) throws JSONException, IOException {
        JsonReader jsonReader = new JsonReader(reader);
        Object rVar = parser(jsonReader);
        if (jsonReader.peek() == JsonToken.END_DOCUMENT) {
            return rVar;
        }
        throw new JSONException("Document not end of EOF");
    }

    /* JADX INFO: renamed from: cn.kuaipan.android.utils.JsonUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$kuaipan$android$utils$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$cn$kuaipan$android$utils$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.NAME.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.END_ARRAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.END_DOCUMENT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonToken[JsonToken.END_OBJECT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static Object parser(JsonReader jsonReader) throws JSONException, IOException {
        switch (AnonymousClass1.$SwitchMap$cn$kuaipan$android$utils$JsonToken[jsonReader.peek().ordinal()]) {
            case 1:
                return parserArray(jsonReader);
            case 2:
                return parserObject(jsonReader);
            case 3:
                return Boolean.valueOf(jsonReader.nextBoolean());
            case 4:
                return parserNumber(jsonReader);
            case 5:
                return jsonReader.nextString();
            case 6:
                jsonReader.nextNull();
                return null;
            case 7:
            case 8:
            case 9:
            case 10:
                throw new JSONException("Meet EOF when json not end.");
            default:
                return null;
        }
    }

    private static Object parserNumber(JsonReader jsonReader) throws IOException {
        try {
            try {
                return Integer.valueOf(jsonReader.nextInt());
            } catch (NumberFormatException unused) {
                return Long.valueOf(jsonReader.nextLong());
            }
        } catch (NumberFormatException unused2) {
            return Double.valueOf(jsonReader.nextDouble());
        }
    }

    private static Object parserObject(JsonReader jsonReader) throws JSONException, IOException {
        jsonReader.beginObject();
        ObtainabelHashMap obtainabelHashMapObtain = ObtainabelHashMap.obtain();
        try {
            JsonToken jsonTokenPeek = jsonReader.peek();
            while (jsonTokenPeek != JsonToken.END_OBJECT) {
                obtainabelHashMapObtain.put(jsonReader.nextName(), parser(jsonReader));
                jsonTokenPeek = jsonReader.peek();
            }
            jsonReader.endObject();
            return obtainabelHashMapObtain;
        } catch (IOException e) {
            obtainabelHashMapObtain.recycle();
            throw e;
        } catch (JSONException e2) {
            obtainabelHashMapObtain.recycle();
            throw e2;
        }
    }

    private static Object parserArray(JsonReader jsonReader) throws JSONException, IOException {
        jsonReader.beginArray();
        ObtainabelList obtainabelListObtain = ObtainabelList.obtain();
        try {
            JsonToken jsonTokenPeek = jsonReader.peek();
            while (jsonTokenPeek != JsonToken.END_ARRAY) {
                obtainabelListObtain.add(parser(jsonReader));
                jsonTokenPeek = jsonReader.peek();
            }
            jsonReader.endArray();
            return obtainabelListObtain;
        } catch (IOException e) {
            obtainabelListObtain.recycle();
            throw e;
        } catch (JSONException e2) {
            obtainabelListObtain.recycle();
            throw e2;
        }
    }
}
