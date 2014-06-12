package com.clouway.bank.core;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class TimeUtility {

  public static Date time(int year,int month, int day) {

    Calendar calendar = Calendar.getInstance();
    calendar.set(year,month,day);

    return calendar.getTime();
  }
}