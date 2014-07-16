package com.clouway.core;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * Created by hisazzul@gmail.com on 7/16/14.
 */
public class IdGenerator implements Generator{

    @Override
    public String getUniqueId(User user){
        HashFunction hf = Hashing.sha1();
        HashCode hashCode = hf.hashString(user.getUserName() + user.getPassword() + System.currentTimeMillis());
        return hashCode.toString();
    }
}