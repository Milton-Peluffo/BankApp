package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.LoginDTO;
import com.proaula.BankApp.user.domain.models.Usuario;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;
import com.proaula.BankApp.user.application.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class LoginServiceTests {

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

        when(usuarioRepository.findByTelefono("3024442123"))
                .thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("4332"); // prueba con un pin correcto

        String resultado = loginService.login(dto);

        assertEquals("login exitoso", resultado);
    }

    // -------------------- PRUEBA, TELEFONO Y PIN INCORRECTO -----------------------

    @Test
    void testLoginIncorrecto() {

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("4332");
        usuario.setBloqueado(false);

        when(usuarioRepository.findByTelefono("3024442123"))
                .thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("9999"); // prueba con un pin distinto al real

        String resultado = loginService.login(dto);

        assertEquals("telefono o pin incorrecto", resultado);
    }

    // -------------------- PRUEBA, EL PIN DEBE TENER EXACTAMENTE 4 DIGITOS -----------------------

    @Test
    void testPinConLetrasOCaracteres() {

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("12a4");

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");

        when(usuarioRepository.findByTelefono("3024442123")).thenReturn(usuario);

        String resultado = loginService.login(dto);

        assertEquals("el pin solo debe ser numerico", resultado);
    }

    // ----------------------- PRUEBA, EL PIN NO DEBE ESTAR VACÍO -------------------------

    @Test
    void testPinVacio() {

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("");

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");

        when(usuarioRepository.findByTelefono("3024442123")).thenReturn(usuario);

        String resultado = loginService.login(dto);

        assertEquals("el pin no puede estar vacio", resultado);
    }

    //---------------------- TEST, BLOQUE SI FALLA 3 VECES EL PIN -------------------------

    @Test
    void testBloqueoTrasTresIntentosFallidos() {

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("4332");
        usuario.setBloqueado(false);

        Mockito.when(usuarioRepository.findByTelefono("3024442123"))
                .thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("9999");

        for (int i = 1; i <= 3; i++) {
            String resultado = loginService.login(dto);
            if (i < 3) {
                assertEquals("telefono o pin incorrecto", resultado);
            } else {
                assertEquals("cuenta bloqueada", resultado);
            }
        }

        assertTrue(usuario.isBloqueado(), "El usuario debe quedar marcado como bloqueado");
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
    }

}