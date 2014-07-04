package com.example.inventoryapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

	private Camera mCamera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Button capture_image;
	private Button capture_image_stop;

	private boolean previewRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		capture_image = (Button) findViewById(R.id.capture_image);
		// capture_image_stop = (Button) findViewById(R.id.capture_image_stop);

		capture_image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				capture();
			}
		});

		// capture_image_stop.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// stop();
		// }
		// });
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(CameraActivity.this);
		initCamera();
	}

	private void initCamera() {
		try {
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void capture() {
		mCamera.takePicture(null, null, null, new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				mCamera = camera;
				String PATH = getAlbumStorageDir("inventoryApp")
						.getAbsolutePath();

				try {
					FileOutputStream fos = new FileOutputStream(PATH);
					System.out.println(PATH);

					fos.write(data);
					fos.close();
				} catch (java.io.IOException e) {
					System.out.println("could not save");
				}
				Toast.makeText(getApplicationContext(), "Picture Taken",
						Toast.LENGTH_SHORT).show();

				Intent intent = new Intent();
				intent.putExtra("image_path", PATH);
				setResult(RESULT_OK, intent);

				camera.stopPreview();
				if (camera != null) {
					camera.release();
					mCamera = null;
				}

				initCamera();

			}
		});
	}

//	public void surfaceChanged(SurfaceHolder holder, int format, int width,
//			int height) {
//		if (isPreviewRunning()) {
//			mCamera.stopPreview();
//		}
//
//		Parameters parameters = mCamera.getParameters();
//		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
//				.getDefaultDisplay();
//
//		if (display.getRotation() == Surface.ROTATION_0) {
//			parameters.setPreviewSize(height, width);
//			mCamera.setDisplayOrientation(90);
//		}
//
//		if (display.getRotation() == Surface.ROTATION_90) {
//			parameters.setPreviewSize(width, height);
//		}
//
//		if (display.getRotation() == Surface.ROTATION_180) {
//			parameters.setPreviewSize(height, width);
//		}
//
//		if (display.getRotation() == Surface.ROTATION_270) {
//			parameters.setPreviewSize(width, height);
//			mCamera.setDisplayOrientation(180);
//		}
//
//		mCamera.setParameters(parameters);
//		previewCamera();
//	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.e("Surface Changed", "format   ==   " + format + ",   width  ===  "
				+ width + ", height   ===    " + height);
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void previewCamera() {
		try {
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
			setPreviewRunning(true);
		} catch (Exception e) {
		}
	}

	public void stop() {

		mCamera.release();
		mCamera = null;

		finish();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e("Surface Created", "");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("Surface Destroyed", "");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
		}
	}

	public File getAlbumStorageDir(String albumName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName);
		file.mkdirs();
		return file;
	}

	public boolean isPreviewRunning() {
		return previewRunning;
	}

	public void setPreviewRunning(boolean previewRunning) {
		this.previewRunning = previewRunning;
	}

}