package com.knoldus.repository;

import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.logininfo.LoginInfo;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static com.knoldus.Constants.QueryConstants.LOGIN_TYPE_BY_USERNAME;

public class LoginRepositoryImpl implements LoginRepository {
    
    private final CassandraSession session;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    
    @Inject
    public LoginRepositoryImpl(CassandraSession session) {
        this.session = session;
    }
    
    @Override
    public CompletionStage<UserLogin> getLoginType(LoginInfo loginInfo) {
        return session
                .selectOne(LOGIN_TYPE_BY_USERNAME, loginInfo.getUsername())
                .thenApply(optRow -> optRow.map(this::mapToUserLogin)
                        .orElseThrow(RuntimeException::new));
    }
    
    public UserLogin mapToUserLogin(Row userRow) {
        String userName = userRow.getString("email_id");
        String password = userRow.getString("password");
        String loginType = userRow.getString("login_type");
        
        return UserLogin.builder().userName(userName).password(password).userType(loginType).build();
    }
}
