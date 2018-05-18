package com.knoldus;

import akka.japi.Pair;
import com.knoldus.exception.AuthenticateException;
import com.knoldus.logininfo.LoginInfo;
import com.knoldus.logininfo.UserInfo;
import com.knoldus.repository.LoginRepository;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {
    
    
    private final LoginRepository loginRepository;
    
    @Inject
    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    
    @Override
    public HeaderServiceCall<LoginInfo, UserInfo> login() {
        return (requestHeader, request) -> loginRepository
                .getLoginType(request)
                .thenApply(userLogin -> {
                    boolean isAuthenticated = LoginUtil.authenticate(userLogin.getPassword(), request.getPassword());
                    
                    if (!isAuthenticated) {
                        throw new AuthenticateException("username and password are not correct");
                    }
                    return Pair.create(ResponseHeader.OK, UserInfo.builder()
                            .userName(userLogin.getUserName())
                            .userType(userLogin.getUserType())
                            .build());
                });
    }
}
