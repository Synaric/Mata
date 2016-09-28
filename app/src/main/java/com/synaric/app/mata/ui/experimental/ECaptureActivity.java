package com.synaric.app.mata.ui.experimental;

import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.synaric.app.mata.R;
import com.synaric.app.mata.ui.base.BaseActivity;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class ECaptureActivity extends BaseActivity {

    public static final String TAG = "ECaptureActivity";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    openCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    private CameraBridgeViewBase openCvCameraView;

    private CameraBridgeViewBase.CvCameraViewListener2 listener = new CameraBridgeViewBase.CvCameraViewListener2() {
        @Override
        public void onCameraViewStarted(int width, int height) {

        }

        @Override
        public void onCameraViewStopped() {

        }

        @Override
        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
            return inputFrame.rgba();
        }
    };

    @Override
    public void onCreate() {
        openCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        openCvCameraView.setVisibility(SurfaceView.VISIBLE);
        openCvCameraView.setCvCameraViewListener(listener);
    }

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_ecapture;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (openCvCameraView != null)
            openCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (openCvCameraView != null)
            openCvCameraView.disableView();
    }
}
