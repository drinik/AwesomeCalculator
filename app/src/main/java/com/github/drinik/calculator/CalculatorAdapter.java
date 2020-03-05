package com.github.drinik.calculator;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class CalculatorAdapter extends BaseAdapter{

    private OnCalculatorItemClickListener mListener;

    public CalculatorAdapter(OnCalculatorItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return CalculatorItem.values().length;
    }

    @Override
    public Object getItem(int position) {
        return CalculatorItem.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_calculator_item, parent, false);
        Button button = (Button) view.findViewById(R.id.button);
        final CalculatorItem item = (CalculatorItem) getItem(position);
        button.setText(item.getValue());
        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCalculatorItemClick(item);
                }
            }
        });

        switch (item) {
            case CLEAR:
            case PLUS_MINUS:
                button.setBackgroundResource(R.drawable.bg_top_actions);
                break;
            case EQUALS:
                button.setBackgroundResource(R.drawable.bg_equals);
                break;
            case MULTIPLICATION:
            case DIVISION:
            case SUBTRACTION:
            case ADDITION:
                button.setBackgroundResource(R.drawable.bg_side_actions);
                break;
            case EMPTY_1:
            case EMPTY_2:
                break;
            default:
                button.setBackgroundResource(R.drawable.bg_numbers);
                break;
        }

        if (item == CalculatorItem.EMPTY_1 || item == CalculatorItem.EMPTY_2) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }

        return view;
    }
}
