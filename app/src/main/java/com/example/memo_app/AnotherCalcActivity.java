package com.example.memo_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.memo_app.R.layout.activity_another_calc;




public class AnotherCalcActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    //  上の「計算ボタン」を押したときのリクエストコード
    private static final int REQUEST_CODE_ANOTHER_CALC_1 = 1;
    //  上の「計算ボタン」を押したときのリクエストコード
    private static final int REQUEST_CODE_ANOTHER_CALC_2 = 2;

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
        setContentView(R.layout.activity_another_calc2);
        //setContentView(activity_another_calc);

        // リスナーにボタンを登録することでボタンをクリックしたときにonClick()関数が呼び出される

        // 上の「計算ボタン」
        findViewById(R.id.calcButton1).setOnClickListener(this);
        // 下の「計算ボタン」
        findViewById(R.id.calcButton2).setOnClickListener(this);
        // 「続けて計算するボタン」
        findViewById(R.id.nextButton).setOnClickListener(this);

        // 上のEditText
        numberInput1 = (EditText) findViewById(R.id.numberInput1);
        numberInput1.addTextChangedListener(this);

        // 下のEditText
        numberInput2 = (EditText) findViewById(R.id.numberInput2);
        numberInput2.addTextChangedListener(this);

        // 演算選択用 Spiner
        operatorSelector = (Spinner) findViewById(R.id.operatorSelector);

        // 計算結果表示用のTextView
        calcResult = (TextView) findViewById(R.id.calcResult);

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        refreshResult();
    }

    // 計算結果の表示を更新する
    private void refreshResult() {
        if (checkEditTextInput()) {
            // 計算を行う
            int result = calc();

            // 計算結果用のTextViewを書き換える
            String resultText = getString(R.string.calc_result_text, result);
            calcResult.setText(resultText);
        } else {
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

    @Override
    public void onClick(View v) {
        // 戻るボタン
        // どちらかのEditTextに値が入っていない場合
        if (!checkEditTextInput()) {
            // キャンセルとみなす
            setResult(RESULT_CANCELED);
        } else {
            int result = calc();

            // インテントを生成し計算結果を認める
            Intent data = new Intent();
            data.putExtra("result", result);
            setResult(RESULT_OK, data);
        }

        // アクティビティを修了
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != RESULT_OK) return;

        // 結果データセットを取り出す
        Bundle resultBundle = data.getExtras();

        // 結果データセットに、所定のキーが含まれていない場合、何もしない
        if(!resultBundle.containsKey("result"))return;

        // 結果データキーから"result"キーに対応するint値を取り出す
        int result = resultBundle.getInt("result");

        if(requestCode == REQUEST_CODE_ANOTHER_CALC_1){
            numberInput1.setText(String.valueOf(result));
        }else if(requestCode == REQUEST_CODE_ANOTHER_CALC_2){
            numberInput1.setText(String.valueOf(result));
        }

        // 計算をし直して、結果を表示する
        refreshResult();
    }

}



