package org.jsoup.nodes;

import com.miui.common.stat.ToDoStat;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miuix.smartaction.SmartAction;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;

/* JADX INFO: loaded from: classes6.dex */
public class FormElement extends Element {
    private static final Evaluator submittable = Selector.evaluatorOf(StringUtil.join(SharedConstants.FormSubmitTags, ", "));
    private final Elements linkedEls;

    public FormElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
        this.linkedEls = new Elements();
    }

    public Elements elements() {
        Elements elementsSelect = select(submittable);
        for (Element element : this.linkedEls) {
            if (element.ownerDocument() != null && !elementsSelect.contains(element)) {
                elementsSelect.add(element);
            }
        }
        return elementsSelect;
    }

    public FormElement addElement(Element element) {
        this.linkedEls.add(element);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    protected void removeChild(Node node) {
        super.removeChild(node);
        this.linkedEls.remove(node);
    }

    public Connection submit() {
        String strAbsUrl = hasAttr("action") ? absUrl("action") : baseUri();
        Validate.notEmpty(strAbsUrl, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        Connection.Method method = attr(XMLWriter.METHOD).equalsIgnoreCase(PassportSimpleRequest.HTTP_METHOD_POST) ? Connection.Method.POST : Connection.Method.GET;
        Document documentOwnerDocument = ownerDocument();
        return (documentOwnerDocument != null ? documentOwnerDocument.connection().newRequest() : Jsoup.newSession()).url(strAbsUrl).data(formData()).method(method);
    }

    public List<Connection.KeyVal> formData() {
        Element elementSelectFirst;
        ArrayList arrayList = new ArrayList();
        for (Element element : elements()) {
            if (element.tag().isFormSubmittable() && !element.hasAttr("disabled")) {
                String strAttr = element.attr("name");
                if (strAttr.length() != 0) {
                    String strAttr2 = element.attr("type");
                    if (!strAttr2.equalsIgnoreCase("button") && !strAttr2.equalsIgnoreCase(AITextWidgetStat.TYPE_IMAGE)) {
                        if (element.nameIs(SmartAction.Feature.SELECT)) {
                            Iterator it = element.select("option[selected]").iterator();
                            boolean z = false;
                            while (it.hasNext()) {
                                arrayList.add(HttpConnection.KeyVal.create(strAttr, ((Element) it.next()).val()));
                                z = true;
                            }
                            if (!z && (elementSelectFirst = element.selectFirst("option")) != null) {
                                arrayList.add(HttpConnection.KeyVal.create(strAttr, elementSelectFirst.val()));
                            }
                        } else if ("checkbox".equalsIgnoreCase(strAttr2) || ToDoStat.RADIO.equalsIgnoreCase(strAttr2)) {
                            if (element.hasAttr("checked")) {
                                arrayList.add(HttpConnection.KeyVal.create(strAttr, element.val().length() > 0 ? element.val() : "on"));
                            }
                        } else {
                            arrayList.add(HttpConnection.KeyVal.create(strAttr, element.val()));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    /* JADX INFO: renamed from: clone */
    public FormElement mo4587clone() {
        return (FormElement) super.mo4587clone();
    }
}
