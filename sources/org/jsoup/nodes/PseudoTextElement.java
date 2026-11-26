package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Tag;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
public class PseudoTextElement extends Element {
    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    void outerHtmlHead(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    void outerHtmlTail(QuietAppendable quietAppendable, Document.OutputSettings outputSettings) {
    }

    public PseudoTextElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
    }
}
