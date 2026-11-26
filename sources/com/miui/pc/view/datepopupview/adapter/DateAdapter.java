package com.miui.pc.view.datepopupview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.notes.R;
import com.miui.pc.view.datepopupview.bean.DateEntity;
import com.miui.pc.view.datepopupview.utils.DateUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class DateAdapter extends RecyclerView.Adapter<DateViewHolder> {
    private static ArrayList<DateEntity> mWeekNames;
    private Context mContext;
    private String mCurrentMonth;
    private ArrayList<DateEntity> mDatas;
    private String mNowDate;
    private OnRecyItemClickListener mOnItemClickListener;
    private String mSelectedDate;

    static {
        ArrayList<DateEntity> arrayList = new ArrayList<>();
        mWeekNames = arrayList;
        arrayList.add(new DateEntity(1, "日"));
        mWeekNames.add(new DateEntity(1, "一"));
        mWeekNames.add(new DateEntity(1, "二"));
        mWeekNames.add(new DateEntity(1, "三"));
        mWeekNames.add(new DateEntity(1, "四"));
        mWeekNames.add(new DateEntity(1, "五"));
        mWeekNames.add(new DateEntity(1, "六"));
    }

    public void setData(ArrayList<DateEntity> arrayList) {
        if (arrayList == null) {
            return;
        }
        ArrayList<DateEntity> arrayList2 = this.mDatas;
        if (arrayList2 == null) {
            this.mDatas = new ArrayList<>();
        } else {
            arrayList2.clear();
        }
        this.mDatas.addAll(mWeekNames);
        this.mDatas.addAll(arrayList);
        notifyDataSetChanged();
    }

    public DateAdapter(Context context, ArrayList<DateEntity> arrayList, String str) {
        this.mContext = context;
        ArrayList<DateEntity> arrayList2 = new ArrayList<>();
        this.mDatas = arrayList2;
        arrayList2.addAll(mWeekNames);
        this.mDatas.addAll(arrayList);
        this.mSelectedDate = str;
        this.mCurrentMonth = DateUtils.getRealMonth(str);
        this.mNowDate = DateUtils.getCurrDate("yyyy-MM-dd");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DateViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_data, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(DateViewHolder dateViewHolder, int i) {
        final DateEntity dateEntity = this.mDatas.get(i);
        dateViewHolder.data.setText(this.mDatas.get(i).mDay);
        if (dateEntity.mType == 0) {
            if (!dateEntity.mMonth.equals(this.mCurrentMonth) || dateEntity.mIsUnAvaible) {
                dateViewHolder.data.setTextColor(1711276032);
            } else {
                dateViewHolder.data.setTextColor(-16777216);
            }
            final View view = dateViewHolder.itemView;
            if (this.mOnItemClickListener != null) {
                view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pc.view.datepopupview.adapter.DateAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        if (dateEntity.mIsUnAvaible) {
                            return;
                        }
                        if (dateEntity.mMonth.equals(DateAdapter.this.mCurrentMonth)) {
                            DateAdapter.this.mOnItemClickListener.onClick(view, dateEntity.mDate, 0);
                        } else {
                            DateAdapter.this.mOnItemClickListener.onClick(view, dateEntity.mDate, 1);
                        }
                    }
                });
            }
            if (this.mDatas.get(i).mIsToday) {
                if (this.mSelectedDate.equals(dateEntity.mDate)) {
                    dateViewHolder.bg.setBackground(this.mContext.getDrawable(R.drawable.selected_date_background));
                    dateViewHolder.data.setTextColor(-1);
                    return;
                } else {
                    dateViewHolder.bg.setBackground(null);
                    dateViewHolder.data.setTextColor(-17664);
                    return;
                }
            }
            if (this.mSelectedDate.equals(dateEntity.mDate)) {
                dateViewHolder.bg.setBackground(this.mContext.getDrawable(R.drawable.submit_button_background));
                dateViewHolder.data.setTextColor(-16777216);
                return;
            } else {
                dateViewHolder.bg.setBackground(null);
                return;
            }
        }
        dateViewHolder.data.setTextColor(1711276032);
        dateViewHolder.bg.setBackground(null);
        dateViewHolder.itemView.setClickable(false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDatas.size();
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        View bg;
        TextView data;

        public DateViewHolder(View view) {
            super(view);
            this.data = (TextView) view.findViewById(R.id.data);
            this.bg = view.findViewById(R.id.bg);
        }
    }

    public void setOnItemClickListener(OnRecyItemClickListener onRecyItemClickListener) {
        this.mOnItemClickListener = onRecyItemClickListener;
    }

    public void setSelectedDate(String str) {
        this.mSelectedDate = str;
        this.mCurrentMonth = DateUtils.getRealMonth(str);
        notifyDataSetChanged();
    }
}
