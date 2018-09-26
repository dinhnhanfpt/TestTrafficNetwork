package com.example.tvo.testtrafficnetwork;

import android.app.usage.NetworkStatsManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail extends AppCompatActivity {
    private Intent intent;
    private Package mpackage;
    private ImageView img;
    private TextView name, data;
    private NetworkStatsManager networkStatsManager;
    private int uid;
    private long rx,tx;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        networkStatsManager = (NetworkStatsManager) getApplicationContext().getSystemService(NETWORK_STATS_SERVICE);
        intent = getIntent();
        mpackage = (Package) intent.getSerializableExtra("data");
        try {
            PackageManager packageManager = getPackageManager();
            packageManager.getPackageInfo(mpackage.getPackageName(),PackageManager.GET_META_DATA);
            PackageInfo packageInfo = packageManager.getPackageInfo(mpackage.getPackageName(),PackageManager.GET_META_DATA);
            uid = packageInfo.applicationInfo.uid;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        img = findViewById(R.id.img_logo);
         name = findViewById(R.id.txt_name);
         data = findViewById(R.id.txt_data);

        setData();

    }
    private void setData(){
        try {
            img.setImageDrawable(getPackageManager().getApplicationIcon(mpackage.getPackageName()));
            name.setText(mpackage.getName());
            NetworkStatsHelper networkStatsHelper = new NetworkStatsHelper(networkStatsManager,uid);
            rx = networkStatsHelper.getPackageRxBytesWifi();
            tx = networkStatsHelper.getPackageTxBytesWifi();
            data.setText(String.valueOf(rx +tx));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
