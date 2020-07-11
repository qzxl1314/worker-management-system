package com.example.appqzx.activity.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appqzx.R;
import com.example.appqzx.activity.classtool.DBHelper;
import com.example.appqzx.activity.classtool.Utility;
import com.example.appqzx.activity.tool.MyAdapter;
import com.example.appqzx.activity.util.ToastUtils;

import java.util.LinkedList;
import java.util.List;

public class work extends AppCompatActivity {
    private Button input;
    private EditText work;
    private DBHelper dbhelper;
    private SQLiteDatabase sqldb;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        input=findViewById(R.id.button3);
        work=findViewById(R.id.editText5);
        dbhelper = new DBHelper(this, "social", null, 1);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = work.getText().toString();
                if (name.isEmpty()||name.equals("")){
                    ToastUtils.show(work.this,"业务名不可为空！");
                }else {
                    insert(name);
                }
                work.setText(null);
            }
        });
        getlist();

    }
    private void insert(String workname) {
        //获得SQLiteDatabase对象，读写模式
        sqldb = dbhelper.getWritableDatabase();
        Cursor mCursor = sqldb.query("work", null, "work_name=?", new String[]{workname}, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        if (mCursor.getCount()==0) {
            //ContentValues类似HashMap，区别是ContentValues只能存简单数据类型，不能存对象
            ContentValues values = new ContentValues();
            values.put("work_name", workname);
            //执行插入操作
            sqldb.insert("work", null, values);
            ToastUtils.show(work.this, "插入成功");
            getlist();
        }else{
            ToastUtils.show(work.this, "插入失败，数据已经存在！");
            work.setText("");
        }
    }
    public List<String> show() {
        //得到数据库对象
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("work", null, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        List<String> a=new LinkedList<>();
        //遍历
        if(mCursor.getCount()!=0) {
            do {
                    a.add(mCursor.getString(mCursor.getColumnIndex("work_name")));
            } while (mCursor.moveToNext());
        }
        return a;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出程序后，关闭数据库资源
        sqldb.close();
    }
    public void getlist() {
        final List<String> data=show();
        System.out.println(data.toString());
        ListView lv=findViewById(R.id.ab);
        adapter = new MyAdapter(work.this,data);
        lv.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv);
        //ListView item的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new MyAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(final int i) {
                Dialog dialog = new AlertDialog.Builder(work.this)

                        .setTitle("删除信息？")  // 创建标题

                        .setMessage("您确定要删除这条信息吗？")    //表示对话框的内容

                        .setIcon(R.drawable.ic_launcher) //设置LOGO

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        }).setPositiveButton("删除", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String work_name=data.get(i);
                                delete(work_name);
                                getlist();

                            }

                        }).create();  //创建对话框

                dialog.show();  //显示对话框

            }
        });

    }
    private void delete(String workname) {
        sqldb = dbhelper.getWritableDatabase();
        //第二个参数是WHERE语句（即执行条件，删除哪条数据）
        //第三个参数是WHERE语句中占位符（即"?"号）的填充值
        sqldb.delete("work", "work_name=?", new String[]{workname});//删除name的值是jack的那条记录
    }
}
