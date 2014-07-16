package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface AccountService {

    boolean userExists(User user);

    void registerUser(User user);

    void deleteUser(User user);
}
