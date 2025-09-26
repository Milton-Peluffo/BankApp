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
        dto.setPin("4332");

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
        dto.setPin("9999");

        String resultado = loginService.login(dto);

        assertEquals("telefono o pin incorrecto", resultado);
    }


    // ============================================ PRUEBAS DEL PIN ===================================================

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

    @Test
    void testPinConLongitudInvalida() {

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");
        usuario.setBloqueado(false);

        when(usuarioRepository.findByTelefono("3024442123"))
                .thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("122223");

        String resultado = loginService.login(dto);
        assertEquals("el pin debe tener exactamente 4 digitos", resultado);
    }

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


    // ============================================ PRUEBAS DEL TELEFONO ===================================================

    @Test
    void testTelefonoVacio() {
        LoginDTO dto = new LoginDTO();
        dto.setTelefono(""); 
        dto.setPin("1234");

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");
        usuario.setBloqueado(false);

        when(usuarioRepository.findByTelefono("")).thenReturn(usuario);

        String resultado = loginService.login(dto);

        assertEquals("el telefono no puede estar vacio", resultado);
    }

    @Test
    void testTelefonoConLetrasOCaracteres() {
        LoginDTO dto = new LoginDTO();
        dto.setTelefono("30244A21@3"); 
        dto.setPin("1234");

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");
        usuario.setBloqueado(false);

        when(usuarioRepository.findByTelefono("30244A21@3")).thenReturn(usuario);

        String resultado = loginService.login(dto);

        assertEquals("el telefono solo debe ser numerico", resultado);
    }

    @Test
    void testTelefonoConLongitudInvalida() {
        LoginDTO dto = new LoginDTO();
        dto.setTelefono("12345"); 
        dto.setPin("1234");

        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");
        usuario.setBloqueado(false);

        when(usuarioRepository.findByTelefono("12345")).thenReturn(usuario);

        String resultado = loginService.login(dto);

        assertEquals("el telefono debe tener exactamente 10 digitos", resultado);
    }


    // ##################################### TEST, BLOQUEO SI FALLA 3 VECES EL PIN #################################

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

    @Test
    void testTelefonoNoRegistrado() {
        when(usuarioRepository.findByTelefono("1111111111")).thenReturn(null);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("1111111111");
        dto.setPin("1234");

        String resultado = loginService.login(dto);

        assertEquals("telefono no registrado", resultado);
    }

    @Test
    void testUsuarioBloqueado() {
        Usuario usuario = new Usuario();
        usuario.setTelefono("3024442123");
        usuario.setPin("1234");
        usuario.setBloqueado(true);

        when(usuarioRepository.findByTelefono("3024442123")).thenReturn(usuario);

        LoginDTO dto = new LoginDTO();
        dto.setTelefono("3024442123");
        dto.setPin("1234");

        String resultado = loginService.login(dto);

        assertEquals("cuenta bloqueada", resultado);
    }

    @Test
    void testConstructorVacioLoginService() {
        LoginService service = new LoginService();
        assertNotNull(service);
    }
}
