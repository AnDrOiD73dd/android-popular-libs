package ru.android73dd.androidpopularlibs.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.android73dd.androidpopularlibs.R;
import ru.android73dd.androidpopularlibs.presentation.presenter.ConvertImagePresenter;
import ru.android73dd.androidpopularlibs.presentation.view.ConvertImageView;

public class ConvertImageActivity extends MvpAppCompatActivity implements ConvertImageView {
    public static final String TAG = "ConvertImageActivity";
    @InjectPresenter
    ConvertImagePresenter mConvertImagePresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, ConvertImageActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);
    }
}
