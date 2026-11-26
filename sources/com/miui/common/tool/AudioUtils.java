package com.miui.common.tool;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.UserHandle;
import android.text.TextUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.ThreadPoolUtils;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.xiaomi.xms.ai.recorder.AIRecorderManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AudioUtils {
    public static final int AUDIO_LABEL_LENGTH = 63;
    public static final int MAX_RECORD_SECONDS = 300;
    public static final int MAX_RECORD_SECONDS_PRO = 3600;
    public static final String MP3_MINI_TYPE = "audio/mp3";
    public static final String MP3_SUFFIX = ".mp3";
    public static final String NOTE_MP3_DIR_NAME = "mp3";
    public static final String PATH_DELETE_OGG = "/system/media/audio/ui/Delete.ogg";
    private static final int SHA1_MP3_STR_LEN = 44;
    private static final int SHA1_STR_LEN = 40;
    private static final String SHA1_STR_REG = "[0-9a-fA-F]{40}";
    private static final String TAG = "AudioUtils";
    public static final String TEMP_AUDIO_FLAG = "<sound fileid=\"\" />";
    public static final String TODO_MP3_DIR_NAME = "todo_mp3";
    static Integer mFinishSoundId;
    static volatile SoundPool mFinishSoundPool;
    public static final Pattern PATTERN_MODERN_AUDIO = Pattern.compile("<sound fileid=\"[0-9a-fA-F]{40}\\.mp3\" />", 8);
    public static final Pattern NEW_PATTERN_MODERN_AUDIO = Pattern.compile("<sound fileid=\"[0-9a-fA-F]{40}\\.mp3\" voicedes=\".*\" />", 8);
    private static volatile Boolean sSupportAIRecorderCache = null;

    public static byte[] shortToByte(short[] sArr, int i) {
        byte[] bArr = new byte[i * 2];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            short s = sArr[i2];
            bArr[i3 + 1] = (byte) (s >> 8);
            bArr[i3] = (byte) s;
        }
        return bArr;
    }

    public static String obtainMp3FilePath(String str) {
        return obtainMp3FilePath(str, String.valueOf(System.currentTimeMillis()), ".mp3");
    }

    public static File obtainMp3FileDir(String str) {
        File externalFilesDir = NoteApp.getInstance().getExternalFilesDir(null);
        if (externalFilesDir == null) {
            Logger.INSTANCE.e(TAG, "Fail to getExternalCacheDir.");
            return null;
        }
        File file = new File(externalFilesDir, str);
        if (file.exists() || file.mkdirs() || file.exists()) {
            return file;
        }
        Logger.INSTANCE.e(TAG, "Fail to create mp3Dir:" + file);
        return null;
    }

    public static String obtainMp3FilePath(String str, String str2, String str3) {
        try {
            return File.createTempFile(str2, str3, obtainMp3FileDir(str)).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNoteMp3FileExist(String str) {
        if (isMp3FileNameFormat(str)) {
            return !new File(obtainMp3FileDir(NOTE_MP3_DIR_NAME), str).exists();
        }
        return true;
    }

    public static boolean isTodoMp3FileExist(String str) {
        if (isMp3FileNameFormat(str)) {
            return new File(obtainMp3FileDir(TODO_MP3_DIR_NAME), str).exists();
        }
        return false;
    }

    public static boolean isMp3FileNameFormat(String str) {
        int length = str.length() - 44;
        if (length < 0) {
            length = 0;
        }
        return str.endsWith(".mp3") && isSha1StringFormat(str, length, str.length() - ".mp3".length());
    }

    public static boolean isSha1StringFormat(String str, int i, int i2) {
        if (i2 - i != 40) {
            return false;
        }
        return Pattern.compile(SHA1_STR_REG).matcher(str.substring(i, i2)).matches();
    }

    public static Set<String> checkNoteAudioValidity(Context context, SQLiteDatabase sQLiteDatabase) {
        HashSet hashSet = new HashSet();
        String[] strArr = {"note_id", "content", Notes.Data.DIRTY};
        Cursor cursorQuery = sQLiteDatabase == null ? context.getContentResolver().query(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, strArr, "mime_type = 'audio/mp3'", null, null, null) : sQLiteDatabase.query("data", strArr, "mime_type = 'audio/mp3'", null, null, null, null);
        try {
            if (cursorQuery == null) {
                Logger.INSTANCE.e(TAG, "Fail to get cursor when checkAudioValidity.");
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                while (!cursorQuery.isClosed() && cursorQuery.moveToNext() && !cursorQuery.isClosed()) {
                    String string = cursorQuery.getString(1);
                    int i = cursorQuery.getInt(2);
                    hashSet.add(string);
                    if (i == 0 && isNoteMp3FileExist(string)) {
                        long j = cursorQuery.getLong(0);
                        if (sb.length() != 0) {
                            sb.append(",");
                        }
                        sb.append(j);
                    }
                }
                if (sb.length() != 0) {
                    ContentValues contentValues = new ContentValues();
                    ContentValues contentValues2 = new ContentValues();
                    contentValues.put(Notes.Data.DIRTY, (Integer) 1);
                    contentValues2.put(Notes.Note.LOCAL_MODIFIED, (Integer) 1);
                    if (sQLiteDatabase == null) {
                        context.getContentResolver().update(Notes.Data.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues, "note_id in ( " + ((Object) sb) + " )", null);
                        context.getContentResolver().update(Notes.Note.CONTENT_URI_FOR_SYNC_ADAPTER, contentValues2, "_id in ( " + ((Object) sb) + " )", null);
                    } else {
                        sQLiteDatabase.update("data", contentValues, "note_id in ( " + ((Object) sb) + " )", null);
                        sQLiteDatabase.update("note", contentValues2, "_id in ( " + ((Object) sb) + " )", null);
                    }
                    cursorQuery.close();
                    return hashSet;
                }
                cursorQuery.close();
                return hashSet;
            } catch (Exception unused) {
                Logger.INSTANCE.e(TAG, "Make sure the Cursor is initialized correctly before accessing data from it");
                cursorQuery.close();
                return hashSet;
            }
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    public static String renameMp3FileToSha1(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            File file = new File(str2);
            if (file.exists()) {
                if (file.length() > 0) {
                    String str3 = AttachmentUtils.getFileSha1Digest(str2) + ".mp3";
                    File file2 = new File(obtainMp3FileDir(str), str3);
                    if (file2.exists()) {
                        file2.delete();
                    }
                    if (!file.renameTo(file2)) {
                        Logger.INSTANCE.e(TAG, "Fail to rename file from " + str2 + " to " + str3);
                    } else {
                        file2.setReadable(true, false);
                        Logger.INSTANCE.d(TAG, "Success to rename file from " + str2 + " to " + file2);
                        return str3;
                    }
                }
                file.delete();
            }
        }
        AttachmentUtils.recordFreshAttachment(str2);
        return "";
    }

    public static boolean isMp3Suffix(String str) {
        return !TextUtils.isEmpty(str) && str.endsWith(".mp3");
    }

    public static int parseTimeFromFile(String str, String str2) throws Throwable {
        MediaMetadataRetriever mediaMetadataRetriever;
        Throwable th;
        Exception e;
        File file = new File(obtainMp3FileDir(str), str2);
        if (!file.exists() || !file.getAbsolutePath().endsWith(".mp3")) {
            return 0;
        }
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                try {
                    mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                    String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
                    if (strExtractMetadata == null) {
                        Logger.INSTANCE.d(TAG, "extractMetadata is fail");
                        extracted(mediaMetadataRetriever);
                        return 0;
                    }
                    extracted(mediaMetadataRetriever);
                    long j = Long.parseLong(strExtractMetadata);
                    int parseFileTime = getParseFileTime((int) Math.round(j / 1000.0d), j);
                    PreferenceUtils.setAudioTimeByFileName(str2, parseFileTime);
                    return parseFileTime;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
                Logger.INSTANCE.e(TAG, "fail to parse time while path is " + file.getAbsolutePath() + ",exception:" + e);
                extracted(mediaMetadataRetriever);
                return 0;
            }
        } catch (Exception e3) {
            mediaMetadataRetriever = null;
            e = e3;
        } catch (Throwable th3) {
            mediaMetadataRetriever = null;
            th = th3;
        }
        th = th2;
        extracted(mediaMetadataRetriever);
        throw th;
    }

    private static void extracted(MediaMetadataRetriever mediaMetadataRetriever) {
        if (mediaMetadataRetriever != null) {
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e) {
                Logger.INSTANCE.e(TAG, "metaRetriever release fail exception:" + e);
            }
        }
    }

    private static int getParseFileTime(int i, long j) {
        if (!isSupportAIRecorder()) {
            int i2 = RomUtils.isPadMode() ? 3600 : 300;
            if (i >= i2) {
                return i2;
            }
            if (j % 1000 >= 500) {
                return i + 1;
            }
            if (j < 1000) {
                return 1;
            }
        }
        return i;
    }

    public static int getFileTime(String str, String str2) {
        int audioTimeByFileName = PreferenceUtils.getAudioTimeByFileName(str);
        return audioTimeByFileName == -1 ? parseTimeFromFile(str2, str) : audioTimeByFileName;
    }

    public static boolean hasAudioLabel(String str) {
        if (str == null || str.length() < 63) {
            return false;
        }
        boolean zFind = PATTERN_MODERN_AUDIO.matcher(str).find();
        return !zFind ? NEW_PATTERN_MODERN_AUDIO.matcher(str).find() : zFind;
    }

    public static String createAudioLabel(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append("<sound fileid=\"").append(str).append("\" />\n");
        }
        return sb.toString();
    }

    public static Matcher getAudioLabelIndex(String str) {
        if (str == null || str.length() < 63) {
            return null;
        }
        return PATTERN_MODERN_AUDIO.matcher(str);
    }

    public static void playDeleteRingtone() {
        if (hasDeleteSoundEffect()) {
            playRingtoneAsync(NoteApp.getInstance());
        }
    }

    private static void playRingtoneAsync(final Context context) {
        if (TextUtils.isEmpty(PATH_DELETE_OGG)) {
            Logger.INSTANCE.d(TAG, "file path is null");
        } else {
            ThreadPoolUtils.INSTANCE.getBackgroundWorker().execute(new Runnable() { // from class: com.miui.common.tool.AudioUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AudioUtils.lambda$playRingtoneAsync$0(context);
                }
            });
        }
    }

    static /* synthetic */ void lambda$playRingtoneAsync$0(Context context) {
        Logger.INSTANCE.d(TAG, "playRingtoneAsync...doInBackground");
        File file = new File(PATH_DELETE_OGG);
        if (file.exists()) {
            Ringtone ringtone = RingtoneManager.getRingtone(context, Uri.fromFile(file));
            if (ringtone != null) {
                ringtone.setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(1).build());
                ringtone.play();
                return;
            }
            return;
        }
        Logger.INSTANCE.d(TAG, "ringtone file delete.ogg is not exist");
    }

    private static boolean getDeleteSoundEffectDefaultValue() {
        try {
            return Resources.getSystem().getBoolean(Resources.getSystem().getIdentifier("default_file_delete_sound_is_on", "bool", "android.miui"));
        } catch (Exception e) {
            Logger.INSTANCE.w(TAG, "onCreate error =  " + e);
            return true;
        }
    }

    private static boolean hasDeleteSoundEffect() {
        try {
            Class<?> cls = Class.forName("android.provider.MiuiSettings$System");
            return ((Boolean) cls.getMethod("getBooleanForUser", ContentResolver.class, String.class, Boolean.TYPE, Integer.TYPE).invoke(null, NoteApp.getInstance().getContentResolver(), cls.getField("DELETE_SOUND_EFFECT").get(cls).toString(), Boolean.valueOf(getDeleteSoundEffectDefaultValue()), Integer.valueOf(((Integer) UserHandle.class.getMethod("myUserId", new Class[0]).invoke(null, new Object[0])).intValue()))).booleanValue();
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "hasDeleteSoundEffect()" + e);
            return getDeleteSoundEffectDefaultValue();
        }
    }

    public static void playAllFinishRingtone() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.miui.common.tool.AudioUtils$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                AudioUtils.lambda$playAllFinishRingtone$2(observableEmitter);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
    }

    static /* synthetic */ void lambda$playAllFinishRingtone$2(ObservableEmitter observableEmitter) throws Exception {
        if (mFinishSoundPool == null) {
            synchronized (AudioUtils.class) {
                if (mFinishSoundPool == null) {
                    SoundPool.Builder builder = new SoundPool.Builder();
                    builder.setMaxStreams(10);
                    AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
                    builder2.setLegacyStreamType(3);
                    builder.setAudioAttributes(builder2.build());
                    SoundPool soundPoolBuild = builder.build();
                    soundPoolBuild.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.miui.common.tool.AudioUtils$$ExternalSyntheticLambda2
                        @Override // android.media.SoundPool.OnLoadCompleteListener
                        public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                            AudioUtils.lambda$playAllFinishRingtone$1(soundPool, i, i2);
                        }
                    });
                    mFinishSoundId = Integer.valueOf(soundPoolBuild.load(NoteApp.getInstance(), R.raw.all_finish_effect, 1));
                    mFinishSoundPool = soundPoolBuild;
                }
            }
            return;
        }
        mFinishSoundPool.play(mFinishSoundId.intValue(), 1.0f, 1.0f, 0, 0, 1.0f);
    }

    static /* synthetic */ void lambda$playAllFinishRingtone$1(SoundPool soundPool, int i, int i2) {
        Logger.INSTANCE.i("AudioSoundPool", " onLoadComplete play " + i + StringUtils.SPACE + i2);
        soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public static boolean isSupportAIRecorder() {
        if (RomUtils.isRuRom()) {
            Logger.INSTANCE.d(TAG, "isSupportAIRecorder RomUtils.isRuRom");
            return false;
        }
        Boolean bool = sSupportAIRecorderCache;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            boolean zIsSupportAICapability = AIRecorderManager.instance.isSupportAICapability(NoteApp.getInstance());
            boolean zIsAIRecorderSupportedDevice = AIRecorderDeviceUtil.isAIRecorderSupportedDevice(NoteApp.getInstance());
            Logger.INSTANCE.d(TAG, "isSupportAIRecorder: " + zIsSupportAICapability);
            Logger.INSTANCE.d(TAG, "isAIRecorderSupportedDevice: " + zIsAIRecorderSupportedDevice);
            boolean z = true;
            if (!zIsSupportAICapability && (!RomUtils.isHyperOS2Rom() || !zIsAIRecorderSupportedDevice)) {
                z = false;
            }
            sSupportAIRecorderCache = Boolean.valueOf(z);
            return z;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "isSupportAIRecorder: " + e.getMessage());
            return false;
        }
    }
}
