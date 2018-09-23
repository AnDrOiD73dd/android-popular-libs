package ru.android73dd.androidpopularlibs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Subscription;
import rx.functions.Action1;

public class RxBindingsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binfings_test);
        final TextView textView = findViewById(R.id.info_text);
        EditText editText = findViewById(R.id.input_text);
        Subscription editTextSub = RxTextView.textChanges(editText).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence value) {
                textView.setText(value);
            }
        });
    }
}
