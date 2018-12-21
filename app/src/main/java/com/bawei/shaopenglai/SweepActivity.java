package com.bawei.shaopenglai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class SweepActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView zXingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep);
        zXingView=findViewById(R.id.zxing);
        zXingView.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingView.startCamera();
        zXingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zXingView.stopCamera();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Intent intent=new Intent(SweepActivity.this,LoginActivity.class);

        startActivity(intent);

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
