package com.parabits.paranote.data.models;

import android.database.Cursor;

/**
 * Created by Razjelll on 28.08.2017.
 */

public interface IModelCreator<T> {
      T createFromCursor(Cursor cursor);
}
