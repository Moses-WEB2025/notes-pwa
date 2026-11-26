package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class NlpCensor {

    public enum ActionSuggestion {
        pass("pass", 0),
        review("review", 1),
        block("block", 2);

        private String _name;
        private int id;

        ActionSuggestion(String str, int i) {
            this._name = str;
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this._name;
        }
    }

    public enum PoliticsLabel {
        normal("normal", 0),
        politics("politics", 1);

        private String _name;
        private int id;

        PoliticsLabel(String str, int i) {
            this._name = str;
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this._name;
        }
    }

    public enum PornpicLabel {
        normal("normal", 0),
        sexy("sexy", 1),
        porn("porn", 2);

        private String _name;
        private int id;

        PornpicLabel(String str, int i) {
            this._name = str;
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this._name;
        }
    }

    public enum VulgarLabel {
        normal("normal", 0),
        marginal("marginal", 1),
        vulgar("vulgar", 2);

        private String _name;
        private int id;

        VulgarLabel(String str, int i) {
            this._name = str;
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this._name;
        }
    }

    public static class ModelRst {

        @Required
        private int label;

        @Required
        private double prob;

        public ModelRst() {
        }

        public ModelRst(int i, double d) {
            this.label = i;
            this.prob = d;
        }

        @Required
        public ModelRst setLabel(int i) {
            this.label = i;
            return this;
        }

        @Required
        public int getLabel() {
            return this.label;
        }

        @Required
        public ModelRst setProb(double d) {
            this.prob = d;
            return this;
        }

        @Required
        public double getProb() {
            return this.prob;
        }
    }

    public static class PoliticsRequest {
        private Optional<String> content_text = Optional.empty();

        @Required
        private String text;

        public PoliticsRequest() {
        }

        public PoliticsRequest(String str) {
            this.text = str;
        }

        @Required
        public PoliticsRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        public PoliticsRequest setContentText(String str) {
            this.content_text = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getContentText() {
            return this.content_text;
        }
    }

    public static class PoliticsResponse {

        @Required
        private ActionSuggestion action_suggestion;

        @Required
        private PoliticsLabel label;
        private Optional<ModelRst> model_rst = Optional.empty();
        private Optional<RuleRst> rule_rst = Optional.empty();

        public PoliticsResponse() {
        }

        public PoliticsResponse(ActionSuggestion actionSuggestion, PoliticsLabel politicsLabel) {
            this.action_suggestion = actionSuggestion;
            this.label = politicsLabel;
        }

        @Required
        public PoliticsResponse setActionSuggestion(ActionSuggestion actionSuggestion) {
            this.action_suggestion = actionSuggestion;
            return this;
        }

        @Required
        public ActionSuggestion getActionSuggestion() {
            return this.action_suggestion;
        }

        @Required
        public PoliticsResponse setLabel(PoliticsLabel politicsLabel) {
            this.label = politicsLabel;
            return this;
        }

        @Required
        public PoliticsLabel getLabel() {
            return this.label;
        }

        public PoliticsResponse setModelRst(ModelRst modelRst) {
            this.model_rst = Optional.ofNullable(modelRst);
            return this;
        }

        public Optional<ModelRst> getModelRst() {
            return this.model_rst;
        }

        public PoliticsResponse setRuleRst(RuleRst ruleRst) {
            this.rule_rst = Optional.ofNullable(ruleRst);
            return this;
        }

        public Optional<RuleRst> getRuleRst() {
            return this.rule_rst;
        }
    }

    public static class PornpicModelRst {

        @Required
        private ActionSuggestion action_suggestion;

        @Required
        private PornpicLabel label;

        @Required
        private double prob;
        private Optional<Integer> code = Optional.empty();
        private Optional<String> url = Optional.empty();

        public PornpicModelRst() {
        }

        public PornpicModelRst(ActionSuggestion actionSuggestion, PornpicLabel pornpicLabel, double d) {
            this.action_suggestion = actionSuggestion;
            this.label = pornpicLabel;
            this.prob = d;
        }

        @Required
        public PornpicModelRst setActionSuggestion(ActionSuggestion actionSuggestion) {
            this.action_suggestion = actionSuggestion;
            return this;
        }

        @Required
        public ActionSuggestion getActionSuggestion() {
            return this.action_suggestion;
        }

        @Required
        public PornpicModelRst setLabel(PornpicLabel pornpicLabel) {
            this.label = pornpicLabel;
            return this;
        }

        @Required
        public PornpicLabel getLabel() {
            return this.label;
        }

        @Required
        public PornpicModelRst setProb(double d) {
            this.prob = d;
            return this;
        }

        @Required
        public double getProb() {
            return this.prob;
        }

        public PornpicModelRst setCode(int i) {
            this.code = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getCode() {
            return this.code;
        }

        public PornpicModelRst setUrl(String str) {
            this.url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getUrl() {
            return this.url;
        }
    }

    public static class PornpicRequest {
        private Optional<String> dataId = Optional.empty();

        @Required
        private String url;

        public PornpicRequest() {
        }

        public PornpicRequest(String str) {
            this.url = str;
        }

        @Required
        public PornpicRequest setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        public PornpicRequest setDataId(String str) {
            this.dataId = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDataId() {
            return this.dataId;
        }
    }

    public static class PornpicResponse {

        @Required
        private String dataId;

        @Required
        private PornpicModelRst model_rst;

        public PornpicResponse() {
        }

        public PornpicResponse(PornpicModelRst pornpicModelRst, String str) {
            this.model_rst = pornpicModelRst;
            this.dataId = str;
        }

        @Required
        public PornpicResponse setModelRst(PornpicModelRst pornpicModelRst) {
            this.model_rst = pornpicModelRst;
            return this;
        }

        @Required
        public PornpicModelRst getModelRst() {
            return this.model_rst;
        }

        @Required
        public PornpicResponse setDataId(String str) {
            this.dataId = str;
            return this;
        }

        @Required
        public String getDataId() {
            return this.dataId;
        }
    }

    public static class RuleRst {

        @Required
        private int forbidden;

        @Required
        private int label;
        private Optional<List<String>> rule = Optional.empty();
        private Optional<List<String>> tag = Optional.empty();

        public RuleRst() {
        }

        public RuleRst(int i, int i2) {
            this.forbidden = i;
            this.label = i2;
        }

        @Required
        public RuleRst setForbidden(int i) {
            this.forbidden = i;
            return this;
        }

        @Required
        public int getForbidden() {
            return this.forbidden;
        }

        @Required
        public RuleRst setLabel(int i) {
            this.label = i;
            return this;
        }

        @Required
        public int getLabel() {
            return this.label;
        }

        public RuleRst setRule(List<String> list) {
            this.rule = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getRule() {
            return this.rule;
        }

        public RuleRst setTag(List<String> list) {
            this.tag = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getTag() {
            return this.tag;
        }
    }

    public static class VulgarRequest {

        @Required
        private String text;

        public VulgarRequest() {
        }

        public VulgarRequest(String str) {
            this.text = str;
        }

        @Required
        public VulgarRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    public static class VulgarResponse {

        @Required
        private ActionSuggestion action_suggestion;
        private Optional<List<String>> hit = Optional.empty();

        @Required
        private VulgarLabel label;

        @Required
        private double prob;

        public VulgarResponse() {
        }

        public VulgarResponse(ActionSuggestion actionSuggestion, VulgarLabel vulgarLabel, double d) {
            this.action_suggestion = actionSuggestion;
            this.label = vulgarLabel;
            this.prob = d;
        }

        @Required
        public VulgarResponse setActionSuggestion(ActionSuggestion actionSuggestion) {
            this.action_suggestion = actionSuggestion;
            return this;
        }

        @Required
        public ActionSuggestion getActionSuggestion() {
            return this.action_suggestion;
        }

        @Required
        public VulgarResponse setLabel(VulgarLabel vulgarLabel) {
            this.label = vulgarLabel;
            return this;
        }

        @Required
        public VulgarLabel getLabel() {
            return this.label;
        }

        @Required
        public VulgarResponse setProb(double d) {
            this.prob = d;
            return this;
        }

        @Required
        public double getProb() {
            return this.prob;
        }

        public VulgarResponse setHit(List<String> list) {
            this.hit = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getHit() {
            return this.hit;
        }
    }
}
