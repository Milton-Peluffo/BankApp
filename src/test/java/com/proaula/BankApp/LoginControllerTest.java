package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.LoginDTO;
import com.proaula.BankApp.user.application.services.LoginService;
import com.proaula.BankApp.user.infrastructure.controllers.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    private LoginService loginService;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginService = Mockito.mock(LoginService.class);
        loginController = new LoginController();
        
        try {
            var field = LoginController.class.getDeclaredField("loginService");
            field.setAccessible(true);
            field.set(loginController, loginService);
        } catch (Exception e) {
            throw new RuntimeException("Error al inyectar mock", e);
        }
    }

    @Test
    void testLoginExitoso() {
        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("1234");

        when(loginService.login(dto)).thenReturn("login exitoso");

        String resultado = loginController.login(dto);

        assertEquals("login exitoso", resultado);
    }

    @Test
    void testLoginFallido() {
        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("9999");

        when(loginService.login(dto)).thenReturn("telefono o pin incorrecto");

        String resultado = loginController.login(dto);

        assertEquals("telefono o pin incorrecto", resultado);
    }
}
