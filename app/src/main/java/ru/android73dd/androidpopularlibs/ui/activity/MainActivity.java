package ru.android73dd.androidpopularlibs.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.android73dd.androidpopularlibs.R;
import ru.android73dd.androidpopularlibs.presentation.presenter.MainPresenter;
import ru.android73dd.androidpopularlibs.presentation.view.MainView;


public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @BindView(R.id.btn_one)
    public Button buttonOne;
    @BindView(R.id.btn_two)
    public Button buttonTwo;
    @BindView(R.id.btn_three)
    public Button buttonThree;

    @InjectPresenter
    public MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter()
    {
        presenter = new MainPresenter();
        //TO SOMETHING WITH PRESENTER
        return presenter;
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onClick(Button button)
    {
        switch (button.getId())
        {
            case R.id.btn_one:
                presenter.onButtonOneClick();
                break;
            case R.id.btn_two:
                presenter.onButtonTwoClick();
                break;
            case R.id.btn_three:
                presenter.onButtonThreeClick();
                break;
        }
    }

    @Override
    public void updateButtonOneText(String text) {
        buttonOne.setText(text);
    }

    @Override
    public void updateButtonTwoText(String text) {
        buttonTwo.setText(text);
    }

    @Override
    public void updateButtonThreeText(String text) {
        buttonThree.setText(text);
    }

    @Override
    public void showInfo(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
