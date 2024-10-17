package com.example.weatherapp.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.weatherapp.data.model.Alert;
import com.example.weatherapp.data.model.Alerts;
import com.example.weatherapp.data.model.Condition;
import com.example.weatherapp.data.model.Converters;
import com.example.weatherapp.data.model.Current;
import com.example.weatherapp.data.model.Forecast;
import com.example.weatherapp.data.model.Forecastday;
import com.example.weatherapp.data.model.Location;
import com.example.weatherapp.data.model.WeatherResponse;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherResponse> __insertionAdapterOfWeatherResponse;

  private final Converters __converters = new Converters();

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherResponse = new EntityInsertionAdapter<WeatherResponse>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather` (`id`,`alert`,`currentcloud`,`currentfeelslike_c`,`currentfeelslike_f`,`currentgust_kph`,`currenthumidity`,`currentis_day`,`currentprecip_in`,`currentprecip_mm`,`currentpressure_in`,`currentpressure_mb`,`currenttemp_c`,`currenttemp_f`,`currentuv`,`currentvis_km`,`currentvis_miles`,`currentwind_degree`,`currentwind_kph`,`currentwind_mph`,`currentcode`,`currenticon`,`currenttext`,`forecastforecastday`,`locationcountry`,`locationlat`,`locationlocaltime`,`locationlocaltime_epoch`,`locationlon`,`locationname`,`locationregion`,`locationtz_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherResponse entity) {
        statement.bindLong(1, entity.getId());
        final Alerts _tmpAlerts = entity.getAlerts();
        final String _tmp = __converters.listToJson(_tmpAlerts.getAlert());
        statement.bindString(2, _tmp);
        final Current _tmpCurrent = entity.getCurrent();
        statement.bindLong(3, _tmpCurrent.getCloud());
        statement.bindDouble(4, _tmpCurrent.getFeelslike_c());
        statement.bindDouble(5, _tmpCurrent.getFeelslike_f());
        statement.bindDouble(6, _tmpCurrent.getGust_kph());
        statement.bindLong(7, _tmpCurrent.getHumidity());
        statement.bindLong(8, _tmpCurrent.is_day());
        statement.bindDouble(9, _tmpCurrent.getPrecip_in());
        statement.bindDouble(10, _tmpCurrent.getPrecip_mm());
        statement.bindDouble(11, _tmpCurrent.getPressure_in());
        statement.bindDouble(12, _tmpCurrent.getPressure_mb());
        statement.bindDouble(13, _tmpCurrent.getTemp_c());
        statement.bindDouble(14, _tmpCurrent.getTemp_f());
        statement.bindDouble(15, _tmpCurrent.getUv());
        statement.bindDouble(16, _tmpCurrent.getVis_km());
        statement.bindDouble(17, _tmpCurrent.getVis_miles());
        statement.bindLong(18, _tmpCurrent.getWind_degree());
        statement.bindDouble(19, _tmpCurrent.getWind_kph());
        statement.bindDouble(20, _tmpCurrent.getWind_mph());
        final Condition _tmpCondition = _tmpCurrent.getCondition();
        statement.bindLong(21, _tmpCondition.getCode());
        statement.bindString(22, _tmpCondition.getIcon());
        statement.bindString(23, _tmpCondition.getText());
        final Forecast _tmpForecast = entity.getForecast();
        final String _tmp_1 = __converters.foreCastDayJistToJson(_tmpForecast.getForecastday());
        statement.bindString(24, _tmp_1);
        final Location _tmpLocation = entity.getLocation();
        statement.bindString(25, _tmpLocation.getCountry());
        statement.bindDouble(26, _tmpLocation.getLat());
        statement.bindString(27, _tmpLocation.getLocaltime());
        statement.bindLong(28, _tmpLocation.getLocaltime_epoch());
        statement.bindDouble(29, _tmpLocation.getLon());
        statement.bindString(30, _tmpLocation.getName());
        statement.bindString(31, _tmpLocation.getRegion());
        statement.bindString(32, _tmpLocation.getTz_id());
      }
    };
  }

  @Override
  public Object insertWeatherData(final WeatherResponse weatherResponse,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherResponse.insert(weatherResponse);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<WeatherResponse> getWeather() {
    final String _sql = "select * from weather";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"weather"}, new Callable<WeatherResponse>() {
      @Override
      @NonNull
      public WeatherResponse call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAlert = CursorUtil.getColumnIndexOrThrow(_cursor, "alert");
          final int _cursorIndexOfCloud = CursorUtil.getColumnIndexOrThrow(_cursor, "currentcloud");
          final int _cursorIndexOfFeelslikeC = CursorUtil.getColumnIndexOrThrow(_cursor, "currentfeelslike_c");
          final int _cursorIndexOfFeelslikeF = CursorUtil.getColumnIndexOrThrow(_cursor, "currentfeelslike_f");
          final int _cursorIndexOfGustKph = CursorUtil.getColumnIndexOrThrow(_cursor, "currentgust_kph");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "currenthumidity");
          final int _cursorIndexOfIsDay = CursorUtil.getColumnIndexOrThrow(_cursor, "currentis_day");
          final int _cursorIndexOfPrecipIn = CursorUtil.getColumnIndexOrThrow(_cursor, "currentprecip_in");
          final int _cursorIndexOfPrecipMm = CursorUtil.getColumnIndexOrThrow(_cursor, "currentprecip_mm");
          final int _cursorIndexOfPressureIn = CursorUtil.getColumnIndexOrThrow(_cursor, "currentpressure_in");
          final int _cursorIndexOfPressureMb = CursorUtil.getColumnIndexOrThrow(_cursor, "currentpressure_mb");
          final int _cursorIndexOfTempC = CursorUtil.getColumnIndexOrThrow(_cursor, "currenttemp_c");
          final int _cursorIndexOfTempF = CursorUtil.getColumnIndexOrThrow(_cursor, "currenttemp_f");
          final int _cursorIndexOfUv = CursorUtil.getColumnIndexOrThrow(_cursor, "currentuv");
          final int _cursorIndexOfVisKm = CursorUtil.getColumnIndexOrThrow(_cursor, "currentvis_km");
          final int _cursorIndexOfVisMiles = CursorUtil.getColumnIndexOrThrow(_cursor, "currentvis_miles");
          final int _cursorIndexOfWindDegree = CursorUtil.getColumnIndexOrThrow(_cursor, "currentwind_degree");
          final int _cursorIndexOfWindKph = CursorUtil.getColumnIndexOrThrow(_cursor, "currentwind_kph");
          final int _cursorIndexOfWindMph = CursorUtil.getColumnIndexOrThrow(_cursor, "currentwind_mph");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currentcode");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "currenticon");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "currenttext");
          final int _cursorIndexOfForecastday = CursorUtil.getColumnIndexOrThrow(_cursor, "forecastforecastday");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "locationcountry");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "locationlat");
          final int _cursorIndexOfLocaltime = CursorUtil.getColumnIndexOrThrow(_cursor, "locationlocaltime");
          final int _cursorIndexOfLocaltimeEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "locationlocaltime_epoch");
          final int _cursorIndexOfLon = CursorUtil.getColumnIndexOrThrow(_cursor, "locationlon");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationname");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "locationregion");
          final int _cursorIndexOfTzId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationtz_id");
          final WeatherResponse _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Alerts _tmpAlerts;
            final List<Alert> _tmpAlert;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfAlert);
            _tmpAlert = __converters.jsonToList(_tmp);
            _tmpAlerts = new Alerts(_tmpAlert);
            final Current _tmpCurrent;
            final int _tmpCloud;
            _tmpCloud = _cursor.getInt(_cursorIndexOfCloud);
            final double _tmpFeelslike_c;
            _tmpFeelslike_c = _cursor.getDouble(_cursorIndexOfFeelslikeC);
            final double _tmpFeelslike_f;
            _tmpFeelslike_f = _cursor.getDouble(_cursorIndexOfFeelslikeF);
            final double _tmpGust_kph;
            _tmpGust_kph = _cursor.getDouble(_cursorIndexOfGustKph);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final int _tmpIs_day;
            _tmpIs_day = _cursor.getInt(_cursorIndexOfIsDay);
            final double _tmpPrecip_in;
            _tmpPrecip_in = _cursor.getDouble(_cursorIndexOfPrecipIn);
            final double _tmpPrecip_mm;
            _tmpPrecip_mm = _cursor.getDouble(_cursorIndexOfPrecipMm);
            final double _tmpPressure_in;
            _tmpPressure_in = _cursor.getDouble(_cursorIndexOfPressureIn);
            final double _tmpPressure_mb;
            _tmpPressure_mb = _cursor.getDouble(_cursorIndexOfPressureMb);
            final double _tmpTemp_c;
            _tmpTemp_c = _cursor.getDouble(_cursorIndexOfTempC);
            final double _tmpTemp_f;
            _tmpTemp_f = _cursor.getDouble(_cursorIndexOfTempF);
            final double _tmpUv;
            _tmpUv = _cursor.getDouble(_cursorIndexOfUv);
            final double _tmpVis_km;
            _tmpVis_km = _cursor.getDouble(_cursorIndexOfVisKm);
            final double _tmpVis_miles;
            _tmpVis_miles = _cursor.getDouble(_cursorIndexOfVisMiles);
            final int _tmpWind_degree;
            _tmpWind_degree = _cursor.getInt(_cursorIndexOfWindDegree);
            final double _tmpWind_kph;
            _tmpWind_kph = _cursor.getDouble(_cursorIndexOfWindKph);
            final double _tmpWind_mph;
            _tmpWind_mph = _cursor.getDouble(_cursorIndexOfWindMph);
            final Condition _tmpCondition;
            final int _tmpCode;
            _tmpCode = _cursor.getInt(_cursorIndexOfCode);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            _tmpCondition = new Condition(_tmpCode,_tmpIcon,_tmpText);
            _tmpCurrent = new Current(_tmpCloud,_tmpCondition,_tmpFeelslike_c,_tmpFeelslike_f,_tmpGust_kph,_tmpHumidity,_tmpIs_day,_tmpPrecip_in,_tmpPrecip_mm,_tmpPressure_in,_tmpPressure_mb,_tmpTemp_c,_tmpTemp_f,_tmpUv,_tmpVis_km,_tmpVis_miles,_tmpWind_degree,_tmpWind_kph,_tmpWind_mph);
            final Forecast _tmpForecast;
            final List<Forecastday> _tmpForecastday;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfForecastday);
            _tmpForecastday = __converters.foreCastDayJsonToList(_tmp_1);
            _tmpForecast = new Forecast(_tmpForecastday);
            final Location _tmpLocation;
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            final String _tmpLocaltime;
            _tmpLocaltime = _cursor.getString(_cursorIndexOfLocaltime);
            final int _tmpLocaltime_epoch;
            _tmpLocaltime_epoch = _cursor.getInt(_cursorIndexOfLocaltimeEpoch);
            final double _tmpLon;
            _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            final String _tmpTz_id;
            _tmpTz_id = _cursor.getString(_cursorIndexOfTzId);
            _tmpLocation = new Location(_tmpCountry,_tmpLat,_tmpLocaltime,_tmpLocaltime_epoch,_tmpLon,_tmpName,_tmpRegion,_tmpTz_id);
            _result = new WeatherResponse(_tmpId,_tmpAlerts,_tmpCurrent,_tmpForecast,_tmpLocation);
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
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
