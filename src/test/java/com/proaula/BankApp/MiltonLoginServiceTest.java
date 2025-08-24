package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.LoginDTO;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;
import com.proaula.BankApp.user.application.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

public class MiltonLoginServiceTest {

    private UsuarioRepository usuarioRepository;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        loginService = new LoginService(usuarioRepository);
    }

    // -------------------- PRUEBA, TELEFONO Y PIN CORRECTO -----------------------

    @Test
    void testLoginCorrecto() {
        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("4332");
        usuario.setBloqueado(false);

        Mockito.when(usuarioRepository.findByTelefono("3024442123"))
                .thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("4332"); // mismo pin correcto

        String resultado = loginService.login(dto);

        assertEquals("login exitoso", resultado); // pasa
    }

    // -------------------- PRUEBA, TELEFONO Y PIN INCORRECTO -----------------------

    @Test
    void testLoginIncorrecto() {
        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("4332");
        usuario.setBloqueado(false);

        Mockito.when(usuarioRepository.findByTelefono("3024442123"))
                .thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("9999"); // pin distinto al real

        String resultado = loginService.login(dto);

        assertEquals("telefono o pin incorrecto", resultado); // pasa
    }


}
