package com.clouway.bank.http;

import javax.servlet.http.Cookie;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public interface Session {

  String getId();

  Cookie getCookie(String name, String value);
}
