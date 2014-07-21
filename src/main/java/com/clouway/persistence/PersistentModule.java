package com.clouway.persistence;

import com.clouway.core.AccountService;
import com.clouway.core.SessionService;
import com.clouway.core.BankService;
import com.clouway.http.AuthorisationService;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BankService.class).to(PersistentBankService.class);
    bind(AccountService.class).to(PersistentAccountService.class);
    bind(SessionService.class).to(PersistentSessionService.class);
    bind(AuthorisationService.class).to(PersistentAccountService.class);
  }
}