package com.google.android.appfunctions.schema.internal.dependencies;

import com.miui.maml.elements.ListScreenElement;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes2.dex */
final class zzch {
    private static final char[] zza;

    static {
        char[] cArr = new char[80];
        zza = cArr;
        Arrays.fill(cArr, ' ');
    }

    static String zza(zzcf zzcfVar, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zzc(zzcfVar, sb, 0);
        return sb.toString();
    }

    static void zzb(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                zzb(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zzb(sb, i, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        zzd(i, sb);
        if (!str.isEmpty()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Character.toLowerCase(str.charAt(0)));
            for (int i2 = 1; i2 < str.length(); i2++) {
                char cCharAt = str.charAt(i2);
                if (Character.isUpperCase(cCharAt)) {
                    sb2.append("_");
                }
                sb2.append(Character.toLowerCase(cCharAt));
            }
            str = sb2.toString();
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"");
            sb.append(zzdc.zza(zzt.zzo((String) obj)));
            sb.append('\"');
            return;
        }
        if (obj instanceof zzt) {
            sb.append(": \"");
            sb.append(zzdc.zza((zzt) obj));
            sb.append('\"');
            return;
        }
        if (obj instanceof zzay) {
            sb.append(" {");
            zzc((zzay) obj, sb, i + 2);
            sb.append(StringUtils.LF);
            zzd(i, sb);
            sb.append(StringSubstitutor.DEFAULT_VAR_END);
            return;
        }
        if (!(obj instanceof Map.Entry)) {
            sb.append(": ");
            sb.append(obj);
            return;
        }
        int i3 = i + 2;
        sb.append(" {");
        Map.Entry entry = (Map.Entry) obj;
        zzb(sb, i3, "key", entry.getKey());
        zzb(sb, i3, "value", entry.getValue());
        sb.append(StringUtils.LF);
        zzd(i, sb);
        sb.append(StringSubstitutor.DEFAULT_VAR_END);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x020c  */
    /* JADX WARN: Code duplicated, block: B:128:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:129:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:130:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:131:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:132:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:133:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:134:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:136:0x020f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:57:0x0163  */
    /* JADX WARN: Code duplicated, block: B:59:0x017c  */
    /* JADX WARN: Code duplicated, block: B:61:0x0184  */
    /* JADX WARN: Code duplicated, block: B:63:0x0188  */
    /* JADX WARN: Code duplicated, block: B:66:0x0193  */
    /* JADX WARN: Code duplicated, block: B:68:0x0197  */
    /* JADX WARN: Code duplicated, block: B:71:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:73:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:76:0x01b4  */
    /* JADX WARN: Code duplicated, block: B:78:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:81:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:83:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:84:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:86:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:89:0x01e2  */
    /* JADX WARN: Code duplicated, block: B:91:0x01e6  */
    /* JADX WARN: Code duplicated, block: B:94:0x01f0  */
    /* JADX WARN: Code duplicated, block: B:99:0x01fe  */
    private static void zzc(zzcf zzcfVar, StringBuilder sb, int i) {
        int i2;
        Method method;
        Method method2;
        Object objInvokeOrDie;
        boolean zEquals;
        Method method3;
        Method method4;
        HashSet hashSet = new HashSet();
        HashMap map = new HashMap();
        TreeMap treeMap = new TreeMap();
        Method[] declaredMethods = zzcfVar.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i3 = 0;
        while (true) {
            i2 = 3;
            if (i3 >= length) {
                break;
            }
            Method method5 = declaredMethods[i3];
            if (!Modifier.isStatic(method5.getModifiers()) && method5.getName().length() >= 3) {
                if (method5.getName().startsWith("set")) {
                    hashSet.add(method5.getName());
                } else if (Modifier.isPublic(method5.getModifiers()) && method5.getParameterTypes().length == 0) {
                    if (method5.getName().startsWith("has")) {
                        map.put(method5.getName(), method5);
                    } else if (method5.getName().startsWith(CallMethod.METHOD_GET)) {
                        treeMap.put(method5.getName(), method5);
                    }
                }
            }
            i3++;
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            String strSubstring = ((String) entry.getKey()).substring(i2);
            if (strSubstring.endsWith(ListScreenElement.TAG_NAME) && !strSubstring.endsWith("OrBuilderList") && !strSubstring.equals(ListScreenElement.TAG_NAME) && (method4 = (Method) entry.getValue()) != null && method4.getReturnType().equals(List.class)) {
                zzb(sb, i, strSubstring.substring(0, strSubstring.length() - 4), zzay.invokeOrDie(method4, zzcfVar, new Object[0]));
            } else if (!strSubstring.endsWith("Map") || strSubstring.equals("Map") || (method3 = (Method) entry.getValue()) == null || !method3.getReturnType().equals(Map.class) || method3.isAnnotationPresent(Deprecated.class) || !Modifier.isPublic(method3.getModifiers())) {
                String.valueOf(strSubstring);
                if (hashSet.contains("set".concat(String.valueOf(strSubstring)))) {
                    if (strSubstring.endsWith("Bytes")) {
                        String strSubstring2 = strSubstring.substring(0, strSubstring.length() - 5);
                        String.valueOf(strSubstring2);
                        if (!treeMap.containsKey(CallMethod.METHOD_GET.concat(String.valueOf(strSubstring2)))) {
                            method = (Method) entry.getValue();
                            String.valueOf(strSubstring);
                            method2 = (Method) map.get("has".concat(String.valueOf(strSubstring)));
                            if (method != null) {
                                objInvokeOrDie = zzay.invokeOrDie(method, zzcfVar, new Object[0]);
                                if (method2 == null) {
                                    if (objInvokeOrDie instanceof Boolean) {
                                        if (((Boolean) objInvokeOrDie).booleanValue()) {
                                            zzb(sb, i, strSubstring, objInvokeOrDie);
                                        }
                                    } else if (objInvokeOrDie instanceof Integer) {
                                        if (((Integer) objInvokeOrDie).intValue() != 0) {
                                            zzb(sb, i, strSubstring, objInvokeOrDie);
                                        }
                                    } else if (objInvokeOrDie instanceof Float) {
                                        if (Float.floatToRawIntBits(((Float) objInvokeOrDie).floatValue()) != 0) {
                                            zzb(sb, i, strSubstring, objInvokeOrDie);
                                        }
                                    } else if (objInvokeOrDie instanceof Double) {
                                        if (objInvokeOrDie instanceof String) {
                                            zEquals = objInvokeOrDie.equals("");
                                        } else if (objInvokeOrDie instanceof zzt) {
                                            zEquals = objInvokeOrDie.equals(zzt.zza);
                                        } else if (objInvokeOrDie instanceof zzcf) {
                                            if (objInvokeOrDie != ((zzcf) objInvokeOrDie).getDefaultInstanceForType()) {
                                                zzb(sb, i, strSubstring, objInvokeOrDie);
                                            }
                                        } else if ((objInvokeOrDie instanceof Enum) || ((Enum) objInvokeOrDie).ordinal() != 0) {
                                            zzb(sb, i, strSubstring, objInvokeOrDie);
                                        }
                                        if (!zEquals) {
                                            zzb(sb, i, strSubstring, objInvokeOrDie);
                                        }
                                    } else if (Double.doubleToRawLongBits(((Double) objInvokeOrDie).doubleValue()) != 0) {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    }
                                } else if (((Boolean) zzay.invokeOrDie(method2, zzcfVar, new Object[0])).booleanValue()) {
                                    zzb(sb, i, strSubstring, objInvokeOrDie);
                                }
                            }
                        }
                    } else {
                        method = (Method) entry.getValue();
                        String.valueOf(strSubstring);
                        method2 = (Method) map.get("has".concat(String.valueOf(strSubstring)));
                        if (method != null) {
                            objInvokeOrDie = zzay.invokeOrDie(method, zzcfVar, new Object[0]);
                            if (method2 == null) {
                                if (objInvokeOrDie instanceof Boolean) {
                                    if (((Boolean) objInvokeOrDie).booleanValue()) {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    }
                                } else if (objInvokeOrDie instanceof Integer) {
                                    if (((Integer) objInvokeOrDie).intValue() != 0) {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    }
                                } else if (objInvokeOrDie instanceof Float) {
                                    if (Float.floatToRawIntBits(((Float) objInvokeOrDie).floatValue()) != 0) {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    }
                                } else if (objInvokeOrDie instanceof Double) {
                                    if (objInvokeOrDie instanceof String) {
                                        zEquals = objInvokeOrDie.equals("");
                                    } else if (objInvokeOrDie instanceof zzt) {
                                        zEquals = objInvokeOrDie.equals(zzt.zza);
                                    } else if (objInvokeOrDie instanceof zzcf) {
                                        if (objInvokeOrDie != ((zzcf) objInvokeOrDie).getDefaultInstanceForType()) {
                                            zzb(sb, i, strSubstring, objInvokeOrDie);
                                        }
                                    } else if (objInvokeOrDie instanceof Enum) {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    } else {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    }
                                    if (!zEquals) {
                                        zzb(sb, i, strSubstring, objInvokeOrDie);
                                    }
                                } else if (Double.doubleToRawLongBits(((Double) objInvokeOrDie).doubleValue()) != 0) {
                                    zzb(sb, i, strSubstring, objInvokeOrDie);
                                }
                            } else if (((Boolean) zzay.invokeOrDie(method2, zzcfVar, new Object[0])).booleanValue()) {
                                zzb(sb, i, strSubstring, objInvokeOrDie);
                            }
                        }
                    }
                }
            } else {
                zzb(sb, i, strSubstring.substring(0, strSubstring.length() - 3), zzay.invokeOrDie(method3, zzcfVar, new Object[0]));
            }
            i2 = 3;
        }
        if (zzcfVar instanceof zzau) {
            Iterator itZzc = ((zzau) zzcfVar).zza.zzc();
            while (itZzc.hasNext()) {
                Map.Entry entry2 = (Map.Entry) itZzc.next();
                int i4 = ((zzav) entry2.getKey()).zzb;
                StringBuilder sb2 = new StringBuilder(String.valueOf(i4).length() + 2);
                sb2.append("[");
                sb2.append(i4);
                sb2.append("]");
                zzb(sb, i, sb2.toString(), entry2.getValue());
            }
        }
        zzdf zzdfVar = ((zzay) zzcfVar).unknownFields;
        if (zzdfVar != null) {
            zzdfVar.zzj(sb, i);
        }
    }

    private static void zzd(int i, StringBuilder sb) {
        while (i > 0) {
            int i2 = 80;
            if (i <= 80) {
                i2 = i;
            }
            sb.append(zza, 0, i2);
            i -= i2;
        }
    }
}
