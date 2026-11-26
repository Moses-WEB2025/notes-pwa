package com.xiaomi.onetrack.util;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import com.market.sdk.utils.Constants;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
class h implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ e d;

    h(e eVar, String str, String str2, String str3) {
        this.d = eVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        try {
            if (TextUtils.isEmpty(this.a)) {
                return;
            }
            if (this.a.contains("http://") || this.a.contains("https://")) {
                str = this.a + "/api/open/device/writeBack";
            } else {
                str = "https://" + this.a + "/api/open/device/writeBack";
            }
            HashMap map = new HashMap();
            map.put("instanceId", q.a().b());
            map.put(Constants.JSON_IMEI_MD5, DeviceUtil.a(this.d.j));
            map.put("oaid", com.xiaomi.onetrack.util.oaid.a.a().a(this.d.j));
            map.put(com.xiaomi.onetrack.b.p.a.e, this.b);
            map.put("user", this.c);
            String strB = com.xiaomi.onetrack.g.b.b(str, map, false);
            if (!TextUtils.isEmpty(strB) && !"".equals(strB)) {
                this.d.b(strB);
                return;
            }
            Message messageObtain = Message.obtain();
            messageObtain.what = 100;
            Bundle bundle = new Bundle();
            bundle.putString("hint", "注册信息失败，请检查是网络环境是否在内网");
            messageObtain.setData(bundle);
            this.d.k.sendMessage(messageObtain);
        } catch (Exception e) {
            r.b(e.a, e.getMessage());
        }
    }
}
