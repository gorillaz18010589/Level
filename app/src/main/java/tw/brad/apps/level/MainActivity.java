package tw.brad.apps.level;
//目的:三軸儀
//res => values => styles => 把這複製上去
//res => values => styles => 把別人寫好得style加上去
//在檔案總管加上android:theme="@style/Brad"樣式
//1.android developer sensor(感應器)  :https://developer.android.com/guide/topics/sensors/sensors_overview
//        *側移動
//        *方向
//        *環境參數
//現在的版本幾乎全支援軟體部分,但硬體手機未必有感應器
//        TYPE_ACCELEROMETER:三軸加速感應器,翻轉手機
//        TYPE_GRAVITY:重力感應器
//        TYPE_LIGHT:光度感應器
//        TYPE_LINEAR_ACCELERATION:加速度感應器
//        TYPE_MAGNETIC_FIELD:磁極感應器
//        TYPE_ORIENTATION:方向感應器
//        TYPE_PRESSURE:大氣壓力感應器
//        TYPE_PROXIMITY:接近感應器
//        TYPE_RELATIVE_HUMIDITY:濕度感應器
//        TYPE_TEMPERATURE:環境溫度感應器(並不是手機cpu)
//擺在andorid.hardware
//SensorManager:感應器管理員
// Sensor:拿到了要有一個事件發生由SensorEventListener去聽
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private MyListener myListener;
    private MyView myView;
    private float viewW, viewH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.myView);
        viewW = getWindowManager().getDefaultDisplay().getWidth(); //取得螢幕的寬
        viewH = getWindowManager().getDefaultDisplay().getHeight();//取得螢幕的高
        Log.v("brad", viewW + "x" + viewH);

        //取得senor的數據
        sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors){
            Log.v("brad", sensor.getName() + ":" + sensor.getStringType() +
                    ":" + sensor.getVendor());
        }
        //調整要玩的種類
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//取得要玩的物件(三軸加速器)

    }

    //在手機開始時
    @Override
    protected void onResume() {
        super.onResume();
        myListener = new MyListener();//呼叫自己寫好的方法
        sensorManager.registerListener(myListener, sensor, SensorManager.SENSOR_DELAY_UI);//設定sensor的事件(1.事件,sensor,SensoMagrer的感應快慢)
    }
    //手機暫停時
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(myListener);
    }

    //Sensor事件監聽者
    private class MyListener implements SensorEventListener {

        //在值有變動時
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values; //抓到值
            changeBall((int)(values[0]*10),(int)(values[1]*10) ,(int)(values[2]*10)); //呼叫方法帶入參數(x座標,y做標)
        }
        //抓精準的值
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
    //讓球移動的方法
    private void changeBall(int x,int y, int z){
        float xx = x*viewW/200 + viewW/2;//讓寬一開始為0 + 偏移量移動到螢幕的一半寬為值
        float yy = y*viewH/200 + viewH/2;//讓高一開始為0 + 偏移量移動到螢幕的一半寬為值
        float zz = z - 50;
        myView.setBallXY(xx,yy, zz);//在我寫的view顯示出來

        //Log.v("brad", "x = " + x + "; xx = " + xx);
    }



}