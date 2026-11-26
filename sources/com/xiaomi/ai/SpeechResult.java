package com.xiaomi.ai;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechResult {
    String action;
    String answer;
    String answerText;
    String content;
    boolean deleteSuccess;
    String directive;
    int displayDuration;
    String domain;
    String extraMessage;
    String intention;
    boolean isRegFinal;
    boolean openMic;
    String query;
    JSONObject regResponse;
    JSONArray regResult;
    boolean regSuccess;
    String requestId;
    String response;
    String sessionId;
    String toDisplay;
    String toSpeak;
    int unknownDomainAction;
    int vadIdle;
    String voiceUserId;
    List<String> voiceUserIds;

    public String getAction() {
        return this.action;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getAnswerText() {
        return this.answerText;
    }

    public String getContent() {
        return this.content;
    }

    public String getDirective() {
        return this.directive;
    }

    public int getDisplayDuration() {
        return this.displayDuration;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getExtraMessage() {
        return this.extraMessage;
    }

    public String getIntention() {
        return this.intention;
    }

    public String getQuery() {
        return this.query;
    }

    public JSONObject getRegResponse() {
        return this.regResponse;
    }

    public JSONArray getRegResult() {
        return this.regResult;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getResponse() {
        return this.response;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getToDisplay() {
        return this.toDisplay;
    }

    public String getToSpeak() {
        return this.toSpeak;
    }

    public int getUnknownDomainAction() {
        return this.unknownDomainAction;
    }

    public int getVadIdle() {
        return this.vadIdle;
    }

    public List<String> getVoiceprintQueryUserIds() {
        return this.voiceUserIds;
    }

    public String getVoiceprintRecognizedUserId() {
        return this.voiceUserId;
    }

    public boolean isOpenMic() {
        return this.openMic;
    }

    public boolean isVoiceprintDeleteSuccess() {
        return this.deleteSuccess;
    }

    public boolean isVoiceprintRecognierSuccess() {
        return this.regSuccess;
    }

    public boolean isVoiceprintRegFinal() {
        return this.isRegFinal;
    }
}
