package com.clouway.persistence;

import com.clouway.core.UserRepository;
import com.clouway.core.SessionService;
import com.clouway.core.BankService;
import com.clouway.core.AuthorisationService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BankService.class).to(PersistentBankService.class);
    bind(UserRepository.class).to(PersistentUserRepository.class);
    bind(SessionService.class).to(PersistentSessionRepository.class);
    bind(AuthorisationService.class).to(PersistentUserRepository.class);
  }
}