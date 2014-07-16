package com.clouway.persistence;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class CalendarUtil {

  public static Date june(int year, int day) {
    Calendar calendar = Calendar.getInstance();

    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }
}
