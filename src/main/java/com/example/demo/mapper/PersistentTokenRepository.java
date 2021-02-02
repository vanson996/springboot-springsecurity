package com.example.demo.mapper;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * @author WYX
 * @date 2021/1/25
 */
public interface PersistentTokenRepository {

    void createNewToken(PersistentRememberMeToken var1);

    void updateToken(String varl, String var2, Date var3);

    PersistentRememberMeToken getTokenForSeries(String var1);

    void removeUserTokens(String var1);

}
