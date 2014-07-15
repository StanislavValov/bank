package com.clouway.http;

import com.clouway.core.User;

import javax.servlet.http.Cookie;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AuthorisationService {

  boolean isUserAuthorised(User user);
}
