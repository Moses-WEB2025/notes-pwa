package cn.kuaipan.android.exception;

import android.text.TextUtils;
import cn.kuaipan.android.kss.KssDef;
import cn.kuaipan.android.utils.TwoKeyHashMap;

/* JADX INFO: loaded from: classes.dex */
final class ServerMsgMap implements ErrorCode {
    private static final TwoKeyHashMap<Integer, String, Integer> CODE_MAP = new TwoKeyHashMap<>();

    ServerMsgMap() {
    }

    static {
        add2Map(202, ErrorCode.MSG202_BAD_ACCOUNT_FORMAT, "badEmailFormat");
        add2Map(202, ErrorCode.MSG202_ACCOUNT_CONFLICT, "sameEmailRegisteredBefore");
        add2Map(202, ErrorCode.MSG202_LOGIN_FAIL, "login fail");
        add2Map(202, ErrorCode.MSG202_BAD_OPENID, "bad openid");
        add2Map(202, ErrorCode.MSG202_WRONG_CODE, "wrong verification code");
        add2Map(202, ErrorCode.MSG202_CANNOT_MKROOT, "cannot create app folder");
        add2Map(202, ErrorCode.MSG202_BAD_ACCESS_CODE, "pickupCodeNotSupport");
        add2Map(202, ErrorCode.MSG202_LONG_ACCESS_CODE, "pickupCodeTooLong");
        add2Map(202, ErrorCode.MSG202_FILE_EXIST, "file exist");
        add2Map(202, ErrorCode.MSG202_FILE_NOT_EXIST, "file not exist");
        add2Map(202, ErrorCode.MSG202_FILE_TOO_MANY, "tooManyFiles");
        add2Map(202, ErrorCode.MSG202_FILE_TOO_LARGE, "file too large");
        add2Map(202, ErrorCode.MSG202_OVER_SPACE, "over space");
        add2Map(202, ErrorCode.MSG202_PATH_TOO_LONG, "fnameTooLong");
        add2Map(202, ErrorCode.MSG202_COMMIT_FAIL, "commit fail");
        add2Map(202, ErrorCode.MSG202_FORBIDDEN, "forbidden");
        add2Map(202, ErrorCode.MSG202_SERVER_DOWN, "account server error");
        add2Map(202, ErrorCode.MSG202_CYCLE_SHARE, "shared");
        add2Map(202, ErrorCode.MSG202_ACCOUNT_BINDED, "cannotBind");
        add2Map(400, ErrorCode.MSG400_BAD_PARAMS, "bad parameters");
        add2Map(400, ErrorCode.MSG400_BAD_REQEST, "bad request");
        add2Map(400, ErrorCode.MSG400_BAD_API, "no such api implemented");
        add2Map(400, ErrorCode.MSG400_BAD_PARAMS, "clientBadParams");
        add2Map(400, ErrorCode.MSG400_SERVER_ERR, "serverError");
        add2Map(400, ErrorCode.MSG400_ACCOUNT_SERVER_ERR, "accountServerError");
        add2Map(400, ErrorCode.MSG400_UNKNOW_ERR, "unknownError");
        add2Map(400, ErrorCode.MSG400_REQUEST_FAIL, "requestFail");
        add2Map(400, ErrorCode.MSG400_MOBILE_BINDED, "mobileExists");
        add2Map(400, ErrorCode.MSG400_SEND_MSG_ERR, "sendMsgError");
        add2Map(400, ErrorCode.MSG400_MANY_REQUEST, "tooManyRequests");
        add2Map(400, ErrorCode.MSG400_FREQ_REQUEST, "tooOften");
        add2Map(400, ErrorCode.MSG400_INVALID_CODE, "invalidCode");
        add2Map(400, ErrorCode.MSG400_INVALID_MOBILE, "invalidMobile");
        add2Map(400, ErrorCode.MSG400_EMPTY_PASSWORD, "emptyPassword");
        add2Map(400, ErrorCode.MSG400_LONG_PASSWORD, "passwordTooLong");
        add2Map(400, ErrorCode.MSG400_NOT_FOUND_USER, "noSuchUser");
        add2Map(400, ErrorCode.MSG400_EMPTY_PASSWORD, "needPassword");
        add2Map(400, ErrorCode.MSG400_CANNOT_SET_PWD, "canNotSetPassword");
        add2Map(400, ErrorCode.MSG400_NOT_REQUEST, "verifyNotRequest");
        add2Map(400, ErrorCode.MSG400_EXPIRED_CODE, "expiredCode");
        add2Map(400, ErrorCode.MSG400_FILE_NOT_EXIST, "file not exist");
        add2Map(401, ErrorCode.MSG401_BAD_SIGN, "bad signature");
        add2Map(401, ErrorCode.MSG401_REUSED_NONCE, "reused nonce");
        add2Map(401, ErrorCode.MSG401_BAD_CONSUMER, "bad consumer key");
        add2Map(401, ErrorCode.MSG401_REQUEST_EXPIRED, "request expired");
        add2Map(401, ErrorCode.MSG401_AUTHMODE_UNSUPPORT, "not supported auth mode");
        add2Map(401, ErrorCode.MSG401_AUTH_EXPIRED, "authorization expired");
        add2Map(401, ErrorCode.MSG401_APICALL_LIMIT, "api daily limit");
        add2Map(401, ErrorCode.MSG401_NOAPI_PERMISSION, "no right to call this api");
        add2Map(401, ErrorCode.MSG401_BAD_VERIFER, "bad verifier");
        add2Map(401, ErrorCode.MSG401_AUTH_FAILED, "authorization failed");
        add2Map(401, ErrorCode.MSG401_INVALID_TOKEN, "invalid token");
        add2Map(403, ErrorCode.MSG403_FILE_EXIST, "file exist");
        add2Map(403, ErrorCode.MSG403_FORBIDDEN, "forbidden");
        add2Map(404, ErrorCode.MSG404_FILE_NOT_EXIST, "file not exist");
        add2Map(406, ErrorCode.MSG406_FILE_TOO_MANY, "too many files");
        add2Map(413, ErrorCode.MSG413_FILE_TOO_LARGE, "file too large");
        add2Map(500, ErrorCode.MSG500_SERVER_ERR, "server error");
        add2Map(507, ErrorCode.MSG507_OVER_SPACE, "over space");
        add2Map(200, ErrorCode.MSG200_FILE_EXIST, "file exist");
        add2Map(200, ErrorCode.MSG200_COMMIT_FAIL, "commit fail");
        add2Map(200, ErrorCode.MSG200_BAD_PARAMS, "ERR_BAD_PARAMS");
        add2Map(200, ErrorCode.MSG200_SERVER_EXCEPTION, KssDef.ERR_SERVER_EXCEPTION);
        add2Map(200, ErrorCode.MSG200_INVALID_CUSTOMERID, "ERR_INVALID_CUSTOMERID");
        add2Map(200, ErrorCode.MSG200_INVALID_STOID, "ERR_INVALID_STOID");
        add2Map(200, ErrorCode.MSG200_STORAGE_REQUEST_ERROR, KssDef.ERR_STORAGE_REQUEST_ERROR);
        add2Map(200, ErrorCode.MSG200_STORAGE_REQUEST_FAILED, KssDef.ERR_STORAGE_REQUEST_FAILED);
        add2Map(200, ErrorCode.MSG200_ERR_CHUNK_OUT_OF_RANGE, KssDef.ERR_CHUNK_OUT_OF_RANGE);
        add2Map(200, ErrorCode.MSG200_ERR_INVALID_UPLOAD_ID, KssDef.ERR_INVALID_UPLOAD_ID);
        add2Map(200, ErrorCode.MSG200_ERR_INVALID_CHUNK_POS, KssDef.ERR_INVALID_CHUNK_POS);
        add2Map(200, ErrorCode.MSG200_ERR_INVALID_CHUNK_SIZE, KssDef.ERR_INVALID_CHUNK_SIZE);
        add2Map(200, ErrorCode.MSG200_ERR_CHUNK_CORRUPTED, KssDef.ERR_CHUNK_CORRUPTED);
        add2Map(200, ErrorCode.MSG200_ERR_BLOCK_CORRUPTED, KssDef.ERR_BLOCK_CORRUPTED);
        add2Map(200, ErrorCode.MSG200_ERR_TOO_MANY_CURRENT_BLOCKS, KssDef.ERR_TOO_MANY_CURRENT_BLOCKS);
        add2Map(200, ErrorCode.MSG200_ERR_STORAGE_COMMIT_ERROR, "ERR_STORAGE_COMMIT_ERROR");
        add2Map(200, ErrorCode.MSG200_ERR_INVALID_FILE_META, KssDef.ERR_INVALID_FILE_META);
        add2Map(200, ErrorCode.MSG200_ERR_INVALID_BLOCK_META, KssDef.ERR_INVALID_BLOCK_META);
        add2Map(200, ErrorCode.MSG200_FORBIDDEN, "forbidden");
        add2Map(200, ErrorCode.MSG200_OVER_SPACE, "over space");
        add2Map(200, ErrorCode.MSG200_TARGET_NOTEXIST, "targetNotExist");
        add2Map(200, ErrorCode.MSG200_STUB_FAIL, "get stub fail");
        add2Map(200, ErrorCode.MSG200_UNSUPPORTED_CHAR, "unsupportedCharRange");
        add2Map(200, ErrorCode.MSG200_DATA_OPER_FAIL, "dataOperationFailed");
        add2Map(200, ErrorCode.MSG200_FILE_TOO_LARGE, "file too large");
    }

    private static void add2Map(int i, int i2, String str) {
        CODE_MAP.put(Integer.valueOf(i), str == null ? null : str.toLowerCase(), Integer.valueOf(i2));
    }

    public static int getErrorCode(int i, String str) {
        Integer num = CODE_MAP.get(Integer.valueOf(i), TextUtils.isEmpty(str) ? null : str.trim().toLowerCase());
        if (num == null) {
            return 200000;
        }
        return num.intValue();
    }
}
