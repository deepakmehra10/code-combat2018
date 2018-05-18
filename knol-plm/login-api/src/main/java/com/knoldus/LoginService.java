package com.knoldus;

import akka.NotUsed;
import com.knoldus.logininfo.LoginInfo;
import com.knoldus.logininfo.UserInfo;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceAcl;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;


public interface LoginService extends Service {
    
    ServiceCall<LoginInfo, UserInfo> login();
    
    
    @Override
    default Descriptor descriptor() {
        return named("login").withCalls(
                pathCall("/api/login", this::login))
                .withAutoAcl(true)
                .withServiceAcls(ServiceAcl.methodAndPath(Method.OPTIONS, "/api/.*"));
        // @formatter:on
    }
}
