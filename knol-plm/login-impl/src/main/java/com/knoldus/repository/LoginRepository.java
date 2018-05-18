package com.knoldus.repository;

import com.knoldus.logininfo.LoginInfo;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface LoginRepository {
    
    
    CompletionStage<UserLogin> getLoginType(LoginInfo loginInfo);
    
    
}
