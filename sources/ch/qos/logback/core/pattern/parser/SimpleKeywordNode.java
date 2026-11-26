package ch.qos.logback.core.pattern.parser;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SimpleKeywordNode extends FormattingNode {
    List<String> optionList;

    protected SimpleKeywordNode(int i, Object obj) {
        super(i, obj);
    }

    SimpleKeywordNode(Object obj) {
        super(1, obj);
    }

    @Override // ch.qos.logback.core.pattern.parser.FormattingNode, ch.qos.logback.core.pattern.parser.Node
    public boolean equals(Object obj) {
        if (!super.equals(obj) || !(obj instanceof SimpleKeywordNode)) {
            return false;
        }
        SimpleKeywordNode simpleKeywordNode = (SimpleKeywordNode) obj;
        List<String> list = this.optionList;
        if (list != null) {
            return list.equals(simpleKeywordNode.optionList);
        }
        return simpleKeywordNode.optionList == null;
    }

    public List<String> getOptions() {
        return this.optionList;
    }

    @Override // ch.qos.logback.core.pattern.parser.FormattingNode, ch.qos.logback.core.pattern.parser.Node
    public int hashCode() {
        return super.hashCode();
    }

    public void setOptions(List<String> list) {
        this.optionList = list;
    }

    @Override // ch.qos.logback.core.pattern.parser.Node
    public String toString() {
        StringBuilder sbAppend;
        Object obj;
        StringBuilder sb = new StringBuilder();
        if (this.optionList == null) {
            sbAppend = new StringBuilder("KeyWord(").append(this.value).append(",");
            obj = this.formatInfo;
        } else {
            sbAppend = new StringBuilder("KeyWord(").append(this.value).append(", ").append(this.formatInfo).append(",");
            obj = this.optionList;
        }
        sb.append(sbAppend.append(obj).append(")").toString());
        sb.append(printNext());
        return sb.toString();
    }
}
