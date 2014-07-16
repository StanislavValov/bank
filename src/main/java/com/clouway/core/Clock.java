package com.clouway.core;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Clock implements ClockUtil {

  @Override
  public Date expirationDate() {
    long expTime = System.currentTimeMillis() + 1 * 60 * 1000;
    Timestamp time = new Timestamp(System.currentTimeMillis());
    time.setTime(expTime);
    return time;
  }

  @Override
  public Date currentTime() {
    return new Timestamp(System.currentTimeMillis());
  }
}