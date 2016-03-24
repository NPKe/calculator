package com.example.kenguyen.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private  LinearLayout theme;
    private LinearLayout floatpoint;

    private int selectedTheme = 0;
    private int selectedFloatPoint = 0;
    private Intent intent  = new Intent();

    private TextView txtTheme, txtFloatPoint;
    CharSequence athemes[] = new CharSequence[]{"Dark", "Light"};
    CharSequence afloatpoint[] = new CharSequence[]{"Dot (.)", "Comma (,)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        theme = (LinearLayout)findViewById(R.id.theme);
        floatpoint = (LinearLayout)findViewById(R.id.floatpoint);
        txtTheme = (TextView)findViewById(R.id.txtTheme);
        txtFloatPoint = (TextView)findViewById(R.id.txtFloatPoint);

        theme.setOnClickListener(this);
        floatpoint.setOnClickListener(this);

        setResult(Activity.RESULT_OK, intent);
        txtTheme.setText(athemes[MainActivity.selectedTheme]);
        txtFloatPoint.setText(afloatpoint[MainActivity.selectedFloatPointFormat]);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.theme:
                ChooseTheme();
                break;
            case R.id.floatpoint:
                ChooseFloatPoint();
                break;
            default:
                break;
        }
    }

    public void ChooseTheme() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose you favorite theme");
        builder.setSingleChoiceItems(athemes, MainActivity.selectedTheme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                intent.putExtra("theme", which);
                txtTheme.setText(athemes[which]);
            }
        });
        builder.show();
    }

    public void ChooseFloatPoint()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose float point format");
        builder.setSingleChoiceItems(afloatpoint, MainActivity.selectedFloatPointFormat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                intent.putExtra("floatpoint", which);
                txtFloatPoint.setText(afloatpoint[which]);
            }
        });
        builder.show();
    }


}
