package com.example.imagecoordinates;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.gesture.GestureLibraries;
import android.gesture.GestureOverlayView;
import android.hardware.GeomagneticField;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView imageView2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView2 = (ImageView) findViewById(R.id.imageView);
//        MyGestureListener ob1= new MyGestureListener();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        imageView2.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String DEBUG_TAG = "Gestures";
    float x;
    float y;
    int i=1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLongPress(MotionEvent event) {

        x = event.getX();
        y = event.getY();
        Log.d("X:", String.valueOf(x));
        Log.d("Y:", String.valueOf(y));
    }

}