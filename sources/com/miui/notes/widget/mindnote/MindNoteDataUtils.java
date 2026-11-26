package com.miui.notes.widget.mindnote;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.miui.notes.model.MindEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* JADX INFO: loaded from: classes4.dex */
public class MindNoteDataUtils {
    public static List<MindItemData> getMindNoteList(MindEntity mindEntity) {
        if (mindEntity != null) {
            if (mindEntity.isMap) {
                return getMindNoteMapList(mindEntity.content);
            }
            return getMindNoteOutLineList(mindEntity.content);
        }
        return new ArrayList();
    }

    public static List<MindItemData> getMindNoteOutLineList(String str) {
        MindContent mindContent;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str) && (mindContent = (MindContent) new Gson().fromJson(str, MindContent.class)) != null) {
            MindContent.MindContentData mindContentData = mindContent.data;
            Stack stack = new Stack();
            stack.push(mindContentData);
            while (!stack.isEmpty()) {
                MindContent.MindContentData mindContentData2 = (MindContent.MindContentData) stack.pop();
                if (mindContentData2 != null) {
                    List<MindContent.MindContentData> list = mindContentData2.children;
                    MindItemData mindItemData = new MindItemData();
                    mindItemData.label = mindContentData2.label;
                    mindItemData.depth = mindContentData2.deep;
                    int size = list != null ? list.size() : 0;
                    if (size > 0) {
                        mindItemData.hasChildren = true;
                    } else {
                        mindItemData.hasChildren = false;
                    }
                    arrayList.add(mindItemData);
                    for (int i = size - 1; i >= 0; i--) {
                        MindContent.MindContentData mindContentData3 = list.get(i);
                        mindContentData3.deep = mindContentData2.deep + 1;
                        stack.push(mindContentData3);
                    }
                }
            }
        }
        arrayList.remove(0);
        return arrayList;
    }

    public static List<MindItemData> getMindNoteMapList(String str) {
        MindContent mindContent;
        MindContent.MindContentData mindContentData;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str) && (mindContent = (MindContent) new Gson().fromJson(str, MindContent.class)) != null && (mindContentData = mindContent.data) != null && mindContentData.children != null && !mindContentData.children.isEmpty()) {
            for (int i = 0; i < mindContentData.children.size(); i++) {
                MindContent.MindContentData mindContentData2 = mindContentData.children.get(i);
                if (mindContentData2 != null) {
                    MindItemData mindItemData = new MindItemData();
                    mindItemData.label = mindContentData2.label;
                    arrayList.add(mindItemData);
                }
            }
        }
        return arrayList;
    }
}
