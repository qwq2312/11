package com.example.weatherdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherdemo.bean.Body;
import com.example.weatherdemo.bean.Forecast;
import com.example.weatherdemo.bean.ResponseData;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.CallBackProxy;
import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.edu.heuet.editspinner.EditSpinner;

public class MainActivity extends AppCompatActivity {
    private String cityName;
    private TextView tvContent;
    private String cityCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 读取 XML 数据
        String[] cityCodeArray = getResources().getStringArray(R.array.cityCode);

        String[] cityNameArray = getResources().getStringArray(R.array.cityName);
        List<String> cityNameList = Arrays.asList(cityNameArray);
        List<String> cityCodeList = Arrays.asList(cityCodeArray);
        // 将数据填充给 Spinner 控件
        EditSpinner editSpinner = findViewById(R.id.editspinner);
        editSpinner.setItemData(cityCodeList);

        tvContent = findViewById(R.id.tv_content);
        Button btSearch = findViewById(R.id.bt_search);
        Button btRefresh= findViewById(R.id.bt_refresh);
        //刷新数据
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DoubleClickHelper.isOnDoubleClick(3000)) {
                    Toast.makeText(MainActivity.this, "刷新频繁！", Toast.LENGTH_SHORT).show();
                    return;
                }
                cityCode = editSpinner.getText();
                // 查找城市代码
                int index = cityCodeList.indexOf(cityCode);
                if (index == -1) {
                    Toast.makeText(MainActivity.this, "输入城市编码未录入系统,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://t.weather.itboy.net/api/weather/city/" + cityCode;
                cityName=cityNameArray[index];//设置显示天气预报信息时显示城市名而不是编码

                // Xhttp2 请求网络,异步不做耗时操作

                XHttp.get(url)
                        .syncRequest(false)
                        .execute(new CallBackProxy<Body<ResponseData>, ResponseData>(
                                new SimpleCallBack<ResponseData>() {
                                    @Override
                                    public void onSuccess(ResponseData data) {
                                        // 处理 data
                                        dealWithData(data);
                                    }

                                    @Override
                                    public void onError(ApiException e) {
                                        // 处理异常
                                    }
                                }
                        ) {
                        });

            }
        });
        // 按钮点击响应事件
        btSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 防止快速点击
                if (DoubleClickHelper.isOnDoubleClick(3000)) {
                    Toast.makeText(MainActivity.this, "查询频繁！", Toast.LENGTH_SHORT).show();
                    return;
                }
                cityCode = editSpinner.getText();
                /*if (TextUtils.isEmpty(cityCode){
                    return;
                }

                 */
                // 查找城市代码
                int index = cityCodeList.indexOf(cityCode);
                if (index == -1) {
                    Toast.makeText(MainActivity.this, "输入城市编码未录入系统,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://t.weather.itboy.net/api/weather/city/" + cityCode;
                cityName=cityNameArray[index];//设置显示天气预报信息时显示城市名而不是编码
                SharedPreferences sp = getSharedPreferences("city_data" , MODE_PRIVATE);
                if(sp.contains("city_data")){
                    duhuancun();
                }

                // Xhttp2 请求网络,异步不做耗时操作

                XHttp.get(url)
                        .syncRequest(false)
                        .execute(new CallBackProxy<Body<ResponseData>, ResponseData>(
                                new SimpleCallBack<ResponseData>() {
                                    @Override
                                    public void onSuccess(ResponseData data) {
                                        // 处理 data
                                        dealWithData(data);
                                    }

                                    @Override
                                    public void onError(ApiException e) {
                                        // 处理异常
                                    }
                                }
                        ) {
                        });

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void dealWithData(ResponseData data) {
        if (data == null) {
            Toast.makeText(MainActivity.this, "结果异常！", Toast.LENGTH_SHORT).show();
            return;
        }
        Forecast todayForecast = data.getForecast().get(0);
        String today = buildForecastString(todayForecast, "今天");
        Forecast tomorrowForecast = data.getForecast().get(1);
        String tomorrow = buildForecastString(tomorrowForecast, "明天");
        huancun(todayForecast);
        huancun1(tomorrowForecast);
        tvContent.setText(today + tomorrow);
    }

    private String buildForecastString(Forecast forecast, String day) {

        Date date=new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sbf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return  "数据更新时间："+sbf.format(date)+"\n\n"+
                day + "【" + cityName + "】天气" + "\n" +
                "日期：" + forecast.getYmd() + "丨" +
                forecast.getWeek() + ";\n" +
                "天气类型：" + forecast.getType() + ";\n" +
                "最高温度：" + forecast.getHigh() + ";\n" +
                "最低温度：" + forecast.getLow() + ";\n" +
                "风力：" + forecast.getFl() + "丨" +
                forecast.getFx() + ";\n" +
                "日出时间："+forecast.getSunrise()+"\n"+
                "日落时间："+forecast.getSunset()+"\n"+
                "天气建议：" + forecast.getNotice() + ";\n\n";



    }
private void huancun(Forecast forecast){
    SharedPreferences sharedPreferences = getSharedPreferences("city_data", MODE_PRIVATE);
    sharedPreferences = getSharedPreferences("city_data", MODE_PRIVATE);//获取实例
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("name", cityName);
    editor.putString("code",cityCode);
    editor.putString("date",forecast.getYmd() );
    editor.putString("type",forecast.getType());
    editor.putString("week",forecast.getWeek() );
    editor.putString("high",forecast.getHigh());
    editor.putString("low",forecast.getLow());
    editor.putString("fl",forecast.getFl());
    editor.putString("fx",forecast.getFx());
    editor.putString("sunrise",forecast.getSunrise());
    editor.putString("sunset",forecast.getSunset());
    editor.putString("notice",forecast.getNotice());
    editor.apply();
}
    private void huancun1(Forecast forecast) {
        SharedPreferences sharedPreferences = getSharedPreferences("city_data", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("city_data", MODE_PRIVATE);//获取实例
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name1", cityName);
        editor.putString("code1", cityCode);
        editor.putString("date1", forecast.getYmd());
        editor.putString("type1", forecast.getType());
        editor.putString("week1", forecast.getWeek());
        editor.putString("high1", forecast.getHigh());
        editor.putString("low1", forecast.getLow());
        editor.putString("fl1", forecast.getFl());
        editor.putString("fx1", forecast.getFx());
        editor.putString("sunrise1", forecast.getSunrise());
        editor.putString("sunset1", forecast.getSunset());
        editor.putString("notice1", forecast.getNotice());
        editor.apply();
    }
    public String duhuancun(){
             SharedPreferences sp = getSharedPreferences("city_data" , MODE_PRIVATE);
        return "【" + cityName + "】天气" + "\n" +
                "日期：" +sp.getString("ymd","") + "丨" +
                sp.getString("week"," ") + ";\n" +
                "天气类型：" + sp.getString("type","")+ ";\n" +
                "最高温度：" + sp.getString("high","") + ";\n" +
                "最低温度：" + sp.getString("low","") + ";\n" +
                "风力：" + sp.getString("fl","") + "丨" +
                sp.getString("fx","") + ";\n" +
                "日出时间："+sp.getString("sunrise","")+"\n"+
                "日落时间："+sp.getString("sunset","")+"\n"+
                "天气建议：" + sp.getString("notice","") + ";\n\n"
        +"【" + cityName + "】天气" + "\n" +
                "日期：" +sp.getString("ymd1","") + "丨" +
                sp.getString("week1"," ") + ";\n" +
                "天气类型：" + sp.getString("type1","")+ ";\n" +
                "最高温度：" + sp.getString("high1","") + ";\n" +
                "最低温度：" + sp.getString("low1","") + ";\n" +
                "风力：" + sp.getString("fl1","") + "丨" +
                sp.getString("fx1","") + ";\n" +
                "日出时间："+sp.getString("sunrise1","")+"\n"+
                "日落时间："+sp.getString("sunset1","")+"\n"+
                "天气建议：" + sp.getString("notice1","") + ";\n\n";
    }
}