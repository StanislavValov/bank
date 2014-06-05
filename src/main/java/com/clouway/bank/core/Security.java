package com.clouway.bank.core;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Security {

  public String idGenerator(){
    TimeBasedGenerator generator = Generators.timeBasedGenerator();
    return generator.generate().toString();
  }
}
