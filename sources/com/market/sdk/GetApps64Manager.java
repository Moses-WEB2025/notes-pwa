package com.market.sdk;

import android.os.AsyncTask;
import com.market.sdk.utils.AppGlobal;
import com.market.sdk.utils.Client;
import com.market.sdk.utils.Connection;
import com.market.sdk.utils.Log;
import com.market.sdk.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class GetApps64Manager {
    private static volatile GetApps64Manager INSTANCE = null;
    private static final String TAG = "GetApps64Manager";

    public static GetApps64Manager getInstance() {
        if (INSTANCE == null) {
            synchronized (GetApps64Manager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GetApps64Manager();
                }
            }
        }
        return INSTANCE;
    }

    public void getSupport64Apps(IGetAppsCallback iGetAppsCallback) {
        new GetSupportAppsAsyncTask(iGetAppsCallback).execute(new String[0]);
    }

    public class GetSupportAppsAsyncTask extends AsyncTask<String, Void, Integer> {
        private IGetAppsCallback callback;
        private List<AppUpdate64> update64List;

        public GetSupportAppsAsyncTask(IGetAppsCallback iGetAppsCallback) {
            this.callback = iGetAppsCallback;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(String... strArr) {
            if (!Utils.isConnected(AppGlobal.getContext())) {
                return 3;
            }
            Connection connection = new Connection(com.market.sdk.utils.Constants.UPDATE_64_URL);
            Objects.requireNonNull(connection);
            Connection.Parameter parameter = new Connection.Parameter(connection);
            parameter.add("sdk", String.valueOf(Client.SDK_VERSION));
            parameter.add(com.market.sdk.utils.Constants.JSON_VERSION, Client.SYSTEM_VERSION);
            parameter.add(com.market.sdk.utils.Constants.JSON_LANGUAGE, Client.getLanguage());
            parameter.add(com.market.sdk.utils.Constants.JSON_COUNTRY, Client.getCountry());
            parameter.add(com.market.sdk.utils.Constants.JSON_REGION, Client.getRegion());
            parameter.add(com.market.sdk.utils.Constants.JSON_CPU_ARCH, Client.getCpuArch());
            parameter.add("model", Client.getModel());
            parameter.add(com.market.sdk.utils.Constants.JSON_DEVICE, Client.getDevice());
            parameter.add("deviceType", String.valueOf(Client.getDeviceType()));
            parameter.add(com.market.sdk.utils.Constants.JSON_XIAOMI_SDK_VERSION, "11");
            parameter.add(com.market.sdk.utils.Constants.JSON_XIAOMI_SDK_VERSION_NAME, AppGlobal.getContext().getResources().getString(R.string.marketSdkVersion));
            parameter.add(com.market.sdk.utils.Constants.JSON_MIUI_BIG_VERSION_NAME, Client.getMiuiBigVersionName());
            parameter.add(com.market.sdk.utils.Constants.JSON_MIUI_BIG_VERSION_CODE, Client.getMiuiBigVersionCode());
            if (Connection.NetworkError.OK == connection.requestJSON()) {
                List<AppUpdate64> response = parseResponse(connection.getResponse());
                this.update64List = response;
                if (response != null) {
                    return 0;
                }
                return 4;
            }
            return 4;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            if (num.intValue() == 0) {
                this.callback.onLoadSuccess(this.update64List);
            } else if (num.intValue() == 4 || num.intValue() == 3) {
                this.callback.onLoadFailed(num.intValue());
            }
        }

        private List<AppUpdate64> parseResponse(JSONObject jSONObject) {
            ArrayList arrayList = new ArrayList();
            if (jSONObject == null) {
                Log.e(GetApps64Manager.TAG, "support64App json obj null");
                return null;
            }
            Log.i(GetApps64Manager.TAG, "support64App : " + jSONObject.toString());
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("apps");
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(new AppUpdate64(jSONArray.getJSONObject(i).optString("packageName"), jSONArray.getJSONObject(i).optString("versionCode"), jSONArray.getJSONObject(i).optString(com.market.sdk.utils.Constants.VERSION_NAME)));
                }
                return arrayList;
            } catch (JSONException e) {
                Log.e(GetApps64Manager.TAG, "parse support64App error: " + e.getLocalizedMessage());
                return null;
            }
        }
    }
}
