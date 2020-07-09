package com.example.appqzx.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appqzx.R;
import com.example.appqzx.activity.classtool.DBHelper;
import com.example.appqzx.activity.classtool.Utility;
import com.example.appqzx.activity.tool.MyAdapter;
import com.example.appqzx.activity.tool.infoadapter;
import com.example.appqzx.activity.util.ToastUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DBHelper dbhelper;
    private SQLiteDatabase sqldb;
    List<String > o=new  LinkedList<>();
    List<String > p=new  LinkedList<>();
    List<String > w=new  LinkedList<>();
    String officename="";
    String personname="";
    String workname="";
    String datetime="";
    Button in;
    Spinner office;
    Spinner person;
    Spinner work;
    ListView lv;
    List<String[]> data=new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in=findViewById(R.id.button);
        office=findViewById(R.id.spinner4);
        person=findViewById(R.id.spinner6);
        work=findViewById(R.id.spinner5);
        lv=findViewById(R.id.ad);
        dbhelper = new DBHelper(this, "social", null, 1);
        final EditText time=findViewById(R.id.editText4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        o=query(1);
        p= query(3);
        w=query(2);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,o);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        office.setAdapter(adapter);
        //添加事件Spinner事件监听
        office.setOnItemSelectedListener(new SpinnerSelectedListener());

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,p);
        //设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        person.setAdapter(adapter1);
        //添加事件Spinner事件监听
        person.setOnItemSelectedListener(new SpinnerSelectedListenerp());

        ArrayAdapter<String> adapter2;
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,w);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        work.setAdapter(adapter2);
        //添加事件Spinner事件监听
        work.setOnItemSelectedListener(new SpinnerSelectedListenerw());
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");

        long nowMills=System.currentTimeMillis();

        String endTime=sdf3.format(nowMills);
        time.setText(endTime);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                data.clear();
                datetime = time.getText().toString();
                queryinfo(officename,workname,personname,datetime);
            }
        });
    }
    public void queryinfoall(){

        sqldb = dbhelper.getReadableDatabase();
        //创建游标

            Cursor mCursor = sqldb.query("info", null, null, null, null, null,
                    null);
            //游标置顶
            mCursor.moveToFirst();
            //遍历
            String[] dat = new String[6];
            if (mCursor.getCount() != 0) {
                do {
                    dat[0] = mCursor.getString(mCursor.getColumnIndex("office_name"));
                    dat[1] = mCursor.getString(mCursor.getColumnIndex("person_name"));
                    dat[2] = mCursor.getString(mCursor.getColumnIndex("work_name"));
                    dat[3] = mCursor.getString(mCursor.getColumnIndex("info_cpntent"));
                    dat[4]=mCursor.getString(mCursor.getColumnIndex("info_what"));
                    dat[5] = mCursor.getString(mCursor.getColumnIndex("info_id"));
                    data.add(dat);
                } while (mCursor.moveToNext());
            }
    }
    public void getlist() {
        infoadapter adapter;
        adapter = new infoadapter(MainActivity.this,data);
        if (adapter==null){
            ToastUtils.show(MainActivity.this,"没有找到数据");
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
                queryinfo(officename,workname,personname,datetime);
            }
        });
        }
    }
    private void delete(String id) {
        sqldb = dbhelper.getWritableDatabase();
        //第二个参数是WHERE语句（即执行条件，删除哪条数据）
        //第三个参数是WHERE语句中占位符（即"?"号）的填充值
        sqldb.delete("info", "info_id=?", new String[]{id});//删除name的值是jack的那条记录
    }

    class  SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
        long arg3) {
            officename=o.get(arg2);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class  SpinnerSelectedListenerp implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            personname=p.get(arg2);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class  SpinnerSelectedListenerw implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            workname=w.get(arg2);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        o=query(1);
        p= query(3);
        w=query(2);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,o);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        office.setAdapter(adapter);
        //添加事件Spinner事件监听
        office.setOnItemSelectedListener(new SpinnerSelectedListener());

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,p);
        //设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        person.setAdapter(adapter1);
        //添加事件Spinner事件监听
        person.setOnItemSelectedListener(new SpinnerSelectedListenerp());

        ArrayAdapter<String> adapter2;
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,w);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        work.setAdapter(adapter2);
        //添加事件Spinner事件监听
        work.setOnItemSelectedListener(new SpinnerSelectedListenerw());
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }else if(id==R.id.nav_mode){
            Intent intent = new Intent(getApplicationContext(), office.class);//启动MainActivity
            startActivity(intent);
        }else if (id==R.id.nav_in){
            Intent intent = new Intent(getApplicationContext(), person.class);//启动MainActivity
            startActivity(intent);
        } else if (id==R.id.nav_file){
        Intent intent = new Intent(getApplicationContext(), work.class);//启动MainActivity
        startActivity(intent);
        }
        else if (id==R.id.nav_info){
            Intent intent = new Intent(getApplicationContext(), info.class);//启动MainActivity
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public List<String> show() {
        //得到数据库对象
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
        Cursor mCursor = sqldb.query("info", null, null, null, null, null,
                null);
        //游标置顶
        mCursor.moveToFirst();
        List<String> a=new LinkedList<>();
        //遍历
        if(mCursor.getCount()!=0) {
            do {
                a.add(mCursor.getString(mCursor.getColumnIndex("office_name")));
            } while (mCursor.moveToNext());
        }
        return a;
    }
    public List<String > query(int i) {
        sqldb = dbhelper.getReadableDatabase();
        List<String> a = new LinkedList<>();
        switch (i) {
            case 1: {
                //创建游标
                Cursor mCursor = sqldb.query("office", null, null, null, null, null,
                        null);
                //游标置顶
                mCursor.moveToFirst();
                //遍历
                a.add("");
                if (mCursor.getCount() != 0) {
                    do {
                        a.add(mCursor.getString(mCursor.getColumnIndex("office_name")));
                    } while (mCursor.moveToNext());
                }
                return a;

            }
            case 2: {
                //创建游标
                Cursor mCursor = sqldb.query("work", null, null, null, null, null,
                        null);
                //游标置顶
                mCursor.moveToFirst();
                //遍历
                a.add("");
                if (mCursor.getCount() != 0) {
                    do {
                        a.add(mCursor.getString(mCursor.getColumnIndex("work_name")));
                    } while (mCursor.moveToNext());
                }
                return a;
            }
            case 3: {
                //创建游标
                Cursor mCursor = sqldb.query("persom", null, null, null, null, null,
                        null);
                //游标置顶
                mCursor.moveToFirst();
                //遍历
                a.add("");
                if (mCursor.getCount() != 0) {
                    do {
                        a.add(mCursor.getString(mCursor.getColumnIndex("person_name")));
                    } while (mCursor.moveToNext());
                }
                return a;
            }
            default:
                return null;
        }
    }
    public void queryinfo(String office, String work, String person,String date){
        data.clear();
        sqldb = dbhelper.getReadableDatabase();
        //创建游标
            Cursor mCursor = sqldb.query("info", null, "office_name=? or person_name=? or work_name=? or info_time=?", new String[]{office,person,work,date}, null, null,
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
                    dat[4]=mCursor.getString(mCursor.getColumnIndex("info_what"));
                    dat[5] = mCursor.getString(mCursor.getColumnIndex("info_id"));
                    data.add(dat);
                } while (mCursor.moveToNext());
            }
            getlist();

    }
}
