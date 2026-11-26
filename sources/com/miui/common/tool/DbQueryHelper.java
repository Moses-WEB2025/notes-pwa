package com.miui.common.tool;

/* JADX INFO: loaded from: classes2.dex */
public class DbQueryHelper {
    public static String formattedKeyWords(String str) {
        return str.replaceAll("([_%/])", "/$1").replaceAll("'", "''");
    }

    public static String createLikeSelection(String str, String str2) {
        StringBuilder sb = new StringBuilder("LIKE ('%");
        sb.append(str2).append("%',").append(str).append(",'/')");
        return sb.toString();
    }

    public static StringBuilder wrapAndSelection(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(" AND ( ").append(str).append(" ) ");
        return sb;
    }

    public static StringBuilder wrapOrSelection(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(" OR ( ").append(str).append(" ) ");
        return sb;
    }

    public static StringBuilder wrapBracketSelection(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ( ").append(str).append(" ) ");
        return sb;
    }
}
