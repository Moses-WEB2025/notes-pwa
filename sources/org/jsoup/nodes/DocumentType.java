package org.jsoup.nodes;

import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public class DocumentType extends LeafNode {
    private static final String NameKey = "name";
    public static final String PUBLIC_KEY = "PUBLIC";
    private static final String PubSysKey = "pubSysKey";
    private static final String PublicId = "publicId";
    public static final String SYSTEM_KEY = "SYSTEM";
    private static final String SystemId = "systemId";

    public DocumentType(String str, String str2, String str3) {
        super(str);
        Validate.notNull(str2);
        Validate.notNull(str3);
        attributes().add("name", str).add(PublicId, str2).add(SystemId, str3);
        updatePubSyskey();
    }

    public void setPubSysKey(String str) {
        if (str != null) {
            attr(PubSysKey, str);
        }
    }

    private void updatePubSyskey() {
        if (has(PublicId)) {
            attributes().add(PubSysKey, PUBLIC_KEY);
        } else if (has(SystemId)) {
            attributes().add(PubSysKey, SYSTEM_KEY);
        }
    }

    public String name() {
        return attr("name");
    }

    public String publicId() {
        return attr(PublicId);
    }

    public String systemId() {
        return attr(SystemId);
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#doctype";
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
        if (outputSettings.syntax() == Document.OutputSettings.Syntax.html && !has(PublicId) && !has(SystemId)) {
            quietAppendable.append("<!doctype");
        } else {
            quietAppendable.append("<!DOCTYPE");
        }
        if (has("name")) {
            quietAppendable.append(StringUtils.SPACE).append(attr("name"));
        }
        if (has(PubSysKey)) {
            quietAppendable.append(StringUtils.SPACE).append(attr(PubSysKey));
        }
        if (has(PublicId)) {
            quietAppendable.append(" \"").append(attr(PublicId)).append('\"');
        }
        if (has(SystemId)) {
            quietAppendable.append(" \"").append(attr(SystemId)).append('\"');
        }
        quietAppendable.append(Typography.greater);
    }

    private boolean has(String str) {
        return !StringUtil.isBlank(attr(str));
    }
}
