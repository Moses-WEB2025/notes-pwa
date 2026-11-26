package com.xiaomi.miai.api;

import com.xiaomi.miai.api.common.ApiNameMapping;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AIApiNameMapping implements ApiNameMapping {
    private static Map<String, Class<?>> classMapping;

    static {
        HashMap map = new HashMap();
        classMapping = map;
        map.put(AIApiConstants.SpeechGeneral.Exception, SpeechGeneral.Exception.class);
        classMapping.put(AIApiConstants.SpeechGeneral.RecognizeStreamEnded, SpeechGeneral.RecognizeStreamEnded.class);
        classMapping.put(AIApiConstants.SpeechGeneral.RecognizeStreamStarted, SpeechGeneral.RecognizeStreamStarted.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechAsr.ConferenceInfoUpdate, SpeechMiAiSpeechAsr.ConferenceInfoUpdate.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechAsr.SpeechConferenceResult, SpeechMiAiSpeechAsr.SpeechConferenceResult.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechAsr.SpeechRecognize, SpeechMiAiSpeechAsr.SpeechRecognize.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechAsr.SpeechRecognizeError, SpeechMiAiSpeechAsr.SpeechRecognizeError.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechAsr.SpeechRecognizeFinish, SpeechMiAiSpeechAsr.SpeechRecognizeFinish.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechAsr.SpeechRecognizeResult, SpeechMiAiSpeechAsr.SpeechRecognizeResult.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechConference.ConferenceServiceInfoUpdate, SpeechMiAiSpeechConference.ConferenceServiceInfoUpdate.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechConference.SpeechConferenceResult, SpeechMiAiSpeechConference.ConferenceServiceResult.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechConference.SpeechConferenceVorResult, SpeechMiAiSpeechConference.SpeechConferenceVorResult.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechTranslation.ConferenceSpeechMessage, SpeechMiAiSpeechTranslation.ConferenceSpeechMessage.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechTranslation.Recognize, SpeechMiAiSpeechTranslation.Recognize.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechTranslation.TranslationItem, SpeechMiAiSpeechTranslation.TranslationItem.class);
        classMapping.put(AIApiConstants.SpeechMiAiSpeechTranslation.TranslationResult, SpeechMiAiSpeechTranslation.TranslationResult.class);
    }

    @Override // com.xiaomi.miai.api.common.ApiNameMapping
    public Class<?> findClass(String str, String str2) {
        return classMapping.get(str + "." + str2);
    }
}
