package tmand13.math_thinking.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by tmand on 7/4/2018.
 */

public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
