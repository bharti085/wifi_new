package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import static com.github.karthyks.runtimepermissions.PermissionActivity.REQUEST_PERMISSION_CODE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.karthyks.runtimepermissions.Permission;
import com.github.karthyks.runtimepermissions.googleapi.LocationSettingsHelper;
import com.google.android.gms.location.LocationRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private Button permissions;
    ImageView imageView2;
    Uri myUri;
    Button button;
    Button buttons;
    List results;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("EXTRA_IMAGEVIEW_URL"));
        imageView2.setImageURI(myUri);
        addTouchListener();
        button = (Button) findViewById(R.id.button2);
        buttons = (Button) findViewById(R.id.buttonheatmap);
        buttons.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnabled();
                i++;
            }
        });
    }

    private void checkEnabled() {
        if (i > 10) {
            buttons.setEnabled(true);
        }
    };
    private void addTouchListener(){
        imageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                String msg = String.format("Coordinates : (%.2f,%.2f)",x,y);
                Log.d( "coordinates", msg);
                Toast.makeText(MainActivity2.this,"X: "+x+" Y: "+y, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        }
    }
