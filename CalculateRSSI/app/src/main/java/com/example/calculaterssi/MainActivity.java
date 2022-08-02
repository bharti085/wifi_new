package com.example.calculaterssi;
//import static com.github.karthyks.runtimepermissions.PermissionActivity.REQUEST_PERMISSION_CODE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import com.github.karthyks.runtimepermissions.Permission;
//import com.github.karthyks.runtimepermissions.googleapi.LocationSettingsHelper;
//import com.google.android.gms.location.LocationRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List results;
    Button bt;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                disp();
                savetofile();
//                boolean a;
//                boolean b;
//                a=isExternalStorageWritable();
//                b=isExternalStorageReadable();
//                Log.d("Number of times=" , String.valueOf(a));
//                Log.d("Number of times=" , String.valueOf(b));
//                checkStoragePermission();
//                checkLocationSettings();
                SLNO++;


            }


        });
    }

    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // Checks if a volume containing external storage is available to at least read.
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

//    private void checkStoragePermission() {
//        Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_STORAGE)
//                .usingActivity(this).withRationale("Storage permission!").build();
//
//        permission.requestPermission(REQUEST_PERMISSION_CODE);
//    }
//
//    private void checkLocationSettings() {
//
//
//        Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_LOCATION)
//                .usingActivity(this).withRationale("Storage permission!").build();
//        permission.requestPermission(REQUEST_PERMISSION_CODE);
//
//        LocationRequest locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationSettingsHelper settingsApi = new LocationSettingsHelper(MainActivity.this,
//                locationRequest, true, false);
//        settingsApi.checkLocationRequest();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private void disp() {
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
//            ConnectivityManager connectivityManager;
//            if(ssid != null && ssid.contains("unknown ssid")) {
//
//                NetworkInfo wifiinfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                String wifiName = wifiinfo.getExtraInfo();
//                if (wifiName.startsWith("\"")) {
//                    wifiName = wifiName.substring(1, wifiName.length());
//                }
//                if (wifiName.endsWith("\"")) {
//                    wifiName = wifiName.substring(0, wifiName.length() - 1);
//                }
//                ssid = wifiName;
//            }

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
            Log.d("Number of times=" , String.valueOf(SLNO));
            Log.d("\t\tSignal Strength of " , ssid);
            Log.d("\t\tRSSI (dbm) = " , String.valueOf(rssi));
            Log.d("\t\tFrequency=" , String.valueOf(frequency));
            Log.d("\t\tLinkspeed=" , String.valueOf(Linkspeed));
            Log.d("\t\tRxLinkSpeed=" , String.valueOf(RxLinkSpeed));
            Log.d("\t\tTxLinkSpeed=" , String.valueOf(TxLinkSpeed));
            Log.d("\t\tOperating band=" , String.valueOf(operating_band));
            //st12.setText("result"+result);
            Toast.makeText(MainActivity.this,  "RSSI:"+ rssi + "  SSID:" + ssid, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Device is not connected to any network", Toast.LENGTH_LONG).show();

        }
    }

    private void savetofile() {
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
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        try {
            System.out.println("2");
            OutputStreamWriter file_writer = new OutputStreamWriter(new FileOutputStream(file, true));
            System.out.println("3");
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            System.out.println("4");
            if (SLNO==0) {
                buffered_writer.write("\nNumber of times" + "\tSSID" + "\tRSSI" + "\tFrequency" + "\tLinkSpeed" + "\tRxLinkSpeed" + "\tTxLinkSpeed" + "\toperating_band");
                System.out.println("5");
            }

            else {
                buffered_writer.write("\n" + SLNO + "\t" + ssid + "\t" + rssi + "\t" + frequency + "\t" + Linkspeed + "\t" + RxLinkSpeed + "\t" + TxLinkSpeed + "\t" + operating_band);
                System.out.println("6");
            }
            System.out.println("7");
            buffered_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}