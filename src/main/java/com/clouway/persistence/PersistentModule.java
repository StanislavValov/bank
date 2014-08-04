package com.clouway.persistence;

import com.clouway.core.UserRepository;
import com.clouway.core.SessionService;
import com.clouway.core.BankService;
import com.clouway.core.AuthorisationService;
import com.google.inject.AbstractModule;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BankService.class).to(PersistentBankService.class);
    bind(UserRepository.class).to(PersistentUserRepository.class);
    bind(SessionService.class).to(PersistentSessionService.class);
    bind(AuthorisationService.class).to(PersistentUserRepository.class);
  }
}