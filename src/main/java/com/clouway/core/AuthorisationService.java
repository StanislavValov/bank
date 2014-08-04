package com.clouway.core;

import com.clouway.core.User;
import com.google.inject.Singleton;

import javax.servlet.http.Cookie;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AuthorisationService {

  boolean isAuthorised(User user);
}