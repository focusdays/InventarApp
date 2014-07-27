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
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.model.CommodityModel;
import com.example.inventoryappbase.core.AsyncResponse;
import com.example.inventoryappbase.core.image.Image2TextAsyncTask;
import com.example.inventoryappbase.core.image.Image2TextPresentationModel;

public class VideoServer extends RoboActivity implements SurfaceHolder.Callback, AutoFocusCallback {
	final static String TAG = "Camera";
    TextView testView;

    Camera camera;
    SurfaceHolder surfaceHolder;
    
	@InjectView(R.id.surfaceview) 		private DrawableSurfaceView surfaceView;
	@InjectView(R.id.capture_image) 	private Button capture_image;
	@InjectView(R.id.capture_stop) 		private Button capture_image_stop;
	@InjectView(R.id.container) 		private LinearLayout layout;
	
	@InjectView(R.id.capture_number)	private TextView capture_number;
	@InjectView(R.id.capture_number_title) 	private TextView capture_number_title;
	
	private ArrayList<CommodityModel> capturedImages = new ArrayList<CommodityModel>();
	int detectingImages = 0;

	private void addCapturedImage(File newFileName) {
		CommodityModel newModel = new CommodityModel();
		newModel.setCommodityPicture(newFileName.getAbsolutePath());
		this.capturedImages.add(newModel);
		this.capture_number.setText(Integer.toString(this.capturedImages.size()));
	}
	
	private int startDetecting() {
		++detectingImages;
		capture_number_title.setText(
				this.getResources().getText(R.string.nof_captured_images_detecting) + " "+detectingImages);
		capture_image_stop.setEnabled(false);
		return detectingImages;
	}
	private int stopDetecting() {
		--detectingImages;
		if (detectingImages == 0) {
			capture_number_title.setText(R.string.nof_captured_images);
			capture_image_stop.setEnabled(true);
		} else {
			capture_number_title.setText(
					this.getResources().getText(R.string.nof_captured_images_detecting) + " "+detectingImages);
		}
		return detectingImages;
	}
	private CommodityModel getCapturedImage(int i) {
		return capturedImages.get(i);
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
    
	protected void detectPicture(int captureIndex) {
    	Image2TextPresentationModel model = new Image2TextPresentationModel(this);
    	model.setImageOriginalFullName(getCapturedImage(captureIndex).getCommodityPicture());
    	model.setIndex(captureIndex);
		AsyncResponse<Image2TextPresentationModel, Image2TextPresentationModel> response = new AsyncResponse<Image2TextPresentationModel, Image2TextPresentationModel>() {
			@Override
			public void processFinish(Image2TextPresentationModel model) {
				CommodityModel commodity = getCapturedImage(model.getIndex());
				commodity.setCommodityTitle(model.getKeywords());
	            Toast.makeText(getApplicationContext(), "keywords detected: "+model.getKeywords(), Toast.LENGTH_SHORT).show();
	    		stopDetecting();
			}
			@Override
			public void processTime(long timeInMs) {
			}
			@Override
			public void processProgress(Image2TextPresentationModel model) {
			}
		};
		startDetecting();
		new Image2TextAsyncTask(this, response, false).execute(model);
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
        		result.putExtra("newIndexStart", 0);
        		result.putExtra("newIndexLenght", 0);
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
        surfaceView.setOnTouchListener(new View.OnTouchListener(){

			   @Override
			   public boolean onTouch(View view, MotionEvent event) {
		            if (event.getAction() == MotionEvent.ACTION_DOWN){
		            	float touchX = event.getX();
		            	float touchY = event.getY();
		            	RectF mOriginalRect = new RectF(
		            			touchX-30, touchY-30, touchX+30, touchY+30);
		            	surfaceView.setDrawableRect(mOriginalRect);
		            	surfaceView.invalidate();
		            }
//				   capture_image.setEnabled(false);
				   camera.autoFocus(VideoServer.this);
		           return false;
			   }
        });

    }
    
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (success) {
//			capture_image.setEnabled(true);
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

    private void captureImage() {
        camera.takePicture(null, null, new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {
                	int index = capturedImages.size();
                	File newImageFile = getNewImageFile("inventoryApp", "jpg");
                    outStream = new FileOutputStream(newImageFile);
                    try {
	                    outStream.write(data);
                    } finally {
                    	outStream.close();
                    }
                    addCapturedImage(newImageFile);
                    Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
                    detectPicture(index);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onPictureTaken - jpeg");
              stop_camera();
              start_camera();
            }
        });
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
          param.setFlashMode(Parameters.FLASH_MODE_AUTO);
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
