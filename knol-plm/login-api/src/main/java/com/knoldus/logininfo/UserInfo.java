package com.knoldus.logininfo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInfo {
    String userName;
    String userType;
}
