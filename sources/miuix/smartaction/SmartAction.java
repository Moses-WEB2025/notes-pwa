package miuix.smartaction;

import android.view.View;

/* JADX INFO: loaded from: classes6.dex */
public interface SmartAction {

    public interface Feature {
        public static final String PHRASE = "phrase";
        public static final String QUERY = "query";
        public static final String SELECT = "select";
        public static final String TRANSLATE = "translate";
    }

    void clearState();

    String getContextData();

    Object getObjectForClassify();

    String getSelectData();

    View getView();

    void insertContentToView(String str);

    boolean isFocused();

    boolean isPasswordInputType();

    void isSmartAction();

    boolean isSupportFeature(String str);

    boolean selectAllText();

    boolean selectCurrentWord();

    void update();
}
