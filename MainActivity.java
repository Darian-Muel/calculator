package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
    private TextView displayRD;

    // Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=";
    //Variable to tell if user is in degrees or rad, 1 is degrees, 2 is rad
    //starts at degrees by default
    private int rd = 1;

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";
    private static final String STATE_DISPLAY_RD = "Degree";
    private static final String STATE_RD = "0";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);
        displayRD = (TextView) findViewById(R.id.rd);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        Button buttonNeg = (Button) findViewById(R.id.btnNeg);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);

        Button buttonTan = (Button) findViewById(R.id.btnTan);
        Button buttonSin = (Button) findViewById(R.id.btnSin);
        Button buttonCos = (Button) findViewById(R.id.btnCos);
        Button buttonRad = (Button) findViewById(R.id.btnRad);
        Button buttonDegrees = (Button) findViewById(R.id.btnDegrees);

        Button buttonSqrt = (Button) findViewById(R.id.btnSqrt);
        Button buttonLog = (Button) findViewById(R.id.btnLog);
        Button buttonPower = (Button) findViewById(R.id.btnPower);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonPower.setOnClickListener(opListener);

        View.OnClickListener negListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newNumber.getText().toString().indexOf("-") == 0) {
                    newNumber.setText(newNumber.getText().toString().substring(1));

                }
                else {
                    String neg = "-";
                    newNumber.setText(neg + newNumber.getText().toString());

                }

            }
        };
        buttonNeg.setOnClickListener(negListener);

        View.OnClickListener rdListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String measurement = b.getText().toString();
                if (measurement.equals("D")) {
                    rd = 1;
                    displayRD.setText("Degrees");
                }
                else {
                    rd = 2;
                    displayRD.setText("Radians");
                }

            }
        };
        buttonDegrees.setOnClickListener(rdListener);
        buttonRad.setOnClickListener(rdListener);

        View.OnClickListener trigListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String function = b.getText().toString();
                if(operand1 != null) {
                    if(rd == 1) {
                        operand1 = Math.toRadians(operand1);

                    }
                    switch (function) {
                        case "Tan":
                            operand1 = Math.tan(operand1);
                            break;
                        case "Cos":
                            operand1 = Math.cos(operand1);
                            break;
                        default:
                            operand1 = Math.sin(operand1);
                            break;
                    }
                    result.setText(operand1.toString());

                }
            }
        };
        buttonTan.setOnClickListener(trigListener);
        buttonSin.setOnClickListener(trigListener);
        buttonCos.setOnClickListener(trigListener);

        View.OnClickListener logListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operand1 != null) {
                    performLog(operand1);
                }

            }
        };
        buttonLog.setOnClickListener(logListener);

        View.OnClickListener sqrtListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operand1 != null) {
                    performSqrt(operand1);

                }

            }
        };
        buttonSqrt.setOnClickListener(sqrtListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        outState.putString(STATE_DISPLAY_RD, displayRD.getText().toString());
        outState.putInt(STATE_RD, rd);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
        displayRD.setText(savedInstanceState.getString(STATE_DISPLAY_RD));
        rd = savedInstanceState.getInt(STATE_RD);



    }

    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        Toast toast = Toast.makeText(this, "error, can not divide by 0.", Toast.LENGTH_LONG);
                        toast.show();
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "^" :
                    operand1 = Math.pow(operand1, value);
                    break;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }
    private void performLog(Double value) {
        if(value <= 0) {
            Toast toast = Toast.makeText(this, "error, value must be greater than 0 for log to work.", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            operand1 = Math.log10(operand1);
            result.setText(operand1.toString());
        }

    }
    private void performSqrt(Double value) {
        if(value < 0) {
            Toast toast = Toast.makeText(this, "error, cannot square root negative numbers", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            operand1 = Math.sqrt(operand1);
            result.setText(operand1.toString());

        }
    }
}