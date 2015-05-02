package co.kaush.rem.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class ParcelableSparseArray
    extends SparseArray<Object>
    implements Parcelable {

  public static final Parcelable.Creator<ParcelableSparseArray> CREATOR = new Creator<ParcelableSparseArray>() {

    @Override
    public ParcelableSparseArray createFromParcel(Parcel source) {
      return new ParcelableSparseArray(source);
    }

    @Override
    public ParcelableSparseArray[] newArray(int size) {
      return new ParcelableSparseArray[size];
    }
  };

  private SparseArray<Object> _sparseArray;

  public ParcelableSparseArray() {
    super();
  }

  private ParcelableSparseArray(Parcel in) {
    _sparseArray = in.readSparseArray(ParcelableSparseArray.class.getClassLoader());
  }

  // -----------------------------------------------------------------------
  // Parcelable implementation

  public boolean contains(int key) {
    return _sparseArray != null && _sparseArray.indexOfKey(key) >= 0;
  }

  public Object[] values() {
    Object[] values = new Object[size()];

    for (int i = 0; i < size(); i++) {
      values[i] = valueAt(i);
    }

    return values;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeSparseArray(_sparseArray);
  }
}
