package com.example.appqzx.activity.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.appqzx.R;

import java.util.ArrayList;
import java.util.List;

public class infoadapter extends BaseAdapter {
    private Context mContext;
    private List<String[]> mList;

    public infoadapter(Context context, List<String[]> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        infoadapter.ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new infoadapter.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_info, null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.item_tv);
            viewHolder.mTextView1 = (TextView) view.findViewById(R.id.item_ta);
            viewHolder.mTextView2 = (TextView) view.findViewById(R.id.item_tt);
            viewHolder.mTextView3 = (TextView) view.findViewById(R.id.item_tf);
            viewHolder.mTextView4=view.findViewById(R.id.sd);
            viewHolder.mButton = (Button) view.findViewById(R.id.item_btn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (infoadapter.ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText("科室名："+mList.get(i)[0]);
        viewHolder.mTextView1.setText("员工名："+mList.get(i)[1]);
        viewHolder.mTextView2.setText("业务名："+mList.get(i)[2]);
        viewHolder.mTextView3.setText("备注："+mList.get(i)[3]);
        viewHolder.mTextView4.setText("类型："+mList.get(i)[4]);
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(i);
            }
        });
        return view;
    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private MyAdapter.onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(MyAdapter.onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    class ViewHolder {
        TextView mTextView;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;

        Button mButton;
    }
}



