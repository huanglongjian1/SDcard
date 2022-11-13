package com.example.sdcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private TextView hasSDTextView;
    private TextView SDPathTextView;
    private TextView FILESpathTextView;
    private TextView createFileTextView;
    private TextView readFileTextView;
    private TextView deleteFileTextView;
    private FileHelper helper;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcardlayout);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquanxian();
            }
        });
     //
    }

    public void init() {


        hasSDTextView = (TextView) findViewById(R.id.hasSDTextView);
        SDPathTextView = (TextView) findViewById(R.id.SDPathTextView);
        FILESpathTextView = (TextView) findViewById(R.id.FILESpathTextView);
        createFileTextView = (TextView) findViewById(R.id.createFileTextView);
        readFileTextView = (TextView) findViewById(R.id.readFileTextView);
        deleteFileTextView = (TextView) findViewById(R.id.deleteFileTextView);
        helper = new FileHelper(getApplicationContext());
        hasSDTextView.setText("SD卡是否存在:" + helper.hasSD());
        SDPathTextView.setText("SD卡路径:" + helper.getSDPATH());
        FILESpathTextView.setText("包路径:" + helper.getFILESPATH());
        try {
            createFileTextView.setText("创建文件："
                    + helper.createSDFile("test.txt").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteFileTextView.setText("删除文件是否成功:"
                + helper.deleteSDFile("xx.txt"));
        helper.writeSDFile("1213212", "test.txt");
        readFileTextView.setText("读取文件:" + helper.readSDFile("test.txt"));

    }
    String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String PERMISSION_STORAGE_MSG = "请授予权限，否则影响部分使用功能";
    public static final int RC_CAMERA_AND_LOCATION = 10001;
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    public void getquanxian() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            init();
            Toast.makeText(this, "权限Ok", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "no-------权限", Toast.LENGTH_LONG).show();
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, PERMISSION_STORAGE_MSG,
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}