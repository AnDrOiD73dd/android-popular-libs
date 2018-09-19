package ru.android73dd.androidpopularlibs;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainView
{
    @BindView(R.id.btn_one)
    Button buttonOne;
    @BindView(R.id.btn_two)
    Button buttonTwo;
    @BindView(R.id.btn_three)
    Button buttonThree;
    EventBus eventBus;

    @InjectPresenter MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        eventBus = new EventBusImplementation();
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter()
    {
        presenter = new MainPresenter();
        //TO SOMETHING WITH PRESENTER
        return presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.eventBus
                .observable(EventClass.class)
                .subscribe(event -> Log.d("AAAAA", String.valueOf(event.getMessage())));
        this.eventBus.post(new EventClass("AdditionalData"));
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
}
