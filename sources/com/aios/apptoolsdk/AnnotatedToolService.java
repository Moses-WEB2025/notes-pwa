package com.aios.apptoolsdk;

import android.content.Context;
import android.util.Log;
import ch.qos.logback.classic.spi.CallerData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class AnnotatedToolService extends AppToolProviderService {
    private static final String TAG = "AnnotatedToolService";
    private static final String TOOLS_JSON = "app_tools.json";

    @Override // com.aios.apptoolsdk.AppToolProviderService
    protected List<AppTool> onCreateTools() {
        String strLoadToolsJson = loadToolsJson();
        if (strLoadToolsJson == null) {
            Log.e(TAG, "未找到 app_tools.json，请确认已添加 apptoolsdk-compiler 注解处理器依赖");
            return Collections.emptyList();
        }
        try {
            JSONArray jSONArray = new JSONArray(strLoadToolsJson);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                AppTool appToolCreateReflectiveTool = createReflectiveTool(jSONArray.getJSONObject(i));
                if (appToolCreateReflectiveTool != null) {
                    arrayList.add(appToolCreateReflectiveTool);
                }
            }
            Log.i(TAG, "从注解加载了 " + arrayList.size() + " 个工具");
            return arrayList;
        } catch (Exception e) {
            Log.e(TAG, "解析 app_tools.json 失败", e);
            return Collections.emptyList();
        }
    }

    private String loadToolsJson() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open(TOOLS_JSON), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    bufferedReader.close();
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "读取 app_tools.json 失败", e);
            return null;
        }
    }

    private AppTool createReflectiveTool(JSONObject jSONObject) {
        AppTool appTool;
        String str = "description";
        try {
            String string = jSONObject.getString("name");
            String string2 = jSONObject.getString("description");
            String string3 = jSONObject.getString(XMLWriter.METHOD);
            String string4 = jSONObject.getString("class");
            int i = jSONObject.getInt("paramCount");
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int i2 = 0;
            for (JSONArray jSONArray = jSONObject.getJSONArray("params"); i2 < jSONArray.length(); jSONArray = jSONArray) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                appTool = null;
                try {
                    String string5 = jSONObject2.getString("name");
                    String str2 = string;
                    String string6 = jSONObject2.getString("type");
                    String str3 = string2;
                    String string7 = jSONObject2.getString(str);
                    String str4 = str;
                    boolean z = jSONObject2.getBoolean("required");
                    arrayList.add(new ParamMeta(string5, string6, jSONObject2.getInt("index")));
                    arrayList2.add(createAppParam(string5, string6, string7, z));
                    i2++;
                    string = str2;
                    string2 = str3;
                    str = str4;
                } catch (Exception e) {
                    e = e;
                    Log.e(TAG, "创建工具失败: " + jSONObject.optString("name", CallerData.NA), e);
                    return appTool;
                }
            }
            String str5 = string;
            String str6 = string2;
            Class<?> cls = Class.forName(string4);
            Method methodFindMethod = findMethod(cls, string3, i);
            if (methodFindMethod == null) {
                Log.e(TAG, "未找到方法: " + string4 + "." + string3 + "(" + i + " params)");
                return null;
            }
            methodFindMethod.setAccessible(true);
            Object objResolveTargetObject = resolveTargetObject(cls);
            if (objResolveTargetObject == null) {
                Log.e(TAG, "无法创建目标对象: " + string4);
                return null;
            }
            return new ReflectiveAppTool(str5, str6, arrayList2, methodFindMethod, objResolveTargetObject, arrayList, i);
        } catch (Exception e2) {
            e = e2;
            appTool = null;
        }
    }

    private Method findMethod(Class<?> cls, String str, int i) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().equals(str) && method.getParameterTypes().length == i) {
                return method;
            }
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass == null || superclass == Object.class) {
            return null;
        }
        return findMethod(superclass, str, i);
    }

    private Object resolveTargetObject(Class<?> cls) {
        if (cls.isAssignableFrom(getClass())) {
            return this;
        }
        try {
            return cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            try {
                return cls.getDeclaredConstructor(Context.class).newInstance(this);
            } catch (Exception unused2) {
                return null;
            }
        }
    }

    private AppParam createAppParam(String str, String str2, String str3, boolean z) {
        str2.hashCode();
        switch (str2) {
            case "number":
                if (z) {
                    return AppParam.number(str, str3, true);
                }
                return AppParam.number(str, str3);
            case "string":
                if (z) {
                    return AppParam.string(str, str3, true);
                }
                return AppParam.string(str, str3);
            case "boolean":
                if (z) {
                    return AppParam.bool(str, str3, true);
                }
                return AppParam.bool(str, str3);
            case "integer":
                if (z) {
                    return AppParam.integer(str, str3, true);
                }
                return AppParam.integer(str, str3);
            default:
                return AppParam.string(str, str3, z);
        }
    }

    private class ReflectiveAppTool extends AppTool {
        private final Method method;
        private final int paramCount;
        private final List<ParamMeta> paramMetas;
        private final Object target;

        ReflectiveAppTool(String str, String str2, List<AppParam> list, Method method, Object obj, List<ParamMeta> list2, int i) {
            super(str, str2, list);
            this.method = method;
            this.target = obj;
            this.paramMetas = list2;
            this.paramCount = i;
        }

        @Override // com.aios.apptoolsdk.AppTool
        public AppResult execute(ToolArgs toolArgs) {
            try {
                Object objInvoke = this.method.invoke(this.target, buildInvokeArgs(toolArgs));
                if (objInvoke instanceof AppResult) {
                    return (AppResult) objInvoke;
                }
                return AppResult.error("方法返回类型不是 AppResult");
            } catch (Exception e) {
                Log.e(AnnotatedToolService.TAG, "反射调用 " + this.method.getName() + " 失败", e);
                Throwable cause = e.getCause();
                Exception cause2 = e;
                if (cause != null) {
                    cause2 = e.getCause();
                }
                return AppResult.error("执行异常: " + cause2.getMessage());
            }
        }

        private Object[] buildInvokeArgs(ToolArgs toolArgs) {
            Object[] objArr = new Object[this.paramCount];
            Class<?>[] parameterTypes = this.method.getParameterTypes();
            boolean[] zArr = new boolean[this.paramCount];
            for (ParamMeta paramMeta : this.paramMetas) {
                zArr[paramMeta.index] = true;
                objArr[paramMeta.index] = extractValue(toolArgs, paramMeta.name, paramMeta.type, parameterTypes[paramMeta.index]);
            }
            for (int i = 0; i < this.paramCount; i++) {
                if (!zArr[i]) {
                    objArr[i] = injectParam(parameterTypes[i]);
                }
            }
            return objArr;
        }

        private Object extractValue(ToolArgs toolArgs, String str, String str2, Class<?> cls) {
            if (!toolArgs.has(str)) {
                return null;
            }
            str2.hashCode();
            switch (str2) {
                case "number":
                    return toolArgs.number(str);
                case "string":
                    return toolArgs.string(str);
                case "boolean":
                    return toolArgs.bool(str);
                case "integer":
                    if (cls == Long.TYPE || cls == Long.class) {
                        return toolArgs.longValue(str);
                    }
                    return toolArgs.integer(str);
                default:
                    return toolArgs.string(str);
            }
        }

        private Object injectParam(Class<?> cls) {
            if (Context.class.isAssignableFrom(cls)) {
                return AnnotatedToolService.this;
            }
            return null;
        }
    }

    private static class ParamMeta {
        final int index;
        final String name;
        final String type;

        ParamMeta(String str, String str2, int i) {
            this.name = str;
            this.type = str2;
            this.index = i;
        }
    }
}
