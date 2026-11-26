package ch.qos.logback.core.spi;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface PropertyContainer {
    Map<String, String> getCopyOfPropertyMap();

    String getProperty(String str);
}
