package com.clouway.bank.persistence;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class CalendarUtil {

  public static Timestamp june(int year, int day) {
    Calendar calendar = Calendar.getInstance();

    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    Date date = calendar.getTime();

    return new Timestamp(date.getTime());
  }
}
