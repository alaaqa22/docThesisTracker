package dtt.business.backing;

import dtt.business.utilities.Hashing;
import dtt.business.utilities.SessionInfo;
import dtt.dataAccess.repository.interfaces.UserDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.User;
import dtt.global.utilities.ConfigReader;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginBackingTest {
    private LoginBacking loginBacking;

    /**
     * Mocks.
     */
    @Mock
    private UserDAO userDAO;

    @Mock
    private Hashing hashing;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ConfigReader.loadProperties();
        loginBacking = new LoginBacking();
        loginBacking.setUserDAO(userDAO);
       // loginBacking.setSessionInfo(new SessionInfo());
        //loginBacking.init();

    }

    @Test
    void login() throws NoSuchAlgorithmException, InvalidKeySpecException {
      //  User userLogin = new User();
         String email = "test@test.com";
         String password = ("password123");
        loginBacking.getUser().setEmail(email);
        loginBacking.getUser().setPassword(password);

        User userDB = new User();
        userDB.setEmail("test@test.com");
         String passwordSalt = "somesaltvalue";
         String passwordHashed = "Vuy8xT+3S0aHMp8XCn1b9WeYvuLvklmPWqpRcWNC1fE=";
        userDB.setPasswordSalt(passwordSalt);
        userDB.setPasswordHashed(passwordHashed);

        when(userDAO.findUserByEmail(loginBacking.getUser(), new Transaction())).thenReturn(true);
        when(userDAO.findUserByEmail(userDB, new Transaction())).thenReturn(true); //Email in DB
        when(hashing.verifyPassword(anyString(), anyString(), anyString())).thenReturn(true);

        String outcome = loginBacking.login();
        verify(userDAO).findUserByEmail((User) any(User.class), (Transaction) any(Transaction.class));

        String expected = "/views/authenticated/circulationslist?faces-redirect=true";
        assertEquals(expected, outcome);
    }

}