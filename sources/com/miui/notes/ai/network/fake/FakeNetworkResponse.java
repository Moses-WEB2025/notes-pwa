package com.miui.notes.ai.network.fake;

import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.network.GlobalAiTextRequestManager;
import com.miui.notes.ai.network.NetworkError;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class FakeNetworkResponse {
    private static final String[] data = {"我最喜欢的季节是秋天，因为它既凉爽又魅力。\n", "秋天的天空非常美丽。它比夏天的天空更清澈，更高远。白云在天空中慢慢地漂浮，它们像羊毛一样轻盈，仿佛可以触摸到。在秋天的天空中，你可以看到更多的鸟儿飞羽，它们在寻找食物，准备过冬。\n", "秋天的树木也非常美丽。所有的树叶都开始变色，变成黄色、橙色和红色。这些颜色构成了一幅美丽的画卷，让人们感到非常惊喜。\n", "秋天还有很多美味的食物。苹果、梨、柿子等水果都非常甜美，还有丰收的稻谷和玉米等农作物。在秋天，我们可以吃到更多的新鲜食物，这些食物对健康非常有益\n"};

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.notes.ai.network.fake.FakeNetworkResponse$1] */
    public static void mockGetResult(final DataListener dataListener) {
        new Thread("fake-network") { // from class: com.miui.notes.ai.network.fake.FakeNetworkResponse.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
                    if (dataListener != null) {
                        for (String str : FakeNetworkResponse.data) {
                            try {
                                Thread.sleep((long) (((Math.random() * 0.5d) + 0.5d) * 100.0d));
                                dataListener.listenString(new AiOutputData("", str, ""));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                dataListener.netError(new NetworkError.Builder().errorCode(-2).errorMsg("deliver fail").build());
                            }
                        }
                        dataListener.listenString(new AiOutputData("我最喜欢的季节是秋天，因为它既凉爽又<correction version='v1'>有</correction>魅力。\n秋天的天空非常美丽。它比夏天的天空更清澈，更高远。白云在天空中慢慢地漂浮，它们像羊毛一样轻盈，仿佛可以触摸到。在秋天的天空中，你可以看到更多的<correction version='v1'><delete>鸟儿</delete></correction>飞<correction version='v1'><delete>羽</delete>鸟</correction>，它们在寻找食物，准备过冬。\n秋天的树木也非常美丽。所有的树叶都开始变色，变成黄色、橙色和红色。这些颜色构成了一幅美丽的画卷，让人们感到非常惊喜。在秋天的风中，树叶会一片片地飘落，它们像雨点一样洒落在地上，给大地铺上了一层彩色的地毯。", GlobalAiTextRequestManager.FLOW_DONE_TAG, ""));
                        dataListener.finishListen();
                    }
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }.start();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.miui.notes.ai.network.fake.FakeNetworkResponse$2] */
    public static void mockGetRecommendDate(final DataListener dataListener) {
        new Thread("fake-network") { // from class: com.miui.notes.ai.network.fake.FakeNetworkResponse.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(1000L);
                    if (dataListener != null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add("帮我写上海3日的旅游攻略");
                        arrayList.add("帮我写一篇客户经理的周计划");
                        arrayList.add("帮我写一份公司年会发言稿帮我写一份公司年会发言稿帮我写一份公司年会发言稿帮我写一份公司年会发言稿");
                        dataListener.listenRecommendData(arrayList);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

    public static void mockGetError(DataListener dataListener) {
        if (dataListener != null) {
            dataListener.netError(new NetworkError.Builder().errorCode(-1).errorMsg("mock fail").build());
        }
    }
}
