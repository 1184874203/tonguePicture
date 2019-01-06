package com.hali.xiaoyangchun.tonguepicture;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hali.xiaoyangchun.tonguepicture.camera.presenter.CameraPresenter;
import com.hali.xiaoyangchun.tonguepicture.camera.widgets.Camera1PreView;

public class MainActivity extends Activity {

    private Button takePictureBtn;
    private Camera1PreView camera1;
    private CameraPresenter cameraPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePictureBtn = findViewById(R.id.btn_take_picture);
        camera1 = findViewById(R.id.sfv_camera1);
        cameraPresenter = new CameraPresenter(this);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPresenter.takePicture();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraPresenter.attachView(camera1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPresenter.detachView();
    }
}
