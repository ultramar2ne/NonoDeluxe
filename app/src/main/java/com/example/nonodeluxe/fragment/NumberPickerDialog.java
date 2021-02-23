package com.example.nonodeluxe.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NumberPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    String title;
    String subtitle;
    int minValue;
    int maxValue;
    int step;
    int defValue;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        title = getArguments().getString("title");
        subtitle = getArguments().getString("subtitle");
        minValue = getArguments().getInt("minvalue");
        maxValue = getArguments().getInt("maxvalue");
        step = getArguments().getInt("step");
        defValue = getArguments().getInt("defvalue");
        
        String[] myValues = getArrayWithSteps(minValue, maxValue, step);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue((maxValue - minValue) / step);
        numberPicker.setDisplayedValues(myValues);

//        현재값 설정 (시작시 시작지점)
        numberPicker.setValue((defValue - minValue)/step);
//        키보드 입력 방지
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);
        builder.setMessage(subtitle);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                valueChangeListener.onValueChange(numberPicker,numberPicker.getValue(),numberPicker.getValue());
            }
        });

        //취소 버튼을 눌렀을 때 동작 설정
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(numberPicker);
        //number picker 실행
        return builder.create();
    }

    //Listener의 getter
    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    //Listener의 setter
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


    private String[] getArrayWithSteps(int min, int max, int step) {
        int number_of_array = (max - min) / step + 1;

        String[] result = new String[number_of_array];

        for (int i = 0; i < number_of_array; i++) {
            result[i] = String.valueOf(min + step * i);
        }
        return result;
    }
}
