package com.clouway.persistence;

import com.clouway.core.AccountService;
import com.clouway.core.SessionService;
import com.clouway.core.BankService;
import com.clouway.http.AuthorisationService;
import com.google.inject.servlet.ServletModule;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class PersistentModule extends ServletModule {

  @Override
  protected void configureServlets() {
    bind(BankService.class).to(MongoDbBankService.class);
    bind(AccountService.class).to(MongoDbAccountService.class);
    bind(SessionService.class).to(MongoDbSessionService.class);
    bind(AuthorisationService.class).to(MongoDbAccountService.class);
  }
}
