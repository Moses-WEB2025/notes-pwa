package com.miui.maml;

import android.util.Log;
import com.miui.maml.data.Variables;
import com.miui.maml.util.Utils;
import java.io.InputStream;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class LanguageHelper {
    private static final String COMPATIBLE_STRING_ROOT_TAG = "strings";
    private static final String DEFAULT_STRING_FILE_PATH = "strings/strings.xml";
    private static final String LOG_TAG = "LanguageHelper";
    private static final String STRING_FILE_PATH = "strings/strings.xml";
    private static final String STRING_ROOT_TAG = "resources";
    private static final String STRING_TAG = "string";

    public static boolean load(Locale locale, ResourceManager resourceManager, Variables variables) {
        String strAddFileNameSuffix;
        String str = "strings/strings.xml";
        InputStream inputStream = null;
        if (locale != null) {
            strAddFileNameSuffix = Utils.addFileNameSuffix("strings/strings.xml", locale.toString());
            if (!resourceManager.resourceExists(strAddFileNameSuffix)) {
                strAddFileNameSuffix = Utils.addFileNameSuffix("strings/strings.xml", locale.getLanguage());
            }
        } else {
            strAddFileNameSuffix = null;
        }
        if (resourceManager.resourceExists(strAddFileNameSuffix)) {
            str = strAddFileNameSuffix;
        } else if (!resourceManager.resourceExists("strings/strings.xml")) {
            Log.i(LOG_TAG, "no available string resources to load.");
            return false;
        }
        try {
            DocumentBuilder documentBuilderNewDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            inputStream = resourceManager.getInputStream(str);
            return setVariables(documentBuilderNewDocumentBuilder.parse(inputStream), variables);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private static boolean setVariables(Document document, Variables variables) {
        boolean z;
        NodeList elementsByTagName = document.getElementsByTagName(STRING_ROOT_TAG);
        if (elementsByTagName.getLength() <= 0) {
            elementsByTagName = document.getElementsByTagName(COMPATIBLE_STRING_ROOT_TAG);
            if (elementsByTagName.getLength() <= 0) {
                return false;
            }
            z = false;
        } else {
            z = true;
        }
        NodeList elementsByTagName2 = ((Element) elementsByTagName.item(0)).getElementsByTagName("string");
        for (int i = 0; i < elementsByTagName2.getLength(); i++) {
            Element element = (Element) elementsByTagName2.item(i);
            variables.put(element.getAttribute("name"), (z ? element.getTextContent() : element.getAttribute("value")).replaceAll("\\\\", ""));
        }
        return true;
    }
}
