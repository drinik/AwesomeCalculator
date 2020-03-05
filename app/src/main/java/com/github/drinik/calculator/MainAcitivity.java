package com.github.drinik.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

public class MainAcitivity extends AppCompatActivity implements OnCalculatorItemClickListener {

    private TextView mTextView;
    private TextView mTextViewCalculation;
    private CalculatorItem mLastActionableCalculatorItemClicked;
    private double mValue;
    private boolean mLastActionableCalculatorItemJustClicked;
    private boolean mWasJustCalculated;
    private boolean mNumberDecimalClicked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textview);
        mTextViewCalculation = (TextView) findViewById(R.id.textviewcalculation);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new CalculatorAdapter(this));
    }


    @Override
    public void onCalculatorItemClick(CalculatorItem item) {
        if (item == CalculatorItem.CLEAR) {
            mLastActionableCalculatorItemClicked = null;
            mTextViewCalculation.setText("0");
            mTextView.setText("0");
            mValue = 0;
        } else if (item == CalculatorItem.PLUS_MINUS) {

            String[] values = mTextViewCalculation.getText().toString().split(" ");

            final String preCalcText = mTextView.getText().toString();
            mTextView.setText(adjustCalculation(String.valueOf(-1 * Double.parseDouble(mTextView.getText().toString())), true));
            int index = mTextViewCalculation.getText().toString().lastIndexOf(preCalcText);
            String newText = mTextViewCalculation.getText().toString().substring(0, index);
            newText += mTextView.getText().toString();
            newText += mTextViewCalculation.getText().toString().substring(index + preCalcText.length());
            mTextViewCalculation.setText(newText);


        } else if (item == CalculatorItem.EQUALS) {
            if (mLastActionableCalculatorItemClicked != null && mNumberDecimalClicked) {
                switch (mLastActionableCalculatorItemClicked) {
                    case MULTIPLICATION:
                        mTextView.setText(adjustCalculation(String.valueOf(mValue * Double.parseDouble(mTextView.getText().toString())), true));
                        break;
                    case DIVISION:
                        mTextView.setText(adjustCalculation(String.valueOf(mValue / Double.parseDouble(mTextView.getText().toString())), true));
                        break;
                    case SUBTRACTION:
                        mTextView.setText(adjustCalculation(String.valueOf(mValue - Double.parseDouble(mTextView.getText().toString())), true));
                        break;
                    case ADDITION:
                        mTextView.setText(adjustCalculation(String.valueOf(mValue + Double.parseDouble(mTextView.getText().toString())), true));
                        break;
                }

                mTextViewCalculation.setText(mTextView.getText().toString());
                mValue = 0;
                mLastActionableCalculatorItemClicked = null;
                mLastActionableCalculatorItemJustClicked = false;
                mWasJustCalculated = true;
            }
        } else if (item == CalculatorItem.MULTIPLICATION || item == CalculatorItem.DIVISION || item == CalculatorItem.SUBTRACTION || item == CalculatorItem.ADDITION) {

            mWasJustCalculated = false;
            boolean didJustEqual = false;
            if (mTextViewCalculation.getText().toString().contains(" ")) {
                onCalculatorItemClick(CalculatorItem.EQUALS);
                mWasJustCalculated = false;
                didJustEqual = true;
            }
            mNumberDecimalClicked = false;

            if (mLastActionableCalculatorItemClicked != null) {
                int index = mTextViewCalculation.getText().toString().lastIndexOf(mLastActionableCalculatorItemClicked.getValue());
                mTextViewCalculation.setText(mTextViewCalculation.getText().toString().substring(0, index - 1));
            }

            mLastActionableCalculatorItemClicked = item;
            mValue = Double.parseDouble(mTextView.getText().toString());

            if ((!mTextViewCalculation.equals("0") && !mLastActionableCalculatorItemJustClicked) || didJustEqual) {
                mTextViewCalculation.setText(mTextViewCalculation.getText().toString() + " " + item.getValue());
            }
            mLastActionableCalculatorItemJustClicked = true;

        } else {
            if (item == CalculatorItem.DECIMAL && mTextView.getText().toString().contains(CalculatorItem.DECIMAL.getValue()) && !mLastActionableCalculatorItemJustClicked && !mWasJustCalculated) {
                return;
            }

            mNumberDecimalClicked = true;

            if (mWasJustCalculated) {
                onCalculatorItemClick(CalculatorItem.CLEAR);
                mWasJustCalculated = false;
            }

            boolean addSpace = mLastActionableCalculatorItemJustClicked;
            String preText = mTextView.getText().toString();

            if (mLastActionableCalculatorItemJustClicked && item == CalculatorItem.DECIMAL) {
                preText = "0";
            }

            if ((mTextView.getText().toString().equals("0") && item != CalculatorItem.DECIMAL) || mLastActionableCalculatorItemJustClicked) {
                preText = "";
                mLastActionableCalculatorItemJustClicked = false;
            }

            mTextView.setText(adjustCalculation(preText + item.getValue()));

            String preCalcText = mTextViewCalculation.getText().toString();
            if (preCalcText.equals("0")) {
                preCalcText = "";
            }

            String calcText;
            if (addSpace) {
                calcText = preCalcText + " " + adjustCalculation(item.getValue());
            } else {
                calcText = adjustCalculation(preCalcText + item.getValue());
            }

            mTextViewCalculation.setText(calcText);
        }
    }


    private String adjustCalculation(String value) {
        return adjustCalculation(value, false);
    }


    private String adjustCalculation(String value, boolean didEqual) {
        if (value.startsWith(".")) {
            value = "0" + value;
        } else if (value.endsWith(".0") && didEqual) {
            value = value.substring(0, value.length() - 2);
        }

        return value;
    }
}
