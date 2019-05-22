package com.example.memo_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.memo_app.R.layout.activity_another_calc;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    // 上のEditText
    private EditText numberInput1;

    // 下のEditText
    private EditText numberInput2;

    // 演算選択用 Spiner
    private Spinner operatorSelector;

    // 計算結果表示用のTextView
    private TextView calcResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(activity_another_calc);

        // 上のEditText
        numberInput1 = (EditText)findViewById(R.id.numberInput1);
        numberInput1.addTextChangedListener(this);

        // 下のEditText
        numberInput2 = (EditText)findViewById(R.id.numberInput2);
        numberInput2.addTextChangedListener(this);

        // 演算選択用 Spiner
        operatorSelector = (Spinner)findViewById(R.id.operatorSelector);

        // 計算結果表示用のTextView
        calcResult = (TextView)findViewById(R.id.calcResult);

    }

    // 2つのEdittextに入力がされているのかをチェックするt
    private boolean checkEditTextInput() {
        // 入力内容を取得する
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();
        // 2つともnullでなければtrueを返す
        return !TextUtils.isEmpty(input1) && !TextUtils.isEmpty(input2);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){

    }

    @Override
    public void afterTextChanged(Editable s) {
        refreshResult();
    }

    // 計算結果の表示を更新する
    private void refreshResult() {
        if(checkEditTextInput()){
            // 計算を行う
            int result = calc();

            // 計算結果用のTextViewを書き換える
            String resultText = getString(R.string.calc_result_text, result);
            calcResult.setText(resultText);
        }else {
            // どちらかが入力されていない場合、計算結果用の表示をデフォルトに戻す
            calcResult.setText(R.string.calc_result_default);
        }
    }

    // 計算を行う
    private int calc() {
        // 入力内容を取得する
        String input1 = numberInput1.getText().toString();
        String input2 = numberInput2.getText().toString();

        // intにキャスト
        int number1 = Integer.parseInt(input1);
        int number2 = Integer.parseInt(input2);

        // Spinenrから選択中のindexを取得する
        int operator = operatorSelector.getSelectedItemPosition();

        // indexに応じて計算結果を返す
        switch (operator) {
            case 0: // 足し算
                return number1 + number2;
            case 1: // 引き算
                return number1 - number2;
            case 2: // 掛け算
                return number1 * number2;
            case 3: // 割り算
                return number1 / number2;
            default:
                // 通常発生しない
                throw new RuntimeException();
        }
    }
}



