package com.proaula.BankApp;

import com.proaula.BankApp.user.application.dtos.UsuarioDTO;
import com.proaula.BankApp.user.application.services.UsuarioService;
import com.proaula.BankApp.user.domain.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TESTS PARA COMPLEMENTAR LA COBERTURA DE UsuarioService

public class UsuarioServiceTests {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService();

        try {
            var field = UsuarioService.class.getDeclaredField("usuarioRepository");
            field.setAccessible(true);
            field.set(usuarioService, usuarioRepository);
        } catch (Exception e) {
            throw new RuntimeException("Error al inyectar mock", e);
        }
    }

    private UsuarioDTO crearUsuarioValido() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombres("Milton");
        dto.setApellidos("Peluffo");
        dto.setCedula("1234567890");
        dto.setCorreo("milton@test.com");
        dto.setTelefono("3001234567");
        dto.setPin("1234");
        return dto;
    }

    @Test
    void testCorreoInvalidoSinArroba() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setCorreo("milton.test.com");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Correo inválido", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testCorreoNulo() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setCorreo(null);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Correo inválido", ex.getMessage());
    }

    @Test
    void testTelefonoCorto() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setTelefono("12345");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Teléfono inválido: debe tener 10 dígitos", ex.getMessage());
    }

    @Test
    void testTelefonoConLetras() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setTelefono("30012A456");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Teléfono inválido: debe tener 10 dígitos", ex.getMessage());
    }

    @Test
    void testNombreConCaracteresEspeciales() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setNombres("Milton123!");

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Nombre inválido", ex.getMessage());
    }

    @Test
    void testApellidoNulo() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setApellidos(null);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Apellido inválido", ex.getMessage());
    }

    @Test
    void testCedulaCorta() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setCedula("123"); 

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Cédula inválida", ex.getMessage());
    }

    @Test
    void testCedulaNula() {
        UsuarioDTO dto = crearUsuarioValido();
        dto.setCedula(null);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Cédula inválida", ex.getMessage());
    }

    @Test
    void testCedulaYaRegistrada() {
        UsuarioDTO dto = crearUsuarioValido();
        when(usuarioRepository.existsByCedula(dto.getCedula())).thenReturn(true);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Cedula ya registrada", ex.getMessage());
    }

    @Test
    void testTelefonoYaRegistrado() {
        UsuarioDTO dto = crearUsuarioValido();
        when(usuarioRepository.existsByTelefono(dto.getTelefono())).thenReturn(true);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Telefono ya registrado", ex.getMessage());
    }

    @Test
    void testCorreoYaRegistrado() {
        UsuarioDTO dto = crearUsuarioValido();
        when(usuarioRepository.existsByCorreo(dto.getCorreo())).thenReturn(true);

        Exception ex = assertThrows(Exception.class, () -> usuarioService.crearUsuario(dto));
        assertEquals("Correo ya registrado", ex.getMessage());
    }
}
