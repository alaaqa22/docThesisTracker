package dtt.business.backing;

import dtt.business.utilities.SessionInfo;
import dtt.global.utilities.ConfigReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class LoginBackingTest {
    private LoginBacking loginBacking;

    @BeforeEach
    void setup() {

        ConfigReader.loadProperties();
        loginBacking = new LoginBacking();
        loginBacking.setUserDAO(new dtt.dataAccess.repository.postgres.UserDAO());
        loginBacking.setSessionInfo(new SessionInfo());
        loginBacking.init();

    }

    @Test
    void login() {
        loginBacking.getUser().setEmail("test@test.com");
        loginBacking.getUser().setPassword("password123");
        String outcome = loginBacking.login();
        String expected = "/views/authenticated/circulationslist?faces-redirect=true";
        assertEquals(expected, outcome);

    }

}