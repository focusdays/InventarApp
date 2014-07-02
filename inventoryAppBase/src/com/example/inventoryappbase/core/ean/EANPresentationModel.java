package com.example.inventoryappbase.core.ean;

import org.robobinding.presentationmodel.DependsOnStateOf;
import org.robobinding.presentationmodel.PresentationModel;

import android.os.Parcel;
import android.os.Parcelable;

@PresentationModel
public class EANPresentationModel  implements Parcelable {

	private String barcode;
	private String format;
	private String title;

	public EANPresentationModel() {
	}

	public EANPresentationModel(Parcel in) {
		this.barcode = in.readString();
		this.format = in.readString();
		this.title = in.readString();
	}
	
	@DependsOnStateOf({"barcode", "format"})
	public String getFormatAndCode() {
		return this.getFormat() + ":"+this.getBarcode();
	}
	
	/* Get + set */
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.barcode);
		dest.writeString(this.format);
		dest.writeString(this.title);
	}

}
