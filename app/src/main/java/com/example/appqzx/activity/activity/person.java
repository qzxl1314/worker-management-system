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
import android.widget.Spinner;

import com.example.appqzx.R;
import com.example.appqzx.activity.classtool.DBHelper;
import com.example.appqzx.activity.classtool.Utility;
import com.example.appqzx.activity.tool.MyAdapter;
import com.example.appqzx.activity.tool.personada;
import com.example.appqzx.activity.util.ToastUtils;


import java.util.LinkedList;
import java.util.List;


public class person extends AppCompatActivity {
    private Button input;
    private EditText work;
    private Spinner office;
    private DBHelper dbhelper;
    private SQLiteDatabase sqldb;
    private personada adapter;
    private List<String> workname=new LinkedList<>();
    private ArrayAdapter<String> adapter1;
    String office1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        input=findViewById(R.id.button3);
        work=findViewById(R.id.editText5);
        office=findViewById(R.id.spinner3);
        dbhelper = new DBHelper(this, "social", null, 1);
        querywork();
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,workname);

        //设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        office.setAdapter(adapter1);

        //添加事件Spinner事件监听
        office.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        office.setVisibility(View.VISIBLE);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = work.getText().toString();
                if (name.isEmpty()||name.equals("")||office1.isEmpty()||office1.equals("")){
                    ToastUtils.show(person.this,"员工名和部门名不可为空！");
                }else {
                    insert(name,office1);
                }
                work.setText(null);
            }
        });
        getlist();

    }
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            office1=workname.get(arg2);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    private void insert(String name,String office1) {
        //获得SQLiteDatabase对象，读写模式
        sqldb = dbhelper.getWritableDatabase();
        Cursor mCursor = sqldb.query("persom", null, "person_name=?", new String[]{name}, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        if (mCursor.getCount()==0) {
            //ContentValues类似HashMap，区别是ContentValues只能存简单数据类型，不能存对象
            ContentValues values = new ContentValues();
            values.put("person_name", name);
            values.put("person_office",office1);
            //执行插入操作
            sqldb.insert("persom", null, values);
            ToastUtils.show(this, "插入成功");
            getlist();
        }else{
            ToastUtils.show(person.this, "插入失败，数据已经存在！");
            work.setText("");
        }
    }
    public  List<String[]> show() {
        //得到数据库对象
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("persom", null, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        List<String[]> a=new LinkedList<>();
        //遍历
        if(mCursor.getCount()!=0) {
            do {

                String[] map=new String[]{mCursor.getString(mCursor.getColumnIndex("person_name")),mCursor.getString(mCursor.getColumnIndex("person_office"))};
                a.add(map);
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
        final    List<String[]> data=show();
        System.out.println(data.toString());
        ListView lv=findViewById(R.id.ab);
        adapter = new personada(person.this,data);
        lv.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv);
        //ListView item的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        //ListView item 中的删除按钮的点击事件
        adapter.setOnItemDeleteClickListener(new personada.onItemDeleteListener() {
            @Override
            public void onDeleteClick(final int i) {
                Dialog dialog = new AlertDialog.Builder(person.this)

                        .setTitle("删除信息？")  // 创建标题

                        .setMessage("您确定要删除这条信息吗？")    //表示对话框的内容

                        .setIcon(R.drawable.ic_launcher) //设置LOGO

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        }).setPositiveButton("删除", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String work_name=data.get(i)[0];
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
        sqldb.delete("persom", "person_name=?", new String[]{workname});//删除name的值是jack的那条记录
    }
    public void querywork(){
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("office", null, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        //遍历
        if(mCursor.getCount()!=0) {
            do {

                workname.add(mCursor.getString(mCursor.getColumnIndex("office_name")));
            } while (mCursor.moveToNext());
        }
    }
}