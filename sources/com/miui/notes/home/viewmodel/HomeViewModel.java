package com.miui.notes.home.viewmodel;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.google.gson.Gson;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.NoteLoadDataEntity;
import com.miui.common.stat.NoteQueryStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.ShortcutsTools;
import com.miui.floatwindow.feature.note.NoteOperationHelper;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.assist.ActivityOptionCompatImpl;
import com.miui.notes.assist.UnPeekLiveData;
import com.miui.notes.cache.NoteCache;
import com.miui.notes.constant.NoteConfig;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.FolderModel;
import com.miui.notes.model.MindEntity;
import com.miui.notes.model.WorkingNote;
import com.miui.notes.page.BaseListPageFragment;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.tool.util.CursorLoaderBuilder;
import com.miui.richeditor.util.ResourceParser;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import miuix.android.content.MiuiIntent;
import miuix.smartaction.SmartAction;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: HomeViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\bP\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 þ\u00012\u00020\u0001:\u0002þ\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006J\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0006J\u0006\u0010\u0019\u001a\u00020\u0006J\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0006J\u0006\u0010\u001e\u001a\u00020\u0006J\u000e\u0010\"\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0006J\u000e\u0010'\u001a\u00020\f2\u0006\u0010(\u001a\u00020$J\u000e\u00100\u001a\u00020\f2\u0006\u0010(\u001a\u00020$J.\u00101\u001a\u00020\f2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u000205J\u001e\u0010:\u001a\u00020\f2\u0006\u00102\u001a\u0002032\u0006\u0010;\u001a\u00020<2\u0006\u00108\u001a\u00020\u000fJ.\u0010=\u001a\u00020\f2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u000205J\u001e\u0010>\u001a\u00020\f2\u0006\u00102\u001a\u0002032\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000fJ\u000e\u0010B\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010F\u001a\u00020\f2\u0006\u0010G\u001a\u00020\u0006J\u000e\u0010I\u001a\u00020\f2\u0006\u0010G\u001a\u00020\u0006J\r\u0010J\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010KJ\u000e\u0010O\u001a\u00020\f2\u0006\u0010P\u001a\u00020\u000fJ\u0006\u0010T\u001a\u00020\u000fJ\u000e\u0010Z\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010^\u001a\u00020\f2\u0006\u0010_\u001a\u00020\u0006J\u0006\u0010`\u001a\u00020\u0006J\u0006\u0010d\u001a\u00020\fJ\u000e\u0010h\u001a\u00020\f2\u0006\u0010i\u001a\u00020\u0006J\u0006\u0010j\u001a\u00020\u0006J\u0006\u0010k\u001a\u00020\u0006J\u000e\u0010r\u001a\u00020\f2\u0006\u0010s\u001a\u00020\u000fJ\u000e\u0010t\u001a\u00020\f2\u0006\u0010u\u001a\u00020\u0006J\u0006\u0010v\u001a\u00020\u0006J\u0006\u0010z\u001a\u00020\fJ\u0006\u0010~\u001a\u00020\fJ\u0010\u0010\u0082\u0001\u001a\u00020\f2\u0007\u0010\u0083\u0001\u001a\u00020\u0006J\u0010\u0010\u0087\u0001\u001a\u00020\f2\u0007\u0010\u0088\u0001\u001a\u00020\u0006J\u0007\u0010\u0088\u0001\u001a\u00020\u0006J\u0007\u0010\u008a\u0001\u001a\u00020\u0006J*\u0010\u008b\u0001\u001a\u00020\f2\b\u0010\u008c\u0001\u001a\u00030\u008d\u00012\u000e\u0010\u008e\u0001\u001a\t\u0012\u0004\u0012\u00020\u00060\u008f\u00012\u0007\u0010\u0090\u0001\u001a\u00020<J\u0018\u0010\u0091\u0001\u001a\u00020\f2\u0006\u0010u\u001a\u00020\u00062\u0007\u0010\u0092\u0001\u001a\u00020<J\u0010\u0010\u0096\u0001\u001a\u00020\f2\u0007\u0010\u0083\u0001\u001a\u00020\u0006J\u0017\u0010\u009b\u0001\u001a\u00020\f2\u0006\u00102\u001a\u0002032\u0006\u00108\u001a\u00020\u000fJ\u0013\u0010\u009c\u0001\u001a\u00030\u0098\u00012\u0007\u0010\u009d\u0001\u001a\u00020$H\u0002J(\u0010¢\u0001\u001a\u00020\f2\t\u0010£\u0001\u001a\u0004\u0018\u00010*2\t\b\u0002\u0010¤\u0001\u001a\u00020\u00062\t\b\u0002\u0010¥\u0001\u001a\u00020\u0006J\u001d\u0010¦\u0001\u001a\u00020\f2\t\u0010£\u0001\u001a\u0004\u0018\u00010*2\t\b\u0002\u0010¤\u0001\u001a\u00020\u0006J\n\u0010§\u0001\u001a\u0005\u0018\u00010\u009f\u0001J\t\u0010¨\u0001\u001a\u0004\u0018\u00010*J\u000f\u0010©\u0001\u001a\u00020\f2\u0006\u00102\u001a\u000203J\u0010\u0010\u00ad\u0001\u001a\u00020\f2\u0007\u0010®\u0001\u001a\u000205J\u0007\u0010¯\u0001\u001a\u000205J\u0011\u0010´\u0001\u001a\u00020\f2\b\u0010µ\u0001\u001a\u00030±\u0001J\u0010\u0010¹\u0001\u001a\u00020\f2\u0007\u0010º\u0001\u001a\u00020\u0006J\u0011\u0010¿\u0001\u001a\u00020\f2\b\u0010À\u0001\u001a\u00030¼\u0001J\u0010\u0010Ä\u0001\u001a\u00020\f2\u0007\u0010Å\u0001\u001a\u00020\u0006J\u0010\u0010É\u0001\u001a\u00020\f2\u0007\u0010Ê\u0001\u001a\u000205J\u0010\u0010Î\u0001\u001a\u00020\f2\u0007\u0010Ï\u0001\u001a\u000205J\u0011\u0010Ð\u0001\u001a\u00020\f2\b\u0010Ñ\u0001\u001a\u00030Ò\u0001J\u0010\u0010Ó\u0001\u001a\u00020\f2\u0007\u0010Ô\u0001\u001a\u00020\u0006J\u0007\u0010Õ\u0001\u001a\u00020*J\u0010\u0010Õ\u0001\u001a\u00020\f2\u0007\u0010Ô\u0001\u001a\u00020\u0006J\u0007\u0010Ö\u0001\u001a\u00020\fJ\u0007\u0010Ð\u0001\u001a\u00020\fJ\u0007\u0010×\u0001\u001a\u00020<JJ\u0010Ö\u0001\u001a\u00020\f2\u0007\u0010Ø\u0001\u001a\u00020\u00062\u0007\u0010Ù\u0001\u001a\u00020\u000f2\u0007\u0010Ú\u0001\u001a\u00020\u000f2\t\u0010Û\u0001\u001a\u0004\u0018\u00010<2\u0007\u0010Ü\u0001\u001a\u0002052\t\u0010Ý\u0001\u001a\u0004\u0018\u00010<2\u0007\u0010Þ\u0001\u001a\u00020\u0006JC\u0010Ö\u0001\u001a\u00020\f2\u0007\u0010Ø\u0001\u001a\u00020\u00062\u0007\u0010Ù\u0001\u001a\u00020\u000f2\u0007\u0010Ú\u0001\u001a\u00020\u000f2\t\u0010Û\u0001\u001a\u0004\u0018\u00010<2\u0007\u0010Ü\u0001\u001a\u0002052\t\u0010Ý\u0001\u001a\u0004\u0018\u00010<H\u0002J$\u0010ß\u0001\u001a\u00020\f2\u0006\u00104\u001a\u0002052\b\u0010à\u0001\u001a\u00030á\u00012\t\u0010â\u0001\u001a\u0004\u0018\u00010<J\u0010\u0010æ\u0001\u001a\u00020\f2\u0007\u0010ç\u0001\u001a\u00020\u0006J\u000e\u0010è\u0001\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010KJ\u0011\u0010ï\u0001\u001a\u00020\f2\b\u0010ð\u0001\u001a\u00030ñ\u0001J\u0011\u0010ò\u0001\u001a\u00020\f2\b\u0010²\u0001\u001a\u00030±\u0001J\u001b\u0010ò\u0001\u001a\u00020\f2\b\u0010²\u0001\u001a\u00030±\u00012\b\u0010ó\u0001\u001a\u00030ô\u0001J\u0011\u0010ø\u0001\u001a\u00020\f2\b\u0010ð\u0001\u001a\u00030ñ\u0001J\u0011\u0010ù\u0001\u001a\u00020\f2\b\u0010²\u0001\u001a\u00030±\u0001J\u0010\u0010û\u0001\u001a\u00020\f2\u0007\u0010ü\u0001\u001a\u00020\u000fJ\u0007\u0010ý\u0001\u001a\u00020\u000fR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\b8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\nR\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\nR\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\nR\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b!\u0010\nR\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\b8F¢\u0006\u0006\u001a\u0004\b&\u0010\nR\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u0015¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020$0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020$0\b8F¢\u0006\u0006\u001a\u0004\b/\u0010\nR\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000f0\b8F¢\u0006\u0006\u001a\u0004\bA\u0010\nR\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\bE\u0010\nR\u0014\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010L\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010M\u001a\b\u0012\u0004\u0012\u00020\u000f0\b8F¢\u0006\u0006\u001a\u0004\bN\u0010\nR\u0014\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010R\u001a\b\u0012\u0004\u0012\u00020\u000f0\b8F¢\u0006\u0006\u001a\u0004\bS\u0010\nR\u001a\u0010U\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b]\u0010\nR\u0014\u0010a\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\bc\u0010\nR\u0014\u0010e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\bg\u0010\nR\u0014\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010m\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010n\u001a\b\u0012\u0004\u0012\u00020\u000f0\b8F¢\u0006\u0006\u001a\u0004\bo\u0010\nR\u0017\u0010p\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\bq\u0010\nR\u0014\u0010w\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\by\u0010\nR\u0014\u0010{\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0006\u001a\u0004\b}\u0010\nR\u0014\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0007\u001a\u0005\b\u0081\u0001\u0010\nR\u0015\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0007\u001a\u0005\b\u0086\u0001\u0010\nR\u0015\u0010\u0089\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0093\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0007\u001a\u0005\b\u0095\u0001\u0010\nR\u0016\u0010\u0097\u0001\u001a\t\u0012\u0005\u0012\u00030\u0098\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0099\u0001\u001a\t\u0012\u0005\u0012\u00030\u0098\u00010\b8F¢\u0006\u0007\u001a\u0005\b\u009a\u0001\u0010\nR\u0016\u0010\u009e\u0001\u001a\t\u0012\u0005\u0012\u00030\u009f\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u0001\u001a\t\u0012\u0005\u0012\u00030\u009f\u00010\b8F¢\u0006\u0007\u001a\u0005\b¡\u0001\u0010\nR\u0015\u0010ª\u0001\u001a\b\u0012\u0004\u0012\u0002050\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010«\u0001\u001a\b\u0012\u0004\u0012\u0002050\b8F¢\u0006\u0007\u001a\u0005\b¬\u0001\u0010\nR\u0016\u0010°\u0001\u001a\t\u0012\u0005\u0012\u00030±\u00010\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010²\u0001\u001a\t\u0012\u0005\u0012\u00030±\u00010\b8F¢\u0006\u0007\u001a\u0005\b³\u0001\u0010\nR\u0015\u0010¶\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010·\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0007\u001a\u0005\b¸\u0001\u0010\nR\u0016\u0010»\u0001\u001a\t\u0012\u0005\u0012\u00030¼\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010½\u0001\u001a\t\u0012\u0005\u0012\u00030¼\u00010\b8F¢\u0006\u0007\u001a\u0005\b¾\u0001\u0010\nR\u0015\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010Â\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0007\u001a\u0005\bÃ\u0001\u0010\nR\u0015\u0010Æ\u0001\u001a\b\u0012\u0004\u0012\u0002050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010Ç\u0001\u001a\b\u0012\u0004\u0012\u0002050\b8F¢\u0006\u0007\u001a\u0005\bÈ\u0001\u0010\nR\u0015\u0010Ë\u0001\u001a\b\u0012\u0004\u0012\u0002050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010Ì\u0001\u001a\b\u0012\u0004\u0012\u0002050\b8F¢\u0006\u0007\u001a\u0005\bÍ\u0001\u0010\nR\u0015\u0010ã\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010ä\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\b8F¢\u0006\u0007\u001a\u0005\bå\u0001\u0010\nR'\u0010é\u0001\u001a\n\u0012\u0005\u0012\u00030±\u00010ê\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\bë\u0001\u0010ì\u0001\"\u0006\bí\u0001\u0010î\u0001R'\u0010õ\u0001\u001a\n\u0012\u0005\u0012\u00030±\u00010ê\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\bö\u0001\u0010ì\u0001\"\u0006\b÷\u0001\u0010î\u0001R\u000f\u0010ú\u0001\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006ÿ\u0001"}, d2 = {"Lcom/miui/notes/home/viewmodel/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_windowsFocus", "Lcom/miui/notes/assist/UnPeekLiveData;", "", "windowsFocus", "Landroidx/lifecycle/LiveData;", "getWindowsFocus", "()Landroidx/lifecycle/LiveData;", "changeWindowsFocus", "", "hasFocus", "_noteMultiMode", "", "noteMultiModeLiveData", "getNoteMultiModeLiveData", "changeMultiMode", "mode", "_aiSearchMode", "Landroidx/lifecycle/MutableLiveData;", "aiSearchMode", "getAiSearchMode", "setAiSearchMode", "isAiSearchMode", "_searchMode", "searchMode", "getSearchMode", "setSearchMode", "isSearchMode", "_stopSearch", "stopSearch", "getStopSearch", "setStopSearch", "_listDataCursor", "Landroid/database/Cursor;", "listDataCursor", "getListDataCursor", "setListDataCursor", "cursor", "createEntityData", "Lcom/miui/common/base/NoteLoadDataEntity;", "getCreateEntityData", "()Landroidx/lifecycle/MutableLiveData;", "_searchDataCursor", "searchDataCursor", "getSearchDataCursor", "setSearchDataCursor", "startQuery", "loaderManager", "Landroidx/loader/app/LoaderManager;", "folderId", "", "bindContext", "Lcom/miui/notes/adapter/BindContext;", "requestCode", "startTime", "startAiQuery", "selection", "", "startSearchQuery", "startPrivateSearchQuery", "_dragMode", "dragModeLiveData", "getDragModeLiveData", "changeDragMode", "_vpScrollableStatus", "vpScrollableStatusLiveData", "getVpScrollableStatusLiveData", "setVpScrollable", TypedValues.Custom.S_BOOLEAN, "_IsPasswordStatus", "setIsPassword", "getIsPassword", "()Ljava/lang/Boolean;", "_currentItem", "currentItemLiveData", "getCurrentItemLiveData", "setCurrentItem", "current", "_pageSelected", "pageSelectedLiveData", "getPageSelectedLiveData", "getPageSelected", "pageIndexIsChange", "getPageIndexIsChange", "()Z", "setPageIndexIsChange", "(Z)V", "selectPage", "_toHomeFromOutMode", "toHomeFromOutMode", "getToHomeFromOutMode", "setToHomeFromOutMode", "state", "getToHomeFromOutModeData", "_toDetailResult", "toDetailResult", "getToDetailResult", "toDetailResultCallback", "_editStatus", "editStatus", "getEditStatus", "setEditStatus", "edit", "isInEditStatus", "isEditStatus", "_keyBoardShowStatus", "_keyBoardForPadding", "keyBoardForPadding", "getKeyBoardForPadding", "keyBoardShowStatus", "getKeyBoardShowStatus", "setKeyBoardForPadding", "padding", "setKeyBoardShowStatus", "show", "isShowKeyBoard", "_toEditModeLiveData", "toEditModeLiveData", "getToEditModeLiveData", "setEditMode", "_toFolderListLiveData", "toFolderListLiveData", "getToFolderListLiveData", "setToFolderList", "_keyboardChange", "keyboardChange", "getKeyboardChange", "setKeyboardChange", "change", "_currentGridMode", "currentGridMode", "getCurrentGridMode", "setCurrentGridMode", "isGridMode", "_globalComposeBtnShow", "getGlobalComposeBtnShow", "addObserverForGlobalComposeBtnShow", "owner", "Landroidx/lifecycle/LifecycleOwner;", "observer", "Landroidx/lifecycle/Observer;", TypedValues.AttributesType.S_TARGET, "setGlobalComposeBtnShow", "invokeSource", "_todoKeyboardChange", "todoKeyboardChange", "getTodoKeyboardChange", "setTodoKeyboardChange", "_naviMenuData", "Lcom/miui/notes/home/viewmodel/NavigationMenuData;", "naviMenuData", "getNaviMenuData", "loadNavigationMenuData", "dealFolderData", SmartAction.Feature.QUERY, "_selectNoteLiveData", "Lcom/miui/notes/home/viewmodel/SelectNoteData;", "selectNoteLiveData", "getSelectNoteLiveData", "setSelectNote", "entity", "openDetail", "useDetailFragmentNav", "postSelectNote", "getSelectedNoteWrapper", "getSelectedNote", "refreshFolderName", "_folderId", "folderIdLiveData", "getFolderIdLiveData", "setFolderId", "id", "getFolderId", "_intent", "Landroid/content/Intent;", "intent", "getIntent", "setIntent", IntegerTokenConverter.CONVERTER_KEY, "_clearCoverFlag", "clearCoverFlag", "getClearCoverFlag", "clearCover", "flag", "_moveToFolderNotifyLiveData", "Lcom/miui/notes/home/viewmodel/MoveToFolderNotifyData;", "moveToFolderNotifyLiveData", "getMoveToFolderNotifyLiveData", "setMoveToFolderNotifyData", "fd", "_searchOnFocusLiveData", "searchOnFocusLiveData", "getSearchOnFocusLiveData", "searchOnFocus", "focus", "_hideNoteLiveDate", "hideNoteLiveData", "getHideNoteLiveData", "hideNote", MiuiIntent.COMMAND_ICON_PANEL_HIDE, "_unHideNoteLiveDate", "unHideNoteLiveData", "getUnHideNoteLiveData", "unHideNote", "unHide", "createMindNote", "bundle", "Landroid/os/Bundle;", "createHandWriteNote", "fromStylus", "createHandWriteMultiNote", "createNormalNote", "checkLastEmptyNoteType", "fromFloatWindow", "widgetId", "widgetType", "phoneNum", "callDate", "contactName", "showPhotoPicker", "onOpenNote", "noteCache", "Lcom/miui/notes/cache/NoteCache;", "searchText", "_toDetailPage", "toDetailPage", "getToDetailPage", "setToDetailPage", "toDetail", "isToDetailPage", "toDetailActivityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "getToDetailActivityResultLauncher", "()Landroidx/activity/result/ActivityResultLauncher;", "setToDetailActivityResultLauncher", "(Landroidx/activity/result/ActivityResultLauncher;)V", "setupToDetailActivityResultLauncher", "fragment", "Lcom/miui/notes/page/BaseListPageFragment;", "launchToDetailActivity", "activityOptionCompatImpl", "Lcom/miui/notes/assist/ActivityOptionCompatImpl;", "toDetailLauncher", "getToDetailLauncher", "setToDetailLauncher", "setupToDetailLauncher", "createNoteFromIntent", "_clickedNotePosition", "setClickedNotePosition", "clickedNotePosition", "getClickedNotePosition", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HomeViewModel extends ViewModel {
    public static final int MODE_DRAG_END = 0;
    public static final int MODE_DRAG_START = 1;
    public static final int MODE_ENTER_MULTI = 1;
    public static final int MODE_EXIT_MULTI = 0;
    public static final int MODE_RECORD_END = 0;
    public static final int MODE_RECORD_START = 1;
    public static final int NOTE_SELECTED = 0;
    private static final String TAG = "HomeViewModel";
    public static final int TODO_SELECTED = 1;
    private boolean pageIndexIsChange;
    public ActivityResultLauncher<Intent> toDetailActivityResultLauncher;
    public ActivityResultLauncher<Intent> toDetailLauncher;
    private final UnPeekLiveData<Boolean> _windowsFocus = new UnPeekLiveData<>();
    private final UnPeekLiveData<Integer> _noteMultiMode = new UnPeekLiveData<>();
    private final MutableLiveData<Boolean> _aiSearchMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _searchMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _stopSearch = new MutableLiveData<>();
    private final MutableLiveData<Cursor> _listDataCursor = new MutableLiveData<>();
    private final MutableLiveData<NoteLoadDataEntity> createEntityData = new MutableLiveData<>();
    private final MutableLiveData<Cursor> _searchDataCursor = new MutableLiveData<>();
    private final MutableLiveData<Integer> _dragMode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _vpScrollableStatus = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _IsPasswordStatus = new MutableLiveData<>();
    private final UnPeekLiveData<Integer> _currentItem = new UnPeekLiveData<>();
    private final UnPeekLiveData<Integer> _pageSelected = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _toHomeFromOutMode = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _toDetailResult = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _editStatus = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _keyBoardShowStatus = new UnPeekLiveData<>();
    private final UnPeekLiveData<Integer> _keyBoardForPadding = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _toEditModeLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _toFolderListLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _keyboardChange = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _currentGridMode = new UnPeekLiveData<>();
    private final MutableLiveData<Boolean> _globalComposeBtnShow = new MutableLiveData<>();
    private final UnPeekLiveData<Boolean> _todoKeyboardChange = new UnPeekLiveData<>();
    private final MutableLiveData<NavigationMenuData> _naviMenuData = new MutableLiveData<>();
    private final MutableLiveData<SelectNoteData> _selectNoteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> _folderId = new MutableLiveData<>();
    private final MutableLiveData<Intent> _intent = new MutableLiveData<>();
    private final UnPeekLiveData<Boolean> _clearCoverFlag = new UnPeekLiveData<>();
    private final UnPeekLiveData<MoveToFolderNotifyData> _moveToFolderNotifyLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> _searchOnFocusLiveData = new UnPeekLiveData<>();
    private final UnPeekLiveData<Long> _hideNoteLiveDate = new UnPeekLiveData<>();
    private final UnPeekLiveData<Long> _unHideNoteLiveDate = new UnPeekLiveData<>();
    private final MutableLiveData<Boolean> _toDetailPage = new MutableLiveData<>();
    private int _clickedNotePosition = -1;

    public final LiveData<Boolean> getWindowsFocus() {
        return this._windowsFocus;
    }

    public final void changeWindowsFocus(boolean hasFocus) {
        this._windowsFocus.setValue(Boolean.valueOf(hasFocus));
    }

    public final LiveData<Integer> getNoteMultiModeLiveData() {
        return this._noteMultiMode;
    }

    public final void changeMultiMode(int mode) {
        this._noteMultiMode.setValue(Integer.valueOf(mode));
    }

    private final LiveData<Boolean> getAiSearchMode() {
        return this._aiSearchMode;
    }

    public final void setAiSearchMode(boolean isAiSearchMode) {
        this._aiSearchMode.setValue(Boolean.valueOf(isAiSearchMode));
    }

    public final boolean isAiSearchMode() {
        return Intrinsics.areEqual((Object) getAiSearchMode().getValue(), (Object) true);
    }

    public final LiveData<Boolean> getSearchMode() {
        return this._searchMode;
    }

    public final void setSearchMode(boolean isSearchMode) {
        this._searchMode.setValue(Boolean.valueOf(isSearchMode));
    }

    public final boolean isSearchMode() {
        return Intrinsics.areEqual((Object) getSearchMode().getValue(), (Object) true);
    }

    public final LiveData<Boolean> getStopSearch() {
        return this._stopSearch;
    }

    public final void setStopSearch(boolean isSearchMode) {
        this._stopSearch.setValue(Boolean.valueOf(isSearchMode));
    }

    public final LiveData<Cursor> getListDataCursor() {
        return this._listDataCursor;
    }

    public final void setListDataCursor(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        this._listDataCursor.setValue(cursor);
    }

    public final MutableLiveData<NoteLoadDataEntity> getCreateEntityData() {
        return this.createEntityData;
    }

    public final LiveData<Cursor> getSearchDataCursor() {
        return this._searchDataCursor;
    }

    public final void setSearchDataCursor(Cursor cursor) {
        Intrinsics.checkNotNullParameter(cursor, "cursor");
        this._searchDataCursor.setValue(cursor);
    }

    public final void startQuery(LoaderManager loaderManager, long folderId, final BindContext bindContext, int requestCode, final long startTime) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        NoteStore.query(loaderManager, folderId, bindContext, requestCode, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.home.viewmodel.HomeViewModel.startQuery.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                Logger.INSTANCE.d(HomeViewModel.TAG, "onLoadFinished,with data count: " + (data != null ? Integer.valueOf(data.getCount()) : null));
                int id = loader.getId();
                if (id == 0 || id == 1 || id == 3) {
                    if (id == 1 && HomeViewModel.this.isSearchMode()) {
                        return;
                    }
                    if ((id == 0 && !HomeViewModel.this.isSearchMode()) || data == null || data.isClosed()) {
                        return;
                    }
                    HomeViewModel.this._listDataCursor.setValue(data);
                    if (!HomeViewModel.this.isSearchMode() || startTime <= 0) {
                        return;
                    }
                    NoteQueryStat.reportNoteSearchResultEvent(data.getCount(), bindContext.getSearchToken(), (int) (System.currentTimeMillis() - startTime), false);
                }
            }
        });
    }

    public final void startAiQuery(LoaderManager loaderManager, String selection, int requestCode) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Intrinsics.checkNotNullParameter(selection, "selection");
        NoteStore.aiQuery(loaderManager, selection, requestCode, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.home.viewmodel.HomeViewModel.startAiQuery.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                if (data == null || data.isClosed()) {
                    return;
                }
                HomeViewModel.this._listDataCursor.setValue(data);
            }
        });
    }

    public final void startSearchQuery(LoaderManager loaderManager, long folderId, final BindContext bindContext, int requestCode, final long startTime) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        NoteStore.query(loaderManager, folderId, bindContext, requestCode, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.home.viewmodel.HomeViewModel.startSearchQuery.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                Logger.INSTANCE.d(HomeViewModel.TAG, "startSearchQuery onLoadFinished, count: " + (data != null ? Integer.valueOf(data.getCount()) : null));
                if (data == null || data.isClosed()) {
                    return;
                }
                HomeViewModel.this._searchDataCursor.setValue(data);
                if (!HomeViewModel.this.isSearchMode() || startTime <= 0) {
                    return;
                }
                int count = data.getCount();
                String searchToken = bindContext.getSearchToken();
                if (searchToken == null) {
                    searchToken = "";
                }
                NoteQueryStat.reportNoteSearchResultEvent(count, searchToken, (int) (System.currentTimeMillis() - startTime), false);
            }
        });
    }

    public final void startPrivateSearchQuery(LoaderManager loaderManager, BindContext bindContext, int requestCode) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        String searchToken = bindContext.getSearchToken();
        bindContext.setSearchToken("");
        NoteStore.query(loaderManager, -4L, bindContext, requestCode, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.home.viewmodel.HomeViewModel.startPrivateSearchQuery.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                Logger.INSTANCE.d(HomeViewModel.TAG, "startPrivateSearchQuery onLoadFinished, count: " + (data != null ? Integer.valueOf(data.getCount()) : null));
                if (data == null || data.isClosed()) {
                    return;
                }
                HomeViewModel.this._searchDataCursor.setValue(data);
            }
        });
        bindContext.setSearchToken(searchToken);
    }

    public final LiveData<Integer> getDragModeLiveData() {
        return this._dragMode;
    }

    public final void changeDragMode(int mode) {
        this._dragMode.setValue(Integer.valueOf(mode));
    }

    public final LiveData<Boolean> getVpScrollableStatusLiveData() {
        return this._vpScrollableStatus;
    }

    public final void setVpScrollable(boolean z) {
        this._vpScrollableStatus.setValue(Boolean.valueOf(z));
    }

    public final void setIsPassword(boolean z) {
        Logger.INSTANCE.d(TAG, "setIsPassword = " + z);
        this._IsPasswordStatus.setValue(Boolean.valueOf(z));
    }

    public final Boolean getIsPassword() {
        return this._IsPasswordStatus.getValue();
    }

    public final LiveData<Integer> getCurrentItemLiveData() {
        return this._currentItem;
    }

    public final void setCurrentItem(int current) {
        Logger.INSTANCE.d(TAG, "setCurrentItem: " + current);
        this._currentItem.setValue(Integer.valueOf(current));
    }

    public final LiveData<Integer> getPageSelectedLiveData() {
        return this._pageSelected;
    }

    public final int getPageSelected() {
        Integer value = getPageSelectedLiveData().getValue();
        if (value != null) {
            return value.intValue();
        }
        return 0;
    }

    public final boolean getPageIndexIsChange() {
        return this.pageIndexIsChange;
    }

    public final void setPageIndexIsChange(boolean z) {
        this.pageIndexIsChange = z;
    }

    public final void selectPage(int mode) {
        Integer value;
        Logger.INSTANCE.d(TAG, "selectPage: mode: " + mode + ", current: " + this._pageSelected.getValue());
        this.pageIndexIsChange = this._pageSelected.getValue() != null && ((value = this._pageSelected.getValue()) == null || value.intValue() != mode);
        this._pageSelected.setValue(Integer.valueOf(mode));
    }

    public final LiveData<Boolean> getToHomeFromOutMode() {
        return this._toHomeFromOutMode;
    }

    public final void setToHomeFromOutMode(boolean state) {
        Logger.INSTANCE.d(TAG, "setToHomeFromOutMode: " + state);
        this._toHomeFromOutMode.setValue(Boolean.valueOf(state));
    }

    public final boolean getToHomeFromOutModeData() {
        Boolean value = this._toHomeFromOutMode.getValue();
        if (value != null) {
            return value.booleanValue();
        }
        return false;
    }

    public final LiveData<Boolean> getToDetailResult() {
        return this._toDetailResult;
    }

    public final void toDetailResultCallback() {
        this._toDetailResult.setValue(true);
    }

    public final LiveData<Boolean> getEditStatus() {
        return this._editStatus;
    }

    public final void setEditStatus(boolean edit) {
        Logger.INSTANCE.d(TAG, "setEditStatus: " + edit + "  previous Status: " + this._editStatus.getValue());
        this._editStatus.setValue(Boolean.valueOf(edit));
    }

    public final boolean isInEditStatus() {
        Boolean value = getEditStatus().getValue();
        if (value != null) {
            return value.booleanValue();
        }
        return false;
    }

    public final boolean isEditStatus() {
        Boolean value = this._editStatus.getValue();
        if (value != null) {
            return value.booleanValue();
        }
        return false;
    }

    public final LiveData<Integer> getKeyBoardForPadding() {
        return this._keyBoardForPadding;
    }

    public final LiveData<Boolean> getKeyBoardShowStatus() {
        return this._keyBoardShowStatus;
    }

    public final void setKeyBoardForPadding(int padding) {
        this._keyBoardForPadding.setValue(Integer.valueOf(padding));
    }

    public final void setKeyBoardShowStatus(boolean show) {
        this._keyBoardShowStatus.setValue(Boolean.valueOf(show));
    }

    public final boolean isShowKeyBoard() {
        Boolean value = getKeyBoardShowStatus().getValue();
        if (value != null) {
            return value.booleanValue();
        }
        return false;
    }

    public final LiveData<Boolean> getToEditModeLiveData() {
        return this._toEditModeLiveData;
    }

    public final void setEditMode() {
        Logger.INSTANCE.d(TAG, "setEditMode true " + this._toEditModeLiveData.getValue());
        this._toEditModeLiveData.setValue(true);
    }

    public final LiveData<Boolean> getToFolderListLiveData() {
        return this._toFolderListLiveData;
    }

    public final void setToFolderList() {
        Logger.INSTANCE.d(TAG, "setToFolderList");
        this._toFolderListLiveData.setValue(true);
    }

    public final LiveData<Boolean> getKeyboardChange() {
        return this._keyboardChange;
    }

    public final void setKeyboardChange(boolean change) {
        this._keyboardChange.setValue(Boolean.valueOf(change));
    }

    public final LiveData<Boolean> getCurrentGridMode() {
        return this._currentGridMode;
    }

    public final void setCurrentGridMode(boolean isGridMode) {
        this._currentGridMode.setValue(Boolean.valueOf(isGridMode));
    }

    public final boolean isGridMode() {
        Boolean value = this._currentGridMode.getValue();
        if (value != null) {
            return value.booleanValue();
        }
        return false;
    }

    public final boolean getGlobalComposeBtnShow() {
        Boolean value = this._globalComposeBtnShow.getValue();
        if (value != null) {
            return value.booleanValue();
        }
        return false;
    }

    public final void addObserverForGlobalComposeBtnShow(LifecycleOwner owner, Observer<Boolean> observer, String target) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        Intrinsics.checkNotNullParameter(target, "target");
        Logger.INSTANCE.d(TAG, "addObserverForGlobalComposeBtnShow, owner:" + owner + ",target:" + target);
        this._globalComposeBtnShow.observe(owner, observer);
    }

    public final void setGlobalComposeBtnShow(boolean show, String invokeSource) {
        Intrinsics.checkNotNullParameter(invokeSource, "invokeSource");
        Logger.INSTANCE.e(TAG, "setGlobalComposeBtnShow: " + show + ", showCreateBtn,invokeSource:" + invokeSource);
        this._globalComposeBtnShow.setValue(Boolean.valueOf(show));
    }

    public final LiveData<Boolean> getTodoKeyboardChange() {
        return this._todoKeyboardChange;
    }

    public final void setTodoKeyboardChange(boolean change) {
        this._todoKeyboardChange.setValue(Boolean.valueOf(change));
    }

    public final LiveData<NavigationMenuData> getNaviMenuData() {
        return this._naviMenuData;
    }

    /* JADX INFO: renamed from: com.miui.notes.home.viewmodel.HomeViewModel$loadNavigationMenuData$1, reason: invalid class name */
    /* JADX INFO: compiled from: HomeViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.home.viewmodel.HomeViewModel$loadNavigationMenuData$1", f = "HomeViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ LoaderManager $loaderManager;
        final /* synthetic */ int $requestCode;
        int label;
        final /* synthetic */ HomeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(LoaderManager loaderManager, int i, HomeViewModel homeViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$loaderManager = loaderManager;
            this.$requestCode = i;
            this.this$0 = homeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$loaderManager, this.$requestCode, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FolderStore folderStore = FolderStore.INSTANCE;
            LoaderManager loaderManager = this.$loaderManager;
            int i = this.$requestCode;
            final HomeViewModel homeViewModel = this.this$0;
            folderStore.query(loaderManager, 5, i, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.home.viewmodel.HomeViewModel.loadNavigationMenuData.1.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    Intrinsics.checkNotNullParameter(loader, "loader");
                    super.onLoadFinished(loader, data);
                    if (data != null) {
                        homeViewModel._naviMenuData.setValue(homeViewModel.dealFolderData(data));
                    }
                }
            });
            return Unit.INSTANCE;
        }
    }

    public final void loadNavigationMenuData(LoaderManager loaderManager, int requestCode) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        Logger.INSTANCE.d(TAG, "loadNavigationMenuData");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(loaderManager, requestCode, this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final NavigationMenuData dealFolderData(Cursor query) {
        Logger.INSTANCE.d(TAG, "dealFolderData");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList(2);
        if (query.getCount() > 0) {
            query.moveToPosition(-1);
            while (query.moveToNext()) {
                FolderModel partFieldsWithMenu = FolderStore.INSTANCE.parsePartFieldsWithMenu(query);
                boolean z = partFieldsWithMenu.getStickAt() > 0;
                if (partFieldsWithMenu.getId() == 0) {
                    arrayList3.add(0, partFieldsWithMenu);
                } else if (partFieldsWithMenu.getId() == -3) {
                    arrayList3.add(partFieldsWithMenu);
                } else if (z) {
                    arrayList.add(partFieldsWithMenu);
                } else {
                    arrayList2.add(partFieldsWithMenu);
                }
            }
        }
        String string = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.foler_pinned);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        NavigationFolderMenu navigationFolderMenu = new NavigationFolderMenu(string, arrayList);
        String string2 = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.folder_note);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        NavigationFolderMenu navigationFolderMenu2 = new NavigationFolderMenu(string2, arrayList2);
        String string3 = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.folder_other);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        return new NavigationMenuData(navigationFolderMenu, navigationFolderMenu2, new NavigationFolderMenu(string3, arrayList3));
    }

    public final LiveData<SelectNoteData> getSelectNoteLiveData() {
        return this._selectNoteLiveData;
    }

    public static /* synthetic */ void setSelectNote$default(HomeViewModel homeViewModel, NoteLoadDataEntity noteLoadDataEntity, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        homeViewModel.setSelectNote(noteLoadDataEntity, z, z2);
    }

    public final void setSelectNote(NoteLoadDataEntity entity, boolean openDetail, boolean useDetailFragmentNav) {
        Logger.INSTANCE.d(TAG, "setSelectNote" + (entity != null ? Long.valueOf(entity.getNoteId()) : null) + "  open:" + openDetail + " useDetailFragmentNav:" + useDetailFragmentNav);
        this._selectNoteLiveData.setValue(new SelectNoteData(entity, openDetail, useDetailFragmentNav, entity != null ? entity.getFolderId() : getFolderId()));
    }

    public static /* synthetic */ void postSelectNote$default(HomeViewModel homeViewModel, NoteLoadDataEntity noteLoadDataEntity, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        homeViewModel.postSelectNote(noteLoadDataEntity, z);
    }

    public final void postSelectNote(NoteLoadDataEntity entity, boolean openDetail) {
        Logger.INSTANCE.d(TAG, "postSelectNote" + (entity != null ? Long.valueOf(entity.getNoteId()) : null) + StringUtils.SPACE);
        this._selectNoteLiveData.postValue(new SelectNoteData(entity, openDetail, false, entity != null ? entity.getFolderId() : getFolderId()));
    }

    public final SelectNoteData getSelectedNoteWrapper() {
        return getSelectNoteLiveData().getValue();
    }

    public final NoteLoadDataEntity getSelectedNote() {
        SelectNoteData value = getSelectNoteLiveData().getValue();
        if (value != null) {
            return value.getEntity();
        }
        return null;
    }

    public final void refreshFolderName(final LoaderManager loaderManager) {
        Intrinsics.checkNotNullParameter(loaderManager, "loaderManager");
        FolderStore.INSTANCE.query(loaderManager, 1, 13, new CursorLoaderBuilder.DefaultLoaderCallbacks() { // from class: com.miui.notes.home.viewmodel.HomeViewModel.refreshFolderName.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.miui.notes.tool.util.CursorLoaderBuilder.DefaultLoaderCallbacks, androidx.loader.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                FolderStore.updateEnableNewDefaultFolderName(BaseNoteApp.INSTANCE.getInstance(), data);
                if (data != null) {
                    data.close();
                }
                loaderManager.destroyLoader(13);
            }
        });
    }

    public final LiveData<Long> getFolderIdLiveData() {
        return this._folderId;
    }

    public final void setFolderId(long id) {
        Logger.INSTANCE.d(TAG, "setFolderId:" + id);
        this._folderId.setValue(Long.valueOf(id));
    }

    public final long getFolderId() {
        Long value = getFolderIdLiveData().getValue();
        return value != null ? value.longValue() : NoteConfig.getDefaultFolderId();
    }

    public final LiveData<Intent> getIntent() {
        return this._intent;
    }

    public final void setIntent(Intent i) {
        Intrinsics.checkNotNullParameter(i, "i");
        this._intent.setValue(i);
    }

    public final LiveData<Boolean> getClearCoverFlag() {
        return this._clearCoverFlag;
    }

    public final void clearCover(boolean flag) {
        this._clearCoverFlag.setValue(Boolean.valueOf(flag));
    }

    public final LiveData<MoveToFolderNotifyData> getMoveToFolderNotifyLiveData() {
        return this._moveToFolderNotifyLiveData;
    }

    public final void setMoveToFolderNotifyData(MoveToFolderNotifyData fd) {
        Intrinsics.checkNotNullParameter(fd, "fd");
        this._moveToFolderNotifyLiveData.setValue(fd);
    }

    public final LiveData<Boolean> getSearchOnFocusLiveData() {
        return this._searchOnFocusLiveData;
    }

    public final void searchOnFocus(boolean focus) {
        this._searchOnFocusLiveData.setValue(Boolean.valueOf(focus));
    }

    public final LiveData<Long> getHideNoteLiveData() {
        return this._hideNoteLiveDate;
    }

    public final void hideNote(long hide) {
        this._hideNoteLiveDate.setValue(Long.valueOf(hide));
    }

    public final LiveData<Long> getUnHideNoteLiveData() {
        return this._unHideNoteLiveDate;
    }

    public final void unHideNote(long unHide) {
        this._unHideNoteLiveDate.setValue(Long.valueOf(unHide));
    }

    public final void createMindNote(Bundle bundle) {
        String str;
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        String string = bundle.getString("note_title");
        int i = bundle.getInt("mind_content_type");
        int i2 = bundle.getInt("widget_id");
        int i3 = bundle.getInt("widget_type");
        long folderId = getFolderId();
        if (!FolderModel.INSTANCE.isCustomFolder(folderId)) {
            folderId = 0;
        }
        long j = folderId;
        boolean z = i == 0;
        String str2 = string;
        if (TextUtils.isEmpty(str2)) {
            str = "";
        } else {
            Intrinsics.checkNotNull(string);
            str = string;
        }
        MindEntity mindEntity = new MindEntity(z, str, "", "");
        WorkingNote workingNoteCreateEmptyMindNote = WorkingNote.INSTANCE.createEmptyMindNote(j, i2, i3, ResourceParser.getDefaultBgId(BaseNoteApp.INSTANCE.getInstance()));
        if (TextUtils.isEmpty(str2)) {
            string = "";
        }
        workingNoteCreateEmptyMindNote.setWorkingTitle(string);
        workingNoteCreateEmptyMindNote.setWorkingText("");
        workingNoteCreateEmptyMindNote.setWorkingMindContent(mindEntity.toNoteContent());
        if (workingNoteCreateEmptyMindNote.createNoteByPad(true)) {
            setSelectNote$default(this, new NoteLoadDataEntity(workingNoteCreateEmptyMindNote.getNoteId(), getFolderId(), "searchText", 1, System.currentTimeMillis(), i, 1, workingNoteCreateEmptyMindNote.getTitle(), false, workingNoteCreateEmptyMindNote.getWidgetId(), workingNoteCreateEmptyMindNote.getWidgetType()), false, false, 6, null);
        }
    }

    public final void createHandWriteNote(boolean fromStylus) {
        if (TextUtils.equals(checkLastEmptyNoteType(), "handwrite")) {
            setEditMode();
            return;
        }
        long folderId = getFolderId();
        if (folderId == -4097) {
            folderId = 0;
        }
        if (fromStylus) {
            folderId = -8;
            setFolderId(-8L);
        }
        setSelectNote$default(this, new NoteLoadDataEntity(-2L, folderId, "", 2, System.currentTimeMillis(), false, null), true, false, 4, null);
    }

    public final NoteLoadDataEntity createHandWriteMultiNote() {
        long folderId = getFolderId();
        if (folderId == -4097) {
            folderId = 0;
        }
        return new NoteLoadDataEntity(-2L, folderId, "", 3, System.currentTimeMillis(), false, null);
    }

    public final void createHandWriteMultiNote(boolean fromStylus) {
        if (TextUtils.equals(checkLastEmptyNoteType(), "multi_handwrite")) {
            setEditMode();
            NoteStore.deleteEmpty(BaseNoteApp.INSTANCE.getInstance(), null);
        }
        long folderId = getFolderId();
        if (folderId == -4097) {
            folderId = 0;
        }
        setSelectNote$default(this, new NoteLoadDataEntity(-2L, folderId, "", 3, System.currentTimeMillis(), false, null), true, false, 4, null);
    }

    public final void createNormalNote() {
        if (Objects.equals(this._noteMultiMode.getValue(), 1)) {
            Logger.INSTANCE.i(TAG, "cannot create note in actionMode");
        } else {
            Logger.INSTANCE.d(TAG, "createNormalNote");
            createNormalNote(false, 0, -1, null, 0L, null);
        }
    }

    public final void createMindNote() {
        if (Objects.equals(this._noteMultiMode.getValue(), 1)) {
            Logger.INSTANCE.i(TAG, "cannot create mind note in actionMode");
            return;
        }
        boolean zIsFoldDevice = RomUtils.isFoldDevice();
        Bundle bundle = new Bundle();
        bundle.putString("note_title", "");
        bundle.putInt("mind_content_type", zIsFoldDevice ? 1 : 0);
        bundle.putInt("widget_id", 0);
        bundle.putInt("widget_type", -1);
        createMindNote(bundle);
    }

    public final String checkLastEmptyNoteType() {
        NoteLoadDataEntity selectedNote = getSelectedNote();
        if (selectedNote != null) {
            Cursor cursorQueryEmpty = NoteStore.queryEmpty(BaseNoteApp.INSTANCE.getInstance(), selectedNote.getNoteId());
            if (cursorQueryEmpty != null && cursorQueryEmpty.getCount() > 0) {
                cursorQueryEmpty.moveToPosition(0);
                String string = cursorQueryEmpty.getString(0);
                Logger.INSTANCE.d(TAG, "LastEmptyNoteType:" + string);
                cursorQueryEmpty.close();
                Intrinsics.checkNotNull(string);
                return string;
            }
            return "";
        }
        return "";
    }

    public final void createNormalNote(boolean fromFloatWindow, int widgetId, int widgetType, String phoneNum, long callDate, String contactName, boolean showPhotoPicker) {
        HomeViewModel homeViewModel;
        long folderId = getFolderId();
        if (FolderModel.INSTANCE.isSpecialFolder(folderId)) {
            homeViewModel = this;
            homeViewModel.setFolderId(-4097L);
        } else {
            homeViewModel = this;
        }
        if (folderId == -4097 || FolderModel.INSTANCE.isSpecialFolder(folderId)) {
            folderId = 0;
        }
        NoteLoadDataEntity noteLoadDataEntity = new NoteLoadDataEntity(-2L, folderId, "", 0, System.currentTimeMillis(), widgetId, widgetType, fromFloatWindow, phoneNum, callDate, contactName);
        noteLoadDataEntity.setShowGalleryPicker(showPhotoPicker);
        setSelectNote$default(homeViewModel, noteLoadDataEntity, false, false, 6, null);
    }

    private final void createNormalNote(boolean fromFloatWindow, int widgetId, int widgetType, String phoneNum, long callDate, String contactName) {
        HomeViewModel homeViewModel;
        long folderId = getFolderId();
        if (FolderModel.INSTANCE.isSpecialFolder(folderId)) {
            homeViewModel = this;
            homeViewModel.setFolderId(-4097L);
        } else {
            homeViewModel = this;
        }
        if (folderId == -4097 || FolderModel.INSTANCE.isSpecialFolder(folderId)) {
            folderId = 0;
        }
        setSelectNote$default(homeViewModel, new NoteLoadDataEntity(-2L, folderId, "", 0, System.currentTimeMillis(), widgetId, widgetType, fromFloatWindow, phoneNum, callDate, contactName), false, false, 6, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onOpenNote(long folderId, NoteCache noteCache, String searchText) {
        NoteLoadDataEntity noteLoadDataEntity;
        NoteLoadDataEntity noteLoadDataEntity2;
        boolean z;
        Intrinsics.checkNotNullParameter(noteCache, "noteCache");
        if (noteCache.getNoteContentType() == 5) {
            String mindContent = noteCache.getNote().getMindContent();
            if (TextUtils.isEmpty(mindContent)) {
                z = 1;
            } else {
                Gson gson = new Gson();
                Intrinsics.checkNotNull(mindContent);
                String strSubstring = mindContent.substring(15);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                z = ((MindEntity) gson.fromJson(strSubstring, MindEntity.class)).isMap;
            }
            noteLoadDataEntity2 = new NoteLoadDataEntity(noteCache.getNote().getId(), folderId, searchText, 1, noteCache.getNote().getUpdatedAt(), !z, 1, noteCache.getNote().getTitle(), false, noteCache.getHighlightKeywords());
        } else {
            if (noteCache.getNoteContentType() == 6) {
                noteLoadDataEntity = new NoteLoadDataEntity(noteCache.getNote().getId(), folderId, searchText, 2, System.currentTimeMillis(), false, noteCache.getHighlightKeywords());
            } else if (noteCache.getNoteContentType() == 7) {
                noteLoadDataEntity = new NoteLoadDataEntity(noteCache.getNote().getId(), folderId, searchText, 3, System.currentTimeMillis(), false, noteCache.getHighlightKeywords());
            } else {
                noteLoadDataEntity = new NoteLoadDataEntity(noteCache.getNote().getId(), folderId, searchText, 0, noteCache.getNote().getUpdatedAt(), false, noteCache.getHighlightKeywords());
            }
            noteLoadDataEntity2 = noteLoadDataEntity;
        }
        noteLoadDataEntity2.mHasNewStyle = NoteOperationHelper.hasNewStyle(noteCache.getNote().getSnippet());
        setSelectNote$default(this, noteLoadDataEntity2, false, false, 6, null);
    }

    public final LiveData<Boolean> getToDetailPage() {
        return this._toDetailPage;
    }

    public final void setToDetailPage(boolean toDetail) {
        this._toDetailPage.setValue(Boolean.valueOf(toDetail));
    }

    public final Boolean isToDetailPage() {
        return this._toDetailPage.getValue();
    }

    public final ActivityResultLauncher<Intent> getToDetailActivityResultLauncher() {
        ActivityResultLauncher<Intent> activityResultLauncher = this.toDetailActivityResultLauncher;
        if (activityResultLauncher != null) {
            return activityResultLauncher;
        }
        Intrinsics.throwUninitializedPropertyAccessException("toDetailActivityResultLauncher");
        return null;
    }

    public final void setToDetailActivityResultLauncher(ActivityResultLauncher<Intent> activityResultLauncher) {
        Intrinsics.checkNotNullParameter(activityResultLauncher, "<set-?>");
        this.toDetailActivityResultLauncher = activityResultLauncher;
    }

    public final void setupToDetailActivityResultLauncher(BaseListPageFragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.home.viewmodel.HomeViewModel$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                HomeViewModel.setupToDetailActivityResultLauncher$lambda$0(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        setToDetailActivityResultLauncher(activityResultLauncherRegisterForActivityResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToDetailActivityResultLauncher$lambda$0(HomeViewModel homeViewModel, ActivityResult activityResult) {
        Log.d(TAG, "setupLauncher edit back: ");
        homeViewModel.setToDetailPage(false);
    }

    public final void launchToDetailActivity(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        getToDetailActivityResultLauncher().launch(intent);
        setToDetailPage(true);
    }

    public final void launchToDetailActivity(Intent intent, ActivityOptionCompatImpl activityOptionCompatImpl) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(activityOptionCompatImpl, "activityOptionCompatImpl");
        getToDetailActivityResultLauncher().launch(intent, activityOptionCompatImpl);
        setToDetailPage(true);
    }

    public final ActivityResultLauncher<Intent> getToDetailLauncher() {
        ActivityResultLauncher<Intent> activityResultLauncher = this.toDetailLauncher;
        if (activityResultLauncher != null) {
            return activityResultLauncher;
        }
        Intrinsics.throwUninitializedPropertyAccessException("toDetailLauncher");
        return null;
    }

    public final void setToDetailLauncher(ActivityResultLauncher<Intent> activityResultLauncher) {
        Intrinsics.checkNotNullParameter(activityResultLauncher, "<set-?>");
        this.toDetailLauncher = activityResultLauncher;
    }

    public final void setupToDetailLauncher(BaseListPageFragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.miui.notes.home.viewmodel.HomeViewModel$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                HomeViewModel.setupToDetailLauncher$lambda$1(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        setToDetailLauncher(activityResultLauncherRegisterForActivityResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupToDetailLauncher$lambda$1(HomeViewModel homeViewModel, ActivityResult activityResult) {
        Log.d(TAG, "setupLauncher edit back: ");
        homeViewModel.toDetailResultCallback();
    }

    public final void createNoteFromIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        int intExtra = intent.getIntExtra("com.miui.notes.widget_id", 0);
        int intExtra2 = intent.getIntExtra("com.miui.notes.widget_type", -1);
        boolean booleanExtra = intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false);
        long longExtra = intent.getLongExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, 0L);
        intent.getBooleanExtra(ShortcutsTools.IS_FROM_OUTER_INTENT, false);
        String stringExtra = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
        String stringExtra2 = intent.getStringExtra(Notes.Intents.INTENT_EXTRA_CONTACT_NAME);
        Bundle extras = intent.getExtras();
        if (StringsKt.equals$default(extras != null ? extras.getString("action", "") : null, "to_gallery_picker", false, 2, null)) {
            createNormalNote(booleanExtra, intExtra, intExtra2, stringExtra, longExtra, stringExtra2, true);
            Bundle extras2 = intent.getExtras();
            Intrinsics.checkNotNull(extras2);
            extras2.remove("action");
            return;
        }
        createNormalNote(booleanExtra, intExtra, intExtra2, stringExtra, longExtra, stringExtra2);
    }

    public final void setClickedNotePosition(int clickedNotePosition) {
        this._clickedNotePosition = clickedNotePosition;
    }

    /* JADX INFO: renamed from: getClickedNotePosition, reason: from getter */
    public final int get_clickedNotePosition() {
        return this._clickedNotePosition;
    }
}
