package com.example.inventoryapp;

import java.io.File;
import java.io.FileNotFoundException;
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
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VideoServer extends RoboActivity implements SurfaceHolder.Callback, AutoFocusCallback {
	final static String TAG = "Camera";
    TextView testView;

    Camera camera;
    SurfaceHolder surfaceHolder;
    
	@InjectView(R.id.surfaceview) 		private SurfaceView surfaceView;
	@InjectView(R.id.capture_image) 	private Button capture_image;
	@InjectView(R.id.capture_stop) 		private Button capture_image_stop;
	@InjectView(R.id.container) 		private LinearLayout layout;
	
	
	@InjectView(R.id.capture_number)	private TextView capture_number;
	private ArrayList<String> capturedImages = new ArrayList<String>();

	private void addCapturedImage(File newFileName) {
		this.capturedImages.add(newFileName.getAbsolutePath());
		this.capture_number.setText(Integer.toString(this.capturedImages.size()));
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
    
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        capture_image_stop.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View arg0) {
            	
            	stop_camera();
        		Intent result = new Intent();
        		result.putStringArrayListExtra("captured_images", capturedImages);
        		setResult(RESULT_OK, result);
        		finish();
            }
        });
        capture_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
                	  
        layout.setOnClickListener(new LinearLayout.OnClickListener(){

			   @Override
			   public void onClick(View arg0) {
				   capture_image.setEnabled(false);
				   camera.autoFocus(VideoServer.this);
			   }
        });

    }
    
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (success) capture_image.setEnabled(true);
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

    private void captureImage() {
        camera.takePicture(null, null, new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                	File newImageFile = getNewImageFile("inventoryApp", "jpg");
                    outStream = new FileOutputStream(newImageFile);
                    try {
	                    outStream.write(data);
	                    addCapturedImage(newImageFile);
	                    Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
                    } finally {
                    	outStream.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onPictureTaken - jpeg");
            }
        });
        stop_camera();
        start_camera();
    }

    private void start_camera()
    {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            Log.e(TAG, "init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        param.setFocusMode(Parameters.FOCUS_MODE_AUTO);
        setCameraDisplayOrientation(this, 0, camera);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e(TAG, "init_camera: " + e);
            return;
        }
    }

    private void stop_camera()
    {
    	if (camera != null) {
	        camera.stopPreview();
	        camera.release();
	        camera = null;
    	}
    }

    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
    	if (surfaceHolder.getSurface() == null){
            // preview surface does not exist
            return;
          }

          // stop preview before making changes
          try {
              camera.stopPreview();
          } catch (Exception e){
            // ignore: tried to stop a non-existent preview
          }

          // set preview size and make any resize, rotate or
          // reformatting changes here

          Camera.Parameters param;
          param = camera.getParameters();
          param.setFocusMode(Parameters.FOCUS_MODE_AUTO);
          setCameraDisplayOrientation(this, 0, camera);

          // start preview with new settings
          try {
              camera.setPreviewDisplay(surfaceHolder);
              camera.startPreview();

          } catch (Exception e){
              Log.d(TAG, "Error starting camera preview: " + e.getMessage());
          }
    }

    public void surfaceCreated(SurfaceHolder holder) {
    	start_camera();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	this.stop_camera();
    }

	@Override
	protected void onPause() {
		super.onPause();
		this.stop_camera();
	}


}
