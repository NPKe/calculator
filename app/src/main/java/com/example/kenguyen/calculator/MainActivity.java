package com.example.kenguyen.calculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static  int selectedTheme = 0;
    public static int selectedFloatPointFormat = 0;

    private String strNumber1, strNumber2, dot, separator;
    private int currentNumber;
    private int currentTheme = R.style.MyLightTheme;

    private Button[] btnNumber;
    private Button[] btnOperator;
    private Button btnEqual, btnDot;

    private TextView txtExpression, txtResult;
    private ImageView imgBackspace;

    Calculator calculator = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent myIntent = getIntent();
        selectedTheme = myIntent.getIntExtra("theme", 0);
        selectedFloatPointFormat = myIntent.getIntExtra("float", 0);

        if (selectedTheme == 0)
            setTheme(R.style.MyDarkTheme);
        else
            setTheme(R.style.MyLightTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialValue();

        findView();

        setOnClickEvent();

        changeFloatPoint(selectedFloatPointFormat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivityForResult(settingIntent, 111);
                return true;

            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 111:
                if (resultCode == Activity.RESULT_OK) {
                    int theme = data.getIntExtra("theme", 0);
                    int floatpoint = data.getIntExtra("floatpoint", 0);
                    changeFloatPoint(floatpoint);
                    setAppTheme(theme);
                }
                break;
            default:
                break;
        }
    }

    private void setAppTheme(int themeID) {
        if (themeID == 0) {
            selectedTheme = 0;
            currentTheme = R.style.MyDarkTheme;
        }
        else if (themeID == 1) {
            selectedTheme = 1;
            currentTheme = R.style.MyLightTheme;
        }

        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.putExtra("theme", selectedTheme);
        newIntent.putExtra("float", selectedFloatPointFormat);
        startActivity(newIntent);
        finish();
    }

    private void changeFloatPoint(int floatPoint) {
        if (floatPoint == 0) {
            selectedFloatPointFormat = 0;
            dot = ".";
            separator = ",";
            btnDot.setText(".");
        }
        else if (floatPoint == 1) {
            selectedFloatPointFormat = 1;
            dot = ",";
            separator = ".";
            btnDot.setText(",");
        }
    }

    private void setOnClickEvent() {
        imgBackspace.setOnClickListener(this);

        btnEqual.setOnClickListener(this);
        btnDot.setOnClickListener(this);

        for (int i = 0; i < btnNumber.length; i++)
        {
            btnNumber[i].setOnClickListener(this);
        }

        for (int i = 0; i < btnOperator.length; i++)
        {
            btnOperator[i].setOnClickListener(this);
        }

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.ExcuteOperation();
                txtExpression.setText(ToString(calculator.number1) + calculator.operator + ToString(calculator.number2));
                txtResult.setText(ToString(calculator.result));

                calculator.number1 = calculator.result;
                calculator.number2 = 0;
                strNumber2 = "0";
                currentNumber = 1;
            }
        });
    }

    private void findView() {
        imgBackspace = (ImageView)findViewById(R.id.backspace);

        txtExpression = (TextView)findViewById(R.id.txtExpression);
        txtResult = (TextView)findViewById(R.id.txtResult);

        btnNumber = new Button[11];
        btnOperator = new Button[5];


        btnNumber[0] = (Button)findViewById(R.id.btnNum0);
        btnNumber[1] = (Button)findViewById(R.id.btnNum1);
        btnNumber[2] = (Button)findViewById(R.id.btnNum2);
        btnNumber[3] = (Button)findViewById(R.id.btnNum3);
        btnNumber[4] = (Button)findViewById(R.id.btnNum4);
        btnNumber[5] = (Button)findViewById(R.id.btnNum5);
        btnNumber[6] = (Button)findViewById(R.id.btnNum6);
        btnNumber[7] = (Button)findViewById(R.id.btnNum7);
        btnNumber[8] = (Button)findViewById(R.id.btnNum8);
        btnNumber[9] = (Button)findViewById(R.id.btnNum9);
        btnNumber[10] = (Button)findViewById(R.id.btnNum00);

        btnOperator[0] = (Button)findViewById(R.id.btnAdd);
        btnOperator[1] = (Button)findViewById(R.id.btnSubtract);
        btnOperator[2] = (Button)findViewById(R.id.btnMulti);
        btnOperator[3] = (Button)findViewById(R.id.btnDivide);
        btnOperator[4] = (Button)findViewById(R.id.btnMod);

        btnEqual = (Button)findViewById(R.id.btnEqual);
        btnDot = (Button)findViewById(R.id.btnDot);
    }

    private void initialValue() {
        calculator.number1 = 0;
        calculator.number2 = 0;
        currentNumber = 1;
        strNumber1 = "";
        strNumber2 = "";
        dot = ".";
        separator = ",";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnNum0:
            case R.id.btnNum00:
            case R.id.btnNum1:
            case R.id.btnNum2:
            case R.id.btnNum3:
            case R.id.btnNum4:
            case R.id.btnNum5:
            case R.id.btnNum6:
            case R.id.btnNum7:
            case R.id.btnNum8:
            case R.id.btnNum9:
            case R.id.btnDot:
                ProcessNumberButtonClick((Button) v);
                break;

            case R.id.btnAdd:
            case R.id.btnSubtract:
            case R.id.btnMulti:
            case R.id.btnDivide:
            case R.id.btnMod:
                ProcessOperatorButtonPress((Button) v);
                break;

            case R.id.backspace:
                ProcessBackspaceButton();
                break;
        }
    }

    private void ProcessBackspaceButton() {
        if (currentNumber == 1) {
            if (strNumber1.length() > 1)
                strNumber1 = strNumber1.substring(0, strNumber1.length() - 1);
            else
                strNumber1 = "0";

            calculator.number1 = Double.parseDouble(strNumber1);

            txtResult.setText(ToString(calculator.number1));
        }
        else {
            if (strNumber2.length() > 1)
                strNumber2 = strNumber2.substring(0, strNumber2.length() - 1);
            else
                strNumber2 = "0";

            calculator.number2 = Double.parseDouble(strNumber2);

            txtResult.setText(ToString(calculator.number2));
        }
    }

    private void ProcessOperatorButtonPress(Button v) {
        char operator = ' ';
        int id = v.getId();

        if (id == R.id.btnAdd)
            operator = '+';
        else if (id == R.id.btnSubtract)
            operator = '-';
        else if (id == R.id.btnMulti)
            operator = 'x';
        else if (id == R.id.btnDivide)
            operator = '÷';
        else if (id == R.id.btnMod)
            operator = '%';

        calculator.operator = operator;

        if (currentNumber == 1)
            currentNumber = 2;
        else
            currentNumber = 1;

        txtResult.setText(v.getText().toString());
        txtExpression.setText(ToString(calculator.number1));
    }

    private void ProcessNumberButtonClick(Button v) {
        if (currentNumber == 1)
        {
            String mDot = "-";
            if (v.getText().toString().equals(".") || v.getText().toString().equals(","))
            {
                mDot = ".";
                strNumber1 = strNumber1 + mDot;
            } else
            {
                strNumber1 = strNumber1 + v.getText().toString();
            }

            calculator.number1 = Double.parseDouble(strNumber1);
            txtResult.setText(ToString(calculator.number1));
            if (mDot.equals("."))
                txtResult.setText(ToString(calculator.number1) + dot);
            else
                txtResult.setText(ToString(calculator.number1));
        }
        else if (currentNumber == 2)
        {
            String mDot = "-";
            if (v.getText().toString().equals(".") || v.getText().toString().equals(","))
            {
                mDot = ".";
                strNumber2 = strNumber2 + mDot;
            } else
            {
                strNumber2 = strNumber2 + v.getText().toString();
            }

            calculator.number2 = Double.parseDouble(strNumber2);
            txtResult.setText(ToString(calculator.number2));
            if (mDot.equals("."))
                txtResult.setText(ToString(calculator.number2) + dot);
            else
                txtResult.setText(ToString(calculator.number2));
        }
    }

    public void btnClear_OnClick(View v) {
        strNumber1 = "0";
        strNumber2 = "0";
        txtExpression.setText("");
        txtResult.setText("0");

        calculator.number1 = 0;
        calculator.number2 = 0;
        calculator.operator = '+';
        currentNumber = 1;
    }

    public String ToString(double num) {
        String temp = Double.toString(num);

        int dotIdx = temp.lastIndexOf('.');
        int lenght = temp.length();

        String part1 = temp.substring(0, dotIdx);
        String part2 = temp.substring(dotIdx + 1, lenght);

        int startIdx = part1.length();
        int endIdx;

        int numPart1 = (int)Math.ceil(part1.length() / 3.0);
        int numPart2 = (int)Math.ceil(part2.length() / 3.0);

        String[] newPart1 = new String[numPart1];
        String[] newPart2 = new String[numPart2];

        for (int i = numPart1 - 1; i >= 0; i--)
        {
            endIdx = part1.length() - 3*(numPart1 - i - 1);
            if (endIdx < 0)
                break;

            startIdx = endIdx - 3;
            if (startIdx < 0)
                startIdx = 0;

            newPart1[i] = part1.substring(startIdx, endIdx);
        }

        String strNumber = "";
        for (int i = 0; i < newPart1.length; i++)
            strNumber = strNumber + separator + newPart1[i];

        strNumber = strNumber.substring(1, strNumber.length());

        strNumber = strNumber + dot;

        strNumber += part2;

        if (strNumber.indexOf(dot + "0") == (strNumber.length() - 2))
            strNumber = strNumber.substring(0, strNumber.length() - 2);

        return strNumber;
    }
}
