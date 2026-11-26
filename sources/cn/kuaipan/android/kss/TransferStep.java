package cn.kuaipan.android.kss;

/* JADX INFO: loaded from: classes.dex */
public enum TransferStep {
    DOWNLOAD_REQUEST_BIZ_HTTP("download_request_biz_http"),
    DOWNLOAD_PREPARE("download_prepare"),
    DOWNLOAD_PREPARE_DECODE_KEY("download_prepare_decode_key"),
    DOWNLOAD_REQUEST_XMS_HTTP("download_request_XMS_http"),
    DOWNLOAD_GET_CONTENT("download_get_content"),
    DOWNLOAD_READ_STREAM("download_read_stream"),
    DOWNLOAD_WRITE_FILE("download_write_file"),
    DOWNLOAD_VERIFY_FILE("download_verify_file"),
    DOWNLOAD_PREPARE_E2EE_APPKEY("download_prepare_e2ee_appKey"),
    UPLOAD_REQUEST_BIZ_HTTP("upload_request_biz_http"),
    UPLOAD_GET_FILE_INFO("upload_get_file_info"),
    UPLOAD_PREPARE("upload_prepare"),
    UPLOAD_PREPARE_ENCODE_KEY("upload_prepare_encode_key"),
    UPLOAD_PREPARE_STREAM("upload_prepare_stream"),
    UPLOAD_REQUEST_XMS_HTTP("upload_request_XMS_http"),
    UPLOAD_REQUEST_COMMIT("upload_request_commit"),
    UPLOAD_THUMBNAIL_REQUEST_BIZ_HTTP("thumbnail_request_biz_http"),
    UPLOAD_THUMBNAIL_REQUEST_COMMIT("upload_thumbnail_request_commit"),
    UPLOAD_THUMBNAIL_PREPARE_ENCODE_KEY("upload_thumbnail_prepare_encode_key"),
    UPLOAD_THUMBNAIL_PREPARE_STREAM("upload_thumbnail_prepare_stream"),
    UPLOAD_THUMBNAIL_REQUEST_XMS_HTTP("upload_thumbnail_request_xms_http");

    private String label;

    TransferStep(String str) {
        this.label = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "TransferStep{label='" + this.label + "'}";
    }
}
