package com.knoldus.repository;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserLogin {
    String userName;
    String password;
    String userType;
}
