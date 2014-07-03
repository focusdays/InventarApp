package com.example.inventoryappbase.core.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import com.example.inventoryappbase.core.ean.Upload;

public class ImageHelper {

	private Bitmap image;
	private Size size;
	private String fileName;

	private long timeInMs;

	public ImageHelper(Bitmap imageBinary, String fileName) {
		this.image = imageBinary;
		this.size = Size.ORIGINAL;
		this.fileName = fileName;
	}
	public ImageHelper(Bitmap imageBinary, String fileName, Size size) {
		this.image = imageBinary;
		this.size = size;
		this.fileName = fileName;
	}
	public ImageHelper(String fileNameOrURL) {
		this.fileName = fileNameOrURL;
		this.image = createBitmapFromFileOrURL(fileNameOrURL);
		this.size = Size.fromFileName(fileNameOrURL);
	}

	public Bitmap getImage() {
		return image;
	}
	public Size getSize() {
		return size;
	}
	public String getFileName() {
		return fileName;
	}
	public Uri getFileNameUri() {
		return Uri.fromFile(new File(this.fileName));
	}
	
	public String getFileName(String fileName) {
		return this.getSize().addToFileName(fileName);
	}
	
	public ImageHelper shrink(String newFileName, Size maxSize) throws IOException {
		float scaleW = (float)maxSize.getWidth() / (float)this.getImage().getWidth();
		Matrix matrix = new Matrix();
        matrix.setScale(scaleW, scaleW);
        return new ImageHelper(
			Bitmap.createBitmap(
					this.getImage(), 
					0, 0, this.getImage().getWidth(), this.getImage().getHeight(),
					matrix, false), 
			maxSize.addToFileName(newFileName),
			maxSize);
	}
	
	public void savePictureBytes() throws IOException {
		OutputStream io = new FileOutputStream(this.getFileName());
		if (io != null) {
			try {
				this.image.compress(Bitmap.CompressFormat.JPEG, 100, io);
			} finally {
				io.close();
			}
		}
	}

	public String detectKeywords() {
		long  time = System.currentTimeMillis();
		try {
			try {
				return this.uploadAndAnalyze(this.getFileName());
			} catch (IOException e) {
				return e.getMessage();
			}
		} finally {
			this.timeInMs = System.currentTimeMillis() - time;
		}
	}



	private String uploadAndAnalyze(String fullFileName) throws IOException {
			return new Upload().multipartRequest(
					"http://inventory42-focusdays14.rhcloud.com/UploadAndDetectServlet", 
					"submit=1", 
					fullFileName, 
					"file", 
					"image/jpeg");
	
	}
	public long getTimeInMs() {
		return timeInMs;
	}
	
	private Bitmap createBitmapFromFileOrURL(String fileOrURL) {
		try {
			URL url = new URL(fileOrURL);
			InputStream in = url.openStream();
			try {
				return BitmapFactory.decodeStream(url.openStream());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		} catch (MalformedURLException e) {
			return BitmapFactory.decodeFile(fileOrURL);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}


}
