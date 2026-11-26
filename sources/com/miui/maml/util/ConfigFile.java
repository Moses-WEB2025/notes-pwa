package com.miui.maml.util;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.maml.folme.AnimatedProperty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import miui.content.res.ThemeNativeUtils;
import miui.content.res.ThemeResources;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public class ConfigFile {
    public static final String DATA = "data";
    private static final String LOG_TAG = "ConfigFile";
    private static final String METHOD = "saveConfigFile";
    public static final String PATH = "path";
    public static final String TAG_APP_PICKER = "AppPicker";
    public static final String TAG_CHECK_BOX = "CheckBox";
    private static final String TAG_GADGET = "Gadget";
    private static final String TAG_GADGETS = "Gadgets";
    public static final String TAG_GROUP = "Group";
    public static final String TAG_IMAGE_PICKER = "ImagePicker";
    public static final String TAG_NUMBER_CHOICE = "NumberChoice";
    public static final String TAG_NUMBER_INPUT = "NumberInput";
    private static final String TAG_ROOT = "Config";
    public static final String TAG_STRING_CHOICE = "StringChoice";
    public static final String TAG_STRING_INPUT = "StringInput";
    private static final String TAG_TASK = "Intent";
    private static final String TAG_TASKS = "Tasks";
    private static final String TAG_VARIABLE = "Variable";
    private static final String TAG_VARIABLES = "Variables";
    private static final String URI = "content://com.miui.maml.provider";
    private boolean mDirty;
    private String mFilePath;
    private boolean mSaveViaProvider;
    private HashMap<String, Variable> mVariables = new HashMap<>();
    private HashMap<String, Task> mTasks = new HashMap<>();
    private ArrayList<Gadget> mGadgets = new ArrayList<>();

    private interface OnLoadElementListener {
        void OnLoadElement(Element element);
    }

    public static class Variable {
        public String name;
        public String type;
        public String value;
    }

    public static class Gadget {
        public String path;
        public int x;
        public int y;

        public Gadget(String str, int i, int i2) {
            this.path = str;
            this.x = i;
            this.y = i2;
        }
    }

    public Collection<Variable> getVariables() {
        return this.mVariables.values();
    }

    public Collection<Task> getTasks() {
        return this.mTasks.values();
    }

    public Collection<Gadget> getGadgets() {
        return this.mGadgets;
    }

    public boolean save(Context context) {
        boolean z = this.mDirty;
        this.mDirty = false;
        return !z || save(this.mFilePath, context);
    }

    public boolean save(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        writeTag(sb, TAG_ROOT, false);
        writeVariables(sb);
        writeTasks(sb);
        writeGadgets(sb);
        writeTag(sb, TAG_ROOT, true);
        if (this.mSaveViaProvider) {
            new SaveAsyncTask(context, sb.toString(), str).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            return true;
        }
        Utils.removeFile(str);
        String str2 = null;
        try {
            createNewFile(str);
        } catch (IOException e) {
            try {
                File externalFilesDir = context.getExternalFilesDir(null);
                str2 = externalFilesDir != null ? externalFilesDir.getPath() + File.separator + FilePathConstant.TEMP : null;
                createNewFile(str2);
            } catch (Exception unused) {
                Log.e(LOG_TAG, "create target file failed" + e);
                return false;
            }
        } catch (Exception e2) {
            Log.e(LOG_TAG, "create target file or temp file failed" + e2);
            return false;
        }
        try {
            if (new File(str).exists()) {
                Utils.writeDataToFile(str, sb.toString());
            } else {
                if (TextUtils.isEmpty(str2) || !new File(str2).exists()) {
                    Log.w(LOG_TAG, "target file and temp file are not exists");
                    return false;
                }
                Utils.writeDataToFile(str2, sb.toString());
                Utils.copyFile(str2, str);
                Utils.removeFile(str2);
            }
            ThemeNativeUtils.updateFilePermissionWithThemeContext(str);
            return true;
        } catch (Exception e3) {
            Log.e(LOG_TAG, "write file error" + e3);
            return false;
        }
    }

    public void setSaveViaProvider(boolean z) {
        this.mSaveViaProvider = z;
    }

    public boolean load(String str) {
        return load(str, false);
    }

    public boolean loadByIconStream(String str) {
        return load(str, true);
    }

    private boolean load(String str, boolean z) {
        this.mFilePath = str;
        this.mVariables.clear();
        this.mTasks.clear();
        DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
        try {
            InputStream iconStream = z ? ThemeResources.getSystem().getIconStream(str, (long[]) null) : new FileInputStream(str);
            if (iconStream != null) {
                try {
                    Element documentElement = documentBuilderFactoryNewInstance.newDocumentBuilder().parse(iconStream).getDocumentElement();
                    if (documentElement == null) {
                        if (iconStream != null) {
                        }
                        return false;
                    }
                    if (documentElement.getNodeName().equals(TAG_ROOT)) {
                        loadVariables(documentElement);
                        loadTasks(documentElement);
                        loadGadgets(documentElement);
                        if (iconStream != null) {
                            iconStream.close();
                        }
                        return true;
                    }
                    if (iconStream == null) {
                        return false;
                    }
                    e.printStackTrace();
                    return false;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        if (iconStream != null) {
                            try {
                                iconStream.close();
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                            }
                        }
                        throw th2;
                    }
                }
            }
            if (iconStream == null) {
                return false;
            }
            iconStream.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getVariable(String str) {
        Variable variable = this.mVariables.get(str);
        if (variable == null) {
            return null;
        }
        return variable.value;
    }

    public void putString(String str, String str2) {
        put(str, str2, TypedValues.Custom.S_STRING);
        this.mDirty = true;
    }

    public void putNumber(String str, String str2) {
        put(str, str2, "number");
        this.mDirty = true;
    }

    public void putNumber(String str, double d) {
        putNumber(str, Utils.doubleToString(d));
    }

    public void putTask(Task task) {
        if (task == null || TextUtils.isEmpty(task.id)) {
            return;
        }
        this.mTasks.put(task.id, task);
        this.mDirty = true;
    }

    public void putGadget(Gadget gadget) {
        if (gadget == null) {
            return;
        }
        this.mGadgets.add(gadget);
        this.mDirty = true;
    }

    public void removeGadget(Gadget gadget) {
        this.mGadgets.remove(gadget);
        this.mDirty = true;
    }

    public void moveGadget(Gadget gadget, int i) {
        if (this.mGadgets.remove(gadget)) {
            this.mGadgets.add(i, gadget);
        }
    }

    public Task getTask(String str) {
        return this.mTasks.get(str);
    }

    public void loadDefaultSettings(Element element) {
        if (element == null || !element.getNodeName().equals(TAG_ROOT)) {
            return;
        }
        Utils.traverseXmlElementChildren(element, "Group", new Utils.XmlTraverseListener() { // from class: com.miui.maml.util.ConfigFile.1
            @Override // com.miui.maml.util.Utils.XmlTraverseListener
            public void onChild(Element element2) {
                Utils.traverseXmlElementChildren(element2, null, new Utils.XmlTraverseListener() { // from class: com.miui.maml.util.ConfigFile.1.1
                    @Override // com.miui.maml.util.Utils.XmlTraverseListener
                    public void onChild(Element element3) {
                        String nodeName = element3.getNodeName();
                        String attribute = element3.getAttribute("id");
                        if (ConfigFile.TAG_STRING_INPUT.equals(nodeName)) {
                            ConfigFile.this.putString(attribute, element3.getAttribute("default"));
                            return;
                        }
                        if (ConfigFile.TAG_CHECK_BOX.equals(nodeName)) {
                            ConfigFile.this.putNumber(attribute, element3.getAttribute("default").equals("1") ? "1" : "0");
                            return;
                        }
                        if (ConfigFile.TAG_NUMBER_INPUT.equals(nodeName)) {
                            ConfigFile.this.putNumber(attribute, Utils.doubleToString(Utils.getAttrAsFloat(element3, "default", 0.0f)));
                            return;
                        }
                        if (ConfigFile.TAG_STRING_CHOICE.equals(nodeName)) {
                            ConfigFile.this.putString(attribute, element3.getAttribute("default"));
                        } else if (ConfigFile.TAG_NUMBER_CHOICE.equals(nodeName)) {
                            ConfigFile.this.putNumber(attribute, Utils.doubleToString(Utils.getAttrAsFloat(element3, "default", 0.0f)));
                        } else if (ConfigFile.TAG_APP_PICKER.equals(nodeName)) {
                            ConfigFile.this.putTask(Task.load(element3));
                        }
                    }
                });
            }
        });
    }

    private void createNewFile(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        file.getParentFile().mkdirs();
        file.delete();
        file.createNewFile();
    }

    private void writeVariables(StringBuilder sb) {
        if (this.mVariables.size() == 0) {
            return;
        }
        writeTag(sb, TAG_VARIABLES, false);
        String[] strArr = {"name", "type", "value"};
        for (Variable variable : this.mVariables.values()) {
            writeTag(sb, "Variable", strArr, new String[]{variable.name, variable.type, variable.value});
        }
        writeTag(sb, TAG_VARIABLES, true);
    }

    private void writeTasks(StringBuilder sb) {
        if (this.mTasks.size() == 0) {
            return;
        }
        writeTag(sb, TAG_TASKS, false);
        String[] strArr = {Task.TAG_ID, Task.TAG_ACTION, Task.TAG_TYPE, Task.TAG_CATEGORY, Task.TAG_PACKAGE, Task.TAG_CLASS, Task.TAG_NAME};
        for (Task task : this.mTasks.values()) {
            writeTag(sb, TAG_TASK, strArr, new String[]{task.id, task.action, task.type, task.category, task.packageName, task.className, task.name}, true);
        }
        writeTag(sb, TAG_TASKS, true);
    }

    private void writeGadgets(StringBuilder sb) {
        if (this.mGadgets.size() == 0) {
            return;
        }
        writeTag(sb, TAG_GADGETS, false);
        String[] strArr = {"path", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y};
        for (Gadget gadget : this.mGadgets) {
            writeTag(sb, TAG_GADGET, strArr, new String[]{gadget.path, String.valueOf(gadget.x), String.valueOf(gadget.y)}, true);
        }
        writeTag(sb, TAG_GADGETS, true);
    }

    private static void writeTag(StringBuilder sb, String str, boolean z) {
        sb.append("<");
        if (z) {
            sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        }
        sb.append(str);
        sb.append(">\n");
    }

    private static void writeTag(StringBuilder sb, String str, String[] strArr, String[] strArr2) {
        writeTag(sb, str, strArr, strArr2, true);
    }

    private static void writeTag(StringBuilder sb, String str, String[] strArr, String[] strArr2, boolean z) {
        sb.append("<");
        sb.append(str);
        for (int i = 0; i < strArr.length; i++) {
            if (!z || !TextUtils.isEmpty(strArr2[i])) {
                sb.append(StringUtils.SPACE);
                sb.append(strArr[i]);
                sb.append("=\"");
                sb.append(strArr2[i]);
                sb.append("\"");
            }
        }
        sb.append("/>\n");
    }

    private void loadVariables(Element element) {
        loadList(element, TAG_VARIABLES, "Variable", new OnLoadElementListener() { // from class: com.miui.maml.util.ConfigFile.2
            @Override // com.miui.maml.util.ConfigFile.OnLoadElementListener
            public void OnLoadElement(Element element2) {
                ConfigFile.this.put(element2.getAttribute("name"), element2.getAttribute("value"), element2.getAttribute("type"));
            }
        });
    }

    private void loadTasks(Element element) {
        loadList(element, TAG_TASKS, TAG_TASK, new OnLoadElementListener() { // from class: com.miui.maml.util.ConfigFile.3
            @Override // com.miui.maml.util.ConfigFile.OnLoadElementListener
            public void OnLoadElement(Element element2) {
                ConfigFile.this.putTask(Task.load(element2));
            }
        });
    }

    private void loadGadgets(Element element) {
        loadList(element, TAG_GADGETS, TAG_GADGET, new OnLoadElementListener() { // from class: com.miui.maml.util.ConfigFile.4
            @Override // com.miui.maml.util.ConfigFile.OnLoadElementListener
            public void OnLoadElement(Element element2) {
                if (element2 != null) {
                    ConfigFile.this.putGadget(new Gadget(element2.getAttribute("path"), Utils.getAttrAsInt(element2, AnimatedProperty.PROPERTY_NAME_X, 0), Utils.getAttrAsInt(element2, AnimatedProperty.PROPERTY_NAME_Y, 0)));
                }
            }
        });
    }

    private void loadList(Element element, String str, String str2, OnLoadElementListener onLoadElementListener) {
        Element child = Utils.getChild(element, str);
        if (child == null) {
            return;
        }
        NodeList childNodes = child.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 1 && nodeItem.getNodeName().equals(str2)) {
                onLoadElementListener.OnLoadElement((Element) nodeItem);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void put(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            return;
        }
        if (TypedValues.Custom.S_STRING.equals(str3) || "number".equals(str3)) {
            Variable variable = this.mVariables.get(str);
            if (variable == null) {
                variable = new Variable();
                variable.name = str;
                this.mVariables.put(str, variable);
            }
            variable.type = str3;
            variable.value = str2;
        }
    }

    private class SaveAsyncTask extends AsyncTask<Void, Void, Void> {
        private Context mContext;
        private String mData;
        private String mPath;

        public SaveAsyncTask(Context context, String str, String str2) {
            this.mContext = context;
            this.mData = str;
            this.mPath = str2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            try {
                Uri uri = Uri.parse(ConfigFile.URI);
                Bundle bundle = new Bundle();
                bundle.putString("data", this.mData);
                bundle.putString("path", this.mPath);
                this.mContext.getContentResolver().call(uri, ConfigFile.METHOD, (String) null, bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
