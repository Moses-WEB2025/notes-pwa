package ch.qos.logback.core.subst;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.spi.PropertyContainer;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.util.OptionHelper;
import java.util.Iterator;
import java.util.Stack;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public class NodeToStringTransformer {
    final Node node;
    final PropertyContainer propertyContainer0;
    final PropertyContainer propertyContainer1;

    /* JADX INFO: renamed from: ch.qos.logback.core.subst.NodeToStringTransformer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ch$qos$logback$core$subst$Node$Type;

        static {
            int[] iArr = new int[Node.Type.values().length];
            $SwitchMap$ch$qos$logback$core$subst$Node$Type = iArr;
            try {
                iArr[Node.Type.LITERAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$subst$Node$Type[Node.Type.VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public NodeToStringTransformer(Node node, PropertyContainer propertyContainer) {
        this(node, propertyContainer, null);
    }

    public NodeToStringTransformer(Node node, PropertyContainer propertyContainer, PropertyContainer propertyContainer2) {
        this.node = node;
        this.propertyContainer0 = propertyContainer;
        this.propertyContainer1 = propertyContainer2;
    }

    private void compileNode(Node node, StringBuilder sb, Stack<Node> stack) throws ScanException {
        while (node != null) {
            int i = AnonymousClass1.$SwitchMap$ch$qos$logback$core$subst$Node$Type[node.type.ordinal()];
            if (i == 1) {
                handleLiteral(node, sb);
            } else if (i == 2) {
                handleVariable(node, sb, stack);
            }
            node = node.next;
        }
    }

    private String constructRecursionErrorMessage(Stack<Node> stack) {
        StringBuilder sb = new StringBuilder("Circular variable reference detected while parsing input [");
        for (Node node : stack) {
            sb.append(StringSubstitutor.DEFAULT_VAR_START).append(variableNodeValue(node)).append(StringSubstitutor.DEFAULT_VAR_END);
            if (stack.lastElement() != node) {
                sb.append(" --> ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean equalNodes(Node node, Node node2) {
        if (node.type != null && !node.type.equals(node2.type)) {
            return false;
        }
        if (node.payload == null || node.payload.equals(node2.payload)) {
            return node.defaultPart == null || node.defaultPart.equals(node2.defaultPart);
        }
        return false;
    }

    private void handleLiteral(Node node, StringBuilder sb) {
        sb.append((String) node.payload);
    }

    private void handleVariable(Node node, StringBuilder sb, Stack<Node> stack) throws ScanException {
        boolean zHaveVisitedNodeAlready = haveVisitedNodeAlready(node, stack);
        stack.push(node);
        if (zHaveVisitedNodeAlready) {
            throw new IllegalArgumentException(constructRecursionErrorMessage(stack));
        }
        StringBuilder sb2 = new StringBuilder();
        compileNode((Node) node.payload, sb2, stack);
        String string = sb2.toString();
        String strLookupKey = lookupKey(string);
        if (strLookupKey != null) {
            compileNode(tokenizeAndParseString(strLookupKey), sb, stack);
            stack.pop();
        } else {
            if (node.defaultPart == null) {
                sb.append(string + CoreConstants.UNDEFINED_PROPERTY_SUFFIX);
                stack.pop();
                return;
            }
            Node node2 = (Node) node.defaultPart;
            StringBuilder sb3 = new StringBuilder();
            compileNode(node2, sb3, stack);
            stack.pop();
            sb.append(sb3.toString());
        }
    }

    private boolean haveVisitedNodeAlready(Node node, Stack<Node> stack) {
        Iterator<Node> it = stack.iterator();
        while (it.hasNext()) {
            if (equalNodes(node, it.next())) {
                return true;
            }
        }
        return false;
    }

    private String lookupKey(String str) {
        String property;
        String property2 = this.propertyContainer0.getProperty(str);
        if (property2 != null) {
            return property2;
        }
        PropertyContainer propertyContainer = this.propertyContainer1;
        if (propertyContainer != null && (property = propertyContainer.getProperty(str)) != null) {
            return property;
        }
        String systemProperty = OptionHelper.getSystemProperty(str, null);
        if (systemProperty != null) {
            return systemProperty;
        }
        String env = OptionHelper.getEnv(str);
        if (env != null) {
            return env;
        }
        return null;
    }

    public static String substituteVariable(String str, PropertyContainer propertyContainer, PropertyContainer propertyContainer2) throws ScanException {
        return new NodeToStringTransformer(tokenizeAndParseString(str), propertyContainer, propertyContainer2).transform();
    }

    private static Node tokenizeAndParseString(String str) throws ScanException {
        return new Parser(new Tokenizer(str).tokenize()).parse();
    }

    private String variableNodeValue(Node node) {
        return (String) ((Node) node.payload).payload;
    }

    public String transform() throws ScanException {
        StringBuilder sb = new StringBuilder();
        compileNode(this.node, sb, new Stack<>());
        return sb.toString();
    }
}
