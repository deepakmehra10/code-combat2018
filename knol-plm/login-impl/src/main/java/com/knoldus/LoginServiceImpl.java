package com.knoldus;

import com.knoldus.logininfo.LoginInfo;
import com.knoldus.repository.LoginRepository;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {
    
    
    private final LoginRepository loginRepository;
    
    @Inject
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    
    @Override
    public ServiceCall<LoginInfo, String> login() {
        return request -> loginRepository.getLoginType(request);
        
    }
}
