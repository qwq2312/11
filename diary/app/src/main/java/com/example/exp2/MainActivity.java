package com.example.exp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/************日记本主活动********************/
public class MainActivity extends AppCompatActivity {
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("日记本");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDbHelper(this, "diary.db", null, 1);//创建数据库
        dbHelper.getWritableDatabase();

        /*新建日记按钮，从SharedPreferences中获取默认标题和作者名字，以此在数据库中创建新日记，
        然后用Intent启动“编辑日记活动”，并用“isNew”告诉“编辑日记活动”这是新日记。*/
        Button btnNewDiary = findViewById(R.id.new_diary);
        btnNewDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("author_data", MODE_PRIVATE);
                String defaultTitle = sharedPreferences.getString("default_title", "无标题日记");
                String authorName = sharedPreferences.getString("author_name", "佚名");
                dbHelper.createDiary(defaultTitle, authorName);
                Intent intent = new Intent(MainActivity.this, EditDiaryActivity.class);
                intent.putExtra("isNew", 1);//传递数据
                startActivity(intent);
            }
        });
        ListView listView = findViewById(R.id.list_view);
        /*ListView的点击事件：用Intent启动“编辑日记活动”，并用position告诉“编辑日记活动”所选日记在数据库的位置*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //启动新活动
                Intent intent = new Intent(MainActivity.this, EditDiaryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        /*ListView的长按事件：用对话框AlertDialog询问用户是否要删除日记，若点击确定，
        则用cursor获取position对应的_id值，然后用dbHelper的deleteDiary()方法删除日记*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                deleteDialog.setTitle("确定删除这篇日记？");
                //添加AlertDialog.Builder对象的setPositiveButton()方法
                deleteDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor cursor = dbHelper.getDiaryListCursor();
                        cursor.moveToPosition(position);
                        int _id = cursor.getInt(cursor.getColumnIndex(MyDbHelper._ID));
                        dbHelper.deleteDiary(_id);
                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                });
                //添加AlertDialog.Builder对象的setNegativeButton()方法
                deleteDialog.setNegativeButton("取消", null);
                deleteDialog.create().show();
                refresh();
                return true;
            }
        });
    }

    /*添加Menu项目*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "编辑作者信息..");
        menu.add(0, 1, 0, "删除所有日记..");
        return super.onCreateOptionsMenu(menu);
    }

    /*对Menu项目的点击做出相应，若选择“编辑作者信息”，则用Intent启动“编辑作者信息活动”，
    若选择“删除所有日记”，则用对话框AlertDialog询问用户是否删除所有日记，若确定，则
    用dbHelper的deleteAllDiary()方法删除所有日记。*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(MainActivity.this, AuthorInfoActivity.class);
                startActivity(intent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确定删除所有日记？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteAllDiary();
                        refresh();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*每次运行这个活动时，都用refresh()方法刷新页面*/
    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }

    /*此方法用来刷新页面，先得到日记的cursor游标，from数组存放数据来源，to数组存放数据的目的地，
     * 用ViewBinder格式化数据，如字体，日期时间的格式，还有日记的预览字数等，
     * 然后装填进SimpleCursorAdapter适配器，最后在底部显示日记数量*/
    @SuppressLint("SetTextI18n")
    public void refresh() {
        android.database.Cursor cursor = dbHelper.getDiaryListCursor();
        String[] from = new String[]{MyDbHelper.TITLE, MyDbHelper.CREATE_TIME, MyDbHelper.CONTENT};
        int[] to = new int[]{R.id.list_item_title, R.id.list_item_create_time, R.id.list_item_content};
        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {

            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (cursor.getColumnIndex(MyDbHelper.TITLE) == columnIndex) {
                    String title = cursor.getString(columnIndex);
                    TextView tv = (TextView) view;
                    tv.setText(title);
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    return true;
                }
                if (cursor.getColumnIndex(MyDbHelper.CREATE_TIME) == columnIndex) {
                    long create_time_long = cursor.getLong(columnIndex);
                    java.util.Date date = new Date(create_time_long);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    String create_time = simpleDateFormat.format(date);
                    TextView tv = (TextView) view;
                    tv.setText(create_time);
                    return true;
                }
                if (cursor.getColumnIndex(MyDbHelper.CONTENT) == columnIndex) {
                    String content = cursor.getString(columnIndex);
                    if (content.length() >= 15)
                        content = content.substring(0, 15) + "…";
                    content = content.replaceAll("[\r\n]", " ");
                    TextView tv = (TextView) view;
                    tv.setText(content);
                    return true;
                }
                return false;
            }
        };
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this, R.layout.diary_list_item, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        simpleCursorAdapter.setViewBinder(viewBinder);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(simpleCursorAdapter);
        TextView count_tv = findViewById(R.id.count);
        count_tv.setText("共" + cursor.getCount() + "篇日记");
    }

}