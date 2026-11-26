package com.xiaomi.miai.config;

import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Endpoints {
    public static Endpoints Production = new ProductionEndpoints();
    public static Endpoints Preview = new PreviewEndpoints();
    public static Endpoints Staging = new StagingEndpoints();

    public abstract String getAIAccountAddreses();

    public abstract String getMiaiSodApiAddress();

    public abstract String getXiaomiAccountAddress();

    public String getMiaiSodApiUrl(String str) {
        String miaiSodApiAddress = getMiaiSodApiAddress();
        if (str.startsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            str = str.substring(1);
        }
        return String.format("%s/%s", miaiSodApiAddress, str);
    }

    public static class ProductionEndpoints extends Endpoints {
        @Override // com.xiaomi.miai.config.Endpoints
        public String getMiaiSodApiAddress() {
            return "https://api.open.ai.xiaomi.com";
        }

        @Override // com.xiaomi.miai.config.Endpoints
        public String getXiaomiAccountAddress() {
            return "https://account.xiaomi.com";
        }

        @Override // com.xiaomi.miai.config.Endpoints
        public String getAIAccountAddreses() {
            return "https://account.ai.xiaomi.com";
        }
    }

    public static class PreviewEndpoints extends ProductionEndpoints {
        @Override // com.xiaomi.miai.config.Endpoints.ProductionEndpoints, com.xiaomi.miai.config.Endpoints
        public String getMiaiSodApiAddress() {
            return "https://api-preview.open.ai.xiaomi.com";
        }

        @Override // com.xiaomi.miai.config.Endpoints.ProductionEndpoints, com.xiaomi.miai.config.Endpoints
        public String getXiaomiAccountAddress() {
            return "https://account.xiaomi.com";
        }

        @Override // com.xiaomi.miai.config.Endpoints.ProductionEndpoints, com.xiaomi.miai.config.Endpoints
        public String getAIAccountAddreses() {
            return "https://account.ai.xiaomi.com";
        }
    }

    public static class StagingEndpoints extends Endpoints {
        @Override // com.xiaomi.miai.config.Endpoints
        public String getMiaiSodApiAddress() {
            return "http://api-staging.miai.srv";
        }

        @Override // com.xiaomi.miai.config.Endpoints
        public String getXiaomiAccountAddress() {
            return "http://open.account.preview.n.xiaomi.net";
        }

        @Override // com.xiaomi.miai.config.Endpoints
        public String getAIAccountAddreses() {
            return "http://account-staging.ai.xiaomi.com";
        }
    }
}
