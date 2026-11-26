package com.miui.handwriting.common.tools;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.miui.common.tool.TextProcessUtils;
import com.miui.handwriting.common.data.bean.BoundingBox;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.data.bean.MiuiRecognizeData;
import com.miui.handwriting.common.data.bean.MiuiRecognizeWrapper;
import com.miui.handwriting.common.data.bean.MiuiSpannedData;
import com.miui.handwriting.common.data.bean.MiuiSpannedDataWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.data.bean.PartieWords;
import com.miui.handwriting.common.data.bean.SearchItemWrapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes3.dex */
public class RecognizeDataHelper {
    public static final String SPANNED_SPLIT = "\t_⚡";
    public static final String SPLIT = "\t_✈";
    public static final String SPLIT_SPECIAL = "✈";
    private static final String TAG = "RecognizeDataHelper";

    public static String getRecognizePath(String str) {
        return str + "/miuiRecognize.json";
    }

    public static String getSpannedDataPath(String str) {
        return str + "/miuiSpannedData.json";
    }

    public static String getSelectRecognizePath(String str) {
        return str + "/miuiSelectRecognize.json";
    }

    public static List<MiuiRecognizeData> partiBeanToRecognizeList(long j, PartiBean partiBean, String str, int i) {
        List<PartieWords> chars;
        ArrayList arrayList = new ArrayList();
        if (partiBean == null || (chars = partiBean.getChars()) == null) {
            return arrayList;
        }
        String strSubstring = str.substring(0, str.lastIndexOf(File.separator));
        for (int i2 = 0; i2 < chars.size(); i2++) {
            BoundingBox bounding_box = chars.get(i2).getBounding_box();
            if (bounding_box != null && bounding_box.getWidth() != 0.0f && bounding_box.getHeight() != 0.0f) {
                arrayList.add(new MiuiRecognizeData(j, strSubstring, i, i2, chars.get(i2).getLabel(), bounding_box.getX(), bounding_box.getY(), bounding_box.getWidth(), bounding_box.getHeight()));
            }
        }
        Log.d(TAG, "partiBeanToRecognizeListSize" + arrayList.size());
        return arrayList;
    }

    public static String getLabel(PartiBean partiBean) {
        StringBuilder sb = new StringBuilder();
        if (partiBean == null) {
            return sb.toString();
        }
        List<PartieWords> chars = partiBean.getChars();
        if (chars == null) {
            return sb.toString();
        }
        for (int i = 0; i < chars.size(); i++) {
            BoundingBox bounding_box = chars.get(i).getBounding_box();
            if (bounding_box != null && bounding_box.getWidth() != 0.0f && bounding_box.getHeight() != 0.0f) {
                sb.append(chars.get(i).getLabel());
            }
        }
        return sb.toString();
    }

    private static Map<String, Rect> getRectMap(List<MiuiRecognizeData> list) {
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            MiuiRecognizeData miuiRecognizeData = list.get(i);
            map.put("" + miuiRecognizeData.getPageIndex() + "-" + miuiRecognizeData.getWordIndex(), new Rect((int) list.get(i).getX(), (int) list.get(i).getY(), (int) (list.get(i).getX() + list.get(i).getWidth()), (int) (list.get(i).getY() + list.get(i).getHeight())));
        }
        return map;
    }

    public static CommonSearchWrapper getRectFromRecognizeWrapper(MiuiRecognizeWrapper miuiRecognizeWrapper, MiuiSpannedDataWrapper miuiSpannedDataWrapper, String str, String str2) {
        int i = findStrLine(filterTextContent(str).toLowerCase(), str2)[0];
        int i2 = findStrLine(filterSpannedContent(str).toLowerCase(), str2)[0];
        int iMin = Math.min(i, i2);
        if (i == -1) {
            i = i2;
        } else if (i2 != -1) {
            i = iMin;
        }
        CommonSearchWrapper commonSearchWrapper = new CommonSearchWrapper();
        List<MiuiRecognizeData> list = miuiRecognizeWrapper != null ? miuiRecognizeWrapper.getList() : null;
        if (list != null && !list.isEmpty()) {
            commonSearchWrapper.searchRectMap = getRectMap(filterMiuiRecognizeDataBySearch(list, str, str2, true));
            List<MiuiSpannedData> listFilterMiuiSpannedDataBySearch = filterMiuiSpannedDataBySearch(miuiSpannedDataWrapper != null ? miuiSpannedDataWrapper.getList() : null, str2, false);
            commonSearchWrapper.searchSpannedRect = getSpannedRect(listFilterMiuiSpannedDataBySearch);
            if (listFilterMiuiSpannedDataBySearch != null && listFilterMiuiSpannedDataBySearch.size() > 0) {
                commonSearchWrapper.searchSpannedRectContent = listFilterMiuiSpannedDataBySearch.get(0).getText();
                commonSearchWrapper.searchSpannedTextSize = listFilterMiuiSpannedDataBySearch.get(0).getTextSize();
            }
        }
        commonSearchWrapper.searchPageIndex = i;
        commonSearchWrapper.searchText = str2;
        Log.d(TAG, "searchPageIndex:" + i);
        Log.d(TAG, "textContent:" + str + "_" + str2);
        return commonSearchWrapper;
    }

    private static int getFirstTextId(List<MiuiSpannedData> list) {
        if (list.isEmpty()) {
            return -1;
        }
        return list.get(0).getTextId();
    }

    private static RectF getSpannedRect(List<MiuiSpannedData> list) {
        if (list.isEmpty()) {
            return null;
        }
        MiuiSpannedData miuiSpannedData = list.get(0);
        return new RectF(miuiSpannedData.getX(), miuiSpannedData.getY(), miuiSpannedData.getX() + miuiSpannedData.getWidth(), miuiSpannedData.getY() + miuiSpannedData.getHeight());
    }

    public static List<MiuiRecognizeData> filterMiuiRecognizeDataBySearch(List<MiuiRecognizeData> list, String str, String str2) {
        return filterMiuiRecognizeDataBySearch(list, str, str2, false);
    }

    public static List<MiuiSpannedData> filterMiuiSpannedDataBySearch(List<MiuiSpannedData> list, String str) {
        return filterMiuiSpannedDataBySearch(list, str, false);
    }

    private static List<MiuiRecognizeData> filterMiuiRecognizeDataBySearch(List<MiuiRecognizeData> list, String str, String str2, boolean z) {
        String[] strArr = new String[str2.length()];
        for (int i = 0; i < str2.length(); i++) {
            strArr[i] = String.valueOf(str2.charAt(i));
        }
        ArrayList arrayList = new ArrayList();
        String lowerCase = str.toLowerCase();
        String lowerCase2 = str2.toLowerCase();
        for (Integer num : findAllMatchingPages(lowerCase, lowerCase2)) {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (list.get(i2).getPageIndex() == num.intValue()) {
                    arrayList2.add(list.get(i2));
                }
            }
            if (!arrayList2.isEmpty()) {
                List<Integer> listFindAllStrLine = findAllStrLine(lowerCase, num.intValue(), lowerCase2);
                for (int i3 = 0; i3 < listFindAllStrLine.size(); i3++) {
                    int iIntValue = listFindAllStrLine.get(i3).intValue();
                    if (iIntValue >= 0 && iIntValue < arrayList2.size() && (lowerCase2.length() + iIntValue) - 1 < arrayList2.size()) {
                        for (int i4 = iIntValue; i4 < lowerCase2.length() + iIntValue; i4++) {
                            if (isFind(strArr, ((MiuiRecognizeData) arrayList2.get(i4)).getLabel())) {
                                arrayList.add((MiuiRecognizeData) arrayList2.get(i4));
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static Set<Integer> findAllMatchingPages(String str, String str2) {
        HashSet hashSet = new HashSet();
        if (str != null && str2 != null) {
            String[] strArrSplit = str.split(SPLIT);
            Pattern patternCompile = Pattern.compile(com.miui.libhandwritingpage.utils.Utils.escapeExprSpecialWord(str2));
            for (int i = 0; i < strArrSplit.length; i++) {
                if (patternCompile.matcher(strArrSplit[i]).find()) {
                    hashSet.add(Integer.valueOf(i));
                }
            }
        }
        return hashSet;
    }

    private static List<MiuiSpannedData> filterMiuiSpannedDataBySearch(List<MiuiSpannedData> list, String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getText().contains(str)) {
                    arrayList.add(list.get(i));
                    if (!z) {
                        break;
                    }
                }
            }
        }
        return arrayList;
    }

    private static boolean isFind(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    private static int[] findStrLine(String str, String str2) {
        int[] iArr = new int[2];
        String[] strArrSplit = str.split(SPLIT);
        Pattern patternCompile = Pattern.compile(com.miui.libhandwritingpage.utils.Utils.escapeExprSpecialWord(str2));
        for (int i = 0; i < strArrSplit.length; i++) {
            Matcher matcher = patternCompile.matcher(strArrSplit[i]);
            if (matcher.find()) {
                iArr[0] = i;
                iArr[1] = matcher.start();
                return iArr;
            }
        }
        iArr[0] = -1;
        iArr[1] = -1;
        return iArr;
    }

    private static List<Integer> findAllStrLine(String str, int i, String str2) {
        ArrayList arrayList = new ArrayList();
        try {
            Matcher matcher = Pattern.compile(com.miui.libhandwritingpage.utils.Utils.escapeExprSpecialWord(str2)).matcher(str.split(SPLIT)[i]);
            while (matcher.find()) {
                arrayList.add(Integer.valueOf(matcher.start()));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e(TAG, "findAllStrLine error", e);
            return arrayList;
        }
    }

    public static List<MiuiRecognizeData> getMiuiRecognizeDataBySearch(SearchItemWrapper searchItemWrapper, String str) {
        return filterMiuiRecognizeDataBySearch(getMiuiRecognizeWrapper(searchItemWrapper.getEntPath(), searchItemWrapper).getList(), filterTextContent(searchItemWrapper.getTextContent()), str);
    }

    public static String filterTextContent(String str) {
        return str.contains(SPANNED_SPLIT) ? str.substring(0, str.lastIndexOf(SPANNED_SPLIT)) : str;
    }

    public static String filterSpannedContent(String str) {
        return str.contains(SPANNED_SPLIT) ? str.substring(str.indexOf(SPANNED_SPLIT) + SPANNED_SPLIT.length()) : str;
    }

    public static List<MiuiSpannedData> getMiuiSpannedDataBySearch(SearchItemWrapper searchItemWrapper, String str) {
        return filterMiuiSpannedDataBySearch(getMiuiSpannedDataWrapper(searchItemWrapper.getEntPath(), searchItemWrapper).getList(), str);
    }

    public static MiuiRecognizeWrapper getMiuiRecognizeWrapper(String str, SearchItemWrapper searchItemWrapper) {
        List<String> recognizedFilePaths = searchItemWrapper.getRecognizedFilePaths();
        if (recognizedFilePaths == null || recognizedFilePaths.isEmpty()) {
            return new MiuiRecognizeWrapper();
        }
        try {
            MiuiRecognizeWrapper recognizeFromFile = readRecognizeFromFile(getRecognizePath(str));
            return recognizeFromFile == null ? new MiuiRecognizeWrapper() : recognizeFromFile;
        } catch (Exception e) {
            Log.e(TAG, "create MiuiRecognizeWrapper fail", e);
            return new MiuiRecognizeWrapper();
        }
    }

    public static MiuiSpannedDataWrapper getMiuiSpannedDataWrapper(String str, SearchItemWrapper searchItemWrapper) {
        List<String> recognizedFilePaths = searchItemWrapper.getRecognizedFilePaths();
        if (recognizedFilePaths == null || recognizedFilePaths.isEmpty()) {
            return new MiuiSpannedDataWrapper();
        }
        try {
            MiuiSpannedDataWrapper spannedDataFromFile = readSpannedDataFromFile(getSpannedDataPath(str));
            return spannedDataFromFile == null ? new MiuiSpannedDataWrapper() : spannedDataFromFile;
        } catch (Exception e) {
            Log.e(TAG, "create MiuiRecognizeWrapper fail", e);
            return new MiuiSpannedDataWrapper();
        }
    }

    public static MiuiRecognizeWrapper readRecognizeFromFile(String str) {
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    return (MiuiRecognizeWrapper) GsonUtils.getGsonInstance().fromJson(sb.toString(), MiuiRecognizeWrapper.class);
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
                e.printStackTrace();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (MiuiRecognizeWrapper) GsonUtils.getGsonInstance().fromJson(sb.toString(), MiuiRecognizeWrapper.class);
    }

    public static MiuiSpannedDataWrapper readSpannedDataFromFile(String str) {
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    return (MiuiSpannedDataWrapper) GsonUtils.getGsonInstance().fromJson(sb.toString(), MiuiSpannedDataWrapper.class);
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
                e.printStackTrace();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (MiuiSpannedDataWrapper) GsonUtils.getGsonInstance().fromJson(sb.toString(), MiuiSpannedDataWrapper.class);
    }

    public static PartiBean readSelectRecognizeDataFromFile(String str) {
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    return (PartiBean) GsonUtils.getGsonInstance().fromJson(sb.toString(), PartiBean.class);
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
                e.printStackTrace();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (PartiBean) GsonUtils.getGsonInstance().fromJson(sb.toString(), PartiBean.class);
    }

    public static void writeRecognizeToFile(MiuiRecognizeWrapper miuiRecognizeWrapper, String str) {
        String json = GsonUtils.getGsonInstance().toJson(miuiRecognizeWrapper);
        try {
            FileWriter fileWriter = new FileWriter(new File(str));
            try {
                fileWriter.write(json);
                fileWriter.close();
            } catch (Throwable th) {
                try {
                    fileWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeRecognizeToFile(MiuiSpannedDataWrapper miuiSpannedDataWrapper, String str) {
        String json = GsonUtils.getGsonInstance().toJson(miuiSpannedDataWrapper);
        try {
            FileWriter fileWriter = new FileWriter(new File(str));
            try {
                fileWriter.write(json);
                fileWriter.close();
            } catch (Throwable th) {
                try {
                    fileWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSelectRecognizeToFile(PartiBean partiBean, String str) {
        String json = GsonUtils.getGsonInstance().toJson(partiBean);
        try {
            FileWriter fileWriter = new FileWriter(new File(str));
            try {
                fileWriter.write(json);
                fileWriter.close();
            } catch (Throwable th) {
                try {
                    fileWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String removeRecognizeSplits(String str, String str2) {
        if (str == null || str2 == null || str2.isEmpty()) {
            return "";
        }
        String strReplaceAll = str.replaceAll("[\t_✈\t_⚡]", "");
        StringBuilder sb = new StringBuilder();
        int length = 0;
        while (true) {
            int iIndexOf = strReplaceAll.indexOf(str2, length);
            if (iIndexOf != -1) {
                sb.append(str2);
                length = iIndexOf + str2.length();
            } else {
                return sb.toString();
            }
        }
    }

    public static String removeSeparatorsAndJoin(CharSequence charSequence) {
        if (charSequence == null) {
            return "";
        }
        String string = charSequence.toString();
        return !containsSeparators(string) ? string : string.replaceAll(Pattern.quote(SPLIT) + "|" + Pattern.quote(SPANNED_SPLIT) + "|" + Pattern.quote(SPLIT_SPECIAL), StringUtils.SPACE).trim().replaceAll(TextProcessUtils.ONE_OR_MULTI_SPACE_REG, StringUtils.SPACE);
    }

    public static boolean containsSeparators(CharSequence charSequence) {
        return charSequence.toString().contains(SPLIT) || charSequence.toString().contains(SPANNED_SPLIT) || charSequence.toString().contains(SPLIT_SPECIAL);
    }
}
