package com.example.sturmgewehr44.democrazy;

import android.app.Activity;
import java.util.Random;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.content.res.Resources;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.WindowInsets;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import java.util.List;
import android.hardware.SensorEventListener;


public class MainViewWatch extends AppCompatActivity implements SensorEventListener {

    private TextView mTextView;
    private Context ctx;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float current = 0.0F;
    private String zipcode;
    private boolean secondpipe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_watch);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        ctx = this;
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//                mTextView.setText("dogg");
//                System.out.println("u suck");
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                int cases = 0;
                if (extras != null) {
                    cases = Integer.parseInt(extras.getString("cases"));
                } else {
                    System.out.println("first");
                }
                final Resources res = getResources();

                final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
                FragmentManager fragmentManager = getFragmentManager();
                GridPagerAdapter gpa = new GridPagerAdapter(ctx, fragmentManager);
                if (cases == 0) {
                    System.out.println("fal");
                } else {
                    ArrayList<String> data = new ArrayList<String>();
                    for (int i = 0; i < cases + 2; i++) {
                        data.add(extras.getString("sen" + Integer.toString(i + 1)));
                        data.add(extras.getString("par" + Integer.toString(i + 1)));
                        data.add(Integer.toString(i + 1));
                    }
                    zipcode = extras.getString("ZIPCODE");
                    data.add(zipcode);
                    gpa.overridePages(cases + 2, data);
                }
                pager.setAdapter(gpa);

                mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
        });
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (mAccelerometer != null) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (x + y + z != current && x + y + z > 1000) {
                System.out.println(x);
                System.out.println(y);
                System.out.println(z);
                System.out.println("doge");
                if (zipcode != null) {
                    Intent shake = new Intent(getBaseContext(), WatchToPhoneService.class);
                    shake.putExtra("/CASE", "SHAKE");
                    int zip = Integer.parseInt(zipcode);
                    Random rand = new Random();
                    int value = rand.nextInt(99999);
                    if ((zip % 2) == (value % 2)) {
                        zip = value + 1;
                    } else {
                        zip = value;
                    }
                    zipcode = Integer.toString(zip);
                    shake.putExtra("ZIPCODE", zipcode);
                    startService(shake);
                }
                current = x + y + z;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager =  (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
