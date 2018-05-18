package com.knoldus;

import com.google.inject.AbstractModule;
import com.knoldus.repository.LoginRepository;
import com.knoldus.repository.LoginRepositoryImpl;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class LoginModule extends AbstractModule implements ServiceGuiceSupport {
    
    @Override
    protected void configure() {
        bindService(LoginService.class, LoginServiceImpl.class);
        bind(LoginRepository.class).to(LoginRepositoryImpl.class);
    }
}
