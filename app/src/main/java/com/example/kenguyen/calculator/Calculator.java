package com.example.kenguyen.calculator;

/**
 * Created by Ke Nguyen on 3/18/2016.
 */
public class Calculator {

    public double number1;
    public double number2;
    public double result;
    public char operator;
    public char decimalCharacter;

    public Calculator()
    {
        number1 = 0;
        number2 = 0;
        result = 0;
        operator = '+';
        decimalCharacter = '.';
    }

    public double ExcuteOperation() {
        switch (operator) {
            case '+':
                result = number1 + number2;
                break;
            case '-':
                result = number1 - number2;
                break;
            case 'x':
                result = number1 * number2;
                break;
            case '÷':
                result = number1 / number2;
                break;
            case '%':
                result = number1 % number2;
                break;
            default:
                result = number1;
        }

        return  result;
    }
}
