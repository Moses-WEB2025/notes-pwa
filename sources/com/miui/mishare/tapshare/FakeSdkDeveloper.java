package com.miui.mishare.tapshare;

/* JADX INFO: loaded from: classes3.dex */
class FakeSdkDeveloper {
    FakeSdkDeveloper() {
    }

    public static void main(String[] strArr) {
        TapShareManager.TapShareSendCallback tapShareSendCallback = new TapShareManager.TapShareSendCallback() { // from class: com.miui.mishare.tapshare.FakeSdkDeveloper.1
            @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
            public void onSendResult(int i) {
            }

            @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
            public void onTapShareCancel(TapShareDataHandler tapShareDataHandler) {
            }

            @Override // com.miui.mishare.tapshare.TapShareManager.TapShareSendCallback
            public boolean onTapShareSend(TapShareDataHandler tapShareDataHandler) {
                tapShareDataHandler.getTapShareInfo();
                tapShareDataHandler.cancelSend(0, "test");
                TapShareSendBean tapShareSendBeanBuild = new TapShareSendBean.Builder().build();
                tapShareDataHandler.sendByString(null, tapShareSendBeanBuild);
                tapShareDataHandler.sendByUris(null, tapShareSendBeanBuild);
                return false;
            }
        };
        TapShareManager.getInstance().registerSendCallback(null, tapShareSendCallback);
        TapShareManager.getInstance().unregisterSendCallback(null, tapShareSendCallback);
        TapShareManager.getInstance().notifyShowTipsView(null);
        TapShareManager.getInstance().notifyHiddenTipsView();
    }
}
