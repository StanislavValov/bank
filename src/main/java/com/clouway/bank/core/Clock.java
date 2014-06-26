package com.clouway.bank.core;

import java.sql.Timestamp;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Clock implements ClockUtil {

  @Override
  public Timestamp expirationDate() {
    long expTime = System.currentTimeMillis()+1*60*1000;
    Timestamp time = new Timestamp(System.currentTimeMillis());
    time.setTime(expTime);
    return time;
  }

  @Override
  public Timestamp currentTime() {
    return new Timestamp(System.currentTimeMillis());
  }
}