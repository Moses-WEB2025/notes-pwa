package com.miui.notes.ai.editor;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.SpannableStringBuilder;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.richeditor.schema.HtmlParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/* JADX INFO: compiled from: ClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u001c\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00072\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¨\u0006\u0011"}, d2 = {"Lcom/miui/notes/ai/editor/ClipManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "htmlUpdate", "", "htmlString", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "htmlParseInner", "htmlText", "mList", "", "Lcom/miui/notes/ai/editor/ClipManager$IClipData;", "IClipData", "TextElement", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ClipManager {
    public static final ClipManager INSTANCE = new ClipManager();

    /* JADX INFO: compiled from: ClipManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001¨\u0006\u0002À\u0006\u0003"}, d2 = {"Lcom/miui/notes/ai/editor/ClipManager$IClipData;", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface IClipData {
    }

    private ClipManager() {
    }

    public final void htmlUpdate(String htmlString, Context context) {
        Intrinsics.checkNotNullParameter(htmlString, "htmlString");
        Intrinsics.checkNotNullParameter(context, "context");
        ArrayList<IClipData> arrayList = new ArrayList();
        htmlParseInner(StringsKt.replace$default(htmlString, "<p class=\"\" data-indentation=\"1\"> </p>", "", false, 4, (Object) null), arrayList);
        Object systemService = context.getSystemService("clipboard");
        if (systemService == null) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        for (IClipData iClipData : arrayList) {
            if (iClipData instanceof TextElement) {
                arrayList2.add(new ClipData.Item(((TextElement) iClipData).getMText()));
                z = true;
            }
        }
        ArrayList arrayList3 = new ArrayList();
        if (z) {
            arrayList3.add("text/plain");
        }
        ClipDescription clipDescription = new ClipDescription("AiTextWidget", (String[]) arrayList3.toArray(new String[0]));
        if (arrayList2.isEmpty()) {
            return;
        }
        ClipData clipData = new ClipData(clipDescription, (ClipData.Item) arrayList2.get(0));
        arrayList2.remove(0);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            clipData.addItem((ClipData.Item) it.next());
        }
        ((ClipboardManager) systemService).setPrimaryClip(clipData);
    }

    public final void htmlParseInner(String htmlText, List<IClipData> mList) {
        Intrinsics.checkNotNullParameter(htmlText, "htmlText");
        Intrinsics.checkNotNullParameter(mList, "mList");
        final StringBuilder sb = new StringBuilder();
        Document document = Jsoup.parse(htmlText);
        Intrinsics.checkNotNullExpressionValue(document, "parse(...)");
        NodeTraversor.traverse(new NodeVisitor() { // from class: com.miui.notes.ai.editor.ClipManager.htmlParseInner.1
            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof TextNode) {
                    TextNode textNode = (TextNode) node;
                    sb.append(textNode.getWholeText());
                    Node nodeParentNode = textNode.parentNode();
                    if (nodeParentNode == null || !nodeParentNode.hasAttr(HtmlParser.LinkCardElement.KEY_LINK)) {
                        return;
                    }
                    sb.append(StringUtils.SPACE + nodeParentNode.attr(HtmlParser.LinkCardElement.KEY_LINK));
                }
            }

            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof Element) {
                    Node nodeNextSibling = node.nextSibling();
                    Element element = (Element) node;
                    if (element.isBlock()) {
                        if ((!(nodeNextSibling instanceof TextNode) && !(nodeNextSibling instanceof Element)) || Intrinsics.areEqual("head", element.tagName()) || Intrinsics.areEqual("pm-block bloc pm-img-container", element.className())) {
                            return;
                        }
                        sb.append(StringUtils.LF);
                    }
                }
            }
        }, document);
        if (sb.length() > 0) {
            mList.add(new TextElement(new SpannableStringBuilder(sb.toString())));
        }
    }

    /* JADX INFO: compiled from: ClipManager.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\n"}, d2 = {"Lcom/miui/notes/ai/editor/ClipManager$TextElement;", "Lcom/miui/notes/ai/editor/ClipManager$IClipData;", "text", "Landroid/text/SpannableStringBuilder;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/text/SpannableStringBuilder;)V", "mText", "getMText", "()Landroid/text/SpannableStringBuilder;", "setMText", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class TextElement implements IClipData {
        private SpannableStringBuilder mText;

        public TextElement(SpannableStringBuilder text) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.mText = text;
        }

        public final SpannableStringBuilder getMText() {
            return this.mText;
        }

        public final void setMText(SpannableStringBuilder spannableStringBuilder) {
            Intrinsics.checkNotNullParameter(spannableStringBuilder, "<set-?>");
            this.mText = spannableStringBuilder;
        }
    }
}
