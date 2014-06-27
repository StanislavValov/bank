package com.clouway.persistence;

import com.clouway.core.AccountService;
import com.clouway.core.BankService;
import com.clouway.http.AuthorisationService;
import com.clouway.http.SessionService;
import com.google.inject.servlet.ServletModule;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentModule extends ServletModule {

  @Override
  protected void configureServlets() {
    bind(BankService.class).to(PersistentBankService.class);
    bind(AccountService.class).to(PersistentAccountService.class);
    bind(SessionService.class).to(PersistentSessionService.class);
    bind(AuthorisationService.class).to(PersistentSessionService.class);
  }
}
