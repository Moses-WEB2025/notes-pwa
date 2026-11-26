package cn.kuaipan.android.utils;

import android.util.Log;
import cn.kuaipan.android.exception.ErrorCode;
import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.exception.KscRuntimeException;
import cn.kuaipan.android.http.KscHttpResponse;
import cn.kuaipan.android.kss.TransferStep;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.zip.DataFormatException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ApiDataHelper {
    private static final String LOG_TAG = "ApiDataHelper";

    public interface IKscData {
        public static final String PARSER_NAME = "PARSER";

        public interface Parser<T extends IKscData> {
            T parserMap(Map<String, Object> map, String... strArr) throws DataFormatException, KscException;
        }
    }

    public static Map<String, Object> contentToMap(KscHttpResponse kscHttpResponse, TransferStep transferStep) throws InterruptedException, KscException {
        InputStream inputStream = null;
        try {
            try {
                try {
                    try {
                        InputStream content = kscHttpResponse.getContent();
                        if (content == null) {
                            throw new KscException(ErrorCode.DATA_IS_NOT_JSON, kscHttpResponse.dump(), transferStep);
                        }
                        Map<String, Object> map = (Map) JsonUtils.parser(content);
                        if (map == null || map.isEmpty()) {
                            throw new KscException(ErrorCode.DATA_TYPE_INVALID, kscHttpResponse.dump(), transferStep);
                        }
                        try {
                            content.close();
                        } catch (Throwable unused) {
                        }
                        return map;
                    } catch (android.util.MalformedJsonException e) {
                        throw new KscException(ErrorCode.DATA_IS_NOT_JSON, kscHttpResponse.dump(), e, transferStep);
                    } catch (ClassCastException e2) {
                        throw new KscException(ErrorCode.DATA_TYPE_INVALID, kscHttpResponse.dump(), e2, transferStep);
                    }
                } catch (IOException e3) {
                    throw KscException.newException(e3, kscHttpResponse.dump(), transferStep);
                }
            } catch (JSONException e4) {
                throw new KscException(ErrorCode.DATA_IS_NOT_JSON, kscHttpResponse.dump(), e4, transferStep);
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (Throwable unused2) {
            }
            throw th;
        }
    }

    public static <T extends IKscData> T parser(KscHttpResponse kscHttpResponse, Map<String, Object> map, Class<T> cls, TransferStep transferStep, String... strArr) throws KscException {
        try {
            return (T) getParser(cls).parserMap(map, strArr);
        } catch (KscRuntimeException e) {
            throw e;
        } catch (ClassCastException e2) {
            throw new KscException(ErrorCode.DATA_TYPE_INVALID, kscHttpResponse.dump(), e2, transferStep);
        } catch (IllegalArgumentException e3) {
            throw new KscException(ErrorCode.DATA_TYPE_INVALID, kscHttpResponse.dump(), e3, transferStep);
        } catch (RuntimeException e4) {
            throw new KscRuntimeException(403999, kscHttpResponse.dump(), e4);
        } catch (DataFormatException e5) {
            throw new KscException(ErrorCode.DATA_TYPE_INVALID, kscHttpResponse.dump(), e5, transferStep);
        }
    }

    public static <T extends IKscData> T parser(JSONObject jSONObject, Map<String, Object> map, Class<T> cls, TransferStep transferStep, String... strArr) throws KscException {
        try {
            return (T) getParser(cls).parserMap(map, strArr);
        } catch (KscRuntimeException e) {
            throw e;
        } catch (ClassCastException e2) {
            throw new KscException(ErrorCode.DATA_TYPE_INVALID, jSONObject.toString(), e2, transferStep);
        } catch (IllegalArgumentException e3) {
            throw new KscException(ErrorCode.DATA_TYPE_INVALID, jSONObject.toString(), e3, transferStep);
        } catch (RuntimeException e4) {
            throw new KscRuntimeException(403999, jSONObject.toString(), e4);
        } catch (DataFormatException e5) {
            throw new KscException(ErrorCode.DATA_TYPE_INVALID, jSONObject.toString(), e5, transferStep);
        }
    }

    private static <T extends IKscData> IKscData.Parser<T> getParser(Class<T> cls) {
        try {
            IKscData.Parser<T> parser = (IKscData.Parser) cls.getField(IKscData.PARSER_NAME).get(null);
            if (parser != null) {
                return parser;
            }
            throw new KscRuntimeException(ErrorCode.BAD_DATA_PARSER, "IKscData protocol requires a IKscData.Creator object called  PARSER on class " + cls);
        } catch (ClassCastException unused) {
            throw new KscRuntimeException(ErrorCode.BAD_DATA_PARSER, "IKscData protocol requires a IKscData.Creator object called  PARSER on class " + cls);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "Parser Class not found in " + cls, e);
            throw new KscRuntimeException(ErrorCode.DATA_MISS_PARSER, "IllegalAccessException when parser: " + cls, e);
        } catch (NoSuchFieldException unused2) {
            throw new KscRuntimeException(ErrorCode.BAD_DATA_PARSER, "IKscData protocol requires a IKscData.Creator object called  PARSER on class " + cls);
        }
    }

    public static String asStringOrThrow(Map<String, Object> map, String str) {
        if (map == null) {
            throw new IllegalArgumentException("DataMap can't be null when parse.");
        }
        Object obj = map.get(str);
        if (obj == null) {
            throw new IllegalArgumentException("Miss required data: " + str);
        }
        return obj.toString();
    }

    public static String asString(Map<String, Object> map, String str) {
        if (map == null) {
            throw new IllegalArgumentException("DataMap can't be null when parse.");
        }
        Object obj = map.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Number asNumber(Object obj, Number number) {
        if (obj == null) {
            return number;
        }
        if (obj instanceof Number) {
            return (Number) obj;
        }
        String string = obj.toString();
        try {
            return Long.valueOf(Long.parseLong(string));
        } catch (NumberFormatException unused) {
            return Double.valueOf(Double.parseDouble(string));
        }
    }

    public static boolean asBoolean(Object obj, boolean z) {
        if (obj == null) {
            return z;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() != 0;
        }
        return Boolean.parseBoolean(obj.toString());
    }

    public static Date asDate(Object obj, Date date) {
        return obj == null ? date : OAuthTimeUtils.parser(obj.toString(), date);
    }
}
