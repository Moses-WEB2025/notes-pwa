package com.miui.maml.data;

import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.util.TextFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class VariableBinderManager implements ContentProviderBinder.QueryCompleteListener {
    private static final String LOG_TAG = "VariableBinderManager";
    public static final String TAG_NAME = "VariableBinders";
    private ScreenElementRoot mRoot;
    private ArrayList<VariableBinder> mVariableBinders = new ArrayList<>();

    public VariableBinderManager(Element element, ScreenElementRoot screenElementRoot) {
        this.mRoot = screenElementRoot;
        if (element != null) {
            load(element, screenElementRoot);
        }
    }

    public void init() {
        Iterator<VariableBinder> it = this.mVariableBinders.iterator();
        while (it.hasNext()) {
            it.next().init();
        }
    }

    public void tick() {
        Iterator<VariableBinder> it = this.mVariableBinders.iterator();
        while (it.hasNext()) {
            it.next().tick();
        }
    }

    public void finish() {
        Iterator<VariableBinder> it = this.mVariableBinders.iterator();
        while (it.hasNext()) {
            it.next().finish();
        }
    }

    public void pause() {
        Iterator<VariableBinder> it = this.mVariableBinders.iterator();
        while (it.hasNext()) {
            it.next().pause();
        }
    }

    public void resume() {
        Iterator<VariableBinder> it = this.mVariableBinders.iterator();
        while (it.hasNext()) {
            it.next().resume();
        }
    }

    public VariableBinder findBinder(String str) {
        for (VariableBinder variableBinder : this.mVariableBinders) {
            if (TextUtils.equals(str, variableBinder.getName())) {
                return variableBinder;
            }
        }
        return null;
    }

    public final void acceptVisitor(VariableBinderVisitor variableBinderVisitor) {
        Iterator<VariableBinder> it = this.mVariableBinders.iterator();
        while (it.hasNext()) {
            it.next().accept(variableBinderVisitor);
        }
    }

    private void load(Element element, ScreenElementRoot screenElementRoot) {
        if (element == null) {
            Log.e(LOG_TAG, "node is null");
            throw new NullPointerException("node is null");
        }
        loadBinders(element, screenElementRoot);
    }

    private static VariableBinder createBinder(Element element, ScreenElementRoot screenElementRoot, VariableBinderManager variableBinderManager) {
        VariableBinder settingsBinder;
        String tagName = element.getTagName();
        if (tagName.equalsIgnoreCase(ContentProviderBinder.TAG_NAME)) {
            settingsBinder = new ContentProviderBinder(element, screenElementRoot);
        } else if (tagName.equalsIgnoreCase(SensorBinder.TAG_NAME)) {
            settingsBinder = new SensorBinder(element, screenElementRoot);
        } else if (tagName.equalsIgnoreCase(BroadcastBinder.TAG_NAME)) {
            settingsBinder = new BroadcastBinder(element, screenElementRoot);
        } else if (tagName.equalsIgnoreCase(FileBinder.TAG_NAME)) {
            settingsBinder = new FileBinder(element, screenElementRoot);
        } else {
            settingsBinder = tagName.equalsIgnoreCase(SettingsBinder.TAG_NAME) ? new SettingsBinder(element, screenElementRoot) : null;
        }
        if (settingsBinder != null) {
            settingsBinder.setQueryCompleteListener(variableBinderManager);
        }
        return settingsBinder;
    }

    private void loadBinders(Element element, ScreenElementRoot screenElementRoot) {
        VariableBinder variableBinderCreateBinder;
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == 1 && (variableBinderCreateBinder = createBinder((Element) childNodes.item(i), screenElementRoot, this)) != null) {
                this.mVariableBinders.add(variableBinderCreateBinder);
            }
        }
    }

    public ContentProviderBinder.Builder addContentProviderBinder(String str) {
        return addContentProviderBinder(new TextFormatter(this.mRoot.getVariables(), str));
    }

    public ContentProviderBinder.Builder addContentProviderBinder(String str, String str2) {
        return addContentProviderBinder(new TextFormatter(this.mRoot.getVariables(), str, str2));
    }

    public ContentProviderBinder.Builder addContentProviderBinder(TextFormatter textFormatter) {
        ContentProviderBinder contentProviderBinder = new ContentProviderBinder(this.mRoot);
        contentProviderBinder.setQueryCompleteListener(this);
        contentProviderBinder.mUriFormatter = textFormatter;
        this.mVariableBinders.add(contentProviderBinder);
        return new ContentProviderBinder.Builder(contentProviderBinder);
    }

    @Override // com.miui.maml.data.ContentProviderBinder.QueryCompleteListener
    public void onQueryCompleted(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (VariableBinder variableBinder : this.mVariableBinders) {
            String dependency = variableBinder.getDependency();
            if (!TextUtils.isEmpty(dependency) && dependency.equals(str)) {
                variableBinder.startQuery();
            }
        }
    }
}
