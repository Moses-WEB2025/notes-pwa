package org.apache.commons.text.lookup;

/* JADX INFO: loaded from: classes6.dex */
public enum DefaultStringLookup {
    BASE64_DECODER(StringLookupFactory.KEY_BASE64_DECODER, StringLookupFactory.INSTANCE.base64DecoderStringLookup()),
    BASE64_ENCODER(StringLookupFactory.KEY_BASE64_ENCODER, StringLookupFactory.INSTANCE.base64EncoderStringLookup()),
    CONST(StringLookupFactory.KEY_CONST, StringLookupFactory.INSTANCE.constantStringLookup()),
    DATE("date", StringLookupFactory.INSTANCE.dateStringLookup()),
    DNS("dns", StringLookupFactory.INSTANCE.dnsStringLookup()),
    ENVIRONMENT("env", StringLookupFactory.INSTANCE.environmentVariableStringLookup()),
    FILE("file", StringLookupFactory.INSTANCE.fileStringLookup()),
    JAVA("java", StringLookupFactory.INSTANCE.javaPlatformStringLookup()),
    LOCAL_HOST(StringLookupFactory.KEY_LOCALHOST, StringLookupFactory.INSTANCE.localHostStringLookup()),
    LOOPBACK_ADDRESS(StringLookupFactory.KEY_LOOPBACK_ADDRESS, StringLookupFactory.INSTANCE.loopbackAddressStringLookup()),
    PROPERTIES(StringLookupFactory.KEY_PROPERTIES, StringLookupFactory.INSTANCE.propertiesStringLookup()),
    RESOURCE_BUNDLE(StringLookupFactory.KEY_RESOURCE_BUNDLE, StringLookupFactory.INSTANCE.resourceBundleStringLookup()),
    SCRIPT(StringLookupFactory.KEY_SCRIPT, StringLookupFactory.INSTANCE.scriptStringLookup()),
    SYSTEM_PROPERTIES("sys", StringLookupFactory.INSTANCE.systemPropertyStringLookup()),
    URL("url", StringLookupFactory.INSTANCE.urlStringLookup()),
    URL_DECODER(StringLookupFactory.KEY_URL_DECODER, StringLookupFactory.INSTANCE.urlDecoderStringLookup()),
    URL_ENCODER(StringLookupFactory.KEY_URL_ENCODER, StringLookupFactory.INSTANCE.urlEncoderStringLookup()),
    XML(StringLookupFactory.KEY_XML, StringLookupFactory.INSTANCE.xmlStringLookup()),
    XML_DECODER(StringLookupFactory.KEY_XML_DECODER, StringLookupFactory.INSTANCE.xmlDecoderStringLookup()),
    XML_ENCODER(StringLookupFactory.KEY_XML_ENCODER, StringLookupFactory.INSTANCE.xmlEncoderStringLookup());

    private final String key;
    private final StringLookup lookup;

    DefaultStringLookup(String str, StringLookup stringLookup) {
        this.key = str;
        this.lookup = stringLookup;
    }

    public String getKey() {
        return this.key;
    }

    public StringLookup getStringLookup() {
        return this.lookup;
    }
}
