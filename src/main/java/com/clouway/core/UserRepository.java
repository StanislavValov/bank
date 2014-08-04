package com.clouway.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public interface UserRepository {

    boolean exists(String userName);

    void register(User user);
}
