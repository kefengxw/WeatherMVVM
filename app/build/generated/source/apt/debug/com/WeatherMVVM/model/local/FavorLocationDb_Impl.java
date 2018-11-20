package com.WeatherMVVM.model.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class FavorLocationDb_Impl extends FavorLocationDb {
  private volatile FavorLocationDao _favorLocationDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `favor_location_table` (`id` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE UNIQUE INDEX `index_favor_location_table_id` ON `favor_location_table` (`id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"14bd74bffb7ddfb6d1ec3e811d255bd3\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `favor_location_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFavorLocationTable = new HashMap<String, TableInfo.Column>(3);
        _columnsFavorLocationTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsFavorLocationTable.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0));
        _columnsFavorLocationTable.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavorLocationTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavorLocationTable = new HashSet<TableInfo.Index>(1);
        _indicesFavorLocationTable.add(new TableInfo.Index("index_favor_location_table_id", true, Arrays.asList("id")));
        final TableInfo _infoFavorLocationTable = new TableInfo("favor_location_table", _columnsFavorLocationTable, _foreignKeysFavorLocationTable, _indicesFavorLocationTable);
        final TableInfo _existingFavorLocationTable = TableInfo.read(_db, "favor_location_table");
        if (! _infoFavorLocationTable.equals(_existingFavorLocationTable)) {
          throw new IllegalStateException("Migration didn't properly handle favor_location_table(com.WeatherMVVM.model.local.FavorLocationBean).\n"
                  + " Expected:\n" + _infoFavorLocationTable + "\n"
                  + " Found:\n" + _existingFavorLocationTable);
        }
      }
    }, "14bd74bffb7ddfb6d1ec3e811d255bd3", "c16e67221c6e8b924dfab8192584e9a3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "favor_location_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `favor_location_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public FavorLocationDao favorLocationDao() {
    if (_favorLocationDao != null) {
      return _favorLocationDao;
    } else {
      synchronized(this) {
        if(_favorLocationDao == null) {
          _favorLocationDao = new FavorLocationDao_Impl(this);
        }
        return _favorLocationDao;
      }
    }
  }
}
