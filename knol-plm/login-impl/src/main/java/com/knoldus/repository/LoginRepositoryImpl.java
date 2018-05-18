package com.knoldus.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.exception.CassandraMappingException;
import com.knoldus.logininfo.LoginInfo;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;
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
        public CompletionStage<String> getLoginType(LoginInfo loginInfo) {
        return session
                .selectOne(LOGIN_TYPE_BY_USERNAME, loginInfo.getUsername(), loginInfo.getPassword())
                .thenApply(optRow -> optRow.map(userType -> userType.getString("user_type"))
                        .orElseThrow(RuntimeException::new));
    }
}
