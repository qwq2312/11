package com.example.exp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**********编辑日记活动**************/
public class EditDiaryActivity extends AppCompatActivity {
    private MyDbHelper dbHelper;
    private int _id;
    Intent intent;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private EditText et_content;
    private ImageView imageView;
    private String title;
    private String content;
    private String photo_path;
    private String temp_photo_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);
        intent = getIntent();
        dbHelper = new MyDbHelper(this, "diary.db", null, 1);
        imageView = findViewById(R.id.picture);
    }

    /*每次启动这个活动都刷新页面*/
    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }

    /*每次离开这个活动都保存日记*/
    @Override
    protected void onPause() {
        updateDiary();
        super.onPause();
    }

    /*添加五个菜单项目参数含义是组，项目编号，显示顺序，内容*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "修改标题");
        menu.add(0, TAKE_PHOTO, 0, "拍照");
        menu.add(0, CHOOSE_PHOTO, 0, "从相册选择");
        menu.add(0, 3, 0, "删除图片");
        menu.add(0, 4, 0, "删除日记");
        return super.onCreateOptionsMenu(menu);
    }

    /*根据不同的选择做出不同的操作*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /*case0：用AlertDialog+EditText让用户输入新的标题，按完成键则保存标题*/
            case 0:
                final EditText editText = new EditText(this);
                editText.setText(getTitle());
                editText.setTextSize(24);
                editText.setMaxWidth(40);
                editText.setSingleLine(true);
                AlertDialog.Builder editTitleDialog = new AlertDialog.Builder(this);
                editTitleDialog.setTitle("修改标题").setView(editText);
                editTitleDialog.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title = editText.getText().toString();
                        updateDiary();
                        refresh();
                        Toast.makeText(EditDiaryActivity.this, "修改标题成功", Toast.LENGTH_SHORT).show();
                    }
                });
                editTitleDialog.show();
                break;
            /*case1：启动相机*/
            case 1:
                takePhoto();
                break;
            /*case2：选择图片*/
            case 2:
                choosePhoto();
                break;
            case 3:
                /*case3：删除图片*/
                photo_path = "";
                updateDiary();
                displayImage();
                break;
            case 4:
                /*case4：删除日记*/
                dbHelper.deleteDiary(_id);
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*此方法用于刷新页面，用cursor获取数据库中存放的_id、title、author、create_time、
     * content、photo_path等值。调用displayImage()显示图片。
     */
    private void refresh() {
        /*若这个是新日记，则cursor游标移动到末尾，
         * 否则，cursor游标移动到该日记的position*/
        Cursor cursor = dbHelper.getDiaryListCursor();
        if (intent.getIntExtra("isNew", 0) == 1)
            cursor.moveToLast();
        else {
            cursor.moveToPosition(intent.getIntExtra("position", 0));
        }
        _id = cursor.getInt(cursor.getColumnIndex(MyDbHelper._ID));
        title = cursor.getString(cursor.getColumnIndex(MyDbHelper.TITLE));
        setTitle(title);

        String author = cursor.getString(cursor.getColumnIndex(MyDbHelper.AUTHOR));
        TextView tv_author = findViewById(R.id.author);
        tv_author.setText(author);

        long create_time_long = cursor.getLong(cursor.getColumnIndex(MyDbHelper.CREATE_TIME));
        java.util.Date date = new Date(create_time_long);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String create_time = simpleDateFormat.format(date);
        TextView tv_create_time = findViewById(R.id.create_time);
        tv_create_time.setText(create_time);

        content = cursor.getString(cursor.getColumnIndex(MyDbHelper.CONTENT));
        et_content = findViewById(R.id.diary_content);
        et_content.setText(content);

        photo_path = cursor.getString(cursor.getColumnIndex(MyDbHelper.PHOTO_PATH));
        displayImage();
    }

    /*显示图片：如果图片路径不为空，则用BitmapFactory解码文件并显示在imageView里，
     * 如果图片路径为空，则不显示图片*/
    private void displayImage() {
        if (photo_path != null && !photo_path.equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(photo_path);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageDrawable(null);
        }
    }

    /*更新日记：获取EditText-content中的Text，然后用dbHelper的updateDiary()更新日记*/
    private void updateDiary() {
        content = et_content.getText().toString();
        dbHelper.updateDiary(_id, title, content, photo_path);
    }

    /*拍照主程序：先建立一个文件，文件路径保存进临时照片路径temp_photo_path中，
    再用FileProvider获取它的uri，创建相机Intent，启动相机活动并监听结果*/
    private void takePhoto() {
        File outputImage = new File(getExternalFilesDir(null), _id + "_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp_photo_path = outputImage.getPath();
        //统一资源标识符
        Uri uri = FileProvider.getUriForFile(this, "com.example.exp2.fileProvider", outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /*从Intent中获取文件Path*/
    private void getPathFromUriFromIntent(Intent data) {
        Uri uri = data.getData();
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            photo_path = getImagePath(uri);
        } else
            photo_path = uri.getPath();
    }

    /*从uri中获取文件Path*/
    private String getImagePath(Uri uri) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,
                null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /*选择图片主程序：先获取权限，后打开相册*/
    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    /*打开相册*/
    private void openAlbum() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /*动态获取权限*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "You denied the permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*用于接收相机或相册的返回信息，并加以处理*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    photo_path = temp_photo_path;
                    updateDiary();
                    displayImage();
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    getPathFromUriFromIntent(data);
                    updateDiary();
                    displayImage();
                }
            default:
                break;
        }
    }
}