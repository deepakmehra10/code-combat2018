package com.knoldus.logininfo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginInfo {

    
    String username;
    String password;


}
