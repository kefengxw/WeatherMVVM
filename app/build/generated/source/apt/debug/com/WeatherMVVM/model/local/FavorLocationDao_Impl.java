package com.WeatherMVVM.model.local;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Set;

@SuppressWarnings("unchecked")
public class FavorLocationDao_Impl implements FavorLocationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfFavorLocationBean;

  public FavorLocationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorLocationBean = new EntityInsertionAdapter<FavorLocationBean>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `favor_location_table`(`id`,`latitude`,`longitude`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavorLocationBean value) {
        stmt.bindLong(1, value.id);
        stmt.bindDouble(2, value.latitude);
        stmt.bindDouble(3, value.longitude);
      }
    };
  }

  @Override
  public void insert(FavorLocationBean it) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfFavorLocationBean.insert(it);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<WeaLocation> getFavorLocationLd() {
    final String _sql = "SELECT latitude, longitude FROM favor_location_table LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<WeaLocation>() {
      private Observer _observer;

      @Override
      protected WeaLocation compute() {
        if (_observer == null) {
          _observer = new Observer("favor_location_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
          final WeaLocation _result;
          if(_cursor.moveToFirst()) {
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _result = new WeaLocation(_tmpLatitude,_tmpLongitude);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
