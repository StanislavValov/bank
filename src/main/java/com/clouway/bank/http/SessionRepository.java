package com.clouway.bank.http;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import javax.servlet.http.Cookie;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public class SessionRepository implements Session {

  @Override
  public String getId(){
    TimeBasedGenerator generator = Generators.timeBasedGenerator();

    return generator.generate().toString();
  }

  @Override
  public Cookie getCookie(String name, String value) {
    return new Cookie(name,value);
  }
}