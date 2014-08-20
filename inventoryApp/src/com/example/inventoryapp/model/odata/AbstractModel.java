package com.example.inventoryapp.model.odata;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbstractModel implements Parcelable {

	private boolean created;
	private boolean updated;
	private boolean deleted;

	public AbstractModel(Parcel source) {
		this.created = source.readInt() == 1;
		this.updated = source.readInt() == 1;
		this.deleted = source.readInt() == 1;
	}

	public AbstractModel() {
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	protected <T extends AbstractModel> T getFirst(Class<T> classType, List<T> list) {
		if (list != null && list.isEmpty()) return null;
		for (T t : list) {
			if (!t.isDeleted()) return t;
		}
		return null;
	}
	protected <T extends AbstractModel> T setFirst(Class<T> classType, List<T> list, T object) {
		if (list.isEmpty()) {
			list.add(object);
		} else {
			if (this.getFirst(classType, list) != object) {
				for (T t : list) {
					t.setDeleted(true);
				}
				list.add(object);
			}
		}
		return object;
	}

	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/*
		private boolean created;
		private boolean updated;
		private boolean deleted;
		 */
		dest.writeInt(this.created ? 1 : 0);
		dest.writeInt(this.updated ? 1 : 0);
		dest.writeInt(this.deleted ? 1 : 0);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
