package com.clouway.bank.http;

import com.clouway.bank.core.User;

import javax.servlet.http.Cookie;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AuthorisationService {

  Cookie authenticate(User user);
}
