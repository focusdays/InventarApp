package com.example.inventoryapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CameraActivity extends RoboActivity implements SurfaceHolder.Callback {

	private final String TAG = "CameraActivity";
	
	private SurfaceHolder mHolder;
	private Camera mCamera;
	@InjectView(R.id.surfaceview) 	private SurfaceView surfaceView;
	

	@InjectView(R.id.capture_image) 	private Button capture_image;
	@InjectView(R.id.capture_stop) 		private Button capture_image_stop;
	
	@InjectView(R.id.capture_number)	private TextView capture_number;
	
	private ArrayList<String> capturedImages = new ArrayList<String>();

	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(0); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	public static void setCameraDisplayOrientation(Activity activity,
		     int cameraId, Camera camera) {

		   Camera.CameraInfo info = 
		       new Camera.CameraInfo();

		   Camera.getCameraInfo(cameraId, info);

		   int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		   int degrees = 0;

		   switch (rotation) {
		       case Surface.ROTATION_0: degrees = 0; break;
		       case Surface.ROTATION_90: degrees = 90; break;
		       case Surface.ROTATION_180: degrees = 180; break;
		       case Surface.ROTATION_270: degrees = 270; break;
		   }

		   int result;
		   if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
		       result = (info.orientation + degrees) % 360;
		       result = (360 - result) % 360;  // compensate the mirror
		   } else {  // back-facing
		       result = (info.orientation - degrees + 360) % 360;
		   }
		   camera.setDisplayOrientation(result);
		}	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		mCamera = getCameraInstance();
		mHolder = surfaceView.getHolder();
		mHolder.addCallback(this);
	}

	private void addCapturedImage(File newFileName) {
		this.capturedImages.add(newFileName.getAbsolutePath());
		this.capture_number.setText(Integer.toString(this.capturedImages.size()));
	}
	public void capture(View v) {
		mCamera.takePicture(null, null, new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				camera.getParameters().getPictureFormat();
				try {
					File newImageFile = getNewImageFile("inventoryApp", "jpg");
					FileOutputStream fos = new FileOutputStream(newImageFile);
					try {
						fos.write(data);
					} finally {
						fos.close();
					}
					addCapturedImage(newImageFile);
					Toast.makeText(getApplicationContext(), "Picture Taken",
							Toast.LENGTH_SHORT).show();
					
				} catch (java.io.IOException e) {
					Toast.makeText(getApplicationContext(), "error while picture taken ("+e.getMessage()+")",
							Toast.LENGTH_LONG).show();
					Log.e(TAG, "error while saving picture"+e.getMessage(), e);
				}
			}
		});
	}


	@Override
	public void surfaceChanged(SurfaceHolder holderNotUsed, int format, int width,
			int height) {
		Log.e("Surface Changed", "format   ==   " + format + ",   width  ===  " + width + ", height   ===    " + height);
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
          }		
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }
		Parameters parameters = mCamera.getParameters();
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		if (display.getRotation() == Surface.ROTATION_0) {
			parameters.setPreviewSize(height, width);
		}

		if (display.getRotation() == Surface.ROTATION_90) {
			parameters.setPreviewSize(width, height);
		}

		if (display.getRotation() == Surface.ROTATION_180) {
			parameters.setPreviewSize(height, width);
		}

		if (display.getRotation() == Surface.ROTATION_270) {
			parameters.setPreviewSize(width, height);
		}
		setCameraDisplayOrientation(this, 0, mCamera);
		parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);
		
		mCamera.setParameters(parameters);
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}


	public void stop(View v) {
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
		mHolder = null;
		
		Intent result = new Intent();
		result.putStringArrayListExtra("captured_images", this.capturedImages);
		setResult(RESULT_OK, result);
		finish();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("Surface Destroyed", "");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
			mHolder = null;
		}
	}

	private File getAlbumStorageDir(String albumName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
		file.mkdirs();
		return file;
	}
	private File getNewImageFile(String albumName, String extension) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		return new File(this.getAlbumStorageDir(albumName), timeStamp+"."+extension);
	}


}