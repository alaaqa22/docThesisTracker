package dtt.business.backing;

import dtt.business.utilities.SystemInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginBackingTest {
    SystemInitializer systemInitializer;
    LoginBacking loginBacking;

    @BeforeEach
    void setup(){
        systemInitializer = new SystemInitializer();
        loginBacking = new LoginBacking();
        loginBacking.init();

    }

    @Test
    void login() {
        loginBacking.getUser().setEmail("test@test.com");
        loginBacking.getUser().setPassword("password123");
        String outcome= loginBacking.login();
        String expected = "/view/authenticated/circulationslist?faces-redirect=true";
        assertEquals(expected,outcome);

    }
    @Test
    void loginFailed() {
        loginBacking.init();
        loginBacking.getUser().setEmail("test1@test.com");
        loginBacking.getUser().setPassword("password123");
        String outcome= loginBacking.login();
        String expected = null;
        assertEquals(expected,outcome);

    }
}