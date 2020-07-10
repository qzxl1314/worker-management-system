package com.example.appqzx.activity.activity;

import android.content.ContentValues;
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
import com.example.appqzx.activity.tool.infoadapter;
import com.example.appqzx.activity.util.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class info extends AppCompatActivity {
    private Spinner work;
    private Spinner People;
    private Spinner what;
    private EditText content;
    private SQLiteDatabase sqldb;
    private com.example.appqzx.activity.tool.info info1=new com.example.appqzx.activity.tool.info();
    private Date date;
    private Button button;
    private DBHelper dbhelper;
    private List<String> personname=new LinkedList<>();
    private List<String> workname=new LinkedList<>();
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    private ListView lv;
    String[] What={"未审核","延期"};
    String work1;
    String name ;
    String what1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        work=findViewById(R.id.spinner2);
        People=findViewById(R.id.spinner);
        content=findViewById(R.id.editText3);
        button=findViewById(R.id.button3);
        what=findViewById(R.id.spinner3);
        lv=findViewById(R.id.ad);
        dbhelper = new DBHelper(this, "social", null, 1);
        querywork();
        queryperson();
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,workname);

        //设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        work.setAdapter(adapter1);

        //添加事件Spinner事件监听
        work.setOnItemSelectedListener(new info.SpinnerSelectedListener());

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,personname);

        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        People.setAdapter(adapter2);

        //添加事件Spinner事件监听
        People.setOnItemSelectedListener(new info.SpinnerSelectedListener1());

        adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,What);

        //设置下拉列表的风格
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        what.setAdapter(adapter3);

        //添加事件Spinner事件监听
        what.setOnItemSelectedListener(new info.SpinnerSelectedListener2());
        queryinfoall();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.isEmpty()||name.equals("")||work1.isEmpty()||work1.equals("")){
                    ToastUtils.show(info.this,"员工名和业务名不可为空！");
                }else {
                    init();
                }
                content.setText(null);
            }
        });

    }
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            work1=workname.get(arg2);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class SpinnerSelectedListener1 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            name=personname.get(arg2);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class SpinnerSelectedListener2 implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            what1=What[arg2];
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    private void init() {
        info1.setWork(work1);
        info1.setPerson(name);
        info1.setContent(content.getText().toString());
        info1.setWhat(what1);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");

        long nowMills=System.currentTimeMillis();

        String endTime=sdf3.format(nowMills);


        info1.setData(endTime);
        info1.setOffice(query(info1.getPerson()));
        //获得SQLiteDatabase对象，读写模式
        sqldb = dbhelper.getWritableDatabase();
        //ContentValues类似HashMap，区别是ContentValues只能存简单数据类型，不能存对象
        ContentValues values = new ContentValues();
        values.put("office_name", info1.getOffice());
        values.put("person_name", info1.getPerson());
        values.put("info_cpntent", info1.getContent());
        values.put("info_what",info1.getWhat());
        values.put("work_name",info1.getWork());
        values.put("info_time",info1.getData());

        //执行插入操作
        sqldb.insert("info", null, values);
        queryinfoall();
    }
    public String query(String person){
        //得到数据库对象
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("persom", new String[]{"person_office"}, "person_name=?", new String[]{person}, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        String map="";
        //遍历
        if(mCursor.getCount()!=0) {
             map=mCursor.getString(mCursor.getColumnIndex("person_office"));
        }else{
            String error="0";
            return error;
        }
        return map;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出程序后，关闭数据库资源
        sqldb.close();
    }
    public void querywork(){
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("work", null, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        //遍历
        if(mCursor.getCount()!=0) {
            do {
                workname.add(mCursor.getString(mCursor.getColumnIndex("work_name")));
            } while (mCursor.moveToNext());
        }
    }
    public void queryperson(){
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("persom", new String[]{"person_name"}, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        //遍历
        if(mCursor.getCount()!=0) {
            do {
                personname.add(mCursor.getString(mCursor.getColumnIndex("person_name")));
            } while (mCursor.moveToNext());
        }
    }
    public void queryinfoall(){
        final List<String[]> data=new LinkedList<>();
        sqldb = dbhelper.getReadableDatabase();
        //创建游标

        Cursor mCursor = sqldb.query("info", null, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        //遍历

        if (mCursor.getCount() != 0) {
            do {
                String[] dat = new String[6];
                dat[0] = mCursor.getString(mCursor.getColumnIndex("office_name"));
                dat[1] = mCursor.getString(mCursor.getColumnIndex("person_name"));
                dat[2] = mCursor.getString(mCursor.getColumnIndex("work_name"));
                dat[3] = mCursor.getString(mCursor.getColumnIndex("info_cpntent"));
                dat[4] = mCursor.getString(mCursor.getColumnIndex("info_what"));
                dat[5] = mCursor.getString(mCursor.getColumnIndex("info_id"));
                System.out.println(mCursor.getString(mCursor.getColumnIndex("info_time")));
                data.add(dat);
            } while (mCursor.moveToNext());
        }
        infoadapter adapter;
        adapter = new infoadapter(info.this,data);
        if (adapter==null){
            ToastUtils.show(info.this,"没有找到数据");
        }else {
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
                public void onDeleteClick(int i) {
                    String id=data.get(i)[5];
                    delete(id);
                    queryinfoall();
                }
            });
        }
    }
    private void delete(String workname) {
        sqldb = dbhelper.getWritableDatabase();
        //第二个参数是WHERE语句（即执行条件，删除哪条数据）
        //第三个参数是WHERE语句中占位符（即"?"号）的填充值
        sqldb.delete("info", "info_id=?", new String[]{workname});//删除name的值是jack的那条记录
    }
}
