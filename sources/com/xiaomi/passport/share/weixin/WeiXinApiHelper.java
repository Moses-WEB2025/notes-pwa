package com.xiaomi.passport.share.weixin;

import android.util.Base64;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes4.dex */
public class WeiXinApiHelper {
    private static final String PKG_NAME = WXMiniProgramObject.class.getPackage().getName();
    public static final int SCENE_SESSION = 0;
    private static final String TAG = "MiuiWeiXinApiHelper";
    private final IWXAPI mWeixinApi;

    public WeiXinApiHelper(IWXAPI iwxapi) {
        this.mWeixinApi = iwxapi;
    }

    public void shareMediaMessageJsonTo(int i, String str, String str2) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Share destination invalid " + i);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "transaction" + System.currentTimeMillis();
        req.scene = i;
        parseJsonAndSend(str, str2, req);
    }

    private void parseJsonAndSend(String str, String str2, SendMessageToWX.Req req) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WXMediaMessage.IMediaObject.class, new CustomDeserializeTypeAdapter(str));
        gsonBuilder.registerTypeAdapter(byte[].class, new ByteArrayTypeAdapter());
        try {
            WXMediaMessage wXMediaMessage = (WXMediaMessage) gsonBuilder.create().fromJson(str2, WXMediaMessage.class);
            req.message = wXMediaMessage;
            if (wXMediaMessage == null || wXMediaMessage.mediaObject == null || !wXMediaMessage.mediaObject.checkArgs()) {
                AccountLogger.log(TAG, "failed to parse for " + str);
            } else {
                AccountLogger.log(TAG, "send weixin API +" + str + "+ result " + this.mWeixinApi.sendReq(req));
            }
        } catch (JsonParseException unused) {
            AccountLogger.log(TAG, "failed to parse for " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String encodeBase64(byte[] bArr) {
        return Base64.encodeToString(bArr, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] decodeBase64(String str) {
        return Base64.decode(str, 0);
    }

    private static class CustomDeserializeTypeAdapter implements JsonSerializer<WXMediaMessage.IMediaObject>, JsonDeserializer<WXMediaMessage.IMediaObject> {
        private final String className;

        public CustomDeserializeTypeAdapter(String str) {
            this.className = str;
        }

        @Override // com.google.gson.JsonSerializer
        public JsonElement serialize(WXMediaMessage.IMediaObject iMediaObject, Type type, JsonSerializationContext jsonSerializationContext) {
            return jsonSerializationContext.serialize(iMediaObject);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.JsonDeserializer
        public WXMediaMessage.IMediaObject deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Class<?> cls;
            AccountLogger.log(WeiXinApiHelper.TAG, "deserialize " + type);
            try {
                if (!this.className.startsWith(WeiXinApiHelper.PKG_NAME)) {
                    cls = Class.forName(WeiXinApiHelper.PKG_NAME + "." + this.className);
                } else {
                    cls = Class.forName(this.className);
                }
                return (WXMediaMessage.IMediaObject) jsonDeserializationContext.deserialize(jsonElement, cls);
            } catch (ClassNotFoundException unused) {
                return null;
            }
        }
    }

    private static class ByteArrayTypeAdapter extends TypeAdapter<byte[]> {
        private ByteArrayTypeAdapter() {
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, byte[] bArr) throws IOException {
            AccountLogger.log(WeiXinApiHelper.TAG, "ByteArrayTypeAdapter write");
            if (bArr != null) {
                jsonWriter.value(WeiXinApiHelper.encodeBase64(bArr));
            } else {
                jsonWriter.nullValue();
            }
        }

        @Override // com.google.gson.TypeAdapter
        /* JADX INFO: renamed from: read, reason: avoid collision after fix types in other method */
        public byte[] read2(JsonReader jsonReader) throws IOException {
            AccountLogger.log(WeiXinApiHelper.TAG, "ByteArrayTypeAdapter read");
            if (jsonReader.peek() != JsonToken.NULL) {
                return WeiXinApiHelper.decodeBase64(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }
    }
}
