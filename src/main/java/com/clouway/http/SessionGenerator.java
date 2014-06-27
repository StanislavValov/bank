package com.clouway.http;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public class SessionGenerator implements Session {

  @Override
  public String getId(String userName, String password) {
    HashFunction hf = Hashing.sha1();
    HashCode hashCode = hf.hashString(userName + password + System.currentTimeMillis());
    return hashCode.toString();
  }
}
