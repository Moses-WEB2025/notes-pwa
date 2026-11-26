package ch.qos.logback.core.joran.action;

import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.util.OptionHelper;
import org.xml.sax.Attributes;

/* JADX INFO: loaded from: classes.dex */
public class NewRuleAction extends Action {
    boolean inError = false;

    @Override // ch.qos.logback.core.joran.action.Action
    public void begin(InterpretationContext interpretationContext, String str, Attributes attributes) {
        String str2;
        this.inError = false;
        String value = attributes.getValue("pattern");
        String value2 = attributes.getValue("actionClass");
        if (OptionHelper.isEmpty(value)) {
            this.inError = true;
            str2 = "No 'pattern' attribute in <newRule>";
        } else if (OptionHelper.isEmpty(value2)) {
            this.inError = true;
            str2 = "No 'actionClass' attribute in <newRule>";
        } else {
            try {
                addInfo("About to add new Joran parsing rule [" + value + "," + value2 + "].");
                interpretationContext.getJoranInterpreter().getRuleStore().addRule(new ElementSelector(value), value2);
                return;
            } catch (Exception unused) {
                this.inError = true;
                str2 = "Could not add new Joran parsing rule [" + value + "," + value2 + "]";
            }
        }
        addError(str2);
    }

    @Override // ch.qos.logback.core.joran.action.Action
    public void end(InterpretationContext interpretationContext, String str) {
    }

    public void finish(InterpretationContext interpretationContext) {
    }
}
