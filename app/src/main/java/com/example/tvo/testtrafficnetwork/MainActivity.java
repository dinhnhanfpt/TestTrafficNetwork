package com.example.tvo.testtrafficnetwork;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.Itemclick{

    private RecyclerView mRecyclerview;
    private Adapter adapter;
    private int REQUEST_READ_PHONE_STATE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = findViewById(R.id.mrecyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

//        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getApplicationContext().getSystemService(Context.NETWORK_STATS_SERVICE);

        if(!checkPermission()){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
        setData();
    }
    private boolean checkPermission(){
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            setData();
        }
        return false;
    }
    private void setData(){
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> ListPackageInfor = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        List<Package> list = new ArrayList<>(ListPackageInfor.size());
        for (PackageInfo mpackage : ListPackageInfor){
            if (packageManager.checkPermission(Manifest.permission.INTERNET,
                    mpackage.packageName) == PackageManager.PERMISSION_DENIED) {
                continue;
            }
            try {
                ApplicationInfo ai = packageManager.getApplicationInfo(mpackage.packageName,PackageManager.GET_META_DATA);
                CharSequence appname = packageManager.getApplicationLabel(ai);
                Package pag = new Package();
                pag.setPackageName(mpackage.packageName);
                pag.setName(appname.toString());
                list.add(pag);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Adapter adapter = new Adapter(list, this);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onclick(Package mypackage) {
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("data", mypackage);
        startActivity(intent);
    }
}
