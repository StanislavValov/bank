package com.clouway.bank.core;

import java.sql.Timestamp;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public interface ClockUtil {

  Timestamp currentTime();

  Timestamp expirationDate();
}
