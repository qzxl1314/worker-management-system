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
import java.util.Map;

public class personada extends BaseAdapter {
    private Context mContext;
    private   List<String[]> mList = new ArrayList<>();

    public personada(Context context,List<String[]> list) {
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
        personada.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new personada.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item1, null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.item_tv);
            viewHolder.getmTextView1=view.findViewById(R.id.item_ta);
            viewHolder.mButton = (Button) view.findViewById(R.id.item_btn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (personada.ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText("员工姓名:"+mList.get(i)[0]);
        viewHolder.getmTextView1.setText("部门名:"+mList.get(i)[1]);
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

    private personada.onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(personada.onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    class ViewHolder {
        TextView mTextView;
        TextView getmTextView1;
        Button mButton;
    }
}

