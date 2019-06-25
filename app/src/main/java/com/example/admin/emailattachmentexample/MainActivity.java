package com.example.admin.emailattachmentexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        export = (Button) findViewById(R.id.export);

        //export button listener
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();
            }
        });
    }


    public void openFolder() {
        String csv1 = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/duration.csv");
        String csv2 = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/app_timestamp.csv");
        String csv3 = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/rating.csv");
        String csv4 = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/first_time.csv");

        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");

        //email to information - can be changed as per need
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"htvusagerecord@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TV Usage App Files");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "1) duration.csv \n 2) app_timestamp.csv \n 3) ratings.csv \n 4) first_time.csv");

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File file1 = new File(csv1);
        Uri uri1 = Uri.fromFile(file1);

        File file2 = new File(csv2);
        Uri uri2 = Uri.fromFile(file2);

        File file3 = new File(csv3);
        Uri uri3 = Uri.fromFile(file3);

        File file4 = new File(csv4);
        Uri uri4 = Uri.fromFile(file4);

        ArrayList<Uri> uris = new ArrayList<Uri>();
        uris.add(uri1);
        uris.add(uri2);
        uris.add(uri3);
        uris.add(uri4);

        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        startActivityForResult(Intent.createChooser(emailIntent, "Sending multiple attachment"), 12345);
    }
}