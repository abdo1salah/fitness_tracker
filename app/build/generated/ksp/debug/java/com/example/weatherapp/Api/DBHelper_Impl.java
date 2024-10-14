package com.example.weatherapp.Api;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DBHelper_Impl extends DBHelper {
  private volatile WeatherDao _weatherDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `weather` (`id` INTEGER NOT NULL, `alert` TEXT NOT NULL, `currentcloud` INTEGER NOT NULL, `currentfeelslike_c` REAL NOT NULL, `currentfeelslike_f` REAL NOT NULL, `currentgust_kph` REAL NOT NULL, `currenthumidity` INTEGER NOT NULL, `currentis_day` INTEGER NOT NULL, `currentprecip_in` REAL NOT NULL, `currentprecip_mm` REAL NOT NULL, `currentpressure_in` REAL NOT NULL, `currentpressure_mb` REAL NOT NULL, `currenttemp_c` REAL NOT NULL, `currenttemp_f` REAL NOT NULL, `currentuv` REAL NOT NULL, `currentvis_km` REAL NOT NULL, `currentwind_degree` INTEGER NOT NULL, `currentwind_kph` REAL NOT NULL, `currentwind_mph` REAL NOT NULL, `currentcode` INTEGER NOT NULL, `currenticon` TEXT NOT NULL, `currenttext` TEXT NOT NULL, `forecastforecastday` TEXT NOT NULL, `locationcountry` TEXT NOT NULL, `locationlat` REAL NOT NULL, `locationlocaltime` TEXT NOT NULL, `locationlocaltime_epoch` INTEGER NOT NULL, `locationlon` REAL NOT NULL, `locationname` TEXT NOT NULL, `locationregion` TEXT NOT NULL, `locationtz_id` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '03041e4f2b1a4eee72d5b71a3a00c0be')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `weather`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWeather = new HashMap<String, TableInfo.Column>(31);
        _columnsWeather.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("alert", new TableInfo.Column("alert", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentcloud", new TableInfo.Column("currentcloud", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentfeelslike_c", new TableInfo.Column("currentfeelslike_c", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentfeelslike_f", new TableInfo.Column("currentfeelslike_f", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentgust_kph", new TableInfo.Column("currentgust_kph", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currenthumidity", new TableInfo.Column("currenthumidity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentis_day", new TableInfo.Column("currentis_day", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentprecip_in", new TableInfo.Column("currentprecip_in", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentprecip_mm", new TableInfo.Column("currentprecip_mm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentpressure_in", new TableInfo.Column("currentpressure_in", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentpressure_mb", new TableInfo.Column("currentpressure_mb", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currenttemp_c", new TableInfo.Column("currenttemp_c", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currenttemp_f", new TableInfo.Column("currenttemp_f", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentuv", new TableInfo.Column("currentuv", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentvis_km", new TableInfo.Column("currentvis_km", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentwind_degree", new TableInfo.Column("currentwind_degree", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentwind_kph", new TableInfo.Column("currentwind_kph", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentwind_mph", new TableInfo.Column("currentwind_mph", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currentcode", new TableInfo.Column("currentcode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currenticon", new TableInfo.Column("currenticon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("currenttext", new TableInfo.Column("currenttext", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("forecastforecastday", new TableInfo.Column("forecastforecastday", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationcountry", new TableInfo.Column("locationcountry", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationlat", new TableInfo.Column("locationlat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationlocaltime", new TableInfo.Column("locationlocaltime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationlocaltime_epoch", new TableInfo.Column("locationlocaltime_epoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationlon", new TableInfo.Column("locationlon", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationname", new TableInfo.Column("locationname", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationregion", new TableInfo.Column("locationregion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("locationtz_id", new TableInfo.Column("locationtz_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeather = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeather = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWeather = new TableInfo("weather", _columnsWeather, _foreignKeysWeather, _indicesWeather);
        final TableInfo _existingWeather = TableInfo.read(db, "weather");
        if (!_infoWeather.equals(_existingWeather)) {
          return new RoomOpenHelper.ValidationResult(false, "weather(com.example.weatherapp.Api.WeatherResponse).\n"
                  + " Expected:\n" + _infoWeather + "\n"
                  + " Found:\n" + _existingWeather);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "03041e4f2b1a4eee72d5b71a3a00c0be", "61fc943200c90ca96ec91bcfb6f5f015");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "weather");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `weather`");
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
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WeatherDao.class, WeatherDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WeatherDao weetherDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }
}
