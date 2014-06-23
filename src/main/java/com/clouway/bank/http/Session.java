package com.clouway.bank.http;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */

public interface Session {

  String getId(String userName, String password);
}
