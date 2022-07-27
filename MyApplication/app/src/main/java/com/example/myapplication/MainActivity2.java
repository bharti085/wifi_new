package com.example.myapplication;

import com.example.myapplication.MyGestureListener;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
//import static com.github.karthyks.runtimepermissions.PermissionActivity.REQUEST_PERMISSION_CODE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.util.Base64;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;


//import com.github.karthyks.runtimepermissions.Permission;
//import com.github.karthyks.runtimepermissions.googleapi.LocationSettingsHelper;
//import com.google.android.gms.location.LocationRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    private GestureDetectorCompat mDetector;
    private Button permissions;
    MyGestureListener ob = new MyGestureListener();
    ImageView imageView2;
    Uri myUri;
    Uri uri2;
    Button buttonM;
    Button bt;
    Button buttonCal;
    List results;
    int i = 1;
    //    Button bt;
    Handler handler;
    double operating_band;
    int rssi;
    String ssid = new String();
    String bssid = new String();
    int frequency;
    int Linkspeed;
    int ChannelNumber;
    int RxLinkSpeed;
    int TxLinkSpeed;
    int count;
    int Bandwidth;
    int SLNO = 0;
    int mynum;
    // Context context = this;
    String currentSSID = new String();
    String previousSSID = new String();
    File file;
    float x;
    float y;
    ContentResolver resolver;
    String base64;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("EXTRA_IMAGEVIEW_URL"));
        imageView2.setImageURI(myUri);
        System.out.println("1");
        System.out.println("1");
        Log.i("url", String.valueOf(myUri));
//        addTouchListener();
        buttonCal = (Button) findViewById(R.id.buttonheatmap);
        progress = new ProgressDialog(MainActivity2.this);
        buttonCal.setEnabled(false);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onLongPress(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());

            x = event.getX();
            y = event.getY();
            disp();
            savetofile();
            checkEnabled();
            i++;
            SLNO++;
            Toast.makeText(MainActivity2.this, "X: " + x + " Y: " + y + "  RSSI:" + rssi, Toast.LENGTH_SHORT).show();
            System.out.println("1");

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    public void disp() {
        //  try {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        rssi = wifiManager.getConnectionInfo().getRssi();
        bssid = wifiManager.getConnectionInfo().getBSSID();
        frequency = wifiManager.getConnectionInfo().getFrequency();

        Linkspeed = wifiManager.getConnectionInfo().getLinkSpeed();
//        RxLinkSpeed = wifiManager.getConnectionInfo().getRxLinkSpeedMbps();
//        TxLinkSpeed = wifiManager.getConnectionInfo().getTxLinkSpeedMbps();

        try {


            results = wifiManager.getScanResults();
            List<ScanResult> results = wifiManager.getScanResults();

            for (ScanResult result : results) {
                if (bssid.equals(result.BSSID)) {
                    if (result.channelWidth == 0) {
                        Bandwidth = 20;
                    } else if (result.channelWidth == 1) {
                        Bandwidth = 40;
                    } else if (result.channelWidth == 2) {
                        Bandwidth = 80;
                    } else if (result.channelWidth == 3) {
                        Bandwidth = 160;
                    } else if (result.channelWidth == 4) {
                        Bandwidth = 160;
                    } else {
                        Bandwidth = 320;
                    }

                    if (frequency >= 2412 && frequency < 2484) {
                        ChannelNumber = ((frequency - 2412) / 5 + 1);
                    } else if (frequency >= 5170 && frequency <= 5825) {
                        ChannelNumber = ((frequency - 5170) / 5 + 34);
                    } else if (frequency == 2484) {
                        ChannelNumber = 14;
                    }
                }
            }
            ssid = wifiInfo.getSSID();


            Log.d("Bharti", "currentSSID" + ssid);
            currentSSID = ssid;
            Log.d("Bharti", "prevSSID" + currentSSID);
            // to obtain the previous AP information
            if (previousSSID.equals(currentSSID)) {
//                st10.setText("\n\t\t" + currentSSID + "=" + SLNO);
                count = SLNO;
            } else if (currentSSID.equals("<unknown ssid>")) {
//                st10.setText("\n\t\t" + previousSSID + "=" + (SLNO));

            } else {
                if (count != 0) {
//                    st11.append("\t" + previousSSID + "=" + count);
                    SLNO = 1;
                }
            }
            if (!currentSSID.equals("<unknown ssid>")) {
                previousSSID = currentSSID;
            }


            if (frequency > 2400 && frequency < 3000)
                operating_band = 2.4;
            else
                operating_band = 5.0;

            // Display on the screen
            Log.d("Number of times=", String.valueOf(SLNO));
            Log.d("\t\tSignal Strength of ", ssid);
            Log.d("\t\tRSSI (dbm) = ", String.valueOf(rssi));
            Log.d("\t\tFrequency=", String.valueOf(frequency));
            Log.d("\t\tLinkspeed=", String.valueOf(Linkspeed));
            Log.d("\t\tRxLinkSpeed=", String.valueOf(RxLinkSpeed));
            Log.d("\t\tTxLinkSpeed=", String.valueOf(TxLinkSpeed));
            Log.d("\t\tOperating band=", String.valueOf(operating_band));
            Log.d("\t\tX=", String.valueOf(x));
            Log.d("\t\tY=", String.valueOf(y));
            //st12.setText("result"+result);
//            Toast.makeText(MainActivity2.this,  "RSSI:"+ rssi + "  SSID:" + ssid, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Toast.makeText(MainActivity2.this, "Device is not connected to any network", Toast.LENGTH_LONG).show();

        }
    }

    public void savetofile() {
        Log.v("Bharti", "entering save file");
        File directory = null;

        directory = new File(Environment.getExternalStorageDirectory() + java.io.File.separator + "WSS");
        directory.mkdirs();
//        if (!directory.exists())
//            Toast.makeText(this, (directory.mkdirs() ? "Directory has been created" : "Directory not created"), Toast.LENGTH_SHORT).show();

        System.out.println(directory);
        file = new File(Environment.getExternalStorageDirectory() + java.io.File.separator + "WSS" + java.io.File.separator + "WSS.txt");
        System.out.println(file);


        Date currentTime = Calendar.getInstance().getTime();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        System.out.println("1");
        uri2 = Uri.parse(String.valueOf(file));
        System.out.println(uri2);

        try {
            System.out.println("2");
            OutputStreamWriter file_writer = new OutputStreamWriter(new FileOutputStream(file, false));
            System.out.println("3");
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            System.out.println("4");
            if (SLNO == 0) {
                buffered_writer.write("\nNumber of times" + "\tSSID" + "\tRSSI" + "\tX" + "\tY" + "\tFrequency" + "\tLinkSpeed" + "\tRxLinkSpeed" + "\tTxLinkSpeed" + "\toperating_band");
                System.out.println("5");
            } else {
                buffered_writer.write("\n" + SLNO + "\t" + ssid + "\t" + rssi + "\t" + x + "\t" + y + "\t" + frequency + "\t" + Linkspeed + "\t" + RxLinkSpeed + "\t" + TxLinkSpeed + "\t" + operating_band);
                System.out.println("6");
            }
            System.out.println("7");
            buffered_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fileUriToBase64(Uri uri, ContentResolver resolver) {
        String encodedBase64 = "";
        try {
            byte[] bytes = readBytes(uri, resolver);
            encodedBase64 = Base64.encodeToString(bytes, 0);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return encodedBase64;
    }

    public static byte[] readBytes(Uri uri, ContentResolver resolver)
            throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = resolver.openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the
        // byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }


    public void checkEnabled() {
        if (i > 10) {
            buttonCal.setEnabled(true);
        }
    }
}






























//
//    ;

//    @SuppressLint("ClickableViewAccessibility")
//    private void addTouchListener() {
//
//        imageView2.setOnTouchListener(new View.OnTouchListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//
//                ob.onLongPress(event);
//                return false;
//            }
//        });
//    }
//}


//        imageView2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
////                final int waitTime = 5000;
//                x = event.getX();
//                y = event.getY();
////                drawCross(x,y);
////                m_canvas.drawCircle(x,y,10,paint);
////                imageView2.setBitmap(bmp);
//                String msg = String.format("Coordinates : (%.2f,%.2f)", x, y);
//                Log.d("coordinates", msg);
//                Toast.makeText(MainActivity2.this, "X: " + x + " Y: " + y, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            });
//        }





