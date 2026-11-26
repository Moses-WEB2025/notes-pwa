package miuix.internal.hybrid;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes5.dex */
public class XmlConfigParser implements ConfigParser {
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_ORIGIN = "origin";
    private static final String ATTRIBUTE_SRC = "src";
    private static final String ATTRIBUTE_SUBDOMAINS = "subdomains";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final String CONFIG_FILE_NAME = "miui_hybrid_config";
    private static final String ELEMENT_ACCESS = "access";
    private static final String ELEMENT_CONTENT = "content";
    private static final String ELEMENT_FEATURE = "feature";
    private static final String ELEMENT_PARAM = "param";
    private static final String ELEMENT_PREFERENCE = "preference";
    private static final String ELEMENT_WIDGET = "widget";
    private static final String KEY_SIGNATURE = "signature";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_VENDOR = "vendor";
    private static final String META_DATA_KEY = "com.miui.sdk.hybrid.config";
    private XmlResourceParser mParser;

    private Config buildCompleteConfig(Config config, Map<String, Object> map) {
        return config;
    }

    private XmlConfigParser(XmlResourceParser xmlResourceParser) {
        this.mParser = xmlResourceParser;
    }

    public static XmlConfigParser create(Context context) throws HybridException {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            int identifier = bundle != null ? bundle.getInt(META_DATA_KEY) : 0;
            if (identifier == 0) {
                identifier = context.getResources().getIdentifier(CONFIG_FILE_NAME, StringLookupFactory.KEY_XML, context.getPackageName());
            }
            return createFromResId(context, identifier);
        } catch (PackageManager.NameNotFoundException e) {
            throw new HybridException(201, e.getMessage());
        }
    }

    public static XmlConfigParser createFromResId(Context context, int i) throws HybridException {
        try {
            return createFromXmlParser(context.getResources().getXml(i));
        } catch (Resources.NotFoundException e) {
            throw new HybridException(201, e.getMessage());
        }
    }

    public static XmlConfigParser createFromXmlParser(XmlResourceParser xmlResourceParser) {
        return new XmlConfigParser(xmlResourceParser);
    }

    @Override // miuix.internal.hybrid.ConfigParser
    public Config parse(Map<String, Object> map) throws HybridException {
        int next;
        if (map == null) {
            map = new HashMap<>();
        }
        Config config = new Config();
        XmlResourceParser xmlResourceParser = this.mParser;
        if (xmlResourceParser != null) {
            do {
                try {
                    try {
                        next = xmlResourceParser.next();
                        if (next == 2) {
                            break;
                        }
                    } catch (IOException e) {
                        throw new HybridException(201, e.getMessage());
                    } catch (XmlPullParserException e2) {
                        throw new HybridException(201, e2.getMessage());
                    }
                } catch (Throwable th) {
                    this.mParser.close();
                    throw th;
                }
            } while (next != 1);
            if (ELEMENT_WIDGET.equals(xmlResourceParser.getName())) {
                parseWidgetElement(config, xmlResourceParser);
            }
            this.mParser.close();
        }
        return buildCompleteConfig(config, map);
    }

    private void parseWidgetElement(Config config, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlResourceParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlResourceParser.getName();
                if ("content".equals(name)) {
                    parseContentElement(config, xmlResourceParser);
                } else if ("feature".equals(name)) {
                    parseFeatureElement(config, xmlResourceParser);
                } else if (ELEMENT_PREFERENCE.equals(name)) {
                    parsePreferenceElement(config, xmlResourceParser);
                } else if (ELEMENT_ACCESS.equals(name)) {
                    parseAccessElement(config, xmlResourceParser);
                }
            }
        }
    }

    private void parseContentElement(Config config, XmlResourceParser xmlResourceParser) {
        config.setContent(xmlResourceParser.getAttributeValue(null, ATTRIBUTE_SRC));
    }

    private void parseFeatureElement(Config config, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        Feature feature = new Feature();
        feature.setName(xmlResourceParser.getAttributeValue(null, "name"));
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && ELEMENT_PARAM.equals(xmlResourceParser.getName())) {
                parseParamElement(feature, xmlResourceParser);
            }
        }
        config.addFeature(feature);
    }

    private void parseParamElement(Feature feature, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        feature.setParam(xmlResourceParser.getAttributeValue(null, "name").toLowerCase(), xmlResourceParser.getAttributeValue(null, "value"));
    }

    private void parsePreferenceElement(Config config, XmlResourceParser xmlResourceParser) {
        String lowerCase = xmlResourceParser.getAttributeValue(null, "name").toLowerCase();
        String attributeValue = xmlResourceParser.getAttributeValue(null, "value");
        if ("signature".equals(lowerCase)) {
            getSecurity(config).setSignature(attributeValue);
            return;
        }
        if ("timestamp".equals(lowerCase)) {
            getSecurity(config).setTimestamp(Long.parseLong(attributeValue));
        } else if (KEY_VENDOR.equals(lowerCase)) {
            config.setVendor(attributeValue);
        } else {
            config.setPreference(lowerCase, attributeValue);
        }
    }

    private Security getSecurity(Config config) {
        Security security = config.getSecurity();
        if (security != null) {
            return security;
        }
        Security security2 = new Security();
        config.setSecurity(security2);
        return security2;
    }

    private void parseAccessElement(Config config, XmlResourceParser xmlResourceParser) {
        Permission permission = new Permission();
        permission.setUri(xmlResourceParser.getAttributeValue(null, "origin"));
        permission.setApplySubdomains(xmlResourceParser.getAttributeBooleanValue(null, ATTRIBUTE_SUBDOMAINS, false));
        permission.setForbidden(false);
        config.addPermission(permission);
    }
}
