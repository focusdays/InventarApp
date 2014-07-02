package com.example.inventoryappbase.core.image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

public class Image2TextPresentationModel implements Parcelable {
	
	private String imageOriginalFullName;
	private String imageSmallFullName;
	private String imageMediumFullName;
	private String imageName;
	private String keywords;
	private String similarKeywords;
	private String fileDir;
	
	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public Image2TextPresentationModel() {
		super();
	}
	public Image2TextPresentationModel(Context context) {
		super();
		this.setTempFileDirecotry(context);
	}

	public Image2TextPresentationModel(Parcel in) {
		this.imageOriginalFullName = in.readString();
		this.imageSmallFullName = in.readString();
		this.imageMediumFullName = in.readString();
		this.imageName = in.readString();
		this.keywords = in.readString();
		this.similarKeywords = in.readString();
		this.fileDir = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.imageOriginalFullName);
		dest.writeString(this.imageSmallFullName);
		dest.writeString(this.imageMediumFullName);
		dest.writeString(this.imageName);
		dest.writeString(this.keywords);
		dest.writeString(this.similarKeywords);
		dest.writeString(this.fileDir);
		
	}
	public ImageHelper createImage() {
		return new ImageHelper(this.getImageOriginalFullName());
	}
	public ImageHelper createSmallImage(ImageHelper image) throws IOException {
		ImageHelper small = this.createShrinkedImage(image, Size.SMALL);
		this.setImageSmallFullName(small.getFileName());
		return small;
	}
	public ImageHelper createMediumImage(ImageHelper image) throws IOException {
		ImageHelper medium = this.createShrinkedImage(image, Size.MEDIUM);
		this.setImageMediumFullName(medium.getFileName());
		return medium;
	}
	protected ImageHelper createShrinkedImage(ImageHelper image, Size size) throws IOException {
		ImageHelper shrink = image.shrink(this.generateNewFileName(), size);
		shrink.savePictureBytes();
		return shrink;
	}
	
	public void setTempFileDirecotry(Context context) {
		this.setFileDir(this.getFileDir(context).getAbsolutePath());
	}
	public String generateNewFileName() {
		String newImageName = "image-" + UUID.randomUUID() + ".jpg";
		return this.getFile(newImageName).toString();
	}
	public Uri getImageOriginalUri() {
		if (this.getImageOriginalFullName() != null) {
			return Uri.fromFile(new File(this.getImageOriginalFullName()));
		} else {
			return null;
		}
	}
	public Uri getImageSmallUri() {
		if (this.getImageSmallFullName() != null) {
			return Uri.fromFile(new File(this.getImageSmallFullName()));
		} else {
			return null;
		}
	}
	public Uri getImageMediumUri() {
		if (this.getImageMediumFullName() != null) {
			return Uri.fromFile(new File(this.getImageMediumFullName()));
		} else {
			return null;
		}
	}
	private File getFile(String name) {
		return new File(this.getFileDir(), name);
	}
	private File getFileDir(Context context) {
		final File file = new File(Environment.getExternalStorageDirectory(),
				context.getPackageName());
		if (!file.exists())
			file.mkdirs();
		return file;
	}
	
	public boolean hasValidImage() {
		return this.getImageOriginalFullName() != null && !this.getImageOriginalFullName().equals("");
	}
	
	/* accessors */
	public String getImageSmallFullName() {
		return imageSmallFullName;
	}

	public void setImageSmallFullName(String imageSmallFullName) {
		this.imageSmallFullName = imageSmallFullName;
	}

	public String getImageMediumFullName() {
		return imageMediumFullName;
	}

	public void setImageMediumFullName(String imageMediumFullName) {
		this.imageMediumFullName = imageMediumFullName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getImageOriginalFullName() {
		return imageOriginalFullName;
	}
	public void setImageOriginalFullName(String imageOriginalFullName) {
		this.imageOriginalFullName = imageOriginalFullName;
	}

	public String getSimilarKeywords() {
		return similarKeywords;
	}

	public void setSimilarKeywords(String similarKeywords) {
		this.similarKeywords = similarKeywords;
	}

	

}
