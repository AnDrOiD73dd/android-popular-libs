package ru.android73dd.androidpopularlibs.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.android73dd.androidpopularlibs.R;
import ru.android73dd.androidpopularlibs.log.Logger;
import ru.android73dd.androidpopularlibs.model.FileManagerImpl;
import ru.android73dd.androidpopularlibs.presentation.presenter.ConvertImagePresenter;
import ru.android73dd.androidpopularlibs.presentation.view.ConvertImageView;

public class ConvertImageActivity extends MvpAppCompatActivity implements ConvertImageView {

    public static final String TAG = "ConvertImageActivity";

    private static final int REQUEST_CODE_SELECT_IMAGE = 1000;

    @InjectPresenter
    ConvertImagePresenter convertImagePresenter;

    @BindView(R.id.status)
    TextView statusView;
    @BindView(R.id.cancel_button)
    Button cancelButton;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, ConvertImageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public ConvertImagePresenter providePresenter() {
        return new ConvertImagePresenter(AndroidSchedulers.mainThread(), new FileManagerImpl());
    }

    @OnClick(R.id.floating_action_button)
    public void onFabClick(FloatingActionButton button) {
        convertImagePresenter.onChooseImageClick();
    }

    @OnClick(R.id.cancel_button)
    public void onCancelClick(Button button) {
        convertImagePresenter.onCancelClick();
    }

    @Override
    public void showCancelButton() {
        cancelButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCancelButton() {
        cancelButton.setVisibility(View.GONE);
    }

    @Override
    public void showChooseImages() {
        String[] mimeTypes = {"image/jpeg", "image/png"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void updateStatus(String text) {
        statusView.setText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("requestCode=%d, resultCode=%d", requestCode, resultCode);
        switch (requestCode) {
            case REQUEST_CODE_SELECT_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = data.getData();
                    String imagePath = getRealPathFromURI(imageUri);
                    if (imageUri != null) {
                        convertImagePresenter.onImageSelected(imagePath);
                    }
                }
                break;
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
