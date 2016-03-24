package com.example.kenguyen.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.jar.Manifest;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout companyInfo, companyLocation, callCompany, messageCompany, emailCompany, shareApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findView();

        setOnClickEvent();
    }

    private void setOnClickEvent() {
        companyInfo.setOnClickListener(this);
        companyLocation.setOnClickListener(this);
        callCompany.setOnClickListener(this);
        messageCompany.setOnClickListener(this);
        emailCompany.setOnClickListener(this);
        shareApp.setOnClickListener(this);
    }

    private void findView() {
        companyInfo = (LinearLayout)findViewById(R.id.companyInfo);
        companyLocation = (LinearLayout)findViewById(R.id.companyLocation);
        callCompany = (LinearLayout)findViewById(R.id.callCompany);
        messageCompany = (LinearLayout)findViewById(R.id.sendMessage);
        emailCompany = (LinearLayout)findViewById(R.id.sendEmail);
        shareApp = (LinearLayout)findViewById(R.id.shareApp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.companyInfo:
                showCompanyInfo();
                break;

            case R.id.companyLocation:
                showLocation();
                break;

            case R.id.callCompany:
                callSaleDepartment();
                break;

            case R.id.sendMessage:
                sendMessageToSaleDepartment();
                break;

            case R.id.sendEmail:
                sendEmail("message/rfc822", Intent.EXTRA_EMAIL, "sale.department@fireants.io");
                break;

            case R.id.shareApp:
                shareApp("text/plain", Intent.EXTRA_TEXT, "Hey, This app is awesome. Try it! Download on play store: https://play.google.com/store/apps/details?id=com.tricolorcat.calculator");
                break;
            default:
                break;
        }
    }

    private void shareApp(String type, String extraText, String value) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(type);
        share.putExtra(extraText, value);
        startActivity(share);
    }

    private void sendEmail(String type, String extraEmail, String value) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType(type);
        email.putExtra(extraEmail, value);
        startActivity(email);
    }

    private void sendMessageToSaleDepartment() {
        Intent message = new Intent(Intent.ACTION_VIEW);
        message.putExtra("address", "84 83 320 730");
        message.setType("vnd.android-dir/mms-sms");
        startActivity(message);
    }

    private void callSaleDepartment() {
        Intent call =  new Intent(Intent.ACTION_CALL, Uri.parse("tel:84 83 320 730"));
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            startActivity(call);
    }

    private void showLocation() {
        String thePlace = "227 Nguyễn Văn Cừ, Hồ Chí Minh, Việt Nam";
        Intent viewLocation = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=(" + thePlace + ")"));
        startActivity(viewLocation);
    }

    private void showCompanyInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Company Information")
                .setMessage("Super Calculator - Fire Ants Inc")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }
}
