package com.miui.notes.excerpt;

import android.content.Context;
import com.miui.notes.base.utils.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes4.dex */
public class JsUtils {
    public static String getFromAssets(Context context, String str) throws Throwable {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            inputStreamReader = new InputStreamReader(context.getResources().getAssets().open(str));
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                            th = th;
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                try {
                                    bufferedReader2.close();
                                } catch (IOException e) {
                                    Logger.INSTANCE.e("NoteApp", "bufReader error :" + e);
                                }
                            }
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                    throw th;
                                } catch (IOException e2) {
                                    Logger.INSTANCE.e("NoteApp", "inputReader error :" + e2);
                                    throw th;
                                }
                            }
                            throw th;
                        }
                        String string = sb.toString();
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            Logger.INSTANCE.e("NoteApp", "bufReader error :" + e3);
                        }
                        try {
                            inputStreamReader.close();
                        } catch (IOException e4) {
                            Logger.INSTANCE.e("NoteApp", "inputReader error :" + e4);
                        }
                        return string;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                    }
                } catch (Exception e5) {
                    e = e5;
                    Logger.INSTANCE.e("NoteApp", "getFromAssets error :" + e);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e6) {
                            Logger.INSTANCE.e("NoteApp", "bufReader error :" + e6);
                        }
                    }
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e7) {
                            Logger.INSTANCE.e("NoteApp", "inputReader error :" + e7);
                        }
                    }
                    return null;
                }
            } catch (Exception e8) {
                e = e8;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e9) {
            e = e9;
            bufferedReader = null;
            inputStreamReader = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = null;
        }
    }
}
