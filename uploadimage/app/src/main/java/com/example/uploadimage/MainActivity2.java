package com.example.uploadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {
    ImageView imageView2;
    Uri myUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView2 =(ImageView)findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("EXTRA_IMAGEVIEW_URL"));
        imageView2.setImageURI(myUri);
    }
}