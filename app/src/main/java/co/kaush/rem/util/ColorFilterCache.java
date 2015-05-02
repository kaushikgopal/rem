package co.kaush.rem.util;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import co.kaush.rem.MyApp;
import co.kaush.rem.R;

public class ColorFilterCache {

  private static ParcelableSparseArray _colorFilterCache;

  public static ColorFilter getWhiteColorFilter() {
    return _getPossiblyCachedColorFilter(R.color.white_2);
  }

  // -----------------------------------------------------------------------------------
  // Internal Helpers

  private static ColorFilter _getPossiblyCachedColorFilter(int colorResourceId) {
    if (!_getColorFilterCache().contains(colorResourceId)) {
      _getColorFilterCache().put(colorResourceId, _getColorFilter(colorResourceId));
    }

    return (ColorFilter) _getColorFilterCache().get(colorResourceId);
  }

  private static PorterDuffColorFilter _getColorFilter(int colorResourceId) {
    return new PorterDuffColorFilter(MyApp.get().getResources().getColor(colorResourceId),
        PorterDuff.Mode.SRC_IN);
  }

  private static ParcelableSparseArray _getColorFilterCache() {
    if (_colorFilterCache == null) {
      _colorFilterCache = new ParcelableSparseArray();
    }
    return _colorFilterCache;
  }
}

