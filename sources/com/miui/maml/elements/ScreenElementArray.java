package com.miui.maml.elements;

import android.text.TextUtils;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.util.Utils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class ScreenElementArray extends ElementGroup {
    private static final String DEF_INDEX_VAR_NAME = "__i";
    public static final String TAG_NAME = "Array";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.miui.maml.elements.ElementGroup
    public ScreenElement onCreateChild(Element element) {
        return null;
    }

    public ScreenElementArray(Element element, final ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        final int attrAsInt = Utils.getAttrAsInt(element, "count", 0);
        String attribute = element.getAttribute(MiCloudConstants.PDC.PARAM_INDEX_NAME);
        final IndexedVariable indexedVariable = new IndexedVariable(TextUtils.isEmpty(attribute) ? DEF_INDEX_VAR_NAME : attribute, getVariables(), true);
        Utils.traverseXmlElementChildren(element, null, new Utils.XmlTraverseListener() { // from class: com.miui.maml.elements.ScreenElementArray.1
            @Override // com.miui.maml.util.Utils.XmlTraverseListener
            public void onChild(Element element2) {
                String attr = ScreenElementArray.this.getAttr(element2, "name");
                boolean zStartsWith = element2.getTagName().startsWith(VariableElement.TAG_NAME);
                ElementGroup elementGroupCreateArrayGroup = null;
                for (int i = 0; i < attrAsInt; i++) {
                    if (zStartsWith) {
                        element2.setAttribute("dontAddToMap", "true");
                    } else {
                        element2.setAttribute("namesSuffix", "[" + i + "]");
                    }
                    ScreenElement screenElementOnCreateChild = ScreenElementArray.super.onCreateChild(element2);
                    if (screenElementOnCreateChild != null) {
                        if (elementGroupCreateArrayGroup == null) {
                            elementGroupCreateArrayGroup = ElementGroup.createArrayGroup(screenElementRoot, indexedVariable);
                            elementGroupCreateArrayGroup.setName(attr);
                            ScreenElementArray.this.addElement(elementGroupCreateArrayGroup);
                        }
                        elementGroupCreateArrayGroup.addElement(screenElementOnCreateChild);
                    }
                }
            }
        });
    }
}
